package j$.util.concurrent;

import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import io.realm.internal.OsCollectionChangeSet;
import j$.sun.misc.DesugarUnsafe;
import j$.util.Collection;
import j$.util.Spliterator;
import j$.util.stream.Stream;
import j$.util.stream.StreamSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements java.util.concurrent.ConcurrentMap<K, V>, Serializable, ConcurrentMap<K, V> {
    private static final int ABASE;
    private static final int ASHIFT;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final DesugarUnsafe U;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 7249069246763182397L;
    private volatile transient long baseCount;
    private volatile transient int cellsBusy;
    private volatile transient CounterCell[] counterCells;
    private transient EntrySetView entrySet;
    private transient KeySetView keySet;
    private volatile transient Node[] nextTable;
    private volatile transient int sizeCtl;
    volatile transient Node[] table;
    private volatile transient int transferIndex;
    private transient ValuesView values;

    static final int spread(int i) {
        return (i ^ (i >>> 16)) & Integer.MAX_VALUE;
    }

    static {
        Class cls = Integer.TYPE;
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", cls), new ObjectStreamField("segmentShift", cls)};
        DesugarUnsafe unsafe = DesugarUnsafe.getUnsafe();
        U = unsafe;
        SIZECTL = unsafe.objectFieldOffset(ConcurrentHashMap.class, "sizeCtl");
        TRANSFERINDEX = unsafe.objectFieldOffset(ConcurrentHashMap.class, "transferIndex");
        BASECOUNT = unsafe.objectFieldOffset(ConcurrentHashMap.class, "baseCount");
        CELLSBUSY = unsafe.objectFieldOffset(ConcurrentHashMap.class, "cellsBusy");
        CELLVALUE = unsafe.objectFieldOffset(CounterCell.class, "value");
        ABASE = unsafe.arrayBaseOffset(Node[].class);
        int iArrayIndexScale = unsafe.arrayIndexScale(Node[].class);
        if (((iArrayIndexScale - 1) & iArrayIndexScale) != 0) {
            throw new ExceptionInInitializerError("array index scale not a power of two");
        }
        ASHIFT = 31 - Integer.numberOfLeadingZeros(iArrayIndexScale);
    }

    static class Node implements Map.Entry {
        final int hash;
        final Object key;
        volatile Node next;
        volatile Object val;

        Node(int i, Object obj, Object obj2) {
            this.hash = i;
            this.key = obj;
            this.val = obj2;
        }

        Node(int i, Object obj, Object obj2, Node node) {
            this(i, obj, obj2);
            this.next = node;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            return this.val;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        public final String toString() {
            return Helpers.mapEntryToString(this.key, this.val);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (value = entry.getValue()) == null) {
                return false;
            }
            Object obj2 = this.key;
            if (key != obj2 && !key.equals(obj2)) {
                return false;
            }
            Object obj3 = this.val;
            return value == obj3 || value.equals(obj3);
        }

        Node find(int i, Object obj) {
            Object obj2;
            if (obj == null) {
                return null;
            }
            Node node = this;
            do {
                if (node.hash == i && ((obj2 = node.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                    return node;
                }
                node = node.next;
            } while (node != null);
            return null;
        }
    }

    private static final int tableSizeFor(int i) {
        int iNumberOfLeadingZeros = (-1) >>> Integer.numberOfLeadingZeros(i - 1);
        if (iNumberOfLeadingZeros < 0) {
            return 1;
        }
        return iNumberOfLeadingZeros >= 1073741824 ? BasicMeasure.EXACTLY : iNumberOfLeadingZeros + 1;
    }

    static Class comparableClassFor(Object obj) {
        Type[] actualTypeArguments;
        if (!(obj instanceof Comparable)) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if (cls != String.class) {
            Type[] genericInterfaces = cls.getGenericInterfaces();
            if (genericInterfaces == null) {
                return null;
            }
            for (Type type : genericInterfaces) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (parameterizedType.getRawType() != Comparable.class || (actualTypeArguments = parameterizedType.getActualTypeArguments()) == null || actualTypeArguments.length != 1 || actualTypeArguments[0] != cls) {
                    }
                }
            }
            return null;
        }
        return cls;
    }

    static int compareComparables(Class cls, Object obj, Object obj2) {
        if (obj2 == null || obj2.getClass() != cls) {
            return 0;
        }
        return ((Comparable) obj).compareTo(obj2);
    }

    static final Node tabAt(Node[] nodeArr, int i) {
        return (Node) U.getObjectAcquire(nodeArr, (((long) i) << ASHIFT) + ((long) ABASE));
    }

    static final boolean casTabAt(Node[] nodeArr, int i, Node node, Node node2) {
        return U.compareAndSetObject(nodeArr, (((long) i) << ASHIFT) + ((long) ABASE), node, node2);
    }

    static final void setTabAt(Node[] nodeArr, int i, Node node) {
        U.putObjectRelease(nodeArr, (((long) i) << ASHIFT) + ((long) ABASE), node);
    }

    public ConcurrentHashMap() {
    }

    public ConcurrentHashMap(int i) {
        this(i, 0.75f, 1);
    }

    public ConcurrentHashMap(int i, float f, int i2) {
        if (f <= 0.0f || i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        long j = (long) (((double) ((i < i2 ? i2 : i) / f)) + 1.0d);
        this.sizeCtl = j >= 1073741824 ? BasicMeasure.EXACTLY : tableSizeFor((int) j);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        long jSumCount = sumCount();
        if (jSumCount < 0) {
            return 0;
        }
        if (jSumCount > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) jSumCount;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return sumCount() <= 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        int length;
        Node nodeTabAt;
        Object obj2;
        int iSpread = spread(obj.hashCode());
        Node[] nodeArr = this.table;
        if (nodeArr != null && (length = nodeArr.length) > 0 && (nodeTabAt = tabAt(nodeArr, (length - 1) & iSpread)) != null) {
            int i = nodeTabAt.hash;
            if (i == iSpread) {
                Object obj3 = nodeTabAt.key;
                if (obj3 == obj || (obj3 != null && obj.equals(obj3))) {
                    return (V) nodeTabAt.val;
                }
            } else if (i < 0) {
                Node nodeFind = nodeTabAt.find(iSpread, obj);
                if (nodeFind != null) {
                    return (V) nodeFind.val;
                }
                return null;
            }
            while (true) {
                nodeTabAt = nodeTabAt.next;
                if (nodeTabAt == null) {
                    break;
                }
                if (nodeTabAt.hash == iSpread && ((obj2 = nodeTabAt.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                    break;
                }
            }
            return (V) nodeTabAt.val;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object obj) {
        obj.getClass();
        Node[] nodeArr = this.table;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                Object obj2 = nodeAdvance.val;
                if (obj2 == obj) {
                    return true;
                }
                if (obj2 != null && obj.equals(obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        return (V) putVal(k, v, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x006a, code lost:
    
        r7 = r6.val;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x006c, code lost:
    
        if (r11 != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x006e, code lost:
    
        r6.val = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00a5, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.lang.Object putVal(java.lang.Object r9, java.lang.Object r10, boolean r11) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto Lc2
            if (r10 == 0) goto Lc2
            int r1 = r9.hashCode()
            int r1 = spread(r1)
            j$.util.concurrent.ConcurrentHashMap$Node[] r2 = r8.table
            r3 = 0
        L10:
            if (r2 == 0) goto Lbc
            int r4 = r2.length
            if (r4 != 0) goto L17
            goto Lbc
        L17:
            int r4 = r4 + (-1)
            r4 = r4 & r1
            j$.util.concurrent.ConcurrentHashMap$Node r5 = tabAt(r2, r4)
            if (r5 != 0) goto L2d
            j$.util.concurrent.ConcurrentHashMap$Node r5 = new j$.util.concurrent.ConcurrentHashMap$Node
            r5.<init>(r1, r9, r10)
            boolean r4 = casTabAt(r2, r4, r0, r5)
            if (r4 == 0) goto L10
            goto Lb4
        L2d:
            int r6 = r5.hash
            r7 = -1
            if (r6 != r7) goto L37
            j$.util.concurrent.ConcurrentHashMap$Node[] r2 = r8.helpTransfer(r2, r5)
            goto L10
        L37:
            if (r11 == 0) goto L4c
            if (r6 != r1) goto L4c
            java.lang.Object r7 = r5.key
            if (r7 == r9) goto L47
            if (r7 == 0) goto L4c
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto L4c
        L47:
            java.lang.Object r7 = r5.val
            if (r7 == 0) goto L4c
            return r7
        L4c:
            monitor-enter(r5)
            j$.util.concurrent.ConcurrentHashMap$Node r7 = tabAt(r2, r4)     // Catch: java.lang.Throwable -> L68
            if (r7 != r5) goto La6
            if (r6 < 0) goto L81
            r3 = 1
            r6 = r5
        L57:
            int r7 = r6.hash     // Catch: java.lang.Throwable -> L68
            if (r7 != r1) goto L71
            java.lang.Object r7 = r6.key     // Catch: java.lang.Throwable -> L68
            if (r7 == r9) goto L6a
            if (r7 == 0) goto L71
            boolean r7 = r9.equals(r7)     // Catch: java.lang.Throwable -> L68
            if (r7 == 0) goto L71
            goto L6a
        L68:
            r9 = move-exception
            goto Lba
        L6a:
            java.lang.Object r7 = r6.val     // Catch: java.lang.Throwable -> L68
            if (r11 != 0) goto La7
            r6.val = r10     // Catch: java.lang.Throwable -> L68
            goto La7
        L71:
            j$.util.concurrent.ConcurrentHashMap$Node r7 = r6.next     // Catch: java.lang.Throwable -> L68
            if (r7 != 0) goto L7d
            j$.util.concurrent.ConcurrentHashMap$Node r7 = new j$.util.concurrent.ConcurrentHashMap$Node     // Catch: java.lang.Throwable -> L68
            r7.<init>(r1, r9, r10)     // Catch: java.lang.Throwable -> L68
            r6.next = r7     // Catch: java.lang.Throwable -> L68
            goto La6
        L7d:
            int r3 = r3 + 1
            r6 = r7
            goto L57
        L81:
            boolean r6 = r5 instanceof j$.util.concurrent.ConcurrentHashMap.TreeBin     // Catch: java.lang.Throwable -> L68
            if (r6 == 0) goto L99
            r3 = r5
            j$.util.concurrent.ConcurrentHashMap$TreeBin r3 = (j$.util.concurrent.ConcurrentHashMap.TreeBin) r3     // Catch: java.lang.Throwable -> L68
            j$.util.concurrent.ConcurrentHashMap$TreeNode r3 = r3.putTreeVal(r1, r9, r10)     // Catch: java.lang.Throwable -> L68
            if (r3 == 0) goto L96
            java.lang.Object r6 = r3.val     // Catch: java.lang.Throwable -> L68
            if (r11 != 0) goto L94
            r3.val = r10     // Catch: java.lang.Throwable -> L68
        L94:
            r7 = r6
            goto L97
        L96:
            r7 = r0
        L97:
            r3 = 2
            goto La7
        L99:
            boolean r6 = r5 instanceof j$.util.concurrent.ConcurrentHashMap.ReservationNode     // Catch: java.lang.Throwable -> L68
            if (r6 != 0) goto L9e
            goto La6
        L9e:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L68
            java.lang.String r10 = "Recursive update"
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L68
            throw r9     // Catch: java.lang.Throwable -> L68
        La6:
            r7 = r0
        La7:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L68
            if (r3 == 0) goto L10
            r9 = 8
            if (r3 < r9) goto Lb1
            r8.treeifyBin(r2, r4)
        Lb1:
            if (r7 == 0) goto Lb4
            return r7
        Lb4:
            r9 = 1
            r8.addCount(r9, r3)
            return r0
        Lba:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L68
            throw r9
        Lbc:
            j$.util.concurrent.ConcurrentHashMap$Node[] r2 = r8.initTable()
            goto L10
        Lc2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.putVal(java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map map) {
        tryPresize(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            putVal(entry.getKey(), entry.getValue(), false);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        return replaceNode(obj, null, null);
    }

    final Object replaceNode(Object obj, Object obj2, Object obj3) {
        int length;
        int i;
        Node nodeTabAt;
        boolean z;
        Object obj4;
        TreeNode treeNodeFindTreeNode;
        Object obj5;
        int iSpread = spread(obj.hashCode());
        Node[] nodeArrHelpTransfer = this.table;
        while (true) {
            if (nodeArrHelpTransfer == null || (length = nodeArrHelpTransfer.length) == 0 || (nodeTabAt = tabAt(nodeArrHelpTransfer, (i = (length - 1) & iSpread))) == null) {
                break;
            }
            int i2 = nodeTabAt.hash;
            if (i2 == -1) {
                nodeArrHelpTransfer = helpTransfer(nodeArrHelpTransfer, nodeTabAt);
            } else {
                synchronized (nodeTabAt) {
                    try {
                        if (tabAt(nodeArrHelpTransfer, i) == nodeTabAt) {
                            z = true;
                            if (i2 >= 0) {
                                Node node = null;
                                Node node2 = nodeTabAt;
                                while (true) {
                                    if (node2.hash != iSpread || ((obj5 = node2.key) != obj && (obj5 == null || !obj.equals(obj5)))) {
                                        Node node3 = node2.next;
                                        if (node3 == null) {
                                            break;
                                        }
                                        node = node2;
                                        node2 = node3;
                                    }
                                }
                                obj4 = node2.val;
                                if (obj3 != null && obj3 != obj4 && (obj4 == null || !obj3.equals(obj4))) {
                                    obj4 = null;
                                } else if (obj2 != null) {
                                    node2.val = obj2;
                                } else if (node != null) {
                                    node.next = node2.next;
                                } else {
                                    setTabAt(nodeArrHelpTransfer, i, node2.next);
                                }
                            } else if (nodeTabAt instanceof TreeBin) {
                                TreeBin treeBin = (TreeBin) nodeTabAt;
                                TreeNode treeNode = treeBin.root;
                                if (treeNode != null && (treeNodeFindTreeNode = treeNode.findTreeNode(iSpread, obj, null)) != null) {
                                    obj4 = treeNodeFindTreeNode.val;
                                    if (obj3 == null || obj3 == obj4 || (obj4 != null && obj3.equals(obj4))) {
                                        if (obj2 != null) {
                                            treeNodeFindTreeNode.val = obj2;
                                        } else if (treeBin.removeTreeNode(treeNodeFindTreeNode)) {
                                            setTabAt(nodeArrHelpTransfer, i, untreeify(treeBin.first));
                                        }
                                    }
                                }
                                obj4 = null;
                            } else if (nodeTabAt instanceof ReservationNode) {
                                throw new IllegalStateException("Recursive update");
                            }
                        }
                        z = false;
                        obj4 = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    if (obj4 != null) {
                        if (obj2 == null) {
                            addCount(-1L, -1);
                        }
                        return obj4;
                    }
                }
            }
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Node nodeTabAt;
        Node node;
        Node[] nodeArrHelpTransfer = this.table;
        long j = 0;
        loop0: while (true) {
            int i = 0;
            while (nodeArrHelpTransfer != null && i < nodeArrHelpTransfer.length) {
                nodeTabAt = tabAt(nodeArrHelpTransfer, i);
                if (nodeTabAt == null) {
                    i++;
                } else {
                    int i2 = nodeTabAt.hash;
                    if (i2 == -1) {
                        break;
                    }
                    synchronized (nodeTabAt) {
                        try {
                            if (tabAt(nodeArrHelpTransfer, i) == nodeTabAt) {
                                if (i2 >= 0) {
                                    node = nodeTabAt;
                                } else {
                                    node = nodeTabAt instanceof TreeBin ? ((TreeBin) nodeTabAt).first : null;
                                }
                                while (node != null) {
                                    j--;
                                    node = node.next;
                                }
                                setTabAt(nodeArrHelpTransfer, i, null);
                                i++;
                            }
                        } finally {
                        }
                    }
                }
            }
            nodeArrHelpTransfer = helpTransfer(nodeArrHelpTransfer, nodeTabAt);
        }
        if (j != 0) {
            addCount(j, -1);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        KeySetView keySetView = this.keySet;
        if (keySetView != null) {
            return keySetView;
        }
        KeySetView keySetView2 = new KeySetView(this, null);
        this.keySet = keySetView2;
        return keySetView2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection values() {
        ValuesView valuesView = this.values;
        if (valuesView != null) {
            return valuesView;
        }
        ValuesView valuesView2 = new ValuesView(this);
        this.values = valuesView2;
        return valuesView2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        EntrySetView entrySetView = this.entrySet;
        if (entrySetView != null) {
            return entrySetView;
        }
        EntrySetView entrySetView2 = new EntrySetView(this);
        this.entrySet = entrySetView2;
        return entrySetView2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        Node[] nodeArr = this.table;
        int iHashCode = 0;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                iHashCode += nodeAdvance.val.hashCode() ^ nodeAdvance.key.hashCode();
            }
        }
        return iHashCode;
    }

    @Override // java.util.AbstractMap
    public String toString() {
        Node[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        Traverser traverser = new Traverser(nodeArr, length, 0, length);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Node nodeAdvance = traverser.advance();
        if (nodeAdvance != null) {
            while (true) {
                Object obj = nodeAdvance.key;
                Object obj2 = nodeAdvance.val;
                if (obj == this) {
                    obj = "(this Map)";
                }
                sb.append(obj);
                sb.append('=');
                if (obj2 == this) {
                    obj2 = "(this Map)";
                }
                sb.append(obj2);
                nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                sb.append(',');
                sb.append(' ');
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        V value;
        V v;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        Node[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        Traverser traverser = new Traverser(nodeArr, length, 0, length);
        while (true) {
            Node nodeAdvance = traverser.advance();
            if (nodeAdvance != null) {
                Object obj2 = nodeAdvance.val;
                Object obj3 = map.get(nodeAdvance.key);
                if (obj3 == null || (obj3 != obj2 && !obj3.equals(obj2))) {
                    break;
                }
            } else {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    K key = entry.getKey();
                    if (key == null || (value = entry.getValue()) == null || (v = get(key)) == null || (value != v && !value.equals(v))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    static class Segment extends ReentrantLock implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;

        Segment(float f) {
            this.loadFactor = f;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int i = 0;
        int i2 = 1;
        while (i2 < 16) {
            i++;
            i2 <<= 1;
        }
        int i3 = 32 - i;
        int i4 = i2 - 1;
        Segment[] segmentArr = new Segment[16];
        for (int i5 = 0; i5 < 16; i5++) {
            segmentArr[i5] = new Segment(0.75f);
        }
        ObjectOutputStream.PutField putFieldPutFields = objectOutputStream.putFields();
        putFieldPutFields.put("segments", segmentArr);
        putFieldPutFields.put("segmentShift", i3);
        putFieldPutFields.put("segmentMask", i4);
        objectOutputStream.writeFields();
        Node[] nodeArr = this.table;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                objectOutputStream.writeObject(nodeAdvance.key);
                objectOutputStream.writeObject(nodeAdvance.val);
            }
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.writeObject(null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        long j;
        long j2;
        Object obj;
        this.sizeCtl = -1;
        objectInputStream.defaultReadObject();
        long j3 = 0;
        long j4 = 0;
        Node node = null;
        while (true) {
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            j = 1;
            if (object == null || object2 == null) {
                break;
            }
            j4++;
            node = new Node(spread(object.hashCode()), object, object2, node);
        }
        if (j4 == 0) {
            this.sizeCtl = 0;
            return;
        }
        long j5 = (long) (((double) (j4 / 0.75f)) + 1.0d);
        int iTableSizeFor = j5 >= 1073741824 ? BasicMeasure.EXACTLY : tableSizeFor((int) j5);
        Node[] nodeArr = new Node[iTableSizeFor];
        int i = iTableSizeFor - 1;
        while (node != null) {
            Node node2 = node.next;
            int i2 = node.hash;
            int i3 = i2 & i;
            Node nodeTabAt = tabAt(nodeArr, i3);
            boolean z = true;
            if (nodeTabAt == null) {
                j2 = j;
            } else {
                Object obj2 = node.key;
                if (nodeTabAt.hash < 0) {
                    if (((TreeBin) nodeTabAt).putTreeVal(i2, obj2, node.val) == null) {
                        j3 += j;
                    }
                    j2 = j;
                } else {
                    j2 = j;
                    int i4 = 0;
                    for (Node node3 = nodeTabAt; node3 != null; node3 = node3.next) {
                        if (node3.hash == i2 && ((obj = node3.key) == obj2 || (obj != null && obj2.equals(obj)))) {
                            z = false;
                            break;
                        }
                        i4++;
                    }
                    if (z && i4 >= 8) {
                        j3 += j2;
                        node.next = nodeTabAt;
                        Node node4 = node;
                        TreeNode treeNode = null;
                        TreeNode treeNode2 = null;
                        while (node4 != null) {
                            TreeNode treeNode3 = new TreeNode(node4.hash, node4.key, node4.val, null, null);
                            treeNode3.prev = treeNode2;
                            if (treeNode2 == null) {
                                treeNode = treeNode3;
                            } else {
                                treeNode2.next = treeNode3;
                            }
                            node4 = node4.next;
                            treeNode2 = treeNode3;
                        }
                        setTabAt(nodeArr, i3, new TreeBin(treeNode));
                    }
                }
                z = false;
            }
            if (z) {
                j3 += j2;
                node.next = nodeTabAt;
                setTabAt(nodeArr, i3, node);
            }
            node = node2;
            j = j2;
        }
        this.table = nodeArr;
        this.sizeCtl = iTableSizeFor - (iTableSizeFor >>> 2);
        this.baseCount = j3;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    public Object putIfAbsent(Object obj, Object obj2) {
        return putVal(obj, obj2, true);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    public boolean remove(Object obj, Object obj2) {
        obj.getClass();
        return (obj2 == null || replaceNode(obj, null, obj2) == null) ? false : true;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    public boolean replace(Object obj, Object obj2, Object obj3) {
        if (obj == null || obj2 == null || obj3 == null) {
            throw null;
        }
        return replaceNode(obj, obj3, obj2) != null;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    public Object replace(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            throw null;
        }
        return replaceNode(obj, obj2, null);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public Object getOrDefault(Object obj, Object obj2) {
        V v = get(obj);
        return v == null ? obj2 : v;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public void forEach(BiConsumer biConsumer) {
        biConsumer.getClass();
        Node[] nodeArr = this.table;
        if (nodeArr == null) {
            return;
        }
        Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
        while (true) {
            Node nodeAdvance = traverser.advance();
            if (nodeAdvance == null) {
                return;
            } else {
                biConsumer.accept(nodeAdvance.key, nodeAdvance.val);
            }
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public void replaceAll(BiFunction biFunction) {
        biFunction.getClass();
        Node[] nodeArr = this.table;
        if (nodeArr == null) {
            return;
        }
        Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
        while (true) {
            Node nodeAdvance = traverser.advance();
            if (nodeAdvance == null) {
                return;
            }
            Object obj = nodeAdvance.val;
            Object obj2 = nodeAdvance.key;
            do {
                Object objApply = biFunction.apply(obj2, obj);
                objApply.getClass();
                if (replaceNode(obj2, objApply, obj) == null) {
                    obj = get(obj2);
                }
            } while (obj != null);
        }
    }

    boolean removeEntryIf(Predicate predicate) {
        predicate.getClass();
        Node[] nodeArr = this.table;
        boolean z = false;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                Object obj = nodeAdvance.key;
                Object obj2 = nodeAdvance.val;
                if (predicate.test(new AbstractMap.SimpleImmutableEntry(obj, obj2)) && replaceNode(obj, null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    boolean removeValueIf(Predicate predicate) {
        predicate.getClass();
        Node[] nodeArr = this.table;
        boolean z = false;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                Object obj = nodeAdvance.key;
                Object obj2 = nodeAdvance.val;
                if (predicate.test(obj2) && replaceNode(obj, null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x008c, code lost:
    
        r5 = r5.val;
     */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object computeIfAbsent(java.lang.Object r12, java.util.function.Function r13) {
        /*
            Method dump skipped, instruction units count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.computeIfAbsent(java.lang.Object, java.util.function.Function):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00aa, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object computeIfPresent(java.lang.Object r14, java.util.function.BiFunction r15) {
        /*
            r13 = this;
            r0 = 0
            if (r14 == 0) goto Lbd
            if (r15 == 0) goto Lbd
            int r1 = r14.hashCode()
            int r1 = spread(r1)
            j$.util.concurrent.ConcurrentHashMap$Node[] r2 = r13.table
            r3 = 0
            r5 = r0
            r4 = r3
        L12:
            if (r2 == 0) goto Lb7
            int r6 = r2.length
            if (r6 != 0) goto L19
            goto Lb7
        L19:
            int r6 = r6 + (-1)
            r6 = r6 & r1
            j$.util.concurrent.ConcurrentHashMap$Node r7 = tabAt(r2, r6)
            if (r7 != 0) goto L24
            goto Lae
        L24:
            int r8 = r7.hash
            r9 = -1
            if (r8 != r9) goto L2e
            j$.util.concurrent.ConcurrentHashMap$Node[] r2 = r13.helpTransfer(r2, r7)
            goto L12
        L2e:
            monitor-enter(r7)
            j$.util.concurrent.ConcurrentHashMap$Node r10 = tabAt(r2, r6)     // Catch: java.lang.Throwable -> L4b
            if (r10 != r7) goto Lab
            if (r8 < 0) goto L70
            r4 = 1
            r10 = r0
            r8 = r7
        L3a:
            int r11 = r8.hash     // Catch: java.lang.Throwable -> L4b
            if (r11 != r1) goto L65
            java.lang.Object r11 = r8.key     // Catch: java.lang.Throwable -> L4b
            if (r11 == r14) goto L4e
            if (r11 == 0) goto L65
            boolean r11 = r14.equals(r11)     // Catch: java.lang.Throwable -> L4b
            if (r11 == 0) goto L65
            goto L4e
        L4b:
            r14 = move-exception
            goto Lb5
        L4e:
            java.lang.Object r5 = r8.val     // Catch: java.lang.Throwable -> L4b
            java.lang.Object r5 = r15.apply(r14, r5)     // Catch: java.lang.Throwable -> L4b
            if (r5 == 0) goto L59
            r8.val = r5     // Catch: java.lang.Throwable -> L4b
            goto Lab
        L59:
            j$.util.concurrent.ConcurrentHashMap$Node r3 = r8.next     // Catch: java.lang.Throwable -> L4b
            if (r10 == 0) goto L60
            r10.next = r3     // Catch: java.lang.Throwable -> L4b
            goto L63
        L60:
            setTabAt(r2, r6, r3)     // Catch: java.lang.Throwable -> L4b
        L63:
            r3 = r9
            goto Lab
        L65:
            j$.util.concurrent.ConcurrentHashMap$Node r10 = r8.next     // Catch: java.lang.Throwable -> L4b
            if (r10 != 0) goto L6a
            goto Lab
        L6a:
            int r4 = r4 + 1
            r12 = r10
            r10 = r8
            r8 = r12
            goto L3a
        L70:
            boolean r8 = r7 instanceof j$.util.concurrent.ConcurrentHashMap.TreeBin     // Catch: java.lang.Throwable -> L4b
            if (r8 == 0) goto L9e
            r4 = r7
            j$.util.concurrent.ConcurrentHashMap$TreeBin r4 = (j$.util.concurrent.ConcurrentHashMap.TreeBin) r4     // Catch: java.lang.Throwable -> L4b
            j$.util.concurrent.ConcurrentHashMap$TreeNode r8 = r4.root     // Catch: java.lang.Throwable -> L4b
            if (r8 == 0) goto L9c
            j$.util.concurrent.ConcurrentHashMap$TreeNode r8 = r8.findTreeNode(r1, r14, r0)     // Catch: java.lang.Throwable -> L4b
            if (r8 == 0) goto L9c
            java.lang.Object r5 = r8.val     // Catch: java.lang.Throwable -> L4b
            java.lang.Object r5 = r15.apply(r14, r5)     // Catch: java.lang.Throwable -> L4b
            if (r5 == 0) goto L8c
            r8.val = r5     // Catch: java.lang.Throwable -> L4b
            goto L9c
        L8c:
            boolean r3 = r4.removeTreeNode(r8)     // Catch: java.lang.Throwable -> L4b
            if (r3 == 0) goto L9b
            j$.util.concurrent.ConcurrentHashMap$TreeNode r3 = r4.first     // Catch: java.lang.Throwable -> L4b
            j$.util.concurrent.ConcurrentHashMap$Node r3 = untreeify(r3)     // Catch: java.lang.Throwable -> L4b
            setTabAt(r2, r6, r3)     // Catch: java.lang.Throwable -> L4b
        L9b:
            r3 = r9
        L9c:
            r4 = 2
            goto Lab
        L9e:
            boolean r6 = r7 instanceof j$.util.concurrent.ConcurrentHashMap.ReservationNode     // Catch: java.lang.Throwable -> L4b
            if (r6 != 0) goto La3
            goto Lab
        La3:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L4b
            java.lang.String r15 = "Recursive update"
            r14.<init>(r15)     // Catch: java.lang.Throwable -> L4b
            throw r14     // Catch: java.lang.Throwable -> L4b
        Lab:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4b
            if (r4 == 0) goto L12
        Lae:
            if (r3 == 0) goto Lb4
            long r14 = (long) r3
            r13.addCount(r14, r4)
        Lb4:
            return r5
        Lb5:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4b
            throw r14
        Lb7:
            j$.util.concurrent.ConcurrentHashMap$Node[] r2 = r13.initTable()
            goto L12
        Lbd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.computeIfPresent(java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public Object compute(Object obj, BiFunction biFunction) {
        Node node;
        Object objApply;
        Object obj2;
        if (obj == null || biFunction == null) {
            throw null;
        }
        int iSpread = spread(obj.hashCode());
        Node[] nodeArrInitTable = this.table;
        int i = 0;
        Object objApply2 = null;
        int i2 = 0;
        while (true) {
            if (nodeArrInitTable != null) {
                int length = nodeArrInitTable.length;
                if (length != 0) {
                    int i3 = (length - 1) & iSpread;
                    Node nodeTabAt = tabAt(nodeArrInitTable, i3);
                    if (nodeTabAt == null) {
                        ReservationNode reservationNode = new ReservationNode();
                        synchronized (reservationNode) {
                            try {
                                if (casTabAt(nodeArrInitTable, i3, null, reservationNode)) {
                                    try {
                                        objApply2 = biFunction.apply(obj, null);
                                        if (objApply2 != null) {
                                            node = new Node(iSpread, obj, objApply2);
                                            i2 = 1;
                                        } else {
                                            node = null;
                                        }
                                        setTabAt(nodeArrInitTable, i3, node);
                                        i = 1;
                                    } catch (Throwable th) {
                                        setTabAt(nodeArrInitTable, i3, null);
                                        throw th;
                                    }
                                }
                            } finally {
                            }
                        }
                        if (i != 0) {
                        }
                    } else {
                        int i4 = nodeTabAt.hash;
                        if (i4 == -1) {
                            nodeArrInitTable = helpTransfer(nodeArrInitTable, nodeTabAt);
                        } else {
                            synchronized (nodeTabAt) {
                                try {
                                    if (tabAt(nodeArrInitTable, i3) == nodeTabAt) {
                                        if (i4 >= 0) {
                                            Node node2 = null;
                                            Node node3 = nodeTabAt;
                                            i = 1;
                                            while (true) {
                                                if (node3.hash == iSpread && ((obj2 = node3.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                                                    break;
                                                }
                                                Node node4 = node3.next;
                                                if (node4 == null) {
                                                    objApply = biFunction.apply(obj, null);
                                                    if (objApply != null) {
                                                        if (node3.next != null) {
                                                            throw new IllegalStateException("Recursive update");
                                                        }
                                                        node3.next = new Node(iSpread, obj, objApply);
                                                        i2 = 1;
                                                    }
                                                } else {
                                                    i++;
                                                    node2 = node3;
                                                    node3 = node4;
                                                }
                                            }
                                            Object objApply3 = biFunction.apply(obj, node3.val);
                                            if (objApply3 != null) {
                                                node3.val = objApply3;
                                                objApply2 = objApply3;
                                            } else {
                                                Node node5 = node3.next;
                                                if (node2 != null) {
                                                    node2.next = node5;
                                                } else {
                                                    setTabAt(nodeArrInitTable, i3, node5);
                                                }
                                                objApply2 = objApply3;
                                                i2 = -1;
                                            }
                                        } else if (nodeTabAt instanceof TreeBin) {
                                            TreeBin treeBin = (TreeBin) nodeTabAt;
                                            TreeNode treeNode = treeBin.root;
                                            TreeNode treeNodeFindTreeNode = treeNode != null ? treeNode.findTreeNode(iSpread, obj, null) : null;
                                            objApply = biFunction.apply(obj, treeNodeFindTreeNode == null ? null : treeNodeFindTreeNode.val);
                                            if (objApply != null) {
                                                if (treeNodeFindTreeNode != null) {
                                                    treeNodeFindTreeNode.val = objApply;
                                                } else {
                                                    treeBin.putTreeVal(iSpread, obj, objApply);
                                                    i2 = 1;
                                                }
                                            } else if (treeNodeFindTreeNode != null) {
                                                if (treeBin.removeTreeNode(treeNodeFindTreeNode)) {
                                                    setTabAt(nodeArrInitTable, i3, untreeify(treeBin.first));
                                                }
                                                i2 = -1;
                                            }
                                            i = 1;
                                            objApply2 = objApply;
                                        } else if (nodeTabAt instanceof ReservationNode) {
                                            throw new IllegalStateException("Recursive update");
                                        }
                                    }
                                } finally {
                                }
                            }
                            if (i != 0) {
                                if (i >= 8) {
                                    treeifyBin(nodeArrInitTable, i3);
                                }
                            }
                        }
                    }
                }
            }
            nodeArrInitTable = initTable();
        }
        if (i2 != 0) {
            addCount(i2, i);
        }
        return objApply2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x00dd, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object merge(java.lang.Object r18, java.lang.Object r19, java.util.function.BiFunction r20) {
        /*
            Method dump skipped, instruction units count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.merge(java.lang.Object, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    public long mappingCount() {
        long jSumCount = sumCount();
        if (jSumCount < 0) {
            return 0L;
        }
        return jSumCount;
    }

    static final class ForwardingNode extends Node {
        final Node[] nextTable;

        ForwardingNode(Node[] nodeArr) {
            super(-1, null, null);
            this.nextTable = nodeArr;
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.Node
        Node find(int i, Object obj) {
            int length;
            Node nodeTabAt;
            Object obj2;
            Node[] nodeArr = this.nextTable;
            loop0: while (obj != null && nodeArr != null && (length = nodeArr.length) != 0 && (nodeTabAt = ConcurrentHashMap.tabAt(nodeArr, (length - 1) & i)) != null) {
                do {
                    int i2 = nodeTabAt.hash;
                    if (i2 == i && ((obj2 = nodeTabAt.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                        return nodeTabAt;
                    }
                    if (i2 < 0) {
                        if (nodeTabAt instanceof ForwardingNode) {
                            nodeArr = ((ForwardingNode) nodeTabAt).nextTable;
                        } else {
                            return nodeTabAt.find(i, obj);
                        }
                    } else {
                        nodeTabAt = nodeTabAt.next;
                    }
                } while (nodeTabAt != null);
            }
            return null;
        }
    }

    static final class ReservationNode extends Node {
        @Override // j$.util.concurrent.ConcurrentHashMap.Node
        Node find(int i, Object obj) {
            return null;
        }

        ReservationNode() {
            super(-3, null, null);
        }
    }

    static final int resizeStamp(int i) {
        return Integer.numberOfLeadingZeros(i) | 32768;
    }

    private final Node[] initTable() {
        while (true) {
            Node[] nodeArr = this.table;
            if (nodeArr != null && nodeArr.length != 0) {
                return nodeArr;
            }
            int i = this.sizeCtl;
            if (i < 0) {
                Thread.yield();
            } else if (U.compareAndSetInt(this, SIZECTL, i, -1)) {
                try {
                    Node[] nodeArr2 = this.table;
                    if (nodeArr2 == null || nodeArr2.length == 0) {
                        int i2 = i > 0 ? i : 16;
                        Node[] nodeArr3 = new Node[i2];
                        this.table = nodeArr3;
                        i = i2 - (i2 >>> 2);
                        nodeArr2 = nodeArr3;
                    }
                    this.sizeCtl = i;
                    return nodeArr2;
                } catch (Throwable th) {
                    this.sizeCtl = i;
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void addCount(long r21, int r23) {
        /*
            r20 = this;
            r1 = r20
            r8 = r21
            r10 = r23
            j$.util.concurrent.ConcurrentHashMap$CounterCell[] r11 = r1.counterCells
            if (r11 != 0) goto L18
            j$.sun.misc.DesugarUnsafe r0 = j$.util.concurrent.ConcurrentHashMap.U
            long r2 = j$.util.concurrent.ConcurrentHashMap.BASECOUNT
            long r4 = r1.baseCount
            long r6 = r4 + r8
            boolean r0 = r0.compareAndSetLong(r1, r2, r4, r6)
            if (r0 != 0) goto L41
        L18:
            r0 = 1
            if (r11 == 0) goto L9a
            int r2 = r11.length
            int r2 = r2 - r0
            if (r2 < 0) goto L9a
            int r3 = j$.util.concurrent.ThreadLocalRandom.getProbe()
            r2 = r2 & r3
            r13 = r11[r2]
            if (r13 == 0) goto L9a
            j$.sun.misc.DesugarUnsafe r12 = j$.util.concurrent.ConcurrentHashMap.U
            long r14 = j$.util.concurrent.ConcurrentHashMap.CELLVALUE
            long r2 = r13.value
            long r18 = r2 + r8
            r16 = r2
            boolean r2 = r12.compareAndSetLong(r13, r14, r16, r18)
            if (r2 != 0) goto L3a
            r0 = r2
            goto L9a
        L3a:
            if (r10 > r0) goto L3d
            goto L99
        L3d:
            long r6 = r1.sumCount()
        L41:
            if (r10 < 0) goto L99
        L43:
            int r4 = r1.sizeCtl
            long r2 = (long) r4
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 < 0) goto L99
            j$.util.concurrent.ConcurrentHashMap$Node[] r6 = r1.table
            if (r6 == 0) goto L99
            int r0 = r6.length
            r2 = 1073741824(0x40000000, float:2.0)
            if (r0 >= r2) goto L99
            int r0 = resizeStamp(r0)
            if (r4 >= 0) goto L80
            int r2 = r4 >>> 16
            if (r2 != r0) goto L99
            int r2 = r0 + 1
            if (r4 == r2) goto L99
            r2 = 65535(0xffff, float:9.1834E-41)
            int r0 = r0 + r2
            if (r4 == r0) goto L99
            j$.util.concurrent.ConcurrentHashMap$Node[] r7 = r1.nextTable
            if (r7 == 0) goto L99
            int r0 = r1.transferIndex
            if (r0 > 0) goto L70
            goto L99
        L70:
            j$.sun.misc.DesugarUnsafe r0 = j$.util.concurrent.ConcurrentHashMap.U
            long r2 = j$.util.concurrent.ConcurrentHashMap.SIZECTL
            int r5 = r4 + 1
            boolean r0 = r0.compareAndSetInt(r1, r2, r4, r5)
            if (r0 == 0) goto L94
            r1.transfer(r6, r7)
            goto L94
        L80:
            r2 = r0
            j$.sun.misc.DesugarUnsafe r0 = j$.util.concurrent.ConcurrentHashMap.U
            r5 = r2
            long r2 = j$.util.concurrent.ConcurrentHashMap.SIZECTL
            int r5 = r5 << 16
            int r5 = r5 + 2
            boolean r0 = r0.compareAndSetInt(r1, r2, r4, r5)
            if (r0 == 0) goto L94
            r0 = 0
            r1.transfer(r6, r0)
        L94:
            long r6 = r1.sumCount()
            goto L43
        L99:
            return
        L9a:
            r1.fullAddCount(r8, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.addCount(long, int):void");
    }

    final Node[] helpTransfer(Node[] nodeArr, Node node) {
        Node[] nodeArr2;
        int i;
        if (nodeArr != null && (node instanceof ForwardingNode) && (nodeArr2 = ((ForwardingNode) node).nextTable) != null) {
            int iResizeStamp = resizeStamp(nodeArr.length);
            while (nodeArr2 == this.nextTable && this.table == nodeArr && (i = this.sizeCtl) < 0 && (i >>> 16) == iResizeStamp && i != iResizeStamp + 1 && i != 65535 + iResizeStamp && this.transferIndex > 0) {
                if (U.compareAndSetInt(this, SIZECTL, i, i + 1)) {
                    transfer(nodeArr, nodeArr2);
                    break;
                }
            }
            return nodeArr2;
        }
        return this.table;
    }

    private final void tryPresize(int i) {
        int length;
        int iTableSizeFor = i >= 536870912 ? 1073741824 : tableSizeFor(i + (i >>> 1) + 1);
        while (true) {
            int i2 = this.sizeCtl;
            if (i2 < 0) {
                break;
            }
            Node[] nodeArr = this.table;
            if (nodeArr == null || (length = nodeArr.length) == 0) {
                int i3 = i2 > iTableSizeFor ? i2 : iTableSizeFor;
                if (U.compareAndSetInt(this, SIZECTL, i2, -1)) {
                    try {
                        if (this.table == nodeArr) {
                            this.table = new Node[i3];
                            i2 = i3 - (i3 >>> 2);
                        }
                    } finally {
                        this.sizeCtl = i2;
                    }
                } else {
                    continue;
                }
            } else if (iTableSizeFor <= i2 || length >= 1073741824) {
                break;
            } else if (nodeArr == this.table) {
                if (U.compareAndSetInt(this, SIZECTL, i2, (resizeStamp(length) << 16) + 2)) {
                    transfer(nodeArr, null);
                }
            }
        }
    }

    private final void transfer(Node[] nodeArr, Node[] nodeArr2) {
        Node[] nodeArr3;
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        char c;
        int i5;
        int i6;
        Node treeBin;
        Node treeBin2;
        Node node;
        int i7;
        ConcurrentHashMap<K, V> concurrentHashMap = this;
        int length = nodeArr.length;
        int i8 = NCPU;
        boolean z2 = true;
        int i9 = i8 > 1 ? (length >>> 3) / i8 : length;
        char c2 = 16;
        int i10 = i9 < 16 ? 16 : i9;
        if (nodeArr2 == null) {
            try {
                Node[] nodeArr4 = new Node[length << 1];
                concurrentHashMap.nextTable = nodeArr4;
                concurrentHashMap.transferIndex = length;
                nodeArr3 = nodeArr4;
            } catch (Throwable unused) {
                concurrentHashMap.sizeCtl = Integer.MAX_VALUE;
                return;
            }
        } else {
            nodeArr3 = nodeArr2;
        }
        int length2 = nodeArr3.length;
        ForwardingNode forwardingNode = new ForwardingNode(nodeArr3);
        boolean zCasTabAt = true;
        int i11 = 0;
        int i12 = 0;
        boolean z3 = false;
        while (true) {
            if (zCasTabAt) {
                int i13 = i11 - 1;
                if (i13 >= i12 || z3) {
                    i12 = i12;
                    i11 = i13;
                    zCasTabAt = false;
                } else {
                    int i14 = concurrentHashMap.transferIndex;
                    if (i14 <= 0) {
                        i11 = -1;
                    } else {
                        DesugarUnsafe desugarUnsafe = U;
                        int i15 = i12;
                        long j = TRANSFERINDEX;
                        if (i14 > i10) {
                            i2 = i15;
                            i3 = i14 - i10;
                            i = i13;
                        } else {
                            i = i13;
                            i2 = i15;
                            i3 = 0;
                        }
                        boolean zCompareAndSetInt = desugarUnsafe.compareAndSetInt(concurrentHashMap, j, i14, i3);
                        i12 = i3;
                        if (zCompareAndSetInt) {
                            i11 = i14 - 1;
                        } else {
                            i12 = i2;
                            i11 = i;
                        }
                    }
                    zCasTabAt = false;
                }
            } else {
                int i16 = i12;
                TreeNode treeNode = null;
                Node node2 = null;
                if (i11 < 0 || i11 >= length || (i6 = i11 + length) >= length2) {
                    i4 = length;
                    z = z2;
                    c = c2;
                    i5 = i10;
                    if (z3) {
                        concurrentHashMap.nextTable = null;
                        concurrentHashMap.table = nodeArr3;
                        concurrentHashMap.sizeCtl = (i4 << 1) - (i4 >>> 1);
                        return;
                    }
                    int i17 = i11;
                    DesugarUnsafe desugarUnsafe2 = U;
                    long j2 = SIZECTL;
                    int i18 = concurrentHashMap.sizeCtl;
                    if (!desugarUnsafe2.compareAndSetInt(concurrentHashMap, j2, i18, i18 - 1)) {
                        i11 = i17;
                    } else {
                        if (i18 - 2 != (resizeStamp(i4) << 16)) {
                            return;
                        }
                        zCasTabAt = z;
                        z3 = zCasTabAt;
                        i11 = i4;
                    }
                } else {
                    Node nodeTabAt = tabAt(nodeArr, i11);
                    if (nodeTabAt == null) {
                        zCasTabAt = casTabAt(nodeArr, i11, null, forwardingNode);
                        i4 = length;
                        z = z2;
                        c = c2;
                        i5 = i10;
                    } else {
                        z = z2;
                        int i19 = nodeTabAt.hash;
                        if (i19 == -1) {
                            i4 = length;
                            c = c2;
                            i5 = i10;
                            zCasTabAt = z;
                        } else {
                            synchronized (nodeTabAt) {
                                try {
                                    if (tabAt(nodeArr, i11) == nodeTabAt) {
                                        if (i19 >= 0) {
                                            int i20 = i19 & length;
                                            Node node3 = nodeTabAt.next;
                                            Node node4 = nodeTabAt;
                                            while (node3 != null) {
                                                char c3 = c2;
                                                int i21 = node3.hash & length;
                                                if (i21 != i20) {
                                                    node4 = node3;
                                                    i20 = i21;
                                                }
                                                node3 = node3.next;
                                                c2 = c3;
                                            }
                                            c = c2;
                                            if (i20 == 0) {
                                                node = null;
                                                node2 = node4;
                                            } else {
                                                node = node4;
                                            }
                                            Node node5 = nodeTabAt;
                                            while (node5 != node4) {
                                                int i22 = node5.hash;
                                                Object obj = node5.key;
                                                int i23 = length;
                                                Object obj2 = node5.val;
                                                if ((i22 & i23) == 0) {
                                                    i7 = i10;
                                                    node2 = new Node(i22, obj, obj2, node2);
                                                } else {
                                                    i7 = i10;
                                                    node = new Node(i22, obj, obj2, node);
                                                }
                                                node5 = node5.next;
                                                length = i23;
                                                i10 = i7;
                                            }
                                            i4 = length;
                                            i5 = i10;
                                            setTabAt(nodeArr3, i11, node2);
                                            setTabAt(nodeArr3, i6, node);
                                            setTabAt(nodeArr, i11, forwardingNode);
                                        } else {
                                            i4 = length;
                                            c = c2;
                                            i5 = i10;
                                            if (nodeTabAt instanceof TreeBin) {
                                                TreeBin treeBin3 = (TreeBin) nodeTabAt;
                                                TreeNode treeNode2 = null;
                                                TreeNode treeNode3 = null;
                                                Node node6 = treeBin3.first;
                                                int i24 = 0;
                                                int i25 = 0;
                                                TreeNode treeNode4 = null;
                                                while (node6 != null) {
                                                    TreeBin treeBin4 = treeBin3;
                                                    int i26 = node6.hash;
                                                    TreeNode treeNode5 = new TreeNode(i26, node6.key, node6.val, null, null);
                                                    if ((i26 & i4) == 0) {
                                                        treeNode5.prev = treeNode3;
                                                        if (treeNode3 == null) {
                                                            treeNode = treeNode5;
                                                        } else {
                                                            treeNode3.next = treeNode5;
                                                        }
                                                        i24++;
                                                        treeNode3 = treeNode5;
                                                    } else {
                                                        treeNode5.prev = treeNode2;
                                                        if (treeNode2 == null) {
                                                            treeNode4 = treeNode5;
                                                        } else {
                                                            treeNode2.next = treeNode5;
                                                        }
                                                        i25++;
                                                        treeNode2 = treeNode5;
                                                    }
                                                    node6 = node6.next;
                                                    treeBin3 = treeBin4;
                                                }
                                                TreeBin treeBin5 = treeBin3;
                                                if (i24 <= 6) {
                                                    treeBin = untreeify(treeNode);
                                                } else {
                                                    treeBin = i25 != 0 ? new TreeBin(treeNode) : treeBin5;
                                                }
                                                if (i25 <= 6) {
                                                    treeBin2 = untreeify(treeNode4);
                                                } else {
                                                    treeBin2 = i24 != 0 ? new TreeBin(treeNode4) : treeBin5;
                                                }
                                                setTabAt(nodeArr3, i11, treeBin);
                                                setTabAt(nodeArr3, i6, treeBin2);
                                                setTabAt(nodeArr, i11, forwardingNode);
                                            }
                                        }
                                        zCasTabAt = z;
                                    } else {
                                        i4 = length;
                                        c = c2;
                                        i5 = i10;
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                }
                concurrentHashMap = this;
                i12 = i16;
                z2 = z;
                c2 = c;
                length = i4;
                i10 = i5;
            }
        }
    }

    static final class CounterCell {
        volatile long value;

        CounterCell(long j) {
            this.value = j;
        }
    }

    final long sumCount() {
        CounterCell[] counterCellArr = this.counterCells;
        long j = this.baseCount;
        if (counterCellArr != null) {
            for (CounterCell counterCell : counterCellArr) {
                if (counterCell != null) {
                    j += counterCell.value;
                }
            }
        }
        return j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0099, code lost:
    
        if (r1.counterCells != r6) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009b, code lost:
    
        r1.counterCells = (j$.util.concurrent.ConcurrentHashMap.CounterCell[]) java.util.Arrays.copyOf(r6, r7 << 1);
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x001c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00f6 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void fullAddCount(long r24, boolean r26) {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.fullAddCount(long, boolean):void");
    }

    private final void treeifyBin(Node[] nodeArr, int i) {
        if (nodeArr != null) {
            int length = nodeArr.length;
            if (length < 64) {
                tryPresize(length << 1);
                return;
            }
            Node nodeTabAt = tabAt(nodeArr, i);
            if (nodeTabAt == null || nodeTabAt.hash < 0) {
                return;
            }
            synchronized (nodeTabAt) {
                try {
                    if (tabAt(nodeArr, i) == nodeTabAt) {
                        TreeNode treeNode = null;
                        TreeNode treeNode2 = null;
                        Node node = nodeTabAt;
                        while (node != null) {
                            TreeNode treeNode3 = new TreeNode(node.hash, node.key, node.val, null, null);
                            treeNode3.prev = treeNode2;
                            if (treeNode2 == null) {
                                treeNode = treeNode3;
                            } else {
                                treeNode2.next = treeNode3;
                            }
                            node = node.next;
                            treeNode2 = treeNode3;
                        }
                        setTabAt(nodeArr, i, new TreeBin(treeNode));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static Node untreeify(Node node) {
        Node node2 = null;
        Node node3 = null;
        while (node != null) {
            Node node4 = new Node(node.hash, node.key, node.val);
            if (node3 == null) {
                node2 = node4;
            } else {
                node3.next = node4;
            }
            node = node.next;
            node3 = node4;
        }
        return node2;
    }

    static final class TreeNode extends Node {
        TreeNode left;
        TreeNode parent;
        TreeNode prev;
        boolean red;
        TreeNode right;

        TreeNode(int i, Object obj, Object obj2, Node node, TreeNode treeNode) {
            super(i, obj, obj2, node);
            this.parent = treeNode;
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.Node
        Node find(int i, Object obj) {
            return findTreeNode(i, obj, null);
        }

        final TreeNode findTreeNode(int i, Object obj, Class cls) {
            int iCompareComparables;
            if (obj == null) {
                return null;
            }
            TreeNode treeNode = this;
            do {
                TreeNode treeNode2 = treeNode.left;
                TreeNode treeNode3 = treeNode.right;
                int i2 = treeNode.hash;
                if (i2 <= i) {
                    if (i2 >= i) {
                        Object obj2 = treeNode.key;
                        if (obj2 == obj || (obj2 != null && obj.equals(obj2))) {
                            return treeNode;
                        }
                        if (treeNode2 != null) {
                            if (treeNode3 != null) {
                                if ((cls == null && (cls = ConcurrentHashMap.comparableClassFor(obj)) == null) || (iCompareComparables = ConcurrentHashMap.compareComparables(cls, obj, obj2)) == 0) {
                                    TreeNode treeNodeFindTreeNode = treeNode3.findTreeNode(i, obj, cls);
                                    if (treeNodeFindTreeNode != null) {
                                        return treeNodeFindTreeNode;
                                    }
                                } else if (iCompareComparables >= 0) {
                                    treeNode2 = treeNode3;
                                }
                            }
                            treeNode = treeNode2;
                        }
                    }
                    treeNode = treeNode3;
                } else {
                    treeNode = treeNode2;
                }
            } while (treeNode != null);
            return null;
        }
    }

    static final class TreeBin extends Node {
        private static final long LOCKSTATE;
        private static final DesugarUnsafe U;
        volatile TreeNode first;
        volatile int lockState;
        TreeNode root;
        volatile Thread waiter;

        static {
            DesugarUnsafe unsafe = DesugarUnsafe.getUnsafe();
            U = unsafe;
            LOCKSTATE = unsafe.objectFieldOffset(TreeBin.class, "lockState");
        }

        static int tieBreakOrder(Object obj, Object obj2) {
            int iCompareTo;
            return (obj == null || obj2 == null || (iCompareTo = obj.getClass().getName().compareTo(obj2.getClass().getName())) == 0) ? System.identityHashCode(obj) <= System.identityHashCode(obj2) ? -1 : 1 : iCompareTo;
        }

        TreeBin(TreeNode treeNode) {
            int iCompareComparables;
            int iTieBreakOrder;
            super(-2, null, null);
            this.first = treeNode;
            TreeNode treeNode2 = null;
            while (treeNode != null) {
                TreeNode treeNode3 = (TreeNode) treeNode.next;
                treeNode.right = null;
                treeNode.left = null;
                if (treeNode2 == null) {
                    treeNode.parent = null;
                    treeNode.red = false;
                } else {
                    Object obj = treeNode.key;
                    int i = treeNode.hash;
                    TreeNode treeNode4 = treeNode2;
                    Class clsComparableClassFor = null;
                    while (true) {
                        Object obj2 = treeNode4.key;
                        int i2 = treeNode4.hash;
                        if (i2 > i) {
                            iTieBreakOrder = -1;
                        } else if (i2 < i) {
                            iTieBreakOrder = 1;
                        } else {
                            iTieBreakOrder = ((clsComparableClassFor == null && (clsComparableClassFor = ConcurrentHashMap.comparableClassFor(obj)) == null) || (iCompareComparables = ConcurrentHashMap.compareComparables(clsComparableClassFor, obj, obj2)) == 0) ? tieBreakOrder(obj, obj2) : iCompareComparables;
                        }
                        TreeNode treeNode5 = iTieBreakOrder <= 0 ? treeNode4.left : treeNode4.right;
                        if (treeNode5 == null) {
                            break;
                        } else {
                            treeNode4 = treeNode5;
                        }
                    }
                    treeNode.parent = treeNode4;
                    if (iTieBreakOrder <= 0) {
                        treeNode4.left = treeNode;
                    } else {
                        treeNode4.right = treeNode;
                    }
                    treeNode = balanceInsertion(treeNode2, treeNode);
                }
                treeNode2 = treeNode;
                treeNode = treeNode3;
            }
            this.root = treeNode2;
        }

        private final void lockRoot() {
            if (U.compareAndSetInt(this, LOCKSTATE, 0, 1)) {
                return;
            }
            contendedLock();
        }

        private final void unlockRoot() {
            this.lockState = 0;
        }

        private final void contendedLock() {
            boolean z = false;
            while (true) {
                int i = this.lockState;
                if ((i & (-3)) == 0) {
                    if (U.compareAndSetInt(this, LOCKSTATE, i, 1)) {
                        break;
                    }
                } else if ((i & 2) == 0) {
                    if (U.compareAndSetInt(this, LOCKSTATE, i, i | 2)) {
                        this.waiter = Thread.currentThread();
                        z = true;
                    }
                } else if (z) {
                    LockSupport.park(this);
                }
            }
            if (z) {
                this.waiter = null;
            }
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.Node
        final Node find(int i, Object obj) {
            Object obj2;
            Thread thread;
            TreeNode treeNodeFindTreeNode = null;
            if (obj != null) {
                Node node = this.first;
                while (node != null) {
                    int i2 = this.lockState;
                    if ((i2 & 3) != 0) {
                        if (node.hash == i && ((obj2 = node.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                            return node;
                        }
                        node = node.next;
                    } else {
                        DesugarUnsafe desugarUnsafe = U;
                        long j = LOCKSTATE;
                        if (desugarUnsafe.compareAndSetInt(this, j, i2, i2 + 4)) {
                            try {
                                TreeNode treeNode = this.root;
                                if (treeNode != null) {
                                    treeNodeFindTreeNode = treeNode.findTreeNode(i, obj, null);
                                }
                                if (desugarUnsafe.getAndAddInt(this, j, -4) == 6 && (thread = this.waiter) != null) {
                                    LockSupport.unpark(thread);
                                }
                                return treeNodeFindTreeNode;
                            } finally {
                            }
                        }
                    }
                }
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:53:0x0095, code lost:
        
            return null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x009e, code lost:
        
            return r6;
         */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0065  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x009b A[LOOP:0: B:3:0x0006->B:57:0x009b, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x0069 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final j$.util.concurrent.ConcurrentHashMap.TreeNode putTreeVal(int r11, java.lang.Object r12, java.lang.Object r13) {
            /*
                r10 = this;
                j$.util.concurrent.ConcurrentHashMap$TreeNode r0 = r10.root
                r7 = 0
                r1 = 0
                r6 = r0
                r0 = r7
            L6:
                if (r6 != 0) goto L18
                j$.util.concurrent.ConcurrentHashMap$TreeNode r1 = new j$.util.concurrent.ConcurrentHashMap$TreeNode
                r5 = 0
                r6 = 0
                r2 = r11
                r3 = r12
                r4 = r13
                r1.<init>(r2, r3, r4, r5, r6)
                r10.root = r1
                r10.first = r1
                goto L95
            L18:
                int r4 = r6.hash
                r8 = 1
                if (r4 <= r11) goto L20
                r4 = -1
            L1e:
                r9 = r4
                goto L60
            L20:
                if (r4 >= r11) goto L24
                r9 = r8
                goto L60
            L24:
                java.lang.Object r4 = r6.key
                if (r4 == r12) goto L9e
                if (r4 == 0) goto L32
                boolean r5 = r12.equals(r4)
                if (r5 == 0) goto L32
                goto L9e
            L32:
                if (r0 != 0) goto L3a
                java.lang.Class r0 = j$.util.concurrent.ConcurrentHashMap.comparableClassFor(r12)
                if (r0 == 0) goto L40
            L3a:
                int r5 = j$.util.concurrent.ConcurrentHashMap.compareComparables(r0, r12, r4)
                if (r5 != 0) goto L5f
            L40:
                if (r1 != 0) goto L5a
                j$.util.concurrent.ConcurrentHashMap$TreeNode r1 = r6.left
                if (r1 == 0) goto L4e
                j$.util.concurrent.ConcurrentHashMap$TreeNode r1 = r1.findTreeNode(r11, r12, r0)
                if (r1 != 0) goto L4d
                goto L4e
            L4d:
                return r1
            L4e:
                j$.util.concurrent.ConcurrentHashMap$TreeNode r1 = r6.right
                if (r1 == 0) goto L59
                j$.util.concurrent.ConcurrentHashMap$TreeNode r1 = r1.findTreeNode(r11, r12, r0)
                if (r1 == 0) goto L59
                return r1
            L59:
                r1 = r8
            L5a:
                int r4 = tieBreakOrder(r12, r4)
                goto L1e
            L5f:
                r9 = r5
            L60:
                if (r9 > 0) goto L65
                j$.util.concurrent.ConcurrentHashMap$TreeNode r4 = r6.left
                goto L67
            L65:
                j$.util.concurrent.ConcurrentHashMap$TreeNode r4 = r6.right
            L67:
                if (r4 != 0) goto L9b
                j$.util.concurrent.ConcurrentHashMap$TreeNode r5 = r10.first
                j$.util.concurrent.ConcurrentHashMap$TreeNode r1 = new j$.util.concurrent.ConcurrentHashMap$TreeNode
                r2 = r11
                r3 = r12
                r4 = r13
                r1.<init>(r2, r3, r4, r5, r6)
                r10.first = r1
                if (r5 == 0) goto L79
                r5.prev = r1
            L79:
                if (r9 > 0) goto L7e
                r6.left = r1
                goto L80
            L7e:
                r6.right = r1
            L80:
                boolean r0 = r6.red
                if (r0 != 0) goto L87
                r1.red = r8
                goto L95
            L87:
                r10.lockRoot()
                j$.util.concurrent.ConcurrentHashMap$TreeNode r0 = r10.root     // Catch: java.lang.Throwable -> L96
                j$.util.concurrent.ConcurrentHashMap$TreeNode r0 = balanceInsertion(r0, r1)     // Catch: java.lang.Throwable -> L96
                r10.root = r0     // Catch: java.lang.Throwable -> L96
                r10.unlockRoot()
            L95:
                return r7
            L96:
                r0 = move-exception
                r10.unlockRoot()
                throw r0
            L9b:
                r6 = r4
                goto L6
            L9e:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.TreeBin.putTreeVal(int, java.lang.Object, java.lang.Object):j$.util.concurrent.ConcurrentHashMap$TreeNode");
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x008d A[PHI: r0
  0x008d: PHI (r0v4 j$.util.concurrent.ConcurrentHashMap$TreeNode) = (r0v3 j$.util.concurrent.ConcurrentHashMap$TreeNode), (r0v12 j$.util.concurrent.ConcurrentHashMap$TreeNode) binds: [B:55:0x0089, B:51:0x0082] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final boolean removeTreeNode(j$.util.concurrent.ConcurrentHashMap.TreeNode r10) {
            /*
                Method dump skipped, instruction units count: 209
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.TreeBin.removeTreeNode(j$.util.concurrent.ConcurrentHashMap$TreeNode):boolean");
        }

        static TreeNode rotateLeft(TreeNode treeNode, TreeNode treeNode2) {
            TreeNode treeNode3;
            if (treeNode2 != null && (treeNode3 = treeNode2.right) != null) {
                TreeNode treeNode4 = treeNode3.left;
                treeNode2.right = treeNode4;
                if (treeNode4 != null) {
                    treeNode4.parent = treeNode2;
                }
                TreeNode treeNode5 = treeNode2.parent;
                treeNode3.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3.red = false;
                    treeNode = treeNode3;
                } else if (treeNode5.left == treeNode2) {
                    treeNode5.left = treeNode3;
                } else {
                    treeNode5.right = treeNode3;
                }
                treeNode3.left = treeNode2;
                treeNode2.parent = treeNode3;
            }
            return treeNode;
        }

        static TreeNode rotateRight(TreeNode treeNode, TreeNode treeNode2) {
            TreeNode treeNode3;
            if (treeNode2 != null && (treeNode3 = treeNode2.left) != null) {
                TreeNode treeNode4 = treeNode3.right;
                treeNode2.left = treeNode4;
                if (treeNode4 != null) {
                    treeNode4.parent = treeNode2;
                }
                TreeNode treeNode5 = treeNode2.parent;
                treeNode3.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3.red = false;
                    treeNode = treeNode3;
                } else if (treeNode5.right == treeNode2) {
                    treeNode5.right = treeNode3;
                } else {
                    treeNode5.left = treeNode3;
                }
                treeNode3.right = treeNode2;
                treeNode2.parent = treeNode3;
            }
            return treeNode;
        }

        static TreeNode balanceInsertion(TreeNode treeNode, TreeNode treeNode2) {
            TreeNode treeNode3;
            treeNode2.red = true;
            while (true) {
                TreeNode treeNode4 = treeNode2.parent;
                if (treeNode4 == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (!treeNode4.red || (treeNode3 = treeNode4.parent) == null) {
                    break;
                }
                TreeNode treeNode5 = treeNode3.left;
                if (treeNode4 == treeNode5) {
                    TreeNode treeNode6 = treeNode3.right;
                    if (treeNode6 != null && treeNode6.red) {
                        treeNode6.red = false;
                        treeNode4.red = false;
                        treeNode3.red = true;
                        treeNode2 = treeNode3;
                    } else {
                        if (treeNode2 == treeNode4.right) {
                            treeNode = rotateLeft(treeNode, treeNode4);
                            TreeNode treeNode7 = treeNode4.parent;
                            treeNode3 = treeNode7 == null ? null : treeNode7.parent;
                            treeNode4 = treeNode7;
                            treeNode2 = treeNode4;
                        }
                        if (treeNode4 != null) {
                            treeNode4.red = false;
                            if (treeNode3 != null) {
                                treeNode3.red = true;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                        }
                    }
                } else if (treeNode5 != null && treeNode5.red) {
                    treeNode5.red = false;
                    treeNode4.red = false;
                    treeNode3.red = true;
                    treeNode2 = treeNode3;
                } else {
                    if (treeNode2 == treeNode4.left) {
                        treeNode = rotateRight(treeNode, treeNode4);
                        TreeNode treeNode8 = treeNode4.parent;
                        treeNode3 = treeNode8 == null ? null : treeNode8.parent;
                        treeNode4 = treeNode8;
                        treeNode2 = treeNode4;
                    }
                    if (treeNode4 != null) {
                        treeNode4.red = false;
                        if (treeNode3 != null) {
                            treeNode3.red = true;
                            treeNode = rotateLeft(treeNode, treeNode3);
                        }
                    }
                }
            }
            return treeNode;
        }

        static TreeNode balanceDeletion(TreeNode treeNode, TreeNode treeNode2) {
            while (treeNode2 != null && treeNode2 != treeNode) {
                TreeNode treeNode3 = treeNode2.parent;
                if (treeNode3 == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (treeNode2.red) {
                    treeNode2.red = false;
                    return treeNode;
                }
                TreeNode treeNode4 = treeNode3.left;
                if (treeNode4 == treeNode2) {
                    TreeNode treeNode5 = treeNode3.right;
                    if (treeNode5 != null && treeNode5.red) {
                        treeNode5.red = false;
                        treeNode3.red = true;
                        treeNode = rotateLeft(treeNode, treeNode3);
                        treeNode3 = treeNode2.parent;
                        treeNode5 = treeNode3 == null ? null : treeNode3.right;
                    }
                    if (treeNode5 != null) {
                        TreeNode treeNode6 = treeNode5.left;
                        TreeNode treeNode7 = treeNode5.right;
                        if ((treeNode7 == null || !treeNode7.red) && (treeNode6 == null || !treeNode6.red)) {
                            treeNode5.red = true;
                        } else {
                            if (treeNode7 == null || !treeNode7.red) {
                                if (treeNode6 != null) {
                                    treeNode6.red = false;
                                }
                                treeNode5.red = true;
                                treeNode = rotateRight(treeNode, treeNode5);
                                treeNode3 = treeNode2.parent;
                                treeNode5 = treeNode3 != null ? treeNode3.right : null;
                            }
                            if (treeNode5 != null) {
                                treeNode5.red = treeNode3 == null ? false : treeNode3.red;
                                TreeNode treeNode8 = treeNode5.right;
                                if (treeNode8 != null) {
                                    treeNode8.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateLeft(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                    treeNode2 = treeNode3;
                } else {
                    if (treeNode4 != null && treeNode4.red) {
                        treeNode4.red = false;
                        treeNode3.red = true;
                        treeNode = rotateRight(treeNode, treeNode3);
                        treeNode3 = treeNode2.parent;
                        treeNode4 = treeNode3 == null ? null : treeNode3.left;
                    }
                    if (treeNode4 != null) {
                        TreeNode treeNode9 = treeNode4.left;
                        TreeNode treeNode10 = treeNode4.right;
                        if ((treeNode9 == null || !treeNode9.red) && (treeNode10 == null || !treeNode10.red)) {
                            treeNode4.red = true;
                        } else {
                            if (treeNode9 == null || !treeNode9.red) {
                                if (treeNode10 != null) {
                                    treeNode10.red = false;
                                }
                                treeNode4.red = true;
                                treeNode = rotateLeft(treeNode, treeNode4);
                                treeNode3 = treeNode2.parent;
                                treeNode4 = treeNode3 != null ? treeNode3.left : null;
                            }
                            if (treeNode4 != null) {
                                treeNode4.red = treeNode3 == null ? false : treeNode3.red;
                                TreeNode treeNode11 = treeNode4.left;
                                if (treeNode11 != null) {
                                    treeNode11.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                    treeNode2 = treeNode3;
                }
            }
            return treeNode;
        }
    }

    static final class TableStack {
        int index;
        int length;
        TableStack next;
        Node[] tab;

        TableStack() {
        }
    }

    static class Traverser {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int index;
        Node next = null;
        TableStack spare;
        TableStack stack;
        Node[] tab;

        Traverser(Node[] nodeArr, int i, int i2, int i3) {
            this.tab = nodeArr;
            this.baseSize = i;
            this.index = i2;
            this.baseIndex = i2;
            this.baseLimit = i3;
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x004b A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0047 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final j$.util.concurrent.ConcurrentHashMap.Node advance() {
            /*
                r6 = this;
                j$.util.concurrent.ConcurrentHashMap$Node r0 = r6.next
                if (r0 == 0) goto L6
                j$.util.concurrent.ConcurrentHashMap$Node r0 = r0.next
            L6:
                if (r0 == 0) goto Lb
                r6.next = r0
                return r0
            Lb:
                int r0 = r6.baseIndex
                int r1 = r6.baseLimit
                r2 = 0
                if (r0 >= r1) goto L5b
                j$.util.concurrent.ConcurrentHashMap$Node[] r0 = r6.tab
                if (r0 == 0) goto L5b
                int r1 = r0.length
                int r3 = r6.index
                if (r1 <= r3) goto L5b
                if (r3 >= 0) goto L1e
                goto L5b
            L1e:
                j$.util.concurrent.ConcurrentHashMap$Node r4 = j$.util.concurrent.ConcurrentHashMap.tabAt(r0, r3)
                if (r4 == 0) goto L42
                int r5 = r4.hash
                if (r5 >= 0) goto L42
                boolean r5 = r4 instanceof j$.util.concurrent.ConcurrentHashMap.ForwardingNode
                if (r5 == 0) goto L37
                j$.util.concurrent.ConcurrentHashMap$ForwardingNode r4 = (j$.util.concurrent.ConcurrentHashMap.ForwardingNode) r4
                j$.util.concurrent.ConcurrentHashMap$Node[] r4 = r4.nextTable
                r6.tab = r4
                r6.pushState(r0, r3, r1)
                r0 = r2
                goto L6
            L37:
                boolean r0 = r4 instanceof j$.util.concurrent.ConcurrentHashMap.TreeBin
                if (r0 == 0) goto L40
                j$.util.concurrent.ConcurrentHashMap$TreeBin r4 = (j$.util.concurrent.ConcurrentHashMap.TreeBin) r4
                j$.util.concurrent.ConcurrentHashMap$TreeNode r0 = r4.first
                goto L43
            L40:
                r0 = r2
                goto L43
            L42:
                r0 = r4
            L43:
                j$.util.concurrent.ConcurrentHashMap$TableStack r2 = r6.stack
                if (r2 == 0) goto L4b
                r6.recoverState(r1)
                goto L6
            L4b:
                int r2 = r6.baseSize
                int r3 = r3 + r2
                r6.index = r3
                if (r3 < r1) goto L6
                int r1 = r6.baseIndex
                int r1 = r1 + 1
                r6.baseIndex = r1
                r6.index = r1
                goto L6
            L5b:
                r6.next = r2
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.ConcurrentHashMap.Traverser.advance():j$.util.concurrent.ConcurrentHashMap$Node");
        }

        private void pushState(Node[] nodeArr, int i, int i2) {
            TableStack tableStack = this.spare;
            if (tableStack != null) {
                this.spare = tableStack.next;
            } else {
                tableStack = new TableStack();
            }
            tableStack.tab = nodeArr;
            tableStack.length = i2;
            tableStack.index = i;
            tableStack.next = this.stack;
            this.stack = tableStack;
        }

        private void recoverState(int i) {
            TableStack tableStack;
            while (true) {
                tableStack = this.stack;
                if (tableStack == null) {
                    break;
                }
                int i2 = this.index;
                int i3 = tableStack.length;
                int i4 = i2 + i3;
                this.index = i4;
                if (i4 < i) {
                    break;
                }
                this.index = tableStack.index;
                this.tab = tableStack.tab;
                tableStack.tab = null;
                TableStack tableStack2 = tableStack.next;
                tableStack.next = this.spare;
                this.stack = tableStack2;
                this.spare = tableStack;
                i = i3;
            }
            if (tableStack == null) {
                int i5 = this.index + this.baseSize;
                this.index = i5;
                if (i5 >= i) {
                    int i6 = this.baseIndex + 1;
                    this.baseIndex = i6;
                    this.index = i6;
                }
            }
        }
    }

    static class BaseIterator extends Traverser {
        Node lastReturned;
        final ConcurrentHashMap map;

        BaseIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3);
            this.map = concurrentHashMap;
            advance();
        }

        public final boolean hasNext() {
            return this.next != null;
        }

        public final boolean hasMoreElements() {
            return this.next != null;
        }

        public final void remove() {
            Node node = this.lastReturned;
            if (node == null) {
                throw new IllegalStateException();
            }
            this.lastReturned = null;
            this.map.replaceNode(node.key, null, null);
        }
    }

    static final class KeyIterator extends BaseIterator implements Iterator, Enumeration {
        KeyIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3, concurrentHashMap);
        }

        @Override // java.util.Iterator
        public final Object next() {
            Node node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = node.key;
            this.lastReturned = node;
            advance();
            return obj;
        }

        @Override // java.util.Enumeration
        public final Object nextElement() {
            return next();
        }
    }

    static final class ValueIterator extends BaseIterator implements Iterator, Enumeration {
        ValueIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3, concurrentHashMap);
        }

        @Override // java.util.Iterator
        public final Object next() {
            Node node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = node.val;
            this.lastReturned = node;
            advance();
            return obj;
        }

        @Override // java.util.Enumeration
        public final Object nextElement() {
            return next();
        }
    }

    static final class EntryIterator extends BaseIterator implements Iterator {
        EntryIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3, concurrentHashMap);
        }

        @Override // java.util.Iterator
        public final Map.Entry next() {
            Node node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = node.key;
            Object obj2 = node.val;
            this.lastReturned = node;
            advance();
            return new MapEntry(obj, obj2, this.map);
        }
    }

    static final class MapEntry implements Map.Entry {
        final Object key;
        final ConcurrentHashMap map;
        Object val;

        MapEntry(Object obj, Object obj2, ConcurrentHashMap concurrentHashMap) {
            this.key = obj;
            this.val = obj2;
            this.map = concurrentHashMap;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.val;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        public String toString() {
            return Helpers.mapEntryToString(this.key, this.val);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (value = entry.getValue()) == null) {
                return false;
            }
            Object obj2 = this.key;
            if (key != obj2 && !key.equals(obj2)) {
                return false;
            }
            Object obj3 = this.val;
            return value == obj3 || value.equals(obj3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            obj.getClass();
            Object obj2 = this.val;
            this.val = obj;
            this.map.put(this.key, obj);
            return obj2;
        }
    }

    static final class KeySpliterator extends Traverser implements Spliterator {
        long est;

        @Override // j$.util.Spliterator
        public int characteristics() {
            return 4353;
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        KeySpliterator(Node[] nodeArr, int i, int i2, int i3, long j) {
            super(nodeArr, i, i2, i3);
            this.est = j;
        }

        @Override // j$.util.Spliterator
        public KeySpliterator trySplit() {
            int i = this.baseIndex;
            int i2 = this.baseLimit;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            Node[] nodeArr = this.tab;
            int i4 = this.baseSize;
            this.baseLimit = i3;
            long j = this.est >>> 1;
            this.est = j;
            return new KeySpliterator(nodeArr, i4, i3, i2, j);
        }

        @Override // j$.util.Spliterator
        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            while (true) {
                Node nodeAdvance = advance();
                if (nodeAdvance == null) {
                    return;
                } else {
                    consumer.accept(nodeAdvance.key);
                }
            }
        }

        @Override // j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            Node nodeAdvance = advance();
            if (nodeAdvance == null) {
                return false;
            }
            consumer.accept(nodeAdvance.key);
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.est;
        }
    }

    static final class ValueSpliterator extends Traverser implements Spliterator {
        long est;

        @Override // j$.util.Spliterator
        public int characteristics() {
            return 4352;
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        ValueSpliterator(Node[] nodeArr, int i, int i2, int i3, long j) {
            super(nodeArr, i, i2, i3);
            this.est = j;
        }

        @Override // j$.util.Spliterator
        public ValueSpliterator trySplit() {
            int i = this.baseIndex;
            int i2 = this.baseLimit;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            Node[] nodeArr = this.tab;
            int i4 = this.baseSize;
            this.baseLimit = i3;
            long j = this.est >>> 1;
            this.est = j;
            return new ValueSpliterator(nodeArr, i4, i3, i2, j);
        }

        @Override // j$.util.Spliterator
        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            while (true) {
                Node nodeAdvance = advance();
                if (nodeAdvance == null) {
                    return;
                } else {
                    consumer.accept(nodeAdvance.val);
                }
            }
        }

        @Override // j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            Node nodeAdvance = advance();
            if (nodeAdvance == null) {
                return false;
            }
            consumer.accept(nodeAdvance.val);
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.est;
        }
    }

    static final class EntrySpliterator extends Traverser implements Spliterator {
        long est;
        final ConcurrentHashMap map;

        @Override // j$.util.Spliterator
        public int characteristics() {
            return 4353;
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        EntrySpliterator(Node[] nodeArr, int i, int i2, int i3, long j, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3);
            this.map = concurrentHashMap;
            this.est = j;
        }

        @Override // j$.util.Spliterator
        public EntrySpliterator trySplit() {
            int i = this.baseIndex;
            int i2 = this.baseLimit;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            Node[] nodeArr = this.tab;
            int i4 = this.baseSize;
            this.baseLimit = i3;
            long j = this.est >>> 1;
            this.est = j;
            return new EntrySpliterator(nodeArr, i4, i3, i2, j, this.map);
        }

        @Override // j$.util.Spliterator
        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            while (true) {
                Node nodeAdvance = advance();
                if (nodeAdvance == null) {
                    return;
                } else {
                    consumer.accept(new MapEntry(nodeAdvance.key, nodeAdvance.val, this.map));
                }
            }
        }

        @Override // j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            Node nodeAdvance = advance();
            if (nodeAdvance == null) {
                return false;
            }
            consumer.accept(new MapEntry(nodeAdvance.key, nodeAdvance.val, this.map));
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.est;
        }
    }

    static abstract class CollectionView implements Collection, Serializable {
        private static final long serialVersionUID = 7249069246763182397L;
        final ConcurrentHashMap map;

        @Override // java.util.Collection
        public abstract boolean contains(Object obj);

        @Override // java.util.Collection, java.lang.Iterable
        public abstract Iterator iterator();

        @Override // java.util.Collection
        public abstract boolean remove(Object obj);

        CollectionView(ConcurrentHashMap concurrentHashMap) {
            this.map = concurrentHashMap;
        }

        @Override // java.util.Collection
        public final void clear() {
            this.map.clear();
        }

        @Override // java.util.Collection
        public final int size() {
            return this.map.size();
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Collection
        public final Object[] toArray() {
            long jMappingCount = this.map.mappingCount();
            if (jMappingCount > 2147483639) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int i = (int) jMappingCount;
            Object[] objArrCopyOf = new Object[i];
            int i2 = 0;
            for (Object obj : this) {
                if (i2 == i) {
                    int i3 = OsCollectionChangeSet.MAX_ARRAY_LENGTH;
                    if (i >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (i < 1073741819) {
                        i3 = (i >>> 1) + 1 + i;
                    }
                    objArrCopyOf = Arrays.copyOf(objArrCopyOf, i3);
                    i = i3;
                }
                objArrCopyOf[i2] = obj;
                i2++;
            }
            return i2 == i ? objArrCopyOf : Arrays.copyOf(objArrCopyOf, i2);
        }

        @Override // java.util.Collection
        public final Object[] toArray(Object[] objArr) {
            long jMappingCount = this.map.mappingCount();
            if (jMappingCount > 2147483639) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int i = (int) jMappingCount;
            Object[] objArrCopyOf = objArr.length >= i ? objArr : (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
            int length = objArrCopyOf.length;
            int i2 = 0;
            for (Object obj : this) {
                if (i2 == length) {
                    int i3 = OsCollectionChangeSet.MAX_ARRAY_LENGTH;
                    if (length >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (length < 1073741819) {
                        i3 = (length >>> 1) + 1 + length;
                    }
                    objArrCopyOf = Arrays.copyOf(objArrCopyOf, i3);
                    length = i3;
                }
                objArrCopyOf[i2] = obj;
                i2++;
            }
            if (objArr != objArrCopyOf || i2 >= length) {
                return i2 == length ? objArrCopyOf : Arrays.copyOf(objArrCopyOf, i2);
            }
            objArrCopyOf[i2] = null;
            return objArrCopyOf;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            Iterator it = iterator();
            if (it.hasNext()) {
                while (true) {
                    Object next = it.next();
                    if (next == this) {
                        next = "(this Collection)";
                    }
                    sb.append(next);
                    if (!it.hasNext()) {
                        break;
                    }
                    sb.append(',');
                    sb.append(' ');
                }
            }
            sb.append(']');
            return sb.toString();
        }

        @Override // java.util.Collection
        public final boolean containsAll(Collection collection) {
            if (collection == this) {
                return true;
            }
            for (Object obj : collection) {
                if (obj == null || !contains(obj)) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection collection) {
            collection.getClass();
            Node[] nodeArr = this.map.table;
            boolean zRemove = false;
            if (nodeArr == null) {
                return false;
            }
            if ((collection instanceof Set) && collection.size() > nodeArr.length) {
                Iterator it = iterator();
                while (it.hasNext()) {
                    if (collection.contains(it.next())) {
                        it.remove();
                        zRemove = true;
                    }
                }
                return zRemove;
            }
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                zRemove |= remove(it2.next());
            }
            return zRemove;
        }

        @Override // java.util.Collection
        public final boolean retainAll(Collection collection) {
            collection.getClass();
            Iterator it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }
    }

    public static class KeySetView extends CollectionView implements Set, Serializable, j$.util.Collection {
        private static final long serialVersionUID = 7249069246763182397L;
        private final Object value;

        @Override // java.util.Collection
        public /* synthetic */ Stream parallelStream() {
            return StreamSupport.stream(Collection.EL.spliterator(this), true);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream parallelStream() {
            return Stream.Wrapper.convert(parallelStream());
        }

        @Override // java.util.Collection, j$.util.Collection
        public /* synthetic */ boolean removeIf(Predicate predicate) {
            return Collection.CC.$default$removeIf(this, predicate);
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set
        public /* synthetic */ java.util.Spliterator spliterator() {
            return Spliterator.Wrapper.convert(spliterator());
        }

        @Override // java.util.Collection, j$.util.Collection
        public /* synthetic */ Stream stream() {
            return Collection.CC.$default$stream(this);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream stream() {
            return Stream.Wrapper.convert(stream());
        }

        @Override // java.util.Collection
        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return toArray((Object[]) intFunction.apply(0));
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(java.util.Collection collection) {
            return super.removeAll(collection);
        }

        KeySetView(ConcurrentHashMap concurrentHashMap, Object obj) {
            super(concurrentHashMap);
            this.value = obj;
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public boolean remove(Object obj) {
            return this.map.remove(obj) != null;
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection, java.lang.Iterable
        public Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new KeyIterator(nodeArr, length, 0, length, concurrentHashMap);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean add(Object obj) {
            Object obj2 = this.value;
            if (obj2 != null) {
                return this.map.putVal(obj, obj2, true) == null;
            }
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, java.util.Set
        public boolean addAll(java.util.Collection collection) {
            Object obj = this.value;
            if (obj == null) {
                throw new UnsupportedOperationException();
            }
            Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (this.map.putVal(it.next(), obj, true) == null) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            Iterator it = iterator();
            int iHashCode = 0;
            while (it.hasNext()) {
                iHashCode += it.next().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            Set set = (Set) obj;
            if (set != this) {
                return containsAll(set) && set.containsAll(this);
            }
            return true;
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set, j$.util.Collection
        public Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long jSumCount = concurrentHashMap.sumCount();
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new KeySpliterator(nodeArr, length, 0, length, jSumCount < 0 ? 0L : jSumCount);
        }

        @Override // java.lang.Iterable, j$.util.Collection
        public void forEach(Consumer consumer) {
            consumer.getClass();
            Node[] nodeArr = this.map.table;
            if (nodeArr == null) {
                return;
            }
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    return;
                } else {
                    consumer.accept(nodeAdvance.key);
                }
            }
        }
    }

    static final class ValuesView extends CollectionView implements java.util.Collection, Serializable, j$.util.Collection {
        private static final long serialVersionUID = 2249069246763182397L;

        @Override // java.util.Collection
        public /* synthetic */ Stream parallelStream() {
            return StreamSupport.stream(Collection.EL.spliterator(this), true);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream parallelStream() {
            return Stream.Wrapper.convert(parallelStream());
        }

        @Override // java.util.Collection, java.lang.Iterable
        public /* synthetic */ java.util.Spliterator spliterator() {
            return Spliterator.Wrapper.convert(spliterator());
        }

        @Override // java.util.Collection, j$.util.Collection
        public /* synthetic */ Stream stream() {
            return Collection.CC.$default$stream(this);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream stream() {
            return Stream.Wrapper.convert(stream());
        }

        @Override // java.util.Collection
        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return toArray((Object[]) intFunction.apply(0));
        }

        ValuesView(ConcurrentHashMap concurrentHashMap) {
            super(concurrentHashMap);
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public final boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public final boolean remove(Object obj) {
            if (obj == null) {
                return false;
            }
            Iterator it = iterator();
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new ValueIterator(nodeArr, length, 0, length, concurrentHashMap);
        }

        @Override // java.util.Collection
        public final boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final boolean addAll(java.util.Collection collection) {
            throw new UnsupportedOperationException();
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public boolean removeAll(java.util.Collection collection) {
            collection.getClass();
            Iterator it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection, j$.util.Collection
        public boolean removeIf(Predicate predicate) {
            return this.map.removeValueIf(predicate);
        }

        @Override // java.util.Collection, java.lang.Iterable, j$.util.Collection, java.util.Set
        public Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long jSumCount = concurrentHashMap.sumCount();
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new ValueSpliterator(nodeArr, length, 0, length, jSumCount < 0 ? 0L : jSumCount);
        }

        @Override // java.lang.Iterable, j$.util.Collection
        public void forEach(Consumer consumer) {
            consumer.getClass();
            Node[] nodeArr = this.map.table;
            if (nodeArr == null) {
                return;
            }
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    return;
                } else {
                    consumer.accept(nodeAdvance.val);
                }
            }
        }
    }

    static final class EntrySetView extends CollectionView implements Set, Serializable, j$.util.Collection {
        private static final long serialVersionUID = 2249069246763182397L;

        @Override // java.util.Collection
        public /* synthetic */ Stream parallelStream() {
            return StreamSupport.stream(Collection.EL.spliterator(this), true);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream parallelStream() {
            return Stream.Wrapper.convert(parallelStream());
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set
        public /* synthetic */ java.util.Spliterator spliterator() {
            return Spliterator.Wrapper.convert(spliterator());
        }

        @Override // java.util.Collection, j$.util.Collection
        public /* synthetic */ Stream stream() {
            return Collection.CC.$default$stream(this);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream stream() {
            return Stream.Wrapper.convert(stream());
        }

        @Override // java.util.Collection
        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return toArray((Object[]) intFunction.apply(0));
        }

        EntrySetView(ConcurrentHashMap concurrentHashMap) {
            super(concurrentHashMap);
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            Object value;
            if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (obj2 = this.map.get(key)) == null || (value = entry.getValue()) == null) {
                return false;
            }
            return value == obj2 || value.equals(obj2);
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (value = entry.getValue()) != null && this.map.remove(key, value);
        }

        @Override // j$.util.concurrent.ConcurrentHashMap.CollectionView, java.util.Collection, java.lang.Iterable
        public Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new EntryIterator(nodeArr, length, 0, length, concurrentHashMap);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean add(Map.Entry entry) {
            return this.map.putVal(entry.getKey(), entry.getValue(), false) == null;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean addAll(java.util.Collection collection) {
            Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (add((Map.Entry) it.next())) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection, j$.util.Collection
        public boolean removeIf(Predicate predicate) {
            return this.map.removeEntryIf(predicate);
        }

        @Override // java.util.Collection, java.util.Set
        public final int hashCode() {
            Node[] nodeArr = this.map.table;
            int iHashCode = 0;
            if (nodeArr != null) {
                Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
                while (true) {
                    Node nodeAdvance = traverser.advance();
                    if (nodeAdvance == null) {
                        break;
                    }
                    iHashCode += nodeAdvance.hashCode();
                }
            }
            return iHashCode;
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean equals(Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            Set set = (Set) obj;
            if (set != this) {
                return containsAll(set) && set.containsAll(this);
            }
            return true;
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set, j$.util.Collection
        public Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long jSumCount = concurrentHashMap.sumCount();
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new EntrySpliterator(nodeArr, length, 0, length, jSumCount >= 0 ? jSumCount : 0L, concurrentHashMap);
        }

        @Override // java.lang.Iterable, j$.util.Collection
        public void forEach(Consumer consumer) {
            consumer.getClass();
            Node[] nodeArr = this.map.table;
            if (nodeArr == null) {
                return;
            }
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    return;
                } else {
                    consumer.accept(new MapEntry(nodeAdvance.key, nodeAdvance.val, this.map));
                }
            }
        }
    }
}

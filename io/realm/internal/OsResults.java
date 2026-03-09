package io.realm.internal;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.Sort;
import io.realm.internal.ObservableCollection;
import io.realm.internal.objectstore.OsKeyPathMapping;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class OsResults implements NativeObject, ObservableCollection {
    public static final byte AGGREGATE_FUNCTION_AVERAGE = 3;
    public static final byte AGGREGATE_FUNCTION_MAXIMUM = 2;
    public static final byte AGGREGATE_FUNCTION_MINIMUM = 1;
    public static final byte AGGREGATE_FUNCTION_SUM = 4;
    private static final String CLOSED_REALM_MESSAGE = "This Realm instance has already been closed, making it unusable.";
    public static final byte MODE_EMPTY = 0;
    public static final byte MODE_LIST = 2;
    public static final byte MODE_QUERY = 3;
    public static final byte MODE_TABLE = 1;
    public static final byte MODE_TABLEVIEW = 4;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final NativeContext context;
    protected boolean loaded;
    private final long nativePtr;
    private final OsSharedRealm sharedRealm;
    private final Table table;
    private boolean isSnapshot = false;
    protected final ObserverPairList<ObservableCollection.CollectionObserverPair> observerPairs = new ObserverPairList<>();

    private interface AddListTypeDelegate<T> {
        void addList(OsObjectBuilder osObjectBuilder, RealmList<T> realmList);
    }

    private static native Object nativeAggregate(long j, long j2, byte b);

    private static native void nativeClear(long j);

    private static native boolean nativeContains(long j, long j2);

    protected static native long nativeCreateResults(long j, long j2);

    private static native long nativeCreateResultsFromBacklinks(long j, long j2, long j3, long j4);

    private static native long nativeCreateSnapshot(long j);

    private static native void nativeDelete(long j, long j2);

    private static native boolean nativeDeleteFirst(long j);

    private static native boolean nativeDeleteLast(long j);

    private static native void nativeEvaluateQueryIfNeeded(long j, boolean z);

    private static native long nativeFirstRow(long j);

    private static native long nativeFreeze(long j, long j2);

    private static native long nativeGetFinalizerPtr();

    private static native byte nativeGetMode(long j);

    private static native long nativeGetRow(long j, int i);

    private static native long nativeGetTable(long j);

    private static native Object nativeGetValue(long j, int i);

    private static native long nativeIndexOf(long j, long j2);

    private static native boolean nativeIsValid(long j);

    private static native long nativeLastRow(long j);

    private static native void nativeSetBinary(long j, String str, @Nullable byte[] bArr);

    private static native void nativeSetBoolean(long j, String str, boolean z);

    private static native void nativeSetDecimal128(long j, String str, long j2, long j3);

    private static native void nativeSetDouble(long j, String str, double d);

    private static native void nativeSetFloat(long j, String str, float f);

    private static native void nativeSetInt(long j, String str, long j2);

    private static native void nativeSetList(long j, String str, long j2);

    private static native void nativeSetNull(long j, String str);

    private static native void nativeSetObject(long j, String str, long j2);

    private static native void nativeSetObjectId(long j, String str, String str2);

    private static native void nativeSetString(long j, String str, @Nullable String str2);

    private static native void nativeSetTimestamp(long j, String str, long j2);

    private static native void nativeSetUUID(long j, String str, String str2);

    private static native long nativeSize(long j);

    private native void nativeStartListening(long j);

    private native void nativeStopListening(long j);

    private static native long nativeStringDescriptor(long j, String str, long j2);

    private static native long nativeWhere(long j);

    private static native String toJSON(long j, int i);

    public static abstract class Iterator<T> implements java.util.Iterator<T> {
        protected OsResults iteratorOsResults;
        protected int pos = -1;

        protected abstract T convertRowToObject(UncheckedRow uncheckedRow);

        protected abstract T getInternal(int i, OsResults osResults);

        public Iterator(OsResults osResults) {
            if (osResults.sharedRealm.isClosed()) {
                throw new IllegalStateException(OsResults.CLOSED_REALM_MESSAGE);
            }
            this.iteratorOsResults = osResults;
            if (osResults.isSnapshot) {
                return;
            }
            if (!osResults.sharedRealm.isInTransaction()) {
                this.iteratorOsResults.sharedRealm.addIterator(this);
            } else {
                detach();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            checkValid();
            return ((long) (this.pos + 1)) < this.iteratorOsResults.size();
        }

        @Override // java.util.Iterator
        @Nullable
        public T next() {
            checkValid();
            int i = this.pos + 1;
            this.pos = i;
            if (i >= this.iteratorOsResults.size()) {
                throw new NoSuchElementException("Cannot access index " + this.pos + " when size is " + this.iteratorOsResults.size() + ". Remember to check hasNext() before using next().");
            }
            return get(this.pos);
        }

        @Override // java.util.Iterator
        @Deprecated
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by RealmResults iterators.");
        }

        void detach() {
            this.iteratorOsResults = this.iteratorOsResults.createSnapshot();
        }

        void invalidate() {
            this.iteratorOsResults = null;
        }

        void checkValid() {
            if (this.iteratorOsResults == null) {
                throw new ConcurrentModificationException("No outside changes to a Realm is allowed while iterating a living Realm collection.");
            }
        }

        @Nullable
        T get(int i) {
            return getInternal(i, this.iteratorOsResults);
        }
    }

    public static abstract class ListIterator<T> extends Iterator<T> implements java.util.ListIterator<T> {
        public ListIterator(OsResults osResults, int i) {
            super(osResults);
            if (i >= 0 && i <= this.iteratorOsResults.size()) {
                this.pos = i - 1;
                return;
            }
            throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (this.iteratorOsResults.size() - 1) + "]. Yours was " + i);
        }

        @Override // java.util.ListIterator
        @Deprecated
        public void add(@Nullable T t) {
            throw new UnsupportedOperationException("Adding an element is not supported. Use Realm.createObject() instead.");
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            checkValid();
            return this.pos >= 0;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            checkValid();
            return this.pos + 1;
        }

        @Override // java.util.ListIterator
        @Nullable
        public T previous() {
            checkValid();
            try {
                this.pos--;
                return get(this.pos);
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException("Cannot access index less than zero. This was " + this.pos + ". Remember to check hasPrevious() before using previous().");
            }
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            checkValid();
            return this.pos;
        }

        @Override // java.util.ListIterator
        @Deprecated
        public void set(@Nullable T t) {
            throw new UnsupportedOperationException("Replacing an element is not supported.");
        }
    }

    public enum Aggregate {
        MINIMUM((byte) 1),
        MAXIMUM((byte) 2),
        AVERAGE((byte) 3),
        SUM((byte) 4);

        private final byte value;

        Aggregate(byte b) {
            this.value = b;
        }

        public byte getValue() {
            return this.value;
        }
    }

    public enum Mode {
        EMPTY,
        TABLE,
        PRIMITIVE_LIST,
        QUERY,
        TABLEVIEW;

        static Mode getByValue(byte b) {
            if (b == 0) {
                return EMPTY;
            }
            if (b == 1) {
                return TABLE;
            }
            if (b == 2) {
                return PRIMITIVE_LIST;
            }
            if (b == 3) {
                return QUERY;
            }
            if (b == 4) {
                return TABLEVIEW;
            }
            throw new IllegalArgumentException("Invalid value: " + ((int) b));
        }
    }

    public static OsResults createForBacklinks(OsSharedRealm osSharedRealm, UncheckedRow uncheckedRow, Table table, String str) {
        return new OsResults(osSharedRealm, table, nativeCreateResultsFromBacklinks(osSharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), table.getNativePtr(), table.getColumnKey(str)));
    }

    public static OsResults createFromQuery(OsSharedRealm osSharedRealm, TableQuery tableQuery) {
        tableQuery.validateQuery();
        return new OsResults(osSharedRealm, tableQuery.getTable(), nativeCreateResults(osSharedRealm.getNativePtr(), tableQuery.getNativePtr()));
    }

    public static OsResults createFromMap(OsSharedRealm osSharedRealm, long j) {
        return new OsResults(osSharedRealm, j);
    }

    OsResults(OsSharedRealm osSharedRealm, long j) {
        this.sharedRealm = osSharedRealm;
        NativeContext nativeContext = osSharedRealm.context;
        this.context = nativeContext;
        this.nativePtr = j;
        nativeContext.addReference(this);
        this.loaded = getMode() != Mode.QUERY;
        this.table = new Table(osSharedRealm, nativeGetTable(j));
    }

    OsResults(OsSharedRealm osSharedRealm, Table table, long j) {
        this.sharedRealm = osSharedRealm;
        NativeContext nativeContext = osSharedRealm.context;
        this.context = nativeContext;
        this.table = table;
        this.nativePtr = j;
        nativeContext.addReference(this);
        this.loaded = getMode() != Mode.QUERY;
    }

    public OsResults createSnapshot() {
        if (this.isSnapshot) {
            return this;
        }
        OsResults osResults = new OsResults(this.sharedRealm, this.table, nativeCreateSnapshot(this.nativePtr));
        osResults.isSnapshot = true;
        return osResults;
    }

    public OsResults freeze(OsSharedRealm osSharedRealm) {
        OsResults osResults = new OsResults(osSharedRealm, this.table.freeze(osSharedRealm), nativeFreeze(this.nativePtr, osSharedRealm.getNativePtr()));
        if (isLoaded()) {
            osResults.load();
        }
        return osResults;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public Object getValue(int i) {
        return nativeGetValue(this.nativePtr, i);
    }

    public UncheckedRow getUncheckedRow(int i) {
        return this.table.getUncheckedRowByPointer(nativeGetRow(this.nativePtr, i));
    }

    public UncheckedRow firstUncheckedRow() {
        long jNativeFirstRow = nativeFirstRow(this.nativePtr);
        if (jNativeFirstRow != 0) {
            return this.table.getUncheckedRowByPointer(jNativeFirstRow);
        }
        return null;
    }

    public UncheckedRow lastUncheckedRow() {
        long jNativeLastRow = nativeLastRow(this.nativePtr);
        if (jNativeLastRow != 0) {
            return this.table.getUncheckedRowByPointer(jNativeLastRow);
        }
        return null;
    }

    public Table getTable() {
        return this.table;
    }

    public TableQuery where() {
        return new TableQuery(this.context, this.table, nativeWhere(this.nativePtr));
    }

    public String toJSON(int i) {
        return toJSON(this.nativePtr, i);
    }

    public Number aggregateNumber(Aggregate aggregate, long j) {
        try {
            return (Number) nativeAggregate(this.nativePtr, j, aggregate.getValue());
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Illegal Argument: " + e.getMessage());
        }
    }

    public Date aggregateDate(Aggregate aggregate, long j) {
        try {
            return (Date) nativeAggregate(this.nativePtr, j, aggregate.getValue());
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Illegal Argument: " + e.getMessage());
        }
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    public void clear() {
        nativeClear(this.nativePtr);
    }

    public OsResults sort(@Nullable OsKeyPathMapping osKeyPathMapping, String str, Sort sort) {
        return new OsResults(this.sharedRealm, this.table, stringDescriptor(this.nativePtr, TableQuery.buildSortDescriptor(new String[]{str}, new Sort[]{sort}), osKeyPathMapping != null ? osKeyPathMapping.getNativePtr() : 0L));
    }

    public OsResults sort(@Nullable OsKeyPathMapping osKeyPathMapping, String[] strArr, Sort[] sortArr) {
        if (sortArr == null || sortArr.length == 0) {
            throw new IllegalArgumentException("You must provide at least one sort order.");
        }
        if (strArr.length != sortArr.length) {
            throw new IllegalArgumentException("Number of fields and sort orders do not match.");
        }
        return new OsResults(this.sharedRealm, this.table, stringDescriptor(this.nativePtr, TableQuery.buildSortDescriptor(strArr, sortArr), osKeyPathMapping != null ? osKeyPathMapping.getNativePtr() : 0L));
    }

    public OsResults distinct(@Nullable OsKeyPathMapping osKeyPathMapping, String[] strArr) {
        return new OsResults(this.sharedRealm, this.table, stringDescriptor(this.nativePtr, TableQuery.buildDistinctDescriptor(strArr), osKeyPathMapping != null ? osKeyPathMapping.getNativePtr() : 0L));
    }

    public boolean contains(UncheckedRow uncheckedRow) {
        return nativeContains(this.nativePtr, uncheckedRow.getNativePtr());
    }

    public int indexOf(UncheckedRow uncheckedRow) {
        long jNativeIndexOf = nativeIndexOf(this.nativePtr, uncheckedRow.getNativePtr());
        if (jNativeIndexOf > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) jNativeIndexOf;
    }

    public void delete(long j) {
        nativeDelete(this.nativePtr, j);
    }

    public boolean deleteFirst() {
        return nativeDeleteFirst(this.nativePtr);
    }

    public boolean deleteLast() {
        return nativeDeleteLast(this.nativePtr);
    }

    public void setNull(String str) {
        nativeSetNull(this.nativePtr, str);
    }

    public void setBoolean(String str, boolean z) {
        nativeSetBoolean(this.nativePtr, str, z);
    }

    public void setInt(String str, long j) {
        nativeSetInt(this.nativePtr, str, j);
    }

    public void setFloat(String str, float f) {
        nativeSetFloat(this.nativePtr, str, f);
    }

    public void setDouble(String str, double d) {
        nativeSetDouble(this.nativePtr, str, d);
    }

    public void setString(String str, @Nullable String str2) {
        nativeSetString(this.nativePtr, str, str2);
    }

    public void setBlob(String str, @Nullable byte[] bArr) {
        nativeSetBinary(this.nativePtr, str, bArr);
    }

    public void setDate(String str, @Nullable Date date) {
        if (date == null) {
            nativeSetNull(this.nativePtr, str);
        } else {
            nativeSetTimestamp(this.nativePtr, str, date.getTime());
        }
    }

    public void setDecimal128(String str, @Nullable Decimal128 decimal128) {
        if (decimal128 == null) {
            nativeSetNull(this.nativePtr, str);
        } else {
            nativeSetDecimal128(this.nativePtr, str, decimal128.getLow(), decimal128.getHigh());
        }
    }

    public void setObjectId(String str, @Nullable ObjectId objectId) {
        if (objectId == null) {
            nativeSetNull(this.nativePtr, str);
        } else {
            nativeSetObjectId(this.nativePtr, str, objectId.toString());
        }
    }

    public void setUUID(String str, @Nullable UUID uuid) {
        if (uuid == null) {
            nativeSetNull(this.nativePtr, str);
        } else {
            nativeSetUUID(this.nativePtr, str, uuid.toString());
        }
    }

    public void setObject(String str, @Nullable Row row) {
        long nativePtr;
        if (row == null) {
            setNull(str);
            return;
        }
        if (row instanceof UncheckedRow) {
            nativePtr = ((UncheckedRow) row).getNativePtr();
        } else if (row instanceof CheckedRow) {
            nativePtr = ((CheckedRow) row).getNativePtr();
        } else {
            throw new UnsupportedOperationException("Unsupported Row type: " + row.getClass().getCanonicalName());
        }
        nativeSetObject(this.nativePtr, str, nativePtr);
    }

    private <T> void addTypeSpecificList(String str, RealmList<T> realmList, AddListTypeDelegate<T> addListTypeDelegate) {
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(getTable(), Collections.EMPTY_SET);
        addListTypeDelegate.addList(osObjectBuilder, realmList);
        try {
            nativeSetList(this.nativePtr, str, osObjectBuilder.getNativePtr());
        } finally {
            osObjectBuilder.close();
        }
    }

    public void setStringList(String str, RealmList<String> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<String>() { // from class: io.realm.internal.OsResults.1
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<String> realmList2) {
                osObjectBuilder.addStringList(0L, realmList2);
            }
        });
    }

    public void setByteList(String str, RealmList<Byte> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Byte>() { // from class: io.realm.internal.OsResults.2
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Byte> realmList2) {
                osObjectBuilder.addByteList(0L, realmList2);
            }
        });
    }

    public void setShortList(String str, RealmList<Short> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Short>() { // from class: io.realm.internal.OsResults.3
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Short> realmList2) {
                osObjectBuilder.addShortList(0L, realmList2);
            }
        });
    }

    public void setIntegerList(String str, RealmList<Integer> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Integer>() { // from class: io.realm.internal.OsResults.4
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Integer> realmList2) {
                osObjectBuilder.addIntegerList(0L, realmList2);
            }
        });
    }

    public void setLongList(String str, RealmList<Long> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Long>() { // from class: io.realm.internal.OsResults.5
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Long> realmList2) {
                osObjectBuilder.addLongList(0L, realmList2);
            }
        });
    }

    public void setBooleanList(String str, RealmList<Boolean> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Boolean>() { // from class: io.realm.internal.OsResults.6
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Boolean> realmList2) {
                osObjectBuilder.addBooleanList(0L, realmList2);
            }
        });
    }

    public void setByteArrayList(String str, RealmList<byte[]> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<byte[]>() { // from class: io.realm.internal.OsResults.7
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<byte[]> realmList2) {
                osObjectBuilder.addByteArrayList(0L, realmList2);
            }
        });
    }

    public void setDateList(String str, RealmList<Date> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Date>() { // from class: io.realm.internal.OsResults.8
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Date> realmList2) {
                osObjectBuilder.addDateList(0L, realmList2);
            }
        });
    }

    public void setFloatList(String str, RealmList<Float> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Float>() { // from class: io.realm.internal.OsResults.9
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Float> realmList2) {
                osObjectBuilder.addFloatList(0L, realmList2);
            }
        });
    }

    public void setDoubleList(String str, RealmList<Double> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Double>() { // from class: io.realm.internal.OsResults.10
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Double> realmList2) {
                osObjectBuilder.addDoubleList(0L, realmList2);
            }
        });
    }

    public void setModelList(String str, RealmList<RealmModel> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<RealmModel>() { // from class: io.realm.internal.OsResults.11
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<RealmModel> realmList2) {
                osObjectBuilder.addObjectList(0L, realmList2);
            }
        });
    }

    public void setDecimal128List(String str, RealmList<Decimal128> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<Decimal128>() { // from class: io.realm.internal.OsResults.12
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<Decimal128> realmList2) {
                osObjectBuilder.addDecimal128List(0L, realmList2);
            }
        });
    }

    public void setObjectIdList(String str, RealmList<ObjectId> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<ObjectId>() { // from class: io.realm.internal.OsResults.13
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<ObjectId> realmList2) {
                osObjectBuilder.addObjectIdList(0L, realmList2);
            }
        });
    }

    public void setUUIDList(String str, RealmList<UUID> realmList) {
        addTypeSpecificList(str, realmList, new AddListTypeDelegate<UUID>() { // from class: io.realm.internal.OsResults.14
            @Override // io.realm.internal.OsResults.AddListTypeDelegate
            public void addList(OsObjectBuilder osObjectBuilder, RealmList<UUID> realmList2) {
                osObjectBuilder.addUUIDList(0L, realmList2);
            }
        });
    }

    public <T> void addListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        if (this.observerPairs.isEmpty()) {
            nativeStartListening(this.nativePtr);
        }
        this.observerPairs.add(new ObservableCollection.CollectionObserverPair(t, orderedRealmCollectionChangeListener));
    }

    public <T> void addListener(T t, RealmChangeListener<T> realmChangeListener) {
        addListener(t, new ObservableCollection.RealmChangeListenerWrapper(realmChangeListener));
    }

    public <T> void removeListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        this.observerPairs.remove(t, orderedRealmCollectionChangeListener);
        if (this.observerPairs.isEmpty()) {
            nativeStopListening(this.nativePtr);
        }
    }

    public <T> void removeListener(T t, RealmChangeListener<T> realmChangeListener) {
        removeListener(t, new ObservableCollection.RealmChangeListenerWrapper(realmChangeListener));
    }

    public void removeAllListeners() {
        this.observerPairs.clear();
        nativeStopListening(this.nativePtr);
    }

    public boolean isValid() {
        return nativeIsValid(this.nativePtr);
    }

    @Override // io.realm.internal.ObservableCollection
    public void notifyChangeListeners(long j) {
        OsCollectionChangeSet osCollectionChangeSet;
        if (j == 0) {
            osCollectionChangeSet = new EmptyLoadChangeSet();
        } else {
            osCollectionChangeSet = new OsCollectionChangeSet(j, !isLoaded());
        }
        if (osCollectionChangeSet.isEmpty() && isLoaded()) {
            return;
        }
        this.loaded = true;
        this.observerPairs.foreach(new ObservableCollection.Callback(osCollectionChangeSet));
    }

    public Mode getMode() {
        return Mode.getByValue(nativeGetMode(this.nativePtr));
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void load() {
        if (this.loaded) {
            return;
        }
        try {
            nativeEvaluateQueryIfNeeded(this.nativePtr, false);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Cannot sort on a collection property")) {
                throw new IllegalStateException("Illegal Argument: " + e.getMessage());
            }
        } catch (IllegalStateException e2) {
            throw new IllegalArgumentException("Illegal Argument: " + e2.getMessage());
        }
        notifyChangeListeners(0L);
    }

    private static long stringDescriptor(long j, String str, long j2) {
        try {
            return nativeStringDescriptor(j, str, j2);
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("Realm accessed from incorrect thread.")) {
                throw e;
            }
            throw new IllegalArgumentException("Illegal Argument: " + e.getMessage());
        }
    }
}

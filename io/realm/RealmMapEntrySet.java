package io.realm;

import io.realm.internal.OsMap;
import io.realm.internal.core.NativeRealmAny;
import io.realm.internal.util.Pair;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
class RealmMapEntrySet<K, V> implements Set<Map.Entry<K, V>> {
    private final BaseRealm baseRealm;
    private final EqualsHelper<K, V> equalsHelper;
    private final IteratorType iteratorType;
    private final OsMap osMap;
    private final TypeSelectorForMap<K, V> typeSelectorForMap;

    public enum IteratorType {
        LONG,
        BYTE,
        SHORT,
        INTEGER,
        FLOAT,
        DOUBLE,
        STRING,
        BOOLEAN,
        DATE,
        DECIMAL128,
        BINARY,
        OBJECT_ID,
        UUID,
        MIXED,
        OBJECT
    }

    RealmMapEntrySet(BaseRealm baseRealm, OsMap osMap, IteratorType iteratorType, TypeSelectorForMap<K, V> typeSelectorForMap) {
        this.baseRealm = baseRealm;
        this.osMap = osMap;
        this.iteratorType = iteratorType;
        this.equalsHelper = new GenericEquals();
        this.typeSelectorForMap = typeSelectorForMap;
    }

    RealmMapEntrySet(BaseRealm baseRealm, OsMap osMap, IteratorType iteratorType, EqualsHelper<K, V> equalsHelper, TypeSelectorForMap<K, V> typeSelectorForMap) {
        this.baseRealm = baseRealm;
        this.osMap = osMap;
        this.iteratorType = iteratorType;
        this.equalsHelper = equalsHelper;
        this.typeSelectorForMap = typeSelectorForMap;
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        long size = this.osMap.size();
        if (size < 2147483647L) {
            return (int) size;
        }
        return Integer.MAX_VALUE;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.osMap.size() == 0;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        for (Map.Entry<K, V> entry : this) {
            if (entry == null && obj == null) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                break;
            }
            if (entry != null && this.equalsHelper.equalsHelper(entry, (Map.Entry) obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return iteratorFactory(this.iteratorType, this.osMap, this.baseRealm, this.typeSelectorForMap);
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        Object[] objArr = new Object[(int) this.osMap.size()];
        Iterator<Map.Entry<K, V>> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
        return objArr;
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        long size = this.osMap.size();
        Object[] objArr = (((long) tArr.length) == size || ((long) tArr.length) > size) ? tArr : (T[]) ((Object[]) Array.newInstance((Class<?>) Map.Entry.class, (int) size));
        Iterator<Map.Entry<K, V>> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
        if (tArr.length > size) {
            objArr[i] = null;
        }
        return (T[]) objArr;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(Map.Entry<K, V> entry) {
        throw new UnsupportedOperationException("This set is immutable and cannot be modified.");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("This set is immutable and cannot be modified.");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return isEmpty();
        }
        for (Object obj : collection) {
            if (!(obj instanceof Map.Entry) || !contains((Map.Entry) obj)) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
        throw new UnsupportedOperationException("This set is immutable and cannot be modified.");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("This set is immutable and cannot be modified.");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("This set is immutable and cannot be modified.");
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("This set is immutable and cannot be modified.");
    }

    /* JADX INFO: renamed from: io.realm.RealmMapEntrySet$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$RealmMapEntrySet$IteratorType;

        static {
            int[] iArr = new int[IteratorType.values().length];
            $SwitchMap$io$realm$RealmMapEntrySet$IteratorType = iArr;
            try {
                iArr[IteratorType.LONG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.BYTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.INTEGER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.FLOAT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.DOUBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.STRING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.DATE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.DECIMAL128.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.BINARY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.OBJECT_ID.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.UUID.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.MIXED.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$io$realm$RealmMapEntrySet$IteratorType[IteratorType.OBJECT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    private static <K, V> EntrySetIterator<K, V> iteratorFactory(IteratorType iteratorType, OsMap osMap, BaseRealm baseRealm, TypeSelectorForMap typeSelectorForMap) {
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmMapEntrySet$IteratorType[iteratorType.ordinal()]) {
            case 1:
                return new LongValueIterator(osMap, baseRealm);
            case 2:
                return new ByteValueIterator(osMap, baseRealm);
            case 3:
                return new ShortValueIterator(osMap, baseRealm);
            case 4:
                return new IntegerValueIterator(osMap, baseRealm);
            case 5:
                return new FloatValueIterator(osMap, baseRealm);
            case 6:
                return new DoubleValueIterator(osMap, baseRealm);
            case 7:
                return new StringValueIterator(osMap, baseRealm);
            case 8:
                return new BooleanValueIterator(osMap, baseRealm);
            case 9:
                return new DateValueIterator(osMap, baseRealm);
            case 10:
                return new Decimal128ValueIterator(osMap, baseRealm);
            case 11:
                return new BinaryValueIterator(osMap, baseRealm);
            case 12:
                return new ObjectIdValueIterator(osMap, baseRealm);
            case 13:
                return new UUIDValueIterator(osMap, baseRealm);
            case 14:
                return new RealmAnyValueIterator(osMap, baseRealm);
            case 15:
                if (typeSelectorForMap == null) {
                    throw new IllegalArgumentException("Missing class container when creating RealmModelValueIterator.");
                }
                return new RealmModelValueIterator(osMap, baseRealm, typeSelectorForMap);
            default:
                throw new IllegalArgumentException("Invalid iterator type.");
        }
    }

    private static abstract class EntrySetIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        protected final BaseRealm baseRealm;
        protected final OsMap osMap;
        private int pos = -1;

        protected abstract Map.Entry<K, V> getEntryInternal(int i);

        EntrySetIterator(OsMap osMap, BaseRealm baseRealm) {
            this.osMap = osMap;
            this.baseRealm = baseRealm;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return ((long) (this.pos + 1)) < this.osMap.size();
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            this.pos++;
            long size = this.osMap.size();
            int i = this.pos;
            if (i >= size) {
                throw new NoSuchElementException("Cannot access index " + this.pos + " when size is " + size + ". Remember to check hasNext() before using next().");
            }
            return getEntryInternal(i);
        }
    }

    private static class LongValueIterator<K> extends EntrySetIterator<K, Long> {
        LongValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Long> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (Long) entryForPrimitive.second);
        }
    }

    private static class ByteValueIterator<K> extends EntrySetIterator<K, Byte> {
        ByteValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Byte> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, Byte.valueOf(((Long) entryForPrimitive.second).byteValue()));
        }
    }

    private static class ShortValueIterator<K> extends EntrySetIterator<K, Short> {
        ShortValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Short> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, Short.valueOf(((Long) entryForPrimitive.second).shortValue()));
        }
    }

    private static class IntegerValueIterator<K> extends EntrySetIterator<K, Integer> {
        IntegerValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Integer> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, Integer.valueOf(((Long) entryForPrimitive.second).intValue()));
        }
    }

    private static class FloatValueIterator<K> extends EntrySetIterator<K, Float> {
        FloatValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Float> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (Float) entryForPrimitive.second);
        }
    }

    private static class DoubleValueIterator<K> extends EntrySetIterator<K, Double> {
        DoubleValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Double> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (Double) entryForPrimitive.second);
        }
    }

    private static class StringValueIterator<K> extends EntrySetIterator<K, String> {
        StringValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, String> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (String) entryForPrimitive.second);
        }
    }

    private static class BooleanValueIterator<K> extends EntrySetIterator<K, Boolean> {
        BooleanValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Boolean> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (Boolean) entryForPrimitive.second);
        }
    }

    private static class DateValueIterator<K> extends EntrySetIterator<K, Date> {
        DateValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Date> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (Date) entryForPrimitive.second);
        }
    }

    private static class Decimal128ValueIterator<K> extends EntrySetIterator<K, Decimal128> {
        Decimal128ValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, Decimal128> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (Decimal128) entryForPrimitive.second);
        }
    }

    private static class BinaryValueIterator<K> extends EntrySetIterator<K, byte[]> {
        BinaryValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, byte[]> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (byte[]) entryForPrimitive.second);
        }
    }

    private static class ObjectIdValueIterator<K> extends EntrySetIterator<K, ObjectId> {
        ObjectIdValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, ObjectId> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (ObjectId) entryForPrimitive.second);
        }
    }

    private static class UUIDValueIterator<K> extends EntrySetIterator<K, UUID> {
        UUIDValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, UUID> getEntryInternal(int i) {
            Pair<K, Object> entryForPrimitive = this.osMap.getEntryForPrimitive(i);
            if (entryForPrimitive.second == null) {
                return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, null);
            }
            return new AbstractMap.SimpleImmutableEntry(entryForPrimitive.first, (UUID) entryForPrimitive.second);
        }
    }

    private static class RealmModelValueIterator<K, V> extends EntrySetIterator<K, V> {
        private final TypeSelectorForMap<K, V> typeSelectorForMap;

        RealmModelValueIterator(OsMap osMap, BaseRealm baseRealm, TypeSelectorForMap<K, V> typeSelectorForMap) {
            super(osMap, baseRealm);
            this.typeSelectorForMap = typeSelectorForMap;
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, V> getEntryInternal(int i) {
            Pair<K, Long> keyObjRowPair = this.osMap.getKeyObjRowPair(i);
            K k = keyObjRowPair.first;
            long jLongValue = keyObjRowPair.second.longValue();
            if (jLongValue == -1) {
                return new AbstractMap.SimpleImmutableEntry(k, null);
            }
            return this.typeSelectorForMap.getModelEntry(this.baseRealm, jLongValue, k);
        }
    }

    private static class RealmAnyValueIterator<K> extends EntrySetIterator<K, RealmAny> {
        RealmAnyValueIterator(OsMap osMap, BaseRealm baseRealm) {
            super(osMap, baseRealm);
        }

        @Override // io.realm.RealmMapEntrySet.EntrySetIterator
        protected Map.Entry<K, RealmAny> getEntryInternal(int i) {
            Pair<K, NativeRealmAny> keyRealmAnyPair = this.osMap.getKeyRealmAnyPair(i);
            return new AbstractMap.SimpleImmutableEntry(keyRealmAnyPair.first, new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, keyRealmAnyPair.second)));
        }
    }
}

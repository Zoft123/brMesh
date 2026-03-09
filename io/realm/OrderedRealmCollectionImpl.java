package io.realm;

import io.realm.internal.InvalidRow;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.core.NativeRealmAny;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
abstract class OrderedRealmCollectionImpl<E> extends AbstractList<E> implements OrderedRealmCollection<E> {
    private static final String NOT_SUPPORTED_MESSAGE = "This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.";
    public final BaseRealm baseRealm;

    @Nullable
    final String className;

    @Nullable
    final Class<E> classSpec;
    final CollectionOperator<E> operator;
    final OsResults osResults;

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isManaged() {
        return true;
    }

    OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, Class<E> cls) {
        this(baseRealm, osResults, cls, null, getCollectionOperator(false, baseRealm, osResults, cls, null));
    }

    OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, Class<E> cls, CollectionOperator<E> collectionOperator) {
        this(baseRealm, osResults, cls, null, collectionOperator);
    }

    OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, String str) {
        this(baseRealm, osResults, null, str, getCollectionOperator(false, baseRealm, osResults, null, str));
    }

    OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, String str, CollectionOperator<E> collectionOperator) {
        this(baseRealm, osResults, null, str, collectionOperator);
    }

    private OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, @Nullable Class<E> cls, @Nullable String str, CollectionOperator<E> collectionOperator) {
        this.baseRealm = baseRealm;
        this.osResults = osResults;
        this.classSpec = cls;
        this.className = str;
        this.operator = collectionOperator;
    }

    Table getTable() {
        return this.osResults.getTable();
    }

    OsResults getOsResults() {
        return this.osResults;
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isValid() {
        return this.osResults.isValid();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(@Nullable Object obj) {
        if (!isLoaded() || ((obj instanceof RealmObjectProxy) && ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE)) {
            return false;
        }
        for (E e : this) {
            if ((e instanceof byte[]) && (obj instanceof byte[])) {
                if (Arrays.equals((byte[]) e, (byte[]) obj)) {
                    return true;
                }
            } else {
                if (e != null && e.equals(obj)) {
                    return true;
                }
                if (e == null && obj == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    @Nullable
    public E get(int i) {
        this.baseRealm.checkIfValid();
        return this.operator.get(i);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first() {
        return firstImpl(true, null);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first(@Nullable E e) {
        return firstImpl(false, e);
    }

    @Nullable
    private E firstImpl(boolean z, @Nullable E e) {
        return this.operator.firstImpl(z, e);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last() {
        return lastImpl(true, null);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last(@Nullable E e) {
        return lastImpl(false, e);
    }

    @Nullable
    private E lastImpl(boolean z, @Nullable E e) {
        return this.operator.lastImpl(z, e);
    }

    @Override // io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        this.baseRealm.checkIfValidAndInTransaction();
        this.osResults.delete(i);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        this.baseRealm.checkIfValid();
        if (size() <= 0) {
            return false;
        }
        this.osResults.clear();
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new RealmCollectionIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return new RealmCollectionListIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator(int i) {
        return new RealmCollectionListIterator(i);
    }

    private long getColumnKeyForSort(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Non-empty field name required.");
        }
        if (str.contains(".")) {
            throw new IllegalArgumentException("Aggregates on child object fields are not supported: " + str);
        }
        long columnKey = this.osResults.getTable().getColumnKey(str);
        if (columnKey >= 0) {
            return columnKey;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Field '%s' does not exist.", str));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        return createLoadedResults(this.osResults.sort(this.baseRealm.getSchema().getKeyPathMapping(), str, Sort.ASCENDING));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        return createLoadedResults(this.osResults.sort(this.baseRealm.getSchema().getKeyPathMapping(), str, sort));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        return createLoadedResults(this.osResults.sort(this.baseRealm.getSchema().getKeyPathMapping(), strArr, sortArr));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isLoaded()) {
            return 0;
        }
        long size = this.osResults.size();
        if (size > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) size;
    }

    @Override // io.realm.RealmCollection
    public Number min(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.MINIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Date minDate(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateDate(OsResults.Aggregate.MINIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Number max(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.MAXIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Date maxDate(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateDate(OsResults.Aggregate.MAXIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.SUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.AVERAGE, getColumnKeyForSort(str)).doubleValue();
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E remove(int i) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E set(int i, E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        this.baseRealm.checkIfValidAndInTransaction();
        return this.osResults.deleteLast();
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        this.baseRealm.checkIfValidAndInTransaction();
        return this.osResults.deleteFirst();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean add(E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public void add(int i, E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    private class RealmCollectionIterator extends OsResults.Iterator<E> {
        RealmCollectionIterator() {
            super(OrderedRealmCollectionImpl.this.osResults);
        }

        @Override // io.realm.internal.OsResults.Iterator
        protected E convertRowToObject(UncheckedRow uncheckedRow) {
            return OrderedRealmCollectionImpl.this.operator.convertRowToObject(uncheckedRow);
        }

        @Override // io.realm.internal.OsResults.Iterator
        protected E getInternal(int i, OsResults osResults) {
            return OrderedRealmCollectionImpl.this.operator.getFromResults(i, osResults);
        }
    }

    @Override // io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        if (this.className != null) {
            return new OrderedRealmCollectionSnapshot<>(this.baseRealm, this.osResults, this.className);
        }
        return new OrderedRealmCollectionSnapshot<>(this.baseRealm, this.osResults, this.classSpec);
    }

    public Realm getRealm() {
        this.baseRealm.checkIfValid();
        BaseRealm baseRealm = this.baseRealm;
        if (!(baseRealm instanceof Realm)) {
            throw new IllegalStateException("This method is only available for typed Realms");
        }
        return (Realm) baseRealm;
    }

    private class RealmCollectionListIterator extends OsResults.ListIterator<E> {
        RealmCollectionListIterator(int i) {
            super(OrderedRealmCollectionImpl.this.osResults, i);
        }

        @Override // io.realm.internal.OsResults.Iterator
        protected E convertRowToObject(UncheckedRow uncheckedRow) {
            return OrderedRealmCollectionImpl.this.operator.convertRowToObject(uncheckedRow);
        }

        @Override // io.realm.internal.OsResults.Iterator
        protected E getInternal(int i, OsResults osResults) {
            return OrderedRealmCollectionImpl.this.operator.getFromResults(i, osResults);
        }
    }

    RealmResults<E> createLoadedResults(OsResults osResults) {
        RealmResults<E> realmResults;
        if (this.className != null) {
            realmResults = new RealmResults<>(this.baseRealm, osResults, this.className);
        } else {
            realmResults = new RealmResults<>(this.baseRealm, osResults, this.classSpec);
        }
        realmResults.load();
        return realmResults;
    }

    protected static <T> CollectionOperator<T> getCollectionOperator(boolean z, BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
        if (z) {
            if (cls == Integer.class) {
                return new IntegerValueOperator(baseRealm, osResults, Integer.class, str);
            }
            if (cls == Short.class) {
                return new ShortValueOperator(baseRealm, osResults, Short.class, str);
            }
            if (cls == Byte.class) {
                return new ByteValueOperator(baseRealm, osResults, Byte.class, str);
            }
            if (cls == RealmAny.class) {
                return new RealmAnyValueOperator(baseRealm, osResults, RealmAny.class, str);
            }
            return new PrimitiveValueOperator(baseRealm, osResults, cls, str);
        }
        return new ModelCollectionOperator(baseRealm, osResults, cls, str);
    }

    static abstract class CollectionOperator<T> {
        protected final BaseRealm baseRealm;

        @Nullable
        protected final String className;

        @Nullable
        protected final Class<T> classSpec;
        protected final OsResults osResults;

        public abstract T convertRowToObject(UncheckedRow uncheckedRow);

        @Nullable
        public abstract T firstImpl(boolean z, @Nullable T t);

        public abstract T get(int i);

        public abstract T getFromResults(int i, OsResults osResults);

        @Nullable
        public abstract T lastImpl(boolean z, @Nullable T t);

        CollectionOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
            this.baseRealm = baseRealm;
            this.osResults = osResults;
            this.classSpec = cls;
            this.className = str;
        }

        protected T convertToObject(@Nullable UncheckedRow uncheckedRow, boolean z, @Nullable T t) {
            if (uncheckedRow != null) {
                return (T) this.baseRealm.get(this.classSpec, this.className, uncheckedRow);
            }
            if (z) {
                throw new IndexOutOfBoundsException("No results were found.");
            }
            return t;
        }
    }

    static class ModelCollectionOperator<T> extends CollectionOperator<T> {
        ModelCollectionOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T get(int i) {
            return (T) this.baseRealm.get(this.classSpec, this.className, this.osResults.getUncheckedRow(i));
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T firstImpl(boolean z, @Nullable T t) {
            return convertToObject(this.osResults.firstUncheckedRow(), z, t);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T lastImpl(boolean z, @Nullable T t) {
            return convertToObject(this.osResults.lastUncheckedRow(), z, t);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T convertRowToObject(UncheckedRow uncheckedRow) {
            return (T) this.baseRealm.get(this.classSpec, this.className, uncheckedRow);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T getFromResults(int i, OsResults osResults) {
            return convertRowToObject(osResults.getUncheckedRow(i));
        }
    }

    static class PrimitiveValueOperator<T> extends CollectionOperator<T> {
        PrimitiveValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T get(int i) {
            return (T) this.osResults.getValue(i);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T firstImpl(boolean z, @Nullable T t) {
            return this.osResults.size() != 0 ? (T) this.osResults.getValue(0) : t;
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T lastImpl(boolean z, @Nullable T t) {
            int size = (int) this.osResults.size();
            return size != 0 ? (T) this.osResults.getValue(size - 1) : t;
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T convertRowToObject(UncheckedRow uncheckedRow) {
            throw new UnsupportedOperationException("Method 'convertRowToObject' cannot be used on primitive Realm collections.");
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T getFromResults(int i, OsResults osResults) {
            return (T) osResults.getValue(i);
        }
    }

    static class IntegerValueOperator extends PrimitiveValueOperator<Integer> {
        IntegerValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<Integer> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Integer get(int i) {
            return Integer.valueOf(((Long) this.osResults.getValue(i)).intValue());
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Integer getFromResults(int i, OsResults osResults) {
            Long l = (Long) osResults.getValue(i);
            if (l == null) {
                return null;
            }
            return Integer.valueOf(l.intValue());
        }
    }

    static class ShortValueOperator extends PrimitiveValueOperator<Short> {
        ShortValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<Short> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Short get(int i) {
            return Short.valueOf(((Long) this.osResults.getValue(i)).shortValue());
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Short getFromResults(int i, OsResults osResults) {
            Long l = (Long) osResults.getValue(i);
            if (l == null) {
                return null;
            }
            return Short.valueOf(l.shortValue());
        }
    }

    static class ByteValueOperator extends PrimitiveValueOperator<Byte> {
        ByteValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<Byte> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Byte get(int i) {
            return Byte.valueOf(((Long) this.osResults.getValue(i)).byteValue());
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Byte getFromResults(int i, OsResults osResults) {
            Long l = (Long) osResults.getValue(i);
            if (l == null) {
                return null;
            }
            return Byte.valueOf(l.byteValue());
        }
    }

    static class RealmAnyValueOperator extends PrimitiveValueOperator<RealmAny> {
        RealmAnyValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<RealmAny> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public RealmAny get(int i) {
            return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, (NativeRealmAny) this.osResults.getValue(i)));
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public RealmAny getFromResults(int i, OsResults osResults) {
            return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, (NativeRealmAny) osResults.getValue(i)));
        }
    }
}

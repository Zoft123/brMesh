package io.realm;

import io.realm.internal.OsList;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
abstract class ManagedListOperator<T> {
    static final String INVALID_OBJECT_TYPE_MESSAGE = "Unacceptable value type. Acceptable: %1$s, actual: %2$s .";
    static final String NULL_OBJECTS_NOT_ALLOWED_MESSAGE = "RealmList does not accept null values.";

    @Nullable
    final Class<T> clazz;
    final OsList osList;
    final BaseRealm realm;

    protected abstract void appendValue(Object obj);

    protected abstract void checkValidValue(@Nullable Object obj);

    public abstract boolean forRealmModel();

    @Nullable
    public abstract T get(int i);

    protected abstract void insertValue(int i, Object obj);

    protected abstract void setValue(int i, Object obj);

    ManagedListOperator(BaseRealm baseRealm, OsList osList, @Nullable Class<T> cls) {
        this.realm = baseRealm;
        this.clazz = cls;
        this.osList = osList;
    }

    public final OsList getOsList() {
        return this.osList;
    }

    public final boolean isValid() {
        return this.osList.isValid();
    }

    public final int size() {
        long size = this.osList.size();
        if (size < 2147483647L) {
            return (int) size;
        }
        return Integer.MAX_VALUE;
    }

    public final boolean isEmpty() {
        return this.osList.isEmpty();
    }

    protected void checkInsertIndex(int i) {
        int size = size();
        if (i < 0 || size < i) {
            throw new IndexOutOfBoundsException("Invalid index " + i + ", size is " + this.osList.size());
        }
    }

    public final void append(@Nullable Object obj) {
        checkValidValue(obj);
        if (obj == null) {
            appendNull();
        } else {
            appendValue(obj);
        }
    }

    private void appendNull() {
        this.osList.addNull();
    }

    public final void insert(int i, @Nullable T t) {
        checkValidValue(t);
        if (t == null) {
            insertNull(i);
        } else {
            insertValue(i, t);
        }
    }

    protected void insertNull(int i) {
        this.osList.insertNull(i);
    }

    @Nullable
    public final T set(int i, @Nullable Object obj) {
        checkValidValue(obj);
        T t = get(i);
        if (obj == null) {
            setNull(i);
            return t;
        }
        setValue(i, obj);
        return t;
    }

    protected void setNull(int i) {
        this.osList.setNull(i);
    }

    final void move(int i, int i2) {
        this.osList.move(i, i2);
    }

    final void remove(int i) {
        this.osList.remove(i);
    }

    final void removeAll() {
        this.osList.removeAll();
    }

    final void delete(int i) {
        this.osList.delete(i);
    }

    final void deleteLast() {
        OsList osList = this.osList;
        osList.delete(osList.size() - 1);
    }

    final void deleteAll() {
        this.osList.deleteAll();
    }
}

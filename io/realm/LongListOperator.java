package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedListOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class LongListOperator<T> extends ManagedListOperator<T> {
    @Override // io.realm.ManagedListOperator
    public boolean forRealmModel() {
        return false;
    }

    LongListOperator(BaseRealm baseRealm, OsList osList, Class<T> cls) {
        super(baseRealm, osList, cls);
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [T, java.lang.Long] */
    @Override // io.realm.ManagedListOperator
    @Nullable
    public T get(int i) {
        ?? r4 = (T) ((Long) this.osList.getValue(i));
        if (r4 == 0) {
            return null;
        }
        if (this.clazz == Long.class) {
            return r4;
        }
        if (this.clazz == Integer.class) {
            return this.clazz.cast(Integer.valueOf(r4.intValue()));
        }
        if (this.clazz == Short.class) {
            return this.clazz.cast(Short.valueOf(r4.shortValue()));
        }
        if (this.clazz == Byte.class) {
            return this.clazz.cast(Byte.valueOf(r4.byteValue()));
        }
        throw new IllegalStateException("Unexpected element type: " + this.clazz.getName());
    }

    @Override // io.realm.ManagedListOperator
    protected void checkValidValue(@Nullable Object obj) {
        if (obj != null && !(obj instanceof Number)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.Long, java.lang.Integer, java.lang.Short, java.lang.Byte", obj.getClass().getName()));
        }
    }

    @Override // io.realm.ManagedListOperator
    public void appendValue(Object obj) {
        this.osList.addLong(((Number) obj).longValue());
    }

    @Override // io.realm.ManagedListOperator
    public void insertValue(int i, Object obj) {
        this.osList.insertLong(i, ((Number) obj).longValue());
    }

    @Override // io.realm.ManagedListOperator
    protected void setValue(int i, Object obj) {
        this.osList.setLong(i, ((Number) obj).longValue());
    }
}

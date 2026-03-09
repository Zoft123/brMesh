package io.realm;

import io.realm.RealmAny;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.core.NativeRealmAny;
import java.util.Collections;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class RealmModelOperator extends RealmAnyOperator {
    private final Class<? extends RealmModel> clazz;
    private final RealmModel value;

    private static <T extends RealmModel> T getRealmModel(BaseRealm baseRealm, Class<T> cls, NativeRealmAny nativeRealmAny) {
        return (T) baseRealm.get(cls, nativeRealmAny.getRealmModelRowKey(), false, Collections.EMPTY_LIST);
    }

    RealmModelOperator(RealmModel realmModel) {
        super(RealmAny.Type.OBJECT);
        this.value = realmModel;
        this.clazz = realmModel.getClass();
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T extends RealmModel> RealmModelOperator(BaseRealm baseRealm, NativeRealmAny nativeRealmAny, Class<T> cls) {
        super(RealmAny.Type.OBJECT, nativeRealmAny);
        this.clazz = cls;
        this.value = getRealmModel(baseRealm, cls, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        if (!(this.value instanceof RealmObjectProxy)) {
            throw new IllegalStateException("Native RealmAny instances only allow managed Realm objects or primitives");
        }
        return new NativeRealmAny((RealmObjectProxy) getValue(RealmObjectProxy.class));
    }

    @Override // io.realm.RealmAnyOperator
    <T> T getValue(Class<T> cls) {
        return cls.cast(this.value);
    }

    @Override // io.realm.RealmAnyOperator
    Class<?> getTypedClass() {
        return RealmObjectProxy.class.isAssignableFrom(this.clazz) ? this.clazz.getSuperclass() : this.clazz;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        RealmModel realmModel = this.value;
        RealmModel realmModel2 = ((RealmModelOperator) obj).value;
        return realmModel == null ? realmModel2 == null : realmModel.equals(realmModel2);
    }

    public String toString() {
        return this.value.toString();
    }

    @Override // io.realm.RealmAnyOperator
    public void checkValidObject(BaseRealm baseRealm) {
        if (!RealmObject.isValid(this.value) || !RealmObject.isManaged(this.value)) {
            throw new IllegalArgumentException("Realm object is not a valid managed object.");
        }
        if (((RealmObjectProxy) this.value).realmGet$proxyState().getRealm$realm() != baseRealm) {
            throw new IllegalArgumentException("Realm object belongs to a different Realm.");
        }
    }
}

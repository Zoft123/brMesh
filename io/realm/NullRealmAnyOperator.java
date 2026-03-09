package io.realm;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class NullRealmAnyOperator extends RealmAnyOperator {
    @Override // io.realm.RealmAnyOperator
    public <T> T getValue(Class<T> cls) {
        return null;
    }

    NullRealmAnyOperator() {
        super(RealmAny.Type.NULL);
    }

    NullRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(RealmAny.Type.NULL, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny();
    }

    public String toString() {
        return GlobalVariable.nullColor;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        return obj != null && getClass().equals(obj.getClass());
    }
}

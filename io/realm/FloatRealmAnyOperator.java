package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class FloatRealmAnyOperator extends PrimitiveRealmAnyOperator {
    FloatRealmAnyOperator(Float f) {
        super(f, RealmAny.Type.FLOAT);
    }

    FloatRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Float.valueOf(nativeRealmAny.asFloat()), RealmAny.Type.FLOAT, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Float) super.getValue(Float.class));
    }
}

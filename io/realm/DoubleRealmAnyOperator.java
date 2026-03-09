package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class DoubleRealmAnyOperator extends PrimitiveRealmAnyOperator {
    DoubleRealmAnyOperator(Double d) {
        super(d, RealmAny.Type.DOUBLE);
    }

    DoubleRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Double.valueOf(nativeRealmAny.asDouble()), RealmAny.Type.DOUBLE, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Double) super.getValue(Double.class));
    }
}

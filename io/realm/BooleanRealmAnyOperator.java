package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class BooleanRealmAnyOperator extends PrimitiveRealmAnyOperator {
    BooleanRealmAnyOperator(Boolean bool) {
        super(bool, RealmAny.Type.BOOLEAN);
    }

    BooleanRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Boolean.valueOf(nativeRealmAny.asBoolean()), RealmAny.Type.BOOLEAN, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Boolean) super.getValue(Boolean.class));
    }
}

package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class StringRealmAnyOperator extends PrimitiveRealmAnyOperator {
    StringRealmAnyOperator(String str) {
        super(str, RealmAny.Type.STRING);
    }

    StringRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asString(), RealmAny.Type.STRING, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((String) super.getValue(String.class));
    }
}

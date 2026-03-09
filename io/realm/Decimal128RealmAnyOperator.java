package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;
import org.bson.types.Decimal128;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class Decimal128RealmAnyOperator extends PrimitiveRealmAnyOperator {
    Decimal128RealmAnyOperator(Decimal128 decimal128) {
        super(decimal128, RealmAny.Type.DECIMAL128);
    }

    Decimal128RealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asDecimal128(), RealmAny.Type.DECIMAL128, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Decimal128) super.getValue(Decimal128.class));
    }
}

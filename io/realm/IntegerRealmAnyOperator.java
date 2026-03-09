package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class IntegerRealmAnyOperator extends PrimitiveRealmAnyOperator {
    IntegerRealmAnyOperator(Byte b) {
        super(b, RealmAny.Type.INTEGER);
    }

    IntegerRealmAnyOperator(Short sh) {
        super(sh, RealmAny.Type.INTEGER);
    }

    IntegerRealmAnyOperator(Integer num) {
        super(num, RealmAny.Type.INTEGER);
    }

    IntegerRealmAnyOperator(Long l) {
        super(l, RealmAny.Type.INTEGER);
    }

    IntegerRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Long.valueOf(nativeRealmAny.asLong()), RealmAny.Type.INTEGER, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Number) super.getValue(Number.class));
    }

    @Override // io.realm.PrimitiveRealmAnyOperator
    public boolean equals(Object obj) {
        return obj != null && getClass().equals(obj.getClass()) && ((Number) getValue(Number.class)).longValue() == ((Number) ((RealmAnyOperator) obj).getValue(Number.class)).longValue();
    }
}

package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;
import java.util.Arrays;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class BinaryRealmAnyOperator extends PrimitiveRealmAnyOperator {
    BinaryRealmAnyOperator(byte[] bArr) {
        super(bArr, RealmAny.Type.BINARY);
    }

    BinaryRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asBinary(), RealmAny.Type.BINARY, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((byte[]) super.getValue(byte[].class));
    }

    @Override // io.realm.PrimitiveRealmAnyOperator
    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return Arrays.equals((byte[]) getValue(byte[].class), (byte[]) ((RealmAnyOperator) obj).getValue(byte[].class));
    }
}

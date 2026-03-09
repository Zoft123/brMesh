package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;
import java.util.UUID;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class UUIDRealmAnyOperator extends PrimitiveRealmAnyOperator {
    UUIDRealmAnyOperator(UUID uuid) {
        super(uuid, RealmAny.Type.UUID);
    }

    UUIDRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asUUID(), RealmAny.Type.UUID, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((UUID) super.getValue(UUID.class));
    }
}

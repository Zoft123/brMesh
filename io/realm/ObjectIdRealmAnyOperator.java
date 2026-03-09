package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;
import org.bson.types.ObjectId;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class ObjectIdRealmAnyOperator extends PrimitiveRealmAnyOperator {
    ObjectIdRealmAnyOperator(ObjectId objectId) {
        super(objectId, RealmAny.Type.OBJECT_ID);
    }

    ObjectIdRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asObjectId(), RealmAny.Type.OBJECT_ID, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((ObjectId) super.getValue(ObjectId.class));
    }
}

package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class BinaryOperator extends SetValueOperator<byte[]> {
    BinaryOperator(BaseRealm baseRealm, OsSet osSet, Class<byte[]> cls) {
        super(baseRealm, osSet, cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.realm.SetValueOperator
    public boolean add(@Nullable byte[] bArr) {
        return this.osSet.add(bArr);
    }

    @Override // io.realm.SetValueOperator
    boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : (byte[]) obj);
    }

    @Override // io.realm.SetValueOperator
    boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((byte[]) obj);
    }

    @Override // io.realm.SetValueOperator
    boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean addAllInternal(Collection<? extends byte[]> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}

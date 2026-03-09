package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class FloatOperator extends SetValueOperator<Float> {
    FloatOperator(BaseRealm baseRealm, OsSet osSet, Class<Float> cls) {
        super(baseRealm, osSet, cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.realm.SetValueOperator
    public boolean add(@Nullable Float f) {
        return this.osSet.add(f);
    }

    @Override // io.realm.SetValueOperator
    boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : (Float) obj);
    }

    @Override // io.realm.SetValueOperator
    boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((Float) obj);
    }

    @Override // io.realm.SetValueOperator
    boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newFloatCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean addAllInternal(Collection<? extends Float> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newFloatCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newFloatCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newFloatCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}

package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class NumberOperator extends SetValueOperator<Number> {
    NumberOperator(BaseRealm baseRealm, OsSet osSet, Class<Number> cls) {
        super(baseRealm, osSet, cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.realm.SetValueOperator
    public boolean add(@Nullable Number number) {
        if (number == null) {
            return this.osSet.add((Long) null);
        }
        return this.osSet.add(Long.valueOf(number.longValue()));
    }

    @Override // io.realm.SetValueOperator
    boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : Long.valueOf(((Number) obj).longValue()));
    }

    @Override // io.realm.SetValueOperator
    boolean removeInternal(@Nullable Object obj) {
        if (obj == null) {
            return this.osSet.remove((Long) null);
        }
        return this.osSet.remove(Long.valueOf(((Number) obj).longValue()));
    }

    @Override // io.realm.SetValueOperator
    boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean addAllInternal(Collection<? extends Number> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}

package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class DateOperator extends SetValueOperator<Date> {
    DateOperator(BaseRealm baseRealm, OsSet osSet, Class<Date> cls) {
        super(baseRealm, osSet, cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.realm.SetValueOperator
    public boolean add(@Nullable Date date) {
        return this.osSet.add(date);
    }

    @Override // io.realm.SetValueOperator
    boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : (Date) obj);
    }

    @Override // io.realm.SetValueOperator
    boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((Date) obj);
    }

    @Override // io.realm.SetValueOperator
    boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean addAllInternal(Collection<? extends Date> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}

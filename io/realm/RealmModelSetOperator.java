package io.realm;

import io.realm.RealmModel;
import io.realm.internal.OsSet;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class RealmModelSetOperator<T extends RealmModel> extends SetValueOperator<T> {
    RealmModelSetOperator(BaseRealm baseRealm, OsSet osSet, Class<T> cls) {
        super(baseRealm, osSet, cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.realm.SetValueOperator
    public boolean add(T t) {
        return this.osSet.addRow(((RealmObjectProxy) getManagedObject(t)).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    private T getManagedObject(T t) {
        if (t != null) {
            return CollectionUtils.checkCanObjectBeCopied(this.baseRealm, t, this.valueClass.getName(), CollectionUtils.SET_TYPE) ? (T) CollectionUtils.copyToRealm(this.baseRealm, t) : t;
        }
        throw new NullPointerException("This set does not permit null values.");
    }

    private void checkValidObject(RealmModel realmModel) {
        if (realmModel == null) {
            throw new NullPointerException("This set does not permit null values.");
        }
        if (!RealmObject.isValid(realmModel) || !RealmObject.isManaged(realmModel)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy) realmModel).realmGet$proxyState().getRealm$realm() != this.baseRealm) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
    }

    @Override // io.realm.SetValueOperator
    boolean containsInternal(Object obj) {
        checkValidObject((RealmModel) obj);
        return this.osSet.containsRow(((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.SetValueOperator
    boolean removeInternal(Object obj) {
        checkValidObject((RealmModel) obj);
        return this.osSet.removeRow(((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.SetValueOperator
    boolean containsAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    boolean addAllInternal(Collection<? extends T> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(getManagedObject(it.next()));
        }
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(arrayList), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.SetValueOperator
    boolean removeAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.SetValueOperator
    boolean retainAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }

    private void checkValidCollection(Collection<? extends T> collection) {
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            checkValidObject(it.next());
        }
    }

    @Override // io.realm.SetValueOperator
    RealmQuery<T> where() {
        return new RealmQuery<>(this.baseRealm, this.osSet, this.valueClass);
    }
}

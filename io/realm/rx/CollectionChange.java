package io.realm.rx;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class CollectionChange<E extends OrderedRealmCollection> {
    private final OrderedCollectionChangeSet changeset;
    private final E collection;

    public CollectionChange(E e, @Nullable OrderedCollectionChangeSet orderedCollectionChangeSet) {
        this.collection = e;
        this.changeset = orderedCollectionChangeSet;
    }

    public E getCollection() {
        return this.collection;
    }

    @Nullable
    public OrderedCollectionChangeSet getChangeset() {
        return this.changeset;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            CollectionChange collectionChange = (CollectionChange) obj;
            if (!this.collection.equals(collectionChange.collection)) {
                return false;
            }
            OrderedCollectionChangeSet orderedCollectionChangeSet = this.changeset;
            OrderedCollectionChangeSet orderedCollectionChangeSet2 = collectionChange.changeset;
            if (orderedCollectionChangeSet != null) {
                return orderedCollectionChangeSet.equals(orderedCollectionChangeSet2);
            }
            if (orderedCollectionChangeSet2 == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.collection.hashCode() * 31;
        OrderedCollectionChangeSet orderedCollectionChangeSet = this.changeset;
        return iHashCode + (orderedCollectionChangeSet != null ? orderedCollectionChangeSet.hashCode() : 0);
    }
}

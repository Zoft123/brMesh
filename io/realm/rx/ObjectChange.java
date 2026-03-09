package io.realm.rx;

import io.realm.ObjectChangeSet;
import io.realm.RealmModel;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class ObjectChange<E extends RealmModel> {
    private final ObjectChangeSet changeset;
    private final E object;

    public ObjectChange(E e, @Nullable ObjectChangeSet objectChangeSet) {
        this.object = e;
        this.changeset = objectChangeSet;
    }

    public E getObject() {
        return this.object;
    }

    @Nullable
    public ObjectChangeSet getChangeset() {
        return this.changeset;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ObjectChange objectChange = (ObjectChange) obj;
            if (!this.object.equals(objectChange.object)) {
                return false;
            }
            ObjectChangeSet objectChangeSet = this.changeset;
            ObjectChangeSet objectChangeSet2 = objectChange.changeset;
            if (objectChangeSet != null) {
                return objectChangeSet.equals(objectChangeSet2);
            }
            if (objectChangeSet2 == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.object.hashCode() * 31;
        ObjectChangeSet objectChangeSet = this.changeset;
        return iHashCode + (objectChangeSet != null ? objectChangeSet.hashCode() : 0);
    }

    public String toString() {
        return "ObjectChange{object=" + this.object + ", changeset=" + this.changeset + '}';
    }
}

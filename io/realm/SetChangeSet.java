package io.realm;

import io.realm.internal.OsCollectionChangeSet;

/* JADX INFO: loaded from: classes4.dex */
public class SetChangeSet {
    private final OsCollectionChangeSet osCollectionChangeSet;

    public SetChangeSet(OsCollectionChangeSet osCollectionChangeSet) {
        this.osCollectionChangeSet = osCollectionChangeSet;
    }

    public int getNumberOfInsertions() {
        return this.osCollectionChangeSet.getInsertions().length;
    }

    public int getNumberOfDeletions() {
        return this.osCollectionChangeSet.getDeletions().length;
    }

    public boolean isEmpty() {
        return this.osCollectionChangeSet.getNativePtr() == 0;
    }
}

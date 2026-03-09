package io.realm;

import io.realm.internal.OsMapChangeSet;

/* JADX INFO: compiled from: MapChangeSet.java */
/* JADX INFO: loaded from: classes4.dex */
class StringMapChangeSet implements MapChangeSet<String> {
    private final OsMapChangeSet osMapChangeSet;

    StringMapChangeSet(long j) {
        this.osMapChangeSet = new OsMapChangeSet(j);
    }

    @Override // io.realm.MapChangeSet
    public String[] getDeletions() {
        return this.osMapChangeSet.getStringKeyDeletions();
    }

    @Override // io.realm.MapChangeSet
    public String[] getInsertions() {
        return this.osMapChangeSet.getStringKeyInsertions();
    }

    @Override // io.realm.MapChangeSet
    public String[] getChanges() {
        return this.osMapChangeSet.getStringKeyModifications();
    }

    @Override // io.realm.MapChangeSet
    public boolean isEmpty() {
        return this.osMapChangeSet.isEmpty();
    }
}

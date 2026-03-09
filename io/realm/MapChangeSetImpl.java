package io.realm;

/* JADX INFO: compiled from: MapChangeSet.java */
/* JADX INFO: loaded from: classes4.dex */
class MapChangeSetImpl<K> implements MapChangeSet<K> {
    private final MapChangeSet<K> delegate;

    MapChangeSetImpl(MapChangeSet<K> mapChangeSet) {
        this.delegate = mapChangeSet;
    }

    @Override // io.realm.MapChangeSet
    public K[] getDeletions() {
        return this.delegate.getDeletions();
    }

    @Override // io.realm.MapChangeSet
    public K[] getInsertions() {
        return this.delegate.getInsertions();
    }

    @Override // io.realm.MapChangeSet
    public K[] getChanges() {
        return this.delegate.getChanges();
    }

    @Override // io.realm.MapChangeSet
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
}

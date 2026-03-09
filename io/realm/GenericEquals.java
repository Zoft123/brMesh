package io.realm;

/* JADX INFO: compiled from: RealmMapEntrySet.java */
/* JADX INFO: loaded from: classes4.dex */
class GenericEquals<K, V> extends EqualsHelper<K, V> {
    GenericEquals() {
    }

    @Override // io.realm.EqualsHelper
    protected boolean compareInternal(V v, V v2) {
        if (v == null) {
            return v2 == null;
        }
        return v.equals(v2);
    }
}

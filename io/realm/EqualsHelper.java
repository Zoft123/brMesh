package io.realm;

import java.util.Map;

/* JADX INFO: compiled from: RealmMapEntrySet.java */
/* JADX INFO: loaded from: classes4.dex */
abstract class EqualsHelper<K, V> {
    protected abstract boolean compareInternal(V v, V v2);

    EqualsHelper() {
    }

    boolean equalsHelper(Map.Entry<K, V> entry, Map.Entry<K, V> entry2) {
        if (entry.getKey().equals(entry2.getKey())) {
            return compareInternal(entry.getValue(), entry2.getValue());
        }
        return false;
    }
}

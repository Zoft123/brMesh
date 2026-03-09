package io.realm;

import io.realm.internal.OsMap;
import io.realm.internal.OsResults;
import io.realm.internal.Table;
import io.realm.internal.util.Pair;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: TypeSelectorForMap.java */
/* JADX INFO: loaded from: classes4.dex */
class SelectorForMap<K, V> extends TypeSelectorForMap<K, V> {
    protected final Class<K> keyClass;
    protected final Class<V> valueClass;

    SelectorForMap(BaseRealm baseRealm, OsMap osMap, Class<K> cls, Class<V> cls2) {
        super(baseRealm, osMap);
        this.keyClass = cls;
        this.valueClass = cls2;
    }

    @Override // io.realm.TypeSelectorForMap
    public Set<K> keySet() {
        return new HashSet(produceResults(this.baseRealm, this.osMap.tableAndKeyPtrs(), true, this.keyClass));
    }

    @Override // io.realm.TypeSelectorForMap
    public Collection<V> getValues() {
        return produceResults(this.baseRealm, this.osMap.tableAndValuePtrs(), !CollectionUtils.isClassForRealmModel(this.valueClass), this.valueClass);
    }

    @Override // io.realm.TypeSelectorForMap
    public RealmDictionary<V> freeze(BaseRealm baseRealm) {
        return new RealmDictionary<>(baseRealm, this.osMap, this.valueClass);
    }

    private <T> RealmResults<T> produceResults(BaseRealm baseRealm, Pair<Table, Long> pair, boolean z, Class<T> cls) {
        return new RealmResults<>(baseRealm, OsResults.createFromMap(baseRealm.sharedRealm, pair.second.longValue()), cls, z);
    }

    @Override // io.realm.TypeSelectorForMap
    Class<V> getValueClass() {
        return this.valueClass;
    }

    @Override // io.realm.TypeSelectorForMap
    String getValueClassName() {
        return this.valueClass.getSimpleName();
    }
}

package io.realm;

import io.realm.internal.Freezable;
import io.realm.internal.ManageableObject;
import io.realm.internal.ObservableMap;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsMap;
import io.realm.internal.util.Pair;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
abstract class ManagedMapManager<K, V> implements Map<K, V>, ManageableObject, Freezable<RealmMap<K, V>>, ObservableMap {
    protected final BaseRealm baseRealm;
    protected final ObserverPairList<ObservableMap.MapObserverPair<K, V>> mapObserverPairs = new ObserverPairList<>();
    protected final MapValueOperator<K, V> mapValueOperator;
    protected final TypeSelectorForMap<K, V> typeSelectorForMap;

    abstract MapChangeSet<K> changeSetFactory(long j);

    abstract boolean containsKeyInternal(@Nullable Object obj);

    @Override // java.util.Map
    public abstract Set<Map.Entry<K, V>> entrySet();

    abstract RealmMap<K, V> freezeInternal(Pair<BaseRealm, OsMap> pair);

    @Override // io.realm.internal.ManageableObject
    public boolean isManaged() {
        return true;
    }

    @Override // java.util.Map
    public abstract V put(@Nullable K k, @Nullable V v);

    abstract void validateMap(Map<? extends K, ? extends V> map);

    ManagedMapManager(BaseRealm baseRealm, MapValueOperator<K, V> mapValueOperator, TypeSelectorForMap<K, V> typeSelectorForMap) {
        this.baseRealm = baseRealm;
        this.mapValueOperator = mapValueOperator;
        this.typeSelectorForMap = typeSelectorForMap;
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isValid() {
        return this.mapValueOperator.isValid();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.mapValueOperator.isFrozen();
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Null keys are not allowed.");
        }
        V v = this.mapValueOperator.get(obj);
        this.mapValueOperator.remove(obj);
        return v;
    }

    @Override // java.util.Map
    public int size() {
        return this.mapValueOperator.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mapValueOperator.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(@Nullable Object obj) {
        return containsKeyInternal(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(@Nullable Object obj) {
        return this.mapValueOperator.containsValue(obj);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        validateMap(map);
        this.mapValueOperator.putAll(map);
    }

    @Override // java.util.Map
    public void clear() {
        this.mapValueOperator.clear();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.mapValueOperator.keySet();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return this.mapValueOperator.values();
    }

    @Override // io.realm.internal.Freezable
    public RealmMap<K, V> freeze() {
        return freezeInternal(this.mapValueOperator.freeze());
    }

    @Override // io.realm.internal.ObservableMap
    public void notifyChangeListeners(long j) {
        MapChangeSetImpl mapChangeSetImpl = new MapChangeSetImpl(changeSetFactory(j));
        if (mapChangeSetImpl.isEmpty()) {
            return;
        }
        this.mapObserverPairs.foreach(new ObservableMap.Callback(mapChangeSetImpl));
    }

    void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, mapChangeListener, true);
        if (this.mapObserverPairs.isEmpty()) {
            this.mapValueOperator.startListening(this);
        }
        this.mapObserverPairs.add(new ObservableMap.MapObserverPair(realmMap, mapChangeListener));
    }

    void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
        addChangeListener(realmMap, new ObservableMap.RealmChangeListenerWrapper(realmChangeListener));
    }

    void removeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
        this.mapObserverPairs.remove(realmMap, mapChangeListener);
        if (this.mapObserverPairs.isEmpty()) {
            this.mapValueOperator.stopListening();
        }
    }

    void removeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
        removeListener(realmMap, new ObservableMap.RealmChangeListenerWrapper(realmChangeListener));
    }

    void removeAllChangeListeners() {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, null, false);
        this.mapObserverPairs.clear();
        this.mapValueOperator.stopListening();
    }

    boolean hasListeners() {
        return !this.mapObserverPairs.isEmpty();
    }

    boolean isNotNullItemTypeValid(@Nullable Object obj, Class<?> cls) {
        return obj == null || obj.getClass() == cls;
    }

    OsMap getOsMap() {
        return this.mapValueOperator.osMap;
    }

    String getClassName() {
        return this.typeSelectorForMap.getValueClassName();
    }

    Class<V> getValueClass() {
        return this.typeSelectorForMap.getValueClass();
    }
}

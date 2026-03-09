package io.realm;

import io.realm.RealmMapEntrySet;
import io.realm.internal.ObservableMap;
import io.realm.internal.OsMap;
import io.realm.internal.util.Pair;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes4.dex */
abstract class MapValueOperator<K, V> {
    protected final BaseRealm baseRealm;
    protected final RealmMapEntrySet.IteratorType iteratorType;
    protected final OsMap osMap;
    protected final TypeSelectorForMap<K, V> typeSelectorForMap;
    protected final Class<V> valueClass;

    abstract boolean containsValueInternal(@Nullable Object obj);

    abstract Set<Map.Entry<K, V>> entrySet();

    @Nullable
    abstract V get(K k);

    @Nullable
    abstract V put(K k, @Nullable V v);

    MapValueOperator(Class<V> cls, BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap, RealmMapEntrySet.IteratorType iteratorType) {
        this.valueClass = cls;
        this.baseRealm = baseRealm;
        this.osMap = osMap;
        this.typeSelectorForMap = typeSelectorForMap;
        this.iteratorType = iteratorType;
    }

    void remove(Object obj) {
        this.osMap.remove(obj);
    }

    int size() {
        return (int) this.osMap.size();
    }

    boolean isEmpty() {
        return this.osMap.size() == 0;
    }

    boolean containsKey(Object obj) {
        return this.osMap.containsKey(obj);
    }

    boolean containsValue(@Nullable Object obj) {
        if (obj != null && obj.getClass() != this.valueClass) {
            throw new ClassCastException("Only '" + this.valueClass.getSimpleName() + "'  values can be used with 'containsValue'.");
        }
        return containsValueInternal(obj);
    }

    boolean isValid() {
        if (this.baseRealm.isClosed()) {
            return false;
        }
        return this.osMap.isValid();
    }

    boolean isFrozen() {
        return this.baseRealm.isFrozen();
    }

    void clear() {
        this.osMap.clear();
    }

    void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    Set<K> keySet() {
        return this.typeSelectorForMap.keySet();
    }

    Collection<V> values() {
        return this.typeSelectorForMap.getValues();
    }

    Pair<BaseRealm, OsMap> freeze() {
        BaseRealm baseRealmFreeze = this.baseRealm.freeze();
        return new Pair<>(baseRealmFreeze, this.osMap.freeze(baseRealmFreeze.sharedRealm));
    }

    void startListening(ObservableMap observableMap) {
        this.osMap.startListening(observableMap);
    }

    void stopListening() {
        this.osMap.stopListening();
    }
}

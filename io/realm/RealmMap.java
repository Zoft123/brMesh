package io.realm;

import io.realm.internal.Freezable;
import io.realm.internal.ManageableObject;
import io.realm.internal.OsMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RealmMap<K, V> implements Map<K, V>, ManageableObject, Freezable<RealmMap<K, V>> {
    protected final MapStrategy<K, V> mapStrategy;

    protected RealmMap() {
        this.mapStrategy = new UnmanagedMapStrategy();
    }

    RealmMap(Map<K, V> map) {
        this();
        this.mapStrategy.putAll(map);
    }

    RealmMap(MapStrategy<K, V> mapStrategy) {
        this.mapStrategy = mapStrategy;
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isManaged() {
        return this.mapStrategy.isManaged();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isValid() {
        return this.mapStrategy.isValid();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.mapStrategy.isFrozen();
    }

    @Override // java.util.Map
    public int size() {
        return this.mapStrategy.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mapStrategy.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(@Nullable Object obj) {
        return this.mapStrategy.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(@Nullable Object obj) {
        return this.mapStrategy.containsValue(obj);
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return this.mapStrategy.get(obj);
    }

    @Override // java.util.Map
    public V put(K k, @Nullable V v) {
        return this.mapStrategy.put(k, v);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return this.mapStrategy.remove(obj);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        this.mapStrategy.putAll(map);
    }

    @Override // java.util.Map
    public void clear() {
        this.mapStrategy.clear();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.mapStrategy.keySet();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return this.mapStrategy.values();
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return this.mapStrategy.entrySet();
    }

    @Override // io.realm.internal.Freezable
    public RealmMap<K, V> freeze() {
        return this.mapStrategy.freeze();
    }

    public void addChangeListener(MapChangeListener<K, V> mapChangeListener) {
        this.mapStrategy.addChangeListener(this, mapChangeListener);
    }

    public void addChangeListener(RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
        this.mapStrategy.addChangeListener(this, realmChangeListener);
    }

    public void removeChangeListener(MapChangeListener<K, V> mapChangeListener) {
        this.mapStrategy.removeChangeListener(this, mapChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
        this.mapStrategy.removeChangeListener(this, realmChangeListener);
    }

    public void removeAllChangeListeners() {
        this.mapStrategy.removeAllChangeListeners();
    }

    boolean hasListeners() {
        return this.mapStrategy.hasListeners();
    }

    OsMap getOsMap() {
        return this.mapStrategy.getOsMap();
    }

    String getValueClassName() {
        return this.mapStrategy.getValueClassName();
    }

    Class<V> getValueClass() {
        return this.mapStrategy.getValueClass();
    }

    static abstract class MapStrategy<K, V> implements Map<K, V>, ManageableObject, Freezable<RealmMap<K, V>> {
        abstract void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener);

        abstract void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener);

        abstract OsMap getOsMap();

        abstract Class<V> getValueClass();

        abstract String getValueClassName();

        abstract boolean hasListeners();

        abstract V putInternal(K k, @Nullable V v);

        abstract void removeAllChangeListeners();

        abstract void removeChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener);

        abstract void removeChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener);

        MapStrategy() {
        }

        @Override // java.util.Map
        public V put(K k, V v) {
            checkValidKey(k);
            return putInternal(k, v);
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected void checkValidKey(K k) {
            if (k == 0) {
                throw new NullPointerException("Null keys are not allowed.");
            }
            if (k.getClass() == String.class) {
                String str = (String) k;
                if (str.contains(".") || str.contains("$")) {
                    throw new IllegalArgumentException("Keys containing dots ('.') or dollar signs ('$') are not allowed.");
                }
            }
        }
    }

    static class ManagedMapStrategy<K, V> extends MapStrategy<K, V> {
        private final ManagedMapManager<K, V> managedMapManager;

        ManagedMapStrategy(ManagedMapManager<K, V> managedMapManager) {
            this.managedMapManager = managedMapManager;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return this.managedMapManager.isManaged();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            return this.managedMapManager.isValid();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return this.managedMapManager.isFrozen();
        }

        @Override // java.util.Map
        public int size() {
            return this.managedMapManager.size();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.managedMapManager.isEmpty();
        }

        @Override // java.util.Map
        public boolean containsKey(@Nullable Object obj) {
            return this.managedMapManager.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(@Nullable Object obj) {
            return this.managedMapManager.containsValue(obj);
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.managedMapManager.get(obj);
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            return this.managedMapManager.remove(obj);
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.managedMapManager.putAll(map);
        }

        @Override // java.util.Map
        public void clear() {
            this.managedMapManager.clear();
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            return this.managedMapManager.keySet();
        }

        @Override // java.util.Map
        public Collection<V> values() {
            return this.managedMapManager.values();
        }

        @Override // java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return this.managedMapManager.entrySet();
        }

        @Override // io.realm.internal.Freezable
        public RealmMap<K, V> freeze() {
            return this.managedMapManager.freeze();
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected V putInternal(K k, V v) {
            return this.managedMapManager.put(k, v);
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            this.managedMapManager.addChangeListener(realmMap, mapChangeListener);
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            this.managedMapManager.addChangeListener(realmMap, realmChangeListener);
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void removeChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            this.managedMapManager.removeListener(realmMap, mapChangeListener);
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void removeChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            this.managedMapManager.removeListener(realmMap, realmChangeListener);
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void removeAllChangeListeners() {
            this.managedMapManager.removeAllChangeListeners();
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected boolean hasListeners() {
            return this.managedMapManager.hasListeners();
        }

        @Override // io.realm.RealmMap.MapStrategy
        OsMap getOsMap() {
            return this.managedMapManager.getOsMap();
        }

        @Override // io.realm.RealmMap.MapStrategy
        String getValueClassName() {
            return this.managedMapManager.getClassName();
        }

        @Override // io.realm.RealmMap.MapStrategy
        Class<V> getValueClass() {
            return this.managedMapManager.getValueClass();
        }
    }

    private static class UnmanagedMapStrategy<K, V> extends MapStrategy<K, V> {
        private final Map<K, V> unmanagedMap;

        @Override // io.realm.RealmMap.MapStrategy
        protected boolean hasListeners() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            return true;
        }

        private UnmanagedMapStrategy() {
            this.unmanagedMap = new HashMap();
        }

        @Override // java.util.Map
        public int size() {
            return this.unmanagedMap.size();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.unmanagedMap.isEmpty();
        }

        @Override // java.util.Map
        public boolean containsKey(@Nullable Object obj) {
            return this.unmanagedMap.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(@Nullable Object obj) {
            return this.unmanagedMap.containsValue(obj);
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.unmanagedMap.get(obj);
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            return this.unmanagedMap.remove(obj);
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.unmanagedMap.putAll(map);
        }

        @Override // java.util.Map
        public void clear() {
            this.unmanagedMap.clear();
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            return this.unmanagedMap.keySet();
        }

        @Override // java.util.Map
        public Collection<V> values() {
            return this.unmanagedMap.values();
        }

        @Override // java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return this.unmanagedMap.entrySet();
        }

        @Override // io.realm.internal.Freezable
        public RealmMap<K, V> freeze() {
            throw new UnsupportedOperationException("Unmanaged RealmMaps cannot be frozen.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected V putInternal(K k, @Nullable V v) {
            return this.unmanagedMap.put(k, v);
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            throw new UnsupportedOperationException("Unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            throw new UnsupportedOperationException("Unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void removeChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void removeChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        protected void removeAllChangeListeners() {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        OsMap getOsMap() {
            throw new UnsupportedOperationException("Unmanaged maps aren't represented in native code.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        String getValueClassName() {
            throw new UnsupportedOperationException("Unmanaged maps do not support retrieving the value class name.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        Class<V> getValueClass() {
            throw new UnsupportedOperationException("Unmanaged maps do not support retrieving the value class.");
        }
    }
}

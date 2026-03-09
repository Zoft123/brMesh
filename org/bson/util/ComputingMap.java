package org.bson.util;

import j$.util.Map;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
final class ComputingMap<K, V> implements Map<K, V>, Function<K, V>, j$.util.Map {
    private final Function<K, V> function;
    private final ConcurrentMap<K, V> map;

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        return Map.CC.$default$compute(this, obj, biFunction);
    }

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ Object computeIfAbsent(Object obj, java.util.function.Function function) {
        return Map.CC.$default$computeIfAbsent(this, obj, function);
    }

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        return Map.CC.$default$computeIfPresent(this, obj, biFunction);
    }

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ void forEach(BiConsumer biConsumer) {
        Map.CC.$default$forEach(this, biConsumer);
    }

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return Map.CC.$default$getOrDefault(this, obj, obj2);
    }

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return Map.CC.$default$merge(this, obj, obj2, biFunction);
    }

    @Override // java.util.Map, j$.util.Map
    public /* synthetic */ void replaceAll(BiFunction biFunction) {
        Map.CC.$default$replaceAll(this, biFunction);
    }

    public static <K, V> java.util.Map<K, V> create(Function<K, V> function) {
        return new ComputingMap(CopyOnWriteMap.newHashMap(), function);
    }

    ComputingMap(ConcurrentMap<K, V> concurrentMap, Function<K, V> function) {
        this.map = (ConcurrentMap) Assertions.notNull("map", concurrentMap);
        this.function = (Function) Assertions.notNull("function", function);
    }

    @Override // java.util.Map
    public V get(Object obj) {
        while (true) {
            V v = this.map.get(obj);
            if (v != null) {
                return v;
            }
            V vApply = this.function.apply(obj);
            if (vApply == null) {
                return null;
            }
            this.map.putIfAbsent(obj, vApply);
        }
    }

    @Override // org.bson.util.Function
    public V apply(K k) {
        return get(k);
    }

    @Override // java.util.Map, j$.util.Map
    public V putIfAbsent(K k, V v) {
        return this.map.putIfAbsent(k, v);
    }

    @Override // java.util.Map, j$.util.Map
    public boolean remove(Object obj, Object obj2) {
        return this.map.remove(obj, obj2);
    }

    @Override // java.util.Map, j$.util.Map
    public boolean replace(K k, V v, V v2) {
        return this.map.replace(k, v, v2);
    }

    @Override // java.util.Map, j$.util.Map
    public V replace(K k, V v) {
        return this.map.replace(k, v);
    }

    @Override // java.util.Map
    public int size() {
        return this.map.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        return this.map.put(k, v);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return this.map.remove(obj);
    }

    @Override // java.util.Map
    public void putAll(java.util.Map<? extends K, ? extends V> map) {
        this.map.putAll(map);
    }

    @Override // java.util.Map
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return this.map.values();
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.map.hashCode();
    }
}

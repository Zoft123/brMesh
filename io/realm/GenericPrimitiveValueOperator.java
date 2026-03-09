package io.realm;

import io.realm.RealmMapEntrySet;
import io.realm.internal.OsMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes4.dex */
class GenericPrimitiveValueOperator<K, V> extends MapValueOperator<K, V> {
    private final EqualsHelper<K, V> equalsHelper;

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    V processValue(Object obj) {
        return obj;
    }

    GenericPrimitiveValueOperator(Class<V> cls, BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap, RealmMapEntrySet.IteratorType iteratorType) {
        this(cls, baseRealm, osMap, typeSelectorForMap, iteratorType, new GenericEquals());
    }

    GenericPrimitiveValueOperator(Class<V> cls, BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap, RealmMapEntrySet.IteratorType iteratorType, EqualsHelper<K, V> equalsHelper) {
        super(cls, baseRealm, osMap, typeSelectorForMap, iteratorType);
        this.equalsHelper = equalsHelper;
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    V get(Object obj) {
        Object obj2 = this.osMap.get(obj);
        if (obj2 == null) {
            return null;
        }
        return processValue(obj2);
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    V put(K k, @Nullable V v) {
        V v2 = get(k);
        this.osMap.put(k, v);
        return v2;
    }

    @Override // io.realm.MapValueOperator
    Set<Map.Entry<K, V>> entrySet() {
        return new RealmMapEntrySet(this.baseRealm, this.osMap, this.iteratorType, this.equalsHelper, null);
    }

    @Override // io.realm.MapValueOperator
    boolean containsValueInternal(@Nullable Object obj) {
        return this.osMap.containsPrimitiveValue(obj);
    }
}

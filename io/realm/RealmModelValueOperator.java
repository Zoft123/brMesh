package io.realm;

import io.realm.RealmMapEntrySet;
import io.realm.internal.OsMap;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes4.dex */
class RealmModelValueOperator<K, V> extends MapValueOperator<K, V> {
    RealmModelValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap) {
        super(RealmModel.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.OBJECT);
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    V get(Object obj) {
        long modelRowKey = this.osMap.getModelRowKey(obj);
        if (modelRowKey == -1) {
            return null;
        }
        return this.typeSelectorForMap.getRealmModel(this.baseRealm, modelRowKey);
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    V put(K k, @Nullable V v) {
        return this.typeSelectorForMap.putRealmModel(this.baseRealm, this.osMap, k, v);
    }

    @Override // io.realm.MapValueOperator
    Set<Map.Entry<K, V>> entrySet() {
        return new RealmMapEntrySet(this.baseRealm, this.osMap, RealmMapEntrySet.IteratorType.OBJECT, this.typeSelectorForMap);
    }

    @Override // io.realm.MapValueOperator
    boolean containsValueInternal(@Nullable Object obj) {
        if (obj == null) {
            return this.osMap.containsPrimitiveValue(null);
        }
        if (obj instanceof RealmObjectProxy) {
            Row row$realm = ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm();
            return this.osMap.containsRealmModel(row$realm.getObjectKey(), row$realm.getTable().getNativePtr());
        }
        throw new IllegalArgumentException("Only managed models can be contained in this dictionary.");
    }

    @Override // io.realm.MapValueOperator
    boolean containsValue(@Nullable Object obj) {
        if (obj != null && !RealmModel.class.isAssignableFrom(obj.getClass())) {
            throw new ClassCastException("Only RealmModel values can be used with 'containsValue'.");
        }
        return containsValueInternal(obj);
    }
}

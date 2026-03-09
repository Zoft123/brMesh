package io.realm;

import io.realm.RealmModel;
import io.realm.internal.OsMap;
import io.realm.internal.RealmObjectProxy;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: TypeSelectorForMap.java */
/* JADX INFO: loaded from: classes4.dex */
class LinkSelectorForMap<K, V extends RealmModel> extends SelectorForMap<K, V> {
    LinkSelectorForMap(BaseRealm baseRealm, OsMap osMap, Class<K> cls, Class<V> cls2) {
        super(baseRealm, osMap, cls, cls2);
    }

    @Override // io.realm.TypeSelectorForMap
    public V getRealmModel(BaseRealm baseRealm, long j) {
        return (V) baseRealm.get(this.valueClass, (String) null, j);
    }

    @Override // io.realm.TypeSelectorForMap
    public V putRealmModel(BaseRealm baseRealm, OsMap osMap, K k, @Nullable V v) {
        long modelRowKey = osMap.getModelRowKey(k);
        if (v == null) {
            osMap.put(k, null);
        } else if (baseRealm.getSchema().getSchemaForClass((Class<? extends RealmModel>) this.valueClass).isEmbedded()) {
            CollectionUtils.updateEmbeddedObject((Realm) baseRealm, v, osMap.createAndPutEmbeddedObject(k));
        } else {
            if (CollectionUtils.checkCanObjectBeCopied(baseRealm, v, this.valueClass.getSimpleName(), CollectionUtils.DICTIONARY_TYPE)) {
                v = (V) CollectionUtils.copyToRealm(baseRealm, v);
            }
            osMap.putRow(k, ((RealmObjectProxy) v).realmGet$proxyState().getRow$realm().getObjectKey());
        }
        if (modelRowKey == -1) {
            return null;
        }
        return (V) baseRealm.get(this.valueClass, modelRowKey, false, new ArrayList());
    }

    @Override // io.realm.TypeSelectorForMap
    public Map.Entry<K, V> getModelEntry(BaseRealm baseRealm, long j, K k) {
        return new AbstractMap.SimpleImmutableEntry(k, baseRealm.get(this.valueClass, (String) null, j));
    }
}

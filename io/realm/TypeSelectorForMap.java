package io.realm;

import io.realm.internal.OsMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
abstract class TypeSelectorForMap<K, V> {
    protected final BaseRealm baseRealm;
    protected final OsMap osMap;

    abstract RealmDictionary<V> freeze(BaseRealm baseRealm);

    abstract Class<V> getValueClass();

    abstract String getValueClassName();

    abstract Collection<V> getValues();

    abstract Set<K> keySet();

    TypeSelectorForMap(BaseRealm baseRealm, OsMap osMap) {
        this.baseRealm = baseRealm;
        this.osMap = osMap;
    }

    protected V getRealmModel(BaseRealm baseRealm, long j) {
        throw new UnsupportedOperationException("Function 'getRealmModel' can only be called from 'LinkSelectorForMap' instances.");
    }

    protected V putRealmModel(BaseRealm baseRealm, OsMap osMap, K k, @Nullable V v) {
        throw new UnsupportedOperationException("Function 'putRealmModel' can only be called from 'LinkSelectorForMap' instances.");
    }

    protected Map.Entry<K, V> getModelEntry(BaseRealm baseRealm, long j, K k) {
        throw new UnsupportedOperationException("Function 'getModelEntry' can only be called from 'LinkSelectorForMap' instances.");
    }
}

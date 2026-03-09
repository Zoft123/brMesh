package io.realm;

import io.realm.RealmMapEntrySet;
import io.realm.internal.OsMap;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes4.dex */
class IntegerValueOperator<K> extends GenericPrimitiveValueOperator<K, Integer> {
    IntegerValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, Integer> typeSelectorForMap) {
        super(Integer.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.INTEGER);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.GenericPrimitiveValueOperator
    public Integer processValue(Object obj) {
        return Integer.valueOf(((Long) obj).intValue());
    }
}

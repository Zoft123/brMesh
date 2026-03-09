package io.realm;

import io.realm.RealmMapEntrySet;
import io.realm.internal.OsMap;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes4.dex */
class ByteValueOperator<K> extends GenericPrimitiveValueOperator<K, Byte> {
    ByteValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, Byte> typeSelectorForMap) {
        super(Byte.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.BYTE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.GenericPrimitiveValueOperator
    public Byte processValue(Object obj) {
        return Byte.valueOf(((Long) obj).byteValue());
    }
}

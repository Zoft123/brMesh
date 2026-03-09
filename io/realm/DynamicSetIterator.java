package io.realm;

import io.realm.internal.OsSet;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class DynamicSetIterator extends SetIterator<DynamicRealmObject> {
    private final String className;

    DynamicSetIterator(OsSet osSet, BaseRealm baseRealm, String str) {
        super(osSet, baseRealm);
        this.className = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.SetIterator
    public DynamicRealmObject getValueAtIndex(int i) {
        return (DynamicRealmObject) this.baseRealm.get(DynamicRealmObject.class, this.className, this.osSet.getRow(i));
    }
}

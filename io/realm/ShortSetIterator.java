package io.realm;

import io.realm.internal.OsSet;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class ShortSetIterator extends SetIterator<Short> {
    ShortSetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.realm.SetIterator
    public Short getValueAtIndex(int i) {
        Object valueAtIndex = this.osSet.getValueAtIndex(i);
        if (valueAtIndex == null) {
            return null;
        }
        return Short.valueOf(((Long) valueAtIndex).shortValue());
    }
}

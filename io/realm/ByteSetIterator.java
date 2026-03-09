package io.realm;

import io.realm.internal.OsSet;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class ByteSetIterator extends SetIterator<Byte> {
    ByteSetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.SetIterator
    public Byte getValueAtIndex(int i) {
        Object valueAtIndex = this.osSet.getValueAtIndex(i);
        if (valueAtIndex == null) {
            return null;
        }
        return Byte.valueOf(((Long) valueAtIndex).byteValue());
    }
}

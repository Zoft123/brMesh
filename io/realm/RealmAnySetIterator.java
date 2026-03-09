package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
class RealmAnySetIterator extends SetIterator<RealmAny> {
    RealmAnySetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.SetIterator
    public RealmAny getValueAtIndex(int i) {
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, new NativeRealmAny(this.osSet.getRealmAny(i))));
    }
}

package io.realm;

import java.util.Arrays;

/* JADX INFO: compiled from: RealmMapEntrySet.java */
/* JADX INFO: loaded from: classes4.dex */
class BinaryEquals<K> extends EqualsHelper<K, byte[]> {
    BinaryEquals() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.realm.EqualsHelper
    public boolean compareInternal(byte[] bArr, byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }
}

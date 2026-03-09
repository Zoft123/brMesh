package org.bson.codecs;

/* JADX INFO: loaded from: classes4.dex */
final class UuidCodecHelper {
    public static void reverseByteArray(byte[] bArr, int i, int i2) {
        for (int i3 = (i2 + i) - 1; i < i3; i3--) {
            byte b = bArr[i];
            bArr[i] = bArr[i3];
            bArr[i3] = b;
            i++;
        }
    }

    private UuidCodecHelper() {
    }
}

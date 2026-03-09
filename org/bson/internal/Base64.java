package org.bson.internal;

/* JADX INFO: loaded from: classes4.dex */
public final class Base64 {
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final byte PAD = 61;
    private static final int SIX_BIT_MASK = 63;
    private static final byte[] ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final int[] DECODE_TABLE = new int[128];

    static {
        int i = 0;
        while (true) {
            byte[] bArr = ENCODE_TABLE;
            if (i >= bArr.length) {
                return;
            }
            DECODE_TABLE[bArr[i]] = i;
            i++;
        }
    }

    public static byte[] decode(String str) {
        int length = ((str.length() * 3) / 4) - (str.endsWith("==") ? 2 : str.endsWith("=") ? 1 : 0);
        byte[] bArr = new byte[length];
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2 += 4) {
            int[] iArr = DECODE_TABLE;
            int i3 = iArr[str.charAt(i2)];
            int i4 = iArr[str.charAt(i2 + 1)];
            int i5 = i + 1;
            bArr[i] = (byte) (((i3 << 2) | (i4 >> 4)) & 255);
            if (i5 >= length) {
                break;
            }
            int i6 = iArr[str.charAt(i2 + 2)];
            int i7 = i + 2;
            bArr[i5] = (byte) (((i4 << 4) | (i6 >> 2)) & 255);
            if (i7 >= length) {
                break;
            }
            i += 3;
            bArr[i7] = (byte) ((iArr[str.charAt(i2 + 3)] | (i6 << 6)) & 255);
        }
        return bArr;
    }

    public static String encode(byte[] bArr) {
        byte[] bArr2 = new byte[((bArr.length / 3) * 4) + (bArr.length % 3 == 0 ? 0 : 4)];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 : bArr) {
            i = (i + 1) % 3;
            if (i4 < 0) {
                i4 += 256;
            }
            i3 = (i3 << 8) + i4;
            if (i == 0) {
                byte[] bArr3 = ENCODE_TABLE;
                bArr2[i2] = bArr3[(i3 >> 18) & 63];
                bArr2[i2 + 1] = bArr3[(i3 >> 12) & 63];
                int i5 = i2 + 3;
                bArr2[i2 + 2] = bArr3[(i3 >> 6) & 63];
                i2 += 4;
                bArr2[i5] = bArr3[i3 & 63];
            }
        }
        if (i == 1) {
            byte[] bArr4 = ENCODE_TABLE;
            bArr2[i2] = bArr4[(i3 >> 2) & 63];
            bArr2[i2 + 1] = bArr4[(i3 << 4) & 63];
            bArr2[i2 + 2] = 61;
            bArr2[i2 + 3] = 61;
        } else if (i == 2) {
            byte[] bArr5 = ENCODE_TABLE;
            bArr2[i2] = bArr5[(i3 >> 10) & 63];
            bArr2[i2 + 1] = bArr5[(i3 >> 4) & 63];
            bArr2[i2 + 2] = bArr5[(i3 << 2) & 63];
            bArr2[i2 + 3] = 61;
        }
        return byteArrayToString(bArr2);
    }

    private static String byteArrayToString(byte[] bArr) {
        return new String(bArr, 0, 0, bArr.length);
    }

    private Base64() {
    }
}

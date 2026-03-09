package org.bson.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class Bits {
    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i) throws IOException {
        readFully(inputStream, bArr, 0, i);
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        if (bArr.length < i2 + i) {
            throw new IllegalArgumentException("Buffer is too small");
        }
        while (i2 > 0) {
            int i3 = inputStream.read(bArr, i, i2);
            if (i3 < 0) {
                throw new EOFException();
            }
            i2 -= i3;
            i += i3;
        }
    }

    public static int readInt(InputStream inputStream) throws IOException {
        return readInt(inputStream, new byte[4]);
    }

    public static int readInt(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 4);
        return readInt(bArr);
    }

    public static int readInt(byte[] bArr) {
        return readInt(bArr, 0);
    }

    public static int readInt(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public static int readIntBE(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static long readLong(InputStream inputStream) throws IOException {
        return readLong(inputStream, new byte[8]);
    }

    public static long readLong(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 8);
        return readLong(bArr);
    }

    public static long readLong(byte[] bArr) {
        return readLong(bArr, 0);
    }

    public static long readLong(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }
}

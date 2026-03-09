package org.bson.internal;

import java.math.BigInteger;

/* JADX INFO: loaded from: classes4.dex */
public final class UnsignedLongs {
    private static final long MAX_VALUE = -1;
    private static final long[] MAX_VALUE_DIVS = new long[37];
    private static final int[] MAX_VALUE_MODS = new int[37];
    private static final int[] MAX_SAFE_DIGITS = new int[37];

    private static int compareLongs(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    public static int compare(long j, long j2) {
        return compareLongs(j - Long.MIN_VALUE, j2 - Long.MIN_VALUE);
    }

    public static String toString(long j) {
        if (j >= 0) {
            return Long.toString(j);
        }
        long j2 = (j >>> 1) / 5;
        return Long.toString(j2) + (j - (10 * j2));
    }

    public static long parse(String str) {
        if (str.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        int i = MAX_SAFE_DIGITS[10] - 1;
        long j = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            int iDigit = Character.digit(str.charAt(i2), 10);
            if (iDigit == -1) {
                throw new NumberFormatException(str);
            }
            if (i2 > i && overflowInParse(j, iDigit, 10)) {
                throw new NumberFormatException("Too large for unsigned long: " + str);
            }
            j = (j * ((long) 10)) + ((long) iDigit);
        }
        return j;
    }

    private static boolean overflowInParse(long j, int i, int i2) {
        if (j < 0) {
            return true;
        }
        long j2 = MAX_VALUE_DIVS[i2];
        if (j < j2) {
            return false;
        }
        return j > j2 || i > MAX_VALUE_MODS[i2];
    }

    private static long divide(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? 0L : 1L;
        }
        if (j >= 0) {
            return j / j2;
        }
        long j3 = ((j >>> 1) / j2) << 1;
        return j3 + ((long) (compare(j - (j3 * j2), j2) < 0 ? 0 : 1));
    }

    private static long remainder(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? j : j - j2;
        }
        if (j >= 0) {
            return j % j2;
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (compare(j3, j2) < 0) {
            j2 = 0;
        }
        return j3 - j2;
    }

    static {
        BigInteger bigInteger = new BigInteger("10000000000000000", 16);
        for (int i = 2; i <= 36; i++) {
            long j = i;
            MAX_VALUE_DIVS[i] = divide(-1L, j);
            MAX_VALUE_MODS[i] = (int) remainder(-1L, j);
            MAX_SAFE_DIGITS[i] = bigInteger.toString(i).length() - 1;
        }
    }

    private UnsignedLongs() {
    }
}

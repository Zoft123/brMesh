package com.alibaba.fastjson.util;

import java.lang.reflect.Array;
import java.math.BigInteger;
import okhttp3.internal.connection.RealConnection;

/* JADX INFO: loaded from: classes.dex */
public final class RyuDouble {
    private static final int[][] POW5_SPLIT = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 326, 4);
    private static final int[][] POW5_INV_SPLIT = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 291, 4);

    static {
        BigInteger bigIntegerSubtract = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        BigInteger bigIntegerSubtract2 = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        int i = 0;
        while (i < 326) {
            BigInteger bigIntegerPow = BigInteger.valueOf(5L).pow(i);
            int iBitLength = bigIntegerPow.bitLength();
            int i2 = i == 0 ? 1 : (int) (((((long) i) * 23219280) + 9999999) / 10000000);
            if (i2 != iBitLength) {
                throw new IllegalStateException(iBitLength + " != " + i2);
            }
            if (i < POW5_SPLIT.length) {
                for (int i3 = 0; i3 < 4; i3++) {
                    POW5_SPLIT[i][i3] = bigIntegerPow.shiftRight((iBitLength - 121) + ((3 - i3) * 31)).and(bigIntegerSubtract).intValue();
                }
            }
            if (i < POW5_INV_SPLIT.length) {
                BigInteger bigIntegerAdd = BigInteger.ONE.shiftLeft(iBitLength + 121).divide(bigIntegerPow).add(BigInteger.ONE);
                for (int i4 = 0; i4 < 4; i4++) {
                    if (i4 == 0) {
                        POW5_INV_SPLIT[i][i4] = bigIntegerAdd.shiftRight((3 - i4) * 31).intValue();
                    } else {
                        POW5_INV_SPLIT[i][i4] = bigIntegerAdd.shiftRight((3 - i4) * 31).and(bigIntegerSubtract2).intValue();
                    }
                }
            }
            i++;
        }
    }

    public static String toString(double d) {
        char[] cArr = new char[24];
        return new String(cArr, 0, toString(d, cArr, 0));
    }

    public static int toString(double d, char[] cArr, int i) {
        int i2;
        char c;
        char c2;
        boolean z;
        long j;
        int i3;
        boolean z2;
        long j2;
        long j3;
        boolean z3;
        int i4;
        long j4;
        boolean z4;
        long j5;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z5;
        int i9;
        int i10;
        int i11;
        int i12;
        if (Double.isNaN(d)) {
            cArr[i] = 'N';
            cArr[i + 1] = 'a';
            i12 = i + 3;
            cArr[i + 2] = 'N';
        } else {
            if (d == Double.POSITIVE_INFINITY) {
                cArr[i] = 'I';
                cArr[i + 1] = 'n';
                cArr[i + 2] = 'f';
                cArr[i + 3] = 'i';
                cArr[i + 4] = 'n';
                cArr[i + 5] = 'i';
                cArr[i + 6] = 't';
                cArr[i + 7] = 'y';
                return (i + 8) - i;
            }
            if (d == Double.NEGATIVE_INFINITY) {
                cArr[i] = '-';
                cArr[i + 1] = 'I';
                cArr[i + 2] = 'n';
                cArr[i + 3] = 'f';
                cArr[i + 4] = 'i';
                cArr[i + 5] = 'n';
                cArr[i + 6] = 'i';
                cArr[i + 7] = 't';
                i12 = i + 9;
                cArr[i + 8] = 'y';
            } else {
                long jDoubleToLongBits = Double.doubleToLongBits(d);
                if (jDoubleToLongBits != 0) {
                    if (jDoubleToLongBits == Long.MIN_VALUE) {
                        cArr[i] = '-';
                        cArr[i + 1] = '0';
                        cArr[i + 2] = '.';
                        i8 = i + 4;
                        cArr[i + 3] = '0';
                    } else {
                        int i13 = (int) ((jDoubleToLongBits >>> 52) & 2047);
                        long j6 = jDoubleToLongBits & 4503599627370495L;
                        if (i13 == 0) {
                            i2 = -1074;
                        } else {
                            i2 = i13 - 1075;
                            j6 |= 4503599627370496L;
                        }
                        boolean z6 = jDoubleToLongBits < 0;
                        boolean z7 = (j6 & 1) == 0;
                        long j7 = 4 * j6;
                        long j8 = j7 + 2;
                        int i14 = (j6 != 4503599627370496L || i13 <= 1) ? 1 : 0;
                        long j9 = (j7 - 1) - ((long) i14);
                        int i15 = i2 - 2;
                        int i16 = 3;
                        if (i15 >= 0) {
                            j = 10000000;
                            int iMax = Math.max(0, ((int) ((((long) i15) * 3010299) / 10000000)) - 1);
                            int i17 = (((-i15) + iMax) + ((iMax == 0 ? 1 : (int) (((((long) iMax) * 23219280) + 9999999) / 10000000)) + 121)) - 114;
                            if (i17 < 0) {
                                throw new IllegalArgumentException("" + i17);
                            }
                            int[] iArr = POW5_INV_SPLIT[iMax];
                            long j10 = j7 >>> 31;
                            long j11 = j7 & 2147483647L;
                            int i18 = iArr[0];
                            c = '.';
                            c2 = '0';
                            int i19 = iArr[1];
                            int i20 = iArr[2];
                            z = z7;
                            i3 = 10;
                            int i21 = iArr[3];
                            long j12 = ((((((((((((j11 * ((long) i21)) >>> 31) + (((long) i20) * j11)) + (j10 * ((long) i21))) >>> 31) + (((long) i19) * j11)) + (((long) i20) * j10)) >>> 31) + (((long) i18) * j11)) + (((long) i19) * j10)) >>> 21) + ((((long) i18) * j10) << 10)) >>> i17;
                            long j13 = j8 >>> 31;
                            long j14 = j8 & 2147483647L;
                            z2 = z6;
                            long j15 = ((((((((((((j14 * ((long) i21)) >>> 31) + (((long) i20) * j14)) + (j13 * ((long) i21))) >>> 31) + (((long) i19) * j14)) + (((long) i20) * j13)) >>> 31) + (((long) i18) * j14)) + (((long) i19) * j13)) >>> 21) + ((((long) i18) * j13) << 10)) >>> i17;
                            long j16 = j9 >>> 31;
                            long j17 = j9 & 2147483647L;
                            j3 = j15;
                            j4 = ((((((((((((j17 * ((long) i21)) >>> 31) + (((long) i20) * j17)) + (j16 * ((long) i21))) >>> 31) + (((long) i19) * j17)) + (((long) i20) * j16)) >>> 31) + (((long) i18) * j17)) + (((long) i19) * j16)) >>> 21) + ((((long) i18) * j16) << 10)) >>> i17;
                            i4 = iMax;
                            if (i4 <= 21) {
                                long j18 = j7 % 5;
                                if (j18 == 0) {
                                    if (j18 != 0) {
                                        i11 = 0;
                                    } else if (j7 % 25 != 0) {
                                        i11 = 1;
                                    } else if (j7 % 125 != 0) {
                                        i11 = 2;
                                    } else if (j7 % 625 != 0) {
                                        i11 = 3;
                                    } else {
                                        long j19 = j7 / 625;
                                        i11 = 4;
                                        while (j19 > 0 && j19 % 5 == 0) {
                                            j19 /= 5;
                                            i11++;
                                        }
                                    }
                                    z5 = i11 >= i4;
                                    z4 = false;
                                    z3 = z5;
                                    j2 = j12;
                                } else if (z) {
                                    if (j9 % 5 != 0) {
                                        i10 = 0;
                                    } else if (j9 % 25 != 0) {
                                        i10 = 1;
                                    } else if (j9 % 125 != 0) {
                                        i10 = 2;
                                    } else if (j9 % 625 != 0) {
                                        i10 = 3;
                                    } else {
                                        long j20 = j9 / 625;
                                        i10 = 4;
                                        while (j20 > 0 && j20 % 5 == 0) {
                                            j20 /= 5;
                                            i10++;
                                        }
                                    }
                                    z4 = i10 >= i4;
                                    z5 = false;
                                    z3 = z5;
                                    j2 = j12;
                                } else {
                                    if (j8 % 5 != 0) {
                                        i9 = 0;
                                    } else if (j8 % 25 != 0) {
                                        i9 = 1;
                                    } else if (j8 % 125 != 0) {
                                        i9 = 2;
                                    } else if (j8 % 625 != 0) {
                                        i9 = 3;
                                    } else {
                                        long j21 = j8 / 625;
                                        i9 = 4;
                                        while (j21 > 0 && j21 % 5 == 0) {
                                            j21 /= 5;
                                            i9++;
                                        }
                                    }
                                    if (i9 >= i4) {
                                        j3--;
                                    }
                                }
                                z4 = false;
                                z3 = z5;
                                j2 = j12;
                            } else {
                                z4 = false;
                                z3 = z5;
                                j2 = j12;
                            }
                        } else {
                            c = '.';
                            c2 = '0';
                            z = z7;
                            j = 10000000;
                            i3 = 10;
                            z2 = z6;
                            int iMax2 = Math.max(0, ((int) ((((long) r0) * 6989700) / 10000000)) - 1);
                            int i22 = (-i15) - iMax2;
                            int i23 = (iMax2 - ((i22 == 0 ? 1 : (int) (((((long) i22) * 23219280) + 9999999) / 10000000)) - 121)) - 114;
                            if (i23 < 0) {
                                throw new IllegalArgumentException("" + i23);
                            }
                            int[] iArr2 = POW5_SPLIT[i22];
                            long j22 = j7 >>> 31;
                            long j23 = j7 & 2147483647L;
                            int i24 = iArr2[0];
                            int i25 = iArr2[1];
                            int i26 = iArr2[2];
                            int i27 = iArr2[3];
                            long j24 = ((((((((((((j23 * ((long) i27)) >>> 31) + (((long) i26) * j23)) + (((long) i27) * j22)) >>> 31) + (((long) i25) * j23)) + (((long) i26) * j22)) >>> 31) + (((long) i24) * j23)) + (((long) i25) * j22)) >>> 21) + ((((long) i24) * j22) << 10)) >>> i23;
                            long j25 = j8 >>> 31;
                            long j26 = j8 & 2147483647L;
                            j2 = j24;
                            j3 = ((((((((((((j26 * ((long) i27)) >>> 31) + (((long) i26) * j26)) + (j25 * ((long) i27))) >>> 31) + (((long) i25) * j26)) + (((long) i26) * j25)) >>> 31) + (((long) i24) * j26)) + (((long) i25) * j25)) >>> 21) + ((((long) i24) * j25) << 10)) >>> i23;
                            long j27 = j9 >>> 31;
                            long j28 = j9 & 2147483647L;
                            long j29 = ((((((((((((j28 * ((long) i27)) >>> 31) + (((long) i26) * j28)) + (((long) i27) * j27)) >>> 31) + (((long) i25) * j28)) + (((long) i26) * j27)) >>> 31) + (((long) i24) * j28)) + (((long) i25) * j27)) >>> 21) + ((((long) i24) * j27) << 10)) >>> i23;
                            int i28 = iMax2 + i15;
                            z3 = true;
                            if (iMax2 <= 1) {
                                if (z) {
                                    boolean z8 = i14 == 1;
                                    i4 = i28;
                                    j4 = j29;
                                    z4 = z8;
                                } else {
                                    j3--;
                                    i4 = i28;
                                }
                            } else if (iMax2 < 63) {
                                i4 = i28;
                                z3 = (((1 << (iMax2 - 1)) - 1) & j7) == 0;
                            } else {
                                i4 = i28;
                                j4 = j29;
                                z3 = false;
                                z4 = false;
                            }
                            j4 = j29;
                            z4 = false;
                        }
                        if (j3 >= 1000000000000000000L) {
                            i16 = 19;
                        } else if (j3 >= 100000000000000000L) {
                            i16 = 18;
                        } else if (j3 >= 10000000000000000L) {
                            i16 = 17;
                        } else if (j3 >= 1000000000000000L) {
                            i16 = 16;
                        } else if (j3 >= 100000000000000L) {
                            i16 = 15;
                        } else if (j3 >= 10000000000000L) {
                            i16 = 14;
                        } else if (j3 >= 1000000000000L) {
                            i16 = 13;
                        } else if (j3 >= 100000000000L) {
                            i16 = 12;
                        } else if (j3 >= RealConnection.IDLE_CONNECTION_HEALTHY_NS) {
                            i16 = 11;
                        } else if (j3 >= 1000000000) {
                            i16 = i3;
                        } else if (j3 >= 100000000) {
                            i16 = 9;
                        } else if (j3 >= j) {
                            i16 = 8;
                        } else if (j3 >= 1000000) {
                            i16 = 7;
                        } else if (j3 >= 100000) {
                            i16 = 6;
                        } else if (j3 >= 10000) {
                            i16 = 5;
                        } else if (j3 >= 1000) {
                            i16 = 4;
                        } else if (j3 < 100) {
                            i16 = j3 >= 10 ? 2 : 1;
                        }
                        int i29 = i4 + i16;
                        int i30 = i29 - 1;
                        boolean z9 = i30 < -3 || i30 >= 7;
                        if (z4 || z3) {
                            int i31 = 0;
                            int i32 = 0;
                            while (true) {
                                long j30 = j3 / 10;
                                long j31 = j4 / 10;
                                if (j30 <= j31 || (j3 < 100 && z9)) {
                                    break;
                                }
                                z4 &= j4 % 10 == 0;
                                z3 &= i31 == 0;
                                i31 = (int) (j2 % 10);
                                j2 /= 10;
                                i32++;
                                j3 = j30;
                                j4 = j31;
                            }
                            if (z4 && z) {
                                while (j4 % 10 == 0 && (j3 >= 100 || !z9)) {
                                    z3 &= i31 == 0;
                                    i31 = (int) (j2 % 10);
                                    j3 /= 10;
                                    j2 /= 10;
                                    j4 /= 10;
                                    i32++;
                                }
                            }
                            if (z3 && i31 == 5 && j2 % 2 == 0) {
                                i31 = 4;
                            }
                            j5 = j2 + ((long) (((j2 != j4 || (z4 && z)) && i31 < 5) ? 0 : 1));
                            i5 = i32;
                        } else {
                            i5 = 0;
                            int i33 = 0;
                            while (true) {
                                long j32 = j3 / 10;
                                long j33 = j4 / 10;
                                if (j32 <= j33 || (j3 < 100 && z9)) {
                                    break;
                                }
                                i33 = (int) (j2 % 10);
                                j2 /= 10;
                                i5++;
                                j3 = j32;
                                j4 = j33;
                            }
                            j5 = j2 + ((long) ((j2 == j4 || i33 >= 5) ? 1 : 0));
                        }
                        int i34 = i16 - i5;
                        if (z2) {
                            i6 = i + 1;
                            cArr[i] = '-';
                        } else {
                            i6 = i;
                        }
                        if (!z9) {
                            if (i30 < 0) {
                                int i35 = i6 + 1;
                                cArr[i6] = c2;
                                int i36 = i6 + 2;
                                cArr[i35] = c;
                                int i37 = -1;
                                while (i37 > i30) {
                                    cArr[i36] = c2;
                                    i37--;
                                    i36++;
                                }
                                i7 = i36;
                                for (int i38 = 0; i38 < i34; i38++) {
                                    cArr[((i36 + i34) - i38) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                    i7++;
                                }
                            } else if (i29 >= i34) {
                                for (int i39 = 0; i39 < i34; i39++) {
                                    cArr[((i6 + i34) - i39) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                }
                                int i40 = i6 + i34;
                                while (i34 < i29) {
                                    cArr[i40] = c2;
                                    i34++;
                                    i40++;
                                }
                                cArr[i40] = c;
                                i7 = i40 + 2;
                                cArr[i40 + 1] = c2;
                            } else {
                                int i41 = i6 + 1;
                                for (int i42 = 0; i42 < i34; i42++) {
                                    if ((i34 - i42) - 1 == i30) {
                                        cArr[((i41 + i34) - i42) - 1] = c;
                                        i41--;
                                    }
                                    cArr[((i41 + i34) - i42) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                }
                                i7 = i6 + i34 + 1;
                            }
                            return i7 - i;
                        }
                        for (int i43 = 0; i43 < i34 - 1; i43++) {
                            int i44 = (int) (j5 % 10);
                            j5 /= 10;
                            cArr[(i6 + i34) - i43] = (char) (i44 + 48);
                        }
                        cArr[i6] = (char) ((j5 % 10) + 48);
                        cArr[i6 + 1] = c;
                        int i45 = i6 + i34 + 1;
                        if (i34 == 1) {
                            cArr[i45] = c2;
                            i45++;
                        }
                        int i46 = i45 + 1;
                        cArr[i45] = 'E';
                        if (i30 < 0) {
                            cArr[i46] = '-';
                            i30 = -i30;
                            i46 = i45 + 2;
                        }
                        if (i30 >= 100) {
                            int i47 = i46 + 1;
                            cArr[i46] = (char) ((i30 / 100) + 48);
                            i30 %= 100;
                            i46 += 2;
                            cArr[i47] = (char) ((i30 / 10) + 48);
                        } else if (i30 >= i3) {
                            cArr[i46] = (char) ((i30 / 10) + 48);
                            i46++;
                        }
                        i8 = i46 + 1;
                        cArr[i46] = (char) ((i30 % 10) + 48);
                    }
                    return i8 - i;
                }
                cArr[i] = '0';
                cArr[i + 1] = '.';
                i12 = i + 3;
                cArr[i + 2] = '0';
            }
        }
        return i12 - i;
    }
}

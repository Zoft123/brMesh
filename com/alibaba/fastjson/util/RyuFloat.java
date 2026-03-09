package com.alibaba.fastjson.util;

import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;

/* JADX INFO: loaded from: classes.dex */
public final class RyuFloat {
    private static final int[][] POW5_SPLIT = {new int[]{536870912, 0}, new int[]{671088640, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{1000000000, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, BasicMeasure.EXACTLY}, new int[]{762939453, 268435456}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};
    private static final int[][] POW5_INV_SPLIT = {new int[]{268435456, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};

    public static String toString(float f) {
        char[] cArr = new char[15];
        return new String(cArr, 0, toString(f, cArr, 0));
    }

    public static int toString(float f, char[] cArr, int i) {
        int i2;
        char c;
        char c2;
        int i3;
        int i4;
        boolean z;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z2;
        boolean z3;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        char c3;
        long j;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        if (!Float.isNaN(f)) {
            if (f == Float.POSITIVE_INFINITY) {
                cArr[i] = 'I';
                cArr[i + 1] = 'n';
                cArr[i + 2] = 'f';
                cArr[i + 3] = 'i';
                cArr[i + 4] = 'n';
                cArr[i + 5] = 'i';
                cArr[i + 6] = 't';
                i18 = i + 8;
                cArr[i + 7] = 'y';
            } else if (f == Float.NEGATIVE_INFINITY) {
                cArr[i] = '-';
                cArr[i + 1] = 'I';
                cArr[i + 2] = 'n';
                cArr[i + 3] = 'f';
                cArr[i + 4] = 'i';
                cArr[i + 5] = 'n';
                cArr[i + 6] = 'i';
                cArr[i + 7] = 't';
                i19 = i + 9;
                cArr[i + 8] = 'y';
            } else {
                int iFloatToIntBits = Float.floatToIntBits(f);
                if (iFloatToIntBits == 0) {
                    cArr[i] = '0';
                    cArr[i + 1] = '.';
                    i19 = i + 3;
                    cArr[i + 2] = '0';
                } else if (iFloatToIntBits == Integer.MIN_VALUE) {
                    cArr[i] = '-';
                    cArr[i + 1] = '0';
                    cArr[i + 2] = '.';
                    i18 = i + 4;
                    cArr[i + 3] = '0';
                } else {
                    int i20 = (iFloatToIntBits >> 23) & 255;
                    int i21 = 8388607 & iFloatToIntBits;
                    if (i20 == 0) {
                        i2 = -149;
                    } else {
                        i2 = i20 - 150;
                        i21 |= 8388608;
                    }
                    boolean z4 = iFloatToIntBits < 0;
                    boolean z5 = (i21 & 1) == 0;
                    int i22 = i21 * 4;
                    int i23 = i22 + 2;
                    int i24 = i22 - ((((long) i21) != 8388608 || i20 <= 1) ? 2 : 1);
                    int i25 = i2 - 2;
                    if (i25 >= 0) {
                        c2 = '-';
                        i8 = (int) ((((long) i25) * 3010299) / 10000000);
                        if (i8 == 0) {
                            i14 = 1;
                            c3 = 2;
                            j = 9999999;
                        } else {
                            c3 = 2;
                            j = 9999999;
                            i14 = (int) (((((long) i8) * 23219280) + 9999999) / 10000000);
                        }
                        int i26 = (-i25) + i8;
                        int[][] iArr = POW5_INV_SPLIT;
                        int[] iArr2 = iArr[i8];
                        c = '0';
                        long j2 = iArr2[0];
                        long j3 = iArr2[1];
                        i4 = 10;
                        long j4 = i22;
                        int i27 = ((i14 + 58) + i26) - 31;
                        i3 = 0;
                        int i28 = (int) (((j4 * j2) + ((j4 * j3) >> 31)) >> i27);
                        z = z4;
                        long j5 = i23;
                        int i29 = (int) (((j5 * j2) + ((j5 * j3) >> 31)) >> i27);
                        long j6 = i24;
                        int i30 = (int) (((j2 * j6) + ((j6 * j3) >> 31)) >> i27);
                        if (i8 == 0 || (i29 - 1) / 10 > i30 / 10) {
                            i15 = i29;
                            i16 = i30;
                            i17 = 0;
                        } else {
                            int i31 = i8 - 1;
                            int i32 = (i26 - 1) + (i31 == 0 ? 1 : (int) (((((long) i31) * 23219280) + j) / 10000000)) + 58;
                            int[] iArr3 = iArr[i31];
                            i15 = i29;
                            i16 = i30;
                            i17 = (int) ((((((long) iArr3[0]) * j4) + ((j4 * ((long) iArr3[1])) >> 31)) >> (i32 - 31)) % 10);
                        }
                        int i33 = 0;
                        while (i23 > 0 && i23 % 5 == 0) {
                            i23 /= 5;
                            i33++;
                        }
                        int i34 = 0;
                        while (i22 > 0 && i22 % 5 == 0) {
                            i22 /= 5;
                            i34++;
                        }
                        int i35 = 0;
                        while (i24 > 0 && i24 % 5 == 0) {
                            i24 /= 5;
                            i35++;
                        }
                        boolean z6 = i33 >= i8;
                        boolean z7 = i34 >= i8;
                        i9 = i35 >= i8 ? 1 : 0;
                        z3 = z7;
                        i6 = i16;
                        i5 = i28;
                        z2 = z6;
                        i10 = i17;
                        i11 = i15;
                    } else {
                        c = '0';
                        c2 = '-';
                        i3 = 0;
                        i4 = 10;
                        z = z4;
                        int i36 = -i25;
                        int i37 = (int) ((((long) i36) * 6989700) / 10000000);
                        int i38 = i36 - i37;
                        int i39 = i38 == 0 ? 1 : (int) (((((long) i38) * 23219280) + 9999999) / 10000000);
                        int[][] iArr4 = POW5_SPLIT;
                        int[] iArr5 = iArr4[i38];
                        long j7 = iArr5[0];
                        long j8 = iArr5[1];
                        int i40 = (i37 - (i39 - 61)) - 31;
                        long j9 = i22;
                        i5 = (int) (((j9 * j7) + ((j9 * j8) >> 31)) >> i40);
                        long j10 = i23;
                        int i41 = (int) (((j10 * j7) + ((j10 * j8) >> 31)) >> i40);
                        long j11 = i24;
                        i6 = (int) (((j7 * j11) + ((j11 * j8) >> 31)) >> i40);
                        if (i37 == 0 || (i41 - 1) / 10 > i6 / 10) {
                            i7 = 0;
                        } else {
                            int i42 = i38 + 1;
                            int i43 = (i37 - 1) - ((i42 == 0 ? 1 : (int) (((((long) i42) * 23219280) + 9999999) / 10000000)) - 61);
                            int[] iArr6 = iArr4[i42];
                            i7 = (int) ((((((long) iArr6[0]) * j9) + ((((long) iArr6[1]) * j9) >> 31)) >> (i43 - 31)) % 10);
                        }
                        i8 = i37 + i25;
                        z2 = 1 >= i37;
                        z3 = i37 < 23 && (((1 << (i37 + (-1))) - 1) & i22) == 0;
                        i9 = (i24 % 2 == 1 ? 0 : 1) >= i37 ? 1 : 0;
                        i10 = i7;
                        i11 = i41;
                    }
                    int i44 = 1000000000;
                    int i45 = i4;
                    while (i45 > 0 && i11 < i44) {
                        i44 /= 10;
                        i45--;
                    }
                    int i46 = i8 + i45;
                    int i47 = i46 - 1;
                    int i48 = (i47 < -3 || i47 >= 7) ? 1 : i3;
                    if (z2 && !z5) {
                        i11--;
                    }
                    int i49 = i3;
                    while (true) {
                        int i50 = i11 / 10;
                        int i51 = i6 / 10;
                        if (i50 <= i51 || (i11 < 100 && i48 != 0)) {
                            break;
                        }
                        i9 &= i6 % 10 == 0 ? 1 : i3;
                        i10 = i5 % 10;
                        i5 /= 10;
                        i49++;
                        i11 = i50;
                        i6 = i51;
                    }
                    if (i9 != 0 && z5) {
                        while (i6 % 10 == 0 && (i11 >= 100 || i48 == 0)) {
                            i11 /= 10;
                            i10 = i5 % 10;
                            i5 /= 10;
                            i6 /= 10;
                            i49++;
                        }
                    }
                    int i52 = i5;
                    if (z3 && i10 == 5 && i52 % 2 == 0) {
                        i10 = 4;
                    }
                    int i53 = i52 + (((i52 != i6 || (i9 != 0 && z5)) && i10 < 5) ? i3 : 1);
                    int i54 = i45 - i49;
                    if (z) {
                        i12 = i + 1;
                        cArr[i] = c2;
                    } else {
                        i12 = i;
                    }
                    if (i48 != 0) {
                        for (int i55 = i3; i55 < i54 - 1; i55++) {
                            int i56 = i53 % 10;
                            i53 /= 10;
                            cArr[(i12 + i54) - i55] = (char) (i56 + 48);
                        }
                        cArr[i12] = (char) ((i53 % 10) + 48);
                        cArr[i12 + 1] = '.';
                        int i57 = i12 + i54 + 1;
                        if (i54 == 1) {
                            cArr[i57] = c;
                            i57++;
                        }
                        int i58 = i57 + 1;
                        cArr[i57] = 'E';
                        if (i47 < 0) {
                            cArr[i58] = c2;
                            i47 = -i47;
                            i58 = i57 + 2;
                        }
                        if (i47 >= i4) {
                            cArr[i58] = (char) ((i47 / 10) + 48);
                            i58++;
                        }
                        i13 = i58 + 1;
                        cArr[i58] = (char) ((i47 % 10) + 48);
                    } else if (i47 < 0) {
                        int i59 = i12 + 1;
                        cArr[i12] = c;
                        int i60 = i12 + 2;
                        cArr[i59] = '.';
                        int i61 = -1;
                        while (i61 > i47) {
                            cArr[i60] = c;
                            i61--;
                            i60++;
                        }
                        int i62 = i60;
                        for (int i63 = i3; i63 < i54; i63++) {
                            cArr[((i60 + i54) - i63) - 1] = (char) ((i53 % 10) + 48);
                            i53 /= 10;
                            i62++;
                        }
                        i13 = i62;
                    } else if (i46 >= i54) {
                        for (int i64 = i3; i64 < i54; i64++) {
                            cArr[((i12 + i54) - i64) - 1] = (char) ((i53 % 10) + 48);
                            i53 /= 10;
                        }
                        int i65 = i12 + i54;
                        while (i54 < i46) {
                            cArr[i65] = c;
                            i54++;
                            i65++;
                        }
                        cArr[i65] = '.';
                        i13 = i65 + 2;
                        cArr[i65 + 1] = c;
                    } else {
                        int i66 = i12 + 1;
                        for (int i67 = i3; i67 < i54; i67++) {
                            if ((i54 - i67) - 1 == i47) {
                                cArr[((i66 + i54) - i67) - 1] = '.';
                                i66--;
                            }
                            cArr[((i66 + i54) - i67) - 1] = (char) ((i53 % 10) + 48);
                            i53 /= 10;
                        }
                        i13 = i54 + 1 + i12;
                    }
                    return i13 - i;
                }
            }
            return i18 - i;
        }
        cArr[i] = 'N';
        cArr[i + 1] = 'a';
        i19 = i + 3;
        cArr[i + 2] = 'N';
        return i19 - i;
    }
}

package com.google.zxing.datamatrix.encoder;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.alibaba.fastjson.asm.Opcodes;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.error.GattError;

/* JADX INFO: loaded from: classes.dex */
public final class ErrorCorrection {
    private static final int MODULO_VALUE = 301;
    private static final int[] FACTOR_SETS = {5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
    private static final int[][] FACTORS = {new int[]{228, 48, 15, 111, 62}, new int[]{23, 68, 144, GattError.GATT_CMD_STARTED, 240, 92, 254}, new int[]{28, 24, Opcodes.INVOKEINTERFACE, Opcodes.IF_ACMPNE, 223, 248, 116, 255, 110, 61}, new int[]{175, GattError.GATT_MORE, 205, 12, 194, 168, 39, 245, 60, 97, 120}, new int[]{41, Opcodes.IFEQ, Opcodes.IFLE, 91, 61, 42, GattError.GATT_NOT_ENCRYPTED, 213, 97, Opcodes.GETSTATIC, 100, 242}, new int[]{156, 97, Opcodes.CHECKCAST, GlobalBluetooth.MAX_FIXED_GROUP_ID, 95, 9, 157, 119, GattError.GATT_MORE, 45, 18, 186, 83, Opcodes.INVOKEINTERFACE}, new int[]{83, 195, 100, 39, Opcodes.NEWARRAY, 75, 66, 61, 241, 213, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY, GattError.GATT_INTERNAL_ERROR, 94, 254, 225, 48, 90, Opcodes.NEWARRAY}, new int[]{15, 195, 244, 9, 233, 71, 168, 2, Opcodes.NEWARRAY, Opcodes.IF_ICMPNE, Opcodes.IFEQ, 145, 253, 79, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, 82, 27, 174, 186, 172}, new int[]{52, 190, 88, 205, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY, 39, Opcodes.ARETURN, 21, 155, 197, 251, 223, 155, 21, 5, 172, 254, 124, 12, Opcodes.PUTFIELD, Opcodes.INVOKESTATIC, 96, 50, 193}, new int[]{211, 231, 43, 97, 71, 96, 103, 174, 37, Opcodes.DCMPL, 170, 53, 75, 34, 249, 121, 17, GattError.GATT_MORE, 110, 213, GattError.GATT_ENCRYPTED_NO_MITM, GattError.GATT_PENDING, 120, Opcodes.DCMPL, 233, 168, 93, 255}, new int[]{245, 127, 242, 218, GattError.GATT_WRONG_STATE, 250, Opcodes.IF_ICMPGE, Opcodes.PUTFIELD, 102, 120, 84, 179, 220, 251, 80, Opcodes.INVOKEVIRTUAL, 229, 18, 2, 4, 68, 33, 101, GattError.GATT_AUTH_FAIL, 95, 119, 115, 44, 175, Opcodes.INVOKESTATIC, 59, 25, 225, 98, 81, 112}, new int[]{77, 193, GattError.GATT_AUTH_FAIL, 31, 19, 38, 22, Opcodes.IFEQ, 247, 105, 122, 2, 245, GattError.GATT_ERROR, 242, 8, 175, 95, 100, 9, Opcodes.GOTO, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, 202, 69, 50, 150, Opcodes.RETURN, 226, 5, 9, 5}, new int[]{245, GattError.GATT_BUSY, 172, 223, 96, 32, 117, 22, 238, GattError.GATT_ERROR, 238, 231, 205, Opcodes.NEWARRAY, 237, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, GattError.GATT_DB_FULL, 88, 120, 100, 66, GattError.GATT_MORE, 186, 240, 82, 44, Opcodes.ARETURN, 87, Opcodes.NEW, 147, Opcodes.IF_ICMPNE, 175, 69, 213, 92, 253, 225, 19}, new int[]{175, 9, 223, 238, 12, 17, 220, 208, 100, 29, 175, 170, 230, Opcodes.CHECKCAST, 215, 235, 150, Opcodes.IF_ICMPEQ, 36, 223, 38, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, GattError.GATT_BUSY, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, 164, 13, GattError.GATT_AUTH_FAIL, 245, 127, 67, 247, 28, 155, 43, 203, 107, 233, 53, GattError.GATT_CONGESTED, 46}, new int[]{242, 93, Opcodes.RET, 50, 144, 210, 39, 118, 202, Opcodes.NEWARRAY, 201, 189, GattError.GATT_CONGESTED, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, 196, 37, Opcodes.INVOKEINTERFACE, 112, GattError.GATT_CMD_STARTED, 230, 245, 63, 197, 190, 250, 106, Opcodes.INVOKEINTERFACE, 221, 175, 64, 114, 71, Opcodes.IF_ICMPLT, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, GattError.GATT_WRONG_STATE, Opcodes.NEWARRAY, 17, Opcodes.IF_ICMPGT, 31, Opcodes.ARETURN, 170, 4, 107, 232, 7, 94, Opcodes.IF_ACMPNE, 224, 124, 86, 47, 11, 204}, new int[]{220, 228, 173, 89, 251, Opcodes.FCMPL, Opcodes.IF_ICMPEQ, 56, 89, 33, 147, 244, Opcodes.IFNE, 36, 73, 127, 213, GattError.GATT_PENDING, 248, Opcodes.GETFIELD, 234, 197, Opcodes.IFLE, Opcodes.RETURN, 68, 122, 93, 213, 15, Opcodes.IF_ICMPNE, 227, 236, 66, GattError.GATT_INVALID_CFG, Opcodes.IFEQ, Opcodes.INVOKEINTERFACE, 202, Opcodes.GOTO, 179, 25, 220, 232, 96, 210, 231, GattError.GATT_PENDING, 223, 239, Opcodes.PUTFIELD, 241, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, Opcodes.IFEQ, GattError.GATT_BUSY, 63, 96, 103, 82, 186}};
    private static final int[] LOG = new int[256];
    private static final int[] ALOG = new int[255];

    static {
        int i = 1;
        for (int i2 = 0; i2 < 255; i2++) {
            ALOG[i2] = i;
            LOG[i] = i2;
            i *= 2;
            if (i >= 256) {
                i ^= 301;
            }
        }
    }

    private ErrorCorrection() {
    }

    public static String encodeECC200(String str, SymbolInfo symbolInfo) {
        if (str.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder sb = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        sb.append(str);
        int interleavedBlockCount = symbolInfo.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb.append(createECCBlock(str, symbolInfo.getErrorCodewords()));
        } else {
            sb.setLength(sb.capacity());
            int[] iArr = new int[interleavedBlockCount];
            int[] iArr2 = new int[interleavedBlockCount];
            int i = 0;
            while (i < interleavedBlockCount) {
                int i2 = i + 1;
                iArr[i] = symbolInfo.getDataLengthForInterleavedBlock(i2);
                iArr2[i] = symbolInfo.getErrorLengthForInterleavedBlock(i2);
                i = i2;
            }
            for (int i3 = 0; i3 < interleavedBlockCount; i3++) {
                StringBuilder sb2 = new StringBuilder(iArr[i3]);
                for (int i4 = i3; i4 < symbolInfo.getDataCapacity(); i4 += interleavedBlockCount) {
                    sb2.append(str.charAt(i4));
                }
                String strCreateECCBlock = createECCBlock(sb2.toString(), iArr2[i3]);
                int i5 = 0;
                int i6 = i3;
                while (i6 < iArr2[i3] * interleavedBlockCount) {
                    sb.setCharAt(symbolInfo.getDataCapacity() + i6, strCreateECCBlock.charAt(i5));
                    i6 += interleavedBlockCount;
                    i5++;
                }
            }
        }
        return sb.toString();
    }

    private static String createECCBlock(CharSequence charSequence, int i) {
        int i2;
        int i3;
        int i4 = 0;
        while (true) {
            int[] iArr = FACTOR_SETS;
            if (i4 >= iArr.length) {
                i4 = -1;
                break;
            }
            if (iArr[i4] == i) {
                break;
            }
            i4++;
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("Illegal number of error correction codewords specified: " + i);
        }
        int[] iArr2 = FACTORS[i4];
        char[] cArr = new char[i];
        for (int i5 = 0; i5 < i; i5++) {
            cArr[i5] = 0;
        }
        for (int i6 = 0; i6 < charSequence.length(); i6++) {
            int i7 = i - 1;
            int iCharAt = cArr[i7] ^ charSequence.charAt(i6);
            while (i7 > 0) {
                if (iCharAt != 0 && (i3 = iArr2[i7]) != 0) {
                    char c = cArr[i7 - 1];
                    int[] iArr3 = ALOG;
                    int[] iArr4 = LOG;
                    cArr[i7] = (char) (iArr3[(iArr4[iCharAt] + iArr4[i3]) % 255] ^ c);
                } else {
                    cArr[i7] = cArr[i7 - 1];
                }
                i7--;
            }
            if (iCharAt != 0 && (i2 = iArr2[0]) != 0) {
                int[] iArr5 = ALOG;
                int[] iArr6 = LOG;
                cArr[0] = (char) iArr5[(iArr6[iCharAt] + iArr6[i2]) % 255];
            } else {
                cArr[0] = 0;
            }
        }
        char[] cArr2 = new char[i];
        for (int i8 = 0; i8 < i; i8++) {
            cArr2[i8] = cArr[(i - i8) - 1];
        }
        return String.valueOf(cArr2);
    }
}

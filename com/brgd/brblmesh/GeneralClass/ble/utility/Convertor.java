package com.brgd.brblmesh.GeneralClass.ble.utility;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;

/* JADX INFO: loaded from: classes.dex */
public class Convertor {
    public static String byte2HexStr(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = GlobalVariable.ILLUMINATION + hexString;
            }
            sb.append(hexString);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    public static int bytesToInt(byte[] bArr) {
        int i = bArr[3] & 255;
        int i2 = (bArr[2] & 255) << 8;
        return ((bArr[0] & 255) << 24) | i | i2 | ((bArr[1] & 255) << 16);
    }

    public static int bytesToShort(byte[] bArr) {
        return ((bArr[0] & 255) << 8) | (bArr[1] & 255);
    }
}

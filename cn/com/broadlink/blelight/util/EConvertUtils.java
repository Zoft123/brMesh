package cn.com.broadlink.blelight.util;

import android.content.Context;
import android.text.TextUtils;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class EConvertUtils {
    private EConvertUtils() {
    }

    public static byte[] hexStr2Bytes(String str) {
        int i = 0;
        if (str == null) {
            return new byte[0];
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 2;
            try {
                bArr[i] = (byte) Integer.parseInt(str.substring(i2, i3), 16);
                i++;
                i2 = i3;
            } catch (Exception unused) {
                return null;
            }
        }
        return bArr;
    }

    public static byte[] aesNoPadding(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/ZeroBytePadding");
            int blockSize = cipher.getBlockSize();
            int length = bArr3.length;
            if (length % blockSize != 0) {
                length += blockSize - (length % blockSize);
            }
            byte[] bArr4 = new byte[length];
            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
            cipher.init(1, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(bArr));
            return cipher.doFinal(bArr4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytes2HexStr(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(to16(b));
        }
        return stringBuffer.toString();
    }

    public static String bytes2InrStr(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        for (int i = 0; i < bArr.length; i++) {
            if (i != 0) {
                stringBuffer.append(EListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            stringBuffer.append(bArr[i] & 255);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static String to16(int i) {
        String hexString = Integer.toHexString(i);
        int length = hexString.length();
        if (length == 1) {
            hexString = GlobalVariable.ILLUMINATION + hexString;
        }
        return length > 2 ? hexString.substring(length - 2, length) : hexString;
    }

    public static String tenTo16_2(long j) {
        String hexString = Long.toHexString(j);
        if (hexString.length() % 2 != 0) {
            hexString = GlobalVariable.ILLUMINATION + hexString;
        }
        return hexbackrow(hexString);
    }

    public static String ten2HexString(int i) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 == 0) {
            return hexString;
        }
        return GlobalVariable.ILLUMINATION + hexString;
    }

    public static long hexto10(String str) {
        return Long.parseLong(hexbackrow(str), 16);
    }

    public static String hexbackrow(String str) {
        String str2;
        int length = str.length();
        int i = length / 2;
        int i2 = 0;
        String string = "";
        if (length % 2 != 0) {
            str2 = GlobalVariable.ILLUMINATION + str.substring(0, 1);
        } else {
            str2 = "";
        }
        while (i2 < i) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            int i3 = length - 2;
            sb.append(str.substring(i3, length));
            i2++;
            string = sb.toString();
            length = i3;
        }
        return string + str2;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static byte[] numberToByte(short s) {
        byte[] bArr = new byte[2];
        for (int i = 0; i < 2; i++) {
            bArr[i] = (byte) ((s >> (i * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] numberToByte(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] numberToByte(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((j >> (i * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] getBytes(String str, int i) {
        byte[] bArr = new byte[i];
        if (!TextUtils.isEmpty(str)) {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            for (int i2 = 0; i2 < i; i2++) {
                if (i2 < bytes.length) {
                    bArr[i2] = bytes[i2];
                }
            }
        }
        return bArr;
    }

    public static byte[] littleEndian(byte[] bArr) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        return byteBufferWrap.array();
    }
}

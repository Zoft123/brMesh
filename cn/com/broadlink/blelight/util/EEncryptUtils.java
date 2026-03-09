package cn.com.broadlink.blelight.util;

import android.text.TextUtils;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class EEncryptUtils {
    public static String base64Encode(byte[] bArr) {
        return new String(Base64.encode(bArr, 2));
    }

    public static String base64Encode(String str) {
        try {
            return base64Encode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String base64Decode(String str) {
        if (str != null) {
            return new String(Base64.decode(str, 2), StandardCharsets.UTF_8);
        }
        return "";
    }

    public static byte[] base64DecodeBytes(String str) {
        return Base64.decode(str, 2);
    }

    public static String base64DecodeHex(String str) {
        return EConvertUtils.bytes2HexStr(Base64.decode(str, 2));
    }

    public static byte[] md5(String str) {
        return md5(str.getBytes());
    }

    public static byte[] md5(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String SHA1(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            return EConvertUtils.bytes2HexStr(messageDigest.digest());
        } catch (Exception unused) {
            return "";
        }
    }

    public static String SHA256(String str) {
        try {
            return EConvertUtils.bytes2HexStr(MessageDigest.getInstance("SHA-256").digest(str.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final String MD5String(String str) {
        try {
            return EConvertUtils.bytes2HexStr(MD5(str));
        } catch (Exception unused) {
            return null;
        }
    }

    public static final byte[] MD5(String str) {
        return MD5(str.getBytes());
    }

    public static final byte[] MD5(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String fileSHA1(File file) {
        String data = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MappedByteBuffer map = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(map);
            data = parseData(messageDigest.digest(), r8.length);
            fileInputStream.close();
            return data;
        } catch (Exception unused) {
            return data;
        }
    }

    public static String fileMD5(File file) {
        String strBytes2HexStr = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MappedByteBuffer map = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(map);
            strBytes2HexStr = EConvertUtils.bytes2HexStr(messageDigest.digest());
            fileInputStream.close();
            return strBytes2HexStr;
        } catch (Exception unused) {
            return strBytes2HexStr;
        }
    }

    public static byte[] aesNoPadding(byte[] bArr, byte[] bArr2, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes();
            Cipher cipher = Cipher.getInstance("AES/CBC/ZeroBytePadding");
            int blockSize = cipher.getBlockSize();
            int length = bytes.length;
            if (length % blockSize != 0) {
                length += blockSize - (length % blockSize);
            }
            byte[] bArr3 = new byte[length];
            System.arraycopy(bytes, 0, bArr3, 0, bytes.length);
            cipher.init(1, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(bArr));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public static String aesNoPaddingDecryptHexString(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArrAesNoPaddingDecrypt = aesNoPaddingDecrypt(bArr, bArr2, bArr3);
        if (bArrAesNoPaddingDecrypt == null) {
            return null;
        }
        return EConvertUtils.bytes2HexStr(bArrAesNoPaddingDecrypt);
    }

    public static byte[] aesNoPaddingDecrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/ZeroBytePadding");
            cipher.init(2, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(bArr));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] aesPKCS5PaddingEncrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(bArr));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String aesPKCS5PaddingDecryptString(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArrAesPKCS5PaddingDecrypt = aesPKCS5PaddingDecrypt(bArr, bArr2, bArr3);
        if (bArrAesPKCS5PaddingDecrypt == null) {
            return null;
        }
        return new String(bArrAesPKCS5PaddingDecrypt);
    }

    public static byte[] aesPKCS5PaddingDecrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(bArr));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] aesPKCS7PaddingEncrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(bArr));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String aesPKCS7PaddingDecryptString(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArrAesPKCS7PaddingDecrypt = aesPKCS7PaddingDecrypt(bArr, bArr2, bArr3);
        if (bArrAesPKCS7PaddingDecrypt == null) {
            return null;
        }
        return new String(bArrAesPKCS7PaddingDecrypt);
    }

    public static byte[] aesPKCS7PaddingDecrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr3 == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(2, new SecretKeySpec(bArr, "AES"), new IvParameterSpec(bArr2));
            return cipher.doFinal(bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseData(byte[] bArr, long j) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < j; i++) {
            stringBuffer.append(EConvertUtils.to16(bArr[i]));
        }
        return stringBuffer.toString();
    }
}

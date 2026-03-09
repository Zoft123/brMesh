package cn.com.broadlink.blelight.helper;

import android.util.Base64;
import cn.com.broadlink.blelight.bean.BLECodeInfo;
import cn.com.broadlink.blelight.util.BLFileUtils;
import cn.com.broadlink.blelight.util.EAppUtils;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.EEncryptUtils;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
public class BLCloudCodeHelper {
    private static final String RSA = "RSA";
    private static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    private static byte[] encrypt(byte[] bArr) throws Exception {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(Base64.decode(BLFileUtils.readAssetsFile(EAppUtils.getApp(), "rsa_public_key.pem").replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", ""), 2)));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(1, publicKeyGeneratePublic);
        return cipher.doFinal(bArr);
    }

    public static String genCode(String str, String str2, int i, String str3) {
        byte[] byteData = new BLECodeInfo(str, str2, i, str3).parseByteData();
        if (byteData == null) {
            return null;
        }
        try {
            return Base64.encodeToString(encrypt(byteData), 2).replaceAll("\\+", "-").replaceAll("/", "_").replaceAll("=", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String genFamilyCode(String str, String str2, int i, String str3) {
        return genCode(str, parseAesKey(str, str2), i, str3);
    }

    public static String parseAesKey(String str, String str2) {
        if (str != null && str.length() >= 32 && str2 != null && str2.length() == 40) {
            try {
                byte[] bArrHexStr2Bytes = EConvertUtils.hexStr2Bytes(str2);
                byte[] bArrMd5 = EEncryptUtils.md5(EConvertUtils.hexStr2Bytes(str.substring(0, 32)));
                byte[] bArr = new byte[16];
                for (int i = 0; i < 16; i++) {
                    bArr[i] = (byte) (bArrMd5[i] ^ bArrHexStr2Bytes[i + 4]);
                }
                return EConvertUtils.bytes2HexStr(bArr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}

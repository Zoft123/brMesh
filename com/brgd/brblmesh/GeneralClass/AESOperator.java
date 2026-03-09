package com.brgd.brblmesh.GeneralClass;

import android.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class AESOperator {
    private static AESOperator instance;
    public String sKey = "brgdbrgdbrgdbrgd";
    public String ivParameter = "0392039203920300";

    private AESOperator() {
    }

    public static AESOperator getInstance() {
        if (instance == null) {
            instance = new AESOperator();
        }
        return instance;
    }

    public String encrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(this.sKey.getBytes(), "AES"), new IvParameterSpec(this.ivParameter.getBytes()));
        return Base64.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)), 0);
    }

    public String decrypt(String str) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.sKey.getBytes(StandardCharsets.US_ASCII), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(this.ivParameter.getBytes()));
        return new String(cipher.doFinal(Base64.decode(str, 0)), StandardCharsets.UTF_8);
    }
}

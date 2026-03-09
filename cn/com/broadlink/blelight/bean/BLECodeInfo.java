package cn.com.broadlink.blelight.bean;

import android.text.TextUtils;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.ELogUtils;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class BLECodeInfo implements Cloneable {
    public String aesKey;
    public String did;
    public String server;
    public short type;
    public short version = 1;
    public short aesKeyId = 1;
    public int authDuration = 10;
    public long authTimestamp = System.currentTimeMillis() / 1000;

    public BLECodeInfo(String str, String str2, int i, String str3) {
        if (str != null && str.length() > 32) {
            str = str.substring(0, 32);
        }
        this.did = str;
        this.aesKey = str2;
        this.type = (short) i;
        if (str3.contains("device-heartbeat-")) {
            return;
        }
        this.server = "device-heartbeat-" + str3;
    }

    public byte[] parseByteData() {
        if (TextUtils.isEmpty(this.did) || TextUtils.isEmpty(this.aesKey)) {
            return null;
        }
        String str = EConvertUtils.bytes2HexStr(EConvertUtils.numberToByte(this.version)) + this.did + EConvertUtils.bytes2HexStr(EConvertUtils.numberToByte(this.type)) + this.aesKey + EConvertUtils.bytes2HexStr(EConvertUtils.numberToByte(this.aesKeyId)) + EConvertUtils.bytes2HexStr(EConvertUtils.numberToByte(this.authTimestamp)) + EConvertUtils.bytes2HexStr(EConvertUtils.numberToByte(this.authDuration)) + EConvertUtils.bytes2HexStr(this.server.getBytes(StandardCharsets.UTF_8));
        ELogUtils.d("jyq_server", "code: " + str);
        return EConvertUtils.hexStr2Bytes(str);
    }
}

package cn.com.broadlink.blelight.helper;

import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cn.com.broadlink.blelight.util.EAppUtils;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.EEncryptUtils;
import cn.com.broadlink.blelight.util.ELogUtils;
import cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brgd.brblmesh.GeneralClass.DevOtaCloudHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class DevSignHelper {
    private static final String PREFERENCE_NAME_GLOBAL = "PREFERENCE_DEV_SIGN_GLOBAL";
    private static final String URL_SIGN = "/appsync/group/vthome/devsignauth";
    private static volatile DevSignHelper instance;
    private String mHost;
    private String mLicenseId;

    public static class DevSignPayload {
        public List<DevlistBean> devlist = new ArrayList();
        public String familykey;

        public static class DevlistBean {
            public String data;
            public String did;
            public Boolean isgateway;
            public String sign;
        }
    }

    private DevSignHelper() {
        if (this.mHost == null || this.mLicenseId == null) {
            ELogUtils.w("jyq_dev_sign", "server info not settled");
        } else {
            EOkHttpUtils.getInstance().init();
        }
    }

    public static DevSignHelper getInstance() {
        if (instance == null) {
            synchronized (DevSignHelper.class) {
                if (instance == null) {
                    instance = new DevSignHelper();
                }
            }
        }
        return instance;
    }

    public void init(String str, String str2) {
        this.mHost = str;
        if (str2 == null || str2.length() < 32) {
            return;
        }
        this.mLicenseId = EEncryptUtils.base64DecodeHex(str2).substring(0, 32);
    }

    public int checkDevSign(final List<DevSignPayload.DevlistBean> list) {
        updateLocalSign(list, null);
        String str = this.mHost;
        if (str == null || this.mLicenseId == null) {
            ELogUtils.w("jyq_dev_sign", "server info not settled");
            return -3001;
        }
        String str2 = String.format(DevOtaCloudHelper.URL_DOWNLOAD, str, URL_SIGN);
        HashMap map = new HashMap();
        map.put("licenseid", this.mLicenseId);
        EOkHttpUtils.getInstance().postAsync(str2, map, genRemotePayload(list), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.DevSignHelper.1
            @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
            public void failed(Call call, IOException iOException) {
            }

            @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
            public void success(Call call, Response response) throws IOException {
                JSONObject jSONObject;
                if (response == null || !response.getIsSuccessful() || response.body() == null) {
                    return;
                }
                JSONObject object = JSON.parseObject(response.body().string());
                if (object.getInteger(NotificationCompat.CATEGORY_STATUS).intValue() != 0 || (jSONObject = object.getJSONObject("auth")) == null) {
                    return;
                }
                DevSignHelper.updateLocalSign(list, jSONObject);
                BLEFastconHelper.getInstance().clearPairedDevMap();
            }
        });
        return 0;
    }

    private static byte[] genRemotePayload(List<DevSignPayload.DevlistBean> list) {
        if (list == null) {
            return null;
        }
        DevSignPayload devSignPayload = new DevSignPayload();
        devSignPayload.familykey = EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey());
        devSignPayload.devlist.addAll(list);
        return encrypt(JSON.toJSONString(devSignPayload));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean updateLocalSign(List<DevSignPayload.DevlistBean> list, JSONObject jSONObject) {
        if (list == null) {
            return false;
        }
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        boolean z = true;
        if (jSONObject == null) {
            for (DevSignPayload.DevlistBean devlistBean : list) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("sign", (Object) devlistBean.sign);
                jSONObject4.put("isgateway", (Object) devlistBean.isgateway);
                jSONObject4.put("code", (Object) 1);
                jSONObject2.put(devlistBean.did, (Object) jSONObject4);
            }
        } else {
            boolean z2 = true;
            for (DevSignPayload.DevlistBean devlistBean2 : list) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("sign", (Object) devlistBean2.sign);
                jSONObject5.put("isgateway", (Object) devlistBean2.isgateway);
                if (jSONObject.containsKey(devlistBean2.did)) {
                    int iIntValue = jSONObject.getInteger(devlistBean2.did).intValue();
                    jSONObject5.put("code", (Object) Integer.valueOf(iIntValue == 0 ? 0 : -1));
                    if (iIntValue != 0) {
                        z2 = false;
                    }
                } else {
                    jSONObject5.put("code", (Object) 1);
                }
                jSONObject2.put(devlistBean2.did, (Object) jSONObject5);
            }
            saveDevSignFlag(z2);
            z = z2;
        }
        jSONObject3.put("devs", (Object) jSONObject2);
        saveLocalSign(jSONObject3.toJSONString());
        return z;
    }

    private static byte[] encrypt(String str) {
        return EEncryptUtils.aesNoPadding(EConvertUtils.hexStr2Bytes("eaacaa3abb5862a21916b5771d1615ac"), EConvertUtils.hexStr2Bytes("62a21916b577eaac1d1615acaa3abb58"), str);
    }

    private static JSONObject decrypt(byte[] bArr) {
        return JSON.parseObject(new String(EEncryptUtils.aesNoPaddingDecrypt(EConvertUtils.hexStr2Bytes("eaacaa3abb5862a21916b5771d1615ac"), EConvertUtils.hexStr2Bytes("62a21916b577eaac1d1615acaa3abb58"), bArr)));
    }

    public static JSONObject getLocalSign() {
        String string = EAppUtils.getApp().getSharedPreferences(PREFERENCE_NAME_GLOBAL, 0).getString(EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey()), null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return decrypt(EConvertUtils.hexStr2Bytes(string));
    }

    private static void saveLocalSign(String str) {
        if (str == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = EAppUtils.getApp().getSharedPreferences(PREFERENCE_NAME_GLOBAL, 0).edit();
        editorEdit.putString(EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey()), EConvertUtils.bytes2HexStr(encrypt(str)));
        editorEdit.apply();
    }

    public static boolean getDevSignFlag() {
        return EAppUtils.getApp().getSharedPreferences(PREFERENCE_NAME_GLOBAL, 0).getBoolean(EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey()) + "_isAllOk", true);
    }

    private static void saveDevSignFlag(boolean z) {
        SharedPreferences.Editor editorEdit = EAppUtils.getApp().getSharedPreferences(PREFERENCE_NAME_GLOBAL, 0).edit();
        editorEdit.putBoolean(EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey()) + "_isAllOk", z);
        editorEdit.apply();
    }

    public static void clearSign() {
        SharedPreferences.Editor editorEdit = EAppUtils.getApp().getSharedPreferences(PREFERENCE_NAME_GLOBAL, 0).edit();
        editorEdit.remove(EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey()));
        editorEdit.remove(EConvertUtils.bytes2HexStr(BLEFastconHelper.getInstance().getPhoneKey()) + "_isAllOk");
        editorEdit.apply();
    }
}

package cn.com.broadlink.blelight.helper;

import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.jni.BLEUtil;
import cn.com.broadlink.blelight.util.EAppUtils;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.EEncryptUtils;
import cn.com.broadlink.blelight.util.ELogUtils;
import cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class GatewayRemoteCtrlHelper {
    private static final int MSG_TYPE_GATEWAY_TIMER = 2850;
    private static final int MSG_TYPE_PASS_THROUGH = 2311;
    private static final String URL_REMOTE_CTRL = "/device/control/opencontrol/v2/control?license=";
    private static volatile GatewayRemoteCtrlHelper instance;
    private static String mCompanyId;
    private static String mFamilyId;
    private static String mLoginSession;
    private static String mUUID;
    private static String mUserId;
    private String mHost;
    private String mLicense;
    private String mLicenseId;
    private boolean mIsNeedRemoteCtrl = true;
    private boolean mMultiGwFlag = true;

    private GatewayRemoteCtrlHelper() {
        EOkHttpUtils.getInstance().init();
    }

    public static GatewayRemoteCtrlHelper getInstance() {
        if (instance == null) {
            synchronized (GatewayRemoteCtrlHelper.class) {
                if (instance == null) {
                    instance = new GatewayRemoteCtrlHelper();
                }
            }
        }
        return instance;
    }

    public void init(String str, String str2) {
        this.mHost = str;
        this.mLicense = str2;
        if (str2 == null || str2.length() < 32) {
            return;
        }
        this.mLicenseId = EEncryptUtils.base64DecodeHex(str2).substring(0, 32);
    }

    public boolean isNeedRemoteCtrl() {
        return this.mIsNeedRemoteCtrl;
    }

    public void setNeedRemoteCtrl(boolean z) {
        this.mIsNeedRemoteCtrl = z;
    }

    public static String getFamilyId() {
        return mFamilyId;
    }

    public static void setFamilyId(String str) {
        mFamilyId = str;
    }

    public static String getUUID() {
        return mUUID;
    }

    public static void setUUID(String str) {
        mUUID = str;
    }

    public static String getLoginSession() {
        return mLoginSession;
    }

    public static void setLoginSession(String str) {
        mLoginSession = str;
    }

    public static String getUserId() {
        return mUserId;
    }

    public static void setUserId(String str) {
        mUserId = str;
    }

    public static String getCompanyId() {
        return mCompanyId;
    }

    public static void setCompanyId(String str) {
        mCompanyId = str;
    }

    public boolean isMultiGwFlag() {
        return this.mMultiGwFlag;
    }

    public void setMultiGwFlag(boolean z) {
        this.mMultiGwFlag = z;
    }

    public int controlRequest(BLEDeviceInfo bLEDeviceInfo, byte[] bArr) {
        if (!this.mIsNeedRemoteCtrl) {
            ELogUtils.w("jyq_server", "IsNeedRemoteCtrl is false");
            return -3002;
        }
        return doRequest(bLEDeviceInfo, MSG_TYPE_PASS_THROUGH, bArr, null);
    }

    public int controlRequestForce(BLEDeviceInfo bLEDeviceInfo, byte[] bArr) {
        return doRequest(bLEDeviceInfo, MSG_TYPE_PASS_THROUGH, bArr, null);
    }

    public int timerRequest(BLEDeviceInfo bLEDeviceInfo, byte[] bArr, EOkHttpUtils.HttpCallback httpCallback) {
        return doRequest(bLEDeviceInfo, MSG_TYPE_GATEWAY_TIMER, bArr, httpCallback);
    }

    public int doRequest(BLEDeviceInfo bLEDeviceInfo, int i, byte[] bArr, EOkHttpUtils.HttpCallback httpCallback) {
        if (!DevSignHelper.getDevSignFlag()) {
            ELogUtils.e("jyq", "DevSignFlag is false!");
            return -3002;
        }
        if (this.mHost == null || this.mLicense == null) {
            ELogUtils.w("jyq_server", "server info not settled");
            return -3001;
        }
        byte[] bArr2 = new byte[bArr.length + 12];
        BLEUtil.package_open_sdk_cmd(i, bArr, bArr.length, bArr2);
        ELogUtils.w("jyq_server", "package_open_sdk_cmd: " + EConvertUtils.bytes2HexStr(bArr2));
        boolean z = true;
        String str = String.format("https://app-service-%s%s%s", this.mHost, URL_REMOTE_CTRL, this.mLicense);
        boolean z2 = i == MSG_TYPE_PASS_THROUGH;
        try {
            if (!this.mMultiGwFlag || !z2) {
                z = false;
            }
            Payload payload = new Payload(bLEDeviceInfo, bArr2, z);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("familyId", (Object) getNullStrParam(mFamilyId));
            jSONObject.put("userId", (Object) getNullStrParam(mUserId));
            jSONObject.put("loginSession", (Object) getNullStrParam(mLoginSession));
            jSONObject.put("uuid", (Object) getNullStrParam(mUUID));
            jSONObject.put("appplatform", (Object) "ANDROID");
            jSONObject.put("appversion", (Object) getNullStrParam(EAppUtils.getAppVersionName()));
            jSONObject.put("appid", (Object) getNullStrParam(EAppUtils.getAppPackageName()));
            jSONObject.put("licenseid", (Object) getNullStrParam(this.mLicenseId));
            jSONObject.put("companyid", (Object) getNullStrParam(mCompanyId));
            EOkHttpUtils.getInstance().postJsonAsync(str, jSONObject, JSON.toJSONString(payload), httpCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String getNullStrParam(String str) {
        return str == null ? "" : str;
    }

    public static String genRemotePayload(BLEDeviceInfo bLEDeviceInfo, byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length + 12];
        BLEUtil.package_open_sdk_cmd(MSG_TYPE_PASS_THROUGH, bArr, bArr.length, bArr2);
        return JSON.toJSONString(new Payload(bLEDeviceInfo, bArr2, true));
    }

    public static class Payload {
        public DirectiveBean directive;

        public Payload() {
        }

        public Payload(BLEDeviceInfo bLEDeviceInfo, byte[] bArr, boolean z) {
            this.directive = new DirectiveBean(bLEDeviceInfo, bArr, z);
        }

        public static class DirectiveBean {
            public EndpointBean endpoint;
            public HeaderBean header = new HeaderBean();
            public PayloadBean payload;

            public static class HeaderBean {
                public String namespace = "DNA.TransmissionControl";
                public String name = "commonControl";
                public String interfaceVersion = ExifInterface.GPS_MEASUREMENT_2D;
                public String messageId = "ble-trans-ctrl-" + (System.currentTimeMillis() / 1000);
            }

            public DirectiveBean() {
            }

            public DirectiveBean(BLEDeviceInfo bLEDeviceInfo, byte[] bArr, boolean z) {
                this.payload = new PayloadBean(EEncryptUtils.base64Encode(bArr));
                this.endpoint = new EndpointBean(bLEDeviceInfo, z);
            }

            public static class EndpointBean {
                public CookieBean cookie;
                public DevicePairedInfoBean devicePairedInfo;
                public String endpointId;

                public static class CookieBean {
                    public String did;
                    public String pid;
                }

                public static class DevicePairedInfoBean {
                    public String cookie;
                    public String cookies;
                    public String did;
                    public String familyId;
                    public String mac;
                    public String pid;
                }

                public EndpointBean() {
                }

                public EndpointBean(BLEDeviceInfo bLEDeviceInfo, boolean z) {
                    this.endpointId = GatewayRemoteCtrlHelper.getGatewayDid(bLEDeviceInfo);
                    DevicePairedInfoBean devicePairedInfoBean = new DevicePairedInfoBean();
                    this.devicePairedInfo = devicePairedInfoBean;
                    devicePairedInfoBean.mac = bLEDeviceInfo.mac;
                    this.devicePairedInfo.did = GatewayRemoteCtrlHelper.getGatewayDid(bLEDeviceInfo);
                    this.devicePairedInfo.pid = GatewayRemoteCtrlHelper.type2Pid(bLEDeviceInfo.type);
                    this.devicePairedInfo.familyId = GatewayRemoteCtrlHelper.mFamilyId;
                    this.devicePairedInfo.cookie = EEncryptUtils.base64Encode(JSON.toJSONString(new DevCookie(bLEDeviceInfo)));
                    if (z) {
                        ArrayList arrayList = new ArrayList();
                        Iterator<BLEDeviceInfo> it = BLEFastconHelper.getInstance().getGatewayList().iterator();
                        while (it.hasNext()) {
                            arrayList.add(EEncryptUtils.base64Encode(JSON.toJSONString(new DevCookie(it.next()))));
                        }
                        this.devicePairedInfo.cookies = JSON.toJSONString(arrayList);
                    }
                    CookieBean cookieBean = new CookieBean();
                    this.cookie = cookieBean;
                    cookieBean.did = GatewayRemoteCtrlHelper.getGatewayDid(bLEDeviceInfo);
                    this.cookie.pid = GatewayRemoteCtrlHelper.type2Pid(bLEDeviceInfo.type);
                }
            }

            public static class PayloadBean {
                public String data;

                public PayloadBean() {
                }

                public PayloadBean(String str) {
                    this.data = str;
                }
            }
        }
    }

    public static class DevCookie {
        public DeviceBean device;
        public boolean isBLE = true;

        public DevCookie() {
        }

        public DevCookie(BLEDeviceInfo bLEDeviceInfo) {
            this.device = new DeviceBean(bLEDeviceInfo);
        }

        public static class DeviceBean {
            public String aeskey;
            public String did;
            public int id = 1;
            public String key;
            public String lanaddr;
            public String mac;
            public String pid;

            public DeviceBean() {
            }

            public DeviceBean(BLEDeviceInfo bLEDeviceInfo) {
                this.key = bLEDeviceInfo.token;
                this.aeskey = bLEDeviceInfo.token;
                this.did = GatewayRemoteCtrlHelper.getGatewayDid(bLEDeviceInfo);
                this.pid = GatewayRemoteCtrlHelper.type2Pid(bLEDeviceInfo.type);
                this.mac = bLEDeviceInfo.mac;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String type2Pid(int i) {
        return "000000000000000000000000" + String.format("%02x", Integer.valueOf(i & 255)) + String.format("%02x", Integer.valueOf((i >> 8) & 255)) + String.format("%02x", Integer.valueOf((i >> 16) & 255)) + "00";
    }

    public static String getGatewayDid(BLEDeviceInfo bLEDeviceInfo) {
        return EAppUtils.shortDid2Long(TextUtils.isEmpty(bLEDeviceInfo.wifiDid) ? bLEDeviceInfo.did : bLEDeviceInfo.wifiDid);
    }
}

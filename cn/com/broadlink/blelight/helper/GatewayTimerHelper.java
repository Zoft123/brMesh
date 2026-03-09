package cn.com.broadlink.blelight.helper;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.bean.BLEGWTimerGetResult;
import cn.com.broadlink.blelight.bean.BLEGWTimerInfo;
import cn.com.broadlink.blelight.bean.BLEGWTimerSetResult;
import cn.com.broadlink.blelight.bean.SimpleCallback;
import cn.com.broadlink.blelight.util.EEncryptUtils;
import cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class GatewayTimerHelper {
    private static final int LEN_UART_HEADER = 12;

    public static void getTimerList(BLEDeviceInfo bLEDeviceInfo, SimpleCallback<BLEGWTimerGetResult> simpleCallback) {
        getTimerList(bLEDeviceInfo, 0, 8, null, simpleCallback);
    }

    public static void getTimerList(final BLEDeviceInfo bLEDeviceInfo, int i, final int i2, final List<BLEGWTimerInfo> list, final SimpleCallback<BLEGWTimerGetResult> simpleCallback) {
        if (bLEDeviceInfo == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLEGWTimerGetResult(-3002, "param error"));
                return;
            }
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(GlobalVariable.DID, (Object) GatewayRemoteCtrlHelper.getGatewayDid(bLEDeviceInfo));
        jSONObject.put(GlobalVariable.TYPE, (Object) TypedValues.CycleType.S_WAVE_PERIOD);
        jSONObject.put("act", (Object) 3);
        jSONObject.put(GlobalVariable.INDEX, (Object) Integer.valueOf(i));
        jSONObject.put("count", (Object) Integer.valueOf(i2));
        GatewayRemoteCtrlHelper.getInstance().timerRequest(bLEDeviceInfo, jSONObject.toString().getBytes(StandardCharsets.UTF_8), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayTimerHelper.1
            @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
            public void success(Call call, Response response) {
                if (response.getIsSuccessful() && response.body() != null) {
                    JSONObject httpResponse = GatewayTimerHelper.parseHttpResponse(response);
                    if (httpResponse != null && httpResponse.containsKey("data")) {
                        try {
                            BLEGWTimerGetResult bLEGWTimerGetResult = (BLEGWTimerGetResult) JSON.parseObject(httpResponse.getString("data"), BLEGWTimerGetResult.class);
                            ArrayList arrayList = new ArrayList();
                            List list2 = list;
                            if (list2 != null) {
                                arrayList.addAll(list2);
                            }
                            if (bLEGWTimerGetResult.status == 0) {
                                if (bLEGWTimerGetResult.timerlist != null) {
                                    arrayList.addAll(bLEGWTimerGetResult.timerlist);
                                }
                                if (arrayList.size() == bLEGWTimerGetResult.total) {
                                    if (simpleCallback != null) {
                                        if (bLEGWTimerGetResult.timerlist != null) {
                                            bLEGWTimerGetResult.timerlist.clear();
                                        } else {
                                            bLEGWTimerGetResult.timerlist = new ArrayList();
                                        }
                                        bLEGWTimerGetResult.timerlist.addAll(arrayList);
                                        simpleCallback.onCallback(bLEGWTimerGetResult);
                                        return;
                                    }
                                    return;
                                }
                                GatewayTimerHelper.getTimerList(bLEDeviceInfo, arrayList.size(), i2, arrayList, simpleCallback);
                                return;
                            }
                            SimpleCallback simpleCallback2 = simpleCallback;
                            if (simpleCallback2 != null) {
                                simpleCallback2.onCallback(bLEGWTimerGetResult);
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            SimpleCallback simpleCallback3 = simpleCallback;
                            if (simpleCallback3 != null) {
                                simpleCallback3.onCallback(new BLEGWTimerGetResult(-3011, "parse json fail"));
                                return;
                            }
                            return;
                        }
                    }
                    Integer integer = httpResponse.getInteger(NotificationCompat.CATEGORY_STATUS);
                    String string = httpResponse.getString("message");
                    SimpleCallback simpleCallback4 = simpleCallback;
                    if (simpleCallback4 != null) {
                        simpleCallback4.onCallback(new BLEGWTimerGetResult(integer.intValue(), string));
                        return;
                    }
                    return;
                }
                SimpleCallback simpleCallback5 = simpleCallback;
                if (simpleCallback5 != null) {
                    simpleCallback5.onCallback(new BLEGWTimerGetResult(-3003, response.message()));
                }
            }

            @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
            public void failed(Call call, IOException iOException) {
                SimpleCallback simpleCallback2 = simpleCallback;
                if (simpleCallback2 != null) {
                    simpleCallback2.onCallback(new BLEGWTimerGetResult(-3001, "http request return error"));
                }
            }
        });
    }

    public static void addTimer(BLEDeviceInfo bLEDeviceInfo, List<BLEGWTimerInfo> list, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        setTimer(bLEDeviceInfo, list, 0, simpleCallback);
    }

    public static void addTimer(BLEDeviceInfo bLEDeviceInfo, BLEGWTimerInfo bLEGWTimerInfo, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(bLEGWTimerInfo);
        setTimer(bLEDeviceInfo, arrayList, 0, simpleCallback);
    }

    public static void modifyTimer(BLEDeviceInfo bLEDeviceInfo, List<BLEGWTimerInfo> list, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        setTimer(bLEDeviceInfo, list, 2, simpleCallback);
    }

    public static void modifyTimer(BLEDeviceInfo bLEDeviceInfo, BLEGWTimerInfo bLEGWTimerInfo, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(bLEGWTimerInfo);
        setTimer(bLEDeviceInfo, arrayList, 2, simpleCallback);
    }

    public static void deleteTimer(BLEDeviceInfo bLEDeviceInfo, List<BLEGWTimerInfo> list, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        setTimer(bLEDeviceInfo, list, 1, simpleCallback);
    }

    public static void deleteTimer(BLEDeviceInfo bLEDeviceInfo, BLEGWTimerInfo bLEGWTimerInfo, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(bLEGWTimerInfo);
        setTimer(bLEDeviceInfo, arrayList, 1, simpleCallback);
    }

    public static void setTimer(BLEDeviceInfo bLEDeviceInfo, List<BLEGWTimerInfo> list, int i, final SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        if (bLEDeviceInfo == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLEGWTimerSetResult(-3002, "param error"));
            }
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(GlobalVariable.DID, (Object) GatewayRemoteCtrlHelper.getGatewayDid(bLEDeviceInfo));
            jSONObject.put("act", (Object) Integer.valueOf(i));
            jSONObject.put("timerlist", (Object) list);
            GatewayRemoteCtrlHelper.getInstance().timerRequest(bLEDeviceInfo, jSONObject.toString().getBytes(StandardCharsets.UTF_8), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayTimerHelper.2
                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void success(Call call, Response response) throws IOException {
                    GatewayTimerHelper.extracted(response, simpleCallback);
                }

                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void failed(Call call, IOException iOException) {
                    SimpleCallback simpleCallback2 = simpleCallback;
                    if (simpleCallback2 != null) {
                        simpleCallback2.onCallback(new BLEGWTimerSetResult(-3001, "http request return error"));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void extracted(Response response, SimpleCallback<BLEGWTimerSetResult> simpleCallback) {
        if (!response.getIsSuccessful() || response.body() == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLEGWTimerSetResult(-3003, response.message()));
                return;
            }
            return;
        }
        JSONObject httpResponse = parseHttpResponse(response);
        if (httpResponse != null && httpResponse.containsKey("data")) {
            try {
                BLEGWTimerSetResult bLEGWTimerSetResult = (BLEGWTimerSetResult) JSON.parseObject(httpResponse.getString("data"), BLEGWTimerSetResult.class);
                if (simpleCallback != null) {
                    simpleCallback.onCallback(bLEGWTimerSetResult);
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                if (simpleCallback != null) {
                    simpleCallback.onCallback(new BLEGWTimerSetResult(-3011, "parse json fail"));
                    return;
                }
                return;
            }
        }
        if (httpResponse == null || !httpResponse.containsKey(NotificationCompat.CATEGORY_STATUS)) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLEGWTimerSetResult(-3010, "server return data error"));
            }
        } else {
            Integer integer = httpResponse.getInteger(NotificationCompat.CATEGORY_STATUS);
            String string = httpResponse.getString("message");
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLEGWTimerSetResult(integer.intValue(), string));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject parseHttpResponse(Response response) {
        JSONObject jSONObject;
        if (response != null) {
            try {
                JSONObject object = JSON.parseObject(response.body().string());
                if (object.containsKey(NotificationCompat.CATEGORY_STATUS) && object.getInteger(NotificationCompat.CATEGORY_STATUS).intValue() != 0) {
                    return object;
                }
                JSONObject jSONObject2 = object.getJSONObject(NotificationCompat.CATEGORY_EVENT);
                if (jSONObject2 != null && (jSONObject = jSONObject2.getJSONObject("payload")) != null) {
                    Integer integer = jSONObject.getInteger(NotificationCompat.CATEGORY_STATUS);
                    if (integer != null && integer.intValue() != 0) {
                        return jSONObject;
                    }
                    if (jSONObject.containsKey("data")) {
                        String strBase64Decode = EEncryptUtils.base64Decode(jSONObject.getString("data"));
                        if (strBase64Decode.length() > 12) {
                            int iIndexOf = strBase64Decode.substring(12).startsWith("{\"") ? 12 : strBase64Decode.indexOf("{\"");
                            if (iIndexOf > 0) {
                                String strSubstring = strBase64Decode.substring(iIndexOf);
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("data", (Object) strSubstring);
                                return jSONObject3;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject jSONObject4 = new JSONObject();
        jSONObject4.put(NotificationCompat.CATEGORY_STATUS, (Object) (-3009));
        jSONObject4.put("message", (Object) "parse data fail");
        return jSONObject4;
    }
}

package cn.com.broadlink.blelight.helper;

import androidx.core.app.NotificationCompat;
import cn.com.broadlink.blelight.bean.BLECycleSceneCommand;
import cn.com.broadlink.blelight.bean.BLECycleSceneGetDetailResult;
import cn.com.broadlink.blelight.bean.BLECycleSceneGetResult;
import cn.com.broadlink.blelight.bean.BLECycleSceneInfo;
import cn.com.broadlink.blelight.bean.BLECycleSceneSetResult;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.bean.SimpleCallback;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.EEncryptUtils;
import cn.com.broadlink.blelight.util.ELogUtils;
import cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import okhttp3.Call;
import okhttp3.Response;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public class GatewayCycleSceneHelper {
    private static final int DNA_BLE_CYCLE_ADD_REQ = 2313;
    private static final int DNA_BLE_CYCLE_DEL_REQ = 2315;
    private static final int DNA_BLE_CYCLE_EXE_REQ = 2317;
    private static final int DNA_BLE_CYCLE_QUERY_DETAIL_REQ = 2321;
    private static final int DNA_BLE_CYCLE_QUERY_LIST_REQ = 2319;
    private static final int LEN_CYCLE_HEADER = 38;
    private static final int LEN_UART_HEADER = 12;

    public static void getList(BLEDeviceInfo bLEDeviceInfo, final SimpleCallback<BLECycleSceneGetResult> simpleCallback) {
        if (bLEDeviceInfo != null) {
            GatewayRemoteCtrlHelper.getInstance().doRequest(bLEDeviceInfo, DNA_BLE_CYCLE_QUERY_LIST_REQ, new JSONObject().toString().getBytes(StandardCharsets.UTF_8), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayCycleSceneHelper.1
                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void success(Call call, Response response) {
                    if (response.getIsSuccessful() && response.body() != null) {
                        JSONObject httpResponse = GatewayCycleSceneHelper.parseHttpResponse(response, true);
                        if (httpResponse == null || !httpResponse.containsKey("data")) {
                            if (httpResponse != null) {
                                Integer integer = httpResponse.getInteger(NotificationCompat.CATEGORY_STATUS);
                                String string = httpResponse.getString("message");
                                SimpleCallback simpleCallback2 = simpleCallback;
                                if (simpleCallback2 != null) {
                                    simpleCallback2.onCallback(new BLECycleSceneGetResult(integer.intValue(), string));
                                    return;
                                }
                                return;
                            }
                            SimpleCallback simpleCallback3 = simpleCallback;
                            if (simpleCallback3 != null) {
                                simpleCallback3.onCallback(new BLECycleSceneGetResult(-3005, "http request return error"));
                                return;
                            }
                            return;
                        }
                        try {
                            BLECycleSceneGetResult bLECycleSceneGetResult = (BLECycleSceneGetResult) JSON.parseObject(httpResponse.getString("data"), BLECycleSceneGetResult.class);
                            if (bLECycleSceneGetResult != null && bLECycleSceneGetResult.status == 0) {
                                SimpleCallback simpleCallback4 = simpleCallback;
                                if (simpleCallback4 != null) {
                                    simpleCallback4.onCallback(bLECycleSceneGetResult);
                                    return;
                                }
                                return;
                            }
                            SimpleCallback simpleCallback5 = simpleCallback;
                            if (simpleCallback5 != null) {
                                simpleCallback5.onCallback(new BLECycleSceneGetResult(-3004, "http request return error"));
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            SimpleCallback simpleCallback6 = simpleCallback;
                            if (simpleCallback6 != null) {
                                simpleCallback6.onCallback(new BLECycleSceneGetResult(-3011, "parse json fail"));
                                return;
                            }
                            return;
                        }
                    }
                    SimpleCallback simpleCallback7 = simpleCallback;
                    if (simpleCallback7 != null) {
                        simpleCallback7.onCallback(new BLECycleSceneGetResult(-3003, response.message()));
                    }
                }

                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void failed(Call call, IOException iOException) {
                    SimpleCallback simpleCallback2 = simpleCallback;
                    if (simpleCallback2 != null) {
                        simpleCallback2.onCallback(new BLECycleSceneGetResult(-3001, "http request return error"));
                    }
                }
            });
        } else if (simpleCallback != null) {
            simpleCallback.onCallback(new BLECycleSceneGetResult(-3002, "param error"));
        }
    }

    public static void getDetail(BLEDeviceInfo bLEDeviceInfo, int i, final SimpleCallback<BLECycleSceneGetDetailResult> simpleCallback) {
        if (bLEDeviceInfo == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneGetDetailResult(-3002, "param error"));
            }
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cscene_idx", (Object) String.valueOf(i));
            GatewayRemoteCtrlHelper.getInstance().doRequest(bLEDeviceInfo, DNA_BLE_CYCLE_QUERY_DETAIL_REQ, jSONObject.toString().getBytes(StandardCharsets.UTF_8), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayCycleSceneHelper.2
                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void success(Call call, Response response) throws IOException {
                    GatewayCycleSceneHelper.parseDetailInfo(response, simpleCallback);
                }

                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void failed(Call call, IOException iOException) {
                    SimpleCallback simpleCallback2 = simpleCallback;
                    if (simpleCallback2 != null) {
                        simpleCallback2.onCallback(new BLECycleSceneGetDetailResult(-3001, "http request return error"));
                    }
                }
            });
        }
    }

    public static void execute(BLEDeviceInfo bLEDeviceInfo, int i, final SimpleCallback<BLECycleSceneSetResult> simpleCallback) {
        if (bLEDeviceInfo == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneSetResult(-3002, "param error"));
            }
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cscene_idx", (Object) String.valueOf(i));
            GatewayRemoteCtrlHelper.getInstance().doRequest(bLEDeviceInfo, DNA_BLE_CYCLE_EXE_REQ, jSONObject.toString().getBytes(StandardCharsets.UTF_8), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayCycleSceneHelper.3
                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void success(Call call, Response response) throws IOException {
                    GatewayCycleSceneHelper.extracted(response, simpleCallback);
                }

                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void failed(Call call, IOException iOException) {
                    SimpleCallback simpleCallback2 = simpleCallback;
                    if (simpleCallback2 != null) {
                        simpleCallback2.onCallback(new BLECycleSceneSetResult(-3001, "http request return error"));
                    }
                }
            });
        }
    }

    public static void delete(BLEDeviceInfo bLEDeviceInfo, int i, final SimpleCallback<BLECycleSceneSetResult> simpleCallback) {
        if (bLEDeviceInfo == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneSetResult(-3002, "param error"));
            }
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cscene_idx", (Object) String.valueOf(i));
            GatewayRemoteCtrlHelper.getInstance().doRequest(bLEDeviceInfo, DNA_BLE_CYCLE_DEL_REQ, jSONObject.toString().getBytes(StandardCharsets.UTF_8), new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayCycleSceneHelper.4
                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void success(Call call, Response response) throws IOException {
                    GatewayCycleSceneHelper.extracted(response, simpleCallback);
                }

                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void failed(Call call, IOException iOException) {
                    SimpleCallback simpleCallback2 = simpleCallback;
                    if (simpleCallback2 != null) {
                        simpleCallback2.onCallback(new BLECycleSceneSetResult(-3001, "http request return error"));
                    }
                }
            });
        }
    }

    public static void addOrUpdate(BLEDeviceInfo bLEDeviceInfo, BLECycleSceneInfo bLECycleSceneInfo, final SimpleCallback<BLECycleSceneSetResult> simpleCallback) {
        if (bLEDeviceInfo == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneSetResult(-3002, "param error"));
                return;
            }
            return;
        }
        byte[] byteFromObj = parseByteFromObj(bLECycleSceneInfo);
        if (byteFromObj.length <= 1360) {
            GatewayRemoteCtrlHelper.getInstance().doRequest(bLEDeviceInfo, DNA_BLE_CYCLE_ADD_REQ, byteFromObj, new EOkHttpUtils.HttpCallback() { // from class: cn.com.broadlink.blelight.helper.GatewayCycleSceneHelper.5
                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void success(Call call, Response response) throws IOException {
                    GatewayCycleSceneHelper.extracted(response, simpleCallback);
                }

                @Override // cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.HttpCallback
                public void failed(Call call, IOException iOException) {
                    SimpleCallback simpleCallback2 = simpleCallback;
                    if (simpleCallback2 != null) {
                        simpleCallback2.onCallback(new BLECycleSceneSetResult(-3001, "http request return error"));
                    }
                }
            });
        } else if (simpleCallback != null) {
            simpleCallback.onCallback(new BLECycleSceneSetResult(-3120, "input command is too long"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void extracted(Response response, SimpleCallback<BLECycleSceneSetResult> simpleCallback) {
        if (!response.getIsSuccessful() || response.body() == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneSetResult(-3003, response.message()));
                return;
            }
            return;
        }
        JSONObject httpResponse = parseHttpResponse(response, true);
        if (httpResponse != null && httpResponse.containsKey("data")) {
            try {
                BLECycleSceneSetResult bLECycleSceneSetResult = (BLECycleSceneSetResult) JSON.parseObject(httpResponse.getString("data"), BLECycleSceneSetResult.class);
                if (simpleCallback != null) {
                    simpleCallback.onCallback(bLECycleSceneSetResult);
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                if (simpleCallback != null) {
                    simpleCallback.onCallback(new BLECycleSceneSetResult(-3011, "parse json fail"));
                    return;
                }
                return;
            }
        }
        if (httpResponse == null || !httpResponse.containsKey(NotificationCompat.CATEGORY_STATUS)) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneSetResult(-3010, "server return data error"));
            }
        } else {
            Integer integer = httpResponse.getInteger(NotificationCompat.CATEGORY_STATUS);
            String string = httpResponse.getString("message");
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneSetResult(integer.intValue(), string));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void parseDetailInfo(Response response, SimpleCallback<BLECycleSceneGetDetailResult> simpleCallback) {
        if (!response.getIsSuccessful() || response.body() == null) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneGetDetailResult(-3003, response.message()));
                return;
            }
            return;
        }
        JSONObject httpResponse = parseHttpResponse(response, false);
        if (httpResponse != null && httpResponse.containsKey("data")) {
            if (simpleCallback != null) {
                try {
                    simpleCallback.onCallback(new BLECycleSceneGetDetailResult(parseObjFromByte(EConvertUtils.hexStr2Bytes(httpResponse.getString("data")))));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    simpleCallback.onCallback(new BLECycleSceneGetDetailResult(-3011, "parse json fail"));
                    return;
                }
            }
            return;
        }
        if (httpResponse == null || !httpResponse.containsKey(NotificationCompat.CATEGORY_STATUS)) {
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneGetDetailResult(-3010, "server return data error"));
            }
        } else {
            Integer integer = httpResponse.getInteger(NotificationCompat.CATEGORY_STATUS);
            String string = httpResponse.getString("message");
            if (simpleCallback != null) {
                simpleCallback.onCallback(new BLECycleSceneGetDetailResult(integer.intValue(), string));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject parseHttpResponse(Response response, boolean z) {
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
                        String string = jSONObject.getString("data");
                        if (z) {
                            String strBase64Decode = EEncryptUtils.base64Decode(string);
                            if (strBase64Decode.length() > 12) {
                                int iIndexOf = strBase64Decode.substring(12).startsWith("{\"") ? 12 : strBase64Decode.indexOf("{\"");
                                if (iIndexOf > 0) {
                                    String strSubstring = strBase64Decode.substring(iIndexOf);
                                    JSONObject jSONObject3 = new JSONObject();
                                    jSONObject3.put("data", (Object) strSubstring);
                                    return jSONObject3;
                                }
                            }
                        } else {
                            String strBase64DecodeHex = EEncryptUtils.base64DecodeHex(string);
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put("data", (Object) strBase64DecodeHex);
                            return jSONObject4;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject jSONObject5 = new JSONObject();
        jSONObject5.put(NotificationCompat.CATEGORY_STATUS, (Object) (-3009));
        jSONObject5.put("message", (Object) "parse data fail");
        return jSONObject5;
    }

    private static byte[] parseByteFromObj(BLECycleSceneInfo bLECycleSceneInfo) {
        if (bLECycleSceneInfo == null) {
            return new byte[0];
        }
        StringBuilder sb = new StringBuilder("5a");
        sb.append(EConvertUtils.to16(38));
        sb.append(EConvertUtils.to16(bLECycleSceneInfo.idx));
        sb.append(EConvertUtils.to16(bLECycleSceneInfo.cnt));
        sb.append(EConvertUtils.to16(bLECycleSceneInfo.commands.size()));
        sb.append(EConvertUtils.bytes2HexStr(EConvertUtils.getBytes(bLECycleSceneInfo.name, 33)));
        if (bLECycleSceneInfo.commands != null) {
            for (BLECycleSceneCommand bLECycleSceneCommand : bLECycleSceneInfo.commands) {
                sb.append(EConvertUtils.bytes2HexStr(EConvertUtils.numberToByte(bLECycleSceneCommand.interval)));
                sb.append(EConvertUtils.to16(bLECycleSceneCommand.type));
                sb.append(EConvertUtils.to16(bLECycleSceneCommand.command.length() / 2));
                sb.append(bLECycleSceneCommand.command);
            }
        }
        String string = sb.toString();
        ELogUtils.d("jyq_cycle", "parseByteFromObj: " + string);
        return EConvertUtils.hexStr2Bytes(string);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static BLECycleSceneInfo parseObjFromByte(byte[] bArr) {
        int i;
        int i2;
        char c;
        ELogUtils.d("jyq_cycle", "parseObjFromByte input: " + EConvertUtils.bytes2HexStr(bArr));
        BLECycleSceneInfo bLECycleSceneInfo = new BLECycleSceneInfo();
        if (bArr != null && bArr.length > 50 && bArr.length < 1460) {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 12, bArr.length);
            bLECycleSceneInfo.idx = bArrCopyOfRange[2] & 255;
            bLECycleSceneInfo.cnt = bArrCopyOfRange[3] & 255;
            int i3 = 5;
            int i4 = 38;
            bLECycleSceneInfo.name = new String(Arrays.copyOfRange(bArrCopyOfRange, 5, 38), StandardCharsets.UTF_8);
            int i5 = 4;
            int i6 = bArrCopyOfRange[4] & 255;
            char c2 = 0;
            int i7 = 0;
            while (i7 < i6) {
                BLECycleSceneCommand bLECycleSceneCommand = new BLECycleSceneCommand();
                bLECycleSceneCommand.interval = (short) ((bArrCopyOfRange[i4] & 255) + ((bArrCopyOfRange[i4 + 1] & 255) * 256));
                bLECycleSceneCommand.type = bArrCopyOfRange[i4 + 2] & 255;
                int i8 = bArrCopyOfRange[i4 + 3] & 255;
                int i9 = i4 + 4;
                byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArrCopyOfRange, i9, i9 + i8);
                i4 += i8 + i5;
                if (bArrCopyOfRange2.length > i3) {
                    byte b = bArrCopyOfRange2[c2];
                    int i10 = b & BSON.CODE_W_SCOPE;
                    byte b2 = bArrCopyOfRange2[i5];
                    i = i5;
                    int i11 = b2 & BSON.CODE_W_SCOPE;
                    int i12 = (b2 >> 4) & 15;
                    i2 = i3;
                    if (i7 == 0) {
                        bLECycleSceneInfo.forward = ((b >> 7) & 1) == 1 ? 1 : c2;
                    }
                    int i13 = bLECycleSceneCommand.type;
                    if (i13 != 0) {
                        c = c2;
                        if (i13 == 1) {
                            if (i11 == 3) {
                                bLECycleSceneCommand.exeId = bArrCopyOfRange2[7] & 255;
                                if (bLECycleSceneCommand.exeId < 193) {
                                    int i14 = ((bArrCopyOfRange2[6] & 255) * 256) + (bArrCopyOfRange2[i2] & 255);
                                    if (i14 == 43050) {
                                        bLECycleSceneCommand.exeId += 256;
                                    } else if (i14 == 43499) {
                                        bLECycleSceneCommand.exeId += 512;
                                    }
                                }
                            }
                            bLECycleSceneCommand.command = EConvertUtils.bytes2HexStr(bArrCopyOfRange2).substring(16, (i12 * 2) + 10);
                            Locale locale = Locale.ENGLISH;
                            Object[] objArr = new Object[1];
                            objArr[c] = Integer.valueOf(bLECycleSceneCommand.exeId);
                            ELogUtils.i("jyq_cycle", String.format(locale, "group---> groupId[%d]", objArr));
                        } else if (i13 == 2) {
                            if (i11 == 11) {
                                bLECycleSceneCommand.exeId = ((bArrCopyOfRange2[i2] & 255) << 16) + ((bArrCopyOfRange2[6] & 255) << 8) + (bArrCopyOfRange2[7] & 255);
                            }
                            Locale locale2 = Locale.ENGLISH;
                            Object[] objArr2 = new Object[1];
                            objArr2[c] = Integer.valueOf(bLECycleSceneCommand.exeId);
                            ELogUtils.i("jyq_cycle", String.format(locale2, "scene---> sceneId[%d]", objArr2));
                        }
                    } else {
                        c = c2;
                        if (i11 == 2) {
                            bLECycleSceneCommand.exeId = (bArrCopyOfRange2[i2] & 255) + (i10 * 256);
                        }
                        bLECycleSceneCommand.command = EConvertUtils.bytes2HexStr(bArrCopyOfRange2).substring(12, (i12 * 2) + 10);
                        Locale locale3 = Locale.ENGLISH;
                        Object[] objArr3 = new Object[1];
                        objArr3[c] = Integer.valueOf(bLECycleSceneCommand.exeId);
                        ELogUtils.i("jyq_cycle", String.format(locale3, "dev---> addr[%d]", objArr3));
                    }
                    bLECycleSceneInfo.commands.add(bLECycleSceneCommand);
                } else {
                    i = i5;
                    i2 = i3;
                    c = c2;
                }
                i7++;
                i5 = i;
                i3 = i2;
                c2 = c;
            }
        }
        ELogUtils.d("jyq_cycle", "parseObjFromByte: " + JSON.toJSONString(bLECycleSceneInfo));
        return bLECycleSceneInfo;
    }
}

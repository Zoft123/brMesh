package com.brgd.brblmesh.GeneralClass;

import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public class DevOtaCloudHelper {
    public static final String URL_DOWNLOAD = "https://app-service-%s%s";
    public static final String URL_GET_VERSION = "https://app-service-%s/getfwversion?devicetype=%s&subpid=%s&hwcode=%s&vendorid=%s&ability=%s&appplatform=%s";

    public static class OtaVersionBean {
        public ChangelogBean changelog;
        public String date;
        public String hash512;
        public String hashsign;
        public int upgradeflag;
        public String url;
        public String version;

        public static class ChangelogBean {

            /* JADX INFO: renamed from: cn, reason: collision with root package name */
            public String f6cn;
            public String en;
        }
    }

    public static class OtaVersionResult {
        public OtaVersionBean data;
        public String msg;
        public int status;
    }

    public static OtaVersionResult getDevOtaVersion(String str, String str2, int i, int i2, String str3) {
        OtaVersionResult otaVersionResult = new OtaVersionResult();
        try {
            String[] strArrSplit = str3.split("\\.");
            if (strArrSplit.length != 5) {
                throw new IllegalArgumentException("Invalid device version format");
            }
            int i3 = Integer.parseInt(strArrSplit[0]);
            int i4 = Integer.parseInt(strArrSplit[1]);
            long j = Long.parseLong(strArrSplit[2]);
            int i5 = Integer.parseInt(strArrSplit[3]);
            Integer.parseInt(strArrSplit[4]);
            String str4 = String.format(Locale.ENGLISH, URL_GET_VERSION, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Long.valueOf((j << 32) + ((long) i5)), Integer.valueOf(i3));
            HashMap map = new HashMap();
            map.put("licenseid", str2);
            String str5 = HttpUtil.get(str4, map);
            if (str5 == null || str5.trim().isEmpty()) {
                throw new IOException("Empty response body");
            }
            JSONObject object = JSON.parseObject(str5);
            if (object.containsKey(NotificationCompat.CATEGORY_STATUS)) {
                otaVersionResult.status = object.getInteger(NotificationCompat.CATEGORY_STATUS).intValue();
                otaVersionResult.msg = object.getString(NotificationCompat.CATEGORY_MESSAGE);
                return otaVersionResult;
            }
            JSONArray jSONArray = object.getJSONObject(String.valueOf(i3)).getJSONArray("versions");
            if (jSONArray == null || jSONArray.isEmpty()) {
                throw new IllegalStateException("No firmware versions found in the response");
            }
            TreeMap treeMap = new TreeMap();
            for (int i6 = 0; i6 < jSONArray.size(); i6++) {
                OtaVersionBean otaVersionBean = (OtaVersionBean) jSONArray.getObject(i6, OtaVersionBean.class);
                treeMap.put(otaVersionBean.version, otaVersionBean);
            }
            Map.Entry entryLastEntry = treeMap.lastEntry();
            if (entryLastEntry != null) {
                otaVersionResult.data = (OtaVersionBean) entryLastEntry.getValue();
                otaVersionResult.status = 0;
                return otaVersionResult;
            }
            throw new IllegalStateException("No firmware versions found in the sorted map");
        } catch (IOException unused) {
            otaVersionResult.status = -1;
            otaVersionResult.msg = "网络请求失败";
            return otaVersionResult;
        } catch (Exception unused2) {
            otaVersionResult.status = -2;
            otaVersionResult.msg = "数据解析失败";
            return otaVersionResult;
        }
    }

    public static int downloadOtaZip(String str, String str2, OtaVersionBean otaVersionBean, String str3) {
        try {
            HashMap map = new HashMap();
            map.put("licenseid", str2);
            map.put("hashsign", otaVersionBean.hashsign);
            map.put("hash512", otaVersionBean.hash512);
            HttpUtil.downloadFile(String.format(Locale.ENGLISH, URL_DOWNLOAD, str, otaVersionBean.url), map, str3);
            return 0;
        } catch (IOException unused) {
            return -1;
        } catch (Exception unused2) {
            return -2;
        }
    }
}

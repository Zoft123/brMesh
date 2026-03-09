package com.brgd.brblmesh.GeneralClass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class HttpUtil {
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;
    private static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

    public static String get(String str, Map<String, String> map) throws IOException {
        for (int i = 0; i < 3; i++) {
            try {
                Request.Builder builderUrl = new Request.Builder().url(str);
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builderUrl.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                Response responseExecute = client.newCall(builderUrl.build()).execute();
                try {
                    if (!responseExecute.getIsSuccessful() || responseExecute.body() == null) {
                        throw new IOException("Unexpected code " + responseExecute.code());
                    }
                    String strString = responseExecute.body().string();
                    if (responseExecute != null) {
                        responseExecute.close();
                    }
                    return strString;
                } finally {
                }
            } catch (IOException e) {
                if (i < 2) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Interrupted while retrying GET request", e2);
                    }
                } else {
                    throw e;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00c3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0107 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void downloadFile(java.lang.String r8, java.util.Map<java.lang.String, java.lang.String> r9, java.lang.String r10) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.brgd.brblmesh.GeneralClass.HttpUtil.downloadFile(java.lang.String, java.util.Map, java.lang.String):void");
    }
}

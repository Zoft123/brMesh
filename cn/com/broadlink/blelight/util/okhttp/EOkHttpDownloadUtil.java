package cn.com.broadlink.blelight.util.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class EOkHttpDownloadUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000, TimeUnit.MILLISECONDS).readTimeout(10000, TimeUnit.MILLISECONDS).writeTimeout(10000, TimeUnit.MILLISECONDS).build();

    public interface ProgressListener {
        void onProgress(long j, long j2, boolean z);
    }

    public static void downloadFile(String str, final ProgressListener progressListener, Callback callback) {
        okHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpDownloadUtil.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Response responseProceed = chain.proceed(chain.request());
                return responseProceed.newBuilder().body(new ProgressResponseBody(responseProceed.body(), progressListener)).build();
            }
        }).build().newCall(new Request.Builder().url(str).build()).enqueue(callback);
    }
}

package cn.com.broadlink.blelight.util.okhttp;

import android.os.Handler;
import android.os.Looper;
import cn.com.broadlink.blelight.util.ELogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import j$.util.Objects;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import kotlin.text.Typography;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/* JADX INFO: loaded from: classes.dex */
public class EOkHttpUtils {
    private static EOkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final byte[] LOCKER = new byte[0];

    public interface HttpCallback {
        void failed(Call call, IOException iOException);

        void success(Call call, Response response) throws IOException;
    }

    static /* synthetic */ boolean lambda$init$2(String str, SSLSession sSLSession) {
        return true;
    }

    private EOkHttpUtils() {
    }

    public static EOkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new EOkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        init(null);
    }

    public void init(final EOkHttpConfig eOkHttpConfig) {
        if (eOkHttpConfig == null) {
            eOkHttpConfig = new EOkHttpConfig();
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils$$ExternalSyntheticLambda0
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public final void log(String str) {
                ELogUtils.d(eOkHttpConfig.getLogTag(), str);
            }
        });
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(eOkHttpConfig.getReadTimeout(), TimeUnit.SECONDS);
        builder.connectTimeout(eOkHttpConfig.getConnectTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(eOkHttpConfig.getWriteTimeout(), TimeUnit.SECONDS);
        if (eOkHttpConfig.isDebugOn()) {
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }
        builder.addInterceptor(new Interceptor() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils$$ExternalSyntheticLambda1
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                return this.f$0.m484lambda$init$1$cncombroadlinkblelightutilokhttpEOkHttpUtils(eOkHttpConfig, chain);
            }
        });
        builder.sslSocketFactory(createSSLSocketFactory(), ETrustManager.getInstance());
        builder.hostnameVerifier(new HostnameVerifier() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils$$ExternalSyntheticLambda2
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                return EOkHttpUtils.lambda$init$2(str, sSLSession);
            }
        });
        this.mOkHttpClient = builder.build();
    }

    /* JADX INFO: renamed from: lambda$init$1$cn-com-broadlink-blelight-util-okhttp-EOkHttpUtils, reason: not valid java name */
    /* synthetic */ Response m484lambda$init$1$cncombroadlinkblelightutilokhttpEOkHttpUtils(EOkHttpConfig eOkHttpConfig, Interceptor.Chain chain) throws IOException {
        Request.Builder builderNewBuilder = chain.request().newBuilder();
        appendHeader(eOkHttpConfig.getCommonHeader(), builderNewBuilder);
        return chain.proceed(builderNewBuilder.build());
    }

    public Response get(String str, Map<String, String> map, Object obj) {
        try {
            return genGetCall(str, map, obj).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T get(String str, Map<String, String> map, Object obj, Class<T> cls) {
        Response responseExecute;
        try {
            responseExecute = genGetCall(str, map, obj).execute();
        } catch (IOException e) {
            e.printStackTrace();
            responseExecute = null;
        }
        return (T) parseObject(responseExecute, cls);
    }

    public Response post(String str, Map<String, String> map, Map<String, String> map2) {
        try {
            return genPostCall(str, map, map2).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response post(String str, Map<String, String> map, Object obj) {
        try {
            return genPostCall(str, map, obj).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T post(String str, Map<String, String> map, Map<String, String> map2, Class<T> cls) {
        Response responseExecute;
        try {
            responseExecute = genPostCall(str, map, map2).execute();
        } catch (IOException e) {
            e.printStackTrace();
            responseExecute = null;
        }
        return (T) parseObject(responseExecute, cls);
    }

    public <T> T post(String str, Map<String, String> map, Object obj, Class<T> cls) {
        Response responseExecute;
        try {
            responseExecute = genPostCall(str, map, obj).execute();
        } catch (IOException e) {
            e.printStackTrace();
            responseExecute = null;
        }
        return (T) parseObject(responseExecute, cls);
    }

    public void getAsync(String str, Map<String, String> map, Object obj, final HttpCallback httpCallback) {
        genGetCall(str, map, obj).enqueue(new Callback() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                httpCallback.failed(call, iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                httpCallback.success(call, response);
            }
        });
    }

    public void postAsync(String str, Map<String, String> map, Map<String, String> map2, final HttpCallback httpCallback) {
        genPostCall(str, map, map2).enqueue(new Callback() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                httpCallback.failed(call, iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                httpCallback.success(call, response);
            }
        });
    }

    public void postAsync(String str, Map<String, String> map, Object obj, final HttpCallback httpCallback) {
        genPostCall(str, map, obj).enqueue(new Callback() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.3
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                httpCallback.failed(call, iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                httpCallback.success(call, response);
            }
        });
    }

    private RequestBody setRequestBody(Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String string = it.next().toString();
                builder.add(string, map.get(string));
            }
        }
        return builder.build();
    }

    public String postJson(String str, String str2) throws IOException {
        Response responseExecute = this.mOkHttpClient.newCall(new Request.Builder().url(str).post(RequestBody.create(str2, TYPE_JSON)).build()).execute();
        if (responseExecute.getIsSuccessful()) {
            return responseExecute.body().string();
        }
        throw new IOException("Unexpected code " + responseExecute);
    }

    public String postJson(String str, Object obj, String str2) throws IOException {
        RequestBody requestBodyCreate = RequestBody.create(str2, TYPE_JSON);
        Request.Builder builder = new Request.Builder();
        appendHeader(parseHeaderFromObj(obj), builder);
        Response responseExecute = this.mOkHttpClient.newCall(builder.url(str).post(requestBodyCreate).build()).execute();
        if (responseExecute.getIsSuccessful()) {
            return responseExecute.body().string();
        }
        throw new IOException("Unexpected code " + responseExecute);
    }

    public void postJsonAsync(String str, String str2, final HttpCallback httpCallback) throws IOException {
        this.mOkHttpClient.newCall(new Request.Builder().post(RequestBody.create(str2, TYPE_JSON)).url(str).build()).enqueue(new Callback() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.4
            @Override // okhttp3.Callback
            public void onFailure(final Call call, final IOException iOException) {
                if (httpCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            httpCallback.failed(call, iOException);
                        }
                    });
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(final Call call, final Response response) {
                if (httpCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                httpCallback.success(call, response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    public void postJsonAsync(String str, Object obj, String str2, final HttpCallback httpCallback) throws IOException {
        RequestBody requestBodyCreate = RequestBody.create(str2, TYPE_JSON);
        Request.Builder builder = new Request.Builder();
        appendHeader(parseHeaderFromObj(obj), builder);
        this.mOkHttpClient.newCall(builder.post(requestBodyCreate).url(str).build()).enqueue(new Callback() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.5
            @Override // okhttp3.Callback
            public void onFailure(final Call call, final IOException iOException) {
                if (httpCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.5.1
                        @Override // java.lang.Runnable
                        public void run() {
                            httpCallback.failed(call, iOException);
                        }
                    });
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(final Call call, final Response response) throws IOException {
                if (httpCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.com.broadlink.blelight.util.okhttp.EOkHttpUtils.5.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                httpCallback.success(call, response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    public SSLSocketFactory createSSLSocketFactory() {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{ETrustManager.getInstance()}, new SecureRandom());
            return sSLContext.getSocketFactory();
        } catch (Exception unused) {
            return null;
        }
    }

    public static <T> T parseObject(Response response, Class<T> cls) {
        if (response == null) {
            return null;
        }
        try {
            return (T) JSON.parseObject(((ResponseBody) Objects.requireNonNull(response.body())).string(), cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void appendHeader(Map<String, String> map, Request.Builder builder) {
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private Call genGetCall(String str, Map<String, String> map, Object obj) {
        Request.Builder builder = new Request.Builder();
        appendHeader(map, builder);
        if (obj != null) {
            str = parseHeaderFromObj(str, obj);
        }
        return this.mOkHttpClient.newCall(builder.get().url(str).build());
    }

    private Call genPostCall(String str, Map<String, String> map, Map<String, String> map2) {
        Request.Builder builder = new Request.Builder();
        RequestBody requestBody = setRequestBody(map2);
        appendHeader(map, builder);
        return this.mOkHttpClient.newCall(builder.post(requestBody).url(str).build());
    }

    private Call genPostCall(String str, Map<String, String> map, Object obj) {
        RequestBody requestBody;
        Request.Builder builder = new Request.Builder();
        if (obj instanceof byte[]) {
            requestBody = RequestBody.create((byte[]) obj, MediaType.parse("application/octet-stream"));
        } else {
            requestBody = setRequestBody(parseHeaderFromObj(obj));
        }
        appendHeader(map, builder);
        return this.mOkHttpClient.newCall(builder.post(requestBody).url(str).build());
    }

    public HashMap<String, String> parseHeaderFromObj(Object obj) {
        HashMap<String, String> map = new HashMap<>();
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            for (String str : jSONObject.keySet()) {
                map.put(str, jSONObject.getString(str));
            }
        } else {
            try {
                for (Field field : EObjFieldParseUtils.getFields(obj.getClass(), Object.class)) {
                    field.setAccessible(true);
                    if (field.get(obj) != null) {
                        map.put(field.getName(), String.valueOf(field.get(obj)));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return map;
            }
        }
        return map;
    }

    public String parseHeaderFromObj(String str, Object obj) {
        List<Field> fields = EObjFieldParseUtils.getFields(obj.getClass(), Object.class);
        StringBuilder sb = new StringBuilder();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) != null && !field.getName().equals("CREATOR")) {
                    sb.append(Typography.amp);
                    sb.append(URLEncoder.encode(field.getName(), "utf-8"));
                    sb.append('=');
                    sb.append(URLEncoder.encode(String.valueOf(field.get(obj)), "utf-8"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        if (sb.length() <= 0) {
            return str;
        }
        sb.replace(0, 1, "?");
        return str + sb.toString();
    }
}

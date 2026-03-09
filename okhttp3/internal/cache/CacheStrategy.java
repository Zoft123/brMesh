package okhttp3.internal.cache;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal.http.DateFormattingKt;

/* JADX INFO: compiled from: CacheStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \r2\u00020\u0001:\u0002\f\rB\u001d\b\u0000\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lokhttp3/internal/cache/CacheStrategy;", "", "networkRequest", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "<init>", "(Lokhttp3/Request;Lokhttp3/Response;)V", "getNetworkRequest", "()Lokhttp3/Request;", "getCacheResponse", "()Lokhttp3/Response;", "Factory", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CacheStrategy {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Response cacheResponse;
    private final Request networkRequest;

    public CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    public final Request getNetworkRequest() {
        return this.networkRequest;
    }

    public final Response getCacheResponse() {
        return this.cacheResponse;
    }

    /* JADX INFO: compiled from: CacheStrategy.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001bH\u0002J\b\u0010\u001d\u001a\u00020\u0003H\u0002J\b\u0010\u001e\u001a\u00020\u0003H\u0002J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lokhttp3/internal/cache/CacheStrategy$Factory;", "", "nowMillis", "", "request", "Lokhttp3/Request;", "cacheResponse", "Lokhttp3/Response;", "<init>", "(JLokhttp3/Request;Lokhttp3/Response;)V", "getRequest$okhttp", "()Lokhttp3/Request;", "servedDate", "Ljava/util/Date;", "servedDateString", "", "lastModified", "lastModifiedString", "expires", "sentRequestMillis", "receivedResponseMillis", "etag", "ageSeconds", "", "isFreshnessLifetimeHeuristic", "", "compute", "Lokhttp3/internal/cache/CacheStrategy;", "computeCandidate", "computeFreshnessLifetime", "cacheResponseAge", "hasConditions", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Factory {
        private int ageSeconds;
        private final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        private final long nowMillis;
        private long receivedResponseMillis;
        private final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long j, Request request, Response response) {
            Intrinsics.checkNotNullParameter(request, "request");
            this.nowMillis = j;
            this.request = request;
            this.cacheResponse = response;
            this.ageSeconds = -1;
            if (response != null) {
                this.sentRequestMillis = response.sentRequestAtMillis();
                this.receivedResponseMillis = response.receivedResponseAtMillis();
                Headers headers = response.headers();
                int size = headers.size();
                for (int i = 0; i < size; i++) {
                    String strName = headers.name(i);
                    String strValue = headers.value(i);
                    if (StringsKt.equals(strName, "Date", true)) {
                        this.servedDate = DateFormattingKt.toHttpDateOrNull(strValue);
                        this.servedDateString = strValue;
                    } else if (StringsKt.equals(strName, "Expires", true)) {
                        this.expires = DateFormattingKt.toHttpDateOrNull(strValue);
                    } else if (StringsKt.equals(strName, "Last-Modified", true)) {
                        this.lastModified = DateFormattingKt.toHttpDateOrNull(strValue);
                        this.lastModifiedString = strValue;
                    } else if (StringsKt.equals(strName, "ETag", true)) {
                        this.etag = strValue;
                    } else if (StringsKt.equals(strName, "Age", true)) {
                        this.ageSeconds = _UtilCommonKt.toNonNegativeInt(strValue, -1);
                    }
                }
            }
        }

        /* JADX INFO: renamed from: getRequest$okhttp, reason: from getter */
        public final Request getRequest() {
            return this.request;
        }

        private final boolean isFreshnessLifetimeHeuristic() {
            Response response = this.cacheResponse;
            Intrinsics.checkNotNull(response);
            return response.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        public final CacheStrategy compute() {
            CacheStrategy cacheStrategyComputeCandidate = computeCandidate();
            return (cacheStrategyComputeCandidate.getNetworkRequest() == null || !this.request.cacheControl().onlyIfCached()) ? cacheStrategyComputeCandidate : new CacheStrategy(null, null);
        }

        private final CacheStrategy computeCandidate() {
            String str;
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.INSTANCE.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            CacheControl cacheControl = this.request.cacheControl();
            if (cacheControl.noCache() || hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            CacheControl cacheControl2 = this.cacheResponse.cacheControl();
            long jCacheResponseAge = cacheResponseAge();
            long jComputeFreshnessLifetime = computeFreshnessLifetime();
            if (cacheControl.maxAgeSeconds() != -1) {
                jComputeFreshnessLifetime = Math.min(jComputeFreshnessLifetime, TimeUnit.SECONDS.toMillis(cacheControl.maxAgeSeconds()));
            }
            long millis = 0;
            long millis2 = cacheControl.minFreshSeconds() != -1 ? TimeUnit.SECONDS.toMillis(cacheControl.minFreshSeconds()) : 0L;
            if (!cacheControl2.mustRevalidate() && cacheControl.maxStaleSeconds() != -1) {
                millis = TimeUnit.SECONDS.toMillis(cacheControl.maxStaleSeconds());
            }
            if (!cacheControl2.noCache()) {
                long j = millis2 + jCacheResponseAge;
                if (j < millis + jComputeFreshnessLifetime) {
                    Response.Builder builderNewBuilder = this.cacheResponse.newBuilder();
                    if (j >= jComputeFreshnessLifetime) {
                        builderNewBuilder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (jCacheResponseAge > 86400000 && isFreshnessLifetimeHeuristic()) {
                        builderNewBuilder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(null, builderNewBuilder.build());
                }
            }
            String str2 = this.etag;
            if (str2 != null) {
                str = "If-None-Match";
            } else {
                if (this.lastModified != null) {
                    str2 = this.lastModifiedString;
                } else if (this.servedDate != null) {
                    str2 = this.servedDateString;
                } else {
                    return new CacheStrategy(this.request, null);
                }
                str = "If-Modified-Since";
            }
            Headers.Builder builderNewBuilder2 = this.request.headers().newBuilder();
            Intrinsics.checkNotNull(str2);
            builderNewBuilder2.addLenient$okhttp(str, str2);
            return new CacheStrategy(this.request.newBuilder().headers(builderNewBuilder2.build()).build(), this.cacheResponse);
        }

        private final long computeFreshnessLifetime() {
            Response response = this.cacheResponse;
            Intrinsics.checkNotNull(response);
            if (response.cacheControl().maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(r0.maxAgeSeconds());
            }
            Date date = this.expires;
            if (date != null) {
                Date date2 = this.servedDate;
                long time = date.getTime() - (date2 != null ? date2.getTime() : this.receivedResponseMillis);
                if (time > 0) {
                    return time;
                }
                return 0L;
            }
            if (this.lastModified != null && this.cacheResponse.request().url().query() == null) {
                Date date3 = this.servedDate;
                long time2 = date3 != null ? date3.getTime() : this.sentRequestMillis;
                Date date4 = this.lastModified;
                Intrinsics.checkNotNull(date4);
                long time3 = time2 - date4.getTime();
                if (time3 > 0) {
                    return time3 / ((long) 10);
                }
            }
            return 0L;
        }

        private final long cacheResponseAge() {
            Date date = this.servedDate;
            long jMax = date != null ? Math.max(0L, this.receivedResponseMillis - date.getTime()) : 0L;
            if (this.ageSeconds != -1) {
                jMax = Math.max(jMax, TimeUnit.SECONDS.toMillis(this.ageSeconds));
            }
            return jMax + Math.max(0L, this.receivedResponseMillis - this.sentRequestMillis) + Math.max(0L, this.nowMillis - this.receivedResponseMillis);
        }

        private final boolean hasConditions(Request request) {
            return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
        }
    }

    /* JADX INFO: compiled from: CacheStrategy.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lokhttp3/internal/cache/CacheStrategy$Companion;", "", "<init>", "()V", "isCacheable", "", "response", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x003b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isCacheable(okhttp3.Response r5, okhttp3.Request r6) {
            /*
                r4 = this;
                java.lang.String r0 = "response"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
                java.lang.String r0 = "request"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
                int r0 = r5.code()
                r1 = 200(0xc8, float:2.8E-43)
                r2 = 0
                if (r0 == r1) goto L65
                r1 = 410(0x19a, float:5.75E-43)
                if (r0 == r1) goto L65
                r1 = 414(0x19e, float:5.8E-43)
                if (r0 == r1) goto L65
                r1 = 501(0x1f5, float:7.02E-43)
                if (r0 == r1) goto L65
                r1 = 203(0xcb, float:2.84E-43)
                if (r0 == r1) goto L65
                r1 = 204(0xcc, float:2.86E-43)
                if (r0 == r1) goto L65
                r1 = 307(0x133, float:4.3E-43)
                if (r0 == r1) goto L3b
                r1 = 308(0x134, float:4.32E-43)
                if (r0 == r1) goto L65
                r1 = 404(0x194, float:5.66E-43)
                if (r0 == r1) goto L65
                r1 = 405(0x195, float:5.68E-43)
                if (r0 == r1) goto L65
                switch(r0) {
                    case 300: goto L65;
                    case 301: goto L65;
                    case 302: goto L3b;
                    default: goto L3a;
                }
            L3a:
                return r2
            L3b:
                java.lang.String r0 = "Expires"
                r1 = 2
                r3 = 0
                java.lang.String r0 = okhttp3.Response.header$default(r5, r0, r3, r1, r3)
                if (r0 != 0) goto L65
                okhttp3.CacheControl r0 = r5.cacheControl()
                int r0 = r0.maxAgeSeconds()
                r1 = -1
                if (r0 != r1) goto L65
                okhttp3.CacheControl r0 = r5.cacheControl()
                boolean r0 = r0.getIsPublic()
                if (r0 != 0) goto L65
                okhttp3.CacheControl r0 = r5.cacheControl()
                boolean r0 = r0.getIsPrivate()
                if (r0 != 0) goto L65
                return r2
            L65:
                okhttp3.CacheControl r5 = r5.cacheControl()
                boolean r5 = r5.noStore()
                if (r5 != 0) goto L7b
                okhttp3.CacheControl r5 = r6.cacheControl()
                boolean r5 = r5.noStore()
                if (r5 != 0) goto L7b
                r5 = 1
                return r5
            L7b:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.CacheStrategy.Companion.isCacheable(okhttp3.Response, okhttp3.Request):boolean");
        }
    }
}

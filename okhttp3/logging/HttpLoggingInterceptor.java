package okhttp3.logging;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.internal.IsProbablyUtf8Kt;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

/* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 +2\u00020\u0001:\u0003)*+B\u0013\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bJ\u001f\u0010\u0013\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0014\"\u00020\b¢\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u000bJ\r\u0010\r\u001a\u00020\u000bH\u0007¢\u0006\u0002\b\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0015\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0000¢\u0006\u0002\b\u001fJ\u0018\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020\u0019H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\f\u0010\u000f¨\u0006,"}, d2 = {"Lokhttp3/logging/HttpLoggingInterceptor;", "Lokhttp3/Interceptor;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "<init>", "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "headersToRedact", "", "", "queryParamsNameToRedact", "value", "Lokhttp3/logging/HttpLoggingInterceptor$Level;", "level", "getLevel", "()Lokhttp3/logging/HttpLoggingInterceptor$Level;", "(Lokhttp3/logging/HttpLoggingInterceptor$Level;)V", "redactHeader", "", GlobalVariable.NAME, "redactQueryParams", "", "([Ljava/lang/String;)V", "setLevel", "-deprecated_level", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "redactUrl", "url", "Lokhttp3/HttpUrl;", "redactUrl$logging_interceptor", "logHeader", "headers", "Lokhttp3/Headers;", "i", "", "bodyHasUnknownEncoding", "", "bodyIsStreaming", "response", "Level", "Logger", "Companion", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class HttpLoggingInterceptor implements Interceptor {
    private volatile Set<String> headersToRedact;
    private volatile Level level;
    private final Logger logger;
    private volatile Set<String> queryParamsNameToRedact;

    public HttpLoggingInterceptor() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public HttpLoggingInterceptor(Logger logger) {
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.logger = logger;
        this.headersToRedact = SetsKt.emptySet();
        this.queryParamsNameToRedact = SetsKt.emptySet();
        this.level = Level.NONE;
    }

    public /* synthetic */ HttpLoggingInterceptor(Logger logger, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Logger.DEFAULT : logger);
    }

    public final Level getLevel() {
        return this.level;
    }

    public final void level(Level level) {
        Intrinsics.checkNotNullParameter(level, "<set-?>");
        this.level = level;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lokhttp3/logging/HttpLoggingInterceptor$Level;", "", "<init>", "(Ljava/lang/String;I)V", "NONE", "BASIC", "HEADERS", "BODY", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Level {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ Level[] $VALUES;
        public static final Level NONE = new Level("NONE", 0);
        public static final Level BASIC = new Level("BASIC", 1);
        public static final Level HEADERS = new Level("HEADERS", 2);
        public static final Level BODY = new Level("BODY", 3);

        private static final /* synthetic */ Level[] $values() {
            return new Level[]{NONE, BASIC, HEADERS, BODY};
        }

        public static EnumEntries<Level> getEntries() {
            return $ENTRIES;
        }

        public static Level valueOf(String str) {
            return (Level) Enum.valueOf(Level.class, str);
        }

        public static Level[] values() {
            return (Level[]) $VALUES.clone();
        }

        private Level(String str, int i) {
        }

        static {
            Level[] levelArr$values = $values();
            $VALUES = levelArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(levelArr$values);
        }
    }

    /* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bæ\u0080\u0001\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "", "log", "", "message", "", "Companion", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public interface Logger {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;
        public static final Logger DEFAULT = new Companion.DefaultLogger();

        void log(String message);

        /* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0006B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0013\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0001¨\u0006\u0007"}, d2 = {"Lokhttp3/logging/HttpLoggingInterceptor$Logger$Companion;", "", "<init>", "()V", "DEFAULT", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "DefaultLogger", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            /* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
            @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lokhttp3/logging/HttpLoggingInterceptor$Logger$Companion$DefaultLogger;", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "<init>", "()V", "log", "", "message", "", "logging-interceptor"}, k = 1, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
            private static final class DefaultLogger implements Logger {
                @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
                public void log(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                    Platform.log$default(Platform.INSTANCE.get(), message, 0, null, 6, null);
                }
            }
        }
    }

    public final void redactHeader(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        TreeSet treeSet = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        TreeSet treeSet2 = treeSet;
        CollectionsKt.addAll(treeSet2, this.headersToRedact);
        treeSet2.add(name);
        this.headersToRedact = treeSet;
    }

    public final void redactQueryParams(String... name) {
        Intrinsics.checkNotNullParameter(name, "name");
        TreeSet treeSet = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        TreeSet treeSet2 = treeSet;
        CollectionsKt.addAll(treeSet2, this.queryParamsNameToRedact);
        CollectionsKt.addAll(treeSet2, name);
        this.queryParamsNameToRedact = treeSet;
    }

    public final HttpLoggingInterceptor setLevel(Level level) {
        Intrinsics.checkNotNullParameter(level, "level");
        this.level = level;
        return this;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to var", replaceWith = @ReplaceWith(expression = "level", imports = {}))
    /* JADX INFO: renamed from: -deprecated_level, reason: not valid java name and from getter */
    public final Level getLevel() {
        return this.level;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws Exception {
        boolean z;
        boolean z2;
        String str;
        long j;
        Long lValueOf;
        GzipSource gzipSource;
        Long lValueOf2;
        Intrinsics.checkNotNullParameter(chain, "chain");
        Level level = this.level;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean z3 = level == Level.BODY;
        boolean z4 = z3 || level == Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        Connection connection = chain.connection();
        StringBuilder sb = new StringBuilder("--> ");
        sb.append(request.method());
        sb.append(' ');
        sb.append(redactUrl$logging_interceptor(request.url()));
        sb.append(connection != null ? " " + connection.getProtocol() : "");
        String string = sb.toString();
        if (!z4 && requestBodyBody != null) {
            string = string + " (" + requestBodyBody.contentLength() + "-byte body)";
        }
        this.logger.log(string);
        if (!z4) {
            z = z4;
            z2 = z3;
            str = "-byte body omitted)";
            j = -1;
        } else {
            j = -1;
            Headers headers = request.headers();
            if (requestBodyBody != null) {
                MediaType mediaType = requestBodyBody.get$contentType();
                if (mediaType == null || headers.get("Content-Type") != null) {
                    z = z4;
                    z2 = z3;
                } else {
                    z = z4;
                    z2 = z3;
                    this.logger.log("Content-Type: " + mediaType);
                }
                if (requestBodyBody.contentLength() != -1 && headers.get("Content-Length") == null) {
                    this.logger.log("Content-Length: " + requestBodyBody.contentLength());
                }
            } else {
                z = z4;
                z2 = z3;
            }
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                logHeader(headers, i);
            }
            if (!z2 || requestBodyBody == null) {
                str = "-byte body omitted)";
                this.logger.log("--> END " + request.method());
            } else {
                if (bodyHasUnknownEncoding(request.headers())) {
                    this.logger.log("--> END " + request.method() + " (encoded body omitted)");
                } else if (requestBodyBody.isDuplex()) {
                    this.logger.log("--> END " + request.method() + " (duplex request body omitted)");
                } else if (requestBodyBody.isOneShot()) {
                    this.logger.log("--> END " + request.method() + " (one-shot body omitted)");
                } else {
                    Buffer buffer = new Buffer();
                    requestBodyBody.writeTo(buffer);
                    if (StringsKt.equals("gzip", headers.get("Content-Encoding"), true)) {
                        lValueOf2 = Long.valueOf(buffer.size());
                        gzipSource = new GzipSource(buffer);
                        try {
                            Buffer buffer2 = new Buffer();
                            buffer2.writeAll(gzipSource);
                            CloseableKt.closeFinally(gzipSource, null);
                            buffer = buffer2;
                        } finally {
                        }
                    } else {
                        lValueOf2 = null;
                    }
                    Charset charsetCharsetOrUtf8 = Internal.charsetOrUtf8(requestBodyBody.get$contentType());
                    this.logger.log("");
                    if (!IsProbablyUtf8Kt.isProbablyUtf8(buffer)) {
                        this.logger.log("--> END " + request.method() + " (binary " + requestBodyBody.contentLength() + "-byte body omitted)");
                    } else if (lValueOf2 == null) {
                        str = "-byte body omitted)";
                        this.logger.log(buffer.readString(charsetCharsetOrUtf8));
                        this.logger.log("--> END " + request.method() + " (" + requestBodyBody.contentLength() + "-byte body)");
                    } else {
                        Logger logger = this.logger;
                        StringBuilder sb2 = new StringBuilder("--> END ");
                        sb2.append(request.method());
                        sb2.append(" (");
                        str = "-byte body omitted)";
                        sb2.append(buffer.size());
                        sb2.append("-byte, ");
                        sb2.append(lValueOf2);
                        sb2.append("-gzipped-byte body)");
                        logger.log(sb2.toString());
                    }
                }
                str = "-byte body omitted)";
            }
        }
        long jNanoTime = System.nanoTime();
        try {
            Response responseProceed = chain.proceed(request);
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime);
            ResponseBody responseBodyBody = responseProceed.body();
            Intrinsics.checkNotNull(responseBodyBody);
            long contentLength = responseBodyBody.getContentLength();
            String str2 = contentLength != j ? contentLength + "-byte" : "unknown-length";
            Logger logger2 = this.logger;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("<-- " + responseProceed.code());
            if (responseProceed.message().length() > 0) {
                sb3.append(" " + responseProceed.message());
            }
            sb3.append(" " + redactUrl$logging_interceptor(responseProceed.request().url()) + " (" + millis + "ms");
            if (!z) {
                sb3.append(", " + str2 + " body");
            }
            sb3.append(")");
            logger2.log(sb3.toString());
            if (z) {
                Headers headers2 = responseProceed.headers();
                int size2 = headers2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    logHeader(headers2, i2);
                }
                if (!z2 || !HttpHeaders.promisesBody(responseProceed)) {
                    this.logger.log("<-- END HTTP");
                } else {
                    if (bodyHasUnknownEncoding(responseProceed.headers())) {
                        this.logger.log("<-- END HTTP (encoded body omitted)");
                        return responseProceed;
                    }
                    if (bodyIsStreaming(responseProceed)) {
                        this.logger.log("<-- END HTTP (streaming)");
                        return responseProceed;
                    }
                    BufferedSource bodySource = responseBodyBody.getSource();
                    bodySource.request(Long.MAX_VALUE);
                    long millis2 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime);
                    Buffer buffer3 = bodySource.getBuffer();
                    if (StringsKt.equals("gzip", headers2.get("Content-Encoding"), true)) {
                        lValueOf = Long.valueOf(buffer3.size());
                        gzipSource = new GzipSource(buffer3.clone());
                        try {
                            Buffer buffer4 = new Buffer();
                            buffer4.writeAll(gzipSource);
                            CloseableKt.closeFinally(gzipSource, null);
                            buffer3 = buffer4;
                        } finally {
                            try {
                                throw th;
                            } finally {
                            }
                        }
                    } else {
                        lValueOf = null;
                    }
                    Charset charsetCharsetOrUtf82 = Internal.charsetOrUtf8(responseBodyBody.getMediaType());
                    if (!IsProbablyUtf8Kt.isProbablyUtf8(buffer3)) {
                        this.logger.log("");
                        this.logger.log("<-- END HTTP (" + millis2 + "ms, binary " + buffer3.size() + str);
                        return responseProceed;
                    }
                    if (contentLength != 0) {
                        this.logger.log("");
                        this.logger.log(buffer3.clone().readString(charsetCharsetOrUtf82));
                    }
                    Logger logger3 = this.logger;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("<-- END HTTP (" + millis2 + "ms, " + buffer3.size() + "-byte");
                    if (lValueOf != null) {
                        sb4.append(", " + lValueOf + "-gzipped-byte");
                    }
                    sb4.append(" body)");
                    logger3.log(sb4.toString());
                    return responseProceed;
                }
            }
            return responseProceed;
        } catch (Exception e) {
            this.logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
    }

    public final String redactUrl$logging_interceptor(HttpUrl url) {
        Intrinsics.checkNotNullParameter(url, "url");
        if (this.queryParamsNameToRedact.isEmpty() || url.querySize() == 0) {
            return url.getUrl();
        }
        HttpUrl.Builder builderQuery = url.newBuilder().query(null);
        int iQuerySize = url.querySize();
        for (int i = 0; i < iQuerySize; i++) {
            String strQueryParameterName = url.queryParameterName(i);
            builderQuery.addEncodedQueryParameter(strQueryParameterName, this.queryParamsNameToRedact.contains(strQueryParameterName) ? "██" : url.queryParameterValue(i));
        }
        return builderQuery.toString();
    }

    private final void logHeader(Headers headers, int i) {
        String strValue = this.headersToRedact.contains(headers.name(i)) ? "██" : headers.value(i);
        this.logger.log(headers.name(i) + ": " + strValue);
    }

    private final boolean bodyHasUnknownEncoding(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || StringsKt.equals(str, "identity", true) || StringsKt.equals(str, "gzip", true)) ? false : true;
    }

    private final boolean bodyIsStreaming(Response response) {
        MediaType mediaType = response.body().getMediaType();
        return mediaType != null && Intrinsics.areEqual(mediaType.type(), "text") && Intrinsics.areEqual(mediaType.subtype(), "event-stream");
    }
}

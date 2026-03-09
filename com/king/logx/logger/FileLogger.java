package com.king.logx.logger;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.king.logx.LogX;
import com.king.logx.logger.config.FileLoggerConfig;
import com.king.logx.util.Utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* JADX INFO: compiled from: FileLogger.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0002\u000e\u0016\b\u0016\u0018\u0000 /2\u00020\u0001:\u0002/0B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001c\u001a\u00020\u0014H\u0014J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u0002J\u0012\u0010%\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020#H\u0002J.\u0010&\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u00142\b\u0010'\u001a\u0004\u0018\u00010(H\u0014J\"\u0010)\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001c\u001a\u00020\u0014H\u0014J\u0010\u0010*\u001a\u00020\u001e2\u0006\u0010\u001c\u001a\u00020\u0014H\u0002J\b\u0010+\u001a\u00020\u001eH\u0002J\u0006\u0010,\u001a\u00020\u001eJ\b\u0010-\u001a\u00020.H\u0002R\u0016\u0010\u0007\u001a\n \b*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017¨\u00061"}, d2 = {"Lcom/king/logx/logger/FileLogger;", "Lcom/king/logx/logger/DefaultLogger;", "context", "Landroid/content/Context;", "config", "Lcom/king/logx/logger/config/FileLoggerConfig;", "(Landroid/content/Context;Lcom/king/logx/logger/config/FileLoggerConfig;)V", "appContext", "kotlin.jvm.PlatformType", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "currentWriter", "Lcom/king/logx/logger/FileLogger$LogWriter;", "fileNameFormat", "com/king/logx/logger/FileLogger$fileNameFormat$1", "Lcom/king/logx/logger/FileLogger$fileNameFormat$1;", "isLogInProgress", "Ljava/util/concurrent/atomic/AtomicBoolean;", "logChannel", "Lkotlinx/coroutines/channels/Channel;", "", "logDateFormat", "com/king/logx/logger/FileLogger$logDateFormat$1", "Lcom/king/logx/logger/FileLogger$logDateFormat$1;", "buildMessage", "priority", "", "tag", "message", "cleanupOldLogs", "", "createLogWriter", "reuseRecentFile", "", "createNewLogFile", "Ljava/io/File;", "logDir", "findLatestUsableLogFile", "log", GlobalVariable.t, "", "println", "processLogMessage", "rotateLogFile", "shutdown", "startLogProcessor", "Lkotlinx/coroutines/Job;", "Companion", "LogWriter", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class FileLogger extends DefaultLogger {
    private static final String TAG = "FileLogger";
    private final Context appContext;
    private final FileLoggerConfig config;
    private final CoroutineScope coroutineScope;
    private volatile LogWriter currentWriter;
    private final FileLogger$fileNameFormat$1 fileNameFormat;
    private AtomicBoolean isLogInProgress;
    private final Channel<String> logChannel;
    private final FileLogger$logDateFormat$1 logDateFormat;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FileLogger(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ FileLogger(Context context, FileLoggerConfig fileLoggerConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            FileLoggerConfig.Companion companion = FileLoggerConfig.INSTANCE;
            fileLoggerConfig = new FileLoggerConfig.Builder().build();
        }
        this(context, fileLoggerConfig);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.king.logx.logger.FileLogger$fileNameFormat$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.king.logx.logger.FileLogger$logDateFormat$1] */
    public FileLogger(Context context, FileLoggerConfig config) {
        super(config);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(config, "config");
        this.config = config;
        this.appContext = context.getApplicationContext();
        this.fileNameFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.king.logx.logger.FileLogger$fileNameFormat$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public SimpleDateFormat initialValue() {
                return new SimpleDateFormat(this.this$0.config.getFileNameFormatPattern(), Locale.getDefault());
            }
        };
        this.logDateFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.king.logx.logger.FileLogger$logDateFormat$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public SimpleDateFormat initialValue() {
                return new SimpleDateFormat(this.this$0.config.getLogDateFormatPattern(), Locale.getDefault());
            }
        };
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)).plus(new FileLogger$special$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.INSTANCE)));
        this.logChannel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6, null);
        this.isLogInProgress = new AtomicBoolean(false);
        startLogProcessor();
    }

    /* JADX INFO: compiled from: FileLogger.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/king/logx/logger/FileLogger$LogWriter;", "", "file", "Ljava/io/File;", "size", "Ljava/util/concurrent/atomic/AtomicLong;", "(Ljava/io/File;Ljava/util/concurrent/atomic/AtomicLong;)V", "getFile", "()Ljava/io/File;", "getSize", "()Ljava/util/concurrent/atomic/AtomicLong;", "writer", "Ljava/io/BufferedWriter;", "close", "", "write", "message", "", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    private static final class LogWriter {
        private final File file;
        private final AtomicLong size;
        private volatile BufferedWriter writer;

        public LogWriter(File file, AtomicLong size) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(size, "size");
            this.file = file;
            this.size = size;
        }

        public final File getFile() {
            return this.file;
        }

        public final AtomicLong getSize() {
            return this.size;
        }

        public final void write(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            if (this.writer == null) {
                this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file, true), StandardCharsets.UTF_8));
            }
            BufferedWriter bufferedWriter = this.writer;
            if (bufferedWriter != null) {
                bufferedWriter.write(message);
            }
        }

        public final synchronized void close() {
            try {
                BufferedWriter bufferedWriter = this.writer;
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                }
            } finally {
                BufferedWriter bufferedWriter2 = this.writer;
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                this.writer = null;
            }
        }
    }

    /* JADX INFO: renamed from: com.king.logx.logger.FileLogger$startLogProcessor$1, reason: invalid class name */
    /* JADX INFO: compiled from: FileLogger.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.king.logx.logger.FileLogger$startLogProcessor$1", f = "FileLogger.kt", i = {0}, l = {289}, m = "invokeSuspend", n = {"$this$consume$iv$iv"}, s = {"L$1"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FileLogger.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0045 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004e A[Catch: all -> 0x0060, TryCatch #0 {all -> 0x0060, blocks: (B:6:0x0018, B:15:0x0046, B:17:0x004e, B:12:0x0037, B:18:0x0058, B:11:0x0032), top: B:26:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0058 A[Catch: all -> 0x0060, TRY_LEAVE, TryCatch #0 {all -> 0x0060, blocks: (B:6:0x0018, B:15:0x0046, B:17:0x004e, B:12:0x0037, B:18:0x0058, B:11:0x0032), top: B:26:0x0008 }] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0043 -> B:15:0x0046). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 0
                r3 = 1
                if (r1 == 0) goto L24
                if (r1 != r3) goto L1c
                java.lang.Object r1 = r6.L$2
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r4 = r6.L$1
                kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
                java.lang.Object r5 = r6.L$0
                com.king.logx.logger.FileLogger r5 = (com.king.logx.logger.FileLogger) r5
                kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L60
                goto L46
            L1c:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L24:
                kotlin.ResultKt.throwOnFailure(r7)
                com.king.logx.logger.FileLogger r7 = com.king.logx.logger.FileLogger.this
                kotlinx.coroutines.channels.Channel r7 = com.king.logx.logger.FileLogger.access$getLogChannel$p(r7)
                r4 = r7
                kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
                com.king.logx.logger.FileLogger r7 = com.king.logx.logger.FileLogger.this
                kotlinx.coroutines.channels.ChannelIterator r1 = r4.iterator()     // Catch: java.lang.Throwable -> L60
                r5 = r7
            L37:
                r6.L$0 = r5     // Catch: java.lang.Throwable -> L60
                r6.L$1 = r4     // Catch: java.lang.Throwable -> L60
                r6.L$2 = r1     // Catch: java.lang.Throwable -> L60
                r6.label = r3     // Catch: java.lang.Throwable -> L60
                java.lang.Object r7 = r1.hasNext(r6)     // Catch: java.lang.Throwable -> L60
                if (r7 != r0) goto L46
                return r0
            L46:
                java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: java.lang.Throwable -> L60
                boolean r7 = r7.booleanValue()     // Catch: java.lang.Throwable -> L60
                if (r7 == 0) goto L58
                java.lang.Object r7 = r1.next()     // Catch: java.lang.Throwable -> L60
                java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Throwable -> L60
                com.king.logx.logger.FileLogger.access$processLogMessage(r5, r7)     // Catch: java.lang.Throwable -> L60
                goto L37
            L58:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L60
                kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r2)
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L60:
                r7 = move-exception
                throw r7     // Catch: java.lang.Throwable -> L62
            L62:
                r0 = move-exception
                kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r7)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.king.logx.logger.FileLogger.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final Job startLogProcessor() {
        return BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void processLogMessage(String message) {
        try {
            long jUtf8ByteSize = Utils.INSTANCE.utf8ByteSize(message);
            LogWriter logWriter = this.currentWriter;
            if (logWriter == null) {
                FileLogger fileLogger = this;
                this.currentWriter = createLogWriter(true);
                cleanupOldLogs();
            } else if (logWriter.getSize().get() + jUtf8ByteSize > this.config.getMaxFileSize()) {
                rotateLogFile();
            }
            LogWriter logWriter2 = this.currentWriter;
            if (logWriter2 != null) {
                logWriter2.write(message);
                logWriter2.getSize().addAndGet(jUtf8ByteSize);
                if (!this.isLogInProgress.get()) {
                    logWriter2.close();
                }
            }
        } catch (Exception e) {
            if (LogX.INSTANCE.isDebug$logx_release()) {
                Log.w(TAG, Utils.INSTANCE.getStackTraceString(e));
            }
            try {
                LogWriter logWriter3 = this.currentWriter;
                if (logWriter3 != null) {
                    logWriter3.close();
                }
            } finally {
                this.currentWriter = null;
            }
        }
    }

    private final LogWriter createLogWriter(boolean reuseRecentFile) {
        File fileCreateNewLogFile;
        Utils.Companion companion = Utils.INSTANCE;
        Context appContext = this.appContext;
        Intrinsics.checkNotNullExpressionValue(appContext, "appContext");
        File cacheFileDir = companion.getCacheFileDir(appContext, this.config.getLogDir());
        if (!cacheFileDir.exists()) {
            cacheFileDir.mkdirs();
        }
        if (!reuseRecentFile || this.config.getReuseThresholdMillis() <= 0 || (fileCreateNewLogFile = findLatestUsableLogFile(cacheFileDir)) == null) {
            fileCreateNewLogFile = createNewLogFile(cacheFileDir);
        }
        if (LogX.INSTANCE.isDebug$logx_release()) {
            Log.d(TAG, "Log file: " + fileCreateNewLogFile);
        }
        return new LogWriter(fileCreateNewLogFile, new AtomicLong(fileCreateNewLogFile.length()));
    }

    private final File createNewLogFile(File logDir) {
        FileLoggerConfig fileLoggerConfig = this.config;
        StringBuilder sb = new StringBuilder();
        sb.append(fileLoggerConfig.getFilePrefix());
        SimpleDateFormat simpleDateFormat = get();
        Intrinsics.checkNotNull(simpleDateFormat);
        sb.append(simpleDateFormat.format(new Date()));
        sb.append(fileLoggerConfig.getFileExtension());
        return new File(logDir, sb.toString());
    }

    private final File findLatestUsableLogFile(File logDir) {
        File file;
        long jCurrentTimeMillis = System.currentTimeMillis() - this.config.getReuseThresholdMillis();
        String[] list = logDir.list();
        if (list != null) {
            long j = 0;
            file = null;
            for (String filename : list) {
                Intrinsics.checkNotNullExpressionValue(filename, "filename");
                if (StringsKt.startsWith$default(filename, this.config.getFilePrefix(), false, 2, (Object) null) && StringsKt.endsWith$default(filename, this.config.getFileExtension(), false, 2, (Object) null)) {
                    try {
                        File file2 = new File(logDir, filename);
                        long jLastModified = file2.lastModified();
                        if (jLastModified >= jCurrentTimeMillis && jLastModified > j) {
                            file = file2;
                            j = jLastModified;
                        }
                    } catch (Exception e) {
                        if (LogX.INSTANCE.isDebug$logx_release()) {
                            Log.w(TAG, Utils.INSTANCE.getStackTraceString(e));
                        }
                    }
                }
            }
        } else {
            file = null;
        }
        if (file == null || file.length() >= this.config.getMaxFileSize() || !file.canWrite()) {
            return null;
        }
        return file;
    }

    private final void rotateLogFile() {
        LogWriter logWriter = this.currentWriter;
        if (logWriter != null) {
            logWriter.close();
        }
        this.currentWriter = createLogWriter(false);
        cleanupOldLogs();
    }

    private final void cleanupOldLogs() {
        List listSortedWith;
        try {
            Utils.Companion companion = Utils.INSTANCE;
            Context appContext = this.appContext;
            Intrinsics.checkNotNullExpressionValue(appContext, "appContext");
            File[] fileArrListFiles = companion.getCacheFileDir(appContext, this.config.getLogDir()).listFiles(new FileFilter() { // from class: com.king.logx.logger.FileLogger$$ExternalSyntheticLambda0
                @Override // java.io.FileFilter
                public final boolean accept(File file) {
                    return FileLogger.cleanupOldLogs$lambda$8(this.f$0, file);
                }
            });
            if (fileArrListFiles == null || (listSortedWith = ArraysKt.sortedWith(fileArrListFiles, new Comparator() { // from class: com.king.logx.logger.FileLogger$cleanupOldLogs$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((File) t).lastModified()), Long.valueOf(((File) t2).lastModified()));
                }
            })) == null || listSortedWith.size() <= this.config.getMaxFileCount()) {
                return;
            }
            Iterator it = CollectionsKt.take(listSortedWith, listSortedWith.size() - this.config.getMaxFileCount()).iterator();
            while (it.hasNext()) {
                ((File) it.next()).delete();
            }
        } catch (Exception e) {
            Log.w(TAG, Utils.INSTANCE.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean cleanupOldLogs$lambda$8(FileLogger this$0, File file) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String name = file.getName();
        Intrinsics.checkNotNullExpressionValue(name, "file.name");
        if (StringsKt.startsWith$default(name, this$0.config.getFilePrefix(), false, 2, (Object) null)) {
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "file.name");
            if (StringsKt.endsWith$default(name2, this$0.config.getFileExtension(), false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.king.logx.logger.DefaultLogger, com.king.logx.logger.Logger
    protected void log(int priority, String tag, String message, Throwable t) {
        this.isLogInProgress.set(true);
        super.log(priority, tag, message, t);
        this.isLogInProgress.set(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.king.logx.logger.DefaultLogger
    public void println(int priority, String tag, String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (this.config.getLogToLogcat()) {
            super.println(priority, tag, message);
        }
        if (!this.logChannel.isClosedForSend()) {
            this.logChannel.mo2903trySendJP2dKIU(buildMessage(priority, tag, message));
        } else if (LogX.INSTANCE.isDebug$logx_release()) {
            Log.w(TAG, "Log channel is closed.");
        }
    }

    protected String buildMessage(int priority, String tag, String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        SimpleDateFormat simpleDateFormat = get();
        Intrinsics.checkNotNull(simpleDateFormat);
        return simpleDateFormat.format(new Date()) + ' ' + Utils.INSTANCE.getLogLevel(priority) + '/' + tag + ": " + message + '\n';
    }

    public final void shutdown() {
        if (CoroutineScopeKt.isActive(this.coroutineScope)) {
            CoroutineScopeKt.cancel$default(this.coroutineScope, null, 1, null);
            Log.d(TAG, "CoroutineScope was cancelled.");
        } else {
            Log.d(TAG, "CoroutineScope was already inactive.");
        }
    }
}

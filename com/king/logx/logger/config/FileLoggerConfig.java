package com.king.logx.logger.config;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.king.logx.logger.LogFormat;
import com.king.logx.logger.config.DefaultLoggerConfig;
import com.king.logx.logger.config.FileLoggerConfig;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileLoggerConfig.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0014\b\u0016\u0018\u0000 #2\u00020\u0001:\u0002\"#Bo\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\t\u0012\u0006\u0010\u000e\u001a\u00020\u0005\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0007\u0012\u0006\u0010\u0013\u001a\u00020\u0007¢\u0006\u0002\u0010\u0014R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\r\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010 ¨\u0006$"}, d2 = {"Lcom/king/logx/logger/config/FileLoggerConfig;", "Lcom/king/logx/logger/config/DefaultLoggerConfig;", "logToLogcat", "", "maxFileSize", "", "maxFileCount", "", "filePrefix", "", "fileExtension", "fileNameFormatPattern", "logDateFormatPattern", "logDir", "reuseThresholdMillis", "logFormat", "Lcom/king/logx/logger/LogFormat;", "showThreadInfo", "methodCount", "methodOffset", "(ZJILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLcom/king/logx/logger/LogFormat;ZII)V", "getFileExtension", "()Ljava/lang/String;", "getFileNameFormatPattern", "getFilePrefix", "getLogDateFormatPattern", "getLogDir", "getLogToLogcat", "()Z", "getMaxFileCount", "()I", "getMaxFileSize", "()J", "getReuseThresholdMillis", "Builder", "Companion", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class FileLoggerConfig extends DefaultLoggerConfig {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String LOG_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String LOG_FILENAME_FORMAT_PATTERN = "yyyyMMdd_HHmmss";
    private final String fileExtension;
    private final String fileNameFormatPattern;
    private final String filePrefix;
    private final String logDateFormatPattern;
    private final String logDir;
    private final boolean logToLogcat;
    private final int maxFileCount;
    private final long maxFileSize;
    private final long reuseThresholdMillis;

    public final boolean getLogToLogcat() {
        return this.logToLogcat;
    }

    public final long getMaxFileSize() {
        return this.maxFileSize;
    }

    public final int getMaxFileCount() {
        return this.maxFileCount;
    }

    public final String getFilePrefix() {
        return this.filePrefix;
    }

    public final String getFileExtension() {
        return this.fileExtension;
    }

    public final String getFileNameFormatPattern() {
        return this.fileNameFormatPattern;
    }

    public final String getLogDateFormatPattern() {
        return this.logDateFormatPattern;
    }

    public final String getLogDir() {
        return this.logDir;
    }

    public final long getReuseThresholdMillis() {
        return this.reuseThresholdMillis;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected FileLoggerConfig(boolean z, long j, int i, String filePrefix, String fileExtension, String fileNameFormatPattern, String logDateFormatPattern, String logDir, long j2, LogFormat logFormat, boolean z2, int i2, int i3) {
        super(logFormat, z2, i2, i3);
        Intrinsics.checkNotNullParameter(filePrefix, "filePrefix");
        Intrinsics.checkNotNullParameter(fileExtension, "fileExtension");
        Intrinsics.checkNotNullParameter(fileNameFormatPattern, "fileNameFormatPattern");
        Intrinsics.checkNotNullParameter(logDateFormatPattern, "logDateFormatPattern");
        Intrinsics.checkNotNullParameter(logDir, "logDir");
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
        this.logToLogcat = z;
        this.maxFileSize = j;
        this.maxFileCount = i;
        this.filePrefix = filePrefix;
        this.fileExtension = fileExtension;
        this.fileNameFormatPattern = fileNameFormatPattern;
        this.logDateFormatPattern = logDateFormatPattern;
        this.logDir = logDir;
        this.reuseThresholdMillis = j2;
    }

    /* JADX INFO: compiled from: FileLoggerConfig.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010+\u001a\u00020,H\u0016J\u0010\u0010\b\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\f\u001a\u00020\u00002\u0006\u0010-\u001a\u00020\u0004H\u0016J\u0010\u0010\u000f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0004H\u0016J\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010-\u001a\u00020\u0004H\u0016J\u0010\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0004H\u0016J\u0010\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u000200H\u0016J\u0010\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0016H\u0016J\u0010\u0010 \u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010&\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\"H\u0016J\u0010\u00101\u001a\u00020\u00002\u0006\u00102\u001a\u00020\u001cH\u0016J\u0010\u00103\u001a\u00020\u00002\u0006\u00104\u001a\u00020\u001cH\u0016J\u0010\u0010*\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\"H\u0016J\u0010\u00105\u001a\u00020\u00002\u0006\u00106\u001a\u00020\u0016H\u0016R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR$\u0010\r\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR$\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR$\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR$\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0016@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR$\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0003\u001a\u00020\u001c@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R$\u0010#\u001a\u00020\"2\u0006\u0010\u0003\u001a\u00020\"@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R$\u0010(\u001a\u00020\"2\u0006\u0010\u0003\u001a\u00020\"@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010%\"\u0004\b*\u0010'¨\u00067"}, d2 = {"Lcom/king/logx/logger/config/FileLoggerConfig$Builder;", "Lcom/king/logx/logger/config/DefaultLoggerConfig$Builder;", "()V", "<set-?>", "", "fileExtension", "getFileExtension", "()Ljava/lang/String;", "setFileExtension", "(Ljava/lang/String;)V", "fileNameFormatPattern", "getFileNameFormatPattern", "setFileNameFormatPattern", "filePrefix", "getFilePrefix", "setFilePrefix", "logDateFormatPattern", "getLogDateFormatPattern", "setLogDateFormatPattern", "logDir", "getLogDir", "setLogDir", "", "logToLogcat", "getLogToLogcat", "()Z", "setLogToLogcat", "(Z)V", "", "maxFileCount", "getMaxFileCount", "()I", "setMaxFileCount", "(I)V", "", "maxFileSize", "getMaxFileSize", "()J", "setMaxFileSize", "(J)V", "reuseThresholdMillis", "getReuseThresholdMillis", "setReuseThresholdMillis", "build", "Lcom/king/logx/logger/config/FileLoggerConfig;", "formatPattern", "setLogFormat", "logFormat", "Lcom/king/logx/logger/LogFormat;", "setMethodCount", "methodCount", "setMethodOffset", "methodOffset", "setShowThreadInfo", "showThreadInfo", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static class Builder extends DefaultLoggerConfig.Builder {
        private boolean logToLogcat;
        private long maxFileSize = PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
        private int maxFileCount = 10;
        private String filePrefix = "logx_";
        private String fileExtension = ".log";
        private String fileNameFormatPattern = FileLoggerConfig.LOG_FILENAME_FORMAT_PATTERN;
        private String logDateFormatPattern = FileLoggerConfig.LOG_DATE_FORMAT_PATTERN;
        private String logDir = "logs";
        private long reuseThresholdMillis = TimeUnit.HOURS.toMillis(1);

        public final boolean getLogToLogcat() {
            return this.logToLogcat;
        }

        /* JADX INFO: renamed from: setLogToLogcat, reason: collision with other method in class */
        public final /* synthetic */ void m1258setLogToLogcat(boolean z) {
            this.logToLogcat = z;
        }

        public final long getMaxFileSize() {
            return this.maxFileSize;
        }

        /* JADX INFO: renamed from: setMaxFileSize, reason: collision with other method in class */
        public final /* synthetic */ void m1260setMaxFileSize(long j) {
            this.maxFileSize = j;
        }

        public final int getMaxFileCount() {
            return this.maxFileCount;
        }

        /* JADX INFO: renamed from: setMaxFileCount, reason: collision with other method in class */
        public final /* synthetic */ void m1259setMaxFileCount(int i) {
            this.maxFileCount = i;
        }

        public final String getFilePrefix() {
            return this.filePrefix;
        }

        /* JADX INFO: renamed from: setFilePrefix, reason: collision with other method in class */
        public final /* synthetic */ void m1255setFilePrefix(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.filePrefix = str;
        }

        public final String getFileExtension() {
            return this.fileExtension;
        }

        /* JADX INFO: renamed from: setFileExtension, reason: collision with other method in class */
        public final /* synthetic */ void m1253setFileExtension(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.fileExtension = str;
        }

        public final String getFileNameFormatPattern() {
            return this.fileNameFormatPattern;
        }

        /* JADX INFO: renamed from: setFileNameFormatPattern, reason: collision with other method in class */
        public final /* synthetic */ void m1254setFileNameFormatPattern(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.fileNameFormatPattern = str;
        }

        public final String getLogDateFormatPattern() {
            return this.logDateFormatPattern;
        }

        /* JADX INFO: renamed from: setLogDateFormatPattern, reason: collision with other method in class */
        public final /* synthetic */ void m1256setLogDateFormatPattern(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.logDateFormatPattern = str;
        }

        public final String getLogDir() {
            return this.logDir;
        }

        /* JADX INFO: renamed from: setLogDir, reason: collision with other method in class */
        public final /* synthetic */ void m1257setLogDir(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.logDir = str;
        }

        public final long getReuseThresholdMillis() {
            return this.reuseThresholdMillis;
        }

        /* JADX INFO: renamed from: setReuseThresholdMillis, reason: collision with other method in class */
        public final /* synthetic */ void m1261setReuseThresholdMillis(long j) {
            this.reuseThresholdMillis = j;
        }

        public Builder setLogToLogcat(boolean logToLogcat) {
            this.logToLogcat = logToLogcat;
            return this;
        }

        public Builder setMaxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public Builder setMaxFileCount(int maxFileCount) {
            this.maxFileCount = maxFileCount;
            return this;
        }

        public Builder setFilePrefix(String filePrefix) {
            Intrinsics.checkNotNullParameter(filePrefix, "filePrefix");
            this.filePrefix = filePrefix;
            return this;
        }

        public Builder setFileExtension(String fileExtension) {
            Intrinsics.checkNotNullParameter(fileExtension, "fileExtension");
            this.fileExtension = fileExtension;
            return this;
        }

        public Builder setFileNameFormatPattern(String formatPattern) {
            Intrinsics.checkNotNullParameter(formatPattern, "formatPattern");
            this.fileNameFormatPattern = formatPattern;
            return this;
        }

        public Builder setLogDateFormatPattern(String formatPattern) {
            Intrinsics.checkNotNullParameter(formatPattern, "formatPattern");
            this.logDateFormatPattern = formatPattern;
            return this;
        }

        public Builder setLogDir(String logDir) {
            Intrinsics.checkNotNullParameter(logDir, "logDir");
            this.logDir = logDir;
            return this;
        }

        public Builder setReuseThresholdMillis(long reuseThresholdMillis) {
            this.reuseThresholdMillis = reuseThresholdMillis;
            return this;
        }

        @Override // com.king.logx.logger.config.DefaultLoggerConfig.Builder, com.king.logx.logger.config.LoggerConfig.Builder
        public Builder setLogFormat(LogFormat logFormat) {
            Intrinsics.checkNotNullParameter(logFormat, "logFormat");
            super.setLogFormat(logFormat);
            return this;
        }

        @Override // com.king.logx.logger.config.DefaultLoggerConfig.Builder, com.king.logx.logger.config.LoggerConfig.Builder
        public Builder setMethodOffset(int methodOffset) {
            super.setMethodOffset(methodOffset);
            return this;
        }

        @Override // com.king.logx.logger.config.DefaultLoggerConfig.Builder
        public Builder setShowThreadInfo(boolean showThreadInfo) {
            super.setShowThreadInfo(showThreadInfo);
            return this;
        }

        @Override // com.king.logx.logger.config.DefaultLoggerConfig.Builder
        public Builder setMethodCount(int methodCount) {
            super.setMethodCount(methodCount);
            return this;
        }

        @Override // com.king.logx.logger.config.DefaultLoggerConfig.Builder, com.king.logx.logger.config.LoggerConfig.Builder
        public FileLoggerConfig build() {
            return new FileLoggerConfig(this.logToLogcat, this.maxFileSize, this.maxFileCount, this.filePrefix, this.fileExtension, this.fileNameFormatPattern, this.logDateFormatPattern, this.logDir, this.reuseThresholdMillis, getLogFormat(), getShowThreadInfo(), getMethodCount(), getMethodOffset());
        }
    }

    /* JADX INFO: compiled from: FileLoggerConfig.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0006\u001a\u00020\u00072\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, d2 = {"Lcom/king/logx/logger/config/FileLoggerConfig$Companion;", "", "()V", "LOG_DATE_FORMAT_PATTERN", "", "LOG_FILENAME_FORMAT_PATTERN", "build", "Lcom/king/logx/logger/config/FileLoggerConfig;", "block", "Lkotlin/Function1;", "Lcom/king/logx/logger/config/FileLoggerConfig$Builder;", "", "Lkotlin/ExtensionFunctionType;", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ FileLoggerConfig build$default(Companion companion, Function1 block, int i, Object obj) {
            if ((i & 1) != 0) {
                block = new Function1<Builder, Unit>() { // from class: com.king.logx.logger.config.FileLoggerConfig$Companion$build$1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FileLoggerConfig.Builder builder) {
                        Intrinsics.checkNotNullParameter(builder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FileLoggerConfig.Builder builder) {
                        invoke2(builder);
                        return Unit.INSTANCE;
                    }
                };
            }
            Intrinsics.checkNotNullParameter(block, "block");
            Builder builder = new Builder();
            block.invoke(builder);
            return builder.build();
        }

        public final /* synthetic */ FileLoggerConfig build(Function1<? super Builder, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            Builder builder = new Builder();
            block.invoke(builder);
            return builder.build();
        }
    }
}

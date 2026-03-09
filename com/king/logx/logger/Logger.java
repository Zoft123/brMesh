package com.king.logx.logger;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.king.logx.LogX;
import com.king.logx.logger.config.LoggerConfig;
import com.king.logx.util.Utils;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Logger.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\b&\u0018\u0000 >2\u00020\u0001:\u0001>B\u001b\b\u0017\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ/\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010'J\u0012\u0010!\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J9\u0010!\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010*J/\u0010+\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010'J\u0012\u0010+\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J9\u0010+\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010*J\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0016J+\u0010,\u001a\u0004\u0018\u00010\u000e2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0010\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%H\u0014¢\u0006\u0002\u0010-J\u001b\u0010.\u001a\u00020\u00052\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000%H\u0004¢\u0006\u0002\u00101J\u0013\u00102\u001a\b\u0012\u0004\u0012\u0002000%H\u0014¢\u0006\u0002\u00103J/\u00104\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010'J\u0012\u00104\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J9\u00104\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010*J\u001a\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u000eH\u0014J\u001a\u00108\u001a\u00020\"2\u0006\u00107\u001a\u00020\u00052\b\u0010#\u001a\u0004\u0018\u00010\u000eH\u0016J.\u00108\u001a\u00020\"2\u0006\u00107\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u000e2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\b\u0010(\u001a\u0004\u0018\u00010)H$J\u001a\u00108\u001a\u00020\"2\u0006\u00107\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J$\u00108\u001a\u00020\"2\u0006\u00107\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0016JA\u00109\u001a\u00020\"2\u0006\u00107\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0002¢\u0006\u0002\u0010:J\u0010\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J/\u0010;\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010'J\u0012\u0010;\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J9\u0010;\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010*J/\u0010<\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010'J\u0012\u0010<\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J9\u0010<\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010*J/\u0010=\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010'J\u0012\u0010=\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J9\u0010=\u001a\u00020\"2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010#\u001a\u0004\u0018\u00010\u000e2\u0016\u0010$\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010&0%\"\u0004\u0018\u00010&H\u0016¢\u0006\u0002\u0010*R\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b8BX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b8BX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b8BX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0019R\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006?"}, d2 = {"Lcom/king/logx/logger/Logger;", "Lcom/king/logx/logger/ILogger;", "logFormat", "Lcom/king/logx/logger/LogFormat;", "methodOffset", "", "(Lcom/king/logx/logger/LogFormat;I)V", "config", "Lcom/king/logx/logger/config/LoggerConfig;", "(Lcom/king/logx/logger/config/LoggerConfig;)V", "explicitLogFormat", "Ljava/lang/ThreadLocal;", "explicitOffset", "explicitTag", "", "format", "getFormat", "()Lcom/king/logx/logger/LogFormat;", "<set-?>", "lastLogFormat", "getLastLogFormat", "setLastLogFormat$logx_release", "(Lcom/king/logx/logger/LogFormat;)V", "lastOffset", "getLastOffset", "()I", "setLastOffset$logx_release", "(I)V", TypedValues.CycleType.S_WAVE_OFFSET, "getOffset", "tag", "getTag", "()Ljava/lang/String;", "d", "", "message", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)V", GlobalVariable.t, "", "(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V", "e", "formatMessage", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "getStackOffset", "trace", "Ljava/lang/StackTraceElement;", "([Ljava/lang/StackTraceElement;)I", "getStackTrace", "()[Ljava/lang/StackTraceElement;", "i", "isLoggable", "", "priority", "log", "prepareLog", "(ILjava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V", GlobalVariable.v, "w", "wtf", "Companion", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class Logger implements ILogger {
    public static final String BOTTOM_BORDER = "└────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final char BOTTOM_LEFT_CORNER = 9492;
    private static final String DOUBLE_DIVIDER = "──────────────────────────────────────────────────";
    public static final char HORIZONTAL_LINE = 9474;
    public static final String INDENT = "    ";
    public static final int MAX_LOG_BYTES = 4000;
    public static final String MIDDLE_BORDER = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final char MIDDLE_CORNER = 9500;
    public static final int MIN_STACK_OFFSET = 5;
    public static final int SIMPLE_LOG_MAX_CHARS = 1333;
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    public static final String TOP_BORDER = "┌────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final char TOP_LEFT_CORNER = 9484;
    public static final int TRACE_LINE_CAPACITY = 128;
    private final ThreadLocal<LogFormat> explicitLogFormat;
    private final ThreadLocal<Integer> explicitOffset;
    private final ThreadLocal<String> explicitTag;
    private volatile LogFormat lastLogFormat;
    private volatile int lastOffset;
    private final LogFormat logFormat;
    private final int methodOffset;

    public Logger() {
        this(null, 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Logger(LogFormat logFormat) {
        this(logFormat, 0, 2, null);
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
    }

    protected abstract void log(int priority, String tag, String message, Throwable t);

    public Logger(LoggerConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        LogFormat logFormat = config.getLogFormat();
        this.logFormat = logFormat;
        int methodOffset = config.getMethodOffset();
        this.methodOffset = methodOffset;
        this.explicitLogFormat = new ThreadLocal<>();
        this.lastLogFormat = logFormat;
        this.explicitOffset = new ThreadLocal<>();
        this.lastOffset = methodOffset;
        this.explicitTag = new ThreadLocal<>();
    }

    public /* synthetic */ Logger(LogFormat logFormat, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? LogFormat.PRETTY : logFormat, (i2 & 2) != 0 ? 0 : i);
    }

    public Logger(LogFormat logFormat, int i) {
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
        LoggerConfig.Companion companion = LoggerConfig.INSTANCE;
        LoggerConfig.Builder builder = new LoggerConfig.Builder();
        builder.m1262setLogFormat(logFormat);
        builder.m1263setMethodOffset(i);
        this(builder.build());
    }

    private final /* synthetic */ LogFormat getFormat() {
        LogFormat logFormat = this.explicitLogFormat.get();
        if (logFormat != null) {
            this.explicitLogFormat.remove();
            return logFormat;
        }
        return this.logFormat;
    }

    public final LogFormat getLastLogFormat() {
        return this.lastLogFormat;
    }

    public final void setLastLogFormat$logx_release(LogFormat logFormat) {
        Intrinsics.checkNotNullParameter(logFormat, "<set-?>");
        this.lastLogFormat = logFormat;
    }

    private final /* synthetic */ int getOffset() {
        Integer num = this.explicitOffset.get();
        if (num != null) {
            this.explicitOffset.remove();
            return num.intValue();
        }
        return this.methodOffset;
    }

    public final int getLastOffset() {
        return this.lastOffset;
    }

    public final void setLastOffset$logx_release(int i) {
        this.lastOffset = i;
    }

    private final /* synthetic */ String getTag() {
        String str = this.explicitTag.get();
        if (str != null) {
            this.explicitTag.remove();
            return str;
        }
        StackTraceElement[] stackTrace = getStackTrace();
        StackTraceElement stackTraceElement = (StackTraceElement) ArraysKt.getOrNull(stackTrace, getStackOffset(stackTrace) + this.lastOffset);
        if (stackTraceElement != null) {
            return Utils.INSTANCE.createStackElementTag(stackTraceElement);
        }
        return null;
    }

    @Override // com.king.logx.logger.ILogger
    public ILogger format(LogFormat logFormat) {
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
        this.explicitLogFormat.set(logFormat);
        return this;
    }

    @Override // com.king.logx.logger.ILogger
    public ILogger offset(int methodOffset) {
        this.explicitOffset.set(Integer.valueOf(methodOffset));
        return this;
    }

    @Override // com.king.logx.logger.ILogger
    public ILogger tag(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        this.explicitTag.set(tag);
        return this;
    }

    @Override // com.king.logx.logger.ILogger
    public void v(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(2, null, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void v(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(2, t, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void v(Throwable t) {
        prepareLog(2, t, null, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void d(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(3, null, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void d(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(3, t, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void d(Throwable t) {
        prepareLog(3, t, null, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void i(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(4, null, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void i(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(4, t, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void i(Throwable t) {
        prepareLog(4, t, null, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void w(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(5, null, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void w(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(5, t, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void w(Throwable t) {
        prepareLog(5, t, null, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void e(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(6, null, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void e(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(6, t, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void e(Throwable t) {
        prepareLog(6, t, null, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void wtf(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(7, null, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void wtf(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        prepareLog(7, t, message, Arrays.copyOf(args, args.length));
    }

    @Override // com.king.logx.logger.ILogger
    public void wtf(Throwable t) {
        prepareLog(7, t, null, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void log(int priority, String message) {
        prepareLog(priority, null, message, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void log(int priority, Throwable t, String message) {
        prepareLog(priority, t, message, new Object[0]);
    }

    @Override // com.king.logx.logger.ILogger
    public void log(int priority, Throwable t) {
        prepareLog(priority, t, null, new Object[0]);
    }

    protected boolean isLoggable(int priority, String tag) {
        return LogX.INSTANCE.isDebug$logx_release();
    }

    private final synchronized void prepareLog(int priority, Throwable t, String message, Object... args) {
        this.lastOffset = getOffset();
        this.lastLogFormat = getFormat();
        String tag = getTag();
        if (isLoggable(priority, tag)) {
            if (!(args.length == 0)) {
                message = formatMessage(message, args);
            }
            log(priority, tag, message, t);
        }
    }

    protected String formatMessage(String message, Object[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (message == null) {
            return null;
        }
        Object[] objArrCopyOf = Arrays.copyOf(args, args.length);
        String str = String.format(message, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(str, "format(this, *args)");
        return str;
    }

    protected StackTraceElement[] getStackTrace() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        Intrinsics.checkNotNullExpressionValue(stackTrace, "Throwable().stackTrace");
        return stackTrace;
    }

    protected final int getStackOffset(StackTraceElement[] trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        int length = trace.length;
        for (int i = 5; i < length; i++) {
            if (!LogX.INSTANCE.getInternalIgnore$logx_release().contains(trace[i].getClassName())) {
                return i;
            }
        }
        return 0;
    }
}

package com.king.logx.logger;

import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.king.logx.logger.config.DefaultLoggerConfig;
import com.king.logx.util.Utils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DefaultLogger.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J.\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u001a\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J9\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\r2\u0014\b\u0006\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u0014H\u0082\bJ\u001a\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u001a\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u001a\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\"\u0010\u0018\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rH\u0014J\u0010\u0010\u0019\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\rH\u0002J'\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0014\b\u0004\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\u0014H\u0082\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/king/logx/logger/DefaultLogger;", "Lcom/king/logx/logger/Logger;", "config", "Lcom/king/logx/logger/config/DefaultLoggerConfig;", "(Lcom/king/logx/logger/config/DefaultLoggerConfig;)V", "methodCount", "", "showThreadInfo", "", "log", "", "priority", "tag", "", "message", GlobalVariable.t, "", "logBottomBorder", "logContent", "transform", "Lkotlin/Function1;", "logDivider", "logStackTrace", "logTopBorder", "println", "shouldSplitChunks", "splitLogChunks", "onChunk", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class DefaultLogger extends Logger {
    private final int methodCount;
    private final boolean showThreadInfo;

    /* JADX INFO: compiled from: DefaultLogger.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogFormat.values().length];
            try {
                iArr[LogFormat.PRETTY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LogFormat.PLAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DefaultLogger() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ DefaultLogger(DefaultLoggerConfig defaultLoggerConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            DefaultLoggerConfig.Companion companion = DefaultLoggerConfig.INSTANCE;
            defaultLoggerConfig = new DefaultLoggerConfig.Builder().build();
        }
        this(defaultLoggerConfig);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultLogger(DefaultLoggerConfig config) {
        super(config);
        Intrinsics.checkNotNullParameter(config, "config");
        this.showThreadInfo = config.getShowThreadInfo();
        this.methodCount = config.getMethodCount();
    }

    @Override // com.king.logx.logger.Logger
    protected void log(int priority, String tag, String message, Throwable t) {
        String stackTraceString;
        String str = message;
        if ((str == null || str.length() == 0) && t != null) {
            stackTraceString = Utils.INSTANCE.getStackTraceString(t);
        } else if (t != null) {
            stackTraceString = message + '\n' + Utils.INSTANCE.getStackTraceString(t);
        } else {
            stackTraceString = String.valueOf(message);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[getLastLogFormat().ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            for (String str2 : StringsKt.lineSequence(stackTraceString)) {
                if (!shouldSplitChunks(str2)) {
                    println(priority, tag, str2);
                } else {
                    int length = str2.length();
                    int i2 = 0;
                    int i3 = 0;
                    for (int i4 = 0; i4 < length; i4++) {
                        int iUtf8ByteSize = Utils.INSTANCE.utf8ByteSize(str2.charAt(i4));
                        i3 += iUtf8ByteSize;
                        if (i3 > 4000) {
                            String strSubstring = str2.substring(i2, i4);
                            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                            println(priority, tag, strSubstring);
                            i2 = i4;
                            i3 = iUtf8ByteSize;
                        }
                    }
                    if (i2 < length) {
                        String strSubstring2 = str2.substring(i2);
                        Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
                        println(priority, tag, strSubstring2);
                    }
                }
            }
            return;
        }
        logTopBorder(priority, tag);
        logStackTrace(priority, tag);
        for (String str3 : StringsKt.lineSequence(stackTraceString)) {
            if (!shouldSplitChunks(str3)) {
                println(priority, tag, "│ " + str3);
            } else {
                int length2 = str3.length();
                int i5 = 0;
                int i6 = 0;
                for (int i7 = 0; i7 < length2; i7++) {
                    int iUtf8ByteSize2 = Utils.INSTANCE.utf8ByteSize(str3.charAt(i7));
                    i6 += iUtf8ByteSize2;
                    if (i6 > 4000) {
                        String strSubstring3 = str3.substring(i5, i7);
                        Intrinsics.checkNotNullExpressionValue(strSubstring3, "this as java.lang.String…ing(startIndex, endIndex)");
                        println(priority, tag, "│ " + strSubstring3);
                        i5 = i7;
                        i6 = iUtf8ByteSize2;
                    }
                }
                if (i5 < length2) {
                    String strSubstring4 = str3.substring(i5);
                    Intrinsics.checkNotNullExpressionValue(strSubstring4, "this as java.lang.String).substring(startIndex)");
                    println(priority, tag, "│ " + strSubstring4);
                }
            }
        }
        logBottomBorder(priority, tag);
    }

    private final void logTopBorder(int priority, String tag) {
        println(priority, tag, Logger.TOP_BORDER);
    }

    private final void logStackTrace(int priority, String tag) {
        if (this.showThreadInfo) {
            println(priority, tag, "│ Thread: " + Thread.currentThread().getName());
            logDivider(priority, tag);
        }
        if (this.methodCount <= 0) {
            return;
        }
        StackTraceElement[] stackTrace = getStackTrace();
        int stackOffset = getStackOffset(stackTrace) + getLastOffset();
        int iMin = Math.min(this.methodCount, stackTrace.length - stackOffset);
        if (iMin <= 0) {
            return;
        }
        int i = (stackOffset + iMin) - 1;
        StringBuilder sb = new StringBuilder(iMin * 128);
        if (stackOffset <= i) {
            String str = "";
            while (true) {
                StackTraceElement stackTraceElement = stackTrace[i];
                sb.append(Logger.HORIZONTAL_LINE);
                sb.append(' ');
                sb.append(str);
                String className = stackTraceElement.getClassName();
                Intrinsics.checkNotNullExpressionValue(className, "stackElement.className");
                sb.append(StringsKt.substringAfterLast$default(className, '.', (String) null, 2, (Object) null));
                sb.append('.');
                sb.append(stackTraceElement.getMethodName());
                sb.append('(');
                sb.append(stackTraceElement.getFileName());
                sb.append(':');
                sb.append(stackTraceElement.getLineNumber());
                sb.append(')');
                String it = sb.toString();
                Intrinsics.checkNotNullExpressionValue(it, "it");
                println(priority, tag, it);
                str = str + Logger.INDENT;
                StringsKt.clear(sb);
                if (i == stackOffset) {
                    break;
                } else {
                    i--;
                }
            }
        }
        logDivider(priority, tag);
    }

    private final void logDivider(int priority, String tag) {
        println(priority, tag, Logger.MIDDLE_BORDER);
    }

    private final void logBottomBorder(int priority, String tag) {
        println(priority, tag, Logger.BOTTOM_BORDER);
    }

    static /* synthetic */ void logContent$default(DefaultLogger defaultLogger, int i, String str, String str2, Function1 function1, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logContent");
        }
        if ((i2 & 8) != 0) {
            function1 = new Function1<String, String>() { // from class: com.king.logx.logger.DefaultLogger.logContent.1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it;
                }
            };
        }
        for (String str3 : StringsKt.lineSequence(str2)) {
            if (!defaultLogger.shouldSplitChunks(str3)) {
                defaultLogger.println(i, str, (String) function1.invoke(str3));
            } else {
                int length = str3.length();
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < length; i5++) {
                    int iUtf8ByteSize = Utils.INSTANCE.utf8ByteSize(str3.charAt(i5));
                    i4 += iUtf8ByteSize;
                    if (i4 > 4000) {
                        String strSubstring = str3.substring(i3, i5);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                        defaultLogger.println(i, str, (String) function1.invoke(strSubstring));
                        i3 = i5;
                        i4 = iUtf8ByteSize;
                    }
                }
                if (i3 < length) {
                    String strSubstring2 = str3.substring(i3);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
                    defaultLogger.println(i, str, (String) function1.invoke(strSubstring2));
                }
            }
        }
    }

    private final void logContent(int priority, String tag, String message, Function1<? super String, String> transform) {
        for (String str : StringsKt.lineSequence(message)) {
            if (!shouldSplitChunks(str)) {
                println(priority, tag, transform.invoke(str));
            } else {
                int length = str.length();
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < length; i3++) {
                    int iUtf8ByteSize = Utils.INSTANCE.utf8ByteSize(str.charAt(i3));
                    i2 += iUtf8ByteSize;
                    if (i2 > 4000) {
                        String strSubstring = str.substring(i, i3);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                        println(priority, tag, transform.invoke(strSubstring));
                        i = i3;
                        i2 = iUtf8ByteSize;
                    }
                }
                if (i < length) {
                    String strSubstring2 = str.substring(i);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
                    println(priority, tag, transform.invoke(strSubstring2));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldSplitChunks(String message) {
        if (message.length() <= 1333) {
            return false;
        }
        if (message.length() > 4000) {
            return true;
        }
        int length = message.length();
        int iUtf8ByteSize = 0;
        for (int i = 0; i < length; i++) {
            iUtf8ByteSize += Utils.INSTANCE.utf8ByteSize(message.charAt(i));
            if (iUtf8ByteSize > 4000) {
                return true;
            }
        }
        return false;
    }

    private final void splitLogChunks(String message, Function1<? super String, Unit> onChunk) {
        int length = message.length();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int iUtf8ByteSize = Utils.INSTANCE.utf8ByteSize(message.charAt(i3));
            i2 += iUtf8ByteSize;
            if (i2 > 4000) {
                String strSubstring = message.substring(i, i3);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                onChunk.invoke(strSubstring);
                i = i3;
                i2 = iUtf8ByteSize;
            }
        }
        if (i < length) {
            String strSubstring2 = message.substring(i);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
            onChunk.invoke(strSubstring2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void println(int priority, String tag, String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        Log.println(priority, tag, message);
    }
}

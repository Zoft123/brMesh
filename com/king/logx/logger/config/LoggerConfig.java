package com.king.logx.logger.config;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.king.logx.logger.LogFormat;
import com.king.logx.logger.config.LoggerConfig;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LoggerConfig.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0016\u0018\u0000 \f2\u00020\u0001:\u0002\u000b\fB\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/king/logx/logger/config/LoggerConfig;", "", "logFormat", "Lcom/king/logx/logger/LogFormat;", "methodOffset", "", "(Lcom/king/logx/logger/LogFormat;I)V", "getLogFormat", "()Lcom/king/logx/logger/LogFormat;", "getMethodOffset", "()I", "Builder", "Companion", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class LoggerConfig {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final LogFormat logFormat;
    private final int methodOffset;

    protected LoggerConfig(LogFormat logFormat, int i) {
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
        this.logFormat = logFormat;
        this.methodOffset = i;
    }

    public final LogFormat getLogFormat() {
        return this.logFormat;
    }

    public final int getMethodOffset() {
        return this.methodOffset;
    }

    /* JADX INFO: compiled from: LoggerConfig.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\b\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\nH\u0016R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0012"}, d2 = {"Lcom/king/logx/logger/config/LoggerConfig$Builder;", "", "()V", "<set-?>", "Lcom/king/logx/logger/LogFormat;", "logFormat", "getLogFormat", "()Lcom/king/logx/logger/LogFormat;", "setLogFormat", "(Lcom/king/logx/logger/LogFormat;)V", "", "methodOffset", "getMethodOffset", "()I", "setMethodOffset", "(I)V", "build", "Lcom/king/logx/logger/config/LoggerConfig;", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static class Builder {
        private LogFormat logFormat = LogFormat.PRETTY;
        private int methodOffset;

        public final LogFormat getLogFormat() {
            return this.logFormat;
        }

        /* JADX INFO: renamed from: setLogFormat, reason: collision with other method in class */
        public final /* synthetic */ void m1262setLogFormat(LogFormat logFormat) {
            Intrinsics.checkNotNullParameter(logFormat, "<set-?>");
            this.logFormat = logFormat;
        }

        public final int getMethodOffset() {
            return this.methodOffset;
        }

        /* JADX INFO: renamed from: setMethodOffset, reason: collision with other method in class */
        public final /* synthetic */ void m1263setMethodOffset(int i) {
            this.methodOffset = i;
        }

        public Builder setLogFormat(LogFormat logFormat) {
            Intrinsics.checkNotNullParameter(logFormat, "logFormat");
            this.logFormat = logFormat;
            return this;
        }

        public Builder setMethodOffset(int methodOffset) {
            this.methodOffset = methodOffset;
            return this;
        }

        public LoggerConfig build() {
            return new LoggerConfig(this.logFormat, this.methodOffset);
        }
    }

    /* JADX INFO: compiled from: LoggerConfig.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u0019\b\u0002\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, d2 = {"Lcom/king/logx/logger/config/LoggerConfig$Companion;", "", "()V", "build", "Lcom/king/logx/logger/config/LoggerConfig;", "block", "Lkotlin/Function1;", "Lcom/king/logx/logger/config/LoggerConfig$Builder;", "", "Lkotlin/ExtensionFunctionType;", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ LoggerConfig build$default(Companion companion, Function1 block, int i, Object obj) {
            if ((i & 1) != 0) {
                block = new Function1<Builder, Unit>() { // from class: com.king.logx.logger.config.LoggerConfig$Companion$build$1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(LoggerConfig.Builder builder) {
                        Intrinsics.checkNotNullParameter(builder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(LoggerConfig.Builder builder) {
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

        public final /* synthetic */ LoggerConfig build(Function1<? super Builder, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            Builder builder = new Builder();
            block.invoke(builder);
            return builder.build();
        }
    }
}

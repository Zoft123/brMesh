package com.king.logx.logger.config;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.king.logx.logger.LogFormat;
import com.king.logx.logger.config.DefaultLoggerConfig;
import com.king.logx.logger.config.LoggerConfig;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DefaultLoggerConfig.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0016\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000e\u000fB'\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/king/logx/logger/config/DefaultLoggerConfig;", "Lcom/king/logx/logger/config/LoggerConfig;", "logFormat", "Lcom/king/logx/logger/LogFormat;", "showThreadInfo", "", "methodCount", "", "methodOffset", "(Lcom/king/logx/logger/LogFormat;ZII)V", "getMethodCount", "()I", "getShowThreadInfo", "()Z", "Builder", "Companion", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public class DefaultLoggerConfig extends LoggerConfig {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int methodCount;
    private final boolean showThreadInfo;

    public final boolean getShowThreadInfo() {
        return this.showThreadInfo;
    }

    public final int getMethodCount() {
        return this.methodCount;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected DefaultLoggerConfig(LogFormat logFormat, boolean z, int i, int i2) {
        super(logFormat, i2);
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
        this.showThreadInfo = z;
        this.methodCount = i;
    }

    /* JADX INFO: compiled from: DefaultLoggerConfig.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\b\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0004H\u0016J\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\nH\u0016R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lcom/king/logx/logger/config/DefaultLoggerConfig$Builder;", "Lcom/king/logx/logger/config/LoggerConfig$Builder;", "()V", "<set-?>", "", "methodCount", "getMethodCount", "()I", "setMethodCount", "(I)V", "", "showThreadInfo", "getShowThreadInfo", "()Z", "setShowThreadInfo", "(Z)V", "build", "Lcom/king/logx/logger/config/DefaultLoggerConfig;", "setLogFormat", "logFormat", "Lcom/king/logx/logger/LogFormat;", "setMethodOffset", "methodOffset", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static class Builder extends LoggerConfig.Builder {
        private boolean showThreadInfo = true;
        private int methodCount = 2;

        public final boolean getShowThreadInfo() {
            return this.showThreadInfo;
        }

        /* JADX INFO: renamed from: setShowThreadInfo, reason: collision with other method in class */
        public final /* synthetic */ void m1252setShowThreadInfo(boolean z) {
            this.showThreadInfo = z;
        }

        public final int getMethodCount() {
            return this.methodCount;
        }

        /* JADX INFO: renamed from: setMethodCount, reason: collision with other method in class */
        public final /* synthetic */ void m1251setMethodCount(int i) {
            this.methodCount = i;
        }

        @Override // com.king.logx.logger.config.LoggerConfig.Builder
        public Builder setLogFormat(LogFormat logFormat) {
            Intrinsics.checkNotNullParameter(logFormat, "logFormat");
            super.setLogFormat(logFormat);
            return this;
        }

        @Override // com.king.logx.logger.config.LoggerConfig.Builder
        public Builder setMethodOffset(int methodOffset) {
            super.setMethodOffset(methodOffset);
            return this;
        }

        public Builder setShowThreadInfo(boolean showThreadInfo) {
            this.showThreadInfo = showThreadInfo;
            return this;
        }

        public Builder setMethodCount(int methodCount) {
            this.methodCount = methodCount;
            return this;
        }

        @Override // com.king.logx.logger.config.LoggerConfig.Builder
        public DefaultLoggerConfig build() {
            return new DefaultLoggerConfig(getLogFormat(), this.showThreadInfo, this.methodCount, getMethodOffset());
        }
    }

    /* JADX INFO: compiled from: DefaultLoggerConfig.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u0019\b\u0002\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, d2 = {"Lcom/king/logx/logger/config/DefaultLoggerConfig$Companion;", "", "()V", "build", "Lcom/king/logx/logger/config/DefaultLoggerConfig;", "block", "Lkotlin/Function1;", "Lcom/king/logx/logger/config/DefaultLoggerConfig$Builder;", "", "Lkotlin/ExtensionFunctionType;", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ DefaultLoggerConfig build$default(Companion companion, Function1 block, int i, Object obj) {
            if ((i & 1) != 0) {
                block = new Function1<Builder, Unit>() { // from class: com.king.logx.logger.config.DefaultLoggerConfig$Companion$build$1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(DefaultLoggerConfig.Builder builder) {
                        Intrinsics.checkNotNullParameter(builder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DefaultLoggerConfig.Builder builder) {
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

        public final /* synthetic */ DefaultLoggerConfig build(Function1<? super Builder, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            Builder builder = new Builder();
            block.invoke(builder);
            return builder.build();
        }
    }
}

package com.king.logx.logger;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompositeLogger.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0001J\u001f\u0010\f\u001a\u00020\r2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0004\"\u00020\u0001¢\u0006\u0002\u0010\u000fJ/\u0010\u0010\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J9\u0010\u0010\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0018J/\u0010\u0019\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010\u0019\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J9\u0010\u0019\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0018J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J/\u0010\u001e\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010\u001e\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J9\u0010\u001e\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0018J\u001a\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J.\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00072\b\u0010!\u001a\u0004\u0018\u00010\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\u001a\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J$\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u0007H\u0016J\u0006\u0010$\u001a\u00020\rJ\u000e\u0010%\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0001J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\u0012H\u0016J/\u0010&\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010&\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J9\u0010&\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0018J/\u0010'\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010'\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J9\u0010'\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0018J/\u0010(\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010(\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J9\u0010(\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0004\"\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0018R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u00010\nj\b\u0012\u0004\u0012\u00020\u0001`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/king/logx/logger/CompositeLogger;", "Lcom/king/logx/logger/Logger;", "()V", "loggerArray", "", "[Lcom/king/logx/logger/Logger;", "loggerCount", "", "()I", "loggers", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "addLogger", "", "logger", "([Lcom/king/logx/logger/Logger;)V", "d", "message", "", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)V", GlobalVariable.t, "", "(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V", "e", "format", "Lcom/king/logx/logger/ILogger;", "logFormat", "Lcom/king/logx/logger/LogFormat;", "i", "log", "priority", "tag", TypedValues.CycleType.S_WAVE_OFFSET, "methodOffset", "removeAllLoggers", "removeLogger", GlobalVariable.v, "w", "wtf", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CompositeLogger extends Logger {
    private volatile Logger[] loggerArray;
    private final ArrayList<Logger> loggers;

    public CompositeLogger() {
        super(null, 0, 3, 0 == true ? 1 : 0);
        this.loggers = new ArrayList<>();
        this.loggerArray = new Logger[0];
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public ILogger format(LogFormat logFormat) {
        Intrinsics.checkNotNullParameter(logFormat, "logFormat");
        for (Logger logger : this.loggerArray) {
            logger.format(logFormat);
        }
        return this;
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public ILogger offset(int methodOffset) {
        for (Logger logger : this.loggerArray) {
            logger.offset(methodOffset);
        }
        return this;
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public ILogger tag(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        for (Logger logger : this.loggerArray) {
            logger.tag(tag);
        }
        return this;
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void v(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.v(message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void v(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.v(t, message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void v(Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.v(t);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void d(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.d(message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void d(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.d(t, message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void d(Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.d(t);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void i(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.i(message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void i(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.i(t, message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void i(Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.i(t);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void w(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.w(message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void w(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.w(t, message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void w(Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.w(t);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void e(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.e(message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void e(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.e(t, message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void e(Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.e(t);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void wtf(String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.wtf(message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void wtf(Throwable t, String message, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        for (Logger logger : this.loggerArray) {
            logger.wtf(t, message, Arrays.copyOf(args, args.length));
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void wtf(Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.wtf(t);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void log(int priority, String message) {
        for (Logger logger : this.loggerArray) {
            logger.log(priority, message);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void log(int priority, Throwable t, String message) {
        for (Logger logger : this.loggerArray) {
            logger.log(priority, t, message);
        }
    }

    @Override // com.king.logx.logger.Logger, com.king.logx.logger.ILogger
    public void log(int priority, Throwable t) {
        for (Logger logger : this.loggerArray) {
            logger.log(priority, t);
        }
    }

    @Override // com.king.logx.logger.Logger
    protected void log(int priority, String tag, String message, Throwable t) {
        throw new AssertionError();
    }

    public final void addLogger(Logger logger) {
        Intrinsics.checkNotNullParameter(logger, "logger");
        if (logger == this) {
            throw new IllegalArgumentException(("Cannot add " + logger.getClass().getSimpleName() + " into itself.").toString());
        }
        synchronized (this.loggers) {
            this.loggers.add(logger);
            this.loggerArray = (Logger[]) this.loggers.toArray(new Logger[0]);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void addLogger(Logger... loggers) {
        Intrinsics.checkNotNullParameter(loggers, "loggers");
        if (loggers.length == 0) {
            return;
        }
        for (Logger logger : loggers) {
            if (logger == this) {
                throw new IllegalArgumentException(("Cannot add " + logger.getClass().getSimpleName() + " into itself.").toString());
            }
        }
        synchronized (this.loggers) {
            Collections.addAll(this.loggers, Arrays.copyOf(loggers, loggers.length));
            this.loggerArray = (Logger[]) this.loggers.toArray(new Logger[0]);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void removeLogger(Logger logger) {
        Intrinsics.checkNotNullParameter(logger, "logger");
        synchronized (this.loggers) {
            if (!this.loggers.remove(logger)) {
                throw new IllegalArgumentException(("Cannot remove logger which is not added: " + logger).toString());
            }
            this.loggerArray = (Logger[]) this.loggers.toArray(new Logger[0]);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void removeAllLoggers() {
        synchronized (this.loggers) {
            this.loggers.clear();
            this.loggerArray = new Logger[0];
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int loggerCount() {
        return this.loggerArray.length;
    }
}

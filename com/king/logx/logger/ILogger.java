package com.king.logx.logger;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import kotlin.Metadata;

/* JADX INFO: compiled from: ILogger.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J/\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH&J9\u0010\u0002\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\u000bJ/\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0012\u0010\f\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH&J9\u0010\f\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\u000bJ\u0010\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fH&J/\u0010\u0010\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0012\u0010\u0010\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH&J9\u0010\u0010\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\u000bJ\u001a\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u001a\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\t\u001a\u0004\u0018\u00010\nH&J$\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0010\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0013H&J\u0010\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0005H&J/\u0010\u0017\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0012\u0010\u0017\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH&J9\u0010\u0017\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\u000bJ/\u0010\u0018\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0012\u0010\u0018\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH&J9\u0010\u0018\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\u000bJ/\u0010\u0019\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0012\u0010\u0019\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH&J9\u0010\u0019\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\u000b¨\u0006\u001a"}, d2 = {"Lcom/king/logx/logger/ILogger;", "", "d", "", "message", "", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)V", GlobalVariable.t, "", "(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V", "e", "format", "logFormat", "Lcom/king/logx/logger/LogFormat;", "i", "log", "priority", "", TypedValues.CycleType.S_WAVE_OFFSET, "methodOffset", "tag", GlobalVariable.v, "w", "wtf", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public interface ILogger {
    void d(String message, Object... args);

    void d(Throwable t);

    void d(Throwable t, String message, Object... args);

    void e(String message, Object... args);

    void e(Throwable t);

    void e(Throwable t, String message, Object... args);

    ILogger format(LogFormat logFormat);

    void i(String message, Object... args);

    void i(Throwable t);

    void i(Throwable t, String message, Object... args);

    void log(int priority, String message);

    void log(int priority, Throwable t);

    void log(int priority, Throwable t, String message);

    ILogger offset(int methodOffset);

    ILogger tag(String tag);

    void v(String message, Object... args);

    void v(Throwable t);

    void v(Throwable t, String message, Object... args);

    void w(String message, Object... args);

    void w(Throwable t);

    void w(Throwable t, String message, Object... args);

    void wtf(String message, Object... args);

    void wtf(Throwable t);

    void wtf(Throwable t, String message, Object... args);
}

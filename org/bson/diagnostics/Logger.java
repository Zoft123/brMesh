package org.bson.diagnostics;

/* JADX INFO: loaded from: classes4.dex */
public interface Logger {
    void debug(String str);

    void debug(String str, Throwable th);

    void error(String str);

    void error(String str, Throwable th);

    String getName();

    void info(String str);

    void info(String str, Throwable th);

    boolean isDebugEnabled();

    boolean isErrorEnabled();

    boolean isInfoEnabled();

    boolean isTraceEnabled();

    boolean isWarnEnabled();

    void trace(String str);

    void trace(String str, Throwable th);

    void warn(String str);

    void warn(String str, Throwable th);
}

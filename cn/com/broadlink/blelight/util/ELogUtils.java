package cn.com.broadlink.blelight.util;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class ELogUtils {
    private static boolean DEBUG = true;
    private static boolean ERROR = true;
    private static boolean INFO = true;
    private static final String TAG = "ELogUtils";
    private static boolean VERBOSE = true;
    private static boolean WARN = true;

    public static void d(String str) {
        d(TAG, str);
    }

    public static void d(String str, String str2) {
        if (DEBUG) {
            Log.d(str, str2);
        }
    }

    public static void v(String str) {
        v(TAG, str);
    }

    public static void v(String str, String str2) {
        if (VERBOSE) {
            Log.v(str, str2);
        }
    }

    public static void i(String str) {
        i(TAG, str);
    }

    public static void i(String str, String str2) {
        if (INFO) {
            Log.i(str, str2);
        }
    }

    public static void e(String str) {
        e(TAG, str);
    }

    public static void e(String str, String str2) {
        if (ERROR) {
            Log.e(str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (ERROR) {
            Log.e(str, str2, th);
        }
    }

    public static void w(String str) {
        w(TAG, str);
    }

    public static void w(String str, String str2) {
        if (WARN) {
            Log.w(str, str2);
        }
    }

    public static void setLogLevel(int i) {
        VERBOSE = false;
        DEBUG = false;
        INFO = false;
        WARN = false;
        ERROR = false;
        if (i >= 0) {
            VERBOSE = true;
        }
        if (i > 0) {
            DEBUG = true;
        }
        if (i > 1) {
            INFO = true;
        }
        if (i > 2) {
            WARN = true;
        }
        if (i > 3) {
            ERROR = true;
        }
    }
}

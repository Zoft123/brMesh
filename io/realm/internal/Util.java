package io.realm.internal;

import android.os.Build;
import androidx.core.os.EnvironmentCompat;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class Util {
    private static Boolean coroutinesAvailable;
    private static Boolean rxJavaAvailable;

    static native String nativeGetTablePrefix();

    public static String getTablePrefix() {
        return nativeGetTablePrefix();
    }

    public static Class<? extends RealmModel> getOriginalModelClass(Class<? extends RealmModel> cls) {
        if (cls.equals(RealmModel.class) || cls.equals(RealmObject.class)) {
            throw new IllegalArgumentException("RealmModel or RealmObject was passed as an argument. Only subclasses of these can be used as arguments to methods that accept a Realm model class.");
        }
        Class superclass = cls.getSuperclass();
        return (superclass.equals(Object.class) || superclass.equals(RealmObject.class)) ? cls : superclass;
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static boolean isEmulator() {
        if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion")) {
            return true;
        }
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT);
    }

    public static boolean isEmptyString(@Nullable String str) {
        return str == null || str.length() == 0;
    }

    public static boolean deleteRealm(String str, File file, String str2) {
        boolean zDelete;
        File file2 = new File(file, str2 + ".management");
        File file3 = new File(str);
        File file4 = new File(str + ".note");
        File[] fileArrListFiles = file2.listFiles();
        if (fileArrListFiles != null) {
            for (File file5 : fileArrListFiles) {
                if (!file5.delete()) {
                    RealmLog.warn(String.format(Locale.ENGLISH, "Realm temporary file at %s cannot be deleted", file5.getAbsolutePath()), new Object[0]);
                }
            }
        }
        if (file2.exists() && !file2.delete()) {
            RealmLog.warn(String.format(Locale.ENGLISH, "Realm temporary folder at %s cannot be deleted", file2.getAbsolutePath()), new Object[0]);
        }
        if (file3.exists()) {
            zDelete = file3.delete();
            if (!zDelete) {
                RealmLog.warn(String.format(Locale.ENGLISH, "Realm file at %s cannot be deleted", file3.getAbsolutePath()), new Object[0]);
            }
        } else {
            zDelete = true;
        }
        if (file4.exists() && !file4.delete()) {
            RealmLog.warn(String.format(Locale.ENGLISH, ".note file at %s cannot be deleted", file4.getAbsolutePath()), new Object[0]);
        }
        return zDelete;
    }

    public static <T> Set<T> toSet(T... tArr) {
        if (tArr == null) {
            return Collections.EMPTY_SET;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (T t : tArr) {
            if (t != null) {
                linkedHashSet.add(t);
            }
        }
        return linkedHashSet;
    }

    public static void checkEmpty(String str, String str2) {
        if (isEmptyString(str)) {
            throw new IllegalArgumentException("Non-empty '" + str2 + "' required.");
        }
    }

    public static void checkNull(@Nullable Object obj, String str) {
        if (obj != null) {
            return;
        }
        throw new IllegalArgumentException("Nonnull '" + str + "' required.");
    }

    public static void checkLooperThread(String str) {
        new AndroidCapabilities().checkCanDeliverNotification(str);
    }

    public static void checkNotOnMainThread(String str) {
        if (new AndroidCapabilities().isMainThread()) {
            throw new IllegalStateException(str);
        }
    }

    public static synchronized boolean isRxJavaAvailable() {
        if (rxJavaAvailable == null) {
            try {
                Class.forName("io.reactivex.Flowable");
                rxJavaAvailable = true;
            } catch (ClassNotFoundException unused) {
                rxJavaAvailable = false;
            }
        }
        return rxJavaAvailable.booleanValue();
    }

    public static synchronized boolean isCoroutinesAvailable() {
        if (coroutinesAvailable == null) {
            try {
                Class.forName("kotlinx.coroutines.flow.Flow");
                coroutinesAvailable = true;
            } catch (ClassNotFoundException unused) {
                coroutinesAvailable = false;
            }
        }
        return coroutinesAvailable.booleanValue();
    }

    public static void checkContainsKey(String str, Map<String, ?> map, String str2) {
        if (map.containsKey(str)) {
            return;
        }
        throw new IllegalArgumentException("Key '" + str + "' required in '" + str2 + "'.");
    }

    public static Class<?> getClassForName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            throw new IllegalArgumentException("Class '" + str + "' does not exist.");
        }
    }
}

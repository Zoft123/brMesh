package org.greenrobot.eventbus.android;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class AndroidDependenciesDetector {
    private static final String ANDROID_COMPONENTS_IMPLEMENTATION_CLASS_NAME = "org.greenrobot.eventbus.android.AndroidComponentsImpl";

    public static boolean isAndroidSDKAvailable() {
        try {
            return Class.forName("android.os.Looper").getDeclaredMethod("getMainLooper", null).invoke(null, null) != null;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return false;
        }
    }

    public static boolean areAndroidComponentsAvailable() {
        try {
            Class.forName(ANDROID_COMPONENTS_IMPLEMENTATION_CLASS_NAME);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static AndroidComponents instantiateAndroidComponents() {
        try {
            return (AndroidComponents) Class.forName(ANDROID_COMPONENTS_IMPLEMENTATION_CLASS_NAME).getConstructor(null).newInstance(null);
        } catch (Throwable unused) {
            return null;
        }
    }
}

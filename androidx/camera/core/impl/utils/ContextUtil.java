package androidx.camera.core.impl.utils;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ContextUtil {
    public static Context getApplicationContext(Context context) {
        int deviceId;
        Context applicationContext = context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 34 && (deviceId = Api34Impl.getDeviceId(context)) != Api34Impl.getDeviceId(applicationContext)) {
            applicationContext = Api34Impl.createDeviceContext(applicationContext, deviceId);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            String attributionTag = Api30Impl.getAttributionTag(context);
            if (!Objects.equals(attributionTag, Api30Impl.getAttributionTag(applicationContext))) {
                return Api30Impl.createAttributionContext(applicationContext, attributionTag);
            }
        }
        return applicationContext;
    }

    public static Application getApplicationFromContext(Context context) {
        for (Context applicationContext = getApplicationContext(context); applicationContext instanceof ContextWrapper; applicationContext = ((ContextWrapper) applicationContext).getBaseContext()) {
            if (applicationContext instanceof Application) {
                return (Application) applicationContext;
            }
        }
        return null;
    }

    private ContextUtil() {
    }

    private static class Api30Impl {
        private Api30Impl() {
        }

        static Context createAttributionContext(Context context, String str) {
            return context.createAttributionContext(str);
        }

        static String getAttributionTag(Context context) {
            return context.getAttributionTag();
        }
    }

    private static class Api34Impl {
        private Api34Impl() {
        }

        static Context createDeviceContext(Context context, int i) {
            return context.createDeviceContext(i);
        }

        static int getDeviceId(Context context) {
            return context.getDeviceId();
        }
    }
}

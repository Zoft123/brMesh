package cn.com.broadlink.blelight.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class EAppUtils {
    private static final EActivityLifecycleImpl ACTIVITY_LIFECYCLE = new EActivityLifecycleImpl();
    private static Application sApplication;

    private EAppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context) {
        init((Application) context.getApplicationContext());
    }

    public static void init(Application application) {
        if (sApplication == null) {
            sApplication = application;
            application.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
    }

    public static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.mActivityList;
    }

    public static Activity getTopActivity() {
        return ACTIVITY_LIFECYCLE.getTopActivity();
    }

    public static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
            return topActivity == null ? getApp() : topActivity;
        }
        return getApp();
    }

    public static boolean isAppForeground() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) getApp().getSystemService("activity");
        if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null && runningAppProcesses.size() != 0) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100) {
                    return runningAppProcessInfo.processName.equals(getApp().getPackageName());
                }
            }
        }
        return false;
    }

    public static String getString(int i) {
        return getTopActivityOrApp().getString(i);
    }

    public static Application getApp() {
        Application application = sApplication;
        if (application != null) {
            return application;
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("getApplication", null).invoke(cls.getMethod("currentActivityThread", null).invoke(null, null), null);
            if (objInvoke == null) {
                throw new NullPointerException("u should init first");
            }
            init((Application) objInvoke);
            return sApplication;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NullPointerException("u should init first");
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            throw new NullPointerException("u should init first");
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            throw new NullPointerException("u should init first");
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            throw new NullPointerException("u should init first");
        }
    }

    public static boolean isAppDebug() {
        return isAppDebug(getApp().getPackageName());
    }

    public static boolean isAppDebug(String str) {
        if (isSpace(str)) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = getApp().getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                if ((applicationInfo.flags & 2) != 0) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAppSystem() {
        return isAppSystem(getApp().getPackageName());
    }

    public static boolean isAppSystem(String str) {
        if (isSpace(str)) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = getApp().getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                if ((applicationInfo.flags & 1) != 0) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exitApp() {
        LinkedList<Activity> activityList = getActivityList();
        for (int size = activityList.size() - 1; size >= 0; size--) {
            activityList.get(size).finish();
        }
    }

    public static Drawable getAppIcon() {
        return getAppIcon(getApp().getPackageName());
    }

    public static Drawable getAppIcon(String str) {
        if (isSpace(str)) {
            return null;
        }
        try {
            PackageManager packageManager = getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAppPackageName() {
        return getApp().getPackageName();
    }

    public static String getAppName() {
        return getAppName(getApp().getPackageName());
    }

    public static String getAppName(String str) {
        if (isSpace(str)) {
            return "";
        }
        try {
            PackageManager packageManager = getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAppPath() {
        return getAppPath(getApp().getPackageName());
    }

    public static String getAppPath(String str) {
        if (isSpace(str)) {
            return "";
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAppVersionName() {
        return getAppVersionName(getApp().getPackageName());
    }

    public static String getAppVersionName(String str) {
        if (isSpace(str)) {
            return "";
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(getApp().getPackageName());
    }

    public static int getAppVersionCode(String str) {
        if (isSpace(str)) {
            return -1;
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return -1;
            }
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static AppInfo getAppInfo() {
        return getAppInfo(getApp().getPackageName());
    }

    public static AppInfo getAppInfo(String str) {
        try {
            PackageManager packageManager = getApp().getPackageManager();
            return getBean(packageManager, packageManager.getPackageInfo(str, 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppInfo> getAppsInfo() {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = getApp().getPackageManager();
        Iterator<PackageInfo> it = packageManager.getInstalledPackages(0).iterator();
        while (it.hasNext()) {
            AppInfo bean = getBean(packageManager, it.next());
            if (bean != null) {
                arrayList.add(bean);
            }
        }
        return arrayList;
    }

    private static AppInfo getBean(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageManager == null || packageInfo == null) {
            return null;
        }
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        return new AppInfo(packageInfo.packageName, applicationInfo.loadLabel(packageManager).toString(), applicationInfo.loadIcon(packageManager), applicationInfo.sourceDir, packageInfo.versionName, packageInfo.versionCode, (applicationInfo.flags & 1) != 0);
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEn(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().startsWith("en");
    }

    public static boolean isZh(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().startsWith("zh");
    }

    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        StringBuffer stringBuffer = new StringBuffer(locale.getLanguage());
        stringBuffer.append("-");
        stringBuffer.append(country);
        return stringBuffer.toString().toLowerCase();
    }

    public static class AppInfo {
        private Drawable icon;
        private boolean isSystem;
        private String name;
        private String packageName;
        private String packagePath;
        private int versionCode;
        private String versionName;

        public Drawable getIcon() {
            return this.icon;
        }

        public void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public boolean isSystem() {
            return this.isSystem;
        }

        public void setSystem(boolean z) {
            this.isSystem = z;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String str) {
            this.packageName = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getPackagePath() {
            return this.packagePath;
        }

        public void setPackagePath(String str) {
            this.packagePath = str;
        }

        public int getVersionCode() {
            return this.versionCode;
        }

        public void setVersionCode(int i) {
            this.versionCode = i;
        }

        public String getVersionName() {
            return this.versionName;
        }

        public void setVersionName(String str) {
            this.versionName = str;
        }

        public AppInfo(String str, String str2, Drawable drawable, String str3, String str4, int i, boolean z) {
            setName(str2);
            setIcon(drawable);
            setPackageName(str);
            setPackagePath(str3);
            setVersionName(str4);
            setVersionCode(i);
            setSystem(z);
        }

        public String toString() {
            return "pkg name: " + getPackageName() + "\napp icon: " + getIcon() + "\napp name: " + getName() + "\napp path: " + getPackagePath() + "\napp v name: " + getVersionName() + "\napp v code: " + getVersionCode() + "\nis system: " + isSystem();
        }
    }

    public static String did2Mac(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (str.length() == 32) {
            String strSubstring = str.substring(20);
            while (i < 6) {
                if (i > 0) {
                    sb.append(":");
                }
                int i2 = i * 2;
                i++;
                sb.append(strSubstring.substring(i2, i * 2));
            }
        } else if (str.length() == 12) {
            while (i < 6) {
                if (i > 0) {
                    sb.append(":");
                }
                int i3 = i * 2;
                i++;
                sb.append(str.substring(i3, i * 2));
            }
        }
        return sb.toString();
    }

    public static String shortDid2Long(String str) {
        if (str == null) {
            return null;
        }
        boolean z = !(str.length() == 40 && str.contains("_user_vt")) && str.contains("_");
        if (str.length() != 12 && !z) {
            return str;
        }
        return "00000000000000000000" + str;
    }

    public static byte[] mergeArrays(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}

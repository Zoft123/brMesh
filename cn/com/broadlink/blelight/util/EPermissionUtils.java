package cn.com.broadlink.blelight.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class EPermissionUtils {
    private static final List<String> PERMISSIONS = getPermissions();
    private static EPermissionUtils sInstance;
    private FullCallback mFullCallback;
    private OnRationaleListener mOnRationaleListener;
    private Set<String> mPermissions = new LinkedHashSet();
    private List<String> mPermissionsDenied;
    private List<String> mPermissionsDeniedForever;
    private List<String> mPermissionsGranted;
    private List<String> mPermissionsRequest;
    private SimpleCallback mSimpleCallback;
    private ThemeCallback mThemeCallback;

    public interface FullCallback {
        void onDenied(List<String> list, List<String> list2);

        void onGranted(List<String> list);
    }

    public interface OnRationaleListener {

        public interface ShouldRequest {
            void again(boolean z);
        }

        void rationale(ShouldRequest shouldRequest);
    }

    public interface SimpleCallback {
        void onDenied();

        void onGranted();
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }

    public static List<String> getPermissions() {
        return getPermissions(EAppUtils.getApp().getPackageName());
    }

    public static List<String> getPermissions(String str) {
        try {
            return Arrays.asList(EAppUtils.getApp().getPackageManager().getPackageInfo(str, 4096).requestedPermissions);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public static boolean isGranted(String... strArr) {
        for (String str : strArr) {
            if (!isGranted(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGranted(String str) {
        return ContextCompat.checkSelfPermission(EAppUtils.getApp(), str) == 0;
    }

    public static void launchAppDetailsSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + EAppUtils.getApp().getPackageName()));
        EAppUtils.getApp().startActivity(intent.addFlags(268435456));
    }

    public static EPermissionUtils permission(String... strArr) {
        return new EPermissionUtils(strArr);
    }

    private EPermissionUtils(String... strArr) {
        for (String str : strArr) {
            for (String str2 : EPermissionConstants.getPermissions(str)) {
                if (PERMISSIONS.contains(str2)) {
                    this.mPermissions.add(str2);
                }
            }
        }
        sInstance = this;
    }

    public EPermissionUtils rationale(OnRationaleListener onRationaleListener) {
        this.mOnRationaleListener = onRationaleListener;
        return this;
    }

    public EPermissionUtils callback(SimpleCallback simpleCallback) {
        this.mSimpleCallback = simpleCallback;
        return this;
    }

    public EPermissionUtils callback(FullCallback fullCallback) {
        this.mFullCallback = fullCallback;
        return this;
    }

    public EPermissionUtils theme(ThemeCallback themeCallback) {
        this.mThemeCallback = themeCallback;
        return this;
    }

    public void request() {
        this.mPermissionsGranted = new ArrayList();
        this.mPermissionsRequest = new ArrayList();
        for (String str : this.mPermissions) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsRequest.add(str);
            }
        }
        if (this.mPermissionsRequest.isEmpty()) {
            requestCallback();
        } else {
            startPermissionActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPermissionActivity() {
        this.mPermissionsDenied = new ArrayList();
        this.mPermissionsDeniedForever = new ArrayList();
        PermissionActivity.start(EAppUtils.getApp());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean rationale(Activity activity) {
        boolean z = false;
        if (this.mOnRationaleListener != null) {
            Iterator<String> it = this.mPermissionsRequest.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (activity.shouldShowRequestPermissionRationale(it.next())) {
                    getPermissionsStatus(activity);
                    this.mOnRationaleListener.rationale(new OnRationaleListener.ShouldRequest() { // from class: cn.com.broadlink.blelight.util.EPermissionUtils.1
                        @Override // cn.com.broadlink.blelight.util.EPermissionUtils.OnRationaleListener.ShouldRequest
                        public void again(boolean z2) {
                            if (z2) {
                                EPermissionUtils.this.startPermissionActivity();
                            } else {
                                EPermissionUtils.this.requestCallback();
                            }
                        }
                    });
                    z = true;
                    break;
                }
            }
            this.mOnRationaleListener = null;
        }
        return z;
    }

    private void getPermissionsStatus(Activity activity) {
        for (String str : this.mPermissionsRequest) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsDenied.add(str);
                if (!activity.shouldShowRequestPermissionRationale(str)) {
                    this.mPermissionsDeniedForever.add(str);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCallback() {
        if (this.mSimpleCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissions.size() == this.mPermissionsGranted.size()) {
                this.mSimpleCallback.onGranted();
            } else if (!this.mPermissionsDenied.isEmpty()) {
                this.mSimpleCallback.onDenied();
            }
            this.mSimpleCallback = null;
        }
        if (this.mFullCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissions.size() == this.mPermissionsGranted.size()) {
                this.mFullCallback.onGranted(this.mPermissionsGranted);
            } else if (!this.mPermissionsDenied.isEmpty()) {
                this.mFullCallback.onDenied(this.mPermissionsDeniedForever, this.mPermissionsDenied);
            }
            this.mFullCallback = null;
        }
        this.mOnRationaleListener = null;
        this.mThemeCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestPermissionsResult(Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }

    public static class PermissionActivity extends Activity {
        public static void start(Context context) {
            Intent intent = new Intent(context, (Class<?>) PermissionActivity.class);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }

        @Override // android.app.Activity
        protected void onCreate(Bundle bundle) {
            getWindow().addFlags(262160);
            if (EPermissionUtils.sInstance != null) {
                if (EPermissionUtils.sInstance.mThemeCallback != null) {
                    EPermissionUtils.sInstance.mThemeCallback.onActivityCreate(this);
                }
                super.onCreate(bundle);
                if (!EPermissionUtils.sInstance.rationale(this)) {
                    if (EPermissionUtils.sInstance.mPermissionsRequest != null) {
                        int size = EPermissionUtils.sInstance.mPermissionsRequest.size();
                        if (size > 0) {
                            requestPermissions((String[]) EPermissionUtils.sInstance.mPermissionsRequest.toArray(new String[size]), 1);
                            return;
                        } else {
                            finish();
                            return;
                        }
                    }
                    return;
                }
                finish();
                return;
            }
            super.onCreate(bundle);
            Log.e("BLPermissionUtils", "request permissions failed");
            finish();
        }

        @Override // android.app.Activity
        public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
            EPermissionUtils.sInstance.onRequestPermissionsResult(this);
            finish();
        }

        @Override // android.app.Activity, android.view.Window.Callback
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            finish();
            return true;
        }
    }
}

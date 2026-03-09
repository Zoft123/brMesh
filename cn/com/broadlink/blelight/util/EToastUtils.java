package cn.com.broadlink.blelight.util;

import android.content.Context;
import android.widget.Toast;

/* JADX INFO: loaded from: classes.dex */
public final class EToastUtils {
    private static Toast mToast;

    public static void show(int i) {
        show(EAppUtils.getApp(), i);
    }

    public static void show(String str) {
        show(EAppUtils.getApp(), str);
    }

    public static void show(Context context, int i) {
        show(context, context.getString(i));
    }

    public static void show(Context context, String str) {
        cancel();
        Toast toastMakeText = Toast.makeText(context, str, 0);
        mToast = toastMakeText;
        toastMakeText.show();
    }

    public static void cancel() {
        Toast toast = mToast;
        if (toast != null) {
            toast.cancel();
        }
    }
}

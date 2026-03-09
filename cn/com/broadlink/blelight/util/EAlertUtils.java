package cn.com.broadlink.blelight.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import cn.com.broadlink.blelight.R;

/* JADX INFO: loaded from: classes.dex */
public class EAlertUtils {
    public static AlertDialog showSimpleDialog(String str, DialogInterface.OnClickListener onClickListener) {
        return showSimpleDialog(str, "OK", (String) null, onClickListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog showSimpleDialog(String str, int i, DialogInterface.OnClickListener onClickListener) {
        return showSimpleDialog(str, EAppUtils.getTopActivity().getString(i), (String) null, onClickListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog showSimpleDialog(String str, int i, int i2, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2) {
        return showSimpleDialog(str, EAppUtils.getTopActivity().getString(i), EAppUtils.getTopActivity().getString(i2), onClickListener, onClickListener2);
    }

    public static AlertDialog showSimpleCancelDialog(String str, DialogInterface.OnClickListener onClickListener) {
        return showSimpleDialog(str, EAppUtils.getTopActivity().getString(R.string.common_sure), EAppUtils.getTopActivity().getString(R.string.common_cancel), onClickListener, (DialogInterface.OnClickListener) null);
    }

    public static AlertDialog showSimpleDialog(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2) {
        Context topActivityOrApp = EAppUtils.getTopActivityOrApp();
        if (topActivityOrApp instanceof Activity) {
            AlertDialog alertDialogCreate = new AlertDialog.Builder(topActivityOrApp).setMessage(str).setPositiveButton(str2, onClickListener).setNegativeButton(str3, onClickListener2).setCancelable(false).create();
            alertDialogCreate.show();
            return alertDialogCreate;
        }
        EToastUtils.show(str);
        return null;
    }
}

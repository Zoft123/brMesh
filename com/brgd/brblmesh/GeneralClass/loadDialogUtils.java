package com.brgd.brblmesh.GeneralClass;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.brgd.brblmesh.R;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class loadDialogUtils {
    public static Dialog createLoadingDialog(Context context, int i) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.dialog_loading_view);
        ((TextView) viewInflate.findViewById(R.id.tipTextView)).setText(i);
        Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(linearLayout, new LinearLayout.LayoutParams(-1, -1));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        window.setGravity(17);
        window.setAttributes(attributes);
        dialog.show();
        return dialog;
    }

    public static void closeDialog(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.dismiss();
    }
}

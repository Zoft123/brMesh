package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class GlobalToast extends Toast {
    private static GlobalToast toast;

    private GlobalToast(Context context) {
        super(context);
    }

    private static void cancelToast() {
        GlobalToast globalToast = toast;
        if (globalToast != null) {
            globalToast.cancel();
        }
    }

    @Override // android.widget.Toast
    public void cancel() {
        try {
            super.cancel();
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
    }

    @Override // android.widget.Toast
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
    }

    private static void initToast(Context context, int i) {
        try {
            cancelToast();
            toast = new GlobalToast(context);
            View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast_layout, (ViewGroup) null);
            ((TextView) viewInflate.findViewById(R.id.toast_text)).setText(i);
            toast.setView(viewInflate);
            toast.setGravity(17, 0, 0);
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
    }

    private static void showToast(Context context, int i, int i2) {
        initToast(context, i);
        if (i2 == 1) {
            toast.setDuration(1);
        } else {
            toast.setDuration(0);
        }
        toast.show();
    }

    public static void showText(Context context, int i, int i2) {
        showToast(context, i, i2);
    }

    private static void initToastWithString(Context context, String str) {
        try {
            cancelToast();
            toast = new GlobalToast(context);
            View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast_layout, (ViewGroup) null);
            ((TextView) viewInflate.findViewById(R.id.toast_text)).setText(str);
            toast.setView(viewInflate);
            toast.setGravity(17, 0, 0);
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
    }

    private static void showToastWithString(Context context, String str, int i) {
        initToastWithString(context, str);
        if (i == 1) {
            toast.setDuration(1);
        } else {
            toast.setDuration(0);
        }
        toast.show();
    }

    public static void showTextWithString(Context context, String str, int i) {
        showToastWithString(context, str, i);
    }
}

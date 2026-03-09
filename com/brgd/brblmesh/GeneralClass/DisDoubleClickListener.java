package com.brgd.brblmesh.GeneralClass;

import android.view.View;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class DisDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;

    protected abstract void disDoubleClick(View view);

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (timeInMillis - this.lastClickTime > 500) {
            this.lastClickTime = timeInMillis;
            disDoubleClick(view);
        }
    }
}

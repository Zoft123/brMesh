package com.brgd.brblmesh.GeneralClass.ble.bleTool;

/* JADX INFO: loaded from: classes.dex */
interface CallbackHandler {
    void post(Runnable runnable);

    void postDelayed(Runnable runnable, long j);

    void removeCallbacks(Runnable runnable);
}

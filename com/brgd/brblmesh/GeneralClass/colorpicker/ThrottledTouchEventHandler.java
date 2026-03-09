package com.brgd.brblmesh.GeneralClass.colorpicker;

import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
class ThrottledTouchEventHandler {
    private final Updatable updatable;
    int minInterval = 100;
    private long lastPassedEventTime = 0;

    ThrottledTouchEventHandler(Updatable updatable) {
        this.updatable = updatable;
    }

    void onTouchEvent(MotionEvent motionEvent) {
        if (this.updatable == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastPassedEventTime <= this.minInterval) {
            return;
        }
        this.lastPassedEventTime = jCurrentTimeMillis;
        this.updatable.update(motionEvent);
    }
}

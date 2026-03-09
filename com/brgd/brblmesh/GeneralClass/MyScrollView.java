package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/* JADX INFO: loaded from: classes.dex */
public class MyScrollView extends ScrollView {
    private final GestureDetector mGestureDetector;

    public MyScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent) && this.mGestureDetector.onTouchEvent(motionEvent);
    }

    static class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        YScrollDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return Math.abs(f2) > 160.0f;
        }
    }
}

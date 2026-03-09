package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatSeekBar;

/* JADX INFO: loaded from: classes.dex */
public class NonSlideSeekBar extends AppCompatSeekBar {
    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public NonSlideSeekBar(Context context) {
        super(context);
    }

    public NonSlideSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NonSlideSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        return true;
    }
}

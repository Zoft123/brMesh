package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;

/* JADX INFO: loaded from: classes.dex */
class BadgeTextView extends AppCompatTextView {
    private boolean mAreDimensOverridden;
    private int mDesiredHeight;
    private int mDesiredWidth;
    private ShapeBadgeItem mShapeBadgeItem;

    private void init() {
    }

    public BadgeTextView(Context context) {
        this(context, null);
    }

    public BadgeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BadgeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDesiredWidth = 100;
        this.mDesiredHeight = 100;
        init();
    }

    void clearPrevious() {
        this.mAreDimensOverridden = false;
        this.mShapeBadgeItem = null;
    }

    void setShapeBadgeItem(ShapeBadgeItem shapeBadgeItem) {
        this.mShapeBadgeItem = shapeBadgeItem;
    }

    void setDimens(int i, int i2) {
        this.mAreDimensOverridden = true;
        this.mDesiredWidth = i;
        this.mDesiredHeight = i2;
        requestLayout();
    }

    void recallOnDraw() {
        invalidate();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ShapeBadgeItem shapeBadgeItem = this.mShapeBadgeItem;
        if (shapeBadgeItem != null) {
            shapeBadgeItem.draw(canvas);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mAreDimensOverridden) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size2 = View.MeasureSpec.getSize(i2);
            if (mode == Integer.MIN_VALUE) {
                size = Math.min(this.mDesiredWidth, size);
            } else if (mode != 1073741824) {
                size = this.mDesiredWidth;
            }
            if (mode2 == Integer.MIN_VALUE) {
                size2 = Math.min(this.mDesiredHeight, size2);
            } else if (mode2 != 1073741824) {
                size2 = this.mDesiredHeight;
            }
            setMeasuredDimension(size, size2);
            return;
        }
        super.onMeasure(i, i2);
    }
}

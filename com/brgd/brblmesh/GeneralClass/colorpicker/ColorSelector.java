package com.brgd.brblmesh.GeneralClass.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ColorSelector extends View {
    private final Paint borderSelectorPaint;
    private float borderSelectorRadius;
    float centerX;
    float centerY;
    private int currentSelectorColor;
    private final Paint selectorPaint;
    private float selectorRadius;

    public ColorSelector(Context context) {
        this(context, null);
    }

    public ColorSelector(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorSelector(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint(1);
        this.borderSelectorPaint = paint;
        paint.setColor(0);
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.selectorPaint = paint2;
        this.currentSelectorColor = -65536;
        paint2.setColor(-65536);
        paint2.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        float fMin = Math.min((i - getPaddingLeft()) - getPaddingRight(), (i2 - getPaddingTop()) - getPaddingBottom()) * 0.5f;
        this.borderSelectorRadius = fMin;
        if (this.selectorRadius < 0.0f) {
            return;
        }
        this.selectorRadius = fMin - 10.0f;
        this.centerX = i * 0.5f;
        this.centerY = i2 * 0.5f;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.centerX, this.centerY, this.borderSelectorRadius, this.borderSelectorPaint);
        canvas.drawCircle(this.centerX, this.centerY, this.selectorRadius, this.selectorPaint);
    }

    public void setSelectorColor(int i) {
        this.selectorPaint.setColor(i);
        this.currentSelectorColor = i;
        invalidate();
    }

    public int getCurrentSelectorColor() {
        return this.currentSelectorColor;
    }

    public void setBorderSelectorColor(int i) {
        this.borderSelectorPaint.setColor(i);
        invalidate();
    }
}

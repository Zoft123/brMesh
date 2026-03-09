package com.brgd.brblmesh.GeneralClass.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ColorWheelSelector extends View {
    private PointF currentPoint;
    private final Paint selectorBottomPaint;
    private final Paint selectorPaint;
    private float selectorRadiusPx;

    public ColorWheelSelector(Context context) {
        this(context, null);
    }

    public ColorWheelSelector(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorWheelSelector(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currentPoint = new PointF();
        Paint paint = new Paint(1);
        this.selectorBottomPaint = paint;
        paint.setColor(-1);
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.selectorPaint = paint2;
        paint2.setColor(-256);
        paint2.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.currentPoint.x, this.currentPoint.y, this.selectorRadiusPx * 2.5f, this.selectorBottomPaint);
        canvas.drawCircle(this.currentPoint.x, this.currentPoint.y, this.selectorRadiusPx * 2.0f, this.selectorPaint);
    }

    public void setSelectorRadiusPx(float f) {
        this.selectorRadiusPx = f;
    }

    public void setCurrentPoint(PointF pointF) {
        this.currentPoint = pointF;
        invalidate();
    }

    public void setSelectorColor(int i) {
        this.selectorPaint.setColor(i);
    }
}

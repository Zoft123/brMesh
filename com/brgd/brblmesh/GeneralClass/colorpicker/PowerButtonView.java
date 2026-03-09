package com.brgd.brblmesh.GeneralClass.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class PowerButtonView extends View {
    private final Paint bottomPaint;
    private float bottomRadius;
    private float centerX;
    private float centerY;
    private final Paint middlePaint;
    private float middleRadius;
    private final Paint topPaint;
    private float topY;

    public PowerButtonView(Context context) {
        this(context, null);
    }

    public PowerButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PowerButtonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint(1);
        this.bottomPaint = paint;
        paint.setColor(CustomColor.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10.0f);
        Paint paint2 = new Paint(1);
        this.middlePaint = paint2;
        paint2.setColor(CustomColor.POWER);
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint(1);
        this.topPaint = paint3;
        paint3.setColor(CustomColor.POWER);
        paint3.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        float fMin = Math.min((i - getPaddingLeft()) - getPaddingRight(), (i2 - getPaddingTop()) - getPaddingBottom()) * 0.5f;
        if (fMin < 0.0f) {
            return;
        }
        this.bottomRadius = fMin;
        this.middleRadius = fMin - 10.0f;
        this.centerX = i * 0.5f;
        float f = i2 * 0.5f;
        this.centerY = f;
        this.topY = f - fMin;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.centerX, this.centerY, this.bottomRadius, this.bottomPaint);
        canvas.drawCircle(this.centerX, this.centerY, this.middleRadius, this.middlePaint);
        canvas.drawCircle(this.centerX, this.topY, (this.bottomRadius * 2.0f) / 3.0f, this.topPaint);
        float f = this.centerX;
        canvas.drawLine(f, this.topY, f, (this.centerY * 3.0f) / 4.0f, this.bottomPaint);
    }

    public void setSelectorColor(int i) {
        invalidate();
        if (isSelected()) {
            this.bottomPaint.setColor(i);
        } else {
            this.bottomPaint.setColor(CustomColor.OFF);
        }
    }
}

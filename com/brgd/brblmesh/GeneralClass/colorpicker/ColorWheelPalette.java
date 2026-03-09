package com.brgd.brblmesh.GeneralClass.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ColorWheelPalette extends View {
    private float centerX;
    private float centerY;
    private final Paint huePaint;
    private float radius;

    public ColorWheelPalette(Context context) {
        this(context, null);
    }

    public ColorWheelPalette(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorWheelPalette(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint(1);
        this.huePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        float fMin = Math.min((i - getPaddingLeft()) - getPaddingRight(), (i2 - getPaddingTop()) - getPaddingBottom()) * 0.5f;
        this.radius = fMin;
        if (fMin < 0.0f) {
            return;
        }
        this.centerX = i * 0.5f;
        this.centerY = i2 * 0.5f;
        this.huePaint.setStrokeWidth(fMin / 4.0f);
        this.huePaint.setShader(new SweepGradient(this.centerX, this.centerY, new int[]{-65536, CustomColor.MAGENTA, CustomColor.BLUE, CustomColor.CYAN, CustomColor.GREEN, -256, -65536}, (float[]) null));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.centerX, this.centerY, (this.radius / 8.0f) * 7.0f, this.huePaint);
    }
}

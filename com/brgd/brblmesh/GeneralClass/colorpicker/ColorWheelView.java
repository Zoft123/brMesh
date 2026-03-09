package com.brgd.brblmesh.GeneralClass.colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.camera.video.AudioStats;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.brgd.brblmesh.GeneralClass.Tool;

/* JADX INFO: loaded from: classes.dex */
public class ColorWheelView extends FrameLayout implements ColorObservable, Updatable {
    public float centerX;
    public float centerY;
    private int currentColor;
    private final PointF currentPoint;
    private final ColorObservableEmitter emitter;
    private final int[] mColors;
    private OnSetColorListener onSetColorListener;
    private final boolean onlyUpdateOnTouchEventUp;
    public float radius;
    private final ColorWheelSelector selector;
    private final float selectorRadiusPx;

    public interface OnSetColorListener {
        void OnSetColor(int i);
    }

    public ColorWheelView(Context context) {
        this(context, null);
    }

    public ColorWheelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorWheelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColors = new int[]{-65536, CustomColor.MAGENTA, CustomColor.BLUE, CustomColor.CYAN, CustomColor.GREEN, -256, -65536};
        this.currentPoint = new PointF();
        this.currentColor = -65536;
        this.onlyUpdateOnTouchEventUp = false;
        this.emitter = new ColorObservableEmitter();
        float f = getResources().getDisplayMetrics().density * 9.0f;
        this.selectorRadiusPx = f;
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        ColorWheelPalette colorWheelPalette = new ColorWheelPalette(context);
        int i2 = ((int) f) * 2;
        colorWheelPalette.setPadding(i2, i2, i2, i2);
        addView(colorWheelPalette, layoutParams);
        ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        ColorWheelSelector colorWheelSelector = new ColorWheelSelector(context);
        this.selector = colorWheelSelector;
        colorWheelSelector.setSelectorRadiusPx(f);
        addView(colorWheelSelector, layoutParams2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int iMin = Math.min(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(iMin, BasicMeasure.EXACTLY));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
        int paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
        float fMin = (Math.min(paddingLeft, paddingTop) * 0.5f) - this.selectorRadiusPx;
        this.radius = fMin;
        if (fMin < 0.0f) {
            return;
        }
        this.centerX = paddingLeft * 0.5f;
        this.centerY = paddingTop * 0.5f;
        setColor(this.currentColor, false);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0 || actionMasked == 1 || actionMasked == 2) {
            update(motionEvent);
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.Updatable
    public void update(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float f = x - this.centerX;
        float f2 = y - this.centerY;
        double dSqrt = Math.sqrt((f * f) + (f2 * f2));
        float f3 = this.radius;
        if (dSqrt - ((double) f3) > 30.0d || dSqrt - ((double) f3) < -160.0d) {
            return;
        }
        boolean z = motionEvent.getActionMasked() == 1;
        this.selector.setSelectorColor(fillPaint(motionEvent));
        if (z) {
            this.emitter.onColor(fillPaint(motionEvent), true, true);
            this.selector.setSelectorColor(fillPaint(motionEvent));
            OnSetColorListener onSetColorListener = this.onSetColorListener;
            if (onSetColorListener != null) {
                onSetColorListener.OnSetColor(fillPaint(motionEvent));
            }
        }
        updateSelector(x, y);
    }

    private int fillPaint(MotionEvent motionEvent) {
        float fAtan2 = (float) (((double) ((float) Math.atan2(motionEvent.getY() - this.centerY, motionEvent.getX() - this.centerX))) / 6.283185307179586d);
        if (fAtan2 < 0.0f) {
            fAtan2 += 1.0f;
        }
        return interColor(this.mColors, fAtan2);
    }

    private int ave(int i, int i2, float f) {
        return i + Math.round(f * (i2 - i));
    }

    private int interColor(int[] iArr, float f) {
        if (f <= 0.0f) {
            return iArr[0];
        }
        if (f >= 1.0f) {
            return iArr[iArr.length - 1];
        }
        float length = f * (iArr.length - 1);
        int i = (int) length;
        float f2 = length - i;
        int i2 = iArr[i];
        int i3 = iArr[i + 1];
        return Color.argb(ave(Color.alpha(i2), Color.alpha(i3), f2), ave(Color.red(i2), Color.red(i3), f2), ave(Color.green(i2), Color.green(i3), f2), ave(Color.blue(i2), Color.blue(i3), f2));
    }

    public void setColor(int i, boolean z) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        float f = fArr[1] * this.radius;
        double d = f;
        double d2 = (float) (((double) (fArr[0] / 180.0f)) * 3.141592653589793d);
        double d3 = -f;
        updateSelector((float) ((Math.cos(d2) * d) + ((double) this.centerX)), (float) ((Math.sin(d2) * d3) + ((double) this.centerY)));
        this.currentColor = i;
        this.emitter.onColor(i, false, z);
        if (i == -16777216 || Tool.isRGBW(i)) {
            this.selector.setVisibility(4);
        } else {
            this.selector.setVisibility(0);
            this.selector.setSelectorColor(fillPaint1((float) ((d * Math.cos(d2)) + ((double) this.centerX)), (float) ((d3 * Math.sin(d2)) + ((double) this.centerY))));
        }
    }

    public void setColor1(int i, boolean z) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        float f = fArr[1] * this.radius;
        double d = f;
        double d2 = (float) (((double) (fArr[0] / 180.0f)) * 3.141592653589793d);
        double d3 = -f;
        updateSelector((float) ((Math.cos(d2) * d) + ((double) this.centerX)), (float) ((Math.sin(d2) * d3) + ((double) this.centerY)));
        this.currentColor = i;
        this.emitter.onColor(i, true, z);
        if (i == -16777216 || Tool.isRGBW(i)) {
            this.selector.setVisibility(4);
        } else {
            this.selector.setVisibility(0);
            this.selector.setSelectorColor(fillPaint1((float) ((d * Math.cos(d2)) + ((double) this.centerX)), (float) ((d3 * Math.sin(d2)) + ((double) this.centerY))));
        }
    }

    private void updateSelector(float f, float f2) {
        double d;
        float f3 = f - this.centerX;
        float f4 = f2 - this.centerY;
        double dSqrt = Math.sqrt((f3 * f3) + (f4 * f4));
        float f5 = this.radius;
        if (dSqrt <= f5) {
            if (dSqrt != AudioStats.AUDIO_AMPLITUDE_NONE) {
                f3 = (float) (((double) f3) / (dSqrt / ((double) f5)));
                d = ((double) f4) / (dSqrt / ((double) f5));
            }
            this.currentPoint.x = ((f3 * 13.0f) / 16.0f) + this.centerX;
            this.currentPoint.y = ((f4 * 13.0f) / 16.0f) + this.centerY;
            this.selector.setVisibility(0);
            this.selector.setCurrentPoint(this.currentPoint);
        }
        f3 = (float) (((double) f3) * (((double) f5) / dSqrt));
        d = ((double) f4) * (((double) f5) / dSqrt);
        f4 = (float) d;
        this.currentPoint.x = ((f3 * 13.0f) / 16.0f) + this.centerX;
        this.currentPoint.y = ((f4 * 13.0f) / 16.0f) + this.centerY;
        this.selector.setVisibility(0);
        this.selector.setCurrentPoint(this.currentPoint);
    }

    public void moveSelector(boolean z) {
        float[] fArr = new float[3];
        Color.colorToHSV(getColor(), fArr);
        if (z) {
            float f = fArr[0];
            if (f < 360.0f) {
                fArr[0] = f + 1.0f;
            } else if (f == 360.0f) {
                fArr[0] = 1.0f;
            }
        } else {
            float f2 = fArr[0];
            if (f2 > 0.0f) {
                fArr[0] = f2 - 1.0f;
            } else if (f2 == 0.0f) {
                fArr[0] = 359.0f;
            }
        }
        float f3 = fArr[1] * this.radius;
        double d = (float) (((double) (fArr[0] / 180.0f)) * 3.141592653589793d);
        float fCos = (float) ((((double) f3) * Math.cos(d)) + ((double) this.centerX));
        float fSin = (float) ((((double) (-f3)) * Math.sin(d)) + ((double) this.centerY));
        updateSelector(fCos, fSin);
        setColor1(fillPaint1(fCos, fSin), false);
    }

    private int fillPaint1(float f, float f2) {
        float fAtan2 = (float) (((double) ((float) Math.atan2(f2 - this.centerY, f - this.centerX))) / 6.283185307179586d);
        if (fAtan2 < 0.0f) {
            fAtan2 += 1.0f;
        }
        return interColor(this.mColors, fAtan2);
    }

    public int getSelectorColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        float f = fArr[1] * this.radius;
        double d = (float) (((double) (fArr[0] / 180.0f)) * 3.141592653589793d);
        return fillPaint1((float) ((((double) f) * Math.cos(d)) + ((double) this.centerX)), (float) ((((double) (-f)) * Math.sin(d)) + ((double) this.centerY)));
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObservable
    public void subscribe(ColorObserver colorObserver) {
        this.emitter.subscribe(colorObserver);
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObservable
    public void unsubscribe(ColorObserver colorObserver) {
        this.emitter.unsubscribe(colorObserver);
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObservable
    public int getColor() {
        return this.emitter.getColor();
    }

    public void setOnSetColorListener(OnSetColorListener onSetColorListener) {
        this.onSetColorListener = onSetColorListener;
    }

    public void removeOnSetColorListener() {
        this.onSetColorListener = null;
    }
}

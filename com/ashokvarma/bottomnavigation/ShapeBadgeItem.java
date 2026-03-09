package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.ViewGroup;
import androidx.camera.video.AudioStats;
import androidx.core.content.ContextCompat;
import com.ashokvarma.bottomnavigation.utils.Utils;

/* JADX INFO: loaded from: classes.dex */
public class ShapeBadgeItem extends BadgeItem<ShapeBadgeItem> {
    public static final int SHAPE_HEART = 2;
    public static final int SHAPE_OVAL = 0;
    public static final int SHAPE_RECTANGLE = 1;
    public static final int SHAPE_STAR_3_VERTICES = 3;
    public static final int SHAPE_STAR_4_VERTICES = 4;
    public static final int SHAPE_STAR_5_VERTICES = 5;
    public static final int SHAPE_STAR_6_VERTICES = 6;
    private Paint mCanvasPaint;
    private int mEdgeMarginInPx;
    private int mHeightInPixels;
    private String mShapeColorCode;
    private int mShapeColorResource;
    private int mWidthInPixels;
    private int mShape = 5;
    private int mShapeColor = -65536;
    private RectF mCanvasRect = new RectF();
    private Path mPath = new Path();

    private double getStarAntiClockRotationOffset(int i) {
        if (i == 5) {
            return 0.3141592653589793d;
        }
        if (i == 6) {
            return 0.5235987755982988d;
        }
        return AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.ashokvarma.bottomnavigation.BadgeItem
    public ShapeBadgeItem getSubInstance() {
        return this;
    }

    @Override // com.ashokvarma.bottomnavigation.BadgeItem
    public /* bridge */ /* synthetic */ boolean isHidden() {
        return super.isHidden();
    }

    public ShapeBadgeItem() {
        Paint paint = new Paint();
        this.mCanvasPaint = paint;
        paint.setColor(this.mShapeColor);
        this.mCanvasPaint.setAntiAlias(true);
        this.mCanvasPaint.setStyle(Paint.Style.FILL);
    }

    public ShapeBadgeItem setShape(int i) {
        this.mShape = i;
        refreshDraw();
        return this;
    }

    public ShapeBadgeItem setShapeColorResource(int i) {
        this.mShapeColorResource = i;
        refreshColor();
        return this;
    }

    public ShapeBadgeItem setShapeColor(String str) {
        this.mShapeColorCode = str;
        refreshColor();
        return this;
    }

    public ShapeBadgeItem setShapeColor(int i) {
        this.mShapeColor = i;
        refreshColor();
        return this;
    }

    public ShapeBadgeItem setSizeInDp(Context context, int i, int i2) {
        this.mHeightInPixels = Utils.dp2px(context, i);
        this.mWidthInPixels = Utils.dp2px(context, i2);
        if (isWeakReferenceValid()) {
            getTextView().get().setDimens(this.mWidthInPixels, this.mHeightInPixels);
        }
        return this;
    }

    public ShapeBadgeItem setSizeInPixels(int i, int i2) {
        this.mHeightInPixels = i;
        this.mWidthInPixels = i2;
        if (isWeakReferenceValid()) {
            getTextView().get().setDimens(this.mWidthInPixels, this.mHeightInPixels);
        }
        return this;
    }

    public ShapeBadgeItem setEdgeMarginInDp(Context context, int i) {
        this.mEdgeMarginInPx = Utils.dp2px(context, i);
        refreshMargin();
        return this;
    }

    public ShapeBadgeItem setEdgeMarginInPixels(int i) {
        this.mEdgeMarginInPx = i;
        refreshMargin();
        return this;
    }

    void draw(Canvas canvas) {
        this.mCanvasRect.set(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        int i = this.mShape;
        switch (i) {
            case 0:
                canvas.drawOval(this.mCanvasRect, this.mCanvasPaint);
                break;
            case 1:
                canvas.drawRect(this.mCanvasRect, this.mCanvasPaint);
                break;
            case 2:
                drawHeart(canvas);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                drawStar(canvas, i);
                break;
        }
    }

    @Override // com.ashokvarma.bottomnavigation.BadgeItem
    void bindToBottomTabInternal(BottomNavigationTab bottomNavigationTab) {
        if (this.mHeightInPixels == 0) {
            this.mHeightInPixels = Utils.dp2px(bottomNavigationTab.getContext(), 12.0f);
        }
        if (this.mWidthInPixels == 0) {
            this.mWidthInPixels = Utils.dp2px(bottomNavigationTab.getContext(), 12.0f);
        }
        if (this.mEdgeMarginInPx == 0) {
            this.mEdgeMarginInPx = Utils.dp2px(bottomNavigationTab.getContext(), 4.0f);
        }
        refreshMargin();
        refreshColor();
        bottomNavigationTab.badgeView.setShapeBadgeItem(this);
        bottomNavigationTab.badgeView.setDimens(this.mWidthInPixels, this.mHeightInPixels);
    }

    private int getShapeColor(Context context) {
        int i = this.mShapeColorResource;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        if (!TextUtils.isEmpty(this.mShapeColorCode)) {
            return Color.parseColor(this.mShapeColorCode);
        }
        return this.mShapeColor;
    }

    private void refreshColor() {
        if (isWeakReferenceValid()) {
            this.mCanvasPaint.setColor(getShapeColor(getTextView().get().getContext()));
        }
        refreshDraw();
    }

    private void refreshDraw() {
        if (isWeakReferenceValid()) {
            getTextView().get().recallOnDraw();
        }
    }

    private void refreshMargin() {
        if (isWeakReferenceValid()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getTextView().get().getLayoutParams();
            marginLayoutParams.bottomMargin = this.mEdgeMarginInPx;
            marginLayoutParams.topMargin = this.mEdgeMarginInPx;
            marginLayoutParams.rightMargin = this.mEdgeMarginInPx;
            marginLayoutParams.leftMargin = this.mEdgeMarginInPx;
            getTextView().get().setLayoutParams(marginLayoutParams);
        }
    }

    private void drawStar(Canvas canvas, int i) {
        float width;
        int width2;
        int i2 = i;
        double d = 6.283185307179586d / ((double) i2);
        double d2 = d / 2.0d;
        double starAntiClockRotationOffset = getStarAntiClockRotationOffset(i2);
        float width3 = canvas.getWidth() / 2.0f;
        float height = canvas.getHeight() / 2.0f;
        if (canvas.getWidth() > canvas.getHeight()) {
            width = canvas.getHeight() * 0.5f;
            width2 = canvas.getHeight();
        } else {
            width = canvas.getWidth() * 0.5f;
            width2 = canvas.getWidth();
        }
        this.mPath.reset();
        Path path = this.mPath;
        double d3 = width3;
        double d4 = width;
        double d5 = AudioStats.AUDIO_AMPLITUDE_NONE - starAntiClockRotationOffset;
        double d6 = height;
        path.moveTo((float) (d3 + (Math.cos(d5) * d4)), (float) (d6 + (Math.sin(d5) * d4)));
        Path path2 = this.mPath;
        double d7 = width2 * 0.25f;
        double d8 = (d2 + AudioStats.AUDIO_AMPLITUDE_NONE) - starAntiClockRotationOffset;
        path2.lineTo((float) (d3 + (Math.cos(d8) * d7)), (float) (d6 + (Math.sin(d8) * d7)));
        int i3 = 1;
        while (i3 < i2) {
            double d9 = ((double) i3) * d;
            double d10 = d9 - starAntiClockRotationOffset;
            this.mPath.lineTo((float) (d3 + (Math.cos(d10) * d4)), (float) (d6 + (Math.sin(d10) * d4)));
            double d11 = (d9 + d2) - starAntiClockRotationOffset;
            this.mPath.lineTo((float) ((Math.cos(d11) * d7) + d3), (float) (d6 + (Math.sin(d11) * d7)));
            i3++;
            i2 = i;
        }
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mCanvasPaint);
    }

    private void drawHeart(Canvas canvas) {
        this.mPath.reset();
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        float f = width / 2.0f;
        float f2 = height / 5.0f;
        this.mPath.moveTo(f, f2);
        float f3 = height / 15.0f;
        float f4 = 2.0f * height;
        float f5 = f4 / 5.0f;
        this.mPath.cubicTo((width * 5.0f) / 14.0f, 0.0f, 0.0f, f3, width / 28.0f, f5);
        float f6 = f4 / 3.0f;
        float f7 = (5.0f * height) / 6.0f;
        this.mPath.cubicTo(width / 14.0f, f6, (3.0f * width) / 7.0f, f7, f, height);
        this.mPath.cubicTo((4.0f * width) / 7.0f, f7, (13.0f * width) / 14.0f, f6, (27.0f * width) / 28.0f, f5);
        this.mPath.cubicTo(width, f3, (9.0f * width) / 14.0f, 0.0f, f, f2);
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mCanvasPaint);
    }
}

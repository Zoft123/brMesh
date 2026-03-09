package com.king.view.viewfinderview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ViewfinderView extends View {
    private static final float DEFAULT_RANGE_RATIO = 1.2f;
    private static final float MAX_ZOOM_RATIO = 1.2f;
    private static final int POINT_ANIMATION_INTERVAL = 3000;
    private float currentZoomRatio;
    private RectF frame;
    private Bitmap frameBitmap;
    private int frameColor;
    private int frameCornerColor;
    private float frameCornerRadius;
    private float frameCornerSize;
    private float frameCornerStrokeWidth;
    private FrameGravity frameGravity;
    private int frameHeight;
    private float frameLineStrokeWidth;
    private float framePaddingBottom;
    private float framePaddingLeft;
    private float framePaddingRight;
    private float framePaddingTop;
    private float frameRatio;
    private int frameWidth;
    private boolean fullRefresh;
    private GestureDetector gestureDetector;
    private boolean isPointAnimation;
    private boolean isShowPoints;
    private String labelText;
    private int labelTextColor;
    private TextLocation labelTextLocation;
    private float labelTextPadding;
    private float labelTextSize;
    private int labelTextWidth;
    private int laserAnimationInterval;
    private Bitmap laserBitmap;
    private float laserBitmapRatio;
    private float laserBitmapWidth;
    private int laserColor;
    private int laserGridColumn;
    private float laserGridHeight;
    private float laserGridStrokeWidth;
    private float laserLineHeight;
    private float laserMovementSpeed;
    private LaserStyle laserStyle;
    private float lastZoomRatio;
    private int maskColor;
    private int minDimension;
    private OnItemClickListener onItemClickListener;
    private Paint paint;
    private int pointAnimationInterval;
    private Bitmap pointBitmap;
    private int pointColor;
    private List<Point> pointList;
    private float pointRadius;
    private float pointRangeRadius;
    private int pointStrokeColor;
    private float pointStrokeRadius;
    private float pointStrokeRatio;
    private float scannerEnd;
    private float scannerStart;
    private TextPaint textPaint;
    private int viewfinderStyle;
    private int zoomCount;
    private float zoomSpeed;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewfinderStyle {
        public static final int CLASSIC = 0;
        public static final int POPULAR = 1;
    }

    private static int shadeColor(int i) {
        return (i & ViewCompat.MEASURED_SIZE_MASK) | 16777216;
    }

    public enum LaserStyle {
        NONE(0),
        LINE(1),
        GRID(2),
        IMAGE(3);

        private final int mValue;

        LaserStyle(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static LaserStyle getFromInt(int i) {
            for (LaserStyle laserStyle : values()) {
                if (laserStyle.mValue == i) {
                    return laserStyle;
                }
            }
            return LINE;
        }
    }

    public enum TextLocation {
        TOP(0),
        BOTTOM(1);

        private final int mValue;

        TextLocation(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TextLocation getFromInt(int i) {
            for (TextLocation textLocation : values()) {
                if (textLocation.mValue == i) {
                    return textLocation;
                }
            }
            return TOP;
        }
    }

    public enum FrameGravity {
        CENTER(0),
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        private final int mValue;

        FrameGravity(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static FrameGravity getFromInt(int i) {
            for (FrameGravity frameGravity : values()) {
                if (frameGravity.mValue == i) {
                    return frameGravity;
                }
            }
            return CENTER;
        }
    }

    public ViewfinderView(Context context) {
        this(context, null);
    }

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewfinderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isPointAnimation = true;
        this.currentZoomRatio = 1.0f;
        this.zoomSpeed = 0.02f;
        this.viewfinderStyle = 0;
        this.isShowPoints = false;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewfinderView);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.viewfinderStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvViewfinderStyle, 0);
        this.maskColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvMaskColor, getColor(context, R.color.viewfinder_mask));
        this.frameColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvFrameColor, getColor(context, R.color.viewfinder_frame));
        this.frameWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_vvFrameWidth, 0);
        this.frameHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_vvFrameHeight, 0);
        this.frameRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.ViewfinderView_vvFrameRatio, 0.625f);
        this.frameLineStrokeWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameLineStrokeWidth, TypedValue.applyDimension(1, 1.0f, displayMetrics));
        this.framePaddingLeft = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingLeft, 0.0f);
        this.framePaddingTop = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingTop, 0.0f);
        this.framePaddingRight = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingRight, 0.0f);
        this.framePaddingBottom = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingBottom, 0.0f);
        this.frameGravity = FrameGravity.getFromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvFrameGravity, FrameGravity.CENTER.mValue));
        this.frameCornerColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvFrameCornerColor, getColor(context, R.color.viewfinder_corner));
        this.frameCornerSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameCornerSize, TypedValue.applyDimension(1, 16.0f, displayMetrics));
        this.frameCornerStrokeWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameCornerStrokeWidth, TypedValue.applyDimension(1, 4.0f, displayMetrics));
        this.frameCornerRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameCornerRadius, 0.0f);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ViewfinderView_vvFrameDrawable);
        this.laserLineHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserLineHeight, TypedValue.applyDimension(1, 5.0f, displayMetrics));
        this.laserMovementSpeed = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserMovementSpeed, TypedValue.applyDimension(1, 2.0f, displayMetrics));
        this.laserAnimationInterval = typedArrayObtainStyledAttributes.getInteger(R.styleable.ViewfinderView_vvLaserAnimationInterval, 20);
        this.laserGridColumn = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvLaserGridColumn, 20);
        this.laserGridHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserGridHeight, TypedValue.applyDimension(1, 40.0f, displayMetrics));
        this.laserGridStrokeWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserGridStrokeWidth, TypedValue.applyDimension(1, 1.0f, displayMetrics));
        this.laserColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvLaserColor, getColor(context, R.color.viewfinder_laser));
        this.laserStyle = LaserStyle.getFromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvLaserStyle, LaserStyle.LINE.mValue));
        this.laserBitmapRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.ViewfinderView_vvLaserDrawableRatio, 0.625f);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ViewfinderView_vvLaserDrawable);
        this.labelText = typedArrayObtainStyledAttributes.getString(R.styleable.ViewfinderView_vvLabelText);
        this.labelTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvLabelTextColor, getColor(context, R.color.viewfinder_label_text));
        this.labelTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLabelTextSize, TypedValue.applyDimension(2, 14.0f, displayMetrics));
        this.labelTextPadding = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLabelTextPadding, TypedValue.applyDimension(1, 24.0f, displayMetrics));
        this.labelTextWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_vvLabelTextWidth, 0);
        this.labelTextLocation = TextLocation.getFromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvLabelTextLocation, 1));
        this.pointColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvPointColor, getColor(context, R.color.viewfinder_point));
        this.pointStrokeColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvPointStrokeColor, getColor(context, R.color.viewfinder_point_stroke));
        this.pointRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvPointRadius, TypedValue.applyDimension(1, 15.0f, displayMetrics));
        this.pointStrokeRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.ViewfinderView_vvPointStrokeRatio, 1.2f);
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ViewfinderView_vvPointDrawable);
        this.isPointAnimation = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ViewfinderView_vvPointAnimation, true);
        this.pointAnimationInterval = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvPointAnimationInterval, 3000);
        this.fullRefresh = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ViewfinderView_vvFullRefresh, false);
        typedArrayObtainStyledAttributes.recycle();
        if (drawable != null) {
            this.frameBitmap = getBitmapFormDrawable(drawable);
        }
        if (drawable2 != null) {
            this.laserBitmap = getBitmapFormDrawable(drawable2);
        }
        if (drawable3 != null) {
            this.pointBitmap = getBitmapFormDrawable(drawable3);
            this.pointRangeRadius = ((r11.getWidth() + this.pointBitmap.getHeight()) / 4.0f) * 1.2f;
        } else {
            float f = this.pointRadius * this.pointStrokeRatio;
            this.pointStrokeRadius = f;
            this.pointRangeRadius = f * 1.2f;
        }
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setAntiAlias(true);
        TextPaint textPaint = new TextPaint(1);
        this.textPaint = textPaint;
        textPaint.setAntiAlias(true);
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.king.view.viewfinderview.ViewfinderView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (ViewfinderView.this.isShowPoints && ViewfinderView.this.checkSingleTap(motionEvent.getX(), motionEvent.getY())) {
                    return true;
                }
                return super.onSingleTapUp(motionEvent);
            }
        });
    }

    private int getColor(Context context, int i) {
        return context.getColor(i);
    }

    private Bitmap getBitmapFormDrawable(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        initFrame(getWidth(), getHeight());
    }

    private void scaleLaserBitmap() {
        if (this.laserBitmap != null) {
            float f = this.laserBitmapWidth;
            if (f > 0.0f) {
                float width = f / r0.getWidth();
                Matrix matrix = new Matrix();
                matrix.postScale(width, width);
                this.laserBitmap = Bitmap.createBitmap(this.laserBitmap, 0, 0, this.laserBitmap.getWidth(), this.laserBitmap.getHeight(), matrix, true);
            }
        }
    }

    private void initFrame(int i, int i2) {
        int iMin = Math.min(i, i2);
        this.minDimension = iMin;
        int i3 = (int) (iMin * this.frameRatio);
        if (this.laserBitmapWidth <= 0.0f) {
            this.laserBitmapWidth = iMin * this.laserBitmapRatio;
            scaleLaserBitmap();
        }
        int i4 = this.frameWidth;
        if (i4 <= 0 || i4 > i) {
            this.frameWidth = i3;
        }
        int i5 = this.frameHeight;
        if (i5 <= 0 || i5 > i2) {
            this.frameHeight = i3;
        }
        if (this.labelTextWidth <= 0) {
            this.labelTextWidth = (i - getPaddingLeft()) - getPaddingRight();
        }
        float f = (((i - this.frameWidth) / 2.0f) + this.framePaddingLeft) - this.framePaddingRight;
        float f2 = (((i2 - this.frameHeight) / 2.0f) + this.framePaddingTop) - this.framePaddingBottom;
        int i6 = AnonymousClass2.$SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[this.frameGravity.ordinal()];
        if (i6 == 1) {
            f = this.framePaddingLeft;
        } else if (i6 == 2) {
            f2 = this.framePaddingTop;
        } else if (i6 == 3) {
            f = (i - this.frameWidth) + this.framePaddingRight;
        } else if (i6 == 4) {
            f2 = (i2 - this.frameHeight) + this.framePaddingBottom;
        }
        this.frame = new RectF(f, f2, this.frameWidth + f, this.frameHeight + f2);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.isShowPoints) {
            drawMask(canvas, getWidth(), getHeight());
            drawResultPoints(canvas, this.pointList);
            if (this.isPointAnimation) {
                calcPointZoomAnimation();
            }
        } else {
            RectF rectF = this.frame;
            if (rectF != null) {
                if (this.scannerStart == 0.0f) {
                    this.scannerStart = rectF.top;
                }
                this.scannerEnd = this.frame.bottom - this.laserLineHeight;
                int i = this.viewfinderStyle;
                if (i != 0) {
                    if (i == 1) {
                        drawLaserScanner(canvas, this.frame);
                        drawTextInfo(canvas, this.frame);
                        postInvalidateDelayed(this.laserAnimationInterval);
                        return;
                    }
                    return;
                }
                drawExterior(canvas, this.frame, getWidth(), getHeight());
                drawLaserScanner(canvas, this.frame);
                drawFrame(canvas, this.frame);
                drawTextInfo(canvas, this.frame);
                if (this.fullRefresh) {
                    postInvalidateDelayed(this.laserAnimationInterval);
                } else {
                    postInvalidateDelayed(this.laserAnimationInterval, (int) this.frame.left, (int) this.frame.top, (int) this.frame.right, (int) this.frame.bottom);
                }
            }
        }
    }

    private void drawTextInfo(Canvas canvas, RectF rectF) {
        if (TextUtils.isEmpty(this.labelText)) {
            return;
        }
        this.textPaint.setColor(this.labelTextColor);
        this.textPaint.setTextSize(this.labelTextSize);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        StaticLayout staticLayout = new StaticLayout(this.labelText, this.textPaint, this.labelTextWidth, Layout.Alignment.ALIGN_NORMAL, 1.2f, 0.0f, true);
        int iSave = canvas.save();
        try {
            if (this.labelTextLocation == TextLocation.BOTTOM) {
                canvas.translate(rectF.centerX(), rectF.bottom + this.labelTextPadding);
            } else {
                canvas.translate(rectF.centerX(), (rectF.top - this.labelTextPadding) - staticLayout.getHeight());
            }
            staticLayout.draw(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    private void drawFrameCorner(Canvas canvas, RectF rectF) {
        this.paint.setColor(this.frameCornerColor);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.frameCornerStrokeWidth);
        float f = (this.frameCornerStrokeWidth - this.frameLineStrokeWidth) / 2.0f;
        RectF rectF2 = new RectF(rectF.left + f, rectF.top + f, rectF.right - f, rectF.bottom - f);
        float f2 = this.frameCornerRadius;
        if (f2 > 0.0f) {
            float f3 = f2 * 2.0f;
            canvas.drawArc(new RectF(rectF2.left, rectF2.top, rectF2.left + f3, rectF2.top + f3), 180.0f, 90.0f, false, this.paint);
            canvas.drawArc(new RectF(rectF2.right - f3, rectF2.top, rectF2.right, rectF2.top + f3), 270.0f, 90.0f, false, this.paint);
            canvas.drawArc(new RectF(rectF2.right - f3, rectF2.bottom - f3, rectF2.right, rectF2.bottom), 0.0f, 90.0f, false, this.paint);
            canvas.drawArc(new RectF(rectF2.left, rectF2.bottom - f3, rectF2.left + f3, rectF2.bottom), 90.0f, 90.0f, false, this.paint);
        }
        if (this.frameCornerSize - this.frameCornerRadius > 0.0f) {
            canvas.drawLine((rectF2.left - f) + this.frameCornerRadius, rectF2.top, rectF2.left + this.frameCornerSize, rectF2.top, this.paint);
            canvas.drawLine(rectF2.left, (rectF2.top - f) + this.frameCornerRadius, rectF2.left, rectF2.top + this.frameCornerSize, this.paint);
            canvas.drawLine(rectF2.right - this.frameCornerSize, rectF2.top, (rectF2.right + f) - this.frameCornerRadius, rectF2.top, this.paint);
            canvas.drawLine(rectF2.right, (rectF2.top - f) + this.frameCornerRadius, rectF2.right, rectF2.top + this.frameCornerSize, this.paint);
            canvas.drawLine((rectF2.right + f) - this.frameCornerRadius, rectF2.bottom, rectF2.right - this.frameCornerSize, rectF2.bottom, this.paint);
            canvas.drawLine(rectF2.right, (rectF2.bottom + f) - this.frameCornerRadius, rectF2.right, rectF2.bottom - this.frameCornerSize, this.paint);
            canvas.drawLine(rectF2.left + this.frameCornerSize, rectF2.bottom, (rectF2.left - f) + this.frameCornerRadius, rectF2.bottom, this.paint);
            canvas.drawLine(rectF2.left, (rectF2.bottom + f) - this.frameCornerRadius, rectF2.left, rectF2.bottom - this.frameCornerSize, this.paint);
        }
    }

    private void drawImageScanner(Canvas canvas, RectF rectF) {
        Bitmap bitmap = this.laserBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, (getWidth() - this.laserBitmap.getWidth()) / 2.0f, this.scannerStart, this.paint);
        } else {
            drawLineScanner(canvas, rectF);
        }
    }

    private void drawLaserScanner(Canvas canvas, RectF rectF) {
        if (this.laserStyle == null) {
            return;
        }
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.laserColor);
        int i = AnonymousClass2.$SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle[this.laserStyle.ordinal()];
        if (i == 1) {
            drawLineScanner(canvas, rectF);
        } else if (i == 2) {
            drawGridScanner(canvas, rectF);
        } else if (i == 3) {
            drawImageScanner(canvas, rectF);
        }
        float f = this.scannerStart;
        if (f < this.scannerEnd) {
            this.scannerStart = f + this.laserMovementSpeed;
        } else {
            this.scannerStart = rectF.top;
        }
        this.paint.setShader(null);
    }

    /* JADX INFO: renamed from: com.king.view.viewfinderview.ViewfinderView$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity;
        static final /* synthetic */ int[] $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle;

        static {
            int[] iArr = new int[LaserStyle.values().length];
            $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle = iArr;
            try {
                iArr[LaserStyle.LINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle[LaserStyle.GRID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle[LaserStyle.IMAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[FrameGravity.values().length];
            $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity = iArr2;
            try {
                iArr2[FrameGravity.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[FrameGravity.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[FrameGravity.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[FrameGravity.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private void drawLineScanner(Canvas canvas, RectF rectF) {
        this.paint.setShader(new LinearGradient(rectF.centerX(), this.scannerStart, rectF.centerX(), this.scannerStart + this.laserLineHeight, new int[]{shadeColor(this.laserColor), this.laserColor}, (float[]) null, Shader.TileMode.CLAMP));
        canvas.drawOval(new RectF(rectF.left + this.frameCornerSize, this.scannerStart, rectF.right - this.frameCornerSize, this.scannerStart + this.laserLineHeight), this.paint);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawGridScanner(android.graphics.Canvas r10, android.graphics.RectF r11) {
        /*
            r9 = this;
            android.graphics.Paint r0 = r9.paint
            float r1 = r9.laserGridStrokeWidth
            r0.setStrokeWidth(r1)
            android.graphics.Paint r0 = r9.paint
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r0.setStyle(r1)
            float r0 = r9.laserGridHeight
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L24
            float r0 = r9.scannerStart
            float r1 = r11.top
            float r0 = r0 - r1
            float r1 = r9.laserGridHeight
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L24
            float r0 = r9.scannerStart
            float r0 = r0 - r1
            goto L26
        L24:
            float r0 = r11.top
        L26:
            r3 = r0
            android.graphics.LinearGradient r1 = new android.graphics.LinearGradient
            float r2 = r11.centerX()
            float r4 = r11.centerX()
            float r5 = r9.scannerStart
            int r0 = r9.laserColor
            int r0 = shadeColor(r0)
            int r6 = r9.laserColor
            int[] r6 = new int[]{r0, r6}
            r7 = 0
            android.graphics.Shader$TileMode r8 = android.graphics.Shader.TileMode.CLAMP
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            android.graphics.Paint r0 = r9.paint
            r0.setShader(r1)
            float r0 = r11.width()
            int r1 = r9.laserGridColumn
            float r1 = (float) r1
            float r0 = r0 / r1
            android.graphics.Path r1 = new android.graphics.Path
            r1.<init>()
            r2 = 1
        L58:
            int r4 = r9.laserGridColumn
            if (r2 >= r4) goto L6c
            float r4 = r11.left
            float r5 = (float) r2
            float r5 = r5 * r0
            float r4 = r4 + r5
            r1.moveTo(r4, r3)
            float r5 = r9.scannerStart
            r1.lineTo(r4, r5)
            int r2 = r2 + 1
            goto L58
        L6c:
            float r2 = r9.scannerStart
            float r2 = r2 - r3
            float r2 = r2 / r0
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            float r3 = r9.frameLineStrokeWidth
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r4
            r4 = 0
        L7c:
            if (r4 > r2) goto L92
            float r5 = r9.scannerStart
            float r6 = (float) r4
            float r6 = r6 * r0
            float r5 = r5 - r6
            float r6 = r11.left
            float r6 = r6 + r3
            r1.moveTo(r6, r5)
            float r6 = r11.right
            float r6 = r6 - r3
            r1.lineTo(r6, r5)
            int r4 = r4 + 1
            goto L7c
        L92:
            android.graphics.Paint r11 = r9.paint
            r10.drawPath(r1, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.king.view.viewfinderview.ViewfinderView.drawGridScanner(android.graphics.Canvas, android.graphics.RectF):void");
    }

    private void drawFrame(Canvas canvas, RectF rectF) {
        this.paint.setColor(this.frameColor);
        this.paint.setStyle(Paint.Style.STROKE);
        Bitmap bitmap = this.frameBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, (Rect) null, rectF, this.paint);
            return;
        }
        this.paint.setStrokeWidth(this.frameLineStrokeWidth);
        float f = this.frameCornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.paint);
        drawFrameCorner(canvas, rectF);
    }

    private void drawExterior(Canvas canvas, RectF rectF, int i, int i2) {
        int i3 = this.maskColor;
        if (i3 == 0) {
            return;
        }
        this.paint.setColor(i3);
        this.paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.addRect(0.0f, 0.0f, i, i2, Path.Direction.CW);
        Path path2 = new Path();
        float f = this.frameCornerRadius;
        path2.addRoundRect(rectF, f, f, Path.Direction.CW);
        path.op(path2, Path.Op.DIFFERENCE);
        canvas.drawPath(path, this.paint);
    }

    private void drawMask(Canvas canvas, int i, int i2) {
        if (this.maskColor != 0) {
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.maskColor);
            canvas.drawRect(0.0f, 0.0f, i, i2, this.paint);
        }
    }

    private void drawResultPoints(Canvas canvas, List<Point> list) {
        this.paint.setColor(-1);
        this.paint.setStyle(Paint.Style.FILL);
        if (list != null) {
            Iterator<Point> it = list.iterator();
            while (it.hasNext()) {
                drawResultPoint(canvas, it.next(), this.currentZoomRatio);
            }
        }
    }

    private void calcPointZoomAnimation() {
        float f = this.currentZoomRatio;
        if (f <= 1.0f) {
            this.lastZoomRatio = f;
            this.currentZoomRatio = f + this.zoomSpeed;
            int i = this.zoomCount;
            if (i < 2) {
                this.zoomCount = i + 1;
            } else {
                this.zoomCount = 0;
            }
        } else if (f >= 1.2f || this.lastZoomRatio > f) {
            this.lastZoomRatio = f;
            this.currentZoomRatio = f - this.zoomSpeed;
        } else {
            this.lastZoomRatio = f;
            this.currentZoomRatio = f + this.zoomSpeed;
        }
        postInvalidateDelayed((this.zoomCount == 0 && this.lastZoomRatio == 1.0f) ? this.pointAnimationInterval : ((long) this.laserAnimationInterval) * 2);
    }

    private void drawResultPoint(Canvas canvas, Point point, float f) {
        if (this.pointBitmap != null) {
            float width = point.x - (this.pointBitmap.getWidth() / 2.0f);
            float height = point.y - (this.pointBitmap.getHeight() / 2.0f);
            if (this.isPointAnimation) {
                int iRound = Math.round(this.pointBitmap.getWidth() * f);
                int iRound2 = Math.round(this.pointBitmap.getHeight() * f);
                int iRound3 = point.x - Math.round(iRound / 2.0f);
                int iRound4 = point.y - Math.round(iRound2 / 2.0f);
                canvas.drawBitmap(this.pointBitmap, (Rect) null, new Rect(iRound3, iRound4, iRound + iRound3, iRound2 + iRound4), this.paint);
                return;
            }
            canvas.drawBitmap(this.pointBitmap, width, height, this.paint);
            return;
        }
        this.paint.setColor(this.pointStrokeColor);
        canvas.drawCircle(point.x, point.y, this.pointStrokeRadius * f, this.paint);
        this.paint.setColor(this.pointColor);
        canvas.drawCircle(point.x, point.y, this.pointRadius * f, this.paint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.gestureDetector.onTouchEvent(motionEvent);
        return this.isShowPoints || super.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkSingleTap(float f, float f2) {
        if (this.pointList != null) {
            for (int i = 0; i < this.pointList.size(); i++) {
                Point point = this.pointList.get(i);
                if (getDistance(f, f2, point.x, point.y) <= this.pointRangeRadius) {
                    OnItemClickListener onItemClickListener = this.onItemClickListener;
                    if (onItemClickListener == null) {
                        return true;
                    }
                    onItemClickListener.onItemClick(i);
                    return true;
                }
            }
        }
        return false;
    }

    private float getDistance(float f, float f2, float f3, float f4) {
        return (float) Math.sqrt(Math.pow(f - f3, 2.0d) + Math.pow(f2 - f4, 2.0d));
    }

    public boolean isShowPoints() {
        return this.isShowPoints;
    }

    public void showScanner() {
        this.isShowPoints = false;
        invalidate();
    }

    public void showResultPoints(List<Point> list) {
        this.pointList = list;
        this.isShowPoints = true;
        this.zoomCount = 0;
        this.lastZoomRatio = 0.0f;
        this.currentZoomRatio = 1.0f;
        invalidate();
    }

    public void setMaskColor(int i) {
        this.maskColor = i;
    }

    public void setFrameColor(int i) {
        this.frameColor = i;
    }

    public void setLaserColor(int i) {
        this.laserColor = i;
    }

    public void setFrameCornerColor(int i) {
        this.frameCornerColor = i;
    }

    public void setLabelTextPadding(float f) {
        this.labelTextPadding = f;
    }

    public void setLabelTextPadding(float f, int i) {
        this.labelTextPadding = TypedValue.applyDimension(i, f, getResources().getDisplayMetrics());
    }

    public void setLabelTextWidth(int i) {
        this.labelTextWidth = i;
    }

    public void setLabelTextLocation(TextLocation textLocation) {
        this.labelTextLocation = textLocation;
    }

    public void setLabelText(String str) {
        this.labelText = str;
    }

    public void setLabelTextColor(int i) {
        this.labelTextColor = i;
    }

    public void setLabelTextColorResource(int i) {
        this.labelTextColor = getColor(getContext(), i);
    }

    public void setLabelTextSize(float f) {
        this.labelTextSize = f;
    }

    public void setLabelTextSize(float f, int i) {
        this.labelTextSize = TypedValue.applyDimension(i, f, getResources().getDisplayMetrics());
    }

    public void setLaserStyle(LaserStyle laserStyle) {
        this.laserStyle = laserStyle;
    }

    public void setLaserGridColumn(int i) {
        this.laserGridColumn = i;
    }

    public void setLaserGridHeight(int i) {
        this.laserGridHeight = i;
    }

    public void setFrameCornerStrokeWidth(int i) {
        this.frameCornerStrokeWidth = i;
    }

    public void setFrameCornerSize(int i) {
        this.frameCornerSize = i;
    }

    public void setFrameCornerSize(int i, int i2) {
        this.frameCornerSize = TypedValue.applyDimension(i2, i, getResources().getDisplayMetrics());
    }

    public void setFrameCornerRadius(int i) {
        this.frameCornerRadius = i;
    }

    public void setFrameCornerRadius(int i, int i2) {
        this.frameCornerRadius = TypedValue.applyDimension(i2, i, getResources().getDisplayMetrics());
    }

    public void setLaserMovementSpeed(int i) {
        this.laserMovementSpeed = i;
    }

    public void setLaserLineHeight(int i) {
        this.laserLineHeight = i;
    }

    public void setFrameLineStrokeWidth(int i) {
        this.frameLineStrokeWidth = i;
    }

    public void setFrameDrawable(int i) {
        setFrameBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setFrameBitmap(Bitmap bitmap) {
        this.frameBitmap = bitmap;
    }

    public void setLaserAnimationInterval(int i) {
        this.laserAnimationInterval = i;
    }

    public void setPointColor(int i) {
        this.pointColor = i;
    }

    public void setPointStrokeColor(int i) {
        this.pointStrokeColor = i;
    }

    public void setPointRadius(float f) {
        this.pointRadius = f;
    }

    public void setPointRadius(float f, int i) {
        this.pointRadius = TypedValue.applyDimension(i, f, getResources().getDisplayMetrics());
    }

    public void setLaserDrawable(int i) {
        setLaserBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setLaserBitmap(Bitmap bitmap) {
        this.laserBitmap = bitmap;
        scaleLaserBitmap();
    }

    public void setPointDrawable(int i) {
        setPointBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setPointBitmap(Bitmap bitmap) {
        this.pointBitmap = bitmap;
        this.pointRangeRadius = ((bitmap.getWidth() + this.pointBitmap.getHeight()) / 4.0f) * 1.2f;
    }

    public void setPointAnimationInterval(int i) {
        this.pointAnimationInterval = i;
    }

    public void setViewfinderStyle(int i) {
        this.viewfinderStyle = i;
    }

    public void setFrameWidth(int i) {
        this.frameWidth = i;
    }

    public void setFrameHeight(int i) {
        this.frameHeight = i;
    }

    public void setFrameRatio(float f) {
        this.frameRatio = f;
    }

    public void setFramePaddingLeft(float f) {
        this.framePaddingLeft = f;
    }

    public void setFramePaddingTop(float f) {
        this.framePaddingTop = f;
    }

    public void setFramePaddingRight(float f) {
        this.framePaddingRight = f;
    }

    public void setFramePadding(float f, float f2, float f3, float f4) {
        this.framePaddingLeft = f;
        this.framePaddingTop = f2;
        this.framePaddingRight = f3;
        this.framePaddingBottom = f4;
    }

    public void setFramePaddingBottom(float f) {
        this.framePaddingBottom = f;
    }

    public void setFrameGravity(FrameGravity frameGravity) {
        this.frameGravity = frameGravity;
    }

    public void setPointAnimation(boolean z) {
        this.isPointAnimation = z;
    }

    public void setPointStrokeRadius(float f) {
        this.pointStrokeRadius = f;
    }

    public void setZoomSpeed(float f) {
        this.zoomSpeed = f;
    }

    public void setPointRangeRadius(float f) {
        this.pointRangeRadius = f;
    }

    public void setLaserBitmapRatio(float f) {
        this.laserBitmapRatio = f;
        int i = this.minDimension;
        if (i > 0) {
            this.laserBitmapWidth = i * f;
            scaleLaserBitmap();
        }
    }

    public void setLaserBitmapWidth(float f) {
        this.laserBitmapWidth = f;
        scaleLaserBitmap();
    }

    public void setFullRefresh(boolean z) {
        this.fullRefresh = z;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

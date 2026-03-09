package com.cunoraz.gifview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GifView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u0000 ;2\u00020\u0001:\u0001;B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020#H\u0003J\u0010\u0010'\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0014J0\u0010(\u001a\u00020#2\u0006\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0014J\u0018\u0010.\u001a\u00020#2\u0006\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u0007H\u0014J\u0010\u00101\u001a\u00020#2\u0006\u00102\u001a\u00020\u0007H\u0017J\u0018\u00103\u001a\u00020#2\u0006\u00104\u001a\u00020\u00012\u0006\u00105\u001a\u00020\u0007H\u0015J\u0010\u00106\u001a\u00020#2\u0006\u00105\u001a\u00020\u0007H\u0014J\u0006\u00107\u001a\u00020#J\u0006\u00108\u001a\u00020#J\"\u00109\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003J\b\u0010:\u001a\u00020#H\u0002R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lcom/cunoraz/gifview/library/GifView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "currentAnimationTime", "movieResourceId", "gifResource", "getGifResource", "()I", "setGifResource", "(I)V", "isPaused", "", "()Z", "setPaused", "(Z)V", "isPlaying", "isVisible", "movie", "Landroid/graphics/Movie;", "movieLeft", "", "movieMeasuredMovieHeight", "movieMeasuredMovieWidth", "movieMovieResourceId", "movieScale", "movieStart", "", "movieTop", "drawMovieFrame", "", "canvas", "Landroid/graphics/Canvas;", "invalidateView", "onDraw", "onLayout", "changed", "left", "top", "right", "bottom", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onScreenStateChanged", "screenState", "onVisibilityChanged", "changedView", "visibility", "onWindowVisibilityChanged", "pause", "play", "setViewAttributes", "updateAnimationTime", "Companion", "library_release"}, k = 1, mv = {1, 1, 13})
public final class GifView extends View {
    private static final int DEFAULT_MOVIE_VIEW_DURATION = 1000;
    private HashMap _$_findViewCache;
    private int currentAnimationTime;
    private volatile boolean isPaused;
    private boolean isVisible;
    private Movie movie;
    private float movieLeft;
    private int movieMeasuredMovieHeight;
    private int movieMeasuredMovieWidth;
    private int movieMovieResourceId;
    private float movieScale;
    private long movieStart;
    private float movieTop;

    public GifView(Context context) {
        this(context, null, 0, 6, null);
    }

    public GifView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public void _$_clearFindViewByIdCache() {
        HashMap map = this._$_findViewCache;
        if (map != null) {
            map.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View viewFindViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), viewFindViewById);
        return viewFindViewById;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ GifView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 2) != 0) {
            attributeSet = null;
        }
        this(context, attributeSet, (i2 & 4) != 0 ? R.styleable.CustomTheme_gifViewStyle : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GifView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.isVisible = true;
        setViewAttributes(context, attributeSet, i);
    }

    /* JADX INFO: renamed from: isPaused, reason: from getter */
    public final boolean getIsPaused() {
        return this.isPaused;
    }

    public final void setPaused(boolean z) {
        this.isPaused = z;
    }

    /* JADX INFO: renamed from: getGifResource, reason: from getter */
    public final int getMovieMovieResourceId() {
        return this.movieMovieResourceId;
    }

    public final void setGifResource(int i) {
        this.movieMovieResourceId = i;
        this.movie = Movie.decodeStream(getResources().openRawResource(this.movieMovieResourceId));
        requestLayout();
    }

    public final boolean isPlaying() {
        return !this.isPaused;
    }

    private final void setViewAttributes(Context context, AttributeSet attrs, int defStyle) {
        setLayerType(1, null);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.GifView, defStyle, R.style.Widget_GifView);
        this.movieMovieResourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.GifView_gif, -1);
        this.isPaused = typedArrayObtainStyledAttributes.getBoolean(R.styleable.GifView_paused, false);
        typedArrayObtainStyledAttributes.recycle();
        if (this.movieMovieResourceId != -1) {
            this.movie = Movie.decodeStream(getResources().openRawResource(this.movieMovieResourceId));
        }
    }

    public final void play() {
        if (this.isPaused) {
            this.isPaused = false;
            this.movieStart = SystemClock.uptimeMillis() - ((long) this.currentAnimationTime);
            invalidate();
        }
    }

    public final void pause() {
        if (this.isPaused) {
            return;
        }
        this.isPaused = true;
        invalidate();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size;
        int size2;
        Movie movie = this.movie;
        if (movie != null) {
            if (movie == null) {
                Intrinsics.throwNpe();
            }
            int iWidth = movie.width();
            Movie movie2 = this.movie;
            if (movie2 == null) {
                Intrinsics.throwNpe();
            }
            int iHeight = movie2.height();
            float fMax = 1.0f / Math.max((View.MeasureSpec.getMode(widthMeasureSpec) == 0 || iWidth <= (size2 = View.MeasureSpec.getSize(widthMeasureSpec))) ? 1.0f : iWidth / size2, (View.MeasureSpec.getMode(heightMeasureSpec) == 0 || iHeight <= (size = View.MeasureSpec.getSize(heightMeasureSpec))) ? 1.0f : iHeight / size);
            this.movieScale = fMax;
            int i = (int) (iWidth * fMax);
            this.movieMeasuredMovieWidth = i;
            int i2 = (int) (iHeight * fMax);
            this.movieMeasuredMovieHeight = i2;
            setMeasuredDimension(i, i2);
            return;
        }
        setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.movieLeft = (getWidth() - this.movieMeasuredMovieWidth) / 2.0f;
        this.movieTop = (getHeight() - this.movieMeasuredMovieHeight) / 2.0f;
        this.isVisible = getVisibility() == 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        if (this.movie != null) {
            if (!this.isPaused) {
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateView();
                return;
            }
            drawMovieFrame(canvas);
        }
    }

    private final void invalidateView() {
        if (this.isVisible) {
            postInvalidateOnAnimation();
        }
    }

    private final void updateAnimationTime() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.movieStart == 0) {
            this.movieStart = jUptimeMillis;
        }
        Movie movie = this.movie;
        if (movie == null) {
            Intrinsics.throwNpe();
        }
        int iDuration = movie.duration();
        if (iDuration == 0) {
            iDuration = 1000;
        }
        this.currentAnimationTime = (int) ((jUptimeMillis - this.movieStart) % ((long) iDuration));
    }

    private final void drawMovieFrame(Canvas canvas) {
        Movie movie = this.movie;
        if (movie == null) {
            Intrinsics.throwNpe();
        }
        movie.setTime(this.currentAnimationTime);
        canvas.save();
        float f = this.movieScale;
        canvas.scale(f, f);
        Movie movie2 = this.movie;
        if (movie2 == null) {
            Intrinsics.throwNpe();
        }
        float f2 = this.movieLeft;
        float f3 = this.movieScale;
        movie2.draw(canvas, f2 / f3, this.movieTop / f3);
        canvas.restore();
    }

    @Override // android.view.View
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        this.isVisible = screenState == 1;
        invalidateView();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        Intrinsics.checkParameterIsNotNull(changedView, "changedView");
        super.onVisibilityChanged(changedView, visibility);
        this.isVisible = visibility == 0;
        invalidateView();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.isVisible = visibility == 0;
        invalidateView();
    }
}

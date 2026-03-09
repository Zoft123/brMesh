package com.king.camera.scan.internal;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: ZoomGestureDetector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 -2\u00020\u0001:\u0003-./B+\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0003\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010(\u001a\u00020\fH\u0002J\b\u0010)\u001a\u00020\u0012H\u0002J\u0010\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020,H\u0007R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u00058\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u00020\u00058\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u001c\"\u0004\b \u0010\u001eR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b&\u0010'¨\u00060"}, d2 = {"Lcom/king/camera/scan/internal/ZoomGestureDetector;", "", "context", "Landroid/content/Context;", "spanSlop", "", "minSpan", "listener", "Lcom/king/camera/scan/internal/ZoomGestureDetector$OnZoomGestureListener;", "(Landroid/content/Context;IILcom/king/camera/scan/internal/ZoomGestureDetector$OnZoomGestureListener;)V", "anchoredZoomMode", "anchoredZoomStartX", "", "anchoredZoomStartY", "currentSpan", "currentSpanX", "currentSpanY", "eventBeforeOrAboveStartingGestureEvent", "", "eventTime", "", "focusX", "focusY", "gestureDetector", "Landroid/view/GestureDetector;", "initialSpan", "isInProgress", "isQuickZoomEnabled", "()Z", "setQuickZoomEnabled", "(Z)V", "isStylusZoomEnabled", "setStylusZoomEnabled", "prevTime", "previousSpan", "previousSpanX", "previousSpanY", "timeDelta", "getTimeDelta", "()J", "getIncrementalScaleFactor", "inAnchoredZoomMode", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "Companion", "OnZoomGestureListener", "ZoomEvent", "camera-scan_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ZoomGestureDetector {
    private static final int ANCHORED_ZOOM_MODE_DOUBLE_TAP = 1;
    private static final int ANCHORED_ZOOM_MODE_NONE = 0;
    private static final int ANCHORED_ZOOM_MODE_STYLUS = 2;
    private static final int DEFAULT_MIN_SPAN = 0;
    private static final float SCALE_FACTOR = 0.5f;
    private int anchoredZoomMode;
    private float anchoredZoomStartX;
    private float anchoredZoomStartY;
    private final Context context;
    private float currentSpan;
    private float currentSpanX;
    private float currentSpanY;
    private boolean eventBeforeOrAboveStartingGestureEvent;
    private long eventTime;
    private int focusX;
    private int focusY;
    private GestureDetector gestureDetector;
    private float initialSpan;
    private boolean isInProgress;
    private boolean isQuickZoomEnabled;
    private boolean isStylusZoomEnabled;
    private final OnZoomGestureListener listener;
    private final int minSpan;
    private long prevTime;
    private float previousSpan;
    private float previousSpanX;
    private float previousSpanY;
    private final int spanSlop;

    /* JADX INFO: compiled from: ZoomGestureDetector.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'¨\u0006\u0006"}, d2 = {"Lcom/king/camera/scan/internal/ZoomGestureDetector$OnZoomGestureListener;", "", "onZoomEvent", "", "zoomEvent", "Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent;", "camera-scan_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public interface OnZoomGestureListener {
        boolean onZoomEvent(ZoomEvent zoomEvent);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ZoomGestureDetector(Context context, int i, OnZoomGestureListener listener) {
        this(context, i, 0, listener, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ZoomGestureDetector(Context context, OnZoomGestureListener listener) {
        this(context, 0, 0, listener, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
    }

    public ZoomGestureDetector(Context context, int i, int i2, OnZoomGestureListener listener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.context = context;
        this.spanSlop = i;
        this.minSpan = i2;
        this.listener = listener;
        this.isQuickZoomEnabled = true;
        this.isStylusZoomEnabled = true;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.king.camera.scan.internal.ZoomGestureDetector$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                this.this$0.anchoredZoomStartX = e.getX();
                this.this$0.anchoredZoomStartY = e.getY();
                this.this$0.anchoredZoomMode = 1;
                return true;
            }
        });
    }

    public /* synthetic */ ZoomGestureDetector(Context context, int i, int i2, OnZoomGestureListener onZoomGestureListener, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? ViewConfiguration.get(context).getScaledTouchSlop() * 2 : i, (i3 & 4) != 0 ? 0 : i2, onZoomGestureListener);
    }

    /* JADX INFO: compiled from: ZoomGestureDetector.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b&\u0018\u00002\u00020\u0001:\u0003\r\u000e\u000fB%\b\u0002\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0010"}, d2 = {"Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent;", "", "eventTime", "", "focusX", "", "focusY", "(JII)V", "getEventTime", "()J", "getFocusX", "()I", "getFocusY", "Begin", "End", "Move", "camera-scan_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static abstract class ZoomEvent {
        private final long eventTime;
        private final int focusX;
        private final int focusY;

        public /* synthetic */ ZoomEvent(long j, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, i, i2);
        }

        private ZoomEvent(long j, int i, int i2) {
            this.eventTime = j;
            this.focusX = i;
            this.focusY = i2;
        }

        public final long getEventTime() {
            return this.eventTime;
        }

        public final int getFocusX() {
            return this.focusX;
        }

        public final int getFocusY() {
            return this.focusY;
        }

        /* JADX INFO: compiled from: ZoomGestureDetector.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent$Begin;", "Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent;", "eventTime", "", "focusX", "", "focusY", "(JII)V", "camera-scan_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        public static final class Begin extends ZoomEvent {
            public Begin(long j, int i, int i2) {
                super(j, i, i2, null);
            }
        }

        /* JADX INFO: compiled from: ZoomGestureDetector.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B-\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent$Move;", "Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent;", "eventTime", "", "focusX", "", "focusY", "incrementalScaleFactor", "", "(JIIF)V", "getIncrementalScaleFactor", "()F", "camera-scan_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        public static final class Move extends ZoomEvent {
            private final float incrementalScaleFactor;

            public final float getIncrementalScaleFactor() {
                return this.incrementalScaleFactor;
            }

            public Move(long j, int i, int i2, float f) {
                super(j, i, i2, null);
                this.incrementalScaleFactor = f;
            }
        }

        /* JADX INFO: compiled from: ZoomGestureDetector.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B-\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent$End;", "Lcom/king/camera/scan/internal/ZoomGestureDetector$ZoomEvent;", "eventTime", "", "focusX", "", "focusY", "incrementalScaleFactor", "", "(JIIF)V", "getIncrementalScaleFactor", "()F", "camera-scan_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        public static final class End extends ZoomEvent {
            private final float incrementalScaleFactor;

            public final float getIncrementalScaleFactor() {
                return this.incrementalScaleFactor;
            }

            public End(long j, int i, int i2, float f) {
                super(j, i, i2, null);
                this.incrementalScaleFactor = f;
            }
        }
    }

    /* JADX INFO: renamed from: isQuickZoomEnabled, reason: from getter */
    public final boolean getIsQuickZoomEnabled() {
        return this.isQuickZoomEnabled;
    }

    public final void setQuickZoomEnabled(boolean z) {
        this.isQuickZoomEnabled = z;
    }

    /* JADX INFO: renamed from: isStylusZoomEnabled, reason: from getter */
    public final boolean getIsStylusZoomEnabled() {
        return this.isStylusZoomEnabled;
    }

    public final void setStylusZoomEnabled(boolean z) {
        this.isStylusZoomEnabled = z;
    }

    public final boolean onTouchEvent(MotionEvent event) {
        boolean z;
        float f;
        float f2;
        Intrinsics.checkNotNullParameter(event, "event");
        this.eventTime = event.getEventTime();
        int actionMasked = event.getActionMasked();
        if (this.isQuickZoomEnabled) {
            this.gestureDetector.onTouchEvent(event);
        }
        int pointerCount = event.getPointerCount();
        boolean z2 = (event.getButtonState() & 32) != 0;
        boolean z3 = this.anchoredZoomMode == 2 && !z2;
        boolean z4 = actionMasked == 1 || actionMasked == 3 || z3;
        float fAbs = 0.0f;
        if (actionMasked == 0 || z4) {
            if (this.isInProgress) {
                z = true;
                this.listener.onZoomEvent(new ZoomEvent.End(this.eventTime, this.focusX, this.focusY, getIncrementalScaleFactor()));
                this.isInProgress = false;
                this.initialSpan = 0.0f;
                this.anchoredZoomMode = 0;
            } else {
                z = true;
                if (inAnchoredZoomMode() && z4) {
                    this.isInProgress = false;
                    this.initialSpan = 0.0f;
                    this.anchoredZoomMode = 0;
                }
            }
            if (z4) {
                return z;
            }
        } else {
            z = true;
        }
        if (!this.isInProgress && this.isStylusZoomEnabled && !inAnchoredZoomMode() && !z4 && z2) {
            this.anchoredZoomStartX = event.getX();
            this.anchoredZoomStartY = event.getY();
            this.anchoredZoomMode = 2;
            this.initialSpan = 0.0f;
        }
        boolean z5 = (actionMasked == 0 || actionMasked == 6 || actionMasked == 5 || z3) ? z : false;
        boolean z6 = actionMasked == 6 ? z : false;
        int actionIndex = z6 ? event.getActionIndex() : -1;
        int i = z6 ? pointerCount - 1 : pointerCount;
        if (inAnchoredZoomMode()) {
            f2 = this.anchoredZoomStartX;
            f = this.anchoredZoomStartY;
            this.eventBeforeOrAboveStartingGestureEvent = event.getY() < f ? z : false;
        } else {
            float x = 0.0f;
            float y = 0.0f;
            for (int i2 = 0; i2 < pointerCount; i2++) {
                if (actionIndex != i2) {
                    x += event.getX(i2);
                    y += event.getY(i2);
                }
            }
            float f3 = i;
            float f4 = x / f3;
            f = y / f3;
            f2 = f4;
        }
        float fAbs2 = 0.0f;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            if (actionIndex != i3) {
                fAbs += Math.abs(event.getX(i3) - f2);
                fAbs2 += Math.abs(event.getY(i3) - f);
            }
        }
        float f5 = i;
        float f6 = fAbs / f5;
        float f7 = fAbs2 / f5;
        float f8 = 2;
        float f9 = f6 * f8;
        float f10 = f7 * f8;
        float fHypot = inAnchoredZoomMode() ? f10 : (float) Math.hypot(f9, f10);
        boolean z7 = this.isInProgress;
        this.focusX = MathKt.roundToInt(f2);
        this.focusY = MathKt.roundToInt(f);
        if (!inAnchoredZoomMode() && this.isInProgress && (fHypot < this.minSpan || z5)) {
            this.listener.onZoomEvent(new ZoomEvent.End(this.eventTime, this.focusX, this.focusY, getIncrementalScaleFactor()));
            this.isInProgress = false;
            this.initialSpan = fHypot;
        }
        if (z5) {
            this.currentSpanX = f9;
            this.previousSpanX = f9;
            this.currentSpanY = f10;
            this.previousSpanY = f10;
            this.currentSpan = fHypot;
            this.previousSpan = fHypot;
            this.initialSpan = fHypot;
        }
        int i4 = inAnchoredZoomMode() ? this.spanSlop : this.minSpan;
        if (!this.isInProgress && fHypot >= i4 && (z7 || Math.abs(fHypot - this.initialSpan) > this.spanSlop)) {
            this.currentSpanX = f9;
            this.previousSpanX = f9;
            this.currentSpanY = f10;
            this.previousSpanY = f10;
            this.currentSpan = fHypot;
            this.previousSpan = fHypot;
            this.prevTime = this.eventTime;
            this.isInProgress = this.listener.onZoomEvent(new ZoomEvent.Begin(this.eventTime, this.focusX, this.focusY));
        }
        if (actionMasked == 2) {
            this.currentSpanX = f9;
            this.currentSpanY = f10;
            this.currentSpan = fHypot;
            if (this.isInProgress ? this.listener.onZoomEvent(new ZoomEvent.Move(this.eventTime, this.focusX, this.focusY, getIncrementalScaleFactor())) : z) {
                this.previousSpanX = this.currentSpanX;
                this.previousSpanY = this.currentSpanY;
                this.previousSpan = this.currentSpan;
                this.prevTime = this.eventTime;
            }
        }
        return z;
    }

    private final boolean inAnchoredZoomMode() {
        return this.anchoredZoomMode != 0;
    }

    private final float getIncrementalScaleFactor() {
        if (inAnchoredZoomMode()) {
            boolean z = this.eventBeforeOrAboveStartingGestureEvent;
            boolean z2 = (z && this.currentSpan < this.previousSpan) || (!z && this.currentSpan > this.previousSpan);
            float fAbs = Math.abs(1 - (this.currentSpan / this.previousSpan)) * SCALE_FACTOR;
            if (this.previousSpan <= this.spanSlop) {
                return 1.0f;
            }
            return z2 ? fAbs + 1.0f : 1.0f - fAbs;
        }
        float f = this.previousSpan;
        if (f > 0.0f) {
            return this.currentSpan / f;
        }
        return 1.0f;
    }

    public final long getTimeDelta() {
        return this.eventTime - this.prevTime;
    }
}

package androidx.camera.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.view.PreviewViewImplementation;
import androidx.camera.view.impl.ZoomGestureDetector;
import androidx.camera.view.internal.ScreenFlashUiInfo;
import androidx.camera.view.internal.compat.quirk.DeviceQuirks;
import androidx.camera.view.internal.compat.quirk.SurfaceViewNotCroppedByParentQuirk;
import androidx.camera.view.internal.compat.quirk.SurfaceViewStretchedQuirk;
import androidx.camera.view.transform.OutputTransform;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class PreviewView extends FrameLayout {
    static final int DEFAULT_BACKGROUND_COLOR = 17170444;
    private static final ImplementationMode DEFAULT_IMPL_MODE = ImplementationMode.PERFORMANCE;
    private static final String TAG = "PreviewView";
    final AtomicReference<PreviewStreamStateObserver> mActiveStreamStateObserver;
    CameraController mCameraController;
    CameraInfoInternal mCameraInfoInternal;
    private final DisplayRotationListener mDisplayRotationListener;
    PreviewViewImplementation mImplementation;
    ImplementationMode mImplementationMode;
    OnFrameUpdateListener mOnFrameUpdateListener;
    Executor mOnFrameUpdateListenerExecutor;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    final MutableLiveData<StreamState> mPreviewStreamStateLiveData;
    final PreviewTransformation mPreviewTransform;
    PreviewViewMeteringPointFactory mPreviewViewMeteringPointFactory;
    final ScreenFlashView mScreenFlashView;
    final Preview.SurfaceProvider mSurfaceProvider;
    private MotionEvent mTouchUpEvent;
    boolean mUseDisplayRotation;
    private final ZoomGestureDetector mZoomGestureDetector;

    public interface OnFrameUpdateListener {
        void onFrameUpdate(long j);
    }

    public enum StreamState {
        IDLE,
        STREAMING
    }

    /* JADX INFO: renamed from: lambda$new$0$androidx-camera-view-PreviewView, reason: not valid java name */
    /* synthetic */ void m328lambda$new$0$androidxcameraviewPreviewView(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i3 - i == i7 - i5 && i4 - i2 == i8 - i6) {
            return;
        }
        redrawPreview();
        attachToControllerIfReady(true);
    }

    /* JADX INFO: renamed from: androidx.camera.view.PreviewView$1, reason: invalid class name */
    class AnonymousClass1 implements Preview.SurfaceProvider {
        AnonymousClass1() {
        }

        @Override // androidx.camera.core.Preview.SurfaceProvider
        public void onSurfaceRequested(final SurfaceRequest surfaceRequest) {
            PreviewViewImplementation surfaceViewImplementation;
            if (!Threads.isMainThread()) {
                ContextCompat.getMainExecutor(PreviewView.this.getContext()).execute(new Runnable() { // from class: androidx.camera.view.PreviewView$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m330lambda$onSurfaceRequested$0$androidxcameraviewPreviewView$1(surfaceRequest);
                    }
                });
                return;
            }
            Logger.d(PreviewView.TAG, "Surface requested by Preview.");
            final CameraInternal camera = surfaceRequest.getCamera();
            PreviewView.this.mCameraInfoInternal = camera.getCameraInfoInternal();
            PreviewView.this.mPreviewViewMeteringPointFactory.setSensorRect(camera.getCameraControlInternal().getSensorRect());
            surfaceRequest.setTransformationInfoListener(ContextCompat.getMainExecutor(PreviewView.this.getContext()), new SurfaceRequest.TransformationInfoListener() { // from class: androidx.camera.view.PreviewView$1$$ExternalSyntheticLambda2
                @Override // androidx.camera.core.SurfaceRequest.TransformationInfoListener
                public final void onTransformationInfoUpdate(SurfaceRequest.TransformationInfo transformationInfo) {
                    this.f$0.m331lambda$onSurfaceRequested$1$androidxcameraviewPreviewView$1(camera, surfaceRequest, transformationInfo);
                }
            });
            if (!PreviewView.shouldReuseImplementation(PreviewView.this.mImplementation, surfaceRequest, PreviewView.this.mImplementationMode)) {
                PreviewView previewView = PreviewView.this;
                if (PreviewView.shouldUseTextureView(surfaceRequest, previewView.mImplementationMode)) {
                    PreviewView previewView2 = PreviewView.this;
                    surfaceViewImplementation = new TextureViewImplementation(previewView2, previewView2.mPreviewTransform);
                } else {
                    PreviewView previewView3 = PreviewView.this;
                    surfaceViewImplementation = new SurfaceViewImplementation(previewView3, previewView3.mPreviewTransform);
                }
                previewView.mImplementation = surfaceViewImplementation;
            }
            final PreviewStreamStateObserver previewStreamStateObserver = new PreviewStreamStateObserver(camera.getCameraInfoInternal(), PreviewView.this.mPreviewStreamStateLiveData, PreviewView.this.mImplementation);
            PreviewView.this.mActiveStreamStateObserver.set(previewStreamStateObserver);
            camera.getCameraState().addObserver(ContextCompat.getMainExecutor(PreviewView.this.getContext()), previewStreamStateObserver);
            PreviewView.this.mImplementation.onSurfaceRequested(surfaceRequest, new PreviewViewImplementation.OnSurfaceNotInUseListener() { // from class: androidx.camera.view.PreviewView$1$$ExternalSyntheticLambda3
                @Override // androidx.camera.view.PreviewViewImplementation.OnSurfaceNotInUseListener
                public final void onSurfaceNotInUse() {
                    this.f$0.m332lambda$onSurfaceRequested$2$androidxcameraviewPreviewView$1(previewStreamStateObserver, camera);
                }
            });
            PreviewView previewView4 = PreviewView.this;
            if (previewView4.indexOfChild(previewView4.mScreenFlashView) == -1) {
                PreviewView previewView5 = PreviewView.this;
                previewView5.addView(previewView5.mScreenFlashView);
            }
            if (PreviewView.this.mOnFrameUpdateListener == null || PreviewView.this.mOnFrameUpdateListenerExecutor == null) {
                return;
            }
            PreviewView.this.mImplementation.setFrameUpdateListener(PreviewView.this.mOnFrameUpdateListenerExecutor, PreviewView.this.mOnFrameUpdateListener);
        }

        /* JADX INFO: renamed from: lambda$onSurfaceRequested$0$androidx-camera-view-PreviewView$1, reason: not valid java name */
        /* synthetic */ void m330lambda$onSurfaceRequested$0$androidxcameraviewPreviewView$1(SurfaceRequest surfaceRequest) {
            PreviewView.this.mSurfaceProvider.onSurfaceRequested(surfaceRequest);
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0057  */
        /* JADX INFO: renamed from: lambda$onSurfaceRequested$1$androidx-camera-view-PreviewView$1, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        /* synthetic */ void m331lambda$onSurfaceRequested$1$androidxcameraviewPreviewView$1(androidx.camera.core.impl.CameraInternal r5, androidx.camera.core.SurfaceRequest r6, androidx.camera.core.SurfaceRequest.TransformationInfo r7) {
            /*
                r4 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Preview transformation info updated. "
                r0.<init>(r1)
                r0.append(r7)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "PreviewView"
                androidx.camera.core.Logger.d(r1, r0)
                androidx.camera.core.impl.CameraInfoInternal r5 = r5.getCameraInfoInternal()
                int r5 = r5.getLensFacing()
                java.lang.Integer r0 = java.lang.Integer.valueOf(r5)
                r2 = 0
                r3 = 1
                if (r0 != 0) goto L2a
                java.lang.String r5 = "The lens facing is null, probably an external."
                androidx.camera.core.Logger.w(r1, r5)
            L28:
                r5 = r3
                goto L31
            L2a:
                r0.getClass()
                if (r5 != 0) goto L30
                goto L28
            L30:
                r5 = r2
            L31:
                androidx.camera.view.PreviewView r0 = androidx.camera.view.PreviewView.this
                androidx.camera.view.PreviewTransformation r0 = r0.mPreviewTransform
                android.util.Size r6 = r6.getResolution()
                r0.setTransformationInfo(r7, r6, r5)
                int r5 = r7.getTargetRotation()
                r6 = -1
                if (r5 == r6) goto L57
                androidx.camera.view.PreviewView r5 = androidx.camera.view.PreviewView.this
                androidx.camera.view.PreviewViewImplementation r5 = r5.mImplementation
                if (r5 == 0) goto L52
                androidx.camera.view.PreviewView r5 = androidx.camera.view.PreviewView.this
                androidx.camera.view.PreviewViewImplementation r5 = r5.mImplementation
                boolean r5 = r5 instanceof androidx.camera.view.SurfaceViewImplementation
                if (r5 == 0) goto L52
                goto L57
            L52:
                androidx.camera.view.PreviewView r5 = androidx.camera.view.PreviewView.this
                r5.mUseDisplayRotation = r2
                goto L5b
            L57:
                androidx.camera.view.PreviewView r5 = androidx.camera.view.PreviewView.this
                r5.mUseDisplayRotation = r3
            L5b:
                androidx.camera.view.PreviewView r5 = androidx.camera.view.PreviewView.this
                r5.redrawPreview()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.view.PreviewView.AnonymousClass1.m331lambda$onSurfaceRequested$1$androidxcameraviewPreviewView$1(androidx.camera.core.impl.CameraInternal, androidx.camera.core.SurfaceRequest, androidx.camera.core.SurfaceRequest$TransformationInfo):void");
        }

        /* JADX INFO: renamed from: lambda$onSurfaceRequested$2$androidx-camera-view-PreviewView$1, reason: not valid java name */
        /* synthetic */ void m332lambda$onSurfaceRequested$2$androidxcameraviewPreviewView$1(PreviewStreamStateObserver previewStreamStateObserver, CameraInternal cameraInternal) {
            if (PreviewView$1$$ExternalSyntheticBackportWithForwarding0.m(PreviewView.this.mActiveStreamStateObserver, previewStreamStateObserver, null)) {
                previewStreamStateObserver.updatePreviewStreamState(StreamState.IDLE);
            }
            previewStreamStateObserver.clear();
            cameraInternal.getCameraState().removeObserver(previewStreamStateObserver);
        }
    }

    public PreviewView(Context context) {
        this(context, null);
    }

    public PreviewView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreviewView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreviewView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ImplementationMode implementationMode = DEFAULT_IMPL_MODE;
        this.mImplementationMode = implementationMode;
        PreviewTransformation previewTransformation = new PreviewTransformation();
        this.mPreviewTransform = previewTransformation;
        this.mUseDisplayRotation = true;
        this.mPreviewStreamStateLiveData = new MutableLiveData<>(StreamState.IDLE);
        this.mActiveStreamStateObserver = new AtomicReference<>();
        this.mPreviewViewMeteringPointFactory = new PreviewViewMeteringPointFactory(previewTransformation);
        this.mDisplayRotationListener = new DisplayRotationListener();
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.camera.view.PreviewView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                this.f$0.m328lambda$new$0$androidxcameraviewPreviewView(view, i3, i4, i5, i6, i7, i8, i9, i10);
            }
        };
        this.mSurfaceProvider = new AnonymousClass1();
        Threads.checkMainThread();
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PreviewView, i, i2);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.PreviewView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        try {
            setScaleType(ScaleType.fromId(typedArrayObtainStyledAttributes.getInteger(R.styleable.PreviewView_scaleType, previewTransformation.getScaleType().getId())));
            setImplementationMode(ImplementationMode.fromId(typedArrayObtainStyledAttributes.getInteger(R.styleable.PreviewView_implementationMode, implementationMode.getId())));
            typedArrayObtainStyledAttributes.recycle();
            this.mZoomGestureDetector = new ZoomGestureDetector(context, new ZoomGestureDetector.OnZoomGestureListener() { // from class: androidx.camera.view.PreviewView$$ExternalSyntheticLambda1
                @Override // androidx.camera.view.impl.ZoomGestureDetector.OnZoomGestureListener
                public final boolean onZoomEvent(ZoomGestureDetector.ZoomEvent zoomEvent) {
                    return this.f$0.m329lambda$new$1$androidxcameraviewPreviewView(zoomEvent);
                }
            });
            if (getBackground() == null) {
                setBackgroundColor(ContextCompat.getColor(getContext(), 17170444));
            }
            ScreenFlashView screenFlashView = new ScreenFlashView(context);
            this.mScreenFlashView = screenFlashView;
            screenFlashView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    /* JADX INFO: renamed from: lambda$new$1$androidx-camera-view-PreviewView, reason: not valid java name */
    /* synthetic */ boolean m329lambda$new$1$androidxcameraviewPreviewView(ZoomGestureDetector.ZoomEvent zoomEvent) {
        CameraController cameraController;
        if (!(zoomEvent instanceof ZoomGestureDetector.ZoomEvent.Move) || (cameraController = this.mCameraController) == null) {
            return true;
        }
        cameraController.onPinchToZoom(((ZoomGestureDetector.ZoomEvent.Move) zoomEvent).getIncrementalScaleFactor());
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startListeningToDisplayChange();
        addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.onAttachedToWindow();
        }
        attachToControllerIfReady(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.onDetachedFromWindow();
        }
        CameraController cameraController = this.mCameraController;
        if (cameraController != null) {
            cameraController.clearPreviewSurface();
        }
        stopListeningToDisplayChange();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mCameraController == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean z = motionEvent.getPointerCount() == 1;
        boolean z2 = motionEvent.getAction() == 1;
        boolean z3 = motionEvent.getEventTime() - motionEvent.getDownTime() < ((long) ViewConfiguration.getLongPressTimeout());
        if (!z || !z2 || !z3) {
            return this.mZoomGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
        }
        this.mTouchUpEvent = motionEvent;
        performClick();
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        if (this.mCameraController != null) {
            MotionEvent motionEvent = this.mTouchUpEvent;
            float x = motionEvent != null ? motionEvent.getX() : getWidth() / 2.0f;
            MotionEvent motionEvent2 = this.mTouchUpEvent;
            this.mCameraController.onTapToFocus(this.mPreviewViewMeteringPointFactory, x, motionEvent2 != null ? motionEvent2.getY() : getHeight() / 2.0f);
        }
        this.mTouchUpEvent = null;
        return super.performClick();
    }

    public void setImplementationMode(ImplementationMode implementationMode) {
        Threads.checkMainThread();
        this.mImplementationMode = implementationMode;
        if (implementationMode == ImplementationMode.PERFORMANCE && this.mOnFrameUpdateListener != null) {
            throw new IllegalArgumentException("PERFORMANCE mode doesn't support frame update listener");
        }
    }

    public ImplementationMode getImplementationMode() {
        Threads.checkMainThread();
        return this.mImplementationMode;
    }

    public Preview.SurfaceProvider getSurfaceProvider() {
        Threads.checkMainThread();
        return this.mSurfaceProvider;
    }

    public void setScaleType(ScaleType scaleType) {
        Threads.checkMainThread();
        this.mPreviewTransform.setScaleType(scaleType);
        redrawPreview();
        attachToControllerIfReady(false);
    }

    public ScaleType getScaleType() {
        Threads.checkMainThread();
        return this.mPreviewTransform.getScaleType();
    }

    public MeteringPointFactory getMeteringPointFactory() {
        Threads.checkMainThread();
        return this.mPreviewViewMeteringPointFactory;
    }

    public LiveData<StreamState> getPreviewStreamState() {
        return this.mPreviewStreamStateLiveData;
    }

    public Bitmap getBitmap() {
        Threads.checkMainThread();
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation == null) {
            return null;
        }
        return previewViewImplementation.getBitmap();
    }

    public ViewPort getViewPort() {
        Threads.checkMainThread();
        if (getDisplay() == null) {
            return null;
        }
        return getViewPort(getDisplay().getRotation());
    }

    public ViewPort getViewPort(int i) {
        Threads.checkMainThread();
        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }
        return new ViewPort.Builder(new Rational(getWidth(), getHeight()), i).setScaleType(getViewPortScaleType()).setLayoutDirection(getLayoutDirection()).build();
    }

    private int getViewPortScaleType() {
        int iOrdinal = getScaleType().ordinal();
        if (iOrdinal == 0) {
            return 0;
        }
        int i = 1;
        if (iOrdinal != 1) {
            i = 2;
            if (iOrdinal != 2) {
                i = 3;
                if (iOrdinal != 3 && iOrdinal != 4 && iOrdinal != 5) {
                    throw new IllegalStateException("Unexpected scale type: " + getScaleType());
                }
            }
        }
        return i;
    }

    void redrawPreview() {
        Threads.checkMainThread();
        if (this.mImplementation != null) {
            updateDisplayRotationIfNeeded();
            this.mImplementation.redrawPreview();
        }
        this.mPreviewViewMeteringPointFactory.recalculate(new Size(getWidth(), getHeight()), getLayoutDirection());
        CameraController cameraController = this.mCameraController;
        if (cameraController != null) {
            cameraController.updatePreviewViewTransform(getSensorToViewTransform());
        }
    }

    static boolean shouldReuseImplementation(PreviewViewImplementation previewViewImplementation, SurfaceRequest surfaceRequest, ImplementationMode implementationMode) {
        return (previewViewImplementation instanceof SurfaceViewImplementation) && !shouldUseTextureView(surfaceRequest, implementationMode);
    }

    static boolean shouldUseTextureView(SurfaceRequest surfaceRequest, ImplementationMode implementationMode) {
        boolean zEquals = surfaceRequest.getCamera().getCameraInfoInternal().getImplementationType().equals(CameraInfo.IMPLEMENTATION_TYPE_CAMERA2_LEGACY);
        boolean z = (DeviceQuirks.get(SurfaceViewStretchedQuirk.class) == null && DeviceQuirks.get(SurfaceViewNotCroppedByParentQuirk.class) == null) ? false : true;
        if (Build.VERSION.SDK_INT <= 24 || zEquals || z) {
            return true;
        }
        int iOrdinal = implementationMode.ordinal();
        if (iOrdinal == 0) {
            return false;
        }
        if (iOrdinal == 1) {
            return true;
        }
        throw new IllegalArgumentException("Invalid implementation mode: " + implementationMode);
    }

    void updateDisplayRotationIfNeeded() {
        Display display;
        CameraInfoInternal cameraInfoInternal;
        if (!this.mUseDisplayRotation || (display = getDisplay()) == null || (cameraInfoInternal = this.mCameraInfoInternal) == null) {
            return;
        }
        this.mPreviewTransform.overrideWithDisplayRotation(cameraInfoInternal.getSensorRotationDegrees(display.getRotation()), display.getRotation());
    }

    public void setFrameUpdateListener(Executor executor, OnFrameUpdateListener onFrameUpdateListener) {
        if (this.mImplementationMode == ImplementationMode.PERFORMANCE) {
            throw new IllegalArgumentException("PERFORMANCE mode doesn't support frame update listener");
        }
        this.mOnFrameUpdateListener = onFrameUpdateListener;
        this.mOnFrameUpdateListenerExecutor = executor;
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.setFrameUpdateListener(executor, onFrameUpdateListener);
        }
    }

    public enum ImplementationMode {
        PERFORMANCE(0),
        COMPATIBLE(1);

        private final int mId;

        ImplementationMode(int i) {
            this.mId = i;
        }

        int getId() {
            return this.mId;
        }

        static ImplementationMode fromId(int i) {
            for (ImplementationMode implementationMode : values()) {
                if (implementationMode.mId == i) {
                    return implementationMode;
                }
            }
            throw new IllegalArgumentException("Unknown implementation mode id " + i);
        }
    }

    public enum ScaleType {
        FILL_START(0),
        FILL_CENTER(1),
        FILL_END(2),
        FIT_START(3),
        FIT_CENTER(4),
        FIT_END(5);

        private final int mId;

        ScaleType(int i) {
            this.mId = i;
        }

        int getId() {
            return this.mId;
        }

        static ScaleType fromId(int i) {
            for (ScaleType scaleType : values()) {
                if (scaleType.mId == i) {
                    return scaleType;
                }
            }
            throw new IllegalArgumentException("Unknown scale type id " + i);
        }
    }

    public void setController(CameraController cameraController) {
        Threads.checkMainThread();
        CameraController cameraController2 = this.mCameraController;
        if (cameraController2 != null && cameraController2 != cameraController) {
            cameraController2.clearPreviewSurface();
            setScreenFlashUiInfo(null);
        }
        this.mCameraController = cameraController;
        attachToControllerIfReady(false);
        setScreenFlashUiInfo(getScreenFlashInternal());
    }

    public CameraController getController() {
        Threads.checkMainThread();
        return this.mCameraController;
    }

    public OutputTransform getOutputTransform() {
        Matrix surfaceToPreviewViewMatrix;
        Threads.checkMainThread();
        try {
            surfaceToPreviewViewMatrix = this.mPreviewTransform.getSurfaceToPreviewViewMatrix(new Size(getWidth(), getHeight()), getLayoutDirection());
        } catch (IllegalStateException unused) {
            surfaceToPreviewViewMatrix = null;
        }
        Rect surfaceCropRect = this.mPreviewTransform.getSurfaceCropRect();
        if (surfaceToPreviewViewMatrix == null || surfaceCropRect == null) {
            Logger.d(TAG, "Transform info is not ready");
            return null;
        }
        surfaceToPreviewViewMatrix.preConcat(TransformUtils.getNormalizedToBuffer(surfaceCropRect));
        if (this.mImplementation instanceof TextureViewImplementation) {
            surfaceToPreviewViewMatrix.postConcat(getMatrix());
        } else if (!getMatrix().isIdentity()) {
            Logger.w(TAG, "PreviewView needs to be in COMPATIBLE mode for the transform to work correctly.");
        }
        return new OutputTransform(surfaceToPreviewViewMatrix, new Size(surfaceCropRect.width(), surfaceCropRect.height()));
    }

    public Matrix getSensorToViewTransform() {
        Threads.checkMainThread();
        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }
        return this.mPreviewTransform.getSensorToViewTransform(new Size(getWidth(), getHeight()), getLayoutDirection());
    }

    private void attachToControllerIfReady(boolean z) {
        Threads.checkMainThread();
        ViewPort viewPort = getViewPort();
        if (this.mCameraController == null || viewPort == null || !isAttachedToWindow()) {
            return;
        }
        try {
            this.mCameraController.attachPreviewSurface(getSurfaceProvider(), viewPort);
        } catch (IllegalStateException e) {
            if (z) {
                Logger.e(TAG, e.toString(), e);
                return;
            }
            throw e;
        }
    }

    private void setScreenFlashUiInfo(ImageCapture.ScreenFlash screenFlash) {
        CameraController cameraController = this.mCameraController;
        if (cameraController == null) {
            Logger.d(TAG, "setScreenFlashUiInfo: mCameraController is null!");
        } else {
            cameraController.setScreenFlashUiInfo(new ScreenFlashUiInfo(ScreenFlashUiInfo.ProviderType.PREVIEW_VIEW, screenFlash));
        }
    }

    private void startListeningToDisplayChange() {
        DisplayManager displayManager = getDisplayManager();
        if (displayManager == null) {
            return;
        }
        displayManager.registerDisplayListener(this.mDisplayRotationListener, new Handler(Looper.getMainLooper()));
    }

    private void stopListeningToDisplayChange() {
        DisplayManager displayManager = getDisplayManager();
        if (displayManager == null) {
            return;
        }
        displayManager.unregisterDisplayListener(this.mDisplayRotationListener);
    }

    private DisplayManager getDisplayManager() {
        Context context = getContext();
        if (context == null) {
            return null;
        }
        return (DisplayManager) context.getApplicationContext().getSystemService("display");
    }

    public void setScreenFlashWindow(Window window) {
        Threads.checkMainThread();
        this.mScreenFlashView.setScreenFlashWindow(window);
        setScreenFlashUiInfo(getScreenFlashInternal());
    }

    private ImageCapture.ScreenFlash getScreenFlashInternal() {
        return this.mScreenFlashView.getScreenFlash();
    }

    public ImageCapture.ScreenFlash getScreenFlash() {
        return getScreenFlashInternal();
    }

    public void setScreenFlashOverlayColor(int i) {
        this.mScreenFlashView.setBackgroundColor(i);
    }

    class DisplayRotationListener implements DisplayManager.DisplayListener {
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        DisplayRotationListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            Display display = PreviewView.this.getDisplay();
            if (display == null || display.getDisplayId() != i) {
                return;
            }
            PreviewView.this.redrawPreview();
        }
    }
}

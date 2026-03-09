package androidx.camera.core.impl;

import android.graphics.Rect;
import androidx.camera.core.CameraControl;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.imagecapture.CameraCapturePipeline;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CameraControlInternal extends CameraControl {
    public static final CameraControlInternal DEFAULT_EMPTY_INSTANCE = new CameraControlInternal() { // from class: androidx.camera.core.impl.CameraControlInternal.2
        @Override // androidx.camera.core.impl.CameraControlInternal
        public void addInteropConfig(Config config) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void addZslConfig(SessionConfig.Builder builder) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void clearInteropConfig() {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public /* synthetic */ void decrementVideoUsage() {
            CC.$default$decrementVideoUsage(this);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public /* synthetic */ ListenableFuture getCameraCapturePipelineAsync(int i, int i2) {
            return CC.$default$getCameraCapturePipelineAsync(this, i, i2);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public int getFlashMode() {
            return 2;
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public /* synthetic */ CameraControlInternal getImplementation() {
            return CC.$default$getImplementation(this);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public Config getInteropConfig() {
            return null;
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public /* synthetic */ void incrementVideoUsage() {
            CC.$default$incrementVideoUsage(this);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public /* synthetic */ boolean isInVideoUsage() {
            return CC.$default$isInVideoUsage(this);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public boolean isZslDisabledByByUserCaseConfig() {
            return false;
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void setFlashMode(int i) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public /* synthetic */ void setScreenFlash(ImageCapture.ScreenFlash screenFlash) {
            CC.$default$setScreenFlash(this, screenFlash);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public void setZslDisabledByUserCaseConfig(boolean z) {
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> enableTorch(boolean z) {
            return Futures.immediateFuture(null);
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Integer> setExposureCompensationIndex(int i) {
            return Futures.immediateFuture(0);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public ListenableFuture<List<Void>> submitStillCaptureRequests(List<CaptureConfig> list, int i, int i2) {
            return Futures.immediateFuture(Collections.EMPTY_LIST);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public SessionConfig getSessionConfig() {
            return SessionConfig.defaultEmptySessionConfig();
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public Rect getSensorRect() {
            return new Rect();
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction focusMeteringAction) {
            return Futures.immediateFuture(FocusMeteringResult.emptyInstance());
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> cancelFocusAndMetering() {
            return Futures.immediateFuture(null);
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> setZoomRatio(float f) {
            return Futures.immediateFuture(null);
        }

        @Override // androidx.camera.core.CameraControl
        public ListenableFuture<Void> setLinearZoom(float f) {
            return Futures.immediateFuture(null);
        }
    };

    public interface ControlUpdateCallback {
        void onCameraControlCaptureRequests(List<CaptureConfig> list);

        void onCameraControlUpdateSessionConfig();
    }

    void addInteropConfig(Config config);

    void addZslConfig(SessionConfig.Builder builder);

    void clearInteropConfig();

    void decrementVideoUsage();

    ListenableFuture<CameraCapturePipeline> getCameraCapturePipelineAsync(int i, int i2);

    int getFlashMode();

    CameraControlInternal getImplementation();

    Config getInteropConfig();

    Rect getSensorRect();

    SessionConfig getSessionConfig();

    void incrementVideoUsage();

    boolean isInVideoUsage();

    boolean isZslDisabledByByUserCaseConfig();

    void setFlashMode(int i);

    void setScreenFlash(ImageCapture.ScreenFlash screenFlash);

    void setZslDisabledByUserCaseConfig(boolean z);

    ListenableFuture<List<Void>> submitStillCaptureRequests(List<CaptureConfig> list, int i, int i2);

    /* JADX INFO: renamed from: androidx.camera.core.impl.CameraControlInternal$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$decrementVideoUsage(CameraControlInternal _this) {
        }

        public static CameraControlInternal $default$getImplementation(CameraControlInternal _this) {
            return _this;
        }

        public static void $default$incrementVideoUsage(CameraControlInternal _this) {
        }

        public static boolean $default$isInVideoUsage(CameraControlInternal _this) {
            return false;
        }

        public static void $default$setScreenFlash(CameraControlInternal _this, ImageCapture.ScreenFlash screenFlash) {
        }

        public static ListenableFuture $default$getCameraCapturePipelineAsync(final CameraControlInternal _this, int i, int i2) {
            return Futures.immediateFuture(new CameraCapturePipeline() { // from class: androidx.camera.core.impl.CameraControlInternal.1
                @Override // androidx.camera.core.imagecapture.CameraCapturePipeline
                public ListenableFuture<Void> invokePreCapture() {
                    return Futures.immediateFuture(null);
                }

                @Override // androidx.camera.core.imagecapture.CameraCapturePipeline
                public ListenableFuture<Void> invokePostCapture() {
                    return Futures.immediateFuture(null);
                }
            });
        }
    }

    public static final class CameraControlException extends Exception {
        private CameraCaptureFailure mCameraCaptureFailure;

        public CameraControlException(CameraCaptureFailure cameraCaptureFailure) {
            this.mCameraCaptureFailure = cameraCaptureFailure;
        }

        public CameraControlException(CameraCaptureFailure cameraCaptureFailure, Throwable th) {
            super(th);
            this.mCameraCaptureFailure = cameraCaptureFailure;
        }

        public CameraCaptureFailure getCameraCaptureFailure() {
            return this.mCameraCaptureFailure;
        }
    }
}

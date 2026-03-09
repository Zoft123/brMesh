package androidx.camera.core.impl;

import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.impl.utils.SessionProcessorUtil;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedCameraControl extends ForwardingCameraControl {
    private final CameraControlInternal mCameraControl;
    private final SessionProcessor mSessionProcessor;

    public RestrictedCameraControl(CameraControlInternal cameraControlInternal, SessionProcessor sessionProcessor) {
        super(cameraControlInternal);
        this.mCameraControl = cameraControlInternal;
        this.mSessionProcessor = sessionProcessor;
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.impl.CameraControlInternal
    public CameraControlInternal getImplementation() {
        return this.mCameraControl;
    }

    public SessionProcessor getSessionProcessor() {
        return this.mSessionProcessor;
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.CameraControl
    public ListenableFuture<Void> enableTorch(boolean z) {
        if (!SessionProcessorUtil.isOperationSupported(this.mSessionProcessor, 6)) {
            return Futures.immediateFailedFuture(new IllegalStateException("Torch is not supported"));
        }
        return this.mCameraControl.enableTorch(z);
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.CameraControl
    public ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction focusMeteringAction) {
        FocusMeteringAction modifiedFocusMeteringAction = SessionProcessorUtil.getModifiedFocusMeteringAction(this.mSessionProcessor, focusMeteringAction);
        if (modifiedFocusMeteringAction == null) {
            return Futures.immediateFailedFuture(new IllegalStateException("FocusMetering is not supported"));
        }
        return this.mCameraControl.startFocusAndMetering(modifiedFocusMeteringAction);
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.CameraControl
    public ListenableFuture<Void> cancelFocusAndMetering() {
        return this.mCameraControl.cancelFocusAndMetering();
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.CameraControl
    public ListenableFuture<Void> setZoomRatio(float f) {
        if (!SessionProcessorUtil.isOperationSupported(this.mSessionProcessor, 0)) {
            return Futures.immediateFailedFuture(new IllegalStateException("Zoom is not supported"));
        }
        return this.mCameraControl.setZoomRatio(f);
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.CameraControl
    public ListenableFuture<Void> setLinearZoom(float f) {
        if (!SessionProcessorUtil.isOperationSupported(this.mSessionProcessor, 0)) {
            return Futures.immediateFailedFuture(new IllegalStateException("Zoom is not supported"));
        }
        return this.mCameraControl.setLinearZoom(f);
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.CameraControl
    public ListenableFuture<Integer> setExposureCompensationIndex(int i) {
        if (!SessionProcessorUtil.isOperationSupported(this.mSessionProcessor, 7)) {
            return Futures.immediateFailedFuture(new IllegalStateException("ExposureCompensation is not supported"));
        }
        return this.mCameraControl.setExposureCompensationIndex(i);
    }
}

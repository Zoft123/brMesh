package androidx.camera.core.impl;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraState;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.ExposureState;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class ForwardingCameraInfo implements CameraInfoInternal {
    private final CameraInfoInternal mCameraInfoInternal;

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public /* synthetic */ boolean isCaptureProcessProgressSupported() {
        return CameraInfoInternal.CC.$default$isCaptureProcessProgressSupported(this);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public /* synthetic */ boolean isPostviewSupported() {
        return CameraInfoInternal.CC.$default$isPostviewSupported(this);
    }

    public ForwardingCameraInfo(CameraInfoInternal cameraInfoInternal) {
        this.mCameraInfoInternal = cameraInfoInternal;
    }

    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees() {
        return this.mCameraInfoInternal.getSensorRotationDegrees();
    }

    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees(int i) {
        return this.mCameraInfoInternal.getSensorRotationDegrees(i);
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean hasFlashUnit() {
        return this.mCameraInfoInternal.hasFlashUnit();
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<Integer> getTorchState() {
        return this.mCameraInfoInternal.getTorchState();
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<ZoomState> getZoomState() {
        return this.mCameraInfoInternal.getZoomState();
    }

    @Override // androidx.camera.core.CameraInfo
    public ExposureState getExposureState() {
        return this.mCameraInfoInternal.getExposureState();
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<CameraState> getCameraState() {
        return this.mCameraInfoInternal.getCameraState();
    }

    @Override // androidx.camera.core.CameraInfo
    public String getImplementationType() {
        return this.mCameraInfoInternal.getImplementationType();
    }

    @Override // androidx.camera.core.CameraInfo
    public int getLensFacing() {
        return this.mCameraInfoInternal.getLensFacing();
    }

    @Override // androidx.camera.core.CameraInfo
    public float getIntrinsicZoomRatio() {
        return this.mCameraInfoInternal.getIntrinsicZoomRatio();
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isFocusMeteringSupported(FocusMeteringAction focusMeteringAction) {
        return this.mCameraInfoInternal.isFocusMeteringSupported(focusMeteringAction);
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isZslSupported() {
        return this.mCameraInfoInternal.isZslSupported();
    }

    @Override // androidx.camera.core.CameraInfo
    public Set<Range<Integer>> getSupportedFrameRateRanges() {
        return this.mCameraInfoInternal.getSupportedFrameRateRanges();
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isPrivateReprocessingSupported() {
        return this.mCameraInfoInternal.isPrivateReprocessingSupported();
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isLogicalMultiCameraSupported() {
        return this.mCameraInfoInternal.isLogicalMultiCameraSupported();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public String getCameraId() {
        return this.mCameraInfoInternal.getCameraId();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public void addSessionCaptureCallback(Executor executor, CameraCaptureCallback cameraCaptureCallback) {
        this.mCameraInfoInternal.addSessionCaptureCallback(executor, cameraCaptureCallback);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public void removeSessionCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
        this.mCameraInfoInternal.removeSessionCaptureCallback(cameraCaptureCallback);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Quirks getCameraQuirks() {
        return this.mCameraInfoInternal.getCameraQuirks();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public EncoderProfilesProvider getEncoderProfilesProvider() {
        return this.mCameraInfoInternal.getEncoderProfilesProvider();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Timebase getTimebase() {
        return this.mCameraInfoInternal.getTimebase();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Set<Integer> getSupportedOutputFormats() {
        return this.mCameraInfoInternal.getSupportedOutputFormats();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public List<Size> getSupportedResolutions(int i) {
        return this.mCameraInfoInternal.getSupportedResolutions(i);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public List<Size> getSupportedHighResolutions(int i) {
        return this.mCameraInfoInternal.getSupportedHighResolutions(i);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Set<DynamicRange> getSupportedDynamicRanges() {
        return this.mCameraInfoInternal.getSupportedDynamicRanges();
    }

    @Override // androidx.camera.core.CameraInfo
    public Set<DynamicRange> querySupportedDynamicRanges(Set<DynamicRange> set) {
        return this.mCameraInfoInternal.querySupportedDynamicRanges(set);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public CameraInfoInternal getImplementation() {
        return this.mCameraInfoInternal.getImplementation();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal, androidx.camera.core.CameraInfo
    public CameraSelector getCameraSelector() {
        return this.mCameraInfoInternal.getCameraSelector();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public boolean isPreviewStabilizationSupported() {
        return this.mCameraInfoInternal.isPreviewStabilizationSupported();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public boolean isVideoStabilizationSupported() {
        return this.mCameraInfoInternal.isVideoStabilizationSupported();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Object getCameraCharacteristics() {
        return this.mCameraInfoInternal.getCameraCharacteristics();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Object getPhysicalCameraCharacteristics(String str) {
        return this.mCameraInfoInternal.getPhysicalCameraCharacteristics(str);
    }

    @Override // androidx.camera.core.CameraInfo
    public Set<CameraInfo> getPhysicalCameraInfos() {
        return this.mCameraInfoInternal.getPhysicalCameraInfos();
    }
}

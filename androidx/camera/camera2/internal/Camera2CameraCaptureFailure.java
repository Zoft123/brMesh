package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureFailure;
import androidx.camera.core.impl.CameraCaptureFailure;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2CameraCaptureFailure extends CameraCaptureFailure {
    private final CaptureFailure mCaptureFailure;

    public Camera2CameraCaptureFailure(CameraCaptureFailure.Reason reason, CaptureFailure captureFailure) {
        super(reason);
        this.mCaptureFailure = captureFailure;
    }

    @Override // androidx.camera.core.impl.CameraCaptureFailure
    public Object getCaptureFailure() {
        return this.mCaptureFailure;
    }
}

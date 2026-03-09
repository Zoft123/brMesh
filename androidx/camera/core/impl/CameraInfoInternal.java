package androidx.camera.core.impl;

import android.util.Size;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.DynamicRange;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public interface CameraInfoInternal extends CameraInfo {
    void addSessionCaptureCallback(Executor executor, CameraCaptureCallback cameraCaptureCallback);

    Object getCameraCharacteristics();

    String getCameraId();

    Quirks getCameraQuirks();

    @Override // androidx.camera.core.CameraInfo
    CameraSelector getCameraSelector();

    EncoderProfilesProvider getEncoderProfilesProvider();

    CameraInfoInternal getImplementation();

    Object getPhysicalCameraCharacteristics(String str);

    Set<DynamicRange> getSupportedDynamicRanges();

    List<Size> getSupportedHighResolutions(int i);

    Set<Integer> getSupportedOutputFormats();

    List<Size> getSupportedResolutions(int i);

    Timebase getTimebase();

    boolean isCaptureProcessProgressSupported();

    boolean isPostviewSupported();

    boolean isPreviewStabilizationSupported();

    boolean isVideoStabilizationSupported();

    void removeSessionCaptureCallback(CameraCaptureCallback cameraCaptureCallback);

    /* JADX INFO: renamed from: androidx.camera.core.impl.CameraInfoInternal$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static CameraInfoInternal $default$getImplementation(CameraInfoInternal _this) {
            return _this;
        }

        public static boolean $default$isCaptureProcessProgressSupported(CameraInfoInternal _this) {
            return false;
        }

        public static boolean $default$isPostviewSupported(CameraInfoInternal _this) {
            return false;
        }

        public static /* synthetic */ List $private$lambda$getCameraSelector$0(CameraInfoInternal _this, List list) {
            String cameraId = _this.getCameraId();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                CameraInfo cameraInfo = (CameraInfo) it.next();
                Preconditions.checkArgument(cameraInfo instanceof CameraInfoInternal);
                if (((CameraInfoInternal) cameraInfo).getCameraId().equals(cameraId)) {
                    return Collections.singletonList(cameraInfo);
                }
            }
            throw new IllegalStateException("Unable to find camera with id " + cameraId + " from list of available cameras.");
        }
    }
}

package androidx.camera.core;

import android.util.Range;
import androidx.camera.core.internal.compat.MediaActionSoundCompat;
import androidx.lifecycle.LiveData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public interface CameraInfo {
    public static final String IMPLEMENTATION_TYPE_CAMERA2 = "androidx.camera.camera2";
    public static final String IMPLEMENTATION_TYPE_CAMERA2_LEGACY = "androidx.camera.camera2.legacy";
    public static final String IMPLEMENTATION_TYPE_FAKE = "androidx.camera.fake";
    public static final String IMPLEMENTATION_TYPE_UNKNOWN = "<unknown>";
    public static final float INTRINSIC_ZOOM_RATIO_UNKNOWN = 1.0f;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImplementationType {
    }

    CameraSelector getCameraSelector();

    LiveData<CameraState> getCameraState();

    ExposureState getExposureState();

    String getImplementationType();

    float getIntrinsicZoomRatio();

    int getLensFacing();

    Set<CameraInfo> getPhysicalCameraInfos();

    int getSensorRotationDegrees();

    int getSensorRotationDegrees(int i);

    Set<Range<Integer>> getSupportedFrameRateRanges();

    LiveData<Integer> getTorchState();

    LiveData<ZoomState> getZoomState();

    boolean hasFlashUnit();

    boolean isFocusMeteringSupported(FocusMeteringAction focusMeteringAction);

    boolean isLogicalMultiCameraSupported();

    boolean isPrivateReprocessingSupported();

    boolean isZslSupported();

    Set<DynamicRange> querySupportedDynamicRanges(Set<DynamicRange> set);

    /* JADX INFO: renamed from: androidx.camera.core.CameraInfo$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static float $default$getIntrinsicZoomRatio(CameraInfo _this) {
            return 1.0f;
        }

        public static int $default$getLensFacing(CameraInfo _this) {
            return -1;
        }

        public static boolean $default$isFocusMeteringSupported(CameraInfo _this, FocusMeteringAction focusMeteringAction) {
            return false;
        }

        public static boolean $default$isLogicalMultiCameraSupported(CameraInfo _this) {
            return false;
        }

        public static boolean $default$isPrivateReprocessingSupported(CameraInfo _this) {
            return false;
        }

        public static boolean $default$isZslSupported(CameraInfo _this) {
            return false;
        }

        public static boolean mustPlayShutterSound() {
            return MediaActionSoundCompat.mustPlayShutterSound();
        }
    }
}

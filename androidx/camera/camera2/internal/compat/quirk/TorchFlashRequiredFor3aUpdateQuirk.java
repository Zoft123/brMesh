package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.core.impl.Quirk;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class TorchFlashRequiredFor3aUpdateQuirk implements Quirk {
    private static final List<String> AFFECTED_PIXEL_MODELS = Arrays.asList("PIXEL 6A", "PIXEL 6 PRO", "PIXEL 7", "PIXEL 7A", "PIXEL 7 PRO", "PIXEL 8", "PIXEL 8 PRO");
    private final CameraCharacteristicsCompat mCameraCharacteristics;

    public TorchFlashRequiredFor3aUpdateQuirk(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        this.mCameraCharacteristics = cameraCharacteristicsCompat;
    }

    static boolean load(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return isAffectedModel(cameraCharacteristicsCompat);
    }

    public boolean isFlashModeTorchRequired() {
        return !isExternalFlashAeModeSupported(this.mCameraCharacteristics);
    }

    private static boolean isAffectedModel(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return isAffectedPixelModel() && isFrontCamera(cameraCharacteristicsCompat);
    }

    private static boolean isAffectedPixelModel() {
        Iterator<String> it = AFFECTED_PIXEL_MODELS.iterator();
        while (it.hasNext()) {
            if (Build.MODEL.toUpperCase(Locale.US).equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFrontCamera(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return ((Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
    }

    private static boolean isExternalFlashAeModeSupported(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return Build.VERSION.SDK_INT >= 28 && Camera2CameraControlImpl.getSupportedAeMode(cameraCharacteristicsCompat, 5) == 5;
    }
}

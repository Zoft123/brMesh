package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.core.impl.Quirk;

/* JADX INFO: loaded from: classes.dex */
public class LegacyCameraSurfaceCleanupQuirk implements Quirk {
    static boolean load(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return Build.VERSION.SDK_INT > 23 && Build.VERSION.SDK_INT < 29 && isLegacyDevice(cameraCharacteristicsCompat);
    }

    static boolean isLegacyDevice(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        Integer num = (Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        return num != null && num.intValue() == 2;
    }
}

package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.core.impl.Quirk;

/* JADX INFO: loaded from: classes.dex */
public class CaptureNoResponseQuirk implements Quirk {
    static boolean load(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return ("samsungexynos7420".equalsIgnoreCase(Build.HARDWARE) || "universal7420".equalsIgnoreCase(Build.HARDWARE)) && ((Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.LENS_FACING)).intValue() == 1;
    }
}

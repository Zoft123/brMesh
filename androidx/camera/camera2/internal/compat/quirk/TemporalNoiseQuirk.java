package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.quirk.CaptureIntentPreviewQuirk;

/* JADX INFO: loaded from: classes.dex */
public class TemporalNoiseQuirk implements CaptureIntentPreviewQuirk {
    @Override // androidx.camera.camera2.internal.compat.quirk.CaptureIntentPreviewQuirk
    public /* synthetic */ boolean workaroundByCaptureIntentPreview() {
        return CaptureIntentPreviewQuirk.CC.$default$workaroundByCaptureIntentPreview(this);
    }

    static boolean load(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return isPixel8() && ((Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
    }

    private static boolean isPixel8() {
        return "Pixel 8".equalsIgnoreCase(Build.MODEL);
    }
}

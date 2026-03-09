package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import androidx.camera.camera2.internal.compat.quirk.CaptureIntentPreviewQuirk;
import androidx.camera.core.internal.compat.quirk.SurfaceProcessingQuirk;

/* JADX INFO: loaded from: classes.dex */
public class PreviewDelayWhenVideoCaptureIsBoundQuirk implements CaptureIntentPreviewQuirk, SurfaceProcessingQuirk {
    @Override // androidx.camera.camera2.internal.compat.quirk.CaptureIntentPreviewQuirk
    public /* synthetic */ boolean workaroundByCaptureIntentPreview() {
        return CaptureIntentPreviewQuirk.CC.$default$workaroundByCaptureIntentPreview(this);
    }

    @Override // androidx.camera.core.internal.compat.quirk.SurfaceProcessingQuirk
    public /* synthetic */ boolean workaroundBySurfaceProcessing() {
        return SurfaceProcessingQuirk.CC.$default$workaroundBySurfaceProcessing(this);
    }

    static boolean load() {
        return "Huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }
}

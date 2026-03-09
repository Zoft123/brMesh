package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import android.util.Size;
import androidx.camera.core.impl.Quirk;
import com.king.camera.scan.config.ResolutionCameraConfig;
import com.king.zxing.util.CodeUtils;

/* JADX INFO: loaded from: classes.dex */
public class StretchedVideoResolutionQuirk implements Quirk {
    static boolean load() {
        return isMotoE5Play();
    }

    private static boolean isMotoE5Play() {
        return "motorola".equalsIgnoreCase(Build.BRAND) && "moto e5 play".equalsIgnoreCase(Build.MODEL);
    }

    public Size getAlternativeResolution(int i) {
        if (i == 4) {
            return new Size(CodeUtils.DEFAULT_REQ_HEIGHT, CodeUtils.DEFAULT_REQ_WIDTH);
        }
        if (i == 5) {
            return new Size(960, ResolutionCameraConfig.IMAGE_QUALITY_720P);
        }
        if (i != 6) {
            return null;
        }
        return new Size(1440, ResolutionCameraConfig.IMAGE_QUALITY_1080P);
    }
}

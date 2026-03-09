package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Size;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.StreamConfigurationMapCompat;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.Quirk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CamcorderProfileResolutionQuirk implements Quirk {
    private static final String TAG = "CamcorderProfileResolutionQuirk";
    private final StreamConfigurationMapCompat mStreamConfigurationMapCompat;
    private List<Size> mSupportedResolutions = null;

    static boolean load(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        Integer num = (Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        return num != null && num.intValue() == 2;
    }

    public CamcorderProfileResolutionQuirk(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        this.mStreamConfigurationMapCompat = cameraCharacteristicsCompat.getStreamConfigurationMapCompat();
    }

    public List<Size> getSupportedResolutions() {
        List<Size> listAsList;
        if (this.mSupportedResolutions == null) {
            Size[] outputSizes = this.mStreamConfigurationMapCompat.getOutputSizes(34);
            if (outputSizes != null) {
                listAsList = Arrays.asList((Size[]) outputSizes.clone());
            } else {
                listAsList = Collections.EMPTY_LIST;
            }
            this.mSupportedResolutions = listAsList;
            Logger.d(TAG, "mSupportedResolutions = " + this.mSupportedResolutions);
        }
        return new ArrayList(this.mSupportedResolutions);
    }
}

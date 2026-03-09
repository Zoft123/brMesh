package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;
import androidx.camera.core.impl.SurfaceCombination;
import androidx.camera.core.impl.SurfaceConfig;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ExtraSupportedSurfaceCombinationsQuirk implements Quirk {
    private static final SurfaceCombination FULL_LEVEL_YUV_PRIV_YUV_CONFIGURATION = createFullYuvPrivYuvConfiguration();
    private static final SurfaceCombination LEVEL_3_LEVEL_PRIV_PRIV_YUV_SUBSET_CONFIGURATION = createLevel3PrivPrivYuvSubsetConfiguration();
    private static final Set<String> SUPPORT_EXTRA_LEVEL_3_CONFIGURATIONS_GOOGLE_MODELS = new HashSet(Arrays.asList("PIXEL 6", "PIXEL 6 PRO", "PIXEL 7", "PIXEL 7 PRO", "PIXEL 8", "PIXEL 8 PRO"));
    private static final Set<String> SUPPORT_EXTRA_LEVEL_3_CONFIGURATIONS_SAMSUNG_MODELS = new HashSet(Arrays.asList("SM-S921", "SC-51E", "SCG25", "SM-S926", "SM-S928", "SC-52E", "SCG26"));
    private static final String TAG = "ExtraSupportedSurfaceCombinationsQuirk";

    static boolean load() {
        return isSamsungS7() || supportExtraLevel3ConfigurationsGoogleDevice() || supportExtraLevel3ConfigurationsSamsungDevice();
    }

    private static boolean isSamsungS7() {
        return "heroqltevzw".equalsIgnoreCase(Build.DEVICE) || "heroqltetmo".equalsIgnoreCase(Build.DEVICE);
    }

    private static boolean supportExtraLevel3ConfigurationsGoogleDevice() {
        if (!"google".equalsIgnoreCase(Build.BRAND)) {
            return false;
        }
        return SUPPORT_EXTRA_LEVEL_3_CONFIGURATIONS_GOOGLE_MODELS.contains(Build.MODEL.toUpperCase(Locale.US));
    }

    private static boolean supportExtraLevel3ConfigurationsSamsungDevice() {
        if (!"samsung".equalsIgnoreCase(Build.BRAND)) {
            return false;
        }
        String upperCase = Build.MODEL.toUpperCase(Locale.US);
        Iterator<String> it = SUPPORT_EXTRA_LEVEL_3_CONFIGURATIONS_SAMSUNG_MODELS.iterator();
        while (it.hasNext()) {
            if (upperCase.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public List<SurfaceCombination> getExtraSupportedSurfaceCombinations(String str) {
        if (isSamsungS7()) {
            return getSamsungS7ExtraCombinations(str);
        }
        if (supportExtraLevel3ConfigurationsGoogleDevice() || supportExtraLevel3ConfigurationsSamsungDevice()) {
            return Collections.singletonList(LEVEL_3_LEVEL_PRIV_PRIV_YUV_SUBSET_CONFIGURATION);
        }
        return Collections.EMPTY_LIST;
    }

    private List<SurfaceCombination> getSamsungS7ExtraCombinations(String str) {
        ArrayList arrayList = new ArrayList();
        if (str.equals(GlobalVariable.RADAR)) {
            arrayList.add(FULL_LEVEL_YUV_PRIV_YUV_CONFIGURATION);
        }
        return arrayList;
    }

    private static SurfaceCombination createFullYuvPrivYuvConfiguration() {
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.VGA));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        return surfaceCombination;
    }

    private static SurfaceCombination createLevel3PrivPrivYuvSubsetConfiguration() {
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.VGA));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        return surfaceCombination;
    }
}

package androidx.camera.video;

import android.util.Size;
import androidx.arch.core.util.Function;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.DynamicRanges;
import androidx.camera.core.impl.EncoderProfilesProvider;
import androidx.camera.core.impl.Quirks;
import androidx.camera.video.internal.BackupHdrProfileEncoderProfilesProvider;
import androidx.camera.video.internal.DynamicRangeMatchedEncoderProfilesProvider;
import androidx.camera.video.internal.QualityExploredEncoderProfilesProvider;
import androidx.camera.video.internal.VideoValidatedEncoderProfilesProxy;
import androidx.camera.video.internal.compat.quirk.DeviceQuirks;
import androidx.camera.video.internal.encoder.VideoEncoderConfig;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import androidx.camera.video.internal.workaround.QualityAddedEncoderProfilesProvider;
import androidx.camera.video.internal.workaround.QualityResolutionModifiedEncoderProfilesProvider;
import androidx.camera.video.internal.workaround.QualityValidatedEncoderProfilesProvider;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class RecorderVideoCapabilities implements VideoCapabilities {
    private final Map<DynamicRange, CapabilitiesByQuality> mCapabilitiesMapForFullySpecifiedDynamicRange = new HashMap();
    private final Map<DynamicRange, CapabilitiesByQuality> mCapabilitiesMapForNonFullySpecifiedDynamicRange = new HashMap();
    private final boolean mIsStabilizationSupported;
    private final EncoderProfilesProvider mProfilesProvider;

    RecorderVideoCapabilities(int i, CameraInfoInternal cameraInfoInternal, Function<VideoEncoderConfig, VideoEncoderInfo> function) {
        Function<VideoEncoderConfig, VideoEncoderInfo> function2;
        Preconditions.checkArgument(i == 0 || i == 1, "Not a supported video capabilities source: " + i);
        EncoderProfilesProvider encoderProfilesProvider = cameraInfoInternal.getEncoderProfilesProvider();
        Quirks all = DeviceQuirks.getAll();
        EncoderProfilesProvider qualityAddedEncoderProfilesProvider = new QualityAddedEncoderProfilesProvider(encoderProfilesProvider, all, cameraInfoInternal, function);
        if (i == 1) {
            function2 = function;
            qualityAddedEncoderProfilesProvider = new QualityExploredEncoderProfilesProvider(qualityAddedEncoderProfilesProvider, Quality.getSortedQualities(), Collections.singleton(DynamicRange.SDR), cameraInfoInternal.getSupportedResolutions(34), function2);
        } else {
            function2 = function;
        }
        EncoderProfilesProvider qualityResolutionModifiedEncoderProfilesProvider = new QualityResolutionModifiedEncoderProfilesProvider(qualityAddedEncoderProfilesProvider, all);
        this.mProfilesProvider = new QualityValidatedEncoderProfilesProvider(isHlg10SupportedByCamera(cameraInfoInternal) ? new BackupHdrProfileEncoderProfilesProvider(qualityResolutionModifiedEncoderProfilesProvider, function2) : qualityResolutionModifiedEncoderProfilesProvider, cameraInfoInternal, all);
        for (DynamicRange dynamicRange : cameraInfoInternal.getSupportedDynamicRanges()) {
            CapabilitiesByQuality capabilitiesByQuality = new CapabilitiesByQuality(new DynamicRangeMatchedEncoderProfilesProvider(this.mProfilesProvider, dynamicRange));
            if (!capabilitiesByQuality.getSupportedQualities().isEmpty()) {
                this.mCapabilitiesMapForFullySpecifiedDynamicRange.put(dynamicRange, capabilitiesByQuality);
            }
        }
        this.mIsStabilizationSupported = cameraInfoInternal.isVideoStabilizationSupported();
    }

    @Override // androidx.camera.video.VideoCapabilities
    public Set<DynamicRange> getSupportedDynamicRanges() {
        return this.mCapabilitiesMapForFullySpecifiedDynamicRange.keySet();
    }

    @Override // androidx.camera.video.VideoCapabilities
    public List<Quality> getSupportedQualities(DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        return capabilities == null ? new ArrayList() : capabilities.getSupportedQualities();
    }

    @Override // androidx.camera.video.VideoCapabilities
    public boolean isQualitySupported(Quality quality, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        return capabilities != null && capabilities.isQualitySupported(quality);
    }

    @Override // androidx.camera.video.VideoCapabilities
    public boolean isStabilizationSupported() {
        return this.mIsStabilizationSupported;
    }

    @Override // androidx.camera.video.VideoCapabilities
    public VideoValidatedEncoderProfilesProxy getProfiles(Quality quality, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        if (capabilities == null) {
            return null;
        }
        return capabilities.getProfiles(quality);
    }

    @Override // androidx.camera.video.VideoCapabilities
    public VideoValidatedEncoderProfilesProxy findNearestHigherSupportedEncoderProfilesFor(Size size, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        if (capabilities == null) {
            return null;
        }
        return capabilities.findNearestHigherSupportedEncoderProfilesFor(size);
    }

    @Override // androidx.camera.video.VideoCapabilities
    public Quality findNearestHigherSupportedQualityFor(Size size, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        if (capabilities == null) {
            return Quality.NONE;
        }
        return capabilities.findNearestHigherSupportedQualityFor(size);
    }

    private CapabilitiesByQuality getCapabilities(DynamicRange dynamicRange) {
        if (dynamicRange.isFullySpecified()) {
            return this.mCapabilitiesMapForFullySpecifiedDynamicRange.get(dynamicRange);
        }
        if (this.mCapabilitiesMapForNonFullySpecifiedDynamicRange.containsKey(dynamicRange)) {
            return this.mCapabilitiesMapForNonFullySpecifiedDynamicRange.get(dynamicRange);
        }
        CapabilitiesByQuality capabilitiesByQualityGenerateCapabilitiesForNonFullySpecifiedDynamicRange = generateCapabilitiesForNonFullySpecifiedDynamicRange(dynamicRange);
        this.mCapabilitiesMapForNonFullySpecifiedDynamicRange.put(dynamicRange, capabilitiesByQualityGenerateCapabilitiesForNonFullySpecifiedDynamicRange);
        return capabilitiesByQualityGenerateCapabilitiesForNonFullySpecifiedDynamicRange;
    }

    private static boolean isHlg10SupportedByCamera(CameraInfoInternal cameraInfoInternal) {
        for (DynamicRange dynamicRange : cameraInfoInternal.getSupportedDynamicRanges()) {
            Integer numValueOf = Integer.valueOf(dynamicRange.getEncoding());
            int bitDepth = dynamicRange.getBitDepth();
            if (numValueOf.equals(3) && bitDepth == 10) {
                return true;
            }
        }
        return false;
    }

    private CapabilitiesByQuality generateCapabilitiesForNonFullySpecifiedDynamicRange(DynamicRange dynamicRange) {
        if (DynamicRanges.canResolve(dynamicRange, getSupportedDynamicRanges())) {
            return new CapabilitiesByQuality(new DynamicRangeMatchedEncoderProfilesProvider(this.mProfilesProvider, dynamicRange));
        }
        return null;
    }
}

package androidx.camera.video;

import android.util.Size;
import androidx.camera.core.DynamicRange;
import androidx.camera.video.internal.VideoValidatedEncoderProfilesProxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public interface VideoCapabilities {
    public static final VideoCapabilities EMPTY = new VideoCapabilities() { // from class: androidx.camera.video.VideoCapabilities.1
        @Override // androidx.camera.video.VideoCapabilities
        public /* synthetic */ VideoValidatedEncoderProfilesProxy findNearestHigherSupportedEncoderProfilesFor(Size size, DynamicRange dynamicRange) {
            return CC.$default$findNearestHigherSupportedEncoderProfilesFor(this, size, dynamicRange);
        }

        @Override // androidx.camera.video.VideoCapabilities
        public /* synthetic */ Quality findNearestHigherSupportedQualityFor(Size size, DynamicRange dynamicRange) {
            return CC.$default$findNearestHigherSupportedQualityFor(this, size, dynamicRange);
        }

        @Override // androidx.camera.video.VideoCapabilities
        public /* synthetic */ VideoValidatedEncoderProfilesProxy getProfiles(Quality quality, DynamicRange dynamicRange) {
            return CC.$default$getProfiles(this, quality, dynamicRange);
        }

        @Override // androidx.camera.video.VideoCapabilities
        public boolean isQualitySupported(Quality quality, DynamicRange dynamicRange) {
            return false;
        }

        @Override // androidx.camera.video.VideoCapabilities
        public boolean isStabilizationSupported() {
            return false;
        }

        @Override // androidx.camera.video.VideoCapabilities
        public Set<DynamicRange> getSupportedDynamicRanges() {
            return new HashSet();
        }

        @Override // androidx.camera.video.VideoCapabilities
        public List<Quality> getSupportedQualities(DynamicRange dynamicRange) {
            return new ArrayList();
        }
    };

    VideoValidatedEncoderProfilesProxy findNearestHigherSupportedEncoderProfilesFor(Size size, DynamicRange dynamicRange);

    Quality findNearestHigherSupportedQualityFor(Size size, DynamicRange dynamicRange);

    VideoValidatedEncoderProfilesProxy getProfiles(Quality quality, DynamicRange dynamicRange);

    Set<DynamicRange> getSupportedDynamicRanges();

    List<Quality> getSupportedQualities(DynamicRange dynamicRange);

    boolean isQualitySupported(Quality quality, DynamicRange dynamicRange);

    boolean isStabilizationSupported();

    /* JADX INFO: renamed from: androidx.camera.video.VideoCapabilities$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static VideoValidatedEncoderProfilesProxy $default$findNearestHigherSupportedEncoderProfilesFor(VideoCapabilities _this, Size size, DynamicRange dynamicRange) {
            return null;
        }

        public static VideoValidatedEncoderProfilesProxy $default$getProfiles(VideoCapabilities _this, Quality quality, DynamicRange dynamicRange) {
            return null;
        }

        public static boolean $default$isStabilizationSupported(VideoCapabilities _this) {
            return false;
        }

        public static Quality $default$findNearestHigherSupportedQualityFor(VideoCapabilities _this, Size size, DynamicRange dynamicRange) {
            return Quality.NONE;
        }
    }
}

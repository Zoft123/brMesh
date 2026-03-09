package androidx.camera.video;

import android.util.Size;
import com.king.camera.scan.config.ResolutionCameraConfig;
import com.king.zxing.util.CodeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class Quality {
    public static final Quality FHD;
    public static final Quality HD;
    public static final Quality HIGHEST;
    public static final Quality LOWEST;
    static final Quality NONE;
    private static final Set<Quality> QUALITIES;
    private static final List<Quality> QUALITIES_ORDER_BY_SIZE;
    public static final Quality SD;
    public static final Quality UHD;

    private Quality() {
    }

    static {
        ConstantQuality constantQualityOf = ConstantQuality.of(4, "SD", Collections.unmodifiableList(Arrays.asList(new Size(ResolutionCameraConfig.IMAGE_QUALITY_720P, CodeUtils.DEFAULT_REQ_WIDTH), new Size(CodeUtils.DEFAULT_REQ_HEIGHT, CodeUtils.DEFAULT_REQ_WIDTH))));
        SD = constantQualityOf;
        ConstantQuality constantQualityOf2 = ConstantQuality.of(5, "HD", Collections.singletonList(new Size(1280, ResolutionCameraConfig.IMAGE_QUALITY_720P)));
        HD = constantQualityOf2;
        ConstantQuality constantQualityOf3 = ConstantQuality.of(6, "FHD", Collections.singletonList(new Size(1920, ResolutionCameraConfig.IMAGE_QUALITY_1080P)));
        FHD = constantQualityOf3;
        ConstantQuality constantQualityOf4 = ConstantQuality.of(8, "UHD", Collections.singletonList(new Size(3840, 2160)));
        UHD = constantQualityOf4;
        ConstantQuality constantQualityOf5 = ConstantQuality.of(0, "LOWEST", Collections.EMPTY_LIST);
        LOWEST = constantQualityOf5;
        ConstantQuality constantQualityOf6 = ConstantQuality.of(1, "HIGHEST", Collections.EMPTY_LIST);
        HIGHEST = constantQualityOf6;
        NONE = ConstantQuality.of(-1, "NONE", Collections.EMPTY_LIST);
        QUALITIES = new HashSet(Arrays.asList(constantQualityOf5, constantQualityOf6, constantQualityOf, constantQualityOf2, constantQualityOf3, constantQualityOf4));
        QUALITIES_ORDER_BY_SIZE = Arrays.asList(constantQualityOf4, constantQualityOf3, constantQualityOf2, constantQualityOf);
    }

    static boolean containsQuality(Quality quality) {
        return QUALITIES.contains(quality);
    }

    public static List<Quality> getSortedQualities() {
        return new ArrayList(QUALITIES_ORDER_BY_SIZE);
    }

    public static abstract class ConstantQuality extends Quality {
        public abstract String getName();

        public abstract List<Size> getTypicalSizes();

        public abstract int getValue();

        public ConstantQuality() {
            super();
        }

        static ConstantQuality of(int i, String str, List<Size> list) {
            return new AutoValue_Quality_ConstantQuality(i, str, list);
        }
    }
}

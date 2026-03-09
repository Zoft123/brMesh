package androidx.camera.core.internal.utils;

import android.util.Size;
import androidx.camera.core.impl.utils.CompareSizesByArea;
import com.king.camera.scan.config.ResolutionCameraConfig;
import com.king.zxing.util.CodeUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public final class SizeUtil {
    public static final Size RESOLUTION_ZERO = new Size(0, 0);
    public static final Size RESOLUTION_QVGA = new Size(320, 240);
    public static final Size RESOLUTION_VGA = new Size(CodeUtils.DEFAULT_REQ_HEIGHT, CodeUtils.DEFAULT_REQ_WIDTH);
    public static final Size RESOLUTION_480P = new Size(ResolutionCameraConfig.IMAGE_QUALITY_720P, CodeUtils.DEFAULT_REQ_WIDTH);
    public static final Size RESOLUTION_720P = new Size(1280, ResolutionCameraConfig.IMAGE_QUALITY_720P);
    public static final Size RESOLUTION_1080P = new Size(1920, ResolutionCameraConfig.IMAGE_QUALITY_1080P);
    public static final Size RESOLUTION_1440P = new Size(1920, 1440);

    public static int getArea(int i, int i2) {
        return i * i2;
    }

    private SizeUtil() {
    }

    public static int getArea(Size size) {
        return getArea(size.getWidth(), size.getHeight());
    }

    public static boolean isSmallerByArea(Size size, Size size2) {
        return getArea(size) < getArea(size2);
    }

    public static boolean isLongerInAnyEdge(Size size, Size size2) {
        return size.getWidth() > size2.getWidth() || size.getHeight() > size2.getHeight();
    }

    public static Size getMaxSize(List<Size> list) {
        if (list.isEmpty()) {
            return null;
        }
        return (Size) Collections.max(list, new CompareSizesByArea());
    }

    public static <T> T findNearestHigherFor(Size size, TreeMap<Size, T> treeMap) {
        Map.Entry<Size, T> entryCeilingEntry = treeMap.ceilingEntry(size);
        if (entryCeilingEntry != null) {
            return entryCeilingEntry.getValue();
        }
        Map.Entry<Size, T> entryFloorEntry = treeMap.floorEntry(size);
        if (entryFloorEntry != null) {
            return entryFloorEntry.getValue();
        }
        return null;
    }
}

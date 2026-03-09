package androidx.camera.core.streamsharing;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.AspectRatioUtil;
import androidx.camera.core.impl.utils.CompareSizesByArea;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.SupportedOutputSizesSorter;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ResolutionsMerger {
    private static final double SAME_AREA_WIDTH_HEIGHT_RATIO = Math.sqrt(2.3703703703703702d);
    private static final String TAG = "ResolutionsMerger";
    private final CameraInfoInternal mCameraInfo;
    private final Map<UseCaseConfig<?>, List<Size>> mChildSizesCache;
    private final Set<UseCaseConfig<?>> mChildrenConfigs;
    private final Rational mFallbackAspectRatio;
    private final Rational mSensorAspectRatio;
    private final Size mSensorSize;
    private final SupportedOutputSizesSorter mSizeSorter;

    private boolean areCroppingInDifferentDirection(float f, float f2, float f3) {
        if (f != f2 && f2 != f3) {
            if (f > f2) {
                return f2 < f3;
            }
            if (f2 > f3) {
                return true;
            }
        }
        return false;
    }

    ResolutionsMerger(CameraInternal cameraInternal, Set<UseCaseConfig<?>> set) {
        this(TransformUtils.rectToSize(cameraInternal.getCameraControlInternal().getSensorRect()), cameraInternal.getCameraInfoInternal(), set);
    }

    private ResolutionsMerger(Size size, CameraInfoInternal cameraInfoInternal, Set<UseCaseConfig<?>> set) {
        this(size, cameraInfoInternal, set, new SupportedOutputSizesSorter(cameraInfoInternal, size));
    }

    ResolutionsMerger(Size size, CameraInfoInternal cameraInfoInternal, Set<UseCaseConfig<?>> set, SupportedOutputSizesSorter supportedOutputSizesSorter) {
        this.mChildSizesCache = new HashMap();
        this.mSensorSize = size;
        Rational sensorAspectRatio = getSensorAspectRatio(size);
        this.mSensorAspectRatio = sensorAspectRatio;
        this.mFallbackAspectRatio = getFallbackAspectRatio(sensorAspectRatio);
        this.mCameraInfo = cameraInfoInternal;
        this.mChildrenConfigs = set;
        this.mSizeSorter = supportedOutputSizesSorter;
    }

    List<Size> getMergedResolutions(MutableConfig mutableConfig) {
        List<Size> cameraSupportedResolutions = getCameraSupportedResolutions();
        if (shouldIncludeHighResolutions()) {
            ArrayList arrayList = new ArrayList(cameraSupportedResolutions);
            arrayList.addAll(getCameraSupportedHighResolutions());
            cameraSupportedResolutions = arrayList;
        }
        List list = (List) mutableConfig.retrieveOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, null);
        if (list != null) {
            cameraSupportedResolutions = getSupportedPrivResolutions(list);
        }
        return selectParentResolutions(cameraSupportedResolutions);
    }

    Pair<Rect, Size> getPreferredChildSizePair(UseCaseConfig<?> useCaseConfig, Rect rect, int i, boolean z) {
        boolean z2;
        if (TransformUtils.is90or270(i)) {
            rect = reverseRect(rect);
            z2 = true;
        } else {
            z2 = false;
        }
        Pair<Rect, Size> preferredChildSizePairInternal = getPreferredChildSizePairInternal(rect, useCaseConfig, z);
        Rect rectReverseRect = (Rect) preferredChildSizePairInternal.first;
        Size sizeReverseSize = (Size) preferredChildSizePairInternal.second;
        if (z2) {
            sizeReverseSize = TransformUtils.reverseSize(sizeReverseSize);
            rectReverseRect = reverseRect(rectReverseRect);
        }
        return new Pair<>(rectReverseRect, sizeReverseSize);
    }

    private Pair<Rect, Size> getPreferredChildSizePairInternal(Rect rect, UseCaseConfig<?> useCaseConfig, boolean z) {
        Size preferredChildSize;
        if (z) {
            preferredChildSize = getPreferredChildSizeForViewport(TransformUtils.rectToSize(rect), useCaseConfig);
        } else {
            Size sizeRectToSize = TransformUtils.rectToSize(rect);
            preferredChildSize = getPreferredChildSize(sizeRectToSize, useCaseConfig);
            rect = getCropRectOfReferenceAspectRatio(sizeRectToSize, preferredChildSize);
        }
        return new Pair<>(rect, preferredChildSize);
    }

    Size getPreferredChildSize(Size size, UseCaseConfig<?> useCaseConfig) {
        List<Size> sortedChildSizes = getSortedChildSizes(useCaseConfig);
        for (Size size2 : sortedChildSizes) {
            if (!isDoubleCropping(size, size2) && !hasUpscaling(size2, size)) {
                return size2;
            }
        }
        for (Size size3 : sortedChildSizes) {
            if (!hasUpscaling(size3, size)) {
                return size3;
            }
        }
        return size;
    }

    Size getPreferredChildSizeForViewport(Size size, UseCaseConfig<?> useCaseConfig) {
        Iterator<Size> it = getSortedChildSizes(useCaseConfig).iterator();
        while (it.hasNext()) {
            Size sizeRectToSize = TransformUtils.rectToSize(getCropRectOfReferenceAspectRatio(it.next(), size));
            if (!hasUpscaling(sizeRectToSize, size)) {
                return sizeRectToSize;
            }
        }
        return size;
    }

    private List<Size> getCameraSupportedResolutions() {
        return this.mCameraInfo.getSupportedResolutions(34);
    }

    private List<Size> getCameraSupportedHighResolutions() {
        return this.mCameraInfo.getSupportedHighResolutions(34);
    }

    private boolean shouldIncludeHighResolutions() {
        boolean z;
        ResolutionSelector resolutionSelector;
        Iterator<UseCaseConfig<?>> it = this.mChildrenConfigs.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            UseCaseConfig<?> next = it.next();
            if (!next.isHighResolutionDisabled(false) && (next instanceof ImageOutputConfig) && (resolutionSelector = ((ImageOutputConfig) next).getResolutionSelector(null)) != null) {
                z = true;
                if (resolutionSelector.getAllowedResolutionMode() == 1) {
                    break;
                }
            }
        }
        return z;
    }

    private List<Size> selectParentResolutions(List<Size> list) {
        ArrayList arrayList = new ArrayList();
        if (needToAddSensorResolutions()) {
            arrayList.addAll(selectParentResolutionsByAspectRatio(this.mSensorAspectRatio, list, false));
        }
        arrayList.addAll(selectParentResolutionsByAspectRatio(this.mFallbackAspectRatio, list, false));
        arrayList.addAll(selectOtherAspectRatioParentResolutionsWithFovPriority(list, false));
        if (arrayList.isEmpty()) {
            Logger.w(TAG, "Failed to find a parent resolution that does not result in double-cropping, this might due to camera not supporting 4:3 and 16:9resolutions or a strict ResolutionSelector settings. Starting resolution selection process with resolutions that might have a smaller FOV.");
            arrayList.addAll(selectOtherAspectRatioParentResolutionsWithFovPriority(list, true));
        }
        Logger.d(TAG, "Parent resolutions: " + arrayList);
        return arrayList;
    }

    private List<Size> selectParentResolutionsByAspectRatio(Rational rational, List<Size> list, boolean z) {
        List<Size> listFilterResolutionsByAspectRatio = filterResolutionsByAspectRatio(rational, list);
        sortInDescendingOrder(listFilterResolutionsByAspectRatio);
        HashSet hashSet = new HashSet(listFilterResolutionsByAspectRatio);
        Iterator<UseCaseConfig<?>> it = this.mChildrenConfigs.iterator();
        while (it.hasNext()) {
            List<Size> sortedChildSizes = getSortedChildSizes(it.next());
            if (!z) {
                sortedChildSizes = filterOutChildSizesCausingDoubleCropping(rational, sortedChildSizes);
            }
            if (sortedChildSizes.isEmpty()) {
                return new ArrayList();
            }
            listFilterResolutionsByAspectRatio = filterOutParentSizeThatIsTooSmall(sortedChildSizes, listFilterResolutionsByAspectRatio);
            hashSet.retainAll(getParentSizesThatAreTooLarge(sortedChildSizes, listFilterResolutionsByAspectRatio));
        }
        ArrayList arrayList = new ArrayList();
        for (Size size : listFilterResolutionsByAspectRatio) {
            if (!hashSet.contains(size)) {
                arrayList.add(size);
            }
        }
        return arrayList;
    }

    private List<Size> selectOtherAspectRatioParentResolutionsWithFovPriority(List<Size> list, boolean z) {
        Map<Rational, List<Size>> mapGroupSizesByAspectRatio = groupSizesByAspectRatio(list);
        ArrayList arrayList = new ArrayList(mapGroupSizesByAspectRatio.keySet());
        sortByFov(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (Rational rational : arrayList) {
            if (!rational.equals(AspectRatioUtil.ASPECT_RATIO_16_9) && !rational.equals(AspectRatioUtil.ASPECT_RATIO_4_3)) {
                arrayList2.addAll(selectParentResolutionsByAspectRatio(rational, (List) Objects.requireNonNull(mapGroupSizesByAspectRatio.get(rational)), z));
            }
        }
        return arrayList2;
    }

    private Map<Rational, List<Size>> groupSizesByAspectRatio(List<Size> list) {
        List arrayList;
        HashMap map = new HashMap();
        map.put(AspectRatioUtil.ASPECT_RATIO_4_3, new ArrayList());
        map.put(AspectRatioUtil.ASPECT_RATIO_16_9, new ArrayList());
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(AspectRatioUtil.ASPECT_RATIO_4_3);
        arrayList2.add(AspectRatioUtil.ASPECT_RATIO_16_9);
        for (Size size : list) {
            if (size.getHeight() > 0) {
                Iterator it = arrayList2.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        arrayList = null;
                        break;
                    }
                    Rational rational = (Rational) it.next();
                    if (AspectRatioUtil.hasMatchingAspectRatio(size, rational)) {
                        arrayList = (List) map.get(rational);
                        break;
                    }
                }
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    Rational rational2 = toRational(size);
                    arrayList2.add(rational2);
                    map.put(rational2, arrayList);
                }
                ((List) Objects.requireNonNull(arrayList)).add(size);
            }
        }
        return map;
    }

    private List<Size> getSortedChildSizes(UseCaseConfig<?> useCaseConfig) {
        if (!this.mChildrenConfigs.contains(useCaseConfig)) {
            throw new IllegalArgumentException("Invalid child config: " + useCaseConfig);
        }
        if (this.mChildSizesCache.containsKey(useCaseConfig)) {
            return (List) Objects.requireNonNull(this.mChildSizesCache.get(useCaseConfig));
        }
        List<Size> listFilterOutChildSizesThatWillNeverBeSelected = filterOutChildSizesThatWillNeverBeSelected(this.mSizeSorter.getSortedSupportedOutputSizes(useCaseConfig));
        this.mChildSizesCache.put(useCaseConfig, listFilterOutChildSizesThatWillNeverBeSelected);
        return listFilterOutChildSizesThatWillNeverBeSelected;
    }

    private boolean needToAddSensorResolutions() {
        Iterator<Size> it = getChildrenRequiredResolutions().iterator();
        while (it.hasNext()) {
            if (!AspectRatioUtil.hasMatchingAspectRatio(it.next(), this.mFallbackAspectRatio)) {
                return true;
            }
        }
        return false;
    }

    private Set<Size> getChildrenRequiredResolutions() {
        HashSet hashSet = new HashSet();
        Iterator<UseCaseConfig<?>> it = this.mChildrenConfigs.iterator();
        while (it.hasNext()) {
            hashSet.addAll(getSortedChildSizes(it.next()));
        }
        return hashSet;
    }

    private List<Size> filterOutChildSizesCausingDoubleCropping(Rational rational, List<Size> list) {
        ArrayList arrayList = new ArrayList();
        for (Size size : list) {
            if (!isDoubleCropping(rational, size)) {
                arrayList.add(size);
            }
        }
        return arrayList;
    }

    private boolean isDoubleCropping(Rational rational, Size size) {
        if (this.mSensorAspectRatio.equals(rational) || AspectRatioUtil.hasMatchingAspectRatio(size, rational)) {
            return false;
        }
        return areCroppingInDifferentDirection(this.mSensorAspectRatio.floatValue(), rational.floatValue(), toRationalWithMod16Considered(size).floatValue());
    }

    private boolean isDoubleCropping(Size size, Size size2) {
        return isDoubleCropping(toRationalWithMod16Considered(size), size2);
    }

    private void sortByFov(List<Rational> list) {
        Collections.sort(list, new CompareAspectRatioByOverlappingAreaToReference(toRational(this.mSensorSize), true));
    }

    static Rect getCropRectOfReferenceAspectRatio(Size size, Size size2) {
        return getCenterCroppedRectangle(toRational(size2), size);
    }

    private static List<Size> getSupportedPrivResolutions(List<Pair<Integer, Size[]>> list) {
        for (Pair<Integer, Size[]> pair : list) {
            if (((Integer) pair.first).equals(34)) {
                return Arrays.asList((Size[]) pair.second);
            }
        }
        return new ArrayList();
    }

    private static Rational getSensorAspectRatio(Size size) {
        Rational rationalFindCloserAspectRatio = findCloserAspectRatio(size);
        Logger.d(TAG, "The closer aspect ratio to the sensor size (" + size + ") is " + rationalFindCloserAspectRatio + ".");
        return rationalFindCloserAspectRatio;
    }

    private static Rational findCloserAspectRatio(Size size) {
        if (((double) size.getWidth()) / ((double) size.getHeight()) > SAME_AREA_WIDTH_HEIGHT_RATIO) {
            return AspectRatioUtil.ASPECT_RATIO_16_9;
        }
        return AspectRatioUtil.ASPECT_RATIO_4_3;
    }

    static Rect reverseRect(Rect rect) {
        return new Rect(rect.top, rect.left, rect.bottom, rect.right);
    }

    private static Rect getCenterCroppedRectangle(Rational rational, Size size) {
        RectF rectF;
        RectF rectF2;
        int width = size.getWidth();
        int height = size.getHeight();
        Rational rational2 = toRational(size);
        if (rational.floatValue() == rational2.floatValue()) {
            rectF2 = new RectF(0.0f, 0.0f, width, height);
        } else {
            if (rational.floatValue() > rational2.floatValue()) {
                float f = width;
                float fFloatValue = f / rational.floatValue();
                float f2 = (height - fFloatValue) / 2.0f;
                rectF = new RectF(0.0f, f2, f, fFloatValue + f2);
            } else {
                float f3 = height;
                float fFloatValue2 = rational.floatValue() * f3;
                float f4 = (width - fFloatValue2) / 2.0f;
                rectF = new RectF(f4, 0.0f, fFloatValue2 + f4, f3);
            }
            rectF2 = rectF;
        }
        Rect rect = new Rect();
        rectF2.round(rect);
        return rect;
    }

    private static Rational getFallbackAspectRatio(Rational rational) {
        if (rational.equals(AspectRatioUtil.ASPECT_RATIO_4_3)) {
            return AspectRatioUtil.ASPECT_RATIO_16_9;
        }
        if (rational.equals(AspectRatioUtil.ASPECT_RATIO_16_9)) {
            return AspectRatioUtil.ASPECT_RATIO_4_3;
        }
        throw new IllegalArgumentException("Invalid sensor aspect-ratio: " + rational);
    }

    static void sortInDescendingOrder(List<Size> list) {
        Collections.sort(list, new CompareSizesByArea(true));
    }

    private static List<Size> removeDuplicates(List<Size> list) {
        return list.isEmpty() ? list : new ArrayList(new LinkedHashSet(list));
    }

    static List<Size> filterResolutionsByAspectRatio(Rational rational, List<Size> list) {
        ArrayList arrayList = new ArrayList();
        for (Size size : list) {
            if (AspectRatioUtil.hasMatchingAspectRatio(size, rational)) {
                arrayList.add(size);
            }
        }
        return arrayList;
    }

    static List<Size> filterOutParentSizeThatIsTooSmall(Collection<Size> collection, List<Size> list) {
        if (collection.isEmpty() || list.isEmpty()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (Size size : list) {
            if (isAnyChildSizeCanBeCroppedOutWithoutUpscalingParent(collection, size)) {
                arrayList.add(size);
            }
        }
        return arrayList;
    }

    private static List<Size> filterOutChildSizesThatWillNeverBeSelected(List<Size> list) {
        Rational rational;
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (Size size : list) {
            Iterator it = map.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    rational = null;
                    break;
                }
                rational = (Rational) it.next();
                if (AspectRatioUtil.hasMatchingAspectRatio(size, rational)) {
                    break;
                }
            }
            if (rational != null) {
                Size size2 = (Size) Objects.requireNonNull((Size) map.get(rational));
                if (size.getHeight() > size2.getHeight() || size.getWidth() > size2.getWidth() || (size.getWidth() == size2.getWidth() && size.getHeight() == size2.getHeight())) {
                }
            } else {
                rational = toRational(size);
            }
            arrayList.add(size);
            map.put(rational, size);
        }
        return arrayList;
    }

    private static boolean isAnyChildSizeCanBeCroppedOutWithoutUpscalingParent(Collection<Size> collection, Size size) {
        Iterator<Size> it = collection.iterator();
        while (it.hasNext()) {
            if (!hasUpscaling(it.next(), size)) {
                return true;
            }
        }
        return false;
    }

    static List<Size> getParentSizesThatAreTooLarge(Collection<Size> collection, List<Size> list) {
        if (collection.isEmpty() || list.isEmpty()) {
            return new ArrayList();
        }
        List<Size> listRemoveDuplicates = removeDuplicates(list);
        ArrayList arrayList = new ArrayList();
        for (Size size : listRemoveDuplicates) {
            if (isAllChildSizesCanBeCroppedOutWithoutUpscalingParent(collection, size)) {
                arrayList.add(size);
            }
        }
        if (!arrayList.isEmpty()) {
            arrayList.remove(arrayList.size() - 1);
        }
        return arrayList;
    }

    private static boolean isAllChildSizesCanBeCroppedOutWithoutUpscalingParent(Collection<Size> collection, Size size) {
        Iterator<Size> it = collection.iterator();
        while (it.hasNext()) {
            if (hasUpscaling(it.next(), size)) {
                return false;
            }
        }
        return true;
    }

    static boolean hasUpscaling(Size size, Size size2) {
        return size.getHeight() > size2.getHeight() || size.getWidth() > size2.getWidth();
    }

    private static Rational toRationalWithMod16Considered(Size size) {
        if (AspectRatioUtil.hasMatchingAspectRatio(size, AspectRatioUtil.ASPECT_RATIO_4_3)) {
            return AspectRatioUtil.ASPECT_RATIO_4_3;
        }
        if (AspectRatioUtil.hasMatchingAspectRatio(size, AspectRatioUtil.ASPECT_RATIO_16_9)) {
            return AspectRatioUtil.ASPECT_RATIO_16_9;
        }
        return toRational(size);
    }

    private static Rational toRational(Size size) {
        return new Rational(size.getWidth(), size.getHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float computeAreaOverlapping(Rational rational, Rational rational2) {
        float fFloatValue = rational.floatValue();
        float fFloatValue2 = rational2.floatValue();
        return fFloatValue > fFloatValue2 ? fFloatValue2 / fFloatValue : fFloatValue / fFloatValue2;
    }

    private static class CompareAspectRatioByOverlappingAreaToReference implements Comparator<Rational> {
        private final Rational mReferenceAspectRatio;
        private final boolean mReverse;

        CompareAspectRatioByOverlappingAreaToReference(Rational rational, boolean z) {
            this.mReferenceAspectRatio = rational;
            this.mReverse = z;
        }

        @Override // java.util.Comparator
        public int compare(Rational rational, Rational rational2) {
            float fComputeAreaOverlapping = ResolutionsMerger.computeAreaOverlapping(rational, this.mReferenceAspectRatio);
            float fComputeAreaOverlapping2 = ResolutionsMerger.computeAreaOverlapping(rational2, this.mReferenceAspectRatio);
            if (this.mReverse) {
                return Float.compare(fComputeAreaOverlapping2, fComputeAreaOverlapping);
            }
            return Float.compare(fComputeAreaOverlapping, fComputeAreaOverlapping2);
        }
    }
}

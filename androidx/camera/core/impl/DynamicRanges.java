package androidx.camera.core.impl;

import androidx.camera.core.DynamicRange;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DynamicRanges.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u001e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0007J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J*\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0007¨\u0006\u0010"}, d2 = {"Landroidx/camera/core/impl/DynamicRanges;", "", "()V", "canMatchBitDepth", "", "dynamicRangeToTest", "Landroidx/camera/core/DynamicRange;", "fullySpecifiedDynamicRange", "canMatchEncoding", "canResolve", "fullySpecifiedDynamicRanges", "", "canResolveUnderSpecifiedTo", "underSpecifiedDynamicRange", "findAllPossibleMatches", "dynamicRangesToTest", "camera-core_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class DynamicRanges {
    public static final DynamicRanges INSTANCE = new DynamicRanges();

    private DynamicRanges() {
    }

    @JvmStatic
    public static final boolean canResolve(DynamicRange dynamicRangeToTest, Set<DynamicRange> fullySpecifiedDynamicRanges) {
        Object next;
        Intrinsics.checkNotNullParameter(dynamicRangeToTest, "dynamicRangeToTest");
        Intrinsics.checkNotNullParameter(fullySpecifiedDynamicRanges, "fullySpecifiedDynamicRanges");
        if (dynamicRangeToTest.isFullySpecified()) {
            return fullySpecifiedDynamicRanges.contains(dynamicRangeToTest);
        }
        Iterator<T> it = fullySpecifiedDynamicRanges.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (INSTANCE.canResolveUnderSpecifiedTo(dynamicRangeToTest, (DynamicRange) next)) {
                break;
            }
        }
        return next != null;
    }

    @JvmStatic
    public static final Set<DynamicRange> findAllPossibleMatches(Set<DynamicRange> dynamicRangesToTest, Set<DynamicRange> fullySpecifiedDynamicRanges) {
        Intrinsics.checkNotNullParameter(dynamicRangesToTest, "dynamicRangesToTest");
        Intrinsics.checkNotNullParameter(fullySpecifiedDynamicRanges, "fullySpecifiedDynamicRanges");
        if (dynamicRangesToTest.isEmpty()) {
            throw new IllegalArgumentException("Candidate dynamic range set must contain at least 1 candidate dynamic range.");
        }
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        for (DynamicRange dynamicRange : dynamicRangesToTest) {
            if (dynamicRange.isFullySpecified()) {
                if (fullySpecifiedDynamicRanges.contains(dynamicRange)) {
                    setCreateSetBuilder.add(dynamicRange);
                }
            } else {
                for (DynamicRange dynamicRange2 : fullySpecifiedDynamicRanges) {
                    if (INSTANCE.canResolveUnderSpecifiedTo(dynamicRange, dynamicRange2)) {
                        setCreateSetBuilder.add(dynamicRange2);
                    }
                }
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    private final boolean canResolveUnderSpecifiedTo(DynamicRange underSpecifiedDynamicRange, DynamicRange fullySpecifiedDynamicRange) {
        return canMatchBitDepth(underSpecifiedDynamicRange, fullySpecifiedDynamicRange) && canMatchEncoding(underSpecifiedDynamicRange, fullySpecifiedDynamicRange);
    }

    private final boolean canMatchBitDepth(DynamicRange dynamicRangeToTest, DynamicRange fullySpecifiedDynamicRange) {
        Preconditions.checkState(fullySpecifiedDynamicRange.isFullySpecified(), "Fully specified range is not actually fully specified.");
        return dynamicRangeToTest.getBitDepth() == 0 || dynamicRangeToTest.getBitDepth() == fullySpecifiedDynamicRange.getBitDepth();
    }

    private final boolean canMatchEncoding(DynamicRange dynamicRangeToTest, DynamicRange fullySpecifiedDynamicRange) {
        Preconditions.checkState(fullySpecifiedDynamicRange.isFullySpecified(), "Fully specified range is not actually fully specified.");
        int encoding = dynamicRangeToTest.getEncoding();
        if (encoding == 0) {
            return true;
        }
        int encoding2 = fullySpecifiedDynamicRange.getEncoding();
        return (encoding == 2 && encoding2 != 1) || encoding == encoding2;
    }
}

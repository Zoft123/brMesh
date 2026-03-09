package com.king.camera.scan.config;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Size;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.core.resolutionselector.AspectRatioStrategy;
import androidx.camera.core.resolutionselector.ResolutionFilter;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import androidx.camera.core.resolutionselector.ResolutionStrategy;
import com.king.logx.LogX;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AdaptiveCameraConfig extends CameraConfig {
    private static final int IMAGE_QUALITY_1080P = 1080;
    private static final int IMAGE_QUALITY_1440P = 1440;
    private static final int IMAGE_QUALITY_480P = 480;
    private static final int IMAGE_QUALITY_720P = 720;
    private int mAnalysisQuality;
    private Size mAnalysisTargetSize;
    private AspectRatioStrategy mAspectRatioStrategy;
    private int mPreviewQuality;
    private Size mPreviewTargetSize;

    public AdaptiveCameraConfig(Context context) {
        initAdaptiveCameraConfig(context);
    }

    private void initAdaptiveCameraConfig(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        LogX.d("displayMetrics: %dx%d", Integer.valueOf(i), Integer.valueOf(i2));
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        LogX.d("processors: %d", Integer.valueOf(iAvailableProcessors));
        if (i < i2) {
            float f = i2 / i;
            if (Math.abs(f - 1.3333334f) < Math.abs(f - 1.7777778f)) {
                this.mAspectRatioStrategy = AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY;
            } else {
                this.mAspectRatioStrategy = AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY;
            }
            if (i >= 1080) {
                this.mPreviewQuality = 1080;
            } else {
                this.mPreviewQuality = Math.max(i, 720);
            }
            int i3 = this.mPreviewQuality;
            this.mPreviewTargetSize = new Size(i3, Math.round(i3 * f));
            if (i >= IMAGE_QUALITY_1440P && iAvailableProcessors >= 8) {
                this.mAnalysisQuality = 1080;
            } else if (i > 720) {
                this.mAnalysisQuality = 720;
            } else {
                this.mAnalysisQuality = 480;
            }
            int i4 = this.mAnalysisQuality;
            this.mAnalysisTargetSize = new Size(i4, Math.round(i4 * f));
            return;
        }
        float f2 = i / i2;
        if (Math.abs(f2 - 1.3333334f) < Math.abs(f2 - 1.7777778f)) {
            this.mAspectRatioStrategy = AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY;
        } else {
            this.mAspectRatioStrategy = AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY;
        }
        if (i2 >= 1080) {
            this.mPreviewQuality = 1080;
        } else {
            this.mPreviewQuality = Math.max(i2, 720);
        }
        this.mPreviewTargetSize = new Size(Math.round(this.mPreviewQuality * f2), this.mPreviewQuality);
        if (i2 >= IMAGE_QUALITY_1440P && iAvailableProcessors >= 8) {
            this.mAnalysisQuality = 1080;
        } else if (i2 > 720) {
            this.mAnalysisQuality = 720;
        } else {
            this.mAnalysisQuality = 480;
        }
        this.mAnalysisTargetSize = new Size(Math.round(this.mAnalysisQuality * f2), this.mAnalysisQuality);
    }

    @Override // com.king.camera.scan.config.CameraConfig
    public CameraSelector options(CameraSelector.Builder builder) {
        return super.options(builder);
    }

    @Override // com.king.camera.scan.config.CameraConfig
    public Preview options(Preview.Builder builder) {
        builder.setResolutionSelector(createPreviewResolutionSelector());
        return super.options(builder);
    }

    @Override // com.king.camera.scan.config.CameraConfig
    public ImageAnalysis options(ImageAnalysis.Builder builder) {
        builder.setResolutionSelector(createAnalysisResolutionSelector());
        return super.options(builder);
    }

    private ResolutionSelector createPreviewResolutionSelector() {
        return new ResolutionSelector.Builder().setAspectRatioStrategy(this.mAspectRatioStrategy).setResolutionStrategy(new ResolutionStrategy(this.mPreviewTargetSize, 1)).setResolutionFilter(new ResolutionFilter() { // from class: com.king.camera.scan.config.AdaptiveCameraConfig$$ExternalSyntheticLambda1
            @Override // androidx.camera.core.resolutionselector.ResolutionFilter
            public final List filter(List list, int i) {
                return this.f$0.m1250x2639c7d4(list, i);
            }
        }).build();
    }

    /* JADX INFO: renamed from: lambda$createPreviewResolutionSelector$0$com-king-camera-scan-config-AdaptiveCameraConfig, reason: not valid java name */
    /* synthetic */ List m1250x2639c7d4(List list, int i) {
        LogX.d("Preview supportedSizes: " + list, new Object[0]);
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Size size = (Size) it.next();
            if (Math.min(size.getWidth(), size.getHeight()) <= this.mPreviewQuality) {
                arrayList.add(size);
            }
        }
        return arrayList;
    }

    private ResolutionSelector createAnalysisResolutionSelector() {
        return new ResolutionSelector.Builder().setAspectRatioStrategy(this.mAspectRatioStrategy).setResolutionStrategy(new ResolutionStrategy(this.mAnalysisTargetSize, 1)).setResolutionFilter(new ResolutionFilter() { // from class: com.king.camera.scan.config.AdaptiveCameraConfig$$ExternalSyntheticLambda0
            @Override // androidx.camera.core.resolutionselector.ResolutionFilter
            public final List filter(List list, int i) {
                return this.f$0.m1249xee92e84d(list, i);
            }
        }).build();
    }

    /* JADX INFO: renamed from: lambda$createAnalysisResolutionSelector$1$com-king-camera-scan-config-AdaptiveCameraConfig, reason: not valid java name */
    /* synthetic */ List m1249xee92e84d(List list, int i) {
        LogX.d("ImageAnalysis supportedSizes: " + list, new Object[0]);
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Size size = (Size) it.next();
            if (Math.min(size.getWidth(), size.getHeight()) <= this.mAnalysisQuality) {
                arrayList.add(size);
            }
        }
        return arrayList;
    }
}

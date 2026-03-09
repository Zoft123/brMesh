package com.king.zxing.analyze;

import android.graphics.Rect;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AreaRectAnalyzer extends ImageAnalyzer {
    boolean isMultiDecode;
    private int mAreaRectHorizontalOffset;
    private float mAreaRectRatio;
    private int mAreaRectVerticalOffset;
    DecodeConfig mDecodeConfig;
    Map<DecodeHintType, ?> mHints;

    public abstract Result analyze(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6);

    public AreaRectAnalyzer(DecodeConfig decodeConfig) {
        this.isMultiDecode = true;
        this.mAreaRectRatio = 0.8f;
        this.mAreaRectHorizontalOffset = 0;
        this.mAreaRectVerticalOffset = 0;
        this.mDecodeConfig = decodeConfig;
        if (decodeConfig != null) {
            this.mHints = decodeConfig.getHints();
            this.isMultiDecode = decodeConfig.isMultiDecode();
            this.mAreaRectRatio = decodeConfig.getAreaRectRatio();
            this.mAreaRectHorizontalOffset = decodeConfig.getAreaRectHorizontalOffset();
            this.mAreaRectVerticalOffset = decodeConfig.getAreaRectVerticalOffset();
            return;
        }
        this.mHints = DecodeFormatManager.DEFAULT_HINTS;
    }

    @Override // com.king.zxing.analyze.ImageAnalyzer
    public Result analyze(byte[] bArr, int i, int i2) {
        AreaRectAnalyzer areaRectAnalyzer;
        byte[] bArr2;
        int i3;
        int i4;
        DecodeConfig decodeConfig = this.mDecodeConfig;
        if (decodeConfig == null) {
            areaRectAnalyzer = this;
            bArr2 = bArr;
            i3 = i;
            i4 = i2;
        } else {
            if (decodeConfig.isFullAreaScan()) {
                return analyze(bArr, i, i2, 0, 0, i, i2);
            }
            areaRectAnalyzer = this;
            bArr2 = bArr;
            i3 = i;
            i4 = i2;
            Rect analyzeAreaRect = areaRectAnalyzer.mDecodeConfig.getAnalyzeAreaRect();
            if (analyzeAreaRect != null) {
                return areaRectAnalyzer.analyze(bArr2, i3, i4, analyzeAreaRect.left, analyzeAreaRect.top, analyzeAreaRect.width(), analyzeAreaRect.height());
            }
        }
        int iMin = (int) (Math.min(i3, i4) * areaRectAnalyzer.mAreaRectRatio);
        return areaRectAnalyzer.analyze(bArr2, i3, i4, ((i3 - iMin) / 2) + areaRectAnalyzer.mAreaRectHorizontalOffset, ((i4 - iMin) / 2) + areaRectAnalyzer.mAreaRectVerticalOffset, iMin, iMin);
    }
}

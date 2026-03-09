package com.king.zxing.analyze;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.logx.LogX;
import com.king.zxing.DecodeConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BarcodeFormatAnalyzer extends AreaRectAnalyzer {
    private Reader mReader;

    public abstract Reader createReader();

    public BarcodeFormatAnalyzer(Map<DecodeHintType, Object> map) {
        this(new DecodeConfig().setHints(map));
    }

    public BarcodeFormatAnalyzer(DecodeConfig decodeConfig) {
        super(decodeConfig);
        initReader();
    }

    private void initReader() {
        this.mReader = createReader();
    }

    @Override // com.king.zxing.analyze.AreaRectAnalyzer
    public Result analyze(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        Result resultDecodeInternal = null;
        if (this.mReader == null) {
            return null;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            PlanarYUVLuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(bArr, i, i2, i3, i4, i5, i6, false);
            resultDecodeInternal = decodeInternal(planarYUVLuminanceSource, this.isMultiDecode);
            if (resultDecodeInternal == null && this.mDecodeConfig != null) {
                if (this.mDecodeConfig.isSupportVerticalCode()) {
                    byte[] bArr2 = new byte[bArr.length];
                    for (int i7 = 0; i7 < i2; i7++) {
                        for (int i8 = 0; i8 < i; i8++) {
                            bArr2[(((i8 * i2) + i2) - i7) - 1] = bArr[(i7 * i) + i8];
                        }
                    }
                    resultDecodeInternal = decodeInternal(new PlanarYUVLuminanceSource(bArr2, i2, i, i4, i3, i6, i5, false), this.mDecodeConfig.isSupportVerticalCodeMultiDecode());
                }
                if (this.mDecodeConfig.isSupportLuminanceInvert()) {
                    resultDecodeInternal = decodeInternal(planarYUVLuminanceSource.invert(), this.mDecodeConfig.isSupportLuminanceInvertMultiDecode());
                }
            }
            if (resultDecodeInternal != null) {
                LogX.d("Found barcode in " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms", new Object[0]);
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.mReader.reset();
            throw th;
        }
        this.mReader.reset();
        return resultDecodeInternal;
    }

    private Result decodeInternal(LuminanceSource luminanceSource, boolean z) {
        Result resultDecode;
        try {
            resultDecode = this.mReader.decode(new BinaryBitmap(new HybridBinarizer(luminanceSource)), this.mHints);
        } catch (Exception unused) {
            resultDecode = null;
        }
        if (!z || resultDecode != null) {
            return resultDecode;
        }
        try {
            return this.mReader.decode(new BinaryBitmap(new GlobalHistogramBinarizer(luminanceSource)), this.mHints);
        } catch (Exception unused2) {
            return resultDecode;
        }
    }
}

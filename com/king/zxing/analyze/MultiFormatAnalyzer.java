package com.king.zxing.analyze;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.logx.LogX;
import com.king.zxing.DecodeConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class MultiFormatAnalyzer extends AreaRectAnalyzer {
    MultiFormatReader mReader;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MultiFormatAnalyzer() {
        this((DecodeConfig) null);
    }

    public MultiFormatAnalyzer(Map<DecodeHintType, Object> map) {
        this(new DecodeConfig().setHints(map));
    }

    public MultiFormatAnalyzer(DecodeConfig decodeConfig) {
        super(decodeConfig);
        initReader();
    }

    private void initReader() {
        this.mReader = new MultiFormatReader();
    }

    @Override // com.king.zxing.analyze.AreaRectAnalyzer
    public Result analyze(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        Result resultDecodeInternal = null;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.mReader.setHints(this.mHints);
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
                if (resultDecodeInternal == null && this.mDecodeConfig.isSupportLuminanceInvert()) {
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
        Result resultDecodeWithState;
        try {
            resultDecodeWithState = this.mReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(luminanceSource)));
        } catch (Exception unused) {
            resultDecodeWithState = null;
        }
        if (!z || resultDecodeWithState != null) {
            return resultDecodeWithState;
        }
        try {
            return this.mReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(luminanceSource)));
        } catch (Exception unused2) {
            return resultDecodeWithState;
        }
    }
}

package com.google.zxing.oned;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class UPCAWriter implements Writer {
    private final EAN13Writer subWriter = new EAN13Writer();

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        return encode(str, barcodeFormat, i, i2, null);
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        if (barcodeFormat != BarcodeFormat.UPC_A) {
            throw new IllegalArgumentException("Can only encode UPC-A, but got " + barcodeFormat);
        }
        return this.subWriter.encode(GlobalVariable.ILLUMINATION + str, BarcodeFormat.EAN_13, i, i2, map);
    }
}

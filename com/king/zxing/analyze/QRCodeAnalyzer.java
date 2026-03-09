package com.king.zxing.analyze;

import com.google.zxing.DecodeHintType;
import com.google.zxing.Reader;
import com.google.zxing.qrcode.QRCodeReader;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class QRCodeAnalyzer extends BarcodeFormatAnalyzer {
    public QRCodeAnalyzer() {
        this(new DecodeConfig().setHints(DecodeFormatManager.QR_CODE_HINTS));
    }

    public QRCodeAnalyzer(Map<DecodeHintType, Object> map) {
        this(new DecodeConfig().setHints(map));
    }

    public QRCodeAnalyzer(DecodeConfig decodeConfig) {
        super(decodeConfig);
    }

    @Override // com.king.zxing.analyze.BarcodeFormatAnalyzer
    public Reader createReader() {
        return new QRCodeReader();
    }
}

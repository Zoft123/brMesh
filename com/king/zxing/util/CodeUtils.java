package com.king.zxing.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.king.logx.LogX;
import com.king.zxing.DecodeFormatManager;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class CodeUtils {
    public static final int DEFAULT_REQ_HEIGHT = 640;
    public static final int DEFAULT_REQ_WIDTH = 480;

    private CodeUtils() {
        throw new AssertionError();
    }

    public static Bitmap createQRCode(String str, int i) {
        return createQRCode(str, i, (Bitmap) null);
    }

    public static Bitmap createQRCode(String str, int i, int i2) {
        return createQRCode(str, i, (Bitmap) null, i2);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap) {
        return createQRCode(str, i, bitmap, ViewCompat.MEASURED_STATE_MASK);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, int i2) {
        return createQRCode(str, i, bitmap, 0.2f, i2);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, float f) {
        HashMap map = new HashMap();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        map.put(EncodeHintType.MARGIN, 1);
        return createQRCode(str, i, bitmap, f, map);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, float f, int i2) {
        HashMap map = new HashMap();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        map.put(EncodeHintType.MARGIN, 1);
        return createQRCode(str, i, bitmap, f, map, i2);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, float f, Map<EncodeHintType, ?> map) {
        return createQRCode(str, i, bitmap, f, map, ViewCompat.MEASURED_STATE_MASK);
    }

    public static Bitmap createQRCode(String str, int i, Bitmap bitmap, float f, Map<EncodeHintType, ?> map, int i2) {
        try {
            BitMatrix bitMatrixEncode = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, i, i, map);
            int[] iArr = new int[i * i];
            for (int i3 = 0; i3 < i; i3++) {
                for (int i4 = 0; i4 < i; i4++) {
                    if (bitMatrixEncode.get(i4, i3)) {
                        iArr[(i3 * i) + i4] = i2;
                    } else {
                        iArr[(i3 * i) + i4] = -1;
                    }
                }
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.setPixels(iArr, 0, i, 0, 0, i, i);
            return bitmap != null ? addLogo(bitmapCreateBitmap, bitmap, f) : bitmapCreateBitmap;
        } catch (Exception e) {
            LogX.w(e);
            return null;
        }
    }

    private static Bitmap addLogo(Bitmap bitmap, Bitmap bitmap2, float f) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap2 != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int width2 = bitmap2.getWidth();
            int height2 = bitmap2.getHeight();
            if (width == 0 || height == 0) {
                return null;
            }
            if (width2 != 0 && height2 != 0) {
                float f2 = width;
                float f3 = (f * f2) / width2;
                try {
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                    canvas.scale(f3, f3, f2 / 2.0f, height / 2.0f);
                    canvas.drawBitmap(bitmap2, (width - width2) / 2.0f, (height - height2) / 2.0f, (Paint) null);
                    canvas.save();
                    canvas.restore();
                    return bitmapCreateBitmap;
                } catch (Exception e) {
                    LogX.w(e);
                    return null;
                }
            }
        }
        return bitmap;
    }

    public static String parseQRCode(String str) {
        Result qRCodeResult = parseQRCodeResult(str);
        if (qRCodeResult != null) {
            return qRCodeResult.getText();
        }
        return null;
    }

    public static Result parseQRCodeResult(String str) {
        return parseQRCodeResult(str, DEFAULT_REQ_WIDTH, DEFAULT_REQ_HEIGHT);
    }

    public static Result parseQRCodeResult(String str, int i, int i2) {
        return parseCodeResult(str, i, i2, DecodeFormatManager.QR_CODE_HINTS);
    }

    public static String parseCode(String str) {
        return parseCode(str, DecodeFormatManager.ALL_HINTS);
    }

    public static String parseCode(String str, Map<DecodeHintType, Object> map) {
        Result codeResult = parseCodeResult(str, map);
        if (codeResult != null) {
            return codeResult.getText();
        }
        return null;
    }

    public static String parseQRCode(Bitmap bitmap) {
        return parseCode(bitmap, DecodeFormatManager.QR_CODE_HINTS);
    }

    public static String parseCode(Bitmap bitmap) {
        return parseCode(bitmap, DecodeFormatManager.ALL_HINTS);
    }

    public static String parseCode(Bitmap bitmap, Map<DecodeHintType, Object> map) {
        Result codeResult = parseCodeResult(bitmap, map);
        if (codeResult != null) {
            return codeResult.getText();
        }
        return null;
    }

    public static Result parseCodeResult(String str, Map<DecodeHintType, Object> map) {
        return parseCodeResult(str, DEFAULT_REQ_WIDTH, DEFAULT_REQ_HEIGHT, map);
    }

    public static Result parseCodeResult(String str, int i, int i2, Map<DecodeHintType, Object> map) {
        return parseCodeResult(compressBitmap(str, i, i2), map);
    }

    public static Result parseCodeResult(Bitmap bitmap) {
        return parseCodeResult(getRGBLuminanceSource(bitmap), DecodeFormatManager.ALL_HINTS);
    }

    public static Result parseCodeResult(Bitmap bitmap, Map<DecodeHintType, Object> map) {
        return parseCodeResult(getRGBLuminanceSource(bitmap), map);
    }

    public static Result parseCodeResult(LuminanceSource luminanceSource, Map<DecodeHintType, Object> map) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        Result resultDecodeInternal = null;
        try {
            multiFormatReader.setHints(map);
            if (luminanceSource != null) {
                resultDecodeInternal = decodeInternal(multiFormatReader, luminanceSource);
                if (resultDecodeInternal == null) {
                    resultDecodeInternal = decodeInternal(multiFormatReader, luminanceSource.invert());
                }
                if (resultDecodeInternal == null && luminanceSource.isRotateSupported()) {
                    resultDecodeInternal = decodeInternal(multiFormatReader, luminanceSource.rotateCounterClockwise());
                }
            }
            return resultDecodeInternal;
        } catch (Exception e) {
            LogX.w(e);
            return null;
        } finally {
            multiFormatReader.reset();
        }
    }

    private static Result decodeInternal(MultiFormatReader multiFormatReader, LuminanceSource luminanceSource) {
        Result resultDecodeWithState;
        try {
            resultDecodeWithState = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(luminanceSource)));
        } catch (Exception unused) {
            resultDecodeWithState = null;
        }
        if (resultDecodeWithState != null) {
            return resultDecodeWithState;
        }
        try {
            return multiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(luminanceSource)));
        } catch (Exception unused2) {
            return resultDecodeWithState;
        }
    }

    private static Bitmap compressBitmap(String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            options.inSampleSize = getSampleSize(i, i2, options);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(str, options);
        }
        return BitmapFactory.decodeFile(str);
    }

    private static int getSampleSize(int i, int i2, BitmapFactory.Options options) {
        float f = options.outWidth;
        float f2 = options.outHeight;
        float f3 = i;
        float f4 = i2;
        int iMax = Math.max(f > f3 ? (int) (f / f3) : 1, f2 > f4 ? (int) (f2 / f4) : 1);
        if (iMax <= 0) {
            return 1;
        }
        return iMax;
    }

    private static RGBLuminanceSource getRGBLuminanceSource(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return new RGBLuminanceSource(width, height, iArr);
    }

    public static Bitmap createBarCode(String str, int i, int i2) {
        return createBarCode(str, BarcodeFormat.CODE_128, i, i2, (Map<EncodeHintType, ?>) null);
    }

    public static Bitmap createBarCode(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        return createBarCode(str, barcodeFormat, i, i2, (Map<EncodeHintType, ?>) null);
    }

    public static Bitmap createBarCode(String str, int i, int i2, boolean z) {
        return createBarCode(str, BarcodeFormat.CODE_128, i, i2, null, z, 40, ViewCompat.MEASURED_STATE_MASK);
    }

    public static Bitmap createBarCode(String str, int i, int i2, boolean z, int i3) {
        return createBarCode(str, BarcodeFormat.CODE_128, i, i2, null, z, 40, i3);
    }

    public static Bitmap createBarCode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        return createBarCode(str, barcodeFormat, i, i2, map, false, 40, ViewCompat.MEASURED_STATE_MASK);
    }

    public static Bitmap createBarCode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map, boolean z) {
        return createBarCode(str, barcodeFormat, i, i2, map, z, 40, ViewCompat.MEASURED_STATE_MASK);
    }

    public static Bitmap createBarCode(String str, BarcodeFormat barcodeFormat, int i, int i2, boolean z, int i3) {
        return createBarCode(str, barcodeFormat, i, i2, null, z, 40, i3);
    }

    public static Bitmap createBarCode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map, boolean z, int i3) {
        return createBarCode(str, barcodeFormat, i, i2, map, z, 40, i3);
    }

    public static Bitmap createBarCode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map, boolean z, int i3, int i4) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            BitMatrix bitMatrixEncode = new MultiFormatWriter().encode(str, barcodeFormat, i, i2, map);
            int width = bitMatrixEncode.getWidth();
            int height = bitMatrixEncode.getHeight();
            int[] iArr = new int[width * height];
            for (int i5 = 0; i5 < height; i5++) {
                int i6 = i5 * width;
                for (int i7 = 0; i7 < width; i7++) {
                    iArr[i6 + i7] = bitMatrixEncode.get(i7, i5) ? i4 : -1;
                }
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
            return z ? addCode(bitmapCreateBitmap, str, i3, i4, i3 / 2) : bitmapCreateBitmap;
        } catch (Exception e) {
            LogX.w(e);
            return null;
        }
    }

    private static Bitmap addCode(Bitmap bitmap, String str, int i, int i2, int i3) {
        if (bitmap == null) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > 0 && height > 0) {
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height + i + (i3 * 2), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                TextPaint textPaint = new TextPaint();
                float f = i;
                textPaint.setTextSize(f);
                textPaint.setColor(i2);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(str, width / 2.0f, height + (f / 2.0f) + i3, textPaint);
                canvas.save();
                canvas.restore();
                return bitmapCreateBitmap;
            } catch (Exception e) {
                LogX.w(e);
            }
        }
        return null;
    }
}

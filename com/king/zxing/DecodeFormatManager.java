package com.king.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class DecodeFormatManager {
    public static final Map<DecodeHintType, Object> ALL_HINTS;
    public static final Map<DecodeHintType, Object> CODE_128_HINTS;
    public static final Map<DecodeHintType, Object> DEFAULT_HINTS;
    public static final Map<DecodeHintType, Object> ONE_DIMENSIONAL_HINTS;
    public static final Map<DecodeHintType, Object> QR_CODE_HINTS;
    public static final Map<DecodeHintType, Object> TWO_DIMENSIONAL_HINTS;

    static {
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        ALL_HINTS = enumMap;
        CODE_128_HINTS = createDecodeHint(BarcodeFormat.CODE_128);
        QR_CODE_HINTS = createDecodeHint(BarcodeFormat.QR_CODE);
        EnumMap enumMap2 = new EnumMap(DecodeHintType.class);
        ONE_DIMENSIONAL_HINTS = enumMap2;
        EnumMap enumMap3 = new EnumMap(DecodeHintType.class);
        TWO_DIMENSIONAL_HINTS = enumMap3;
        EnumMap enumMap4 = new EnumMap(DecodeHintType.class);
        DEFAULT_HINTS = enumMap4;
        addDecodeHintTypes(enumMap, getAllFormats());
        addDecodeHintTypes(enumMap2, getOneDimensionalFormats());
        addDecodeHintTypes(enumMap3, getTwoDimensionalFormats());
        addDecodeHintTypes(enumMap4, getDefaultFormats());
    }

    private DecodeFormatManager() {
        throw new AssertionError();
    }

    private static List<BarcodeFormat> getAllFormats() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.AZTEC);
        arrayList.add(BarcodeFormat.CODABAR);
        arrayList.add(BarcodeFormat.CODE_39);
        arrayList.add(BarcodeFormat.CODE_93);
        arrayList.add(BarcodeFormat.CODE_128);
        arrayList.add(BarcodeFormat.DATA_MATRIX);
        arrayList.add(BarcodeFormat.EAN_8);
        arrayList.add(BarcodeFormat.EAN_13);
        arrayList.add(BarcodeFormat.ITF);
        arrayList.add(BarcodeFormat.MAXICODE);
        arrayList.add(BarcodeFormat.PDF_417);
        arrayList.add(BarcodeFormat.QR_CODE);
        arrayList.add(BarcodeFormat.RSS_14);
        arrayList.add(BarcodeFormat.RSS_EXPANDED);
        arrayList.add(BarcodeFormat.UPC_A);
        arrayList.add(BarcodeFormat.UPC_E);
        arrayList.add(BarcodeFormat.UPC_EAN_EXTENSION);
        return arrayList;
    }

    private static List<BarcodeFormat> getOneDimensionalFormats() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.CODABAR);
        arrayList.add(BarcodeFormat.CODE_39);
        arrayList.add(BarcodeFormat.CODE_93);
        arrayList.add(BarcodeFormat.CODE_128);
        arrayList.add(BarcodeFormat.EAN_8);
        arrayList.add(BarcodeFormat.EAN_13);
        arrayList.add(BarcodeFormat.ITF);
        arrayList.add(BarcodeFormat.RSS_14);
        arrayList.add(BarcodeFormat.RSS_EXPANDED);
        arrayList.add(BarcodeFormat.UPC_A);
        arrayList.add(BarcodeFormat.UPC_E);
        arrayList.add(BarcodeFormat.UPC_EAN_EXTENSION);
        return arrayList;
    }

    private static List<BarcodeFormat> getTwoDimensionalFormats() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.AZTEC);
        arrayList.add(BarcodeFormat.DATA_MATRIX);
        arrayList.add(BarcodeFormat.MAXICODE);
        arrayList.add(BarcodeFormat.PDF_417);
        arrayList.add(BarcodeFormat.QR_CODE);
        return arrayList;
    }

    private static List<BarcodeFormat> getDefaultFormats() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.QR_CODE);
        arrayList.add(BarcodeFormat.UPC_A);
        arrayList.add(BarcodeFormat.EAN_13);
        arrayList.add(BarcodeFormat.CODE_128);
        return arrayList;
    }

    public static Map<DecodeHintType, Object> createDecodeHints(BarcodeFormat... barcodeFormatArr) {
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        addDecodeHintTypes(enumMap, Arrays.asList(barcodeFormatArr));
        return enumMap;
    }

    public static Map<DecodeHintType, Object> createDecodeHint(BarcodeFormat barcodeFormat) {
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        addDecodeHintTypes(enumMap, Collections.singletonList(barcodeFormat));
        return enumMap;
    }

    private static void addDecodeHintTypes(Map<DecodeHintType, Object> map, List<BarcodeFormat> list) {
        map.put(DecodeHintType.POSSIBLE_FORMATS, list);
        map.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        map.put(DecodeHintType.CHARACTER_SET, "UTF-8");
    }
}

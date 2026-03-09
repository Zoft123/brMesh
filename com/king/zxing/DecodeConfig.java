package com.king.zxing;

import android.graphics.Rect;
import com.google.zxing.DecodeHintType;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class DecodeConfig {
    public static final float DEFAULT_AREA_RECT_RATIO = 0.8f;
    private Rect analyzeAreaRect;
    private int areaRectHorizontalOffset;
    private int areaRectVerticalOffset;
    private boolean isSupportLuminanceInvert;
    private boolean isSupportLuminanceInvertMultiDecode;
    private boolean isSupportVerticalCode;
    private boolean isSupportVerticalCodeMultiDecode;
    private Map<DecodeHintType, Object> hints = DecodeFormatManager.DEFAULT_HINTS;
    private boolean isMultiDecode = true;
    private boolean isFullAreaScan = false;
    private float areaRectRatio = 0.8f;

    public Map<DecodeHintType, Object> getHints() {
        return this.hints;
    }

    public DecodeConfig setHints(Map<DecodeHintType, Object> map) {
        this.hints = map;
        return this;
    }

    public boolean isSupportLuminanceInvert() {
        return this.isSupportLuminanceInvert;
    }

    public DecodeConfig setSupportLuminanceInvert(boolean z) {
        this.isSupportLuminanceInvert = z;
        return this;
    }

    public boolean isSupportVerticalCode() {
        return this.isSupportVerticalCode;
    }

    public DecodeConfig setSupportVerticalCode(boolean z) {
        this.isSupportVerticalCode = z;
        return this;
    }

    public boolean isMultiDecode() {
        return this.isMultiDecode;
    }

    public DecodeConfig setMultiDecode(boolean z) {
        this.isMultiDecode = z;
        return this;
    }

    public boolean isSupportLuminanceInvertMultiDecode() {
        return this.isSupportLuminanceInvertMultiDecode;
    }

    public DecodeConfig setSupportLuminanceInvertMultiDecode(boolean z) {
        this.isSupportLuminanceInvertMultiDecode = z;
        return this;
    }

    public boolean isSupportVerticalCodeMultiDecode() {
        return this.isSupportVerticalCodeMultiDecode;
    }

    public DecodeConfig setSupportVerticalCodeMultiDecode(boolean z) {
        this.isSupportVerticalCodeMultiDecode = z;
        return this;
    }

    public Rect getAnalyzeAreaRect() {
        return this.analyzeAreaRect;
    }

    public DecodeConfig setAnalyzeAreaRect(Rect rect) {
        this.analyzeAreaRect = rect;
        return this;
    }

    public boolean isFullAreaScan() {
        return this.isFullAreaScan;
    }

    public DecodeConfig setFullAreaScan(boolean z) {
        this.isFullAreaScan = z;
        return this;
    }

    public float getAreaRectRatio() {
        return this.areaRectRatio;
    }

    public DecodeConfig setAreaRectRatio(float f) {
        this.areaRectRatio = f;
        return this;
    }

    public int getAreaRectVerticalOffset() {
        return this.areaRectVerticalOffset;
    }

    public DecodeConfig setAreaRectVerticalOffset(int i) {
        this.areaRectVerticalOffset = i;
        return this;
    }

    public int getAreaRectHorizontalOffset() {
        return this.areaRectHorizontalOffset;
    }

    public DecodeConfig setAreaRectHorizontalOffset(int i) {
        this.areaRectHorizontalOffset = i;
        return this;
    }

    public String toString() {
        return "DecodeConfig{hints=" + this.hints + ", isMultiDecode=" + this.isMultiDecode + ", isSupportLuminanceInvert=" + this.isSupportLuminanceInvert + ", isSupportLuminanceInvertMultiDecode=" + this.isSupportLuminanceInvertMultiDecode + ", isSupportVerticalCode=" + this.isSupportVerticalCode + ", isSupportVerticalCodeMultiDecode=" + this.isSupportVerticalCodeMultiDecode + ", analyzeAreaRect=" + this.analyzeAreaRect + ", isFullAreaScan=" + this.isFullAreaScan + ", areaRectRatio=" + this.areaRectRatio + ", areaRectVerticalOffset=" + this.areaRectVerticalOffset + ", areaRectHorizontalOffset=" + this.areaRectHorizontalOffset + '}';
    }
}

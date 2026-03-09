package androidx.camera.core;

import android.util.Range;
import android.util.Rational;

/* JADX INFO: loaded from: classes.dex */
public interface ExposureState {
    int getExposureCompensationIndex();

    Range<Integer> getExposureCompensationRange();

    Rational getExposureCompensationStep();

    boolean isExposureCompensationSupported();
}

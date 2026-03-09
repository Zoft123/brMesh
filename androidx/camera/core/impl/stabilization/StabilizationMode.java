package androidx.camera.core.impl.stabilization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class StabilizationMode {
    public static final int OFF = 1;
    public static final int ON = 2;
    public static final int UNSPECIFIED = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private StabilizationMode() {
    }
}

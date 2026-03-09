package androidx.camera.camera2.internal.compat.workaround;

import androidx.camera.camera2.internal.compat.quirk.Preview3AThreadCrashQuirk;
import androidx.camera.core.impl.Quirks;

/* JADX INFO: loaded from: classes.dex */
public class SessionResetPolicy {
    private final boolean mNeedAbortCapture;

    public SessionResetPolicy(Quirks quirks) {
        this.mNeedAbortCapture = quirks.contains(Preview3AThreadCrashQuirk.class);
    }

    public boolean needAbortCapture() {
        return this.mNeedAbortCapture;
    }
}

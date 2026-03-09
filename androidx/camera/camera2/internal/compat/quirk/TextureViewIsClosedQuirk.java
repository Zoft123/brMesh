package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;

/* JADX INFO: loaded from: classes.dex */
public class TextureViewIsClosedQuirk implements Quirk {
    static boolean load() {
        return Build.VERSION.SDK_INT <= 23;
    }
}

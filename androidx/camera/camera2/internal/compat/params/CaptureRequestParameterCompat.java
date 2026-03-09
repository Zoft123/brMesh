package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.core.impl.Config;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CaptureRequestParameterCompat.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Landroidx/camera/camera2/internal/compat/params/CaptureRequestParameterCompat;", "", "()V", "setSettingsOverrideZoom", "", "options", "Landroidx/camera/camera2/impl/Camera2ImplConfig$Builder;", "priority", "Landroidx/camera/core/impl/Config$OptionPriority;", "camera-camera2_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CaptureRequestParameterCompat {
    public static final CaptureRequestParameterCompat INSTANCE = new CaptureRequestParameterCompat();

    private CaptureRequestParameterCompat() {
    }

    @JvmStatic
    public static final void setSettingsOverrideZoom(Camera2ImplConfig.Builder options, Config.OptionPriority priority) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(priority, "priority");
        if (Build.VERSION.SDK_INT >= 34) {
            options.setCaptureRequestOptionWithPriority(CaptureRequest.CONTROL_SETTINGS_OVERRIDE, 1, priority);
        }
    }
}

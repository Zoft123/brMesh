package androidx.camera.core;

import androidx.camera.core.impl.Identifier;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CameraFilter {
    public static final Identifier DEFAULT_ID = Identifier.create(new Object());

    List<CameraInfo> filter(List<CameraInfo> list);

    Identifier getIdentifier();

    /* JADX INFO: renamed from: androidx.camera.core.CameraFilter$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }
}

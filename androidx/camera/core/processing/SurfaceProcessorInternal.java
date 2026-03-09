package androidx.camera.core.processing;

import androidx.camera.core.SurfaceProcessor;
import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
public interface SurfaceProcessorInternal extends SurfaceProcessor {
    void release();

    ListenableFuture<Void> snapshot(int i, int i2);

    /* JADX INFO: renamed from: androidx.camera.core.processing.SurfaceProcessorInternal$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }
}

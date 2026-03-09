package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import androidx.camera.camera2.internal.SynchronizedCaptureSession;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.SessionConfig;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
interface CaptureSessionInterface {
    void cancelIssuedCaptureRequests();

    void close();

    List<CaptureConfig> getCaptureConfigs();

    SessionConfig getSessionConfig();

    boolean isInOpenState();

    void issueCaptureRequests(List<CaptureConfig> list);

    ListenableFuture<Void> open(SessionConfig sessionConfig, CameraDevice cameraDevice, SynchronizedCaptureSession.Opener opener);

    ListenableFuture<Void> release(boolean z);

    void setSessionConfig(SessionConfig sessionConfig);

    void setStreamUseCaseMap(Map<DeferrableSurface, Long> map);
}

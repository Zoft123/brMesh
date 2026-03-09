package androidx.camera.video;

import androidx.camera.core.CameraInfo;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.Timebase;

/* JADX INFO: loaded from: classes.dex */
public interface VideoOutput {

    public enum SourceState {
        ACTIVE_STREAMING,
        ACTIVE_NON_STREAMING,
        INACTIVE
    }

    VideoCapabilities getMediaCapabilities(CameraInfo cameraInfo);

    Observable<MediaSpec> getMediaSpec();

    Observable<StreamInfo> getStreamInfo();

    Observable<Boolean> isSourceStreamRequired();

    void onSourceStateChanged(SourceState sourceState);

    void onSurfaceRequested(SurfaceRequest surfaceRequest);

    void onSurfaceRequested(SurfaceRequest surfaceRequest, Timebase timebase);

    /* JADX INFO: renamed from: androidx.camera.video.VideoOutput$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onSourceStateChanged(VideoOutput _this, SourceState sourceState) {
        }
    }
}

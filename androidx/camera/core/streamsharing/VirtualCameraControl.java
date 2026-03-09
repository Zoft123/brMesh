package androidx.camera.core.streamsharing;

import androidx.camera.core.imagecapture.CameraCapturePipeline;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.ForwardingCameraControl;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.streamsharing.StreamSharing;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class VirtualCameraControl extends ForwardingCameraControl {
    private static final int DEFAULT_JPEG_QUALITY = 100;
    private static final int DEFAULT_ROTATION_DEGREES = 0;
    private final StreamSharing.Control mStreamSharingControl;

    VirtualCameraControl(CameraControlInternal cameraControlInternal, StreamSharing.Control control) {
        super(cameraControlInternal);
        this.mStreamSharingControl = control;
    }

    @Override // androidx.camera.core.impl.ForwardingCameraControl, androidx.camera.core.impl.CameraControlInternal
    public ListenableFuture<List<Void>> submitStillCaptureRequests(final List<CaptureConfig> list, int i, int i2) {
        Preconditions.checkArgument(list.size() == 1, "Only support one capture config.");
        final ListenableFuture<CameraCapturePipeline> cameraCapturePipelineAsync = getCameraCapturePipelineAsync(i, i2);
        return Futures.allAsList(Collections.singletonList(FutureChain.from(cameraCapturePipelineAsync).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.streamsharing.VirtualCameraControl$$ExternalSyntheticLambda0
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return ((CameraCapturePipeline) cameraCapturePipelineAsync.get()).invokePreCapture();
            }
        }, CameraXExecutors.directExecutor()).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.streamsharing.VirtualCameraControl$$ExternalSyntheticLambda1
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f$0.m253x49c89588(list, (Void) obj);
            }
        }, CameraXExecutors.directExecutor()).transformAsync(new AsyncFunction() { // from class: androidx.camera.core.streamsharing.VirtualCameraControl$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return ((CameraCapturePipeline) cameraCapturePipelineAsync.get()).invokePostCapture();
            }
        }, CameraXExecutors.directExecutor())));
    }

    /* JADX INFO: renamed from: lambda$submitStillCaptureRequests$1$androidx-camera-core-streamsharing-VirtualCameraControl, reason: not valid java name */
    /* synthetic */ ListenableFuture m253x49c89588(List list, Void r4) throws Exception {
        return this.mStreamSharingControl.jpegSnapshot(getJpegQuality((CaptureConfig) list.get(0)), getRotationDegrees((CaptureConfig) list.get(0)));
    }

    private int getJpegQuality(CaptureConfig captureConfig) {
        return ((Integer) Objects.requireNonNull((Integer) captureConfig.getImplementationOptions().retrieveOption(CaptureConfig.OPTION_JPEG_QUALITY, 100))).intValue();
    }

    private int getRotationDegrees(CaptureConfig captureConfig) {
        return ((Integer) Objects.requireNonNull((Integer) captureConfig.getImplementationOptions().retrieveOption(CaptureConfig.OPTION_ROTATION, 0))).intValue();
    }
}

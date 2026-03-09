package androidx.camera.camera2.interop;

import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.interop.CaptureRequestOptions;
import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2CameraControl {
    private final Camera2CameraControlImpl mCamera2CameraControlImpl;
    CallbackToFutureAdapter.Completer<Void> mCompleter;
    final Executor mExecutor;
    private boolean mIsActive = false;
    private boolean mPendingUpdate = false;
    final Object mLock = new Object();
    private Camera2ImplConfig.Builder mBuilder = new Camera2ImplConfig.Builder();

    public Camera2CameraControl(Camera2CameraControlImpl camera2CameraControlImpl, Executor executor) {
        this.mCamera2CameraControlImpl = camera2CameraControlImpl;
        this.mExecutor = executor;
    }

    public static Camera2CameraControl from(CameraControl cameraControl) {
        CameraControlInternal implementation = ((CameraControlInternal) cameraControl).getImplementation();
        Preconditions.checkArgument(implementation instanceof Camera2CameraControlImpl, "CameraControl doesn't contain Camera2 implementation.");
        return ((Camera2CameraControlImpl) implementation).getCamera2CameraControl();
    }

    public ListenableFuture<Void> setCaptureRequestOptions(CaptureRequestOptions captureRequestOptions) {
        clearCaptureRequestOptionsInternal();
        addCaptureRequestOptionsInternal(captureRequestOptions);
        return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda5
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m160x121854a6(completer);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$setCaptureRequestOptions$1$androidx-camera-camera2-interop-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ Object m160x121854a6(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m159xf7fcd607(completer);
            }
        });
        return "setCaptureRequestOptions";
    }

    public ListenableFuture<Void> addCaptureRequestOptions(CaptureRequestOptions captureRequestOptions) {
        addCaptureRequestOptionsInternal(captureRequestOptions);
        return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m155x9f862fa3(completer);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$addCaptureRequestOptions$3$androidx-camera-camera2-interop-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ Object m155x9f862fa3(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m154x856ab104(completer);
            }
        });
        return "addCaptureRequestOptions";
    }

    public CaptureRequestOptions getCaptureRequestOptions() {
        CaptureRequestOptions captureRequestOptionsBuild;
        synchronized (this.mLock) {
            captureRequestOptionsBuild = CaptureRequestOptions.Builder.from(this.mBuilder.build()).build();
        }
        return captureRequestOptionsBuild;
    }

    public ListenableFuture<Void> clearCaptureRequestOptions() {
        clearCaptureRequestOptionsInternal();
        return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda6
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m157x5f672bcd(completer);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$clearCaptureRequestOptions$5$androidx-camera-camera2-interop-Camera2CameraControl, reason: not valid java name */
    /* synthetic */ Object m157x5f672bcd(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m156x454bad2e(completer);
            }
        });
        return "clearCaptureRequestOptions";
    }

    public Camera2ImplConfig getCamera2ImplConfig() {
        Camera2ImplConfig camera2ImplConfigBuild;
        synchronized (this.mLock) {
            camera2ImplConfigBuild = this.mBuilder.build();
        }
        return camera2ImplConfigBuild;
    }

    public void applyOptionsToBuilder(Camera2ImplConfig.Builder builder) {
        synchronized (this.mLock) {
            builder.insertAllOptions(this.mBuilder.getMutableConfig(), Config.OptionPriority.ALWAYS_OVERRIDE);
        }
    }

    private void addCaptureRequestOptionsInternal(CaptureRequestOptions captureRequestOptions) {
        synchronized (this.mLock) {
            this.mBuilder.insertAllOptions(captureRequestOptions);
        }
    }

    private void clearCaptureRequestOptionsInternal() {
        synchronized (this.mLock) {
            this.mBuilder = new Camera2ImplConfig.Builder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: updateConfig, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void m159xf7fcd607(CallbackToFutureAdapter.Completer<Void> completer) {
        this.mPendingUpdate = true;
        failInFlightUpdate(new CameraControl.OperationCanceledException("Camera2CameraControl was updated with new options."));
        this.mCompleter = completer;
        if (this.mIsActive) {
            updateSession();
        }
    }

    private void updateSession() {
        this.mCamera2CameraControlImpl.updateSessionConfigAsync().addListener(new Runnable() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.completeInFlightUpdate();
            }
        }, this.mExecutor);
        this.mPendingUpdate = false;
    }

    public void setActive(final boolean z) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.interop.Camera2CameraControl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m158xdc333ae6(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setActiveInternal, reason: merged with bridge method [inline-methods] */
    public void m158xdc333ae6(boolean z) {
        if (this.mIsActive == z) {
            return;
        }
        this.mIsActive = z;
        if (z) {
            if (this.mPendingUpdate) {
                updateSession();
                return;
            }
            return;
        }
        failInFlightUpdate(new CameraControl.OperationCanceledException("The camera control has became inactive."));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeInFlightUpdate() {
        CallbackToFutureAdapter.Completer<Void> completer = this.mCompleter;
        if (completer != null) {
            completer.set(null);
            this.mCompleter = null;
        }
    }

    private void failInFlightUpdate(Exception exc) {
        CallbackToFutureAdapter.Completer<Void> completer = this.mCompleter;
        if (completer != null) {
            if (exc == null) {
                exc = new Exception("Camera2CameraControl failed with unknown error.");
            }
            completer.setException(exc);
            this.mCompleter = null;
        }
    }
}

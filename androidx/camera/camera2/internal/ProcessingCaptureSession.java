package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.arch.core.util.Function;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.internal.SynchronizedCaptureSession;
import androidx.camera.camera2.internal.compat.params.DynamicRangesCompat;
import androidx.camera.camera2.internal.compat.quirk.CaptureSessionShouldUseMrirQuirk;
import androidx.camera.camera2.internal.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.interop.CaptureRequestOptions;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurfaces;
import androidx.camera.core.impl.OutputSurface;
import androidx.camera.core.impl.OutputSurfaceConfiguration;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionProcessor;
import androidx.camera.core.impl.SessionProcessorSurface;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.streamsharing.StreamSharing;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
final class ProcessingCaptureSession implements CaptureSessionInterface {
    private static final String TAG = "ProcessingCaptureSession";
    private static final long TIMEOUT_GET_SURFACE_IN_MS = 5000;
    private static List<DeferrableSurface> sHeldProcessorSurfaces = new ArrayList();
    private static int sNextInstanceId = 0;
    private final Camera2CameraInfoImpl mCamera2CameraInfoImpl;
    private final CaptureSession mCaptureSession;
    final Executor mExecutor;
    private int mInstanceId;
    private SessionConfig mProcessorSessionConfig;
    private ProcessorState mProcessorState;
    private Camera2RequestProcessor mRequestProcessor;
    private final ScheduledExecutorService mScheduledExecutorService;
    private SessionConfig mSessionConfig;
    private final SessionProcessor mSessionProcessor;
    private final SessionProcessorCaptureCallback mSessionProcessorCaptureCallback;
    private List<DeferrableSurface> mOutputSurfaces = new ArrayList();
    private volatile List<CaptureConfig> mPendingCaptureConfigs = null;
    private CaptureRequestOptions mSessionOptions = new CaptureRequestOptions.Builder().build();
    private CaptureRequestOptions mStillCaptureOptions = new CaptureRequestOptions.Builder().build();

    private enum ProcessorState {
        UNINITIALIZED,
        SESSION_INITIALIZED,
        ON_CAPTURE_SESSION_STARTED,
        ON_CAPTURE_SESSION_ENDED,
        DE_INITIALIZED
    }

    private boolean isTemplateTypeForStillCapture(int i) {
        return i == 2 || i == 4;
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void setStreamUseCaseMap(Map<DeferrableSurface, Long> map) {
    }

    ProcessingCaptureSession(SessionProcessor sessionProcessor, Camera2CameraInfoImpl camera2CameraInfoImpl, DynamicRangesCompat dynamicRangesCompat, Executor executor, ScheduledExecutorService scheduledExecutorService) {
        this.mInstanceId = 0;
        this.mCaptureSession = new CaptureSession(dynamicRangesCompat, DeviceQuirks.get(CaptureSessionShouldUseMrirQuirk.class) != null);
        this.mSessionProcessor = sessionProcessor;
        this.mCamera2CameraInfoImpl = camera2CameraInfoImpl;
        this.mExecutor = executor;
        this.mScheduledExecutorService = scheduledExecutorService;
        this.mProcessorState = ProcessorState.UNINITIALIZED;
        this.mSessionProcessorCaptureCallback = new SessionProcessorCaptureCallback();
        int i = sNextInstanceId;
        sNextInstanceId = i + 1;
        this.mInstanceId = i;
        Logger.d(TAG, "New ProcessingCaptureSession (id=" + this.mInstanceId + ")");
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public ListenableFuture<Void> open(final SessionConfig sessionConfig, final CameraDevice cameraDevice, final SynchronizedCaptureSession.Opener opener) {
        Preconditions.checkArgument(this.mProcessorState == ProcessorState.UNINITIALIZED, "Invalid state state:" + this.mProcessorState);
        Preconditions.checkArgument(sessionConfig.getSurfaces().isEmpty() ^ true, "SessionConfig contains no surfaces");
        Logger.d(TAG, "open (id=" + this.mInstanceId + ")");
        List<DeferrableSurface> surfaces = sessionConfig.getSurfaces();
        this.mOutputSurfaces = surfaces;
        return FutureChain.from(DeferrableSurfaces.surfaceListWithTimeout(surfaces, false, TIMEOUT_GET_SURFACE_IN_MS, this.mExecutor, this.mScheduledExecutorService)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession$$ExternalSyntheticLambda1
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f$0.m110x6ab9d420(sessionConfig, cameraDevice, opener, (List) obj);
            }
        }, this.mExecutor).transform(new Function() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession$$ExternalSyntheticLambda2
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return this.f$0.m111x7b6fa0e1((Void) obj);
            }
        }, this.mExecutor);
    }

    /* JADX INFO: renamed from: lambda$open$2$androidx-camera-camera2-internal-ProcessingCaptureSession, reason: not valid java name */
    /* synthetic */ ListenableFuture m110x6ab9d420(SessionConfig sessionConfig, CameraDevice cameraDevice, SynchronizedCaptureSession.Opener opener, List list) throws Exception {
        OutputSurface outputSurfaceCreate;
        Logger.d(TAG, "-- getSurfaces done, start init (id=" + this.mInstanceId + ")");
        if (this.mProcessorState == ProcessorState.DE_INITIALIZED) {
            return Futures.immediateFailedFuture(new IllegalStateException("SessionProcessorCaptureSession is closed."));
        }
        final DeferrableSurface surface = null;
        if (list.contains(null)) {
            return Futures.immediateFailedFuture(new DeferrableSurface.SurfaceClosedException("Surface closed", sessionConfig.getSurfaces().get(list.indexOf(null))));
        }
        OutputSurface outputSurfaceCreate2 = null;
        OutputSurface outputSurfaceCreate3 = null;
        OutputSurface outputSurfaceCreate4 = null;
        for (int i = 0; i < sessionConfig.getSurfaces().size(); i++) {
            DeferrableSurface deferrableSurface = sessionConfig.getSurfaces().get(i);
            if (isPreview(deferrableSurface) || isStreamSharing(deferrableSurface)) {
                outputSurfaceCreate2 = OutputSurface.create(deferrableSurface.getSurface().get(), deferrableSurface.getPrescribedSize(), deferrableSurface.getPrescribedStreamFormat());
            } else if (isImageCapture(deferrableSurface)) {
                outputSurfaceCreate3 = OutputSurface.create(deferrableSurface.getSurface().get(), deferrableSurface.getPrescribedSize(), deferrableSurface.getPrescribedStreamFormat());
            } else if (isImageAnalysis(deferrableSurface)) {
                outputSurfaceCreate4 = OutputSurface.create(deferrableSurface.getSurface().get(), deferrableSurface.getPrescribedSize(), deferrableSurface.getPrescribedStreamFormat());
            }
        }
        if (sessionConfig.getPostviewOutputConfig() != null) {
            surface = sessionConfig.getPostviewOutputConfig().getSurface();
            outputSurfaceCreate = OutputSurface.create(surface.getSurface().get(), surface.getPrescribedSize(), surface.getPrescribedStreamFormat());
        } else {
            outputSurfaceCreate = null;
        }
        this.mProcessorState = ProcessorState.SESSION_INITIALIZED;
        try {
            ArrayList arrayList = new ArrayList(this.mOutputSurfaces);
            if (surface != null) {
                arrayList.add(surface);
            }
            DeferrableSurfaces.incrementAll(arrayList);
            Logger.w(TAG, "== initSession (id=" + this.mInstanceId + ")");
            try {
                SessionConfig sessionConfigInitSession = this.mSessionProcessor.initSession(this.mCamera2CameraInfoImpl, OutputSurfaceConfiguration.create(outputSurfaceCreate2, outputSurfaceCreate3, outputSurfaceCreate4, outputSurfaceCreate));
                this.mProcessorSessionConfig = sessionConfigInitSession;
                sessionConfigInitSession.getSurfaces().get(0).getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m109x494e3a9e(surface);
                    }
                }, CameraXExecutors.directExecutor());
                for (final DeferrableSurface deferrableSurface2 : this.mProcessorSessionConfig.getSurfaces()) {
                    sHeldProcessorSurfaces.add(deferrableSurface2);
                    deferrableSurface2.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            ProcessingCaptureSession.sHeldProcessorSurfaces.remove(deferrableSurface2);
                        }
                    }, this.mExecutor);
                }
                SessionConfig.ValidatingBuilder validatingBuilder = new SessionConfig.ValidatingBuilder();
                validatingBuilder.add(sessionConfig);
                validatingBuilder.clearSurfaces();
                validatingBuilder.add(this.mProcessorSessionConfig);
                Preconditions.checkArgument(validatingBuilder.isValid(), "Cannot transform the SessionConfig");
                ListenableFuture<Void> listenableFutureOpen = this.mCaptureSession.open(validatingBuilder.build(), (CameraDevice) Preconditions.checkNotNull(cameraDevice), opener);
                Futures.addCallback(listenableFutureOpen, new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession.1
                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onSuccess(Void r1) {
                    }

                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onFailure(Throwable th) {
                        Logger.e(ProcessingCaptureSession.TAG, "open session failed ", th);
                        ProcessingCaptureSession.this.close();
                        ProcessingCaptureSession.this.release(false);
                    }
                }, this.mExecutor);
                return listenableFutureOpen;
            } catch (Throwable th) {
                Logger.e(TAG, "initSession failed", th);
                DeferrableSurfaces.decrementAll(this.mOutputSurfaces);
                if (surface != null) {
                    surface.decrementUseCount();
                }
                throw th;
            }
        } catch (DeferrableSurface.SurfaceClosedException e) {
            return Futures.immediateFailedFuture(e);
        }
    }

    /* JADX INFO: renamed from: lambda$open$0$androidx-camera-camera2-internal-ProcessingCaptureSession, reason: not valid java name */
    /* synthetic */ void m109x494e3a9e(DeferrableSurface deferrableSurface) {
        DeferrableSurfaces.decrementAll(this.mOutputSurfaces);
        if (deferrableSurface != null) {
            deferrableSurface.decrementUseCount();
        }
    }

    /* JADX INFO: renamed from: lambda$open$3$androidx-camera-camera2-internal-ProcessingCaptureSession, reason: not valid java name */
    /* synthetic */ Void m111x7b6fa0e1(Void r1) {
        onConfigured(this.mCaptureSession);
        return null;
    }

    private static void cancelRequests(List<CaptureConfig> list) {
        for (CaptureConfig captureConfig : list) {
            Iterator<CameraCaptureCallback> it = captureConfig.getCameraCaptureCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onCaptureCancelled(captureConfig.getId());
            }
        }
    }

    void issueTriggerRequest(CaptureConfig captureConfig) {
        Logger.d(TAG, "issueTriggerRequest");
        CaptureRequestOptions captureRequestOptionsBuild = CaptureRequestOptions.Builder.from(captureConfig.getImplementationOptions()).build();
        Iterator it = captureRequestOptionsBuild.listOptions().iterator();
        while (it.hasNext()) {
            CaptureRequest.Key key = (CaptureRequest.Key) ((Config.Option) it.next()).getToken();
            if (key.equals(CaptureRequest.CONTROL_AF_TRIGGER) || key.equals(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER)) {
                this.mSessionProcessor.startTrigger(captureRequestOptionsBuild, captureConfig.getTagBundle(), new CaptureCallbackAdapter(captureConfig.getId(), captureConfig.getCameraCaptureCallbacks()));
                return;
            }
        }
        cancelRequests(Arrays.asList(captureConfig));
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void issueCaptureRequests(List<CaptureConfig> list) {
        if (list.isEmpty()) {
            return;
        }
        Logger.d(TAG, "issueCaptureRequests (id=" + this.mInstanceId + ") + state =" + this.mProcessorState);
        int iOrdinal = this.mProcessorState.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            if (this.mPendingCaptureConfigs != null) {
                cancelRequests(list);
                Logger.d(TAG, "cancel the request because are pending un-submitted request");
                return;
            } else {
                this.mPendingCaptureConfigs = list;
                return;
            }
        }
        if (iOrdinal == 2) {
            for (CaptureConfig captureConfig : list) {
                if (isTemplateTypeForStillCapture(captureConfig.getTemplateType())) {
                    issueStillCaptureRequest(captureConfig);
                } else {
                    issueTriggerRequest(captureConfig);
                }
            }
            return;
        }
        if (iOrdinal == 3 || iOrdinal == 4) {
            Logger.d(TAG, "Run issueCaptureRequests in wrong state, state = " + this.mProcessorState);
            cancelRequests(list);
        }
    }

    void issueStillCaptureRequest(CaptureConfig captureConfig) {
        CaptureRequestOptions.Builder builderFrom = CaptureRequestOptions.Builder.from(captureConfig.getImplementationOptions());
        if (captureConfig.getImplementationOptions().containsOption(CaptureConfig.OPTION_ROTATION)) {
            builderFrom.setCaptureRequestOption(CaptureRequest.JPEG_ORIENTATION, (Integer) captureConfig.getImplementationOptions().retrieveOption(CaptureConfig.OPTION_ROTATION));
        }
        if (captureConfig.getImplementationOptions().containsOption(CaptureConfig.OPTION_JPEG_QUALITY)) {
            builderFrom.setCaptureRequestOption(CaptureRequest.JPEG_QUALITY, Byte.valueOf(((Integer) captureConfig.getImplementationOptions().retrieveOption(CaptureConfig.OPTION_JPEG_QUALITY)).byteValue()));
        }
        CaptureRequestOptions captureRequestOptionsBuild = builderFrom.build();
        this.mStillCaptureOptions = captureRequestOptionsBuild;
        updateParameters(this.mSessionOptions, captureRequestOptionsBuild);
        this.mSessionProcessor.startCapture(captureConfig.isPostviewEnabled(), captureConfig.getTagBundle(), new CaptureCallbackAdapter(captureConfig.getId(), captureConfig.getCameraCaptureCallbacks()));
    }

    private static class CaptureCallbackAdapter implements SessionProcessor.CaptureCallback {
        private List<CameraCaptureCallback> mCameraCaptureCallbacks;
        private final int mCaptureConfigId;
        private CameraCaptureResult mCaptureResult;

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public /* synthetic */ void onCaptureProcessStarted(int i) {
            SessionProcessor.CaptureCallback.CC.$default$onCaptureProcessStarted(this, i);
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public /* synthetic */ void onCaptureSequenceAborted(int i) {
            SessionProcessor.CaptureCallback.CC.$default$onCaptureSequenceAborted(this, i);
        }

        private CaptureCallbackAdapter(int i, List<CameraCaptureCallback> list) {
            this.mCaptureResult = null;
            this.mCaptureConfigId = i;
            this.mCameraCaptureCallbacks = list;
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int i, long j) {
            Iterator<CameraCaptureCallback> it = this.mCameraCaptureCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onCaptureStarted(this.mCaptureConfigId);
            }
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int i) {
            Iterator<CameraCaptureCallback> it = this.mCameraCaptureCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onCaptureFailed(this.mCaptureConfigId, new CameraCaptureFailure(CameraCaptureFailure.Reason.ERROR));
            }
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(long j, int i, CameraCaptureResult cameraCaptureResult) {
            this.mCaptureResult = cameraCaptureResult;
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int i) {
            CameraCaptureResult emptyCameraCaptureResult = this.mCaptureResult;
            if (emptyCameraCaptureResult == null) {
                emptyCameraCaptureResult = new CameraCaptureResult.EmptyCameraCaptureResult();
            }
            Iterator<CameraCaptureCallback> it = this.mCameraCaptureCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onCaptureCompleted(this.mCaptureConfigId, emptyCameraCaptureResult);
            }
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureProcessProgressed(int i) {
            Iterator<CameraCaptureCallback> it = this.mCameraCaptureCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onCaptureProcessProgressed(this.mCaptureConfigId, i);
            }
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public ListenableFuture<Void> release(boolean z) {
        Logger.d(TAG, "release (id=" + this.mInstanceId + ") mProcessorState=" + this.mProcessorState);
        ListenableFuture<Void> listenableFutureRelease = this.mCaptureSession.release(z);
        int iOrdinal = this.mProcessorState.ordinal();
        if (iOrdinal == 1 || iOrdinal == 3) {
            listenableFutureRelease.addListener(new Runnable() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m112x14d78e45();
                }
            }, CameraXExecutors.directExecutor());
        }
        this.mProcessorState = ProcessorState.DE_INITIALIZED;
        return listenableFutureRelease;
    }

    /* JADX INFO: renamed from: lambda$release$4$androidx-camera-camera2-internal-ProcessingCaptureSession, reason: not valid java name */
    /* synthetic */ void m112x14d78e45() {
        Logger.d(TAG, "== deInitSession (id=" + this.mInstanceId + ")");
        this.mSessionProcessor.deInitSession();
    }

    private static List<SessionProcessorSurface> getSessionProcessorSurfaceList(List<DeferrableSurface> list) {
        ArrayList arrayList = new ArrayList();
        for (DeferrableSurface deferrableSurface : list) {
            Preconditions.checkArgument(deferrableSurface instanceof SessionProcessorSurface, "Surface must be SessionProcessorSurface");
            arrayList.add((SessionProcessorSurface) deferrableSurface);
        }
        return arrayList;
    }

    void onConfigured(CaptureSession captureSession) {
        if (this.mProcessorState != ProcessorState.SESSION_INITIALIZED) {
            return;
        }
        this.mRequestProcessor = new Camera2RequestProcessor(captureSession, getSessionProcessorSurfaceList(this.mProcessorSessionConfig.getSurfaces()));
        Logger.d(TAG, "== onCaptureSessinStarted (id = " + this.mInstanceId + ")");
        this.mSessionProcessor.onCaptureSessionStart(this.mRequestProcessor);
        this.mProcessorState = ProcessorState.ON_CAPTURE_SESSION_STARTED;
        SessionConfig sessionConfig = this.mSessionConfig;
        if (sessionConfig != null) {
            setSessionConfig(sessionConfig);
        }
        if (this.mPendingCaptureConfigs != null) {
            issueCaptureRequests(this.mPendingCaptureConfigs);
            this.mPendingCaptureConfigs = null;
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public SessionConfig getSessionConfig() {
        return this.mSessionConfig;
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public List<CaptureConfig> getCaptureConfigs() {
        return this.mPendingCaptureConfigs != null ? this.mPendingCaptureConfigs : Collections.EMPTY_LIST;
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void cancelIssuedCaptureRequests() {
        Logger.d(TAG, "cancelIssuedCaptureRequests (id=" + this.mInstanceId + ")");
        if (this.mPendingCaptureConfigs != null) {
            for (CaptureConfig captureConfig : this.mPendingCaptureConfigs) {
                Iterator<CameraCaptureCallback> it = captureConfig.getCameraCaptureCallbacks().iterator();
                while (it.hasNext()) {
                    it.next().onCaptureCancelled(captureConfig.getId());
                }
            }
            this.mPendingCaptureConfigs = null;
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void close() {
        Logger.d(TAG, "close (id=" + this.mInstanceId + ") state=" + this.mProcessorState);
        if (this.mProcessorState == ProcessorState.ON_CAPTURE_SESSION_STARTED) {
            Logger.d(TAG, "== onCaptureSessionEnd (id = " + this.mInstanceId + ")");
            this.mSessionProcessor.onCaptureSessionEnd();
            Camera2RequestProcessor camera2RequestProcessor = this.mRequestProcessor;
            if (camera2RequestProcessor != null) {
                camera2RequestProcessor.close();
            }
            this.mProcessorState = ProcessorState.ON_CAPTURE_SESSION_ENDED;
        }
        this.mCaptureSession.close();
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void setSessionConfig(SessionConfig sessionConfig) {
        Logger.d(TAG, "setSessionConfig (id=" + this.mInstanceId + ")");
        this.mSessionConfig = sessionConfig;
        if (sessionConfig == null) {
            return;
        }
        Camera2RequestProcessor camera2RequestProcessor = this.mRequestProcessor;
        if (camera2RequestProcessor != null) {
            camera2RequestProcessor.updateSessionConfig(sessionConfig);
        }
        if (this.mProcessorState == ProcessorState.ON_CAPTURE_SESSION_STARTED) {
            CaptureRequestOptions captureRequestOptionsBuild = CaptureRequestOptions.Builder.from(sessionConfig.getImplementationOptions()).build();
            this.mSessionOptions = captureRequestOptionsBuild;
            updateParameters(captureRequestOptionsBuild, this.mStillCaptureOptions);
            if (!hasPreviewSurface(sessionConfig.getRepeatingCaptureConfig())) {
                this.mSessionProcessor.stopRepeating();
            } else {
                this.mSessionProcessor.startRepeating(sessionConfig.getRepeatingCaptureConfig().getTagBundle(), this.mSessionProcessorCaptureCallback);
            }
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public boolean isInOpenState() {
        return this.mCaptureSession.isInOpenState();
    }

    private static boolean hasPreviewSurface(CaptureConfig captureConfig) {
        for (DeferrableSurface deferrableSurface : captureConfig.getSurfaces()) {
            if (isPreview(deferrableSurface) || isStreamSharing(deferrableSurface)) {
                return true;
            }
        }
        return false;
    }

    private void updateParameters(CaptureRequestOptions captureRequestOptions, CaptureRequestOptions captureRequestOptions2) {
        Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
        builder.insertAllOptions(captureRequestOptions);
        builder.insertAllOptions(captureRequestOptions2);
        this.mSessionProcessor.setParameters(builder.build());
    }

    private static boolean isPreview(DeferrableSurface deferrableSurface) {
        return Objects.equals(deferrableSurface.getContainerClass(), Preview.class);
    }

    private static boolean isImageCapture(DeferrableSurface deferrableSurface) {
        return Objects.equals(deferrableSurface.getContainerClass(), ImageCapture.class);
    }

    private static boolean isImageAnalysis(DeferrableSurface deferrableSurface) {
        return Objects.equals(deferrableSurface.getContainerClass(), ImageAnalysis.class);
    }

    private static boolean isStreamSharing(DeferrableSurface deferrableSurface) {
        return Objects.equals(deferrableSurface.getContainerClass(), StreamSharing.class);
    }

    private static class SessionProcessorCaptureCallback implements SessionProcessor.CaptureCallback {
        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(long j, int i, CameraCaptureResult cameraCaptureResult) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int i) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public /* synthetic */ void onCaptureProcessProgressed(int i) {
            SessionProcessor.CaptureCallback.CC.$default$onCaptureProcessProgressed(this, i);
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureProcessStarted(int i) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceAborted(int i) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int i) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int i, long j) {
        }

        SessionProcessorCaptureCallback() {
        }
    }
}

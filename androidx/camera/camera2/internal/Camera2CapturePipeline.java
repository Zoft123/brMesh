package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.Camera2CapturePipeline;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.workaround.FlashAvailabilityChecker;
import androidx.camera.camera2.internal.compat.workaround.OverrideAeModeForStillCapture;
import androidx.camera.camera2.internal.compat.workaround.UseFlashModeTorchFor3aUpdate;
import androidx.camera.camera2.internal.compat.workaround.UseTorchAsFlash;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.imagecapture.CameraCapturePipeline;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraCaptureResults;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.ConvergenceUtils;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
class Camera2CapturePipeline {
    private static final String TAG = "Camera2CapturePipeline";
    private final Camera2CameraControlImpl mCameraControl;
    private final Quirks mCameraQuirk;
    private final Executor mExecutor;
    private final boolean mHasFlashUnit;
    private final boolean mIsLegacyDevice;
    private final ScheduledExecutorService mScheduler;
    private int mTemplate = 1;
    private final UseTorchAsFlash mUseTorchAsFlash;

    interface PipelineTask {
        boolean isCaptureResultNeeded();

        void postCapture();

        ListenableFuture<Boolean> preCapture(TotalCaptureResult totalCaptureResult);
    }

    Camera2CapturePipeline(Camera2CameraControlImpl camera2CameraControlImpl, CameraCharacteristicsCompat cameraCharacteristicsCompat, Quirks quirks, Executor executor, ScheduledExecutorService scheduledExecutorService) {
        this.mCameraControl = camera2CameraControlImpl;
        Integer num = (Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        this.mIsLegacyDevice = num != null && num.intValue() == 2;
        this.mExecutor = executor;
        this.mScheduler = scheduledExecutorService;
        this.mCameraQuirk = quirks;
        this.mUseTorchAsFlash = new UseTorchAsFlash(quirks);
        Objects.requireNonNull(cameraCharacteristicsCompat);
        this.mHasFlashUnit = FlashAvailabilityChecker.isFlashAvailable(new Camera2CameraInfoImpl$$ExternalSyntheticLambda0(cameraCharacteristicsCompat));
    }

    public void setTemplate(int i) {
        this.mTemplate = i;
    }

    public ListenableFuture<List<Void>> submitStillCaptures(List<CaptureConfig> list, int i, int i2, int i3) {
        return Futures.nonCancellationPropagating(createPipeline(i, i2, i3).executeCapture(list, i2));
    }

    Pipeline createPipeline(int i, int i2, int i3) {
        int i4;
        OverrideAeModeForStillCapture overrideAeModeForStillCapture = new OverrideAeModeForStillCapture(this.mCameraQuirk);
        Pipeline pipeline = new Pipeline(this.mTemplate, this.mExecutor, this.mScheduler, this.mCameraControl, this.mIsLegacyDevice, overrideAeModeForStillCapture);
        if (i == 0) {
            pipeline.addTask(new AfTask(this.mCameraControl));
        }
        if (i2 == 3) {
            pipeline.addTask(new ScreenFlashTask(this.mCameraControl, this.mExecutor, this.mScheduler, new UseFlashModeTorchFor3aUpdate(this.mCameraQuirk)));
        } else {
            if (this.mHasFlashUnit) {
                if (isTorchAsFlash(i3)) {
                    i4 = i2;
                    pipeline.addTask(new TorchTask(this.mCameraControl, i4, this.mExecutor, this.mScheduler, (this.mUseTorchAsFlash.shouldUseTorchAsFlash() || this.mCameraControl.isInVideoUsage()) ? false : true));
                } else {
                    i4 = i2;
                    pipeline.addTask(new AePreCaptureTask(this.mCameraControl, i4, overrideAeModeForStillCapture));
                }
            }
            Logger.d(TAG, "createPipeline: captureMode = " + i + ", flashMode = " + i4 + ", flashType = " + i3 + ", pipeline tasks = " + pipeline.mTasks);
            return pipeline;
        }
        i4 = i2;
        Logger.d(TAG, "createPipeline: captureMode = " + i + ", flashMode = " + i4 + ", flashType = " + i3 + ", pipeline tasks = " + pipeline.mTasks);
        return pipeline;
    }

    CameraCapturePipeline getCameraCapturePipeline(int i, int i2, int i3) {
        return new CameraCapturePipelineImpl(createPipeline(i, i2, i3), this.mExecutor, i2);
    }

    static class CameraCapturePipelineImpl implements CameraCapturePipeline {
        private final Executor mExecutor;
        private int mFlashMode;
        private final Pipeline mPipelineDelegate;

        static /* synthetic */ Void lambda$invokePreCapture$0(TotalCaptureResult totalCaptureResult) {
            return null;
        }

        CameraCapturePipelineImpl(Pipeline pipeline, Executor executor, int i) {
            this.mPipelineDelegate = pipeline;
            this.mExecutor = executor;
            this.mFlashMode = i;
        }

        @Override // androidx.camera.core.imagecapture.CameraCapturePipeline
        public ListenableFuture<Void> invokePreCapture() {
            Logger.d(Camera2CapturePipeline.TAG, "invokePreCapture");
            return FutureChain.from(this.mPipelineDelegate.executePreCapture(this.mFlashMode)).transform(new Function() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$CameraCapturePipelineImpl$$ExternalSyntheticLambda1
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    return Camera2CapturePipeline.CameraCapturePipelineImpl.lambda$invokePreCapture$0((TotalCaptureResult) obj);
                }
            }, this.mExecutor);
        }

        @Override // androidx.camera.core.imagecapture.CameraCapturePipeline
        public ListenableFuture<Void> invokePostCapture() {
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$CameraCapturePipelineImpl$$ExternalSyntheticLambda0
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m70x44fc0440(completer);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$invokePostCapture$1$androidx-camera-camera2-internal-Camera2CapturePipeline$CameraCapturePipelineImpl, reason: not valid java name */
        /* synthetic */ Object m70x44fc0440(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mPipelineDelegate.executePostCapture();
            completer.set(null);
            return "invokePostCaptureFuture";
        }
    }

    static class Pipeline {
        private static final long CHECK_3A_TIMEOUT_IN_NS = TimeUnit.SECONDS.toNanos(1);
        private static final long CHECK_3A_WITH_FLASH_TIMEOUT_IN_NS = TimeUnit.SECONDS.toNanos(5);
        private final Camera2CameraControlImpl mCameraControl;
        private final Executor mExecutor;
        private final boolean mIsLegacyDevice;
        private final OverrideAeModeForStillCapture mOverrideAeModeForStillCapture;
        private final ScheduledExecutorService mScheduler;
        private final int mTemplate;
        private long mTimeout3A = CHECK_3A_TIMEOUT_IN_NS;
        final List<PipelineTask> mTasks = new ArrayList();
        private final PipelineTask mPipelineSubTask = new AnonymousClass1();

        /* JADX INFO: renamed from: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$1, reason: invalid class name */
        class AnonymousClass1 implements PipelineTask {
            AnonymousClass1() {
            }

            @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
            public ListenableFuture<Boolean> preCapture(TotalCaptureResult totalCaptureResult) {
                ArrayList arrayList = new ArrayList();
                Iterator<PipelineTask> it = Pipeline.this.mTasks.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().preCapture(totalCaptureResult));
                }
                return Futures.transform(Futures.allAsList(arrayList), new Function() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$1$$ExternalSyntheticLambda0
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        return Boolean.valueOf(((List) obj).contains(true));
                    }
                }, CameraXExecutors.directExecutor());
            }

            @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
            public boolean isCaptureResultNeeded() {
                Iterator<PipelineTask> it = Pipeline.this.mTasks.iterator();
                while (it.hasNext()) {
                    if (it.next().isCaptureResultNeeded()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
            public void postCapture() {
                Iterator<PipelineTask> it = Pipeline.this.mTasks.iterator();
                while (it.hasNext()) {
                    it.next().postCapture();
                }
            }
        }

        Pipeline(int i, Executor executor, ScheduledExecutorService scheduledExecutorService, Camera2CameraControlImpl camera2CameraControlImpl, boolean z, OverrideAeModeForStillCapture overrideAeModeForStillCapture) {
            this.mTemplate = i;
            this.mExecutor = executor;
            this.mScheduler = scheduledExecutorService;
            this.mCameraControl = camera2CameraControlImpl;
            this.mIsLegacyDevice = z;
            this.mOverrideAeModeForStillCapture = overrideAeModeForStillCapture;
        }

        void addTask(PipelineTask pipelineTask) {
            this.mTasks.add(pipelineTask);
        }

        private void setTimeout3A(long j) {
            this.mTimeout3A = j;
        }

        ListenableFuture<List<Void>> executeCapture(final List<CaptureConfig> list, final int i) {
            FutureChain futureChainTransformAsync = FutureChain.from(executePreCapture(i)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$$ExternalSyntheticLambda1
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m71xe4aaa129(list, i, (TotalCaptureResult) obj);
                }
            }, this.mExecutor);
            futureChainTransformAsync.addListener(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.executePostCapture();
                }
            }, this.mExecutor);
            return futureChainTransformAsync;
        }

        /* JADX INFO: renamed from: lambda$executeCapture$0$androidx-camera-camera2-internal-Camera2CapturePipeline$Pipeline, reason: not valid java name */
        /* synthetic */ ListenableFuture m71xe4aaa129(List list, int i, TotalCaptureResult totalCaptureResult) throws Exception {
            return submitConfigsInternal(list, i);
        }

        public ListenableFuture<TotalCaptureResult> executePreCapture(final int i) {
            ListenableFuture<TotalCaptureResult> listenableFutureImmediateFuture = Futures.immediateFuture(null);
            if (this.mTasks.isEmpty()) {
                return listenableFutureImmediateFuture;
            }
            return FutureChain.from(this.mPipelineSubTask.isCaptureResultNeeded() ? Camera2CapturePipeline.waitForResult(this.mCameraControl, null) : Futures.immediateFuture(null)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$$ExternalSyntheticLambda3
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m72xc852b1db(i, (TotalCaptureResult) obj);
                }
            }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$$ExternalSyntheticLambda4
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m73x9cb1c219((Boolean) obj);
                }
            }, this.mExecutor);
        }

        /* JADX INFO: renamed from: lambda$executePreCapture$1$androidx-camera-camera2-internal-Camera2CapturePipeline$Pipeline, reason: not valid java name */
        /* synthetic */ ListenableFuture m72xc852b1db(int i, TotalCaptureResult totalCaptureResult) throws Exception {
            if (Camera2CapturePipeline.isFlashRequired(i, totalCaptureResult)) {
                setTimeout3A(CHECK_3A_WITH_FLASH_TIMEOUT_IN_NS);
            }
            return this.mPipelineSubTask.preCapture(totalCaptureResult);
        }

        /* JADX INFO: renamed from: lambda$executePreCapture$3$androidx-camera-camera2-internal-Camera2CapturePipeline$Pipeline, reason: not valid java name */
        /* synthetic */ ListenableFuture m73x9cb1c219(Boolean bool) throws Exception {
            if (Boolean.TRUE.equals(bool)) {
                return Camera2CapturePipeline.waitForResult(this.mTimeout3A, this.mScheduler, this.mCameraControl, new ResultListener.Checker() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$$ExternalSyntheticLambda5
                    @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.ResultListener.Checker
                    public final boolean check(TotalCaptureResult totalCaptureResult) {
                        return Camera2CapturePipeline.is3AConverged(totalCaptureResult, false);
                    }
                });
            }
            return Futures.immediateFuture(null);
        }

        public void executePostCapture() {
            this.mPipelineSubTask.postCapture();
        }

        ListenableFuture<List<Void>> submitConfigsInternal(List<CaptureConfig> list, int i) {
            ImageProxy imageProxyDequeueImageFromBuffer;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (CaptureConfig captureConfig : list) {
                final CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(captureConfig);
                CameraCaptureResult cameraCaptureResultRetrieveCameraCaptureResult = (captureConfig.getTemplateType() != 5 || this.mCameraControl.getZslControl().isZslDisabledByFlashMode() || this.mCameraControl.getZslControl().isZslDisabledByUserCaseConfig() || (imageProxyDequeueImageFromBuffer = this.mCameraControl.getZslControl().dequeueImageFromBuffer()) == null || !this.mCameraControl.getZslControl().enqueueImageToImageWriter(imageProxyDequeueImageFromBuffer)) ? null : CameraCaptureResults.retrieveCameraCaptureResult(imageProxyDequeueImageFromBuffer.getImageInfo());
                if (cameraCaptureResultRetrieveCameraCaptureResult != null) {
                    builderFrom.setCameraCaptureResult(cameraCaptureResultRetrieveCameraCaptureResult);
                } else {
                    applyStillCaptureTemplate(builderFrom, captureConfig);
                }
                if (this.mOverrideAeModeForStillCapture.shouldSetAeModeAlwaysFlash(i)) {
                    applyAeModeQuirk(builderFrom);
                }
                arrayList.add(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$$ExternalSyntheticLambda0
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        return this.f$0.m74x1f41775a(builderFrom, completer);
                    }
                }));
                arrayList2.add(builderFrom.build());
            }
            this.mCameraControl.submitCaptureRequestsInternal(arrayList2);
            return Futures.allAsList(arrayList);
        }

        /* JADX INFO: renamed from: lambda$submitConfigsInternal$4$androidx-camera-camera2-internal-Camera2CapturePipeline$Pipeline, reason: not valid java name */
        /* synthetic */ Object m74x1f41775a(CaptureConfig.Builder builder, final CallbackToFutureAdapter.Completer completer) throws Exception {
            builder.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline.Pipeline.2
                @Override // androidx.camera.core.impl.CameraCaptureCallback
                public void onCaptureCompleted(int i, CameraCaptureResult cameraCaptureResult) {
                    completer.set(null);
                }

                @Override // androidx.camera.core.impl.CameraCaptureCallback
                public void onCaptureFailed(int i, CameraCaptureFailure cameraCaptureFailure) {
                    completer.setException(new ImageCaptureException(2, "Capture request failed with reason " + cameraCaptureFailure.getReason(), null));
                }

                @Override // androidx.camera.core.impl.CameraCaptureCallback
                public void onCaptureCancelled(int i) {
                    completer.setException(new ImageCaptureException(3, "Capture request is cancelled because camera is closed", null));
                }
            });
            return "submitStillCapture";
        }

        private void applyStillCaptureTemplate(CaptureConfig.Builder builder, CaptureConfig captureConfig) {
            int i;
            if (this.mTemplate != 3 || this.mIsLegacyDevice) {
                i = (captureConfig.getTemplateType() == -1 || captureConfig.getTemplateType() == 5) ? 2 : -1;
            } else {
                i = 4;
            }
            if (i != -1) {
                builder.setTemplateType(i);
            }
        }

        private void applyAeModeQuirk(CaptureConfig.Builder builder) {
            Camera2ImplConfig.Builder builder2 = new Camera2ImplConfig.Builder();
            builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, 3);
            builder.addImplementationOptions(builder2.build());
        }
    }

    static ListenableFuture<TotalCaptureResult> waitForResult(long j, ScheduledExecutorService scheduledExecutorService, Camera2CameraControlImpl camera2CameraControlImpl, ResultListener.Checker checker) {
        return Futures.makeTimeoutFuture(TimeUnit.NANOSECONDS.toMillis(j), scheduledExecutorService, null, true, waitForResult(camera2CameraControlImpl, checker));
    }

    static ListenableFuture<TotalCaptureResult> waitForResult(final Camera2CameraControlImpl camera2CameraControlImpl, ResultListener.Checker checker) {
        final ResultListener resultListener = new ResultListener(checker);
        camera2CameraControlImpl.addCaptureResultListener(resultListener);
        ListenableFuture<TotalCaptureResult> future = resultListener.getFuture();
        future.addListener(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                camera2CameraControlImpl.removeCaptureResultListener(resultListener);
            }
        }, camera2CameraControlImpl.mExecutor);
        return future;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean is3AConverged(TotalCaptureResult totalCaptureResult, boolean z) {
        if (totalCaptureResult == null) {
            return false;
        }
        return ConvergenceUtils.is3AConverged(new Camera2CameraCaptureResult(totalCaptureResult), z);
    }

    static class AfTask implements PipelineTask {
        private final Camera2CameraControlImpl mCameraControl;
        private boolean mIsExecuted = false;

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return true;
        }

        AfTask(Camera2CameraControlImpl camera2CameraControlImpl) {
            this.mCameraControl = camera2CameraControlImpl;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public ListenableFuture<Boolean> preCapture(TotalCaptureResult totalCaptureResult) {
            Integer num;
            int iIntValue;
            ListenableFuture<Boolean> listenableFutureImmediateFuture = Futures.immediateFuture(true);
            if (totalCaptureResult != null && (num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_MODE)) != null && ((iIntValue = num.intValue()) == 1 || iIntValue == 2)) {
                Logger.d(Camera2CapturePipeline.TAG, "TriggerAf? AF mode auto");
                Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (num2 != null && num2.intValue() == 0) {
                    Logger.d(Camera2CapturePipeline.TAG, "Trigger AF");
                    this.mIsExecuted = true;
                    this.mCameraControl.getFocusMeteringControl().triggerAf(null, false);
                }
            }
            return listenableFutureImmediateFuture;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            if (this.mIsExecuted) {
                Logger.d(Camera2CapturePipeline.TAG, "cancel TriggerAF");
                this.mCameraControl.getFocusMeteringControl().cancelAfAeTrigger(true, false);
            }
        }
    }

    static class TorchTask implements PipelineTask {
        private static final long CHECK_3A_WITH_TORCH_TIMEOUT_IN_NS = TimeUnit.SECONDS.toNanos(2);
        private final Camera2CameraControlImpl mCameraControl;
        private final Executor mExecutor;
        private final int mFlashMode;
        private boolean mIsExecuted = false;
        private final ScheduledExecutorService mScheduler;
        private final boolean mTriggerAePrecapture;

        TorchTask(Camera2CameraControlImpl camera2CameraControlImpl, int i, Executor executor, ScheduledExecutorService scheduledExecutorService, boolean z) {
            this.mCameraControl = camera2CameraControlImpl;
            this.mFlashMode = i;
            this.mExecutor = executor;
            this.mScheduler = scheduledExecutorService;
            this.mTriggerAePrecapture = z;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public ListenableFuture<Boolean> preCapture(TotalCaptureResult totalCaptureResult) {
            Logger.d(Camera2CapturePipeline.TAG, "TorchTask#preCapture: isFlashRequired = " + Camera2CapturePipeline.isFlashRequired(this.mFlashMode, totalCaptureResult));
            if (Camera2CapturePipeline.isFlashRequired(this.mFlashMode, totalCaptureResult)) {
                if (this.mCameraControl.isTorchOn()) {
                    Logger.d(Camera2CapturePipeline.TAG, "Torch already on, not turn on");
                } else {
                    Logger.d(Camera2CapturePipeline.TAG, "Turn on torch");
                    this.mIsExecuted = true;
                    return FutureChain.from(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$TorchTask$$ExternalSyntheticLambda1
                        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                            return this.f$0.m84x3ebf0c8c(completer);
                        }
                    })).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$TorchTask$$ExternalSyntheticLambda2
                        @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                        public final ListenableFuture apply(Object obj) {
                            return this.f$0.m85x1a80884d((Void) obj);
                        }
                    }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$TorchTask$$ExternalSyntheticLambda3
                        @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                        public final ListenableFuture apply(Object obj) {
                            return this.f$0.m86xd2037fcf((Void) obj);
                        }
                    }, this.mExecutor).transform(new Function() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$TorchTask$$ExternalSyntheticLambda4
                        @Override // androidx.arch.core.util.Function
                        public final Object apply(Object obj) {
                            return Camera2CapturePipeline.TorchTask.lambda$preCapture$4((TotalCaptureResult) obj);
                        }
                    }, CameraXExecutors.directExecutor());
                }
            }
            return Futures.immediateFuture(false);
        }

        /* JADX INFO: renamed from: lambda$preCapture$0$androidx-camera-camera2-internal-Camera2CapturePipeline$TorchTask, reason: not valid java name */
        /* synthetic */ Object m84x3ebf0c8c(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mCameraControl.getTorchControl().m121xbd10d892(completer, true);
            return "TorchOn";
        }

        /* JADX INFO: renamed from: lambda$preCapture$1$androidx-camera-camera2-internal-Camera2CapturePipeline$TorchTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m85x1a80884d(Void r1) throws Exception {
            if (this.mTriggerAePrecapture) {
                return this.mCameraControl.getFocusMeteringControl().triggerAePrecapture();
            }
            return Futures.immediateFuture(null);
        }

        /* JADX INFO: renamed from: lambda$preCapture$3$androidx-camera-camera2-internal-Camera2CapturePipeline$TorchTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m86xd2037fcf(Void r5) throws Exception {
            return Camera2CapturePipeline.waitForResult(CHECK_3A_WITH_TORCH_TIMEOUT_IN_NS, this.mScheduler, this.mCameraControl, new ResultListener.Checker() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$TorchTask$$ExternalSyntheticLambda0
                @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.ResultListener.Checker
                public final boolean check(TotalCaptureResult totalCaptureResult) {
                    return Camera2CapturePipeline.is3AConverged(totalCaptureResult, true);
                }
            });
        }

        static /* synthetic */ Boolean lambda$preCapture$4(TotalCaptureResult totalCaptureResult) {
            return false;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return this.mFlashMode == 0;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            if (this.mIsExecuted) {
                this.mCameraControl.getTorchControl().m121xbd10d892(null, false);
                Logger.d(Camera2CapturePipeline.TAG, "Turning off torch");
                if (this.mTriggerAePrecapture) {
                    this.mCameraControl.getFocusMeteringControl().cancelAfAeTrigger(false, true);
                }
            }
        }
    }

    static class AePreCaptureTask implements PipelineTask {
        private final Camera2CameraControlImpl mCameraControl;
        private final int mFlashMode;
        private boolean mIsExecuted = false;
        private final OverrideAeModeForStillCapture mOverrideAeModeForStillCapture;

        AePreCaptureTask(Camera2CameraControlImpl camera2CameraControlImpl, int i, OverrideAeModeForStillCapture overrideAeModeForStillCapture) {
            this.mCameraControl = camera2CameraControlImpl;
            this.mFlashMode = i;
            this.mOverrideAeModeForStillCapture = overrideAeModeForStillCapture;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public ListenableFuture<Boolean> preCapture(TotalCaptureResult totalCaptureResult) {
            if (Camera2CapturePipeline.isFlashRequired(this.mFlashMode, totalCaptureResult)) {
                Logger.d(Camera2CapturePipeline.TAG, "Trigger AE");
                this.mIsExecuted = true;
                return FutureChain.from(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$AePreCaptureTask$$ExternalSyntheticLambda0
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        return this.f$0.m69x78c3ed41(completer);
                    }
                })).transform(new Function() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$AePreCaptureTask$$ExternalSyntheticLambda1
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        return Camera2CapturePipeline.AePreCaptureTask.lambda$preCapture$1((Void) obj);
                    }
                }, CameraXExecutors.directExecutor());
            }
            return Futures.immediateFuture(false);
        }

        /* JADX INFO: renamed from: lambda$preCapture$0$androidx-camera-camera2-internal-Camera2CapturePipeline$AePreCaptureTask, reason: not valid java name */
        /* synthetic */ Object m69x78c3ed41(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mCameraControl.getFocusMeteringControl().m106xcc3739a4(completer);
            this.mOverrideAeModeForStillCapture.onAePrecaptureStarted();
            return "AePreCapture";
        }

        static /* synthetic */ Boolean lambda$preCapture$1(Void r0) {
            return true;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return this.mFlashMode == 0;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            if (this.mIsExecuted) {
                Logger.d(Camera2CapturePipeline.TAG, "cancel TriggerAePreCapture");
                this.mCameraControl.getFocusMeteringControl().cancelAfAeTrigger(false, true);
                this.mOverrideAeModeForStillCapture.onAePrecaptureFinished();
            }
        }
    }

    static class ScreenFlashTask implements PipelineTask {
        private static final long CHECK_3A_WITH_SCREEN_FLASH_TIMEOUT_IN_NS = TimeUnit.SECONDS.toNanos(2);
        private final Camera2CameraControlImpl mCameraControl;
        private final Executor mExecutor;
        private final ScheduledExecutorService mScheduler;
        private final ImageCapture.ScreenFlash mScreenFlash;
        private final UseFlashModeTorchFor3aUpdate mUseFlashModeTorchFor3aUpdate;

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return false;
        }

        ScreenFlashTask(Camera2CameraControlImpl camera2CameraControlImpl, Executor executor, ScheduledExecutorService scheduledExecutorService, UseFlashModeTorchFor3aUpdate useFlashModeTorchFor3aUpdate) {
            this.mCameraControl = camera2CameraControlImpl;
            this.mExecutor = executor;
            this.mScheduler = scheduledExecutorService;
            this.mUseFlashModeTorchFor3aUpdate = useFlashModeTorchFor3aUpdate;
            this.mScreenFlash = (ImageCapture.ScreenFlash) Objects.requireNonNull(camera2CameraControlImpl.getScreenFlash());
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public ListenableFuture<Boolean> preCapture(TotalCaptureResult totalCaptureResult) {
            Logger.d(Camera2CapturePipeline.TAG, "ScreenFlashTask#preCapture");
            final AtomicReference atomicReference = new AtomicReference();
            final ListenableFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda8
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return Camera2CapturePipeline.ScreenFlashTask.lambda$preCapture$1(atomicReference, completer);
                }
            });
            return FutureChain.from(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda9
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m78x6ffc4037(atomicReference, completer);
                }
            })).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda10
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m79x37082738((Void) obj);
                }
            }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda11
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m81xc51ff53a((Void) obj);
                }
            }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda12
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m82x8c2bdc3b(future, obj);
                }
            }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda13
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m83x5337c33c((Void) obj);
                }
            }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda1
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m76x8e5210ab((Void) obj);
                }
            }, this.mExecutor).transform(new Function() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda2
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    return Camera2CapturePipeline.ScreenFlashTask.lambda$preCapture$11((TotalCaptureResult) obj);
                }
            }, CameraXExecutors.directExecutor());
        }

        static /* synthetic */ Object lambda$preCapture$1(AtomicReference atomicReference, final CallbackToFutureAdapter.Completer completer) throws Exception {
            atomicReference.set(new ImageCapture.ScreenFlashListener() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda7
                @Override // androidx.camera.core.ImageCapture.ScreenFlashListener
                public final void onCompleted() {
                    Camera2CapturePipeline.ScreenFlashTask.lambda$preCapture$0(completer);
                }
            });
            return "OnScreenFlashUiApplied";
        }

        static /* synthetic */ void lambda$preCapture$0(CallbackToFutureAdapter.Completer completer) {
            Logger.d(Camera2CapturePipeline.TAG, "ScreenFlashTask#preCapture: UI change applied");
            completer.set(null);
        }

        /* JADX INFO: renamed from: lambda$preCapture$3$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ Object m78x6ffc4037(final AtomicReference atomicReference, final CallbackToFutureAdapter.Completer completer) throws Exception {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m77xa8f05936(atomicReference, completer);
                }
            });
            return "OnScreenFlashStart";
        }

        /* JADX INFO: renamed from: lambda$preCapture$2$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ void m77xa8f05936(AtomicReference atomicReference, CallbackToFutureAdapter.Completer completer) {
            Logger.d(Camera2CapturePipeline.TAG, "ScreenFlashTask#preCapture: invoking applyScreenFlashUi");
            this.mScreenFlash.apply(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3L), (ImageCapture.ScreenFlashListener) atomicReference.get());
            completer.set(null);
        }

        /* JADX INFO: renamed from: lambda$preCapture$4$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m79x37082738(Void r2) throws Exception {
            return this.mCameraControl.getFocusMeteringControl().enableExternalFlashAeMode(true);
        }

        /* JADX INFO: renamed from: lambda$preCapture$6$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m81xc51ff53a(Void r1) throws Exception {
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda0
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m80xfe140e39(completer);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$preCapture$5$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ Object m80xfe140e39(CallbackToFutureAdapter.Completer completer) throws Exception {
            if (!this.mUseFlashModeTorchFor3aUpdate.shouldUseFlashModeTorch()) {
                completer.set(null);
                return "EnableTorchInternal";
            }
            Logger.d(Camera2CapturePipeline.TAG, "ScreenFlashTask#preCapture: enable torch");
            this.mCameraControl.enableTorchInternal(true);
            completer.set(null);
            return "EnableTorchInternal";
        }

        /* JADX INFO: renamed from: lambda$preCapture$7$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m82x8c2bdc3b(ListenableFuture listenableFuture, Object obj) throws Exception {
            return Futures.makeTimeoutFuture(TimeUnit.SECONDS.toMillis(3L), this.mScheduler, null, true, listenableFuture);
        }

        /* JADX INFO: renamed from: lambda$preCapture$8$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m83x5337c33c(Void r1) throws Exception {
            return this.mCameraControl.getFocusMeteringControl().triggerAePrecapture();
        }

        /* JADX INFO: renamed from: lambda$preCapture$10$androidx-camera-camera2-internal-Camera2CapturePipeline$ScreenFlashTask, reason: not valid java name */
        /* synthetic */ ListenableFuture m76x8e5210ab(Void r5) throws Exception {
            return Camera2CapturePipeline.waitForResult(CHECK_3A_WITH_SCREEN_FLASH_TIMEOUT_IN_NS, this.mScheduler, this.mCameraControl, new ResultListener.Checker() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda6
                @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.ResultListener.Checker
                public final boolean check(TotalCaptureResult totalCaptureResult) {
                    return Camera2CapturePipeline.is3AConverged(totalCaptureResult, false);
                }
            });
        }

        static /* synthetic */ Boolean lambda$preCapture$11(TotalCaptureResult totalCaptureResult) {
            return false;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            Logger.d(Camera2CapturePipeline.TAG, "ScreenFlashTask#postCapture");
            if (this.mUseFlashModeTorchFor3aUpdate.shouldUseFlashModeTorch()) {
                this.mCameraControl.enableTorchInternal(false);
            }
            this.mCameraControl.getFocusMeteringControl().enableExternalFlashAeMode(false).addListener(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d(Camera2CapturePipeline.TAG, "enableExternalFlashAeMode disabled");
                }
            }, this.mExecutor);
            this.mCameraControl.getFocusMeteringControl().cancelAfAeTrigger(false, true);
            ScheduledExecutorService scheduledExecutorServiceMainThreadExecutor = CameraXExecutors.mainThreadExecutor();
            final ImageCapture.ScreenFlash screenFlash = this.mScreenFlash;
            Objects.requireNonNull(screenFlash);
            scheduledExecutorServiceMainThreadExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ScreenFlashTask$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    screenFlash.clear();
                }
            });
        }
    }

    static boolean isFlashRequired(int i, TotalCaptureResult totalCaptureResult) {
        Logger.d(TAG, "isFlashRequired: flashMode = " + i);
        if (i == 0) {
            Integer num = totalCaptureResult != null ? (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_STATE) : null;
            Logger.d(TAG, "isFlashRequired: aeState = " + num);
            return num != null && num.intValue() == 4;
        }
        if (i != 1) {
            if (i == 2) {
                return false;
            }
            if (i != 3) {
                throw new AssertionError(i);
            }
        }
        return true;
    }

    static class ResultListener implements Camera2CameraControlImpl.CaptureResultListener {
        private final Checker mChecker;
        private CallbackToFutureAdapter.Completer<TotalCaptureResult> mCompleter;
        private final ListenableFuture<TotalCaptureResult> mFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline$ResultListener$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m75xaa6bbe23(completer);
            }
        });

        interface Checker {
            boolean check(TotalCaptureResult totalCaptureResult);
        }

        /* JADX INFO: renamed from: lambda$new$0$androidx-camera-camera2-internal-Camera2CapturePipeline$ResultListener, reason: not valid java name */
        /* synthetic */ Object m75xaa6bbe23(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mCompleter = completer;
            return "waitFor3AResult";
        }

        ResultListener(Checker checker) {
            this.mChecker = checker;
        }

        public ListenableFuture<TotalCaptureResult> getFuture() {
            return this.mFuture;
        }

        @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
        public boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
            Checker checker = this.mChecker;
            if (checker != null && !checker.check(totalCaptureResult)) {
                return false;
            }
            this.mCompleter.set(totalCaptureResult);
            return true;
        }
    }

    private boolean isTorchAsFlash(int i) {
        return this.mUseTorchAsFlash.shouldUseTorchAsFlash() || this.mTemplate == 3 || i == 1;
    }
}

package androidx.camera.camera2.internal;

import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Build;
import android.util.Log;
import android.util.Rational;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.compat.workaround.MeteringRegionCorrection;
import androidx.camera.core.CameraControl;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
class FocusMeteringControl {
    static final long AUTO_FOCUS_TIMEOUT_DURATION = 5000;
    private static final MeteringRectangle[] EMPTY_RECTANGLES = new MeteringRectangle[0];
    private static final String TAG = "FocusMeteringControl";
    private MeteringRectangle[] mAeRects;
    private MeteringRectangle[] mAfRects;
    private ScheduledFuture<?> mAutoCancelHandle;
    private ScheduledFuture<?> mAutoFocusTimeoutHandle;
    private MeteringRectangle[] mAwbRects;
    private final Camera2CameraControlImpl mCameraControl;
    final Executor mExecutor;
    private boolean mIsExternalFlashAeModeEnabled;
    private final MeteringRegionCorrection mMeteringRegionCorrection;
    CallbackToFutureAdapter.Completer<FocusMeteringResult> mRunningActionCompleter;
    CallbackToFutureAdapter.Completer<Void> mRunningCancelCompleter;
    private final ScheduledExecutorService mScheduler;
    private Camera2CameraControlImpl.CaptureResultListener mSessionListenerForAeMode;
    private volatile boolean mIsActive = false;
    private volatile Rational mPreviewAspectRatio = null;
    private boolean mIsInAfAutoMode = false;
    Integer mCurrentAfState = 0;
    long mFocusTimeoutCounter = 0;
    boolean mIsAutoFocusCompleted = false;
    boolean mIsFocusSuccessful = false;
    private int mTemplate = 1;
    private Camera2CameraControlImpl.CaptureResultListener mSessionListenerForFocus = null;
    private Camera2CameraControlImpl.CaptureResultListener mSessionListenerForCancel = null;

    FocusMeteringControl(Camera2CameraControlImpl camera2CameraControlImpl, ScheduledExecutorService scheduledExecutorService, Executor executor, Quirks quirks) {
        MeteringRectangle[] meteringRectangleArr = EMPTY_RECTANGLES;
        this.mAfRects = meteringRectangleArr;
        this.mAeRects = meteringRectangleArr;
        this.mAwbRects = meteringRectangleArr;
        this.mRunningActionCompleter = null;
        this.mRunningCancelCompleter = null;
        this.mIsExternalFlashAeModeEnabled = false;
        this.mSessionListenerForAeMode = null;
        this.mCameraControl = camera2CameraControlImpl;
        this.mExecutor = executor;
        this.mScheduler = scheduledExecutorService;
        this.mMeteringRegionCorrection = new MeteringRegionCorrection(quirks);
    }

    void setActive(boolean z) {
        if (z == this.mIsActive) {
            return;
        }
        this.mIsActive = z;
        if (this.mIsActive) {
            return;
        }
        cancelFocusAndMeteringWithoutAsyncResult();
    }

    public void setPreviewAspectRatio(Rational rational) {
        this.mPreviewAspectRatio = rational;
    }

    private Rational getDefaultAspectRatio() {
        if (this.mPreviewAspectRatio != null) {
            return this.mPreviewAspectRatio;
        }
        Rect cropSensorRegion = this.mCameraControl.getCropSensorRegion();
        return new Rational(cropSensorRegion.width(), cropSensorRegion.height());
    }

    void setTemplate(int i) {
        this.mTemplate = i;
    }

    void addFocusMeteringOptions(Camera2ImplConfig.Builder builder) {
        builder.setCaptureRequestOptionWithPriority(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(this.mCameraControl.getSupportedAfMode(this.mIsInAfAutoMode ? 1 : getDefaultAfMode())), Config.OptionPriority.REQUIRED);
        if (this.mAfRects.length != 0) {
            builder.setCaptureRequestOptionWithPriority(CaptureRequest.CONTROL_AF_REGIONS, this.mAfRects, Config.OptionPriority.REQUIRED);
        }
        if (this.mAeRects.length != 0) {
            builder.setCaptureRequestOptionWithPriority(CaptureRequest.CONTROL_AE_REGIONS, this.mAeRects, Config.OptionPriority.REQUIRED);
        }
        if (this.mAwbRects.length != 0) {
            builder.setCaptureRequestOptionWithPriority(CaptureRequest.CONTROL_AWB_REGIONS, this.mAwbRects, Config.OptionPriority.REQUIRED);
        }
    }

    private static boolean isValid(MeteringPoint meteringPoint) {
        return meteringPoint.getX() >= 0.0f && meteringPoint.getX() <= 1.0f && meteringPoint.getY() >= 0.0f && meteringPoint.getY() <= 1.0f;
    }

    private static PointF getFovAdjustedPoint(MeteringPoint meteringPoint, Rational rational, Rational rational2, int i, MeteringRegionCorrection meteringRegionCorrection) {
        if (meteringPoint.getSurfaceAspectRatio() != null) {
            rational2 = meteringPoint.getSurfaceAspectRatio();
        }
        PointF correctedPoint = meteringRegionCorrection.getCorrectedPoint(meteringPoint, i);
        if (!rational2.equals(rational)) {
            if (rational2.compareTo(rational) > 0) {
                float fDoubleValue = (float) (rational2.doubleValue() / rational.doubleValue());
                correctedPoint.y = (((float) ((((double) fDoubleValue) - 1.0d) / 2.0d)) + correctedPoint.y) * (1.0f / fDoubleValue);
                return correctedPoint;
            }
            float fDoubleValue2 = (float) (rational.doubleValue() / rational2.doubleValue());
            correctedPoint.x = (((float) ((((double) fDoubleValue2) - 1.0d) / 2.0d)) + correctedPoint.x) * (1.0f / fDoubleValue2);
        }
        return correctedPoint;
    }

    private static MeteringRectangle getMeteringRect(MeteringPoint meteringPoint, PointF pointF, Rect rect) {
        int iWidth = (int) (rect.left + (pointF.x * rect.width()));
        int iHeight = (int) (rect.top + (pointF.y * rect.height()));
        int size = ((int) (meteringPoint.getSize() * rect.width())) / 2;
        int size2 = ((int) (meteringPoint.getSize() * rect.height())) / 2;
        Rect rect2 = new Rect(iWidth - size, iHeight - size2, iWidth + size, iHeight + size2);
        rect2.left = rangeLimit(rect2.left, rect.right, rect.left);
        rect2.right = rangeLimit(rect2.right, rect.right, rect.left);
        rect2.top = rangeLimit(rect2.top, rect.bottom, rect.top);
        rect2.bottom = rangeLimit(rect2.bottom, rect.bottom, rect.top);
        return new MeteringRectangle(rect2, 1000);
    }

    private static int rangeLimit(int i, int i2, int i3) {
        return Math.min(Math.max(i, i3), i2);
    }

    ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction focusMeteringAction) {
        return startFocusAndMetering(focusMeteringAction, AUTO_FOCUS_TIMEOUT_DURATION);
    }

    ListenableFuture<FocusMeteringResult> startFocusAndMetering(final FocusMeteringAction focusMeteringAction, final long j) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda8
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m105xb5d4fd7e(focusMeteringAction, j, completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startFocusAndMetering$1$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ Object m105xb5d4fd7e(final FocusMeteringAction focusMeteringAction, final long j, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m104x8c80a83d(completer, focusMeteringAction, j);
            }
        });
        return "startFocusAndMetering";
    }

    private List<MeteringRectangle> getMeteringRectangles(List<MeteringPoint> list, int i, Rational rational, Rect rect, int i2) {
        if (list.isEmpty() || i == 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        Rational rational2 = new Rational(rect.width(), rect.height());
        for (MeteringPoint meteringPoint : list) {
            if (arrayList.size() == i) {
                break;
            }
            if (isValid(meteringPoint)) {
                MeteringRectangle meteringRect = getMeteringRect(meteringPoint, getFovAdjustedPoint(meteringPoint, rational2, rational, i2, this.mMeteringRegionCorrection), rect);
                if (meteringRect.getWidth() != 0 && meteringRect.getHeight() != 0) {
                    arrayList.add(meteringRect);
                }
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: startFocusAndMeteringInternal, reason: merged with bridge method [inline-methods] */
    public void m104x8c80a83d(CallbackToFutureAdapter.Completer<FocusMeteringResult> completer, FocusMeteringAction focusMeteringAction, long j) {
        if (!this.mIsActive) {
            completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            return;
        }
        Rect cropSensorRegion = this.mCameraControl.getCropSensorRegion();
        Rational defaultAspectRatio = getDefaultAspectRatio();
        List<MeteringRectangle> meteringRectangles = getMeteringRectangles(focusMeteringAction.getMeteringPointsAf(), this.mCameraControl.getMaxAfRegionCount(), defaultAspectRatio, cropSensorRegion, 1);
        List<MeteringRectangle> meteringRectangles2 = getMeteringRectangles(focusMeteringAction.getMeteringPointsAe(), this.mCameraControl.getMaxAeRegionCount(), defaultAspectRatio, cropSensorRegion, 2);
        List<MeteringRectangle> meteringRectangles3 = getMeteringRectangles(focusMeteringAction.getMeteringPointsAwb(), this.mCameraControl.getMaxAwbRegionCount(), defaultAspectRatio, cropSensorRegion, 4);
        if (meteringRectangles.isEmpty() && meteringRectangles2.isEmpty() && meteringRectangles3.isEmpty()) {
            completer.setException(new IllegalArgumentException("None of the specified AF/AE/AWB MeteringPoints is supported on this camera."));
            return;
        }
        failActionFuture("Cancelled by another startFocusAndMetering()");
        failCancelFuture("Cancelled by another startFocusAndMetering()");
        disableAutoCancel();
        this.mRunningActionCompleter = completer;
        MeteringRectangle[] meteringRectangleArr = EMPTY_RECTANGLES;
        executeMeteringAction((MeteringRectangle[]) meteringRectangles.toArray(meteringRectangleArr), (MeteringRectangle[]) meteringRectangles2.toArray(meteringRectangleArr), (MeteringRectangle[]) meteringRectangles3.toArray(meteringRectangleArr), focusMeteringAction, j);
    }

    void triggerAf(final CallbackToFutureAdapter.Completer<CameraCaptureResult> completer, boolean z) {
        if (!this.mIsActive) {
            if (completer != null) {
                completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
                return;
            }
            return;
        }
        CaptureConfig.Builder builder = new CaptureConfig.Builder();
        builder.setTemplateType(this.mTemplate);
        builder.setUseRepeatingSurface(true);
        Camera2ImplConfig.Builder builder2 = new Camera2ImplConfig.Builder();
        builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AF_TRIGGER, 1);
        if (z) {
            builder2.setCaptureRequestOptionWithPriority(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(this.mCameraControl.getSupportedAeMode(1)), Config.OptionPriority.HIGH_PRIORITY_REQUIRED);
        }
        builder.addImplementationOptions(builder2.build());
        builder.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.camera2.internal.FocusMeteringControl.1
            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCompleted(int i, CameraCaptureResult cameraCaptureResult) {
                CallbackToFutureAdapter.Completer completer2 = completer;
                if (completer2 != null) {
                    completer2.set(cameraCaptureResult);
                }
            }

            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureFailed(int i, CameraCaptureFailure cameraCaptureFailure) {
                CallbackToFutureAdapter.Completer completer2 = completer;
                if (completer2 != null) {
                    completer2.setException(new CameraControlInternal.CameraControlException(cameraCaptureFailure));
                }
            }

            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCancelled(int i) {
                CallbackToFutureAdapter.Completer completer2 = completer;
                if (completer2 != null) {
                    completer2.setException(new CameraControl.OperationCanceledException("Camera is closed"));
                }
            }
        });
        this.mCameraControl.submitCaptureRequestsInternal(Collections.singletonList(builder.build()));
    }

    ListenableFuture<Void> triggerAePrecapture() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda10
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m107xf58b8ee5(completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$triggerAePrecapture$3$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ Object m107xf58b8ee5(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m106xcc3739a4(completer);
            }
        });
        return "triggerAePrecapture";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: triggerAePrecapture, reason: merged with bridge method [inline-methods] */
    public void m106xcc3739a4(final CallbackToFutureAdapter.Completer<Void> completer) {
        Logger.d(TAG, "triggerAePrecapture");
        if (!this.mIsActive) {
            if (completer != null) {
                completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
                return;
            }
            return;
        }
        CaptureConfig.Builder builder = new CaptureConfig.Builder();
        builder.setTemplateType(this.mTemplate);
        builder.setUseRepeatingSurface(true);
        Camera2ImplConfig.Builder builder2 = new Camera2ImplConfig.Builder();
        builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
        builder.addImplementationOptions(builder2.build());
        builder.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.camera2.internal.FocusMeteringControl.2
            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCompleted(int i, CameraCaptureResult cameraCaptureResult) {
                if (completer != null) {
                    Logger.d(FocusMeteringControl.TAG, "triggerAePrecapture: triggering capture request completed");
                    completer.set(null);
                }
            }

            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureFailed(int i, CameraCaptureFailure cameraCaptureFailure) {
                CallbackToFutureAdapter.Completer completer2 = completer;
                if (completer2 != null) {
                    completer2.setException(new CameraControlInternal.CameraControlException(cameraCaptureFailure));
                }
            }

            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCancelled(int i) {
                CallbackToFutureAdapter.Completer completer2 = completer;
                if (completer2 != null) {
                    completer2.setException(new CameraControl.OperationCanceledException("Camera is closed"));
                }
            }
        });
        this.mCameraControl.submitCaptureRequestsInternal(Collections.singletonList(builder.build()));
    }

    void cancelAfAeTrigger(boolean z, boolean z2) {
        if (this.mIsActive) {
            CaptureConfig.Builder builder = new CaptureConfig.Builder();
            builder.setUseRepeatingSurface(true);
            builder.setTemplateType(this.mTemplate);
            Camera2ImplConfig.Builder builder2 = new Camera2ImplConfig.Builder();
            if (z) {
                builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AF_TRIGGER, 2);
            }
            if (z2) {
                builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 2);
            }
            builder.addImplementationOptions(builder2.build());
            this.mCameraControl.submitCaptureRequestsInternal(Collections.singletonList(builder.build()));
        }
    }

    boolean isExternalFlashAeModeEnabled() {
        return this.mIsExternalFlashAeModeEnabled;
    }

    ListenableFuture<Void> enableExternalFlashAeMode(final boolean z) {
        if (Build.VERSION.SDK_INT < 28) {
            Log.d(TAG, "CONTROL_AE_MODE_ON_EXTERNAL_FLASH is not supported in API " + Build.VERSION.SDK_INT);
            return Futures.immediateFuture(null);
        }
        if (this.mCameraControl.getSupportedAeMode(5) != 5) {
            Log.d(TAG, "CONTROL_AE_MODE_ON_EXTERNAL_FLASH is not supported in this device");
            return Futures.immediateFuture(null);
        }
        Log.d(TAG, "enableExternalFlashAeMode: CONTROL_AE_MODE_ON_EXTERNAL_FLASH supported");
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m97xc21eb171(z, completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$enableExternalFlashAeMode$5$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ Object m97xc21eb171(final boolean z, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m96x98ca5c30(z, completer);
            }
        });
        return "enableExternalFlashAeMode";
    }

    /* JADX INFO: renamed from: lambda$enableExternalFlashAeMode$4$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ void m96x98ca5c30(boolean z, CallbackToFutureAdapter.Completer completer) {
        this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForAeMode);
        this.mIsExternalFlashAeModeEnabled = z;
        enableExternalFlashAeMode((CallbackToFutureAdapter.Completer<Void>) completer);
    }

    private void enableExternalFlashAeMode(final CallbackToFutureAdapter.Completer<Void> completer) {
        if (!this.mIsActive) {
            if (completer != null) {
                completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            }
        } else {
            final long jUpdateSessionConfigSynchronous = this.mCameraControl.updateSessionConfigSynchronous();
            Camera2CameraControlImpl.CaptureResultListener captureResultListener = new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda4
                @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
                public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                    return this.f$0.m98xeb7306b2(jUpdateSessionConfigSynchronous, completer, totalCaptureResult);
                }
            };
            this.mSessionListenerForAeMode = captureResultListener;
            this.mCameraControl.addCaptureResultListener(captureResultListener);
        }
    }

    /* JADX INFO: renamed from: lambda$enableExternalFlashAeMode$6$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ boolean m98xeb7306b2(long j, CallbackToFutureAdapter.Completer completer, TotalCaptureResult totalCaptureResult) {
        boolean z = ((Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_MODE)).intValue() == 5;
        Logger.d(TAG, "enableExternalFlashAeMode: isAeModeExternalFlash = " + z);
        if (z != this.mIsExternalFlashAeModeEnabled || !Camera2CameraControlImpl.isSessionUpdated(totalCaptureResult, j)) {
            return false;
        }
        Logger.d(TAG, "enableExternalFlashAeMode: session updated with isAeModeExternalFlash = " + z);
        if (completer != null) {
            completer.set(null);
        }
        return true;
    }

    private void disableAutoCancel() {
        ScheduledFuture<?> scheduledFuture = this.mAutoCancelHandle;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mAutoCancelHandle = null;
        }
    }

    private void clearAutoFocusTimeoutHandle() {
        ScheduledFuture<?> scheduledFuture = this.mAutoFocusTimeoutHandle;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mAutoFocusTimeoutHandle = null;
        }
    }

    int getDefaultAfMode() {
        return this.mTemplate != 3 ? 4 : 3;
    }

    private boolean isAfModeSupported() {
        return this.mCameraControl.getSupportedAfMode(1) == 1;
    }

    void completeActionFuture(boolean z) {
        clearAutoFocusTimeoutHandle();
        CallbackToFutureAdapter.Completer<FocusMeteringResult> completer = this.mRunningActionCompleter;
        if (completer != null) {
            completer.set(FocusMeteringResult.create(z));
            this.mRunningActionCompleter = null;
        }
    }

    private void failActionFuture(String str) {
        this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForFocus);
        CallbackToFutureAdapter.Completer<FocusMeteringResult> completer = this.mRunningActionCompleter;
        if (completer != null) {
            completer.setException(new CameraControl.OperationCanceledException(str));
            this.mRunningActionCompleter = null;
        }
    }

    private void failCancelFuture(String str) {
        this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForCancel);
        CallbackToFutureAdapter.Completer<Void> completer = this.mRunningCancelCompleter;
        if (completer != null) {
            completer.setException(new CameraControl.OperationCanceledException(str));
            this.mRunningCancelCompleter = null;
        }
    }

    private void completeCancelFuture() {
        CallbackToFutureAdapter.Completer<Void> completer = this.mRunningCancelCompleter;
        if (completer != null) {
            completer.set(null);
            this.mRunningCancelCompleter = null;
        }
    }

    private void executeMeteringAction(MeteringRectangle[] meteringRectangleArr, MeteringRectangle[] meteringRectangleArr2, MeteringRectangle[] meteringRectangleArr3, FocusMeteringAction focusMeteringAction, long j) {
        final long jUpdateSessionConfigSynchronous;
        this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForFocus);
        disableAutoCancel();
        clearAutoFocusTimeoutHandle();
        this.mAfRects = meteringRectangleArr;
        this.mAeRects = meteringRectangleArr2;
        this.mAwbRects = meteringRectangleArr3;
        if (shouldTriggerAF()) {
            this.mIsInAfAutoMode = true;
            this.mIsAutoFocusCompleted = false;
            this.mIsFocusSuccessful = false;
            jUpdateSessionConfigSynchronous = this.mCameraControl.updateSessionConfigSynchronous();
            triggerAf(null, true);
        } else {
            this.mIsInAfAutoMode = false;
            this.mIsAutoFocusCompleted = true;
            this.mIsFocusSuccessful = false;
            jUpdateSessionConfigSynchronous = this.mCameraControl.updateSessionConfigSynchronous();
        }
        this.mCurrentAfState = 0;
        final boolean zIsAfModeSupported = isAfModeSupported();
        Camera2CameraControlImpl.CaptureResultListener captureResultListener = new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda12
            @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
            public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                return this.f$0.m101xcac1cdee(zIsAfModeSupported, jUpdateSessionConfigSynchronous, totalCaptureResult);
            }
        };
        this.mSessionListenerForFocus = captureResultListener;
        this.mCameraControl.addCaptureResultListener(captureResultListener);
        final long j2 = this.mFocusTimeoutCounter + 1;
        this.mFocusTimeoutCounter = j2;
        this.mAutoFocusTimeoutHandle = this.mScheduler.schedule(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m103x1d6a7870(j2);
            }
        }, j, TimeUnit.MILLISECONDS);
        if (focusMeteringAction.isAutoCancelEnabled()) {
            this.mAutoCancelHandle = this.mScheduler.schedule(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m100x5ba3a0b1(j2);
                }
            }, focusMeteringAction.getAutoCancelDurationInMillis(), TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: renamed from: lambda$executeMeteringAction$7$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ boolean m101xcac1cdee(boolean z, long j, TotalCaptureResult totalCaptureResult) {
        Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
        if (shouldTriggerAF()) {
            if (!z || num == null) {
                this.mIsFocusSuccessful = true;
                this.mIsAutoFocusCompleted = true;
            } else if (this.mCurrentAfState.intValue() == 3) {
                if (num.intValue() == 4) {
                    this.mIsFocusSuccessful = true;
                    this.mIsAutoFocusCompleted = true;
                } else if (num.intValue() == 5) {
                    this.mIsFocusSuccessful = false;
                    this.mIsAutoFocusCompleted = true;
                }
            }
        }
        if (this.mIsAutoFocusCompleted && Camera2CameraControlImpl.isSessionUpdated(totalCaptureResult, j)) {
            completeActionFuture(this.mIsFocusSuccessful);
            return true;
        }
        if (!this.mCurrentAfState.equals(num) && num != null) {
            this.mCurrentAfState = num;
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$executeMeteringAction$9$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ void m103x1d6a7870(final long j) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m102xf416232f(j);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$executeMeteringAction$8$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ void m102xf416232f(long j) {
        if (j == this.mFocusTimeoutCounter) {
            this.mIsFocusSuccessful = false;
            completeActionFuture(false);
        }
    }

    /* JADX INFO: renamed from: lambda$executeMeteringAction$11$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ void m100x5ba3a0b1(final long j) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m99x324f4b70(j);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$executeMeteringAction$10$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ void m99x324f4b70(long j) {
        if (j == this.mFocusTimeoutCounter) {
            cancelFocusAndMeteringWithoutAsyncResult();
        }
    }

    private boolean shouldTriggerAF() {
        return this.mAfRects.length > 0;
    }

    ListenableFuture<Void> cancelFocusAndMetering() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda2
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m94xb951626b(completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$cancelFocusAndMetering$13$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ Object m94xb951626b(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m93x8ffd0d2a(completer);
            }
        });
        return "cancelFocusAndMetering";
    }

    void cancelFocusAndMeteringWithoutAsyncResult() {
        m93x8ffd0d2a(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: cancelFocusAndMeteringInternal, reason: merged with bridge method [inline-methods] */
    public void m93x8ffd0d2a(CallbackToFutureAdapter.Completer<Void> completer) {
        failCancelFuture("Cancelled by another cancelFocusAndMetering()");
        failActionFuture("Cancelled by cancelFocusAndMetering()");
        this.mRunningCancelCompleter = completer;
        disableAutoCancel();
        clearAutoFocusTimeoutHandle();
        if (shouldTriggerAF()) {
            cancelAfAeTrigger(true, false);
        }
        MeteringRectangle[] meteringRectangleArr = EMPTY_RECTANGLES;
        this.mAfRects = meteringRectangleArr;
        this.mAeRects = meteringRectangleArr;
        this.mAwbRects = meteringRectangleArr;
        this.mIsInAfAutoMode = false;
        final long jUpdateSessionConfigSynchronous = this.mCameraControl.updateSessionConfigSynchronous();
        if (this.mRunningCancelCompleter != null) {
            final int supportedAfMode = this.mCameraControl.getSupportedAfMode(getDefaultAfMode());
            Camera2CameraControlImpl.CaptureResultListener captureResultListener = new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.FocusMeteringControl$$ExternalSyntheticLambda11
                @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
                public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                    return this.f$0.m95x244b226f(supportedAfMode, jUpdateSessionConfigSynchronous, totalCaptureResult);
                }
            };
            this.mSessionListenerForCancel = captureResultListener;
            this.mCameraControl.addCaptureResultListener(captureResultListener);
        }
    }

    /* JADX INFO: renamed from: lambda$cancelFocusAndMeteringInternal$14$androidx-camera-camera2-internal-FocusMeteringControl, reason: not valid java name */
    /* synthetic */ boolean m95x244b226f(int i, long j, TotalCaptureResult totalCaptureResult) {
        if (((Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_MODE)).intValue() != i || !Camera2CameraControlImpl.isSessionUpdated(totalCaptureResult, j)) {
            return false;
        }
        completeCancelFuture();
        return true;
    }

    boolean isFocusMeteringSupported(FocusMeteringAction focusMeteringAction) {
        Rect cropSensorRegion = this.mCameraControl.getCropSensorRegion();
        Rational defaultAspectRatio = getDefaultAspectRatio();
        return (getMeteringRectangles(focusMeteringAction.getMeteringPointsAf(), this.mCameraControl.getMaxAfRegionCount(), defaultAspectRatio, cropSensorRegion, 1).isEmpty() && getMeteringRectangles(focusMeteringAction.getMeteringPointsAe(), this.mCameraControl.getMaxAeRegionCount(), defaultAspectRatio, cropSensorRegion, 2).isEmpty() && getMeteringRectangles(focusMeteringAction.getMeteringPointsAwb(), this.mCameraControl.getMaxAwbRegionCount(), defaultAspectRatio, cropSensorRegion, 4).isEmpty()) ? false : true;
    }
}

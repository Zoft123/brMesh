package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.camera.camera2.internal.Camera2CaptureCallbacks;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RequestMonitor {
    private static final String TAG = "RequestMonitor";
    private final boolean mQuirkEnabled;
    private final List<ListenableFuture<Void>> mRequestTasks = Collections.synchronizedList(new ArrayList());

    static /* synthetic */ Void lambda$getRequestsProcessedFuture$0(List list) {
        return null;
    }

    public RequestMonitor(boolean z) {
        this.mQuirkEnabled = z;
    }

    public boolean shouldMonitorRequest() {
        return this.mQuirkEnabled;
    }

    public ListenableFuture<Void> getRequestsProcessedFuture() {
        if (this.mRequestTasks.isEmpty()) {
            return Futures.immediateFuture(null);
        }
        return Futures.nonCancellationPropagating(Futures.transform(Futures.successfulAsList(new ArrayList(this.mRequestTasks)), new Function() { // from class: androidx.camera.camera2.internal.compat.workaround.RequestMonitor$$ExternalSyntheticLambda1
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return RequestMonitor.lambda$getRequestsProcessedFuture$0((List) obj);
            }
        }, CameraXExecutors.directExecutor()));
    }

    public CameraCaptureSession.CaptureCallback createMonitorListener(CameraCaptureSession.CaptureCallback captureCallback) {
        return shouldMonitorRequest() ? Camera2CaptureCallbacks.createComboCallback(createMonitorListener(), captureCallback) : captureCallback;
    }

    private CameraCaptureSession.CaptureCallback createMonitorListener() {
        final RequestCompleteListener requestCompleteListener = new RequestCompleteListener();
        final ListenableFuture<Void> listenableFuture = requestCompleteListener.mStartRequestFuture;
        this.mRequestTasks.add(listenableFuture);
        Log.d(TAG, "RequestListener " + requestCompleteListener + " monitoring " + this);
        listenableFuture.addListener(new Runnable() { // from class: androidx.camera.camera2.internal.compat.workaround.RequestMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m151x78e11639(requestCompleteListener, listenableFuture);
            }
        }, CameraXExecutors.directExecutor());
        return requestCompleteListener;
    }

    /* JADX INFO: renamed from: lambda$createMonitorListener$1$androidx-camera-camera2-internal-compat-workaround-RequestMonitor, reason: not valid java name */
    /* synthetic */ void m151x78e11639(RequestCompleteListener requestCompleteListener, ListenableFuture listenableFuture) {
        Log.d(TAG, "RequestListener " + requestCompleteListener + " done " + this);
        this.mRequestTasks.remove(listenableFuture);
    }

    public void stop() {
        LinkedList linkedList = new LinkedList(this.mRequestTasks);
        while (!linkedList.isEmpty()) {
            ((ListenableFuture) Objects.requireNonNull((ListenableFuture) linkedList.poll())).cancel(true);
        }
    }

    static class RequestCompleteListener extends CameraCaptureSession.CaptureCallback {
        CallbackToFutureAdapter.Completer<Void> mStartRequestCompleter;
        final ListenableFuture<Void> mStartRequestFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.compat.workaround.RequestMonitor$RequestCompleteListener$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m152x4a889b3e(completer);
            }
        });

        RequestCompleteListener() {
        }

        /* JADX INFO: renamed from: lambda$new$0$androidx-camera-camera2-internal-compat-workaround-RequestMonitor$RequestCompleteListener, reason: not valid java name */
        /* synthetic */ Object m152x4a889b3e(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mStartRequestCompleter = completer;
            return "RequestCompleteListener[" + this + "]";
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
            completeFuture();
        }

        private void completeFuture() {
            CallbackToFutureAdapter.Completer<Void> completer = this.mStartRequestCompleter;
            if (completer != null) {
                completer.set(null);
                this.mStartRequestCompleter = null;
            }
        }
    }
}

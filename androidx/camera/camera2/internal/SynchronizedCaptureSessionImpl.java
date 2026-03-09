package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.camera2.internal.compat.quirk.CaptureSessionStuckQuirk;
import androidx.camera.camera2.internal.compat.quirk.IncorrectCaptureStateQuirk;
import androidx.camera.camera2.internal.compat.workaround.ForceCloseCaptureSession;
import androidx.camera.camera2.internal.compat.workaround.ForceCloseDeferrableSurface;
import androidx.camera.camera2.internal.compat.workaround.RequestMonitor;
import androidx.camera.camera2.internal.compat.workaround.SessionResetPolicy;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
class SynchronizedCaptureSessionImpl extends SynchronizedCaptureSessionBaseImpl {
    private static final String TAG = "SyncCaptureSessionImpl";
    private final ForceCloseDeferrableSurface mCloseSurfaceQuirk;
    private final AtomicBoolean mClosed;
    private List<DeferrableSurface> mDeferrableSurfaces;
    private final ForceCloseCaptureSession mForceCloseSessionQuirk;
    private final Object mObjectLock;
    ListenableFuture<List<Void>> mOpenSessionBlockerFuture;
    private final RequestMonitor mRequestMonitor;
    private final ScheduledExecutorService mScheduledExecutorService;
    private final SessionResetPolicy mSessionResetPolicy;

    SynchronizedCaptureSessionImpl(Quirks quirks, Quirks quirks2, CaptureSessionRepository captureSessionRepository, Executor executor, ScheduledExecutorService scheduledExecutorService, Handler handler) {
        super(captureSessionRepository, executor, scheduledExecutorService, handler);
        this.mObjectLock = new Object();
        this.mClosed = new AtomicBoolean(false);
        this.mCloseSurfaceQuirk = new ForceCloseDeferrableSurface(quirks, quirks2);
        this.mRequestMonitor = new RequestMonitor(quirks.contains(CaptureSessionStuckQuirk.class) || quirks.contains(IncorrectCaptureStateQuirk.class));
        this.mForceCloseSessionQuirk = new ForceCloseCaptureSession(quirks2);
        this.mSessionResetPolicy = new SessionResetPolicy(quirks2);
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession.Opener
    public ListenableFuture<Void> openCaptureSession(final CameraDevice cameraDevice, final SessionConfigurationCompat sessionConfigurationCompat, final List<DeferrableSurface> list) {
        ListenableFuture<Void> listenableFutureNonCancellationPropagating;
        synchronized (this.mObjectLock) {
            List<SynchronizedCaptureSession> captureSessions = this.mCaptureSessionRepository.getCaptureSessions();
            ArrayList arrayList = new ArrayList();
            Iterator<SynchronizedCaptureSession> it = captureSessions.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getOpeningBlocker());
            }
            ListenableFuture<List<Void>> listenableFutureSuccessfulAsList = Futures.successfulAsList(arrayList);
            this.mOpenSessionBlockerFuture = listenableFutureSuccessfulAsList;
            listenableFutureNonCancellationPropagating = Futures.nonCancellationPropagating(FutureChain.from(listenableFutureSuccessfulAsList).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.SynchronizedCaptureSessionImpl$$ExternalSyntheticLambda2
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f$0.m120xe055f2f(cameraDevice, sessionConfigurationCompat, list, (List) obj);
                }
            }, getExecutor()));
        }
        return listenableFutureNonCancellationPropagating;
    }

    /* JADX INFO: renamed from: lambda$openCaptureSession$0$androidx-camera-camera2-internal-SynchronizedCaptureSessionImpl, reason: not valid java name */
    /* synthetic */ ListenableFuture m120xe055f2f(CameraDevice cameraDevice, SessionConfigurationCompat sessionConfigurationCompat, List list, List list2) throws Exception {
        if (this.mSessionResetPolicy.needAbortCapture()) {
            closeCreatedSession();
        }
        debugLog("start openCaptureSession");
        return super.openCaptureSession(cameraDevice, sessionConfigurationCompat, list);
    }

    private void closeCreatedSession() {
        Iterator<SynchronizedCaptureSession> it = this.mCaptureSessionRepository.getCaptureSessions().iterator();
        while (it.hasNext()) {
            it.next().close();
        }
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession
    public ListenableFuture<Void> getOpeningBlocker() {
        return Futures.makeTimeoutFuture(1500L, this.mScheduledExecutorService, this.mRequestMonitor.getRequestsProcessedFuture());
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession.Opener
    public ListenableFuture<List<Surface>> startWithDeferrableSurface(List<DeferrableSurface> list, long j) {
        ListenableFuture<List<Surface>> listenableFutureStartWithDeferrableSurface;
        synchronized (this.mObjectLock) {
            this.mDeferrableSurfaces = list;
            listenableFutureStartWithDeferrableSurface = super.startWithDeferrableSurface(list, j);
        }
        return listenableFutureStartWithDeferrableSurface;
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession.Opener
    public boolean stop() {
        boolean zStop;
        synchronized (this.mObjectLock) {
            if (isCameraCaptureSessionOpen()) {
                this.mCloseSurfaceQuirk.onSessionEnd(this.mDeferrableSurfaces);
            } else {
                ListenableFuture<List<Void>> listenableFuture = this.mOpenSessionBlockerFuture;
                if (listenableFuture != null) {
                    listenableFuture.cancel(true);
                }
            }
            zStop = super.stop();
        }
        return zStop;
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession
    public int setSingleRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        return super.setSingleRepeatingRequest(captureRequest, this.mRequestMonitor.createMonitorListener(captureCallback));
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession
    public int captureBurstRequests(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        return super.captureBurstRequests(list, this.mRequestMonitor.createMonitorListener(captureCallback));
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
    public void onConfigured(SynchronizedCaptureSession synchronizedCaptureSession) {
        debugLog("Session onConfigured()");
        this.mForceCloseSessionQuirk.onSessionConfigured(synchronizedCaptureSession, this.mCaptureSessionRepository.getCreatingCaptureSessions(), this.mCaptureSessionRepository.getCaptureSessions(), new ForceCloseCaptureSession.OnConfigured() { // from class: androidx.camera.camera2.internal.SynchronizedCaptureSessionImpl$$ExternalSyntheticLambda0
            @Override // androidx.camera.camera2.internal.compat.workaround.ForceCloseCaptureSession.OnConfigured
            public final void run(SynchronizedCaptureSession synchronizedCaptureSession2) {
                this.f$0.m119xa3e3cc53(synchronizedCaptureSession2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onConfigured$1$androidx-camera-camera2-internal-SynchronizedCaptureSessionImpl, reason: not valid java name */
    /* synthetic */ void m119xa3e3cc53(SynchronizedCaptureSession synchronizedCaptureSession) {
        super.onConfigured(synchronizedCaptureSession);
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession
    public void close() {
        if (!this.mClosed.compareAndSet(false, true)) {
            debugLog("close() has been called. Skip this invocation.");
            return;
        }
        if (this.mSessionResetPolicy.needAbortCapture()) {
            try {
                debugLog("Call abortCaptures() before closing session.");
                abortCaptures();
            } catch (Exception e) {
                debugLog("Exception when calling abortCaptures()" + e);
            }
        }
        debugLog("Session call close()");
        this.mRequestMonitor.getRequestsProcessedFuture().addListener(new Runnable() { // from class: androidx.camera.camera2.internal.SynchronizedCaptureSessionImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m118xfa0843b5();
            }
        }, getExecutor());
    }

    /* JADX INFO: renamed from: lambda$close$2$androidx-camera-camera2-internal-SynchronizedCaptureSessionImpl, reason: not valid java name */
    /* synthetic */ void m118xfa0843b5() {
        debugLog("Session call super.close()");
        super.close();
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
    public void onClosed(SynchronizedCaptureSession synchronizedCaptureSession) {
        synchronized (this.mObjectLock) {
            this.mCloseSurfaceQuirk.onSessionEnd(this.mDeferrableSurfaces);
        }
        debugLog("onClosed()");
        super.onClosed(synchronizedCaptureSession);
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession
    public void finishClose() {
        super.finishClose();
        this.mRequestMonitor.stop();
    }

    @Override // androidx.camera.camera2.internal.SynchronizedCaptureSessionBaseImpl, androidx.camera.camera2.internal.SynchronizedCaptureSession
    public void onCameraDeviceError(int i) {
        super.onCameraDeviceError(i);
        if (i == 5) {
            synchronized (this.mObjectLock) {
                if (isCameraCaptureSessionOpen() && this.mDeferrableSurfaces != null) {
                    debugLog("Close DeferrableSurfaces for CameraDevice error.");
                    Iterator<DeferrableSurface> it = this.mDeferrableSurfaces.iterator();
                    while (it.hasNext()) {
                        it.next().close();
                    }
                }
            }
        }
    }

    void debugLog(String str) {
        Logger.d(TAG, "[" + this + "] " + str);
    }
}

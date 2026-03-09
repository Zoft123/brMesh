package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.imagecapture.TakePictureRequest;
import androidx.camera.core.impl.utils.Threads;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RequestWithCallback implements TakePictureCallback {
    private CallbackToFutureAdapter.Completer<Void> mCaptureCompleter;
    private ListenableFuture<Void> mCaptureRequestFuture;
    private CallbackToFutureAdapter.Completer<Void> mCompleteCompleter;
    private final TakePictureRequest.RetryControl mRetryControl;
    private final TakePictureRequest mTakePictureRequest;
    private boolean mIsAborted = false;
    private boolean mIsStarted = false;
    private final ListenableFuture<Void> mCaptureFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.imagecapture.RequestWithCallback$$ExternalSyntheticLambda0
        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
            return this.f$0.m193xfdcdcb4d(completer);
        }
    });
    private final ListenableFuture<Void> mCompleteFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.imagecapture.RequestWithCallback$$ExternalSyntheticLambda1
        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
            return this.f$0.m194x2722208e(completer);
        }
    });

    RequestWithCallback(TakePictureRequest takePictureRequest, TakePictureRequest.RetryControl retryControl) {
        this.mTakePictureRequest = takePictureRequest;
        this.mRetryControl = retryControl;
    }

    /* JADX INFO: renamed from: lambda$new$0$androidx-camera-core-imagecapture-RequestWithCallback, reason: not valid java name */
    /* synthetic */ Object m193xfdcdcb4d(CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCaptureCompleter = completer;
        return "CaptureCompleteFuture";
    }

    /* JADX INFO: renamed from: lambda$new$1$androidx-camera-core-imagecapture-RequestWithCallback, reason: not valid java name */
    /* synthetic */ Object m194x2722208e(CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCompleteCompleter = completer;
        return "RequestCompleteFuture";
    }

    public void setCaptureRequestFuture(ListenableFuture<Void> listenableFuture) {
        Threads.checkMainThread();
        Preconditions.checkState(this.mCaptureRequestFuture == null, "CaptureRequestFuture can only be set once.");
        this.mCaptureRequestFuture = listenableFuture;
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onCaptureStarted() {
        Threads.checkMainThread();
        if (this.mIsAborted || this.mIsStarted) {
            return;
        }
        this.mIsStarted = true;
        ImageCapture.OnImageCapturedCallback inMemoryCallback = this.mTakePictureRequest.getInMemoryCallback();
        if (inMemoryCallback != null) {
            inMemoryCallback.onCaptureStarted();
        }
        ImageCapture.OnImageSavedCallback onDiskCallback = this.mTakePictureRequest.getOnDiskCallback();
        if (onDiskCallback != null) {
            onDiskCallback.onCaptureStarted();
        }
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onImageCaptured() {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        if (!this.mIsStarted) {
            onCaptureStarted();
        }
        this.mCaptureCompleter.set(null);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onFinalResult(ImageCapture.OutputFileResults outputFileResults) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        checkOnImageCaptured();
        markComplete();
        this.mTakePictureRequest.onResult(outputFileResults);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onFinalResult(ImageProxy imageProxy) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            imageProxy.close();
            return;
        }
        checkOnImageCaptured();
        markComplete();
        this.mTakePictureRequest.onResult(imageProxy);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onCaptureProcessProgressed(int i) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        this.mTakePictureRequest.onCaptureProcessProgressed(i);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onPostviewBitmapAvailable(Bitmap bitmap) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        this.mTakePictureRequest.onPostviewBitmapAvailable(bitmap);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onProcessFailure(ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        checkOnImageCaptured();
        markComplete();
        onFailure(imageCaptureException);
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public boolean isAborted() {
        return this.mIsAborted;
    }

    @Override // androidx.camera.core.imagecapture.TakePictureCallback
    public void onCaptureFailure(ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        if (this.mIsAborted) {
            return;
        }
        boolean zDecrementRetryCounter = this.mTakePictureRequest.decrementRetryCounter();
        if (!zDecrementRetryCounter) {
            onFailure(imageCaptureException);
        }
        markComplete();
        this.mCaptureCompleter.setException(imageCaptureException);
        if (zDecrementRetryCounter) {
            this.mRetryControl.retryRequest(this.mTakePictureRequest);
        }
    }

    void abortAndSendErrorToApp(ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        if (this.mCompleteFuture.isDone()) {
            return;
        }
        abort(imageCaptureException);
        onFailure(imageCaptureException);
    }

    void abortSilentlyAndRetry() {
        Threads.checkMainThread();
        if (this.mCompleteFuture.isDone()) {
            return;
        }
        abort(new ImageCaptureException(3, "The request is aborted silently and retried.", null));
        this.mRetryControl.retryRequest(this.mTakePictureRequest);
    }

    private void abort(ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        this.mIsAborted = true;
        ((ListenableFuture) Objects.requireNonNull(this.mCaptureRequestFuture)).cancel(true);
        this.mCaptureCompleter.setException(imageCaptureException);
        this.mCompleteCompleter.set(null);
    }

    ListenableFuture<Void> getCaptureFuture() {
        Threads.checkMainThread();
        return this.mCaptureFuture;
    }

    ListenableFuture<Void> getCompleteFuture() {
        Threads.checkMainThread();
        return this.mCompleteFuture;
    }

    public TakePictureRequest getTakePictureRequest() {
        return this.mTakePictureRequest;
    }

    private void checkOnImageCaptured() {
        Preconditions.checkState(this.mCaptureFuture.isDone(), "onImageCaptured() must be called before onFinalResult()");
    }

    private void markComplete() {
        Preconditions.checkState(!this.mCompleteFuture.isDone(), "The callback can only complete once.");
        this.mCompleteCompleter.set(null);
    }

    private void onFailure(ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        this.mTakePictureRequest.onError(imageCaptureException);
    }
}

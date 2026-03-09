package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureStage;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ProcessingRequest {
    static final int PROGRESS_NOT_RECEIVED = -1;
    private final TakePictureCallback mCallback;
    final ListenableFuture<Void> mCaptureFuture;
    private final Rect mCropRect;
    private final int mJpegQuality;
    private int mLastCaptureProcessProgressed;
    private final ImageCapture.OutputFileOptions mOutputFileOptions;
    private final int mRequestId;
    private final int mRotationDegrees;
    private final Matrix mSensorToBufferTransform;
    private final List<Integer> mStageIds;
    private final String mTagBundleKey;

    ProcessingRequest(CaptureBundle captureBundle, ImageCapture.OutputFileOptions outputFileOptions, Rect rect, int i, int i2, Matrix matrix, TakePictureCallback takePictureCallback, ListenableFuture<Void> listenableFuture) {
        this(captureBundle, outputFileOptions, rect, i, i2, matrix, takePictureCallback, listenableFuture, 0);
    }

    ProcessingRequest(CaptureBundle captureBundle, ImageCapture.OutputFileOptions outputFileOptions, Rect rect, int i, int i2, Matrix matrix, TakePictureCallback takePictureCallback, ListenableFuture<Void> listenableFuture, int i3) {
        this.mLastCaptureProcessProgressed = -1;
        this.mRequestId = i3;
        this.mOutputFileOptions = outputFileOptions;
        this.mJpegQuality = i2;
        this.mRotationDegrees = i;
        this.mCropRect = rect;
        this.mSensorToBufferTransform = matrix;
        this.mCallback = takePictureCallback;
        this.mTagBundleKey = String.valueOf(captureBundle.hashCode());
        this.mStageIds = new ArrayList();
        Iterator it = ((List) Objects.requireNonNull(captureBundle.getCaptureStages())).iterator();
        while (it.hasNext()) {
            this.mStageIds.add(Integer.valueOf(((CaptureStage) it.next()).getId()));
        }
        this.mCaptureFuture = listenableFuture;
    }

    String getTagBundleKey() {
        return this.mTagBundleKey;
    }

    List<Integer> getStageIds() {
        return this.mStageIds;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    ImageCapture.OutputFileOptions getOutputFileOptions() {
        return this.mOutputFileOptions;
    }

    Rect getCropRect() {
        return this.mCropRect;
    }

    int getRotationDegrees() {
        return this.mRotationDegrees;
    }

    int getJpegQuality() {
        return this.mJpegQuality;
    }

    Matrix getSensorToBufferTransform() {
        return this.mSensorToBufferTransform;
    }

    boolean isInMemoryCapture() {
        return getOutputFileOptions() == null;
    }

    void onCaptureStarted() {
        this.mCallback.onCaptureStarted();
    }

    void onCaptureProcessProgressed(int i) {
        if (this.mLastCaptureProcessProgressed != i) {
            this.mLastCaptureProcessProgressed = i;
            this.mCallback.onCaptureProcessProgressed(i);
        }
    }

    void onImageCaptured() {
        if (this.mLastCaptureProcessProgressed != -1) {
            onCaptureProcessProgressed(100);
        }
        this.mCallback.onImageCaptured();
    }

    void onFinalResult(ImageCapture.OutputFileResults outputFileResults) {
        this.mCallback.onFinalResult(outputFileResults);
    }

    void onPostviewBitmapAvailable(Bitmap bitmap) {
        this.mCallback.onPostviewBitmapAvailable(bitmap);
    }

    void onFinalResult(ImageProxy imageProxy) {
        this.mCallback.onFinalResult(imageProxy);
    }

    void onProcessFailure(ImageCaptureException imageCaptureException) {
        this.mCallback.onProcessFailure(imageCaptureException);
    }

    void onCaptureFailure(ImageCaptureException imageCaptureException) {
        this.mCallback.onCaptureFailure(imageCaptureException);
    }

    boolean isAborted() {
        return this.mCallback.isAborted();
    }

    ListenableFuture<Void> getCaptureFuture() {
        return this.mCaptureFuture;
    }
}

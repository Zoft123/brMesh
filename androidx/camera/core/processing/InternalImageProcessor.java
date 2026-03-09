package androidx.camera.core.processing;

import androidx.camera.core.CameraEffect;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProcessor;
import androidx.camera.core.ProcessingException;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import j$.util.Objects;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class InternalImageProcessor {
    private final Consumer<Throwable> mErrorListener;
    private final Executor mExecutor;
    private final ImageProcessor mImageProcessor;

    public InternalImageProcessor(CameraEffect cameraEffect) {
        Preconditions.checkArgument(cameraEffect.getTargets() == 4);
        this.mExecutor = cameraEffect.getExecutor();
        this.mImageProcessor = (ImageProcessor) Objects.requireNonNull(cameraEffect.getImageProcessor());
        this.mErrorListener = cameraEffect.getErrorListener();
    }

    public ImageProcessor.Response safeProcess(final ImageProcessor.Request request) throws ImageCaptureException {
        try {
            return (ImageProcessor.Response) CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.InternalImageProcessor$$ExternalSyntheticLambda1
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m228xe9947096(request, completer);
                }
            }).get();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            Exception cause2 = e;
            if (cause != null) {
                cause2 = e.getCause();
            }
            throw new ImageCaptureException(0, "Failed to invoke ImageProcessor.", cause2);
        }
    }

    /* JADX INFO: renamed from: lambda$safeProcess$1$androidx-camera-core-processing-InternalImageProcessor, reason: not valid java name */
    /* synthetic */ Object m228xe9947096(final ImageProcessor.Request request, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.InternalImageProcessor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m227xe85e1db7(request, completer);
            }
        });
        return "InternalImageProcessor#process " + request.hashCode();
    }

    /* JADX INFO: renamed from: lambda$safeProcess$0$androidx-camera-core-processing-InternalImageProcessor, reason: not valid java name */
    /* synthetic */ void m227xe85e1db7(ImageProcessor.Request request, CallbackToFutureAdapter.Completer completer) {
        try {
            completer.set(this.mImageProcessor.process(request));
        } catch (ProcessingException e) {
            this.mErrorListener.accept(e);
            completer.setException(e);
        }
    }
}

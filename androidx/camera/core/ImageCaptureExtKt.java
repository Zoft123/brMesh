package androidx.camera.core;

import android.graphics.Bitmap;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.imagecapture.RequestWithCallback;
import androidx.camera.core.imagecapture.TakePictureRequest;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;

/* JADX INFO: compiled from: ImageCaptureExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0001\u001aT\u0010\u0003\u001a\u00020\u0004*\u00020\u00022\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\t2\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0007\u0018\u00010\tH\u0086@¢\u0006\u0002\u0010\r\u001a\\\u0010\u0003\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\t2\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0007\u0018\u00010\tH\u0086@¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"getTakePictureRequest", "Landroidx/camera/core/imagecapture/TakePictureRequest;", "Landroidx/camera/core/ImageCapture;", "takePicture", "Landroidx/camera/core/ImageProxy;", "onCaptureStarted", "Lkotlin/Function0;", "", "onCaptureProcessProgressed", "Lkotlin/Function1;", "", "onPostviewBitmapAvailable", "Landroid/graphics/Bitmap;", "(Landroidx/camera/core/ImageCapture;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/core/ImageCapture$OutputFileResults;", "outputFileOptions", "Landroidx/camera/core/ImageCapture$OutputFileOptions;", "(Landroidx/camera/core/ImageCapture;Landroidx/camera/core/ImageCapture$OutputFileOptions;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "camera-core_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ImageCaptureExtKt {
    public static /* synthetic */ Object takePicture$default(ImageCapture imageCapture, Function0 function0, Function1 function1, Function1 function12, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        if ((i & 2) != 0) {
            function1 = null;
        }
        if ((i & 4) != 0) {
            function12 = null;
        }
        return takePicture(imageCapture, function0, function1, function12, continuation);
    }

    public static /* synthetic */ Object takePicture$default(ImageCapture imageCapture, ImageCapture.OutputFileOptions outputFileOptions, Function0 function0, Function1 function1, Function1 function12, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 4) != 0) {
            function1 = null;
        }
        if ((i & 8) != 0) {
            function12 = null;
        }
        return takePicture(imageCapture, outputFileOptions, function0, function1, function12, continuation);
    }

    public static final TakePictureRequest getTakePictureRequest(ImageCapture imageCapture) {
        Intrinsics.checkNotNullParameter(imageCapture, "<this>");
        RequestWithCallback capturingRequest = imageCapture.getTakePictureManager().getCapturingRequest();
        if (capturingRequest != null) {
            return capturingRequest.getTakePictureRequest();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, androidx.camera.core.DelegatingImageCapturedCallback] */
    public static final Object takePicture(ImageCapture imageCapture, final Function0<Unit> function0, final Function1<? super Integer, Unit> function1, final Function1<? super Bitmap, Unit> function12, Continuation<? super ImageProxy> continuation) {
        Executor executorDirectExecutor;
        CoroutineContext.Element element = continuation.get$context().get(ContinuationInterceptor.INSTANCE);
        DelegatingImageCapturedCallback delegatingImageCapturedCallback = null;
        CoroutineDispatcher coroutineDispatcher = element instanceof CoroutineDispatcher ? (CoroutineDispatcher) element : null;
        if (coroutineDispatcher == null || (executorDirectExecutor = ExecutorsKt.asExecutor(coroutineDispatcher)) == null) {
            executorDirectExecutor = CameraXExecutors.directExecutor();
            Intrinsics.checkNotNullExpressionValue(executorDirectExecutor, "directExecutor()");
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new DelegatingImageCapturedCallback(new ImageCapture.OnImageCapturedCallback() { // from class: androidx.camera.core.ImageCaptureExtKt$takePicture$2$1
            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onCaptureStarted() {
                Function0<Unit> function02 = function0;
                if (function02 != null) {
                    function02.invoke();
                }
            }

            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onCaptureProcessProgressed(int progress) {
                Function1<Integer, Unit> function13 = function1;
                if (function13 != null) {
                    function13.invoke(Integer.valueOf(progress));
                }
            }

            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onPostviewBitmapAvailable(Bitmap bitmap) {
                Intrinsics.checkNotNullParameter(bitmap, "bitmap");
                Function1<Bitmap, Unit> function13 = function12;
                if (function13 != null) {
                    function13.invoke(bitmap);
                }
            }

            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onCaptureSuccess(ImageProxy imageProxy) {
                DelegatingImageCapturedCallback delegatingImageCapturedCallback2;
                Intrinsics.checkNotNullParameter(imageProxy, "imageProxy");
                if (objectRef.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
                    delegatingImageCapturedCallback2 = null;
                } else {
                    delegatingImageCapturedCallback2 = objectRef.element;
                }
                delegatingImageCapturedCallback2.dispose();
                CancellableContinuation<ImageProxy> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m1359constructorimpl(imageProxy));
            }

            @Override // androidx.camera.core.ImageCapture.OnImageCapturedCallback
            public void onError(ImageCaptureException exception) {
                DelegatingImageCapturedCallback delegatingImageCapturedCallback2;
                Intrinsics.checkNotNullParameter(exception, "exception");
                if (objectRef.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
                    delegatingImageCapturedCallback2 = null;
                } else {
                    delegatingImageCapturedCallback2 = objectRef.element;
                }
                delegatingImageCapturedCallback2.dispose();
                CancellableContinuation<ImageProxy> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m1359constructorimpl(ResultKt.createFailure(exception)));
            }
        });
        cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: androidx.camera.core.ImageCaptureExtKt$takePicture$2$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                DelegatingImageCapturedCallback delegatingImageCapturedCallback2;
                if (objectRef.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
                    delegatingImageCapturedCallback2 = null;
                } else {
                    delegatingImageCapturedCallback2 = objectRef.element;
                }
                delegatingImageCapturedCallback2.dispose();
            }
        });
        if (objectRef.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
        } else {
            delegatingImageCapturedCallback = (DelegatingImageCapturedCallback) objectRef.element;
        }
        imageCapture.m173lambda$takePicture$1$androidxcameracoreImageCapture(executorDirectExecutor, delegatingImageCapturedCallback);
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, androidx.camera.core.DelegatingImageSavedCallback] */
    public static final Object takePicture(ImageCapture imageCapture, ImageCapture.OutputFileOptions outputFileOptions, final Function0<Unit> function0, final Function1<? super Integer, Unit> function1, final Function1<? super Bitmap, Unit> function12, Continuation<? super ImageCapture.OutputFileResults> continuation) {
        Executor executorDirectExecutor;
        CoroutineContext.Element element = continuation.get$context().get(ContinuationInterceptor.INSTANCE);
        DelegatingImageSavedCallback delegatingImageSavedCallback = null;
        CoroutineDispatcher coroutineDispatcher = element instanceof CoroutineDispatcher ? (CoroutineDispatcher) element : null;
        if (coroutineDispatcher == null || (executorDirectExecutor = ExecutorsKt.asExecutor(coroutineDispatcher)) == null) {
            executorDirectExecutor = CameraXExecutors.directExecutor();
            Intrinsics.checkNotNullExpressionValue(executorDirectExecutor, "directExecutor()");
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new DelegatingImageSavedCallback(new ImageCapture.OnImageSavedCallback() { // from class: androidx.camera.core.ImageCaptureExtKt$takePicture$4$1
            @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
            public void onCaptureStarted() {
                Function0<Unit> function02 = function0;
                if (function02 != null) {
                    function02.invoke();
                }
            }

            @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
            public void onCaptureProcessProgressed(int progress) {
                Function1<Integer, Unit> function13 = function1;
                if (function13 != null) {
                    function13.invoke(Integer.valueOf(progress));
                }
            }

            @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
            public void onPostviewBitmapAvailable(Bitmap bitmap) {
                Intrinsics.checkNotNullParameter(bitmap, "bitmap");
                Function1<Bitmap, Unit> function13 = function12;
                if (function13 != null) {
                    function13.invoke(bitmap);
                }
            }

            @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
            public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                DelegatingImageSavedCallback delegatingImageSavedCallback2;
                Intrinsics.checkNotNullParameter(outputFileResults, "outputFileResults");
                if (objectRef.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
                    delegatingImageSavedCallback2 = null;
                } else {
                    delegatingImageSavedCallback2 = objectRef.element;
                }
                delegatingImageSavedCallback2.dispose();
                CancellableContinuation<ImageCapture.OutputFileResults> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m1359constructorimpl(outputFileResults));
            }

            @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
            public void onError(ImageCaptureException exception) {
                DelegatingImageSavedCallback delegatingImageSavedCallback2;
                Intrinsics.checkNotNullParameter(exception, "exception");
                if (objectRef.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
                    delegatingImageSavedCallback2 = null;
                } else {
                    delegatingImageSavedCallback2 = objectRef.element;
                }
                delegatingImageSavedCallback2.dispose();
                CancellableContinuation<ImageCapture.OutputFileResults> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m1359constructorimpl(ResultKt.createFailure(exception)));
            }
        });
        cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: androidx.camera.core.ImageCaptureExtKt$takePicture$4$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                DelegatingImageSavedCallback delegatingImageSavedCallback2;
                if (objectRef.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
                    delegatingImageSavedCallback2 = null;
                } else {
                    delegatingImageSavedCallback2 = objectRef.element;
                }
                delegatingImageSavedCallback2.dispose();
            }
        });
        if (objectRef.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("delegatingCallback");
        } else {
            delegatingImageSavedCallback = (DelegatingImageSavedCallback) objectRef.element;
        }
        imageCapture.m174lambda$takePicture$2$androidxcameracoreImageCapture(outputFileOptions, executorDirectExecutor, delegatingImageSavedCallback);
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}

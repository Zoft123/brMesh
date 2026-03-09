package androidx.camera.core.processing;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import androidx.arch.core.util.Function;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.ImageProcessingUtil;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.utils.MatrixExt;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.processing.util.GLUtils;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Triple;

/* JADX INFO: loaded from: classes.dex */
public class DefaultSurfaceProcessor implements SurfaceProcessorInternal, SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "DefaultSurfaceProcessor";
    private final Executor mGlExecutor;
    final Handler mGlHandler;
    private final OpenGlRenderer mGlRenderer;
    final HandlerThread mGlThread;
    private int mInputSurfaceCount;
    private final AtomicBoolean mIsReleaseRequested;
    private boolean mIsReleased;
    final Map<SurfaceOutput, Surface> mOutputSurfaces;
    private final List<PendingSnapshot> mPendingSnapshots;
    private final float[] mSurfaceOutputMatrix;
    private final float[] mTextureMatrix;

    static /* synthetic */ void lambda$executeSafely$11() {
    }

    DefaultSurfaceProcessor(DynamicRange dynamicRange) {
        this(dynamicRange, Collections.EMPTY_MAP);
    }

    DefaultSurfaceProcessor(DynamicRange dynamicRange, Map<GLUtils.InputFormat, ShaderProvider> map) {
        this.mIsReleaseRequested = new AtomicBoolean(false);
        this.mTextureMatrix = new float[16];
        this.mSurfaceOutputMatrix = new float[16];
        this.mOutputSurfaces = new LinkedHashMap();
        this.mInputSurfaceCount = 0;
        this.mIsReleased = false;
        this.mPendingSnapshots = new ArrayList();
        HandlerThread handlerThread = new HandlerThread("GL Thread");
        this.mGlThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mGlHandler = handler;
        this.mGlExecutor = CameraXExecutors.newHandlerExecutor(handler);
        this.mGlRenderer = new OpenGlRenderer();
        try {
            initGlRenderer(dynamicRange, map);
        } catch (RuntimeException e) {
            release();
            throw e;
        }
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onInputSurface(final SurfaceRequest surfaceRequest) {
        if (this.mIsReleaseRequested.get()) {
            surfaceRequest.willNotProvideSurface();
            return;
        }
        Runnable runnable = new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m221x1447cb3f(surfaceRequest);
            }
        };
        Objects.requireNonNull(surfaceRequest);
        executeSafely(runnable, new DefaultSurfaceProcessor$$ExternalSyntheticLambda2(surfaceRequest));
    }

    /* JADX INFO: renamed from: lambda$onInputSurface$2$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m221x1447cb3f(final SurfaceRequest surfaceRequest) {
        this.mInputSurfaceCount++;
        final SurfaceTexture surfaceTexture = new SurfaceTexture(this.mGlRenderer.getTextureName());
        surfaceTexture.setDefaultBufferSize(surfaceRequest.getResolution().getWidth(), surfaceRequest.getResolution().getHeight());
        final Surface surface = new Surface(surfaceTexture);
        surfaceRequest.setTransformationInfoListener(this.mGlExecutor, new SurfaceRequest.TransformationInfoListener() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda8
            @Override // androidx.camera.core.SurfaceRequest.TransformationInfoListener
            public final void onTransformationInfoUpdate(SurfaceRequest.TransformationInfo transformationInfo) {
                this.f$0.m219xc91fb93d(surfaceRequest, transformationInfo);
            }
        });
        surfaceRequest.provideSurface(surface, this.mGlExecutor, new Consumer() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda9
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.m220xeeb3c23e(surfaceRequest, surfaceTexture, surface, (SurfaceRequest.Result) obj);
            }
        });
        surfaceTexture.setOnFrameAvailableListener(this, this.mGlHandler);
    }

    /* JADX INFO: renamed from: lambda$onInputSurface$0$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m219xc91fb93d(SurfaceRequest surfaceRequest, SurfaceRequest.TransformationInfo transformationInfo) {
        GLUtils.InputFormat inputFormat = GLUtils.InputFormat.DEFAULT;
        if (surfaceRequest.getDynamicRange().is10BitHdr() && transformationInfo.hasCameraTransform()) {
            inputFormat = GLUtils.InputFormat.YUV;
        }
        this.mGlRenderer.setInputFormat(inputFormat);
    }

    /* JADX INFO: renamed from: lambda$onInputSurface$1$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m220xeeb3c23e(SurfaceRequest surfaceRequest, SurfaceTexture surfaceTexture, Surface surface, SurfaceRequest.Result result) {
        surfaceRequest.clearTransformationInfoListener();
        surfaceTexture.setOnFrameAvailableListener(null);
        surfaceTexture.release();
        surface.release();
        this.mInputSurfaceCount--;
        checkReadyToRelease();
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onOutputSurface(final SurfaceOutput surfaceOutput) {
        if (this.mIsReleaseRequested.get()) {
            surfaceOutput.close();
            return;
        }
        Runnable runnable = new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m223x2d273c72(surfaceOutput);
            }
        };
        Objects.requireNonNull(surfaceOutput);
        executeSafely(runnable, new DefaultSurfaceProcessor$$ExternalSyntheticLambda13(surfaceOutput));
    }

    /* JADX INFO: renamed from: lambda$onOutputSurface$4$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m223x2d273c72(final SurfaceOutput surfaceOutput) {
        Surface surface = surfaceOutput.getSurface(this.mGlExecutor, new Consumer() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda7
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.m222x7933371(surfaceOutput, (SurfaceOutput.Event) obj);
            }
        });
        this.mGlRenderer.registerOutputSurface(surface);
        this.mOutputSurfaces.put(surfaceOutput, surface);
    }

    /* JADX INFO: renamed from: lambda$onOutputSurface$3$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m222x7933371(SurfaceOutput surfaceOutput, SurfaceOutput.Event event) {
        surfaceOutput.close();
        Surface surfaceRemove = this.mOutputSurfaces.remove(surfaceOutput);
        if (surfaceRemove != null) {
            this.mGlRenderer.unregisterOutputSurface(surfaceRemove);
        }
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorInternal
    public void release() {
        if (this.mIsReleaseRequested.getAndSet(true)) {
            return;
        }
        executeSafely(new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m224xba38c72d();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$release$5$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m224xba38c72d() {
        this.mIsReleased = true;
        checkReadyToRelease();
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorInternal
    public ListenableFuture<Void> snapshot(final int i, final int i2) {
        return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda5
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m226xe90bf147(i, i2, completer);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$snapshot$8$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ Object m226xe90bf147(int i, int i2, final CallbackToFutureAdapter.Completer completer) throws Exception {
        final AutoValue_DefaultSurfaceProcessor_PendingSnapshot autoValue_DefaultSurfaceProcessor_PendingSnapshotOf = PendingSnapshot.of(i, i2, completer);
        executeSafely(new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m225x9de3df45(autoValue_DefaultSurfaceProcessor_PendingSnapshotOf);
            }
        }, new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                completer.setException(new Exception("Failed to snapshot: OpenGLRenderer not ready."));
            }
        });
        return "DefaultSurfaceProcessor#snapshot";
    }

    /* JADX INFO: renamed from: lambda$snapshot$6$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m225x9de3df45(PendingSnapshot pendingSnapshot) {
        this.mPendingSnapshots.add(pendingSnapshot);
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (this.mIsReleaseRequested.get()) {
            return;
        }
        surfaceTexture.updateTexImage();
        surfaceTexture.getTransformMatrix(this.mTextureMatrix);
        Triple<Surface, Size, float[]> triple = null;
        for (Map.Entry<SurfaceOutput, Surface> entry : this.mOutputSurfaces.entrySet()) {
            Surface value = entry.getValue();
            SurfaceOutput key = entry.getKey();
            key.updateTransformMatrix(this.mSurfaceOutputMatrix, this.mTextureMatrix);
            if (key.getFormat() == 34) {
                try {
                    this.mGlRenderer.render(surfaceTexture.getTimestamp(), this.mSurfaceOutputMatrix, value);
                } catch (RuntimeException e) {
                    Logger.e(TAG, "Failed to render with OpenGL.", e);
                }
            } else {
                Preconditions.checkState(key.getFormat() == 256, "Unsupported format: " + key.getFormat());
                Preconditions.checkState(triple == null, "Only one JPEG output is supported.");
                triple = new Triple<>(value, key.getSize(), (float[]) this.mSurfaceOutputMatrix.clone());
            }
        }
        try {
            takeSnapshotAndDrawJpeg(triple);
        } catch (RuntimeException e2) {
            failAllPendingSnapshots(e2);
        }
    }

    private void takeSnapshotAndDrawJpeg(Triple<Surface, Size, float[]> triple) {
        if (this.mPendingSnapshots.isEmpty()) {
            return;
        }
        if (triple == null) {
            failAllPendingSnapshots(new Exception("Failed to snapshot: no JPEG Surface."));
            return;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                Iterator<PendingSnapshot> it = this.mPendingSnapshots.iterator();
                int rotationDegrees = -1;
                int jpegQuality = -1;
                Bitmap bitmap = null;
                byte[] byteArray = null;
                while (it.hasNext()) {
                    PendingSnapshot next = it.next();
                    if (rotationDegrees != next.getRotationDegrees() || bitmap == null) {
                        rotationDegrees = next.getRotationDegrees();
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        bitmap = getBitmap(triple.getSecond(), triple.getThird(), rotationDegrees);
                        jpegQuality = -1;
                    }
                    if (jpegQuality != next.getJpegQuality()) {
                        byteArrayOutputStream.reset();
                        jpegQuality = next.getJpegQuality();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, jpegQuality, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                    }
                    ImageProcessingUtil.writeJpegBytesToSurface(triple.getFirst(), (byte[]) Objects.requireNonNull(byteArray));
                    next.getCompleter().set(null);
                    it.remove();
                }
                byteArrayOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            failAllPendingSnapshots(e);
        }
    }

    private void failAllPendingSnapshots(Throwable th) {
        Iterator<PendingSnapshot> it = this.mPendingSnapshots.iterator();
        while (it.hasNext()) {
            it.next().getCompleter().setException(th);
        }
        this.mPendingSnapshots.clear();
    }

    private Bitmap getBitmap(Size size, float[] fArr, int i) {
        float[] fArr2 = (float[]) fArr.clone();
        MatrixExt.preRotate(fArr2, i, 0.5f, 0.5f);
        MatrixExt.preVerticalFlip(fArr2, 0.5f);
        return this.mGlRenderer.snapshot(TransformUtils.rotateSize(size, i), fArr2);
    }

    private void checkReadyToRelease() {
        if (this.mIsReleased && this.mInputSurfaceCount == 0) {
            Iterator<SurfaceOutput> it = this.mOutputSurfaces.keySet().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            Iterator<PendingSnapshot> it2 = this.mPendingSnapshots.iterator();
            while (it2.hasNext()) {
                it2.next().getCompleter().setException(new Exception("Failed to snapshot: DefaultSurfaceProcessor is released."));
            }
            this.mOutputSurfaces.clear();
            this.mGlRenderer.release();
            this.mGlThread.quit();
        }
    }

    private void initGlRenderer(final DynamicRange dynamicRange, final Map<GLUtils.InputFormat, ShaderProvider> map) {
        try {
            CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda10
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m217x3628f8a0(dynamicRange, map, completer);
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            boolean z = e instanceof ExecutionException;
            Throwable cause = e;
            if (z) {
                cause = e.getCause();
            }
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new IllegalStateException("Failed to create DefaultSurfaceProcessor", cause);
        }
    }

    /* JADX INFO: renamed from: lambda$initGlRenderer$10$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ Object m217x3628f8a0(final DynamicRange dynamicRange, final Map map, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executeSafely(new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m218x5398b3bc(dynamicRange, map, completer);
            }
        });
        return "Init GlRenderer";
    }

    /* JADX INFO: renamed from: lambda$initGlRenderer$9$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m218x5398b3bc(DynamicRange dynamicRange, Map map, CallbackToFutureAdapter.Completer completer) {
        try {
            this.mGlRenderer.init(dynamicRange, map);
            completer.set(null);
        } catch (RuntimeException e) {
            completer.setException(e);
        }
    }

    private void executeSafely(Runnable runnable) {
        executeSafely(runnable, new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                DefaultSurfaceProcessor.lambda$executeSafely$11();
            }
        });
    }

    private void executeSafely(final Runnable runnable, final Runnable runnable2) {
        try {
            this.mGlExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m216x436c3965(runnable2, runnable);
                }
            });
        } catch (RejectedExecutionException e) {
            Logger.w(TAG, "Unable to executor runnable", e);
            runnable2.run();
        }
    }

    /* JADX INFO: renamed from: lambda$executeSafely$12$androidx-camera-core-processing-DefaultSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m216x436c3965(Runnable runnable, Runnable runnable2) {
        if (this.mIsReleased) {
            runnable.run();
        } else {
            runnable2.run();
        }
    }

    static abstract class PendingSnapshot {
        abstract CallbackToFutureAdapter.Completer<Void> getCompleter();

        abstract int getJpegQuality();

        abstract int getRotationDegrees();

        PendingSnapshot() {
        }

        static AutoValue_DefaultSurfaceProcessor_PendingSnapshot of(int i, int i2, CallbackToFutureAdapter.Completer<Void> completer) {
            return new AutoValue_DefaultSurfaceProcessor_PendingSnapshot(i, i2, completer);
        }
    }

    public static class Factory {
        private static Function<DynamicRange, SurfaceProcessorInternal> sSupplier = new Function() { // from class: androidx.camera.core.processing.DefaultSurfaceProcessor$Factory$$ExternalSyntheticLambda0
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return new DefaultSurfaceProcessor((DynamicRange) obj);
            }
        };

        private Factory() {
        }

        public static SurfaceProcessorInternal newInstance(DynamicRange dynamicRange) {
            return sSupplier.apply(dynamicRange);
        }

        public static void setSupplier(Function<DynamicRange, SurfaceProcessorInternal> function) {
            sSupplier = function;
        }
    }
}

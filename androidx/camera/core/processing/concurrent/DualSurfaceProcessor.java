package androidx.camera.core.processing.concurrent;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.LayoutSettings;
import androidx.camera.core.Logger;
import androidx.camera.core.ProcessingException;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda13;
import androidx.camera.core.processing.DefaultSurfaceProcessor$$ExternalSyntheticLambda2;
import androidx.camera.core.processing.ShaderProvider;
import androidx.camera.core.processing.SurfaceProcessorInternal;
import androidx.camera.core.processing.util.GLUtils;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes.dex */
public class DualSurfaceProcessor implements SurfaceProcessorInternal, SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "DualSurfaceProcessor";
    private final Executor mGlExecutor;
    final Handler mGlHandler;
    private final DualOpenGlRenderer mGlRenderer;
    final HandlerThread mGlThread;
    private int mInputSurfaceCount;
    private final AtomicBoolean mIsReleaseRequested;
    private boolean mIsReleased;
    final Map<SurfaceOutput, Surface> mOutputSurfaces;
    private SurfaceTexture mPrimarySurfaceTexture;
    private SurfaceTexture mSecondarySurfaceTexture;

    static /* synthetic */ void lambda$executeSafely$7() {
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorInternal
    public /* synthetic */ ListenableFuture snapshot(int i, int i2) {
        return Futures.immediateFuture(null);
    }

    DualSurfaceProcessor(DynamicRange dynamicRange, LayoutSettings layoutSettings, LayoutSettings layoutSettings2) {
        this(dynamicRange, Collections.EMPTY_MAP, layoutSettings, layoutSettings2);
    }

    DualSurfaceProcessor(DynamicRange dynamicRange, Map<GLUtils.InputFormat, ShaderProvider> map, LayoutSettings layoutSettings, LayoutSettings layoutSettings2) {
        this.mInputSurfaceCount = 0;
        this.mIsReleased = false;
        this.mIsReleaseRequested = new AtomicBoolean(false);
        this.mOutputSurfaces = new LinkedHashMap();
        HandlerThread handlerThread = new HandlerThread("GL Thread");
        this.mGlThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mGlHandler = handler;
        this.mGlExecutor = CameraXExecutors.newHandlerExecutor(handler);
        this.mGlRenderer = new DualOpenGlRenderer(layoutSettings, layoutSettings2);
        try {
            initGlRenderer(dynamicRange, map);
        } catch (RuntimeException e) {
            release();
            throw e;
        }
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onInputSurface(final SurfaceRequest surfaceRequest) throws ProcessingException {
        if (this.mIsReleaseRequested.get()) {
            surfaceRequest.willNotProvideSurface();
            return;
        }
        Runnable runnable = new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m245xb7e95d0f(surfaceRequest);
            }
        };
        Objects.requireNonNull(surfaceRequest);
        executeSafely(runnable, new DefaultSurfaceProcessor$$ExternalSyntheticLambda2(surfaceRequest));
    }

    /* JADX INFO: renamed from: lambda$onInputSurface$1$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m245xb7e95d0f(SurfaceRequest surfaceRequest) {
        this.mInputSurfaceCount++;
        final SurfaceTexture surfaceTexture = new SurfaceTexture(this.mGlRenderer.getTextureName(surfaceRequest.isPrimary()));
        surfaceTexture.setDefaultBufferSize(surfaceRequest.getResolution().getWidth(), surfaceRequest.getResolution().getHeight());
        final Surface surface = new Surface(surfaceTexture);
        surfaceRequest.provideSurface(surface, this.mGlExecutor, new Consumer() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.m244x6a29e50e(surfaceTexture, surface, (SurfaceRequest.Result) obj);
            }
        });
        if (surfaceRequest.isPrimary()) {
            this.mPrimarySurfaceTexture = surfaceTexture;
        } else {
            this.mSecondarySurfaceTexture = surfaceTexture;
            surfaceTexture.setOnFrameAvailableListener(this, this.mGlHandler);
        }
    }

    /* JADX INFO: renamed from: lambda$onInputSurface$0$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m244x6a29e50e(SurfaceTexture surfaceTexture, Surface surface, SurfaceRequest.Result result) {
        surfaceTexture.setOnFrameAvailableListener(null);
        surfaceTexture.release();
        surface.release();
        this.mInputSurfaceCount--;
        checkReadyToRelease();
    }

    @Override // androidx.camera.core.SurfaceProcessor
    public void onOutputSurface(final SurfaceOutput surfaceOutput) throws ProcessingException {
        if (this.mIsReleaseRequested.get()) {
            surfaceOutput.close();
            return;
        }
        Runnable runnable = new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m247x4069eb42(surfaceOutput);
            }
        };
        Objects.requireNonNull(surfaceOutput);
        executeSafely(runnable, new DefaultSurfaceProcessor$$ExternalSyntheticLambda13(surfaceOutput));
    }

    /* JADX INFO: renamed from: lambda$onOutputSurface$3$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m247x4069eb42(final SurfaceOutput surfaceOutput) {
        Surface surface = surfaceOutput.getSurface(this.mGlExecutor, new Consumer() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda6
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.m246xf2aa7341(surfaceOutput, (SurfaceOutput.Event) obj);
            }
        });
        this.mGlRenderer.registerOutputSurface(surface);
        this.mOutputSurfaces.put(surfaceOutput, surface);
    }

    /* JADX INFO: renamed from: lambda$onOutputSurface$2$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m246xf2aa7341(SurfaceOutput surfaceOutput, SurfaceOutput.Event event) {
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
        executeSafely(new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m248x5e4e8afd();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$release$4$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m248x5e4e8afd() {
        this.mIsReleased = true;
        checkReadyToRelease();
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        SurfaceTexture surfaceTexture2;
        if (this.mIsReleaseRequested.get() || (surfaceTexture2 = this.mPrimarySurfaceTexture) == null || this.mSecondarySurfaceTexture == null) {
            return;
        }
        surfaceTexture2.updateTexImage();
        this.mSecondarySurfaceTexture.updateTexImage();
        for (Map.Entry<SurfaceOutput, Surface> entry : this.mOutputSurfaces.entrySet()) {
            Surface value = entry.getValue();
            SurfaceOutput key = entry.getKey();
            if (key.getFormat() == 34) {
                try {
                    this.mGlRenderer.render(surfaceTexture.getTimestamp(), value, key, this.mPrimarySurfaceTexture, this.mSecondarySurfaceTexture);
                } catch (RuntimeException e) {
                    Logger.e(TAG, "Failed to render with OpenGL.", e);
                }
            }
        }
    }

    private void initGlRenderer(final DynamicRange dynamicRange, final Map<GLUtils.InputFormat, ShaderProvider> map) {
        try {
            CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda3
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f$0.m243x75bd888a(dynamicRange, map, completer);
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

    /* JADX INFO: renamed from: lambda$initGlRenderer$6$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ Object m243x75bd888a(final DynamicRange dynamicRange, final Map map, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executeSafely(new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m242x27fe1089(dynamicRange, map, completer);
            }
        });
        return "Init GlRenderer";
    }

    /* JADX INFO: renamed from: lambda$initGlRenderer$5$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m242x27fe1089(DynamicRange dynamicRange, Map map, CallbackToFutureAdapter.Completer completer) {
        try {
            this.mGlRenderer.init(dynamicRange, map);
            completer.set(null);
        } catch (RuntimeException e) {
            completer.setException(e);
        }
    }

    private void executeSafely(Runnable runnable) {
        executeSafely(runnable, new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                DualSurfaceProcessor.lambda$executeSafely$7();
            }
        });
    }

    private void executeSafely(final Runnable runnable, final Runnable runnable2) {
        try {
            this.mGlExecutor.execute(new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m241x6156b869(runnable2, runnable);
                }
            });
        } catch (RejectedExecutionException e) {
            Logger.w(TAG, "Unable to executor runnable", e);
            runnable2.run();
        }
    }

    /* JADX INFO: renamed from: lambda$executeSafely$8$androidx-camera-core-processing-concurrent-DualSurfaceProcessor, reason: not valid java name */
    /* synthetic */ void m241x6156b869(Runnable runnable, Runnable runnable2) {
        if (this.mIsReleased) {
            runnable.run();
        } else {
            runnable2.run();
        }
    }

    private void checkReadyToRelease() {
        if (this.mIsReleased && this.mInputSurfaceCount == 0) {
            Iterator<SurfaceOutput> it = this.mOutputSurfaces.keySet().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            this.mOutputSurfaces.clear();
            this.mGlRenderer.release();
            this.mGlThread.quit();
        }
    }

    public static class Factory {
        private static Function3<DynamicRange, LayoutSettings, LayoutSettings, SurfaceProcessorInternal> sSupplier = new Function3() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessor$Factory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return new DualSurfaceProcessor((DynamicRange) obj, (LayoutSettings) obj2, (LayoutSettings) obj3);
            }
        };

        private Factory() {
        }

        public static SurfaceProcessorInternal newInstance(DynamicRange dynamicRange, LayoutSettings layoutSettings, LayoutSettings layoutSettings2) {
            return sSupplier.invoke(dynamicRange, layoutSettings, layoutSettings2);
        }

        public static void setSupplier(Function3<DynamicRange, LayoutSettings, LayoutSettings, SurfaceProcessorInternal> function3) {
            sSupplier = function3;
        }
    }
}

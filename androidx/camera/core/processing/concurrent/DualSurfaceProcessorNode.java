package androidx.camera.core.processing.concurrent;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Size;
import androidx.camera.core.Logger;
import androidx.camera.core.ProcessingException;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.processing.Node;
import androidx.camera.core.processing.SurfaceEdge;
import androidx.camera.core.processing.SurfaceProcessorInternal;
import androidx.camera.core.processing.TargetUtils;
import androidx.camera.core.processing.util.OutConfig;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: classes.dex */
public class DualSurfaceProcessorNode implements Node<In, Out> {
    private static final String TAG = "DualSurfaceProcessorNode";
    private In mInput;
    private Out mOutput;
    final CameraInternal mPrimaryCameraInternal;
    final CameraInternal mSecondaryCameraInternal;
    final SurfaceProcessorInternal mSurfaceProcessor;

    public static class Out extends HashMap<DualOutConfig, SurfaceEdge> {
    }

    public DualSurfaceProcessorNode(CameraInternal cameraInternal, CameraInternal cameraInternal2, SurfaceProcessorInternal surfaceProcessorInternal) {
        this.mPrimaryCameraInternal = cameraInternal;
        this.mSecondaryCameraInternal = cameraInternal2;
        this.mSurfaceProcessor = surfaceProcessorInternal;
    }

    @Override // androidx.camera.core.processing.Node
    public Out transform(In in) {
        Threads.checkMainThread();
        this.mInput = in;
        this.mOutput = new Out();
        SurfaceEdge primarySurfaceEdge = this.mInput.getPrimarySurfaceEdge();
        SurfaceEdge secondarySurfaceEdge = this.mInput.getSecondarySurfaceEdge();
        for (DualOutConfig dualOutConfig : this.mInput.getOutConfigs()) {
            this.mOutput.put(dualOutConfig, transformSingleOutput(primarySurfaceEdge, dualOutConfig.getPrimaryOutConfig()));
        }
        sendSurfaceRequest(this.mPrimaryCameraInternal, primarySurfaceEdge, this.mOutput, true);
        sendSurfaceRequest(this.mSecondaryCameraInternal, secondarySurfaceEdge, this.mOutput, false);
        sendSurfaceOutputs(this.mPrimaryCameraInternal, this.mSecondaryCameraInternal, primarySurfaceEdge, secondarySurfaceEdge, this.mOutput);
        return this.mOutput;
    }

    private SurfaceEdge transformSingleOutput(SurfaceEdge surfaceEdge, OutConfig outConfig) {
        Rect cropRect = outConfig.getCropRect();
        int rotationDegrees = outConfig.getRotationDegrees();
        boolean zIsMirroring = outConfig.isMirroring();
        Matrix matrix = new Matrix();
        Preconditions.checkArgument(TransformUtils.isAspectRatioMatchingWithRoundingError(TransformUtils.getRotatedSize(cropRect, rotationDegrees), outConfig.getSize()));
        Rect rectSizeToRect = TransformUtils.sizeToRect(outConfig.getSize());
        return new SurfaceEdge(outConfig.getTargets(), outConfig.getFormat(), surfaceEdge.getStreamSpec().toBuilder().setResolution(outConfig.getSize()).build(), matrix, false, rectSizeToRect, surfaceEdge.getRotationDegrees() - rotationDegrees, -1, surfaceEdge.isMirroring() != zIsMirroring);
    }

    private void sendSurfaceRequest(CameraInternal cameraInternal, SurfaceEdge surfaceEdge, Map<DualOutConfig, SurfaceEdge> map, boolean z) {
        try {
            this.mSurfaceProcessor.onInputSurface(surfaceEdge.createSurfaceRequest(cameraInternal, z));
        } catch (ProcessingException e) {
            Logger.e(TAG, "Failed to send SurfaceRequest to SurfaceProcessor.", e);
        }
    }

    private void sendSurfaceOutputs(CameraInternal cameraInternal, CameraInternal cameraInternal2, SurfaceEdge surfaceEdge, SurfaceEdge surfaceEdge2, Map<DualOutConfig, SurfaceEdge> map) {
        for (final Map.Entry<DualOutConfig, SurfaceEdge> entry : map.entrySet()) {
            final CameraInternal cameraInternal3 = cameraInternal;
            final CameraInternal cameraInternal4 = cameraInternal2;
            final SurfaceEdge surfaceEdge3 = surfaceEdge;
            final SurfaceEdge surfaceEdge4 = surfaceEdge2;
            m250xacee03b(cameraInternal3, cameraInternal4, surfaceEdge3, surfaceEdge4, entry);
            entry.getValue().addOnInvalidatedListener(new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m250xacee03b(cameraInternal3, cameraInternal4, surfaceEdge3, surfaceEdge4, entry);
                }
            });
            cameraInternal = cameraInternal3;
            cameraInternal2 = cameraInternal4;
            surfaceEdge = surfaceEdge3;
            surfaceEdge2 = surfaceEdge4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: createAndSendSurfaceOutput, reason: merged with bridge method [inline-methods] */
    public void m250xacee03b(CameraInternal cameraInternal, CameraInternal cameraInternal2, SurfaceEdge surfaceEdge, SurfaceEdge surfaceEdge2, Map.Entry<DualOutConfig, SurfaceEdge> entry) {
        final SurfaceEdge value = entry.getValue();
        Size resolution = surfaceEdge.getStreamSpec().getResolution();
        Rect cropRect = entry.getKey().getPrimaryOutConfig().getCropRect();
        if (!surfaceEdge.hasCameraTransform()) {
            cameraInternal = null;
        }
        SurfaceOutput.CameraInputInfo cameraInputInfoOf = SurfaceOutput.CameraInputInfo.of(resolution, cropRect, cameraInternal, entry.getKey().getPrimaryOutConfig().getRotationDegrees(), entry.getKey().getPrimaryOutConfig().isMirroring());
        Size resolution2 = surfaceEdge2.getStreamSpec().getResolution();
        Rect cropRect2 = entry.getKey().getSecondaryOutConfig().getCropRect();
        if (!surfaceEdge2.hasCameraTransform()) {
            cameraInternal2 = null;
        }
        Futures.addCallback(value.createSurfaceOutputFuture(entry.getKey().getPrimaryOutConfig().getFormat(), cameraInputInfoOf, SurfaceOutput.CameraInputInfo.of(resolution2, cropRect2, cameraInternal2, entry.getKey().getSecondaryOutConfig().getRotationDegrees(), entry.getKey().getSecondaryOutConfig().isMirroring())), new FutureCallback<SurfaceOutput>() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(SurfaceOutput surfaceOutput) {
                Preconditions.checkNotNull(surfaceOutput);
                try {
                    DualSurfaceProcessorNode.this.mSurfaceProcessor.onOutputSurface(surfaceOutput);
                } catch (ProcessingException e) {
                    Logger.e(DualSurfaceProcessorNode.TAG, "Failed to send SurfaceOutput to SurfaceProcessor.", e);
                }
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                if (value.getTargets() == 2 && (th instanceof CancellationException)) {
                    Logger.d(DualSurfaceProcessorNode.TAG, "Downstream VideoCapture failed to provide Surface.");
                    return;
                }
                Logger.w(DualSurfaceProcessorNode.TAG, "Downstream node failed to provide Surface. Target: " + TargetUtils.getHumanReadableName(value.getTargets()), th);
            }
        }, CameraXExecutors.mainThreadExecutor());
    }

    @Override // androidx.camera.core.processing.Node
    public void release() {
        this.mSurfaceProcessor.release();
        Threads.runOnMain(new Runnable() { // from class: androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m249x5a17381c();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$release$1$androidx-camera-core-processing-concurrent-DualSurfaceProcessorNode, reason: not valid java name */
    /* synthetic */ void m249x5a17381c() {
        Out out = this.mOutput;
        if (out != null) {
            Iterator<SurfaceEdge> it = out.values().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        }
    }

    public static abstract class In {
        public abstract List<DualOutConfig> getOutConfigs();

        public abstract SurfaceEdge getPrimarySurfaceEdge();

        public abstract SurfaceEdge getSecondarySurfaceEdge();

        public static In of(SurfaceEdge surfaceEdge, SurfaceEdge surfaceEdge2, List<DualOutConfig> list) {
            return new AutoValue_DualSurfaceProcessorNode_In(surfaceEdge, surfaceEdge2, list);
        }
    }
}

package androidx.camera.core.streamsharing;

import android.graphics.Rect;
import android.util.Log;
import android.util.Size;
import androidx.camera.core.CameraEffect;
import androidx.camera.core.LayoutSettings;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.StreamSpec;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.processing.DefaultSurfaceProcessor;
import androidx.camera.core.processing.SurfaceEdge;
import androidx.camera.core.processing.SurfaceProcessorNode;
import androidx.camera.core.processing.concurrent.DualOutConfig;
import androidx.camera.core.processing.concurrent.DualSurfaceProcessor;
import androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode;
import androidx.camera.core.processing.util.OutConfig;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* JADX INFO: loaded from: classes.dex */
public class StreamSharing extends UseCase {
    private static final String TAG = "StreamSharing";
    private SurfaceEdge mCameraEdge;
    private SessionConfig.CloseableErrorListener mCloseableErrorListener;
    private final StreamSharingConfig mDefaultConfig;
    private DualSurfaceProcessorNode mDualSharingNode;
    private SurfaceProcessorNode mEffectNode;
    private final LayoutSettings mLayoutSettings;
    private SurfaceEdge mSecondaryCameraEdge;
    private final LayoutSettings mSecondaryLayoutSettings;
    SessionConfig.Builder mSecondarySessionConfigBuilder;
    private SurfaceEdge mSecondarySharingInputEdge;
    SessionConfig.Builder mSessionConfigBuilder;
    private SurfaceEdge mSharingInputEdge;
    private SurfaceProcessorNode mSharingNode;
    private final VirtualCameraAdapter mVirtualCameraAdapter;

    interface Control {
        ListenableFuture<Void> jpegSnapshot(int i, int i2);
    }

    private static StreamSharingConfig getDefaultConfig(Set<UseCase> set) {
        MutableConfig mutableConfig = new StreamSharingBuilder().getMutableConfig();
        mutableConfig.insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 34);
        ArrayList arrayList = new ArrayList();
        for (UseCase useCase : set) {
            if (useCase.getCurrentConfig().containsOption(UseCaseConfig.OPTION_CAPTURE_TYPE)) {
                arrayList.add(useCase.getCurrentConfig().getCaptureType());
            } else {
                Log.e(TAG, "A child does not have capture type.");
            }
        }
        mutableConfig.insertOption(StreamSharingConfig.OPTION_CAPTURE_TYPES, arrayList);
        mutableConfig.insertOption(ImageOutputConfig.OPTION_MIRROR_MODE, 2);
        return new StreamSharingConfig(OptionsBundle.from(mutableConfig));
    }

    public StreamSharing(CameraInternal cameraInternal, CameraInternal cameraInternal2, LayoutSettings layoutSettings, LayoutSettings layoutSettings2, Set<UseCase> set, UseCaseConfigFactory useCaseConfigFactory) {
        super(getDefaultConfig(set));
        this.mDefaultConfig = getDefaultConfig(set);
        this.mLayoutSettings = layoutSettings;
        this.mSecondaryLayoutSettings = layoutSettings2;
        this.mVirtualCameraAdapter = new VirtualCameraAdapter(cameraInternal, cameraInternal2, set, useCaseConfigFactory, new Control() { // from class: androidx.camera.core.streamsharing.StreamSharing$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.streamsharing.StreamSharing.Control
            public final ListenableFuture jpegSnapshot(int i, int i2) {
                return this.f$0.m252lambda$new$0$androidxcameracorestreamsharingStreamSharing(i, i2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$androidx-camera-core-streamsharing-StreamSharing, reason: not valid java name */
    /* synthetic */ ListenableFuture m252lambda$new$0$androidxcameracorestreamsharingStreamSharing(int i, int i2) {
        SurfaceProcessorNode surfaceProcessorNode = this.mSharingNode;
        if (surfaceProcessorNode != null) {
            return surfaceProcessorNode.getSurfaceProcessor().snapshot(i, i2);
        }
        return Futures.immediateFailedFuture(new Exception("Failed to take picture: pipeline is not ready."));
    }

    @Override // androidx.camera.core.UseCase
    public UseCaseConfig<?> getDefaultConfig(boolean z, UseCaseConfigFactory useCaseConfigFactory) {
        Config config = useCaseConfigFactory.getConfig(this.mDefaultConfig.getCaptureType(), 1);
        if (z) {
            config = Config.CC.mergeConfigs(config, this.mDefaultConfig.getConfig());
        }
        if (config == null) {
            return null;
        }
        return getUseCaseConfigBuilder(config).getUseCaseConfig();
    }

    @Override // androidx.camera.core.UseCase
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(Config config) {
        return new StreamSharingBuilder(MutableOptionsBundle.from(config));
    }

    @Override // androidx.camera.core.UseCase
    protected UseCaseConfig<?> onMergeConfig(CameraInfoInternal cameraInfoInternal, UseCaseConfig.Builder<?, ?, ?> builder) {
        this.mVirtualCameraAdapter.mergeChildrenConfigs(builder.getMutableConfig());
        return builder.getUseCaseConfig();
    }

    @Override // androidx.camera.core.UseCase
    protected StreamSpec onSuggestedStreamSpecUpdated(StreamSpec streamSpec, StreamSpec streamSpec2) {
        updateSessionConfig(createPipelineAndUpdateChildrenSpecs(getCameraId(), getSecondaryCameraId(), getCurrentConfig(), streamSpec, streamSpec2));
        notifyActive();
        return streamSpec;
    }

    @Override // androidx.camera.core.UseCase
    protected StreamSpec onSuggestedStreamSpecImplementationOptionsUpdated(Config config) {
        this.mSessionConfigBuilder.addImplementationOptions(config);
        updateSessionConfig(UByte$$ExternalSyntheticBackport0.m(new Object[]{this.mSessionConfigBuilder.build()}));
        return getAttachedStreamSpec().toBuilder().setImplementationOptions(config).build();
    }

    @Override // androidx.camera.core.UseCase
    public void onBind() {
        super.onBind();
        this.mVirtualCameraAdapter.bindChildren();
    }

    @Override // androidx.camera.core.UseCase
    public void onUnbind() {
        super.onUnbind();
        clearPipeline();
        this.mVirtualCameraAdapter.unbindChildren();
    }

    @Override // androidx.camera.core.UseCase
    public void onStateAttached() {
        super.onStateAttached();
        this.mVirtualCameraAdapter.notifyStateAttached();
    }

    @Override // androidx.camera.core.UseCase
    public void onStateDetached() {
        super.onStateDetached();
        this.mVirtualCameraAdapter.notifyStateDetached();
    }

    public Set<UseCase> getChildren() {
        return this.mVirtualCameraAdapter.getChildren();
    }

    @Override // androidx.camera.core.UseCase
    public Set<Integer> getSupportedEffectTargets() {
        HashSet hashSet = new HashSet();
        hashSet.add(3);
        return hashSet;
    }

    private List<SessionConfig> createPipelineAndUpdateChildrenSpecs(String str, String str2, UseCaseConfig<?> useCaseConfig, StreamSpec streamSpec, StreamSpec streamSpec2) {
        Threads.checkMainThread();
        if (streamSpec2 == null) {
            createPrimaryCamera(str, str2, useCaseConfig, streamSpec, null);
            this.mSharingNode = getSharingNode((CameraInternal) Objects.requireNonNull(getCamera()), streamSpec);
            Map<UseCase, OutConfig> childrenOutConfigs = this.mVirtualCameraAdapter.getChildrenOutConfigs(this.mSharingInputEdge, getTargetRotationInternal(), getViewPortCropRect() != null);
            SurfaceProcessorNode.Out outTransform2 = this.mSharingNode.transform(SurfaceProcessorNode.In.of(this.mSharingInputEdge, new ArrayList(childrenOutConfigs.values())));
            HashMap map = new HashMap();
            for (Map.Entry<UseCase, OutConfig> entry : childrenOutConfigs.entrySet()) {
                map.put(entry.getKey(), outTransform2.get(entry.getValue()));
            }
            this.mVirtualCameraAdapter.setChildrenEdges(map);
            return UByte$$ExternalSyntheticBackport0.m(new Object[]{this.mSessionConfigBuilder.build()});
        }
        createPrimaryCamera(str, str2, useCaseConfig, streamSpec, streamSpec2);
        createSecondaryCamera(str, str2, useCaseConfig, streamSpec, streamSpec2);
        this.mDualSharingNode = getDualSharingNode(getCamera(), getSecondaryCamera(), streamSpec, this.mLayoutSettings, this.mSecondaryLayoutSettings);
        Map<UseCase, DualOutConfig> childrenOutConfigs2 = this.mVirtualCameraAdapter.getChildrenOutConfigs(this.mSharingInputEdge, this.mSecondarySharingInputEdge, getTargetRotationInternal(), getViewPortCropRect() != null);
        DualSurfaceProcessorNode.Out outTransform22 = this.mDualSharingNode.transform(DualSurfaceProcessorNode.In.of(this.mSharingInputEdge, this.mSecondarySharingInputEdge, new ArrayList(childrenOutConfigs2.values())));
        HashMap map2 = new HashMap();
        for (Map.Entry<UseCase, DualOutConfig> entry2 : childrenOutConfigs2.entrySet()) {
            map2.put(entry2.getKey(), outTransform22.get(entry2.getValue()));
        }
        this.mVirtualCameraAdapter.setChildrenEdges(map2);
        return UByte$$ExternalSyntheticBackport0.m(new Object[]{this.mSessionConfigBuilder.build(), this.mSecondarySessionConfigBuilder.build()});
    }

    private void createPrimaryCamera(String str, String str2, UseCaseConfig<?> useCaseConfig, StreamSpec streamSpec, StreamSpec streamSpec2) {
        SurfaceEdge surfaceEdge = new SurfaceEdge(3, 34, streamSpec, getSensorToBufferTransformMatrix(), ((CameraInternal) Objects.requireNonNull(getCamera())).getHasTransform(), (Rect) Objects.requireNonNull(getCropRect(streamSpec.getResolution())), getRelativeRotation((CameraInternal) Objects.requireNonNull(getCamera())), -1, isMirroringRequired((CameraInternal) Objects.requireNonNull(getCamera())));
        this.mCameraEdge = surfaceEdge;
        this.mSharingInputEdge = getSharingInputEdge(surfaceEdge, (CameraInternal) Objects.requireNonNull(getCamera()));
        SessionConfig.Builder builderCreateSessionConfigBuilder = createSessionConfigBuilder(this.mCameraEdge, useCaseConfig, streamSpec);
        this.mSessionConfigBuilder = builderCreateSessionConfigBuilder;
        addCameraErrorListener(builderCreateSessionConfigBuilder, str, str2, useCaseConfig, streamSpec, streamSpec2);
    }

    private void createSecondaryCamera(String str, String str2, UseCaseConfig<?> useCaseConfig, StreamSpec streamSpec, StreamSpec streamSpec2) {
        SurfaceEdge surfaceEdge = new SurfaceEdge(3, 34, streamSpec2, getSensorToBufferTransformMatrix(), ((CameraInternal) Objects.requireNonNull(getSecondaryCamera())).getHasTransform(), (Rect) Objects.requireNonNull(getCropRect(streamSpec2.getResolution())), getRelativeRotation((CameraInternal) Objects.requireNonNull(getSecondaryCamera())), -1, isMirroringRequired((CameraInternal) Objects.requireNonNull(getSecondaryCamera())));
        this.mSecondaryCameraEdge = surfaceEdge;
        this.mSecondarySharingInputEdge = getSharingInputEdge(surfaceEdge, (CameraInternal) Objects.requireNonNull(getSecondaryCamera()));
        SessionConfig.Builder builderCreateSessionConfigBuilder = createSessionConfigBuilder(this.mSecondaryCameraEdge, useCaseConfig, streamSpec2);
        this.mSecondarySessionConfigBuilder = builderCreateSessionConfigBuilder;
        addCameraErrorListener(builderCreateSessionConfigBuilder, str, str2, useCaseConfig, streamSpec, streamSpec2);
    }

    private SessionConfig.Builder createSessionConfigBuilder(SurfaceEdge surfaceEdge, UseCaseConfig<?> useCaseConfig, StreamSpec streamSpec) {
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(useCaseConfig, streamSpec.getResolution());
        propagateChildrenTemplate(builderCreateFrom);
        propagateChildrenCamera2Interop(streamSpec.getResolution(), builderCreateFrom);
        builderCreateFrom.addSurface(surfaceEdge.getDeferrableSurface(), streamSpec.getDynamicRange(), null, -1);
        builderCreateFrom.addRepeatingCameraCaptureCallback(this.mVirtualCameraAdapter.getParentMetadataCallback());
        if (streamSpec.getImplementationOptions() != null) {
            builderCreateFrom.addImplementationOptions(streamSpec.getImplementationOptions());
        }
        return builderCreateFrom;
    }

    private void propagateChildrenTemplate(SessionConfig.Builder builder) {
        Iterator<UseCase> it = getChildren().iterator();
        int higherPriorityTemplateType = -1;
        while (it.hasNext()) {
            higherPriorityTemplateType = SessionConfig.getHigherPriorityTemplateType(higherPriorityTemplateType, getChildTemplate(it.next()));
        }
        if (higherPriorityTemplateType != -1) {
            builder.setTemplateType(higherPriorityTemplateType);
        }
    }

    private static int getChildTemplate(UseCase useCase) {
        return useCase.getCurrentConfig().getDefaultSessionConfig().getTemplateType();
    }

    private void propagateChildrenCamera2Interop(Size size, SessionConfig.Builder builder) {
        Iterator<UseCase> it = getChildren().iterator();
        while (it.hasNext()) {
            SessionConfig sessionConfigBuild = SessionConfig.Builder.createFrom(it.next().getCurrentConfig(), size).build();
            builder.addAllRepeatingCameraCaptureCallbacks(sessionConfigBuild.getRepeatingCameraCaptureCallbacks());
            builder.addAllCameraCaptureCallbacks(sessionConfigBuild.getSingleCameraCaptureCallbacks());
            builder.addAllSessionStateCallbacks(sessionConfigBuild.getSessionStateCallbacks());
            builder.addAllDeviceStateCallbacks(sessionConfigBuild.getDeviceStateCallbacks());
            builder.addImplementationOptions(sessionConfigBuild.getImplementationOptions());
        }
    }

    private SurfaceEdge getSharingInputEdge(SurfaceEdge surfaceEdge, CameraInternal cameraInternal) {
        if (getEffect() == null || getEffect().getTransformation() == 2 || getEffect().getOutputOption() == 1) {
            return surfaceEdge;
        }
        this.mEffectNode = new SurfaceProcessorNode(cameraInternal, getEffect().createSurfaceProcessorInternal());
        int rotationAppliedByEffect = getRotationAppliedByEffect();
        Rect cropRectAppliedByEffect = getCropRectAppliedByEffect(surfaceEdge);
        OutConfig outConfigOf = OutConfig.of(surfaceEdge.getTargets(), surfaceEdge.getFormat(), cropRectAppliedByEffect, TransformUtils.getRotatedSize(cropRectAppliedByEffect, rotationAppliedByEffect), rotationAppliedByEffect, getMirroringAppliedByEffect(), true);
        return (SurfaceEdge) Objects.requireNonNull(this.mEffectNode.transform(SurfaceProcessorNode.In.of(surfaceEdge, Collections.singletonList(outConfigOf))).get(outConfigOf));
    }

    private SurfaceProcessorNode getSharingNode(CameraInternal cameraInternal, StreamSpec streamSpec) {
        if (getEffect() != null && getEffect().getOutputOption() == 1) {
            SurfaceProcessorNode surfaceProcessorNode = new SurfaceProcessorNode(cameraInternal, getEffect().createSurfaceProcessorInternal());
            this.mEffectNode = surfaceProcessorNode;
            return surfaceProcessorNode;
        }
        return new SurfaceProcessorNode(cameraInternal, DefaultSurfaceProcessor.Factory.newInstance(streamSpec.getDynamicRange()));
    }

    private DualSurfaceProcessorNode getDualSharingNode(CameraInternal cameraInternal, CameraInternal cameraInternal2, StreamSpec streamSpec, LayoutSettings layoutSettings, LayoutSettings layoutSettings2) {
        return new DualSurfaceProcessorNode(cameraInternal, cameraInternal2, DualSurfaceProcessor.Factory.newInstance(streamSpec.getDynamicRange(), layoutSettings, layoutSettings2));
    }

    private int getRotationAppliedByEffect() {
        if (((CameraEffect) Preconditions.checkNotNull(getEffect())).getTransformation() == 1) {
            return getRelativeRotation((CameraInternal) Preconditions.checkNotNull(getCamera()));
        }
        return 0;
    }

    private boolean getMirroringAppliedByEffect() {
        if (((CameraEffect) Preconditions.checkNotNull(getEffect())).getTransformation() == 1) {
            CameraInternal cameraInternal = (CameraInternal) Preconditions.checkNotNull(getCamera());
            if (cameraInternal.isFrontFacing() && cameraInternal.getHasTransform()) {
                return true;
            }
        }
        return false;
    }

    private Rect getCropRectAppliedByEffect(SurfaceEdge surfaceEdge) {
        if (((CameraEffect) Preconditions.checkNotNull(getEffect())).getTransformation() == 1) {
            return TransformUtils.sizeToRect(surfaceEdge.getStreamSpec().getResolution());
        }
        return surfaceEdge.getCropRect();
    }

    private void addCameraErrorListener(SessionConfig.Builder builder, final String str, final String str2, final UseCaseConfig<?> useCaseConfig, final StreamSpec streamSpec, final StreamSpec streamSpec2) {
        SessionConfig.CloseableErrorListener closeableErrorListener = this.mCloseableErrorListener;
        if (closeableErrorListener != null) {
            closeableErrorListener.close();
        }
        SessionConfig.CloseableErrorListener closeableErrorListener2 = new SessionConfig.CloseableErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.streamsharing.StreamSharing$$ExternalSyntheticLambda1
            @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
            public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
                this.f$0.m251x83a3b685(str, str2, useCaseConfig, streamSpec, streamSpec2, sessionConfig, sessionError);
            }
        });
        this.mCloseableErrorListener = closeableErrorListener2;
        builder.setErrorListener(closeableErrorListener2);
    }

    /* JADX INFO: renamed from: lambda$addCameraErrorListener$1$androidx-camera-core-streamsharing-StreamSharing, reason: not valid java name */
    /* synthetic */ void m251x83a3b685(String str, String str2, UseCaseConfig useCaseConfig, StreamSpec streamSpec, StreamSpec streamSpec2, SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        if (getCamera() == null) {
            return;
        }
        clearPipeline();
        updateSessionConfig(createPipelineAndUpdateChildrenSpecs(str, str2, useCaseConfig, streamSpec, streamSpec2));
        notifyReset();
        this.mVirtualCameraAdapter.resetChildren();
    }

    private void clearPipeline() {
        SessionConfig.CloseableErrorListener closeableErrorListener = this.mCloseableErrorListener;
        if (closeableErrorListener != null) {
            closeableErrorListener.close();
            this.mCloseableErrorListener = null;
        }
        SurfaceEdge surfaceEdge = this.mCameraEdge;
        if (surfaceEdge != null) {
            surfaceEdge.close();
            this.mCameraEdge = null;
        }
        SurfaceEdge surfaceEdge2 = this.mSecondaryCameraEdge;
        if (surfaceEdge2 != null) {
            surfaceEdge2.close();
            this.mSecondaryCameraEdge = null;
        }
        SurfaceEdge surfaceEdge3 = this.mSharingInputEdge;
        if (surfaceEdge3 != null) {
            surfaceEdge3.close();
            this.mSharingInputEdge = null;
        }
        SurfaceEdge surfaceEdge4 = this.mSecondarySharingInputEdge;
        if (surfaceEdge4 != null) {
            surfaceEdge4.close();
            this.mSecondarySharingInputEdge = null;
        }
        SurfaceProcessorNode surfaceProcessorNode = this.mSharingNode;
        if (surfaceProcessorNode != null) {
            surfaceProcessorNode.release();
            this.mSharingNode = null;
        }
        DualSurfaceProcessorNode dualSurfaceProcessorNode = this.mDualSharingNode;
        if (dualSurfaceProcessorNode != null) {
            dualSurfaceProcessorNode.release();
            this.mDualSharingNode = null;
        }
        SurfaceProcessorNode surfaceProcessorNode2 = this.mEffectNode;
        if (surfaceProcessorNode2 != null) {
            surfaceProcessorNode2.release();
            this.mEffectNode = null;
        }
    }

    private Rect getCropRect(Size size) {
        if (getViewPortCropRect() != null) {
            return getViewPortCropRect();
        }
        return new Rect(0, 0, size.getWidth(), size.getHeight());
    }

    SurfaceEdge getCameraEdge() {
        return this.mCameraEdge;
    }

    SurfaceProcessorNode getSharingNode() {
        return this.mSharingNode;
    }

    VirtualCameraAdapter getVirtualCameraAdapter() {
        return this.mVirtualCameraAdapter;
    }

    public static List<UseCaseConfigFactory.CaptureType> getCaptureTypes(UseCase useCase) {
        ArrayList arrayList = new ArrayList();
        if (isStreamSharing(useCase)) {
            Iterator<UseCase> it = ((StreamSharing) useCase).getChildren().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getCurrentConfig().getCaptureType());
            }
            return arrayList;
        }
        arrayList.add(useCase.getCurrentConfig().getCaptureType());
        return arrayList;
    }

    public static boolean isStreamSharing(UseCase useCase) {
        return useCase instanceof StreamSharing;
    }

    public SurfaceEdge getSharingInputEdge() {
        return this.mSharingInputEdge;
    }
}

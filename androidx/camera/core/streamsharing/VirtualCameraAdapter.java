package androidx.camera.core.streamsharing;

import android.graphics.Rect;
import android.util.Pair;
import android.util.Size;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.processing.SurfaceEdge;
import androidx.camera.core.processing.concurrent.DualOutConfig;
import androidx.camera.core.processing.util.OutConfig;
import androidx.camera.core.streamsharing.StreamSharing;
import androidx.core.util.Preconditions;
import j$.util.Objects;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class VirtualCameraAdapter implements UseCase.StateChangeCallback {
    final Set<UseCase> mChildren;
    private final Set<UseCaseConfig<?>> mChildrenConfigs;
    private final Map<UseCase, UseCaseConfig<?>> mChildrenConfigsMap;
    private final CameraInternal mParentCamera;
    private final ResolutionsMerger mResolutionsMerger;
    private final CameraInternal mSecondaryParentCamera;
    private ResolutionsMerger mSecondaryResolutionsMerger;
    private final UseCaseConfigFactory mUseCaseConfigFactory;
    final Map<UseCase, SurfaceEdge> mChildrenEdges = new HashMap();
    private final Map<UseCase, VirtualCamera> mChildrenVirtualCameras = new HashMap();
    final Map<UseCase, Boolean> mChildrenActiveState = new HashMap();
    private final CameraCaptureCallback mParentMetadataCallback = createCameraCaptureCallback();

    VirtualCameraAdapter(CameraInternal cameraInternal, CameraInternal cameraInternal2, Set<UseCase> set, UseCaseConfigFactory useCaseConfigFactory, StreamSharing.Control control) {
        this.mParentCamera = cameraInternal;
        this.mSecondaryParentCamera = cameraInternal2;
        this.mUseCaseConfigFactory = useCaseConfigFactory;
        this.mChildren = set;
        Map<UseCase, UseCaseConfig<?>> childrenConfigsMap = toChildrenConfigsMap(cameraInternal, set, useCaseConfigFactory);
        this.mChildrenConfigsMap = childrenConfigsMap;
        HashSet hashSet = new HashSet(childrenConfigsMap.values());
        this.mChildrenConfigs = hashSet;
        this.mResolutionsMerger = new ResolutionsMerger(cameraInternal, hashSet);
        if (cameraInternal2 != null) {
            this.mSecondaryResolutionsMerger = new ResolutionsMerger(cameraInternal2, hashSet);
        }
        for (UseCase useCase : set) {
            this.mChildrenActiveState.put(useCase, false);
            this.mChildrenVirtualCameras.put(useCase, new VirtualCamera(cameraInternal, this, control));
        }
    }

    void mergeChildrenConfigs(MutableConfig mutableConfig) {
        mutableConfig.insertOption(ImageOutputConfig.OPTION_CUSTOM_ORDERED_RESOLUTIONS, this.mResolutionsMerger.getMergedResolutions(mutableConfig));
        mutableConfig.insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(getHighestSurfacePriority(this.mChildrenConfigs)));
        DynamicRange dynamicRangeResolveDynamicRange = DynamicRangeUtils.resolveDynamicRange(this.mChildrenConfigs);
        if (dynamicRangeResolveDynamicRange == null) {
            throw new IllegalArgumentException("Failed to merge child dynamic ranges, can not find a dynamic range that satisfies all children.");
        }
        mutableConfig.insertOption(ImageInputConfig.OPTION_INPUT_DYNAMIC_RANGE, dynamicRangeResolveDynamicRange);
        for (UseCase useCase : this.mChildren) {
            if (useCase.getCurrentConfig().getVideoStabilizationMode() != 0) {
                mutableConfig.insertOption(UseCaseConfig.OPTION_VIDEO_STABILIZATION_MODE, Integer.valueOf(useCase.getCurrentConfig().getVideoStabilizationMode()));
            }
            if (useCase.getCurrentConfig().getPreviewStabilizationMode() != 0) {
                mutableConfig.insertOption(UseCaseConfig.OPTION_PREVIEW_STABILIZATION_MODE, Integer.valueOf(useCase.getCurrentConfig().getPreviewStabilizationMode()));
            }
        }
    }

    void bindChildren() {
        for (UseCase useCase : this.mChildren) {
            useCase.bindToCamera((CameraInternal) Objects.requireNonNull(this.mChildrenVirtualCameras.get(useCase)), null, null, useCase.getDefaultConfig(true, this.mUseCaseConfigFactory));
        }
    }

    void unbindChildren() {
        for (UseCase useCase : this.mChildren) {
            useCase.unbindFromCamera((CameraInternal) Objects.requireNonNull(this.mChildrenVirtualCameras.get(useCase)));
        }
    }

    void notifyStateAttached() {
        for (UseCase useCase : this.mChildren) {
            useCase.onStateAttached();
            useCase.onCameraControlReady();
        }
    }

    void notifyStateDetached() {
        Iterator<UseCase> it = this.mChildren.iterator();
        while (it.hasNext()) {
            it.next().onStateDetached();
        }
    }

    Set<UseCase> getChildren() {
        return this.mChildren;
    }

    Map<UseCase, OutConfig> getChildrenOutConfigs(SurfaceEdge surfaceEdge, int i, boolean z) {
        HashMap map = new HashMap();
        for (UseCase useCase : this.mChildren) {
            SurfaceEdge surfaceEdge2 = surfaceEdge;
            map.put(useCase, calculateOutConfig(useCase, this.mResolutionsMerger, this.mParentCamera, surfaceEdge2, i, z));
            surfaceEdge = surfaceEdge2;
        }
        return map;
    }

    Map<UseCase, DualOutConfig> getChildrenOutConfigs(SurfaceEdge surfaceEdge, SurfaceEdge surfaceEdge2, int i, boolean z) {
        HashMap map = new HashMap();
        for (UseCase useCase : this.mChildren) {
            SurfaceEdge surfaceEdge3 = surfaceEdge;
            int i2 = i;
            boolean z2 = z;
            OutConfig outConfigCalculateOutConfig = calculateOutConfig(useCase, this.mResolutionsMerger, this.mParentCamera, surfaceEdge3, i2, z2);
            SurfaceEdge surfaceEdge4 = surfaceEdge2;
            map.put(useCase, DualOutConfig.of(outConfigCalculateOutConfig, calculateOutConfig(useCase, this.mSecondaryResolutionsMerger, (CameraInternal) Objects.requireNonNull(this.mSecondaryParentCamera), surfaceEdge4, i2, z2)));
            surfaceEdge = surfaceEdge3;
            surfaceEdge2 = surfaceEdge4;
            i = i2;
            z = z2;
        }
        return map;
    }

    private OutConfig calculateOutConfig(UseCase useCase, ResolutionsMerger resolutionsMerger, CameraInternal cameraInternal, SurfaceEdge surfaceEdge, int i, boolean z) {
        int sensorRotationDegrees = cameraInternal.getCameraInfo().getSensorRotationDegrees(i);
        boolean zIsMirrored = TransformUtils.isMirrored(surfaceEdge.getSensorToBufferTransform());
        Pair<Rect, Size> preferredChildSizePair = resolutionsMerger.getPreferredChildSizePair((UseCaseConfig) Objects.requireNonNull(this.mChildrenConfigsMap.get(useCase)), surfaceEdge.getCropRect(), TransformUtils.getRotationDegrees(surfaceEdge.getSensorToBufferTransform()), z);
        Rect rect = (Rect) preferredChildSizePair.first;
        Size size = (Size) preferredChildSizePair.second;
        int childRotationDegrees = getChildRotationDegrees(useCase, this.mParentCamera);
        ((VirtualCamera) Objects.requireNonNull(this.mChildrenVirtualCameras.get(useCase))).setRotationDegrees(childRotationDegrees);
        int iWithin360 = TransformUtils.within360((surfaceEdge.getRotationDegrees() + childRotationDegrees) - sensorRotationDegrees);
        return OutConfig.of(getChildTargetType(useCase), getChildFormat(useCase), rect, TransformUtils.rotateSize(size, iWithin360), iWithin360, useCase.isMirroringRequired(cameraInternal) ^ zIsMirrored);
    }

    void setChildrenEdges(Map<UseCase, SurfaceEdge> map) {
        this.mChildrenEdges.clear();
        this.mChildrenEdges.putAll(map);
        for (Map.Entry<UseCase, SurfaceEdge> entry : this.mChildrenEdges.entrySet()) {
            UseCase key = entry.getKey();
            SurfaceEdge value = entry.getValue();
            key.setViewPortCropRect(value.getCropRect());
            key.setSensorToBufferTransformMatrix(value.getSensorToBufferTransform());
            key.updateSuggestedStreamSpec(value.getStreamSpec(), null);
            key.notifyState();
        }
    }

    void resetChildren() {
        Threads.checkMainThread();
        Iterator<UseCase> it = this.mChildren.iterator();
        while (it.hasNext()) {
            onUseCaseReset(it.next());
        }
    }

    CameraCaptureCallback getParentMetadataCallback() {
        return this.mParentMetadataCallback;
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseActive(UseCase useCase) {
        Threads.checkMainThread();
        if (isUseCaseActive(useCase)) {
            return;
        }
        this.mChildrenActiveState.put(useCase, true);
        DeferrableSurface childSurface = getChildSurface(useCase);
        if (childSurface != null) {
            forceSetProvider(getUseCaseEdge(useCase), childSurface, useCase.getSessionConfig());
        }
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseInactive(UseCase useCase) {
        Threads.checkMainThread();
        if (isUseCaseActive(useCase)) {
            this.mChildrenActiveState.put(useCase, false);
            getUseCaseEdge(useCase).disconnect();
        }
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseUpdated(UseCase useCase) {
        Threads.checkMainThread();
        if (isUseCaseActive(useCase)) {
            SurfaceEdge useCaseEdge = getUseCaseEdge(useCase);
            DeferrableSurface childSurface = getChildSurface(useCase);
            if (childSurface != null) {
                forceSetProvider(useCaseEdge, childSurface, useCase.getSessionConfig());
            } else {
                useCaseEdge.disconnect();
            }
        }
    }

    @Override // androidx.camera.core.UseCase.StateChangeCallback
    public void onUseCaseReset(UseCase useCase) {
        DeferrableSurface childSurface;
        Threads.checkMainThread();
        SurfaceEdge useCaseEdge = getUseCaseEdge(useCase);
        if (isUseCaseActive(useCase) && (childSurface = getChildSurface(useCase)) != null) {
            forceSetProvider(useCaseEdge, childSurface, useCase.getSessionConfig());
        }
    }

    private int getChildRotationDegrees(UseCase useCase, CameraInternal cameraInternal) {
        return cameraInternal.getCameraInfo().getSensorRotationDegrees(((ImageOutputConfig) useCase.getCurrentConfig()).getTargetRotation(0));
    }

    private static int getChildFormat(UseCase useCase) {
        return useCase instanceof ImageCapture ? 256 : 34;
    }

    private static int getChildTargetType(UseCase useCase) {
        if (useCase instanceof Preview) {
            return 1;
        }
        return useCase instanceof ImageCapture ? 4 : 2;
    }

    private static Map<UseCase, UseCaseConfig<?>> toChildrenConfigsMap(CameraInternal cameraInternal, Set<UseCase> set, UseCaseConfigFactory useCaseConfigFactory) {
        HashMap map = new HashMap();
        for (UseCase useCase : set) {
            map.put(useCase, useCase.mergeConfigs(cameraInternal.getCameraInfoInternal(), null, useCase.getDefaultConfig(true, useCaseConfigFactory)));
        }
        return map;
    }

    private static int getHighestSurfacePriority(Set<UseCaseConfig<?>> set) {
        Iterator<UseCaseConfig<?>> it = set.iterator();
        int iMax = 0;
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().getSurfaceOccupancyPriority(0));
        }
        return iMax;
    }

    private SurfaceEdge getUseCaseEdge(UseCase useCase) {
        return (SurfaceEdge) Objects.requireNonNull(this.mChildrenEdges.get(useCase));
    }

    private boolean isUseCaseActive(UseCase useCase) {
        return ((Boolean) Objects.requireNonNull(this.mChildrenActiveState.get(useCase))).booleanValue();
    }

    private static void forceSetProvider(SurfaceEdge surfaceEdge, DeferrableSurface deferrableSurface, SessionConfig sessionConfig) {
        surfaceEdge.invalidate();
        try {
            surfaceEdge.setProvider(deferrableSurface);
        } catch (DeferrableSurface.SurfaceClosedException unused) {
            if (sessionConfig.getErrorListener() != null) {
                sessionConfig.getErrorListener().onError(sessionConfig, SessionConfig.SessionError.SESSION_ERROR_SURFACE_NEEDS_RESET);
            }
        }
    }

    static DeferrableSurface getChildSurface(UseCase useCase) {
        List<DeferrableSurface> surfaces;
        if (useCase instanceof ImageCapture) {
            surfaces = useCase.getSessionConfig().getSurfaces();
        } else {
            surfaces = useCase.getSessionConfig().getRepeatingCaptureConfig().getSurfaces();
        }
        Preconditions.checkState(surfaces.size() <= 1);
        if (surfaces.size() == 1) {
            return surfaces.get(0);
        }
        return null;
    }

    CameraCaptureCallback createCameraCaptureCallback() {
        return new CameraCaptureCallback() { // from class: androidx.camera.core.streamsharing.VirtualCameraAdapter.1
            @Override // androidx.camera.core.impl.CameraCaptureCallback
            public void onCaptureCompleted(int i, CameraCaptureResult cameraCaptureResult) {
                super.onCaptureCompleted(i, cameraCaptureResult);
                Iterator<UseCase> it = VirtualCameraAdapter.this.mChildren.iterator();
                while (it.hasNext()) {
                    VirtualCameraAdapter.sendCameraCaptureResultToChild(cameraCaptureResult, it.next().getSessionConfig(), i);
                }
            }
        };
    }

    static void sendCameraCaptureResultToChild(CameraCaptureResult cameraCaptureResult, SessionConfig sessionConfig, int i) {
        Iterator<CameraCaptureCallback> it = sessionConfig.getRepeatingCameraCaptureCallbacks().iterator();
        while (it.hasNext()) {
            it.next().onCaptureCompleted(i, new VirtualCameraCaptureResult(sessionConfig.getRepeatingCaptureConfig().getTagBundle(), cameraCaptureResult));
        }
    }
}

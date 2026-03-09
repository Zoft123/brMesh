package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.StreamSpec;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import androidx.camera.core.processing.TargetUtils;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import androidx.core.util.Preconditions;
import j$.util.Objects;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class UseCase {
    private StreamSpec mAttachedStreamSpec;
    private CameraInternal mCamera;
    private UseCaseConfig<?> mCameraConfig;
    private UseCaseConfig<?> mCurrentConfig;
    private CameraEffect mEffect;
    private UseCaseConfig<?> mExtendedConfig;
    private String mPhysicalCameraId;
    private CameraInternal mSecondaryCamera;
    private UseCaseConfig<?> mUseCaseConfig;
    private Rect mViewPortCropRect;
    private final Set<StateChangeCallback> mStateChangeCallbacks = new HashSet();
    private final Object mCameraLock = new Object();
    private State mState = State.INACTIVE;
    private Matrix mSensorToBufferTransformMatrix = new Matrix();
    private SessionConfig mAttachedSessionConfig = SessionConfig.defaultEmptySessionConfig();
    private SessionConfig mAttachedSecondarySessionConfig = SessionConfig.defaultEmptySessionConfig();

    enum State {
        ACTIVE,
        INACTIVE
    }

    public interface StateChangeCallback {
        void onUseCaseActive(UseCase useCase);

        void onUseCaseInactive(UseCase useCase);

        void onUseCaseReset(UseCase useCase);

        void onUseCaseUpdated(UseCase useCase);
    }

    public abstract UseCaseConfig<?> getDefaultConfig(boolean z, UseCaseConfigFactory useCaseConfigFactory);

    public abstract UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(Config config);

    public void onBind() {
    }

    public void onCameraControlReady() {
    }

    public void onStateAttached() {
    }

    public void onStateDetached() {
    }

    protected StreamSpec onSuggestedStreamSpecUpdated(StreamSpec streamSpec, StreamSpec streamSpec2) {
        return streamSpec;
    }

    public void onUnbind() {
    }

    protected UseCase(UseCaseConfig<?> useCaseConfig) {
        this.mUseCaseConfig = useCaseConfig;
        this.mCurrentConfig = useCaseConfig;
    }

    public UseCaseConfig<?> mergeConfigs(CameraInfoInternal cameraInfoInternal, UseCaseConfig<?> useCaseConfig, UseCaseConfig<?> useCaseConfig2) {
        MutableOptionsBundle mutableOptionsBundleCreate;
        if (useCaseConfig2 != null) {
            mutableOptionsBundleCreate = MutableOptionsBundle.from((Config) useCaseConfig2);
            mutableOptionsBundleCreate.removeOption(TargetConfig.OPTION_TARGET_NAME);
        } else {
            mutableOptionsBundleCreate = MutableOptionsBundle.create();
        }
        if ((this.mUseCaseConfig.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO) || this.mUseCaseConfig.containsOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION)) && mutableOptionsBundleCreate.containsOption(ImageOutputConfig.OPTION_RESOLUTION_SELECTOR)) {
            mutableOptionsBundleCreate.removeOption(ImageOutputConfig.OPTION_RESOLUTION_SELECTOR);
        }
        if (this.mUseCaseConfig.containsOption(ImageOutputConfig.OPTION_RESOLUTION_SELECTOR) && mutableOptionsBundleCreate.containsOption(ImageOutputConfig.OPTION_MAX_RESOLUTION) && ((ResolutionSelector) this.mUseCaseConfig.retrieveOption(ImageOutputConfig.OPTION_RESOLUTION_SELECTOR)).getResolutionStrategy() != null) {
            mutableOptionsBundleCreate.removeOption(ImageOutputConfig.OPTION_MAX_RESOLUTION);
        }
        Iterator<Config.Option<?>> it = this.mUseCaseConfig.listOptions().iterator();
        while (it.hasNext()) {
            Config.CC.mergeOptionValue(mutableOptionsBundleCreate, mutableOptionsBundleCreate, this.mUseCaseConfig, it.next());
        }
        if (useCaseConfig != null) {
            for (Config.Option<?> option : useCaseConfig.listOptions()) {
                if (!option.getId().equals(TargetConfig.OPTION_TARGET_NAME.getId())) {
                    Config.CC.mergeOptionValue(mutableOptionsBundleCreate, mutableOptionsBundleCreate, useCaseConfig, option);
                }
            }
        }
        if (mutableOptionsBundleCreate.containsOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION) && mutableOptionsBundleCreate.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO)) {
            mutableOptionsBundleCreate.removeOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO);
        }
        if (mutableOptionsBundleCreate.containsOption(ImageOutputConfig.OPTION_RESOLUTION_SELECTOR) && ((ResolutionSelector) mutableOptionsBundleCreate.retrieveOption(ImageOutputConfig.OPTION_RESOLUTION_SELECTOR)).getAllowedResolutionMode() != 0) {
            mutableOptionsBundleCreate.insertOption(UseCaseConfig.OPTION_ZSL_DISABLED, true);
        }
        return onMergeConfig(cameraInfoInternal, getUseCaseConfigBuilder(mutableOptionsBundleCreate));
    }

    protected UseCaseConfig<?> onMergeConfig(CameraInfoInternal cameraInfoInternal, UseCaseConfig.Builder<?, ?, ?> builder) {
        return builder.getUseCaseConfig();
    }

    public static int snapToSurfaceRotation(int i) {
        Preconditions.checkArgumentInRange(i, 0, 359, "orientation");
        if (i >= 315 || i < 45) {
            return 0;
        }
        if (i >= 225) {
            return 1;
        }
        return i >= 135 ? 2 : 3;
    }

    public void setPhysicalCameraId(String str) {
        this.mPhysicalCameraId = str;
    }

    public String getPhysicalCameraId() {
        return this.mPhysicalCameraId;
    }

    protected boolean setTargetRotationInternal(int i) {
        int targetRotation = ((ImageOutputConfig) getCurrentConfig()).getTargetRotation(-1);
        if (targetRotation != -1 && targetRotation == i) {
            return false;
        }
        UseCaseConfig.Builder<?, ?, ?> useCaseConfigBuilder = getUseCaseConfigBuilder(this.mUseCaseConfig);
        UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(useCaseConfigBuilder, i);
        this.mUseCaseConfig = useCaseConfigBuilder.getUseCaseConfig();
        CameraInternal camera = getCamera();
        if (camera == null) {
            this.mCurrentConfig = this.mUseCaseConfig;
            return true;
        }
        this.mCurrentConfig = mergeConfigs(camera.getCameraInfoInternal(), this.mExtendedConfig, this.mCameraConfig);
        return true;
    }

    protected int getTargetRotationInternal() {
        return ((ImageOutputConfig) this.mCurrentConfig).getTargetRotation(0);
    }

    protected Range<Integer> getTargetFrameRateInternal() {
        return this.mCurrentConfig.getTargetFrameRate(StreamSpec.FRAME_RATE_RANGE_UNSPECIFIED);
    }

    protected int getMirrorModeInternal() {
        return ((ImageOutputConfig) this.mCurrentConfig).getMirrorMode(-1);
    }

    public boolean isMirroringRequired(CameraInternal cameraInternal) {
        int mirrorModeInternal = getMirrorModeInternal();
        if (mirrorModeInternal == -1 || mirrorModeInternal == 0) {
            return false;
        }
        if (mirrorModeInternal == 1) {
            return true;
        }
        if (mirrorModeInternal == 2) {
            return cameraInternal.isFrontFacing();
        }
        throw new AssertionError("Unknown mirrorMode: " + mirrorModeInternal);
    }

    protected int getAppTargetRotation() {
        return ((ImageOutputConfig) this.mCurrentConfig).getAppTargetRotation(-1);
    }

    protected int getRelativeRotation(CameraInternal cameraInternal) {
        return getRelativeRotation(cameraInternal, false);
    }

    protected int getRelativeRotation(CameraInternal cameraInternal, boolean z) {
        int sensorRotationDegrees = cameraInternal.getCameraInfoInternal().getSensorRotationDegrees(getTargetRotationInternal());
        return (cameraInternal.getHasTransform() || !z) ? sensorRotationDegrees : TransformUtils.within360(-sensorRotationDegrees);
    }

    protected void updateSessionConfig(List<SessionConfig> list) {
        if (list.isEmpty()) {
            return;
        }
        this.mAttachedSessionConfig = list.get(0);
        if (list.size() > 1) {
            this.mAttachedSecondarySessionConfig = list.get(1);
        }
        Iterator<SessionConfig> it = list.iterator();
        while (it.hasNext()) {
            for (DeferrableSurface deferrableSurface : it.next().getSurfaces()) {
                if (deferrableSurface.getContainerClass() == null) {
                    deferrableSurface.setContainerClass(getClass());
                }
            }
        }
    }

    private void addStateChangeCallback(StateChangeCallback stateChangeCallback) {
        this.mStateChangeCallbacks.add(stateChangeCallback);
    }

    private void removeStateChangeCallback(StateChangeCallback stateChangeCallback) {
        this.mStateChangeCallbacks.remove(stateChangeCallback);
    }

    public SessionConfig getSessionConfig() {
        return this.mAttachedSessionConfig;
    }

    public SessionConfig getSecondarySessionConfig() {
        return this.mAttachedSecondarySessionConfig;
    }

    protected final void notifyActive() {
        this.mState = State.ACTIVE;
        notifyState();
    }

    protected final void notifyInactive() {
        this.mState = State.INACTIVE;
        notifyState();
    }

    protected final void notifyUpdated() {
        Iterator<StateChangeCallback> it = this.mStateChangeCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUseCaseUpdated(this);
        }
    }

    protected final void notifyReset() {
        Iterator<StateChangeCallback> it = this.mStateChangeCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUseCaseReset(this);
        }
    }

    public final void notifyState() {
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal == 0) {
            Iterator<StateChangeCallback> it = this.mStateChangeCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onUseCaseActive(this);
            }
        } else {
            if (iOrdinal != 1) {
                return;
            }
            Iterator<StateChangeCallback> it2 = this.mStateChangeCallbacks.iterator();
            while (it2.hasNext()) {
                it2.next().onUseCaseInactive(this);
            }
        }
    }

    protected String getCameraId() {
        return ((CameraInternal) Preconditions.checkNotNull(getCamera(), "No camera attached to use case: " + this)).getCameraInfoInternal().getCameraId();
    }

    protected String getSecondaryCameraId() {
        if (getSecondaryCamera() == null) {
            return null;
        }
        return getSecondaryCamera().getCameraInfoInternal().getCameraId();
    }

    protected boolean isCurrentCamera(String str) {
        if (getCamera() == null) {
            return false;
        }
        return Objects.equals(str, getCameraId());
    }

    public String getName() {
        return (String) Objects.requireNonNull(this.mCurrentConfig.getTargetName("<UnknownUseCase-" + hashCode() + ">"));
    }

    protected UseCaseConfig<?> getAppConfig() {
        return this.mUseCaseConfig;
    }

    public UseCaseConfig<?> getCurrentConfig() {
        return this.mCurrentConfig;
    }

    public CameraInternal getCamera() {
        CameraInternal cameraInternal;
        synchronized (this.mCameraLock) {
            cameraInternal = this.mCamera;
        }
        return cameraInternal;
    }

    public CameraInternal getSecondaryCamera() {
        CameraInternal cameraInternal;
        synchronized (this.mCameraLock) {
            cameraInternal = this.mSecondaryCamera;
        }
        return cameraInternal;
    }

    public Size getAttachedSurfaceResolution() {
        StreamSpec streamSpec = this.mAttachedStreamSpec;
        if (streamSpec != null) {
            return streamSpec.getResolution();
        }
        return null;
    }

    public StreamSpec getAttachedStreamSpec() {
        return this.mAttachedStreamSpec;
    }

    public void updateSuggestedStreamSpec(StreamSpec streamSpec, StreamSpec streamSpec2) {
        this.mAttachedStreamSpec = onSuggestedStreamSpecUpdated(streamSpec, streamSpec2);
    }

    public void updateSuggestedStreamSpecImplementationOptions(Config config) {
        this.mAttachedStreamSpec = onSuggestedStreamSpecImplementationOptionsUpdated(config);
    }

    protected StreamSpec onSuggestedStreamSpecImplementationOptionsUpdated(Config config) {
        StreamSpec streamSpec = this.mAttachedStreamSpec;
        if (streamSpec == null) {
            throw new UnsupportedOperationException("Attempt to update the implementation options for a use case without attached stream specifications.");
        }
        return streamSpec.toBuilder().setImplementationOptions(config).build();
    }

    public final void bindToCamera(CameraInternal cameraInternal, CameraInternal cameraInternal2, UseCaseConfig<?> useCaseConfig, UseCaseConfig<?> useCaseConfig2) {
        synchronized (this.mCameraLock) {
            this.mCamera = cameraInternal;
            this.mSecondaryCamera = cameraInternal2;
            addStateChangeCallback(cameraInternal);
            if (cameraInternal2 != null) {
                addStateChangeCallback(cameraInternal2);
            }
        }
        this.mExtendedConfig = useCaseConfig;
        this.mCameraConfig = useCaseConfig2;
        this.mCurrentConfig = mergeConfigs(cameraInternal.getCameraInfoInternal(), this.mExtendedConfig, this.mCameraConfig);
        onBind();
    }

    public final void unbindFromCamera(CameraInternal cameraInternal) {
        onUnbind();
        synchronized (this.mCameraLock) {
            CameraInternal cameraInternal2 = this.mCamera;
            if (cameraInternal == cameraInternal2) {
                removeStateChangeCallback(cameraInternal2);
                this.mCamera = null;
            }
            CameraInternal cameraInternal3 = this.mSecondaryCamera;
            if (cameraInternal == cameraInternal3) {
                removeStateChangeCallback(cameraInternal3);
                this.mSecondaryCamera = null;
            }
        }
        this.mAttachedStreamSpec = null;
        this.mViewPortCropRect = null;
        this.mCurrentConfig = this.mUseCaseConfig;
        this.mExtendedConfig = null;
        this.mCameraConfig = null;
    }

    protected CameraControlInternal getCameraControl() {
        synchronized (this.mCameraLock) {
            CameraInternal cameraInternal = this.mCamera;
            if (cameraInternal == null) {
                return CameraControlInternal.DEFAULT_EMPTY_INSTANCE;
            }
            return cameraInternal.getCameraControlInternal();
        }
    }

    public void setViewPortCropRect(Rect rect) {
        this.mViewPortCropRect = rect;
    }

    public void setEffect(CameraEffect cameraEffect) {
        Preconditions.checkArgument(cameraEffect == null || isEffectTargetsSupported(cameraEffect.getTargets()));
        this.mEffect = cameraEffect;
    }

    public CameraEffect getEffect() {
        return this.mEffect;
    }

    public Rect getViewPortCropRect() {
        return this.mViewPortCropRect;
    }

    public void setSensorToBufferTransformMatrix(Matrix matrix) {
        this.mSensorToBufferTransformMatrix = new Matrix(matrix);
    }

    public Matrix getSensorToBufferTransformMatrix() {
        return this.mSensorToBufferTransformMatrix;
    }

    public int getImageFormat() {
        return this.mCurrentConfig.getInputFormat();
    }

    protected ResolutionInfo getResolutionInfoInternal() {
        CameraInternal camera = getCamera();
        Size attachedSurfaceResolution = getAttachedSurfaceResolution();
        if (camera == null || attachedSurfaceResolution == null) {
            return null;
        }
        Rect viewPortCropRect = getViewPortCropRect();
        if (viewPortCropRect == null) {
            viewPortCropRect = new Rect(0, 0, attachedSurfaceResolution.getWidth(), attachedSurfaceResolution.getHeight());
        }
        return new ResolutionInfo(attachedSurfaceResolution, viewPortCropRect, getRelativeRotation(camera));
    }

    protected Set<Integer> getSupportedEffectTargets() {
        return Collections.EMPTY_SET;
    }

    public boolean isEffectTargetsSupported(int i) {
        Iterator<Integer> it = getSupportedEffectTargets().iterator();
        while (it.hasNext()) {
            if (TargetUtils.isSuperset(i, it.next().intValue())) {
                return true;
            }
        }
        return false;
    }
}

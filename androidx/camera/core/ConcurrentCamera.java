package androidx.camera.core;

import androidx.lifecycle.LifecycleOwner;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ConcurrentCamera {
    private List<Camera> mCameras;

    public ConcurrentCamera(List<Camera> list) {
        this.mCameras = list;
    }

    public List<Camera> getCameras() {
        return this.mCameras;
    }

    public static final class SingleCameraConfig {
        private CameraSelector mCameraSelector;
        private LayoutSettings mLayoutSettings;
        private LifecycleOwner mLifecycleOwner;
        private UseCaseGroup mUseCaseGroup;

        public SingleCameraConfig(CameraSelector cameraSelector, UseCaseGroup useCaseGroup, LifecycleOwner lifecycleOwner) {
            this(cameraSelector, useCaseGroup, LayoutSettings.DEFAULT, lifecycleOwner);
        }

        public SingleCameraConfig(CameraSelector cameraSelector, UseCaseGroup useCaseGroup, LayoutSettings layoutSettings, LifecycleOwner lifecycleOwner) {
            this.mCameraSelector = cameraSelector;
            this.mUseCaseGroup = useCaseGroup;
            this.mLayoutSettings = layoutSettings;
            this.mLifecycleOwner = lifecycleOwner;
        }

        public CameraSelector getCameraSelector() {
            return this.mCameraSelector;
        }

        public LifecycleOwner getLifecycleOwner() {
            return this.mLifecycleOwner;
        }

        public UseCaseGroup getUseCaseGroup() {
            return this.mUseCaseGroup;
        }

        public LayoutSettings getLayoutSettings() {
            return this.mLayoutSettings;
        }
    }
}

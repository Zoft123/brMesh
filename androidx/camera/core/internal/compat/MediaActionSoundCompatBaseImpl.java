package androidx.camera.core.internal.compat;

import android.hardware.Camera;

/* JADX INFO: loaded from: classes.dex */
class MediaActionSoundCompatBaseImpl {
    private static final int SAMPLE_CAMERA_ID = 0;

    static boolean mustPlayShutterSound() {
        if (Camera.getNumberOfCameras() < 1) {
            return false;
        }
        try {
            Camera.getCameraInfo(0, new Camera.CameraInfo());
            return !r0.canDisableShutterSound;
        } catch (RuntimeException unused) {
            return false;
        }
    }

    private MediaActionSoundCompatBaseImpl() {
    }
}

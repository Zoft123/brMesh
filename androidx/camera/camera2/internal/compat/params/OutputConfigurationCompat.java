package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.os.Build;
import android.util.Size;
import android.view.Surface;
import androidx.activity.ComponentDialog$$ExternalSyntheticApiModelOutline0;
import androidx.camera.camera2.internal.compat.ApiCompat;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class OutputConfigurationCompat {
    public static final int STREAM_USE_CASE_NONE = -1;
    public static final int SURFACE_GROUP_ID_NONE = -1;
    private final OutputConfigurationCompatImpl mImpl;

    interface OutputConfigurationCompatImpl {
        void addSurface(Surface surface);

        void enableSurfaceSharing();

        long getDynamicRangeProfile();

        int getMaxSharedSurfaceCount();

        int getMirrorMode();

        Object getOutputConfiguration();

        String getPhysicalCameraId();

        long getStreamUseCase();

        Surface getSurface();

        int getSurfaceGroupId();

        List<Surface> getSurfaces();

        void removeSurface(Surface surface);

        void setDynamicRangeProfile(long j);

        void setMirrorMode(int i);

        void setPhysicalCameraId(String str);

        void setStreamUseCase(long j);
    }

    public OutputConfigurationCompat(Surface surface) {
        this(-1, surface);
    }

    public OutputConfigurationCompat(int i, Surface surface) {
        if (Build.VERSION.SDK_INT >= 33) {
            this.mImpl = new OutputConfigurationCompatApi33Impl(i, surface);
            return;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new OutputConfigurationCompatApi28Impl(i, surface);
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.mImpl = new OutputConfigurationCompatApi26Impl(i, surface);
        } else if (Build.VERSION.SDK_INT >= 24) {
            this.mImpl = new OutputConfigurationCompatApi24Impl(i, surface);
        } else {
            this.mImpl = new OutputConfigurationCompatBaseImpl(surface);
        }
    }

    public <T> OutputConfigurationCompat(Size size, Class<T> cls) {
        OutputConfiguration outputConfigurationNewOutputConfiguration = ApiCompat.Api26Impl.newOutputConfiguration(size, cls);
        if (Build.VERSION.SDK_INT >= 33) {
            this.mImpl = OutputConfigurationCompatApi33Impl.wrap(outputConfigurationNewOutputConfiguration);
        } else if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = OutputConfigurationCompatApi28Impl.wrap(outputConfigurationNewOutputConfiguration);
        } else {
            this.mImpl = OutputConfigurationCompatApi26Impl.wrap(outputConfigurationNewOutputConfiguration);
        }
    }

    public OutputConfigurationCompat(OutputConfiguration outputConfiguration) {
        this.mImpl = OutputConfigurationCompatApi33Impl.wrap(outputConfiguration);
    }

    private OutputConfigurationCompat(OutputConfigurationCompatImpl outputConfigurationCompatImpl) {
        this.mImpl = outputConfigurationCompatImpl;
    }

    public static OutputConfigurationCompat wrap(Object obj) {
        OutputConfigurationCompatImpl outputConfigurationCompatImplWrap;
        if (obj == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            outputConfigurationCompatImplWrap = OutputConfigurationCompatApi33Impl.wrap(ComponentDialog$$ExternalSyntheticApiModelOutline0.m8m(obj));
        } else if (Build.VERSION.SDK_INT >= 28) {
            outputConfigurationCompatImplWrap = OutputConfigurationCompatApi28Impl.wrap(ComponentDialog$$ExternalSyntheticApiModelOutline0.m8m(obj));
        } else if (Build.VERSION.SDK_INT >= 26) {
            outputConfigurationCompatImplWrap = OutputConfigurationCompatApi26Impl.wrap(ComponentDialog$$ExternalSyntheticApiModelOutline0.m8m(obj));
        } else {
            outputConfigurationCompatImplWrap = Build.VERSION.SDK_INT >= 24 ? OutputConfigurationCompatApi24Impl.wrap(ComponentDialog$$ExternalSyntheticApiModelOutline0.m8m(obj)) : null;
        }
        if (outputConfigurationCompatImplWrap == null) {
            return null;
        }
        return new OutputConfigurationCompat(outputConfigurationCompatImplWrap);
    }

    public void enableSurfaceSharing() {
        this.mImpl.enableSurfaceSharing();
    }

    public int getMirrorMode() {
        return this.mImpl.getMirrorMode();
    }

    public void setMirrorMode(int i) {
        this.mImpl.setMirrorMode(i);
    }

    public String getPhysicalCameraId() {
        return this.mImpl.getPhysicalCameraId();
    }

    public void setPhysicalCameraId(String str) {
        this.mImpl.setPhysicalCameraId(str);
    }

    public void addSurface(Surface surface) {
        this.mImpl.addSurface(surface);
    }

    public void removeSurface(Surface surface) {
        this.mImpl.removeSurface(surface);
    }

    public int getMaxSharedSurfaceCount() {
        return this.mImpl.getMaxSharedSurfaceCount();
    }

    public Surface getSurface() {
        return this.mImpl.getSurface();
    }

    public List<Surface> getSurfaces() {
        return this.mImpl.getSurfaces();
    }

    public int getSurfaceGroupId() {
        return this.mImpl.getSurfaceGroupId();
    }

    public long getDynamicRangeProfile() {
        return this.mImpl.getDynamicRangeProfile();
    }

    public void setDynamicRangeProfile(long j) {
        this.mImpl.setDynamicRangeProfile(j);
    }

    public void setStreamUseCase(long j) {
        this.mImpl.setStreamUseCase(j);
    }

    public long getStreamUseCase() {
        return this.mImpl.getStreamUseCase();
    }

    public boolean equals(Object obj) {
        if (obj instanceof OutputConfigurationCompat) {
            return this.mImpl.equals(((OutputConfigurationCompat) obj).mImpl);
        }
        return false;
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public Object unwrap() {
        return this.mImpl.getOutputConfiguration();
    }
}

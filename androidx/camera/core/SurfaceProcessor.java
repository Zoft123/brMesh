package androidx.camera.core;

/* JADX INFO: loaded from: classes.dex */
public interface SurfaceProcessor {
    void onInputSurface(SurfaceRequest surfaceRequest) throws ProcessingException;

    void onOutputSurface(SurfaceOutput surfaceOutput) throws ProcessingException;
}

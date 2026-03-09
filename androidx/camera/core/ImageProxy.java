package androidx.camera.core;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public interface ImageProxy extends AutoCloseable {

    public interface PlaneProxy {
        ByteBuffer getBuffer();

        int getPixelStride();

        int getRowStride();
    }

    @Override // java.lang.AutoCloseable
    void close();

    Rect getCropRect();

    int getFormat();

    int getHeight();

    Image getImage();

    ImageInfo getImageInfo();

    PlaneProxy[] getPlanes();

    int getWidth();

    void setCropRect(Rect rect);

    Bitmap toBitmap();

    /* JADX INFO: renamed from: androidx.camera.core.ImageProxy$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }
}

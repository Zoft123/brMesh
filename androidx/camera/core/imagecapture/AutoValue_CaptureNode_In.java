package androidx.camera.core.imagecapture;

import android.util.Size;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.imagecapture.CaptureNode;
import androidx.camera.core.imagecapture.TakePictureManager;
import androidx.camera.core.processing.Edge;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_CaptureNode_In extends CaptureNode.In {
    private final Edge<TakePictureManager.CaptureError> errorEdge;
    private final ImageReaderProxyProvider imageReaderProxyProvider;
    private final int inputFormat;
    private final int outputFormat;
    private final int postviewImageFormat;
    private final Size postviewSize;
    private final Edge<ProcessingRequest> requestEdge;
    private final Size size;
    private final boolean virtualCamera;

    AutoValue_CaptureNode_In(Size size, int i, int i2, boolean z, ImageReaderProxyProvider imageReaderProxyProvider, Size size2, int i3, Edge<ProcessingRequest> edge, Edge<TakePictureManager.CaptureError> edge2) {
        if (size == null) {
            throw new NullPointerException("Null size");
        }
        this.size = size;
        this.inputFormat = i;
        this.outputFormat = i2;
        this.virtualCamera = z;
        this.imageReaderProxyProvider = imageReaderProxyProvider;
        this.postviewSize = size2;
        this.postviewImageFormat = i3;
        if (edge == null) {
            throw new NullPointerException("Null requestEdge");
        }
        this.requestEdge = edge;
        if (edge2 == null) {
            throw new NullPointerException("Null errorEdge");
        }
        this.errorEdge = edge2;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    Size getSize() {
        return this.size;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    int getInputFormat() {
        return this.inputFormat;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    int getOutputFormat() {
        return this.outputFormat;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    boolean isVirtualCamera() {
        return this.virtualCamera;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    ImageReaderProxyProvider getImageReaderProxyProvider() {
        return this.imageReaderProxyProvider;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    Size getPostviewSize() {
        return this.postviewSize;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    int getPostviewImageFormat() {
        return this.postviewImageFormat;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    Edge<ProcessingRequest> getRequestEdge() {
        return this.requestEdge;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.In
    Edge<TakePictureManager.CaptureError> getErrorEdge() {
        return this.errorEdge;
    }

    public String toString() {
        return "In{size=" + this.size + ", inputFormat=" + this.inputFormat + ", outputFormat=" + this.outputFormat + ", virtualCamera=" + this.virtualCamera + ", imageReaderProxyProvider=" + this.imageReaderProxyProvider + ", postviewSize=" + this.postviewSize + ", postviewImageFormat=" + this.postviewImageFormat + ", requestEdge=" + this.requestEdge + ", errorEdge=" + this.errorEdge + "}";
    }

    public boolean equals(Object obj) {
        ImageReaderProxyProvider imageReaderProxyProvider;
        Size size;
        if (obj == this) {
            return true;
        }
        if (obj instanceof CaptureNode.In) {
            CaptureNode.In in = (CaptureNode.In) obj;
            if (this.size.equals(in.getSize()) && this.inputFormat == in.getInputFormat() && this.outputFormat == in.getOutputFormat() && this.virtualCamera == in.isVirtualCamera() && ((imageReaderProxyProvider = this.imageReaderProxyProvider) != null ? imageReaderProxyProvider.equals(in.getImageReaderProxyProvider()) : in.getImageReaderProxyProvider() == null) && ((size = this.postviewSize) != null ? size.equals(in.getPostviewSize()) : in.getPostviewSize() == null) && this.postviewImageFormat == in.getPostviewImageFormat() && this.requestEdge.equals(in.getRequestEdge()) && this.errorEdge.equals(in.getErrorEdge())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (((((((this.size.hashCode() ^ 1000003) * 1000003) ^ this.inputFormat) * 1000003) ^ this.outputFormat) * 1000003) ^ (this.virtualCamera ? 1231 : 1237)) * 1000003;
        ImageReaderProxyProvider imageReaderProxyProvider = this.imageReaderProxyProvider;
        int iHashCode2 = (iHashCode ^ (imageReaderProxyProvider == null ? 0 : imageReaderProxyProvider.hashCode())) * 1000003;
        Size size = this.postviewSize;
        return ((((((iHashCode2 ^ (size != null ? size.hashCode() : 0)) * 1000003) ^ this.postviewImageFormat) * 1000003) ^ this.requestEdge.hashCode()) * 1000003) ^ this.errorEdge.hashCode();
    }
}

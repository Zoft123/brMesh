package androidx.camera.core;

import android.util.Size;
import androidx.camera.core.ImageProxy;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
final class ImageProxyDownsampler {

    enum DownsamplingMethod {
        NEAREST_NEIGHBOR,
        AVERAGING
    }

    private ImageProxyDownsampler() {
    }

    static ForwardingImageProxy downsample(ImageProxy imageProxy, int i, int i2, DownsamplingMethod downsamplingMethod) {
        byte[] bArr;
        int i3;
        byte[] bArr2;
        if (imageProxy.getFormat() != 35) {
            throw new UnsupportedOperationException("Only YUV_420_888 format is currently supported.");
        }
        if (imageProxy.getWidth() < i || imageProxy.getHeight() < i2) {
            throw new IllegalArgumentException("Downsampled dimension " + new Size(i, i2) + " is not <= original dimension " + new Size(imageProxy.getWidth(), imageProxy.getHeight()) + ".");
        }
        if (imageProxy.getWidth() == i && imageProxy.getHeight() == i2) {
            return new ForwardingImageProxyImpl(imageProxy, imageProxy.getPlanes(), i, i2);
        }
        int[] iArr = {imageProxy.getWidth(), imageProxy.getWidth() / 2, imageProxy.getWidth() / 2};
        int[] iArr2 = {imageProxy.getHeight(), imageProxy.getHeight() / 2, imageProxy.getHeight() / 2};
        int i4 = i / 2;
        int[] iArr3 = {i, i4, i4};
        int i5 = i2 / 2;
        int[] iArr4 = {i2, i5, i5};
        ImageProxy.PlaneProxy[] planeProxyArr = new ImageProxy.PlaneProxy[3];
        for (int i6 = 0; i6 < 3; i6++) {
            ImageProxy.PlaneProxy planeProxy = imageProxy.getPlanes()[i6];
            ByteBuffer buffer = planeProxy.getBuffer();
            byte[] bArr3 = new byte[iArr3[i6] * iArr4[i6]];
            int iOrdinal = downsamplingMethod.ordinal();
            if (iOrdinal == 0) {
                bArr = bArr3;
                int i7 = iArr[i6];
                int pixelStride = planeProxy.getPixelStride();
                int rowStride = planeProxy.getRowStride();
                i3 = 1;
                resizeNearestNeighbor(buffer, i7, pixelStride, rowStride, iArr2[i6], bArr, iArr3[i6], iArr4[i6]);
            } else if (iOrdinal == 1) {
                bArr = bArr3;
                int i8 = iArr[i6];
                int pixelStride2 = planeProxy.getPixelStride();
                int rowStride2 = planeProxy.getRowStride();
                i3 = 1;
                resizeAveraging(buffer, i8, pixelStride2, rowStride2, iArr2[i6], bArr, iArr3[i6], iArr4[i6]);
            } else {
                bArr2 = bArr3;
                i3 = 1;
                planeProxyArr[i6] = createPlaneProxy(iArr3[i6], i3, bArr2);
            }
            bArr2 = bArr;
            planeProxyArr[i6] = createPlaneProxy(iArr3[i6], i3, bArr2);
        }
        return new ForwardingImageProxyImpl(imageProxy, planeProxyArr, i, i2);
    }

    private static void resizeNearestNeighbor(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6) {
        float f = i / i5;
        float f2 = i4 / i6;
        byte[] bArr2 = new byte[i3];
        int[] iArr = new int[i5];
        for (int i7 = 0; i7 < i5; i7++) {
            iArr[i7] = ((int) (i7 * f)) * i2;
        }
        synchronized (byteBuffer) {
            byteBuffer.rewind();
            for (int i8 = 0; i8 < i6; i8++) {
                int i9 = i8 * i5;
                byteBuffer.position(Math.min((int) (i8 * f2), i4 - 1) * i3);
                byteBuffer.get(bArr2, 0, Math.min(i3, byteBuffer.remaining()));
                for (int i10 = 0; i10 < i5; i10++) {
                    bArr[i9 + i10] = bArr2[iArr[i10]];
                }
            }
        }
    }

    private static void resizeAveraging(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6) {
        float f = i / i5;
        float f2 = i4 / i6;
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[i3];
        int[] iArr = new int[i5];
        int i7 = 0;
        for (int i8 = 0; i8 < i5; i8++) {
            iArr[i8] = ((int) (i8 * f)) * i2;
        }
        synchronized (byteBuffer) {
            byteBuffer.rewind();
            int i9 = 0;
            while (i9 < i6) {
                int i10 = (int) (i9 * f2);
                int i11 = i4 - 1;
                int iMin = Math.min(i10, i11) * i3;
                int iMin2 = Math.min(i10 + 1, i11) * i3;
                int i12 = i9 * i5;
                byteBuffer.position(iMin);
                byteBuffer.get(bArr2, i7, Math.min(i3, byteBuffer.remaining()));
                byteBuffer.position(iMin2);
                byteBuffer.get(bArr3, i7, Math.min(i3, byteBuffer.remaining()));
                for (int i13 = i7; i13 < i5; i13++) {
                    int i14 = iArr[i13];
                    bArr[i12 + i13] = (byte) ((((((bArr2[i14] & 255) + (bArr2[i14 + i2] & 255)) + (bArr3[i14] & 255)) + (bArr3[i14 + i2] & 255)) / 4) & 255);
                }
                i9++;
                i7 = 0;
            }
        }
    }

    private static ImageProxy.PlaneProxy createPlaneProxy(int i, int i2, byte[] bArr) {
        return new ImageProxy.PlaneProxy(bArr, i, i2) { // from class: androidx.camera.core.ImageProxyDownsampler.1
            final ByteBuffer mBuffer;
            final /* synthetic */ byte[] val$data;
            final /* synthetic */ int val$pixelStride;
            final /* synthetic */ int val$rowStride;

            {
                this.val$data = bArr;
                this.val$rowStride = i;
                this.val$pixelStride = i2;
                this.mBuffer = ByteBuffer.wrap(bArr);
            }

            @Override // androidx.camera.core.ImageProxy.PlaneProxy
            public int getRowStride() {
                return this.val$rowStride;
            }

            @Override // androidx.camera.core.ImageProxy.PlaneProxy
            public int getPixelStride() {
                return this.val$pixelStride;
            }

            @Override // androidx.camera.core.ImageProxy.PlaneProxy
            public ByteBuffer getBuffer() {
                return this.mBuffer;
            }
        };
    }

    private static final class ForwardingImageProxyImpl extends ForwardingImageProxy {
        private final int mDownsampledHeight;
        private final ImageProxy.PlaneProxy[] mDownsampledPlanes;
        private final int mDownsampledWidth;

        ForwardingImageProxyImpl(ImageProxy imageProxy, ImageProxy.PlaneProxy[] planeProxyArr, int i, int i2) {
            super(imageProxy);
            this.mDownsampledPlanes = planeProxyArr;
            this.mDownsampledWidth = i;
            this.mDownsampledHeight = i2;
        }

        @Override // androidx.camera.core.ForwardingImageProxy, androidx.camera.core.ImageProxy
        public int getWidth() {
            return this.mDownsampledWidth;
        }

        @Override // androidx.camera.core.ForwardingImageProxy, androidx.camera.core.ImageProxy
        public int getHeight() {
            return this.mDownsampledHeight;
        }

        @Override // androidx.camera.core.ForwardingImageProxy, androidx.camera.core.ImageProxy
        public ImageProxy.PlaneProxy[] getPlanes() {
            return this.mDownsampledPlanes;
        }
    }
}

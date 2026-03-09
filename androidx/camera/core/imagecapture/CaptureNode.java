package androidx.camera.core.imagecapture;

import android.util.Size;
import android.view.Surface;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.ImageReaderProxys;
import androidx.camera.core.Logger;
import androidx.camera.core.MetadataImageReader;
import androidx.camera.core.SafeCloseImageReaderProxy;
import androidx.camera.core.imagecapture.ProcessingNode;
import androidx.camera.core.imagecapture.TakePictureManager;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureCallbacks;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.processing.Edge;
import androidx.camera.core.processing.Node;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class CaptureNode implements Node<In, ProcessingNode.In> {
    static final int MAX_IMAGES = 4;
    private static final String TAG = "CaptureNode";
    private In mInputEdge;
    private ProcessingNode.In mOutputEdge;
    SafeCloseImageReaderProxy mSafeCloseImageReaderForPostview;
    SafeCloseImageReaderProxy mSafeCloseImageReaderProxy;
    ProcessingRequest mCurrentRequest = null;
    private NoMetadataImageReader mNoMetadataImageReader = null;

    CaptureNode() {
    }

    @Override // androidx.camera.core.processing.Node
    public ProcessingNode.In transform(In in) {
        Consumer<ProcessingRequest> consumer;
        ImageReaderProxy imageReaderProxy;
        Preconditions.checkState(this.mInputEdge == null && this.mSafeCloseImageReaderProxy == null, "CaptureNode does not support recreation yet.");
        this.mInputEdge = in;
        Size size = in.getSize();
        int inputFormat = in.getInputFormat();
        boolean zIsVirtualCamera = in.isVirtualCamera();
        CameraCaptureCallback anonymousClass1 = new AnonymousClass1();
        if (!zIsVirtualCamera && in.getImageReaderProxyProvider() == null) {
            MetadataImageReader metadataImageReader = new MetadataImageReader(size.getWidth(), size.getHeight(), inputFormat, 4);
            anonymousClass1 = CameraCaptureCallbacks.createComboCallback(anonymousClass1, metadataImageReader.getCameraCaptureCallback());
            consumer = new Consumer() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.onRequestAvailable((ProcessingRequest) obj);
                }
            };
            imageReaderProxy = metadataImageReader;
        } else {
            NoMetadataImageReader noMetadataImageReader = new NoMetadataImageReader(createImageReaderProxy(in.getImageReaderProxyProvider(), size.getWidth(), size.getHeight(), inputFormat));
            this.mNoMetadataImageReader = noMetadataImageReader;
            consumer = new Consumer() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m183lambda$transform$0$androidxcameracoreimagecaptureCaptureNode((ProcessingRequest) obj);
                }
            };
            imageReaderProxy = noMetadataImageReader;
        }
        in.setCameraCaptureCallback(anonymousClass1);
        in.setSurface((Surface) Objects.requireNonNull(imageReaderProxy.getSurface()));
        this.mSafeCloseImageReaderProxy = new SafeCloseImageReaderProxy(imageReaderProxy);
        imageReaderProxy.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy2) {
                this.f$0.m184lambda$transform$1$androidxcameracoreimagecaptureCaptureNode(imageReaderProxy2);
            }
        }, CameraXExecutors.mainThreadExecutor());
        if (in.getPostviewSize() != null) {
            ImageReaderProxy imageReaderProxyCreateImageReaderProxy = createImageReaderProxy(in.getImageReaderProxyProvider(), in.getPostviewSize().getWidth(), in.getPostviewSize().getHeight(), in.getPostviewImageFormat());
            imageReaderProxyCreateImageReaderProxy.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda3
                @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
                public final void onImageAvailable(ImageReaderProxy imageReaderProxy2) {
                    this.f$0.m185lambda$transform$2$androidxcameracoreimagecaptureCaptureNode(imageReaderProxy2);
                }
            }, CameraXExecutors.mainThreadExecutor());
            this.mSafeCloseImageReaderForPostview = new SafeCloseImageReaderProxy(imageReaderProxyCreateImageReaderProxy);
            in.setPostviewSurface(imageReaderProxyCreateImageReaderProxy.getSurface(), in.getPostviewSize(), in.getPostviewImageFormat());
        }
        in.getRequestEdge().setListener(consumer);
        in.getErrorEdge().setListener(new Consumer() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda4
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.sendCaptureError((TakePictureManager.CaptureError) obj);
            }
        });
        ProcessingNode.In inOf = ProcessingNode.In.of(in.getInputFormat(), in.getOutputFormat());
        this.mOutputEdge = inOf;
        return inOf;
    }

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.CaptureNode$1, reason: invalid class name */
    class AnonymousClass1 extends CameraCaptureCallback {
        AnonymousClass1() {
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureStarted(int i) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m187xc251443c();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onCaptureStarted$0$androidx-camera-core-imagecapture-CaptureNode$1, reason: not valid java name */
        /* synthetic */ void m187xc251443c() {
            if (CaptureNode.this.mCurrentRequest != null) {
                CaptureNode.this.mCurrentRequest.onCaptureStarted();
            }
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureProcessProgressed(int i, final int i2) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m186xa945b317(i2);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onCaptureProcessProgressed$1$androidx-camera-core-imagecapture-CaptureNode$1, reason: not valid java name */
        /* synthetic */ void m186xa945b317(int i) {
            if (CaptureNode.this.mCurrentRequest != null) {
                CaptureNode.this.mCurrentRequest.onCaptureProcessProgressed(i);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$transform$0$androidx-camera-core-imagecapture-CaptureNode, reason: not valid java name */
    /* synthetic */ void m183lambda$transform$0$androidxcameracoreimagecaptureCaptureNode(ProcessingRequest processingRequest) {
        onRequestAvailable(processingRequest);
        this.mNoMetadataImageReader.acceptProcessingRequest(processingRequest);
    }

    /* JADX INFO: renamed from: lambda$transform$1$androidx-camera-core-imagecapture-CaptureNode, reason: not valid java name */
    /* synthetic */ void m184lambda$transform$1$androidxcameracoreimagecaptureCaptureNode(ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            if (imageProxyAcquireLatestImage != null) {
                onImageProxyAvailable(imageProxyAcquireLatestImage);
                return;
            }
            ProcessingRequest processingRequest = this.mCurrentRequest;
            if (processingRequest != null) {
                sendCaptureError(TakePictureManager.CaptureError.of(processingRequest.getRequestId(), new ImageCaptureException(2, "Failed to acquire latest image", null)));
            }
        } catch (IllegalStateException e) {
            ProcessingRequest processingRequest2 = this.mCurrentRequest;
            if (processingRequest2 != null) {
                sendCaptureError(TakePictureManager.CaptureError.of(processingRequest2.getRequestId(), new ImageCaptureException(2, "Failed to acquire latest image", e)));
            }
        }
    }

    /* JADX INFO: renamed from: lambda$transform$2$androidx-camera-core-imagecapture-CaptureNode, reason: not valid java name */
    /* synthetic */ void m185lambda$transform$2$androidxcameracoreimagecaptureCaptureNode(ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            if (imageProxyAcquireLatestImage != null) {
                propagatePostviewImage(imageProxyAcquireLatestImage);
            }
        } catch (IllegalStateException e) {
            Logger.e(TAG, "Failed to acquire latest image of postview", e);
        }
    }

    private static ImageReaderProxy createImageReaderProxy(ImageReaderProxyProvider imageReaderProxyProvider, int i, int i2, int i3) {
        if (imageReaderProxyProvider != null) {
            return imageReaderProxyProvider.newInstance(i, i2, i3, 4, 0L);
        }
        return ImageReaderProxys.createIsolatedReader(i, i2, i3, 4);
    }

    void onImageProxyAvailable(ImageProxy imageProxy) {
        Threads.checkMainThread();
        if (this.mCurrentRequest == null) {
            Logger.w(TAG, "Discarding ImageProxy which was inadvertently acquired: " + imageProxy);
            imageProxy.close();
            return;
        }
        if (((Integer) imageProxy.getImageInfo().getTagBundle().getTag(this.mCurrentRequest.getTagBundleKey())) == null) {
            Logger.w(TAG, "Discarding ImageProxy which was acquired for aborted request");
            imageProxy.close();
        } else {
            matchAndPropagateImage(imageProxy);
        }
    }

    private void matchAndPropagateImage(ImageProxy imageProxy) {
        Threads.checkMainThread();
        ((ProcessingNode.In) Objects.requireNonNull(this.mOutputEdge)).getEdge().accept(ProcessingNode.InputPacket.of(this.mCurrentRequest, imageProxy));
        ProcessingRequest processingRequest = this.mCurrentRequest;
        this.mCurrentRequest = null;
        processingRequest.onImageCaptured();
    }

    private void propagatePostviewImage(ImageProxy imageProxy) {
        if (this.mCurrentRequest == null) {
            Logger.w(TAG, "Postview image is closed due to request completed or aborted");
            imageProxy.close();
        } else {
            ((ProcessingNode.In) Objects.requireNonNull(this.mOutputEdge)).getPostviewEdge().accept(ProcessingNode.InputPacket.of(this.mCurrentRequest, imageProxy));
        }
    }

    void onRequestAvailable(final ProcessingRequest processingRequest) {
        Threads.checkMainThread();
        Preconditions.checkState(processingRequest.getStageIds().size() == 1, "only one capture stage is supported.");
        Preconditions.checkState(getCapacity() > 0, "Too many acquire images. Close image to be able to process next.");
        this.mCurrentRequest = processingRequest;
        Futures.addCallback(processingRequest.getCaptureFuture(), new FutureCallback<Void>() { // from class: androidx.camera.core.imagecapture.CaptureNode.2
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Void r1) {
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                Threads.checkMainThread();
                if (processingRequest == CaptureNode.this.mCurrentRequest) {
                    Logger.w(CaptureNode.TAG, "request aborted, id=" + CaptureNode.this.mCurrentRequest.getRequestId());
                    if (CaptureNode.this.mNoMetadataImageReader != null) {
                        CaptureNode.this.mNoMetadataImageReader.clearProcessingRequest();
                    }
                    CaptureNode.this.mCurrentRequest = null;
                }
            }
        }, CameraXExecutors.directExecutor());
    }

    void sendCaptureError(TakePictureManager.CaptureError captureError) {
        Threads.checkMainThread();
        ProcessingRequest processingRequest = this.mCurrentRequest;
        if (processingRequest == null || processingRequest.getRequestId() != captureError.getRequestId()) {
            return;
        }
        this.mCurrentRequest.onCaptureFailure(captureError.getImageCaptureException());
    }

    @Override // androidx.camera.core.processing.Node
    public void release() {
        Threads.checkMainThread();
        releaseInputResources((In) Objects.requireNonNull(this.mInputEdge), (SafeCloseImageReaderProxy) Objects.requireNonNull(this.mSafeCloseImageReaderProxy), this.mSafeCloseImageReaderForPostview);
    }

    private void releaseInputResources(In in, final SafeCloseImageReaderProxy safeCloseImageReaderProxy, final SafeCloseImageReaderProxy safeCloseImageReaderProxy2) {
        in.getSurface().close();
        in.getSurface().getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                safeCloseImageReaderProxy.safeClose();
            }
        }, CameraXExecutors.mainThreadExecutor());
        if (in.getPostviewSurface() != null) {
            in.getPostviewSurface().close();
            in.getPostviewSurface().getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    CaptureNode.lambda$releaseInputResources$4(safeCloseImageReaderProxy2);
                }
            }, CameraXExecutors.mainThreadExecutor());
        }
    }

    static /* synthetic */ void lambda$releaseInputResources$4(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        if (safeCloseImageReaderProxy != null) {
            safeCloseImageReaderProxy.safeClose();
        }
    }

    In getInputEdge() {
        return (In) Objects.requireNonNull(this.mInputEdge);
    }

    public SafeCloseImageReaderProxy getSafeCloseImageReaderProxy() {
        return (SafeCloseImageReaderProxy) Objects.requireNonNull(this.mSafeCloseImageReaderProxy);
    }

    public int getCapacity() {
        Threads.checkMainThread();
        Preconditions.checkState(this.mSafeCloseImageReaderProxy != null, "The ImageReader is not initialized.");
        return this.mSafeCloseImageReaderProxy.getCapacity();
    }

    public void setOnImageCloseListener(ForwardingImageProxy.OnImageCloseListener onImageCloseListener) {
        Threads.checkMainThread();
        Preconditions.checkState(this.mSafeCloseImageReaderProxy != null, "The ImageReader is not initialized.");
        this.mSafeCloseImageReaderProxy.setOnImageCloseListener(onImageCloseListener);
    }

    static abstract class In {
        private CameraCaptureCallback mCameraCaptureCallback = new CameraCaptureCallback() { // from class: androidx.camera.core.imagecapture.CaptureNode.In.1
        };
        private DeferrableSurface mPostviewSurface = null;
        private DeferrableSurface mSurface;

        abstract Edge<TakePictureManager.CaptureError> getErrorEdge();

        abstract ImageReaderProxyProvider getImageReaderProxyProvider();

        abstract int getInputFormat();

        abstract int getOutputFormat();

        abstract int getPostviewImageFormat();

        abstract Size getPostviewSize();

        abstract Edge<ProcessingRequest> getRequestEdge();

        abstract Size getSize();

        abstract boolean isVirtualCamera();

        In() {
        }

        DeferrableSurface getSurface() {
            return (DeferrableSurface) Objects.requireNonNull(this.mSurface);
        }

        DeferrableSurface getPostviewSurface() {
            return this.mPostviewSurface;
        }

        void setSurface(Surface surface) {
            Preconditions.checkState(this.mSurface == null, "The surface is already set.");
            this.mSurface = new ImmediateSurface(surface, getSize(), getInputFormat());
        }

        void setPostviewSurface(Surface surface, Size size, int i) {
            this.mPostviewSurface = new ImmediateSurface(surface, size, i);
        }

        CameraCaptureCallback getCameraCaptureCallback() {
            return this.mCameraCaptureCallback;
        }

        void setCameraCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
            this.mCameraCaptureCallback = cameraCaptureCallback;
        }

        static In of(Size size, int i, int i2, boolean z, ImageReaderProxyProvider imageReaderProxyProvider) {
            return new AutoValue_CaptureNode_In(size, i, i2, z, imageReaderProxyProvider, null, 35, new Edge(), new Edge());
        }

        static In of(Size size, int i, int i2, boolean z, ImageReaderProxyProvider imageReaderProxyProvider, Size size2, int i3) {
            return new AutoValue_CaptureNode_In(size, i, i2, z, imageReaderProxyProvider, size2, i3, new Edge(), new Edge());
        }
    }
}

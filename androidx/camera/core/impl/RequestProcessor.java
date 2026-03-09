package androidx.camera.core.impl;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface RequestProcessor {

    public interface Callback {

        /* JADX INFO: renamed from: androidx.camera.core.impl.RequestProcessor$Callback$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onCaptureBufferLost(Callback _this, Request request, long j, int i) {
            }

            public static void $default$onCaptureCompleted(Callback _this, Request request, CameraCaptureResult cameraCaptureResult) {
            }

            public static void $default$onCaptureFailed(Callback _this, Request request, CameraCaptureFailure cameraCaptureFailure) {
            }

            public static void $default$onCaptureProgressed(Callback _this, Request request, CameraCaptureResult cameraCaptureResult) {
            }

            public static void $default$onCaptureSequenceAborted(Callback _this, int i) {
            }

            public static void $default$onCaptureSequenceCompleted(Callback _this, int i, long j) {
            }

            public static void $default$onCaptureStarted(Callback _this, Request request, long j, long j2) {
            }
        }

        void onCaptureBufferLost(Request request, long j, int i);

        void onCaptureCompleted(Request request, CameraCaptureResult cameraCaptureResult);

        void onCaptureFailed(Request request, CameraCaptureFailure cameraCaptureFailure);

        void onCaptureProgressed(Request request, CameraCaptureResult cameraCaptureResult);

        void onCaptureSequenceAborted(int i);

        void onCaptureSequenceCompleted(int i, long j);

        void onCaptureStarted(Request request, long j, long j2);
    }

    public interface Request {
        Config getParameters();

        List<Integer> getTargetOutputConfigIds();

        int getTemplateId();
    }

    void abortCaptures();

    int setRepeating(Request request, Callback callback);

    void stopRepeating();

    int submit(Request request, Callback callback);

    int submit(List<Request> list, Callback callback);
}

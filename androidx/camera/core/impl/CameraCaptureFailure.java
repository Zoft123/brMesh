package androidx.camera.core.impl;

/* JADX INFO: loaded from: classes.dex */
public class CameraCaptureFailure {
    private final Reason mReason;

    public enum Reason {
        ERROR
    }

    public Object getCaptureFailure() {
        return null;
    }

    public CameraCaptureFailure(Reason reason) {
        this.mReason = reason;
    }

    public Reason getReason() {
        return this.mReason;
    }
}

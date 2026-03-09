package io.realm.internal;

/* JADX INFO: loaded from: classes4.dex */
public class OsMapChangeSet implements NativeObject {
    public static final int EMPTY_CHANGESET = 0;
    private static long finalizerPtr = nativeGetFinalizerPtr();
    private final long nativePtr;

    private static native long nativeGetFinalizerPtr();

    private static native String[] nativeGetStringKeyDeletions(long j);

    private static native String[] nativeGetStringKeyInsertions(long j);

    private static native String[] nativeGetStringKeyModifications(long j);

    public OsMapChangeSet(long j) {
        this.nativePtr = j;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return finalizerPtr;
    }

    public boolean isEmpty() {
        return this.nativePtr == 0;
    }

    public String[] getStringKeyDeletions() {
        return nativeGetStringKeyDeletions(this.nativePtr);
    }

    public String[] getStringKeyInsertions() {
        return nativeGetStringKeyInsertions(this.nativePtr);
    }

    public String[] getStringKeyModifications() {
        return nativeGetStringKeyModifications(this.nativePtr);
    }
}

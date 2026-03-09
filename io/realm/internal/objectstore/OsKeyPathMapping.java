package io.realm.internal.objectstore;

import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;

/* JADX INFO: loaded from: classes4.dex */
public class OsKeyPathMapping implements NativeObject {
    private static final long nativeFinalizerPtr = nativeGetFinalizerMethodPtr();
    public long mappingPointer;

    private static native long nativeCreateMapping(long j);

    private static native long nativeGetFinalizerMethodPtr();

    public OsKeyPathMapping(long j) {
        this.mappingPointer = -1L;
        this.mappingPointer = nativeCreateMapping(j);
        NativeContext.dummyContext.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.mappingPointer;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }
}

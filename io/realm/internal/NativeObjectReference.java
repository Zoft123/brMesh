package io.realm.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/* JADX INFO: loaded from: classes4.dex */
final class NativeObjectReference extends PhantomReference<NativeObject> {
    private static ReferencePool referencePool = new ReferencePool();
    private final NativeContext context;
    private final long nativeFinalizerPtr;
    private final long nativePtr;
    private NativeObjectReference next;
    private NativeObjectReference prev;

    static native void nativeCleanUp(long j, long j2);

    private static class ReferencePool {
        NativeObjectReference head;

        private ReferencePool() {
        }

        synchronized void add(NativeObjectReference nativeObjectReference) {
            nativeObjectReference.prev = null;
            nativeObjectReference.next = this.head;
            NativeObjectReference nativeObjectReference2 = this.head;
            if (nativeObjectReference2 != null) {
                nativeObjectReference2.prev = nativeObjectReference;
            }
            this.head = nativeObjectReference;
        }

        synchronized void remove(NativeObjectReference nativeObjectReference) {
            NativeObjectReference nativeObjectReference2 = nativeObjectReference.next;
            NativeObjectReference nativeObjectReference3 = nativeObjectReference.prev;
            nativeObjectReference.next = null;
            nativeObjectReference.prev = null;
            if (nativeObjectReference3 != null) {
                nativeObjectReference3.next = nativeObjectReference2;
            } else {
                this.head = nativeObjectReference2;
            }
            if (nativeObjectReference2 != null) {
                nativeObjectReference2.prev = nativeObjectReference3;
            }
        }
    }

    NativeObjectReference(NativeContext nativeContext, NativeObject nativeObject, ReferenceQueue<? super NativeObject> referenceQueue) {
        super(nativeObject, referenceQueue);
        this.nativePtr = nativeObject.getNativePtr();
        this.nativeFinalizerPtr = nativeObject.getNativeFinalizerPtr();
        this.context = nativeContext;
        referencePool.add(this);
    }

    void cleanup() {
        synchronized (this.context) {
            nativeCleanUp(this.nativeFinalizerPtr, this.nativePtr);
        }
        referencePool.remove(this);
    }
}

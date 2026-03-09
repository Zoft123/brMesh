package io.realm.internal;

import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes4.dex */
public class NativeContext {
    public static final NativeContext dummyContext;
    private static final Thread finalizingThread;
    private static final ReferenceQueue<NativeObject> referenceQueue;

    public interface NativeContextRunnable {
        void run(NativeContext nativeContext);
    }

    static {
        ReferenceQueue<NativeObject> referenceQueue2 = new ReferenceQueue<>();
        referenceQueue = referenceQueue2;
        Thread thread = new Thread(new FinalizerRunnable(referenceQueue2));
        finalizingThread = thread;
        dummyContext = new NativeContext();
        thread.setName("RealmFinalizingDaemon");
        thread.start();
    }

    public void addReference(NativeObject nativeObject) {
        new NativeObjectReference(this, nativeObject, referenceQueue);
    }

    static void execute(NativeContextRunnable nativeContextRunnable) {
        ManualReleaseNativeContext manualReleaseNativeContext = new ManualReleaseNativeContext();
        nativeContextRunnable.run(manualReleaseNativeContext);
        manualReleaseNativeContext.release();
    }

    private static class ManualReleaseNativeContext extends NativeContext {
        private final LinkedList<NativeObject> references = new LinkedList<>();

        ManualReleaseNativeContext() {
        }

        @Override // io.realm.internal.NativeContext
        public void addReference(NativeObject nativeObject) {
            this.references.add(nativeObject);
        }

        public void release() {
            for (NativeObject nativeObject : this.references) {
                NativeObjectReference.nativeCleanUp(nativeObject.getNativeFinalizerPtr(), nativeObject.getNativePtr());
            }
        }
    }
}

package io.realm.internal;

import io.realm.log.RealmLog;
import java.lang.ref.ReferenceQueue;

/* JADX INFO: loaded from: classes4.dex */
class FinalizerRunnable implements Runnable {
    private final ReferenceQueue<NativeObject> referenceQueue;

    FinalizerRunnable(ReferenceQueue<NativeObject> referenceQueue) {
        this.referenceQueue = referenceQueue;
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                ((NativeObjectReference) this.referenceQueue.remove()).cleanup();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                RealmLog.fatal("The FinalizerRunnable thread has been interrupted. Native resources cannot be freed anymore", new Object[0]);
                return;
            }
        }
    }
}

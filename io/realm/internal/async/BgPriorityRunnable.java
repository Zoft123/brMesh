package io.realm.internal.async;

import android.os.Process;

/* JADX INFO: loaded from: classes4.dex */
public class BgPriorityRunnable implements Runnable {
    private final Runnable runnable;

    BgPriorityRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        this.runnable.run();
    }
}

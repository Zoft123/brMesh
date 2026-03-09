package io.realm.internal.async;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* JADX INFO: loaded from: classes4.dex */
public class RealmThreadPoolExecutor extends ThreadPoolExecutor implements AutoCloseable {
    private static final int CORE_POOL_SIZE = calculateCorePoolSize();
    private static final int QUEUE_SIZE = 100;
    private static final String SYS_CPU_DIR = "/sys/devices/system/cpu/";
    private boolean isPaused;
    private ReentrantLock pauseLock;
    private Condition unpaused;

    @Override // java.lang.AutoCloseable
    public /* synthetic */ void close() {
        UByte$$ExternalSyntheticBackport0.m((ExecutorService) this);
    }

    public static RealmThreadPoolExecutor newDefaultExecutor() {
        int i = CORE_POOL_SIZE;
        return new RealmThreadPoolExecutor(i, i);
    }

    public static RealmThreadPoolExecutor newSingleThreadExecutor() {
        return new RealmThreadPoolExecutor(1, 1);
    }

    private static int calculateCorePoolSize() {
        int iCountFilesInDir = countFilesInDir(SYS_CPU_DIR, "cpu[0-9]+");
        if (iCountFilesInDir <= 0) {
            iCountFilesInDir = Runtime.getRuntime().availableProcessors();
        }
        if (iCountFilesInDir <= 0) {
            return 1;
        }
        return (iCountFilesInDir * 2) + 1;
    }

    private static int countFilesInDir(String str, String str2) {
        final Pattern patternCompile = Pattern.compile(str2);
        try {
            File[] fileArrListFiles = new File(str).listFiles(new FileFilter() { // from class: io.realm.internal.async.RealmThreadPoolExecutor.1
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    return patternCompile.matcher(file.getName()).matches();
                }
            });
            if (fileArrListFiles == null) {
                return 0;
            }
            return fileArrListFiles.length;
        } catch (SecurityException unused) {
            return 0;
        }
    }

    private RealmThreadPoolExecutor(int i, int i2) {
        super(i, i2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100));
        ReentrantLock reentrantLock = new ReentrantLock();
        this.pauseLock = reentrantLock;
        this.unpaused = reentrantLock.newCondition();
    }

    public Future<?> submitTransaction(Runnable runnable) {
        return super.submit(new BgPriorityRunnable(runnable));
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        this.pauseLock.lock();
        while (this.isPaused) {
            try {
                try {
                    this.unpaused.await();
                } catch (InterruptedException unused) {
                    thread.interrupt();
                }
            } finally {
                this.pauseLock.unlock();
            }
        }
    }

    public void pause() {
        this.pauseLock.lock();
        try {
            this.isPaused = true;
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void resume() {
        this.pauseLock.lock();
        try {
            this.isPaused = false;
            this.unpaused.signalAll();
        } finally {
            this.pauseLock.unlock();
        }
    }
}

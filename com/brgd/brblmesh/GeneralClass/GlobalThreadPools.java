package com.brgd.brblmesh.GeneralClass;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class GlobalThreadPools {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int KEEP_ALIVE_SECONDS = 60;
    private static final int MAXIMUM_POOL_SIZE;
    private static final ExecutorService THREAD_POOL_EXECUTOR;
    private static volatile GlobalThreadPools instance;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        CORE_POOL_SIZE = iAvailableProcessors;
        int i = iAvailableProcessors * 2;
        MAXIMUM_POOL_SIZE = i;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(iAvailableProcessors);
        sPoolWorkQueue = linkedBlockingQueue;
        ThreadFactory threadFactory = new ThreadFactory() { // from class: com.brgd.brblmesh.GeneralClass.GlobalThreadPools.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "MangoTask #" + this.mCount.getAndIncrement());
            }
        };
        sThreadFactory = threadFactory;
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(iAvailableProcessors, i, 60L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private GlobalThreadPools() {
    }

    public static GlobalThreadPools getInstance() {
        if (instance == null) {
            synchronized (GlobalThreadPools.class) {
                if (instance == null) {
                    instance = new GlobalThreadPools();
                }
            }
        }
        return instance;
    }

    public void execute(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public List<Runnable> shutdownNow() {
        return THREAD_POOL_EXECUTOR.shutdownNow();
    }

    public void shutDown() {
        ExecutorService executorService = THREAD_POOL_EXECUTOR;
        executorService.shutdown();
        try {
            if (executorService.awaitTermination(60L, TimeUnit.SECONDS)) {
                return;
            }
            executorService.shutdownNow();
        } catch (InterruptedException unused) {
            THREAD_POOL_EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

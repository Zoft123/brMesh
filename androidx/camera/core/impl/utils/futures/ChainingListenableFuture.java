package androidx.camera.core.impl.utils.futures;

import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
class ChainingListenableFuture<I, O> extends FutureChain<O> implements Runnable {
    private AsyncFunction<? super I, ? extends O> mFunction;
    private ListenableFuture<? extends I> mInputFuture;
    private final BlockingQueue<Boolean> mMayInterruptIfRunningChannel = new LinkedBlockingQueue(1);
    private final CountDownLatch mOutputCreated = new CountDownLatch(1);
    volatile ListenableFuture<? extends O> mOutputFuture;

    ChainingListenableFuture(AsyncFunction<? super I, ? extends O> asyncFunction, ListenableFuture<? extends I> listenableFuture) {
        this.mFunction = (AsyncFunction) Preconditions.checkNotNull(asyncFunction);
        this.mInputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
    }

    @Override // androidx.camera.core.impl.utils.futures.FutureChain, java.util.concurrent.Future
    public O get() throws ExecutionException, InterruptedException {
        if (!isDone()) {
            ListenableFuture<? extends I> listenableFuture = this.mInputFuture;
            if (listenableFuture != null) {
                listenableFuture.get();
            }
            this.mOutputCreated.await();
            ListenableFuture<? extends O> listenableFuture2 = this.mOutputFuture;
            if (listenableFuture2 != null) {
                listenableFuture2.get();
            }
        }
        return (O) super.get();
    }

    @Override // androidx.camera.core.impl.utils.futures.FutureChain, java.util.concurrent.Future
    public O get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!isDone()) {
            if (timeUnit != TimeUnit.NANOSECONDS) {
                j = TimeUnit.NANOSECONDS.convert(j, timeUnit);
                timeUnit = TimeUnit.NANOSECONDS;
            }
            ListenableFuture<? extends I> listenableFuture = this.mInputFuture;
            if (listenableFuture != null) {
                long jNanoTime = System.nanoTime();
                listenableFuture.get(j, timeUnit);
                j -= Math.max(0L, System.nanoTime() - jNanoTime);
            }
            long jNanoTime2 = System.nanoTime();
            if (!this.mOutputCreated.await(j, timeUnit)) {
                throw new TimeoutException();
            }
            j -= Math.max(0L, System.nanoTime() - jNanoTime2);
            ListenableFuture<? extends O> listenableFuture2 = this.mOutputFuture;
            if (listenableFuture2 != null) {
                listenableFuture2.get(j, timeUnit);
            }
        }
        return (O) super.get(j, timeUnit);
    }

    @Override // androidx.camera.core.impl.utils.futures.FutureChain, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        if (!super.cancel(z)) {
            return false;
        }
        putUninterruptibly(this.mMayInterruptIfRunningChannel, Boolean.valueOf(z));
        cancel(this.mInputFuture, z);
        cancel(this.mOutputFuture, z);
        return true;
    }

    private void cancel(Future<?> future, boolean z) {
        if (future != null) {
            future.cancel(z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.camera.core.impl.utils.futures.AsyncFunction<? super I, ? extends O>, com.google.common.util.concurrent.ListenableFuture<? extends I>] */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.camera.core.impl.utils.futures.AsyncFunction<? super I, ? extends O>, com.google.common.util.concurrent.ListenableFuture<? extends I>] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    @Override // java.lang.Runnable
    public void run() {
        ?? r0;
        CountDownLatch countDownLatch = (AsyncFunction<? super I, ? extends O>) null;
        try {
            try {
                try {
                    try {
                        final ListenableFuture<? extends O> listenableFutureApply = this.mFunction.apply(Futures.getUninterruptibly(this.mInputFuture));
                        this.mOutputFuture = listenableFutureApply;
                        if (isCancelled()) {
                            listenableFutureApply.cancel(((Boolean) takeUninterruptibly(this.mMayInterruptIfRunningChannel)).booleanValue());
                            this.mOutputFuture = null;
                        } else {
                            listenableFutureApply.addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.ChainingListenableFuture.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        try {
                                            try {
                                                ChainingListenableFuture.this.set(Futures.getUninterruptibly(listenableFutureApply));
                                            } catch (ExecutionException e) {
                                                ChainingListenableFuture.this.setException(e.getCause());
                                            }
                                        } catch (CancellationException unused) {
                                            ChainingListenableFuture.this.cancel(false);
                                        }
                                    } finally {
                                        ChainingListenableFuture.this.mOutputFuture = null;
                                    }
                                }
                            }, CameraXExecutors.directExecutor());
                        }
                    } catch (Error e) {
                        setException(e);
                        r0 = countDownLatch;
                        this.mFunction = (AsyncFunction<? super I, ? extends O>) r0;
                        this.mInputFuture = (ListenableFuture<? extends I>) r0;
                        this.mOutputCreated.countDown();
                        return;
                    } catch (UndeclaredThrowableException e2) {
                        setException(e2.getCause());
                        r0 = countDownLatch;
                        this.mFunction = (AsyncFunction<? super I, ? extends O>) r0;
                        this.mInputFuture = (ListenableFuture<? extends I>) r0;
                        this.mOutputCreated.countDown();
                        return;
                    }
                } catch (CancellationException unused) {
                    cancel(false);
                } catch (ExecutionException e3) {
                    setException(e3.getCause());
                }
                this.mFunction = null;
                this.mInputFuture = null;
                countDownLatch = (AsyncFunction<? super I, ? extends O>) this.mOutputCreated;
                countDownLatch.countDown();
            } catch (Throwable th) {
                this.mFunction = (AsyncFunction<? super I, ? extends O>) countDownLatch;
                this.mInputFuture = (ListenableFuture<? extends I>) countDownLatch;
                this.mOutputCreated.countDown();
                throw th;
            }
        } catch (Exception e4) {
            setException(e4);
            r0 = countDownLatch;
        }
    }

    private <E> E takeUninterruptibly(BlockingQueue<E> blockingQueue) {
        E eTake;
        boolean z = false;
        while (true) {
            try {
                eTake = blockingQueue.take();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return eTake;
    }

    private <E> void putUninterruptibly(BlockingQueue<E> blockingQueue, E e) {
        boolean z = false;
        while (true) {
            try {
                blockingQueue.put(e);
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }
}

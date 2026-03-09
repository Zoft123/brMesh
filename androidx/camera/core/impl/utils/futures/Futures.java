package androidx.camera.core.impl.utils.futures;

import androidx.arch.core.util.Function;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.ImmediateFuture;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import cn.com.broadlink.blelight.util.EListUtils;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public final class Futures {
    private static final Function<?, ?> IDENTITY_FUNCTION = new Function<Object, Object>() { // from class: androidx.camera.core.impl.utils.futures.Futures.2
        @Override // androidx.arch.core.util.Function
        public Object apply(Object obj) {
            return obj;
        }
    };

    public static <V> ListenableFuture<V> immediateFuture(V v) {
        if (v == null) {
            return ImmediateFuture.nullFuture();
        }
        return new ImmediateFuture.ImmediateSuccessfulFuture(v);
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable th) {
        return new ImmediateFuture.ImmediateFailedFuture(th);
    }

    public static <V> ScheduledFuture<V> immediateFailedScheduledFuture(Throwable th) {
        return new ImmediateFuture.ImmediateFailedScheduledFuture(th);
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        ChainingListenableFuture chainingListenableFuture = new ChainingListenableFuture(asyncFunction, listenableFuture);
        listenableFuture.addListener(chainingListenableFuture, executor);
        return chainingListenableFuture;
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, final Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        return transformAsync(listenableFuture, new AsyncFunction<I, O>() { // from class: androidx.camera.core.impl.utils.futures.Futures.1
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public ListenableFuture<O> apply(I i) {
                return Futures.immediateFuture(function.apply(i));
            }
        }, executor);
    }

    public static <V> void propagate(ListenableFuture<V> listenableFuture, CallbackToFutureAdapter.Completer<V> completer) {
        propagateTransform(listenableFuture, IDENTITY_FUNCTION, completer, CameraXExecutors.directExecutor());
    }

    public static <I, O> void propagateTransform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, CallbackToFutureAdapter.Completer<O> completer, Executor executor) {
        propagateTransform(true, listenableFuture, function, completer, executor);
    }

    private static <I, O> void propagateTransform(boolean z, final ListenableFuture<I> listenableFuture, final Function<? super I, ? extends O> function, final CallbackToFutureAdapter.Completer<O> completer, Executor executor) {
        Preconditions.checkNotNull(listenableFuture);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(completer);
        Preconditions.checkNotNull(executor);
        addCallback(listenableFuture, new FutureCallback<I>() { // from class: androidx.camera.core.impl.utils.futures.Futures.3
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(I i) {
                try {
                    completer.set(function.apply(i));
                } catch (Throwable th) {
                    completer.setException(th);
                }
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                completer.setException(th);
            }
        }, executor);
        if (z) {
            completer.addCancellationListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.Futures.4
                @Override // java.lang.Runnable
                public void run() {
                    listenableFuture.cancel(true);
                }
            }, CameraXExecutors.directExecutor());
        }
    }

    public static <V> ListenableFuture<V> nonCancellationPropagating(final ListenableFuture<V> listenableFuture) {
        Preconditions.checkNotNull(listenableFuture);
        return listenableFuture.isDone() ? listenableFuture : CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda3
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return Futures.lambda$nonCancellationPropagating$0(listenableFuture, completer);
            }
        });
    }

    static /* synthetic */ Object lambda$nonCancellationPropagating$0(ListenableFuture listenableFuture, CallbackToFutureAdapter.Completer completer) throws Exception {
        propagateTransform(false, listenableFuture, IDENTITY_FUNCTION, completer, CameraXExecutors.directExecutor());
        return "nonCancellationPropagating[" + listenableFuture + "]";
    }

    public static <V> ListenableFuture<List<V>> successfulAsList(Collection<? extends ListenableFuture<? extends V>> collection) {
        return new ListFuture(new ArrayList(collection), false, CameraXExecutors.directExecutor());
    }

    public static <V> ListenableFuture<List<V>> allAsList(Collection<? extends ListenableFuture<? extends V>> collection) {
        return new ListFuture(new ArrayList(collection), true, CameraXExecutors.directExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback, Executor executor) {
        Preconditions.checkNotNull(futureCallback);
        listenableFuture.addListener(new CallbackListener(listenableFuture, futureCallback), executor);
    }

    private static final class CallbackListener<V> implements Runnable {
        final FutureCallback<? super V> mCallback;
        final Future<V> mFuture;

        CallbackListener(Future<V> future, FutureCallback<? super V> futureCallback) {
            this.mFuture = future;
            this.mCallback = futureCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mCallback.onSuccess(Futures.getDone(this.mFuture));
            } catch (Error e) {
                e = e;
                this.mCallback.onFailure(e);
            } catch (RuntimeException e2) {
                e = e2;
                this.mCallback.onFailure(e);
            } catch (ExecutionException e3) {
                Throwable cause = e3.getCause();
                if (cause == null) {
                    this.mCallback.onFailure(e3);
                } else {
                    this.mCallback.onFailure(cause);
                }
            }
        }

        public String toString() {
            return getClass().getSimpleName() + EListUtils.DEFAULT_JOIN_SEPARATOR + this.mCallback;
        }
    }

    public static <V> V getDone(Future<V> future) throws ExecutionException {
        Preconditions.checkState(future.isDone(), "Future was expected to be done, " + future);
        return (V) getUninterruptibly(future);
    }

    public static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        V v;
        boolean z = false;
        while (true) {
            try {
                v = future.get();
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
        return v;
    }

    public static <V> ListenableFuture<V> makeTimeoutFuture(final long j, final ScheduledExecutorService scheduledExecutorService, final ListenableFuture<V> listenableFuture) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return Futures.lambda$makeTimeoutFuture$3(listenableFuture, scheduledExecutorService, j, completer);
            }
        });
    }

    static /* synthetic */ Object lambda$makeTimeoutFuture$3(final ListenableFuture listenableFuture, ScheduledExecutorService scheduledExecutorService, final long j, final CallbackToFutureAdapter.Completer completer) throws Exception {
        propagate(listenableFuture, completer);
        if (!listenableFuture.isDone()) {
            final ScheduledFuture scheduledFutureSchedule = scheduledExecutorService.schedule(new Callable() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda7
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return Boolean.valueOf(completer.setException(new TimeoutException("Future[" + listenableFuture + "] is not done within " + j + " ms.")));
                }
            }, j, TimeUnit.MILLISECONDS);
            listenableFuture.addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    scheduledFutureSchedule.cancel(true);
                }
            }, CameraXExecutors.directExecutor());
        }
        return "TimeoutFuture[" + listenableFuture + "]";
    }

    public static <V> ListenableFuture<V> makeTimeoutFuture(final long j, final ScheduledExecutorService scheduledExecutorService, final V v, final boolean z, final ListenableFuture<V> listenableFuture) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda4
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return Futures.lambda$makeTimeoutFuture$6(listenableFuture, scheduledExecutorService, v, z, j, completer);
            }
        });
    }

    static /* synthetic */ Object lambda$makeTimeoutFuture$6(final ListenableFuture listenableFuture, ScheduledExecutorService scheduledExecutorService, final Object obj, final boolean z, long j, final CallbackToFutureAdapter.Completer completer) throws Exception {
        propagate(listenableFuture, completer);
        if (!listenableFuture.isDone()) {
            final ScheduledFuture<?> scheduledFutureSchedule = scheduledExecutorService.schedule(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Futures.lambda$makeTimeoutFuture$4(completer, obj, z, listenableFuture);
                }
            }, j, TimeUnit.MILLISECONDS);
            listenableFuture.addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    scheduledFutureSchedule.cancel(true);
                }
            }, CameraXExecutors.directExecutor());
        }
        return "TimeoutFuture[" + listenableFuture + "]";
    }

    static /* synthetic */ void lambda$makeTimeoutFuture$4(CallbackToFutureAdapter.Completer completer, Object obj, boolean z, ListenableFuture listenableFuture) {
        completer.set(obj);
        if (z) {
            listenableFuture.cancel(true);
        }
    }

    public static <V> ListenableFuture<Void> transformAsyncOnCompletion(final ListenableFuture<V> listenableFuture) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return Futures.lambda$transformAsyncOnCompletion$8(listenableFuture, completer);
            }
        });
    }

    static /* synthetic */ Object lambda$transformAsyncOnCompletion$8(ListenableFuture listenableFuture, final CallbackToFutureAdapter.Completer completer) throws Exception {
        listenableFuture.addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.Futures$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                completer.set(null);
            }
        }, CameraXExecutors.directExecutor());
        return "transformVoidFuture [" + listenableFuture + "]";
    }

    private Futures() {
    }
}

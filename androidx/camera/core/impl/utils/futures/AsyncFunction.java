package androidx.camera.core.impl.utils.futures;

import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface AsyncFunction<I, O> {
    ListenableFuture<O> apply(I i) throws Exception;
}

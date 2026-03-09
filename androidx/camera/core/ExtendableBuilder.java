package androidx.camera.core;

import androidx.camera.core.impl.MutableConfig;

/* JADX INFO: loaded from: classes.dex */
public interface ExtendableBuilder<T> {
    T build();

    MutableConfig getMutableConfig();
}

package org.greenrobot.eventbus;

/* JADX INFO: loaded from: classes4.dex */
public interface MainThreadSupport {
    Poster createPoster(EventBus eventBus);

    boolean isMainThread();
}

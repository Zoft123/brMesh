package io.realm.mongodb.sync;

/* JADX INFO: loaded from: classes4.dex */
public interface SubscriptionSet$StateChangeCallback {
    void onError(Throwable th);

    void onStateChange(SubscriptionSet subscriptionSet);
}

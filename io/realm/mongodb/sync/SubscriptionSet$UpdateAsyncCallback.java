package io.realm.mongodb.sync;

/* JADX INFO: loaded from: classes4.dex */
public interface SubscriptionSet$UpdateAsyncCallback extends SubscriptionSet$UpdateCallback {
    void onError(Throwable th);

    void onSuccess(SubscriptionSet subscriptionSet);
}

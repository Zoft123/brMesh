package io.realm.internal.objectstore;

import io.realm.mongodb.sync.SubscriptionSet;
import io.realm.mongodb.sync.SubscriptionSet$UpdateAsyncCallback;

/* JADX INFO: loaded from: classes4.dex */
class OsSubscriptionSet$3 implements Runnable {
    final /* synthetic */ OsSubscriptionSet this$0;
    final /* synthetic */ SubscriptionSet$UpdateAsyncCallback val$callback;

    OsSubscriptionSet$3(OsSubscriptionSet osSubscriptionSet, SubscriptionSet$UpdateAsyncCallback subscriptionSet$UpdateAsyncCallback) {
        this.this$0 = osSubscriptionSet;
        this.val$callback = subscriptionSet$UpdateAsyncCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            final SubscriptionSet subscriptionSetUpdate = this.this$0.update(this.val$callback);
            OsSubscriptionSet.access$000(this.this$0).post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet$3.1
                @Override // java.lang.Runnable
                public void run() {
                    OsSubscriptionSet$3.this.val$callback.onSuccess(subscriptionSetUpdate);
                }
            });
        } catch (Throwable th) {
            OsSubscriptionSet.access$000(this.this$0).post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet$3.2
                @Override // java.lang.Runnable
                public void run() {
                    OsSubscriptionSet$3.this.val$callback.onError(th);
                }
            });
        }
    }
}

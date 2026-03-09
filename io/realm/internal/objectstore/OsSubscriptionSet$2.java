package io.realm.internal.objectstore;

import io.realm.mongodb.sync.SubscriptionSet$StateChangeCallback;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
class OsSubscriptionSet$2 implements Runnable {
    final /* synthetic */ OsSubscriptionSet this$0;
    final /* synthetic */ SubscriptionSet$StateChangeCallback val$callback;
    final /* synthetic */ Long val$timeOut;
    final /* synthetic */ TimeUnit val$unit;

    OsSubscriptionSet$2(OsSubscriptionSet osSubscriptionSet, Long l, TimeUnit timeUnit, SubscriptionSet$StateChangeCallback subscriptionSet$StateChangeCallback) {
        this.this$0 = osSubscriptionSet;
        this.val$timeOut = l;
        this.val$unit = timeUnit;
        this.val$callback = subscriptionSet$StateChangeCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.this$0.waitForSynchronization(this.val$timeOut, this.val$unit);
            OsSubscriptionSet.access$000(this.this$0).post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet$2.1
                @Override // java.lang.Runnable
                public void run() {
                    OsSubscriptionSet$2.this.val$callback.onStateChange(OsSubscriptionSet$2.this.this$0);
                }
            });
        } catch (Exception e) {
            OsSubscriptionSet.access$000(this.this$0).post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet$2.2
                @Override // java.lang.Runnable
                public void run() {
                    OsSubscriptionSet$2.this.val$callback.onError(e);
                }
            });
        }
    }
}

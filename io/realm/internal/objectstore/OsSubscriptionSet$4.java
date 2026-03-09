package io.realm.internal.objectstore;

import io.realm.mongodb.sync.Subscription;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes4.dex */
class OsSubscriptionSet$4 implements Iterator<Subscription> {
    private int cursor = 0;
    private final int size;
    final /* synthetic */ OsSubscriptionSet this$0;

    OsSubscriptionSet$4(OsSubscriptionSet osSubscriptionSet) {
        this.this$0 = osSubscriptionSet;
        this.size = osSubscriptionSet.size();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.cursor < this.size;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public Subscription next() {
        if (this.cursor >= this.size) {
            throw new NoSuchElementException("Iterator has no more elements. Tried index " + this.cursor + ". Size is " + this.size + ".");
        }
        long jAccess$200 = OsSubscriptionSet.access$200(OsSubscriptionSet.access$100(this.this$0), this.cursor);
        this.cursor++;
        return new OsSubscription(jAccess$200);
    }
}

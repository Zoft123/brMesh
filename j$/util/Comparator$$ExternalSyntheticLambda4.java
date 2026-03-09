package j$.util;

import java.io.Serializable;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda4 implements java.util.Comparator, Serializable {
    public final /* synthetic */ ToLongFunction f$0;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda4(ToLongFunction toLongFunction) {
        this.f$0 = toLongFunction;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ToLongFunction toLongFunction = this.f$0;
        return Long.compare(toLongFunction.applyAsLong(obj), toLongFunction.applyAsLong(obj2));
    }
}

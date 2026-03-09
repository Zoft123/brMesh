package j$.util;

import java.io.Serializable;
import java.util.function.Function;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda5 implements java.util.Comparator, Serializable {
    public final /* synthetic */ java.util.Comparator f$0;
    public final /* synthetic */ Function f$1;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda5(java.util.Comparator comparator, Function function) {
        this.f$0 = comparator;
        this.f$1 = function;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        java.util.Comparator comparator = this.f$0;
        Function function = this.f$1;
        return comparator.compare(function.apply(obj), function.apply(obj2));
    }
}

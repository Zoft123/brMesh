package j$.util;

import java.io.Serializable;
import java.util.function.Function;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda3 implements java.util.Comparator, Serializable {
    public final /* synthetic */ Function f$0;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda3(Function function) {
        this.f$0 = function;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Function function = this.f$0;
        return ((Comparable) function.apply(obj)).compareTo(function.apply(obj2));
    }
}

package j$.util;

import java.io.Serializable;
import java.util.function.ToDoubleFunction;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda2 implements java.util.Comparator, Serializable {
    public final /* synthetic */ ToDoubleFunction f$0;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda2(ToDoubleFunction toDoubleFunction) {
        this.f$0 = toDoubleFunction;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ToDoubleFunction toDoubleFunction = this.f$0;
        return Double.compare(toDoubleFunction.applyAsDouble(obj), toDoubleFunction.applyAsDouble(obj2));
    }
}

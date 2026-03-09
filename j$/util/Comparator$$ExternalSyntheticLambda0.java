package j$.util;

import java.io.Serializable;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda0 implements java.util.Comparator, Serializable {
    public final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda0(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ToIntFunction toIntFunction = this.f$0;
        return Integer.compare(toIntFunction.applyAsInt(obj), toIntFunction.applyAsInt(obj2));
    }
}

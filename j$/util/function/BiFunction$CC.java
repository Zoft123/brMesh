package j$.util.function;

import j$.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: j$.util.function.BiFunction$-CC, reason: invalid class name */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class BiFunction$CC {
    public static BiFunction $default$andThen(final BiFunction biFunction, final Function function) {
        Objects.requireNonNull(function);
        return new BiFunction() { // from class: j$.util.function.BiFunction$$ExternalSyntheticLambda0
            public /* synthetic */ BiFunction andThen(Function function2) {
                return BiFunction$CC.$default$andThen(this, function2);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return function.apply(biFunction.apply(obj, obj2));
            }
        };
    }
}

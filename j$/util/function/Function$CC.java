package j$.util.function;

import j$.util.Objects;
import java.util.function.Function;

/* JADX INFO: renamed from: j$.util.function.Function$-CC, reason: invalid class name */
/* JADX INFO: loaded from: classes3.dex */
public abstract /* synthetic */ class Function$CC {
    public static Function $default$compose(final Function function, final Function function2) {
        Objects.requireNonNull(function2);
        return new Function() { // from class: j$.util.function.Function$$ExternalSyntheticLambda2
            public /* synthetic */ Function andThen(Function function3) {
                return Function$CC.$default$andThen(this, function3);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply(function2.apply(obj));
            }

            public /* synthetic */ Function compose(Function function3) {
                return Function$CC.$default$compose(this, function3);
            }
        };
    }

    public static Function $default$andThen(final Function function, final Function function2) {
        Objects.requireNonNull(function2);
        return new Function() { // from class: j$.util.function.Function$$ExternalSyntheticLambda1
            public /* synthetic */ Function andThen(Function function3) {
                return Function$CC.$default$andThen(this, function3);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function2.apply(function.apply(obj));
            }

            public /* synthetic */ Function compose(Function function3) {
                return Function$CC.$default$compose(this, function3);
            }
        };
    }
}

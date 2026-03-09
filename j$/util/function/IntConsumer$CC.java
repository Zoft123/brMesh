package j$.util.function;

import j$.util.Objects;
import java.util.function.IntConsumer;

/* JADX INFO: renamed from: j$.util.function.IntConsumer$-CC, reason: invalid class name */
/* JADX INFO: loaded from: classes3.dex */
public abstract /* synthetic */ class IntConsumer$CC {
    public static IntConsumer $default$andThen(final IntConsumer intConsumer, final IntConsumer intConsumer2) {
        Objects.requireNonNull(intConsumer2);
        return new IntConsumer() { // from class: j$.util.function.IntConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                IntConsumer$CC.$private$lambda$andThen$0(intConsumer, intConsumer2, i);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer3) {
                return IntConsumer$CC.$default$andThen(this, intConsumer3);
            }
        };
    }

    public static /* synthetic */ void $private$lambda$andThen$0(IntConsumer intConsumer, IntConsumer intConsumer2, int i) {
        intConsumer.accept(i);
        intConsumer2.accept(i);
    }
}

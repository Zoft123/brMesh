package j$.util.stream;

import j$.util.function.IntConsumer$CC;
import java.util.function.IntConsumer;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class IntPipeline$$ExternalSyntheticLambda10 implements IntConsumer {
    public final /* synthetic */ Sink f$0;

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f$0.accept(i);
    }

    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }
}

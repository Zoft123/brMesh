package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import j$.util.stream.Sink;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
abstract class MatchOps {

    enum MatchKind {
        ANY(true, true),
        ALL(false, false),
        NONE(true, false);

        private final boolean shortCircuitResult;
        private final boolean stopOnPredicateMatches;

        MatchKind(boolean z, boolean z2) {
            this.stopOnPredicateMatches = z;
            this.shortCircuitResult = z2;
        }
    }

    public static TerminalOp makeRef(final Predicate predicate, final MatchKind matchKind) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp(StreamShape.REFERENCE, matchKind, new Supplier() { // from class: j$.util.stream.MatchOps$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return MatchOps.lambda$makeRef$0(matchKind, predicate);
            }
        });
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeRef$0(MatchKind matchKind, Predicate predicate) {
        return new BooleanTerminalSink(predicate) { // from class: j$.util.stream.MatchOps.1MatchSink
            final /* synthetic */ Predicate val$predicate;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this.val$matchKind);
                this.val$predicate = predicate;
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                if (this.stop || this.val$predicate.test(obj) != this.val$matchKind.stopOnPredicateMatches) {
                    return;
                }
                this.stop = true;
                this.value = this.val$matchKind.shortCircuitResult;
            }
        };
    }

    public static TerminalOp makeInt(final IntPredicate intPredicate, final MatchKind matchKind) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp(StreamShape.INT_VALUE, matchKind, new Supplier(intPredicate) { // from class: j$.util.stream.MatchOps$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return MatchOps.lambda$makeInt$1(this.f$0, null);
            }
        });
    }

    /* JADX INFO: renamed from: j$.util.stream.MatchOps$2MatchSink, reason: invalid class name */
    class C2MatchSink extends BooleanTerminalSink implements Sink.OfInt {
        final /* synthetic */ MatchKind val$matchKind;

        @Override // j$.util.stream.Sink.OfInt
        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.CC.$default$accept(this, obj);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C2MatchSink(MatchKind matchKind, IntPredicate intPredicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
        }

        @Override // j$.util.stream.MatchOps.BooleanTerminalSink, j$.util.stream.Sink
        public void accept(int i) {
            if (this.stop) {
                return;
            }
            IntPredicate intPredicate = null;
            intPredicate.test(i);
            throw null;
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeInt$1(MatchKind matchKind, IntPredicate intPredicate) {
        return new C2MatchSink(matchKind, intPredicate);
    }

    public static TerminalOp makeLong(final LongPredicate longPredicate, final MatchKind matchKind) {
        Objects.requireNonNull(longPredicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp(StreamShape.LONG_VALUE, matchKind, new Supplier(longPredicate) { // from class: j$.util.stream.MatchOps$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return MatchOps.lambda$makeLong$2(this.f$0, null);
            }
        });
    }

    /* JADX INFO: renamed from: j$.util.stream.MatchOps$3MatchSink, reason: invalid class name */
    class C3MatchSink extends BooleanTerminalSink implements Sink.OfLong {
        final /* synthetic */ MatchKind val$matchKind;

        @Override // j$.util.stream.Sink.OfLong
        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.CC.$default$accept(this, obj);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C3MatchSink(MatchKind matchKind, LongPredicate longPredicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
        }

        @Override // j$.util.stream.MatchOps.BooleanTerminalSink, j$.util.stream.Sink
        public void accept(long j) {
            if (this.stop) {
                return;
            }
            LongPredicate longPredicate = null;
            longPredicate.test(j);
            throw null;
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeLong$2(MatchKind matchKind, LongPredicate longPredicate) {
        return new C3MatchSink(matchKind, longPredicate);
    }

    public static TerminalOp makeDouble(final DoublePredicate doublePredicate, final MatchKind matchKind) {
        Objects.requireNonNull(doublePredicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp(StreamShape.DOUBLE_VALUE, matchKind, new Supplier(doublePredicate) { // from class: j$.util.stream.MatchOps$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return MatchOps.lambda$makeDouble$3(this.f$0, null);
            }
        });
    }

    /* JADX INFO: renamed from: j$.util.stream.MatchOps$4MatchSink, reason: invalid class name */
    class C4MatchSink extends BooleanTerminalSink implements Sink.OfDouble {
        final /* synthetic */ MatchKind val$matchKind;

        @Override // j$.util.stream.Sink.OfDouble
        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.CC.$default$accept(this, obj);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C4MatchSink(MatchKind matchKind, DoublePredicate doublePredicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
        }

        @Override // j$.util.stream.MatchOps.BooleanTerminalSink, j$.util.stream.Sink, java.util.function.DoubleConsumer
        public void accept(double d) {
            if (this.stop) {
                return;
            }
            DoublePredicate doublePredicate = null;
            doublePredicate.test(d);
            throw null;
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeDouble$3(MatchKind matchKind, DoublePredicate doublePredicate) {
        return new C4MatchSink(matchKind, doublePredicate);
    }

    private static final class MatchOp implements TerminalOp {
        private final StreamShape inputShape;
        final MatchKind matchKind;
        final Supplier sinkSupplier;

        MatchOp(StreamShape streamShape, MatchKind matchKind, Supplier supplier) {
            this.inputShape = streamShape;
            this.matchKind = matchKind;
            this.sinkSupplier = supplier;
        }

        @Override // j$.util.stream.TerminalOp
        public int getOpFlags() {
            return StreamOpFlag.IS_SHORT_CIRCUIT | StreamOpFlag.NOT_ORDERED;
        }

        @Override // j$.util.stream.TerminalOp
        public Boolean evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return Boolean.valueOf(((BooleanTerminalSink) pipelineHelper.wrapAndCopyInto((BooleanTerminalSink) this.sinkSupplier.get(), spliterator)).getAndClearState());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // j$.util.stream.TerminalOp
        public Boolean evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return (Boolean) new MatchTask(this, pipelineHelper, spliterator).invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class BooleanTerminalSink implements Sink {
        boolean stop;
        boolean value;

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void begin(long j) {
            Sink.CC.$default$begin(this, j);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        BooleanTerminalSink(MatchKind matchKind) {
            this.value = !matchKind.shortCircuitResult;
        }

        public boolean getAndClearState() {
            return this.value;
        }

        @Override // j$.util.stream.Sink
        public boolean cancellationRequested() {
            return this.stop;
        }
    }

    private static final class MatchTask extends AbstractShortCircuitTask {
        private final MatchOp op;

        MatchTask(MatchOp matchOp, PipelineHelper pipelineHelper, Spliterator spliterator) {
            super(pipelineHelper, spliterator);
            this.op = matchOp;
        }

        MatchTask(MatchTask matchTask, Spliterator spliterator) {
            super(matchTask, spliterator);
            this.op = matchTask.op;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public MatchTask makeChild(Spliterator spliterator) {
            return new MatchTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public Boolean doLeaf() {
            boolean andClearState = ((BooleanTerminalSink) this.helper.wrapAndCopyInto((BooleanTerminalSink) this.op.sinkSupplier.get(), this.spliterator)).getAndClearState();
            if (andClearState != this.op.matchKind.shortCircuitResult) {
                return null;
            }
            shortCircuit(Boolean.valueOf(andClearState));
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractShortCircuitTask
        public Boolean getEmptyResult() {
            return Boolean.valueOf(!this.op.matchKind.shortCircuitResult);
        }
    }
}

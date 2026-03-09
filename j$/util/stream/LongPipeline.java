package j$.util.stream;

import j$.util.LongSummaryStatistics;
import j$.util.Objects;
import j$.util.OptionalDouble;
import j$.util.OptionalLong;
import j$.util.Spliterator;
import j$.util.Spliterators;
import j$.util.function.BiConsumer$CC;
import j$.util.function.BiFunction$CC;
import j$.util.stream.DoublePipeline;
import j$.util.stream.IntPipeline;
import j$.util.stream.MatchOps;
import j$.util.stream.Node;
import j$.util.stream.ReferencePipeline;
import j$.util.stream.Sink;
import j$.util.stream.StreamSpliterators$DelegatingSpliterator;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
abstract class LongPipeline extends AbstractPipeline implements LongStream {
    LongPipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    LongPipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    private static LongConsumer adapt(Sink sink) {
        if (sink instanceof LongConsumer) {
            return (LongConsumer) sink;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Sink<Long> s)");
        }
        Objects.requireNonNull(sink);
        return new LongPipeline$$ExternalSyntheticLambda12(sink);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Spliterator.OfLong adapt(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfLong) {
            return (Spliterator.OfLong) spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Spliterator<Long> s)");
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    @Override // j$.util.stream.AbstractPipeline
    final StreamShape getOutputShape() {
        return StreamShape.LONG_VALUE;
    }

    @Override // j$.util.stream.AbstractPipeline
    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collectLong(pipelineHelper, spliterator, z);
    }

    @Override // j$.util.stream.AbstractPipeline
    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators$LongWrappingSpliterator(pipelineHelper, supplier, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.util.stream.AbstractPipeline
    public final Spliterator.OfLong lazySpliterator(Supplier supplier) {
        return new StreamSpliterators$DelegatingSpliterator.OfLong(supplier);
    }

    @Override // j$.util.stream.AbstractPipeline
    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean zCancellationRequested;
        Spliterator.OfLong ofLongAdapt = adapt(spliterator);
        LongConsumer longConsumerAdapt = adapt(sink);
        do {
            zCancellationRequested = sink.cancellationRequested();
            if (zCancellationRequested) {
                break;
            }
        } while (ofLongAdapt.tryAdvance(longConsumerAdapt));
        return zCancellationRequested;
    }

    @Override // j$.util.stream.AbstractPipeline, j$.util.stream.PipelineHelper
    final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.longBuilder(j);
    }

    private Stream mapToObj(final LongFunction longFunction, int i) {
        return new ReferencePipeline.StatelessOp(this, StreamShape.LONG_VALUE, i) { // from class: j$.util.stream.LongPipeline.1
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i2, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.1.1
                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        this.downstream.accept(longFunction.apply(j));
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.BaseStream
    /* JADX INFO: renamed from: iterator */
    public final Iterator<Long> iterator2() {
        return Spliterators.iterator(spliterator());
    }

    @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
    public final Spliterator.OfLong spliterator() {
        return adapt(super.spliterator());
    }

    @Override // j$.util.stream.LongStream
    public final DoubleStream asDoubleStream() {
        return new DoublePipeline.StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_DISTINCT) { // from class: j$.util.stream.LongPipeline.2
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.2.1
                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        this.downstream.accept(j);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final Stream boxed() {
        return mapToObj(new LongFunction() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda3
            @Override // java.util.function.LongFunction
            public final Object apply(long j) {
                return Long.valueOf(j);
            }
        }, 0);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream map(LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return new StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, longUnaryOperator) { // from class: j$.util.stream.LongPipeline.3
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.3.1
                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        getClass();
                        LongUnaryOperator longUnaryOperator2 = null;
                        longUnaryOperator2.applyAsLong(j);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final Stream mapToObj(LongFunction longFunction) {
        Objects.requireNonNull(longFunction);
        return mapToObj(longFunction, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT);
    }

    @Override // j$.util.stream.LongStream
    public final IntStream mapToInt(LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        return new IntPipeline.StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, longToIntFunction) { // from class: j$.util.stream.LongPipeline.4
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.4.1
                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        getClass();
                        LongToIntFunction longToIntFunction2 = null;
                        longToIntFunction2.applyAsInt(j);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction) {
        Objects.requireNonNull(longToDoubleFunction);
        return new DoublePipeline.StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, longToDoubleFunction) { // from class: j$.util.stream.LongPipeline.5
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.5.1
                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        getClass();
                        LongToDoubleFunction longToDoubleFunction2 = null;
                        longToDoubleFunction2.applyAsDouble(j);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final LongStream flatMap(final LongFunction longFunction) {
        Objects.requireNonNull(longFunction);
        return new StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED) { // from class: j$.util.stream.LongPipeline.6
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.6.1
                    boolean cancellationRequestedCalled;
                    LongConsumer downstreamAsLong;

                    {
                        Sink sink2 = this.downstream;
                        Objects.requireNonNull(sink2);
                        this.downstreamAsLong = new LongPipeline$$ExternalSyntheticLambda12(sink2);
                    }

                    @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        LongStream longStream = (LongStream) longFunction.apply(j);
                        if (longStream != null) {
                            try {
                                if (!this.cancellationRequestedCalled) {
                                    longStream.sequential().forEach(this.downstreamAsLong);
                                } else {
                                    Spliterator.OfLong ofLongSpliterator = longStream.sequential().spliterator();
                                    while (!this.downstream.cancellationRequested() && ofLongSpliterator.tryAdvance(this.downstreamAsLong)) {
                                    }
                                }
                            } catch (Throwable th) {
                                try {
                                    longStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                        if (longStream != null) {
                            longStream.close();
                        }
                    }

                    @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.cancellationRequestedCalled = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.BaseStream
    public LongStream unordered() {
        return !isOrdered() ? this : new StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_ORDERED) { // from class: j$.util.stream.LongPipeline.7
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return sink;
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final LongStream filter(LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new StatelessOp(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SIZED, longPredicate) { // from class: j$.util.stream.LongPipeline.8
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.8.1
                    @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        getClass();
                        LongPredicate longPredicate2 = null;
                        longPredicate2.test(j);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final LongStream peek(final LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new StatelessOp(this, StreamShape.LONG_VALUE, 0) { // from class: j$.util.stream.LongPipeline.9
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedLong(sink) { // from class: j$.util.stream.LongPipeline.9.1
                    @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                    public void accept(long j) {
                        longConsumer.accept(j);
                        this.downstream.accept(j);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.LongStream
    public final LongStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return SliceOps.makeLong(this, 0L, j);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : SliceOps.makeLong(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.LongStream
    public final LongStream takeWhile(LongPredicate longPredicate) {
        return WhileOps.makeTakeWhileLong(this, longPredicate);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream dropWhile(LongPredicate longPredicate) {
        return WhileOps.makeDropWhileLong(this, longPredicate);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream sorted() {
        return SortedOps.makeLong(this);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream distinct() {
        return boxed().distinct().mapToLong(new ToLongFunction() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda11
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((Long) obj).longValue();
            }
        });
    }

    @Override // j$.util.stream.LongStream
    public void forEach(LongConsumer longConsumer) {
        evaluate(ForEachOps.makeLong(longConsumer, false));
    }

    @Override // j$.util.stream.LongStream
    public void forEachOrdered(LongConsumer longConsumer) {
        evaluate(ForEachOps.makeLong(longConsumer, true));
    }

    @Override // j$.util.stream.LongStream
    public final long sum() {
        return reduce(0L, new LongBinaryOperator() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda9
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j, long j2) {
                return j + j2;
            }
        });
    }

    @Override // j$.util.stream.LongStream
    public final OptionalLong min() {
        return reduce(new LongBinaryOperator() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda10
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j, long j2) {
                return Math.min(j, j2);
            }
        });
    }

    @Override // j$.util.stream.LongStream
    public final OptionalLong max() {
        return reduce(new LongBinaryOperator() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda8
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j, long j2) {
                return Math.max(j, j2);
            }
        });
    }

    static /* synthetic */ long[] lambda$average$1() {
        return new long[2];
    }

    @Override // j$.util.stream.LongStream
    public final OptionalDouble average() {
        long j = ((long[]) collect(new Supplier() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return LongPipeline.lambda$average$1();
            }
        }, new ObjLongConsumer() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda5
            @Override // java.util.function.ObjLongConsumer
            public final void accept(Object obj, long j2) {
                LongPipeline.lambda$average$2((long[]) obj, j2);
            }
        }, new BiConsumer() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LongPipeline.lambda$average$3((long[]) obj, (long[]) obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        }))[0];
        if (j > 0) {
            return OptionalDouble.of(r0[1] / j);
        }
        return OptionalDouble.empty();
    }

    static /* synthetic */ void lambda$average$2(long[] jArr, long j) {
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + j;
    }

    static /* synthetic */ void lambda$average$3(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
    }

    @Override // j$.util.stream.LongStream
    public final long count() {
        return ((Long) evaluate(ReduceOps.makeLongCounting())).longValue();
    }

    @Override // j$.util.stream.LongStream
    public final LongSummaryStatistics summaryStatistics() {
        return (LongSummaryStatistics) collect(new Supplier() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda61
            @Override // java.util.function.Supplier
            public final Object get() {
                return new LongSummaryStatistics();
            }
        }, new ObjLongConsumer() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda0
            @Override // java.util.function.ObjLongConsumer
            public final void accept(Object obj, long j) {
                ((LongSummaryStatistics) obj).accept(j);
            }
        }, new BiConsumer() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((LongSummaryStatistics) obj).combine((LongSummaryStatistics) obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        });
    }

    @Override // j$.util.stream.LongStream
    public final long reduce(long j, LongBinaryOperator longBinaryOperator) {
        return ((Long) evaluate(ReduceOps.makeLong(j, longBinaryOperator))).longValue();
    }

    @Override // j$.util.stream.LongStream
    public final OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
        return (OptionalLong) evaluate(ReduceOps.makeLong(longBinaryOperator));
    }

    @Override // j$.util.stream.LongStream
    public final Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, final BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        return evaluate(ReduceOps.makeLong(supplier, objLongConsumer, new BinaryOperator() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda7
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return LongPipeline.lambda$collect$4(biConsumer, obj, obj2);
            }
        }));
    }

    static /* synthetic */ Object lambda$collect$4(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        return obj;
    }

    @Override // j$.util.stream.LongStream
    public final boolean anyMatch(LongPredicate longPredicate) {
        return ((Boolean) evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    @Override // j$.util.stream.LongStream
    public final boolean allMatch(LongPredicate longPredicate) {
        return ((Boolean) evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    @Override // j$.util.stream.LongStream
    public final boolean noneMatch(LongPredicate longPredicate) {
        return ((Boolean) evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    @Override // j$.util.stream.LongStream
    public final OptionalLong findFirst() {
        return (OptionalLong) evaluate(FindOps.makeLong(true));
    }

    @Override // j$.util.stream.LongStream
    public final OptionalLong findAny() {
        return (OptionalLong) evaluate(FindOps.makeLong(false));
    }

    static /* synthetic */ Long[] lambda$toArray$5(int i) {
        return new Long[i];
    }

    @Override // j$.util.stream.LongStream
    public final long[] toArray() {
        return (long[]) Nodes.flattenLong((Node.OfLong) evaluateToArrayNode(new IntFunction() { // from class: j$.util.stream.LongPipeline$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return LongPipeline.lambda$toArray$5(i);
            }
        })).asPrimitiveArray();
    }

    static class Head extends LongPipeline {
        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) super.sequential();
        }

        Head(Spliterator spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        @Override // j$.util.stream.AbstractPipeline
        final Sink opWrapSink(int i, Sink sink) {
            throw new UnsupportedOperationException();
        }

        @Override // j$.util.stream.LongPipeline, j$.util.stream.LongStream
        public void forEach(LongConsumer longConsumer) {
            if (!isParallel()) {
                LongPipeline.adapt(sourceStageSpliterator()).forEachRemaining(longConsumer);
            } else {
                super.forEach(longConsumer);
            }
        }

        @Override // j$.util.stream.LongPipeline, j$.util.stream.LongStream
        public void forEachOrdered(LongConsumer longConsumer) {
            if (!isParallel()) {
                LongPipeline.adapt(sourceStageSpliterator()).forEachRemaining(longConsumer);
            } else {
                super.forEachOrdered(longConsumer);
            }
        }
    }

    static abstract class StatelessOp extends LongPipeline {
        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            return false;
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) super.sequential();
        }

        StatelessOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }

    static abstract class StatefulOp extends LongPipeline {
        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            return true;
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) super.sequential();
        }

        StatefulOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }
}

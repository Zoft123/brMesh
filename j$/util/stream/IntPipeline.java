package j$.util.stream;

import j$.util.IntSummaryStatistics;
import j$.util.Objects;
import j$.util.OptionalDouble;
import j$.util.OptionalInt;
import j$.util.Spliterator;
import j$.util.Spliterators;
import j$.util.function.BiConsumer$CC;
import j$.util.function.BiFunction$CC;
import j$.util.stream.DoublePipeline;
import j$.util.stream.LongPipeline;
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
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes3.dex */
abstract class IntPipeline extends AbstractPipeline implements IntStream {
    IntPipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    IntPipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    private static IntConsumer adapt(Sink sink) {
        if (sink instanceof IntConsumer) {
            return (IntConsumer) sink;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Sink<Integer> s)");
        }
        Objects.requireNonNull(sink);
        return new IntPipeline$$ExternalSyntheticLambda10(sink);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Spliterator.OfInt adapt(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfInt) {
            return (Spliterator.OfInt) spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Spliterator<Integer> s)");
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    @Override // j$.util.stream.AbstractPipeline
    final StreamShape getOutputShape() {
        return StreamShape.INT_VALUE;
    }

    @Override // j$.util.stream.AbstractPipeline
    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collectInt(pipelineHelper, spliterator, z);
    }

    @Override // j$.util.stream.AbstractPipeline
    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators$IntWrappingSpliterator(pipelineHelper, supplier, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.util.stream.AbstractPipeline
    public final Spliterator.OfInt lazySpliterator(Supplier supplier) {
        return new StreamSpliterators$DelegatingSpliterator.OfInt(supplier);
    }

    @Override // j$.util.stream.AbstractPipeline
    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean zCancellationRequested;
        Spliterator.OfInt ofIntAdapt = adapt(spliterator);
        IntConsumer intConsumerAdapt = adapt(sink);
        do {
            zCancellationRequested = sink.cancellationRequested();
            if (zCancellationRequested) {
                break;
            }
        } while (ofIntAdapt.tryAdvance(intConsumerAdapt));
        return zCancellationRequested;
    }

    @Override // j$.util.stream.AbstractPipeline, j$.util.stream.PipelineHelper
    final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.intBuilder(j);
    }

    private Stream mapToObj(final IntFunction intFunction, int i) {
        return new ReferencePipeline.StatelessOp(this, StreamShape.INT_VALUE, i) { // from class: j$.util.stream.IntPipeline.1
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i2, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.1.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i3) {
                        this.downstream.accept(intFunction.apply(i3));
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.BaseStream
    /* JADX INFO: renamed from: iterator */
    public final Iterator<Integer> iterator2() {
        return Spliterators.iterator(spliterator());
    }

    @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
    public final Spliterator.OfInt spliterator() {
        return adapt(super.spliterator());
    }

    @Override // j$.util.stream.IntStream
    public final LongStream asLongStream() {
        return new LongPipeline.StatelessOp(this, StreamShape.INT_VALUE, 0) { // from class: j$.util.stream.IntPipeline.2
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.2.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        this.downstream.accept(i2);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final DoubleStream asDoubleStream() {
        return new DoublePipeline.StatelessOp(this, StreamShape.INT_VALUE, 0) { // from class: j$.util.stream.IntPipeline.3
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.3.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        this.downstream.accept(i2);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final Stream boxed() {
        return mapToObj(new IntFunction() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda12
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return Integer.valueOf(i);
            }
        }, 0);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream map(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new StatelessOp(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, intUnaryOperator) { // from class: j$.util.stream.IntPipeline.4
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.4.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        getClass();
                        IntUnaryOperator intUnaryOperator2 = null;
                        intUnaryOperator2.applyAsInt(i2);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final Stream mapToObj(IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        return mapToObj(intFunction, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT);
    }

    @Override // j$.util.stream.IntStream
    public final LongStream mapToLong(IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        return new LongPipeline.StatelessOp(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, intToLongFunction) { // from class: j$.util.stream.IntPipeline.5
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.5.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        getClass();
                        IntToLongFunction intToLongFunction2 = null;
                        intToLongFunction2.applyAsLong(i2);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final DoubleStream mapToDouble(IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        return new DoublePipeline.StatelessOp(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, intToDoubleFunction) { // from class: j$.util.stream.IntPipeline.6
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.6.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        getClass();
                        IntToDoubleFunction intToDoubleFunction2 = null;
                        intToDoubleFunction2.applyAsDouble(i2);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final IntStream flatMap(final IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        return new StatelessOp(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED) { // from class: j$.util.stream.IntPipeline.7
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.7.1
                    boolean cancellationRequestedCalled;
                    IntConsumer downstreamAsInt;

                    {
                        Sink sink2 = this.downstream;
                        Objects.requireNonNull(sink2);
                        this.downstreamAsInt = new IntPipeline$$ExternalSyntheticLambda10(sink2);
                    }

                    @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        IntStream intStream = (IntStream) intFunction.apply(i2);
                        if (intStream != null) {
                            try {
                                if (!this.cancellationRequestedCalled) {
                                    intStream.sequential().forEach(this.downstreamAsInt);
                                } else {
                                    Spliterator.OfInt ofIntSpliterator = intStream.sequential().spliterator();
                                    while (!this.downstream.cancellationRequested() && ofIntSpliterator.tryAdvance(this.downstreamAsInt)) {
                                    }
                                }
                            } catch (Throwable th) {
                                try {
                                    intStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                        if (intStream != null) {
                            intStream.close();
                        }
                    }

                    @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.cancellationRequestedCalled = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.BaseStream
    public IntStream unordered() {
        return !isOrdered() ? this : new StatelessOp(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_ORDERED) { // from class: j$.util.stream.IntPipeline.8
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return sink;
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final IntStream filter(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new StatelessOp(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SIZED, intPredicate) { // from class: j$.util.stream.IntPipeline.9
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.9.1
                    @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        getClass();
                        IntPredicate intPredicate2 = null;
                        intPredicate2.test(i2);
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final IntStream peek(final IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new StatelessOp(this, StreamShape.INT_VALUE, 0) { // from class: j$.util.stream.IntPipeline.10
            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedInt(sink) { // from class: j$.util.stream.IntPipeline.10.1
                    @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                    public void accept(int i2) {
                        intConsumer.accept(i2);
                        this.downstream.accept(i2);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.IntStream
    public final IntStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return SliceOps.makeInt(this, 0L, j);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : SliceOps.makeInt(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.IntStream
    public final IntStream takeWhile(IntPredicate intPredicate) {
        return WhileOps.makeTakeWhileInt(this, intPredicate);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream dropWhile(IntPredicate intPredicate) {
        return WhileOps.makeDropWhileInt(this, intPredicate);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream sorted() {
        return SortedOps.makeInt(this);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream distinct() {
        return boxed().distinct().mapToInt(new ToIntFunction() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda11
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) obj).intValue();
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public void forEach(IntConsumer intConsumer) {
        evaluate(ForEachOps.makeInt(intConsumer, false));
    }

    @Override // j$.util.stream.IntStream
    public void forEachOrdered(IntConsumer intConsumer) {
        evaluate(ForEachOps.makeInt(intConsumer, true));
    }

    @Override // j$.util.stream.IntStream
    public final int sum() {
        return reduce(0, new IntBinaryOperator() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda5
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return i + i2;
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt min() {
        return reduce(new IntBinaryOperator() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda1
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Math.min(i, i2);
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt max() {
        return reduce(new IntBinaryOperator() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda6
            @Override // java.util.function.IntBinaryOperator
            public final int applyAsInt(int i, int i2) {
                return Math.max(i, i2);
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final long count() {
        return ((Long) evaluate(ReduceOps.makeIntCounting())).longValue();
    }

    static /* synthetic */ long[] lambda$average$1() {
        return new long[2];
    }

    @Override // j$.util.stream.IntStream
    public final OptionalDouble average() {
        long j = ((long[]) collect(new Supplier() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return IntPipeline.lambda$average$1();
            }
        }, new ObjIntConsumer() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda8
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                IntPipeline.lambda$average$2((long[]) obj, i);
            }
        }, new BiConsumer() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IntPipeline.lambda$average$3((long[]) obj, (long[]) obj2);
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

    static /* synthetic */ void lambda$average$2(long[] jArr, int i) {
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + ((long) i);
    }

    static /* synthetic */ void lambda$average$3(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
    }

    @Override // j$.util.stream.IntStream
    public final IntSummaryStatistics summaryStatistics() {
        return (IntSummaryStatistics) collect(new Supplier() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda27
            @Override // java.util.function.Supplier
            public final Object get() {
                return new IntSummaryStatistics();
            }
        }, new ObjIntConsumer() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda2
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                ((IntSummaryStatistics) obj).accept(i);
            }
        }, new BiConsumer() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((IntSummaryStatistics) obj).combine((IntSummaryStatistics) obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final int reduce(int i, IntBinaryOperator intBinaryOperator) {
        return ((Integer) evaluate(ReduceOps.makeInt(i, intBinaryOperator))).intValue();
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        return (OptionalInt) evaluate(ReduceOps.makeInt(intBinaryOperator));
    }

    @Override // j$.util.stream.IntStream
    public final Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, final BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        return evaluate(ReduceOps.makeInt(supplier, objIntConsumer, new BinaryOperator() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda4
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return IntPipeline.lambda$collect$4(biConsumer, obj, obj2);
            }
        }));
    }

    static /* synthetic */ Object lambda$collect$4(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        return obj;
    }

    @Override // j$.util.stream.IntStream
    public final boolean anyMatch(IntPredicate intPredicate) {
        return ((Boolean) evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final boolean allMatch(IntPredicate intPredicate) {
        return ((Boolean) evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final boolean noneMatch(IntPredicate intPredicate) {
        return ((Boolean) evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt findFirst() {
        return (OptionalInt) evaluate(FindOps.makeInt(true));
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt findAny() {
        return (OptionalInt) evaluate(FindOps.makeInt(false));
    }

    static /* synthetic */ Integer[] lambda$toArray$5(int i) {
        return new Integer[i];
    }

    @Override // j$.util.stream.IntStream
    public final int[] toArray() {
        return (int[]) Nodes.flattenInt((Node.OfInt) evaluateToArrayNode(new IntFunction() { // from class: j$.util.stream.IntPipeline$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return IntPipeline.lambda$toArray$5(i);
            }
        })).asPrimitiveArray();
    }

    static class Head extends IntPipeline {
        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) super.sequential();
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

        @Override // j$.util.stream.IntPipeline, j$.util.stream.IntStream
        public void forEach(IntConsumer intConsumer) {
            if (!isParallel()) {
                IntPipeline.adapt(sourceStageSpliterator()).forEachRemaining(intConsumer);
            } else {
                super.forEach(intConsumer);
            }
        }

        @Override // j$.util.stream.IntPipeline, j$.util.stream.IntStream
        public void forEachOrdered(IntConsumer intConsumer) {
            if (!isParallel()) {
                IntPipeline.adapt(sourceStageSpliterator()).forEachRemaining(intConsumer);
            } else {
                super.forEachOrdered(intConsumer);
            }
        }
    }

    static abstract class StatelessOp extends IntPipeline {
        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            return false;
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) super.sequential();
        }

        StatelessOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }

    static abstract class StatefulOp extends IntPipeline {
        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            return true;
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) super.sequential();
        }

        StatefulOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }
}

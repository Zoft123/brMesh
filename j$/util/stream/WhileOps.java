package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import j$.util.stream.DoublePipeline;
import j$.util.stream.IntPipeline;
import j$.util.stream.LongPipeline;
import j$.util.stream.Node;
import j$.util.stream.ReferencePipeline;
import j$.util.stream.Sink;
import j$.util.stream.WhileOps;
import java.util.Comparator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes3.dex */
abstract class WhileOps {
    static final int DROP_FLAGS;
    static final int TAKE_FLAGS;

    interface DropWhileOp {
        DropWhileSink opWrapSink(Sink sink, boolean z);
    }

    interface DropWhileSink extends Sink {
        long getDropCount();
    }

    static {
        int i = StreamOpFlag.NOT_SIZED;
        TAKE_FLAGS = StreamOpFlag.IS_SHORT_CIRCUIT | i;
        DROP_FLAGS = i;
    }

    static Stream makeTakeWhileRef(AbstractPipeline abstractPipeline, final Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new ReferencePipeline.StatefulOp(abstractPipeline, StreamShape.REFERENCE, TAKE_FLAGS) { // from class: j$.util.stream.WhileOps.1
            @Override // j$.util.stream.AbstractPipeline
            Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
                if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                    return opEvaluateParallel(pipelineHelper, spliterator, Nodes.castingArray()).spliterator();
                }
                return new UnorderedWhileSpliterator.OfRef.Taking(pipelineHelper.wrapSpliterator(spliterator), false, predicate);
            }

            @Override // j$.util.stream.AbstractPipeline
            Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
                return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
            }

            @Override // j$.util.stream.AbstractPipeline
            Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedReference(sink) { // from class: j$.util.stream.WhileOps.1.1
                    boolean take = true;

                    @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // java.util.function.Consumer
                    public void accept(Object obj) {
                        if (this.take) {
                            boolean zTest = predicate.test(obj);
                            this.take = zTest;
                            if (zTest) {
                                this.downstream.accept(obj);
                            }
                        }
                    }

                    @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                    public boolean cancellationRequested() {
                        return !this.take || this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$2, reason: invalid class name */
    class AnonymousClass2 extends IntPipeline.StatefulOp {
        AnonymousClass2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntPredicate intPredicate) {
            super(abstractPipeline, streamShape, i);
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.WhileOps$2$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return WhileOps.AnonymousClass2.lambda$opEvaluateParallelLazy$0(i);
                    }
                }).spliterator();
            }
            return new UnorderedWhileSpliterator.OfInt.Taking((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), false, null);
        }

        static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Integer[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return new Sink.ChainedInt(sink) { // from class: j$.util.stream.WhileOps.2.1
                boolean take = true;

                @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                public void begin(long j) {
                    this.downstream.begin(-1L);
                }

                @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                public void accept(int i2) {
                    if (this.take) {
                        AnonymousClass2.this.getClass();
                        IntPredicate intPredicate = null;
                        intPredicate.test(i2);
                        throw null;
                    }
                }

                @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                public boolean cancellationRequested() {
                    return !this.take || this.downstream.cancellationRequested();
                }
            };
        }
    }

    static IntStream makeTakeWhileInt(AbstractPipeline abstractPipeline, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new AnonymousClass2(abstractPipeline, StreamShape.INT_VALUE, TAKE_FLAGS, intPredicate);
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$3, reason: invalid class name */
    class AnonymousClass3 extends LongPipeline.StatefulOp {
        AnonymousClass3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongPredicate longPredicate) {
            super(abstractPipeline, streamShape, i);
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.WhileOps$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return WhileOps.AnonymousClass3.lambda$opEvaluateParallelLazy$0(i);
                    }
                }).spliterator();
            }
            return new UnorderedWhileSpliterator.OfLong.Taking((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), false, null);
        }

        static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Long[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return new Sink.ChainedLong(sink) { // from class: j$.util.stream.WhileOps.3.1
                boolean take = true;

                @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                public void begin(long j) {
                    this.downstream.begin(-1L);
                }

                @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                public void accept(long j) {
                    if (this.take) {
                        AnonymousClass3.this.getClass();
                        LongPredicate longPredicate = null;
                        longPredicate.test(j);
                        throw null;
                    }
                }

                @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                public boolean cancellationRequested() {
                    return !this.take || this.downstream.cancellationRequested();
                }
            };
        }
    }

    static LongStream makeTakeWhileLong(AbstractPipeline abstractPipeline, LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new AnonymousClass3(abstractPipeline, StreamShape.LONG_VALUE, TAKE_FLAGS, longPredicate);
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$4, reason: invalid class name */
    class AnonymousClass4 extends DoublePipeline.StatefulOp {
        AnonymousClass4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoublePredicate doublePredicate) {
            super(abstractPipeline, streamShape, i);
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.WhileOps$4$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return WhileOps.AnonymousClass4.lambda$opEvaluateParallelLazy$0(i);
                    }
                }).spliterator();
            }
            return new UnorderedWhileSpliterator.OfDouble.Taking((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), false, null);
        }

        static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Double[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.WhileOps.4.1
                boolean take = true;

                @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                public void begin(long j) {
                    this.downstream.begin(-1L);
                }

                @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                public void accept(double d) {
                    if (this.take) {
                        AnonymousClass4.this.getClass();
                        DoublePredicate doublePredicate = null;
                        doublePredicate.test(d);
                        throw null;
                    }
                }

                @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                public boolean cancellationRequested() {
                    return !this.take || this.downstream.cancellationRequested();
                }
            };
        }
    }

    static DoubleStream makeTakeWhileDouble(AbstractPipeline abstractPipeline, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new AnonymousClass4(abstractPipeline, StreamShape.DOUBLE_VALUE, TAKE_FLAGS, doublePredicate);
    }

    static Stream makeDropWhileRef(AbstractPipeline abstractPipeline, Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new C1Op(abstractPipeline, StreamShape.REFERENCE, DROP_FLAGS, predicate);
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$1Op, reason: invalid class name */
    class C1Op extends ReferencePipeline.StatefulOp implements DropWhileOp {
        final /* synthetic */ Predicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C1Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Predicate predicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = predicate;
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, Nodes.castingArray()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfRef.Dropping(pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        /* JADX INFO: renamed from: j$.util.stream.WhileOps$1Op$1OpSink, reason: invalid class name */
        class C1OpSink extends Sink.ChainedReference implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            /* JADX WARN: Removed duplicated region for block: B:8:0x0015  */
            @Override // java.util.function.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void accept(java.lang.Object r7) {
                /*
                    r6 = this;
                    boolean r0 = r6.take
                    if (r0 != 0) goto L15
                    j$.util.stream.WhileOps$1Op r0 = j$.util.stream.WhileOps.C1Op.this
                    java.util.function.Predicate r0 = r0.val$predicate
                    boolean r0 = r0.test(r7)
                    r1 = r0 ^ 1
                    r6.take = r1
                    if (r0 != 0) goto L13
                    goto L15
                L13:
                    r0 = 0
                    goto L16
                L15:
                    r0 = 1
                L16:
                    boolean r1 = r6.val$retainAndCountDroppedElements
                    if (r1 == 0) goto L23
                    if (r0 != 0) goto L23
                    long r2 = r6.dropCount
                    r4 = 1
                    long r2 = r2 + r4
                    r6.dropCount = r2
                L23:
                    if (r1 != 0) goto L29
                    if (r0 == 0) goto L28
                    goto L29
                L28:
                    return
                L29:
                    j$.util.stream.Sink r0 = r6.downstream
                    r0.accept(r7)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.WhileOps.C1Op.C1OpSink.accept(java.lang.Object):void");
            }

            @Override // j$.util.stream.WhileOps.DropWhileSink
            public long getDropCount() {
                return this.dropCount;
            }
        }

        @Override // j$.util.stream.WhileOps.DropWhileOp
        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new C1OpSink(sink, z);
        }
    }

    static IntStream makeDropWhileInt(AbstractPipeline abstractPipeline, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new C2Op(abstractPipeline, StreamShape.INT_VALUE, DROP_FLAGS, intPredicate);
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$2Op, reason: invalid class name */
    class C2Op extends IntPipeline.StatefulOp implements DropWhileOp {
        public C2Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntPredicate intPredicate) {
            super(abstractPipeline, streamShape, i);
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.WhileOps$2Op$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return WhileOps.C2Op.lambda$opEvaluateParallelLazy$0(i);
                    }
                }).spliterator();
            }
            return new UnorderedWhileSpliterator.OfInt.Dropping((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), false, null);
        }

        static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Integer[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        /* JADX INFO: renamed from: j$.util.stream.WhileOps$2Op$1OpSink, reason: invalid class name */
        class C1OpSink extends Sink.ChainedInt implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
            public void accept(int i) {
                if (!this.take) {
                    C2Op.this.getClass();
                    IntPredicate intPredicate = null;
                    intPredicate.test(i);
                    throw null;
                }
                this.downstream.accept(i);
            }

            @Override // j$.util.stream.WhileOps.DropWhileSink
            public long getDropCount() {
                return this.dropCount;
            }
        }

        @Override // j$.util.stream.WhileOps.DropWhileOp
        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new C1OpSink(sink, z);
        }
    }

    static LongStream makeDropWhileLong(AbstractPipeline abstractPipeline, LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new C3Op(abstractPipeline, StreamShape.LONG_VALUE, DROP_FLAGS, longPredicate);
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$3Op, reason: invalid class name */
    class C3Op extends LongPipeline.StatefulOp implements DropWhileOp {
        public C3Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongPredicate longPredicate) {
            super(abstractPipeline, streamShape, i);
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.WhileOps$3Op$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return WhileOps.C3Op.lambda$opEvaluateParallelLazy$0(i);
                    }
                }).spliterator();
            }
            return new UnorderedWhileSpliterator.OfLong.Dropping((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), false, null);
        }

        static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Long[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        /* JADX INFO: renamed from: j$.util.stream.WhileOps$3Op$1OpSink, reason: invalid class name */
        class C1OpSink extends Sink.ChainedLong implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
            public void accept(long j) {
                if (!this.take) {
                    C3Op.this.getClass();
                    LongPredicate longPredicate = null;
                    longPredicate.test(j);
                    throw null;
                }
                this.downstream.accept(j);
            }

            @Override // j$.util.stream.WhileOps.DropWhileSink
            public long getDropCount() {
                return this.dropCount;
            }
        }

        @Override // j$.util.stream.WhileOps.DropWhileOp
        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new C1OpSink(sink, z);
        }
    }

    static DoubleStream makeDropWhileDouble(AbstractPipeline abstractPipeline, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new C4Op(abstractPipeline, StreamShape.DOUBLE_VALUE, DROP_FLAGS, doublePredicate);
    }

    /* JADX INFO: renamed from: j$.util.stream.WhileOps$4Op, reason: invalid class name */
    class C4Op extends DoublePipeline.StatefulOp implements DropWhileOp {
        public C4Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoublePredicate doublePredicate) {
            super(abstractPipeline, streamShape, i);
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.WhileOps$4Op$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return WhileOps.C4Op.lambda$opEvaluateParallelLazy$0(i);
                    }
                }).spliterator();
            }
            return new UnorderedWhileSpliterator.OfDouble.Dropping((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), false, null);
        }

        static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Double[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        /* JADX INFO: renamed from: j$.util.stream.WhileOps$4Op$1OpSink, reason: invalid class name */
        class C1OpSink extends Sink.ChainedDouble implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
            public void accept(double d) {
                if (!this.take) {
                    C4Op.this.getClass();
                    DoublePredicate doublePredicate = null;
                    doublePredicate.test(d);
                    throw null;
                }
                this.downstream.accept(d);
            }

            @Override // j$.util.stream.WhileOps.DropWhileSink
            public long getDropCount() {
                return this.dropCount;
            }
        }

        @Override // j$.util.stream.WhileOps.DropWhileOp
        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new C1OpSink(sink, z);
        }
    }

    static abstract class UnorderedWhileSpliterator implements Spliterator {
        final AtomicBoolean cancel;
        int count;
        final boolean noSplitting;
        final Spliterator s;
        boolean takeOrDrop;

        @Override // j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.CC.$default$forEachRemaining(this, consumer);
        }

        @Override // j$.util.Spliterator
        public long getExactSizeIfKnown() {
            return -1L;
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        abstract Spliterator makeSpliterator(Spliterator spliterator);

        UnorderedWhileSpliterator(Spliterator spliterator, boolean z) {
            this.takeOrDrop = true;
            this.s = spliterator;
            this.noSplitting = z;
            this.cancel = new AtomicBoolean();
        }

        UnorderedWhileSpliterator(Spliterator spliterator, UnorderedWhileSpliterator unorderedWhileSpliterator) {
            this.takeOrDrop = true;
            this.s = spliterator;
            this.noSplitting = unorderedWhileSpliterator.noSplitting;
            this.cancel = unorderedWhileSpliterator.cancel;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.s.estimateSize();
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.s.characteristics() & (-16449);
        }

        @Override // j$.util.Spliterator
        public Comparator getComparator() {
            return this.s.getComparator();
        }

        @Override // j$.util.Spliterator
        public Spliterator trySplit() {
            Spliterator spliteratorTrySplit = this.noSplitting ? null : this.s.trySplit();
            if (spliteratorTrySplit != null) {
                return makeSpliterator(spliteratorTrySplit);
            }
            return null;
        }

        boolean checkCancelOnCount() {
            return (this.count == 0 && this.cancel.get()) ? false : true;
        }

        static abstract class OfRef extends UnorderedWhileSpliterator implements Consumer {
            final Predicate p;
            Object t;

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            OfRef(Spliterator spliterator, boolean z, Predicate predicate) {
                super(spliterator, z);
                this.p = predicate;
            }

            OfRef(Spliterator spliterator, OfRef ofRef) {
                super(spliterator, ofRef);
                this.p = ofRef.p;
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                this.count = (this.count + 1) & 63;
                this.t = obj;
            }

            static final class Taking extends OfRef {
                Taking(Spliterator spliterator, boolean z, Predicate predicate) {
                    super(spliterator, z, predicate);
                }

                Taking(Spliterator spliterator, Taking taking) {
                    super(spliterator, taking);
                }

                @Override // j$.util.Spliterator
                public boolean tryAdvance(Consumer consumer) {
                    boolean zTest;
                    if (this.takeOrDrop && checkCancelOnCount() && this.s.tryAdvance(this)) {
                        zTest = this.p.test(this.t);
                        if (zTest) {
                            consumer.accept(this.t);
                            return true;
                        }
                    } else {
                        zTest = true;
                    }
                    this.takeOrDrop = false;
                    if (!zTest) {
                        this.cancel.set(true);
                    }
                    return false;
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public Spliterator trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return super.trySplit();
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                Spliterator makeSpliterator(Spliterator spliterator) {
                    return new Taking(spliterator, this);
                }
            }

            static final class Dropping extends OfRef {
                Dropping(Spliterator spliterator, boolean z, Predicate predicate) {
                    super(spliterator, z, predicate);
                }

                Dropping(Spliterator spliterator, Dropping dropping) {
                    super(spliterator, dropping);
                }

                @Override // j$.util.Spliterator
                public boolean tryAdvance(Consumer consumer) {
                    boolean zTryAdvance;
                    if (this.takeOrDrop) {
                        boolean z = false;
                        this.takeOrDrop = false;
                        while (true) {
                            zTryAdvance = this.s.tryAdvance(this);
                            if (!zTryAdvance || !checkCancelOnCount() || !this.p.test(this.t)) {
                                break;
                            }
                            z = true;
                        }
                        if (zTryAdvance) {
                            if (z) {
                                this.cancel.set(true);
                            }
                            consumer.accept(this.t);
                        }
                        return zTryAdvance;
                    }
                    return this.s.tryAdvance(consumer);
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                Spliterator makeSpliterator(Spliterator spliterator) {
                    return new Dropping(spliterator, this);
                }
            }
        }

        static abstract class OfInt extends UnorderedWhileSpliterator implements IntConsumer, Spliterator.OfInt {
            int t;

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer$CC.$default$andThen(this, intConsumer);
            }

            @Override // j$.util.Spliterator.OfPrimitive
            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                forEachRemaining((IntConsumer) obj);
            }

            @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
            }

            @Override // j$.util.Spliterator.OfInt
            public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                Spliterator.OfInt.CC.$default$forEachRemaining((Spliterator.OfInt) this, intConsumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfInt.CC.$default$tryAdvance(this, consumer);
            }

            OfInt(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                super(ofInt, z);
            }

            OfInt(Spliterator.OfInt ofInt, OfInt ofInt2) {
                super(ofInt, ofInt2);
                ofInt2.getClass();
            }

            @Override // java.util.function.IntConsumer
            public void accept(int i) {
                this.count = (this.count + 1) & 63;
                this.t = i;
            }

            static final class Taking extends OfInt {
                Taking(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                    super(ofInt, z, intPredicate);
                }

                Taking(Spliterator.OfInt ofInt, OfInt ofInt2) {
                    super(ofInt, ofInt2);
                }

                @Override // j$.util.Spliterator.OfPrimitive
                public boolean tryAdvance(IntConsumer intConsumer) {
                    if (this.takeOrDrop && checkCancelOnCount() && ((Spliterator.OfInt) this.s).tryAdvance((IntConsumer) this)) {
                        IntPredicate intPredicate = null;
                        intPredicate.test(this.t);
                        throw null;
                    }
                    this.takeOrDrop = false;
                    return false;
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public Spliterator.OfInt trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return (Spliterator.OfInt) super.trySplit();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
                    return new Taking(ofInt, this);
                }
            }

            static final class Dropping extends OfInt {
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                    return (Spliterator.OfInt) super.trySplit();
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                    return (Spliterator.OfPrimitive) super.trySplit();
                }

                Dropping(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                    super(ofInt, z, intPredicate);
                }

                Dropping(Spliterator.OfInt ofInt, OfInt ofInt2) {
                    super(ofInt, ofInt2);
                }

                @Override // j$.util.Spliterator.OfPrimitive
                public boolean tryAdvance(IntConsumer intConsumer) {
                    if (this.takeOrDrop) {
                        this.takeOrDrop = false;
                        boolean zTryAdvance = ((Spliterator.OfInt) this.s).tryAdvance((IntConsumer) this);
                        if (zTryAdvance && checkCancelOnCount()) {
                            IntPredicate intPredicate = null;
                            intPredicate.test(this.t);
                            throw null;
                        }
                        if (zTryAdvance) {
                            intConsumer.accept(this.t);
                        }
                        return zTryAdvance;
                    }
                    return ((Spliterator.OfInt) this.s).tryAdvance(intConsumer);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
                    return new Dropping(ofInt, this);
                }
            }
        }

        static abstract class OfLong extends UnorderedWhileSpliterator implements LongConsumer, Spliterator.OfLong {
            long t;

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer$CC.$default$andThen(this, longConsumer);
            }

            @Override // j$.util.Spliterator.OfPrimitive
            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                forEachRemaining((LongConsumer) obj);
            }

            @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
            }

            @Override // j$.util.Spliterator.OfLong
            public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                Spliterator.OfLong.CC.$default$forEachRemaining((Spliterator.OfLong) this, longConsumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfLong.CC.$default$tryAdvance(this, consumer);
            }

            OfLong(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                super(ofLong, z);
            }

            OfLong(Spliterator.OfLong ofLong, OfLong ofLong2) {
                super(ofLong, ofLong2);
                ofLong2.getClass();
            }

            @Override // java.util.function.LongConsumer
            public void accept(long j) {
                this.count = (this.count + 1) & 63;
                this.t = j;
            }

            static final class Taking extends OfLong {
                Taking(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                    super(ofLong, z, longPredicate);
                }

                Taking(Spliterator.OfLong ofLong, OfLong ofLong2) {
                    super(ofLong, ofLong2);
                }

                @Override // j$.util.Spliterator.OfPrimitive
                public boolean tryAdvance(LongConsumer longConsumer) {
                    if (this.takeOrDrop && checkCancelOnCount() && ((Spliterator.OfLong) this.s).tryAdvance((LongConsumer) this)) {
                        LongPredicate longPredicate = null;
                        longPredicate.test(this.t);
                        throw null;
                    }
                    this.takeOrDrop = false;
                    return false;
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public Spliterator.OfLong trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return (Spliterator.OfLong) super.trySplit();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
                    return new Taking(ofLong, this);
                }
            }

            static final class Dropping extends OfLong {
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                    return (Spliterator.OfLong) super.trySplit();
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                    return (Spliterator.OfPrimitive) super.trySplit();
                }

                Dropping(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                    super(ofLong, z, longPredicate);
                }

                Dropping(Spliterator.OfLong ofLong, OfLong ofLong2) {
                    super(ofLong, ofLong2);
                }

                @Override // j$.util.Spliterator.OfPrimitive
                public boolean tryAdvance(LongConsumer longConsumer) {
                    if (this.takeOrDrop) {
                        this.takeOrDrop = false;
                        boolean zTryAdvance = ((Spliterator.OfLong) this.s).tryAdvance((LongConsumer) this);
                        if (zTryAdvance && checkCancelOnCount()) {
                            LongPredicate longPredicate = null;
                            longPredicate.test(this.t);
                            throw null;
                        }
                        if (zTryAdvance) {
                            longConsumer.accept(this.t);
                        }
                        return zTryAdvance;
                    }
                    return ((Spliterator.OfLong) this.s).tryAdvance(longConsumer);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
                    return new Dropping(ofLong, this);
                }
            }
        }

        static abstract class OfDouble extends UnorderedWhileSpliterator implements DoubleConsumer, Spliterator.OfDouble {
            double t;

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
            }

            @Override // j$.util.Spliterator.OfPrimitive
            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                forEachRemaining((DoubleConsumer) obj);
            }

            @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
            }

            @Override // j$.util.Spliterator.OfDouble
            public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                Spliterator.OfDouble.CC.$default$forEachRemaining((Spliterator.OfDouble) this, doubleConsumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfDouble.CC.$default$tryAdvance(this, consumer);
            }

            OfDouble(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                super(ofDouble, z);
            }

            OfDouble(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
                ofDouble2.getClass();
            }

            @Override // java.util.function.DoubleConsumer
            public void accept(double d) {
                this.count = (this.count + 1) & 63;
                this.t = d;
            }

            static final class Taking extends OfDouble {
                Taking(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                    super(ofDouble, z, doublePredicate);
                }

                Taking(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                    super(ofDouble, ofDouble2);
                }

                @Override // j$.util.Spliterator.OfPrimitive
                public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                    if (this.takeOrDrop && checkCancelOnCount() && ((Spliterator.OfDouble) this.s).tryAdvance((DoubleConsumer) this)) {
                        DoublePredicate doublePredicate = null;
                        doublePredicate.test(this.t);
                        throw null;
                    }
                    this.takeOrDrop = false;
                    return false;
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public Spliterator.OfDouble trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return (Spliterator.OfDouble) super.trySplit();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
                    return new Taking(ofDouble, this);
                }
            }

            static final class Dropping extends OfDouble {
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                    return (Spliterator.OfDouble) super.trySplit();
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                    return (Spliterator.OfPrimitive) super.trySplit();
                }

                Dropping(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                    super(ofDouble, z, doublePredicate);
                }

                Dropping(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                    super(ofDouble, ofDouble2);
                }

                @Override // j$.util.Spliterator.OfPrimitive
                public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                    if (this.takeOrDrop) {
                        this.takeOrDrop = false;
                        boolean zTryAdvance = ((Spliterator.OfDouble) this.s).tryAdvance((DoubleConsumer) this);
                        if (zTryAdvance && checkCancelOnCount()) {
                            DoublePredicate doublePredicate = null;
                            doublePredicate.test(this.t);
                            throw null;
                        }
                        if (zTryAdvance) {
                            doubleConsumer.accept(this.t);
                        }
                        return zTryAdvance;
                    }
                    return ((Spliterator.OfDouble) this.s).tryAdvance(doubleConsumer);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
                    return new Dropping(ofDouble, this);
                }
            }
        }
    }

    private static final class TakeWhileTask extends AbstractShortCircuitTask {
        private volatile boolean completed;
        private final IntFunction generator;
        private final boolean isOrdered;
        private final AbstractPipeline op;
        private boolean shortCircuited;
        private long thisNodeSize;

        TakeWhileTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.isOrdered = StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags());
        }

        TakeWhileTask(TakeWhileTask takeWhileTask, Spliterator spliterator) {
            super(takeWhileTask, spliterator);
            this.op = takeWhileTask.op;
            this.generator = takeWhileTask.generator;
            this.isOrdered = takeWhileTask.isOrdered;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public TakeWhileTask makeChild(Spliterator spliterator) {
            return new TakeWhileTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractShortCircuitTask
        public final Node getEmptyResult() {
            return Nodes.emptyNode(this.op.getOutputShape());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public final Node doLeaf() {
            Node.Builder builderMakeNodeBuilder = this.helper.makeNodeBuilder(-1L, this.generator);
            Sink sinkOpWrapSink = this.op.opWrapSink(this.helper.getStreamAndOpFlags(), builderMakeNodeBuilder);
            PipelineHelper pipelineHelper = this.helper;
            boolean zCopyIntoWithCancel = pipelineHelper.copyIntoWithCancel(pipelineHelper.wrapSink(sinkOpWrapSink), this.spliterator);
            this.shortCircuited = zCopyIntoWithCancel;
            if (zCopyIntoWithCancel) {
                cancelLaterNodes();
            }
            Node nodeBuild = builderMakeNodeBuilder.build();
            this.thisNodeSize = nodeBuild.count();
            return nodeBuild;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0043  */
        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onCompletion(java.util.concurrent.CountedCompleter r5) {
            /*
                r4 = this;
                boolean r0 = r4.isLeaf()
                if (r0 != 0) goto L59
                j$.util.stream.AbstractTask r0 = r4.leftChild
                j$.util.stream.WhileOps$TakeWhileTask r0 = (j$.util.stream.WhileOps.TakeWhileTask) r0
                boolean r0 = r0.shortCircuited
                j$.util.stream.AbstractTask r1 = r4.rightChild
                j$.util.stream.WhileOps$TakeWhileTask r1 = (j$.util.stream.WhileOps.TakeWhileTask) r1
                boolean r1 = r1.shortCircuited
                r0 = r0 | r1
                r4.shortCircuited = r0
                boolean r0 = r4.isOrdered
                if (r0 == 0) goto L26
                boolean r0 = r4.canceled
                if (r0 == 0) goto L26
                r0 = 0
                r4.thisNodeSize = r0
                j$.util.stream.Node r0 = r4.getEmptyResult()
                goto L56
            L26:
                boolean r0 = r4.isOrdered
                if (r0 == 0) goto L43
                j$.util.stream.AbstractTask r0 = r4.leftChild
                r1 = r0
                j$.util.stream.WhileOps$TakeWhileTask r1 = (j$.util.stream.WhileOps.TakeWhileTask) r1
                boolean r1 = r1.shortCircuited
                if (r1 == 0) goto L43
                r1 = r0
                j$.util.stream.WhileOps$TakeWhileTask r1 = (j$.util.stream.WhileOps.TakeWhileTask) r1
                long r1 = r1.thisNodeSize
                r4.thisNodeSize = r1
                j$.util.stream.WhileOps$TakeWhileTask r0 = (j$.util.stream.WhileOps.TakeWhileTask) r0
                java.lang.Object r0 = r0.getLocalResult()
                j$.util.stream.Node r0 = (j$.util.stream.Node) r0
                goto L56
            L43:
                j$.util.stream.AbstractTask r0 = r4.leftChild
                j$.util.stream.WhileOps$TakeWhileTask r0 = (j$.util.stream.WhileOps.TakeWhileTask) r0
                long r0 = r0.thisNodeSize
                j$.util.stream.AbstractTask r2 = r4.rightChild
                j$.util.stream.WhileOps$TakeWhileTask r2 = (j$.util.stream.WhileOps.TakeWhileTask) r2
                long r2 = r2.thisNodeSize
                long r0 = r0 + r2
                r4.thisNodeSize = r0
                j$.util.stream.Node r0 = r4.merge()
            L56:
                r4.setLocalResult(r0)
            L59:
                r0 = 1
                r4.completed = r0
                super.onCompletion(r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.WhileOps.TakeWhileTask.onCompletion(java.util.concurrent.CountedCompleter):void");
        }

        Node merge() {
            AbstractTask abstractTask = this.leftChild;
            if (((TakeWhileTask) abstractTask).thisNodeSize == 0) {
                return (Node) ((TakeWhileTask) this.rightChild).getLocalResult();
            }
            if (((TakeWhileTask) this.rightChild).thisNodeSize == 0) {
                return (Node) ((TakeWhileTask) abstractTask).getLocalResult();
            }
            return Nodes.conc(this.op.getOutputShape(), (Node) ((TakeWhileTask) this.leftChild).getLocalResult(), (Node) ((TakeWhileTask) this.rightChild).getLocalResult());
        }

        @Override // j$.util.stream.AbstractShortCircuitTask
        protected void cancel() {
            super.cancel();
            if (this.isOrdered && this.completed) {
                setLocalResult(getEmptyResult());
            }
        }
    }

    private static final class DropWhileTask extends AbstractTask {
        private final IntFunction generator;
        private long index;
        private final boolean isOrdered;
        private final AbstractPipeline op;
        private long thisNodeSize;

        DropWhileTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.isOrdered = StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags());
        }

        DropWhileTask(DropWhileTask dropWhileTask, Spliterator spliterator) {
            super(dropWhileTask, spliterator);
            this.op = dropWhileTask.op;
            this.generator = dropWhileTask.generator;
            this.isOrdered = dropWhileTask.isOrdered;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public DropWhileTask makeChild(Spliterator spliterator) {
            return new DropWhileTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public final Node doLeaf() {
            boolean zIsRoot = isRoot();
            Node.Builder builderMakeNodeBuilder = this.helper.makeNodeBuilder((!zIsRoot && this.isOrdered && StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags)) ? this.op.exactOutputSizeIfKnown(this.spliterator) : -1L, this.generator);
            DropWhileSink dropWhileSinkOpWrapSink = ((DropWhileOp) this.op).opWrapSink(builderMakeNodeBuilder, this.isOrdered && !zIsRoot);
            this.helper.wrapAndCopyInto(dropWhileSinkOpWrapSink, this.spliterator);
            Node nodeBuild = builderMakeNodeBuilder.build();
            this.thisNodeSize = nodeBuild.count();
            this.index = dropWhileSinkOpWrapSink.getDropCount();
            return nodeBuild;
        }

        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                if (this.isOrdered) {
                    AbstractTask abstractTask = this.leftChild;
                    long j = ((DropWhileTask) abstractTask).index;
                    this.index = j;
                    if (j == ((DropWhileTask) abstractTask).thisNodeSize) {
                        this.index = j + ((DropWhileTask) this.rightChild).index;
                    }
                }
                this.thisNodeSize = ((DropWhileTask) this.leftChild).thisNodeSize + ((DropWhileTask) this.rightChild).thisNodeSize;
                Node nodeMerge = merge();
                if (isRoot()) {
                    nodeMerge = doTruncate(nodeMerge);
                }
                setLocalResult(nodeMerge);
            }
            super.onCompletion(countedCompleter);
        }

        private Node merge() {
            AbstractTask abstractTask = this.leftChild;
            if (((DropWhileTask) abstractTask).thisNodeSize == 0) {
                return (Node) ((DropWhileTask) this.rightChild).getLocalResult();
            }
            if (((DropWhileTask) this.rightChild).thisNodeSize == 0) {
                return (Node) ((DropWhileTask) abstractTask).getLocalResult();
            }
            return Nodes.conc(this.op.getOutputShape(), (Node) ((DropWhileTask) this.leftChild).getLocalResult(), (Node) ((DropWhileTask) this.rightChild).getLocalResult());
        }

        private Node doTruncate(Node node) {
            return this.isOrdered ? node.truncate(this.index, node.count(), this.generator) : node;
        }
    }
}

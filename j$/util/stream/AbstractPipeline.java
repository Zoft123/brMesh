package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.stream.Node;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractPipeline extends PipelineHelper implements BaseStream {
    private int combinedFlags;
    private int depth;
    private boolean linkedOrConsumed;
    private AbstractPipeline nextStage;
    private boolean parallel;
    private final AbstractPipeline previousStage;
    private boolean sourceAnyStateful;
    private Runnable sourceCloseAction;
    protected final int sourceOrOpFlags;
    private Spliterator sourceSpliterator;
    private final AbstractPipeline sourceStage;
    private Supplier sourceSupplier;

    static /* synthetic */ Spliterator lambda$wrapSpliterator$1(Spliterator spliterator) {
        return spliterator;
    }

    abstract Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction);

    abstract boolean forEachWithCancel(Spliterator spliterator, Sink sink);

    abstract StreamShape getOutputShape();

    abstract Spliterator lazySpliterator(Supplier supplier);

    @Override // j$.util.stream.PipelineHelper
    abstract Node.Builder makeNodeBuilder(long j, IntFunction intFunction);

    abstract boolean opIsStateful();

    abstract Sink opWrapSink(int i, Sink sink);

    abstract Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z);

    AbstractPipeline(Supplier supplier, int i, boolean z) {
        this.previousStage = null;
        this.sourceSupplier = supplier;
        this.sourceStage = this;
        int i2 = StreamOpFlag.STREAM_MASK & i;
        this.sourceOrOpFlags = i2;
        this.combinedFlags = (~(i2 << 1)) & StreamOpFlag.INITIAL_OPS_VALUE;
        this.depth = 0;
        this.parallel = z;
    }

    AbstractPipeline(Spliterator spliterator, int i, boolean z) {
        this.previousStage = null;
        this.sourceSpliterator = spliterator;
        this.sourceStage = this;
        int i2 = StreamOpFlag.STREAM_MASK & i;
        this.sourceOrOpFlags = i2;
        this.combinedFlags = (~(i2 << 1)) & StreamOpFlag.INITIAL_OPS_VALUE;
        this.depth = 0;
        this.parallel = z;
    }

    AbstractPipeline(AbstractPipeline abstractPipeline, int i) {
        if (abstractPipeline.linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        abstractPipeline.linkedOrConsumed = true;
        abstractPipeline.nextStage = this;
        this.previousStage = abstractPipeline;
        this.sourceOrOpFlags = StreamOpFlag.OP_MASK & i;
        this.combinedFlags = StreamOpFlag.combineOpFlags(i, abstractPipeline.combinedFlags);
        AbstractPipeline abstractPipeline2 = abstractPipeline.sourceStage;
        this.sourceStage = abstractPipeline2;
        if (opIsStateful()) {
            abstractPipeline2.sourceAnyStateful = true;
        }
        this.depth = abstractPipeline.depth + 1;
    }

    final Object evaluate(TerminalOp terminalOp) {
        if (this.linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.linkedOrConsumed = true;
        if (isParallel()) {
            return terminalOp.evaluateParallel(this, sourceSpliterator(terminalOp.getOpFlags()));
        }
        return terminalOp.evaluateSequential(this, sourceSpliterator(terminalOp.getOpFlags()));
    }

    final Node evaluateToArrayNode(IntFunction intFunction) {
        if (this.linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.linkedOrConsumed = true;
        if (isParallel() && this.previousStage != null && opIsStateful()) {
            this.depth = 0;
            AbstractPipeline abstractPipeline = this.previousStage;
            return opEvaluateParallel(abstractPipeline, abstractPipeline.sourceSpliterator(0), intFunction);
        }
        return evaluate(sourceSpliterator(0), true, intFunction);
    }

    final Spliterator sourceStageSpliterator() {
        AbstractPipeline abstractPipeline = this.sourceStage;
        if (this != abstractPipeline) {
            throw new IllegalStateException();
        }
        if (this.linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.linkedOrConsumed = true;
        Spliterator spliterator = abstractPipeline.sourceSpliterator;
        if (spliterator != null) {
            abstractPipeline.sourceSpliterator = null;
            return spliterator;
        }
        Supplier supplier = abstractPipeline.sourceSupplier;
        if (supplier != null) {
            Spliterator spliterator2 = (Spliterator) supplier.get();
            this.sourceStage.sourceSupplier = null;
            return spliterator2;
        }
        throw new IllegalStateException("source already consumed or closed");
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream sequential() {
        this.sourceStage.parallel = false;
        return this;
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream parallel() {
        this.sourceStage.parallel = true;
        return this;
    }

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public void close() {
        this.linkedOrConsumed = true;
        this.sourceSupplier = null;
        this.sourceSpliterator = null;
        AbstractPipeline abstractPipeline = this.sourceStage;
        Runnable runnable = abstractPipeline.sourceCloseAction;
        if (runnable != null) {
            abstractPipeline.sourceCloseAction = null;
            runnable.run();
        }
    }

    @Override // j$.util.stream.BaseStream
    public BaseStream onClose(Runnable runnable) {
        if (this.linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        Objects.requireNonNull(runnable);
        AbstractPipeline abstractPipeline = this.sourceStage;
        Runnable runnable2 = abstractPipeline.sourceCloseAction;
        if (runnable2 != null) {
            runnable = Streams.composeWithExceptions(runnable2, runnable);
        }
        abstractPipeline.sourceCloseAction = runnable;
        return this;
    }

    @Override // j$.util.stream.BaseStream
    public Spliterator spliterator() {
        if (this.linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.linkedOrConsumed = true;
        AbstractPipeline abstractPipeline = this.sourceStage;
        if (this == abstractPipeline) {
            Spliterator spliterator = abstractPipeline.sourceSpliterator;
            if (spliterator != null) {
                abstractPipeline.sourceSpliterator = null;
                return spliterator;
            }
            Supplier supplier = abstractPipeline.sourceSupplier;
            if (supplier != null) {
                abstractPipeline.sourceSupplier = null;
                return lazySpliterator(supplier);
            }
            throw new IllegalStateException("source already consumed or closed");
        }
        return wrap(this, new Supplier() { // from class: j$.util.stream.AbstractPipeline$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m1330lambda$spliterator$0$javautilstreamAbstractPipeline();
            }
        }, isParallel());
    }

    /* JADX INFO: renamed from: lambda$spliterator$0$java-util-stream-AbstractPipeline, reason: not valid java name */
    /* synthetic */ Spliterator m1330lambda$spliterator$0$javautilstreamAbstractPipeline() {
        return sourceSpliterator(0);
    }

    @Override // j$.util.stream.BaseStream
    public final boolean isParallel() {
        return this.sourceStage.parallel;
    }

    private Spliterator sourceSpliterator(int i) {
        int i2;
        int i3;
        AbstractPipeline abstractPipeline = this.sourceStage;
        Spliterator spliteratorOpEvaluateParallelLazy = abstractPipeline.sourceSpliterator;
        if (spliteratorOpEvaluateParallelLazy != null) {
            abstractPipeline.sourceSpliterator = null;
        } else {
            Supplier supplier = abstractPipeline.sourceSupplier;
            if (supplier != null) {
                spliteratorOpEvaluateParallelLazy = (Spliterator) supplier.get();
                this.sourceStage.sourceSupplier = null;
            } else {
                throw new IllegalStateException("source already consumed or closed");
            }
        }
        if (isParallel()) {
            AbstractPipeline abstractPipeline2 = this.sourceStage;
            if (abstractPipeline2.sourceAnyStateful) {
                AbstractPipeline abstractPipeline3 = abstractPipeline2.nextStage;
                int i4 = 1;
                while (abstractPipeline2 != this) {
                    int i5 = abstractPipeline3.sourceOrOpFlags;
                    if (abstractPipeline3.opIsStateful()) {
                        if (StreamOpFlag.SHORT_CIRCUIT.isKnown(i5)) {
                            i5 &= ~StreamOpFlag.IS_SHORT_CIRCUIT;
                        }
                        spliteratorOpEvaluateParallelLazy = abstractPipeline3.opEvaluateParallelLazy(abstractPipeline2, spliteratorOpEvaluateParallelLazy);
                        if (spliteratorOpEvaluateParallelLazy.hasCharacteristics(64)) {
                            i2 = (~StreamOpFlag.NOT_SIZED) & i5;
                            i3 = StreamOpFlag.IS_SIZED;
                        } else {
                            i2 = (~StreamOpFlag.IS_SIZED) & i5;
                            i3 = StreamOpFlag.NOT_SIZED;
                        }
                        i5 = i2 | i3;
                        i4 = 0;
                    }
                    int i6 = i4 + 1;
                    abstractPipeline3.depth = i4;
                    abstractPipeline3.combinedFlags = StreamOpFlag.combineOpFlags(i5, abstractPipeline2.combinedFlags);
                    AbstractPipeline abstractPipeline4 = abstractPipeline3;
                    abstractPipeline3 = abstractPipeline3.nextStage;
                    abstractPipeline2 = abstractPipeline4;
                    i4 = i6;
                }
            }
        }
        if (i != 0) {
            this.combinedFlags = StreamOpFlag.combineOpFlags(i, this.combinedFlags);
        }
        return spliteratorOpEvaluateParallelLazy;
    }

    @Override // j$.util.stream.PipelineHelper
    final StreamShape getSourceShape() {
        AbstractPipeline abstractPipeline = this;
        while (abstractPipeline.depth > 0) {
            abstractPipeline = abstractPipeline.previousStage;
        }
        return abstractPipeline.getOutputShape();
    }

    @Override // j$.util.stream.PipelineHelper
    final long exactOutputSizeIfKnown(Spliterator spliterator) {
        if (StreamOpFlag.SIZED.isKnown(getStreamAndOpFlags())) {
            return spliterator.getExactSizeIfKnown();
        }
        return -1L;
    }

    @Override // j$.util.stream.PipelineHelper
    final Sink wrapAndCopyInto(Sink sink, Spliterator spliterator) {
        copyInto(wrapSink((Sink) Objects.requireNonNull(sink)), spliterator);
        return sink;
    }

    @Override // j$.util.stream.PipelineHelper
    final void copyInto(Sink sink, Spliterator spliterator) {
        Objects.requireNonNull(sink);
        if (!StreamOpFlag.SHORT_CIRCUIT.isKnown(getStreamAndOpFlags())) {
            sink.begin(spliterator.getExactSizeIfKnown());
            spliterator.forEachRemaining(sink);
            sink.end();
            return;
        }
        copyIntoWithCancel(sink, spliterator);
    }

    @Override // j$.util.stream.PipelineHelper
    final boolean copyIntoWithCancel(Sink sink, Spliterator spliterator) {
        AbstractPipeline abstractPipeline = this;
        while (abstractPipeline.depth > 0) {
            abstractPipeline = abstractPipeline.previousStage;
        }
        sink.begin(spliterator.getExactSizeIfKnown());
        boolean zForEachWithCancel = abstractPipeline.forEachWithCancel(spliterator, sink);
        sink.end();
        return zForEachWithCancel;
    }

    @Override // j$.util.stream.PipelineHelper
    final int getStreamAndOpFlags() {
        return this.combinedFlags;
    }

    final boolean isOrdered() {
        return StreamOpFlag.ORDERED.isKnown(this.combinedFlags);
    }

    @Override // j$.util.stream.PipelineHelper
    final Sink wrapSink(Sink sink) {
        Objects.requireNonNull(sink);
        for (AbstractPipeline abstractPipeline = this; abstractPipeline.depth > 0; abstractPipeline = abstractPipeline.previousStage) {
            sink = abstractPipeline.opWrapSink(abstractPipeline.previousStage.combinedFlags, sink);
        }
        return sink;
    }

    @Override // j$.util.stream.PipelineHelper
    final Spliterator wrapSpliterator(final Spliterator spliterator) {
        return this.depth == 0 ? spliterator : wrap(this, new Supplier() { // from class: j$.util.stream.AbstractPipeline$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return AbstractPipeline.lambda$wrapSpliterator$1(spliterator);
            }
        }, isParallel());
    }

    @Override // j$.util.stream.PipelineHelper
    final Node evaluate(Spliterator spliterator, boolean z, IntFunction intFunction) {
        if (isParallel()) {
            return evaluateToNode(this, spliterator, z, intFunction);
        }
        return ((Node.Builder) wrapAndCopyInto(makeNodeBuilder(exactOutputSizeIfKnown(spliterator), intFunction), spliterator)).build();
    }

    Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    static /* synthetic */ Object[] lambda$opEvaluateParallelLazy$2(int i) {
        return new Object[i];
    }

    Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
        return opEvaluateParallel(pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.AbstractPipeline$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AbstractPipeline.lambda$opEvaluateParallelLazy$2(i);
            }
        }).spliterator();
    }
}

package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.stream.Sink;
import j$.util.stream.SpinedBuffer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
final class StreamSpliterators$DoubleWrappingSpliterator extends StreamSpliterators$AbstractWrappingSpliterator implements Spliterator.OfDouble {
    @Override // j$.util.Spliterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        Spliterator.OfDouble.CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
    }

    @Override // j$.util.Spliterator
    public /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return Spliterator.OfDouble.CC.$default$tryAdvance(this, consumer);
    }

    StreamSpliterators$DoubleWrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        super(pipelineHelper, supplier, z);
    }

    StreamSpliterators$DoubleWrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        super(pipelineHelper, spliterator, z);
    }

    @Override // j$.util.stream.StreamSpliterators$AbstractWrappingSpliterator
    StreamSpliterators$AbstractWrappingSpliterator wrap(Spliterator spliterator) {
        return new StreamSpliterators$DoubleWrappingSpliterator(this.ph, spliterator, this.isParallel);
    }

    @Override // j$.util.stream.StreamSpliterators$AbstractWrappingSpliterator
    void initPartialTraversalState() {
        final SpinedBuffer.OfDouble ofDouble = new SpinedBuffer.OfDouble();
        this.buffer = ofDouble;
        PipelineHelper pipelineHelper = this.ph;
        Objects.requireNonNull(ofDouble);
        this.bufferSink = pipelineHelper.wrapSink(new Sink.OfDouble() { // from class: j$.util.stream.StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda1
            @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
            public final void accept(double d) {
                ofDouble.accept(d);
            }

            @Override // j$.util.stream.Sink
            public /* synthetic */ void accept(int i) {
                Sink.CC.$default$accept((Sink) this, i);
            }

            @Override // j$.util.stream.Sink
            public /* synthetic */ void accept(long j) {
                Sink.CC.$default$accept((Sink) this, j);
            }

            @Override // j$.util.stream.Sink.OfDouble
            public /* synthetic */ void accept(Double d) {
                Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfDouble.CC.$default$accept(this, obj);
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
            }

            @Override // j$.util.stream.Sink
            public /* synthetic */ void begin(long j) {
                Sink.CC.$default$begin(this, j);
            }

            @Override // j$.util.stream.Sink
            public /* synthetic */ boolean cancellationRequested() {
                return Sink.CC.$default$cancellationRequested(this);
            }

            @Override // j$.util.stream.Sink
            public /* synthetic */ void end() {
                Sink.CC.$default$end(this);
            }
        });
        this.pusher = new BooleanSupplier() { // from class: j$.util.stream.StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.m1351xbf8f913e();
            }
        };
    }

    /* JADX INFO: renamed from: lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$DoubleWrappingSpliterator, reason: not valid java name */
    /* synthetic */ boolean m1351xbf8f913e() {
        return this.spliterator.tryAdvance(this.bufferSink);
    }

    @Override // j$.util.stream.StreamSpliterators$AbstractWrappingSpliterator, j$.util.Spliterator
    public Spliterator.OfDouble trySplit() {
        return (Spliterator.OfDouble) super.trySplit();
    }

    @Override // j$.util.Spliterator.OfPrimitive
    public boolean tryAdvance(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        boolean zDoAdvance = doAdvance();
        if (zDoAdvance) {
            doubleConsumer.accept(((SpinedBuffer.OfDouble) this.buffer).get(this.nextToConsume));
        }
        return zDoAdvance;
    }

    @Override // j$.util.Spliterator.OfPrimitive
    public void forEachRemaining(final DoubleConsumer doubleConsumer) {
        if (this.buffer == null && !this.finished) {
            Objects.requireNonNull(doubleConsumer);
            init();
            PipelineHelper pipelineHelper = this.ph;
            Objects.requireNonNull(doubleConsumer);
            pipelineHelper.wrapAndCopyInto(new Sink.OfDouble() { // from class: j$.util.stream.StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda0
                @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                public final void accept(double d) {
                    doubleConsumer.accept(d);
                }

                @Override // j$.util.stream.Sink
                public /* synthetic */ void accept(int i) {
                    Sink.CC.$default$accept((Sink) this, i);
                }

                @Override // j$.util.stream.Sink
                public /* synthetic */ void accept(long j) {
                    Sink.CC.$default$accept((Sink) this, j);
                }

                @Override // j$.util.stream.Sink.OfDouble
                public /* synthetic */ void accept(Double d) {
                    Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
                }

                @Override // java.util.function.Consumer
                public /* bridge */ /* synthetic */ void accept(Object obj) {
                    Sink.OfDouble.CC.$default$accept(this, obj);
                }

                public /* synthetic */ Consumer andThen(Consumer consumer) {
                    return Consumer$CC.$default$andThen(this, consumer);
                }

                public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer2) {
                    return DoubleConsumer$CC.$default$andThen(this, doubleConsumer2);
                }

                @Override // j$.util.stream.Sink
                public /* synthetic */ void begin(long j) {
                    Sink.CC.$default$begin(this, j);
                }

                @Override // j$.util.stream.Sink
                public /* synthetic */ boolean cancellationRequested() {
                    return Sink.CC.$default$cancellationRequested(this);
                }

                @Override // j$.util.stream.Sink
                public /* synthetic */ void end() {
                    Sink.CC.$default$end(this);
                }
            }, this.spliterator);
            this.finished = true;
            return;
        }
        while (tryAdvance(doubleConsumer)) {
        }
    }
}

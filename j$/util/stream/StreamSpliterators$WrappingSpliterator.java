package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.stream.Sink;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
final class StreamSpliterators$WrappingSpliterator extends StreamSpliterators$AbstractWrappingSpliterator {
    StreamSpliterators$WrappingSpliterator(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        super(pipelineHelper, supplier, z);
    }

    StreamSpliterators$WrappingSpliterator(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        super(pipelineHelper, spliterator, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.util.stream.StreamSpliterators$AbstractWrappingSpliterator
    public StreamSpliterators$WrappingSpliterator wrap(Spliterator spliterator) {
        return new StreamSpliterators$WrappingSpliterator(this.ph, spliterator, this.isParallel);
    }

    @Override // j$.util.stream.StreamSpliterators$AbstractWrappingSpliterator
    void initPartialTraversalState() {
        final SpinedBuffer spinedBuffer = new SpinedBuffer();
        this.buffer = spinedBuffer;
        PipelineHelper pipelineHelper = this.ph;
        Objects.requireNonNull(spinedBuffer);
        this.bufferSink = pipelineHelper.wrapSink(new Sink() { // from class: j$.util.stream.StreamSpliterators$WrappingSpliterator$$ExternalSyntheticLambda0
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

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                spinedBuffer.accept(obj);
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
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
        this.pusher = new BooleanSupplier() { // from class: j$.util.stream.StreamSpliterators$WrappingSpliterator$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.m1354xf58cc34f();
            }
        };
    }

    /* JADX INFO: renamed from: lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$WrappingSpliterator, reason: not valid java name */
    /* synthetic */ boolean m1354xf58cc34f() {
        return this.spliterator.tryAdvance(this.bufferSink);
    }

    @Override // j$.util.Spliterator
    public boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        boolean zDoAdvance = doAdvance();
        if (zDoAdvance) {
            consumer.accept(((SpinedBuffer) this.buffer).get(this.nextToConsume));
        }
        return zDoAdvance;
    }

    @Override // j$.util.Spliterator
    public void forEachRemaining(final Consumer consumer) {
        if (this.buffer == null && !this.finished) {
            Objects.requireNonNull(consumer);
            init();
            PipelineHelper pipelineHelper = this.ph;
            Objects.requireNonNull(consumer);
            pipelineHelper.wrapAndCopyInto(new Sink() { // from class: j$.util.stream.StreamSpliterators$WrappingSpliterator$$ExternalSyntheticLambda2
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

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    consumer.accept(obj);
                }

                public /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
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
        while (tryAdvance(consumer)) {
        }
    }
}

package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import j$.util.stream.StreamSpliterators$ArrayBuffer;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes3.dex */
abstract class StreamSpliterators$UnorderedSliceSpliterator {
    protected final int chunkSize;
    private final AtomicLong permits;
    protected final Spliterator s;
    private final long skipThreshold;
    protected final boolean unlimited;

    enum PermitStatus {
        NO_MORE,
        MAYBE_MORE,
        UNLIMITED
    }

    protected abstract Spliterator makeSpliterator(Spliterator spliterator);

    StreamSpliterators$UnorderedSliceSpliterator(Spliterator spliterator, long j, long j2) {
        this.s = spliterator;
        this.unlimited = j2 < 0;
        this.skipThreshold = j2 >= 0 ? j2 : 0L;
        this.chunkSize = 128;
        this.permits = new AtomicLong(j2 >= 0 ? j + j2 : j);
    }

    StreamSpliterators$UnorderedSliceSpliterator(Spliterator spliterator, StreamSpliterators$UnorderedSliceSpliterator streamSpliterators$UnorderedSliceSpliterator) {
        this.s = spliterator;
        this.unlimited = streamSpliterators$UnorderedSliceSpliterator.unlimited;
        this.permits = streamSpliterators$UnorderedSliceSpliterator.permits;
        this.skipThreshold = streamSpliterators$UnorderedSliceSpliterator.skipThreshold;
        this.chunkSize = streamSpliterators$UnorderedSliceSpliterator.chunkSize;
    }

    protected final long acquirePermits(long j) {
        long j2;
        long jMin;
        do {
            j2 = this.permits.get();
            if (j2 == 0) {
                if (this.unlimited) {
                    return j;
                }
                return 0L;
            }
            jMin = Math.min(j2, j);
            if (jMin <= 0) {
                break;
            }
        } while (!this.permits.compareAndSet(j2, j2 - jMin));
        if (this.unlimited) {
            return Math.max(j - jMin, 0L);
        }
        long j3 = this.skipThreshold;
        return j2 > j3 ? Math.max(jMin - (j2 - j3), 0L) : jMin;
    }

    protected final PermitStatus permitStatus() {
        if (this.permits.get() > 0) {
            return PermitStatus.MAYBE_MORE;
        }
        return this.unlimited ? PermitStatus.UNLIMITED : PermitStatus.NO_MORE;
    }

    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit;
        if (this.permits.get() == 0 || (spliteratorTrySplit = this.s.trySplit()) == null) {
            return null;
        }
        return makeSpliterator(spliteratorTrySplit);
    }

    public final long estimateSize() {
        return this.s.estimateSize();
    }

    public final int characteristics() {
        return this.s.characteristics() & (-16465);
    }

    static final class OfRef extends StreamSpliterators$UnorderedSliceSpliterator implements Spliterator, Consumer {
        Object tmpSlot;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        OfRef(Spliterator spliterator, long j, long j2) {
            super(spliterator, j, j2);
        }

        OfRef(Spliterator spliterator, OfRef ofRef) {
            super(spliterator, ofRef);
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            this.tmpSlot = obj;
        }

        @Override // j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            Objects.requireNonNull(consumer);
            while (permitStatus() != PermitStatus.NO_MORE && this.s.tryAdvance(this)) {
                if (acquirePermits(1L) == 1) {
                    consumer.accept(this.tmpSlot);
                    this.tmpSlot = null;
                    return true;
                }
            }
            return false;
        }

        @Override // j$.util.Spliterator
        public void forEachRemaining(Consumer consumer) {
            Objects.requireNonNull(consumer);
            StreamSpliterators$ArrayBuffer.OfRef ofRef = null;
            while (true) {
                PermitStatus permitStatus = permitStatus();
                if (permitStatus == PermitStatus.NO_MORE) {
                    return;
                }
                if (permitStatus == PermitStatus.MAYBE_MORE) {
                    if (ofRef == null) {
                        ofRef = new StreamSpliterators$ArrayBuffer.OfRef(this.chunkSize);
                    } else {
                        ofRef.reset();
                    }
                    long j = 0;
                    while (this.s.tryAdvance(ofRef)) {
                        j++;
                        if (j >= this.chunkSize) {
                            break;
                        }
                    }
                    if (j == 0) {
                        return;
                    } else {
                        ofRef.forEach(consumer, acquirePermits(j));
                    }
                } else {
                    this.s.forEachRemaining(consumer);
                    return;
                }
            }
        }

        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator
        protected Spliterator makeSpliterator(Spliterator spliterator) {
            return new OfRef(spliterator, this);
        }
    }

    static abstract class OfPrimitive extends StreamSpliterators$UnorderedSliceSpliterator implements Spliterator.OfPrimitive {
        protected abstract void acceptConsumed(Object obj);

        protected abstract StreamSpliterators$ArrayBuffer.OfPrimitive bufferCreate(int i);

        @Override // j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        OfPrimitive(Spliterator.OfPrimitive ofPrimitive, long j, long j2) {
            super(ofPrimitive, j, j2);
        }

        OfPrimitive(Spliterator.OfPrimitive ofPrimitive, OfPrimitive ofPrimitive2) {
            super(ofPrimitive, ofPrimitive2);
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public boolean tryAdvance(Object obj) {
            Objects.requireNonNull(obj);
            while (permitStatus() != PermitStatus.NO_MORE && ((Spliterator.OfPrimitive) this.s).tryAdvance(this)) {
                if (acquirePermits(1L) == 1) {
                    acceptConsumed(obj);
                    return true;
                }
            }
            return false;
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public void forEachRemaining(Object obj) {
            Objects.requireNonNull(obj);
            StreamSpliterators$ArrayBuffer.OfPrimitive ofPrimitiveBufferCreate = null;
            while (true) {
                PermitStatus permitStatus = permitStatus();
                if (permitStatus == PermitStatus.NO_MORE) {
                    return;
                }
                if (permitStatus == PermitStatus.MAYBE_MORE) {
                    if (ofPrimitiveBufferCreate == null) {
                        ofPrimitiveBufferCreate = bufferCreate(this.chunkSize);
                    } else {
                        ofPrimitiveBufferCreate.reset();
                    }
                    long j = 0;
                    while (((Spliterator.OfPrimitive) this.s).tryAdvance(ofPrimitiveBufferCreate)) {
                        j++;
                        if (j >= this.chunkSize) {
                            break;
                        }
                    }
                    if (j == 0) {
                        return;
                    } else {
                        ofPrimitiveBufferCreate.forEach(obj, acquirePermits(j));
                    }
                } else {
                    ((Spliterator.OfPrimitive) this.s).forEachRemaining(obj);
                    return;
                }
            }
        }
    }

    static final class OfInt extends OfPrimitive implements Spliterator.OfInt, IntConsumer {
        int tmpValue;

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfInt.CC.$default$tryAdvance(this, consumer);
        }

        @Override // j$.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining((Object) intConsumer);
        }

        @Override // j$.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
            return super.tryAdvance((Object) intConsumer);
        }

        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive, j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt) super.trySplit();
        }

        OfInt(Spliterator.OfInt ofInt, long j, long j2) {
            super(ofInt, j, j2);
        }

        OfInt(Spliterator.OfInt ofInt, OfInt ofInt2) {
            super(ofInt, ofInt2);
        }

        @Override // java.util.function.IntConsumer
        public void accept(int i) {
            this.tmpValue = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive
        public void acceptConsumed(IntConsumer intConsumer) {
            intConsumer.accept(this.tmpValue);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive
        public StreamSpliterators$ArrayBuffer.OfInt bufferCreate(int i) {
            return new StreamSpliterators$ArrayBuffer.OfInt(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator
        public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
            return new OfInt(ofInt, this);
        }
    }

    static final class OfLong extends OfPrimitive implements Spliterator.OfLong, LongConsumer {
        long tmpValue;

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfLong.CC.$default$tryAdvance(this, consumer);
        }

        @Override // j$.util.Spliterator.OfLong
        public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
            super.forEachRemaining((Object) longConsumer);
        }

        @Override // j$.util.Spliterator.OfLong
        public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
            return super.tryAdvance((Object) longConsumer);
        }

        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive, j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
            return (Spliterator.OfLong) super.trySplit();
        }

        OfLong(Spliterator.OfLong ofLong, long j, long j2) {
            super(ofLong, j, j2);
        }

        OfLong(Spliterator.OfLong ofLong, OfLong ofLong2) {
            super(ofLong, ofLong2);
        }

        @Override // java.util.function.LongConsumer
        public void accept(long j) {
            this.tmpValue = j;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive
        public void acceptConsumed(LongConsumer longConsumer) {
            longConsumer.accept(this.tmpValue);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive
        public StreamSpliterators$ArrayBuffer.OfLong bufferCreate(int i) {
            return new StreamSpliterators$ArrayBuffer.OfLong(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator
        public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
            return new OfLong(ofLong, this);
        }
    }

    static final class OfDouble extends OfPrimitive implements Spliterator.OfDouble, DoubleConsumer {
        double tmpValue;

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.CC.$default$tryAdvance(this, consumer);
        }

        @Override // j$.util.Spliterator.OfDouble
        public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
            super.forEachRemaining((Object) doubleConsumer);
        }

        @Override // j$.util.Spliterator.OfDouble
        public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
            return super.tryAdvance((Object) doubleConsumer);
        }

        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive, j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
            return (Spliterator.OfDouble) super.trySplit();
        }

        OfDouble(Spliterator.OfDouble ofDouble, long j, long j2) {
            super(ofDouble, j, j2);
        }

        OfDouble(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
            super(ofDouble, ofDouble2);
        }

        @Override // java.util.function.DoubleConsumer
        public void accept(double d) {
            this.tmpValue = d;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive
        public void acceptConsumed(DoubleConsumer doubleConsumer) {
            doubleConsumer.accept(this.tmpValue);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator.OfPrimitive
        public StreamSpliterators$ArrayBuffer.OfDouble bufferCreate(int i) {
            return new StreamSpliterators$ArrayBuffer.OfDouble(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator
        public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
            return new OfDouble(ofDouble, this);
        }
    }
}

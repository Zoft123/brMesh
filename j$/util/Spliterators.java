package j$.util;

import j$.util.Iterator;
import j$.util.PrimitiveIterator;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes3.dex */
public final class Spliterators {
    private static final Spliterator EMPTY_SPLITERATOR = new EmptySpliterator.OfRef();
    private static final Spliterator.OfInt EMPTY_INT_SPLITERATOR = new EmptySpliterator.OfInt();
    private static final Spliterator.OfLong EMPTY_LONG_SPLITERATOR = new EmptySpliterator.OfLong();
    private static final Spliterator.OfDouble EMPTY_DOUBLE_SPLITERATOR = new EmptySpliterator.OfDouble();

    public static Spliterator emptySpliterator() {
        return EMPTY_SPLITERATOR;
    }

    public static Spliterator.OfInt emptyIntSpliterator() {
        return EMPTY_INT_SPLITERATOR;
    }

    public static Spliterator.OfLong emptyLongSpliterator() {
        return EMPTY_LONG_SPLITERATOR;
    }

    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return EMPTY_DOUBLE_SPLITERATOR;
    }

    public static Spliterator spliterator(Object[] objArr, int i, int i2, int i3) {
        checkFromToBounds(((Object[]) Objects.requireNonNull(objArr)).length, i, i2);
        return new ArraySpliterator(objArr, i, i2, i3);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i, int i2, int i3) {
        checkFromToBounds(((int[]) Objects.requireNonNull(iArr)).length, i, i2);
        return new IntArraySpliterator(iArr, i, i2, i3);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i, int i2, int i3) {
        checkFromToBounds(((long[]) Objects.requireNonNull(jArr)).length, i, i2);
        return new LongArraySpliterator(jArr, i, i2, i3);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i, int i2, int i3) {
        checkFromToBounds(((double[]) Objects.requireNonNull(dArr)).length, i, i2);
        return new DoubleArraySpliterator(dArr, i, i2, i3);
    }

    private static void checkFromToBounds(int i, int i2, int i3) {
        if (i2 <= i3) {
            if (i2 < 0) {
                throw new ArrayIndexOutOfBoundsException(i2);
            }
            if (i3 > i) {
                throw new ArrayIndexOutOfBoundsException(i3);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("origin(" + i2 + ") > fence(" + i3 + ")");
    }

    public static Spliterator spliterator(java.util.Collection collection, int i) {
        return new IteratorSpliterator((java.util.Collection) Objects.requireNonNull(collection), i);
    }

    public static <T> Spliterator<T> spliteratorUnknownSize(java.util.Iterator<? extends T> it, int i) {
        return new IteratorSpliterator((java.util.Iterator) Objects.requireNonNull(it), i);
    }

    /* JADX INFO: renamed from: j$.util.Spliterators$1Adapter, reason: invalid class name */
    class C1Adapter implements java.util.Iterator, Consumer {
        Object nextElement;
        final /* synthetic */ Spliterator val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        C1Adapter(Spliterator spliterator) {
            this.val$spliterator = spliterator;
        }

        @Override // java.util.function.Consumer
        public void accept(Object obj) {
            this.valueReady = true;
            this.nextElement = obj;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static java.util.Iterator iterator(Spliterator spliterator) {
        Objects.requireNonNull(spliterator);
        return new C1Adapter(spliterator);
    }

    /* JADX INFO: renamed from: j$.util.Spliterators$2Adapter, reason: invalid class name */
    class C2Adapter implements PrimitiveIterator.OfInt, IntConsumer, Iterator {
        int nextElement;
        final /* synthetic */ Spliterator.OfInt val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.PrimitiveIterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            forEachRemaining((IntConsumer) obj);
        }

        @Override // j$.util.PrimitiveIterator.OfInt, java.util.Iterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfInt.CC.$default$forEachRemaining((PrimitiveIterator.OfInt) this, consumer);
        }

        @Override // j$.util.PrimitiveIterator.OfInt
        public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            PrimitiveIterator.OfInt.CC.$default$forEachRemaining((PrimitiveIterator.OfInt) this, intConsumer);
        }

        @Override // j$.util.PrimitiveIterator.OfInt, java.util.Iterator
        public /* synthetic */ Integer next() {
            return PrimitiveIterator.OfInt.CC.$default$next((PrimitiveIterator.OfInt) this);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ Object next() {
            return next();
        }

        C2Adapter(Spliterator.OfInt ofInt) {
            this.val$spliterator = ofInt;
        }

        @Override // java.util.function.IntConsumer
        public void accept(int i) {
            this.valueReady = true;
            this.nextElement = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance((IntConsumer) this);
            }
            return this.valueReady;
        }

        @Override // j$.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static PrimitiveIterator.OfInt iterator(Spliterator.OfInt ofInt) {
        Objects.requireNonNull(ofInt);
        return new C2Adapter(ofInt);
    }

    /* JADX INFO: renamed from: j$.util.Spliterators$3Adapter, reason: invalid class name */
    class C3Adapter implements PrimitiveIterator.OfLong, LongConsumer, Iterator {
        long nextElement;
        final /* synthetic */ Spliterator.OfLong val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.PrimitiveIterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            forEachRemaining((LongConsumer) obj);
        }

        @Override // j$.util.PrimitiveIterator.OfLong, java.util.Iterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfLong.CC.$default$forEachRemaining((PrimitiveIterator.OfLong) this, consumer);
        }

        @Override // j$.util.PrimitiveIterator.OfLong
        public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
            PrimitiveIterator.OfLong.CC.$default$forEachRemaining((PrimitiveIterator.OfLong) this, longConsumer);
        }

        @Override // j$.util.PrimitiveIterator.OfLong, java.util.Iterator
        public /* synthetic */ Long next() {
            return PrimitiveIterator.OfLong.CC.$default$next((PrimitiveIterator.OfLong) this);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ Object next() {
            return next();
        }

        C3Adapter(Spliterator.OfLong ofLong) {
            this.val$spliterator = ofLong;
        }

        @Override // java.util.function.LongConsumer
        public void accept(long j) {
            this.valueReady = true;
            this.nextElement = j;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance((LongConsumer) this);
            }
            return this.valueReady;
        }

        @Override // j$.util.PrimitiveIterator.OfLong
        public long nextLong() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static PrimitiveIterator.OfLong iterator(Spliterator.OfLong ofLong) {
        Objects.requireNonNull(ofLong);
        return new C3Adapter(ofLong);
    }

    /* JADX INFO: renamed from: j$.util.Spliterators$4Adapter, reason: invalid class name */
    class C4Adapter implements PrimitiveIterator.OfDouble, DoubleConsumer, Iterator {
        double nextElement;
        final /* synthetic */ Spliterator.OfDouble val$spliterator;
        boolean valueReady = false;

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        @Override // j$.util.PrimitiveIterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
            forEachRemaining((DoubleConsumer) obj);
        }

        @Override // j$.util.PrimitiveIterator.OfDouble, java.util.Iterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            PrimitiveIterator.OfDouble.CC.$default$forEachRemaining((PrimitiveIterator.OfDouble) this, consumer);
        }

        @Override // j$.util.PrimitiveIterator.OfDouble
        public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
            PrimitiveIterator.OfDouble.CC.$default$forEachRemaining((PrimitiveIterator.OfDouble) this, doubleConsumer);
        }

        @Override // j$.util.PrimitiveIterator.OfDouble, java.util.Iterator
        public /* synthetic */ Double next() {
            return PrimitiveIterator.OfDouble.CC.$default$next((PrimitiveIterator.OfDouble) this);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ Object next() {
            return next();
        }

        C4Adapter(Spliterator.OfDouble ofDouble) {
            this.val$spliterator = ofDouble;
        }

        @Override // java.util.function.DoubleConsumer
        public void accept(double d) {
            this.valueReady = true;
            this.nextElement = d;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.valueReady) {
                this.val$spliterator.tryAdvance((DoubleConsumer) this);
            }
            return this.valueReady;
        }

        @Override // j$.util.PrimitiveIterator.OfDouble
        public double nextDouble() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static PrimitiveIterator.OfDouble iterator(Spliterator.OfDouble ofDouble) {
        Objects.requireNonNull(ofDouble);
        return new C4Adapter(ofDouble);
    }

    private static abstract class EmptySpliterator {
        public int characteristics() {
            return 16448;
        }

        public long estimateSize() {
            return 0L;
        }

        public Spliterator trySplit() {
            return null;
        }

        EmptySpliterator() {
        }

        public boolean tryAdvance(Object obj) {
            Objects.requireNonNull(obj);
            return false;
        }

        public void forEachRemaining(Object obj) {
            Objects.requireNonNull(obj);
        }

        private static final class OfRef extends EmptySpliterator implements Spliterator {
            @Override // j$.util.Spliterator
            public /* synthetic */ java.util.Comparator getComparator() {
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

            @Override // j$.util.Spliterator
            public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
                super.forEachRemaining((Object) consumer);
            }

            @Override // j$.util.Spliterator
            public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return super.tryAdvance((Object) consumer);
            }

            OfRef() {
            }
        }

        private static final class OfInt extends EmptySpliterator implements Spliterator.OfInt {
            @Override // j$.util.Spliterator.OfInt, j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ java.util.Comparator getComparator() {
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

            @Override // j$.util.Spliterator.OfInt, j$.util.Spliterator
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

            @Override // j$.util.Spliterators.EmptySpliterator, j$.util.Spliterator.OfDouble, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            @Override // j$.util.Spliterators.EmptySpliterator, j$.util.Spliterator.OfDouble, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfInt() {
            }
        }

        private static final class OfLong extends EmptySpliterator implements Spliterator.OfLong {
            @Override // j$.util.Spliterator.OfLong, j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ java.util.Comparator getComparator() {
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

            @Override // j$.util.Spliterator.OfLong, j$.util.Spliterator
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

            @Override // j$.util.Spliterators.EmptySpliterator, j$.util.Spliterator.OfDouble, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            @Override // j$.util.Spliterators.EmptySpliterator, j$.util.Spliterator.OfDouble, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfLong() {
            }
        }

        private static final class OfDouble extends EmptySpliterator implements Spliterator.OfDouble {
            @Override // j$.util.Spliterator.OfDouble, j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ java.util.Comparator getComparator() {
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

            @Override // j$.util.Spliterator.OfDouble, j$.util.Spliterator
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

            @Override // j$.util.Spliterators.EmptySpliterator, j$.util.Spliterator.OfDouble, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            @Override // j$.util.Spliterators.EmptySpliterator, j$.util.Spliterator.OfDouble, j$.util.Spliterator.OfPrimitive, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfDouble() {
            }
        }
    }

    static final class ArraySpliterator implements Spliterator {
        private final Object[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        public ArraySpliterator(Object[] objArr, int i, int i2, int i3) {
            this.array = objArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        @Override // j$.util.Spliterator
        public Spliterator trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            Object[] objArr = this.array;
            this.index = i2;
            return new ArraySpliterator(objArr, i, i2, this.characteristics);
        }

        @Override // j$.util.Spliterator
        public void forEachRemaining(Consumer consumer) {
            int i;
            consumer.getClass();
            Object[] objArr = this.array;
            int length = objArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    consumer.accept(objArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        @Override // j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            Object[] objArr = this.array;
            this.index = i + 1;
            consumer.accept(objArr[i]);
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // j$.util.Spliterator
        public java.util.Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class IntArraySpliterator implements Spliterator.OfInt {
        private final int[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        @Override // j$.util.Spliterator.OfInt, j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfInt.CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        @Override // j$.util.Spliterator.OfInt, j$.util.Spliterator
        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfInt.CC.$default$tryAdvance(this, consumer);
        }

        public IntArraySpliterator(int[] iArr, int i, int i2, int i3) {
            this.array = iArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        @Override // j$.util.Spliterator
        public Spliterator.OfInt trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            int[] iArr = this.array;
            this.index = i2;
            return new IntArraySpliterator(iArr, i, i2, this.characteristics);
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public void forEachRemaining(IntConsumer intConsumer) {
            int i;
            intConsumer.getClass();
            int[] iArr = this.array;
            int length = iArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    intConsumer.accept(iArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public boolean tryAdvance(IntConsumer intConsumer) {
            intConsumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            int[] iArr = this.array;
            this.index = i + 1;
            intConsumer.accept(iArr[i]);
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // j$.util.Spliterator
        public java.util.Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class LongArraySpliterator implements Spliterator.OfLong {
        private final long[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        @Override // j$.util.Spliterator.OfLong, j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfLong.CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        @Override // j$.util.Spliterator.OfLong, j$.util.Spliterator
        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfLong.CC.$default$tryAdvance(this, consumer);
        }

        public LongArraySpliterator(long[] jArr, int i, int i2, int i3) {
            this.array = jArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        @Override // j$.util.Spliterator
        public Spliterator.OfLong trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            long[] jArr = this.array;
            this.index = i2;
            return new LongArraySpliterator(jArr, i, i2, this.characteristics);
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public void forEachRemaining(LongConsumer longConsumer) {
            int i;
            longConsumer.getClass();
            long[] jArr = this.array;
            int length = jArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    longConsumer.accept(jArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public boolean tryAdvance(LongConsumer longConsumer) {
            longConsumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            long[] jArr = this.array;
            this.index = i + 1;
            longConsumer.accept(jArr[i]);
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // j$.util.Spliterator
        public java.util.Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static final class DoubleArraySpliterator implements Spliterator.OfDouble {
        private final double[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        @Override // j$.util.Spliterator.OfDouble, j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.OfDouble.CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        @Override // j$.util.Spliterator.OfDouble, j$.util.Spliterator
        public /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return Spliterator.OfDouble.CC.$default$tryAdvance(this, consumer);
        }

        public DoubleArraySpliterator(double[] dArr, int i, int i2, int i3) {
            this.array = dArr;
            this.index = i;
            this.fence = i2;
            this.characteristics = i3 | 16448;
        }

        @Override // j$.util.Spliterator
        public Spliterator.OfDouble trySplit() {
            int i = this.index;
            int i2 = (this.fence + i) >>> 1;
            if (i >= i2) {
                return null;
            }
            double[] dArr = this.array;
            this.index = i2;
            return new DoubleArraySpliterator(dArr, i, i2, this.characteristics);
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            int i;
            doubleConsumer.getClass();
            double[] dArr = this.array;
            int length = dArr.length;
            int i2 = this.fence;
            if (length < i2 || (i = this.index) < 0) {
                return;
            }
            this.index = i2;
            if (i < i2) {
                do {
                    doubleConsumer.accept(dArr[i]);
                    i++;
                } while (i < i2);
            }
        }

        @Override // j$.util.Spliterator.OfPrimitive
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            int i = this.index;
            if (i < 0 || i >= this.fence) {
                return false;
            }
            double[] dArr = this.array;
            this.index = i + 1;
            doubleConsumer.accept(dArr[i]);
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // j$.util.Spliterator
        public java.util.Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    static class IteratorSpliterator implements Spliterator {
        private int batch;
        private final int characteristics;
        private final java.util.Collection collection;
        private long est;
        private java.util.Iterator it;

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        public IteratorSpliterator(java.util.Collection collection, int i) {
            this.collection = collection;
            this.it = null;
            this.characteristics = (i & 4096) == 0 ? i | 16448 : i;
        }

        public IteratorSpliterator(java.util.Iterator it, int i) {
            this.collection = null;
            this.it = it;
            this.est = Long.MAX_VALUE;
            this.characteristics = i & (-16449);
        }

        @Override // j$.util.Spliterator
        public Spliterator trySplit() {
            long size;
            java.util.Iterator it = this.it;
            if (it == null) {
                it = this.collection.iterator();
                this.it = it;
                size = this.collection.size();
                this.est = size;
            } else {
                size = this.est;
            }
            if (size <= 1 || !it.hasNext()) {
                return null;
            }
            int i = this.batch + 1024;
            if (i > size) {
                i = (int) size;
            }
            if (i > 33554432) {
                i = 33554432;
            }
            Object[] objArr = new Object[i];
            int i2 = 0;
            do {
                objArr[i2] = it.next();
                i2++;
                if (i2 >= i) {
                    break;
                }
            } while (it.hasNext());
            this.batch = i2;
            long j = this.est;
            if (j != Long.MAX_VALUE) {
                this.est = j - ((long) i2);
            }
            return new ArraySpliterator(objArr, 0, i2, this.characteristics);
        }

        @Override // j$.util.Spliterator
        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            java.util.Iterator it = this.it;
            if (it == null) {
                it = this.collection.iterator();
                this.it = it;
                this.est = this.collection.size();
            }
            Iterator.EL.forEachRemaining(it, consumer);
        }

        @Override // j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            if (this.it == null) {
                this.it = this.collection.iterator();
                this.est = this.collection.size();
            }
            if (!this.it.hasNext()) {
                return false;
            }
            consumer.accept(this.it.next());
            return true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            if (this.it == null) {
                this.it = this.collection.iterator();
                long size = this.collection.size();
                this.est = size;
                return size;
            }
            return this.est;
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // j$.util.Spliterator
        public java.util.Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }
    }
}

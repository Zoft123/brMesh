package kotlin.streams.jdk8;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import j$.util.Spliterator;
import j$.util.Spliterators;
import j$.util.stream.Collectors;
import j$.util.stream.DoubleStream;
import j$.util.stream.IntStream;
import j$.util.stream.LongStream;
import j$.util.stream.Stream;
import j$.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: Streams.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\u001a%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u0007¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00060\u0002*\u00020\u0005H\u0007¢\u0006\u0004\b\u0003\u0010\u0007\u001a\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\t0\u0002*\u00020\bH\u0007¢\u0006\u0004\b\u0003\u0010\n\u001a\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\f0\u0002*\u00020\u000bH\u0007¢\u0006\u0004\b\u0003\u0010\r\u001a%\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0007¢\u0006\u0004\b\u000e\u0010\u000f\u001a%\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u0007¢\u0006\u0004\b\u0011\u0010\u0012\u001a\u0019\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010*\u00020\u0005H\u0007¢\u0006\u0004\b\u0011\u0010\u0013\u001a\u0019\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0010*\u00020\bH\u0007¢\u0006\u0004\b\u0011\u0010\u0014\u001a\u0019\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0010*\u00020\u000bH\u0007¢\u0006\u0004\b\u0011\u0010\u0015¨\u0006\u0016"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "j$/util/stream/Stream", "Lkotlin/sequences/Sequence;", "asSequence", "(Lj$/util/stream/Stream;)Lkotlin/sequences/Sequence;", "j$/util/stream/IntStream", "", "(Lj$/util/stream/IntStream;)Lkotlin/sequences/Sequence;", "j$/util/stream/LongStream", "", "(Lj$/util/stream/LongStream;)Lkotlin/sequences/Sequence;", "j$/util/stream/DoubleStream", "", "(Lj$/util/stream/DoubleStream;)Lkotlin/sequences/Sequence;", "asStream", "(Lkotlin/sequences/Sequence;)Lj$/util/stream/Stream;", "", "toList", "(Lj$/util/stream/Stream;)Ljava/util/List;", "(Lj$/util/stream/IntStream;)Ljava/util/List;", "(Lj$/util/stream/LongStream;)Ljava/util/List;", "(Lj$/util/stream/DoubleStream;)Ljava/util/List;", "kotlin-stdlib-jdk8"}, k = 2, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class StreamsKt {
    public static final <T> Sequence<T> asSequence(final Stream<T> stream) {
        Intrinsics.checkNotNullParameter(stream, "<this>");
        return new Sequence<T>() { // from class: kotlin.streams.jdk8.StreamsKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator<T> iterator() {
                Iterator<T> it = stream.iterator2();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                return it;
            }
        };
    }

    public static final Sequence<Integer> asSequence(final IntStream intStream) {
        Intrinsics.checkNotNullParameter(intStream, "<this>");
        return new Sequence<Integer>() { // from class: kotlin.streams.jdk8.StreamsKt$asSequence$$inlined$Sequence$2
            @Override // kotlin.sequences.Sequence
            public Iterator<Integer> iterator() {
                Iterator<Integer> itIterator2 = intStream.iterator2();
                Intrinsics.checkNotNullExpressionValue(itIterator2, "iterator(...)");
                return itIterator2;
            }
        };
    }

    public static final Sequence<Long> asSequence(final LongStream longStream) {
        Intrinsics.checkNotNullParameter(longStream, "<this>");
        return new Sequence<Long>() { // from class: kotlin.streams.jdk8.StreamsKt$asSequence$$inlined$Sequence$3
            @Override // kotlin.sequences.Sequence
            public Iterator<Long> iterator() {
                Iterator<Long> itIterator2 = longStream.iterator2();
                Intrinsics.checkNotNullExpressionValue(itIterator2, "iterator(...)");
                return itIterator2;
            }
        };
    }

    public static final Sequence<Double> asSequence(final DoubleStream doubleStream) {
        Intrinsics.checkNotNullParameter(doubleStream, "<this>");
        return new Sequence<Double>() { // from class: kotlin.streams.jdk8.StreamsKt$asSequence$$inlined$Sequence$4
            @Override // kotlin.sequences.Sequence
            public Iterator<Double> iterator() {
                Iterator<Double> itIterator2 = doubleStream.iterator2();
                Intrinsics.checkNotNullExpressionValue(itIterator2, "iterator(...)");
                return itIterator2;
            }
        };
    }

    public static final <T> Stream<T> asStream(final Sequence<? extends T> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Stream<T> stream = StreamSupport.stream(new Supplier() { // from class: kotlin.streams.jdk8.StreamsKt$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return StreamsKt.asStream$lambda$4(sequence);
            }
        }, 16, false);
        Intrinsics.checkNotNullExpressionValue(stream, "stream(...)");
        return stream;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Spliterator asStream$lambda$4(Sequence sequence) {
        return Spliterators.spliteratorUnknownSize(sequence.iterator(), 16);
    }

    public static final <T> List<T> toList(Stream<T> stream) {
        Intrinsics.checkNotNullParameter(stream, "<this>");
        Object objCollect = stream.collect(Collectors.toList());
        Intrinsics.checkNotNullExpressionValue(objCollect, "collect(...)");
        return (List) objCollect;
    }

    public static final List<Integer> toList(IntStream intStream) {
        Intrinsics.checkNotNullParameter(intStream, "<this>");
        int[] array = intStream.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return ArraysKt.asList(array);
    }

    public static final List<Long> toList(LongStream longStream) {
        Intrinsics.checkNotNullParameter(longStream, "<this>");
        long[] array = longStream.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return ArraysKt.asList(array);
    }

    public static final List<Double> toList(DoubleStream doubleStream) {
        Intrinsics.checkNotNullParameter(doubleStream, "<this>");
        double[] array = doubleStream.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return ArraysKt.asList(array);
    }
}

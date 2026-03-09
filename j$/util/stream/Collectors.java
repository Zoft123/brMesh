package j$.util.stream;

import j$.util.function.BiConsumer$CC;
import j$.util.function.BiFunction$CC;
import j$.util.function.Function$CC;
import j$.util.stream.Collector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public final class Collectors {
    static final Set CH_CONCURRENT_ID;
    static final Set CH_CONCURRENT_NOID;
    static final Set CH_ID;
    static final Set CH_NOID;
    static final Set CH_UNORDERED_ID;
    static final Set CH_UNORDERED_NOID;

    static /* synthetic */ Object lambda$castingIdentity$2(Object obj) {
        return obj;
    }

    static {
        Collector.Characteristics characteristics = Collector.Characteristics.CONCURRENT;
        Collector.Characteristics characteristics2 = Collector.Characteristics.UNORDERED;
        Collector.Characteristics characteristics3 = Collector.Characteristics.IDENTITY_FINISH;
        CH_CONCURRENT_ID = Collections.unmodifiableSet(EnumSet.of(characteristics, characteristics2, characteristics3));
        CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(characteristics, characteristics2));
        CH_ID = Collections.unmodifiableSet(EnumSet.of(characteristics3));
        CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(characteristics2, characteristics3));
        CH_NOID = Collections.EMPTY_SET;
        CH_UNORDERED_NOID = Collections.unmodifiableSet(EnumSet.of(characteristics2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Function castingIdentity() {
        return new Function() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda65
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Collectors.lambda$castingIdentity$2(obj);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        };
    }

    static class CollectorImpl implements Collector {
        private final BiConsumer accumulator;
        private final Set characteristics;
        private final BinaryOperator combiner;
        private final Function finisher;
        private final Supplier supplier;

        CollectorImpl(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Function function, Set set) {
            this.supplier = supplier;
            this.accumulator = biConsumer;
            this.combiner = binaryOperator;
            this.finisher = function;
            this.characteristics = set;
        }

        CollectorImpl(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Set set) {
            this(supplier, biConsumer, binaryOperator, Collectors.castingIdentity(), set);
        }

        @Override // j$.util.stream.Collector
        public BiConsumer accumulator() {
            return this.accumulator;
        }

        @Override // j$.util.stream.Collector
        public Supplier supplier() {
            return this.supplier;
        }

        @Override // j$.util.stream.Collector
        public BinaryOperator combiner() {
            return this.combiner;
        }

        @Override // j$.util.stream.Collector
        public Function finisher() {
            return this.finisher;
        }

        @Override // j$.util.stream.Collector
        public Set characteristics() {
            return this.characteristics;
        }
    }

    public static <T> Collector<T, ?, List<T>> toList() {
        return new CollectorImpl(new Supplier() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda21
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ArrayList();
            }
        }, new BiConsumer() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda22
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((List) obj).add(obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        }, new BinaryOperator() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda64
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Collectors.lambda$toList$4((List) obj, (List) obj2);
            }
        }, CH_ID);
    }

    static /* synthetic */ List lambda$toList$4(List list, List list2) {
        list.addAll(list2);
        return list;
    }

    static double[] sumWithCompensation(double[] dArr, double d) {
        double d2 = d - dArr[1];
        double d3 = dArr[0];
        double d4 = d3 + d2;
        dArr[1] = (d4 - d3) - d2;
        dArr[0] = d4;
        return dArr;
    }

    static double computeFinalSum(double[] dArr) {
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        return (Double.isNaN(d) && Double.isInfinite(d2)) ? d2 : d;
    }
}

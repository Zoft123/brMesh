package j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes3.dex */
public interface Collector<T, A, R> {

    public enum Characteristics {
        CONCURRENT,
        UNORDERED,
        IDENTITY_FINISH;

        public abstract /* synthetic */ class EnumConversion {
            public static /* synthetic */ Characteristics convert(Collector.Characteristics characteristics) {
                if (characteristics == null) {
                    return null;
                }
                return characteristics == Collector.Characteristics.CONCURRENT ? Characteristics.CONCURRENT : characteristics == Collector.Characteristics.UNORDERED ? Characteristics.UNORDERED : Characteristics.IDENTITY_FINISH;
            }

            public static /* synthetic */ Collector.Characteristics convert(Characteristics characteristics) {
                if (characteristics == null) {
                    return null;
                }
                return characteristics == Characteristics.CONCURRENT ? Collector.Characteristics.CONCURRENT : characteristics == Characteristics.UNORDERED ? Collector.Characteristics.UNORDERED : Collector.Characteristics.IDENTITY_FINISH;
            }
        }
    }

    public final /* synthetic */ class VivifiedWrapper implements Collector {
        public final /* synthetic */ java.util.stream.Collector wrappedValue;

        private /* synthetic */ VivifiedWrapper(java.util.stream.Collector collector) {
            this.wrappedValue = collector;
        }

        public static /* synthetic */ Collector convert(java.util.stream.Collector collector) {
            if (collector == null) {
                return null;
            }
            return collector instanceof Wrapper ? Collector.this : new VivifiedWrapper(collector);
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ BiConsumer accumulator() {
            return this.wrappedValue.accumulator();
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ Set characteristics() {
            return StreamApiFlips.flipCharacteristicSet(this.wrappedValue.characteristics());
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ BinaryOperator combiner() {
            return this.wrappedValue.combiner();
        }

        public /* synthetic */ boolean equals(Object obj) {
            java.util.stream.Collector collector = this.wrappedValue;
            if (obj instanceof VivifiedWrapper) {
                obj = ((VivifiedWrapper) obj).wrappedValue;
            }
            return collector.equals(obj);
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ Function finisher() {
            return this.wrappedValue.finisher();
        }

        public /* synthetic */ int hashCode() {
            return this.wrappedValue.hashCode();
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ Supplier supplier() {
            return this.wrappedValue.supplier();
        }
    }

    public final /* synthetic */ class Wrapper implements java.util.stream.Collector {
        private /* synthetic */ Wrapper() {
        }

        public static /* synthetic */ java.util.stream.Collector convert(Collector collector) {
            if (collector == null) {
                return null;
            }
            return collector instanceof VivifiedWrapper ? ((VivifiedWrapper) collector).wrappedValue : new Wrapper();
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ BiConsumer accumulator() {
            return Collector.this.accumulator();
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ Set characteristics() {
            return StreamApiFlips.flipCharacteristicSet(Collector.this.characteristics());
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ BinaryOperator combiner() {
            return Collector.this.combiner();
        }

        public /* synthetic */ boolean equals(Object obj) {
            Collector collector = Collector.this;
            if (obj instanceof Wrapper) {
                obj = Collector.this;
            }
            return collector.equals(obj);
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ Function finisher() {
            return Collector.this.finisher();
        }

        public /* synthetic */ int hashCode() {
            return Collector.this.hashCode();
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ Supplier supplier() {
            return Collector.this.supplier();
        }
    }

    BiConsumer accumulator();

    Set characteristics();

    BinaryOperator combiner();

    Function finisher();

    Supplier supplier();
}

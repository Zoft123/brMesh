package j$.util;

import j$.util.stream.Stream;
import j$.util.stream.StreamSupport;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public interface Collection {

    /* JADX INFO: renamed from: j$.util.Collection$-EL, reason: invalid class name */
    public abstract /* synthetic */ class EL {
        public static /* synthetic */ void forEach(java.util.Collection collection, Consumer consumer) {
            if (collection instanceof Collection) {
                ((Collection) collection).forEach(consumer);
            } else {
                CC.$default$forEach(collection, consumer);
            }
        }

        public static /* synthetic */ boolean removeIf(java.util.Collection collection, Predicate predicate) {
            return collection instanceof Collection ? ((Collection) collection).removeIf(predicate) : CC.$default$removeIf(collection, predicate);
        }

        public static /* synthetic */ Spliterator spliterator(java.util.Collection collection) {
            return collection instanceof Collection ? ((Collection) collection).spliterator() : collection instanceof LinkedHashSet ? DesugarLinkedHashSet.spliterator((LinkedHashSet) collection) : collection instanceof SortedSet ? SortedSet$CC.$default$spliterator((SortedSet) collection) : collection instanceof Set ? Spliterators.spliterator((Set) collection, 1) : collection instanceof List ? List$CC.$default$spliterator((List) collection) : CC.$default$spliterator(collection);
        }

        public static /* synthetic */ Stream stream(java.util.Collection collection) {
            return collection instanceof Collection ? ((Collection) collection).stream() : CC.$default$stream(collection);
        }
    }

    void forEach(Consumer consumer);

    boolean removeIf(Predicate predicate);

    Spliterator spliterator();

    Stream stream();

    /* JADX INFO: renamed from: j$.util.Collection$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static boolean $default$removeIf(java.util.Collection collection, Predicate predicate) {
            if (DesugarCollections.SYNCHRONIZED_COLLECTION.isInstance(collection)) {
                return DesugarCollections.removeIf(collection, predicate);
            }
            Objects.requireNonNull(predicate);
            java.util.Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (predicate.test(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public static void $default$forEach(java.util.Collection collection, Consumer consumer) {
            Objects.requireNonNull(consumer);
            java.util.Iterator it = collection.iterator();
            while (it.hasNext()) {
                consumer.accept(it.next());
            }
        }

        public static Spliterator $default$spliterator(java.util.Collection collection) {
            return Spliterators.spliterator(collection, 0);
        }

        public static Stream $default$stream(java.util.Collection collection) {
            return StreamSupport.stream(EL.spliterator(collection), false);
        }
    }
}

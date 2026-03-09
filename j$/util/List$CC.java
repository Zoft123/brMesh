package j$.util;

import j$.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: renamed from: j$.util.List$-CC, reason: invalid class name */
/* JADX INFO: loaded from: classes3.dex */
public abstract /* synthetic */ class List$CC {
    public static void $default$sort(List list, java.util.Comparator comparator) {
        Object[] array = list.toArray();
        Arrays.sort(array, comparator);
        ListIterator listIterator = list.listIterator();
        for (Object obj : array) {
            listIterator.next();
            listIterator.set(obj);
        }
    }

    public static Spliterator $default$spliterator(List list) {
        if (list instanceof RandomAccess) {
            return new AbstractList.RandomAccessSpliterator(list);
        }
        return Spliterators.spliterator(list, 16);
    }
}

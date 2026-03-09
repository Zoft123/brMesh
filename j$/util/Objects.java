package j$.util;

import java.util.Arrays;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public final class Objects {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public static int hash(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static String toString(Object obj, String str) {
        return obj != null ? obj.toString() : str;
    }

    public static <T> T requireNonNull(T t) {
        t.getClass();
        return t;
    }

    public static Object requireNonNull(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str);
    }

    public static Object requireNonNullElse(Object obj, Object obj2) {
        return obj != null ? obj : requireNonNull(obj2, "defaultObj");
    }

    public static <T> T requireNonNullElseGet(T t, Supplier<? extends T> supplier) {
        return t != null ? t : (T) requireNonNull(((Supplier) requireNonNull(supplier, "supplier")).get(), "supplier.get()");
    }
}

package org.bson.assertions;

/* JADX INFO: loaded from: classes4.dex */
public final class Assertions {
    public static <T> T notNull(String str, T t) {
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException(str + " can not be null");
    }

    public static void isTrue(String str, boolean z) {
        if (z) {
            return;
        }
        throw new IllegalStateException("state should be: " + str);
    }

    public static void isTrueArgument(String str, boolean z) {
        if (z) {
            return;
        }
        throw new IllegalArgumentException("state should be: " + str);
    }

    public static <T> T isTrueArgument(String str, T t, boolean z) {
        if (z) {
            return t;
        }
        throw new IllegalArgumentException("state should be: " + str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T convertToType(Class<T> cls, Object obj, String str) {
        if (cls.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        throw new IllegalArgumentException(str);
    }

    private Assertions() {
    }
}

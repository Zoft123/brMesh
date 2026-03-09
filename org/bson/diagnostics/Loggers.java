package org.bson.diagnostics;

import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
public final class Loggers {
    public static final String PREFIX = "org.bson";
    private static final boolean USE_SLF4J = shouldUseSLF4J();

    public static Logger getLogger(String str) {
        Assertions.notNull("suffix", str);
        if (str.startsWith(".") || str.endsWith(".")) {
            throw new IllegalArgumentException("The suffix can not start or end with a '.'");
        }
        String str2 = "org.bson." + str;
        if (USE_SLF4J) {
            return new SLF4JLogger(str2);
        }
        return new JULLogger(str2);
    }

    private static boolean shouldUseSLF4J() {
        try {
            Class.forName("org.slf4j.Logger");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private Loggers() {
    }
}

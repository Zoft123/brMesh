package org.bson.types;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public final class MinKey implements Serializable {
    private static final long serialVersionUID = 4075901136671855684L;

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof MinKey;
    }

    public String toString() {
        return "MinKey";
    }
}

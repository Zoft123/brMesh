package org.bson.types;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class Code implements Serializable {
    private static final long serialVersionUID = 475535263314046697L;
    private final String code;

    public Code(String str) {
        this.code = str;
    }

    public String getCode() {
        return this.code;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.code.equals(((Code) obj).code);
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    public String toString() {
        return "Code{code='" + this.code + "'}";
    }
}

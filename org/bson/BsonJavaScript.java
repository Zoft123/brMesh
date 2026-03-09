package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonJavaScript extends BsonValue {
    private final String code;

    public BsonJavaScript(String str) {
        this.code = str;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.JAVASCRIPT;
    }

    public String getCode() {
        return this.code;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.code.equals(((BsonJavaScript) obj).code);
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    public String toString() {
        return "BsonJavaScript{code='" + this.code + "'}";
    }
}

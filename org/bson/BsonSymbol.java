package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonSymbol extends BsonValue {
    private final String symbol;

    public BsonSymbol(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Value can not be null");
        }
        this.symbol = str;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.SYMBOL;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.symbol.equals(((BsonSymbol) obj).symbol);
    }

    public int hashCode() {
        return this.symbol.hashCode();
    }

    public String toString() {
        return this.symbol;
    }
}

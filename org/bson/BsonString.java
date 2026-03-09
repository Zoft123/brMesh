package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonString extends BsonValue implements Comparable<BsonString> {
    private final String value;

    public BsonString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Value can not be null");
        }
        this.value = str;
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonString bsonString) {
        return this.value.compareTo(bsonString.value);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.STRING;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value.equals(((BsonString) obj).value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return "BsonString{value='" + this.value + "'}";
    }
}

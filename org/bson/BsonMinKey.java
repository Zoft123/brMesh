package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonMinKey extends BsonValue {
    public int hashCode() {
        return 0;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.MIN_KEY;
    }

    public boolean equals(Object obj) {
        return obj instanceof BsonMinKey;
    }

    public String toString() {
        return "BsonMinKey";
    }
}

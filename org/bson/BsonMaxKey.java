package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonMaxKey extends BsonValue {
    public int hashCode() {
        return 0;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.MAX_KEY;
    }

    public boolean equals(Object obj) {
        return obj instanceof BsonMaxKey;
    }

    public String toString() {
        return "BsonMaxKey";
    }
}

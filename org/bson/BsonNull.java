package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonNull extends BsonValue {
    public static final BsonNull VALUE = new BsonNull();

    public int hashCode() {
        return 0;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.NULL;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }

    public String toString() {
        return "BsonNull";
    }
}

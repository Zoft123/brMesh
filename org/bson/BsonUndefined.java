package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonUndefined extends BsonValue {
    public int hashCode() {
        return 0;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.UNDEFINED;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }
}

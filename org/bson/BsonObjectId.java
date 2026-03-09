package org.bson;

import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class BsonObjectId extends BsonValue implements Comparable<BsonObjectId> {
    private final ObjectId value;

    public BsonObjectId() {
        this(new ObjectId());
    }

    public BsonObjectId(ObjectId objectId) {
        if (objectId == null) {
            throw new IllegalArgumentException("value may not be null");
        }
        this.value = objectId;
    }

    public ObjectId getValue() {
        return this.value;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.OBJECT_ID;
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonObjectId bsonObjectId) {
        return this.value.compareTo(bsonObjectId.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value.equals(((BsonObjectId) obj).value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return "BsonObjectId{value=" + this.value.toHexString() + '}';
    }
}

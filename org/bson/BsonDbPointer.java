package org.bson;

import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDbPointer extends BsonValue {
    private final ObjectId id;
    private final String namespace;

    public BsonDbPointer(String str, ObjectId objectId) {
        if (str == null) {
            throw new IllegalArgumentException("namespace can not be null");
        }
        if (objectId == null) {
            throw new IllegalArgumentException("id can not be null");
        }
        this.namespace = str;
        this.id = objectId;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DB_POINTER;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public ObjectId getId() {
        return this.id;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonDbPointer bsonDbPointer = (BsonDbPointer) obj;
        return this.id.equals(bsonDbPointer.id) && this.namespace.equals(bsonDbPointer.namespace);
    }

    public int hashCode() {
        return (this.namespace.hashCode() * 31) + this.id.hashCode();
    }

    public String toString() {
        return "BsonDbPointer{namespace='" + this.namespace + "', id=" + this.id + '}';
    }
}

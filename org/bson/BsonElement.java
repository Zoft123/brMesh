package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonElement {
    private final String name;
    private final BsonValue value;

    public BsonElement(String str, BsonValue bsonValue) {
        this.name = str;
        this.value = bsonValue;
    }

    public String getName() {
        return this.name;
    }

    public BsonValue getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonElement bsonElement = (BsonElement) obj;
        if (getName() == null ? bsonElement.getName() == null : getName().equals(bsonElement.getName())) {
            return getValue() == null ? bsonElement.getValue() == null : getValue().equals(bsonElement.getValue());
        }
        return false;
    }

    public int hashCode() {
        return ((getName() != null ? getName().hashCode() : 0) * 31) + (getValue() != null ? getValue().hashCode() : 0);
    }
}

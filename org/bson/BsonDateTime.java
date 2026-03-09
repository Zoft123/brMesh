package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDateTime extends BsonValue implements Comparable<BsonDateTime> {
    private final long value;

    public BsonDateTime(long j) {
        this.value = j;
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonDateTime bsonDateTime) {
        return Long.valueOf(this.value).compareTo(Long.valueOf(bsonDateTime.value));
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DATE_TIME;
    }

    public long getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((BsonDateTime) obj).value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        return "BsonDateTime{value=" + this.value + '}';
    }
}

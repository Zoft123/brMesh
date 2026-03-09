package org.bson;

import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonInt64 extends BsonNumber implements Comparable<BsonInt64> {
    private final long value;

    public BsonInt64(long j) {
        this.value = j;
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonInt64 bsonInt64) {
        long j = this.value;
        long j2 = bsonInt64.value;
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.INT64;
    }

    public long getValue() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public int intValue() {
        return (int) this.value;
    }

    @Override // org.bson.BsonNumber
    public long longValue() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public double doubleValue() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public Decimal128 decimal128Value() {
        return new Decimal128(this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((BsonInt64) obj).value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        return "BsonInt64{value=" + this.value + '}';
    }
}

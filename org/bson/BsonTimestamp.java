package org.bson;

import org.bson.internal.UnsignedLongs;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonTimestamp extends BsonValue implements Comparable<BsonTimestamp> {
    private final long value;

    public BsonTimestamp() {
        this.value = 0L;
    }

    public BsonTimestamp(long j) {
        this.value = j;
    }

    public BsonTimestamp(int i, int i2) {
        this.value = (((long) i2) & 4294967295L) | (((long) i) << 32);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.TIMESTAMP;
    }

    public long getValue() {
        return this.value;
    }

    public int getTime() {
        return (int) (this.value >> 32);
    }

    public int getInc() {
        return (int) this.value;
    }

    public String toString() {
        return "Timestamp{value=" + getValue() + ", seconds=" + getTime() + ", inc=" + getInc() + '}';
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonTimestamp bsonTimestamp) {
        return UnsignedLongs.compare(this.value, bsonTimestamp.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((BsonTimestamp) obj).value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }
}

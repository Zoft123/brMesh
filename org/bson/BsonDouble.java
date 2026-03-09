package org.bson;

import androidx.camera.video.AudioStats;
import java.math.BigDecimal;
import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDouble extends BsonNumber implements Comparable<BsonDouble> {
    private final double value;

    public BsonDouble(double d) {
        this.value = d;
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonDouble bsonDouble) {
        return Double.compare(this.value, bsonDouble.value);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DOUBLE;
    }

    public double getValue() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public int intValue() {
        return (int) this.value;
    }

    @Override // org.bson.BsonNumber
    public long longValue() {
        return (long) this.value;
    }

    @Override // org.bson.BsonNumber
    public Decimal128 decimal128Value() {
        if (Double.isNaN(this.value)) {
            return Decimal128.NaN;
        }
        if (Double.isInfinite(this.value)) {
            return this.value > AudioStats.AUDIO_AMPLITUDE_NONE ? Decimal128.POSITIVE_INFINITY : Decimal128.NEGATIVE_INFINITY;
        }
        return new Decimal128(new BigDecimal(this.value));
    }

    @Override // org.bson.BsonNumber
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && Double.compare(((BsonDouble) obj).value, this.value) == 0;
    }

    public int hashCode() {
        long jDoubleToLongBits = Double.doubleToLongBits(this.value);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    public String toString() {
        return "BsonDouble{value=" + this.value + '}';
    }
}

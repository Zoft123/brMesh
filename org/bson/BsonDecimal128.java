package org.bson;

import org.bson.assertions.Assertions;
import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonDecimal128 extends BsonNumber {
    private final Decimal128 value;

    public BsonDecimal128(Decimal128 decimal128) {
        Assertions.notNull("value", decimal128);
        this.value = decimal128;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DECIMAL128;
    }

    public Decimal128 getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value.equals(((BsonDecimal128) obj).value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return "BsonDecimal128{value=" + this.value + '}';
    }

    @Override // org.bson.BsonNumber
    public int intValue() {
        return this.value.bigDecimalValue().intValue();
    }

    @Override // org.bson.BsonNumber
    public long longValue() {
        return this.value.bigDecimalValue().longValue();
    }

    @Override // org.bson.BsonNumber
    public double doubleValue() {
        return this.value.bigDecimalValue().doubleValue();
    }

    @Override // org.bson.BsonNumber
    public Decimal128 decimal128Value() {
        return this.value;
    }
}

package org.bson.codecs;

import java.math.BigDecimal;
import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes4.dex */
final class NumberCodecHelper {

    /* JADX INFO: renamed from: org.bson.codecs.NumberCodecHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;

        static {
            int[] iArr = new int[BsonType.values().length];
            $SwitchMap$org$bson$BsonType = iArr;
            try {
                iArr[BsonType.INT32.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT64.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DECIMAL128.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static int decodeInt(BsonReader bsonReader) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonType[currentBsonType.ordinal()];
        if (i == 1) {
            return bsonReader.readInt32();
        }
        if (i == 2) {
            long int64 = bsonReader.readInt64();
            int i2 = (int) int64;
            if (int64 == i2) {
                return i2;
            }
            throw invalidConversion(Integer.class, Long.valueOf(int64));
        }
        if (i == 3) {
            double d = bsonReader.readDouble();
            int i3 = (int) d;
            if (d == i3) {
                return i3;
            }
            throw invalidConversion(Integer.class, Double.valueOf(d));
        }
        if (i == 4) {
            Decimal128 decimal128 = bsonReader.readDecimal128();
            int iIntValue = decimal128.intValue();
            if (decimal128.equals(new Decimal128(iIntValue))) {
                return iIntValue;
            }
            throw invalidConversion(Integer.class, decimal128);
        }
        throw new BsonInvalidOperationException(String.format("Invalid numeric type, found: %s", currentBsonType));
    }

    static long decodeLong(BsonReader bsonReader) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonType[currentBsonType.ordinal()];
        if (i == 1) {
            return bsonReader.readInt32();
        }
        if (i == 2) {
            return bsonReader.readInt64();
        }
        if (i == 3) {
            double d = bsonReader.readDouble();
            long j = (long) d;
            if (d == j) {
                return j;
            }
            throw invalidConversion(Long.class, Double.valueOf(d));
        }
        if (i == 4) {
            Decimal128 decimal128 = bsonReader.readDecimal128();
            long jLongValue = decimal128.longValue();
            if (decimal128.equals(new Decimal128(jLongValue))) {
                return jLongValue;
            }
            throw invalidConversion(Long.class, decimal128);
        }
        throw new BsonInvalidOperationException(String.format("Invalid numeric type, found: %s", currentBsonType));
    }

    static double decodeDouble(BsonReader bsonReader) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonType[currentBsonType.ordinal()];
        if (i == 1) {
            return bsonReader.readInt32();
        }
        if (i == 2) {
            long int64 = bsonReader.readInt64();
            double d = int64;
            if (int64 == ((long) d)) {
                return d;
            }
            throw invalidConversion(Double.class, Long.valueOf(int64));
        }
        if (i == 3) {
            return bsonReader.readDouble();
        }
        if (i == 4) {
            Decimal128 decimal128 = bsonReader.readDecimal128();
            try {
                double dDoubleValue = decimal128.doubleValue();
                if (decimal128.equals(new Decimal128(new BigDecimal(dDoubleValue)))) {
                    return dDoubleValue;
                }
                throw invalidConversion(Double.class, decimal128);
            } catch (NumberFormatException unused) {
                throw invalidConversion(Double.class, decimal128);
            }
        }
        throw new BsonInvalidOperationException(String.format("Invalid numeric type, found: %s", currentBsonType));
    }

    private static <T extends Number> BsonInvalidOperationException invalidConversion(Class<T> cls, Number number) {
        return new BsonInvalidOperationException(String.format("Could not convert `%s` to a %s without losing precision", number, cls));
    }

    private NumberCodecHelper() {
    }
}

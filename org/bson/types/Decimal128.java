package org.bson.types;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public final class Decimal128 extends Number implements Comparable<Decimal128> {
    private static final int EXPONENT_OFFSET = 6176;
    private static final int MAX_BIT_LENGTH = 113;
    private static final int MAX_EXPONENT = 6111;
    private static final int MIN_EXPONENT = -6176;
    private static final long SIGN_BIT_MASK = Long.MIN_VALUE;
    private static final long serialVersionUID = 4570973266503637887L;
    private final long high;
    private final long low;
    private static final BigInteger BIG_INT_TEN = new BigInteger("10");
    private static final BigInteger BIG_INT_ONE = new BigInteger(GlobalVariable.RADAR);
    private static final BigInteger BIG_INT_ZERO = new BigInteger(GlobalVariable.ILLUMINATION);
    private static final Set<String> NaN_STRINGS = new HashSet(Collections.singletonList("nan"));
    private static final Set<String> NEGATIVE_NaN_STRINGS = new HashSet(Collections.singletonList("-nan"));
    private static final Set<String> POSITIVE_INFINITY_STRINGS = new HashSet(Arrays.asList("inf", "+inf", "infinity", "+infinity"));
    private static final Set<String> NEGATIVE_INFINITY_STRINGS = new HashSet(Arrays.asList("-inf", "-infinity"));
    private static final long INFINITY_MASK = 8646911284551352320L;
    public static final Decimal128 POSITIVE_INFINITY = fromIEEE754BIDEncoding(INFINITY_MASK, 0);
    public static final Decimal128 NEGATIVE_INFINITY = fromIEEE754BIDEncoding(-576460752303423488L, 0);
    public static final Decimal128 NEGATIVE_NaN = fromIEEE754BIDEncoding(-288230376151711744L, 0);
    private static final long NaN_MASK = 8935141660703064064L;
    public static final Decimal128 NaN = fromIEEE754BIDEncoding(NaN_MASK, 0);
    public static final Decimal128 POSITIVE_ZERO = fromIEEE754BIDEncoding(3476778912330022912L, 0);
    public static final Decimal128 NEGATIVE_ZERO = fromIEEE754BIDEncoding(-5746593124524752896L, 0);

    public static Decimal128 parse(String str) {
        String lowerCase = str.toLowerCase();
        if (NaN_STRINGS.contains(lowerCase)) {
            return NaN;
        }
        if (NEGATIVE_NaN_STRINGS.contains(lowerCase)) {
            return NEGATIVE_NaN;
        }
        if (POSITIVE_INFINITY_STRINGS.contains(lowerCase)) {
            return POSITIVE_INFINITY;
        }
        if (NEGATIVE_INFINITY_STRINGS.contains(lowerCase)) {
            return NEGATIVE_INFINITY;
        }
        return new Decimal128(new BigDecimal(str), str.charAt(0) == '-');
    }

    public static Decimal128 fromIEEE754BIDEncoding(long j, long j2) {
        return new Decimal128(j, j2);
    }

    public Decimal128(long j) {
        this(new BigDecimal(j, MathContext.DECIMAL128));
    }

    public Decimal128(BigDecimal bigDecimal) {
        this(bigDecimal, bigDecimal.signum() == -1);
    }

    private Decimal128(long j, long j2) {
        this.high = j;
        this.low = j2;
    }

    private Decimal128(BigDecimal bigDecimal, boolean z) {
        int i;
        BigDecimal bigDecimalClampAndRound = clampAndRound(bigDecimal);
        long j = -bigDecimalClampAndRound.scale();
        if (j < -6176 || j > 6111) {
            throw new AssertionError("Exponent is out of range for Decimal128 encoding: " + j);
        }
        if (bigDecimalClampAndRound.unscaledValue().bitLength() > MAX_BIT_LENGTH) {
            throw new AssertionError("Unscaled roundedValue is out of range for Decimal128 encoding:" + bigDecimalClampAndRound.unscaledValue());
        }
        BigInteger bigIntegerAbs = bigDecimalClampAndRound.unscaledValue().abs();
        int iBitLength = bigIntegerAbs.bitLength();
        long j2 = 0;
        int i2 = 0;
        long j3 = 0;
        while (true) {
            if (i2 >= Math.min(64, iBitLength)) {
                break;
            }
            if (bigIntegerAbs.testBit(i2)) {
                j3 |= 1 << i2;
            }
            i2++;
        }
        for (i = 64; i < iBitLength; i++) {
            if (bigIntegerAbs.testBit(i)) {
                j2 |= 1 << (i - 64);
            }
        }
        long j4 = ((j + 6176) << 49) | j2;
        this.high = (bigDecimalClampAndRound.signum() == -1 || z) ? j4 | Long.MIN_VALUE : j4;
        this.low = j3;
    }

    private BigDecimal clampAndRound(BigDecimal bigDecimal) {
        if ((-bigDecimal.scale()) > MAX_EXPONENT) {
            int i = (-bigDecimal.scale()) - MAX_EXPONENT;
            if (bigDecimal.unscaledValue().equals(BIG_INT_ZERO)) {
                return new BigDecimal(bigDecimal.unscaledValue(), -6111);
            }
            if (bigDecimal.precision() + i > 34) {
                throw new NumberFormatException("Exponent is out of range for Decimal128 encoding of " + bigDecimal);
            }
            return new BigDecimal(bigDecimal.unscaledValue().multiply(BIG_INT_TEN.pow(i)), bigDecimal.scale() + i);
        }
        if ((-bigDecimal.scale()) < MIN_EXPONENT) {
            int iScale = bigDecimal.scale() + MIN_EXPONENT;
            return new BigDecimal(bigDecimal.unscaledValue().divide(ensureExactRounding(bigDecimal, iScale) == 0 ? BIG_INT_ONE : BIG_INT_TEN.pow(iScale)), bigDecimal.scale() - iScale);
        }
        BigDecimal bigDecimalRound = bigDecimal.round(MathContext.DECIMAL128);
        int iPrecision = bigDecimal.precision() - bigDecimalRound.precision();
        if (iPrecision > 0) {
            ensureExactRounding(bigDecimal, iPrecision);
        }
        return bigDecimalRound;
    }

    private int ensureExactRounding(BigDecimal bigDecimal, int i) {
        String string = bigDecimal.unscaledValue().abs().toString();
        int iMax = Math.max(0, string.length() - i);
        for (int i2 = iMax; i2 < string.length(); i2++) {
            if (string.charAt(i2) != '0') {
                throw new NumberFormatException("Conversion to Decimal128 would require inexact rounding of " + bigDecimal);
            }
        }
        return iMax;
    }

    public long getHigh() {
        return this.high;
    }

    public long getLow() {
        return this.low;
    }

    public BigDecimal bigDecimalValue() {
        if (isNaN()) {
            throw new ArithmeticException("NaN can not be converted to a BigDecimal");
        }
        if (isInfinite()) {
            throw new ArithmeticException("Infinity can not be converted to a BigDecimal");
        }
        BigDecimal bigDecimalBigDecimalValueNoNegativeZeroCheck = bigDecimalValueNoNegativeZeroCheck();
        if (isNegative() && bigDecimalBigDecimalValueNoNegativeZeroCheck.signum() == 0) {
            throw new ArithmeticException("Negative zero can not be converted to a BigDecimal");
        }
        return bigDecimalBigDecimalValueNoNegativeZeroCheck;
    }

    private boolean hasDifferentSign(BigDecimal bigDecimal) {
        return isNegative() && bigDecimal.signum() == 0;
    }

    private boolean isZero(BigDecimal bigDecimal) {
        return (isNaN() || isInfinite() || bigDecimal.compareTo(BigDecimal.ZERO) != 0) ? false : true;
    }

    private BigDecimal bigDecimalValueNoNegativeZeroCheck() {
        int i = -getExponent();
        if (twoHighestCombinationBitsAreSet()) {
            return BigDecimal.valueOf(0L, i);
        }
        return new BigDecimal(new BigInteger(isNegative() ? -1 : 1, getBytes()), i);
    }

    private byte[] getBytes() {
        byte[] bArr = new byte[15];
        long j = 255;
        long j2 = 255;
        for (int i = 14; i >= 7; i--) {
            bArr[i] = (byte) ((this.low & j2) >>> ((14 - i) << 3));
            j2 <<= 8;
        }
        for (int i2 = 6; i2 >= 1; i2--) {
            bArr[i2] = (byte) ((this.high & j) >>> ((6 - i2) << 3));
            j <<= 8;
        }
        bArr[0] = (byte) ((this.high & 281474976710656L) >>> 48);
        return bArr;
    }

    private int getExponent() {
        long j;
        char c;
        if (twoHighestCombinationBitsAreSet()) {
            j = this.high & 2305807824841605120L;
            c = '/';
        } else {
            j = this.high & 9223231299366420480L;
            c = '1';
        }
        return ((int) (j >>> c)) + MIN_EXPONENT;
    }

    private boolean twoHighestCombinationBitsAreSet() {
        return (this.high & 6917529027641081856L) == 6917529027641081856L;
    }

    public boolean isNegative() {
        return (this.high & Long.MIN_VALUE) == Long.MIN_VALUE;
    }

    public boolean isInfinite() {
        return (this.high & INFINITY_MASK) == INFINITY_MASK;
    }

    public boolean isFinite() {
        return !isInfinite();
    }

    public boolean isNaN() {
        return (this.high & NaN_MASK) == NaN_MASK;
    }

    @Override // java.lang.Comparable
    public int compareTo(Decimal128 decimal128) {
        if (isNaN()) {
            return !decimal128.isNaN() ? 1 : 0;
        }
        if (isInfinite()) {
            if (isNegative()) {
                return (decimal128.isInfinite() && decimal128.isNegative()) ? 0 : -1;
            }
            if (decimal128.isNaN()) {
                return -1;
            }
            return (!decimal128.isInfinite() || decimal128.isNegative()) ? 1 : 0;
        }
        BigDecimal bigDecimalBigDecimalValueNoNegativeZeroCheck = bigDecimalValueNoNegativeZeroCheck();
        BigDecimal bigDecimalBigDecimalValueNoNegativeZeroCheck2 = decimal128.bigDecimalValueNoNegativeZeroCheck();
        if (isZero(bigDecimalBigDecimalValueNoNegativeZeroCheck) && decimal128.isZero(bigDecimalBigDecimalValueNoNegativeZeroCheck2)) {
            if (hasDifferentSign(bigDecimalBigDecimalValueNoNegativeZeroCheck)) {
                return decimal128.hasDifferentSign(bigDecimalBigDecimalValueNoNegativeZeroCheck2) ? 0 : -1;
            }
            if (decimal128.hasDifferentSign(bigDecimalBigDecimalValueNoNegativeZeroCheck2)) {
                return 1;
            }
        }
        if (decimal128.isNaN()) {
            return -1;
        }
        if (decimal128.isInfinite()) {
            return decimal128.isNegative() ? 1 : -1;
        }
        return bigDecimalBigDecimalValueNoNegativeZeroCheck.compareTo(bigDecimalBigDecimalValueNoNegativeZeroCheck2);
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) doubleValue();
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) doubleValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        if (isNaN()) {
            return Double.NaN;
        }
        if (isInfinite()) {
            return isNegative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        BigDecimal bigDecimalBigDecimalValueNoNegativeZeroCheck = bigDecimalValueNoNegativeZeroCheck();
        if (hasDifferentSign(bigDecimalBigDecimalValueNoNegativeZeroCheck)) {
            return -0.0d;
        }
        return bigDecimalBigDecimalValueNoNegativeZeroCheck.doubleValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Decimal128 decimal128 = (Decimal128) obj;
        return this.high == decimal128.high && this.low == decimal128.low;
    }

    public int hashCode() {
        long j = this.low;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        long j2 = this.high;
        return i + ((int) ((j2 >>> 32) ^ j2));
    }

    public String toString() {
        if (isNaN()) {
            return "NaN";
        }
        if (isInfinite()) {
            if (isNegative()) {
                return "-Infinity";
            }
            return "Infinity";
        }
        return toStringWithBigDecimal();
    }

    private String toStringWithBigDecimal() {
        StringBuilder sb = new StringBuilder();
        BigDecimal bigDecimalBigDecimalValueNoNegativeZeroCheck = bigDecimalValueNoNegativeZeroCheck();
        String string = bigDecimalBigDecimalValueNoNegativeZeroCheck.unscaledValue().abs().toString();
        if (isNegative()) {
            sb.append('-');
        }
        int i = -bigDecimalBigDecimalValueNoNegativeZeroCheck.scale();
        int length = (string.length() - 1) + i;
        if (i > 0 || length < -6) {
            sb.append(string.charAt(0));
            if (string.length() > 1) {
                sb.append('.');
                sb.append((CharSequence) string, 1, string.length());
            }
            sb.append('E');
            if (length > 0) {
                sb.append('+');
            }
            sb.append(length);
        } else if (i == 0) {
            sb.append(string);
        } else {
            int length2 = (-i) - string.length();
            if (length2 >= 0) {
                sb.append("0.");
                for (int i2 = 0; i2 < length2; i2++) {
                    sb.append('0');
                }
                sb.append((CharSequence) string, 0, string.length());
            } else {
                int i3 = -length2;
                sb.append((CharSequence) string, 0, i3);
                sb.append('.');
                sb.append((CharSequence) string, i3, i3 - i);
            }
        }
        return sb.toString();
    }
}

package j$.time;

import j$.time.format.DateTimeParseException;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAmount;
import j$.util.Objects;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public final class Duration implements TemporalAmount, Comparable<Duration>, Serializable {
    private static final long serialVersionUID = 3078945930695997490L;
    private final int nanos;
    private final long seconds;
    public static final Duration ZERO = new Duration(0, 0);
    private static final BigInteger BI_NANOS_PER_SECOND = BigInteger.valueOf(1000000000);

    private static class Lazy {
        static final Pattern PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?", 2);
    }

    public static Duration ofSeconds(long j) {
        return create(j, 0);
    }

    public static Duration ofSeconds(long j, long j2) {
        return create(Clock$OffsetClock$$ExternalSyntheticBackport0.m(j, DesugarLocalDate$$ExternalSyntheticBackport0.m(j2, 1000000000L)), (int) Clock$TickClock$$ExternalSyntheticBackport0.m(j2, 1000000000L));
    }

    public static Duration ofNanos(long j) {
        long j2 = j / 1000000000;
        int i = (int) (j % 1000000000);
        if (i < 0) {
            i = (int) (((long) i) + 1000000000);
            j2--;
        }
        return create(j2, i);
    }

    public static Duration parse(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text");
        Matcher matcher = Lazy.PATTERN.matcher(charSequence);
        if (matcher.matches() && !charMatch(charSequence, matcher.start(3), matcher.end(3), 'T')) {
            int i = 1;
            boolean zCharMatch = charMatch(charSequence, matcher.start(1), matcher.end(1), '-');
            int iStart = matcher.start(2);
            int iEnd = matcher.end(2);
            int iStart2 = matcher.start(4);
            int iEnd2 = matcher.end(4);
            int iStart3 = matcher.start(5);
            int iEnd3 = matcher.end(5);
            int iStart4 = matcher.start(6);
            int iEnd4 = matcher.end(6);
            int iStart5 = matcher.start(7);
            int iEnd5 = matcher.end(7);
            if (iStart >= 0 || iStart2 >= 0 || iStart3 >= 0 || iStart4 >= 0) {
                long number = parseNumber(charSequence, iStart, iEnd, 86400, "days");
                long number2 = parseNumber(charSequence, iStart2, iEnd2, 3600, "hours");
                long number3 = parseNumber(charSequence, iStart3, iEnd3, 60, "minutes");
                long number4 = parseNumber(charSequence, iStart4, iEnd4, 1, "seconds");
                if (iStart4 >= 0 && charSequence.charAt(iStart4) == '-') {
                    i = -1;
                }
                try {
                    return create(zCharMatch, number, number2, number3, number4, parseFraction(charSequence, iStart5, iEnd5, i));
                } catch (ArithmeticException e) {
                    throw ((DateTimeParseException) new DateTimeParseException("Text cannot be parsed to a Duration: overflow", charSequence, 0).initCause(e));
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Duration", charSequence, 0);
    }

    private static boolean charMatch(CharSequence charSequence, int i, int i2, char c) {
        return i >= 0 && i2 == i + 1 && charSequence.charAt(i) == c;
    }

    private static long parseNumber(CharSequence charSequence, int i, int i2, int i3, String str) {
        if (i < 0 || i2 < 0) {
            return 0L;
        }
        try {
            return Duration$$ExternalSyntheticBackport1.m(Long.parseLong(charSequence.subSequence(i, i2).toString(), 10), i3);
        } catch (ArithmeticException | NumberFormatException e) {
            throw ((DateTimeParseException) new DateTimeParseException("Text cannot be parsed to a Duration: " + str, charSequence, 0).initCause(e));
        }
    }

    private static int parseFraction(CharSequence charSequence, int i, int i2, int i3) {
        int i4;
        if (i < 0 || i2 < 0 || (i4 = i2 - i) == 0) {
            return 0;
        }
        try {
            int i5 = Integer.parseInt(charSequence.subSequence(i, i2).toString(), 10);
            for (i4 = i2 - i; i4 < 9; i4++) {
                i5 *= 10;
            }
            return i5 * i3;
        } catch (ArithmeticException | NumberFormatException e) {
            throw ((DateTimeParseException) new DateTimeParseException("Text cannot be parsed to a Duration: fraction", charSequence, 0).initCause(e));
        }
    }

    private static Duration create(boolean z, long j, long j2, long j3, long j4, int i) {
        long jM = Clock$OffsetClock$$ExternalSyntheticBackport0.m(j, Clock$OffsetClock$$ExternalSyntheticBackport0.m(j2, Clock$OffsetClock$$ExternalSyntheticBackport0.m(j3, j4)));
        if (z) {
            return ofSeconds(jM, i).negated();
        }
        return ofSeconds(jM, i);
    }

    private static Duration create(long j, int i) {
        if ((((long) i) | j) == 0) {
            return ZERO;
        }
        return new Duration(j, i);
    }

    private Duration(long j, int i) {
        this.seconds = j;
        this.nanos = i;
    }

    public long getSeconds() {
        return this.seconds;
    }

    public int getNano() {
        return this.nanos;
    }

    public Duration multipliedBy(long j) {
        if (j == 0) {
            return ZERO;
        }
        return j == 1 ? this : create(toBigDecimalSeconds().multiply(BigDecimal.valueOf(j)));
    }

    private BigDecimal toBigDecimalSeconds() {
        return BigDecimal.valueOf(this.seconds).add(BigDecimal.valueOf(this.nanos, 9));
    }

    private static Duration create(BigDecimal bigDecimal) {
        BigInteger bigIntegerExact = bigDecimal.movePointRight(9).toBigIntegerExact();
        BigInteger[] bigIntegerArrDivideAndRemainder = bigIntegerExact.divideAndRemainder(BI_NANOS_PER_SECOND);
        if (bigIntegerArrDivideAndRemainder[0].bitLength() > 63) {
            throw new ArithmeticException("Exceeds capacity of Duration: " + bigIntegerExact);
        }
        return ofSeconds(bigIntegerArrDivideAndRemainder[0].longValue(), bigIntegerArrDivideAndRemainder[1].intValue());
    }

    public Duration negated() {
        return multipliedBy(-1L);
    }

    @Override // j$.time.temporal.TemporalAmount
    public Temporal addTo(Temporal temporal) {
        long j = this.seconds;
        if (j != 0) {
            temporal = temporal.plus(j, ChronoUnit.SECONDS);
        }
        int i = this.nanos;
        return i != 0 ? temporal.plus(i, ChronoUnit.NANOS) : temporal;
    }

    public long toMillis() {
        long j = this.seconds;
        long j2 = this.nanos;
        if (j < 0) {
            j++;
            j2 -= 1000000000;
        }
        return Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(j, 1000), j2 / 1000000);
    }

    @Override // java.lang.Comparable
    public int compareTo(Duration duration) {
        int iCompare = Long.compare(this.seconds, duration.seconds);
        return iCompare != 0 ? iCompare : this.nanos - duration.nanos;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Duration) {
            Duration duration = (Duration) obj;
            if (this.seconds == duration.seconds && this.nanos == duration.nanos) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        long j = this.seconds;
        return ((int) (j ^ (j >>> 32))) + (this.nanos * 51);
    }

    public String toString() {
        if (this == ZERO) {
            return "PT0S";
        }
        long j = this.seconds;
        if (j < 0 && this.nanos > 0) {
            j++;
        }
        long j2 = j / 3600;
        int i = (int) ((j % 3600) / 60);
        int i2 = (int) (j % 60);
        StringBuilder sb = new StringBuilder(24);
        sb.append("PT");
        if (j2 != 0) {
            sb.append(j2);
            sb.append('H');
        }
        if (i != 0) {
            sb.append(i);
            sb.append('M');
        }
        if (i2 == 0 && this.nanos == 0 && sb.length() > 2) {
            return sb.toString();
        }
        if (this.seconds < 0 && this.nanos > 0 && i2 == 0) {
            sb.append("-0");
        } else {
            sb.append(i2);
        }
        if (this.nanos > 0) {
            int length = sb.length();
            if (this.seconds < 0) {
                sb.append(2000000000 - ((long) this.nanos));
            } else {
                sb.append(((long) this.nanos) + 1000000000);
            }
            while (sb.charAt(sb.length() - 1) == '0') {
                sb.setLength(sb.length() - 1);
            }
            sb.setCharAt(length, '.');
        }
        sb.append('S');
        return sb.toString();
    }

    private Object writeReplace() {
        return new Ser((byte) 1, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) {
        dataOutput.writeLong(this.seconds);
        dataOutput.writeInt(this.nanos);
    }

    static Duration readExternal(DataInput dataInput) {
        return ofSeconds(dataInput.readLong(), dataInput.readInt());
    }
}

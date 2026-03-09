package j$.time;

import j$.time.chrono.Chronology;
import j$.time.chrono.IsoChronology;
import j$.time.format.DateTimeParseException;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAmount;
import j$.time.temporal.TemporalQueries;
import j$.util.Objects;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.BSON;

/* JADX INFO: loaded from: classes3.dex */
public final class Period implements TemporalAmount, Serializable {
    private static final long serialVersionUID = -3587258372562876L;
    private final int days;
    private final int months;
    private final int years;
    public static final Period ZERO = new Period(0, 0, 0);
    private static final Pattern PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
    private static final List SUPPORTED_UNITS = Duration$DurationUnits$$ExternalSyntheticBackport0.m(new Object[]{ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS});

    public static Period ofDays(int i) {
        return create(0, 0, i);
    }

    public static Period of(int i, int i2, int i3) {
        return create(i, i2, i3);
    }

    public static Period parse(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text");
        Matcher matcher = PATTERN.matcher(charSequence);
        if (matcher.matches()) {
            int i = charMatch(charSequence, matcher.start(1), matcher.end(1), '-') ? -1 : 1;
            int iStart = matcher.start(2);
            int iEnd = matcher.end(2);
            int iStart2 = matcher.start(3);
            int iEnd2 = matcher.end(3);
            int iStart3 = matcher.start(4);
            int iEnd3 = matcher.end(4);
            int iStart4 = matcher.start(5);
            int iEnd4 = matcher.end(5);
            if (iStart >= 0 || iStart2 >= 0 || iStart3 >= 0 || iStart4 >= 0) {
                try {
                    return create(parseNumber(charSequence, iStart, iEnd, i), parseNumber(charSequence, iStart2, iEnd2, i), Period$$ExternalSyntheticBackport3.m(parseNumber(charSequence, iStart4, iEnd4, i), Period$$ExternalSyntheticBackport0.m(parseNumber(charSequence, iStart3, iEnd3, i), 7)));
                } catch (NumberFormatException e) {
                    throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0, e);
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0);
    }

    private static boolean charMatch(CharSequence charSequence, int i, int i2, char c) {
        return i >= 0 && i2 == i + 1 && charSequence.charAt(i) == c;
    }

    private static int parseNumber(CharSequence charSequence, int i, int i2, int i3) {
        if (i < 0 || i2 < 0) {
            return 0;
        }
        if (charSequence.charAt(i) == '+') {
            i++;
        }
        try {
            return Period$$ExternalSyntheticBackport0.m(Integer.parseInt(charSequence.subSequence(i, i2).toString(), 10), i3);
        } catch (ArithmeticException e) {
            throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0, e);
        }
    }

    private static Period create(int i, int i2, int i3) {
        if ((i | i2 | i3) == 0) {
            return ZERO;
        }
        return new Period(i, i2, i3);
    }

    private Period(int i, int i2, int i3) {
        this.years = i;
        this.months = i2;
        this.days = i3;
    }

    public boolean isZero() {
        return this == ZERO;
    }

    public int getDays() {
        return this.days;
    }

    public long toTotalMonths() {
        return (((long) this.years) * 12) + ((long) this.months);
    }

    @Override // j$.time.temporal.TemporalAmount
    public Temporal addTo(Temporal temporal) {
        validateChrono(temporal);
        if (this.months == 0) {
            int i = this.years;
            if (i != 0) {
                temporal = temporal.plus(i, ChronoUnit.YEARS);
            }
        } else {
            long totalMonths = toTotalMonths();
            if (totalMonths != 0) {
                temporal = temporal.plus(totalMonths, ChronoUnit.MONTHS);
            }
        }
        int i2 = this.days;
        return i2 != 0 ? temporal.plus(i2, ChronoUnit.DAYS) : temporal;
    }

    private void validateChrono(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
        if (chronology == null || IsoChronology.INSTANCE.equals(chronology)) {
            return;
        }
        throw new DateTimeException("Chronology mismatch, expected: ISO, actual: " + chronology.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Period) {
            Period period = (Period) obj;
            if (this.years == period.years && this.months == period.months && this.days == period.days) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.years + Integer.rotateLeft(this.months, 8) + Integer.rotateLeft(this.days, 16);
    }

    public String toString() {
        if (this == ZERO) {
            return "P0D";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('P');
        int i = this.years;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.months;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.days;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }

    private Object writeReplace() {
        return new Ser(BSON.SYMBOL, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) {
        dataOutput.writeInt(this.years);
        dataOutput.writeInt(this.months);
        dataOutput.writeInt(this.days);
    }

    static Period readExternal(DataInput dataInput) {
        return of(dataInput.readInt(), dataInput.readInt(), dataInput.readInt());
    }
}

package j$.time.chrono;

import j$.time.DateTimeException;
import j$.time.LocalDate;
import j$.time.LocalTime;
import j$.time.temporal.ChronoField;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalAmount;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.UnsupportedTemporalTypeException;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public final class JapaneseDate extends ChronoLocalDateImpl implements ChronoLocalDate, Serializable {
    static final LocalDate MEIJI_6_ISODATE = LocalDate.of(1873, 1, 1);
    private static final long serialVersionUID = -305327627230580483L;
    private transient JapaneseEra era;
    private final transient LocalDate isoDate;
    private transient int yearOfEra;

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static JapaneseDate of(JapaneseEra japaneseEra, int i, int i2, int i3) {
        Objects.requireNonNull(japaneseEra, "era");
        LocalDate localDateOf = LocalDate.of((japaneseEra.getSince().getYear() + i) - 1, i2, i3);
        if (localDateOf.isBefore(japaneseEra.getSince()) || japaneseEra != JapaneseEra.from(localDateOf)) {
            throw new DateTimeException("year, month, and day not valid for Era");
        }
        return new JapaneseDate(japaneseEra, i, localDateOf);
    }

    static JapaneseDate ofYearDay(JapaneseEra japaneseEra, int i, int i2) {
        LocalDate localDateOfYearDay;
        Objects.requireNonNull(japaneseEra, "era");
        if (i == 1) {
            localDateOfYearDay = LocalDate.ofYearDay(japaneseEra.getSince().getYear(), (japaneseEra.getSince().getDayOfYear() + i2) - 1);
        } else {
            localDateOfYearDay = LocalDate.ofYearDay((japaneseEra.getSince().getYear() + i) - 1, i2);
        }
        if (localDateOfYearDay.isBefore(japaneseEra.getSince()) || japaneseEra != JapaneseEra.from(localDateOfYearDay)) {
            throw new DateTimeException("Invalid parameters");
        }
        return new JapaneseDate(japaneseEra, i, localDateOfYearDay);
    }

    JapaneseDate(LocalDate localDate) {
        if (localDate.isBefore(MEIJI_6_ISODATE)) {
            throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
        }
        this.era = JapaneseEra.from(localDate);
        this.yearOfEra = (localDate.getYear() - this.era.getSince().getYear()) + 1;
        this.isoDate = localDate;
    }

    JapaneseDate(JapaneseEra japaneseEra, int i, LocalDate localDate) {
        if (localDate.isBefore(MEIJI_6_ISODATE)) {
            throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
        }
        this.era = japaneseEra;
        this.yearOfEra = i;
        this.isoDate = localDate;
    }

    @Override // j$.time.chrono.ChronoLocalDate
    public JapaneseChronology getChronology() {
        return JapaneseChronology.INSTANCE;
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public JapaneseEra getEra() {
        return this.era;
    }

    public int lengthOfMonth() {
        return this.isoDate.lengthOfMonth();
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public int lengthOfYear() {
        int iLengthOfYear;
        JapaneseEra next = this.era.next();
        if (next != null && next.getSince().getYear() == this.isoDate.getYear()) {
            iLengthOfYear = next.getSince().getDayOfYear() - 1;
        } else {
            iLengthOfYear = this.isoDate.lengthOfYear();
        }
        return this.yearOfEra == 1 ? iLengthOfYear - (this.era.getSince().getDayOfYear() - 1) : iLengthOfYear;
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate, j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        if (temporalField != ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH && temporalField != ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR && temporalField != ChronoField.ALIGNED_WEEK_OF_MONTH && temporalField != ChronoField.ALIGNED_WEEK_OF_YEAR) {
            if (temporalField instanceof ChronoField) {
                return temporalField.isDateBased();
            }
            if (temporalField != null && temporalField.isSupportedBy(this)) {
                return true;
            }
        }
        return false;
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (isSupported(temporalField)) {
                ChronoField chronoField = (ChronoField) temporalField;
                int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                if (i == 1) {
                    return ValueRange.of(1L, lengthOfMonth());
                }
                if (i == 2) {
                    return ValueRange.of(1L, lengthOfYear());
                }
                if (i == 3) {
                    int year = this.era.getSince().getYear();
                    if (this.era.next() != null) {
                        return ValueRange.of(1L, (r0.getSince().getYear() - year) + 1);
                    }
                    return ValueRange.of(1L, 999999999 - year);
                }
                return getChronology().range(chronoField);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    /* JADX INFO: renamed from: j$.time.chrono.JapaneseDate$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoField;

        static {
            int[] iArr = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr;
            try {
                iArr[ChronoField.DAY_OF_MONTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.DAY_OF_YEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.YEAR_OF_ERA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_WEEK_OF_MONTH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_WEEK_OF_YEAR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ERA.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.YEAR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()]) {
                case 2:
                    if (this.yearOfEra == 1) {
                        return (this.isoDate.getDayOfYear() - this.era.getSince().getDayOfYear()) + 1;
                    }
                    return this.isoDate.getDayOfYear();
                case 3:
                    return this.yearOfEra;
                case 4:
                case 5:
                case 6:
                case 7:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
                case 8:
                    return this.era.getValue();
                default:
                    return this.isoDate.getLong(temporalField);
            }
        }
        return temporalField.getFrom(this);
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.temporal.Temporal
    public JapaneseDate with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            if (getLong(chronoField) == j) {
                return this;
            }
            int[] iArr = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField;
            int i = iArr[chronoField.ordinal()];
            if (i == 3 || i == 8 || i == 9) {
                int iCheckValidIntValue = getChronology().range(chronoField).checkValidIntValue(j, chronoField);
                int i2 = iArr[chronoField.ordinal()];
                if (i2 == 3) {
                    return withYear(iCheckValidIntValue);
                }
                if (i2 == 8) {
                    return withYear(JapaneseEra.of(iCheckValidIntValue), this.yearOfEra);
                }
                if (i2 == 9) {
                    return with(this.isoDate.withYear(iCheckValidIntValue));
                }
            }
            return with(this.isoDate.with(temporalField, j));
        }
        return (JapaneseDate) super.with(temporalField, j);
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.temporal.Temporal
    public JapaneseDate with(TemporalAdjuster temporalAdjuster) {
        return (JapaneseDate) super.with(temporalAdjuster);
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public JapaneseDate plus(TemporalAmount temporalAmount) {
        return (JapaneseDate) super.plus(temporalAmount);
    }

    private JapaneseDate withYear(JapaneseEra japaneseEra, int i) {
        return with(this.isoDate.withYear(JapaneseChronology.INSTANCE.prolepticYear(japaneseEra, i)));
    }

    private JapaneseDate withYear(int i) {
        return withYear(getEra(), i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.time.chrono.ChronoLocalDateImpl
    public JapaneseDate plusYears(long j) {
        return with(this.isoDate.plusYears(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.time.chrono.ChronoLocalDateImpl
    public JapaneseDate plusMonths(long j) {
        return with(this.isoDate.plusMonths(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.time.chrono.ChronoLocalDateImpl
    public JapaneseDate plusDays(long j) {
        return with(this.isoDate.plusDays(j));
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.temporal.Temporal
    public JapaneseDate plus(long j, TemporalUnit temporalUnit) {
        return (JapaneseDate) super.plus(j, temporalUnit);
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.temporal.Temporal
    public JapaneseDate minus(long j, TemporalUnit temporalUnit) {
        return (JapaneseDate) super.minus(j, temporalUnit);
    }

    private JapaneseDate with(LocalDate localDate) {
        return localDate.equals(this.isoDate) ? this : new JapaneseDate(localDate);
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public final ChronoLocalDateTime atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public long toEpochDay() {
        return this.isoDate.toEpochDay();
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JapaneseDate) {
            return this.isoDate.equals(((JapaneseDate) obj).isoDate);
        }
        return false;
    }

    @Override // j$.time.chrono.ChronoLocalDateImpl, j$.time.chrono.ChronoLocalDate
    public int hashCode() {
        return getChronology().getId().hashCode() ^ this.isoDate.hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser((byte) 4, this);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(get(ChronoField.YEAR));
        dataOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
        dataOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
    }

    static JapaneseDate readExternal(DataInput dataInput) throws IOException {
        return JapaneseChronology.INSTANCE.date(dataInput.readInt(), (int) dataInput.readByte(), (int) dataInput.readByte());
    }
}

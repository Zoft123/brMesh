package j$.time;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import j$.time.format.DateTimeFormatter;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
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
import kotlin.time.DurationKt;
import org.bson.BSON;

/* JADX INFO: loaded from: classes3.dex */
public final class LocalTime implements Temporal, TemporalAdjuster, Comparable<LocalTime>, Serializable {
    private static final LocalTime[] HOURS = new LocalTime[24];
    public static final LocalTime MAX;
    public static final LocalTime MIDNIGHT;
    public static final LocalTime MIN;
    public static final LocalTime NOON;
    private static final long serialVersionUID = 6414437269572265201L;
    private final byte hour;
    private final byte minute;
    private final int nano;
    private final byte second;

    static {
        int i = 0;
        while (true) {
            LocalTime[] localTimeArr = HOURS;
            if (i < localTimeArr.length) {
                localTimeArr[i] = new LocalTime(i, 0, 0, 0);
                i++;
            } else {
                LocalTime localTime = localTimeArr[0];
                MIDNIGHT = localTime;
                NOON = localTimeArr[12];
                MIN = localTime;
                MAX = new LocalTime(23, 59, 59, 999999999);
                return;
            }
        }
    }

    public static LocalTime of(int i, int i2) {
        ChronoField.HOUR_OF_DAY.checkValidValue(i);
        if (i2 == 0) {
            return HOURS[i];
        }
        ChronoField.MINUTE_OF_HOUR.checkValidValue(i2);
        return new LocalTime(i, i2, 0, 0);
    }

    public static LocalTime of(int i, int i2, int i3, int i4) {
        ChronoField.HOUR_OF_DAY.checkValidValue(i);
        ChronoField.MINUTE_OF_HOUR.checkValidValue(i2);
        ChronoField.SECOND_OF_MINUTE.checkValidValue(i3);
        ChronoField.NANO_OF_SECOND.checkValidValue(i4);
        return create(i, i2, i3, i4);
    }

    public static LocalTime ofSecondOfDay(long j) {
        ChronoField.SECOND_OF_DAY.checkValidValue(j);
        int i = (int) (j / 3600);
        long j2 = j - ((long) (i * 3600));
        int i2 = (int) (j2 / 60);
        return create(i, i2, (int) (j2 - ((long) (i2 * 60))), 0);
    }

    public static LocalTime ofNanoOfDay(long j) {
        ChronoField.NANO_OF_DAY.checkValidValue(j);
        int i = (int) (j / 3600000000000L);
        long j2 = j - (((long) i) * 3600000000000L);
        int i2 = (int) (j2 / 60000000000L);
        long j3 = j2 - (((long) i2) * 60000000000L);
        int i3 = (int) (j3 / 1000000000);
        return create(i, i2, i3, (int) (j3 - (((long) i3) * 1000000000)));
    }

    public static LocalTime from(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        LocalTime localTime = (LocalTime) temporalAccessor.query(TemporalQueries.localTime());
        if (localTime != null) {
            return localTime;
        }
        throw new DateTimeException("Unable to obtain LocalTime from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    public static LocalTime parse(CharSequence charSequence) {
        return parse(charSequence, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static LocalTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (LocalTime) dateTimeFormatter.parse(charSequence, new TemporalQuery() { // from class: j$.time.LocalTime$$ExternalSyntheticLambda0
            @Override // j$.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return LocalTime.from(temporalAccessor);
            }
        });
    }

    private static LocalTime create(int i, int i2, int i3, int i4) {
        if ((i2 | i3 | i4) == 0) {
            return HOURS[i];
        }
        return new LocalTime(i, i2, i3, i4);
    }

    private LocalTime(int i, int i2, int i3, int i4) {
        this.hour = (byte) i;
        this.minute = (byte) i2;
        this.second = (byte) i3;
        this.nano = i4;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return temporalField.isTimeBased();
        }
        return temporalField != null && temporalField.isSupportedBy(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.CC.$default$range(this, temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return get0(temporalField);
        }
        return TemporalAccessor.CC.$default$get(this, temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.NANO_OF_DAY) {
                return toNanoOfDay();
            }
            if (temporalField == ChronoField.MICRO_OF_DAY) {
                return toNanoOfDay() / 1000;
            }
            return get0(temporalField);
        }
        return temporalField.getFrom(this);
    }

    private int get0(TemporalField temporalField) {
        switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()]) {
            case 1:
                return this.nano;
            case 2:
                throw new UnsupportedTemporalTypeException("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
            case 3:
                return this.nano / 1000;
            case 4:
                throw new UnsupportedTemporalTypeException("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
            case 5:
                return this.nano / DurationKt.NANOS_IN_MILLIS;
            case 6:
                return (int) (toNanoOfDay() / 1000000);
            case 7:
                return this.second;
            case 8:
                return toSecondOfDay();
            case 9:
                return this.minute;
            case 10:
                return (this.hour * 60) + this.minute;
            case 11:
                return this.hour % BSON.REF;
            case 12:
                int i = this.hour % BSON.REF;
                if (i % 12 == 0) {
                    return 12;
                }
                return i;
            case 13:
                return this.hour;
            case 14:
                byte b = this.hour;
                if (b == 0) {
                    return 24;
                }
                return b;
            case 15:
                return this.hour / BSON.REF;
            default:
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public int getNano() {
        return this.nano;
    }

    @Override // j$.time.temporal.Temporal
    public LocalTime with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalTime) {
            return (LocalTime) temporalAdjuster;
        }
        return (LocalTime) temporalAdjuster.adjustInto(this);
    }

    @Override // j$.time.temporal.Temporal
    public LocalTime with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            chronoField.checkValidValue(j);
            switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()]) {
                case 1:
                    return withNano((int) j);
                case 2:
                    return ofNanoOfDay(j);
                case 3:
                    return withNano(((int) j) * 1000);
                case 4:
                    return ofNanoOfDay(j * 1000);
                case 5:
                    return withNano(((int) j) * DurationKt.NANOS_IN_MILLIS);
                case 6:
                    return ofNanoOfDay(j * 1000000);
                case 7:
                    return withSecond((int) j);
                case 8:
                    return plusSeconds(j - ((long) toSecondOfDay()));
                case 9:
                    return withMinute((int) j);
                case 10:
                    return plusMinutes(j - ((long) ((this.hour * 60) + this.minute)));
                case 11:
                    return plusHours(j - ((long) (this.hour % BSON.REF)));
                case 12:
                    if (j == 12) {
                        j = 0;
                    }
                    return plusHours(j - ((long) (this.hour % BSON.REF)));
                case 13:
                    return withHour((int) j);
                case 14:
                    if (j == 24) {
                        j = 0;
                    }
                    return withHour((int) j);
                case 15:
                    return plusHours((j - ((long) (this.hour / BSON.REF))) * 12);
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
        }
        return (LocalTime) temporalField.adjustInto(this, j);
    }

    public LocalTime withHour(int i) {
        if (this.hour == i) {
            return this;
        }
        ChronoField.HOUR_OF_DAY.checkValidValue(i);
        return create(i, this.minute, this.second, this.nano);
    }

    public LocalTime withMinute(int i) {
        if (this.minute == i) {
            return this;
        }
        ChronoField.MINUTE_OF_HOUR.checkValidValue(i);
        return create(this.hour, i, this.second, this.nano);
    }

    public LocalTime withSecond(int i) {
        if (this.second == i) {
            return this;
        }
        ChronoField.SECOND_OF_MINUTE.checkValidValue(i);
        return create(this.hour, this.minute, i, this.nano);
    }

    public LocalTime withNano(int i) {
        if (this.nano == i) {
            return this;
        }
        ChronoField.NANO_OF_SECOND.checkValidValue(i);
        return create(this.hour, this.minute, this.second, i);
    }

    /* JADX INFO: renamed from: j$.time.LocalTime$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoField;
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoUnit;

        static {
            int[] iArr = new int[ChronoUnit.values().length];
            $SwitchMap$java$time$temporal$ChronoUnit = iArr;
            try {
                iArr[ChronoUnit.NANOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MICROS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MILLIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HALF_DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr2;
            try {
                iArr2[ChronoField.NANO_OF_SECOND.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.NANO_OF_DAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MICRO_OF_SECOND.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MICRO_OF_DAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MILLI_OF_SECOND.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MILLI_OF_DAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.SECOND_OF_MINUTE.ordinal()] = 7;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.SECOND_OF_DAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MINUTE_OF_HOUR.ordinal()] = 9;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MINUTE_OF_DAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.HOUR_OF_AMPM.ordinal()] = 11;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.CLOCK_HOUR_OF_AMPM.ordinal()] = 12;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.HOUR_OF_DAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.CLOCK_HOUR_OF_DAY.ordinal()] = 14;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.AMPM_OF_DAY.ordinal()] = 15;
            } catch (NoSuchFieldError unused22) {
            }
        }
    }

    @Override // j$.time.temporal.Temporal
    public LocalTime plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) temporalUnit).ordinal()]) {
                case 1:
                    return plusNanos(j);
                case 2:
                    return plusNanos((j % 86400000000L) * 1000);
                case 3:
                    return plusNanos((j % 86400000) * 1000000);
                case 4:
                    return plusSeconds(j);
                case 5:
                    return plusMinutes(j);
                case 6:
                    return plusHours(j);
                case 7:
                    return plusHours((j % 2) * 12);
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
        }
        return (LocalTime) temporalUnit.addTo(this, j);
    }

    public LocalTime plusHours(long j) {
        return j == 0 ? this : create(((((int) (j % 24)) + this.hour) + 24) % 24, this.minute, this.second, this.nano);
    }

    public LocalTime plusMinutes(long j) {
        if (j != 0) {
            int i = (this.hour * 60) + this.minute;
            int i2 = ((((int) (j % 1440)) + i) + 1440) % 1440;
            if (i != i2) {
                return create(i2 / 60, i2 % 60, this.second, this.nano);
            }
        }
        return this;
    }

    public LocalTime plusSeconds(long j) {
        if (j != 0) {
            int i = (this.hour * BSON.NUMBER_INT) + (this.minute * 60) + this.second;
            int i2 = ((((int) (j % 86400)) + i) + 86400) % 86400;
            if (i != i2) {
                return create(i2 / 3600, (i2 / 60) % 60, i2 % 60, this.nano);
            }
        }
        return this;
    }

    public LocalTime plusNanos(long j) {
        if (j != 0) {
            long nanoOfDay = toNanoOfDay();
            long j2 = (((j % 86400000000000L) + nanoOfDay) + 86400000000000L) % 86400000000000L;
            if (nanoOfDay != j2) {
                return create((int) (j2 / 3600000000000L), (int) ((j2 / 60000000000L) % 60), (int) ((j2 / 1000000000) % 60), (int) (j2 % 1000000000));
            }
        }
        return this;
    }

    @Override // j$.time.temporal.Temporal
    public LocalTime minus(long j, TemporalUnit temporalUnit) {
        return j == Long.MIN_VALUE ? plus(Long.MAX_VALUE, temporalUnit).plus(1L, temporalUnit) : plus(-j, temporalUnit);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object query(TemporalQuery temporalQuery) {
        if (temporalQuery == TemporalQueries.chronology() || temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset()) {
            return null;
        }
        if (temporalQuery == TemporalQueries.localTime()) {
            return this;
        }
        if (temporalQuery == TemporalQueries.localDate()) {
            return null;
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return ChronoUnit.NANOS;
        }
        return temporalQuery.queryFrom(this);
    }

    @Override // j$.time.temporal.TemporalAdjuster
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.NANO_OF_DAY, toNanoOfDay());
    }

    public LocalDateTime atDate(LocalDate localDate) {
        return LocalDateTime.of(localDate, this);
    }

    public OffsetTime atOffset(ZoneOffset zoneOffset) {
        return OffsetTime.of(this, zoneOffset);
    }

    public int toSecondOfDay() {
        return (this.hour * BSON.NUMBER_INT) + (this.minute * 60) + this.second;
    }

    public long toNanoOfDay() {
        return (((long) this.hour) * 3600000000000L) + (((long) this.minute) * 60000000000L) + (((long) this.second) * 1000000000) + ((long) this.nano);
    }

    @Override // java.lang.Comparable
    public int compareTo(LocalTime localTime) {
        int iCompare = Integer.compare(this.hour, localTime.hour);
        return (iCompare == 0 && (iCompare = Integer.compare(this.minute, localTime.minute)) == 0 && (iCompare = Integer.compare(this.second, localTime.second)) == 0) ? Integer.compare(this.nano, localTime.nano) : iCompare;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalTime) {
            LocalTime localTime = (LocalTime) obj;
            if (this.hour == localTime.hour && this.minute == localTime.minute && this.second == localTime.second && this.nano == localTime.nano) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        long nanoOfDay = toNanoOfDay();
        return (int) (nanoOfDay ^ (nanoOfDay >>> 32));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(18);
        byte b = this.hour;
        byte b2 = this.minute;
        byte b3 = this.second;
        int i = this.nano;
        sb.append(b < 10 ? GlobalVariable.ILLUMINATION : "");
        sb.append((int) b);
        sb.append(b2 < 10 ? ":0" : ":");
        sb.append((int) b2);
        if (b3 > 0 || i > 0) {
            sb.append(b3 < 10 ? ":0" : ":");
            sb.append((int) b3);
            if (i > 0) {
                sb.append('.');
                if (i % DurationKt.NANOS_IN_MILLIS == 0) {
                    sb.append(Integer.toString((i / DurationKt.NANOS_IN_MILLIS) + 1000).substring(1));
                } else if (i % 1000 == 0) {
                    sb.append(Integer.toString((i / 1000) + DurationKt.NANOS_IN_MILLIS).substring(1));
                } else {
                    sb.append(Integer.toString(i + 1000000000).substring(1));
                }
            }
        }
        return sb.toString();
    }

    private Object writeReplace() {
        return new Ser((byte) 4, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) {
        if (this.nano == 0) {
            if (this.second == 0) {
                if (this.minute == 0) {
                    dataOutput.writeByte(~this.hour);
                    return;
                } else {
                    dataOutput.writeByte(this.hour);
                    dataOutput.writeByte(~this.minute);
                    return;
                }
            }
            dataOutput.writeByte(this.hour);
            dataOutput.writeByte(this.minute);
            dataOutput.writeByte(~this.second);
            return;
        }
        dataOutput.writeByte(this.hour);
        dataOutput.writeByte(this.minute);
        dataOutput.writeByte(this.second);
        dataOutput.writeInt(this.nano);
    }

    static LocalTime readExternal(DataInput dataInput) throws IOException {
        int i;
        int i2;
        int i3 = dataInput.readByte();
        int i4 = 0;
        if (i3 < 0) {
            i3 = ~i3;
            i2 = 0;
            i = 0;
        } else {
            byte b = dataInput.readByte();
            if (b < 0) {
                int i5 = ~b;
                i = 0;
                i4 = i5;
                i2 = 0;
            } else {
                byte b2 = dataInput.readByte();
                if (b2 < 0) {
                    i2 = ~b2;
                    i = 0;
                    i4 = b;
                } else {
                    i = dataInput.readInt();
                    i4 = b;
                    i2 = b2;
                }
            }
        }
        return of(i3, i4, i2, i);
    }
}

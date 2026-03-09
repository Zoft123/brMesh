package j$.time;

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

/* JADX INFO: loaded from: classes3.dex */
public final class Instant implements Temporal, TemporalAdjuster, Comparable<Instant>, Serializable {
    private static final long serialVersionUID = -665713676816604388L;
    private final int nanos;
    private final long seconds;
    public static final Instant EPOCH = new Instant(0, 0);
    public static final Instant MIN = ofEpochSecond(-31557014167219200L, 0);
    public static final Instant MAX = ofEpochSecond(31556889864403199L, 999999999);

    public static Instant now() {
        return Clock.systemUTC().instant();
    }

    public static Instant ofEpochSecond(long j) {
        return create(j, 0);
    }

    public static Instant ofEpochSecond(long j, long j2) {
        return create(Clock$OffsetClock$$ExternalSyntheticBackport0.m(j, DesugarLocalDate$$ExternalSyntheticBackport0.m(j2, 1000000000L)), (int) Clock$TickClock$$ExternalSyntheticBackport0.m(j2, 1000000000L));
    }

    public static Instant ofEpochMilli(long j) {
        return create(DesugarLocalDate$$ExternalSyntheticBackport0.m(j, 1000), DesugarLocalTime$$ExternalSyntheticBackport0.m(j, 1000) * DurationKt.NANOS_IN_MILLIS);
    }

    public static Instant from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Instant) {
            return (Instant) temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        try {
            return ofEpochSecond(temporalAccessor.getLong(ChronoField.INSTANT_SECONDS), temporalAccessor.get(ChronoField.NANO_OF_SECOND));
        } catch (DateTimeException e) {
            throw new DateTimeException("Unable to obtain Instant from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName(), e);
        }
    }

    public static Instant parse(CharSequence charSequence) {
        return (Instant) DateTimeFormatter.ISO_INSTANT.parse(charSequence, new TemporalQuery() { // from class: j$.time.Instant$$ExternalSyntheticLambda1
            @Override // j$.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return Instant.from(temporalAccessor);
            }
        });
    }

    private static Instant create(long j, int i) {
        if ((((long) i) | j) == 0) {
            return EPOCH;
        }
        if (j < -31557014167219200L || j > 31556889864403199L) {
            throw new DateTimeException("Instant exceeds minimum or maximum instant");
        }
        return new Instant(j, i);
    }

    private Instant(long j, int i) {
        this.seconds = j;
        this.nanos = i;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        return temporalField instanceof ChronoField ? temporalField == ChronoField.INSTANT_SECONDS || temporalField == ChronoField.NANO_OF_SECOND || temporalField == ChronoField.MICRO_OF_SECOND || temporalField == ChronoField.MILLI_OF_SECOND : temporalField != null && temporalField.isSupportedBy(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.CC.$default$range(this, temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
            if (i == 1) {
                return this.nanos;
            }
            if (i == 2) {
                return this.nanos / 1000;
            }
            if (i == 3) {
                return this.nanos / DurationKt.NANOS_IN_MILLIS;
            }
            if (i == 4) {
                ChronoField.INSTANT_SECONDS.checkValidIntValue(this.seconds);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return range(temporalField).checkValidIntValue(temporalField.getFrom(this), temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        int i;
        if (temporalField instanceof ChronoField) {
            int i2 = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
            if (i2 == 1) {
                i = this.nanos;
            } else if (i2 == 2) {
                i = this.nanos / 1000;
            } else {
                if (i2 != 3) {
                    if (i2 == 4) {
                        return this.seconds;
                    }
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
                }
                i = this.nanos / DurationKt.NANOS_IN_MILLIS;
            }
            return i;
        }
        return temporalField.getFrom(this);
    }

    public long getEpochSecond() {
        return this.seconds;
    }

    public int getNano() {
        return this.nanos;
    }

    @Override // j$.time.temporal.Temporal
    public Instant with(TemporalAdjuster temporalAdjuster) {
        return (Instant) temporalAdjuster.adjustInto(this);
    }

    @Override // j$.time.temporal.Temporal
    public Instant with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            chronoField.checkValidValue(j);
            int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    int i2 = ((int) j) * 1000;
                    if (i2 != this.nanos) {
                        return create(this.seconds, i2);
                    }
                } else if (i == 3) {
                    int i3 = ((int) j) * DurationKt.NANOS_IN_MILLIS;
                    if (i3 != this.nanos) {
                        return create(this.seconds, i3);
                    }
                } else {
                    if (i != 4) {
                        throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
                    }
                    if (j != this.seconds) {
                        return create(j, this.nanos);
                    }
                }
            } else if (j != this.nanos) {
                return create(this.seconds, (int) j);
            }
            return this;
        }
        return (Instant) temporalField.adjustInto(this, j);
    }

    /* JADX INFO: renamed from: j$.time.Instant$1, reason: invalid class name */
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
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.DAYS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr2;
            try {
                iArr2[ChronoField.NANO_OF_SECOND.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MICRO_OF_SECOND.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MILLI_OF_SECOND.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.INSTANT_SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    @Override // j$.time.temporal.Temporal
    public Instant plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) temporalUnit).ordinal()]) {
                case 1:
                    return plusNanos(j);
                case 2:
                    return plus(j / 1000000, (j % 1000000) * 1000);
                case 3:
                    return plusMillis(j);
                case 4:
                    return plusSeconds(j);
                case 5:
                    return plusSeconds(Duration$$ExternalSyntheticBackport1.m(j, 60));
                case 6:
                    return plusSeconds(Duration$$ExternalSyntheticBackport1.m(j, 3600));
                case 7:
                    return plusSeconds(Duration$$ExternalSyntheticBackport1.m(j, 43200));
                case 8:
                    return plusSeconds(Duration$$ExternalSyntheticBackport1.m(j, 86400));
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
        }
        return (Instant) temporalUnit.addTo(this, j);
    }

    public Instant plusSeconds(long j) {
        return plus(j, 0L);
    }

    public Instant plusMillis(long j) {
        return plus(j / 1000, (j % 1000) * 1000000);
    }

    public Instant plusNanos(long j) {
        return plus(0L, j);
    }

    private Instant plus(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return ofEpochSecond(Clock$OffsetClock$$ExternalSyntheticBackport0.m(Clock$OffsetClock$$ExternalSyntheticBackport0.m(this.seconds, j), j2 / 1000000000), ((long) this.nanos) + (j2 % 1000000000));
    }

    @Override // j$.time.temporal.Temporal
    public Instant minus(long j, TemporalUnit temporalUnit) {
        return j == Long.MIN_VALUE ? plus(Long.MAX_VALUE, temporalUnit).plus(1L, temporalUnit) : plus(-j, temporalUnit);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object query(TemporalQuery temporalQuery) {
        if (temporalQuery == TemporalQueries.precision()) {
            return ChronoUnit.NANOS;
        }
        if (temporalQuery == TemporalQueries.chronology() || temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset() || temporalQuery == TemporalQueries.localDate() || temporalQuery == TemporalQueries.localTime()) {
            return null;
        }
        return temporalQuery.queryFrom(this);
    }

    @Override // j$.time.temporal.TemporalAdjuster
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.INSTANT_SECONDS, this.seconds).with(ChronoField.NANO_OF_SECOND, this.nanos);
    }

    public OffsetDateTime atOffset(ZoneOffset zoneOffset) {
        return OffsetDateTime.ofInstant(this, zoneOffset);
    }

    public ZonedDateTime atZone(ZoneId zoneId) {
        return ZonedDateTime.ofInstant(this, zoneId);
    }

    public long toEpochMilli() {
        long j = this.seconds;
        if (j < 0 && this.nanos > 0) {
            return Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(j + 1, 1000), (this.nanos / DurationKt.NANOS_IN_MILLIS) - 1000);
        }
        return Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(j, 1000), this.nanos / DurationKt.NANOS_IN_MILLIS);
    }

    @Override // java.lang.Comparable
    public int compareTo(Instant instant) {
        int iCompare = Long.compare(this.seconds, instant.seconds);
        return iCompare != 0 ? iCompare : this.nanos - instant.nanos;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Instant) {
            Instant instant = (Instant) obj;
            if (this.seconds == instant.seconds && this.nanos == instant.nanos) {
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
        return DateTimeFormatter.ISO_INSTANT.format(this);
    }

    private Object writeReplace() {
        return new Ser((byte) 2, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.seconds);
        dataOutput.writeInt(this.nanos);
    }

    static Instant readExternal(DataInput dataInput) {
        return ofEpochSecond(dataInput.readLong(), dataInput.readInt());
    }
}

package j$.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
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
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public final class OffsetTime implements Temporal, TemporalAdjuster, Comparable<OffsetTime>, Serializable {
    private static final long serialVersionUID = 7264499704384272492L;
    private final ZoneOffset offset;
    private final LocalTime time;
    public static final OffsetTime MIN = LocalTime.MIN.atOffset(ZoneOffset.MAX);
    public static final OffsetTime MAX = LocalTime.MAX.atOffset(ZoneOffset.MIN);

    public static OffsetTime of(LocalTime localTime, ZoneOffset zoneOffset) {
        return new OffsetTime(localTime, zoneOffset);
    }

    public static OffsetTime from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof OffsetTime) {
            return (OffsetTime) temporalAccessor;
        }
        try {
            return new OffsetTime(LocalTime.from(temporalAccessor), ZoneOffset.from(temporalAccessor));
        } catch (DateTimeException e) {
            throw new DateTimeException("Unable to obtain OffsetTime from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName(), e);
        }
    }

    public static OffsetTime parse(CharSequence charSequence) {
        return parse(charSequence, DateTimeFormatter.ISO_OFFSET_TIME);
    }

    public static OffsetTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (OffsetTime) dateTimeFormatter.parse(charSequence, new TemporalQuery() { // from class: j$.time.OffsetTime$$ExternalSyntheticLambda0
            @Override // j$.time.temporal.TemporalQuery
            public final Object queryFrom(TemporalAccessor temporalAccessor) {
                return OffsetTime.from(temporalAccessor);
            }
        });
    }

    private OffsetTime(LocalTime localTime, ZoneOffset zoneOffset) {
        this.time = (LocalTime) Objects.requireNonNull(localTime, "time");
        this.offset = (ZoneOffset) Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
    }

    private OffsetTime with(LocalTime localTime, ZoneOffset zoneOffset) {
        return (this.time == localTime && this.offset.equals(zoneOffset)) ? this : new OffsetTime(localTime, zoneOffset);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        return temporalField instanceof ChronoField ? temporalField.isTimeBased() || temporalField == ChronoField.OFFSET_SECONDS : temporalField != null && temporalField.isSupportedBy(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.OFFSET_SECONDS) {
                return temporalField.range();
            }
            return this.time.range(temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int get(TemporalField temporalField) {
        return TemporalAccessor.CC.$default$get(this, temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.OFFSET_SECONDS) {
                return this.offset.getTotalSeconds();
            }
            return this.time.getLong(temporalField);
        }
        return temporalField.getFrom(this);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalTime) {
            return with((LocalTime) temporalAdjuster, this.offset);
        }
        if (temporalAdjuster instanceof ZoneOffset) {
            return with(this.time, (ZoneOffset) temporalAdjuster);
        }
        if (temporalAdjuster instanceof OffsetTime) {
            return (OffsetTime) temporalAdjuster;
        }
        return (OffsetTime) temporalAdjuster.adjustInto(this);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.OFFSET_SECONDS) {
                return with(this.time, ZoneOffset.ofTotalSeconds(((ChronoField) temporalField).checkValidIntValue(j)));
            }
            return with(this.time.with(temporalField, j), this.offset);
        }
        return (OffsetTime) temporalField.adjustInto(this, j);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return with(this.time.plus(j, temporalUnit), this.offset);
        }
        return (OffsetTime) temporalUnit.addTo(this, j);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime minus(long j, TemporalUnit temporalUnit) {
        return j == Long.MIN_VALUE ? plus(Long.MAX_VALUE, temporalUnit).plus(1L, temporalUnit) : plus(-j, temporalUnit);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object query(TemporalQuery temporalQuery) {
        if (temporalQuery == TemporalQueries.offset() || temporalQuery == TemporalQueries.zone()) {
            return this.offset;
        }
        if (((temporalQuery == TemporalQueries.zoneId()) || (temporalQuery == TemporalQueries.chronology())) || temporalQuery == TemporalQueries.localDate()) {
            return null;
        }
        if (temporalQuery == TemporalQueries.localTime()) {
            return this.time;
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return ChronoUnit.NANOS;
        }
        return temporalQuery.queryFrom(this);
    }

    @Override // j$.time.temporal.TemporalAdjuster
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.NANO_OF_DAY, this.time.toNanoOfDay()).with(ChronoField.OFFSET_SECONDS, this.offset.getTotalSeconds());
    }

    private long toEpochNano() {
        return this.time.toNanoOfDay() - (((long) this.offset.getTotalSeconds()) * 1000000000);
    }

    @Override // java.lang.Comparable
    public int compareTo(OffsetTime offsetTime) {
        if (this.offset.equals(offsetTime.offset)) {
            return this.time.compareTo(offsetTime.time);
        }
        int iCompare = Long.compare(toEpochNano(), offsetTime.toEpochNano());
        return iCompare == 0 ? this.time.compareTo(offsetTime.time) : iCompare;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OffsetTime) {
            OffsetTime offsetTime = (OffsetTime) obj;
            if (this.time.equals(offsetTime.time) && this.offset.equals(offsetTime.offset)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.time.hashCode() ^ this.offset.hashCode();
    }

    public String toString() {
        return this.time.toString() + this.offset.toString();
    }

    private Object writeReplace() {
        return new Ser((byte) 9, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(ObjectOutput objectOutput) {
        this.time.writeExternal(objectOutput);
        this.offset.writeExternal(objectOutput);
    }

    static OffsetTime readExternal(ObjectInput objectInput) {
        return of(LocalTime.readExternal(objectInput), ZoneOffset.readExternal(objectInput));
    }
}

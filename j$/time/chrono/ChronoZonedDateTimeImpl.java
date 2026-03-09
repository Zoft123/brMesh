package j$.time.chrono;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.Instant;
import j$.time.LocalDateTime;
import j$.time.LocalTime;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.ValueRange;
import j$.time.zone.ZoneOffsetTransition;
import j$.time.zone.ZoneRules;
import j$.util.Objects;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
final class ChronoZonedDateTimeImpl implements ChronoZonedDateTime, Serializable {
    private static final long serialVersionUID = -5261813987200935591L;
    private final transient ChronoLocalDateTimeImpl dateTime;
    private final transient ZoneOffset offset;
    private final transient ZoneId zone;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ int compareTo(ChronoZonedDateTime chronoZonedDateTime) {
        return ChronoZonedDateTime.CC.$default$compareTo((ChronoZonedDateTime) this, chronoZonedDateTime);
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(ChronoZonedDateTime<?> chronoZonedDateTime) {
        return compareTo((ChronoZonedDateTime) chronoZonedDateTime);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ int get(TemporalField temporalField) {
        return ChronoZonedDateTime.CC.$default$get(this, temporalField);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ Chronology getChronology() {
        return toLocalDate().getChronology();
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ long getLong(TemporalField temporalField) {
        return ChronoZonedDateTime.CC.$default$getLong(this, temporalField);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime, j$.time.temporal.Temporal
    public /* synthetic */ ChronoZonedDateTime minus(long j, TemporalUnit temporalUnit) {
        return ensureValid(getChronology(), Temporal.CC.$default$minus(this, j, temporalUnit));
    }

    @Override // j$.time.temporal.Temporal
    public /* bridge */ /* synthetic */ Temporal minus(long j, TemporalUnit temporalUnit) {
        return minus(j, temporalUnit);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return ChronoZonedDateTime.CC.$default$query(this, temporalQuery);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return ChronoZonedDateTime.CC.$default$range(this, temporalField);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ long toEpochSecond() {
        return ChronoZonedDateTime.CC.$default$toEpochSecond(this);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ ChronoLocalDate toLocalDate() {
        return toLocalDateTime().toLocalDate();
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ LocalTime toLocalTime() {
        return toLocalDateTime().toLocalTime();
    }

    @Override // j$.time.chrono.ChronoZonedDateTime, j$.time.temporal.Temporal
    public /* synthetic */ ChronoZonedDateTime with(TemporalAdjuster temporalAdjuster) {
        return ensureValid(getChronology(), temporalAdjuster.adjustInto(this));
    }

    @Override // j$.time.temporal.Temporal
    public /* bridge */ /* synthetic */ Temporal with(TemporalAdjuster temporalAdjuster) {
        return with(temporalAdjuster);
    }

    static ChronoZonedDateTime ofBest(ChronoLocalDateTimeImpl chronoLocalDateTimeImpl, ZoneId zoneId, ZoneOffset zoneOffset) {
        Objects.requireNonNull(chronoLocalDateTimeImpl, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ChronoZonedDateTimeImpl(chronoLocalDateTimeImpl, (ZoneOffset) zoneId, zoneId);
        }
        ZoneRules rules = zoneId.getRules();
        LocalDateTime localDateTimeFrom = LocalDateTime.from(chronoLocalDateTimeImpl);
        List validOffsets = rules.getValidOffsets(localDateTimeFrom);
        if (validOffsets.size() == 1) {
            zoneOffset = (ZoneOffset) validOffsets.get(0);
        } else if (validOffsets.size() == 0) {
            ZoneOffsetTransition transition = rules.getTransition(localDateTimeFrom);
            chronoLocalDateTimeImpl = chronoLocalDateTimeImpl.plusSeconds(transition.getDuration().getSeconds());
            zoneOffset = transition.getOffsetAfter();
        } else if (zoneOffset == null || !validOffsets.contains(zoneOffset)) {
            zoneOffset = (ZoneOffset) validOffsets.get(0);
        }
        Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
        return new ChronoZonedDateTimeImpl(chronoLocalDateTimeImpl, zoneOffset, zoneId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ChronoZonedDateTimeImpl ofInstant(Chronology chronology, Instant instant, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        Objects.requireNonNull(offset, TypedValues.CycleType.S_WAVE_OFFSET);
        return new ChronoZonedDateTimeImpl((ChronoLocalDateTimeImpl) chronology.localDateTime(LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), offset)), offset, zoneId);
    }

    private ChronoZonedDateTimeImpl create(Instant instant, ZoneId zoneId) {
        return ofInstant(getChronology(), instant, zoneId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ChronoZonedDateTimeImpl ensureValid(Chronology chronology, Temporal temporal) {
        ChronoZonedDateTimeImpl chronoZonedDateTimeImpl = (ChronoZonedDateTimeImpl) temporal;
        if (chronology.equals(chronoZonedDateTimeImpl.getChronology())) {
            return chronoZonedDateTimeImpl;
        }
        throw new ClassCastException("Chronology mismatch, required: " + chronology.getId() + ", actual: " + chronoZonedDateTimeImpl.getChronology().getId());
    }

    private ChronoZonedDateTimeImpl(ChronoLocalDateTimeImpl chronoLocalDateTimeImpl, ZoneOffset zoneOffset, ZoneId zoneId) {
        this.dateTime = (ChronoLocalDateTimeImpl) Objects.requireNonNull(chronoLocalDateTimeImpl, "dateTime");
        this.offset = (ZoneOffset) Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
        this.zone = (ZoneId) Objects.requireNonNull(zoneId, "zone");
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ZoneOffset getOffset() {
        return this.offset;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ChronoLocalDateTime toLocalDateTime() {
        return this.dateTime;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ZoneId getZone() {
        return this.zone;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ChronoZonedDateTime withZoneSameLocal(ZoneId zoneId) {
        return ofBest(this.dateTime, zoneId, this.offset);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return true;
        }
        return temporalField != null && temporalField.isSupportedBy(this);
    }

    @Override // j$.time.temporal.Temporal
    public ChronoZonedDateTime with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (i == 1) {
                return plus(j - toEpochSecond(), (TemporalUnit) ChronoUnit.SECONDS);
            }
            if (i == 2) {
                return create(this.dateTime.toInstant(ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(j))), this.zone);
            }
            return ofBest(this.dateTime.with(temporalField, j), this.zone, this.offset);
        }
        return ensureValid(getChronology(), temporalField.adjustInto(this, j));
    }

    /* JADX INFO: renamed from: j$.time.chrono.ChronoZonedDateTimeImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoField;

        static {
            int[] iArr = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr;
            try {
                iArr[ChronoField.INSTANT_SECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.OFFSET_SECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // j$.time.temporal.Temporal
    public ChronoZonedDateTime plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return with((TemporalAdjuster) this.dateTime.plus(j, temporalUnit));
        }
        return ensureValid(getChronology(), temporalUnit.addTo(this, j));
    }

    private Object writeReplace() {
        return new Ser((byte) 3, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.dateTime);
        objectOutput.writeObject(this.offset);
        objectOutput.writeObject(this.zone);
    }

    static ChronoZonedDateTime readExternal(ObjectInput objectInput) {
        ChronoLocalDateTime chronoLocalDateTime = (ChronoLocalDateTime) objectInput.readObject();
        ZoneOffset zoneOffset = (ZoneOffset) objectInput.readObject();
        return chronoLocalDateTime.atZone(zoneOffset).withZoneSameLocal((ZoneId) objectInput.readObject());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoZonedDateTime) && compareTo((ChronoZonedDateTime) obj) == 0;
    }

    public int hashCode() {
        return (toLocalDateTime().hashCode() ^ getOffset().hashCode()) ^ Integer.rotateLeft(getZone().hashCode(), 3);
    }

    public String toString() {
        String str = toLocalDateTime().toString() + getOffset().toString();
        if (getOffset() == getZone()) {
            return str;
        }
        return str + "[" + getZone().toString() + "]";
    }
}

package j$.time.chrono;

import androidx.exifinterface.media.ExifInterface;
import j$.time.Clock;
import j$.time.DateTimeException;
import j$.time.DesugarLocalDate$$ExternalSyntheticBackport0;
import j$.time.DesugarLocalTime$$ExternalSyntheticBackport0;
import j$.time.Duration$DurationUnits$$ExternalSyntheticBackport0;
import j$.time.Instant;
import j$.time.Instant$$ExternalSyntheticBackport0;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.Month;
import j$.time.Year;
import j$.time.ZoneId;
import j$.time.ZonedDateTime;
import j$.time.format.ResolverStyle;
import j$.time.temporal.ChronoField;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class IsoChronology extends AbstractChronology implements Serializable {
    public static final IsoChronology INSTANCE = new IsoChronology();
    private static final long serialVersionUID = -1440403870442975015L;

    private IsoChronology() {
    }

    @Override // j$.time.chrono.Chronology
    public String getId() {
        return ExifInterface.TAG_RW2_ISO;
    }

    @Override // j$.time.chrono.Chronology
    public String getCalendarType() {
        return "iso8601";
    }

    @Override // j$.time.chrono.Chronology
    public LocalDate date(int i, int i2, int i3) {
        return LocalDate.of(i, i2, i3);
    }

    @Override // j$.time.chrono.Chronology
    public LocalDate dateYearDay(int i, int i2) {
        return LocalDate.ofYearDay(i, i2);
    }

    @Override // j$.time.chrono.Chronology
    public LocalDate dateEpochDay(long j) {
        return LocalDate.ofEpochDay(j);
    }

    @Override // j$.time.chrono.Chronology
    public LocalDate date(TemporalAccessor temporalAccessor) {
        return LocalDate.from(temporalAccessor);
    }

    @Override // j$.time.chrono.AbstractChronology, j$.time.chrono.Chronology
    public LocalDateTime localDateTime(TemporalAccessor temporalAccessor) {
        return LocalDateTime.from(temporalAccessor);
    }

    @Override // j$.time.chrono.AbstractChronology, j$.time.chrono.Chronology
    public ZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    @Override // j$.time.chrono.AbstractChronology
    public LocalDate dateNow() {
        return dateNow(Clock.systemDefaultZone());
    }

    public LocalDate dateNow(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return date((TemporalAccessor) LocalDate.now(clock));
    }

    @Override // j$.time.chrono.Chronology
    public boolean isLeapYear(long j) {
        if ((3 & j) == 0) {
            return j % 100 != 0 || j % 400 == 0;
        }
        return false;
    }

    @Override // j$.time.chrono.Chronology
    public int prolepticYear(Era era, int i) {
        if (era instanceof IsoEra) {
            return era == IsoEra.CE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be IsoEra");
    }

    @Override // j$.time.chrono.Chronology
    public IsoEra eraOf(int i) {
        return IsoEra.of(i);
    }

    @Override // j$.time.chrono.Chronology
    public List eras() {
        return Duration$DurationUnits$$ExternalSyntheticBackport0.m(IsoEra.values());
    }

    @Override // j$.time.chrono.AbstractChronology, j$.time.chrono.Chronology
    public LocalDate resolveDate(Map map, ResolverStyle resolverStyle) {
        return (LocalDate) super.resolveDate(map, resolverStyle);
    }

    @Override // j$.time.chrono.AbstractChronology
    void resolveProlepticMonth(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.PROLEPTIC_MONTH;
        Long l = (Long) map.remove(chronoField);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                chronoField.checkValidValue(l.longValue());
            }
            addFieldValue(map, ChronoField.MONTH_OF_YEAR, DesugarLocalTime$$ExternalSyntheticBackport0.m(l.longValue(), 12) + 1);
            addFieldValue(map, ChronoField.YEAR, DesugarLocalDate$$ExternalSyntheticBackport0.m(l.longValue(), 12));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.time.chrono.AbstractChronology
    public LocalDate resolveYearOfEra(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR_OF_ERA;
        Long l = (Long) map.remove(chronoField);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                chronoField.checkValidValue(l.longValue());
            }
            Long l2 = (Long) map.remove(ChronoField.ERA);
            if (l2 != null) {
                if (l2.longValue() == 1) {
                    addFieldValue(map, ChronoField.YEAR, l.longValue());
                    return null;
                }
                if (l2.longValue() == 0) {
                    addFieldValue(map, ChronoField.YEAR, Instant$$ExternalSyntheticBackport0.m(1L, l.longValue()));
                    return null;
                }
                throw new DateTimeException("Invalid value for era: " + l2);
            }
            ChronoField chronoField2 = ChronoField.YEAR;
            Long l3 = (Long) map.get(chronoField2);
            if (resolverStyle != ResolverStyle.STRICT) {
                addFieldValue(map, chronoField2, (l3 == null || l3.longValue() > 0) ? l.longValue() : Instant$$ExternalSyntheticBackport0.m(1L, l.longValue()));
                return null;
            }
            if (l3 != null) {
                long jLongValue = l3.longValue();
                long jLongValue2 = l.longValue();
                if (jLongValue <= 0) {
                    jLongValue2 = Instant$$ExternalSyntheticBackport0.m(1L, jLongValue2);
                }
                addFieldValue(map, chronoField2, jLongValue2);
                return null;
            }
            map.put(chronoField, l);
            return null;
        }
        ChronoField chronoField3 = ChronoField.ERA;
        if (!map.containsKey(chronoField3)) {
            return null;
        }
        chronoField3.checkValidValue(((Long) map.get(chronoField3)).longValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.time.chrono.AbstractChronology
    public LocalDate resolveYMD(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = chronoField.checkValidIntValue(((Long) map.remove(chronoField)).longValue());
        if (resolverStyle == ResolverStyle.LENIENT) {
            return LocalDate.of(iCheckValidIntValue, 1, 1).plusMonths(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L)).plusDays(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L));
        }
        ChronoField chronoField2 = ChronoField.MONTH_OF_YEAR;
        int iCheckValidIntValue2 = chronoField2.checkValidIntValue(((Long) map.remove(chronoField2)).longValue());
        ChronoField chronoField3 = ChronoField.DAY_OF_MONTH;
        int iCheckValidIntValue3 = chronoField3.checkValidIntValue(((Long) map.remove(chronoField3)).longValue());
        if (resolverStyle == ResolverStyle.SMART) {
            if (iCheckValidIntValue2 == 4 || iCheckValidIntValue2 == 6 || iCheckValidIntValue2 == 9 || iCheckValidIntValue2 == 11) {
                iCheckValidIntValue3 = Math.min(iCheckValidIntValue3, 30);
            } else if (iCheckValidIntValue2 == 2) {
                iCheckValidIntValue3 = Math.min(iCheckValidIntValue3, Month.FEBRUARY.length(Year.isLeap(iCheckValidIntValue)));
            }
        }
        return LocalDate.of(iCheckValidIntValue, iCheckValidIntValue2, iCheckValidIntValue3);
    }

    @Override // j$.time.chrono.Chronology
    public ValueRange range(ChronoField chronoField) {
        return chronoField.range();
    }

    @Override // j$.time.chrono.AbstractChronology
    Object writeReplace() {
        return super.writeReplace();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}

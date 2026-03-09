package j$.time.chrono;

import androidx.exifinterface.media.ExifInterface;
import j$.time.DateTimeException;
import j$.time.DayOfWeek;
import j$.time.Instant;
import j$.time.Instant$$ExternalSyntheticBackport0;
import j$.time.LocalDate$$ExternalSyntheticBackport0;
import j$.time.ZoneId;
import j$.time.chrono.Chronology;
import j$.time.format.ResolverStyle;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAdjusters;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalUnit;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractChronology implements Chronology {
    private static final ConcurrentHashMap CHRONOS_BY_ID = new ConcurrentHashMap();
    private static final ConcurrentHashMap CHRONOS_BY_TYPE = new ConcurrentHashMap();
    private static final Locale JAPANESE_CALENDAR_LOCALE = new Locale("ja", "JP", "JP");

    public abstract /* synthetic */ ChronoLocalDate dateNow();

    @Override // j$.time.chrono.Chronology
    public /* synthetic */ ChronoLocalDateTime localDateTime(TemporalAccessor temporalAccessor) {
        return Chronology.CC.$default$localDateTime(this, temporalAccessor);
    }

    @Override // j$.time.chrono.Chronology
    public /* synthetic */ ChronoZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId) {
        return ChronoZonedDateTimeImpl.ofInstant(this, instant, zoneId);
    }

    static Chronology registerChrono(Chronology chronology) {
        return registerChrono(chronology, chronology.getId());
    }

    static Chronology registerChrono(Chronology chronology, String str) {
        String calendarType;
        Chronology chronology2 = (Chronology) CHRONOS_BY_ID.putIfAbsent(str, chronology);
        if (chronology2 == null && (calendarType = chronology.getCalendarType()) != null) {
            CHRONOS_BY_TYPE.putIfAbsent(calendarType, chronology);
        }
        return chronology2;
    }

    private static boolean initCache() {
        if (CHRONOS_BY_ID.get(ExifInterface.TAG_RW2_ISO) != null) {
            return false;
        }
        registerChrono(HijrahChronology.INSTANCE);
        registerChrono(JapaneseChronology.INSTANCE);
        registerChrono(MinguoChronology.INSTANCE);
        registerChrono(ThaiBuddhistChronology.INSTANCE);
        for (AbstractChronology abstractChronology : ServiceLoader.load(AbstractChronology.class, null)) {
            if (!abstractChronology.getId().equals(ExifInterface.TAG_RW2_ISO)) {
                registerChrono(abstractChronology);
            }
        }
        registerChrono(IsoChronology.INSTANCE);
        return true;
    }

    static Chronology of(String str) {
        Objects.requireNonNull(str, "id");
        do {
            Chronology chronologyOf0 = of0(str);
            if (chronologyOf0 != null) {
                return chronologyOf0;
            }
        } while (initCache());
        for (Chronology chronology : ServiceLoader.load(Chronology.class)) {
            if (str.equals(chronology.getId()) || str.equals(chronology.getCalendarType())) {
                return chronology;
            }
        }
        throw new DateTimeException("Unknown chronology: " + str);
    }

    private static Chronology of0(String str) {
        Chronology chronology = (Chronology) CHRONOS_BY_ID.get(str);
        return chronology == null ? (Chronology) CHRONOS_BY_TYPE.get(str) : chronology;
    }

    protected AbstractChronology() {
    }

    @Override // j$.time.chrono.Chronology
    public ChronoLocalDate resolveDate(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.EPOCH_DAY;
        if (map.containsKey(chronoField)) {
            return dateEpochDay(((Long) map.remove(chronoField)).longValue());
        }
        resolveProlepticMonth(map, resolverStyle);
        ChronoLocalDate chronoLocalDateResolveYearOfEra = resolveYearOfEra(map, resolverStyle);
        if (chronoLocalDateResolveYearOfEra != null) {
            return chronoLocalDateResolveYearOfEra;
        }
        if (!map.containsKey(ChronoField.YEAR)) {
            return null;
        }
        if (map.containsKey(ChronoField.MONTH_OF_YEAR)) {
            if (map.containsKey(ChronoField.DAY_OF_MONTH)) {
                return resolveYMD(map, resolverStyle);
            }
            if (map.containsKey(ChronoField.ALIGNED_WEEK_OF_MONTH)) {
                if (map.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)) {
                    return resolveYMAA(map, resolverStyle);
                }
                if (map.containsKey(ChronoField.DAY_OF_WEEK)) {
                    return resolveYMAD(map, resolverStyle);
                }
            }
        }
        if (map.containsKey(ChronoField.DAY_OF_YEAR)) {
            return resolveYD(map, resolverStyle);
        }
        if (!map.containsKey(ChronoField.ALIGNED_WEEK_OF_YEAR)) {
            return null;
        }
        if (map.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)) {
            return resolveYAA(map, resolverStyle);
        }
        if (map.containsKey(ChronoField.DAY_OF_WEEK)) {
            return resolveYAD(map, resolverStyle);
        }
        return null;
    }

    void resolveProlepticMonth(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.PROLEPTIC_MONTH;
        Long l = (Long) map.remove(chronoField);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                chronoField.checkValidValue(l.longValue());
            }
            ChronoLocalDate chronoLocalDateWith = dateNow().with((TemporalField) ChronoField.DAY_OF_MONTH, 1L).with((TemporalField) chronoField, l.longValue());
            addFieldValue(map, ChronoField.MONTH_OF_YEAR, chronoLocalDateWith.get(r0));
            addFieldValue(map, ChronoField.YEAR, chronoLocalDateWith.get(r0));
        }
    }

    ChronoLocalDate resolveYearOfEra(Map map, ResolverStyle resolverStyle) {
        int iM;
        ChronoField chronoField = ChronoField.YEAR_OF_ERA;
        Long l = (Long) map.remove(chronoField);
        if (l != null) {
            Long l2 = (Long) map.remove(ChronoField.ERA);
            if (resolverStyle != ResolverStyle.LENIENT) {
                iM = range(chronoField).checkValidIntValue(l.longValue(), chronoField);
            } else {
                iM = LocalDate$$ExternalSyntheticBackport0.m(l.longValue());
            }
            if (l2 != null) {
                addFieldValue(map, ChronoField.YEAR, prolepticYear(eraOf(range(r2).checkValidIntValue(l2.longValue(), r2)), iM));
                return null;
            }
            ChronoField chronoField2 = ChronoField.YEAR;
            if (map.containsKey(chronoField2)) {
                addFieldValue(map, chronoField2, prolepticYear(dateYearDay(range(chronoField2).checkValidIntValue(((Long) map.get(chronoField2)).longValue(), chronoField2), 1).getEra(), iM));
                return null;
            }
            if (resolverStyle == ResolverStyle.STRICT) {
                map.put(chronoField, l);
                return null;
            }
            if (eras().isEmpty()) {
                addFieldValue(map, chronoField2, iM);
                return null;
            }
            addFieldValue(map, chronoField2, prolepticYear((Era) r9.get(r9.size() - 1), iM));
            return null;
        }
        ChronoField chronoField3 = ChronoField.ERA;
        if (!map.containsKey(chronoField3)) {
            return null;
        }
        range(chronoField3).checkValidValue(((Long) map.get(chronoField3)).longValue(), chronoField3);
        return null;
    }

    ChronoLocalDate resolveYMD(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = range(chronoField).checkValidIntValue(((Long) map.remove(chronoField)).longValue(), chronoField);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long jM = Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
            return date(iCheckValidIntValue, 1, 1).plus(jM, (TemporalUnit) ChronoUnit.MONTHS).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        ChronoField chronoField2 = ChronoField.MONTH_OF_YEAR;
        int iCheckValidIntValue2 = range(chronoField2).checkValidIntValue(((Long) map.remove(chronoField2)).longValue(), chronoField2);
        ChronoField chronoField3 = ChronoField.DAY_OF_MONTH;
        int iCheckValidIntValue3 = range(chronoField3).checkValidIntValue(((Long) map.remove(chronoField3)).longValue(), chronoField3);
        if (resolverStyle == ResolverStyle.SMART) {
            try {
                return date(iCheckValidIntValue, iCheckValidIntValue2, iCheckValidIntValue3);
            } catch (DateTimeException unused) {
                return date(iCheckValidIntValue, iCheckValidIntValue2, 1).with(TemporalAdjusters.lastDayOfMonth());
            }
        }
        return date(iCheckValidIntValue, iCheckValidIntValue2, iCheckValidIntValue3);
    }

    ChronoLocalDate resolveYD(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = range(chronoField).checkValidIntValue(((Long) map.remove(chronoField)).longValue(), chronoField);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return dateYearDay(iCheckValidIntValue, 1).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_YEAR)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        ChronoField chronoField2 = ChronoField.DAY_OF_YEAR;
        return dateYearDay(iCheckValidIntValue, range(chronoField2).checkValidIntValue(((Long) map.remove(chronoField2)).longValue(), chronoField2));
    }

    ChronoLocalDate resolveYMAA(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = range(chronoField).checkValidIntValue(((Long) map.remove(chronoField)).longValue(), chronoField);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long jM = Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
            return date(iCheckValidIntValue, 1, 1).plus(jM, (TemporalUnit) ChronoUnit.MONTHS).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), 1L), (TemporalUnit) ChronoUnit.WEEKS).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        ChronoField chronoField2 = ChronoField.MONTH_OF_YEAR;
        int iCheckValidIntValue2 = range(chronoField2).checkValidIntValue(((Long) map.remove(chronoField2)).longValue(), chronoField2);
        ChronoField chronoField3 = ChronoField.ALIGNED_WEEK_OF_MONTH;
        int iCheckValidIntValue3 = range(chronoField3).checkValidIntValue(((Long) map.remove(chronoField3)).longValue(), chronoField3);
        ChronoField chronoField4 = ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
        ChronoLocalDate chronoLocalDatePlus = date(iCheckValidIntValue, iCheckValidIntValue2, 1).plus(((iCheckValidIntValue3 - 1) * 7) + (range(chronoField4).checkValidIntValue(((Long) map.remove(chronoField4)).longValue(), chronoField4) - 1), (TemporalUnit) ChronoUnit.DAYS);
        if (resolverStyle != ResolverStyle.STRICT || chronoLocalDatePlus.get(chronoField2) == iCheckValidIntValue2) {
            return chronoLocalDatePlus;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
    }

    ChronoLocalDate resolveYMAD(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = range(chronoField).checkValidIntValue(((Long) map.remove(chronoField)).longValue(), chronoField);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return resolveAligned(date(iCheckValidIntValue, 1, 1), Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L), Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), 1L), Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_WEEK)).longValue(), 1L));
        }
        ChronoField chronoField2 = ChronoField.MONTH_OF_YEAR;
        int iCheckValidIntValue2 = range(chronoField2).checkValidIntValue(((Long) map.remove(chronoField2)).longValue(), chronoField2);
        ChronoField chronoField3 = ChronoField.ALIGNED_WEEK_OF_MONTH;
        int iCheckValidIntValue3 = range(chronoField3).checkValidIntValue(((Long) map.remove(chronoField3)).longValue(), chronoField3);
        ChronoField chronoField4 = ChronoField.DAY_OF_WEEK;
        ChronoLocalDate chronoLocalDateWith = date(iCheckValidIntValue, iCheckValidIntValue2, 1).plus((iCheckValidIntValue3 - 1) * 7, (TemporalUnit) ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(range(chronoField4).checkValidIntValue(((Long) map.remove(chronoField4)).longValue(), chronoField4))));
        if (resolverStyle != ResolverStyle.STRICT || chronoLocalDateWith.get(chronoField2) == iCheckValidIntValue2) {
            return chronoLocalDateWith;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
    }

    ChronoLocalDate resolveYAA(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = range(chronoField).checkValidIntValue(((Long) map.remove(chronoField)).longValue(), chronoField);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return dateYearDay(iCheckValidIntValue, 1).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), 1L), (TemporalUnit) ChronoUnit.WEEKS).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        ChronoField chronoField2 = ChronoField.ALIGNED_WEEK_OF_YEAR;
        int iCheckValidIntValue2 = range(chronoField2).checkValidIntValue(((Long) map.remove(chronoField2)).longValue(), chronoField2);
        ChronoField chronoField3 = ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
        ChronoLocalDate chronoLocalDatePlus = dateYearDay(iCheckValidIntValue, 1).plus(((iCheckValidIntValue2 - 1) * 7) + (range(chronoField3).checkValidIntValue(((Long) map.remove(chronoField3)).longValue(), chronoField3) - 1), (TemporalUnit) ChronoUnit.DAYS);
        if (resolverStyle != ResolverStyle.STRICT || chronoLocalDatePlus.get(chronoField) == iCheckValidIntValue) {
            return chronoLocalDatePlus;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
    }

    ChronoLocalDate resolveYAD(Map map, ResolverStyle resolverStyle) {
        ChronoField chronoField = ChronoField.YEAR;
        int iCheckValidIntValue = range(chronoField).checkValidIntValue(((Long) map.remove(chronoField)).longValue(), chronoField);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return resolveAligned(dateYearDay(iCheckValidIntValue, 1), 0L, Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), 1L), Instant$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_WEEK)).longValue(), 1L));
        }
        ChronoField chronoField2 = ChronoField.ALIGNED_WEEK_OF_YEAR;
        int iCheckValidIntValue2 = range(chronoField2).checkValidIntValue(((Long) map.remove(chronoField2)).longValue(), chronoField2);
        ChronoField chronoField3 = ChronoField.DAY_OF_WEEK;
        ChronoLocalDate chronoLocalDateWith = dateYearDay(iCheckValidIntValue, 1).plus((iCheckValidIntValue2 - 1) * 7, (TemporalUnit) ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(range(chronoField3).checkValidIntValue(((Long) map.remove(chronoField3)).longValue(), chronoField3))));
        if (resolverStyle != ResolverStyle.STRICT || chronoLocalDateWith.get(chronoField) == iCheckValidIntValue) {
            return chronoLocalDateWith;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
    }

    ChronoLocalDate resolveAligned(ChronoLocalDate chronoLocalDate, long j, long j2, long j3) {
        long j4;
        ChronoLocalDate chronoLocalDatePlus = chronoLocalDate.plus(j, (TemporalUnit) ChronoUnit.MONTHS);
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        ChronoLocalDate chronoLocalDatePlus2 = chronoLocalDatePlus.plus(j2, (TemporalUnit) chronoUnit);
        if (j3 > 7) {
            long j5 = j3 - 1;
            chronoLocalDatePlus2 = chronoLocalDatePlus2.plus(j5 / 7, (TemporalUnit) chronoUnit);
            j4 = j5 % 7;
        } else {
            if (j3 < 1) {
                chronoLocalDatePlus2 = chronoLocalDatePlus2.plus(Instant$$ExternalSyntheticBackport0.m(j3, 7L) / 7, (TemporalUnit) chronoUnit);
                j4 = (j3 + 6) % 7;
            }
            return chronoLocalDatePlus2.with(TemporalAdjusters.nextOrSame(DayOfWeek.of((int) j3)));
        }
        j3 = j4 + 1;
        return chronoLocalDatePlus2.with(TemporalAdjusters.nextOrSame(DayOfWeek.of((int) j3)));
    }

    void addFieldValue(Map map, ChronoField chronoField, long j) {
        Long l = (Long) map.get(chronoField);
        if (l != null && l.longValue() != j) {
            throw new DateTimeException("Conflict found: " + chronoField + " " + l + " differs from " + chronoField + " " + j);
        }
        map.put(chronoField, Long.valueOf(j));
    }

    @Override // java.lang.Comparable
    public int compareTo(Chronology chronology) {
        return getId().compareTo(chronology.getId());
    }

    @Override // j$.time.chrono.Chronology
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AbstractChronology) && compareTo((Chronology) obj) == 0;
    }

    @Override // j$.time.chrono.Chronology
    public int hashCode() {
        return getClass().hashCode() ^ getId().hashCode();
    }

    @Override // j$.time.chrono.Chronology
    public String toString() {
        return getId();
    }

    Object writeReplace() {
        return new Ser((byte) 1, this);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(getId());
    }

    static Chronology readExternal(DataInput dataInput) {
        return Chronology.CC.of(dataInput.readUTF());
    }
}

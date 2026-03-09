package j$.time.temporal;

import cn.com.broadlink.blelight.util.EListUtils;
import j$.time.Clock$OffsetClock$$ExternalSyntheticBackport0;
import j$.time.DateTimeException;
import j$.time.DayOfWeek;
import j$.time.Duration$$ExternalSyntheticBackport1;
import j$.time.Instant$$ExternalSyntheticBackport0;
import j$.time.LocalDate$$ExternalSyntheticBackport0;
import j$.time.chrono.ChronoLocalDate;
import j$.time.chrono.Chronology;
import j$.time.format.ResolverStyle;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
public final class WeekFields implements Serializable {
    private static final ConcurrentMap CACHE = new ConcurrentHashMap(4, 0.75f, 2);
    public static final WeekFields ISO = new WeekFields(DayOfWeek.MONDAY, 4);
    public static final WeekFields SUNDAY_START = of(DayOfWeek.SUNDAY, 1);
    public static final TemporalUnit WEEK_BASED_YEARS = IsoFields.WEEK_BASED_YEARS;
    private static final long serialVersionUID = -1177360819670808121L;
    private final DayOfWeek firstDayOfWeek;
    private final int minimalDays;
    private final transient TemporalField dayOfWeek = ComputedDayOfField.ofDayOfWeekField(this);
    private final transient TemporalField weekOfMonth = ComputedDayOfField.ofWeekOfMonthField(this);
    private final transient TemporalField weekOfYear = ComputedDayOfField.ofWeekOfYearField(this);
    private final transient TemporalField weekOfWeekBasedYear = ComputedDayOfField.ofWeekOfWeekBasedYearField(this);
    private final transient TemporalField weekBasedYear = ComputedDayOfField.ofWeekBasedYearField(this);

    public static WeekFields of(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        return of(DayOfWeek.SUNDAY.plus(r4.getFirstDayOfWeek() - 1), Calendar.getInstance(new Locale(locale.getLanguage(), locale.getCountry())).getMinimalDaysInFirstWeek());
    }

    public static WeekFields of(DayOfWeek dayOfWeek, int i) {
        String str = dayOfWeek.toString() + i;
        ConcurrentMap concurrentMap = CACHE;
        WeekFields weekFields = (WeekFields) concurrentMap.get(str);
        if (weekFields != null) {
            return weekFields;
        }
        concurrentMap.putIfAbsent(str, new WeekFields(dayOfWeek, i));
        return (WeekFields) concurrentMap.get(str);
    }

    private WeekFields(DayOfWeek dayOfWeek, int i) {
        Objects.requireNonNull(dayOfWeek, "firstDayOfWeek");
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Minimal number of days is invalid");
        }
        this.firstDayOfWeek = dayOfWeek;
        this.minimalDays = i;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.firstDayOfWeek == null) {
            throw new InvalidObjectException("firstDayOfWeek is null");
        }
        int i = this.minimalDays;
        if (i < 1 || i > 7) {
            throw new InvalidObjectException("Minimal number of days is invalid");
        }
    }

    private Object readResolve() throws InvalidObjectException {
        try {
            return of(this.firstDayOfWeek, this.minimalDays);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectException("Invalid serialized WeekFields: " + e.getMessage());
        }
    }

    public DayOfWeek getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public int getMinimalDaysInFirstWeek() {
        return this.minimalDays;
    }

    public TemporalField dayOfWeek() {
        return this.dayOfWeek;
    }

    public TemporalField weekOfMonth() {
        return this.weekOfMonth;
    }

    public TemporalField weekOfWeekBasedYear() {
        return this.weekOfWeekBasedYear;
    }

    public TemporalField weekBasedYear() {
        return this.weekBasedYear;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof WeekFields) && hashCode() == obj.hashCode();
    }

    public int hashCode() {
        return (this.firstDayOfWeek.ordinal() * 7) + this.minimalDays;
    }

    public String toString() {
        return "WeekFields[" + this.firstDayOfWeek + EListUtils.DEFAULT_JOIN_SEPARATOR + this.minimalDays + "]";
    }

    static class ComputedDayOfField implements TemporalField {
        private final TemporalUnit baseUnit;
        private final String name;
        private final ValueRange range;
        private final TemporalUnit rangeUnit;
        private final WeekFields weekDef;
        private static final ValueRange DAY_OF_WEEK_RANGE = ValueRange.of(1, 7);
        private static final ValueRange WEEK_OF_MONTH_RANGE = ValueRange.of(0, 1, 4, 6);
        private static final ValueRange WEEK_OF_YEAR_RANGE = ValueRange.of(0, 1, 52, 54);
        private static final ValueRange WEEK_OF_WEEK_BASED_YEAR_RANGE = ValueRange.of(1, 52, 53);

        @Override // j$.time.temporal.TemporalField
        public boolean isDateBased() {
            return true;
        }

        @Override // j$.time.temporal.TemporalField
        public boolean isTimeBased() {
            return false;
        }

        static ComputedDayOfField ofDayOfWeekField(WeekFields weekFields) {
            return new ComputedDayOfField("DayOfWeek", weekFields, ChronoUnit.DAYS, ChronoUnit.WEEKS, DAY_OF_WEEK_RANGE);
        }

        static ComputedDayOfField ofWeekOfMonthField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekOfMonth", weekFields, ChronoUnit.WEEKS, ChronoUnit.MONTHS, WEEK_OF_MONTH_RANGE);
        }

        static ComputedDayOfField ofWeekOfYearField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekOfYear", weekFields, ChronoUnit.WEEKS, ChronoUnit.YEARS, WEEK_OF_YEAR_RANGE);
        }

        static ComputedDayOfField ofWeekOfWeekBasedYearField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekOfWeekBasedYear", weekFields, ChronoUnit.WEEKS, IsoFields.WEEK_BASED_YEARS, WEEK_OF_WEEK_BASED_YEAR_RANGE);
        }

        static ComputedDayOfField ofWeekBasedYearField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekBasedYear", weekFields, IsoFields.WEEK_BASED_YEARS, ChronoUnit.FOREVER, ChronoField.YEAR.range());
        }

        private ChronoLocalDate ofWeekBasedYear(Chronology chronology, int i, int i2, int i3) {
            ChronoLocalDate chronoLocalDateDate = chronology.date(i, 1, 1);
            int iStartOfWeekOffset = startOfWeekOffset(1, localizedDayOfWeek(chronoLocalDateDate));
            return chronoLocalDateDate.plus((-iStartOfWeekOffset) + (i3 - 1) + ((Math.min(i2, computeWeek(iStartOfWeekOffset, chronoLocalDateDate.lengthOfYear() + this.weekDef.getMinimalDaysInFirstWeek()) - 1) - 1) * 7), (TemporalUnit) ChronoUnit.DAYS);
        }

        private ComputedDayOfField(String str, WeekFields weekFields, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, ValueRange valueRange) {
            this.name = str;
            this.weekDef = weekFields;
            this.baseUnit = temporalUnit;
            this.rangeUnit = temporalUnit2;
            this.range = valueRange;
        }

        @Override // j$.time.temporal.TemporalField
        public long getFrom(TemporalAccessor temporalAccessor) {
            int iLocalizedWeekBasedYear;
            TemporalUnit temporalUnit = this.rangeUnit;
            if (temporalUnit == ChronoUnit.WEEKS) {
                iLocalizedWeekBasedYear = localizedDayOfWeek(temporalAccessor);
            } else {
                if (temporalUnit == ChronoUnit.MONTHS) {
                    return localizedWeekOfMonth(temporalAccessor);
                }
                if (temporalUnit == ChronoUnit.YEARS) {
                    return localizedWeekOfYear(temporalAccessor);
                }
                if (temporalUnit == WeekFields.WEEK_BASED_YEARS) {
                    iLocalizedWeekBasedYear = localizedWeekOfWeekBasedYear(temporalAccessor);
                } else if (temporalUnit == ChronoUnit.FOREVER) {
                    iLocalizedWeekBasedYear = localizedWeekBasedYear(temporalAccessor);
                } else {
                    throw new IllegalStateException("unreachable, rangeUnit: " + this.rangeUnit + ", this: " + this);
                }
            }
            return iLocalizedWeekBasedYear;
        }

        private int localizedDayOfWeek(TemporalAccessor temporalAccessor) {
            return WeekFields$ComputedDayOfField$$ExternalSyntheticBackport0.m(temporalAccessor.get(ChronoField.DAY_OF_WEEK) - this.weekDef.getFirstDayOfWeek().getValue(), 7) + 1;
        }

        private int localizedDayOfWeek(int i) {
            return WeekFields$ComputedDayOfField$$ExternalSyntheticBackport0.m(i - this.weekDef.getFirstDayOfWeek().getValue(), 7) + 1;
        }

        private long localizedWeekOfMonth(TemporalAccessor temporalAccessor) {
            int iLocalizedDayOfWeek = localizedDayOfWeek(temporalAccessor);
            int i = temporalAccessor.get(ChronoField.DAY_OF_MONTH);
            return computeWeek(startOfWeekOffset(i, iLocalizedDayOfWeek), i);
        }

        private long localizedWeekOfYear(TemporalAccessor temporalAccessor) {
            int iLocalizedDayOfWeek = localizedDayOfWeek(temporalAccessor);
            int i = temporalAccessor.get(ChronoField.DAY_OF_YEAR);
            return computeWeek(startOfWeekOffset(i, iLocalizedDayOfWeek), i);
        }

        private int localizedWeekBasedYear(TemporalAccessor temporalAccessor) {
            int iLocalizedDayOfWeek = localizedDayOfWeek(temporalAccessor);
            int i = temporalAccessor.get(ChronoField.YEAR);
            ChronoField chronoField = ChronoField.DAY_OF_YEAR;
            int i2 = temporalAccessor.get(chronoField);
            int iStartOfWeekOffset = startOfWeekOffset(i2, iLocalizedDayOfWeek);
            int iComputeWeek = computeWeek(iStartOfWeekOffset, i2);
            return iComputeWeek == 0 ? i - 1 : iComputeWeek >= computeWeek(iStartOfWeekOffset, ((int) temporalAccessor.range(chronoField).getMaximum()) + this.weekDef.getMinimalDaysInFirstWeek()) ? i + 1 : i;
        }

        private int localizedWeekOfWeekBasedYear(TemporalAccessor temporalAccessor) {
            int iComputeWeek;
            int iLocalizedDayOfWeek = localizedDayOfWeek(temporalAccessor);
            ChronoField chronoField = ChronoField.DAY_OF_YEAR;
            int i = temporalAccessor.get(chronoField);
            int iStartOfWeekOffset = startOfWeekOffset(i, iLocalizedDayOfWeek);
            int iComputeWeek2 = computeWeek(iStartOfWeekOffset, i);
            if (iComputeWeek2 == 0) {
                return localizedWeekOfWeekBasedYear(Chronology.CC.from(temporalAccessor).date(temporalAccessor).minus(i, (TemporalUnit) ChronoUnit.DAYS));
            }
            return (iComputeWeek2 <= 50 || iComputeWeek2 < (iComputeWeek = computeWeek(iStartOfWeekOffset, ((int) temporalAccessor.range(chronoField).getMaximum()) + this.weekDef.getMinimalDaysInFirstWeek()))) ? iComputeWeek2 : (iComputeWeek2 - iComputeWeek) + 1;
        }

        private int startOfWeekOffset(int i, int i2) {
            int iM = WeekFields$ComputedDayOfField$$ExternalSyntheticBackport0.m(i - i2, 7);
            return iM + 1 > this.weekDef.getMinimalDaysInFirstWeek() ? 7 - iM : -iM;
        }

        private int computeWeek(int i, int i2) {
            return ((i + 7) + (i2 - 1)) / 7;
        }

        @Override // j$.time.temporal.TemporalField
        public Temporal adjustInto(Temporal temporal, long j) {
            if (this.range.checkValidIntValue(j, this) == temporal.get(this)) {
                return temporal;
            }
            if (this.rangeUnit == ChronoUnit.FOREVER) {
                return ofWeekBasedYear(Chronology.CC.from(temporal), (int) j, temporal.get(this.weekDef.weekOfWeekBasedYear), temporal.get(this.weekDef.dayOfWeek));
            }
            return temporal.plus(r0 - r1, this.baseUnit);
        }

        @Override // j$.time.temporal.TemporalField
        public ChronoLocalDate resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
            int iM = LocalDate$$ExternalSyntheticBackport0.m(((Long) map.get(this)).longValue());
            if (this.rangeUnit == ChronoUnit.WEEKS) {
                long jM = WeekFields$ComputedDayOfField$$ExternalSyntheticBackport0.m((this.weekDef.getFirstDayOfWeek().getValue() - 1) + (this.range.checkValidIntValue(r2, this) - 1), 7) + 1;
                map.remove(this);
                map.put(ChronoField.DAY_OF_WEEK, Long.valueOf(jM));
                return null;
            }
            ChronoField chronoField = ChronoField.DAY_OF_WEEK;
            if (!map.containsKey(chronoField)) {
                return null;
            }
            int iLocalizedDayOfWeek = localizedDayOfWeek(chronoField.checkValidIntValue(((Long) map.get(chronoField)).longValue()));
            Chronology chronologyFrom = Chronology.CC.from(temporalAccessor);
            ChronoField chronoField2 = ChronoField.YEAR;
            if (map.containsKey(chronoField2)) {
                int iCheckValidIntValue = chronoField2.checkValidIntValue(((Long) map.get(chronoField2)).longValue());
                if (this.rangeUnit == ChronoUnit.MONTHS) {
                    Object obj = ChronoField.MONTH_OF_YEAR;
                    if (map.containsKey(obj)) {
                        return resolveWoM(map, chronologyFrom, iCheckValidIntValue, ((Long) map.get(obj)).longValue(), iM, iLocalizedDayOfWeek, resolverStyle);
                    }
                }
                if (this.rangeUnit == ChronoUnit.YEARS) {
                    return resolveWoY(map, chronologyFrom, iCheckValidIntValue, iM, iLocalizedDayOfWeek, resolverStyle);
                }
            } else {
                TemporalUnit temporalUnit = this.rangeUnit;
                if ((temporalUnit == WeekFields.WEEK_BASED_YEARS || temporalUnit == ChronoUnit.FOREVER) && map.containsKey(this.weekDef.weekBasedYear) && map.containsKey(this.weekDef.weekOfWeekBasedYear)) {
                    return resolveWBY(map, chronologyFrom, iLocalizedDayOfWeek, resolverStyle);
                }
            }
            return null;
        }

        private ChronoLocalDate resolveWoM(Map map, Chronology chronology, int i, long j, long j2, int i2, ResolverStyle resolverStyle) {
            ChronoLocalDate chronoLocalDatePlus;
            if (resolverStyle == ResolverStyle.LENIENT) {
                ChronoLocalDate chronoLocalDatePlus2 = chronology.date(i, 1, 1).plus(Instant$$ExternalSyntheticBackport0.m(j, 1L), (TemporalUnit) ChronoUnit.MONTHS);
                chronoLocalDatePlus = chronoLocalDatePlus2.plus(Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(Instant$$ExternalSyntheticBackport0.m(j2, localizedWeekOfMonth(chronoLocalDatePlus2)), 7), i2 - localizedDayOfWeek(chronoLocalDatePlus2)), (TemporalUnit) ChronoUnit.DAYS);
            } else {
                ChronoField chronoField = ChronoField.MONTH_OF_YEAR;
                chronoLocalDatePlus = chronology.date(i, chronoField.checkValidIntValue(j), 1).plus((((int) (((long) this.range.checkValidIntValue(j2, this)) - localizedWeekOfMonth(r6))) * 7) + (i2 - localizedDayOfWeek(r6)), (TemporalUnit) ChronoUnit.DAYS);
                if (resolverStyle == ResolverStyle.STRICT && chronoLocalDatePlus.getLong(chronoField) != j) {
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
                }
            }
            map.remove(this);
            map.remove(ChronoField.YEAR);
            map.remove(ChronoField.MONTH_OF_YEAR);
            map.remove(ChronoField.DAY_OF_WEEK);
            return chronoLocalDatePlus;
        }

        private ChronoLocalDate resolveWoY(Map map, Chronology chronology, int i, long j, int i2, ResolverStyle resolverStyle) {
            ChronoLocalDate chronoLocalDatePlus;
            ChronoLocalDate chronoLocalDateDate = chronology.date(i, 1, 1);
            if (resolverStyle == ResolverStyle.LENIENT) {
                chronoLocalDatePlus = chronoLocalDateDate.plus(Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(Instant$$ExternalSyntheticBackport0.m(j, localizedWeekOfYear(chronoLocalDateDate)), 7), i2 - localizedDayOfWeek(chronoLocalDateDate)), (TemporalUnit) ChronoUnit.DAYS);
            } else {
                chronoLocalDatePlus = chronoLocalDateDate.plus((((int) (((long) this.range.checkValidIntValue(j, this)) - localizedWeekOfYear(chronoLocalDateDate))) * 7) + (i2 - localizedDayOfWeek(chronoLocalDateDate)), (TemporalUnit) ChronoUnit.DAYS);
                if (resolverStyle == ResolverStyle.STRICT && chronoLocalDatePlus.getLong(ChronoField.YEAR) != i) {
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
                }
            }
            map.remove(this);
            map.remove(ChronoField.YEAR);
            map.remove(ChronoField.DAY_OF_WEEK);
            return chronoLocalDatePlus;
        }

        private ChronoLocalDate resolveWBY(Map map, Chronology chronology, int i, ResolverStyle resolverStyle) {
            ChronoLocalDate chronoLocalDateOfWeekBasedYear;
            int iCheckValidIntValue = this.weekDef.weekBasedYear.range().checkValidIntValue(((Long) map.get(this.weekDef.weekBasedYear)).longValue(), this.weekDef.weekBasedYear);
            if (resolverStyle == ResolverStyle.LENIENT) {
                chronoLocalDateOfWeekBasedYear = ofWeekBasedYear(chronology, iCheckValidIntValue, 1, i).plus(Instant$$ExternalSyntheticBackport0.m(((Long) map.get(this.weekDef.weekOfWeekBasedYear)).longValue(), 1L), (TemporalUnit) ChronoUnit.WEEKS);
            } else {
                chronoLocalDateOfWeekBasedYear = ofWeekBasedYear(chronology, iCheckValidIntValue, this.weekDef.weekOfWeekBasedYear.range().checkValidIntValue(((Long) map.get(this.weekDef.weekOfWeekBasedYear)).longValue(), this.weekDef.weekOfWeekBasedYear), i);
                if (resolverStyle == ResolverStyle.STRICT && localizedWeekBasedYear(chronoLocalDateOfWeekBasedYear) != iCheckValidIntValue) {
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different week-based-year");
                }
            }
            map.remove(this);
            map.remove(this.weekDef.weekBasedYear);
            map.remove(this.weekDef.weekOfWeekBasedYear);
            map.remove(ChronoField.DAY_OF_WEEK);
            return chronoLocalDateOfWeekBasedYear;
        }

        @Override // j$.time.temporal.TemporalField
        public ValueRange range() {
            return this.range;
        }

        @Override // j$.time.temporal.TemporalField
        public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
            if (!temporalAccessor.isSupported(ChronoField.DAY_OF_WEEK)) {
                return false;
            }
            TemporalUnit temporalUnit = this.rangeUnit;
            if (temporalUnit == ChronoUnit.WEEKS) {
                return true;
            }
            if (temporalUnit == ChronoUnit.MONTHS) {
                return temporalAccessor.isSupported(ChronoField.DAY_OF_MONTH);
            }
            if (temporalUnit == ChronoUnit.YEARS) {
                return temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR);
            }
            if (temporalUnit == WeekFields.WEEK_BASED_YEARS) {
                return temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR);
            }
            if (temporalUnit == ChronoUnit.FOREVER) {
                return temporalAccessor.isSupported(ChronoField.YEAR);
            }
            return false;
        }

        @Override // j$.time.temporal.TemporalField
        public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
            TemporalUnit temporalUnit = this.rangeUnit;
            if (temporalUnit == ChronoUnit.WEEKS) {
                return this.range;
            }
            if (temporalUnit == ChronoUnit.MONTHS) {
                return rangeByWeek(temporalAccessor, ChronoField.DAY_OF_MONTH);
            }
            if (temporalUnit == ChronoUnit.YEARS) {
                return rangeByWeek(temporalAccessor, ChronoField.DAY_OF_YEAR);
            }
            if (temporalUnit == WeekFields.WEEK_BASED_YEARS) {
                return rangeWeekOfWeekBasedYear(temporalAccessor);
            }
            if (temporalUnit == ChronoUnit.FOREVER) {
                return ChronoField.YEAR.range();
            }
            throw new IllegalStateException("unreachable, rangeUnit: " + this.rangeUnit + ", this: " + this);
        }

        private ValueRange rangeByWeek(TemporalAccessor temporalAccessor, TemporalField temporalField) {
            int iStartOfWeekOffset = startOfWeekOffset(temporalAccessor.get(temporalField), localizedDayOfWeek(temporalAccessor));
            ValueRange valueRangeRange = temporalAccessor.range(temporalField);
            return ValueRange.of(computeWeek(iStartOfWeekOffset, (int) valueRangeRange.getMinimum()), computeWeek(iStartOfWeekOffset, (int) valueRangeRange.getMaximum()));
        }

        private ValueRange rangeWeekOfWeekBasedYear(TemporalAccessor temporalAccessor) {
            ChronoField chronoField = ChronoField.DAY_OF_YEAR;
            if (!temporalAccessor.isSupported(chronoField)) {
                return WEEK_OF_YEAR_RANGE;
            }
            int iLocalizedDayOfWeek = localizedDayOfWeek(temporalAccessor);
            int i = temporalAccessor.get(chronoField);
            int iStartOfWeekOffset = startOfWeekOffset(i, iLocalizedDayOfWeek);
            int iComputeWeek = computeWeek(iStartOfWeekOffset, i);
            if (iComputeWeek == 0) {
                return rangeWeekOfWeekBasedYear(Chronology.CC.from(temporalAccessor).date(temporalAccessor).minus(i + 7, (TemporalUnit) ChronoUnit.DAYS));
            }
            if (iComputeWeek >= computeWeek(iStartOfWeekOffset, this.weekDef.getMinimalDaysInFirstWeek() + ((int) temporalAccessor.range(chronoField).getMaximum()))) {
                return rangeWeekOfWeekBasedYear(Chronology.CC.from(temporalAccessor).date(temporalAccessor).plus((r0 - i) + 8, (TemporalUnit) ChronoUnit.DAYS));
            }
            return ValueRange.of(1L, r1 - 1);
        }

        public String toString() {
            return this.name + "[" + this.weekDef.toString() + "]";
        }
    }
}

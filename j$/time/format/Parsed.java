package j$.time.format;

import j$.time.Clock$OffsetClock$$ExternalSyntheticBackport0;
import j$.time.Clock$TickClock$$ExternalSyntheticBackport0;
import j$.time.DateTimeException;
import j$.time.DesugarLocalDate$$ExternalSyntheticBackport0;
import j$.time.Duration$$ExternalSyntheticBackport1;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalTime;
import j$.time.Period;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.chrono.ChronoLocalDate;
import j$.time.chrono.ChronoLocalDateTime;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.chrono.Chronology;
import j$.time.temporal.ChronoField;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.UnsupportedTemporalTypeException;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
final class Parsed implements TemporalAccessor {
    Chronology chrono;
    private ChronoLocalDate date;
    boolean leapSecond;
    private ResolverStyle resolverStyle;
    private LocalTime time;
    ZoneId zone;
    final Map fieldValues = new HashMap();
    Period excessDays = Period.ZERO;

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ int get(TemporalField temporalField) {
        return TemporalAccessor.CC.$default$get(this, temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.CC.$default$range(this, temporalField);
    }

    Parsed() {
    }

    Parsed copy() {
        Parsed parsed = new Parsed();
        parsed.fieldValues.putAll(this.fieldValues);
        parsed.zone = this.zone;
        parsed.chrono = this.chrono;
        parsed.leapSecond = this.leapSecond;
        return parsed;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        ChronoLocalDate chronoLocalDate;
        LocalTime localTime;
        if (this.fieldValues.containsKey(temporalField) || (((chronoLocalDate = this.date) != null && chronoLocalDate.isSupported(temporalField)) || ((localTime = this.time) != null && localTime.isSupported(temporalField)))) {
            return true;
        }
        return (temporalField == null || (temporalField instanceof ChronoField) || !temporalField.isSupportedBy(this)) ? false : true;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        Objects.requireNonNull(temporalField, "field");
        Long l = (Long) this.fieldValues.get(temporalField);
        if (l != null) {
            return l.longValue();
        }
        ChronoLocalDate chronoLocalDate = this.date;
        if (chronoLocalDate != null && chronoLocalDate.isSupported(temporalField)) {
            return this.date.getLong(temporalField);
        }
        LocalTime localTime = this.time;
        if (localTime != null && localTime.isSupported(temporalField)) {
            return this.time.getLong(temporalField);
        }
        if (temporalField instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return temporalField.getFrom(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object query(TemporalQuery temporalQuery) {
        if (temporalQuery == TemporalQueries.zoneId()) {
            return this.zone;
        }
        if (temporalQuery == TemporalQueries.chronology()) {
            return this.chrono;
        }
        if (temporalQuery == TemporalQueries.localDate()) {
            ChronoLocalDate chronoLocalDate = this.date;
            if (chronoLocalDate != null) {
                return LocalDate.from(chronoLocalDate);
            }
            return null;
        }
        if (temporalQuery == TemporalQueries.localTime()) {
            return this.time;
        }
        if (temporalQuery == TemporalQueries.offset()) {
            Long l = (Long) this.fieldValues.get(ChronoField.OFFSET_SECONDS);
            if (l != null) {
                return ZoneOffset.ofTotalSeconds(l.intValue());
            }
            ZoneId zoneId = this.zone;
            return zoneId instanceof ZoneOffset ? zoneId : temporalQuery.queryFrom(this);
        }
        if (temporalQuery == TemporalQueries.zone()) {
            return temporalQuery.queryFrom(this);
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return null;
        }
        return temporalQuery.queryFrom(this);
    }

    TemporalAccessor resolve(ResolverStyle resolverStyle, Set set) {
        if (set != null) {
            this.fieldValues.keySet().retainAll(set);
        }
        this.resolverStyle = resolverStyle;
        resolveFields();
        resolveTimeLenient();
        crossCheck();
        resolvePeriod();
        resolveFractional();
        resolveInstant();
        return this;
    }

    private void resolveFields() {
        resolveInstantFields();
        resolveDateFields();
        resolveTimeFields();
        if (this.fieldValues.size() > 0) {
            int i = 0;
            loop0: while (i < 50) {
                Iterator it = this.fieldValues.entrySet().iterator();
                while (it.hasNext()) {
                    TemporalField temporalField = (TemporalField) ((Map.Entry) it.next()).getKey();
                    TemporalAccessor temporalAccessorResolve = temporalField.resolve(this.fieldValues, this, this.resolverStyle);
                    if (temporalAccessorResolve != null) {
                        if (temporalAccessorResolve instanceof ChronoZonedDateTime) {
                            ChronoZonedDateTime chronoZonedDateTime = (ChronoZonedDateTime) temporalAccessorResolve;
                            ZoneId zoneId = this.zone;
                            if (zoneId == null) {
                                this.zone = chronoZonedDateTime.getZone();
                            } else if (!zoneId.equals(chronoZonedDateTime.getZone())) {
                                throw new DateTimeException("ChronoZonedDateTime must use the effective parsed zone: " + this.zone);
                            }
                            temporalAccessorResolve = chronoZonedDateTime.toLocalDateTime();
                        }
                        if (temporalAccessorResolve instanceof ChronoLocalDateTime) {
                            ChronoLocalDateTime chronoLocalDateTime = (ChronoLocalDateTime) temporalAccessorResolve;
                            updateCheckConflict(chronoLocalDateTime.toLocalTime(), Period.ZERO);
                            updateCheckConflict(chronoLocalDateTime.toLocalDate());
                        } else if (temporalAccessorResolve instanceof ChronoLocalDate) {
                            updateCheckConflict((ChronoLocalDate) temporalAccessorResolve);
                        } else if (temporalAccessorResolve instanceof LocalTime) {
                            updateCheckConflict((LocalTime) temporalAccessorResolve, Period.ZERO);
                        } else {
                            throw new DateTimeException("Method resolve() can only return ChronoZonedDateTime, ChronoLocalDateTime, ChronoLocalDate or LocalTime");
                        }
                    } else if (!this.fieldValues.containsKey(temporalField)) {
                        break;
                    }
                    i++;
                }
            }
            if (i == 50) {
                throw new DateTimeException("One of the parsed fields has an incorrectly implemented resolve method");
            }
            if (i > 0) {
                resolveInstantFields();
                resolveDateFields();
                resolveTimeFields();
            }
        }
    }

    private void updateCheckConflict(TemporalField temporalField, TemporalField temporalField2, Long l) {
        Long l2 = (Long) this.fieldValues.put(temporalField2, l);
        if (l2 == null || l2.longValue() == l.longValue()) {
            return;
        }
        throw new DateTimeException("Conflict found: " + temporalField2 + " " + l2 + " differs from " + temporalField2 + " " + l + " while resolving  " + temporalField);
    }

    private void resolveInstantFields() {
        if (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS)) {
            ZoneId zoneId = this.zone;
            if (zoneId != null) {
                resolveInstantFields0(zoneId);
                return;
            }
            Long l = (Long) this.fieldValues.get(ChronoField.OFFSET_SECONDS);
            if (l != null) {
                resolveInstantFields0(ZoneOffset.ofTotalSeconds(l.intValue()));
            }
        }
    }

    private void resolveInstantFields0(ZoneId zoneId) {
        Map map = this.fieldValues;
        ChronoField chronoField = ChronoField.INSTANT_SECONDS;
        updateCheckConflict(this.chrono.zonedDateTime(Instant.ofEpochSecond(((Long) map.remove(chronoField)).longValue()), zoneId).toLocalDate());
        updateCheckConflict(chronoField, ChronoField.SECOND_OF_DAY, Long.valueOf(r5.toLocalTime().toSecondOfDay()));
    }

    private void resolveDateFields() {
        updateCheckConflict(this.chrono.resolveDate(this.fieldValues, this.resolverStyle));
    }

    private void updateCheckConflict(ChronoLocalDate chronoLocalDate) {
        ChronoLocalDate chronoLocalDate2 = this.date;
        if (chronoLocalDate2 != null) {
            if (chronoLocalDate == null || chronoLocalDate2.equals(chronoLocalDate)) {
                return;
            }
            throw new DateTimeException("Conflict found: Fields resolved to two different dates: " + this.date + " " + chronoLocalDate);
        }
        if (chronoLocalDate != null) {
            if (!this.chrono.equals(chronoLocalDate.getChronology())) {
                throw new DateTimeException("ChronoLocalDate must use the effective parsed chronology: " + this.chrono);
            }
            this.date = chronoLocalDate;
        }
    }

    private void resolveTimeFields() {
        Map map = this.fieldValues;
        ChronoField chronoField = ChronoField.CLOCK_HOUR_OF_DAY;
        if (map.containsKey(chronoField)) {
            long jLongValue = ((Long) this.fieldValues.remove(chronoField)).longValue();
            ResolverStyle resolverStyle = this.resolverStyle;
            if (resolverStyle == ResolverStyle.STRICT || (resolverStyle == ResolverStyle.SMART && jLongValue != 0)) {
                chronoField.checkValidValue(jLongValue);
            }
            TemporalField temporalField = ChronoField.HOUR_OF_DAY;
            if (jLongValue == 24) {
                jLongValue = 0;
            }
            updateCheckConflict(chronoField, temporalField, Long.valueOf(jLongValue));
        }
        Map map2 = this.fieldValues;
        ChronoField chronoField2 = ChronoField.CLOCK_HOUR_OF_AMPM;
        if (map2.containsKey(chronoField2)) {
            long jLongValue2 = ((Long) this.fieldValues.remove(chronoField2)).longValue();
            ResolverStyle resolverStyle2 = this.resolverStyle;
            if (resolverStyle2 == ResolverStyle.STRICT || (resolverStyle2 == ResolverStyle.SMART && jLongValue2 != 0)) {
                chronoField2.checkValidValue(jLongValue2);
            }
            updateCheckConflict(chronoField2, ChronoField.HOUR_OF_AMPM, Long.valueOf(jLongValue2 != 12 ? jLongValue2 : 0L));
        }
        Map map3 = this.fieldValues;
        ChronoField chronoField3 = ChronoField.AMPM_OF_DAY;
        if (map3.containsKey(chronoField3)) {
            Map map4 = this.fieldValues;
            ChronoField chronoField4 = ChronoField.HOUR_OF_AMPM;
            if (map4.containsKey(chronoField4)) {
                long jLongValue3 = ((Long) this.fieldValues.remove(chronoField3)).longValue();
                long jLongValue4 = ((Long) this.fieldValues.remove(chronoField4)).longValue();
                if (this.resolverStyle == ResolverStyle.LENIENT) {
                    updateCheckConflict(chronoField3, ChronoField.HOUR_OF_DAY, Long.valueOf(Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(jLongValue3, 12), jLongValue4)));
                } else {
                    chronoField3.checkValidValue(jLongValue3);
                    chronoField4.checkValidValue(jLongValue3);
                    updateCheckConflict(chronoField3, ChronoField.HOUR_OF_DAY, Long.valueOf((jLongValue3 * 12) + jLongValue4));
                }
            }
        }
        Map map5 = this.fieldValues;
        ChronoField chronoField5 = ChronoField.NANO_OF_DAY;
        if (map5.containsKey(chronoField5)) {
            long jLongValue5 = ((Long) this.fieldValues.remove(chronoField5)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                chronoField5.checkValidValue(jLongValue5);
            }
            updateCheckConflict(chronoField5, ChronoField.HOUR_OF_DAY, Long.valueOf(jLongValue5 / 3600000000000L));
            updateCheckConflict(chronoField5, ChronoField.MINUTE_OF_HOUR, Long.valueOf((jLongValue5 / 60000000000L) % 60));
            updateCheckConflict(chronoField5, ChronoField.SECOND_OF_MINUTE, Long.valueOf((jLongValue5 / 1000000000) % 60));
            updateCheckConflict(chronoField5, ChronoField.NANO_OF_SECOND, Long.valueOf(jLongValue5 % 1000000000));
        }
        Map map6 = this.fieldValues;
        ChronoField chronoField6 = ChronoField.MICRO_OF_DAY;
        if (map6.containsKey(chronoField6)) {
            long jLongValue6 = ((Long) this.fieldValues.remove(chronoField6)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                chronoField6.checkValidValue(jLongValue6);
            }
            updateCheckConflict(chronoField6, ChronoField.SECOND_OF_DAY, Long.valueOf(jLongValue6 / 1000000));
            updateCheckConflict(chronoField6, ChronoField.MICRO_OF_SECOND, Long.valueOf(jLongValue6 % 1000000));
        }
        Map map7 = this.fieldValues;
        ChronoField chronoField7 = ChronoField.MILLI_OF_DAY;
        if (map7.containsKey(chronoField7)) {
            long jLongValue7 = ((Long) this.fieldValues.remove(chronoField7)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                chronoField7.checkValidValue(jLongValue7);
            }
            updateCheckConflict(chronoField7, ChronoField.SECOND_OF_DAY, Long.valueOf(jLongValue7 / 1000));
            updateCheckConflict(chronoField7, ChronoField.MILLI_OF_SECOND, Long.valueOf(jLongValue7 % 1000));
        }
        Map map8 = this.fieldValues;
        ChronoField chronoField8 = ChronoField.SECOND_OF_DAY;
        if (map8.containsKey(chronoField8)) {
            long jLongValue8 = ((Long) this.fieldValues.remove(chronoField8)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                chronoField8.checkValidValue(jLongValue8);
            }
            updateCheckConflict(chronoField8, ChronoField.HOUR_OF_DAY, Long.valueOf(jLongValue8 / 3600));
            updateCheckConflict(chronoField8, ChronoField.MINUTE_OF_HOUR, Long.valueOf((jLongValue8 / 60) % 60));
            updateCheckConflict(chronoField8, ChronoField.SECOND_OF_MINUTE, Long.valueOf(jLongValue8 % 60));
        }
        Map map9 = this.fieldValues;
        ChronoField chronoField9 = ChronoField.MINUTE_OF_DAY;
        if (map9.containsKey(chronoField9)) {
            long jLongValue9 = ((Long) this.fieldValues.remove(chronoField9)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                chronoField9.checkValidValue(jLongValue9);
            }
            updateCheckConflict(chronoField9, ChronoField.HOUR_OF_DAY, Long.valueOf(jLongValue9 / 60));
            updateCheckConflict(chronoField9, ChronoField.MINUTE_OF_HOUR, Long.valueOf(jLongValue9 % 60));
        }
        Map map10 = this.fieldValues;
        ChronoField chronoField10 = ChronoField.NANO_OF_SECOND;
        if (map10.containsKey(chronoField10)) {
            long jLongValue10 = ((Long) this.fieldValues.get(chronoField10)).longValue();
            ResolverStyle resolverStyle3 = this.resolverStyle;
            ResolverStyle resolverStyle4 = ResolverStyle.LENIENT;
            if (resolverStyle3 != resolverStyle4) {
                chronoField10.checkValidValue(jLongValue10);
            }
            Map map11 = this.fieldValues;
            ChronoField chronoField11 = ChronoField.MICRO_OF_SECOND;
            if (map11.containsKey(chronoField11)) {
                long jLongValue11 = ((Long) this.fieldValues.remove(chronoField11)).longValue();
                if (this.resolverStyle != resolverStyle4) {
                    chronoField11.checkValidValue(jLongValue11);
                }
                jLongValue10 = (jLongValue10 % 1000) + (jLongValue11 * 1000);
                updateCheckConflict(chronoField11, chronoField10, Long.valueOf(jLongValue10));
            }
            Map map12 = this.fieldValues;
            ChronoField chronoField12 = ChronoField.MILLI_OF_SECOND;
            if (map12.containsKey(chronoField12)) {
                long jLongValue12 = ((Long) this.fieldValues.remove(chronoField12)).longValue();
                if (this.resolverStyle != resolverStyle4) {
                    chronoField12.checkValidValue(jLongValue12);
                }
                updateCheckConflict(chronoField12, chronoField10, Long.valueOf((jLongValue12 * 1000000) + (jLongValue10 % 1000000)));
            }
        }
        Map map13 = this.fieldValues;
        ChronoField chronoField13 = ChronoField.HOUR_OF_DAY;
        if (map13.containsKey(chronoField13)) {
            Map map14 = this.fieldValues;
            ChronoField chronoField14 = ChronoField.MINUTE_OF_HOUR;
            if (map14.containsKey(chronoField14)) {
                Map map15 = this.fieldValues;
                ChronoField chronoField15 = ChronoField.SECOND_OF_MINUTE;
                if (map15.containsKey(chronoField15) && this.fieldValues.containsKey(chronoField10)) {
                    resolveTime(((Long) this.fieldValues.remove(chronoField13)).longValue(), ((Long) this.fieldValues.remove(chronoField14)).longValue(), ((Long) this.fieldValues.remove(chronoField15)).longValue(), ((Long) this.fieldValues.remove(chronoField10)).longValue());
                }
            }
        }
    }

    private void resolveTimeLenient() {
        if (this.time == null) {
            Map map = this.fieldValues;
            ChronoField chronoField = ChronoField.MILLI_OF_SECOND;
            if (map.containsKey(chronoField)) {
                long jLongValue = ((Long) this.fieldValues.remove(chronoField)).longValue();
                Map map2 = this.fieldValues;
                ChronoField chronoField2 = ChronoField.MICRO_OF_SECOND;
                if (map2.containsKey(chronoField2)) {
                    long jLongValue2 = (jLongValue * 1000) + (((Long) this.fieldValues.get(chronoField2)).longValue() % 1000);
                    updateCheckConflict(chronoField, chronoField2, Long.valueOf(jLongValue2));
                    this.fieldValues.remove(chronoField2);
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(jLongValue2 * 1000));
                } else {
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(jLongValue * 1000000));
                }
            } else {
                Map map3 = this.fieldValues;
                ChronoField chronoField3 = ChronoField.MICRO_OF_SECOND;
                if (map3.containsKey(chronoField3)) {
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(((Long) this.fieldValues.remove(chronoField3)).longValue() * 1000));
                }
            }
            Map map4 = this.fieldValues;
            ChronoField chronoField4 = ChronoField.HOUR_OF_DAY;
            Long l = (Long) map4.get(chronoField4);
            if (l != null) {
                Map map5 = this.fieldValues;
                ChronoField chronoField5 = ChronoField.MINUTE_OF_HOUR;
                Long l2 = (Long) map5.get(chronoField5);
                Map map6 = this.fieldValues;
                ChronoField chronoField6 = ChronoField.SECOND_OF_MINUTE;
                Long l3 = (Long) map6.get(chronoField6);
                Map map7 = this.fieldValues;
                ChronoField chronoField7 = ChronoField.NANO_OF_SECOND;
                Long l4 = (Long) map7.get(chronoField7);
                if (l2 == null && (l3 != null || l4 != null)) {
                    return;
                }
                if (l2 != null && l3 == null && l4 != null) {
                    return;
                }
                long jLongValue3 = l2 != null ? l2.longValue() : 0L;
                long jLongValue4 = l3 != null ? l3.longValue() : 0L;
                resolveTime(l.longValue(), jLongValue3, jLongValue4, l4 != null ? l4.longValue() : 0L);
                this.fieldValues.remove(chronoField4);
                this.fieldValues.remove(chronoField5);
                this.fieldValues.remove(chronoField6);
                this.fieldValues.remove(chronoField7);
            }
        }
        if (this.resolverStyle == ResolverStyle.LENIENT || this.fieldValues.size() <= 0) {
            return;
        }
        for (Map.Entry entry : this.fieldValues.entrySet()) {
            TemporalField temporalField = (TemporalField) entry.getKey();
            if ((temporalField instanceof ChronoField) && temporalField.isTimeBased()) {
                ((ChronoField) temporalField).checkValidValue(((Long) entry.getValue()).longValue());
            }
        }
    }

    private void resolveTime(long j, long j2, long j3, long j4) {
        if (this.resolverStyle == ResolverStyle.LENIENT) {
            long jM = Clock$OffsetClock$$ExternalSyntheticBackport0.m(Clock$OffsetClock$$ExternalSyntheticBackport0.m(Clock$OffsetClock$$ExternalSyntheticBackport0.m(Duration$$ExternalSyntheticBackport1.m(j, 3600000000000L), Duration$$ExternalSyntheticBackport1.m(j2, 60000000000L)), Duration$$ExternalSyntheticBackport1.m(j3, 1000000000L)), j4);
            updateCheckConflict(LocalTime.ofNanoOfDay(Clock$TickClock$$ExternalSyntheticBackport0.m(jM, 86400000000000L)), Period.ofDays((int) DesugarLocalDate$$ExternalSyntheticBackport0.m(jM, 86400000000000L)));
            return;
        }
        int iCheckValidIntValue = ChronoField.MINUTE_OF_HOUR.checkValidIntValue(j2);
        int iCheckValidIntValue2 = ChronoField.NANO_OF_SECOND.checkValidIntValue(j4);
        if (this.resolverStyle == ResolverStyle.SMART && j == 24 && iCheckValidIntValue == 0 && j3 == 0 && iCheckValidIntValue2 == 0) {
            updateCheckConflict(LocalTime.MIDNIGHT, Period.ofDays(1));
        } else {
            updateCheckConflict(LocalTime.of(ChronoField.HOUR_OF_DAY.checkValidIntValue(j), iCheckValidIntValue, ChronoField.SECOND_OF_MINUTE.checkValidIntValue(j3), iCheckValidIntValue2), Period.ZERO);
        }
    }

    private void resolvePeriod() {
        if (this.date == null || this.time == null || this.excessDays.isZero()) {
            return;
        }
        this.date = this.date.plus(this.excessDays);
        this.excessDays = Period.ZERO;
    }

    private void resolveFractional() {
        if (this.time == null) {
            if (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS) || this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY) || this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE)) {
                Map map = this.fieldValues;
                ChronoField chronoField = ChronoField.NANO_OF_SECOND;
                if (map.containsKey(chronoField)) {
                    long jLongValue = ((Long) this.fieldValues.get(chronoField)).longValue();
                    this.fieldValues.put(ChronoField.MICRO_OF_SECOND, Long.valueOf(jLongValue / 1000));
                    this.fieldValues.put(ChronoField.MILLI_OF_SECOND, Long.valueOf(jLongValue / 1000000));
                } else {
                    this.fieldValues.put(chronoField, 0L);
                    this.fieldValues.put(ChronoField.MICRO_OF_SECOND, 0L);
                    this.fieldValues.put(ChronoField.MILLI_OF_SECOND, 0L);
                }
            }
        }
    }

    private void resolveInstant() {
        if (this.date == null || this.time == null) {
            return;
        }
        Long l = (Long) this.fieldValues.get(ChronoField.OFFSET_SECONDS);
        if (l != null) {
            this.fieldValues.put(ChronoField.INSTANT_SECONDS, Long.valueOf(this.date.atTime(this.time).atZone(ZoneOffset.ofTotalSeconds(l.intValue())).toEpochSecond()));
        } else if (this.zone != null) {
            this.fieldValues.put(ChronoField.INSTANT_SECONDS, Long.valueOf(this.date.atTime(this.time).atZone(this.zone).toEpochSecond()));
        }
    }

    private void updateCheckConflict(LocalTime localTime, Period period) {
        LocalTime localTime2 = this.time;
        if (localTime2 != null) {
            if (!localTime2.equals(localTime)) {
                throw new DateTimeException("Conflict found: Fields resolved to different times: " + this.time + " " + localTime);
            }
            if (!this.excessDays.isZero() && !period.isZero() && !this.excessDays.equals(period)) {
                throw new DateTimeException("Conflict found: Fields resolved to different excess periods: " + this.excessDays + " " + period);
            }
            this.excessDays = period;
            return;
        }
        this.time = localTime;
        this.excessDays = period;
    }

    private void crossCheck() {
        ChronoLocalDate chronoLocalDate = this.date;
        if (chronoLocalDate != null) {
            crossCheck(chronoLocalDate);
        }
        LocalTime localTime = this.time;
        if (localTime != null) {
            crossCheck(localTime);
            if (this.date == null || this.fieldValues.size() <= 0) {
                return;
            }
            crossCheck(this.date.atTime(this.time));
        }
    }

    private void crossCheck(TemporalAccessor temporalAccessor) {
        Iterator it = this.fieldValues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            TemporalField temporalField = (TemporalField) entry.getKey();
            if (temporalAccessor.isSupported(temporalField)) {
                try {
                    long j = temporalAccessor.getLong(temporalField);
                    long jLongValue = ((Long) entry.getValue()).longValue();
                    if (j != jLongValue) {
                        throw new DateTimeException("Conflict found: Field " + temporalField + " " + j + " differs from " + temporalField + " " + jLongValue + " derived from " + temporalAccessor);
                    }
                    it.remove();
                } catch (RuntimeException unused) {
                    continue;
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(this.fieldValues);
        sb.append(',');
        sb.append(this.chrono);
        if (this.zone != null) {
            sb.append(',');
            sb.append(this.zone);
        }
        if (this.date != null || this.time != null) {
            sb.append(" resolved to ");
            ChronoLocalDate chronoLocalDate = this.date;
            if (chronoLocalDate != null) {
                sb.append(chronoLocalDate);
                if (this.time != null) {
                    sb.append('T');
                    sb.append(this.time);
                }
            } else {
                sb.append(this.time);
            }
        }
        return sb.toString();
    }
}

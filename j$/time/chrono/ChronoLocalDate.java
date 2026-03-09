package j$.time.chrono;

import j$.time.LocalTime;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalAmount;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.UnsupportedTemporalTypeException;

/* JADX INFO: loaded from: classes3.dex */
public interface ChronoLocalDate extends Temporal, TemporalAdjuster, Comparable {
    ChronoLocalDateTime atTime(LocalTime localTime);

    int compareTo(ChronoLocalDate chronoLocalDate);

    boolean equals(Object obj);

    Chronology getChronology();

    Era getEra();

    int hashCode();

    boolean isLeapYear();

    @Override // j$.time.temporal.TemporalAccessor
    boolean isSupported(TemporalField temporalField);

    int lengthOfYear();

    @Override // j$.time.temporal.Temporal
    ChronoLocalDate minus(long j, TemporalUnit temporalUnit);

    @Override // j$.time.temporal.Temporal
    ChronoLocalDate plus(long j, TemporalUnit temporalUnit);

    ChronoLocalDate plus(TemporalAmount temporalAmount);

    long toEpochDay();

    String toString();

    @Override // j$.time.temporal.Temporal
    ChronoLocalDate with(TemporalAdjuster temporalAdjuster);

    @Override // j$.time.temporal.Temporal
    ChronoLocalDate with(TemporalField temporalField, long j);

    /* JADX INFO: renamed from: j$.time.chrono.ChronoLocalDate$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static int $default$lengthOfYear(ChronoLocalDate chronoLocalDate) {
            return chronoLocalDate.isLeapYear() ? 366 : 365;
        }

        public static boolean $default$isSupported(ChronoLocalDate chronoLocalDate, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                return temporalField.isDateBased();
            }
            return temporalField != null && temporalField.isSupportedBy(chronoLocalDate);
        }

        public static ChronoLocalDate $default$with(ChronoLocalDate chronoLocalDate, TemporalField temporalField, long j) {
            if (temporalField instanceof ChronoField) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
            return ChronoLocalDateImpl.ensureValid(chronoLocalDate.getChronology(), temporalField.adjustInto(chronoLocalDate, j));
        }

        public static ChronoLocalDate $default$plus(ChronoLocalDate chronoLocalDate, long j, TemporalUnit temporalUnit) {
            if (temporalUnit instanceof ChronoUnit) {
                throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
            return ChronoLocalDateImpl.ensureValid(chronoLocalDate.getChronology(), temporalUnit.addTo(chronoLocalDate, j));
        }

        public static Object $default$query(ChronoLocalDate chronoLocalDate, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset() || temporalQuery == TemporalQueries.localTime()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return chronoLocalDate.getChronology();
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return ChronoUnit.DAYS;
            }
            return temporalQuery.queryFrom(chronoLocalDate);
        }

        public static int $default$compareTo(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
            int iCompare = Long.compare(chronoLocalDate.toEpochDay(), chronoLocalDate2.toEpochDay());
            return iCompare == 0 ? chronoLocalDate.getChronology().compareTo(chronoLocalDate2.getChronology()) : iCompare;
        }

        public static boolean $default$isBefore(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
            return chronoLocalDate.toEpochDay() < chronoLocalDate2.toEpochDay();
        }
    }
}

package j$.time.chrono;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.LocalTime;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public interface ChronoLocalDateTime extends Temporal, TemporalAdjuster, Comparable {
    ChronoZonedDateTime atZone(ZoneId zoneId);

    int compareTo(ChronoLocalDateTime chronoLocalDateTime);

    Chronology getChronology();

    int hashCode();

    @Override // j$.time.temporal.Temporal
    ChronoLocalDateTime minus(long j, TemporalUnit temporalUnit);

    long toEpochSecond(ZoneOffset zoneOffset);

    ChronoLocalDate toLocalDate();

    LocalTime toLocalTime();

    String toString();

    /* JADX INFO: renamed from: j$.time.chrono.ChronoLocalDateTime$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static Object $default$query(ChronoLocalDateTime chronoLocalDateTime, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.localTime()) {
                return chronoLocalDateTime.toLocalTime();
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return chronoLocalDateTime.getChronology();
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return ChronoUnit.NANOS;
            }
            return temporalQuery.queryFrom(chronoLocalDateTime);
        }

        public static long $default$toEpochSecond(ChronoLocalDateTime chronoLocalDateTime, ZoneOffset zoneOffset) {
            Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
            return ((chronoLocalDateTime.toLocalDate().toEpochDay() * 86400) + ((long) chronoLocalDateTime.toLocalTime().toSecondOfDay())) - ((long) zoneOffset.getTotalSeconds());
        }

        public static int $default$compareTo(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
            int iCompareTo = chronoLocalDateTime.toLocalDate().compareTo(chronoLocalDateTime2.toLocalDate());
            return (iCompareTo == 0 && (iCompareTo = chronoLocalDateTime.toLocalTime().compareTo(chronoLocalDateTime2.toLocalTime())) == 0) ? chronoLocalDateTime.getChronology().compareTo(chronoLocalDateTime2.getChronology()) : iCompareTo;
        }

        public static boolean $default$isAfter(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
            long epochDay = chronoLocalDateTime.toLocalDate().toEpochDay();
            long epochDay2 = chronoLocalDateTime2.toLocalDate().toEpochDay();
            if (epochDay <= epochDay2) {
                return epochDay == epochDay2 && chronoLocalDateTime.toLocalTime().toNanoOfDay() > chronoLocalDateTime2.toLocalTime().toNanoOfDay();
            }
            return true;
        }

        public static boolean $default$isBefore(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
            long epochDay = chronoLocalDateTime.toLocalDate().toEpochDay();
            long epochDay2 = chronoLocalDateTime2.toLocalDate().toEpochDay();
            if (epochDay >= epochDay2) {
                return epochDay == epochDay2 && chronoLocalDateTime.toLocalTime().toNanoOfDay() < chronoLocalDateTime2.toLocalTime().toNanoOfDay();
            }
            return true;
        }
    }
}

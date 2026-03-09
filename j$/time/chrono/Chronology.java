package j$.time.chrono;

import j$.time.DateTimeException;
import j$.time.Instant;
import j$.time.LocalTime;
import j$.time.ZoneId;
import j$.time.format.ResolverStyle;
import j$.time.temporal.ChronoField;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface Chronology extends Comparable {
    int compareTo(Chronology chronology);

    ChronoLocalDate date(int i, int i2, int i3);

    ChronoLocalDate date(TemporalAccessor temporalAccessor);

    ChronoLocalDate dateEpochDay(long j);

    ChronoLocalDate dateYearDay(int i, int i2);

    boolean equals(Object obj);

    Era eraOf(int i);

    List eras();

    String getCalendarType();

    String getId();

    int hashCode();

    boolean isLeapYear(long j);

    ChronoLocalDateTime localDateTime(TemporalAccessor temporalAccessor);

    int prolepticYear(Era era, int i);

    ValueRange range(ChronoField chronoField);

    ChronoLocalDate resolveDate(Map map, ResolverStyle resolverStyle);

    String toString();

    ChronoZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId);

    /* JADX INFO: renamed from: j$.time.chrono.Chronology$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static Chronology from(TemporalAccessor temporalAccessor) {
            Objects.requireNonNull(temporalAccessor, "temporal");
            return (Chronology) Objects.requireNonNullElse((Chronology) temporalAccessor.query(TemporalQueries.chronology()), IsoChronology.INSTANCE);
        }

        public static Chronology of(String str) {
            return AbstractChronology.of(str);
        }

        public static ChronoLocalDateTime $default$localDateTime(Chronology chronology, TemporalAccessor temporalAccessor) {
            try {
                return chronology.date(temporalAccessor).atTime(LocalTime.from(temporalAccessor));
            } catch (DateTimeException e) {
                throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + temporalAccessor.getClass(), e);
            }
        }
    }
}

package j$.time.temporal;

import j$.time.DateTimeException;
import j$.time.Instant$$ExternalSyntheticBackport0;
import j$.time.chrono.ChronoLocalDate;
import j$.time.chrono.Chronology;
import j$.time.format.ResolverStyle;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JulianFields {
    public static final TemporalField JULIAN_DAY = Field.JULIAN_DAY;
    public static final TemporalField MODIFIED_JULIAN_DAY = Field.MODIFIED_JULIAN_DAY;
    public static final TemporalField RATA_DIE = Field.RATA_DIE;

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'JULIAN_DAY' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    private static final class Field implements TemporalField {
        private static final /* synthetic */ Field[] $VALUES;
        public static final Field JULIAN_DAY;
        public static final Field MODIFIED_JULIAN_DAY;
        public static final Field RATA_DIE;
        private static final long serialVersionUID = -7501623920830201812L;
        private final transient TemporalUnit baseUnit;
        private final transient String name;
        private final transient long offset;
        private final transient ValueRange range;
        private final transient TemporalUnit rangeUnit;

        @Override // j$.time.temporal.TemporalField
        public boolean isDateBased() {
            return true;
        }

        @Override // j$.time.temporal.TemporalField
        public boolean isTimeBased() {
            return false;
        }

        public static Field valueOf(String str) {
            return (Field) Enum.valueOf(Field.class, str);
        }

        public static Field[] values() {
            return (Field[]) $VALUES.clone();
        }

        static {
            ChronoUnit chronoUnit = ChronoUnit.DAYS;
            ChronoUnit chronoUnit2 = ChronoUnit.FOREVER;
            Field field = new Field("JULIAN_DAY", 0, "JulianDay", chronoUnit, chronoUnit2, 2440588L);
            JULIAN_DAY = field;
            Field field2 = new Field("MODIFIED_JULIAN_DAY", 1, "ModifiedJulianDay", chronoUnit, chronoUnit2, 40587L);
            MODIFIED_JULIAN_DAY = field2;
            Field field3 = new Field("RATA_DIE", 2, "RataDie", chronoUnit, chronoUnit2, 719163L);
            RATA_DIE = field3;
            $VALUES = new Field[]{field, field2, field3};
        }

        private Field(String str, int i, String str2, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, long j) {
            this.name = str2;
            this.baseUnit = temporalUnit;
            this.rangeUnit = temporalUnit2;
            this.range = ValueRange.of((-365243219162L) + j, 365241780471L + j);
            this.offset = j;
        }

        @Override // j$.time.temporal.TemporalField
        public ValueRange range() {
            return this.range;
        }

        @Override // j$.time.temporal.TemporalField
        public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
            return temporalAccessor.isSupported(ChronoField.EPOCH_DAY);
        }

        @Override // j$.time.temporal.TemporalField
        public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
            if (!isSupportedBy(temporalAccessor)) {
                throw new DateTimeException("Unsupported field: " + this);
            }
            return range();
        }

        @Override // j$.time.temporal.TemporalField
        public long getFrom(TemporalAccessor temporalAccessor) {
            return temporalAccessor.getLong(ChronoField.EPOCH_DAY) + this.offset;
        }

        @Override // j$.time.temporal.TemporalField
        public Temporal adjustInto(Temporal temporal, long j) {
            if (!range().isValidValue(j)) {
                throw new DateTimeException("Invalid value: " + this.name + " " + j);
            }
            return temporal.with(ChronoField.EPOCH_DAY, Instant$$ExternalSyntheticBackport0.m(j, this.offset));
        }

        @Override // j$.time.temporal.TemporalField
        public ChronoLocalDate resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
            long jLongValue = ((Long) map.remove(this)).longValue();
            Chronology chronologyFrom = Chronology.CC.from(temporalAccessor);
            if (resolverStyle == ResolverStyle.LENIENT) {
                return chronologyFrom.dateEpochDay(Instant$$ExternalSyntheticBackport0.m(jLongValue, this.offset));
            }
            range().checkValidValue(jLongValue, this);
            return chronologyFrom.dateEpochDay(jLongValue - this.offset);
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }
    }
}

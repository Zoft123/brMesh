package j$.time.temporal;

import j$.time.DayOfWeek;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TemporalAdjusters {
    static /* synthetic */ Temporal lambda$lastDayOfMonth$2(Temporal temporal) {
        ChronoField chronoField = ChronoField.DAY_OF_MONTH;
        return temporal.with(chronoField, temporal.range(chronoField).getMaximum());
    }

    public static TemporalAdjuster lastDayOfMonth() {
        return new TemporalAdjuster() { // from class: j$.time.temporal.TemporalAdjusters$$ExternalSyntheticLambda9
            @Override // j$.time.temporal.TemporalAdjuster
            public final Temporal adjustInto(Temporal temporal) {
                return TemporalAdjusters.lambda$lastDayOfMonth$2(temporal);
            }
        };
    }

    public static TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek) {
        final int value = dayOfWeek.getValue();
        return new TemporalAdjuster() { // from class: j$.time.temporal.TemporalAdjusters$$ExternalSyntheticLambda6
            @Override // j$.time.temporal.TemporalAdjuster
            public final Temporal adjustInto(Temporal temporal) {
                return TemporalAdjusters.lambda$nextOrSame$10(value, temporal);
            }
        };
    }

    static /* synthetic */ Temporal lambda$nextOrSame$10(int i, Temporal temporal) {
        int i2 = temporal.get(ChronoField.DAY_OF_WEEK);
        if (i2 == i) {
            return temporal;
        }
        return temporal.plus(i2 - i >= 0 ? 7 - r0 : -r0, ChronoUnit.DAYS);
    }

    public static TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek) {
        final int value = dayOfWeek.getValue();
        return new TemporalAdjuster() { // from class: j$.time.temporal.TemporalAdjusters$$ExternalSyntheticLambda8
            @Override // j$.time.temporal.TemporalAdjuster
            public final Temporal adjustInto(Temporal temporal) {
                return TemporalAdjusters.lambda$previousOrSame$12(value, temporal);
            }
        };
    }

    static /* synthetic */ Temporal lambda$previousOrSame$12(int i, Temporal temporal) {
        int i2 = temporal.get(ChronoField.DAY_OF_WEEK);
        if (i2 == i) {
            return temporal;
        }
        return temporal.minus(i - i2 >= 0 ? 7 - r2 : -r2, ChronoUnit.DAYS);
    }
}

package j$.time.temporal;

import j$.time.format.ResolverStyle;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface TemporalField {

    /* JADX INFO: renamed from: j$.time.temporal.TemporalField$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static TemporalAccessor $default$resolve(TemporalField temporalField, Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
            return null;
        }
    }

    Temporal adjustInto(Temporal temporal, long j);

    long getFrom(TemporalAccessor temporalAccessor);

    boolean isDateBased();

    boolean isSupportedBy(TemporalAccessor temporalAccessor);

    boolean isTimeBased();

    ValueRange range();

    ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor);

    TemporalAccessor resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle);
}

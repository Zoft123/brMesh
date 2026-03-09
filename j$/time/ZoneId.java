package j$.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalQueries;
import j$.time.zone.ZoneRules;
import j$.time.zone.ZoneRulesException;
import j$.util.DesugarTimeZone;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ZoneId implements Serializable {
    public static final Map SHORT_IDS = ZoneId$$ExternalSyntheticBackport1.m(new Map.Entry[]{ZoneId$$ExternalSyntheticBackport0.m("ACT", "Australia/Darwin"), ZoneId$$ExternalSyntheticBackport0.m("AET", "Australia/Sydney"), ZoneId$$ExternalSyntheticBackport0.m("AGT", "America/Argentina/Buenos_Aires"), ZoneId$$ExternalSyntheticBackport0.m("ART", "Africa/Cairo"), ZoneId$$ExternalSyntheticBackport0.m("AST", "America/Anchorage"), ZoneId$$ExternalSyntheticBackport0.m("BET", "America/Sao_Paulo"), ZoneId$$ExternalSyntheticBackport0.m("BST", "Asia/Dhaka"), ZoneId$$ExternalSyntheticBackport0.m("CAT", "Africa/Harare"), ZoneId$$ExternalSyntheticBackport0.m("CNT", "America/St_Johns"), ZoneId$$ExternalSyntheticBackport0.m("CST", "America/Chicago"), ZoneId$$ExternalSyntheticBackport0.m("CTT", "Asia/Shanghai"), ZoneId$$ExternalSyntheticBackport0.m("EAT", "Africa/Addis_Ababa"), ZoneId$$ExternalSyntheticBackport0.m("ECT", "Europe/Paris"), ZoneId$$ExternalSyntheticBackport0.m("IET", "America/Indiana/Indianapolis"), ZoneId$$ExternalSyntheticBackport0.m("IST", "Asia/Kolkata"), ZoneId$$ExternalSyntheticBackport0.m("JST", "Asia/Tokyo"), ZoneId$$ExternalSyntheticBackport0.m("MIT", "Pacific/Apia"), ZoneId$$ExternalSyntheticBackport0.m("NET", "Asia/Yerevan"), ZoneId$$ExternalSyntheticBackport0.m("NST", "Pacific/Auckland"), ZoneId$$ExternalSyntheticBackport0.m("PLT", "Asia/Karachi"), ZoneId$$ExternalSyntheticBackport0.m("PNT", "America/Phoenix"), ZoneId$$ExternalSyntheticBackport0.m("PRT", "America/Puerto_Rico"), ZoneId$$ExternalSyntheticBackport0.m("PST", "America/Los_Angeles"), ZoneId$$ExternalSyntheticBackport0.m("SST", "Pacific/Guadalcanal"), ZoneId$$ExternalSyntheticBackport0.m("VST", "Asia/Ho_Chi_Minh"), ZoneId$$ExternalSyntheticBackport0.m("EST", "-05:00"), ZoneId$$ExternalSyntheticBackport0.m("MST", "-07:00"), ZoneId$$ExternalSyntheticBackport0.m("HST", "-10:00")});
    private static final long serialVersionUID = 8352817235686L;

    public abstract String getId();

    public abstract ZoneRules getRules();

    abstract void write(DataOutput dataOutput);

    public static ZoneId systemDefault() {
        return DesugarTimeZone.toZoneId(TimeZone.getDefault());
    }

    public static ZoneId of(String str, Map map) {
        Objects.requireNonNull(str, "zoneId");
        Objects.requireNonNull(map, "aliasMap");
        return of((String) Objects.requireNonNullElse((String) map.get(str), str));
    }

    public static ZoneId of(String str) {
        return of(str, true);
    }

    public static ZoneId ofOffset(String str, ZoneOffset zoneOffset) {
        Objects.requireNonNull(str, "prefix");
        Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
        if (str.isEmpty()) {
            return zoneOffset;
        }
        if (!str.equals("GMT") && !str.equals("UTC") && !str.equals("UT")) {
            throw new IllegalArgumentException("prefix should be GMT, UTC or UT, is: " + str);
        }
        if (zoneOffset.getTotalSeconds() != 0) {
            str = str.concat(zoneOffset.getId());
        }
        return new ZoneRegion(str, zoneOffset.getRules());
    }

    static ZoneId of(String str, boolean z) {
        Objects.requireNonNull(str, "zoneId");
        if (str.length() <= 1 || str.startsWith("+") || str.startsWith("-")) {
            return ZoneOffset.of(str);
        }
        if (str.startsWith("UTC") || str.startsWith("GMT")) {
            return ofWithPrefix(str, 3, z);
        }
        if (str.startsWith("UT")) {
            return ofWithPrefix(str, 2, z);
        }
        return ZoneRegion.ofId(str, z);
    }

    private static ZoneId ofWithPrefix(String str, int i, boolean z) {
        String strSubstring = str.substring(0, i);
        if (str.length() == i) {
            return ofOffset(strSubstring, ZoneOffset.UTC);
        }
        if (str.charAt(i) != '+' && str.charAt(i) != '-') {
            return ZoneRegion.ofId(str, z);
        }
        try {
            ZoneOffset zoneOffsetOf = ZoneOffset.of(str.substring(i));
            if (zoneOffsetOf == ZoneOffset.UTC) {
                return ofOffset(strSubstring, zoneOffsetOf);
            }
            return ofOffset(strSubstring, zoneOffsetOf);
        } catch (DateTimeException e) {
            throw new DateTimeException("Invalid ID for offset-based ZoneId: " + str, e);
        }
    }

    public static ZoneId from(TemporalAccessor temporalAccessor) {
        ZoneId zoneId = (ZoneId) temporalAccessor.query(TemporalQueries.zone());
        if (zoneId != null) {
            return zoneId;
        }
        throw new DateTimeException("Unable to obtain ZoneId from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    ZoneId() {
        if (getClass() != ZoneOffset.class && getClass() != ZoneRegion.class) {
            throw new AssertionError("Invalid subclass");
        }
    }

    public ZoneId normalized() {
        try {
            ZoneRules rules = getRules();
            if (rules.isFixedOffset()) {
                return rules.getOffset(Instant.EPOCH);
            }
        } catch (ZoneRulesException unused) {
        }
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneId) {
            return getId().equals(((ZoneId) obj).getId());
        }
        return false;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public String toString() {
        return getId();
    }

    private Object writeReplace() {
        return new Ser((byte) 7, this);
    }
}

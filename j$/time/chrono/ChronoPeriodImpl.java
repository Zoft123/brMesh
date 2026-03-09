package j$.time.chrono;

import j$.time.DateTimeException;
import j$.time.Duration$DurationUnits$$ExternalSyntheticBackport0;
import j$.time.chrono.Chronology;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAmount;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
final class ChronoPeriodImpl implements TemporalAmount, Serializable {
    private static final List SUPPORTED_UNITS = Duration$DurationUnits$$ExternalSyntheticBackport0.m(new Object[]{ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS});
    private static final long serialVersionUID = 57387258289L;
    private final Chronology chrono;
    final int days;
    final int months;
    final int years;

    ChronoPeriodImpl(Chronology chronology, int i, int i2, int i3) {
        Objects.requireNonNull(chronology, "chrono");
        this.chrono = chronology;
        this.years = i;
        this.months = i2;
        this.days = i3;
    }

    public Chronology getChronology() {
        return this.chrono;
    }

    public boolean isZero() {
        return this.years == 0 && this.months == 0 && this.days == 0;
    }

    private long monthRange() {
        ValueRange valueRangeRange = this.chrono.range(ChronoField.MONTH_OF_YEAR);
        if (valueRangeRange.isFixed() && valueRangeRange.isIntValue()) {
            return (valueRangeRange.getMaximum() - valueRangeRange.getMinimum()) + 1;
        }
        return -1L;
    }

    @Override // j$.time.temporal.TemporalAmount
    public Temporal addTo(Temporal temporal) {
        validateChrono(temporal);
        if (this.months == 0) {
            int i = this.years;
            if (i != 0) {
                temporal = temporal.plus(i, ChronoUnit.YEARS);
            }
        } else {
            long jMonthRange = monthRange();
            if (jMonthRange > 0) {
                temporal = temporal.plus((((long) this.years) * jMonthRange) + ((long) this.months), ChronoUnit.MONTHS);
            } else {
                int i2 = this.years;
                if (i2 != 0) {
                    temporal = temporal.plus(i2, ChronoUnit.YEARS);
                }
                temporal = temporal.plus(this.months, ChronoUnit.MONTHS);
            }
        }
        int i3 = this.days;
        return i3 != 0 ? temporal.plus(i3, ChronoUnit.DAYS) : temporal;
    }

    private void validateChrono(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
        if (chronology == null || this.chrono.equals(chronology)) {
            return;
        }
        throw new DateTimeException("Chronology mismatch, expected: " + this.chrono.getId() + ", actual: " + chronology.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChronoPeriodImpl) {
            ChronoPeriodImpl chronoPeriodImpl = (ChronoPeriodImpl) obj;
            if (this.years == chronoPeriodImpl.years && this.months == chronoPeriodImpl.months && this.days == chronoPeriodImpl.days && this.chrono.equals(chronoPeriodImpl.chrono)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.years + Integer.rotateLeft(this.months, 8)) + Integer.rotateLeft(this.days, 16)) ^ this.chrono.hashCode();
    }

    public String toString() {
        if (isZero()) {
            return getChronology().toString() + " P0D";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getChronology().toString());
        sb.append(' ');
        sb.append('P');
        int i = this.years;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.months;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.days;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }

    protected Object writeReplace() {
        return new Ser((byte) 9, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.chrono.getId());
        dataOutput.writeInt(this.years);
        dataOutput.writeInt(this.months);
        dataOutput.writeInt(this.days);
    }

    static ChronoPeriodImpl readExternal(DataInput dataInput) {
        return new ChronoPeriodImpl(Chronology.CC.of(dataInput.readUTF()), dataInput.readInt(), dataInput.readInt(), dataInput.readInt());
    }
}

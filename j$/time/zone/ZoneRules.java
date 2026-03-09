package j$.time.zone;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.Clock;
import j$.time.DesugarLocalDate$$ExternalSyntheticBackport0;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.ZoneOffset;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
public final class ZoneRules implements Serializable {
    private static final long serialVersionUID = 3044319355680032515L;
    private final ZoneOffsetTransitionRule[] lastRules;
    private final transient ConcurrentMap lastRulesCache = new ConcurrentHashMap();
    private final long[] savingsInstantTransitions;
    private final LocalDateTime[] savingsLocalTransitions;
    private final ZoneOffset[] standardOffsets;
    private final long[] standardTransitions;
    private final TimeZone timeZone;
    private final ZoneOffset[] wallOffsets;
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private static final ZoneOffsetTransitionRule[] EMPTY_LASTRULES = new ZoneOffsetTransitionRule[0];
    private static final LocalDateTime[] EMPTY_LDT_ARRAY = new LocalDateTime[0];
    private static final ZoneOffsetTransition[] NO_TRANSITIONS = new ZoneOffsetTransition[0];

    public static ZoneRules of(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
        return new ZoneRules(zoneOffset);
    }

    private ZoneRules(long[] jArr, ZoneOffset[] zoneOffsetArr, long[] jArr2, ZoneOffset[] zoneOffsetArr2, ZoneOffsetTransitionRule[] zoneOffsetTransitionRuleArr) {
        this.standardTransitions = jArr;
        this.standardOffsets = zoneOffsetArr;
        this.savingsInstantTransitions = jArr2;
        this.wallOffsets = zoneOffsetArr2;
        this.lastRules = zoneOffsetTransitionRuleArr;
        if (jArr2.length == 0) {
            this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        } else {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < jArr2.length) {
                int i2 = i + 1;
                ZoneOffsetTransition zoneOffsetTransition = new ZoneOffsetTransition(jArr2[i], zoneOffsetArr2[i], zoneOffsetArr2[i2]);
                if (zoneOffsetTransition.isGap()) {
                    arrayList.add(zoneOffsetTransition.getDateTimeBefore());
                    arrayList.add(zoneOffsetTransition.getDateTimeAfter());
                } else {
                    arrayList.add(zoneOffsetTransition.getDateTimeAfter());
                    arrayList.add(zoneOffsetTransition.getDateTimeBefore());
                }
                i = i2;
            }
            this.savingsLocalTransitions = (LocalDateTime[]) arrayList.toArray(new LocalDateTime[arrayList.size()]);
        }
        this.timeZone = null;
    }

    private ZoneRules(ZoneOffset zoneOffset) {
        ZoneOffset[] zoneOffsetArr = {zoneOffset};
        this.standardOffsets = zoneOffsetArr;
        long[] jArr = EMPTY_LONG_ARRAY;
        this.standardTransitions = jArr;
        this.savingsInstantTransitions = jArr;
        this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        this.wallOffsets = zoneOffsetArr;
        this.lastRules = EMPTY_LASTRULES;
        this.timeZone = null;
    }

    ZoneRules(TimeZone timeZone) {
        ZoneOffset[] zoneOffsetArr = {offsetFromMillis(timeZone.getRawOffset())};
        this.standardOffsets = zoneOffsetArr;
        long[] jArr = EMPTY_LONG_ARRAY;
        this.standardTransitions = jArr;
        this.savingsInstantTransitions = jArr;
        this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        this.wallOffsets = zoneOffsetArr;
        this.lastRules = EMPTY_LASTRULES;
        this.timeZone = timeZone;
    }

    private static ZoneOffset offsetFromMillis(int i) {
        return ZoneOffset.ofTotalSeconds(i / 1000);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(this.timeZone != null ? (byte) 100 : (byte) 1, this);
    }

    void writeExternal(DataOutput dataOutput) {
        dataOutput.writeInt(this.standardTransitions.length);
        for (long j : this.standardTransitions) {
            Ser.writeEpochSec(j, dataOutput);
        }
        for (ZoneOffset zoneOffset : this.standardOffsets) {
            Ser.writeOffset(zoneOffset, dataOutput);
        }
        dataOutput.writeInt(this.savingsInstantTransitions.length);
        for (long j2 : this.savingsInstantTransitions) {
            Ser.writeEpochSec(j2, dataOutput);
        }
        for (ZoneOffset zoneOffset2 : this.wallOffsets) {
            Ser.writeOffset(zoneOffset2, dataOutput);
        }
        dataOutput.writeByte(this.lastRules.length);
        for (ZoneOffsetTransitionRule zoneOffsetTransitionRule : this.lastRules) {
            zoneOffsetTransitionRule.writeExternal(dataOutput);
        }
    }

    void writeExternalTimeZone(DataOutput dataOutput) {
        dataOutput.writeUTF(this.timeZone.getID());
    }

    static ZoneRules readExternal(DataInput dataInput) throws IOException {
        long[] jArr;
        long[] jArr2;
        int i = dataInput.readInt();
        if (i == 0) {
            jArr = EMPTY_LONG_ARRAY;
        } else {
            jArr = new long[i];
        }
        long[] jArr3 = jArr;
        for (int i2 = 0; i2 < i; i2++) {
            jArr3[i2] = Ser.readEpochSec(dataInput);
        }
        int i3 = i + 1;
        ZoneOffset[] zoneOffsetArr = new ZoneOffset[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            zoneOffsetArr[i4] = Ser.readOffset(dataInput);
        }
        int i5 = dataInput.readInt();
        if (i5 == 0) {
            jArr2 = EMPTY_LONG_ARRAY;
        } else {
            jArr2 = new long[i5];
        }
        long[] jArr4 = jArr2;
        for (int i6 = 0; i6 < i5; i6++) {
            jArr4[i6] = Ser.readEpochSec(dataInput);
        }
        int i7 = i5 + 1;
        ZoneOffset[] zoneOffsetArr2 = new ZoneOffset[i7];
        for (int i8 = 0; i8 < i7; i8++) {
            zoneOffsetArr2[i8] = Ser.readOffset(dataInput);
        }
        int i9 = dataInput.readByte();
        ZoneOffsetTransitionRule[] zoneOffsetTransitionRuleArr = i9 == 0 ? EMPTY_LASTRULES : new ZoneOffsetTransitionRule[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            zoneOffsetTransitionRuleArr[i10] = ZoneOffsetTransitionRule.readExternal(dataInput);
        }
        return new ZoneRules(jArr3, zoneOffsetArr, jArr4, zoneOffsetArr2, zoneOffsetTransitionRuleArr);
    }

    static ZoneRules readExternalTimeZone(DataInput dataInput) {
        return new ZoneRules(TimeZone.getTimeZone(dataInput.readUTF()));
    }

    public boolean isFixedOffset() {
        TimeZone timeZone = this.timeZone;
        return timeZone != null ? !timeZone.useDaylightTime() && this.timeZone.getDSTSavings() == 0 && previousTransition(Instant.now()) == null : this.savingsInstantTransitions.length == 0;
    }

    public ZoneOffset getOffset(Instant instant) {
        TimeZone timeZone = this.timeZone;
        if (timeZone != null) {
            return offsetFromMillis(timeZone.getOffset(instant.toEpochMilli()));
        }
        if (this.savingsInstantTransitions.length == 0) {
            return this.standardOffsets[0];
        }
        long epochSecond = instant.getEpochSecond();
        if (this.lastRules.length > 0) {
            if (epochSecond > this.savingsInstantTransitions[r7.length - 1]) {
                ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray = findTransitionArray(findYear(epochSecond, this.wallOffsets[r7.length - 1]));
                ZoneOffsetTransition zoneOffsetTransition = null;
                for (int i = 0; i < zoneOffsetTransitionArrFindTransitionArray.length; i++) {
                    zoneOffsetTransition = zoneOffsetTransitionArrFindTransitionArray[i];
                    if (epochSecond < zoneOffsetTransition.toEpochSecond()) {
                        return zoneOffsetTransition.getOffsetBefore();
                    }
                }
                return zoneOffsetTransition.getOffsetAfter();
            }
        }
        int iBinarySearch = Arrays.binarySearch(this.savingsInstantTransitions, epochSecond);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        }
        return this.wallOffsets[iBinarySearch + 1];
    }

    public List getValidOffsets(LocalDateTime localDateTime) {
        Object offsetInfo = getOffsetInfo(localDateTime);
        if (offsetInfo instanceof ZoneOffsetTransition) {
            return ((ZoneOffsetTransition) offsetInfo).getValidOffsets();
        }
        return Collections.singletonList((ZoneOffset) offsetInfo);
    }

    public ZoneOffsetTransition getTransition(LocalDateTime localDateTime) {
        Object offsetInfo = getOffsetInfo(localDateTime);
        if (offsetInfo instanceof ZoneOffsetTransition) {
            return (ZoneOffsetTransition) offsetInfo;
        }
        return null;
    }

    private Object getOffsetInfo(LocalDateTime localDateTime) {
        Object obj = null;
        int i = 0;
        if (this.timeZone != null) {
            ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray = findTransitionArray(localDateTime.getYear());
            if (zoneOffsetTransitionArrFindTransitionArray.length == 0) {
                return offsetFromMillis(this.timeZone.getOffset(localDateTime.toEpochSecond(this.standardOffsets[0]) * 1000));
            }
            int length = zoneOffsetTransitionArrFindTransitionArray.length;
            while (i < length) {
                ZoneOffsetTransition zoneOffsetTransition = zoneOffsetTransitionArrFindTransitionArray[i];
                Object objFindOffsetInfo = findOffsetInfo(localDateTime, zoneOffsetTransition);
                if ((objFindOffsetInfo instanceof ZoneOffsetTransition) || objFindOffsetInfo.equals(zoneOffsetTransition.getOffsetBefore())) {
                    return objFindOffsetInfo;
                }
                i++;
                obj = objFindOffsetInfo;
            }
            return obj;
        }
        if (this.savingsInstantTransitions.length == 0) {
            return this.standardOffsets[0];
        }
        if (this.lastRules.length > 0) {
            if (localDateTime.isAfter(this.savingsLocalTransitions[r0.length - 1])) {
                ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray2 = findTransitionArray(localDateTime.getYear());
                int length2 = zoneOffsetTransitionArrFindTransitionArray2.length;
                while (i < length2) {
                    ZoneOffsetTransition zoneOffsetTransition2 = zoneOffsetTransitionArrFindTransitionArray2[i];
                    Object objFindOffsetInfo2 = findOffsetInfo(localDateTime, zoneOffsetTransition2);
                    if ((objFindOffsetInfo2 instanceof ZoneOffsetTransition) || objFindOffsetInfo2.equals(zoneOffsetTransition2.getOffsetBefore())) {
                        return objFindOffsetInfo2;
                    }
                    i++;
                    obj = objFindOffsetInfo2;
                }
                return obj;
            }
        }
        int iBinarySearch = Arrays.binarySearch(this.savingsLocalTransitions, localDateTime);
        if (iBinarySearch == -1) {
            return this.wallOffsets[0];
        }
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        } else {
            Object[] objArr = this.savingsLocalTransitions;
            if (iBinarySearch < objArr.length - 1) {
                int i2 = iBinarySearch + 1;
                if (objArr[iBinarySearch].equals(objArr[i2])) {
                    iBinarySearch = i2;
                }
            }
        }
        if ((iBinarySearch & 1) == 0) {
            LocalDateTime[] localDateTimeArr = this.savingsLocalTransitions;
            LocalDateTime localDateTime2 = localDateTimeArr[iBinarySearch];
            LocalDateTime localDateTime3 = localDateTimeArr[iBinarySearch + 1];
            ZoneOffset[] zoneOffsetArr = this.wallOffsets;
            int i3 = iBinarySearch / 2;
            ZoneOffset zoneOffset = zoneOffsetArr[i3];
            ZoneOffset zoneOffset2 = zoneOffsetArr[i3 + 1];
            if (zoneOffset2.getTotalSeconds() > zoneOffset.getTotalSeconds()) {
                return new ZoneOffsetTransition(localDateTime2, zoneOffset, zoneOffset2);
            }
            return new ZoneOffsetTransition(localDateTime3, zoneOffset, zoneOffset2);
        }
        return this.wallOffsets[(iBinarySearch / 2) + 1];
    }

    private Object findOffsetInfo(LocalDateTime localDateTime, ZoneOffsetTransition zoneOffsetTransition) {
        LocalDateTime dateTimeBefore = zoneOffsetTransition.getDateTimeBefore();
        if (zoneOffsetTransition.isGap()) {
            if (localDateTime.isBefore(dateTimeBefore)) {
                return zoneOffsetTransition.getOffsetBefore();
            }
            if (!localDateTime.isBefore(zoneOffsetTransition.getDateTimeAfter())) {
                return zoneOffsetTransition.getOffsetAfter();
            }
        } else {
            if (!localDateTime.isBefore(dateTimeBefore)) {
                return zoneOffsetTransition.getOffsetAfter();
            }
            if (localDateTime.isBefore(zoneOffsetTransition.getDateTimeAfter())) {
                return zoneOffsetTransition.getOffsetBefore();
            }
        }
        return zoneOffsetTransition;
    }

    private ZoneOffsetTransition[] findTransitionArray(int i) {
        Integer numValueOf = Integer.valueOf(i);
        ZoneOffsetTransition[] zoneOffsetTransitionArr = (ZoneOffsetTransition[]) this.lastRulesCache.get(numValueOf);
        if (zoneOffsetTransitionArr != null) {
            return zoneOffsetTransitionArr;
        }
        if (this.timeZone == null) {
            ZoneOffsetTransitionRule[] zoneOffsetTransitionRuleArr = this.lastRules;
            ZoneOffsetTransition[] zoneOffsetTransitionArr2 = new ZoneOffsetTransition[zoneOffsetTransitionRuleArr.length];
            for (int i2 = 0; i2 < zoneOffsetTransitionRuleArr.length; i2++) {
                zoneOffsetTransitionArr2[i2] = zoneOffsetTransitionRuleArr[i2].createTransition(i);
            }
            if (i < 2100) {
                this.lastRulesCache.putIfAbsent(numValueOf, zoneOffsetTransitionArr2);
            }
            return zoneOffsetTransitionArr2;
        }
        if (i < 1800) {
            return NO_TRANSITIONS;
        }
        long epochSecond = LocalDateTime.of(i - 1, 12, 31, 0, 0).toEpochSecond(this.standardOffsets[0]);
        long j = 1000;
        int offset = this.timeZone.getOffset(epochSecond * 1000);
        long j2 = 31968000 + epochSecond;
        ZoneOffsetTransition[] zoneOffsetTransitionArr3 = NO_TRANSITIONS;
        while (epochSecond < j2) {
            long j3 = 7776000 + epochSecond;
            long j4 = j;
            if (offset != this.timeZone.getOffset(j3 * j4)) {
                while (j3 - epochSecond > 1) {
                    long j5 = epochSecond;
                    long jM = DesugarLocalDate$$ExternalSyntheticBackport0.m(j3 + epochSecond, 2L);
                    if (this.timeZone.getOffset(jM * j4) == offset) {
                        epochSecond = jM;
                    } else {
                        j3 = jM;
                        epochSecond = j5;
                    }
                }
                long j6 = epochSecond;
                epochSecond = this.timeZone.getOffset(j6 * j4) != offset ? j6 : j3;
                ZoneOffset zoneOffsetOffsetFromMillis = offsetFromMillis(offset);
                int offset2 = this.timeZone.getOffset(epochSecond * j4);
                ZoneOffset zoneOffsetOffsetFromMillis2 = offsetFromMillis(offset2);
                if (findYear(epochSecond, zoneOffsetOffsetFromMillis2) == i) {
                    ZoneOffsetTransition[] zoneOffsetTransitionArr4 = (ZoneOffsetTransition[]) Arrays.copyOf(zoneOffsetTransitionArr3, zoneOffsetTransitionArr3.length + 1);
                    zoneOffsetTransitionArr4[zoneOffsetTransitionArr4.length - 1] = new ZoneOffsetTransition(epochSecond, zoneOffsetOffsetFromMillis, zoneOffsetOffsetFromMillis2);
                    offset = offset2;
                    zoneOffsetTransitionArr3 = zoneOffsetTransitionArr4;
                } else {
                    offset = offset2;
                }
            } else {
                epochSecond = j3;
            }
            j = j4;
        }
        if (1916 <= i && i < 2100) {
            this.lastRulesCache.putIfAbsent(numValueOf, zoneOffsetTransitionArr3);
        }
        return zoneOffsetTransitionArr3;
    }

    public ZoneOffset getStandardOffset(Instant instant) {
        TimeZone timeZone = this.timeZone;
        if (timeZone != null) {
            return offsetFromMillis(timeZone.getRawOffset());
        }
        if (this.savingsInstantTransitions.length == 0) {
            return this.standardOffsets[0];
        }
        int iBinarySearch = Arrays.binarySearch(this.standardTransitions, instant.getEpochSecond());
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        }
        return this.standardOffsets[iBinarySearch + 1];
    }

    public boolean isDaylightSavings(Instant instant) {
        return !getStandardOffset(instant).equals(getOffset(instant));
    }

    public boolean isValidOffset(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return getValidOffsets(localDateTime).contains(zoneOffset);
    }

    public ZoneOffsetTransition previousTransition(Instant instant) {
        if (this.timeZone != null) {
            long epochSecond = instant.getEpochSecond();
            if (instant.getNano() > 0 && epochSecond < Long.MAX_VALUE) {
                epochSecond++;
            }
            int iFindYear = findYear(epochSecond, getOffset(instant));
            ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray = findTransitionArray(iFindYear);
            for (int length = zoneOffsetTransitionArrFindTransitionArray.length - 1; length >= 0; length--) {
                if (epochSecond > zoneOffsetTransitionArrFindTransitionArray[length].toEpochSecond()) {
                    return zoneOffsetTransitionArrFindTransitionArray[length];
                }
            }
            if (iFindYear > 1800) {
                ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray2 = findTransitionArray(iFindYear - 1);
                for (int length2 = zoneOffsetTransitionArrFindTransitionArray2.length - 1; length2 >= 0; length2--) {
                    if (epochSecond > zoneOffsetTransitionArrFindTransitionArray2[length2].toEpochSecond()) {
                        return zoneOffsetTransitionArrFindTransitionArray2[length2];
                    }
                }
                int offset = this.timeZone.getOffset((epochSecond - 1) * 1000);
                long epochDay = LocalDate.of(1800, 1, 1).toEpochDay() * 86400;
                for (long jMin = Math.min(epochSecond - 31104000, (Clock.systemUTC().millis() / 1000) + 31968000); epochDay <= jMin; jMin -= 7776000) {
                    int offset2 = this.timeZone.getOffset(jMin * 1000);
                    if (offset != offset2) {
                        int iFindYear2 = findYear(jMin, offsetFromMillis(offset2));
                        ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray3 = findTransitionArray(iFindYear2 + 1);
                        for (int length3 = zoneOffsetTransitionArrFindTransitionArray3.length - 1; length3 >= 0; length3--) {
                            if (epochSecond > zoneOffsetTransitionArrFindTransitionArray3[length3].toEpochSecond()) {
                                return zoneOffsetTransitionArrFindTransitionArray3[length3];
                            }
                        }
                        ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray4 = findTransitionArray(iFindYear2);
                        return zoneOffsetTransitionArrFindTransitionArray4[zoneOffsetTransitionArrFindTransitionArray4.length - 1];
                    }
                }
            }
            return null;
        }
        if (this.savingsInstantTransitions.length == 0) {
            return null;
        }
        long epochSecond2 = instant.getEpochSecond();
        if (instant.getNano() > 0 && epochSecond2 < Long.MAX_VALUE) {
            epochSecond2++;
        }
        long[] jArr = this.savingsInstantTransitions;
        long j = jArr[jArr.length - 1];
        if (this.lastRules.length > 0 && epochSecond2 > j) {
            ZoneOffset[] zoneOffsetArr = this.wallOffsets;
            ZoneOffset zoneOffset = zoneOffsetArr[zoneOffsetArr.length - 1];
            int iFindYear3 = findYear(epochSecond2, zoneOffset);
            ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray5 = findTransitionArray(iFindYear3);
            for (int length4 = zoneOffsetTransitionArrFindTransitionArray5.length - 1; length4 >= 0; length4--) {
                if (epochSecond2 > zoneOffsetTransitionArrFindTransitionArray5[length4].toEpochSecond()) {
                    return zoneOffsetTransitionArrFindTransitionArray5[length4];
                }
            }
            int i = iFindYear3 - 1;
            if (i > findYear(j, zoneOffset)) {
                ZoneOffsetTransition[] zoneOffsetTransitionArrFindTransitionArray6 = findTransitionArray(i);
                return zoneOffsetTransitionArrFindTransitionArray6[zoneOffsetTransitionArrFindTransitionArray6.length - 1];
            }
        }
        int iBinarySearch = Arrays.binarySearch(this.savingsInstantTransitions, epochSecond2);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 1;
        }
        if (iBinarySearch <= 0) {
            return null;
        }
        int i2 = iBinarySearch - 1;
        long j2 = this.savingsInstantTransitions[i2];
        ZoneOffset[] zoneOffsetArr2 = this.wallOffsets;
        return new ZoneOffsetTransition(j2, zoneOffsetArr2[i2], zoneOffsetArr2[iBinarySearch]);
    }

    private int findYear(long j, ZoneOffset zoneOffset) {
        return LocalDate.ofEpochDay(DesugarLocalDate$$ExternalSyntheticBackport0.m(j + ((long) zoneOffset.getTotalSeconds()), 86400)).getYear();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneRules) {
            ZoneRules zoneRules = (ZoneRules) obj;
            if (Objects.equals(this.timeZone, zoneRules.timeZone) && Arrays.equals(this.standardTransitions, zoneRules.standardTransitions) && Arrays.equals(this.standardOffsets, zoneRules.standardOffsets) && Arrays.equals(this.savingsInstantTransitions, zoneRules.savingsInstantTransitions) && Arrays.equals(this.wallOffsets, zoneRules.wallOffsets) && Arrays.equals(this.lastRules, zoneRules.lastRules)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.timeZone) ^ Arrays.hashCode(this.standardTransitions)) ^ Arrays.hashCode(this.standardOffsets)) ^ Arrays.hashCode(this.savingsInstantTransitions)) ^ Arrays.hashCode(this.wallOffsets)) ^ Arrays.hashCode(this.lastRules);
    }

    public String toString() {
        TimeZone timeZone = this.timeZone;
        if (timeZone != null) {
            return "ZoneRules[timeZone=" + timeZone.getID() + "]";
        }
        return "ZoneRules[currentStandardOffset=" + this.standardOffsets[r0.length - 1] + "]";
    }
}

package j$.time.zone;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.ZoneOffset;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;

/* JADX INFO: loaded from: classes3.dex */
final class Ser implements Externalizable {
    private static final long serialVersionUID = -8885321777449118786L;
    private Object object;
    private byte type;

    public Ser() {
    }

    Ser(byte b, Object obj) {
        this.type = b;
        this.object = obj;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        writeInternal(this.type, this.object, objectOutput);
    }

    private static void writeInternal(byte b, Object obj, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(b);
        if (b == 1) {
            ((ZoneRules) obj).writeExternal(dataOutput);
            return;
        }
        if (b == 2) {
            ((ZoneOffsetTransition) obj).writeExternal(dataOutput);
        } else if (b == 3) {
            ((ZoneOffsetTransitionRule) obj).writeExternal(dataOutput);
        } else {
            if (b == 100) {
                ((ZoneRules) obj).writeExternalTimeZone(dataOutput);
                return;
            }
            throw new InvalidClassException("Unknown serialized type");
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) {
        byte b = objectInput.readByte();
        this.type = b;
        this.object = readInternal(b, objectInput);
    }

    private static Object readInternal(byte b, DataInput dataInput) throws StreamCorruptedException {
        if (b == 1) {
            return ZoneRules.readExternal(dataInput);
        }
        if (b == 2) {
            return ZoneOffsetTransition.readExternal(dataInput);
        }
        if (b == 3) {
            return ZoneOffsetTransitionRule.readExternal(dataInput);
        }
        if (b == 100) {
            return ZoneRules.readExternalTimeZone(dataInput);
        }
        throw new StreamCorruptedException("Unknown serialized type");
    }

    private Object readResolve() {
        return this.object;
    }

    static void writeOffset(ZoneOffset zoneOffset, DataOutput dataOutput) throws IOException {
        int totalSeconds = zoneOffset.getTotalSeconds();
        int i = totalSeconds % TypedValues.Custom.TYPE_INT == 0 ? totalSeconds / TypedValues.Custom.TYPE_INT : 127;
        dataOutput.writeByte(i);
        if (i == 127) {
            dataOutput.writeInt(totalSeconds);
        }
    }

    static ZoneOffset readOffset(DataInput dataInput) throws IOException {
        byte b = dataInput.readByte();
        return b == 127 ? ZoneOffset.ofTotalSeconds(dataInput.readInt()) : ZoneOffset.ofTotalSeconds(b * 900);
    }

    static void writeEpochSec(long j, DataOutput dataOutput) throws IOException {
        if (j >= -4575744000L && j < 10413792000L && j % 900 == 0) {
            int i = (int) ((j + 4575744000L) / 900);
            dataOutput.writeByte((i >>> 16) & 255);
            dataOutput.writeByte((i >>> 8) & 255);
            dataOutput.writeByte(i & 255);
            return;
        }
        dataOutput.writeByte(255);
        dataOutput.writeLong(j);
    }

    static long readEpochSec(DataInput dataInput) {
        int i = dataInput.readByte() & 255;
        if (i == 255) {
            return dataInput.readLong();
        }
        return (((long) (((i << 16) + ((dataInput.readByte() & 255) << 8)) + (dataInput.readByte() & 255))) * 900) - 4575744000L;
    }
}

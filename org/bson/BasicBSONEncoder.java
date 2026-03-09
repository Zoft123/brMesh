package org.bson;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import org.bson.AbstractBsonWriter;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.OutputBuffer;
import org.bson.types.BSONTimestamp;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.CodeWScope;
import org.bson.types.Decimal128;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;
import org.bson.types.Symbol;

/* JADX INFO: loaded from: classes4.dex */
public class BasicBSONEncoder implements BSONEncoder {
    private BsonBinaryWriter bsonWriter;
    private OutputBuffer outputBuffer;

    protected boolean putSpecial(String str, Object obj) {
        return false;
    }

    @Override // org.bson.BSONEncoder
    public byte[] encode(BSONObject bSONObject) {
        BasicOutputBuffer basicOutputBuffer = new BasicOutputBuffer();
        set(basicOutputBuffer);
        putObject(bSONObject);
        done();
        return basicOutputBuffer.toByteArray();
    }

    @Override // org.bson.BSONEncoder
    public void done() {
        this.bsonWriter.close();
        this.bsonWriter = null;
    }

    @Override // org.bson.BSONEncoder
    public void set(OutputBuffer outputBuffer) {
        if (this.bsonWriter != null) {
            throw new IllegalStateException("Performing another operation at this moment");
        }
        this.outputBuffer = outputBuffer;
        this.bsonWriter = new BsonBinaryWriter(outputBuffer);
    }

    protected OutputBuffer getOutputBuffer() {
        return this.outputBuffer;
    }

    protected BsonBinaryWriter getBsonWriter() {
        return this.bsonWriter;
    }

    @Override // org.bson.BSONEncoder
    public int putObject(BSONObject bSONObject) {
        int position = getOutputBuffer().getPosition();
        this.bsonWriter.writeStartDocument();
        if (isTopLevelDocument() && bSONObject.containsField("_id")) {
            _putObjectField("_id", bSONObject.get("_id"));
        }
        for (String str : bSONObject.keySet()) {
            if (!isTopLevelDocument() || !str.equals("_id")) {
                _putObjectField(str, bSONObject.get(str));
            }
        }
        this.bsonWriter.writeEndDocument();
        return getOutputBuffer().getPosition() - position;
    }

    private boolean isTopLevelDocument() {
        return this.bsonWriter.getContext().getParentContext() == null;
    }

    protected void putName(String str) {
        if (this.bsonWriter.getState() == AbstractBsonWriter.State.NAME) {
            this.bsonWriter.writeName(str);
        }
    }

    protected void _putObjectField(String str, Object obj) {
        if ("_transientFields".equals(str)) {
            return;
        }
        if (str.contains("\u0000")) {
            throw new IllegalArgumentException("Document field names can't have a NULL character. (Bad Key: '" + str + "')");
        }
        if ("$where".equals(str) && (obj instanceof String)) {
            putCode(str, new Code((String) obj));
        }
        Object objApplyEncodingHooks = BSON.applyEncodingHooks(obj);
        if (objApplyEncodingHooks == null) {
            putNull(str);
            return;
        }
        if (objApplyEncodingHooks instanceof Date) {
            putDate(str, (Date) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Decimal128) {
            putDecimal128(str, (Decimal128) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Number) {
            putNumber(str, (Number) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Character) {
            putString(str, objApplyEncodingHooks.toString());
            return;
        }
        if (objApplyEncodingHooks instanceof String) {
            putString(str, objApplyEncodingHooks.toString());
            return;
        }
        if (objApplyEncodingHooks instanceof ObjectId) {
            putObjectId(str, (ObjectId) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Boolean) {
            putBoolean(str, (Boolean) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Pattern) {
            putPattern(str, (Pattern) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Iterable) {
            putIterable(str, (Iterable) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof BSONObject) {
            putObject(str, (BSONObject) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Map) {
            putMap(str, (Map) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof byte[]) {
            putBinary(str, (byte[]) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Binary) {
            putBinary(str, (Binary) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof UUID) {
            putUUID(str, (UUID) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks.getClass().isArray()) {
            putArray(str, objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Symbol) {
            putSymbol(str, (Symbol) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof BSONTimestamp) {
            putTimestamp(str, (BSONTimestamp) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof CodeWScope) {
            putCodeWScope(str, (CodeWScope) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Code) {
            putCode(str, (Code) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof MinKey) {
            putMinKey(str);
            return;
        }
        if (objApplyEncodingHooks instanceof MaxKey) {
            putMaxKey(str);
        } else {
            if (putSpecial(str, objApplyEncodingHooks)) {
                return;
            }
            throw new IllegalArgumentException("Can't serialize " + objApplyEncodingHooks.getClass());
        }
    }

    protected void putNull(String str) {
        putName(str);
        this.bsonWriter.writeNull();
    }

    protected void putUndefined(String str) {
        putName(str);
        this.bsonWriter.writeUndefined();
    }

    protected void putTimestamp(String str, BSONTimestamp bSONTimestamp) {
        putName(str);
        this.bsonWriter.writeTimestamp(new BsonTimestamp(bSONTimestamp.getTime(), bSONTimestamp.getInc()));
    }

    protected void putCode(String str, Code code) {
        putName(str);
        this.bsonWriter.writeJavaScript(code.getCode());
    }

    protected void putCodeWScope(String str, CodeWScope codeWScope) {
        putName(str);
        this.bsonWriter.writeJavaScriptWithScope(codeWScope.getCode());
        putObject(codeWScope.getScope());
    }

    protected void putBoolean(String str, Boolean bool) {
        putName(str);
        this.bsonWriter.writeBoolean(bool.booleanValue());
    }

    protected void putDate(String str, Date date) {
        putName(str);
        this.bsonWriter.writeDateTime(date.getTime());
    }

    protected void putNumber(String str, Number number) {
        putName(str);
        if ((number instanceof Integer) || (number instanceof Short) || (number instanceof Byte) || (number instanceof AtomicInteger)) {
            this.bsonWriter.writeInt32(number.intValue());
            return;
        }
        if ((number instanceof Long) || (number instanceof AtomicLong)) {
            this.bsonWriter.writeInt64(number.longValue());
        } else if ((number instanceof Float) || (number instanceof Double)) {
            this.bsonWriter.writeDouble(number.doubleValue());
        } else {
            throw new IllegalArgumentException("Can't serialize " + number.getClass());
        }
    }

    protected void putDecimal128(String str, Decimal128 decimal128) {
        putName(str);
        this.bsonWriter.writeDecimal128(decimal128);
    }

    protected void putBinary(String str, byte[] bArr) {
        putName(str);
        this.bsonWriter.writeBinaryData(new BsonBinary(bArr));
    }

    protected void putBinary(String str, Binary binary) {
        putName(str);
        this.bsonWriter.writeBinaryData(new BsonBinary(binary.getType(), binary.getData()));
    }

    protected void putUUID(String str, UUID uuid) {
        putName(str);
        byte[] bArr = new byte[16];
        writeLongToArrayLittleEndian(bArr, 0, uuid.getMostSignificantBits());
        writeLongToArrayLittleEndian(bArr, 8, uuid.getLeastSignificantBits());
        this.bsonWriter.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_LEGACY, bArr));
    }

    protected void putSymbol(String str, Symbol symbol) {
        putName(str);
        this.bsonWriter.writeSymbol(symbol.getSymbol());
    }

    protected void putString(String str, String str2) {
        putName(str);
        this.bsonWriter.writeString(str2);
    }

    protected void putPattern(String str, Pattern pattern) {
        putName(str);
        this.bsonWriter.writeRegularExpression(new BsonRegularExpression(pattern.pattern(), BSON.regexFlags(pattern.flags())));
    }

    protected void putObjectId(String str, ObjectId objectId) {
        putName(str);
        this.bsonWriter.writeObjectId(objectId);
    }

    protected void putArray(String str, Object obj) {
        putName(str);
        this.bsonWriter.writeStartArray();
        int i = 0;
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int length = iArr.length;
            while (i < length) {
                this.bsonWriter.writeInt32(iArr[i]);
                i++;
            }
        } else if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            int length2 = jArr.length;
            while (i < length2) {
                this.bsonWriter.writeInt64(jArr[i]);
                i++;
            }
        } else if (obj instanceof float[]) {
            int length3 = ((float[]) obj).length;
            while (i < length3) {
                this.bsonWriter.writeDouble(r7[i]);
                i++;
            }
        } else if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            int length4 = sArr.length;
            while (i < length4) {
                this.bsonWriter.writeInt32(sArr[i]);
                i++;
            }
        } else if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            int length5 = bArr.length;
            while (i < length5) {
                this.bsonWriter.writeInt32(bArr[i]);
                i++;
            }
        } else if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            int length6 = dArr.length;
            while (i < length6) {
                this.bsonWriter.writeDouble(dArr[i]);
                i++;
            }
        } else if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            int length7 = zArr.length;
            while (i < length7) {
                this.bsonWriter.writeBoolean(zArr[i]);
                i++;
            }
        } else if (obj instanceof String[]) {
            String[] strArr = (String[]) obj;
            int length8 = strArr.length;
            while (i < length8) {
                this.bsonWriter.writeString(strArr[i]);
                i++;
            }
        } else {
            int length9 = Array.getLength(obj);
            while (i < length9) {
                _putObjectField(String.valueOf(i), Array.get(obj, i));
                i++;
            }
        }
        this.bsonWriter.writeEndArray();
    }

    protected void putIterable(String str, Iterable iterable) {
        putName(str);
        this.bsonWriter.writeStartArray();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            _putObjectField(String.valueOf(0), it.next());
        }
        this.bsonWriter.writeEndArray();
    }

    protected void putMap(String str, Map map) {
        putName(str);
        this.bsonWriter.writeStartDocument();
        for (Map.Entry entry : map.entrySet()) {
            _putObjectField((String) entry.getKey(), entry.getValue());
        }
        this.bsonWriter.writeEndDocument();
    }

    protected int putObject(String str, BSONObject bSONObject) {
        putName(str);
        return putObject(bSONObject);
    }

    protected void putMinKey(String str) {
        putName(str);
        this.bsonWriter.writeMinKey();
    }

    protected void putMaxKey(String str) {
        putName(str);
        this.bsonWriter.writeMaxKey();
    }

    private static void writeLongToArrayLittleEndian(byte[] bArr, int i, long j) {
        bArr[i] = (byte) (j & 255);
        bArr[i + 1] = (byte) ((j >> 8) & 255);
        bArr[i + 2] = (byte) ((j >> 16) & 255);
        bArr[i + 3] = (byte) ((j >> 24) & 255);
        bArr[i + 4] = (byte) ((j >> 32) & 255);
        bArr[i + 5] = (byte) ((j >> 40) & 255);
        bArr[i + 6] = (byte) ((j >> 48) & 255);
        bArr[i + 7] = (byte) ((j >> 56) & 255);
    }
}

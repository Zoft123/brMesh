package org.bson;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.io.BasicOutputBuffer;
import org.bson.json.JsonReader;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDocument extends BsonValue implements Map<String, BsonValue>, Cloneable, Bson, Serializable {
    private static final long serialVersionUID = 1;
    private final Map<String, BsonValue> map = new LinkedHashMap();

    @Override // org.bson.conversions.Bson
    public <C> BsonDocument toBsonDocument(Class<C> cls, CodecRegistry codecRegistry) {
        return this;
    }

    public static BsonDocument parse(String str) {
        return new BsonDocumentCodec().decode((BsonReader) new JsonReader(str), DecoderContext.builder().build());
    }

    public BsonDocument(List<BsonElement> list) {
        for (BsonElement bsonElement : list) {
            put(bsonElement.getName(), bsonElement.getValue());
        }
    }

    public BsonDocument(String str, BsonValue bsonValue) {
        put(str, bsonValue);
    }

    public BsonDocument() {
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DOCUMENT;
    }

    @Override // java.util.Map
    public int size() {
        return this.map.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override // java.util.Map
    public BsonValue get(Object obj) {
        return this.map.get(obj);
    }

    public BsonDocument getDocument(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asDocument();
    }

    public BsonArray getArray(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asArray();
    }

    public BsonNumber getNumber(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asNumber();
    }

    public BsonInt32 getInt32(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asInt32();
    }

    public BsonInt64 getInt64(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asInt64();
    }

    public BsonDecimal128 getDecimal128(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asDecimal128();
    }

    public BsonDouble getDouble(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asDouble();
    }

    public BsonBoolean getBoolean(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asBoolean();
    }

    public BsonString getString(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asString();
    }

    public BsonDateTime getDateTime(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asDateTime();
    }

    public BsonTimestamp getTimestamp(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asTimestamp();
    }

    public BsonObjectId getObjectId(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asObjectId();
    }

    public BsonRegularExpression getRegularExpression(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asRegularExpression();
    }

    public BsonBinary getBinary(Object obj) {
        throwIfKeyAbsent(obj);
        return get(obj).asBinary();
    }

    public boolean isNull(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isNull();
        }
        return false;
    }

    public boolean isDocument(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isDocument();
        }
        return false;
    }

    public boolean isArray(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isArray();
        }
        return false;
    }

    public boolean isNumber(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isNumber();
        }
        return false;
    }

    public boolean isInt32(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isInt32();
        }
        return false;
    }

    public boolean isInt64(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isInt64();
        }
        return false;
    }

    public boolean isDecimal128(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isDecimal128();
        }
        return false;
    }

    public boolean isDouble(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isDouble();
        }
        return false;
    }

    public boolean isBoolean(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isBoolean();
        }
        return false;
    }

    public boolean isString(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isString();
        }
        return false;
    }

    public boolean isDateTime(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isDateTime();
        }
        return false;
    }

    public boolean isTimestamp(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isTimestamp();
        }
        return false;
    }

    public boolean isObjectId(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isObjectId();
        }
        return false;
    }

    public boolean isBinary(Object obj) {
        if (containsKey(obj)) {
            return get(obj).isBinary();
        }
        return false;
    }

    public BsonValue get(Object obj, BsonValue bsonValue) {
        BsonValue bsonValue2 = get(obj);
        return bsonValue2 != null ? bsonValue2 : bsonValue;
    }

    public BsonDocument getDocument(Object obj, BsonDocument bsonDocument) {
        return !containsKey(obj) ? bsonDocument : get(obj).asDocument();
    }

    public BsonArray getArray(Object obj, BsonArray bsonArray) {
        return !containsKey(obj) ? bsonArray : get(obj).asArray();
    }

    public BsonNumber getNumber(Object obj, BsonNumber bsonNumber) {
        return !containsKey(obj) ? bsonNumber : get(obj).asNumber();
    }

    public BsonInt32 getInt32(Object obj, BsonInt32 bsonInt32) {
        return !containsKey(obj) ? bsonInt32 : get(obj).asInt32();
    }

    public BsonInt64 getInt64(Object obj, BsonInt64 bsonInt64) {
        return !containsKey(obj) ? bsonInt64 : get(obj).asInt64();
    }

    public BsonDecimal128 getDecimal128(Object obj, BsonDecimal128 bsonDecimal128) {
        return !containsKey(obj) ? bsonDecimal128 : get(obj).asDecimal128();
    }

    public BsonDouble getDouble(Object obj, BsonDouble bsonDouble) {
        return !containsKey(obj) ? bsonDouble : get(obj).asDouble();
    }

    public BsonBoolean getBoolean(Object obj, BsonBoolean bsonBoolean) {
        return !containsKey(obj) ? bsonBoolean : get(obj).asBoolean();
    }

    public BsonString getString(Object obj, BsonString bsonString) {
        return !containsKey(obj) ? bsonString : get(obj).asString();
    }

    public BsonDateTime getDateTime(Object obj, BsonDateTime bsonDateTime) {
        return !containsKey(obj) ? bsonDateTime : get(obj).asDateTime();
    }

    public BsonTimestamp getTimestamp(Object obj, BsonTimestamp bsonTimestamp) {
        return !containsKey(obj) ? bsonTimestamp : get(obj).asTimestamp();
    }

    public BsonObjectId getObjectId(Object obj, BsonObjectId bsonObjectId) {
        return !containsKey(obj) ? bsonObjectId : get(obj).asObjectId();
    }

    public BsonBinary getBinary(Object obj, BsonBinary bsonBinary) {
        return !containsKey(obj) ? bsonBinary : get(obj).asBinary();
    }

    public BsonRegularExpression getRegularExpression(Object obj, BsonRegularExpression bsonRegularExpression) {
        return !containsKey(obj) ? bsonRegularExpression : get(obj).asRegularExpression();
    }

    @Override // java.util.Map
    public BsonValue put(String str, BsonValue bsonValue) {
        if (bsonValue == null) {
            throw new IllegalArgumentException(String.format("The value for key %s can not be null", str));
        }
        if (str.contains("\u0000")) {
            throw new BSONException(String.format("BSON cstring '%s' is not valid because it contains a null character at index %d", str, Integer.valueOf(str.indexOf(0))));
        }
        return this.map.put(str, bsonValue);
    }

    @Override // java.util.Map
    public BsonValue remove(Object obj) {
        return this.map.remove(obj);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends String, ? extends BsonValue> map) {
        for (Map.Entry<? extends String, ? extends BsonValue> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.Map
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override // java.util.Map
    public Collection<BsonValue> values() {
        return this.map.values();
    }

    @Override // java.util.Map
    public Set<Map.Entry<String, BsonValue>> entrySet() {
        return this.map.entrySet();
    }

    public BsonDocument append(String str, BsonValue bsonValue) {
        put(str, bsonValue);
        return this;
    }

    public String getFirstKey() {
        return keySet().iterator().next();
    }

    public BsonReader asBsonReader() {
        return new BsonDocumentReader(this);
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BsonDocument) {
            return entrySet().equals(((BsonDocument) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    public int hashCode() {
        return entrySet().hashCode();
    }

    public String toJson() {
        return toJson(new JsonWriterSettings());
    }

    public String toJson(JsonWriterSettings jsonWriterSettings) {
        StringWriter stringWriter = new StringWriter();
        new BsonDocumentCodec().encode((BsonWriter) new JsonWriter(stringWriter, jsonWriterSettings), this, EncoderContext.builder().build());
        return stringWriter.toString();
    }

    public String toString() {
        return toJson();
    }

    @Override // 
    public BsonDocument clone() {
        BsonDocument bsonDocument = new BsonDocument();
        for (Map.Entry<String, BsonValue> entry : entrySet()) {
            int i = AnonymousClass1.$SwitchMap$org$bson$BsonType[entry.getValue().getBsonType().ordinal()];
            if (i == 1) {
                bsonDocument.put(entry.getKey(), (BsonValue) entry.getValue().asDocument().clone());
            } else if (i == 2) {
                bsonDocument.put(entry.getKey(), (BsonValue) entry.getValue().asArray().clone());
            } else if (i == 3) {
                bsonDocument.put(entry.getKey(), (BsonValue) BsonBinary.clone(entry.getValue().asBinary()));
            } else if (i == 4) {
                bsonDocument.put(entry.getKey(), (BsonValue) BsonJavaScriptWithScope.clone(entry.getValue().asJavaScriptWithScope()));
            } else {
                bsonDocument.put(entry.getKey(), entry.getValue());
            }
        }
        return bsonDocument;
    }

    /* JADX INFO: renamed from: org.bson.BsonDocument$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;

        static {
            int[] iArr = new int[BsonType.values().length];
            $SwitchMap$org$bson$BsonType = iArr;
            try {
                iArr[BsonType.DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BINARY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void throwIfKeyAbsent(Object obj) {
        if (containsKey(obj)) {
            return;
        }
        throw new BsonInvalidOperationException("Document does not contain key " + obj);
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 1;
        private final byte[] bytes;

        SerializationProxy(BsonDocument bsonDocument) {
            BasicOutputBuffer basicOutputBuffer = new BasicOutputBuffer();
            new BsonDocumentCodec().encode((BsonWriter) new BsonBinaryWriter(basicOutputBuffer), bsonDocument, EncoderContext.builder().build());
            this.bytes = new byte[basicOutputBuffer.size()];
            int iPosition = 0;
            for (ByteBuf byteBuf : basicOutputBuffer.getByteBuffers()) {
                System.arraycopy(byteBuf.array(), byteBuf.position(), this.bytes, iPosition, byteBuf.limit());
                iPosition += byteBuf.position();
            }
        }

        private Object readResolve() {
            return new BsonDocumentCodec().decode((BsonReader) new BsonBinaryReader(ByteBuffer.wrap(this.bytes).order(ByteOrder.LITTLE_ENDIAN)), DecoderContext.builder().build());
        }
    }
}

package org.bson;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.bson.assertions.Assertions;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.Decoder;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.RawBsonDocumentCodec;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.ByteBufferBsonInput;
import org.bson.json.JsonReader;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

/* JADX INFO: loaded from: classes4.dex */
public final class RawBsonDocument extends BsonDocument {
    private static final int MIN_BSON_DOCUMENT_SIZE = 5;
    private static final long serialVersionUID = 1;
    private final byte[] bytes;
    private final int length;
    private final int offset;

    public static RawBsonDocument parse(String str) {
        Assertions.notNull("json", str);
        return new RawBsonDocumentCodec().decode((BsonReader) new JsonReader(str), DecoderContext.builder().build());
    }

    public RawBsonDocument(byte[] bArr) {
        this((byte[]) Assertions.notNull("bytes", bArr), 0, bArr.length);
    }

    public RawBsonDocument(byte[] bArr, int i, int i2) {
        Assertions.notNull("bytes", bArr);
        Assertions.isTrueArgument("offset >= 0", i >= 0);
        Assertions.isTrueArgument("offset < bytes.length", i < bArr.length);
        Assertions.isTrueArgument("length <= bytes.length - offset", i2 <= bArr.length - i);
        Assertions.isTrueArgument("length >= 5", i2 >= 5);
        this.bytes = bArr;
        this.offset = i;
        this.length = i2;
    }

    public <T> RawBsonDocument(T t, Codec<T> codec) {
        Assertions.notNull("document", t);
        Assertions.notNull("codec", codec);
        BasicOutputBuffer basicOutputBuffer = new BasicOutputBuffer();
        BsonBinaryWriter bsonBinaryWriter = new BsonBinaryWriter(basicOutputBuffer);
        try {
            codec.encode(bsonBinaryWriter, t, EncoderContext.builder().build());
            this.bytes = basicOutputBuffer.getInternalBuffer();
            this.offset = 0;
            this.length = basicOutputBuffer.getPosition();
        } finally {
            bsonBinaryWriter.close();
        }
    }

    public ByteBuf getByteBuffer() {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.bytes, this.offset, this.length);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        return new ByteBufNIO(byteBufferWrap);
    }

    public <T> T decode(Codec<T> codec) {
        return (T) decode((Decoder) codec);
    }

    public <T> T decode(Decoder<T> decoder) {
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            return decoder.decode(bsonBinaryReaderCreateReader, DecoderContext.builder().build());
        } finally {
            bsonBinaryReaderCreateReader.close();
        }
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public BsonValue put(String str, BsonValue bsonValue) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override // org.bson.BsonDocument
    public BsonDocument append(String str, BsonValue bsonValue) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public void putAll(Map<? extends String, ? extends BsonValue> map) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public BsonValue remove(Object obj) {
        throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean isEmpty() {
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            bsonBinaryReaderCreateReader.readStartDocument();
            if (bsonBinaryReaderCreateReader.readBsonType() == BsonType.END_OF_DOCUMENT) {
                bsonBinaryReaderCreateReader.readEndDocument();
                bsonBinaryReaderCreateReader.close();
                return true;
            }
            bsonBinaryReaderCreateReader.close();
            return false;
        } catch (Throwable th) {
            bsonBinaryReaderCreateReader.close();
            throw th;
        }
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public int size() {
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            bsonBinaryReaderCreateReader.readStartDocument();
            int i = 0;
            while (bsonBinaryReaderCreateReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                i++;
                bsonBinaryReaderCreateReader.readName();
                bsonBinaryReaderCreateReader.skipValue();
            }
            bsonBinaryReaderCreateReader.readEndDocument();
            return i;
        } finally {
            bsonBinaryReaderCreateReader.close();
        }
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public Set<Map.Entry<String, BsonValue>> entrySet() {
        return toBsonDocument().entrySet();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public Collection<BsonValue> values() {
        return toBsonDocument().values();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public Set<String> keySet() {
        return toBsonDocument().keySet();
    }

    @Override // org.bson.BsonDocument
    public String getFirstKey() {
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            bsonBinaryReaderCreateReader.readStartDocument();
            try {
                return bsonBinaryReaderCreateReader.readName();
            } catch (BsonInvalidOperationException unused) {
                throw new NoSuchElementException();
            }
        } finally {
            bsonBinaryReaderCreateReader.close();
        }
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean containsKey(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("key can not be null");
        }
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            bsonBinaryReaderCreateReader.readStartDocument();
            while (bsonBinaryReaderCreateReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (!bsonBinaryReaderCreateReader.readName().equals(obj)) {
                    bsonBinaryReaderCreateReader.skipValue();
                } else {
                    bsonBinaryReaderCreateReader.close();
                    return true;
                }
            }
            bsonBinaryReaderCreateReader.readEndDocument();
            bsonBinaryReaderCreateReader.close();
            return false;
        } catch (Throwable th) {
            bsonBinaryReaderCreateReader.close();
            throw th;
        }
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean containsValue(Object obj) {
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            bsonBinaryReaderCreateReader.readStartDocument();
            while (bsonBinaryReaderCreateReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                bsonBinaryReaderCreateReader.skipName();
                if (RawBsonValueHelper.decode(this.bytes, bsonBinaryReaderCreateReader).equals(obj)) {
                    bsonBinaryReaderCreateReader.close();
                    return true;
                }
            }
            bsonBinaryReaderCreateReader.readEndDocument();
            bsonBinaryReaderCreateReader.close();
            return false;
        } catch (Throwable th) {
            bsonBinaryReaderCreateReader.close();
            throw th;
        }
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public BsonValue get(Object obj) {
        Assertions.notNull(GlobalVariable.KEY, obj);
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            bsonBinaryReaderCreateReader.readStartDocument();
            while (bsonBinaryReaderCreateReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (bsonBinaryReaderCreateReader.readName().equals(obj)) {
                    return RawBsonValueHelper.decode(this.bytes, bsonBinaryReaderCreateReader);
                }
                bsonBinaryReaderCreateReader.skipValue();
            }
            bsonBinaryReaderCreateReader.readEndDocument();
            bsonBinaryReaderCreateReader.close();
            return null;
        } finally {
            bsonBinaryReaderCreateReader.close();
        }
    }

    @Override // org.bson.BsonDocument
    public String toJson() {
        return toJson(new JsonWriterSettings());
    }

    @Override // org.bson.BsonDocument
    public String toJson(JsonWriterSettings jsonWriterSettings) {
        StringWriter stringWriter = new StringWriter();
        new RawBsonDocumentCodec().encode((BsonWriter) new JsonWriter(stringWriter, jsonWriterSettings), this, EncoderContext.builder().build());
        return stringWriter.toString();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean equals(Object obj) {
        return toBsonDocument().equals(obj);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public int hashCode() {
        return toBsonDocument().hashCode();
    }

    @Override // org.bson.BsonDocument
    public BsonDocument clone() {
        return new RawBsonDocument((byte[]) this.bytes.clone(), this.offset, this.length);
    }

    private BsonBinaryReader createReader() {
        return new BsonBinaryReader(new ByteBufferBsonInput(getByteBuffer()));
    }

    private BsonDocument toBsonDocument() {
        BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
        try {
            return new BsonDocumentCodec().decode((BsonReader) bsonBinaryReaderCreateReader, DecoderContext.builder().build());
        } finally {
            bsonBinaryReaderCreateReader.close();
        }
    }

    private Object writeReplace() {
        return new SerializationProxy(this.bytes, this.offset, this.length);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 1;
        private final byte[] bytes;

        SerializationProxy(byte[] bArr, int i, int i2) {
            if (bArr.length == i2) {
                this.bytes = bArr;
                return;
            }
            byte[] bArr2 = new byte[i2];
            this.bytes = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, i2);
        }

        private Object readResolve() {
            return new RawBsonDocument(this.bytes);
        }
    }
}

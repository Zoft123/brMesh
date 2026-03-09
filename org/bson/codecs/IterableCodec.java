package org.bson.codecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.Transformer;
import org.bson.UuidRepresentation;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class IterableCodec implements Codec<Iterable>, OverridableUuidRepresentationCodec<Iterable> {
    private final BsonTypeCodecMap bsonTypeCodecMap;
    private final CodecRegistry registry;
    private final UuidRepresentation uuidRepresentation;
    private final Transformer valueTransformer;

    public IterableCodec(CodecRegistry codecRegistry, BsonTypeClassMap bsonTypeClassMap) {
        this(codecRegistry, bsonTypeClassMap, null);
    }

    public IterableCodec(CodecRegistry codecRegistry, BsonTypeClassMap bsonTypeClassMap, Transformer transformer) {
        this(codecRegistry, new BsonTypeCodecMap((BsonTypeClassMap) Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap), codecRegistry), transformer, UuidRepresentation.JAVA_LEGACY);
    }

    private IterableCodec(CodecRegistry codecRegistry, BsonTypeCodecMap bsonTypeCodecMap, Transformer transformer, UuidRepresentation uuidRepresentation) {
        this.registry = (CodecRegistry) Assertions.notNull("registry", codecRegistry);
        this.bsonTypeCodecMap = bsonTypeCodecMap;
        this.valueTransformer = transformer == null ? new Transformer() { // from class: org.bson.codecs.IterableCodec.1
            @Override // org.bson.Transformer
            public Object transform(Object obj) {
                return obj;
            }
        } : transformer;
        this.uuidRepresentation = uuidRepresentation;
    }

    @Override // org.bson.codecs.OverridableUuidRepresentationCodec
    public Codec<Iterable> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
        return new IterableCodec(this.registry, this.bsonTypeCodecMap, this.valueTransformer, uuidRepresentation);
    }

    @Override // org.bson.codecs.Decoder
    public Iterable decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartArray();
        ArrayList arrayList = new ArrayList();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            arrayList.add(readValue(bsonReader, decoderContext));
        }
        bsonReader.readEndArray();
        return arrayList;
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Iterable iterable, EncoderContext encoderContext) {
        bsonWriter.writeStartArray();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            writeValue(bsonWriter, encoderContext, it.next());
        }
        bsonWriter.writeEndArray();
    }

    @Override // org.bson.codecs.Encoder
    public Class<Iterable> getEncoderClass() {
        return Iterable.class;
    }

    private void writeValue(BsonWriter bsonWriter, EncoderContext encoderContext, Object obj) {
        if (obj == null) {
            bsonWriter.writeNull();
        } else {
            encoderContext.encodeWithChildContext(this.registry.get(obj.getClass()), bsonWriter, obj);
        }
    }

    private Object readValue(BsonReader bsonReader, DecoderContext decoderContext) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        if (currentBsonType == BsonType.NULL) {
            bsonReader.readNull();
            return null;
        }
        Codec<?> codec = this.bsonTypeCodecMap.get(currentBsonType);
        if (currentBsonType == BsonType.BINARY && bsonReader.peekBinarySize() == 16) {
            byte bPeekBinarySubType = bsonReader.peekBinarySubType();
            if (bPeekBinarySubType == 3) {
                if (this.uuidRepresentation == UuidRepresentation.JAVA_LEGACY || this.uuidRepresentation == UuidRepresentation.C_SHARP_LEGACY || this.uuidRepresentation == UuidRepresentation.PYTHON_LEGACY) {
                    codec = this.registry.get(UUID.class);
                }
            } else if (bPeekBinarySubType == 4 && (this.uuidRepresentation == UuidRepresentation.JAVA_LEGACY || this.uuidRepresentation == UuidRepresentation.STANDARD)) {
                codec = this.registry.get(UUID.class);
            }
        }
        return this.valueTransformer.transform(codec.decode(bsonReader, decoderContext));
    }
}

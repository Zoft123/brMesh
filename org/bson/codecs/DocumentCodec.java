package org.bson.codecs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.Transformer;
import org.bson.UuidRepresentation;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class DocumentCodec implements CollectibleCodec<Document>, OverridableUuidRepresentationCodec<Document> {
    private static final String ID_FIELD_NAME = "_id";
    private final BsonTypeCodecMap bsonTypeCodecMap;
    private final IdGenerator idGenerator;
    private final CodecRegistry registry;
    private final UuidRepresentation uuidRepresentation;
    private final Transformer valueTransformer;
    private static final CodecRegistry DEFAULT_REGISTRY = CodecRegistries.fromProviders((List<? extends CodecProvider>) Arrays.asList(new ValueCodecProvider(), new BsonValueCodecProvider(), new DocumentCodecProvider()));
    private static final BsonTypeClassMap DEFAULT_BSON_TYPE_CLASS_MAP = new BsonTypeClassMap();

    public DocumentCodec() {
        this(DEFAULT_REGISTRY);
    }

    public DocumentCodec(CodecRegistry codecRegistry) {
        this(codecRegistry, DEFAULT_BSON_TYPE_CLASS_MAP);
    }

    public DocumentCodec(CodecRegistry codecRegistry, BsonTypeClassMap bsonTypeClassMap) {
        this(codecRegistry, bsonTypeClassMap, null);
    }

    public DocumentCodec(CodecRegistry codecRegistry, BsonTypeClassMap bsonTypeClassMap, Transformer transformer) {
        this(codecRegistry, new BsonTypeCodecMap((BsonTypeClassMap) Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap), codecRegistry), new ObjectIdGenerator(), transformer, UuidRepresentation.JAVA_LEGACY);
    }

    private DocumentCodec(CodecRegistry codecRegistry, BsonTypeCodecMap bsonTypeCodecMap, IdGenerator idGenerator, Transformer transformer, UuidRepresentation uuidRepresentation) {
        this.registry = (CodecRegistry) Assertions.notNull("registry", codecRegistry);
        this.bsonTypeCodecMap = bsonTypeCodecMap;
        this.idGenerator = idGenerator;
        this.valueTransformer = transformer == null ? new Transformer() { // from class: org.bson.codecs.DocumentCodec.1
            @Override // org.bson.Transformer
            public Object transform(Object obj) {
                return obj;
            }
        } : transformer;
        this.uuidRepresentation = uuidRepresentation;
    }

    @Override // org.bson.codecs.OverridableUuidRepresentationCodec
    public Codec<Document> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
        return new DocumentCodec(this.registry, this.bsonTypeCodecMap, this.idGenerator, this.valueTransformer, uuidRepresentation);
    }

    @Override // org.bson.codecs.CollectibleCodec
    public boolean documentHasId(Document document) {
        return document.containsKey(ID_FIELD_NAME);
    }

    @Override // org.bson.codecs.CollectibleCodec
    public BsonValue getDocumentId(Document document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("The document does not contain an _id");
        }
        Object obj = document.get(ID_FIELD_NAME);
        if (obj instanceof BsonValue) {
            return (BsonValue) obj;
        }
        BsonDocument bsonDocument = new BsonDocument();
        BsonDocumentWriter bsonDocumentWriter = new BsonDocumentWriter(bsonDocument);
        bsonDocumentWriter.writeStartDocument();
        bsonDocumentWriter.writeName(ID_FIELD_NAME);
        writeValue(bsonDocumentWriter, EncoderContext.builder().build(), obj);
        bsonDocumentWriter.writeEndDocument();
        return bsonDocument.get((Object) ID_FIELD_NAME);
    }

    @Override // org.bson.codecs.CollectibleCodec
    public Document generateIdIfAbsentFromDocument(Document document) {
        if (!documentHasId(document)) {
            document.put(ID_FIELD_NAME, this.idGenerator.generate());
        }
        return document;
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Document document, EncoderContext encoderContext) {
        writeMap(bsonWriter, document, encoderContext);
    }

    @Override // org.bson.codecs.Decoder
    public Document decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document document = new Document();
        bsonReader.readStartDocument();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            document.put(bsonReader.readName(), readValue(bsonReader, decoderContext));
        }
        bsonReader.readEndDocument();
        return document;
    }

    @Override // org.bson.codecs.Encoder
    public Class<Document> getEncoderClass() {
        return Document.class;
    }

    private void beforeFields(BsonWriter bsonWriter, EncoderContext encoderContext, Map<String, Object> map) {
        if (encoderContext.isEncodingCollectibleDocument() && map.containsKey(ID_FIELD_NAME)) {
            bsonWriter.writeName(ID_FIELD_NAME);
            writeValue(bsonWriter, encoderContext, map.get(ID_FIELD_NAME));
        }
    }

    private boolean skipField(EncoderContext encoderContext, String str) {
        return encoderContext.isEncodingCollectibleDocument() && str.equals(ID_FIELD_NAME);
    }

    private void writeValue(BsonWriter bsonWriter, EncoderContext encoderContext, Object obj) {
        if (obj == null) {
            bsonWriter.writeNull();
            return;
        }
        if (obj instanceof Iterable) {
            writeIterable(bsonWriter, (Iterable) obj, encoderContext.getChildContext());
        } else if (obj instanceof Map) {
            writeMap(bsonWriter, (Map) obj, encoderContext.getChildContext());
        } else {
            encoderContext.encodeWithChildContext(this.registry.get(obj.getClass()), bsonWriter, obj);
        }
    }

    private void writeMap(BsonWriter bsonWriter, Map<String, Object> map, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        beforeFields(bsonWriter, encoderContext, map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!skipField(encoderContext, entry.getKey())) {
                bsonWriter.writeName(entry.getKey());
                writeValue(bsonWriter, encoderContext, entry.getValue());
            }
        }
        bsonWriter.writeEndDocument();
    }

    private void writeIterable(BsonWriter bsonWriter, Iterable<Object> iterable, EncoderContext encoderContext) {
        bsonWriter.writeStartArray();
        Iterator<Object> it = iterable.iterator();
        while (it.hasNext()) {
            writeValue(bsonWriter, encoderContext, it.next());
        }
        bsonWriter.writeEndArray();
    }

    private Object readValue(BsonReader bsonReader, DecoderContext decoderContext) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        if (currentBsonType == BsonType.NULL) {
            bsonReader.readNull();
            return null;
        }
        if (currentBsonType == BsonType.ARRAY) {
            return readList(bsonReader, decoderContext);
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

    private List<Object> readList(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartArray();
        ArrayList arrayList = new ArrayList();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            arrayList.add(readValue(bsonReader, decoderContext));
        }
        bsonReader.readEndArray();
        return arrayList;
    }
}

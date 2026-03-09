package org.bson.codecs;

import java.util.ArrayList;
import org.bson.BsonArray;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class BsonArrayCodec implements Codec<BsonArray> {
    private static final CodecRegistry DEFAULT_REGISTRY = CodecRegistries.fromProviders(new BsonValueCodecProvider());
    private final CodecRegistry codecRegistry;

    public BsonArrayCodec() {
        this(DEFAULT_REGISTRY);
    }

    public BsonArrayCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = (CodecRegistry) Assertions.notNull("codecRegistry", codecRegistry);
    }

    @Override // org.bson.codecs.Decoder
    public BsonArray decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartArray();
        ArrayList arrayList = new ArrayList();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            arrayList.add(readValue(bsonReader, decoderContext));
        }
        bsonReader.readEndArray();
        return new BsonArray(arrayList);
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonArray bsonArray, EncoderContext encoderContext) {
        bsonWriter.writeStartArray();
        for (BsonValue bsonValue : bsonArray) {
            encoderContext.encodeWithChildContext(this.codecRegistry.get(bsonValue.getClass()), bsonWriter, bsonValue);
        }
        bsonWriter.writeEndArray();
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonArray> getEncoderClass() {
        return BsonArray.class;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected BsonValue readValue(BsonReader bsonReader, DecoderContext decoderContext) {
        return (BsonValue) this.codecRegistry.get(BsonValueCodecProvider.getClassForBsonType(bsonReader.getCurrentBsonType())).decode(bsonReader, decoderContext);
    }
}

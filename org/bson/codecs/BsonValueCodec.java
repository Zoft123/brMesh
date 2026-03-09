package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class BsonValueCodec implements Codec<BsonValue> {
    private final CodecRegistry codecRegistry;

    public BsonValueCodec() {
        this(CodecRegistries.fromProviders(new BsonValueCodecProvider()));
    }

    public BsonValueCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.bson.codecs.Decoder
    public BsonValue decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return (BsonValue) this.codecRegistry.get(BsonValueCodecProvider.getClassForBsonType(bsonReader.getCurrentBsonType())).decode(bsonReader, decoderContext);
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonValue bsonValue, EncoderContext encoderContext) {
        encoderContext.encodeWithChildContext(this.codecRegistry.get(bsonValue.getClass()), bsonWriter, bsonValue);
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonValue> getEncoderClass() {
        return BsonValue.class;
    }
}

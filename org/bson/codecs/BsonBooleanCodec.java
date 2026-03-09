package org.bson.codecs;

import org.bson.BsonBoolean;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonBooleanCodec implements Codec<BsonBoolean> {
    @Override // org.bson.codecs.Decoder
    public BsonBoolean decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return BsonBoolean.valueOf(bsonReader.readBoolean());
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonBoolean bsonBoolean, EncoderContext encoderContext) {
        bsonWriter.writeBoolean(bsonBoolean.getValue());
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonBoolean> getEncoderClass() {
        return BsonBoolean.class;
    }
}

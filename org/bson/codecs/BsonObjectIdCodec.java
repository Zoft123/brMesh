package org.bson.codecs;

import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonObjectIdCodec implements Codec<BsonObjectId> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonObjectId bsonObjectId, EncoderContext encoderContext) {
        bsonWriter.writeObjectId(bsonObjectId.getValue());
    }

    @Override // org.bson.codecs.Decoder
    public BsonObjectId decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new BsonObjectId(bsonReader.readObjectId());
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonObjectId> getEncoderClass() {
        return BsonObjectId.class;
    }
}

package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonUndefined;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonUndefinedCodec implements Codec<BsonUndefined> {
    @Override // org.bson.codecs.Decoder
    public BsonUndefined decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readUndefined();
        return new BsonUndefined();
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonUndefined bsonUndefined, EncoderContext encoderContext) {
        bsonWriter.writeUndefined();
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonUndefined> getEncoderClass() {
        return BsonUndefined.class;
    }
}

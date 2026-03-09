package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.types.MaxKey;

/* JADX INFO: loaded from: classes4.dex */
public class MaxKeyCodec implements Codec<MaxKey> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, MaxKey maxKey, EncoderContext encoderContext) {
        bsonWriter.writeMaxKey();
    }

    @Override // org.bson.codecs.Decoder
    public MaxKey decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readMaxKey();
        return new MaxKey();
    }

    @Override // org.bson.codecs.Encoder
    public Class<MaxKey> getEncoderClass() {
        return MaxKey.class;
    }
}

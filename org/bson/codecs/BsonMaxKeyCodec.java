package org.bson.codecs;

import org.bson.BsonMaxKey;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonMaxKeyCodec implements Codec<BsonMaxKey> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonMaxKey bsonMaxKey, EncoderContext encoderContext) {
        bsonWriter.writeMaxKey();
    }

    @Override // org.bson.codecs.Decoder
    public BsonMaxKey decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readMaxKey();
        return new BsonMaxKey();
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonMaxKey> getEncoderClass() {
        return BsonMaxKey.class;
    }
}

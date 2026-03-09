package org.bson.codecs;

import org.bson.BsonNull;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonNullCodec implements Codec<BsonNull> {
    @Override // org.bson.codecs.Decoder
    public BsonNull decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readNull();
        return BsonNull.VALUE;
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonNull bsonNull, EncoderContext encoderContext) {
        bsonWriter.writeNull();
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonNull> getEncoderClass() {
        return BsonNull.class;
    }
}

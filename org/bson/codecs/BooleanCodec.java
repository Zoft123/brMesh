package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BooleanCodec implements Codec<Boolean> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Boolean bool, EncoderContext encoderContext) {
        bsonWriter.writeBoolean(bool.booleanValue());
    }

    @Override // org.bson.codecs.Decoder
    public Boolean decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Boolean.valueOf(bsonReader.readBoolean());
    }

    @Override // org.bson.codecs.Encoder
    public Class<Boolean> getEncoderClass() {
        return Boolean.class;
    }
}

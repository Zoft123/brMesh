package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class IntegerCodec implements Codec<Integer> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Integer num, EncoderContext encoderContext) {
        bsonWriter.writeInt32(num.intValue());
    }

    @Override // org.bson.codecs.Decoder
    public Integer decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Integer.valueOf(NumberCodecHelper.decodeInt(bsonReader));
    }

    @Override // org.bson.codecs.Encoder
    public Class<Integer> getEncoderClass() {
        return Integer.class;
    }
}

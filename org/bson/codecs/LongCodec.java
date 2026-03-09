package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class LongCodec implements Codec<Long> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Long l, EncoderContext encoderContext) {
        bsonWriter.writeInt64(l.longValue());
    }

    @Override // org.bson.codecs.Decoder
    public Long decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Long.valueOf(NumberCodecHelper.decodeLong(bsonReader));
    }

    @Override // org.bson.codecs.Encoder
    public Class<Long> getEncoderClass() {
        return Long.class;
    }
}

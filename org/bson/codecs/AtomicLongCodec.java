package org.bson.codecs;

import java.util.concurrent.atomic.AtomicLong;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class AtomicLongCodec implements Codec<AtomicLong> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, AtomicLong atomicLong, EncoderContext encoderContext) {
        bsonWriter.writeInt64(atomicLong.longValue());
    }

    @Override // org.bson.codecs.Decoder
    public AtomicLong decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new AtomicLong(NumberCodecHelper.decodeLong(bsonReader));
    }

    @Override // org.bson.codecs.Encoder
    public Class<AtomicLong> getEncoderClass() {
        return AtomicLong.class;
    }
}

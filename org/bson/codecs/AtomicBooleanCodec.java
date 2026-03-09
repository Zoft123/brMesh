package org.bson.codecs;

import java.util.concurrent.atomic.AtomicBoolean;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class AtomicBooleanCodec implements Codec<AtomicBoolean> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, AtomicBoolean atomicBoolean, EncoderContext encoderContext) {
        bsonWriter.writeBoolean(atomicBoolean.get());
    }

    @Override // org.bson.codecs.Decoder
    public AtomicBoolean decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new AtomicBoolean(bsonReader.readBoolean());
    }

    @Override // org.bson.codecs.Encoder
    public Class<AtomicBoolean> getEncoderClass() {
        return AtomicBoolean.class;
    }
}

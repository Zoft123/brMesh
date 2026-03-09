package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonTimestampCodec implements Codec<BsonTimestamp> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonTimestamp bsonTimestamp, EncoderContext encoderContext) {
        bsonWriter.writeTimestamp(bsonTimestamp);
    }

    @Override // org.bson.codecs.Decoder
    public BsonTimestamp decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return bsonReader.readTimestamp();
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonTimestamp> getEncoderClass() {
        return BsonTimestamp.class;
    }
}

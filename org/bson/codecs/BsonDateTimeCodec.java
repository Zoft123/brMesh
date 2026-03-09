package org.bson.codecs;

import org.bson.BsonDateTime;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDateTimeCodec implements Codec<BsonDateTime> {
    @Override // org.bson.codecs.Decoder
    public BsonDateTime decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new BsonDateTime(bsonReader.readDateTime());
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonDateTime bsonDateTime, EncoderContext encoderContext) {
        bsonWriter.writeDateTime(bsonDateTime.getValue());
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonDateTime> getEncoderClass() {
        return BsonDateTime.class;
    }
}

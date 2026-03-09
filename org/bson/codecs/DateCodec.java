package org.bson.codecs;

import java.util.Date;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class DateCodec implements Codec<Date> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Date date, EncoderContext encoderContext) {
        bsonWriter.writeDateTime(date.getTime());
    }

    @Override // org.bson.codecs.Decoder
    public Date decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new Date(bsonReader.readDateTime());
    }

    @Override // org.bson.codecs.Encoder
    public Class<Date> getEncoderClass() {
        return Date.class;
    }
}

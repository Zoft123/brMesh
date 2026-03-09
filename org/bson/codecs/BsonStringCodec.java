package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonStringCodec implements Codec<BsonString> {
    @Override // org.bson.codecs.Decoder
    public BsonString decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new BsonString(bsonReader.readString());
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonString bsonString, EncoderContext encoderContext) {
        bsonWriter.writeString(bsonString.getValue());
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonString> getEncoderClass() {
        return BsonString.class;
    }
}

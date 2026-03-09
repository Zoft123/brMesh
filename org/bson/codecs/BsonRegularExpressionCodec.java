package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonRegularExpression;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonRegularExpressionCodec implements Codec<BsonRegularExpression> {
    @Override // org.bson.codecs.Decoder
    public BsonRegularExpression decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return bsonReader.readRegularExpression();
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonRegularExpression bsonRegularExpression, EncoderContext encoderContext) {
        bsonWriter.writeRegularExpression(bsonRegularExpression);
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonRegularExpression> getEncoderClass() {
        return BsonRegularExpression.class;
    }
}

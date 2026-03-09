package org.bson.codecs;

import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class FloatCodec implements Codec<Float> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Float f, EncoderContext encoderContext) {
        bsonWriter.writeDouble(f.floatValue());
    }

    @Override // org.bson.codecs.Decoder
    public Float decode(BsonReader bsonReader, DecoderContext decoderContext) {
        double dDecodeDouble = NumberCodecHelper.decodeDouble(bsonReader);
        if (dDecodeDouble < -3.4028234663852886E38d || dDecodeDouble > 3.4028234663852886E38d) {
            throw new BsonInvalidOperationException(String.format("%s can not be converted into a Float.", Double.valueOf(dDecodeDouble)));
        }
        return Float.valueOf((float) dDecodeDouble);
    }

    @Override // org.bson.codecs.Encoder
    public Class<Float> getEncoderClass() {
        return Float.class;
    }
}

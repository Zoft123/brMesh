package org.bson.codecs;

import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class ShortCodec implements Codec<Short> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Short sh, EncoderContext encoderContext) {
        bsonWriter.writeInt32(sh.shortValue());
    }

    @Override // org.bson.codecs.Decoder
    public Short decode(BsonReader bsonReader, DecoderContext decoderContext) {
        int iDecodeInt = NumberCodecHelper.decodeInt(bsonReader);
        if (iDecodeInt < -32768 || iDecodeInt > 32767) {
            throw new BsonInvalidOperationException(String.format("%s can not be converted into a Short.", Integer.valueOf(iDecodeInt)));
        }
        return Short.valueOf((short) iDecodeInt);
    }

    @Override // org.bson.codecs.Encoder
    public Class<Short> getEncoderClass() {
        return Short.class;
    }
}

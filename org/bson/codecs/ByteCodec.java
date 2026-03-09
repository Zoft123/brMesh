package org.bson.codecs;

import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class ByteCodec implements Codec<Byte> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Byte b, EncoderContext encoderContext) {
        bsonWriter.writeInt32(b.byteValue());
    }

    @Override // org.bson.codecs.Decoder
    public Byte decode(BsonReader bsonReader, DecoderContext decoderContext) {
        int iDecodeInt = NumberCodecHelper.decodeInt(bsonReader);
        if (iDecodeInt < -128 || iDecodeInt > 127) {
            throw new BsonInvalidOperationException(String.format("%s can not be converted into a Byte.", Integer.valueOf(iDecodeInt)));
        }
        return Byte.valueOf((byte) iDecodeInt);
    }

    @Override // org.bson.codecs.Encoder
    public Class<Byte> getEncoderClass() {
        return Byte.class;
    }
}

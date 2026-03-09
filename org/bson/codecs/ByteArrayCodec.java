package org.bson.codecs;

import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class ByteArrayCodec implements Codec<byte[]> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, byte[] bArr, EncoderContext encoderContext) {
        bsonWriter.writeBinaryData(new BsonBinary(bArr));
    }

    @Override // org.bson.codecs.Decoder
    public byte[] decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return bsonReader.readBinaryData().getData();
    }

    @Override // org.bson.codecs.Encoder
    public Class<byte[]> getEncoderClass() {
        return byte[].class;
    }
}

package org.bson.codecs;

import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.types.Binary;

/* JADX INFO: loaded from: classes4.dex */
public class BinaryCodec implements Codec<Binary> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Binary binary, EncoderContext encoderContext) {
        bsonWriter.writeBinaryData(new BsonBinary(binary.getType(), binary.getData()));
    }

    @Override // org.bson.codecs.Decoder
    public Binary decode(BsonReader bsonReader, DecoderContext decoderContext) {
        BsonBinary binaryData = bsonReader.readBinaryData();
        return new Binary(binaryData.getType(), binaryData.getData());
    }

    @Override // org.bson.codecs.Encoder
    public Class<Binary> getEncoderClass() {
        return Binary.class;
    }
}

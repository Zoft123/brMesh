package org.bson.codecs;

import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonBinaryCodec implements Codec<BsonBinary> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonBinary bsonBinary, EncoderContext encoderContext) {
        bsonWriter.writeBinaryData(bsonBinary);
    }

    @Override // org.bson.codecs.Decoder
    public BsonBinary decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return bsonReader.readBinaryData();
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonBinary> getEncoderClass() {
        return BsonBinary.class;
    }
}

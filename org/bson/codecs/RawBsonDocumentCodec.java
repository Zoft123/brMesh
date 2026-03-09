package org.bson.codecs;

import org.bson.BsonBinaryReader;
import org.bson.BsonBinaryWriter;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.RawBsonDocument;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.ByteBufferBsonInput;

/* JADX INFO: loaded from: classes4.dex */
public class RawBsonDocumentCodec implements Codec<RawBsonDocument> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, RawBsonDocument rawBsonDocument, EncoderContext encoderContext) {
        BsonBinaryReader bsonBinaryReader = new BsonBinaryReader(new ByteBufferBsonInput(rawBsonDocument.getByteBuffer()));
        try {
            bsonWriter.pipe(bsonBinaryReader);
        } finally {
            bsonBinaryReader.close();
        }
    }

    @Override // org.bson.codecs.Decoder
    public RawBsonDocument decode(BsonReader bsonReader, DecoderContext decoderContext) {
        BasicOutputBuffer basicOutputBuffer = new BasicOutputBuffer(0);
        BsonBinaryWriter bsonBinaryWriter = new BsonBinaryWriter(basicOutputBuffer);
        try {
            bsonBinaryWriter.pipe(bsonReader);
            return new RawBsonDocument(basicOutputBuffer.getInternalBuffer(), 0, basicOutputBuffer.getPosition());
        } finally {
            bsonBinaryWriter.close();
            basicOutputBuffer.close();
        }
    }

    @Override // org.bson.codecs.Encoder
    public Class<RawBsonDocument> getEncoderClass() {
        return RawBsonDocument.class;
    }
}

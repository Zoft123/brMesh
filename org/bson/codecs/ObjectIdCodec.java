package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class ObjectIdCodec implements Codec<ObjectId> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, ObjectId objectId, EncoderContext encoderContext) {
        bsonWriter.writeObjectId(objectId);
    }

    @Override // org.bson.codecs.Decoder
    public ObjectId decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return bsonReader.readObjectId();
    }

    @Override // org.bson.codecs.Encoder
    public Class<ObjectId> getEncoderClass() {
        return ObjectId.class;
    }
}

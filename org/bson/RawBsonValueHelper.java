package org.bson;

import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.io.BsonInputMark;

/* JADX INFO: loaded from: classes4.dex */
final class RawBsonValueHelper {
    private static final CodecRegistry REGISTRY = CodecRegistries.fromProviders(new BsonValueCodecProvider());

    /* JADX WARN: Multi-variable type inference failed */
    static BsonValue decode(byte[] bArr, BsonBinaryReader bsonBinaryReader) {
        if (bsonBinaryReader.getCurrentBsonType() == BsonType.DOCUMENT || bsonBinaryReader.getCurrentBsonType() == BsonType.ARRAY) {
            int position = bsonBinaryReader.getBsonInput().getPosition();
            BsonInputMark mark = bsonBinaryReader.getBsonInput().getMark(4);
            int int32 = bsonBinaryReader.getBsonInput().readInt32();
            mark.reset();
            bsonBinaryReader.skipValue();
            if (bsonBinaryReader.getCurrentBsonType() == BsonType.DOCUMENT) {
                return new RawBsonDocument(bArr, position, int32);
            }
            return new RawBsonArray(bArr, position, int32);
        }
        return (BsonValue) REGISTRY.get(BsonValueCodecProvider.getClassForBsonType(bsonBinaryReader.getCurrentBsonType())).decode(bsonBinaryReader, DecoderContext.builder().build());
    }

    private RawBsonValueHelper() {
    }
}

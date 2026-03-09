package org.bson.codecs;

import java.util.UUID;
import org.bson.BSONException;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.UuidRepresentation;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.internal.UuidHelper;

/* JADX INFO: loaded from: classes4.dex */
public class UuidCodec implements Codec<UUID> {
    private final UuidRepresentation uuidRepresentation;

    public UuidCodec(UuidRepresentation uuidRepresentation) {
        Assertions.notNull("uuidRepresentation", uuidRepresentation);
        this.uuidRepresentation = uuidRepresentation;
    }

    public UuidCodec() {
        this.uuidRepresentation = UuidRepresentation.JAVA_LEGACY;
    }

    public UuidRepresentation getUuidRepresentation() {
        return this.uuidRepresentation;
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, UUID uuid, EncoderContext encoderContext) {
        if (this.uuidRepresentation == UuidRepresentation.UNSPECIFIED) {
            throw new CodecConfigurationException("The uuidRepresentation has not been specified, so the UUID cannot be encoded.");
        }
        byte[] bArrEncodeUuidToBinary = UuidHelper.encodeUuidToBinary(uuid, this.uuidRepresentation);
        if (this.uuidRepresentation == UuidRepresentation.STANDARD) {
            bsonWriter.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_STANDARD, bArrEncodeUuidToBinary));
        } else {
            bsonWriter.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_LEGACY, bArrEncodeUuidToBinary));
        }
    }

    @Override // org.bson.codecs.Decoder
    public UUID decode(BsonReader bsonReader, DecoderContext decoderContext) {
        byte bPeekBinarySubType = bsonReader.peekBinarySubType();
        if (bPeekBinarySubType != BsonBinarySubType.UUID_LEGACY.getValue() && bPeekBinarySubType != BsonBinarySubType.UUID_STANDARD.getValue()) {
            throw new BSONException("Unexpected BsonBinarySubType");
        }
        return UuidHelper.decodeBinaryToUuid(bsonReader.readBinaryData().getData(), bPeekBinarySubType, this.uuidRepresentation);
    }

    @Override // org.bson.codecs.Encoder
    public Class<UUID> getEncoderClass() {
        return UUID.class;
    }

    public String toString() {
        return "UuidCodec{uuidRepresentation=" + this.uuidRepresentation + '}';
    }
}

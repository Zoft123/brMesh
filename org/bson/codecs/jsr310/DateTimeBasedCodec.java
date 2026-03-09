package org.bson.codecs.jsr310;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
abstract class DateTimeBasedCodec<T> implements Codec<T> {
    DateTimeBasedCodec() {
    }

    long validateAndReadDateTime(BsonReader bsonReader) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        if (!currentBsonType.equals(BsonType.DATE_TIME)) {
            throw new CodecConfigurationException(String.format("Could not decode into %s, expected '%s' BsonType but got '%s'.", getEncoderClass().getSimpleName(), BsonType.DATE_TIME, currentBsonType));
        }
        return bsonReader.readDateTime();
    }
}

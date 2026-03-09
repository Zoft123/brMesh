package org.bson.codecs.jsr310;

import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.ZoneOffset;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
public class LocalDateCodec extends DateTimeBasedCodec<LocalDate> {
    @Override // org.bson.codecs.Decoder
    public LocalDate decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Instant.ofEpochMilli(validateAndReadDateTime(bsonReader)).atZone(ZoneOffset.UTC).toLocalDate();
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, LocalDate localDate, EncoderContext encoderContext) {
        try {
            bsonWriter.writeDateTime(localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
        } catch (ArithmeticException e) {
            throw new CodecConfigurationException(String.format("Unsupported LocalDate '%s' could not be converted to milliseconds: %s", localDate, e.getMessage()), e);
        }
    }

    @Override // org.bson.codecs.Encoder
    public Class<LocalDate> getEncoderClass() {
        return LocalDate.class;
    }
}

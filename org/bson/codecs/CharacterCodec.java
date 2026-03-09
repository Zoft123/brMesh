package org.bson.codecs;

import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
public class CharacterCodec implements Codec<Character> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Character ch, EncoderContext encoderContext) {
        Assertions.notNull("value", ch);
        bsonWriter.writeString(ch.toString());
    }

    @Override // org.bson.codecs.Decoder
    public Character decode(BsonReader bsonReader, DecoderContext decoderContext) {
        String string = bsonReader.readString();
        if (string.length() != 1) {
            throw new BsonInvalidOperationException(String.format("Attempting to decode the string '%s' to a character, but its length is not equal to one", string));
        }
        return Character.valueOf(string.charAt(0));
    }

    @Override // org.bson.codecs.Encoder
    public Class<Character> getEncoderClass() {
        return Character.class;
    }
}

package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.types.Code;

/* JADX INFO: loaded from: classes4.dex */
public class CodeCodec implements Codec<Code> {
    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Code code, EncoderContext encoderContext) {
        bsonWriter.writeJavaScript(code.getCode());
    }

    @Override // org.bson.codecs.Decoder
    public Code decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new Code(bsonReader.readJavaScript());
    }

    @Override // org.bson.codecs.Encoder
    public Class<Code> getEncoderClass() {
        return Code.class;
    }
}

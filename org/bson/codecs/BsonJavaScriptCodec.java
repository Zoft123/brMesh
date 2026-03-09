package org.bson.codecs;

import org.bson.BsonJavaScript;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonJavaScriptCodec implements Codec<BsonJavaScript> {
    @Override // org.bson.codecs.Decoder
    public BsonJavaScript decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new BsonJavaScript(bsonReader.readJavaScript());
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonJavaScript bsonJavaScript, EncoderContext encoderContext) {
        bsonWriter.writeJavaScript(bsonJavaScript.getCode());
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonJavaScript> getEncoderClass() {
        return BsonJavaScript.class;
    }
}

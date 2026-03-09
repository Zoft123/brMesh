package org.bson.codecs;

import org.bson.BsonDocument;
import org.bson.BsonJavaScriptWithScope;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonJavaScriptWithScopeCodec implements Codec<BsonJavaScriptWithScope> {
    private final Codec<BsonDocument> documentCodec;

    public BsonJavaScriptWithScopeCodec(Codec<BsonDocument> codec) {
        this.documentCodec = codec;
    }

    @Override // org.bson.codecs.Decoder
    public BsonJavaScriptWithScope decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new BsonJavaScriptWithScope(bsonReader.readJavaScriptWithScope(), this.documentCodec.decode(bsonReader, decoderContext));
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonJavaScriptWithScope bsonJavaScriptWithScope, EncoderContext encoderContext) {
        bsonWriter.writeJavaScriptWithScope(bsonJavaScriptWithScope.getCode());
        this.documentCodec.encode(bsonWriter, bsonJavaScriptWithScope.getScope(), encoderContext);
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonJavaScriptWithScope> getEncoderClass() {
        return BsonJavaScriptWithScope.class;
    }
}

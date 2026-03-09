package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.types.CodeWithScope;

/* JADX INFO: loaded from: classes4.dex */
public class CodeWithScopeCodec implements Codec<CodeWithScope> {
    private final Codec<Document> documentCodec;

    public CodeWithScopeCodec(Codec<Document> codec) {
        this.documentCodec = codec;
    }

    @Override // org.bson.codecs.Decoder
    public CodeWithScope decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new CodeWithScope(bsonReader.readJavaScriptWithScope(), this.documentCodec.decode(bsonReader, decoderContext));
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, CodeWithScope codeWithScope, EncoderContext encoderContext) {
        bsonWriter.writeJavaScriptWithScope(codeWithScope.getCode());
        this.documentCodec.encode(bsonWriter, codeWithScope.getScope(), encoderContext);
    }

    @Override // org.bson.codecs.Encoder
    public Class<CodeWithScope> getEncoderClass() {
        return CodeWithScope.class;
    }
}

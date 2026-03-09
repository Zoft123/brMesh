package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonSymbol;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes4.dex */
public class BsonSymbolCodec implements Codec<BsonSymbol> {
    @Override // org.bson.codecs.Decoder
    public BsonSymbol decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new BsonSymbol(bsonReader.readSymbol());
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonSymbol bsonSymbol, EncoderContext encoderContext) {
        bsonWriter.writeSymbol(bsonSymbol.getSymbol());
    }

    @Override // org.bson.codecs.Encoder
    public Class<BsonSymbol> getEncoderClass() {
        return BsonSymbol.class;
    }
}

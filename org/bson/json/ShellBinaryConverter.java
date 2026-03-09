package org.bson.json;

import org.bson.BsonBinary;
import org.bson.internal.Base64;

/* JADX INFO: loaded from: classes4.dex */
class ShellBinaryConverter implements Converter<BsonBinary> {
    ShellBinaryConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonBinary bsonBinary, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw(String.format("new BinData(%s, \"%s\")", Integer.toString(bsonBinary.getType() & 255), Base64.encode(bsonBinary.getData())));
    }
}

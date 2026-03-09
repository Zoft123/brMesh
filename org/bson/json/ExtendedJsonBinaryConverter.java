package org.bson.json;

import org.bson.BsonBinary;
import org.bson.internal.Base64;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonBinaryConverter implements Converter<BsonBinary> {
    ExtendedJsonBinaryConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonBinary bsonBinary, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeStartObject("$binary");
        strictJsonWriter.writeString("base64", Base64.encode(bsonBinary.getData()));
        strictJsonWriter.writeString("subType", String.format("%02X", Byte.valueOf(bsonBinary.getType())));
        strictJsonWriter.writeEndObject();
        strictJsonWriter.writeEndObject();
    }
}

package org.bson.json;

import org.bson.BsonBinary;
import org.bson.internal.Base64;

/* JADX INFO: loaded from: classes4.dex */
class LegacyExtendedJsonBinaryConverter implements Converter<BsonBinary> {
    LegacyExtendedJsonBinaryConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonBinary bsonBinary, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$binary", Base64.encode(bsonBinary.getData()));
        strictJsonWriter.writeString("$type", String.format("%02X", Byte.valueOf(bsonBinary.getType())));
        strictJsonWriter.writeEndObject();
    }
}

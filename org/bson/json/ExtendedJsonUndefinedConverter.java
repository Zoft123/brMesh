package org.bson.json;

import org.bson.BsonUndefined;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonUndefinedConverter implements Converter<BsonUndefined> {
    ExtendedJsonUndefinedConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonUndefined bsonUndefined, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeBoolean("$undefined", true);
        strictJsonWriter.writeEndObject();
    }
}

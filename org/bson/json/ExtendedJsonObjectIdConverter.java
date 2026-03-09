package org.bson.json;

import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonObjectIdConverter implements Converter<ObjectId> {
    ExtendedJsonObjectIdConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(ObjectId objectId, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$oid", objectId.toHexString());
        strictJsonWriter.writeEndObject();
    }
}

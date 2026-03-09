package org.bson.json;

import org.bson.BsonNull;

/* JADX INFO: loaded from: classes4.dex */
class JsonNullConverter implements Converter<BsonNull> {
    JsonNullConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonNull bsonNull, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeNull();
    }
}

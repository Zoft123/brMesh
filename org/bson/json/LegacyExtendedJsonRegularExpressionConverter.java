package org.bson.json;

import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes4.dex */
class LegacyExtendedJsonRegularExpressionConverter implements Converter<BsonRegularExpression> {
    LegacyExtendedJsonRegularExpressionConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonRegularExpression bsonRegularExpression, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$regex", bsonRegularExpression.getPattern());
        strictJsonWriter.writeString("$options", bsonRegularExpression.getOptions());
        strictJsonWriter.writeEndObject();
    }
}

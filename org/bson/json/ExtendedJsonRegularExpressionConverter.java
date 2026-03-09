package org.bson.json;

import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonRegularExpressionConverter implements Converter<BsonRegularExpression> {
    ExtendedJsonRegularExpressionConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonRegularExpression bsonRegularExpression, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeStartObject("$regularExpression");
        strictJsonWriter.writeString("pattern", bsonRegularExpression.getPattern());
        strictJsonWriter.writeString("options", bsonRegularExpression.getOptions());
        strictJsonWriter.writeEndObject();
        strictJsonWriter.writeEndObject();
    }
}

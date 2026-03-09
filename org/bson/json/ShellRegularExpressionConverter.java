package org.bson.json;

import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes4.dex */
class ShellRegularExpressionConverter implements Converter<BsonRegularExpression> {
    ShellRegularExpressionConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonRegularExpression bsonRegularExpression, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw("/" + (bsonRegularExpression.getPattern().equals("") ? "(?:)" : bsonRegularExpression.getPattern().replace("/", "\\/")) + "/" + bsonRegularExpression.getOptions());
    }
}

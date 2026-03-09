package org.bson.json;

import org.bson.BsonUndefined;

/* JADX INFO: loaded from: classes4.dex */
class ShellUndefinedConverter implements Converter<BsonUndefined> {
    ShellUndefinedConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonUndefined bsonUndefined, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw("undefined");
    }
}

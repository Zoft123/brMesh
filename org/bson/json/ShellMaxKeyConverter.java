package org.bson.json;

import org.bson.BsonMaxKey;

/* JADX INFO: loaded from: classes4.dex */
class ShellMaxKeyConverter implements Converter<BsonMaxKey> {
    ShellMaxKeyConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonMaxKey bsonMaxKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw("MaxKey");
    }
}

package org.bson.json;

import org.bson.BsonTimestamp;

/* JADX INFO: loaded from: classes4.dex */
class ShellTimestampConverter implements Converter<BsonTimestamp> {
    ShellTimestampConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonTimestamp bsonTimestamp, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw(String.format("Timestamp(%d, %d)", Integer.valueOf(bsonTimestamp.getTime()), Integer.valueOf(bsonTimestamp.getInc())));
    }
}

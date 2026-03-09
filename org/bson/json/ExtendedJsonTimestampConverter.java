package org.bson.json;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import org.bson.BsonTimestamp;
import org.bson.internal.UnsignedLongs;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonTimestampConverter implements Converter<BsonTimestamp> {
    private long toUnsignedLong(int i) {
        return ((long) i) & 4294967295L;
    }

    ExtendedJsonTimestampConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonTimestamp bsonTimestamp, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeStartObject("$timestamp");
        strictJsonWriter.writeNumber(GlobalVariable.t, UnsignedLongs.toString(toUnsignedLong(bsonTimestamp.getTime())));
        strictJsonWriter.writeNumber("i", UnsignedLongs.toString(toUnsignedLong(bsonTimestamp.getInc())));
        strictJsonWriter.writeEndObject();
        strictJsonWriter.writeEndObject();
    }
}

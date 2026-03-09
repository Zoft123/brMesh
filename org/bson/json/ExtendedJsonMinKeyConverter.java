package org.bson.json;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import org.bson.BsonMinKey;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonMinKeyConverter implements Converter<BsonMinKey> {
    ExtendedJsonMinKeyConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonMinKey bsonMinKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeNumber("$minKey", GlobalVariable.RADAR);
        strictJsonWriter.writeEndObject();
    }
}

package org.bson.json;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import org.bson.BsonMaxKey;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonMaxKeyConverter implements Converter<BsonMaxKey> {
    ExtendedJsonMaxKeyConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(BsonMaxKey bsonMaxKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeNumber("$maxKey", GlobalVariable.RADAR);
        strictJsonWriter.writeEndObject();
    }
}

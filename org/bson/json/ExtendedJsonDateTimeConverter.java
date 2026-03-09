package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonDateTimeConverter implements Converter<Long> {
    ExtendedJsonDateTimeConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeStartObject("$date");
        strictJsonWriter.writeString("$numberLong", Long.toString(l.longValue()));
        strictJsonWriter.writeEndObject();
        strictJsonWriter.writeEndObject();
    }
}

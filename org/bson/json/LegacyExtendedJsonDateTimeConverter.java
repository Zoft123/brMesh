package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class LegacyExtendedJsonDateTimeConverter implements Converter<Long> {
    LegacyExtendedJsonDateTimeConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeNumber("$date", Long.toString(l.longValue()));
        strictJsonWriter.writeEndObject();
    }
}

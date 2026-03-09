package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class RelaxedExtendedJsonDateTimeConverter implements Converter<Long> {
    private static final Converter<Long> FALLBACK_CONVERTER = new ExtendedJsonDateTimeConverter();
    private static final long LAST_MS_OF_YEAR_9999 = 253402300799999L;

    RelaxedExtendedJsonDateTimeConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        if (l.longValue() < 0 || l.longValue() > 253402300799999L) {
            FALLBACK_CONVERTER.convert(l, strictJsonWriter);
            return;
        }
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$date", DateTimeFormatter.format(l.longValue()));
        strictJsonWriter.writeEndObject();
    }
}

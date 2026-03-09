package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class ExtendedJsonDoubleConverter implements Converter<Double> {
    ExtendedJsonDoubleConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Double d, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeName("$numberDouble");
        strictJsonWriter.writeString(Double.toString(d.doubleValue()));
        strictJsonWriter.writeEndObject();
    }
}

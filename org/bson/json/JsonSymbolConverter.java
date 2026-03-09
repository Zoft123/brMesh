package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class JsonSymbolConverter implements Converter<String> {
    JsonSymbolConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(String str, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$symbol", str);
        strictJsonWriter.writeEndObject();
    }
}

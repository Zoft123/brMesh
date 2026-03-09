package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class JsonJavaScriptConverter implements Converter<String> {
    JsonJavaScriptConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(String str, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$code", str);
        strictJsonWriter.writeEndObject();
    }
}

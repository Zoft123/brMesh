package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class JsonStringConverter implements Converter<String> {
    JsonStringConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(String str, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeString(str);
    }
}

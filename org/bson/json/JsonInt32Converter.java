package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class JsonInt32Converter implements Converter<Integer> {
    JsonInt32Converter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Integer num, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeNumber(Integer.toString(num.intValue()));
    }
}

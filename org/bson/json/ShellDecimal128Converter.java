package org.bson.json;

import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes4.dex */
class ShellDecimal128Converter implements Converter<Decimal128> {
    ShellDecimal128Converter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Decimal128 decimal128, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw(String.format("NumberDecimal(\"%s\")", decimal128.toString()));
    }
}

package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class RelaxedExtendedJsonDoubleConverter implements Converter<Double> {
    private static final Converter<Double> FALLBACK_CONVERTER = new ExtendedJsonDoubleConverter();

    RelaxedExtendedJsonDoubleConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Double d, StrictJsonWriter strictJsonWriter) {
        if (d.isNaN() || d.isInfinite()) {
            FALLBACK_CONVERTER.convert(d, strictJsonWriter);
        } else {
            strictJsonWriter.writeNumber(Double.toString(d.doubleValue()));
        }
    }
}

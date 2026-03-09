package org.bson.json;

import j$.util.DesugarTimeZone;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
class ShellDateTimeConverter implements Converter<Long> {
    ShellDateTimeConverter() {
    }

    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(DesugarTimeZone.getTimeZone("UTC"));
        if (l.longValue() < -59014396800000L || l.longValue() > 253399536000000L) {
            strictJsonWriter.writeRaw(String.format("new Date(%d)", l));
        } else {
            strictJsonWriter.writeRaw(String.format("ISODate(\"%s\")", simpleDateFormat.format(new Date(l.longValue()))));
        }
    }
}

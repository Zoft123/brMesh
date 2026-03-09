package com.alibaba.fastjson.serializer;

import io.realm.Realm$$ExternalSyntheticApiModelOutline0;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public class AdderSerializer implements ObjectSerializer {
    public static final AdderSerializer instance = new AdderSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (Realm$$ExternalSyntheticApiModelOutline0.m1287m(obj)) {
            serializeWriter.writeFieldValue('{', "value", Realm$$ExternalSyntheticApiModelOutline0.m1284m(obj).longValue());
            serializeWriter.write(125);
        } else if (Realm$$ExternalSyntheticApiModelOutline0.m$1(obj)) {
            serializeWriter.writeFieldValue('{', "value", Realm$$ExternalSyntheticApiModelOutline0.m1283m(obj).doubleValue());
            serializeWriter.write(125);
        }
    }
}

package org.bson.codecs.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes4.dex */
final class MapOfCodecsProvider implements CodecProvider {
    private final Map<Class<?>, Codec<?>> codecsMap = new HashMap();

    MapOfCodecsProvider(List<? extends Codec<?>> list) {
        for (Codec<?> codec : list) {
            this.codecsMap.put(codec.getEncoderClass(), codec);
        }
    }

    @Override // org.bson.codecs.configuration.CodecProvider
    public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry) {
        return (Codec) this.codecsMap.get(cls);
    }
}

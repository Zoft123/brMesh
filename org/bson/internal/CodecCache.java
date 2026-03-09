package org.bson.internal;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class CodecCache {
    private final ConcurrentMap<Class<?>, Optional<? extends Codec<?>>> codecCache = new ConcurrentHashMap();

    CodecCache() {
    }

    public boolean containsKey(Class<?> cls) {
        return this.codecCache.containsKey(cls);
    }

    public void put(Class<?> cls, Codec<?> codec) {
        this.codecCache.put(cls, Optional.of(codec));
    }

    public <T> Codec<T> getOrThrow(Class<T> cls) {
        if (this.codecCache.containsKey(cls)) {
            Optional<? extends Codec<?>> optional = this.codecCache.get(cls);
            if (!optional.isEmpty()) {
                return (Codec) optional.get();
            }
        }
        throw new CodecConfigurationException(String.format("Can't find a codec for %s.", cls));
    }
}

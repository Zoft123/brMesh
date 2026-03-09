package org.bson.codecs.jsr310;

import java.util.HashMap;
import java.util.Map;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class Jsr310CodecProvider implements CodecProvider {
    private static final Map<Class<?>, Codec<?>> JSR310_CODEC_MAP = new HashMap();

    static {
        try {
            Class.forName("java.time.Instant");
            putCodec(new InstantCodec());
            putCodec(new LocalDateCodec());
            putCodec(new LocalDateTimeCodec());
            putCodec(new LocalTimeCodec());
        } catch (ClassNotFoundException unused) {
        }
    }

    private static void putCodec(Codec<?> codec) {
        JSR310_CODEC_MAP.put(codec.getEncoderClass(), codec);
    }

    @Override // org.bson.codecs.configuration.CodecProvider
    public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry) {
        return (Codec) JSR310_CODEC_MAP.get(cls);
    }
}

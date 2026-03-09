package org.bson.codecs;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonCodecProvider implements CodecProvider {
    @Override // org.bson.codecs.configuration.CodecProvider
    public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry) {
        if (Bson.class.isAssignableFrom(cls)) {
            return new BsonCodec(codecRegistry);
        }
        return null;
    }
}

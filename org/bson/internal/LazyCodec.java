package org.bson.internal;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
class LazyCodec<T> implements Codec<T> {
    private final Class<T> clazz;
    private final CodecRegistry registry;
    private volatile Codec<T> wrapped;

    LazyCodec(CodecRegistry codecRegistry, Class<T> cls) {
        this.registry = codecRegistry;
        this.clazz = cls;
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, T t, EncoderContext encoderContext) {
        getWrapped().encode(bsonWriter, t, encoderContext);
    }

    @Override // org.bson.codecs.Encoder
    public Class<T> getEncoderClass() {
        return this.clazz;
    }

    @Override // org.bson.codecs.Decoder
    public T decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return getWrapped().decode(bsonReader, decoderContext);
    }

    private Codec<T> getWrapped() {
        if (this.wrapped == null) {
            this.wrapped = this.registry.get(this.clazz);
        }
        return this.wrapped;
    }
}

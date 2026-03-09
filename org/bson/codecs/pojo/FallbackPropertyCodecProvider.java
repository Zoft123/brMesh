package org.bson.codecs.pojo;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
final class FallbackPropertyCodecProvider implements PropertyCodecProvider {
    private final CodecRegistry codecRegistry;
    private final PojoCodec<?> pojoCodec;

    FallbackPropertyCodecProvider(PojoCodec<?> pojoCodec, CodecRegistry codecRegistry) {
        this.pojoCodec = pojoCodec;
        this.codecRegistry = codecRegistry;
    }

    @Override // org.bson.codecs.pojo.PropertyCodecProvider
    public <S> Codec<S> get(TypeWithTypeParameters<S> typeWithTypeParameters, PropertyCodecRegistry propertyCodecRegistry) {
        if (typeWithTypeParameters.getType() == this.pojoCodec.getEncoderClass()) {
            return this.pojoCodec;
        }
        return this.codecRegistry.get(typeWithTypeParameters.getType());
    }
}

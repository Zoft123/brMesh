package org.bson.codecs.pojo;

import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes4.dex */
public interface PropertyCodecProvider {
    <T> Codec<T> get(TypeWithTypeParameters<T> typeWithTypeParameters, PropertyCodecRegistry propertyCodecRegistry);
}

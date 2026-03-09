package org.bson.codecs.configuration;

import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes4.dex */
public interface CodecRegistry {
    <T> Codec<T> get(Class<T> cls);
}

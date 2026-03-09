package org.bson.codecs.pojo;

import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes4.dex */
abstract class PojoCodec<T> implements Codec<T> {
    abstract ClassModel<T> getClassModel();

    PojoCodec() {
    }
}

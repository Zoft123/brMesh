package org.bson.codecs.pojo;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface TypeWithTypeParameters<T> {
    Class<T> getType();

    List<? extends TypeWithTypeParameters<?>> getTypeParameters();
}

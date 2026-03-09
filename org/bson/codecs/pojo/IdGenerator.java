package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes4.dex */
public interface IdGenerator<T> {
    T generate();

    Class<T> getType();
}

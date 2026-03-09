package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes4.dex */
public interface InstanceCreator<T> {
    T getInstance();

    <S> void set(S s, PropertyModel<S> propertyModel);
}

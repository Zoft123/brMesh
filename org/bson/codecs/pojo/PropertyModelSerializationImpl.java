package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes4.dex */
class PropertyModelSerializationImpl<T> implements PropertySerialization<T> {
    @Override // org.bson.codecs.pojo.PropertySerialization
    public boolean shouldSerialize(T t) {
        return t != null;
    }

    PropertyModelSerializationImpl() {
    }
}

package org.bson.codecs;

import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class ObjectIdGenerator implements IdGenerator {
    @Override // org.bson.codecs.IdGenerator
    public Object generate() {
        return new ObjectId();
    }
}

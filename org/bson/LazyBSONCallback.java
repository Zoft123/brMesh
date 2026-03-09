package org.bson;

import java.util.List;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class LazyBSONCallback extends EmptyBSONCallback {
    private Object root;

    @Override // org.bson.EmptyBSONCallback, org.bson.BSONCallback
    public void reset() {
        this.root = null;
    }

    @Override // org.bson.EmptyBSONCallback, org.bson.BSONCallback
    public Object get() {
        return getRoot();
    }

    @Override // org.bson.EmptyBSONCallback, org.bson.BSONCallback
    public void gotBinary(String str, byte b, byte[] bArr) {
        setRoot(createObject(bArr, 0));
    }

    public Object createObject(byte[] bArr, int i) {
        return new LazyBSONObject(bArr, i, this);
    }

    public List createArray(byte[] bArr, int i) {
        return new LazyBSONList(bArr, i, this);
    }

    public Object createDBRef(String str, ObjectId objectId) {
        return new BasicBSONObject("$ns", str).append("$id", objectId);
    }

    private Object getRoot() {
        return this.root;
    }

    private void setRoot(Object obj) {
        this.root = obj;
    }
}

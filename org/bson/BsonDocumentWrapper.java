package org.bson;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public final class BsonDocumentWrapper<T> extends BsonDocument {
    private static final long serialVersionUID = 1;
    private final transient Encoder<T> encoder;
    private BsonDocument unwrapped;
    private final transient T wrappedDocument;

    public static BsonDocument asBsonDocument(Object obj, CodecRegistry codecRegistry) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BsonDocument) {
            return (BsonDocument) obj;
        }
        return new BsonDocumentWrapper(obj, codecRegistry.get(obj.getClass()));
    }

    public BsonDocumentWrapper(T t, Encoder<T> encoder) {
        if (t == null) {
            throw new IllegalArgumentException("Document can not be null");
        }
        this.wrappedDocument = t;
        this.encoder = encoder;
    }

    public T getWrappedDocument() {
        return this.wrappedDocument;
    }

    public Encoder<T> getEncoder() {
        return this.encoder;
    }

    public boolean isUnwrapped() {
        return this.unwrapped != null;
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public int size() {
        return getUnwrapped().size();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean isEmpty() {
        return getUnwrapped().isEmpty();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean containsKey(Object obj) {
        return getUnwrapped().containsKey(obj);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean containsValue(Object obj) {
        return getUnwrapped().containsValue(obj);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public BsonValue get(Object obj) {
        return getUnwrapped().get(obj);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public BsonValue put(String str, BsonValue bsonValue) {
        return getUnwrapped().put(str, bsonValue);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public BsonValue remove(Object obj) {
        return getUnwrapped().remove(obj);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public void putAll(Map<? extends String, ? extends BsonValue> map) {
        super.putAll(map);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public void clear() {
        super.clear();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public Set<String> keySet() {
        return getUnwrapped().keySet();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public Collection<BsonValue> values() {
        return getUnwrapped().values();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public Set<Map.Entry<String, BsonValue>> entrySet() {
        return getUnwrapped().entrySet();
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public boolean equals(Object obj) {
        return getUnwrapped().equals(obj);
    }

    @Override // org.bson.BsonDocument, java.util.Map
    public int hashCode() {
        return getUnwrapped().hashCode();
    }

    @Override // org.bson.BsonDocument
    public String toString() {
        return getUnwrapped().toString();
    }

    @Override // org.bson.BsonDocument
    public BsonDocument clone() {
        return getUnwrapped().clone();
    }

    private BsonDocument getUnwrapped() {
        if (this.encoder == null) {
            throw new BsonInvalidOperationException("Can not unwrap a BsonDocumentWrapper with no Encoder");
        }
        if (this.unwrapped == null) {
            BsonDocument bsonDocument = new BsonDocument();
            this.encoder.encode(new BsonDocumentWriter(bsonDocument), this.wrappedDocument, EncoderContext.builder().build());
            this.unwrapped = bsonDocument;
        }
        return this.unwrapped;
    }

    private Object writeReplace() {
        return getUnwrapped();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}

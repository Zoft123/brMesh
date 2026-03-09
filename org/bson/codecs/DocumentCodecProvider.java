package org.bson.codecs;

import org.bson.Document;
import org.bson.Transformer;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.CodeWithScope;

/* JADX INFO: loaded from: classes4.dex */
public class DocumentCodecProvider implements CodecProvider {
    private final BsonTypeClassMap bsonTypeClassMap;
    private final Transformer valueTransformer;

    public DocumentCodecProvider() {
        this(new BsonTypeClassMap());
    }

    public DocumentCodecProvider(Transformer transformer) {
        this(new BsonTypeClassMap(), transformer);
    }

    public DocumentCodecProvider(BsonTypeClassMap bsonTypeClassMap) {
        this(bsonTypeClassMap, null);
    }

    public DocumentCodecProvider(BsonTypeClassMap bsonTypeClassMap, Transformer transformer) {
        this.bsonTypeClassMap = (BsonTypeClassMap) Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
        this.valueTransformer = transformer;
    }

    @Override // org.bson.codecs.configuration.CodecProvider
    public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry) {
        if (cls == CodeWithScope.class) {
            return new CodeWithScopeCodec(codecRegistry.get(Document.class));
        }
        if (cls == Document.class) {
            return new DocumentCodec(codecRegistry, this.bsonTypeClassMap, this.valueTransformer);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DocumentCodecProvider documentCodecProvider = (DocumentCodecProvider) obj;
        if (!this.bsonTypeClassMap.equals(documentCodecProvider.bsonTypeClassMap)) {
            return false;
        }
        Transformer transformer = this.valueTransformer;
        Transformer transformer2 = documentCodecProvider.valueTransformer;
        return transformer == null ? transformer2 == null : transformer.equals(transformer2);
    }

    public int hashCode() {
        int iHashCode = this.bsonTypeClassMap.hashCode() * 31;
        Transformer transformer = this.valueTransformer;
        return iHashCode + (transformer != null ? transformer.hashCode() : 0);
    }
}

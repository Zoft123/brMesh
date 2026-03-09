package org.bson.codecs;

import org.bson.Transformer;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class IterableCodecProvider implements CodecProvider {
    private final BsonTypeClassMap bsonTypeClassMap;
    private final Transformer valueTransformer;

    public IterableCodecProvider() {
        this(new BsonTypeClassMap());
    }

    public IterableCodecProvider(Transformer transformer) {
        this(new BsonTypeClassMap(), transformer);
    }

    public IterableCodecProvider(BsonTypeClassMap bsonTypeClassMap) {
        this(bsonTypeClassMap, null);
    }

    public IterableCodecProvider(BsonTypeClassMap bsonTypeClassMap, Transformer transformer) {
        this.bsonTypeClassMap = (BsonTypeClassMap) Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
        this.valueTransformer = transformer;
    }

    @Override // org.bson.codecs.configuration.CodecProvider
    public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry) {
        if (Iterable.class.isAssignableFrom(cls)) {
            return new IterableCodec(codecRegistry, this.bsonTypeClassMap, this.valueTransformer);
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
        IterableCodecProvider iterableCodecProvider = (IterableCodecProvider) obj;
        if (!this.bsonTypeClassMap.equals(iterableCodecProvider.bsonTypeClassMap)) {
            return false;
        }
        Transformer transformer = this.valueTransformer;
        Transformer transformer2 = iterableCodecProvider.valueTransformer;
        return transformer == null ? transformer2 == null : transformer.equals(transformer2);
    }

    public int hashCode() {
        int iHashCode = this.bsonTypeClassMap.hashCode() * 31;
        Transformer transformer = this.valueTransformer;
        return iHashCode + (transformer != null ? transformer.hashCode() : 0);
    }
}

package org.bson.codecs;

import org.bson.BsonType;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public class BsonTypeCodecMap {
    private final BsonTypeClassMap bsonTypeClassMap;
    private final Codec<?>[] codecs = new Codec[256];

    public BsonTypeCodecMap(BsonTypeClassMap bsonTypeClassMap, CodecRegistry codecRegistry) {
        this.bsonTypeClassMap = (BsonTypeClassMap) Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
        Assertions.notNull("codecRegistry", codecRegistry);
        for (BsonType bsonType : bsonTypeClassMap.keys()) {
            Class<?> cls = bsonTypeClassMap.get(bsonType);
            if (cls != null) {
                try {
                    this.codecs[bsonType.getValue()] = codecRegistry.get(cls);
                } catch (CodecConfigurationException unused) {
                }
            }
        }
    }

    public Codec<?> get(BsonType bsonType) {
        Codec<?> codec = this.codecs[bsonType.getValue()];
        if (codec != null) {
            return codec;
        }
        Class<?> cls = this.bsonTypeClassMap.get(bsonType);
        if (cls == null) {
            throw new CodecConfigurationException(String.format("No class mapped for BSON type %s.", bsonType));
        }
        throw new CodecConfigurationException(String.format("Can't find a codec for %s.", cls));
    }
}

package org.bson.codecs.pojo;

import java.util.HashMap;
import java.util.Map;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class MapPropertyCodecProvider implements PropertyCodecProvider {
    MapPropertyCodecProvider() {
    }

    @Override // org.bson.codecs.pojo.PropertyCodecProvider
    public <T> Codec<T> get(TypeWithTypeParameters<T> typeWithTypeParameters, PropertyCodecRegistry propertyCodecRegistry) {
        if (!Map.class.isAssignableFrom(typeWithTypeParameters.getType()) || typeWithTypeParameters.getTypeParameters().size() != 2) {
            return null;
        }
        Class<?> type = typeWithTypeParameters.getTypeParameters().get(0).getType();
        if (!type.equals(String.class)) {
            throw new CodecConfigurationException(String.format("Invalid Map type. Maps MUST have string keys, found %s instead.", type));
        }
        try {
            return new MapCodec(typeWithTypeParameters.getType(), propertyCodecRegistry.get((TypeWithTypeParameters) typeWithTypeParameters.getTypeParameters().get(1)));
        } catch (CodecConfigurationException e) {
            if (typeWithTypeParameters.getTypeParameters().get(1).getType() == Object.class) {
                try {
                    return propertyCodecRegistry.get(TypeData.builder(Map.class).build());
                } catch (CodecConfigurationException unused) {
                    throw e;
                }
            }
            throw e;
        }
    }

    private static class MapCodec<T> implements Codec<Map<String, T>> {
        private final Codec<T> codec;
        private final Class<Map<String, T>> encoderClass;

        MapCodec(Class<Map<String, T>> cls, Codec<T> codec) {
            this.encoderClass = cls;
            this.codec = codec;
        }

        @Override // org.bson.codecs.Encoder
        public void encode(BsonWriter bsonWriter, Map<String, T> map, EncoderContext encoderContext) {
            bsonWriter.writeStartDocument();
            for (Map.Entry<String, T> entry : map.entrySet()) {
                bsonWriter.writeName(entry.getKey());
                if (entry.getValue() == null) {
                    bsonWriter.writeNull();
                } else {
                    this.codec.encode(bsonWriter, entry.getValue(), encoderContext);
                }
            }
            bsonWriter.writeEndDocument();
        }

        @Override // org.bson.codecs.Decoder
        public Map<String, T> decode(BsonReader bsonReader, DecoderContext decoderContext) {
            bsonReader.readStartDocument();
            Map<String, T> mapCodec = getInstance();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (bsonReader.getCurrentBsonType() == BsonType.NULL) {
                    mapCodec.put(bsonReader.readName(), null);
                    bsonReader.readNull();
                } else {
                    mapCodec.put(bsonReader.readName(), this.codec.decode(bsonReader, decoderContext));
                }
            }
            bsonReader.readEndDocument();
            return mapCodec;
        }

        @Override // org.bson.codecs.Encoder
        public Class<Map<String, T>> getEncoderClass() {
            return this.encoderClass;
        }

        private Map<String, T> getInstance() {
            if (this.encoderClass.isInterface()) {
                return new HashMap();
            }
            try {
                return this.encoderClass.getDeclaredConstructor(null).newInstance(null);
            } catch (Exception e) {
                throw new CodecConfigurationException(e.getMessage(), e);
            }
        }
    }
}

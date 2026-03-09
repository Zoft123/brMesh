package org.bson.codecs.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class CollectionPropertyCodecProvider implements PropertyCodecProvider {
    CollectionPropertyCodecProvider() {
    }

    @Override // org.bson.codecs.pojo.PropertyCodecProvider
    public <T> Codec<T> get(TypeWithTypeParameters<T> typeWithTypeParameters, PropertyCodecRegistry propertyCodecRegistry) {
        if (Collection.class.isAssignableFrom(typeWithTypeParameters.getType()) && typeWithTypeParameters.getTypeParameters().size() == 1) {
            return new CollectionCodec(typeWithTypeParameters.getType(), propertyCodecRegistry.get((TypeWithTypeParameters) typeWithTypeParameters.getTypeParameters().get(0)));
        }
        return null;
    }

    private static class CollectionCodec<T> implements Codec<Collection<T>> {
        private final Codec<T> codec;
        private final Class<Collection<T>> encoderClass;

        CollectionCodec(Class<Collection<T>> cls, Codec<T> codec) {
            this.encoderClass = cls;
            this.codec = codec;
        }

        @Override // org.bson.codecs.Encoder
        public void encode(BsonWriter bsonWriter, Collection<T> collection, EncoderContext encoderContext) {
            bsonWriter.writeStartArray();
            for (T t : collection) {
                if (t == null) {
                    bsonWriter.writeNull();
                } else {
                    this.codec.encode(bsonWriter, t, encoderContext);
                }
            }
            bsonWriter.writeEndArray();
        }

        @Override // org.bson.codecs.Decoder
        public Collection<T> decode(BsonReader bsonReader, DecoderContext decoderContext) {
            Collection<T> collectionCodec = getInstance();
            bsonReader.readStartArray();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (bsonReader.getCurrentBsonType() == BsonType.NULL) {
                    collectionCodec.add(null);
                    bsonReader.readNull();
                } else {
                    collectionCodec.add(this.codec.decode(bsonReader, decoderContext));
                }
            }
            bsonReader.readEndArray();
            return collectionCodec;
        }

        @Override // org.bson.codecs.Encoder
        public Class<Collection<T>> getEncoderClass() {
            return this.encoderClass;
        }

        private Collection<T> getInstance() {
            if (this.encoderClass.isInterface()) {
                if (this.encoderClass.isAssignableFrom(ArrayList.class)) {
                    return new ArrayList();
                }
                if (this.encoderClass.isAssignableFrom(HashSet.class)) {
                    return new HashSet();
                }
                throw new CodecConfigurationException(String.format("Unsupported Collection interface of %s!", this.encoderClass.getName()));
            }
            try {
                return this.encoderClass.getDeclaredConstructor(null).newInstance(null);
            } catch (Exception e) {
                throw new CodecConfigurationException(e.getMessage(), e);
            }
        }
    }
}

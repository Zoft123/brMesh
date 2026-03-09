package org.bson.codecs.pojo;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonReaderMark;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.TypeData;
import org.bson.diagnostics.Logger;
import org.bson.diagnostics.Loggers;

/* JADX INFO: loaded from: classes4.dex */
final class PojoCodecImpl<T> extends PojoCodec<T> {
    private static final Logger LOGGER = Loggers.getLogger("PojoCodec");
    private final ClassModel<T> classModel;
    private final ConcurrentMap<ClassModel<?>, Codec<?>> codecCache;
    private final DiscriminatorLookup discriminatorLookup;
    private final PropertyCodecRegistry propertyCodecRegistry;
    private final CodecRegistry registry;
    private final boolean specialized;

    PojoCodecImpl(ClassModel<T> classModel, CodecRegistry codecRegistry, List<PropertyCodecProvider> list, DiscriminatorLookup discriminatorLookup) {
        this.classModel = classModel;
        CodecRegistry codecRegistryFromRegistries = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs((Codec<?>[]) new Codec[]{this}), codecRegistry);
        this.registry = codecRegistryFromRegistries;
        this.discriminatorLookup = discriminatorLookup;
        this.codecCache = new ConcurrentHashMap();
        this.propertyCodecRegistry = new PropertyCodecRegistryImpl(this, codecRegistryFromRegistries, list);
        this.specialized = shouldSpecialize(classModel);
        specialize();
    }

    PojoCodecImpl(ClassModel<T> classModel, CodecRegistry codecRegistry, PropertyCodecRegistry propertyCodecRegistry, DiscriminatorLookup discriminatorLookup, ConcurrentMap<ClassModel<?>, Codec<?>> concurrentMap, boolean z) {
        this.classModel = classModel;
        this.registry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs((Codec<?>[]) new Codec[]{this}), codecRegistry);
        this.discriminatorLookup = discriminatorLookup;
        this.codecCache = concurrentMap;
        this.propertyCodecRegistry = propertyCodecRegistry;
        this.specialized = z;
        specialize();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to org.bson.codecs.pojo.PojoCodecImpl<T> for r2v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    private void specialize() {
        /*
            r2 = this;
            boolean r0 = r2.specialized
            if (r0 == 0) goto L25
            java.util.concurrent.ConcurrentMap<org.bson.codecs.pojo.ClassModel<?>, org.bson.codecs.Codec<?>> r0 = r2.codecCache
            org.bson.codecs.pojo.ClassModel<T> r1 = r2.classModel
            r0.put(r1, r2)
            org.bson.codecs.pojo.ClassModel<T> r0 = r2.classModel
            java.util.List r0 = r0.getPropertyModels()
            java.util.Iterator r0 = r0.iterator()
        L15:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L25
            java.lang.Object r1 = r0.next()
            org.bson.codecs.pojo.PropertyModel r1 = (org.bson.codecs.pojo.PropertyModel) r1
            r2.addToCache(r1)
            goto L15
        L25:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bson.codecs.pojo.PojoCodecImpl.specialize():void");
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, T t, EncoderContext encoderContext) {
        if (!this.specialized) {
            throw new CodecConfigurationException(String.format("%s contains generic types that have not been specialised.%nTop level classes with generic types are not supported by the PojoCodec.", this.classModel.getName()));
        }
        if (areEquivalentTypes(t.getClass(), this.classModel.getType())) {
            bsonWriter.writeStartDocument();
            encodeIdProperty(bsonWriter, t, encoderContext, this.classModel.getIdPropertyModelHolder());
            if (this.classModel.useDiscriminator()) {
                bsonWriter.writeString(this.classModel.getDiscriminatorKey(), this.classModel.getDiscriminator());
            }
            Iterator<PropertyModel<?>> it = this.classModel.getPropertyModels().iterator();
            while (it.hasNext()) {
                PropertyModel<S> propertyModel = (PropertyModel) it.next();
                if (!propertyModel.equals(this.classModel.getIdPropertyModel())) {
                    encodeProperty(bsonWriter, t, encoderContext, propertyModel);
                }
            }
            bsonWriter.writeEndDocument();
            return;
        }
        this.registry.get(t.getClass()).encode(bsonWriter, t, encoderContext);
    }

    @Override // org.bson.codecs.Decoder
    public T decode(BsonReader bsonReader, DecoderContext decoderContext) {
        if (decoderContext.hasCheckedDiscriminator()) {
            if (!this.specialized) {
                throw new CodecConfigurationException(String.format("%s contains generic types that have not been specialised.%nTop level classes with generic types are not supported by the PojoCodec.", this.classModel.getName()));
            }
            InstanceCreator<T> instanceCreator = this.classModel.getInstanceCreator();
            decodeProperties(bsonReader, decoderContext, instanceCreator);
            return instanceCreator.getInstance();
        }
        return getCodecFromDocument(bsonReader, this.classModel.useDiscriminator(), this.classModel.getDiscriminatorKey(), this.registry, this.discriminatorLookup, this).decode(bsonReader, DecoderContext.builder().checkedDiscriminator(true).build());
    }

    @Override // org.bson.codecs.Encoder
    public Class<T> getEncoderClass() {
        return this.classModel.getType();
    }

    public String toString() {
        return String.format("PojoCodec<%s>", this.classModel);
    }

    @Override // org.bson.codecs.pojo.PojoCodec
    ClassModel<T> getClassModel() {
        return this.classModel;
    }

    private <S> void encodeIdProperty(BsonWriter bsonWriter, T t, EncoderContext encoderContext, IdPropertyModelHolder<S> idPropertyModelHolder) {
        if (idPropertyModelHolder.getPropertyModel() != null) {
            if (idPropertyModelHolder.getIdGenerator() == null) {
                encodeProperty(bsonWriter, t, encoderContext, idPropertyModelHolder.getPropertyModel());
                return;
            }
            S sGenerate = idPropertyModelHolder.getPropertyModel().getPropertyAccessor().get(t);
            if (sGenerate == null && encoderContext.isEncodingCollectibleDocument()) {
                sGenerate = idPropertyModelHolder.getIdGenerator().generate();
                try {
                    idPropertyModelHolder.getPropertyModel().getPropertyAccessor().set(t, sGenerate);
                } catch (Exception unused) {
                }
            }
            encodeValue(bsonWriter, encoderContext, idPropertyModelHolder.getPropertyModel(), sGenerate);
        }
    }

    private <S> void encodeProperty(BsonWriter bsonWriter, T t, EncoderContext encoderContext, PropertyModel<S> propertyModel) {
        if (propertyModel == null || !propertyModel.isReadable()) {
            return;
        }
        encodeValue(bsonWriter, encoderContext, propertyModel, propertyModel.getPropertyAccessor().get(t));
    }

    private <S> void encodeValue(BsonWriter bsonWriter, EncoderContext encoderContext, PropertyModel<S> propertyModel, S s) {
        if (propertyModel.shouldSerialize(s)) {
            bsonWriter.writeName(propertyModel.getReadName());
            if (s == null) {
                bsonWriter.writeNull();
                return;
            }
            try {
                encoderContext.encodeWithChildContext(propertyModel.getCachedCodec(), bsonWriter, s);
            } catch (CodecConfigurationException e) {
                throw new CodecConfigurationException(String.format("Failed to encode '%s'. Encoding '%s' errored with: %s", this.classModel.getName(), propertyModel.getReadName(), e.getMessage()), e);
            }
        }
    }

    private void decodeProperties(BsonReader bsonReader, DecoderContext decoderContext, InstanceCreator<T> instanceCreator) {
        BsonReader bsonReader2;
        DecoderContext decoderContext2;
        InstanceCreator<T> instanceCreator2;
        bsonReader.readStartDocument();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String name = bsonReader.readName();
            if (this.classModel.useDiscriminator() && this.classModel.getDiscriminatorKey().equals(name)) {
                bsonReader.readString();
                bsonReader2 = bsonReader;
                decoderContext2 = decoderContext;
                instanceCreator2 = instanceCreator;
            } else {
                bsonReader2 = bsonReader;
                decoderContext2 = decoderContext;
                instanceCreator2 = instanceCreator;
                decodePropertyModel(bsonReader2, decoderContext2, instanceCreator2, name, getPropertyModelByWriteName(this.classModel, name));
            }
            bsonReader = bsonReader2;
            decoderContext = decoderContext2;
            instanceCreator = instanceCreator2;
        }
        bsonReader.readEndDocument();
    }

    private <S> void decodePropertyModel(BsonReader bsonReader, DecoderContext decoderContext, InstanceCreator<T> instanceCreator, String str, PropertyModel<S> propertyModel) {
        Object objDecodeWithChildContext;
        if (propertyModel != null) {
            try {
                if (bsonReader.getCurrentBsonType() == BsonType.NULL) {
                    bsonReader.readNull();
                    objDecodeWithChildContext = null;
                } else {
                    objDecodeWithChildContext = decoderContext.decodeWithChildContext(propertyModel.getCachedCodec(), bsonReader);
                }
                if (propertyModel.isWritable()) {
                    instanceCreator.set(objDecodeWithChildContext, propertyModel);
                    return;
                }
                return;
            } catch (BsonInvalidOperationException e) {
                throw new CodecConfigurationException(String.format("Failed to decode '%s'. Decoding '%s' errored with: %s", this.classModel.getName(), str, e.getMessage()), e);
            } catch (CodecConfigurationException e2) {
                throw new CodecConfigurationException(String.format("Failed to decode '%s'. Decoding '%s' errored with: %s", this.classModel.getName(), str, e2.getMessage()), e2);
            }
        }
        Logger logger = LOGGER;
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Found property not present in the ClassModel: %s", str));
        }
        bsonReader.skipValue();
    }

    private <S> void addToCache(PropertyModel<S> propertyModel) {
        propertyModel.cachedCodec(propertyModel.getCodec() != null ? propertyModel.getCodec() : specializePojoCodec(propertyModel));
    }

    private <S, V> boolean areEquivalentTypes(Class<S> cls, Class<V> cls2) {
        if (cls.equals(cls2)) {
            return true;
        }
        if (Collection.class.isAssignableFrom(cls) && Collection.class.isAssignableFrom(cls2)) {
            return true;
        }
        return Map.class.isAssignableFrom(cls) && Map.class.isAssignableFrom(cls2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <S> Codec<S> specializePojoCodec(PropertyModel<S> propertyModel) {
        Codec<S> codecFromPropertyRegistry = getCodecFromPropertyRegistry(propertyModel);
        if (!(codecFromPropertyRegistry instanceof PojoCodec)) {
            return codecFromPropertyRegistry;
        }
        ClassModel<S> specializedClassModel = getSpecializedClassModel(((PojoCodec) codecFromPropertyRegistry).getClassModel(), propertyModel);
        if (this.codecCache.containsKey(specializedClassModel)) {
            return (Codec) this.codecCache.get(specializedClassModel);
        }
        return new LazyPojoCodec(specializedClassModel, this.registry, this.propertyCodecRegistry, this.discriminatorLookup, this.codecCache);
    }

    private <S> Codec<S> getCodecFromPropertyRegistry(PropertyModel<S> propertyModel) {
        try {
            return this.propertyCodecRegistry.get(propertyModel.getTypeData());
        } catch (CodecConfigurationException e) {
            return new LazyMissingCodec(propertyModel.getTypeData().getType(), e);
        }
    }

    private <S, V> ClassModel<S> getSpecializedClassModel(ClassModel<S> classModel, PropertyModel<V> propertyModel) {
        boolean z = (propertyModel.useDiscriminator() == null ? classModel.useDiscriminator() : propertyModel.useDiscriminator().booleanValue()) != classModel.useDiscriminator() && (classModel.getDiscriminatorKey() != null && classModel.getDiscriminator() != null);
        if (propertyModel.getTypeData().getTypeParameters().isEmpty() && !z) {
            return classModel;
        }
        ArrayList arrayList = new ArrayList(classModel.getPropertyModels());
        PropertyModel idPropertyModel = classModel.getIdPropertyModel();
        List<TypeData<?>> typeParameters = propertyModel.getTypeData().getTypeParameters();
        for (int i = 0; i < arrayList.size(); i++) {
            PropertyModel<V> propertyModel2 = (PropertyModel) arrayList.get(i);
            String name = propertyModel2.getName();
            TypeParameterMap typeParameterMap = classModel.getPropertyNameToTypeParameterMap().get(name);
            if (typeParameterMap.hasTypeParameters()) {
                PropertyModel<V> specializedPropertyModel = getSpecializedPropertyModel(propertyModel2, typeParameterMap, typeParameters);
                arrayList.set(i, specializedPropertyModel);
                if (idPropertyModel != null && idPropertyModel.getName().equals(name)) {
                    idPropertyModel = specializedPropertyModel;
                }
            }
        }
        return new ClassModel<>(classModel.getType(), classModel.getPropertyNameToTypeParameterMap(), classModel.getInstanceCreatorFactory(), Boolean.valueOf(z ? propertyModel.useDiscriminator().booleanValue() : classModel.useDiscriminator()), classModel.getDiscriminatorKey(), classModel.getDiscriminator(), IdPropertyModelHolder.create(classModel, idPropertyModel), arrayList);
    }

    private <V> PropertyModel<V> getSpecializedPropertyModel(PropertyModel<V> propertyModel, TypeParameterMap typeParameterMap, List<TypeData<?>> list) {
        TypeData<?> typeDataBuild;
        Map<Integer, Integer> propertyToClassParamIndexMap = typeParameterMap.getPropertyToClassParamIndexMap();
        Integer num = propertyToClassParamIndexMap.get(-1);
        if (num != null) {
            typeDataBuild = list.get(num.intValue());
        } else {
            TypeData.Builder builder = TypeData.builder(propertyModel.getTypeData().getType());
            ArrayList arrayList = new ArrayList(propertyModel.getTypeData().getTypeParameters());
            for (int i = 0; i < arrayList.size(); i++) {
                for (Map.Entry<Integer, Integer> entry : propertyToClassParamIndexMap.entrySet()) {
                    if (entry.getKey().equals(Integer.valueOf(i))) {
                        arrayList.set(i, list.get(entry.getValue().intValue()));
                    }
                }
            }
            builder.addTypeParameters(arrayList);
            typeDataBuild = builder.build();
        }
        TypeData<?> typeData = typeDataBuild;
        return propertyModel.getTypeData().equals(typeData) ? propertyModel : new PropertyModel<>(propertyModel.getName(), propertyModel.getReadName(), propertyModel.getWriteName(), typeData, null, propertyModel.getPropertySerialization(), propertyModel.useDiscriminator(), propertyModel.getPropertyAccessor(), propertyModel.getError());
    }

    private Codec<T> getCodecFromDocument(BsonReader bsonReader, boolean z, String str, CodecRegistry codecRegistry, DiscriminatorLookup discriminatorLookup, Codec<T> codec) {
        if (z) {
            BsonReaderMark mark = bsonReader.getMark();
            bsonReader.readStartDocument();
            boolean z2 = false;
            while (!z2 && bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (str.equals(bsonReader.readName())) {
                    z2 = true;
                    try {
                        codec = codecRegistry.get(discriminatorLookup.lookup(bsonReader.readString()));
                    } catch (Exception e) {
                        throw new CodecConfigurationException(String.format("Failed to decode '%s'. Decoding errored with: %s", this.classModel.getName(), e.getMessage()), e);
                    }
                } else {
                    bsonReader.skipValue();
                }
            }
            mark.reset();
        }
        return codec;
    }

    private PropertyModel<?> getPropertyModelByWriteName(ClassModel<T> classModel, String str) {
        for (PropertyModel<?> propertyModel : classModel.getPropertyModels()) {
            if (propertyModel.isWritable() && propertyModel.getWriteName().equals(str)) {
                return propertyModel;
            }
        }
        return null;
    }

    private static <T> boolean shouldSpecialize(ClassModel<T> classModel) {
        if (!classModel.hasTypeParameters()) {
            return true;
        }
        for (Map.Entry<String, TypeParameterMap> entry : classModel.getPropertyNameToTypeParameterMap().entrySet()) {
            TypeParameterMap value = entry.getValue();
            PropertyModel<?> propertyModel = classModel.getPropertyModel(entry.getKey());
            if (value.hasTypeParameters() && (propertyModel == null || propertyModel.getCodec() == null)) {
                return false;
            }
        }
        return true;
    }
}

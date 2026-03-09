package org.bson.codecs.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.assertions.Assertions;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.diagnostics.Logger;
import org.bson.diagnostics.Loggers;

/* JADX INFO: loaded from: classes4.dex */
public final class PojoCodecProvider implements CodecProvider {
    static final Logger LOGGER = Loggers.getLogger("codecs.pojo");
    private final boolean automatic;
    private final Map<Class<?>, ClassModel<?>> classModels;
    private final List<Convention> conventions;
    private final DiscriminatorLookup discriminatorLookup;
    private final Set<String> packages;
    private final List<PropertyCodecProvider> propertyCodecProviders;

    private PojoCodecProvider(boolean z, Map<Class<?>, ClassModel<?>> map, Set<String> set, List<Convention> list, List<PropertyCodecProvider> list2) {
        this.automatic = z;
        this.classModels = map;
        this.packages = set;
        this.conventions = list;
        this.discriminatorLookup = new DiscriminatorLookup(map, set);
        this.propertyCodecProviders = list2;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override // org.bson.codecs.configuration.CodecProvider
    public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry) {
        return getPojoCodec(cls, codecRegistry);
    }

    private <T> PojoCodec<T> getPojoCodec(Class<T> cls, CodecRegistry codecRegistry) {
        ClassModel<?> classModel = this.classModels.get(cls);
        if (classModel != null) {
            return new PojoCodecImpl(classModel, codecRegistry, this.propertyCodecProviders, this.discriminatorLookup);
        }
        if (this.automatic || (cls.getPackage() != null && this.packages.contains(cls.getPackage().getName()))) {
            try {
                ClassModel<?> classModelCreateClassModel = createClassModel(cls, this.conventions);
                if (!cls.isInterface()) {
                    if (!classModelCreateClassModel.getPropertyModels().isEmpty()) {
                    }
                }
                this.discriminatorLookup.addClassModel(classModelCreateClassModel);
                return new AutomaticPojoCodec(new PojoCodecImpl(classModelCreateClassModel, codecRegistry, this.propertyCodecProviders, this.discriminatorLookup));
            } catch (Exception e) {
                LOGGER.warn(String.format("Cannot use '%s' with the PojoCodec.", cls.getSimpleName()), e);
                return null;
            }
        }
        return null;
    }

    public static final class Builder {
        private boolean automatic;
        private final Map<Class<?>, ClassModel<?>> classModels;
        private final List<Class<?>> clazzes;
        private List<Convention> conventions;
        private final Set<String> packages;
        private final List<PropertyCodecProvider> propertyCodecProviders;

        public PojoCodecProvider build() {
            List listUnmodifiableList = this.conventions != null ? Collections.unmodifiableList(new ArrayList(this.conventions)) : null;
            for (Class<?> cls : this.clazzes) {
                if (!this.classModels.containsKey(cls)) {
                    register(PojoCodecProvider.createClassModel(cls, listUnmodifiableList));
                }
            }
            return new PojoCodecProvider(this.automatic, this.classModels, this.packages, listUnmodifiableList, this.propertyCodecProviders);
        }

        public Builder automatic(boolean z) {
            this.automatic = z;
            return this;
        }

        public Builder conventions(List<Convention> list) {
            this.conventions = (List) Assertions.notNull("conventions", list);
            return this;
        }

        public Builder register(Class<?>... clsArr) {
            this.clazzes.addAll(Arrays.asList(clsArr));
            return this;
        }

        public Builder register(ClassModel<?>... classModelArr) {
            Assertions.notNull("classModels", classModelArr);
            for (ClassModel<?> classModel : classModelArr) {
                this.classModels.put(classModel.getType(), classModel);
            }
            return this;
        }

        public Builder register(String... strArr) {
            this.packages.addAll(Arrays.asList((Object[]) Assertions.notNull("packageNames", strArr)));
            return this;
        }

        public Builder register(PropertyCodecProvider... propertyCodecProviderArr) {
            this.propertyCodecProviders.addAll(Arrays.asList((Object[]) Assertions.notNull("providers", propertyCodecProviderArr)));
            return this;
        }

        private Builder() {
            this.packages = new HashSet();
            this.classModels = new HashMap();
            this.clazzes = new ArrayList();
            this.conventions = null;
            this.propertyCodecProviders = new ArrayList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> ClassModel<T> createClassModel(Class<T> cls, List<Convention> list) {
        ClassModelBuilder classModelBuilderBuilder = ClassModel.builder(cls);
        if (list != null) {
            classModelBuilderBuilder.conventions(list);
        }
        return classModelBuilderBuilder.build();
    }
}

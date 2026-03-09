package org.bson.codecs.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class ClassModel<T> {
    private final String discriminator;
    private final boolean discriminatorEnabled;
    private final String discriminatorKey;
    private final boolean hasTypeParameters;
    private final IdPropertyModelHolder<?> idPropertyModelHolder;
    private final InstanceCreatorFactory<T> instanceCreatorFactory;
    private final String name;
    private final List<PropertyModel<?>> propertyModels;
    private final Map<String, TypeParameterMap> propertyNameToTypeParameterMap;
    private final Class<T> type;

    ClassModel(Class<T> cls, Map<String, TypeParameterMap> map, InstanceCreatorFactory<T> instanceCreatorFactory, Boolean bool, String str, String str2, IdPropertyModelHolder<?> idPropertyModelHolder, List<PropertyModel<?>> list) {
        this.name = cls.getSimpleName();
        this.type = cls;
        this.hasTypeParameters = cls.getTypeParameters().length > 0;
        this.propertyNameToTypeParameterMap = Collections.unmodifiableMap(new HashMap(map));
        this.instanceCreatorFactory = instanceCreatorFactory;
        this.discriminatorEnabled = bool.booleanValue();
        this.discriminatorKey = str;
        this.discriminator = str2;
        this.idPropertyModelHolder = idPropertyModelHolder;
        this.propertyModels = Collections.unmodifiableList(new ArrayList(list));
    }

    public static <S> ClassModelBuilder<S> builder(Class<S> cls) {
        return new ClassModelBuilder<>(cls);
    }

    InstanceCreator<T> getInstanceCreator() {
        return this.instanceCreatorFactory.create();
    }

    public Class<T> getType() {
        return this.type;
    }

    public boolean hasTypeParameters() {
        return this.hasTypeParameters;
    }

    public boolean useDiscriminator() {
        return this.discriminatorEnabled;
    }

    public String getDiscriminatorKey() {
        return this.discriminatorKey;
    }

    public String getDiscriminator() {
        return this.discriminator;
    }

    public PropertyModel<?> getPropertyModel(String str) {
        for (PropertyModel<?> propertyModel : this.propertyModels) {
            if (propertyModel.getName().equals(str)) {
                return propertyModel;
            }
        }
        return null;
    }

    public List<PropertyModel<?>> getPropertyModels() {
        return this.propertyModels;
    }

    public PropertyModel<?> getIdPropertyModel() {
        IdPropertyModelHolder<?> idPropertyModelHolder = this.idPropertyModelHolder;
        if (idPropertyModelHolder != null) {
            return idPropertyModelHolder.getPropertyModel();
        }
        return null;
    }

    IdPropertyModelHolder<?> getIdPropertyModelHolder() {
        return this.idPropertyModelHolder;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "ClassModel{type=" + this.type + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassModel classModel = (ClassModel) obj;
        if (this.discriminatorEnabled != classModel.discriminatorEnabled || !getType().equals(classModel.getType()) || !getInstanceCreatorFactory().equals(classModel.getInstanceCreatorFactory())) {
            return false;
        }
        if (getDiscriminatorKey() == null ? classModel.getDiscriminatorKey() != null : !getDiscriminatorKey().equals(classModel.getDiscriminatorKey())) {
            return false;
        }
        if (getDiscriminator() == null ? classModel.getDiscriminator() != null : !getDiscriminator().equals(classModel.getDiscriminator())) {
            return false;
        }
        IdPropertyModelHolder<?> idPropertyModelHolder = this.idPropertyModelHolder;
        if (idPropertyModelHolder == null ? classModel.idPropertyModelHolder == null : idPropertyModelHolder.equals(classModel.idPropertyModelHolder)) {
            return getPropertyModels().equals(classModel.getPropertyModels()) && getPropertyNameToTypeParameterMap().equals(classModel.getPropertyNameToTypeParameterMap());
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((getType().hashCode() * 31) + getInstanceCreatorFactory().hashCode()) * 31) + (this.discriminatorEnabled ? 1 : 0)) * 31) + (getDiscriminatorKey() != null ? getDiscriminatorKey().hashCode() : 0)) * 31) + (getDiscriminator() != null ? getDiscriminator().hashCode() : 0)) * 31) + (getIdPropertyModelHolder() != null ? getIdPropertyModelHolder().hashCode() : 0)) * 31) + getPropertyModels().hashCode()) * 31) + getPropertyNameToTypeParameterMap().hashCode();
    }

    InstanceCreatorFactory<T> getInstanceCreatorFactory() {
        return this.instanceCreatorFactory;
    }

    Map<String, TypeParameterMap> getPropertyNameToTypeParameterMap() {
        return this.propertyNameToTypeParameterMap;
    }
}

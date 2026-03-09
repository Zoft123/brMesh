package org.bson.codecs.pojo;

import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes4.dex */
public final class PropertyModel<T> {
    private volatile Codec<T> cachedCodec;
    private final Codec<T> codec;
    private final String error;
    private final String name;
    private final PropertyAccessor<T> propertyAccessor;
    private final PropertySerialization<T> propertySerialization;
    private final String readName;
    private final TypeData<T> typeData;
    private final Boolean useDiscriminator;
    private final String writeName;

    PropertyModel(String str, String str2, String str3, TypeData<T> typeData, Codec<T> codec, PropertySerialization<T> propertySerialization, Boolean bool, PropertyAccessor<T> propertyAccessor, String str4) {
        this.name = str;
        this.readName = str2;
        this.writeName = str3;
        this.typeData = typeData;
        this.codec = codec;
        this.cachedCodec = codec;
        this.propertySerialization = propertySerialization;
        this.useDiscriminator = bool;
        this.propertyAccessor = propertyAccessor;
        this.error = str4;
    }

    public static <T> PropertyModelBuilder<T> builder() {
        return new PropertyModelBuilder<>();
    }

    public String getName() {
        return this.name;
    }

    public String getWriteName() {
        return this.writeName;
    }

    public String getReadName() {
        return this.readName;
    }

    public boolean isWritable() {
        return this.writeName != null;
    }

    public boolean isReadable() {
        return this.readName != null;
    }

    public TypeData<T> getTypeData() {
        return this.typeData;
    }

    public Codec<T> getCodec() {
        return this.codec;
    }

    public boolean shouldSerialize(T t) {
        return this.propertySerialization.shouldSerialize(t);
    }

    public PropertyAccessor<T> getPropertyAccessor() {
        return this.propertyAccessor;
    }

    public Boolean useDiscriminator() {
        return this.useDiscriminator;
    }

    public String toString() {
        return "PropertyModel{propertyName='" + this.name + "', readName='" + this.readName + "', writeName='" + this.writeName + "', typeData=" + this.typeData + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PropertyModel propertyModel = (PropertyModel) obj;
        if (getName() == null ? propertyModel.getName() != null : !getName().equals(propertyModel.getName())) {
            return false;
        }
        if (getReadName() == null ? propertyModel.getReadName() != null : !getReadName().equals(propertyModel.getReadName())) {
            return false;
        }
        if (getWriteName() == null ? propertyModel.getWriteName() != null : !getWriteName().equals(propertyModel.getWriteName())) {
            return false;
        }
        if (getTypeData() == null ? propertyModel.getTypeData() != null : !getTypeData().equals(propertyModel.getTypeData())) {
            return false;
        }
        if (getCodec() == null ? propertyModel.getCodec() != null : !getCodec().equals(propertyModel.getCodec())) {
            return false;
        }
        if (getPropertySerialization() == null ? propertyModel.getPropertySerialization() != null : !getPropertySerialization().equals(propertyModel.getPropertySerialization())) {
            return false;
        }
        Boolean bool = this.useDiscriminator;
        if (bool == null ? propertyModel.useDiscriminator != null : !bool.equals(propertyModel.useDiscriminator)) {
            return false;
        }
        if (getPropertyAccessor() == null ? propertyModel.getPropertyAccessor() != null : !getPropertyAccessor().equals(propertyModel.getPropertyAccessor())) {
            return false;
        }
        if (getError() == null ? propertyModel.getError() == null : getError().equals(propertyModel.getError())) {
            return getCachedCodec() == null ? propertyModel.getCachedCodec() == null : getCachedCodec().equals(propertyModel.getCachedCodec());
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (((((((((((getName() != null ? getName().hashCode() : 0) * 31) + (getReadName() != null ? getReadName().hashCode() : 0)) * 31) + (getWriteName() != null ? getWriteName().hashCode() : 0)) * 31) + (getTypeData() != null ? getTypeData().hashCode() : 0)) * 31) + (getCodec() != null ? getCodec().hashCode() : 0)) * 31) + (getPropertySerialization() != null ? getPropertySerialization().hashCode() : 0)) * 31;
        Boolean bool = this.useDiscriminator;
        return ((((((iHashCode + (bool != null ? bool.hashCode() : 0)) * 31) + (getPropertyAccessor() != null ? getPropertyAccessor().hashCode() : 0)) * 31) + (getError() != null ? getError().hashCode() : 0)) * 31) + (getCachedCodec() != null ? getCachedCodec().hashCode() : 0);
    }

    boolean hasError() {
        return this.error != null;
    }

    String getError() {
        return this.error;
    }

    PropertySerialization<T> getPropertySerialization() {
        return this.propertySerialization;
    }

    void cachedCodec(Codec<T> codec) {
        this.cachedCodec = codec;
    }

    Codec<T> getCachedCodec() {
        return this.cachedCodec;
    }
}

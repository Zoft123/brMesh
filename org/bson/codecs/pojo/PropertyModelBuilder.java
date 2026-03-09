package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import org.bson.assertions.Assertions;
import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes4.dex */
public final class PropertyModelBuilder<T> {
    private Codec<T> codec;
    private Boolean discriminatorEnabled;
    private String error;
    private String name;
    private PropertyAccessor<T> propertyAccessor;
    private PropertySerialization<T> propertySerialization;
    private String readName;
    private TypeData<T> typeData;
    private String writeName;
    private List<Annotation> readAnnotations = Collections.EMPTY_LIST;
    private List<Annotation> writeAnnotations = Collections.EMPTY_LIST;

    PropertyModelBuilder() {
    }

    public String getName() {
        return this.name;
    }

    public String getReadName() {
        return this.readName;
    }

    public PropertyModelBuilder<T> readName(String str) {
        this.readName = str;
        return this;
    }

    public String getWriteName() {
        return this.writeName;
    }

    public PropertyModelBuilder<T> writeName(String str) {
        this.writeName = str;
        return this;
    }

    public PropertyModelBuilder<T> codec(Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    Codec<T> getCodec() {
        return this.codec;
    }

    public PropertyModelBuilder<T> propertySerialization(PropertySerialization<T> propertySerialization) {
        this.propertySerialization = (PropertySerialization) Assertions.notNull("propertySerialization", propertySerialization);
        return this;
    }

    public PropertySerialization<T> getPropertySerialization() {
        return this.propertySerialization;
    }

    public List<Annotation> getReadAnnotations() {
        return this.readAnnotations;
    }

    public PropertyModelBuilder<T> readAnnotations(List<Annotation> list) {
        this.readAnnotations = Collections.unmodifiableList((List) Assertions.notNull("annotations", list));
        return this;
    }

    public List<Annotation> getWriteAnnotations() {
        return this.writeAnnotations;
    }

    public PropertyModelBuilder<T> writeAnnotations(List<Annotation> list) {
        this.writeAnnotations = list;
        return this;
    }

    public boolean isWritable() {
        return this.writeName != null;
    }

    public boolean isReadable() {
        return this.readName != null;
    }

    public Boolean isDiscriminatorEnabled() {
        return this.discriminatorEnabled;
    }

    public PropertyModelBuilder<T> discriminatorEnabled(boolean z) {
        this.discriminatorEnabled = Boolean.valueOf(z);
        return this;
    }

    public PropertyAccessor<T> getPropertyAccessor() {
        return this.propertyAccessor;
    }

    public PropertyModelBuilder<T> propertyAccessor(PropertyAccessor<T> propertyAccessor) {
        this.propertyAccessor = propertyAccessor;
        return this;
    }

    public PropertyModel<T> build() {
        if (!isReadable() && !isWritable()) {
            throw new IllegalStateException(String.format("Invalid PropertyModel '%s', neither readable or writable,", this.name));
        }
        return new PropertyModel<>((String) PojoBuilderHelper.stateNotNull("propertyName", this.name), this.readName, this.writeName, (TypeData) PojoBuilderHelper.stateNotNull("typeData", this.typeData), this.codec, (PropertySerialization) PojoBuilderHelper.stateNotNull("propertySerialization", this.propertySerialization), this.discriminatorEnabled, (PropertyAccessor) PojoBuilderHelper.stateNotNull("propertyAccessor", this.propertyAccessor), this.error);
    }

    public String toString() {
        return String.format("PropertyModelBuilder{propertyName=%s, typeData=%s}", this.name, this.typeData);
    }

    PropertyModelBuilder<T> propertyName(String str) {
        this.name = (String) Assertions.notNull("propertyName", str);
        return this;
    }

    TypeData<T> getTypeData() {
        return this.typeData;
    }

    PropertyModelBuilder<T> typeData(TypeData<T> typeData) {
        this.typeData = (TypeData) Assertions.notNull("typeData", typeData);
        return this;
    }

    PropertyModelBuilder<T> setError(String str) {
        this.error = str;
        return this;
    }
}

package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class PropertyMetadata<T> {
    private final String declaringClassName;
    private String error;
    private Field field;
    private Method getter;
    private final String name;
    private Method setter;
    private final TypeData<T> typeData;
    private TypeParameterMap typeParameterMap;
    private List<TypeData<?>> typeParameters;
    private final Map<Class<? extends Annotation>, Annotation> readAnnotations = new HashMap();
    private final Map<Class<? extends Annotation>, Annotation> writeAnnotations = new HashMap();

    PropertyMetadata(String str, String str2, TypeData<T> typeData) {
        this.name = str;
        this.declaringClassName = str2;
        this.typeData = typeData;
    }

    public String getName() {
        return this.name;
    }

    public List<Annotation> getReadAnnotations() {
        return new ArrayList(this.readAnnotations.values());
    }

    public PropertyMetadata<T> addReadAnnotation(Annotation annotation) {
        if (this.readAnnotations.containsKey(annotation.annotationType())) {
            if (annotation.equals(this.readAnnotations.get(annotation.annotationType()))) {
                return this;
            }
            throw new CodecConfigurationException(String.format("Read annotation %s for '%s' already exists in %s", annotation.annotationType(), this.name, this.declaringClassName));
        }
        this.readAnnotations.put(annotation.annotationType(), annotation);
        return this;
    }

    public List<Annotation> getWriteAnnotations() {
        return new ArrayList(this.writeAnnotations.values());
    }

    public PropertyMetadata<T> addWriteAnnotation(Annotation annotation) {
        if (this.writeAnnotations.containsKey(annotation.annotationType())) {
            if (annotation.equals(this.writeAnnotations.get(annotation.annotationType()))) {
                return this;
            }
            throw new CodecConfigurationException(String.format("Write annotation %s for '%s' already exists in %s", annotation.annotationType(), this.name, this.declaringClassName));
        }
        this.writeAnnotations.put(annotation.annotationType(), annotation);
        return this;
    }

    public Field getField() {
        return this.field;
    }

    public PropertyMetadata<T> field(Field field) {
        this.field = field;
        return this;
    }

    public Method getGetter() {
        return this.getter;
    }

    public void setGetter(Method method) {
        this.getter = method;
    }

    public Method getSetter() {
        return this.setter;
    }

    public void setSetter(Method method) {
        this.setter = method;
    }

    public String getDeclaringClassName() {
        return this.declaringClassName;
    }

    public TypeData<T> getTypeData() {
        return this.typeData;
    }

    public TypeParameterMap getTypeParameterMap() {
        return this.typeParameterMap;
    }

    public List<TypeData<?>> getTypeParameters() {
        return this.typeParameters;
    }

    public <S> PropertyMetadata<T> typeParameterInfo(TypeParameterMap typeParameterMap, TypeData<S> typeData) {
        if (typeParameterMap != null && typeData != null) {
            this.typeParameterMap = typeParameterMap;
            this.typeParameters = typeData.getTypeParameters();
        }
        return this;
    }

    String getError() {
        return this.error;
    }

    void setError(String str) {
        this.error = str;
    }

    public boolean isSerializable() {
        if (this.getter != null) {
            Field field = this.field;
            return field == null || notStaticOrTransient(field.getModifiers());
        }
        Field field2 = this.field;
        return field2 != null && isPublicAndNotStaticOrTransient(field2.getModifiers());
    }

    public boolean isDeserializable() {
        if (this.setter != null) {
            Field field = this.field;
            return field == null || (!Modifier.isFinal(field.getModifiers()) && notStaticOrTransient(this.field.getModifiers()));
        }
        Field field2 = this.field;
        return (field2 == null || Modifier.isFinal(field2.getModifiers()) || !isPublicAndNotStaticOrTransient(this.field.getModifiers())) ? false : true;
    }

    private boolean notStaticOrTransient(int i) {
        return (Modifier.isTransient(i) || Modifier.isStatic(i)) ? false : true;
    }

    private boolean isPublicAndNotStaticOrTransient(int i) {
        return Modifier.isPublic(i) && notStaticOrTransient(i);
    }
}

package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

/* JADX INFO: loaded from: classes4.dex */
final class CreatorExecutable<T> {
    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final Integer idPropertyIndex;
    private final Method method;
    private final List<Type> parameterGenericTypes;
    private final List<Class<?>> parameterTypes;
    private final List<BsonProperty> properties;

    CreatorExecutable(Class<T> cls, Constructor<T> constructor) {
        this(cls, constructor, null);
    }

    CreatorExecutable(Class<T> cls, Method method) {
        this(cls, null, method);
    }

    private CreatorExecutable(Class<T> cls, Constructor<T> constructor, Method method) {
        Annotation[][] parameterAnnotations;
        this.properties = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.parameterTypes = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.parameterGenericTypes = arrayList2;
        this.clazz = cls;
        this.constructor = constructor;
        this.method = method;
        Integer num = null;
        if (constructor != null || method != null) {
            Class<?>[] parameterTypes = constructor != null ? constructor.getParameterTypes() : method.getParameterTypes();
            Type[] genericParameterTypes = constructor != null ? constructor.getGenericParameterTypes() : method.getGenericParameterTypes();
            arrayList.addAll(Arrays.asList(parameterTypes));
            arrayList2.addAll(Arrays.asList(genericParameterTypes));
            if (constructor != null) {
                parameterAnnotations = constructor.getParameterAnnotations();
            } else {
                parameterAnnotations = method.getParameterAnnotations();
            }
            Integer numValueOf = null;
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] annotationArr = parameterAnnotations[i];
                int length = annotationArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        Annotation annotation = annotationArr[i2];
                        if (annotation.annotationType().equals(BsonProperty.class)) {
                            this.properties.add((BsonProperty) annotation);
                            break;
                        } else {
                            if (annotation.annotationType().equals(BsonId.class)) {
                                this.properties.add(null);
                                numValueOf = Integer.valueOf(i);
                                break;
                            }
                            i2++;
                        }
                    }
                }
            }
            num = numValueOf;
        }
        this.idPropertyIndex = num;
    }

    Class<T> getType() {
        return this.clazz;
    }

    List<BsonProperty> getProperties() {
        return this.properties;
    }

    Integer getIdPropertyIndex() {
        return this.idPropertyIndex;
    }

    List<Class<?>> getParameterTypes() {
        return this.parameterTypes;
    }

    List<Type> getParameterGenericTypes() {
        return this.parameterGenericTypes;
    }

    T getInstance() {
        checkHasAnExecutable();
        try {
            Constructor<T> constructor = this.constructor;
            if (constructor != null) {
                return constructor.newInstance(null);
            }
            return (T) this.method.invoke(this.clazz, null);
        } catch (Exception e) {
            throw new CodecConfigurationException(e.getMessage(), e);
        }
    }

    T getInstance(Object[] objArr) {
        checkHasAnExecutable();
        try {
            Constructor<T> constructor = this.constructor;
            if (constructor != null) {
                return constructor.newInstance(objArr);
            }
            return (T) this.method.invoke(this.clazz, objArr);
        } catch (Exception e) {
            throw new CodecConfigurationException(e.getMessage(), e);
        }
    }

    CodecConfigurationException getError(Class<?> cls, String str) {
        return getError(cls, this.constructor != null, str);
    }

    private void checkHasAnExecutable() {
        if (this.constructor == null && this.method == null) {
            throw new CodecConfigurationException(String.format("Cannot find a public constructor for '%s'.", this.clazz.getSimpleName()));
        }
    }

    private static CodecConfigurationException getError(Class<?> cls, boolean z, String str) {
        return new CodecConfigurationException(String.format("Invalid @BsonCreator %s in %s. %s", z ? "constructor" : "method", cls.getSimpleName(), str));
    }
}

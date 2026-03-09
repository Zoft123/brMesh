package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.bson.assertions.Assertions;
import org.bson.codecs.pojo.PropertyReflectionUtils;
import org.bson.codecs.pojo.TypeData;
import org.bson.codecs.pojo.TypeParameterMap;

/* JADX INFO: loaded from: classes4.dex */
final class PojoBuilderHelper {
    static <T> void configureClassModelBuilder(ClassModelBuilder<T> classModelBuilder, Class<T> cls) {
        classModelBuilder.type((Class) Assertions.notNull("clazz", cls));
        ArrayList arrayList = new ArrayList();
        TreeSet treeSet = new TreeSet();
        Map<String, TypeParameterMap> map = new HashMap<>();
        String simpleName = cls.getSimpleName();
        HashMap map2 = new HashMap();
        Class<T> superclass = cls;
        TypeData typeDataNewInstance = null;
        while (!superclass.isEnum() && superclass.getSuperclass() != null) {
            arrayList.addAll(Arrays.asList(superclass.getDeclaredAnnotations()));
            ArrayList arrayList2 = new ArrayList();
            for (TypeVariable<Class<T>> typeVariable : superclass.getTypeParameters()) {
                arrayList2.add(typeVariable.getName());
            }
            PropertyReflectionUtils.PropertyMethods propertyMethods = PropertyReflectionUtils.getPropertyMethods(superclass);
            for (Method method : propertyMethods.getSetterMethods()) {
                String propertyName = PropertyReflectionUtils.toPropertyName(method);
                treeSet.add(propertyName);
                PropertyMetadata orCreateMethodPropertyMetadata = getOrCreateMethodPropertyMetadata(propertyName, simpleName, map2, TypeData.newInstance(method), map, typeDataNewInstance, arrayList2, getGenericType(method));
                if (orCreateMethodPropertyMetadata.getSetter() == null) {
                    orCreateMethodPropertyMetadata.setSetter(method);
                    for (Annotation annotation : method.getDeclaredAnnotations()) {
                        orCreateMethodPropertyMetadata.addWriteAnnotation(annotation);
                    }
                }
            }
            for (Method method2 : propertyMethods.getGetterMethods()) {
                String propertyName2 = PropertyReflectionUtils.toPropertyName(method2);
                treeSet.add(propertyName2);
                PropertyMetadata propertyMetadata = (PropertyMetadata) map2.get(propertyName2);
                if (propertyMetadata == null || propertyMetadata.getGetter() == null) {
                    PropertyMetadata orCreateMethodPropertyMetadata2 = getOrCreateMethodPropertyMetadata(propertyName2, simpleName, map2, TypeData.newInstance(method2), map, typeDataNewInstance, arrayList2, getGenericType(method2));
                    if (orCreateMethodPropertyMetadata2.getGetter() == null) {
                        orCreateMethodPropertyMetadata2.setGetter(method2);
                        Annotation[] declaredAnnotations = method2.getDeclaredAnnotations();
                        for (Annotation annotation2 : declaredAnnotations) {
                            orCreateMethodPropertyMetadata2.addReadAnnotation(annotation2);
                        }
                    }
                }
            }
            Field[] declaredFields = superclass.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                treeSet.add(field.getName());
                TreeSet treeSet2 = treeSet;
                PropertyMetadata orCreateFieldPropertyMetadata = getOrCreateFieldPropertyMetadata(field.getName(), simpleName, map2, TypeData.newInstance(field), map, typeDataNewInstance, arrayList2, field.getGenericType());
                if (orCreateFieldPropertyMetadata != null && orCreateFieldPropertyMetadata.getField() == null) {
                    orCreateFieldPropertyMetadata.field(field);
                    Annotation[] declaredAnnotations2 = field.getDeclaredAnnotations();
                    int length2 = declaredAnnotations2.length;
                    int i2 = 0;
                    while (i2 < length2) {
                        Annotation[] annotationArr = declaredAnnotations2;
                        Annotation annotation3 = annotationArr[i2];
                        orCreateFieldPropertyMetadata.addReadAnnotation(annotation3);
                        orCreateFieldPropertyMetadata.addWriteAnnotation(annotation3);
                        i2++;
                        declaredAnnotations2 = annotationArr;
                    }
                }
                i++;
                treeSet = treeSet2;
            }
            typeDataNewInstance = TypeData.newInstance(superclass.getGenericSuperclass(), superclass);
            superclass = superclass.getSuperclass();
            treeSet = treeSet;
        }
        TreeSet treeSet3 = treeSet;
        if (superclass.isInterface()) {
            arrayList.addAll(Arrays.asList(superclass.getDeclaredAnnotations()));
        }
        Iterator it = treeSet3.iterator();
        while (it.hasNext()) {
            PropertyMetadata propertyMetadata2 = (PropertyMetadata) map2.get((String) it.next());
            if (propertyMetadata2.isSerializable() || propertyMetadata2.isDeserializable()) {
                classModelBuilder.addProperty(createPropertyModelBuilder(propertyMetadata2));
            }
        }
        Collections.reverse(arrayList);
        classModelBuilder.annotations(arrayList);
        classModelBuilder.propertyNameToTypeParameterMap(map);
        Constructor<?> constructor = null;
        for (Constructor<?> constructor2 : cls.getDeclaredConstructors()) {
            if (constructor2.getParameterTypes().length == 0 && (Modifier.isPublic(constructor2.getModifiers()) || Modifier.isProtected(constructor2.getModifiers()))) {
                constructor2.setAccessible(true);
                constructor = constructor2;
            }
        }
        classModelBuilder.instanceCreatorFactory(new InstanceCreatorFactoryImpl<>(new CreatorExecutable(cls, constructor)));
    }

    private static <T, S> PropertyMetadata<T> getOrCreateMethodPropertyMetadata(String str, String str2, Map<String, PropertyMetadata<?>> map, TypeData<T> typeData, Map<String, TypeParameterMap> map2, TypeData<S> typeData2, List<String> list, Type type) {
        PropertyMetadata<T> orCreatePropertyMetadata = getOrCreatePropertyMetadata(str, str2, map, typeData);
        if (!isAssignableClass(orCreatePropertyMetadata.getTypeData().getType(), typeData.getType())) {
            orCreatePropertyMetadata.setError(String.format("Property '%s' in %s, has differing data types: %s and %s.", str, str2, orCreatePropertyMetadata.getTypeData(), typeData));
        }
        cachePropertyTypeData(orCreatePropertyMetadata, map2, typeData2, list, type);
        return orCreatePropertyMetadata;
    }

    private static boolean isAssignableClass(Class<?> cls, Class<?> cls2) {
        return cls.isAssignableFrom(cls2) || cls2.isAssignableFrom(cls);
    }

    private static <T, S> PropertyMetadata<T> getOrCreateFieldPropertyMetadata(String str, String str2, Map<String, PropertyMetadata<?>> map, TypeData<T> typeData, Map<String, TypeParameterMap> map2, TypeData<S> typeData2, List<String> list, Type type) {
        PropertyMetadata<T> orCreatePropertyMetadata = getOrCreatePropertyMetadata(str, str2, map, typeData);
        if (!orCreatePropertyMetadata.getTypeData().getType().isAssignableFrom(typeData.getType())) {
            return null;
        }
        cachePropertyTypeData(orCreatePropertyMetadata, map2, typeData2, list, type);
        return orCreatePropertyMetadata;
    }

    private static <T> PropertyMetadata<T> getOrCreatePropertyMetadata(String str, String str2, Map<String, PropertyMetadata<?>> map, TypeData<T> typeData) {
        PropertyMetadata<T> propertyMetadata = (PropertyMetadata) map.get(str);
        if (propertyMetadata != null) {
            return propertyMetadata;
        }
        PropertyMetadata<T> propertyMetadata2 = new PropertyMetadata<>(str, str2, typeData);
        map.put(str, propertyMetadata2);
        return propertyMetadata2;
    }

    private static <T, S> void cachePropertyTypeData(PropertyMetadata<T> propertyMetadata, Map<String, TypeParameterMap> map, TypeData<S> typeData, List<String> list, Type type) {
        TypeParameterMap typeParameterMap = getTypeParameterMap(list, type);
        map.put(propertyMetadata.getName(), typeParameterMap);
        propertyMetadata.typeParameterInfo(typeParameterMap, typeData);
    }

    private static Type getGenericType(Method method) {
        return PropertyReflectionUtils.isGetter(method) ? method.getGenericReturnType() : method.getGenericParameterTypes()[0];
    }

    static <T> PropertyModelBuilder<T> createPropertyModelBuilder(PropertyMetadata<T> propertyMetadata) {
        PropertyModelBuilder<T> error = PropertyModel.builder().propertyName(propertyMetadata.getName()).readName(propertyMetadata.getName()).writeName(propertyMetadata.getName()).typeData(propertyMetadata.getTypeData()).readAnnotations(propertyMetadata.getReadAnnotations()).writeAnnotations(propertyMetadata.getWriteAnnotations()).propertySerialization(new PropertyModelSerializationImpl()).propertyAccessor(new PropertyAccessorImpl(propertyMetadata)).setError(propertyMetadata.getError());
        if (propertyMetadata.getTypeParameters() != null) {
            specializePropertyModelBuilder(error, propertyMetadata);
        }
        return error;
    }

    private static TypeParameterMap getTypeParameterMap(List<String> list, Type type) {
        int iIndexOf = list.indexOf(type.toString());
        TypeParameterMap.Builder builder = TypeParameterMap.builder();
        if (iIndexOf != -1) {
            builder.addIndex(iIndexOf);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            for (int i = 0; i < parameterizedType.getActualTypeArguments().length; i++) {
                int iIndexOf2 = list.indexOf(parameterizedType.getActualTypeArguments()[i].toString());
                if (iIndexOf2 != -1) {
                    builder.addIndex(i, iIndexOf2);
                }
            }
        }
        return builder.build();
    }

    private static <V> void specializePropertyModelBuilder(PropertyModelBuilder<V> propertyModelBuilder, PropertyMetadata<V> propertyMetadata) {
        TypeData<V> typeDataBuild;
        if (!propertyMetadata.getTypeParameterMap().hasTypeParameters() || propertyMetadata.getTypeParameters().isEmpty()) {
            return;
        }
        Map<Integer, Integer> propertyToClassParamIndexMap = propertyMetadata.getTypeParameterMap().getPropertyToClassParamIndexMap();
        Integer num = propertyToClassParamIndexMap.get(-1);
        if (num != null) {
            typeDataBuild = (TypeData) propertyMetadata.getTypeParameters().get(num.intValue());
        } else {
            TypeData.Builder builder = TypeData.builder(propertyModelBuilder.getTypeData().getType());
            ArrayList arrayList = new ArrayList(propertyModelBuilder.getTypeData().getTypeParameters());
            for (int i = 0; i < arrayList.size(); i++) {
                for (Map.Entry<Integer, Integer> entry : propertyToClassParamIndexMap.entrySet()) {
                    if (entry.getKey().equals(Integer.valueOf(i))) {
                        arrayList.set(i, propertyMetadata.getTypeParameters().get(entry.getValue().intValue()));
                    }
                }
            }
            builder.addTypeParameters(arrayList);
            typeDataBuild = builder.build();
        }
        propertyModelBuilder.typeData(typeDataBuild);
    }

    static <V> V stateNotNull(String str, V v) {
        if (v != null) {
            return v;
        }
        throw new IllegalStateException(String.format("%s cannot be null", str));
    }

    private PojoBuilderHelper() {
    }
}

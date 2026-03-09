package org.bson.codecs.pojo;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
final class TypeData<T> implements TypeWithTypeParameters<T> {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_CLASS_MAP;
    private final Class<T> type;
    private final List<TypeData<?>> typeParameters;

    public static <T> Builder<T> builder(Class<T> cls) {
        return new Builder<>((Class) Assertions.notNull(GlobalVariable.TYPE, cls));
    }

    public static TypeData<?> newInstance(Method method) {
        if (PropertyReflectionUtils.isGetter(method)) {
            return newInstance(method.getGenericReturnType(), method.getReturnType());
        }
        return newInstance(method.getGenericParameterTypes()[0], method.getParameterTypes()[0]);
    }

    public static TypeData<?> newInstance(Field field) {
        return newInstance(field.getGenericType(), field.getType());
    }

    public static <T> TypeData<T> newInstance(Type type, Class<T> cls) {
        Builder builder = builder(cls);
        if (type instanceof ParameterizedType) {
            for (Type type2 : ((ParameterizedType) type).getActualTypeArguments()) {
                getNestedTypeData(builder, type2);
            }
        }
        return builder.build();
    }

    private static <T> void getNestedTypeData(Builder<T> builder, Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Builder builder2 = builder((Class) parameterizedType.getRawType());
            for (Type type2 : parameterizedType.getActualTypeArguments()) {
                getNestedTypeData(builder2, type2);
            }
            builder.addTypeParameter(builder2.build());
            return;
        }
        if (type instanceof WildcardType) {
            builder.addTypeParameter(builder((Class) ((WildcardType) type).getUpperBounds()[0]).build());
        } else if (type instanceof TypeVariable) {
            builder.addTypeParameter(builder(Object.class).build());
        } else if (type instanceof Class) {
            builder.addTypeParameter(builder((Class) type).build());
        }
    }

    @Override // org.bson.codecs.pojo.TypeWithTypeParameters
    public Class<T> getType() {
        return this.type;
    }

    @Override // org.bson.codecs.pojo.TypeWithTypeParameters
    public List<TypeData<?>> getTypeParameters() {
        return this.typeParameters;
    }

    public static final class Builder<T> {
        private final Class<T> type;
        private final List<TypeData<?>> typeParameters;

        private Builder(Class<T> cls) {
            this.typeParameters = new ArrayList();
            this.type = cls;
        }

        public <S> Builder<T> addTypeParameter(TypeData<S> typeData) {
            this.typeParameters.add((TypeData<?>) Assertions.notNull("typeParameter", typeData));
            return this;
        }

        public Builder<T> addTypeParameters(List<TypeData<?>> list) {
            Assertions.notNull("typeParameters", list);
            Iterator<TypeData<?>> it = list.iterator();
            while (it.hasNext()) {
                addTypeParameter((TypeData) it.next());
            }
            return this;
        }

        public TypeData<T> build() {
            return new TypeData<>(this.type, Collections.unmodifiableList(this.typeParameters));
        }
    }

    public String toString() {
        String str;
        if (this.typeParameters.isEmpty()) {
            str = "";
        } else {
            str = ", typeParameters=[" + nestedTypeParameters(this.typeParameters) + "]";
        }
        return "TypeData{type=" + this.type.getSimpleName() + str + "}";
    }

    private static String nestedTypeParameters(List<TypeData<?>> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        int i = 0;
        for (TypeData<?> typeData : list) {
            i++;
            sb.append(typeData.getType().getSimpleName());
            if (!typeData.getTypeParameters().isEmpty()) {
                sb.append(String.format("<%s>", nestedTypeParameters(typeData.getTypeParameters())));
            }
            if (i < size) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypeData)) {
            return false;
        }
        TypeData typeData = (TypeData) obj;
        return getType().equals(typeData.getType()) && getTypeParameters().equals(typeData.getTypeParameters());
    }

    public int hashCode() {
        return (getType().hashCode() * 31) + getTypeParameters().hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private TypeData(Class<T> cls, List<TypeData<?>> list) {
        this.type = (Class<T>) boxType(cls);
        this.typeParameters = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean isAssignableFrom(Class<?> cls) {
        return this.type.isAssignableFrom((Class<?>) boxType(cls));
    }

    private <S> Class<S> boxType(Class<S> cls) {
        return cls.isPrimitive() ? (Class) PRIMITIVE_CLASS_MAP.get(cls) : cls;
    }

    static {
        HashMap map = new HashMap();
        map.put(Boolean.TYPE, Boolean.class);
        map.put(Byte.TYPE, Byte.class);
        map.put(Character.TYPE, Character.class);
        map.put(Double.TYPE, Double.class);
        map.put(Float.TYPE, Float.class);
        map.put(Integer.TYPE, Integer.class);
        map.put(Long.TYPE, Long.class);
        map.put(Short.TYPE, Short.class);
        PRIMITIVE_CLASS_MAP = map;
    }
}

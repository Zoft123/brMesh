package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;

    /* JADX INFO: renamed from: kotlin, reason: collision with root package name */
    public boolean f5kotlin;
    public Constructor<?> kotlinDefaultConstructor;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    public JavaBeanInfo(Class<?> cls, Class<?> cls2, Constructor<?> constructor, Constructor<?> constructor2, Method method, Method method2, JSONType jSONType, List<FieldInfo> list) {
        JSONField jSONField;
        this.clazz = cls;
        this.builderClass = cls2;
        this.defaultConstructor = constructor;
        this.creatorConstructor = constructor2;
        this.factoryMethod = method;
        this.parserFeatures = TypeUtils.getParserFeatures(cls);
        this.buildMethod = method2;
        this.jsonType = jSONType;
        if (jSONType != null) {
            String strTypeName = jSONType.typeName();
            String strTypeKey = jSONType.typeKey();
            this.typeKey = strTypeKey.length() <= 0 ? null : strTypeKey;
            if (strTypeName.length() != 0) {
                this.typeName = strTypeName;
            } else {
                this.typeName = cls.getName();
            }
            String[] strArrOrders = jSONType.orders();
            this.orders = strArrOrders.length == 0 ? null : strArrOrders;
        } else {
            this.typeName = cls.getName();
            this.typeKey = null;
            this.orders = null;
        }
        FieldInfo[] fieldInfoArr = new FieldInfo[list.size()];
        this.fields = fieldInfoArr;
        list.toArray(fieldInfoArr);
        FieldInfo[] fieldInfoArr2 = new FieldInfo[fieldInfoArr.length];
        int i = 0;
        if (this.orders != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
            for (FieldInfo fieldInfo : fieldInfoArr) {
                linkedHashMap.put(fieldInfo.name, fieldInfo);
            }
            int i2 = 0;
            for (String str : this.orders) {
                FieldInfo fieldInfo2 = (FieldInfo) linkedHashMap.get(str);
                if (fieldInfo2 != null) {
                    fieldInfoArr2[i2] = fieldInfo2;
                    linkedHashMap.remove(str);
                    i2++;
                }
            }
            Iterator it = linkedHashMap.values().iterator();
            while (it.hasNext()) {
                fieldInfoArr2[i2] = (FieldInfo) it.next();
                i2++;
            }
        } else {
            System.arraycopy(fieldInfoArr, 0, fieldInfoArr2, 0, fieldInfoArr.length);
            Arrays.sort(fieldInfoArr2);
        }
        this.sortedFields = Arrays.equals(this.fields, fieldInfoArr2) ? this.fields : fieldInfoArr2;
        if (constructor != null) {
            this.defaultConstructorParameterSize = constructor.getParameterTypes().length;
        } else if (method != null) {
            this.defaultConstructorParameterSize = method.getParameterTypes().length;
        } else {
            this.defaultConstructorParameterSize = 0;
        }
        if (constructor2 != null) {
            this.creatorConstructorParameterTypes = constructor2.getParameterTypes();
            boolean zIsKotlin = TypeUtils.isKotlin(cls);
            this.f5kotlin = zIsKotlin;
            if (zIsKotlin) {
                this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(cls);
                try {
                    this.kotlinDefaultConstructor = cls.getConstructor(null);
                } catch (Throwable unused) {
                }
                Annotation[][] parameterAnnotations = constructor2.getParameterAnnotations();
                for (int i3 = 0; i3 < this.creatorConstructorParameters.length && i3 < parameterAnnotations.length; i3++) {
                    Annotation[] annotationArr = parameterAnnotations[i3];
                    int length = annotationArr.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length) {
                            jSONField = null;
                            break;
                        }
                        Annotation annotation = annotationArr[i4];
                        if (annotation instanceof JSONField) {
                            jSONField = (JSONField) annotation;
                            break;
                        }
                        i4++;
                    }
                    if (jSONField != null) {
                        String strName = jSONField.name();
                        if (strName.length() > 0) {
                            this.creatorConstructorParameters[i3] = strName;
                        }
                    }
                }
                return;
            }
            if (this.creatorConstructorParameterTypes.length == this.fields.length) {
                while (true) {
                    Type[] typeArr = this.creatorConstructorParameterTypes;
                    if (i >= typeArr.length) {
                        return;
                    }
                    if (typeArr[i] != this.fields[i].fieldClass) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            this.creatorConstructorParameters = ASMUtils.lookupParameterNames(constructor2);
        }
    }

    private static FieldInfo getField(List<FieldInfo> list, String str) {
        Field field;
        for (FieldInfo fieldInfo : list) {
            if (fieldInfo.name.equals(str) || ((field = fieldInfo.field) != null && fieldInfo.getAnnotation() != null && field.getName().equals(str))) {
                return fieldInfo;
            }
        }
        return null;
    }

    static boolean add(List<FieldInfo> list, FieldInfo fieldInfo) {
        for (int size = list.size() - 1; size >= 0; size--) {
            FieldInfo fieldInfo2 = list.get(size);
            if (fieldInfo2.name.equals(fieldInfo.name) && (!fieldInfo2.getOnly || fieldInfo.getOnly)) {
                if (fieldInfo2.fieldClass.isAssignableFrom(fieldInfo.fieldClass)) {
                    list.set(size, fieldInfo);
                    return true;
                }
                if (fieldInfo2.compareTo(fieldInfo) >= 0) {
                    return false;
                }
                list.set(size, fieldInfo);
                return true;
            }
        }
        list.add(fieldInfo);
        return true;
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(cls, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, false);
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean z, boolean z2) {
        return build(cls, type, propertyNamingStrategy, z, z2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0690  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x06f3  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0731  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x0736  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x073f  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x078c  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0790  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x07ee  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x07f9  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0840  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0943  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0824 A[EDGE_INSN: B:461:0x0824->B:380:0x0824 BREAK  A[LOOP:4: B:283:0x05a2->B:379:0x0810], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.alibaba.fastjson.util.JavaBeanInfo build(java.lang.Class<?> r31, java.lang.reflect.Type r32, com.alibaba.fastjson.PropertyNamingStrategy r33, boolean r34, boolean r35, boolean r36) {
        /*
            Method dump skipped, instruction units count: 2414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.JavaBeanInfo.build(java.lang.Class, java.lang.reflect.Type, com.alibaba.fastjson.PropertyNamingStrategy, boolean, boolean, boolean):com.alibaba.fastjson.util.JavaBeanInfo");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0013 A[EDGE_INSN: B:44:0x0013->B:6:0x0013 BREAK  A[LOOP:1: B:20:0x004b->B:45:?]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void computeFields(java.lang.Class<?> r17, java.lang.reflect.Type r18, com.alibaba.fastjson.PropertyNamingStrategy r19, java.util.List<com.alibaba.fastjson.util.FieldInfo> r20, java.lang.reflect.Field[] r21) {
        /*
            r0 = r19
            r1 = r21
            int r2 = r1.length
            r3 = 0
            r4 = r3
        L7:
            if (r4 >= r2) goto Lc1
            r8 = r1[r4]
            int r5 = r8.getModifiers()
            r6 = r5 & 8
            if (r6 == 0) goto L17
        L13:
            r5 = r20
            goto Lbd
        L17:
            r5 = r5 & 16
            if (r5 == 0) goto L47
            java.lang.Class r5 = r8.getType()
            java.lang.Class<java.util.Map> r6 = java.util.Map.class
            boolean r6 = r6.isAssignableFrom(r5)
            if (r6 != 0) goto L47
            java.lang.Class<java.util.Collection> r6 = java.util.Collection.class
            boolean r6 = r6.isAssignableFrom(r5)
            if (r6 != 0) goto L47
            java.lang.Class<java.util.concurrent.atomic.AtomicLong> r6 = java.util.concurrent.atomic.AtomicLong.class
            boolean r6 = r6.equals(r5)
            if (r6 != 0) goto L47
            java.lang.Class<java.util.concurrent.atomic.AtomicInteger> r6 = java.util.concurrent.atomic.AtomicInteger.class
            boolean r6 = r6.equals(r5)
            if (r6 != 0) goto L47
            java.lang.Class<java.util.concurrent.atomic.AtomicBoolean> r6 = java.util.concurrent.atomic.AtomicBoolean.class
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L13
        L47:
            java.util.Iterator r5 = r20.iterator()
        L4b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L64
            java.lang.Object r6 = r5.next()
            com.alibaba.fastjson.util.FieldInfo r6 = (com.alibaba.fastjson.util.FieldInfo) r6
            java.lang.String r6 = r6.name
            java.lang.String r7 = r8.getName()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L4b
            goto L13
        L64:
            java.lang.String r5 = r8.getName()
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r6 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r6 = r8.getAnnotation(r6)
            r15 = r6
            com.alibaba.fastjson.annotation.JSONField r15 = (com.alibaba.fastjson.annotation.JSONField) r15
            if (r15 == 0) goto La0
            boolean r6 = r15.deserialize()
            if (r6 != 0) goto L7a
            goto L13
        L7a:
            int r6 = r15.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r7 = r15.serialzeFeatures()
            int r7 = com.alibaba.fastjson.serializer.SerializerFeature.of(r7)
            com.alibaba.fastjson.parser.Feature[] r9 = r15.parseFeatures()
            int r9 = com.alibaba.fastjson.parser.Feature.of(r9)
            java.lang.String r10 = r15.name()
            int r10 = r10.length()
            if (r10 == 0) goto L9c
            java.lang.String r5 = r15.name()
        L9c:
            r11 = r6
            r12 = r7
            r13 = r9
            goto La3
        La0:
            r11 = r3
            r12 = r11
            r13 = r12
        La3:
            if (r0 == 0) goto La9
            java.lang.String r5 = r0.translate(r5)
        La9:
            r6 = r5
            com.alibaba.fastjson.util.FieldInfo r5 = new com.alibaba.fastjson.util.FieldInfo
            r14 = 0
            r16 = 0
            r7 = 0
            r9 = r17
            r10 = r18
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            r6 = r5
            r5 = r20
            add(r5, r6)
        Lbd:
            int r4 = r4 + 1
            goto L7
        Lc1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.JavaBeanInfo.computeFields(java.lang.Class, java.lang.reflect.Type, com.alibaba.fastjson.PropertyNamingStrategy, java.util.List, java.lang.reflect.Field[]):void");
    }

    static Constructor<?> getDefaultConstructor(Class<?> cls, Constructor<?>[] constructorArr) {
        Constructor<?> constructor = null;
        if (Modifier.isAbstract(cls.getModifiers())) {
            return null;
        }
        int length = constructorArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor2 = constructorArr[i];
            if (constructor2.getParameterTypes().length == 0) {
                constructor = constructor2;
                break;
            }
            i++;
        }
        if (constructor == null && cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())) {
            for (Constructor<?> constructor3 : constructorArr) {
                Class<?>[] parameterTypes = constructor3.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0].equals(cls.getDeclaringClass())) {
                    return constructor3;
                }
            }
        }
        return constructor;
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructorArr) {
        Constructor constructor = null;
        for (Constructor constructor2 : constructorArr) {
            if (((JSONCreator) constructor2.getAnnotation(JSONCreator.class)) != null) {
                if (constructor != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                constructor = constructor2;
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructorArr) {
            Annotation[][] parameterAnnotations = constructor3.getParameterAnnotations();
            if (parameterAnnotations.length != 0) {
                int length = parameterAnnotations.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        for (Annotation annotation : parameterAnnotations[i]) {
                            if (annotation instanceof JSONField) {
                                break;
                            }
                        }
                    } else {
                        if (constructor != null) {
                            throw new JSONException("multi-JSONCreator");
                        }
                        constructor = constructor3;
                    }
                    i++;
                }
            }
        }
        return constructor;
    }

    private static Method getFactoryMethod(Class<?> cls, Method[] methodArr, boolean z) {
        Method method = null;
        for (Method method2 : methodArr) {
            if (Modifier.isStatic(method2.getModifiers()) && cls.isAssignableFrom(method2.getReturnType()) && ((JSONCreator) method2.getAnnotation(JSONCreator.class)) != null) {
                if (method != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                method = method2;
            }
        }
        if (method == null && z) {
            for (Method method3 : methodArr) {
                if (TypeUtils.isJacksonCreator(method3)) {
                    return method3;
                }
            }
        }
        return method;
    }

    public static Class<?> getBuilderClass(JSONType jSONType) {
        return getBuilderClass(null, jSONType);
    }

    public static Class<?> getBuilderClass(Class<?> cls, JSONType jSONType) {
        Class<?> clsBuilder;
        if (cls != null && cls.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (jSONType == null || (clsBuilder = jSONType.builder()) == Void.class) {
            return null;
        }
        return clsBuilder;
    }
}

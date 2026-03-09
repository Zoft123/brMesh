package org.bson.codecs.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

/* JADX INFO: loaded from: classes4.dex */
final class ConventionAnnotationImpl implements Convention {
    ConventionAnnotationImpl() {
    }

    @Override // org.bson.codecs.pojo.Convention
    public void apply(ClassModelBuilder<?> classModelBuilder) {
        Iterator<Annotation> it = classModelBuilder.getAnnotations().iterator();
        while (it.hasNext()) {
            processClassAnnotation(classModelBuilder, it.next());
        }
        Iterator<PropertyModelBuilder<?>> it2 = classModelBuilder.getPropertyModelBuilders().iterator();
        while (it2.hasNext()) {
            processPropertyAnnotations(classModelBuilder, it2.next());
        }
        processCreatorAnnotation(classModelBuilder);
        cleanPropertyBuilders(classModelBuilder);
    }

    private void processClassAnnotation(ClassModelBuilder<?> classModelBuilder, Annotation annotation) {
        if (annotation instanceof BsonDiscriminator) {
            BsonDiscriminator bsonDiscriminator = (BsonDiscriminator) annotation;
            String strKey = bsonDiscriminator.key();
            if (!strKey.equals("")) {
                classModelBuilder.discriminatorKey(strKey);
            }
            String strValue = bsonDiscriminator.value();
            if (!strValue.equals("")) {
                classModelBuilder.discriminator(strValue);
            }
            classModelBuilder.enableDiscriminator(true);
        }
    }

    private void processPropertyAnnotations(ClassModelBuilder<?> classModelBuilder, PropertyModelBuilder<?> propertyModelBuilder) {
        for (Annotation annotation : propertyModelBuilder.getReadAnnotations()) {
            if (annotation instanceof BsonProperty) {
                BsonProperty bsonProperty = (BsonProperty) annotation;
                if (!"".equals(bsonProperty.value())) {
                    propertyModelBuilder.readName(bsonProperty.value());
                }
                propertyModelBuilder.discriminatorEnabled(bsonProperty.useDiscriminator());
                if (propertyModelBuilder.getName().equals(classModelBuilder.getIdPropertyName())) {
                    classModelBuilder.idPropertyName(null);
                }
            } else if (annotation instanceof BsonId) {
                classModelBuilder.idPropertyName(propertyModelBuilder.getName());
            } else if (annotation instanceof BsonIgnore) {
                propertyModelBuilder.readName(null);
            }
        }
        for (Annotation annotation2 : propertyModelBuilder.getWriteAnnotations()) {
            if (annotation2 instanceof BsonProperty) {
                BsonProperty bsonProperty2 = (BsonProperty) annotation2;
                if (!"".equals(bsonProperty2.value())) {
                    propertyModelBuilder.writeName(bsonProperty2.value());
                }
            } else if (annotation2 instanceof BsonIgnore) {
                propertyModelBuilder.writeName(null);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00cf, code lost:
    
        if (r7 == null) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d1, code lost:
    
        r2 = r7.getProperties();
        r3 = r7.getParameterTypes();
        r4 = r7.getParameterGenericTypes();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e5, code lost:
    
        if (r2.size() != r3.size()) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e7, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ed, code lost:
    
        if (r5 >= r2.size()) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f3, code lost:
    
        if (r7.getIdPropertyIndex() == null) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0101, code lost:
    
        if (r7.getIdPropertyIndex().equals(java.lang.Integer.valueOf(r5)) == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0103, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0105, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0107, code lost:
    
        r10 = r3.get(r5);
        r11 = r4.get(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0113, code lost:
    
        if (r9 == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0115, code lost:
    
        r9 = r19.getProperty(r19.getIdPropertyName());
        r17 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0123, code lost:
    
        r9 = r2.get(r5);
        r12 = r19.getPropertyModelBuilders().iterator();
        r13 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0136, code lost:
    
        if (r12.hasNext() == false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0138, code lost:
    
        r14 = r12.next();
        r17 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x014c, code lost:
    
        if (r9.value().equals(r14.getWriteName()) == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x014e, code lost:
    
        r13 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x015c, code lost:
    
        if (r9.value().equals(r14.getReadName()) == false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x015e, code lost:
    
        r13 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x015f, code lost:
    
        r6 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0162, code lost:
    
        r17 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0164, code lost:
    
        if (r13 != null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0166, code lost:
    
        r6 = r19.getProperty(r9.value());
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x016f, code lost:
    
        r6 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0170, code lost:
    
        if (r6 != null) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0172, code lost:
    
        r9 = addCreatorPropertyToClassModelBuilder(r19, r9.value(), r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x018b, code lost:
    
        if (r9.value().equals(r6.getName()) != false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x018d, code lost:
    
        r6.writeName(r9.value());
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0194, code lost:
    
        tryToExpandToGenericType(r10, r6, r11);
        r9 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01a0, code lost:
    
        if (r9.getTypeData().isAssignableFrom(r10) == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01a2, code lost:
    
        r5 = r5 + 1;
        r6 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01a8, code lost:
    
        r0 = r9.getWriteName();
        r2 = r9.getTypeData().getType();
        r3 = new java.lang.Object[3];
        r3[0] = r0;
        r3[1] = r2;
        r3[r17] = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01c7, code lost:
    
        throw r7.getError(r1, java.lang.String.format("Invalid Property type for '%s'. Expected %s, found %s.", r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01c8, code lost:
    
        r19.instanceCreatorFactory(new org.bson.codecs.pojo.InstanceCreatorFactoryImpl(r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01d2, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01db, code lost:
    
        throw r7.getError(r1, "All parameters in the @BsonCreator method / constructor must be annotated with a @BsonProperty.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01de, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T> void processCreatorAnnotation(org.bson.codecs.pojo.ClassModelBuilder<T> r19) {
        /*
            Method dump skipped, instruction units count: 479
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bson.codecs.pojo.ConventionAnnotationImpl.processCreatorAnnotation(org.bson.codecs.pojo.ClassModelBuilder):void");
    }

    private static <T> void tryToExpandToGenericType(Class<?> cls, PropertyModelBuilder<T> propertyModelBuilder, Type type) {
        if (cls.isAssignableFrom(propertyModelBuilder.getTypeData().getType())) {
            propertyModelBuilder.typeData(TypeData.newInstance(type, cls));
        }
    }

    private <T, S> PropertyModelBuilder<S> addCreatorPropertyToClassModelBuilder(ClassModelBuilder<T> classModelBuilder, String str, Class<S> cls) {
        PropertyModelBuilder<T> propertyModelBuilderWriteName = PojoBuilderHelper.createPropertyModelBuilder(new PropertyMetadata(str, classModelBuilder.getType().getSimpleName(), TypeData.builder(cls).build())).readName(null).writeName(str);
        classModelBuilder.addProperty(propertyModelBuilderWriteName);
        return propertyModelBuilderWriteName;
    }

    private void cleanPropertyBuilders(ClassModelBuilder<?> classModelBuilder) {
        ArrayList arrayList = new ArrayList();
        for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
            if (!propertyModelBuilder.isReadable() && !propertyModelBuilder.isWritable()) {
                arrayList.add(propertyModelBuilder.getName());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            classModelBuilder.removeProperty((String) it.next());
        }
    }
}

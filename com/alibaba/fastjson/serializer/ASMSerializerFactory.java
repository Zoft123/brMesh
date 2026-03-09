package com.alibaba.fastjson.serializer;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.CollectionUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
public class ASMSerializerFactory implements Opcodes {
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String JavaBeanSerializer;
    static final String JavaBeanSerializer_desc;
    static final String ObjectSerializer;
    static final String ObjectSerializer_desc;
    static final String SerialContext_desc;
    static final String SerializeFilterable_desc;
    static final String SerializeWriter;
    static final String SerializeWriter_desc;
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();

    static {
        String strType = ASMUtils.type(ObjectSerializer.class);
        ObjectSerializer = strType;
        ObjectSerializer_desc = "L" + strType + ";";
        String strType2 = ASMUtils.type(SerializeWriter.class);
        SerializeWriter = strType2;
        SerializeWriter_desc = "L" + strType2 + ";";
        JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
        JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + ";";
        SerialContext_desc = ASMUtils.desc((Class<?>) SerialContext.class);
        SerializeFilterable_desc = ASMUtils.desc((Class<?>) SerializeFilterable.class);
    }

    static class Context {
        static final int features = 5;
        static int fieldName = 6;
        static final int obj = 2;
        static int original = 7;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static int processValue = 8;
        static final int serializer = 1;
        private final SerializeBeanInfo beanInfo;
        private final String className;
        private final FieldInfo[] getters;
        private final boolean nonContext;
        private final boolean writeDirect;
        private Map<String, Integer> variants = new HashMap();
        private int variantIndex = 9;

        public Context(FieldInfo[] fieldInfoArr, SerializeBeanInfo serializeBeanInfo, String str, boolean z, boolean z2) {
            this.getters = fieldInfoArr;
            this.className = str;
            this.beanInfo = serializeBeanInfo;
            this.writeDirect = z;
            this.nonContext = z2 || serializeBeanInfo.beanType.isEnum();
        }

        public int var(String str) {
            if (this.variants.get(str) == null) {
                Map<String, Integer> map = this.variants;
                int i = this.variantIndex;
                this.variantIndex = i + 1;
                map.put(str, Integer.valueOf(i));
            }
            return this.variants.get(str).intValue();
        }

        public int var(String str, int i) {
            if (this.variants.get(str) == null) {
                this.variants.put(str, Integer.valueOf(this.variantIndex));
                this.variantIndex += i;
            }
            return this.variants.get(str).intValue();
        }

        public int getFieldOrinal(String str) {
            int length = this.getters.length;
            for (int i = 0; i < length; i++) {
                if (this.getters[i].name.equals(str)) {
                    return i;
                }
            }
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:88:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x043f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.alibaba.fastjson.serializer.JavaBeanSerializer createJavaBeanSerializer(com.alibaba.fastjson.serializer.SerializeBeanInfo r33) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 1481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ASMSerializerFactory.createJavaBeanSerializer(com.alibaba.fastjson.serializer.SerializeBeanInfo):com.alibaba.fastjson.serializer.JavaBeanSerializer");
    }

    private void generateWriteAsArray(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        int i;
        String str;
        int i2;
        int i3;
        String str2;
        String str3;
        char c;
        boolean z;
        int i4;
        String str4;
        ASMSerializerFactory aSMSerializerFactory;
        char c2;
        Label label;
        String str5;
        Type type;
        Class<?> cls2;
        int i5;
        Label label2;
        Label label3;
        String str6;
        Class<?> cls3;
        Label label4;
        String str7;
        int i6;
        int i7;
        String str8;
        int i8;
        ASMSerializerFactory aSMSerializerFactory2 = this;
        FieldInfo[] fieldInfoArr2 = fieldInfoArr;
        Label label5 = new Label();
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        String str9 = JSONSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "hasPropertyFilters", "(" + SerializeFilterable_desc + ")Z");
        methodVisitor.visitJumpInsn(Opcodes.IFNE, label5);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, 3);
        methodVisitor.visitVarInsn(25, 4);
        methodVisitor.visitVarInsn(21, 5);
        String str10 = JavaBeanSerializer;
        String str11 = "(L";
        StringBuilder sb = new StringBuilder("(L");
        sb.append(str9);
        String str12 = ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
        sb.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, str10, "writeNoneASM", sb.toString());
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitLabel(label5);
        String str13 = "out";
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(16, 91);
        String str14 = SerializeWriter;
        String str15 = "(I)V";
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "write", "(I)V");
        int i9 = 0;
        int length = fieldInfoArr2.length;
        if (length == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "write", "(I)V");
            return;
        }
        int i10 = 0;
        while (i10 < length) {
            int i11 = i10 == length + (-1) ? 93 : 44;
            FieldInfo fieldInfo = fieldInfoArr2[i10];
            Class<?> cls4 = fieldInfo.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls4 == Byte.TYPE || cls4 == Short.TYPE || cls4 == Integer.TYPE) {
                i = i9;
                str = str13;
                int i12 = i11;
                i2 = length;
                i3 = i10;
                str2 = str11;
                str3 = str15;
                c = 25;
                z = true;
                i4 = Opcodes.INVOKEVIRTUAL;
                str4 = str12;
                methodVisitor.visitVarInsn(25, context.var(str));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory = this;
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                String str16 = SerializeWriter;
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str16, "writeInt", str3);
                c2 = 16;
                methodVisitor.visitVarInsn(16, i12);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str16, "write", str3);
            } else {
                if (cls4 == Long.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    String str17 = SerializeWriter;
                    i4 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str17, "writeLong", "(J)V");
                    methodVisitor.visitVarInsn(16, i11);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str17, "write", str15);
                } else if (cls4 == Float.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(4);
                    String str18 = SerializeWriter;
                    i4 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str18, "writeFloat", "(FZ)V");
                    methodVisitor.visitVarInsn(16, i11);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str18, "write", str15);
                } else if (cls4 == Double.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(4);
                    String str19 = SerializeWriter;
                    i4 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "writeDouble", "(DZ)V");
                    methodVisitor.visitVarInsn(16, i11);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str19, "write", str15);
                } else if (cls4 == Boolean.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    String str20 = SerializeWriter;
                    i4 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str20, "write", "(Z)V");
                    methodVisitor.visitVarInsn(16, i11);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str20, "write", str15);
                } else if (cls4 == Character.TYPE) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                    methodVisitor.visitVarInsn(16, i11);
                    String str21 = SerializeWriter;
                    i4 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str21, "writeString", "(Ljava/lang/String;C)V");
                    str = str13;
                    i2 = length;
                    i3 = i10;
                    c2 = 16;
                    str2 = str11;
                    str3 = str15;
                    c = 25;
                    z = true;
                    str4 = str12;
                    aSMSerializerFactory = aSMSerializerFactory2;
                    i = i9;
                } else if (cls4 == String.class) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitVarInsn(16, i11);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                    str = str13;
                    i2 = length;
                    i3 = i10;
                    c = 25;
                    str2 = str11;
                    str3 = str15;
                    z = true;
                    str4 = str12;
                    i4 = 182;
                    c2 = 16;
                    aSMSerializerFactory = aSMSerializerFactory2;
                    i = i9;
                } else if (cls4.isEnum()) {
                    methodVisitor.visitVarInsn(25, context.var(str13));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    String str22 = SerializeWriter;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str22, "writeEnum", "(Ljava/lang/Enum;)V");
                    methodVisitor.visitVarInsn(16, i11);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str22, "write", str15);
                    str = str13;
                    i2 = length;
                    i3 = i10;
                    i4 = 182;
                    str2 = str11;
                    str3 = str15;
                    c = 25;
                    z = true;
                    c2 = 16;
                    str4 = str12;
                    aSMSerializerFactory = aSMSerializerFactory2;
                    i = i9;
                } else if (List.class.isAssignableFrom(cls4)) {
                    Type type2 = fieldInfo.fieldType;
                    if (type2 instanceof Class) {
                        type = Object.class;
                    } else {
                        type = ((ParameterizedType) type2).getActualTypeArguments()[i9];
                    }
                    if (!(type instanceof Class) || (cls2 = (Class) type) == Object.class) {
                        cls2 = null;
                    }
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    Type type3 = type;
                    methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
                    i2 = length;
                    methodVisitor.visitVarInsn(58, context.var(CollectionUtils.LIST_TYPE));
                    if (cls2 == String.class && context.writeDirect) {
                        methodVisitor.visitVarInsn(25, context.var(str13));
                        methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/util/List;)V");
                        str7 = str13;
                        i5 = i11;
                        i3 = i10;
                        str2 = str11;
                        str8 = str15;
                        i7 = 16;
                        str4 = str12;
                        i8 = 182;
                        i6 = 25;
                    } else {
                        Label label6 = new Label();
                        Label label7 = new Label();
                        i3 = i10;
                        i5 = i11;
                        methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
                        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label7);
                        methodVisitor.visitVarInsn(25, context.var(str13));
                        String str23 = SerializeWriter;
                        String str24 = str12;
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str23, "writeNull", "()V");
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label6);
                        methodVisitor.visitLabel(label7);
                        methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
                        methodVisitor.visitVarInsn(54, context.var("size"));
                        methodVisitor.visitVarInsn(25, context.var(str13));
                        methodVisitor.visitVarInsn(16, 91);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str23, "write", str15);
                        Label label8 = new Label();
                        Label label9 = new Label();
                        Label label10 = new Label();
                        methodVisitor.visitInsn(3);
                        String str25 = str11;
                        methodVisitor.visitVarInsn(54, context.var("i"));
                        methodVisitor.visitLabel(label8);
                        methodVisitor.visitVarInsn(21, context.var("i"));
                        methodVisitor.visitVarInsn(21, context.var("size"));
                        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label10);
                        methodVisitor.visitVarInsn(21, context.var("i"));
                        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label9);
                        methodVisitor.visitVarInsn(25, context.var(str13));
                        methodVisitor.visitVarInsn(16, 44);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str23, "write", str15);
                        methodVisitor.visitLabel(label9);
                        methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
                        methodVisitor.visitVarInsn(21, context.var("i"));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
                        methodVisitor.visitVarInsn(58, context.var("list_item"));
                        Label label11 = new Label();
                        Label label12 = new Label();
                        String str26 = str15;
                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label12);
                        methodVisitor.visitVarInsn(25, context.var(str13));
                        String str27 = str13;
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str23, "writeNull", "()V");
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label11);
                        methodVisitor.visitLabel(label12);
                        Label label13 = new Label();
                        Label label14 = new Label();
                        if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                            label2 = label8;
                            label3 = label10;
                            str6 = str23;
                            cls3 = cls2;
                            str4 = str24;
                            str2 = str25;
                            label4 = label11;
                        } else {
                            methodVisitor.visitVarInsn(25, context.var("list_item"));
                            str6 = str23;
                            label3 = label10;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls2)));
                            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label14);
                            aSMSerializerFactory2._getListFieldItemSer(context, methodVisitor, fieldInfo, cls2);
                            methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                            Label label15 = new Label();
                            Label label16 = new Label();
                            if (context.writeDirect) {
                                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                String str28 = JavaBeanSerializer;
                                methodVisitor.visitTypeInsn(193, str28);
                                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label15);
                                cls3 = cls2;
                                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, str28);
                                methodVisitor.visitVarInsn(25, 1);
                                methodVisitor.visitVarInsn(25, context.var("list_item"));
                                if (context.nonContext) {
                                    methodVisitor.visitInsn(1);
                                    label2 = label8;
                                } else {
                                    methodVisitor.visitVarInsn(21, context.var("i"));
                                    label2 = label8;
                                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                }
                                methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls3)));
                                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                str2 = str25;
                                StringBuilder sb2 = new StringBuilder(str2);
                                sb2.append(JSONSerializer);
                                str4 = str24;
                                sb2.append(str4);
                                label4 = label11;
                                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str28, "writeAsArrayNonContext", sb2.toString());
                                methodVisitor.visitJumpInsn(Opcodes.GOTO, label16);
                                methodVisitor.visitLabel(label15);
                            } else {
                                label2 = label8;
                                cls3 = cls2;
                                str4 = str24;
                                str2 = str25;
                                label4 = label11;
                            }
                            methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("list_item"));
                            if (context.nonContext) {
                                methodVisitor.visitInsn(1);
                            } else {
                                methodVisitor.visitVarInsn(21, context.var("i"));
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                            }
                            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls3)));
                            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", str2 + JSONSerializer + str4);
                            methodVisitor.visitLabel(label16);
                            methodVisitor.visitJumpInsn(Opcodes.GOTO, label13);
                        }
                        methodVisitor.visitLabel(label14);
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                        if (context.nonContext) {
                            methodVisitor.visitInsn(1);
                        } else {
                            methodVisitor.visitVarInsn(21, context.var("i"));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        }
                        if (cls3 != null && Modifier.isPublic(cls3.getModifiers())) {
                            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc((Class<?>) type3)));
                            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                        } else {
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                        }
                        methodVisitor.visitLabel(label13);
                        methodVisitor.visitLabel(label4);
                        methodVisitor.visitIincInsn(context.var("i"), 1);
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
                        methodVisitor.visitLabel(label3);
                        str7 = str27;
                        i6 = 25;
                        methodVisitor.visitVarInsn(25, context.var(str7));
                        i7 = 16;
                        methodVisitor.visitVarInsn(16, 93);
                        str8 = str26;
                        i8 = Opcodes.INVOKEVIRTUAL;
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "write", str8);
                        methodVisitor.visitLabel(label6);
                    }
                    methodVisitor.visitVarInsn(i6, context.var(str7));
                    methodVisitor.visitVarInsn(i7, i5);
                    methodVisitor.visitMethodInsn(i8, SerializeWriter, "write", str8);
                    str = str7;
                    str3 = str8;
                    i4 = i8;
                    c = 25;
                    z = true;
                    c2 = 16;
                    aSMSerializerFactory = this;
                    i = i9;
                } else {
                    String str29 = str13;
                    int i13 = i11;
                    i2 = length;
                    i3 = i10;
                    str2 = str11;
                    String str30 = str15;
                    str4 = str12;
                    Label label17 = new Label();
                    Label label18 = new Label();
                    _get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(89);
                    methodVisitor.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                    methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label18);
                    methodVisitor.visitVarInsn(25, context.var(str29));
                    String str31 = SerializeWriter;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str31, "writeNull", "()V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label17);
                    methodVisitor.visitLabel(label18);
                    Label label19 = new Label();
                    Label label20 = new Label();
                    methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                    methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls4)));
                    methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label20);
                    _getFieldSer(context, methodVisitor, fieldInfo);
                    methodVisitor.visitVarInsn(58, context.var("fied_ser"));
                    Label label21 = new Label();
                    Label label22 = new Label();
                    if (context.writeDirect && Modifier.isPublic(cls4.getModifiers())) {
                        methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                        String str32 = JavaBeanSerializer;
                        methodVisitor.visitTypeInsn(193, str32);
                        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label21);
                        label = label17;
                        methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, str32);
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        methodVisitor.visitVarInsn(25, Context.fieldName);
                        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls4)));
                        methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        str5 = "writeWithFieldName";
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str32, "writeAsArrayNonContext", str2 + JSONSerializer + str4);
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label22);
                        methodVisitor.visitLabel(label21);
                    } else {
                        label = label17;
                        str5 = "writeWithFieldName";
                    }
                    methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                    methodVisitor.visitVarInsn(25, Context.fieldName);
                    methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls4)));
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    String str33 = ObjectSerializer;
                    StringBuilder sb3 = new StringBuilder(str2);
                    String str34 = JSONSerializer;
                    sb3.append(str34);
                    sb3.append(str4);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, str33, "write", sb3.toString());
                    methodVisitor.visitLabel(label22);
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label19);
                    methodVisitor.visitLabel(label20);
                    String format = fieldInfo.getFormat();
                    z = true;
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                    if (format != null) {
                        methodVisitor.visitLdcInsn(format);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str34, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                        i = i9;
                        i4 = 182;
                    } else {
                        methodVisitor.visitVarInsn(25, Context.fieldName);
                        if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                            i4 = Opcodes.INVOKEVIRTUAL;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str34, str5, "(Ljava/lang/Object;Ljava/lang/Object;)V");
                            i = i9;
                        } else {
                            i = i9;
                            methodVisitor.visitVarInsn(25, i);
                            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            i4 = Opcodes.INVOKEVIRTUAL;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str34, str5, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                        }
                    }
                    methodVisitor.visitLabel(label19);
                    methodVisitor.visitLabel(label);
                    str = str29;
                    c = 25;
                    methodVisitor.visitVarInsn(25, context.var(str));
                    methodVisitor.visitVarInsn(16, i13);
                    str3 = str30;
                    methodVisitor.visitMethodInsn(i4, str31, "write", str3);
                    aSMSerializerFactory = this;
                    c2 = 16;
                }
                str = str13;
                i2 = length;
                i3 = i10;
                str2 = str11;
                str3 = str15;
                c = 25;
                z = true;
                c2 = 16;
                str4 = str12;
                aSMSerializerFactory = aSMSerializerFactory2;
                i = i9;
            }
            i9 = i;
            str11 = str2;
            aSMSerializerFactory2 = aSMSerializerFactory;
            str12 = str4;
            i10 = i3 + 1;
            str15 = str3;
            length = i2;
            str13 = str;
            fieldInfoArr2 = fieldInfoArr;
        }
    }

    private void generateWriteMethod(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        Label label;
        String str;
        String str2;
        int i;
        int i2;
        ASMSerializerFactory aSMSerializerFactory = this;
        Class<?> cls2 = cls;
        MethodVisitor methodVisitor2 = methodVisitor;
        Label label2 = new Label();
        int length = fieldInfoArr.length;
        if (context.writeDirect) {
            label = label2;
        } else {
            Label label3 = new Label();
            Label label4 = new Label();
            label = label2;
            methodVisitor2.visitVarInsn(25, context.var("out"));
            methodVisitor2.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor2.visitJumpInsn(Opcodes.IFNE, label4);
            boolean z = false;
            for (FieldInfo fieldInfo : fieldInfoArr) {
                if (fieldInfo.method != null) {
                    z = true;
                }
            }
            if (z) {
                methodVisitor2.visitVarInsn(25, context.var("out"));
                methodVisitor2.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                methodVisitor2.visitJumpInsn(Opcodes.IFEQ, label3);
            } else {
                methodVisitor2.visitJumpInsn(Opcodes.GOTO, label3);
            }
            methodVisitor2.visitLabel(label4);
            methodVisitor2.visitVarInsn(25, 0);
            methodVisitor2.visitVarInsn(25, 1);
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitVarInsn(25, 3);
            methodVisitor2.visitVarInsn(25, 4);
            methodVisitor2.visitVarInsn(21, 5);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor2.visitInsn(Opcodes.RETURN);
            methodVisitor2.visitLabel(label3);
        }
        if (!context.nonContext) {
            Label label5 = new Label();
            methodVisitor2.visitVarInsn(25, 0);
            methodVisitor2.visitVarInsn(25, 1);
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitVarInsn(21, 5);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            methodVisitor2.visitJumpInsn(Opcodes.IFEQ, label5);
            methodVisitor2.visitInsn(Opcodes.RETURN);
            methodVisitor2.visitLabel(label5);
        }
        if (context.writeDirect) {
            if (context.nonContext) {
                str = "writeAsArrayNonContext";
            } else {
                str = "writeAsArray";
            }
        } else {
            str = "writeAsArrayNormal";
        }
        if ((context.beanInfo.features & SerializerFeature.BeanToArray.mask) == 0) {
            Label label6 = new Label();
            methodVisitor2.visitVarInsn(25, context.var("out"));
            methodVisitor2.visitLdcInsn(Integer.valueOf(SerializerFeature.BeanToArray.mask));
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor2.visitJumpInsn(Opcodes.IFEQ, label6);
            methodVisitor2.visitVarInsn(25, 0);
            methodVisitor2.visitVarInsn(25, 1);
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitVarInsn(25, 3);
            methodVisitor2.visitVarInsn(25, 4);
            methodVisitor2.visitVarInsn(21, 5);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor2.visitInsn(Opcodes.RETURN);
            methodVisitor2.visitLabel(label6);
        } else {
            methodVisitor2.visitVarInsn(25, 0);
            methodVisitor2.visitVarInsn(25, 1);
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitVarInsn(25, 3);
            methodVisitor2.visitVarInsn(25, 4);
            methodVisitor2.visitVarInsn(21, 5);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor2.visitInsn(Opcodes.RETURN);
        }
        if (!context.nonContext) {
            methodVisitor2.visitVarInsn(25, 1);
            String str3 = JSONSerializer;
            StringBuilder sb = new StringBuilder("()");
            String str4 = SerialContext_desc;
            sb.append(str4);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "getContext", sb.toString());
            methodVisitor2.visitVarInsn(58, context.var("parent"));
            methodVisitor2.visitVarInsn(25, 1);
            methodVisitor2.visitVarInsn(25, context.var("parent"));
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitVarInsn(25, 3);
            methodVisitor2.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "setContext", "(" + str4 + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean z2 = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        if (z2 || !context.writeDirect) {
            Label label7 = new Label();
            Label label8 = new Label();
            Label label9 = new Label();
            if (z2) {
                str2 = "parent";
                i = Opcodes.INVOKEVIRTUAL;
            } else {
                methodVisitor2.visitVarInsn(25, 1);
                methodVisitor2.visitVarInsn(25, 4);
                methodVisitor2.visitVarInsn(25, 2);
                String str5 = JSONSerializer;
                str2 = "parent";
                i = Opcodes.INVOKEVIRTUAL;
                methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                methodVisitor2.visitJumpInsn(Opcodes.IFEQ, label8);
            }
            methodVisitor2.visitVarInsn(25, 4);
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitMethodInsn(i, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor2.visitJumpInsn(Opcodes.IF_ACMPEQ, label8);
            methodVisitor2.visitLabel(label9);
            methodVisitor2.visitVarInsn(25, context.var("out"));
            methodVisitor2.visitVarInsn(16, 123);
            methodVisitor2.visitMethodInsn(i, SerializeWriter, "write", "(I)V");
            methodVisitor2.visitVarInsn(25, 0);
            methodVisitor2.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                methodVisitor2.visitLdcInsn(context.beanInfo.typeKey);
            } else {
                methodVisitor2.visitInsn(1);
            }
            methodVisitor2.visitVarInsn(25, 2);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            methodVisitor2.visitVarInsn(16, 44);
            methodVisitor2.visitJumpInsn(Opcodes.GOTO, label7);
            methodVisitor2.visitLabel(label8);
            methodVisitor2.visitVarInsn(16, 123);
            methodVisitor2.visitLabel(label7);
        } else {
            methodVisitor2.visitVarInsn(16, 123);
            str2 = "parent";
        }
        methodVisitor2.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            aSMSerializerFactory._before(methodVisitor2, context);
        }
        if (context.writeDirect) {
            i2 = 0;
        } else {
            methodVisitor2.visitVarInsn(25, context.var("out"));
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            methodVisitor2.visitVarInsn(54, context.var("notWriteDefaultValue"));
            methodVisitor2.visitVarInsn(25, 1);
            methodVisitor2.visitVarInsn(25, 0);
            String str6 = JSONSerializer;
            StringBuilder sb2 = new StringBuilder("(");
            String str7 = SerializeFilterable_desc;
            sb2.append(str7);
            sb2.append(")Z");
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "checkValue", sb2.toString());
            methodVisitor2.visitVarInsn(54, context.var("checkValue"));
            methodVisitor2.visitVarInsn(25, 1);
            i2 = 0;
            methodVisitor2.visitVarInsn(25, 0);
            methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "hasNameFilters", "(" + str7 + ")Z");
            methodVisitor2.visitVarInsn(54, context.var("hasNameFilters"));
        }
        int i3 = i2;
        while (i3 < length) {
            FieldInfo fieldInfo2 = fieldInfoArr[i3];
            Class<?> cls3 = fieldInfo2.fieldClass;
            methodVisitor2.visitLdcInsn(fieldInfo2.name);
            methodVisitor2.visitVarInsn(58, Context.fieldName);
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                aSMSerializerFactory._int(cls2, methodVisitor2, fieldInfo2, context, context.var(cls3.getName()), 'I');
            } else if (cls3 == Long.TYPE) {
                aSMSerializerFactory._long(cls2, methodVisitor2, fieldInfo2, context);
            } else if (cls3 == Float.TYPE) {
                aSMSerializerFactory._float(cls2, methodVisitor2, fieldInfo2, context);
            } else if (cls3 == Double.TYPE) {
                aSMSerializerFactory._double(cls2, methodVisitor2, fieldInfo2, context);
            } else if (cls3 == Boolean.TYPE) {
                aSMSerializerFactory._int(cls2, methodVisitor2, fieldInfo2, context, context.var(TypedValues.Custom.S_BOOLEAN), 'Z');
                aSMSerializerFactory = this;
                methodVisitor2 = methodVisitor;
            } else if (cls3 == Character.TYPE) {
                aSMSerializerFactory = this;
                methodVisitor2 = methodVisitor;
                aSMSerializerFactory._int(cls, methodVisitor2, fieldInfo2, context, context.var("char"), 'C');
            } else {
                aSMSerializerFactory = this;
                methodVisitor2 = methodVisitor;
                if (cls3 == String.class) {
                    aSMSerializerFactory._string(cls, methodVisitor2, fieldInfo2, context);
                } else if (cls3 == BigDecimal.class) {
                    aSMSerializerFactory._decimal(cls, methodVisitor2, fieldInfo2, context);
                } else if (List.class.isAssignableFrom(cls3)) {
                    aSMSerializerFactory._list(cls, methodVisitor2, fieldInfo2, context);
                } else if (cls3.isEnum()) {
                    aSMSerializerFactory._enum(cls, methodVisitor2, fieldInfo2, context);
                } else {
                    aSMSerializerFactory._object(cls, methodVisitor2, fieldInfo2, context);
                }
            }
            i3++;
            cls2 = cls;
        }
        if (!context.writeDirect) {
            aSMSerializerFactory._after(methodVisitor2, context);
        }
        Label label10 = new Label();
        Label label11 = new Label();
        methodVisitor2.visitVarInsn(21, context.var("seperator"));
        methodVisitor2.visitIntInsn(16, 123);
        methodVisitor2.visitJumpInsn(Opcodes.IF_ICMPNE, label10);
        methodVisitor2.visitVarInsn(25, context.var("out"));
        methodVisitor2.visitVarInsn(16, 123);
        String str8 = SerializeWriter;
        methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "write", "(I)V");
        methodVisitor2.visitLabel(label10);
        methodVisitor2.visitVarInsn(25, context.var("out"));
        methodVisitor2.visitVarInsn(16, 125);
        methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "write", "(I)V");
        methodVisitor2.visitLabel(label11);
        methodVisitor2.visitLabel(label);
        if (context.nonContext) {
            return;
        }
        methodVisitor2.visitVarInsn(25, 1);
        methodVisitor2.visitVarInsn(25, context.var(str2));
        methodVisitor2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(" + SerialContext_desc + ")V");
    }

    private void _object(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("object"));
        _filters(methodVisitor, fieldInfo, context, label);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitLabel(label);
    }

    private void _enum(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label3);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Enum");
        methodVisitor.visitVarInsn(58, context.var("enum"));
        _filters(methodVisitor, fieldInfo, context, label3);
        methodVisitor.visitVarInsn(25, context.var("enum"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Enum", GlobalVariable.NAME, "()Ljava/lang/String;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            String str = SerializeWriter;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "write", "(I)V");
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeFieldName", "(Ljava/lang/String;Z)V");
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label3);
    }

    private void _int(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, int i, char c) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(54, i);
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(21, i);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;" + c + ")V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _long(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(55, context.var("long", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(22, context.var("long", 2));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _float(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(56, context.var(TypedValues.Custom.S_FLOAT));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _double(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(57, context.var("double", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(24, context.var("double", 2));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _get(MethodVisitor methodVisitor, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            methodVisitor.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            methodVisitor.visitMethodInsn(declaringClass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (method.getReturnType().equals(fieldInfo.fieldClass)) {
                return;
            }
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldInfo.fieldClass));
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("entity"));
        Field field = fieldInfo.field;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
        if (field.getType().equals(fieldInfo.fieldClass)) {
            return;
        }
        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldInfo.fieldClass));
    }

    private void _decimal(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("decimal"));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label3);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitLabel(label);
    }

    private void _string(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        if (fieldInfo.name.equals(context.beanInfo.typeKey)) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var(TypedValues.Custom.S_STRING));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label2);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        methodVisitor.visitLabel(label2);
        if ("trim".equals(fieldInfo.format)) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "trim", "()Ljava/lang/String;");
            methodVisitor.visitVarInsn(58, context.var(TypedValues.Custom.S_STRING));
        }
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitLabel(label);
    }

    private void _list(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label;
        Label label2;
        Label label3;
        Class<?> cls2;
        String str;
        Label label4;
        Label label5;
        FieldInfo fieldInfo2;
        Label label6;
        int i;
        int i2;
        int i3;
        Label label7;
        Label label8;
        Type collectionItemType = TypeUtils.getCollectionItemType(fieldInfo.fieldType);
        Class<?> cls3 = null;
        Class<?> cls4 = collectionItemType instanceof Class ? (Class) collectionItemType : null;
        if (cls4 != Object.class && cls4 != Serializable.class) {
            cls3 = cls4;
        }
        Label label9 = new Label();
        Label label10 = new Label();
        Label label11 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label9);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
        methodVisitor.visitVarInsn(58, context.var(CollectionUtils.LIST_TYPE));
        _filters(methodVisitor, fieldInfo, context, label9);
        methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label10);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label11);
        methodVisitor.visitLabel(label10);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        String str2 = SerializeWriter;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
        methodVisitor.visitVarInsn(54, context.var("size"));
        Label label12 = new Label();
        Label label13 = new Label();
        methodVisitor.visitVarInsn(21, context.var("size"));
        methodVisitor.visitInsn(3);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label12);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(Ljava/lang/String;)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label13);
        methodVisitor.visitLabel(label12);
        if (context.nonContext) {
            label = label13;
        } else {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            label = label13;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (collectionItemType == String.class && context.writeDirect) {
            i2 = 25;
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
            i3 = Opcodes.INVOKEVIRTUAL;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(Ljava/util/List;)V");
            i = 1;
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 91);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(I)V");
            Label label14 = new Label();
            Label label15 = new Label();
            Label label16 = new Label();
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, context.var("i"));
            methodVisitor.visitLabel(label14);
            methodVisitor.visitVarInsn(21, context.var("i"));
            methodVisitor.visitVarInsn(21, context.var("size"));
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label16);
            methodVisitor.visitVarInsn(21, context.var("i"));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label15);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(I)V");
            methodVisitor.visitLabel(label15);
            methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
            methodVisitor.visitVarInsn(21, context.var("i"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
            methodVisitor.visitVarInsn(58, context.var("list_item"));
            Label label17 = new Label();
            Label label18 = new Label();
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label18);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "writeNull", "()V");
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label17);
            methodVisitor.visitLabel(label18);
            Label label19 = new Label();
            Label label20 = new Label();
            if (cls3 == null || !Modifier.isPublic(cls3.getModifiers())) {
                label2 = label14;
                label3 = label17;
                cls2 = cls3;
                str = "out";
                label4 = label19;
                label5 = label16;
                fieldInfo2 = fieldInfo;
                label6 = label20;
            } else {
                str = "out";
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                label5 = label16;
                label2 = label14;
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls3)));
                methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label20);
                fieldInfo2 = fieldInfo;
                _getListFieldItemSer(context, methodVisitor, fieldInfo2, cls3);
                cls2 = cls3;
                methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                Label label21 = new Label();
                Label label22 = new Label();
                if (context.writeDirect) {
                    String str3 = (context.nonContext && context.writeDirect) ? "writeDirectNonContext" : "write";
                    label3 = label17;
                    label8 = label20;
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    String str4 = JavaBeanSerializer;
                    methodVisitor.visitTypeInsn(193, str4);
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label21);
                    label7 = label19;
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, str4);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                    } else {
                        methodVisitor.visitVarInsn(21, context.var("i"));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls2)));
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, str3, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label22);
                    methodVisitor.visitLabel(label21);
                } else {
                    label3 = label17;
                    label7 = label19;
                    label8 = label20;
                }
                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    methodVisitor.visitInsn(1);
                } else {
                    methodVisitor.visitVarInsn(21, context.var("i"));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label22);
                label4 = label7;
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
                label6 = label8;
            }
            methodVisitor.visitLabel(label6);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                methodVisitor.visitInsn(1);
            } else {
                methodVisitor.visitVarInsn(21, context.var("i"));
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (cls2 != null && Modifier.isPublic(cls2.getModifiers())) {
                methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc((Class<?>) collectionItemType)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo2.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            } else {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            }
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLabel(label3);
            i = 1;
            methodVisitor.visitIincInsn(context.var("i"), 1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            methodVisitor.visitLabel(label5);
            i2 = 25;
            methodVisitor.visitVarInsn(25, context.var(str));
            methodVisitor.visitVarInsn(16, 93);
            i3 = Opcodes.INVOKEVIRTUAL;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(I)V");
        }
        methodVisitor.visitVarInsn(i2, i);
        methodVisitor.visitMethodInsn(i3, JSONSerializer, "popContext", "()V");
        methodVisitor.visitLabel(label);
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label11);
        methodVisitor.visitLabel(label9);
    }

    private void _filters(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (fieldInfo.fieldTransient) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
        _notWriteDefault(methodVisitor, fieldInfo, context, label);
        if (context.writeDirect) {
            return;
        }
        _apply(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        _processKey(methodVisitor, fieldInfo, context);
        _processValue(methodVisitor, fieldInfo, context, label);
    }

    private void _nameApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            _labelApply(methodVisitor, fieldInfo, context, label);
        }
        if (fieldInfo.field == null) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
    }

    private void _labelApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(fieldInfo.label);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _writeObject(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        String str;
        Label label2;
        Label label3;
        String str2;
        String format = fieldInfo.getFormat();
        Class<?> cls = fieldInfo.fieldClass;
        Label label4 = new Label();
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, context.var("object"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label4);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        Label label5 = new Label();
        Label label6 = new Label();
        if (!Modifier.isPublic(cls.getModifiers()) || ParserConfig.isPrimitive2(cls)) {
            str = format;
            label2 = label5;
            label3 = label6;
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls)));
            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label6);
            _getFieldSer(context, methodVisitor, fieldInfo);
            methodVisitor.visitVarInsn(58, context.var("fied_ser"));
            Label label7 = new Label();
            Label label8 = new Label();
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            String str3 = JavaBeanSerializer;
            methodVisitor.visitTypeInsn(193, str3);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label7);
            boolean z = (fieldInfo.serialzeFeatures & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
            boolean z2 = (fieldInfo.serialzeFeatures & SerializerFeature.BeanToArray.mask) != 0;
            if (z || (context.nonContext && context.writeDirect)) {
                str2 = z2 ? "writeAsArrayNonContext" : "writeDirectNonContext";
            } else {
                str2 = z2 ? "writeAsArray" : "write";
            }
            str = format;
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, str3);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            StringBuilder sb = new StringBuilder("(L");
            String str4 = JSONSerializer;
            sb.append(str4);
            sb.append(";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, str2, sb.toString());
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label8);
            methodVisitor.visitLabel(label7);
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + str4 + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(label8);
            label2 = label5;
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            label3 = label6;
        }
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, 1);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        if (str != null) {
            methodVisitor.visitLdcInsn(str);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, Context.fieldName);
            if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                if (fieldInfo.fieldClass == String.class) {
                    methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc((Class<?>) String.class)));
                } else {
                    methodVisitor.visitVarInsn(25, 0);
                    methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
        }
        methodVisitor.visitLabel(label2);
        _seperator(methodVisitor, context);
    }

    private void _before(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _after(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _notWriteDefault(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (context.writeDirect) {
            return;
        }
        Label label2 = new Label();
        methodVisitor.visitVarInsn(21, context.var("notWriteDefaultValue"));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
        Class<?> cls = fieldInfo.fieldClass;
        if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        } else if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long"));
            methodVisitor.visitInsn(9);
            methodVisitor.visitInsn(Opcodes.LCMP);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitInsn(11);
            methodVisitor.visitInsn(Opcodes.FCMPL);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double"));
            methodVisitor.visitInsn(14);
            methodVisitor.visitInsn(Opcodes.DCMPL);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        }
        methodVisitor.visitLabel(label2);
    }

    private void _apply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _processValue(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        Label label2 = new Label();
        Class<?> cls = fieldInfo.fieldClass;
        if (cls.isPrimitive()) {
            Label label3 = new Label();
            methodVisitor.visitVarInsn(21, context.var("checkValue"));
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label3);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(58, Context.processValue);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            methodVisitor.visitLabel(label3);
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(Integer.valueOf(context.getFieldOrinal(fieldInfo.name)));
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getBeanContext", "(I)" + ASMUtils.desc((Class<?>) BeanContext.class));
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "processValue", "(L" + JSONSerializer + ";" + ASMUtils.desc((Class<?>) BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, Context.processValue);
        methodVisitor.visitVarInsn(25, Context.original);
        methodVisitor.visitVarInsn(25, Context.processValue);
        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, label2);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
        methodVisitor.visitLabel(label2);
    }

    private void _processKey(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        methodVisitor.visitVarInsn(21, context.var("hasNameFilters"));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var(TypedValues.Custom.S_FLOAT));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var(TypedValues.Custom.S_BOOLEAN));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var(TypedValues.Custom.S_STRING));
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var(CollectionUtils.LIST_TYPE));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        methodVisitor.visitVarInsn(58, Context.fieldName);
        methodVisitor.visitLabel(label);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void _if_write_null(com.alibaba.fastjson.asm.MethodVisitor r12, com.alibaba.fastjson.util.FieldInfo r13, com.alibaba.fastjson.serializer.ASMSerializerFactory.Context r14) {
        /*
            Method dump skipped, instruction units count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ASMSerializerFactory._if_write_null(com.alibaba.fastjson.asm.MethodVisitor, com.alibaba.fastjson.util.FieldInfo, com.alibaba.fastjson.serializer.ASMSerializerFactory$Context):void");
    }

    private void _writeFieldName(MethodVisitor methodVisitor, Context context) {
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
        }
    }

    private void _seperator(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _getListFieldItemSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        String str2 = fieldInfo.name + "_asm_list_item_ser_";
        String str3 = ObjectSerializer_desc;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, str2, str3);
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + str3);
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", str3);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", str3);
    }

    private void _getFieldSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        String str2 = fieldInfo.name + "_asm_ser_";
        String str3 = ObjectSerializer_desc;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, str2, str3);
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + str3);
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_ser_", str3);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_ser_", str3);
    }
}

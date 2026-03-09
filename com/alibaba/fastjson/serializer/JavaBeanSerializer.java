package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (Map<String, String>) null);
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, createAliasMap(strArr));
    }

    static Map<String, String> createAliasMap(String... strArr) {
        HashMap map = new HashMap();
        for (String str : strArr) {
            map.put(str, str);
        }
        return map;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    public JavaBeanSerializer(Class<?> cls, Map<String, String> map) {
        this(TypeUtils.buildBeanInfo(cls, map, null));
    }

    public JavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) {
        this.beanInfo = serializeBeanInfo;
        this.sortedGetters = new FieldSerializer[serializeBeanInfo.sortedFields.length];
        int i = 0;
        while (true) {
            FieldSerializer[] fieldSerializerArr = this.sortedGetters;
            if (i >= fieldSerializerArr.length) {
                break;
            }
            fieldSerializerArr[i] = new FieldSerializer(serializeBeanInfo.beanType, serializeBeanInfo.sortedFields[i]);
            i++;
        }
        if (serializeBeanInfo.fields == serializeBeanInfo.sortedFields) {
            this.getters = this.sortedGetters;
        } else {
            this.getters = new FieldSerializer[serializeBeanInfo.fields.length];
            int i2 = 0;
            while (true) {
                if (i2 >= this.getters.length) {
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(serializeBeanInfo.fields[i2].name);
                if (fieldSerializer != null) {
                    this.getters[i2] = fieldSerializer;
                    i2++;
                } else {
                    FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                    System.arraycopy(fieldSerializerArr2, 0, this.getters, 0, fieldSerializerArr2.length);
                    break;
                }
            }
        }
        if (serializeBeanInfo.jsonType != null) {
            for (Class<? extends SerializeFilter> cls : serializeBeanInfo.jsonType.serialzeFilters()) {
                try {
                    addFilter(cls.getConstructor(null).newInstance(null));
                } catch (Exception unused) {
                }
            }
        }
        if (serializeBeanInfo.jsonType != null) {
            for (Class<? extends SerializeFilter> cls2 : serializeBeanInfo.jsonType.serialzeFilters()) {
                try {
                    addFilter(cls2.getConstructor(null).newInstance(null));
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void writeDirectNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i);
    }

    public void writeAsArray(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i);
    }

    public void writeAsArrayNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    public void writeNoneASM(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(3:(4:377|60|(4:69|(4:(1:76)|94|95|392)(1:74)|77|(1:(5:85|92|(1:94)(8:96|(7:98|(0)(2:101|102)|105|106|(5:108|109|(1:111)|112|(4:114|(8:121|(1:125)|(1:235)(6:184|185|373|186|(0)(1:234)|243)|(3:237|(2:244|(1:246))|243)(1:247)|(2:(1:250)(1:251)|252)(2:254|(2:(1:257)|258)(4:(1:266)(1:(1:264)(1:265))|(2:268|(2:285|(1:292)(2:291|393))(1:(2:274|(1:280)(1:279))(2:281|(1:283)(1:284))))(1:293)|294|(2:308|396)(2:298|(2:301|(3:303|(3:306|(3:397|308|396)(1:398)|304)|395)(0))(2:300|394))))|253|294|(3:296|308|396)(0))|120|243)(2:126|(5:128|(3:135|(1:139)|(10:177|179|181|182|235|(0)(0)|(0)(0)|253|294|(0)(0))(0))|134|120|243)(2:140|(2:142|(3:149|(1:153)|(0)(0))(3:148|120|243))(2:154|(2:156|(3:163|(1:167)|(0)(0))(4:162|134|120|243))(1:(2:169|(1:171)))))))(1:174)|175|(0)(0))(1:103)|104|105|106|(0)(0)|175|(0)(0))|95|392)(6:385|86|92|(0)(0)|95|392))(0))(0)|315)|58|379) */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0259, code lost:
    
        if (r9.isEnabled(com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue.mask) == false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:340:0x0459, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0263 A[Catch: all -> 0x0419, Exception -> 0x041e, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x041e, blocks: (B:60:0x00ca, B:69:0x00e8, B:77:0x00fd, B:92:0x0129, B:105:0x015c, B:177:0x0263, B:184:0x0281), top: B:377:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0316 A[Catch: Exception -> 0x0440, all -> 0x0450, TryCatch #0 {all -> 0x0450, blocks: (B:186:0x0285, B:188:0x0289, B:190:0x028d, B:193:0x0298, B:195:0x029c, B:197:0x02a0, B:200:0x02ab, B:202:0x02af, B:204:0x02b3, B:207:0x02be, B:209:0x02c2, B:211:0x02c6, B:214:0x02d4, B:216:0x02d8, B:218:0x02dc, B:221:0x02ea, B:223:0x02ee, B:225:0x02f2, B:228:0x0300, B:230:0x0304, B:232:0x0308, B:237:0x0316, B:239:0x031a, B:241:0x031e, B:244:0x032b, B:246:0x0338, B:250:0x0342, B:252:0x0348, B:294:0x03d3, B:296:0x03d7, B:298:0x03db, B:301:0x03e4, B:303:0x03ec, B:304:0x03f4, B:306:0x03fa, B:257:0x0354, B:258:0x0357, B:261:0x035f, B:264:0x0365, B:268:0x037a, B:271:0x0384, B:274:0x038e, B:276:0x0397, B:279:0x03a1, B:280:0x03a5, B:281:0x03a9, B:283:0x03b0, B:284:0x03b4, B:285:0x03b8, B:287:0x03bc, B:289:0x03c0, B:292:0x03cc, B:293:0x03d0, B:265:0x0370, B:310:0x0405, B:324:0x042b, B:326:0x0431, B:328:0x0439, B:333:0x0448), top: B:373:0x0285 }] */
    /* JADX WARN: Removed duplicated region for block: B:247:0x033c  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x03d7 A[Catch: Exception -> 0x0440, all -> 0x0450, TryCatch #0 {all -> 0x0450, blocks: (B:186:0x0285, B:188:0x0289, B:190:0x028d, B:193:0x0298, B:195:0x029c, B:197:0x02a0, B:200:0x02ab, B:202:0x02af, B:204:0x02b3, B:207:0x02be, B:209:0x02c2, B:211:0x02c6, B:214:0x02d4, B:216:0x02d8, B:218:0x02dc, B:221:0x02ea, B:223:0x02ee, B:225:0x02f2, B:228:0x0300, B:230:0x0304, B:232:0x0308, B:237:0x0316, B:239:0x031a, B:241:0x031e, B:244:0x032b, B:246:0x0338, B:250:0x0342, B:252:0x0348, B:294:0x03d3, B:296:0x03d7, B:298:0x03db, B:301:0x03e4, B:303:0x03ec, B:304:0x03f4, B:306:0x03fa, B:257:0x0354, B:258:0x0357, B:261:0x035f, B:264:0x0365, B:268:0x037a, B:271:0x0384, B:274:0x038e, B:276:0x0397, B:279:0x03a1, B:280:0x03a5, B:281:0x03a9, B:283:0x03b0, B:284:0x03b4, B:285:0x03b8, B:287:0x03bc, B:289:0x03c0, B:292:0x03cc, B:293:0x03d0, B:265:0x0370, B:310:0x0405, B:324:0x042b, B:326:0x0431, B:328:0x0439, B:333:0x0448), top: B:373:0x0285 }] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0428  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0448 A[Catch: Exception -> 0x0440, all -> 0x0450, TRY_LEAVE, TryCatch #0 {all -> 0x0450, blocks: (B:186:0x0285, B:188:0x0289, B:190:0x028d, B:193:0x0298, B:195:0x029c, B:197:0x02a0, B:200:0x02ab, B:202:0x02af, B:204:0x02b3, B:207:0x02be, B:209:0x02c2, B:211:0x02c6, B:214:0x02d4, B:216:0x02d8, B:218:0x02dc, B:221:0x02ea, B:223:0x02ee, B:225:0x02f2, B:228:0x0300, B:230:0x0304, B:232:0x0308, B:237:0x0316, B:239:0x031a, B:241:0x031e, B:244:0x032b, B:246:0x0338, B:250:0x0342, B:252:0x0348, B:294:0x03d3, B:296:0x03d7, B:298:0x03db, B:301:0x03e4, B:303:0x03ec, B:304:0x03f4, B:306:0x03fa, B:257:0x0354, B:258:0x0357, B:261:0x035f, B:264:0x0365, B:268:0x037a, B:271:0x0384, B:274:0x038e, B:276:0x0397, B:279:0x03a1, B:280:0x03a5, B:281:0x03a9, B:283:0x03b0, B:284:0x03b4, B:285:0x03b8, B:287:0x03bc, B:289:0x03c0, B:292:0x03cc, B:293:0x03d0, B:265:0x0370, B:310:0x0405, B:324:0x042b, B:326:0x0431, B:328:0x0439, B:333:0x0448), top: B:373:0x0285 }] */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0467 A[Catch: all -> 0x045d, TRY_LEAVE, TryCatch #10 {all -> 0x045d, blocks: (B:25:0x004f, B:26:0x0052, B:28:0x0055, B:30:0x005d, B:31:0x0063, B:33:0x0071, B:35:0x0078, B:46:0x009a, B:48:0x00a4, B:52:0x00ad, B:56:0x00b6, B:58:0x00c7, B:63:0x00de, B:71:0x00ee, B:79:0x0103, B:81:0x010d, B:98:0x013f, B:101:0x014e, B:109:0x0171, B:111:0x017b, B:114:0x018f, B:116:0x019d, B:118:0x01a1, B:121:0x01ad, B:123:0x01b1, B:179:0x0267, B:181:0x0273, B:348:0x0467, B:351:0x0487, B:359:0x04d9, B:361:0x04df, B:362:0x04f7, B:364:0x04fb, B:369:0x0505, B:370:0x050a, B:353:0x049e, B:355:0x04a2, B:357:0x04a8, B:358:0x04c3, B:125:0x01b7, B:128:0x01c1, B:130:0x01cf, B:132:0x01d3, B:135:0x01dc, B:137:0x01e0, B:140:0x01e9, B:142:0x01f1, B:144:0x01ff, B:146:0x0203, B:149:0x020c, B:151:0x0210, B:153:0x0216, B:154:0x021b, B:156:0x0223, B:158:0x0231, B:160:0x0235, B:163:0x023e, B:165:0x0242, B:167:0x0248, B:169:0x024d, B:171:0x0251, B:86:0x0118, B:37:0x007e, B:39:0x0084, B:41:0x0088, B:44:0x0090), top: B:375:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0487 A[Catch: all -> 0x045d, TRY_ENTER, TryCatch #10 {all -> 0x045d, blocks: (B:25:0x004f, B:26:0x0052, B:28:0x0055, B:30:0x005d, B:31:0x0063, B:33:0x0071, B:35:0x0078, B:46:0x009a, B:48:0x00a4, B:52:0x00ad, B:56:0x00b6, B:58:0x00c7, B:63:0x00de, B:71:0x00ee, B:79:0x0103, B:81:0x010d, B:98:0x013f, B:101:0x014e, B:109:0x0171, B:111:0x017b, B:114:0x018f, B:116:0x019d, B:118:0x01a1, B:121:0x01ad, B:123:0x01b1, B:179:0x0267, B:181:0x0273, B:348:0x0467, B:351:0x0487, B:359:0x04d9, B:361:0x04df, B:362:0x04f7, B:364:0x04fb, B:369:0x0505, B:370:0x050a, B:353:0x049e, B:355:0x04a2, B:357:0x04a8, B:358:0x04c3, B:125:0x01b7, B:128:0x01c1, B:130:0x01cf, B:132:0x01d3, B:135:0x01dc, B:137:0x01e0, B:140:0x01e9, B:142:0x01f1, B:144:0x01ff, B:146:0x0203, B:149:0x020c, B:151:0x0210, B:153:0x0216, B:154:0x021b, B:156:0x0223, B:158:0x0231, B:160:0x0235, B:163:0x023e, B:165:0x0242, B:167:0x0248, B:169:0x024d, B:171:0x0251, B:86:0x0118, B:37:0x007e, B:39:0x0084, B:41:0x0088, B:44:0x0090), top: B:375:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x04df A[Catch: all -> 0x045d, TryCatch #10 {all -> 0x045d, blocks: (B:25:0x004f, B:26:0x0052, B:28:0x0055, B:30:0x005d, B:31:0x0063, B:33:0x0071, B:35:0x0078, B:46:0x009a, B:48:0x00a4, B:52:0x00ad, B:56:0x00b6, B:58:0x00c7, B:63:0x00de, B:71:0x00ee, B:79:0x0103, B:81:0x010d, B:98:0x013f, B:101:0x014e, B:109:0x0171, B:111:0x017b, B:114:0x018f, B:116:0x019d, B:118:0x01a1, B:121:0x01ad, B:123:0x01b1, B:179:0x0267, B:181:0x0273, B:348:0x0467, B:351:0x0487, B:359:0x04d9, B:361:0x04df, B:362:0x04f7, B:364:0x04fb, B:369:0x0505, B:370:0x050a, B:353:0x049e, B:355:0x04a2, B:357:0x04a8, B:358:0x04c3, B:125:0x01b7, B:128:0x01c1, B:130:0x01cf, B:132:0x01d3, B:135:0x01dc, B:137:0x01e0, B:140:0x01e9, B:142:0x01f1, B:144:0x01ff, B:146:0x0203, B:149:0x020c, B:151:0x0210, B:153:0x0216, B:154:0x021b, B:156:0x0223, B:158:0x0231, B:160:0x0235, B:163:0x023e, B:165:0x0242, B:167:0x0248, B:169:0x024d, B:171:0x0251, B:86:0x0118, B:37:0x007e, B:39:0x0084, B:41:0x0088, B:44:0x0090), top: B:375:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:364:0x04fb A[Catch: all -> 0x045d, TryCatch #10 {all -> 0x045d, blocks: (B:25:0x004f, B:26:0x0052, B:28:0x0055, B:30:0x005d, B:31:0x0063, B:33:0x0071, B:35:0x0078, B:46:0x009a, B:48:0x00a4, B:52:0x00ad, B:56:0x00b6, B:58:0x00c7, B:63:0x00de, B:71:0x00ee, B:79:0x0103, B:81:0x010d, B:98:0x013f, B:101:0x014e, B:109:0x0171, B:111:0x017b, B:114:0x018f, B:116:0x019d, B:118:0x01a1, B:121:0x01ad, B:123:0x01b1, B:179:0x0267, B:181:0x0273, B:348:0x0467, B:351:0x0487, B:359:0x04d9, B:361:0x04df, B:362:0x04f7, B:364:0x04fb, B:369:0x0505, B:370:0x050a, B:353:0x049e, B:355:0x04a2, B:357:0x04a8, B:358:0x04c3, B:125:0x01b7, B:128:0x01c1, B:130:0x01cf, B:132:0x01d3, B:135:0x01dc, B:137:0x01e0, B:140:0x01e9, B:142:0x01f1, B:144:0x01ff, B:146:0x0203, B:149:0x020c, B:151:0x0210, B:153:0x0216, B:154:0x021b, B:156:0x0223, B:158:0x0231, B:160:0x0235, B:163:0x023e, B:165:0x0242, B:167:0x0248, B:169:0x024d, B:171:0x0251, B:86:0x0118, B:37:0x007e, B:39:0x0084, B:41:0x0088, B:44:0x0090), top: B:375:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0500  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0503  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0504  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x00ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x012f A[PHI: r21
  0x012f: PHI (r21v8 com.alibaba.fastjson.serializer.FieldSerializer) = 
  (r21v1 com.alibaba.fastjson.serializer.FieldSerializer)
  (r21v4 com.alibaba.fastjson.serializer.FieldSerializer)
  (r21v1 com.alibaba.fastjson.serializer.FieldSerializer)
  (r21v1 com.alibaba.fastjson.serializer.FieldSerializer)
  (r21v1 com.alibaba.fastjson.serializer.FieldSerializer)
 binds: [B:75:0x00fa, B:93:0x012d, B:82:0x0111, B:67:0x00e5, B:64:0x00e0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x013b  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void write(com.alibaba.fastjson.serializer.JSONSerializer r31, java.lang.Object r32, java.lang.Object r33, java.lang.reflect.Type r34, int r35, boolean r36) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }

    protected void writeClassName(JSONSerializer jSONSerializer, String str, Object obj) {
        if (str == null) {
            str = jSONSerializer.config.typeKey;
        }
        jSONSerializer.out.writeFieldName(str, false);
        String name = this.beanInfo.typeName;
        if (name == null) {
            Class<?> superclass = obj.getClass();
            if (TypeUtils.isProxy(superclass)) {
                superclass = superclass.getSuperclass();
            }
            name = superclass.getName();
        }
        jSONSerializer.write(name);
    }

    public boolean writeReference(JSONSerializer jSONSerializer, Object obj, int i) {
        SerialContext serialContext = jSONSerializer.context;
        int i2 = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (serialContext == null || (serialContext.features & i2) != 0 || (i & i2) != 0 || jSONSerializer.references == null || !jSONSerializer.references.containsKey(obj)) {
            return false;
        }
        jSONSerializer.writeReference(obj);
        return true;
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer) {
        return isWriteAsArray(jSONSerializer, 0);
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer, int i) {
        int i2 = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & i2) == 0 && !jSONSerializer.out.beanToArray && (i & i2) == 0) ? false : true;
    }

    public Object getFieldValue(Object obj, String str) {
        FieldSerializer fieldSerializer = getFieldSerializer(str);
        if (fieldSerializer == null) {
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e) {
            throw new JSONException("getFieldValue error." + str, e);
        } catch (InvocationTargetException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        }
    }

    public Object getFieldValue(Object obj, String str, long j, boolean z) {
        FieldSerializer fieldSerializer = getFieldSerializer(j);
        if (fieldSerializer == null) {
            if (!z) {
                return null;
            }
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e) {
            throw new JSONException("getFieldValue error." + str, e);
        } catch (InvocationTargetException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        }
    }

    public FieldSerializer getFieldSerializer(String str) {
        if (str == null) {
            return null;
        }
        int length = this.sortedGetters.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int iCompareTo = this.sortedGetters[i2].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i = i2 + 1;
            } else {
                if (iCompareTo <= 0) {
                    return this.sortedGetters[i2];
                }
                length = i2 - 1;
            }
        }
        return null;
    }

    public FieldSerializer getFieldSerializer(long j) {
        PropertyNamingStrategy[] propertyNamingStrategyArrValues;
        int iBinarySearch;
        if (this.hashArray == null) {
            propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            long[] jArr = new long[this.sortedGetters.length * propertyNamingStrategyArrValues.length];
            int i = 0;
            int i2 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr = this.sortedGetters;
                if (i >= fieldSerializerArr.length) {
                    break;
                }
                String str = fieldSerializerArr[i].fieldInfo.name;
                jArr[i2] = TypeUtils.fnv1a_64(str);
                i2++;
                for (PropertyNamingStrategy propertyNamingStrategy : propertyNamingStrategyArrValues) {
                    String strTranslate = propertyNamingStrategy.translate(str);
                    if (!str.equals(strTranslate)) {
                        jArr[i2] = TypeUtils.fnv1a_64(strTranslate);
                        i2++;
                    }
                }
                i++;
            }
            Arrays.sort(jArr, 0, i2);
            this.hashArray = new long[i2];
            System.arraycopy(jArr, 0, this.hashArray, 0, i2);
        } else {
            propertyNamingStrategyArrValues = null;
        }
        int iBinarySearch2 = Arrays.binarySearch(this.hashArray, j);
        if (iBinarySearch2 < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (propertyNamingStrategyArrValues == null) {
                propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            }
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            int i3 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                if (i3 >= fieldSerializerArr2.length) {
                    break;
                }
                String str2 = fieldSerializerArr2[i3].fieldInfo.name;
                int iBinarySearch3 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(str2));
                if (iBinarySearch3 >= 0) {
                    sArr[iBinarySearch3] = (short) i3;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : propertyNamingStrategyArrValues) {
                    String strTranslate2 = propertyNamingStrategy2.translate(str2);
                    if (!str2.equals(strTranslate2) && (iBinarySearch = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(strTranslate2))) >= 0) {
                        sArr[iBinarySearch] = (short) i3;
                    }
                }
                i3++;
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[iBinarySearch2];
        if (s != -1) {
            return this.sortedGetters[s];
        }
        return null;
    }

    public List<Object> getFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            arrayList.add(fieldSerializer.getPropertyValue(obj));
        }
        return arrayList;
    }

    public List<Object> getObjectFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            Class<?> cls = fieldSerializer.fieldInfo.fieldClass;
            if (!cls.isPrimitive() && !cls.getName().startsWith("java.lang.")) {
                arrayList.add(fieldSerializer.getPropertyValue(obj));
            }
        }
        return arrayList;
    }

    public int getSize(Object obj) throws Exception {
        int i = 0;
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                i++;
            }
        }
        return i;
    }

    public Set<String> getFieldNames(Object obj) throws Exception {
        HashSet hashSet = new HashSet();
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                hashSet.add(fieldSerializer.fieldInfo.name);
            }
        }
        return hashSet;
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
        }
        return linkedHashMap;
    }

    protected BeanContext getBeanContext(int i) {
        return this.sortedGetters[i].fieldContext;
    }

    protected Type getFieldType(int i) {
        return this.sortedGetters[i].fieldInfo.fieldType;
    }

    protected char writeBefore(JSONSerializer jSONSerializer, Object obj, char c) {
        if (jSONSerializer.beforeFilters != null) {
            Iterator<BeforeFilter> it = jSONSerializer.beforeFilters.iterator();
            while (it.hasNext()) {
                c = it.next().writeBefore(jSONSerializer, obj, c);
            }
        }
        if (this.beforeFilters != null) {
            Iterator<BeforeFilter> it2 = this.beforeFilters.iterator();
            while (it2.hasNext()) {
                c = it2.next().writeBefore(jSONSerializer, obj, c);
            }
        }
        return c;
    }

    protected char writeAfter(JSONSerializer jSONSerializer, Object obj, char c) {
        if (jSONSerializer.afterFilters != null) {
            Iterator<AfterFilter> it = jSONSerializer.afterFilters.iterator();
            while (it.hasNext()) {
                c = it.next().writeAfter(jSONSerializer, obj, c);
            }
        }
        if (this.afterFilters != null) {
            Iterator<AfterFilter> it2 = this.afterFilters.iterator();
            while (it2.hasNext()) {
                c = it2.next().writeAfter(jSONSerializer, obj, c);
            }
        }
        return c;
    }

    protected boolean applyLabel(JSONSerializer jSONSerializer, String str) {
        if (jSONSerializer.labelFilters != null) {
            Iterator<LabelFilter> it = jSONSerializer.labelFilters.iterator();
            while (it.hasNext()) {
                if (!it.next().apply(str)) {
                    return false;
                }
            }
        }
        if (this.labelFilters == null) {
            return true;
        }
        Iterator<LabelFilter> it2 = this.labelFilters.iterator();
        while (it2.hasNext()) {
            if (!it2.next().apply(str)) {
                return false;
            }
        }
        return true;
    }
}

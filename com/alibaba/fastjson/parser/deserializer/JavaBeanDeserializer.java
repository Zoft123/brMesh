package com.alibaba.fastjson.parser.deserializer;

import androidx.camera.video.AudioStats;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import j$.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls) {
        this(parserConfig, cls, cls);
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, JavaBeanInfo.build(cls, type, parserConfig.propertyNamingStrategy, parserConfig.fieldBased, parserConfig.compatibleWithJavaBean, parserConfig.isJacksonCompatible()));
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) {
        this.clazz = javaBeanInfo.clazz;
        this.beanInfo = javaBeanInfo;
        this.sortedFieldDeserializers = new FieldDeserializer[javaBeanInfo.sortedFields.length];
        int length = javaBeanInfo.sortedFields.length;
        HashMap map = null;
        for (int i = 0; i < length; i++) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i];
            FieldDeserializer fieldDeserializerCreateFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, javaBeanInfo, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializerCreateFieldDeserializer;
            if (length > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializerCreateFieldDeserializer);
            }
            for (String str : fieldInfo.alternateNames) {
                if (map == null) {
                    map = new HashMap();
                }
                map.put(str, fieldDeserializerCreateFieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = map;
        this.fieldDeserializers = new FieldDeserializer[javaBeanInfo.fields.length];
        int length2 = javaBeanInfo.fields.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(javaBeanInfo.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String str) {
        return getFieldDeserializer(str, null);
    }

    public FieldDeserializer getFieldDeserializer(String str, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        Map<String, FieldDeserializer> map = this.fieldDeserializerMap;
        if (map != null && (fieldDeserializer = map.get(str)) != null) {
            return fieldDeserializer;
        }
        int length = this.sortedFieldDeserializers.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int iCompareTo = this.sortedFieldDeserializers[i2].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i = i2 + 1;
            } else {
                if (iCompareTo <= 0) {
                    if (isSetFlag(i2, iArr)) {
                        return null;
                    }
                    return this.sortedFieldDeserializers[i2];
                }
                length = i2 - 1;
            }
        }
        Map<String, FieldDeserializer> map2 = this.alterNameFieldDeserializers;
        if (map2 != null) {
            return map2.get(str);
        }
        return null;
    }

    public FieldDeserializer getFieldDeserializer(long j) {
        int i = 0;
        if (this.hashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            int i2 = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i2 >= fieldDeserializerArr.length) {
                    break;
                }
                jArr[i2] = TypeUtils.fnv1a_64(fieldDeserializerArr[i2].fieldInfo.name);
                i2++;
            }
            Arrays.sort(jArr);
            this.hashArray = jArr;
        }
        int iBinarySearch = Arrays.binarySearch(this.hashArray, j);
        if (iBinarySearch < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            while (true) {
                FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                if (i >= fieldDeserializerArr2.length) {
                    break;
                }
                int iBinarySearch2 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(fieldDeserializerArr2[i].fieldInfo.name));
                if (iBinarySearch2 >= 0) {
                    sArr[iBinarySearch2] = (short) i;
                }
                i++;
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[iBinarySearch];
        if (s != -1) {
            return this.sortedFieldDeserializers[s];
        }
        return null;
    }

    static boolean isSetFlag(int i, int[] iArr) {
        if (iArr == null) {
            return false;
        }
        int i2 = i / 32;
        int i3 = i % 32;
        if (i2 < iArr.length) {
            if (((1 << i3) & iArr[i2]) != 0) {
                return true;
            }
        }
        return false;
    }

    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) {
        Object objNewInstance;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        Object obj = null;
        if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        }
        if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.defaultConstructor;
            if (this.beanInfo.defaultConstructorParameterSize != 0) {
                ParseContext context = defaultJSONParser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (type instanceof Class) {
                    String name = ((Class) type).getName();
                    String strSubstring = name.substring(0, name.lastIndexOf(36));
                    Object obj2 = context.object;
                    String name2 = obj2.getClass().getName();
                    if (!name2.equals(strSubstring)) {
                        ParseContext parseContext = context.parent;
                        if (parseContext == null || parseContext.object == null || !("java.util.ArrayList".equals(name2) || "java.util.List".equals(name2) || "java.util.Collection".equals(name2) || "java.util.Map".equals(name2) || "java.util.HashMap".equals(name2))) {
                            obj = obj2;
                        } else if (parseContext.object.getClass().getName().equals(strSubstring)) {
                            obj = parseContext.object;
                        }
                        obj2 = obj;
                    }
                    if (obj2 == null || ((obj2 instanceof Collection) && ((Collection) obj2).isEmpty())) {
                        throw new JSONException("can't create non-static inner class instance.");
                    }
                    objNewInstance = constructor.newInstance(obj2);
                } else {
                    throw new JSONException("can't create non-static inner class instance.");
                }
            } else if (constructor != null) {
                objNewInstance = constructor.newInstance(null);
            } else {
                objNewInstance = this.beanInfo.factoryMethod.invoke(null, null);
            }
            if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                for (FieldInfo fieldInfo : this.beanInfo.fields) {
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(objNewInstance, "");
                        } catch (Exception e) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                        }
                    }
                }
            }
            return objNewInstance;
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i, null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Enum<?> enumScanEnum;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException("error");
        }
        T t = (T) createInstance(defaultJSONParser, type);
        int length = this.sortedFieldDeserializers.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            char c = i == length + (-1) ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanInt(c));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanString(c));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanLong(c));
            } else if (cls.isEnum()) {
                char current = jSONLexer.getCurrent();
                if (current == '\"' || current == 'n') {
                    enumScanEnum = jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c);
                } else if (current >= '0' && current <= '9') {
                    enumScanEnum = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c));
                } else {
                    enumScanEnum = scanEnum(jSONLexer, c);
                }
                fieldDeserializer.setValue(t, enumScanEnum);
            } else if (cls == Boolean.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanBoolean(c));
            } else if (cls == Float.TYPE) {
                fieldDeserializer.setValue(t, Float.valueOf(jSONLexer.scanFloat(c)));
            } else if (cls == Double.TYPE) {
                fieldDeserializer.setValue(t, Double.valueOf(jSONLexer.scanDouble(c)));
            } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                fieldDeserializer.setValue(t, new Date(jSONLexer.scanLong(c)));
            } else if (cls == BigDecimal.class) {
                fieldDeserializer.setValue(t, jSONLexer.scanDecimal(c));
            } else {
                jSONLexer.nextToken(14);
                fieldDeserializer.setValue(t, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType, fieldDeserializer.fieldInfo.name));
                if (jSONLexer.token() == 15) {
                    break;
                }
                check(jSONLexer, c == ']' ? 15 : 16);
            }
            i++;
        }
        jSONLexer.nextToken(16);
        return t;
    }

    protected void check(JSONLexer jSONLexer, int i) {
        if (jSONLexer.token() != i) {
            throw new JSONException("syntax error");
        }
    }

    protected Enum<?> scanEnum(JSONLexer jSONLexer, char c) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    /* JADX WARN: Code restructure failed: missing block: B:281:0x0378, code lost:
    
        if (r8.matchStat == (-2)) goto L282;
     */
    /* JADX WARN: Code restructure failed: missing block: B:379:0x0514, code lost:
    
        r6 = r17;
        r3 = r3;
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:605:0x089e, code lost:
    
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r8.token()));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01c3 A[Catch: all -> 0x0382, TryCatch #1 {all -> 0x0382, blocks: (B:107:0x0197, B:109:0x01a3, B:111:0x01a9, B:117:0x01c3, B:119:0x01c9, B:290:0x038f, B:292:0x0399, B:294:0x03a5, B:442:0x0607, B:444:0x060d, B:449:0x0619, B:452:0x0621, B:453:0x0625, B:455:0x0628, B:457:0x0630, B:459:0x0640, B:498:0x06c9, B:460:0x0647, B:462:0x064c, B:463:0x0652, B:465:0x0656, B:466:0x065c, B:468:0x0660, B:469:0x0665, B:471:0x0669, B:472:0x066e, B:474:0x0672, B:475:0x0677, B:477:0x067b, B:480:0x0682, B:483:0x068d, B:485:0x0693, B:487:0x069a, B:489:0x06a4, B:491:0x06ac, B:493:0x06b0, B:495:0x06ba, B:497:0x06c5, B:532:0x073e, B:534:0x0746, B:537:0x074e, B:539:0x0751, B:541:0x0755, B:543:0x075b, B:545:0x0762, B:547:0x076e, B:549:0x0774, B:550:0x0780, B:552:0x0783, B:554:0x0787, B:556:0x078d, B:558:0x0794, B:559:0x079d, B:564:0x07b0, B:565:0x07b8, B:567:0x07be, B:569:0x07d0, B:581:0x0833, B:571:0x07d9, B:572:0x07ff, B:560:0x07a0, B:561:0x07a5, B:573:0x0800, B:575:0x0806, B:578:0x0812, B:579:0x0830, B:499:0x06cf, B:501:0x06d9, B:503:0x06e3, B:505:0x06e9, B:530:0x0738, B:506:0x06f1, B:508:0x06f7, B:509:0x06fc, B:511:0x0700, B:512:0x0705, B:514:0x0709, B:515:0x070e, B:517:0x0712, B:518:0x0717, B:520:0x071b, B:521:0x0720, B:523:0x0724, B:526:0x072b, B:582:0x0835, B:589:0x0844, B:595:0x0851, B:596:0x0858, B:297:0x03b0, B:304:0x03c4, B:306:0x03ce, B:308:0x03da, B:340:0x0461, B:342:0x046a, B:347:0x047a, B:348:0x0481, B:310:0x03e0, B:312:0x03e8, B:314:0x03ee, B:315:0x03f1, B:316:0x03fd, B:319:0x0406, B:321:0x040a, B:322:0x040d, B:324:0x0411, B:325:0x0414, B:326:0x0420, B:328:0x0428, B:329:0x042e, B:331:0x0434, B:333:0x043a, B:334:0x0440, B:335:0x0448, B:336:0x044c, B:339:0x0454, B:349:0x0482, B:350:0x049c, B:352:0x049f, B:356:0x04a9, B:358:0x04b3, B:360:0x04c6, B:363:0x04cf, B:365:0x04d7, B:367:0x04ee, B:369:0x04f6, B:371:0x04fa, B:376:0x0509, B:378:0x0511, B:382:0x0526, B:383:0x052e, B:354:0x04a5, B:123:0x01d8, B:128:0x01e7, B:135:0x01f5, B:138:0x0201, B:282:0x037a, B:143:0x020b, B:145:0x020f, B:148:0x0218, B:153:0x0222, B:156:0x022b, B:161:0x0235, B:164:0x023e, B:167:0x0244, B:172:0x024e, B:177:0x0258, B:182:0x0262, B:184:0x0268, B:187:0x0276, B:189:0x027e, B:191:0x0282, B:194:0x0291, B:199:0x029c, B:202:0x02a6, B:207:0x02b1, B:210:0x02bb, B:215:0x02c6, B:218:0x02d0, B:221:0x02d7, B:224:0x02df, B:226:0x02e7, B:230:0x02f3, B:233:0x02f9, B:229:0x02ef, B:236:0x0300, B:238:0x0308, B:242:0x0314, B:245:0x031a, B:241:0x0310, B:248:0x0320, B:252:0x0330, B:255:0x0336, B:251:0x032c, B:258:0x033c, B:260:0x0344, B:264:0x0350, B:267:0x0356, B:263:0x034c, B:270:0x035c, B:272:0x0362, B:277:0x036f, B:280:0x0375, B:276:0x036b), top: B:625:0x0197, inners: #0, #3, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x038f A[Catch: all -> 0x0382, TryCatch #1 {all -> 0x0382, blocks: (B:107:0x0197, B:109:0x01a3, B:111:0x01a9, B:117:0x01c3, B:119:0x01c9, B:290:0x038f, B:292:0x0399, B:294:0x03a5, B:442:0x0607, B:444:0x060d, B:449:0x0619, B:452:0x0621, B:453:0x0625, B:455:0x0628, B:457:0x0630, B:459:0x0640, B:498:0x06c9, B:460:0x0647, B:462:0x064c, B:463:0x0652, B:465:0x0656, B:466:0x065c, B:468:0x0660, B:469:0x0665, B:471:0x0669, B:472:0x066e, B:474:0x0672, B:475:0x0677, B:477:0x067b, B:480:0x0682, B:483:0x068d, B:485:0x0693, B:487:0x069a, B:489:0x06a4, B:491:0x06ac, B:493:0x06b0, B:495:0x06ba, B:497:0x06c5, B:532:0x073e, B:534:0x0746, B:537:0x074e, B:539:0x0751, B:541:0x0755, B:543:0x075b, B:545:0x0762, B:547:0x076e, B:549:0x0774, B:550:0x0780, B:552:0x0783, B:554:0x0787, B:556:0x078d, B:558:0x0794, B:559:0x079d, B:564:0x07b0, B:565:0x07b8, B:567:0x07be, B:569:0x07d0, B:581:0x0833, B:571:0x07d9, B:572:0x07ff, B:560:0x07a0, B:561:0x07a5, B:573:0x0800, B:575:0x0806, B:578:0x0812, B:579:0x0830, B:499:0x06cf, B:501:0x06d9, B:503:0x06e3, B:505:0x06e9, B:530:0x0738, B:506:0x06f1, B:508:0x06f7, B:509:0x06fc, B:511:0x0700, B:512:0x0705, B:514:0x0709, B:515:0x070e, B:517:0x0712, B:518:0x0717, B:520:0x071b, B:521:0x0720, B:523:0x0724, B:526:0x072b, B:582:0x0835, B:589:0x0844, B:595:0x0851, B:596:0x0858, B:297:0x03b0, B:304:0x03c4, B:306:0x03ce, B:308:0x03da, B:340:0x0461, B:342:0x046a, B:347:0x047a, B:348:0x0481, B:310:0x03e0, B:312:0x03e8, B:314:0x03ee, B:315:0x03f1, B:316:0x03fd, B:319:0x0406, B:321:0x040a, B:322:0x040d, B:324:0x0411, B:325:0x0414, B:326:0x0420, B:328:0x0428, B:329:0x042e, B:331:0x0434, B:333:0x043a, B:334:0x0440, B:335:0x0448, B:336:0x044c, B:339:0x0454, B:349:0x0482, B:350:0x049c, B:352:0x049f, B:356:0x04a9, B:358:0x04b3, B:360:0x04c6, B:363:0x04cf, B:365:0x04d7, B:367:0x04ee, B:369:0x04f6, B:371:0x04fa, B:376:0x0509, B:378:0x0511, B:382:0x0526, B:383:0x052e, B:354:0x04a5, B:123:0x01d8, B:128:0x01e7, B:135:0x01f5, B:138:0x0201, B:282:0x037a, B:143:0x020b, B:145:0x020f, B:148:0x0218, B:153:0x0222, B:156:0x022b, B:161:0x0235, B:164:0x023e, B:167:0x0244, B:172:0x024e, B:177:0x0258, B:182:0x0262, B:184:0x0268, B:187:0x0276, B:189:0x027e, B:191:0x0282, B:194:0x0291, B:199:0x029c, B:202:0x02a6, B:207:0x02b1, B:210:0x02bb, B:215:0x02c6, B:218:0x02d0, B:221:0x02d7, B:224:0x02df, B:226:0x02e7, B:230:0x02f3, B:233:0x02f9, B:229:0x02ef, B:236:0x0300, B:238:0x0308, B:242:0x0314, B:245:0x031a, B:241:0x0310, B:248:0x0320, B:252:0x0330, B:255:0x0336, B:251:0x032c, B:258:0x033c, B:260:0x0344, B:264:0x0350, B:267:0x0356, B:263:0x034c, B:270:0x035c, B:272:0x0362, B:277:0x036f, B:280:0x0375, B:276:0x036b), top: B:625:0x0197, inners: #0, #3, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0535  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0540 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x056d A[PHI: r6 r7 r17
  0x056d: PHI (r6v11 java.lang.Object) = (r6v10 java.lang.Object), (r6v10 java.lang.Object), (r6v22 java.lang.Object) binds: [B:386:0x053e, B:387:0x0540, B:392:0x0556] A[DONT_GENERATE, DONT_INLINE]
  0x056d: PHI (r7v6 com.alibaba.fastjson.parser.ParseContext) = 
  (r7v2 com.alibaba.fastjson.parser.ParseContext)
  (r7v2 com.alibaba.fastjson.parser.ParseContext)
  (r7v11 com.alibaba.fastjson.parser.ParseContext)
 binds: [B:386:0x053e, B:387:0x0540, B:392:0x0556] A[DONT_GENERATE, DONT_INLINE]
  0x056d: PHI (r17v3 ??) = (r17v1 ??), (r17v1 ??), (r17v5 ??) binds: [B:386:0x053e, B:387:0x0540, B:392:0x0556] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x05c0  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x05f6 A[Catch: all -> 0x08a8, TryCatch #2 {all -> 0x08a8, blocks: (B:603:0x0873, B:433:0x05e5, B:436:0x05f6, B:438:0x05fe, B:598:0x085d, B:600:0x0865, B:604:0x087f, B:605:0x089e, B:425:0x05c3, B:427:0x05c9, B:429:0x05d1, B:431:0x05dd, B:606:0x089f, B:607:0x08a7), top: B:626:0x0873 }] */
    /* JADX WARN: Removed duplicated region for block: B:564:0x07b0 A[Catch: all -> 0x0382, TRY_ENTER, TryCatch #1 {all -> 0x0382, blocks: (B:107:0x0197, B:109:0x01a3, B:111:0x01a9, B:117:0x01c3, B:119:0x01c9, B:290:0x038f, B:292:0x0399, B:294:0x03a5, B:442:0x0607, B:444:0x060d, B:449:0x0619, B:452:0x0621, B:453:0x0625, B:455:0x0628, B:457:0x0630, B:459:0x0640, B:498:0x06c9, B:460:0x0647, B:462:0x064c, B:463:0x0652, B:465:0x0656, B:466:0x065c, B:468:0x0660, B:469:0x0665, B:471:0x0669, B:472:0x066e, B:474:0x0672, B:475:0x0677, B:477:0x067b, B:480:0x0682, B:483:0x068d, B:485:0x0693, B:487:0x069a, B:489:0x06a4, B:491:0x06ac, B:493:0x06b0, B:495:0x06ba, B:497:0x06c5, B:532:0x073e, B:534:0x0746, B:537:0x074e, B:539:0x0751, B:541:0x0755, B:543:0x075b, B:545:0x0762, B:547:0x076e, B:549:0x0774, B:550:0x0780, B:552:0x0783, B:554:0x0787, B:556:0x078d, B:558:0x0794, B:559:0x079d, B:564:0x07b0, B:565:0x07b8, B:567:0x07be, B:569:0x07d0, B:581:0x0833, B:571:0x07d9, B:572:0x07ff, B:560:0x07a0, B:561:0x07a5, B:573:0x0800, B:575:0x0806, B:578:0x0812, B:579:0x0830, B:499:0x06cf, B:501:0x06d9, B:503:0x06e3, B:505:0x06e9, B:530:0x0738, B:506:0x06f1, B:508:0x06f7, B:509:0x06fc, B:511:0x0700, B:512:0x0705, B:514:0x0709, B:515:0x070e, B:517:0x0712, B:518:0x0717, B:520:0x071b, B:521:0x0720, B:523:0x0724, B:526:0x072b, B:582:0x0835, B:589:0x0844, B:595:0x0851, B:596:0x0858, B:297:0x03b0, B:304:0x03c4, B:306:0x03ce, B:308:0x03da, B:340:0x0461, B:342:0x046a, B:347:0x047a, B:348:0x0481, B:310:0x03e0, B:312:0x03e8, B:314:0x03ee, B:315:0x03f1, B:316:0x03fd, B:319:0x0406, B:321:0x040a, B:322:0x040d, B:324:0x0411, B:325:0x0414, B:326:0x0420, B:328:0x0428, B:329:0x042e, B:331:0x0434, B:333:0x043a, B:334:0x0440, B:335:0x0448, B:336:0x044c, B:339:0x0454, B:349:0x0482, B:350:0x049c, B:352:0x049f, B:356:0x04a9, B:358:0x04b3, B:360:0x04c6, B:363:0x04cf, B:365:0x04d7, B:367:0x04ee, B:369:0x04f6, B:371:0x04fa, B:376:0x0509, B:378:0x0511, B:382:0x0526, B:383:0x052e, B:354:0x04a5, B:123:0x01d8, B:128:0x01e7, B:135:0x01f5, B:138:0x0201, B:282:0x037a, B:143:0x020b, B:145:0x020f, B:148:0x0218, B:153:0x0222, B:156:0x022b, B:161:0x0235, B:164:0x023e, B:167:0x0244, B:172:0x024e, B:177:0x0258, B:182:0x0262, B:184:0x0268, B:187:0x0276, B:189:0x027e, B:191:0x0282, B:194:0x0291, B:199:0x029c, B:202:0x02a6, B:207:0x02b1, B:210:0x02bb, B:215:0x02c6, B:218:0x02d0, B:221:0x02d7, B:224:0x02df, B:226:0x02e7, B:230:0x02f3, B:233:0x02f9, B:229:0x02ef, B:236:0x0300, B:238:0x0308, B:242:0x0314, B:245:0x031a, B:241:0x0310, B:248:0x0320, B:252:0x0330, B:255:0x0336, B:251:0x032c, B:258:0x033c, B:260:0x0344, B:264:0x0350, B:267:0x0356, B:263:0x034c, B:270:0x035c, B:272:0x0362, B:277:0x036f, B:280:0x0375, B:276:0x036b), top: B:625:0x0197, inners: #0, #3, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:617:0x08b8  */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.lang.Class, java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r10v42 */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v54 */
    /* JADX WARN: Type inference failed for: r10v55 */
    /* JADX WARN: Type inference failed for: r10v56 */
    /* JADX WARN: Type inference failed for: r10v57 */
    /* JADX WARN: Type inference failed for: r10v58 */
    /* JADX WARN: Type inference failed for: r10v59 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v60 */
    /* JADX WARN: Type inference failed for: r10v61 */
    /* JADX WARN: Type inference failed for: r10v62 */
    /* JADX WARN: Type inference failed for: r10v63 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.alibaba.fastjson.parser.ParseContext] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r13v33 */
    /* JADX WARN: Type inference failed for: r13v34 */
    /* JADX WARN: Type inference failed for: r13v5, types: [com.alibaba.fastjson.parser.deserializer.FieldDeserializer] */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v4, types: [com.alibaba.fastjson.util.FieldInfo] */
    /* JADX WARN: Type inference failed for: r15v10, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Type inference failed for: r17v7 */
    /* JADX WARN: Type inference failed for: r17v8 */
    /* JADX WARN: Type inference failed for: r17v9 */
    /* JADX WARN: Type inference failed for: r19v2, types: [com.alibaba.fastjson.annotation.JSONField] */
    /* JADX WARN: Type inference failed for: r19v7 */
    /* JADX WARN: Type inference failed for: r19v8 */
    /* JADX WARN: Type inference failed for: r27v0 */
    /* JADX WARN: Type inference failed for: r27v1 */
    /* JADX WARN: Type inference failed for: r27v2 */
    /* JADX WARN: Type inference failed for: r27v3 */
    /* JADX WARN: Type inference failed for: r27v4 */
    /* JADX WARN: Type inference failed for: r27v5 */
    /* JADX WARN: Type inference failed for: r27v6 */
    /* JADX WARN: Type inference failed for: r27v7 */
    /* JADX WARN: Type inference failed for: r29v0, types: [com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer] */
    /* JADX WARN: Type inference failed for: r30v0, types: [com.alibaba.fastjson.parser.DefaultJSONParser] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v63 */
    /* JADX WARN: Type inference failed for: r3v64 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v85 */
    /* JADX WARN: Type inference failed for: r3v86 */
    /* JADX WARN: Type inference failed for: r4v110 */
    /* JADX WARN: Type inference failed for: r4v111 */
    /* JADX WARN: Type inference failed for: r4v112 */
    /* JADX WARN: Type inference failed for: r4v113 */
    /* JADX WARN: Type inference failed for: r4v114 */
    /* JADX WARN: Type inference failed for: r4v14, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Type inference failed for: r5v28, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r5v52 */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v125 */
    /* JADX WARN: Type inference failed for: r6v126 */
    /* JADX WARN: Type inference failed for: r6v127 */
    /* JADX WARN: Type inference failed for: r6v128 */
    /* JADX WARN: Type inference failed for: r6v129 */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r6v130 */
    /* JADX WARN: Type inference failed for: r6v131 */
    /* JADX WARN: Type inference failed for: r6v132 */
    /* JADX WARN: Type inference failed for: r6v133 */
    /* JADX WARN: Type inference failed for: r6v134 */
    /* JADX WARN: Type inference failed for: r6v135 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v23, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Type inference failed for: r6v40, types: [com.alibaba.fastjson.parser.deserializer.ObjectDeserializer] */
    /* JADX WARN: Type inference failed for: r6v95, types: [com.alibaba.fastjson.parser.ParserConfig] */
    /* JADX WARN: Type inference failed for: r6v99 */
    /* JADX WARN: Type inference failed for: r8v15, types: [java.lang.Object[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r30, java.lang.reflect.Type r31, java.lang.Object r32, java.lang.Object r33, int r34, int[] r35) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 2243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    protected Enum scanEnum(JSONLexerBase jSONLexerBase, char[] cArr, ObjectDeserializer objectDeserializer) {
        EnumDeserializer enumDeserializer = objectDeserializer instanceof EnumDeserializer ? (EnumDeserializer) objectDeserializer : null;
        if (enumDeserializer == null) {
            jSONLexerBase.matchStat = -1;
            return null;
        }
        long jScanEnumSymbol = jSONLexerBase.scanEnumSymbol(cArr);
        if (jSONLexerBase.matchStat <= 0) {
            return null;
        }
        Enum enumByHashCode = enumDeserializer.getEnumByHashCode(jScanEnumSymbol);
        if (enumByHashCode == null) {
            if (jScanEnumSymbol == -3750763034362895579L) {
                return null;
            }
            if (jSONLexerBase.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return enumByHashCode;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        return parseField(defaultJSONParser, str, obj, type, map, null);
    }

    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        ?? r17;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = Feature.DisableFieldSmartMatch.mask;
        if (jSONLexer.isEnabled(i) || (i & this.beanInfo.parserFeatures) != 0) {
            fieldDeserializer = getFieldDeserializer(str);
        } else {
            fieldDeserializer = smartMatch(str, iArr);
        }
        int i2 = Feature.SupportNonPublicField.mask;
        if (fieldDeserializer != null || (!jSONLexer.isEnabled(i2) && (i2 & this.beanInfo.parserFeatures) == 0)) {
            r17 = 1;
        } else {
            if (this.extraFieldDeserializers == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(1, 0.75f, 1);
                for (Class<?> superclass = this.clazz; superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                    for (Field field : superclass.getDeclaredFields()) {
                        String name = field.getName();
                        if (getFieldDeserializer(name) == null) {
                            int modifiers = field.getModifiers();
                            if ((modifiers & 16) == 0 && (modifiers & 8) == 0) {
                                concurrentHashMap.put(name, field);
                            }
                        }
                    }
                }
                this.extraFieldDeserializers = concurrentHashMap;
            }
            Object obj2 = this.extraFieldDeserializers.get(str);
            if (obj2 == null) {
                r17 = 1;
            } else if (obj2 instanceof FieldDeserializer) {
                fieldDeserializer = (FieldDeserializer) obj2;
                r17 = 1;
            } else {
                Field field2 = (Field) obj2;
                field2.setAccessible(true);
                r17 = 1;
                DefaultFieldDeserializer defaultFieldDeserializer = new DefaultFieldDeserializer(defaultJSONParser.getConfig(), this.clazz, new FieldInfo(str, field2.getDeclaringClass(), field2.getType(), field2.getGenericType(), field2, 0, 0, 0));
                this.extraFieldDeserializers.put(str, defaultFieldDeserializer);
                fieldDeserializer = defaultFieldDeserializer;
            }
        }
        if (fieldDeserializer == null) {
            if (!jSONLexer.isEnabled(Feature.IgnoreNotMatch)) {
                throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + str);
            }
            int i3 = 0;
            int i4 = -1;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i3 >= fieldDeserializerArr.length) {
                    break;
                }
                FieldDeserializer fieldDeserializer2 = fieldDeserializerArr[i3];
                FieldInfo fieldInfo = fieldDeserializer2.fieldInfo;
                if (fieldInfo.unwrapped && (fieldDeserializer2 instanceof DefaultFieldDeserializer)) {
                    if (fieldInfo.field != null) {
                        DefaultFieldDeserializer defaultFieldDeserializer2 = (DefaultFieldDeserializer) fieldDeserializer2;
                        ObjectDeserializer fieldValueDeserilizer = defaultFieldDeserializer2.getFieldValueDeserilizer(defaultJSONParser.getConfig());
                        if (fieldValueDeserilizer instanceof JavaBeanDeserializer) {
                            FieldDeserializer fieldDeserializer3 = ((JavaBeanDeserializer) fieldValueDeserilizer).getFieldDeserializer(str);
                            if (fieldDeserializer3 != null) {
                                try {
                                    Object objCreateInstance = fieldInfo.field.get(obj);
                                    if (objCreateInstance == null) {
                                        objCreateInstance = ((JavaBeanDeserializer) fieldValueDeserilizer).createInstance(defaultJSONParser, fieldInfo.fieldType);
                                        fieldDeserializer2.setValue(obj, objCreateInstance);
                                    }
                                    jSONLexer.nextTokenWithColon(defaultFieldDeserializer2.getFastMatchToken());
                                    fieldDeserializer3.parseField(defaultJSONParser, objCreateInstance, type, map);
                                    i4 = i3;
                                } catch (Exception e) {
                                    throw new JSONException("parse unwrapped field error.", e);
                                }
                            } else {
                                continue;
                            }
                        } else if (fieldValueDeserilizer instanceof MapDeserializer) {
                            MapDeserializer mapDeserializer = (MapDeserializer) fieldValueDeserilizer;
                            try {
                                Map<Object, Object> mapCreateMap = (Map) fieldInfo.field.get(obj);
                                if (mapCreateMap == null) {
                                    mapCreateMap = mapDeserializer.createMap(fieldInfo.fieldType);
                                    fieldDeserializer2.setValue(obj, mapCreateMap);
                                }
                                jSONLexer.nextTokenWithColon();
                                mapCreateMap.put(str, defaultJSONParser.parse(str));
                                i4 = i3;
                            } catch (Exception e2) {
                                throw new JSONException("parse unwrapped field error.", e2);
                            }
                        } else {
                            continue;
                        }
                    } else if (fieldInfo.method.getParameterTypes().length == 2) {
                        jSONLexer.nextTokenWithColon();
                        Object obj3 = defaultJSONParser.parse(str);
                        try {
                            Method method = fieldInfo.method;
                            Object[] objArr = new Object[2];
                            objArr[0] = str;
                            objArr[r17] = obj3;
                            method.invoke(obj, objArr);
                            i4 = i3;
                        } catch (Exception e3) {
                            throw new JSONException("parse unwrapped field error.", e3);
                        }
                    } else {
                        continue;
                    }
                }
                i3++;
            }
            if (i4 == -1) {
                defaultJSONParser.parseExtra(obj, str);
                return false;
            }
            if (iArr != null) {
                int i5 = i4 / 32;
                iArr[i5] = iArr[i5] | (r17 << (i4 % 32));
            }
            return r17;
        }
        int i6 = 0;
        while (true) {
            FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
            if (i6 >= fieldDeserializerArr2.length) {
                i6 = -1;
                break;
            }
            if (fieldDeserializerArr2[i6] == fieldDeserializer) {
                break;
            }
            i6++;
        }
        if (i6 != -1 && iArr != null && str.startsWith("_") && isSetFlag(i6, iArr)) {
            defaultJSONParser.parseExtra(obj, str);
            return false;
        }
        jSONLexer.nextTokenWithColon(fieldDeserializer.getFastMatchToken());
        fieldDeserializer.parseField(defaultJSONParser, obj, type, map);
        if (iArr != null) {
            int i7 = i6 / 32;
            iArr[i7] = iArr[i7] | (r17 << (i6 % 32));
        }
        return r17;
    }

    public FieldDeserializer smartMatch(String str) {
        return smartMatch(str, null);
    }

    public FieldDeserializer smartMatch(String str, int[] iArr) {
        boolean zStartsWith;
        if (str == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(str, iArr);
        if (fieldDeserializer == null) {
            long jFnv1a_64_lower = TypeUtils.fnv1a_64_lower(str);
            int i = 0;
            if (this.smartMatchHashArray == null) {
                long[] jArr = new long[this.sortedFieldDeserializers.length];
                int i2 = 0;
                while (true) {
                    FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                    if (i2 >= fieldDeserializerArr.length) {
                        break;
                    }
                    jArr[i2] = TypeUtils.fnv1a_64_lower(fieldDeserializerArr[i2].fieldInfo.name);
                    i2++;
                }
                Arrays.sort(jArr);
                this.smartMatchHashArray = jArr;
            }
            int iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, jFnv1a_64_lower);
            if (iBinarySearch < 0) {
                zStartsWith = str.startsWith("is");
                if (zStartsWith) {
                    iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(str.substring(2)));
                }
            } else {
                zStartsWith = false;
            }
            if (iBinarySearch >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    short[] sArr = new short[this.smartMatchHashArray.length];
                    Arrays.fill(sArr, (short) -1);
                    while (true) {
                        FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                        if (i >= fieldDeserializerArr2.length) {
                            break;
                        }
                        int iBinarySearch2 = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(fieldDeserializerArr2[i].fieldInfo.name));
                        if (iBinarySearch2 >= 0) {
                            sArr[iBinarySearch2] = (short) i;
                        }
                        i++;
                    }
                    this.smartMatchHashArrayMapping = sArr;
                }
                short s = this.smartMatchHashArrayMapping[iBinarySearch];
                if (s != -1 && !isSetFlag(s, iArr)) {
                    fieldDeserializer = this.sortedFieldDeserializers[s];
                }
            }
            if (fieldDeserializer != null) {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
                    return null;
                }
                Class<?> cls = fieldInfo.fieldClass;
                if (zStartsWith && cls != Boolean.TYPE && cls != Boolean.class) {
                    return null;
                }
            }
        }
        return fieldDeserializer;
    }

    private Object createFactoryInstance(ParserConfig parserConfig, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, obj);
    }

    public Object createInstance(Map<String, Object> map, ParserConfig parserConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer num;
        Object objCast;
        float f;
        double d;
        if (this.beanInfo.creatorConstructor == null && this.beanInfo.factoryMethod == null) {
            Object objCreateInstance = createInstance((DefaultJSONParser) null, this.clazz);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                FieldDeserializer fieldDeserializerSmartMatch = smartMatch(key);
                if (fieldDeserializerSmartMatch != null) {
                    FieldInfo fieldInfo = fieldDeserializerSmartMatch.fieldInfo;
                    Field field = fieldDeserializerSmartMatch.fieldInfo.field;
                    Type type = fieldInfo.fieldType;
                    if (field != null) {
                        Class<?> type2 = field.getType();
                        if (type2 == Boolean.TYPE) {
                            if (value == Boolean.FALSE) {
                                field.setBoolean(objCreateInstance, false);
                            } else if (value == Boolean.TRUE) {
                                field.setBoolean(objCreateInstance, true);
                            }
                        } else if (type2 == Integer.TYPE) {
                            if (value instanceof Number) {
                                field.setInt(objCreateInstance, ((Number) value).intValue());
                            }
                        } else if (type2 == Long.TYPE) {
                            if (value instanceof Number) {
                                field.setLong(objCreateInstance, ((Number) value).longValue());
                            }
                        } else if (type2 == Float.TYPE) {
                            if (value instanceof Number) {
                                field.setFloat(objCreateInstance, ((Number) value).floatValue());
                            } else if (value instanceof String) {
                                String str = (String) value;
                                if (str.length() <= 10) {
                                    f = TypeUtils.parseFloat(str);
                                } else {
                                    f = Float.parseFloat(str);
                                }
                                field.setFloat(objCreateInstance, f);
                            }
                        } else if (type2 == Double.TYPE) {
                            if (value instanceof Number) {
                                field.setDouble(objCreateInstance, ((Number) value).doubleValue());
                            } else if (value instanceof String) {
                                String str2 = (String) value;
                                if (str2.length() <= 10) {
                                    d = TypeUtils.parseDouble(str2);
                                } else {
                                    d = Double.parseDouble(str2);
                                }
                                field.setDouble(objCreateInstance, d);
                            }
                        } else if (value != null && type == value.getClass()) {
                            field.set(objCreateInstance, value);
                        }
                    }
                    String str3 = fieldInfo.format;
                    if (str3 != null && type == Date.class) {
                        objCast = TypeUtils.castToDate(value, str3);
                    } else if (type instanceof ParameterizedType) {
                        objCast = TypeUtils.cast(value, (ParameterizedType) type, parserConfig);
                    } else {
                        objCast = TypeUtils.cast(value, type, parserConfig);
                    }
                    fieldDeserializerSmartMatch.setValue(objCreateInstance, objCast);
                }
            }
            if (this.beanInfo.buildMethod == null) {
                return objCreateInstance;
            }
            try {
                return this.beanInfo.buildMethod.invoke(objCreateInstance, null);
            } catch (Exception e) {
                throw new JSONException("build object error", e);
            }
        }
        FieldInfo[] fieldInfoArr = this.beanInfo.fields;
        int length = fieldInfoArr.length;
        Object[] objArr = new Object[length];
        HashMap map2 = null;
        for (int i = 0; i < length; i++) {
            FieldInfo fieldInfo2 = fieldInfoArr[i];
            Object objValueOf = map.get(fieldInfo2.name);
            if (objValueOf == null) {
                Class<?> cls = fieldInfo2.fieldClass;
                if (cls == Integer.TYPE) {
                    objValueOf = 0;
                } else if (cls == Long.TYPE) {
                    objValueOf = 0L;
                } else if (cls == Short.TYPE) {
                    objValueOf = (short) 0;
                } else if (cls == Byte.TYPE) {
                    objValueOf = (byte) 0;
                } else if (cls == Float.TYPE) {
                    objValueOf = Float.valueOf(0.0f);
                } else if (cls == Double.TYPE) {
                    objValueOf = Double.valueOf(AudioStats.AUDIO_AMPLITUDE_NONE);
                } else if (cls == Character.TYPE) {
                    objValueOf = '0';
                } else if (cls == Boolean.TYPE) {
                    objValueOf = false;
                }
                if (map2 == null) {
                    map2 = new HashMap();
                }
                map2.put(fieldInfo2.name, Integer.valueOf(i));
            }
            objArr[i] = objValueOf;
        }
        if (map2 != null) {
            for (Map.Entry<String, Object> entry2 : map.entrySet()) {
                String key2 = entry2.getKey();
                Object value2 = entry2.getValue();
                FieldDeserializer fieldDeserializerSmartMatch2 = smartMatch(key2);
                if (fieldDeserializerSmartMatch2 != null && (num = (Integer) map2.get(fieldDeserializerSmartMatch2.fieldInfo.name)) != null) {
                    objArr[num.intValue()] = value2;
                }
            }
        }
        if (this.beanInfo.creatorConstructor != null) {
            if (this.beanInfo.f5kotlin) {
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (objArr[i2] != null || this.beanInfo.fields == null || i2 >= this.beanInfo.fields.length) {
                            i2++;
                        } else if (this.beanInfo.fields[i2].fieldClass == String.class && this.beanInfo.kotlinDefaultConstructor != null) {
                            try {
                                Object objNewInstance = this.beanInfo.kotlinDefaultConstructor.newInstance(null);
                                for (int i3 = 0; i3 < length; i3++) {
                                    Object obj = objArr[i3];
                                    if (obj != null && this.beanInfo.fields != null && i3 < this.beanInfo.fields.length) {
                                        this.beanInfo.fields[i3].set(objNewInstance, obj);
                                    }
                                }
                                return objNewInstance;
                            } catch (Exception e2) {
                                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e2);
                            }
                        }
                    }
                }
            }
            try {
                return this.beanInfo.creatorConstructor.newInstance(objArr);
            } catch (Exception e3) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e3);
            }
        }
        if (this.beanInfo.factoryMethod == null) {
            return null;
        }
        try {
            return this.beanInfo.factoryMethod.invoke(null, objArr);
        } catch (Exception e4) {
            throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e4);
        }
    }

    public Type getFieldType(int i) {
        return this.sortedFieldDeserializers[i].fieldInfo.fieldType;
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i) {
        return parseRest(defaultJSONParser, type, obj, obj2, i, new int[0]);
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i, int[] iArr) {
        return deserialze(defaultJSONParser, type, obj, obj2, i, iArr);
    }

    protected JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        if (javaBeanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> cls : javaBeanInfo.jsonType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        int i = jSONLexerBase.token();
        if (i == 8) {
            jSONLexerBase.nextToken(16);
            jSONLexerBase.token();
            return;
        }
        if (i != 14) {
            defaultJSONParser.throwException(i);
        }
        if (jSONLexerBase.getCurrent() == '[') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(14);
        } else {
            jSONLexerBase.nextToken(14);
        }
        if (jSONLexerBase.token() == 15) {
            jSONLexerBase.nextToken();
            return;
        }
        int i2 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, Integer.valueOf(i2)));
            i2++;
            if (jSONLexerBase.token() != 16) {
                break;
            }
            if (jSONLexerBase.getCurrent() == '[') {
                jSONLexerBase.next();
                jSONLexerBase.setToken(14);
            } else {
                jSONLexerBase.nextToken(14);
            }
        }
        int i3 = jSONLexerBase.token();
        if (i3 != 15) {
            defaultJSONParser.throwException(i3);
        }
        if (jSONLexerBase.getCurrent() == ',') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(16);
        } else {
            jSONLexerBase.nextToken(16);
        }
    }
}

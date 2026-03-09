package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        Class<?>[] clsArr = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class};
        for (int i = 0; i < 17; i++) {
            primitiveClasses.add(clsArr[i]);
        }
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat = simpleDateFormat;
            simpleDateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(str, new JSONScanner(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i) {
        this(str, new JSONScanner(str, i), parserConfig);
    }

    public DefaultJSONParser(char[] cArr, int i, ParserConfig parserConfig, int i2) {
        this(cArr, new JSONScanner(cArr, i, i2), parserConfig);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this((Object) null, jSONLexer, parserConfig);
    }

    public DefaultJSONParser(Object obj, JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.autoTypeAccept = null;
        this.lexer = jSONLexer;
        this.input = obj;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char current = jSONLexer.getCurrent();
        if (current == '{') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 12;
        } else if (current == '[') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getInput() {
        Object obj = this.input;
        if (obj instanceof char[]) {
            return new String((char[]) this.input);
        }
        return obj.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0275, code lost:
    
        r4.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0280, code lost:
    
        if (r4.token() != 13) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0282, code lost:
    
        r4.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0285, code lost:
    
        r0 = r16.config.getDeserializer(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x028d, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x028f, code lost:
    
        r0 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r0;
        r2 = r0.createInstance(r16, r7);
        r3 = r9.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x02a1, code lost:
    
        if (r3.hasNext() == false) goto L407;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x02a3, code lost:
    
        r4 = (java.util.Map.Entry) r3.next();
        r8 = r4.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x02af, code lost:
    
        if ((r8 instanceof java.lang.String) == false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02b1, code lost:
    
        r8 = r0.getFieldDeserializer((java.lang.String) r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02b7, code lost:
    
        if (r8 == null) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x02b9, code lost:
    
        r8.setValue(r2, r4.getValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02c1, code lost:
    
        r2 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02c2, code lost:
    
        if (r2 != null) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02c6, code lost:
    
        if (r7 != java.lang.Cloneable.class) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02c8, code lost:
    
        r2 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02d4, code lost:
    
        if ("java.util.Collections$EmptyMap".equals(r6) == false) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02d6, code lost:
    
        r2 = java.util.Collections.EMPTY_MAP;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02df, code lost:
    
        if ("java.util.Collections$UnmodifiableMap".equals(r6) == false) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02e1, code lost:
    
        r2 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02eb, code lost:
    
        r2 = r7.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02f2, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02f3, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02fb, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x02fc, code lost:
    
        setResolveStatus(2);
        r3 = r16.context;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0302, code lost:
    
        if (r3 == null) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0304, code lost:
    
        if (r18 == null) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0308, code lost:
    
        if ((r18 instanceof java.lang.Integer) != false) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x030e, code lost:
    
        if ((r3.fieldName instanceof java.lang.Integer) != false) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0310, code lost:
    
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0317, code lost:
    
        if (r17.size() <= 0) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0319, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r17, (java.lang.Class<java.lang.Object>) r7, r16.config);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0325, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0326, code lost:
    
        r0 = r16.config.getDeserializer(r7);
        r3 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0336, code lost:
    
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r3) == false) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x033a, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x033e, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0340, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0347, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0349, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0354, code lost:
    
        return r0.deserialze(r16, r7, r18);
     */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0205 A[Catch: all -> 0x067c, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0446 A[Catch: all -> 0x067c, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0472 A[Catch: all -> 0x067c, TRY_ENTER, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x049b  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x04ce A[Catch: all -> 0x067c, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:335:0x05c9  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x05cd A[Catch: all -> 0x067c, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:342:0x05d9 A[Catch: all -> 0x067c, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:345:0x05e5 A[Catch: all -> 0x067c, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x05fa A[Catch: all -> 0x067c, TRY_ENTER, TryCatch #1 {all -> 0x067c, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00ac, B:109:0x0205, B:110:0x020b, B:112:0x0216, B:114:0x021e, B:118:0x0234, B:120:0x0242, B:133:0x026f, B:134:0x0275, B:136:0x0282, B:137:0x0285, B:139:0x028f, B:140:0x029d, B:142:0x02a3, B:144:0x02b1, B:146:0x02b9, B:151:0x02c8, B:152:0x02ce, B:154:0x02d6, B:155:0x02d9, B:157:0x02e1, B:158:0x02eb, B:162:0x02f4, B:163:0x02fb, B:164:0x02fc, B:167:0x0306, B:169:0x030a, B:171:0x0310, B:172:0x0313, B:174:0x0319, B:177:0x0326, B:183:0x0340, B:187:0x034d, B:184:0x0345, B:186:0x0349, B:122:0x0249, B:124:0x024f, B:129:0x025c, B:130:0x025f, B:194:0x035c, B:196:0x0364, B:198:0x036e, B:200:0x037f, B:202:0x038a, B:204:0x0392, B:206:0x0396, B:208:0x039c, B:211:0x03a1, B:213:0x03a5, B:233:0x03f7, B:235:0x03ff, B:238:0x0408, B:239:0x0423, B:215:0x03ac, B:217:0x03b4, B:219:0x03b8, B:220:0x03bb, B:221:0x03c7, B:224:0x03d0, B:226:0x03d4, B:227:0x03d7, B:229:0x03db, B:230:0x03df, B:231:0x03eb, B:240:0x0424, B:241:0x0442, B:244:0x0446, B:246:0x044a, B:248:0x044e, B:250:0x0454, B:251:0x0457, B:255:0x045f, B:261:0x0472, B:263:0x0481, B:265:0x048c, B:266:0x0494, B:267:0x0497, B:279:0x04c3, B:281:0x04ce, B:284:0x04d9, B:287:0x04e9, B:288:0x0507, B:274:0x04a7, B:276:0x04b1, B:278:0x04c0, B:277:0x04b6, B:291:0x050c, B:293:0x0516, B:295:0x051e, B:296:0x0521, B:298:0x052c, B:299:0x0530, B:301:0x053b, B:304:0x0542, B:307:0x054e, B:308:0x0553, B:311:0x0558, B:313:0x055d, B:317:0x0568, B:319:0x0570, B:321:0x0585, B:325:0x05a4, B:327:0x05aa, B:330:0x05b0, B:332:0x05b6, B:334:0x05be, B:337:0x05cd, B:340:0x05d5, B:342:0x05d9, B:343:0x05e0, B:345:0x05e5, B:346:0x05e8, B:348:0x05f0, B:351:0x05fa, B:354:0x0604, B:355:0x0609, B:356:0x060e, B:357:0x0629, B:322:0x0590, B:323:0x0597, B:358:0x062a, B:360:0x063c, B:363:0x0643, B:366:0x0651, B:367:0x066f, B:39:0x00be, B:40:0x00dc, B:42:0x00df, B:44:0x00ea, B:46:0x00ee, B:48:0x00f2, B:50:0x00f8, B:51:0x00fb, B:58:0x010a, B:60:0x0112, B:63:0x0122, B:64:0x013a, B:65:0x013b, B:66:0x0140, B:77:0x0155, B:78:0x015b, B:80:0x0162, B:82:0x016b, B:84:0x0173, B:86:0x0178, B:89:0x0180, B:90:0x0198, B:81:0x0167, B:91:0x0199, B:92:0x01b1, B:98:0x01bb, B:100:0x01c3, B:103:0x01d4, B:104:0x01f4, B:105:0x01f5, B:106:0x01fa, B:107:0x01fb, B:368:0x0670, B:369:0x0675, B:370:0x0676, B:371:0x067b), top: B:377:0x0070, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:394:0x04d7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x05f0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object parseObject(java.util.Map r17, java.lang.Object r18) {
        /*
            Method dump skipped, instruction units count: 1665
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i = this.lexer.token();
        if (i == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (i == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() == JavaBeanDeserializer.class) {
                return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
            }
            return (T) deserializer.deserialze(this, type, obj);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer deserializer;
        int i = this.lexer.token();
        if (i == 21 || i == 22) {
            this.lexer.nextToken();
            i = this.lexer.token();
        }
        if (i != 14) {
            throw new JSONException("expect '[', but " + JSONToken.name(i) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            deserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(deserializer.getFastMatchToken());
        }
        ParseContext parseContext = this.context;
        setContext(collection, obj);
        int i2 = 0;
        while (true) {
            try {
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (this.lexer.token() == 16) {
                        this.lexer.nextToken();
                    }
                }
                if (this.lexer.token() != 15) {
                    Object objDeserialze = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            objDeserialze = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj2 = parse();
                            if (obj2 != null) {
                                objDeserialze = obj2.toString();
                            }
                        }
                        collection.add(objDeserialze);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                        } else {
                            objDeserialze = deserializer.deserialze(this, type, Integer.valueOf(i2));
                        }
                        collection.add(objDeserialze);
                        checkListResolve(collection);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i2++;
                } else {
                    setContext(parseContext);
                    this.lexer.nextToken(16);
                    return;
                }
            } catch (Throwable th) {
                setContext(parseContext);
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object objCast;
        Class<?> componentType;
        boolean zIsArray;
        int i = 8;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i2 = 14;
        if (this.lexer.token() != 14) {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
        Object[] objArr = new Object[typeArr.length];
        if (typeArr.length == 0) {
            this.lexer.nextToken(15);
            if (this.lexer.token() != 15) {
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(16);
            return new Object[0];
        }
        this.lexer.nextToken(2);
        int i3 = 0;
        while (i3 < typeArr.length) {
            if (this.lexer.token() == i) {
                this.lexer.nextToken(16);
                objCast = null;
            } else {
                Type type = typeArr[i3];
                if (type == Integer.TYPE || type == Integer.class) {
                    if (this.lexer.token() == 2) {
                        objCast = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                    } else {
                        objCast = TypeUtils.cast(parse(), type, this.config);
                    }
                } else if (type == String.class) {
                    if (this.lexer.token() == 4) {
                        objCast = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        objCast = TypeUtils.cast(parse(), type, this.config);
                    }
                } else {
                    if (i3 == typeArr.length - 1 && (type instanceof Class)) {
                        Class cls = (Class) type;
                        zIsArray = cls.isArray();
                        componentType = cls.getComponentType();
                    } else {
                        componentType = null;
                        zIsArray = false;
                    }
                    if (zIsArray && this.lexer.token() != i2) {
                        ArrayList arrayList = new ArrayList();
                        ObjectDeserializer deserializer = this.config.getDeserializer(componentType);
                        int fastMatchToken = deserializer.getFastMatchToken();
                        if (this.lexer.token() != 15) {
                            while (true) {
                                arrayList.add(deserializer.deserialze(this, type, null));
                                if (this.lexer.token() != 16) {
                                    break;
                                }
                                this.lexer.nextToken(fastMatchToken);
                            }
                            if (this.lexer.token() != 15) {
                                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                            }
                        }
                        objCast = TypeUtils.cast(arrayList, type, this.config);
                    } else {
                        objCast = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i3));
                    }
                }
            }
            objArr[i3] = objCast;
            if (this.lexer.token() == 15) {
                break;
            }
            if (this.lexer.token() != 16) {
                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
            }
            if (i3 == typeArr.length - 1) {
                this.lexer.nextToken(15);
            } else {
                this.lexer.nextToken(2);
            }
            i3++;
            i = 8;
            i2 = 14;
        }
        if (this.lexer.token() != 15) {
            throw new JSONException("syntax error");
        }
        this.lexer.nextToken(16);
        return objArr;
    }

    public void parseObject(Object obj) {
        Object objDeserialze;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String strScanSymbol = this.lexer.scanSymbol(this.symbolTable);
            if (strScanSymbol == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(strScanSymbol) : null;
            if (fieldDeserializer == null) {
                if (!this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + strScanSymbol);
                }
                this.lexer.nextTokenWithColon();
                parse();
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken();
                    return;
                }
            } else {
                Class<?> cls2 = fieldDeserializer.fieldInfo.fieldClass;
                Type type = fieldDeserializer.fieldInfo.fieldType;
                if (cls2 == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else if (cls2 == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    objDeserialze = StringCodec.deserialze(this);
                } else if (cls2 == Long.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = LongCodec.instance.deserialze(this, type, null);
                } else {
                    ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                    this.lexer.nextTokenWithColon(deserializer2.getFastMatchToken());
                    objDeserialze = deserializer2.deserialze(this, type, null);
                }
                fieldDeserializer.setValue(obj, objDeserialze);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type " + type);
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class<?>) type2, (Collection) arrayList);
            return arrayList;
        }
        if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (Object.class.equals(type3)) {
                if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                }
                throw new JSONException("not support type : " + type);
            }
            ArrayList arrayList2 = new ArrayList();
            parseArray((Class<?>) type3, (Collection) arrayList2);
            return arrayList2;
        }
        if (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }
            Type type4 = bounds[0];
            if (type4 instanceof Class) {
                ArrayList arrayList3 = new ArrayList();
                parseArray((Class<?>) type4, (Collection) arrayList3);
                return arrayList3;
            }
        }
        if (type2 instanceof ParameterizedType) {
            ArrayList arrayList4 = new ArrayList();
            parseArray((ParameterizedType) type2, arrayList4);
            return arrayList4;
        }
        throw new JSONException("TODO : " + type);
    }

    public void acceptType(String str) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (str.equals(jSONLexer.stringVal())) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken();
                return;
            }
            return;
        }
        throw new JSONException("type not match error");
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int i) {
        this.resolveStatus = i;
    }

    public Object getObject(String str) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (str.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public void checkListResolve(Collection collection) {
        if (this.resolveStatus == 1) {
            if (collection instanceof List) {
                int size = collection.size() - 1;
                ResolveTask lastResolveTask = getLastResolveTask();
                lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, size);
                lastResolveTask.ownerContext = this.context;
                setResolveStatus(0);
                return;
            }
            ResolveTask lastResolveTask2 = getLastResolveTask();
            lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(collection);
            lastResolveTask2.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map map, Object obj) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        Object object = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        if (object == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) object);
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    public final void parseArray(Collection collection, Object obj) {
        Object object;
        Number numberDecimalValue;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() != 14) {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
        jSONLexer.nextToken(4);
        ParseContext parseContext = this.context;
        setContext(collection, obj);
        int i = 0;
        while (true) {
            try {
                if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (jSONLexer.token() == 16) {
                        jSONLexer.nextToken();
                    }
                }
                int i2 = jSONLexer.token();
                if (i2 == 2) {
                    Number numberIntegerValue = jSONLexer.integerValue();
                    jSONLexer.nextToken(16);
                    object = numberIntegerValue;
                } else if (i2 == 3) {
                    if (jSONLexer.isEnabled(Feature.UseBigDecimal)) {
                        numberDecimalValue = jSONLexer.decimalValue(true);
                    } else {
                        numberDecimalValue = jSONLexer.decimalValue(false);
                    }
                    object = numberDecimalValue;
                    jSONLexer.nextToken(16);
                } else if (i2 == 4) {
                    String strStringVal = jSONLexer.stringVal();
                    jSONLexer.nextToken(16);
                    object = strStringVal;
                    if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                        JSONScanner jSONScanner = new JSONScanner(strStringVal);
                        Object time = strStringVal;
                        if (jSONScanner.scanISO8601DateIfMatch()) {
                            time = jSONScanner.getCalendar().getTime();
                        }
                        jSONScanner.close();
                        object = time;
                    }
                } else if (i2 == 6) {
                    Boolean bool = Boolean.TRUE;
                    jSONLexer.nextToken(16);
                    object = bool;
                } else if (i2 != 7) {
                    object = null;
                    object = null;
                    if (i2 == 8) {
                        jSONLexer.nextToken(4);
                    } else if (i2 == 12) {
                        object = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i));
                    } else {
                        if (i2 == 20) {
                            throw new JSONException("unclosed jsonArray");
                        }
                        if (i2 == 23) {
                            jSONLexer.nextToken(4);
                        } else if (i2 == 14) {
                            JSONArray jSONArray = new JSONArray();
                            parseArray(jSONArray, Integer.valueOf(i));
                            object = jSONArray;
                            if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                object = jSONArray.toArray();
                            }
                        } else {
                            if (i2 == 15) {
                                jSONLexer.nextToken(16);
                                return;
                            }
                            object = parse();
                        }
                    }
                } else {
                    Boolean bool2 = Boolean.FALSE;
                    jSONLexer.nextToken(16);
                    object = bool2;
                }
                collection.add(object);
                checkListResolve(collection);
                if (jSONLexer.token() == 16) {
                    jSONLexer.nextToken(4);
                }
                i++;
            } finally {
                setContext(parseContext);
            }
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(r0.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = parseContext;
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        int i = this.contextArrayIndex;
        if (i <= 0) {
            return;
        }
        int i2 = i - 1;
        this.contextArrayIndex = i2;
        this.contextArray[i2] = null;
    }

    public ParseContext setContext(Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, obj, obj2);
    }

    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        ParseContext parseContext2 = new ParseContext(parseContext, obj, obj2);
        this.context = parseContext2;
        addContext(parseContext2);
        return this.context;
    }

    private void addContext(ParseContext parseContext) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= parseContextArr.length) {
            ParseContext[] parseContextArr2 = new ParseContext[(parseContextArr.length * 3) / 2];
            System.arraycopy(parseContextArr, 0, parseContextArr2, 0, parseContextArr.length);
            this.contextArray = parseContextArr2;
        }
        this.contextArray[i] = parseContext;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parseKey() {
        if (this.lexer.token() == 18) {
            String strStringVal = this.lexer.stringVal();
            this.lexer.nextToken(16);
            return strStringVal;
        }
        return parse(null);
    }

    public Object parse(Object obj) {
        JSONLexer jSONLexer = this.lexer;
        int i = jSONLexer.token();
        if (i == 2) {
            Number numberIntegerValue = jSONLexer.integerValue();
            jSONLexer.nextToken();
            return numberIntegerValue;
        }
        if (i == 3) {
            Number numberDecimalValue = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
            jSONLexer.nextToken();
            return numberDecimalValue;
        }
        if (i == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            if (!jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                return strStringVal;
            }
            JSONScanner jSONScanner = new JSONScanner(strStringVal);
            try {
                return jSONScanner.scanISO8601DateIfMatch() ? jSONScanner.getCalendar().getTime() : strStringVal;
            } finally {
                jSONScanner.close();
            }
        }
        if (i == 12) {
            return parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), obj);
        }
        if (i == 14) {
            JSONArray jSONArray = new JSONArray();
            parseArray(jSONArray, obj);
            return jSONLexer.isEnabled(Feature.UseObjectArray) ? jSONArray.toArray() : jSONArray;
        }
        if (i == 18) {
            if ("NaN".equals(jSONLexer.stringVal())) {
                jSONLexer.nextToken();
                return null;
            }
            throw new JSONException("syntax error, " + jSONLexer.info());
        }
        if (i != 26) {
            switch (i) {
                case 6:
                    jSONLexer.nextToken();
                    return Boolean.TRUE;
                case 7:
                    jSONLexer.nextToken();
                    return Boolean.FALSE;
                case 8:
                    jSONLexer.nextToken();
                    return null;
                case 9:
                    jSONLexer.nextToken(18);
                    if (jSONLexer.token() != 18) {
                        throw new JSONException("syntax error");
                    }
                    jSONLexer.nextToken(10);
                    accept(10);
                    long jLongValue = jSONLexer.integerValue().longValue();
                    accept(2);
                    accept(11);
                    return new Date(jLongValue);
                default:
                    switch (i) {
                        case 20:
                            if (jSONLexer.isBlankInput()) {
                                return null;
                            }
                            throw new JSONException("unterminated json string, " + jSONLexer.info());
                        case 21:
                            jSONLexer.nextToken();
                            HashSet hashSet = new HashSet();
                            parseArray(hashSet, obj);
                            return hashSet;
                        case 22:
                            jSONLexer.nextToken();
                            TreeSet treeSet = new TreeSet();
                            parseArray(treeSet, obj);
                            return treeSet;
                        case 23:
                            jSONLexer.nextToken();
                            return null;
                        default:
                            throw new JSONException("syntax error, " + jSONLexer.info());
                    }
            }
        }
        byte[] bArrBytesValue = jSONLexer.bytesValue();
        jSONLexer.nextToken();
        return bArrBytesValue;
    }

    public void config(Feature feature, boolean z) {
        this.lexer.config(feature, z);
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int i) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public final void accept(int i, int i2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken(i2);
        } else {
            throwException(i);
        }
    }

    public void throwException(int i) {
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        } finally {
            jSONLexer.close();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object resolveReference(java.lang.String r5) {
        /*
            r4 = this;
            com.alibaba.fastjson.parser.ParseContext[] r0 = r4.contextArray
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            r0 = 0
        L7:
            com.alibaba.fastjson.parser.ParseContext[] r2 = r4.contextArray
            int r3 = r2.length
            if (r0 >= r3) goto L22
            int r3 = r4.contextArrayIndex
            if (r0 >= r3) goto L22
            r2 = r2[r0]
            java.lang.String r3 = r2.toString()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L1f
            java.lang.Object r5 = r2.object
            return r5
        L1f:
            int r0 = r0 + 1
            goto L7
        L22:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.resolveReference(java.lang.String):java.lang.Object");
    }

    public void handleResovleTask(Object obj) {
        Object objEval;
        List<ResolveTask> list = this.resolveTaskList;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ResolveTask resolveTask = this.resolveTaskList.get(i);
            String str = resolveTask.referenceValue;
            Object obj2 = resolveTask.ownerContext != null ? resolveTask.ownerContext.object : null;
            if (str.startsWith("$")) {
                objEval = getObject(str);
                if (objEval == null) {
                    try {
                        objEval = JSONPath.eval(obj, str);
                    } catch (JSONPathException unused) {
                    }
                }
            } else {
                objEval = resolveTask.context.object;
            }
            FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
            if (fieldDeserializer != null) {
                if (objEval != null && objEval.getClass() == JSONObject.class && fieldDeserializer.fieldInfo != null && !Map.class.isAssignableFrom(fieldDeserializer.fieldInfo.fieldClass)) {
                    objEval = JSONPath.eval(this.contextArray[0].object, str);
                }
                fieldDeserializer.setValue(obj2, objEval);
            }
        }
    }

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    public void parseExtra(Object obj, String str) {
        Object object;
        this.lexer.nextTokenWithColon();
        List<ExtraTypeProvider> list = this.extraTypeProviders;
        Type extraType = null;
        if (list != null) {
            Iterator<ExtraTypeProvider> it = list.iterator();
            while (it.hasNext()) {
                extraType = it.next().getExtraType(obj, str);
            }
        }
        if (extraType == null) {
            object = parse();
        } else {
            object = parseObject(extraType);
        }
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, object);
            return;
        }
        List<ExtraProcessor> list2 = this.extraProcessors;
        if (list2 != null) {
            Iterator<ExtraProcessor> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().processExtra(obj, str, object);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x0235, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable r11, java.lang.Object r12) {
        /*
            Method dump skipped, instruction units count: 612
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable, java.lang.Object):java.lang.Object");
    }
}

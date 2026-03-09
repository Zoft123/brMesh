package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public class MapSerializer extends SerializeFilterable implements ObjectSerializer {
    public static MapSerializer instance = new MapSerializer();
    private static final int NON_STRINGKEY_AS_STRING = SerializerFeature.of(new SerializerFeature[]{SerializerFeature.BrowserCompatible, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.BrowserSecure});

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0022 A[PHI: r3
  0x0022: PHI (r3v31 java.util.Map<java.lang.String, java.lang.Object>) = 
  (r3v2 java.util.Map<java.lang.String, java.lang.Object>)
  (r3v2 java.util.Map<java.lang.String, java.lang.Object>)
  (r3v2 java.util.Map<java.lang.String, java.lang.Object>)
  (r3v1 java.util.Map<java.lang.String, java.lang.Object>)
 binds: [B:16:0x0030, B:18:0x0034, B:220:0x0022, B:9:0x001f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0211 A[Catch: all -> 0x02e7, TryCatch #2 {all -> 0x02e7, blocks: (B:28:0x0052, B:29:0x0055, B:31:0x0061, B:40:0x007c, B:42:0x008d, B:43:0x0099, B:45:0x009f, B:47:0x00b1, B:50:0x00b9, B:53:0x00be, B:55:0x00c8, B:57:0x00cc, B:60:0x00d7, B:63:0x00e3, B:65:0x00e7, B:68:0x00ef, B:71:0x00f4, B:73:0x00fe, B:75:0x0102, B:78:0x010d, B:81:0x0117, B:83:0x011b, B:86:0x0123, B:89:0x0128, B:91:0x0132, B:93:0x0136, B:96:0x0141, B:99:0x014b, B:101:0x014f, B:104:0x0157, B:107:0x015c, B:109:0x0166, B:111:0x016a, B:114:0x0176, B:117:0x0181, B:119:0x0185, B:122:0x018d, B:125:0x0192, B:127:0x019c, B:129:0x01a0, B:130:0x01a9, B:131:0x01af, B:133:0x01b3, B:136:0x01bb, B:139:0x01c0, B:141:0x01ca, B:143:0x01ce, B:144:0x01d7, B:162:0x0211, B:165:0x0221, B:167:0x0227, B:169:0x022c, B:170:0x022f, B:172:0x0237, B:173:0x023a, B:184:0x0261, B:185:0x026d, B:187:0x0273, B:188:0x0278, B:194:0x028a, B:196:0x0295, B:175:0x0240, B:176:0x0243, B:178:0x024b, B:180:0x024f, B:182:0x025a, B:181:0x0257, B:160:0x020b, B:37:0x0075), top: B:225:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0227 A[Catch: all -> 0x02e7, TryCatch #2 {all -> 0x02e7, blocks: (B:28:0x0052, B:29:0x0055, B:31:0x0061, B:40:0x007c, B:42:0x008d, B:43:0x0099, B:45:0x009f, B:47:0x00b1, B:50:0x00b9, B:53:0x00be, B:55:0x00c8, B:57:0x00cc, B:60:0x00d7, B:63:0x00e3, B:65:0x00e7, B:68:0x00ef, B:71:0x00f4, B:73:0x00fe, B:75:0x0102, B:78:0x010d, B:81:0x0117, B:83:0x011b, B:86:0x0123, B:89:0x0128, B:91:0x0132, B:93:0x0136, B:96:0x0141, B:99:0x014b, B:101:0x014f, B:104:0x0157, B:107:0x015c, B:109:0x0166, B:111:0x016a, B:114:0x0176, B:117:0x0181, B:119:0x0185, B:122:0x018d, B:125:0x0192, B:127:0x019c, B:129:0x01a0, B:130:0x01a9, B:131:0x01af, B:133:0x01b3, B:136:0x01bb, B:139:0x01c0, B:141:0x01ca, B:143:0x01ce, B:144:0x01d7, B:162:0x0211, B:165:0x0221, B:167:0x0227, B:169:0x022c, B:170:0x022f, B:172:0x0237, B:173:0x023a, B:184:0x0261, B:185:0x026d, B:187:0x0273, B:188:0x0278, B:194:0x028a, B:196:0x0295, B:175:0x0240, B:176:0x0243, B:178:0x024b, B:180:0x024f, B:182:0x025a, B:181:0x0257, B:160:0x020b, B:37:0x0075), top: B:225:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0221 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x026d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0261 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:247:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009f A[Catch: all -> 0x02e7, TryCatch #2 {all -> 0x02e7, blocks: (B:28:0x0052, B:29:0x0055, B:31:0x0061, B:40:0x007c, B:42:0x008d, B:43:0x0099, B:45:0x009f, B:47:0x00b1, B:50:0x00b9, B:53:0x00be, B:55:0x00c8, B:57:0x00cc, B:60:0x00d7, B:63:0x00e3, B:65:0x00e7, B:68:0x00ef, B:71:0x00f4, B:73:0x00fe, B:75:0x0102, B:78:0x010d, B:81:0x0117, B:83:0x011b, B:86:0x0123, B:89:0x0128, B:91:0x0132, B:93:0x0136, B:96:0x0141, B:99:0x014b, B:101:0x014f, B:104:0x0157, B:107:0x015c, B:109:0x0166, B:111:0x016a, B:114:0x0176, B:117:0x0181, B:119:0x0185, B:122:0x018d, B:125:0x0192, B:127:0x019c, B:129:0x01a0, B:130:0x01a9, B:131:0x01af, B:133:0x01b3, B:136:0x01bb, B:139:0x01c0, B:141:0x01ca, B:143:0x01ce, B:144:0x01d7, B:162:0x0211, B:165:0x0221, B:167:0x0227, B:169:0x022c, B:170:0x022f, B:172:0x0237, B:173:0x023a, B:184:0x0261, B:185:0x026d, B:187:0x0273, B:188:0x0278, B:194:0x028a, B:196:0x0295, B:175:0x0240, B:176:0x0243, B:178:0x024b, B:180:0x024f, B:182:0x025a, B:181:0x0257, B:160:0x020b, B:37:0x0075), top: B:225:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00e3 A[Catch: all -> 0x02e7, TryCatch #2 {all -> 0x02e7, blocks: (B:28:0x0052, B:29:0x0055, B:31:0x0061, B:40:0x007c, B:42:0x008d, B:43:0x0099, B:45:0x009f, B:47:0x00b1, B:50:0x00b9, B:53:0x00be, B:55:0x00c8, B:57:0x00cc, B:60:0x00d7, B:63:0x00e3, B:65:0x00e7, B:68:0x00ef, B:71:0x00f4, B:73:0x00fe, B:75:0x0102, B:78:0x010d, B:81:0x0117, B:83:0x011b, B:86:0x0123, B:89:0x0128, B:91:0x0132, B:93:0x0136, B:96:0x0141, B:99:0x014b, B:101:0x014f, B:104:0x0157, B:107:0x015c, B:109:0x0166, B:111:0x016a, B:114:0x0176, B:117:0x0181, B:119:0x0185, B:122:0x018d, B:125:0x0192, B:127:0x019c, B:129:0x01a0, B:130:0x01a9, B:131:0x01af, B:133:0x01b3, B:136:0x01bb, B:139:0x01c0, B:141:0x01ca, B:143:0x01ce, B:144:0x01d7, B:162:0x0211, B:165:0x0221, B:167:0x0227, B:169:0x022c, B:170:0x022f, B:172:0x0237, B:173:0x023a, B:184:0x0261, B:185:0x026d, B:187:0x0273, B:188:0x0278, B:194:0x028a, B:196:0x0295, B:175:0x0240, B:176:0x0243, B:178:0x024b, B:180:0x024f, B:182:0x025a, B:181:0x0257, B:160:0x020b, B:37:0x0075), top: B:225:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0117 A[Catch: all -> 0x02e7, TryCatch #2 {all -> 0x02e7, blocks: (B:28:0x0052, B:29:0x0055, B:31:0x0061, B:40:0x007c, B:42:0x008d, B:43:0x0099, B:45:0x009f, B:47:0x00b1, B:50:0x00b9, B:53:0x00be, B:55:0x00c8, B:57:0x00cc, B:60:0x00d7, B:63:0x00e3, B:65:0x00e7, B:68:0x00ef, B:71:0x00f4, B:73:0x00fe, B:75:0x0102, B:78:0x010d, B:81:0x0117, B:83:0x011b, B:86:0x0123, B:89:0x0128, B:91:0x0132, B:93:0x0136, B:96:0x0141, B:99:0x014b, B:101:0x014f, B:104:0x0157, B:107:0x015c, B:109:0x0166, B:111:0x016a, B:114:0x0176, B:117:0x0181, B:119:0x0185, B:122:0x018d, B:125:0x0192, B:127:0x019c, B:129:0x01a0, B:130:0x01a9, B:131:0x01af, B:133:0x01b3, B:136:0x01bb, B:139:0x01c0, B:141:0x01ca, B:143:0x01ce, B:144:0x01d7, B:162:0x0211, B:165:0x0221, B:167:0x0227, B:169:0x022c, B:170:0x022f, B:172:0x0237, B:173:0x023a, B:184:0x0261, B:185:0x026d, B:187:0x0273, B:188:0x0278, B:194:0x028a, B:196:0x0295, B:175:0x0240, B:176:0x0243, B:178:0x024b, B:180:0x024f, B:182:0x025a, B:181:0x0257, B:160:0x020b, B:37:0x0075), top: B:225:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x014b A[Catch: all -> 0x02e7, TryCatch #2 {all -> 0x02e7, blocks: (B:28:0x0052, B:29:0x0055, B:31:0x0061, B:40:0x007c, B:42:0x008d, B:43:0x0099, B:45:0x009f, B:47:0x00b1, B:50:0x00b9, B:53:0x00be, B:55:0x00c8, B:57:0x00cc, B:60:0x00d7, B:63:0x00e3, B:65:0x00e7, B:68:0x00ef, B:71:0x00f4, B:73:0x00fe, B:75:0x0102, B:78:0x010d, B:81:0x0117, B:83:0x011b, B:86:0x0123, B:89:0x0128, B:91:0x0132, B:93:0x0136, B:96:0x0141, B:99:0x014b, B:101:0x014f, B:104:0x0157, B:107:0x015c, B:109:0x0166, B:111:0x016a, B:114:0x0176, B:117:0x0181, B:119:0x0185, B:122:0x018d, B:125:0x0192, B:127:0x019c, B:129:0x01a0, B:130:0x01a9, B:131:0x01af, B:133:0x01b3, B:136:0x01bb, B:139:0x01c0, B:141:0x01ca, B:143:0x01ce, B:144:0x01d7, B:162:0x0211, B:165:0x0221, B:167:0x0227, B:169:0x022c, B:170:0x022f, B:172:0x0237, B:173:0x023a, B:184:0x0261, B:185:0x026d, B:187:0x0273, B:188:0x0278, B:194:0x028a, B:196:0x0295, B:175:0x0240, B:176:0x0243, B:178:0x024b, B:180:0x024f, B:182:0x025a, B:181:0x0257, B:160:0x020b, B:37:0x0075), top: B:225:0x0052 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r19, java.lang.Object r20, java.lang.Object r21, java.lang.reflect.Type r22, int r23, boolean r24) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 747
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.MapSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }
}

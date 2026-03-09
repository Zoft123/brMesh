package org.bson;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bson.util.ClassMap;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class BSON {
    public static final byte ARRAY = 4;
    public static final byte BINARY = 5;
    public static final byte BOOLEAN = 8;
    public static final byte B_BINARY = 2;
    public static final byte B_FUNC = 1;
    public static final byte B_GENERAL = 0;
    public static final byte B_UUID = 3;
    public static final byte CODE = 13;
    public static final byte CODE_W_SCOPE = 15;
    public static final byte DATE = 9;
    public static final byte EOO = 0;
    private static final int FLAG_GLOBAL = 256;
    private static final int[] FLAG_LOOKUP;
    public static final byte MAXKEY = 127;
    public static final byte MINKEY = -1;
    public static final byte NULL = 10;
    public static final byte NUMBER = 1;
    public static final byte NUMBER_INT = 16;
    public static final byte NUMBER_LONG = 18;
    public static final byte OBJECT = 3;
    public static final byte OID = 7;
    public static final byte REF = 12;
    public static final byte REGEX = 11;
    public static final byte STRING = 2;
    public static final byte SYMBOL = 14;
    public static final byte TIMESTAMP = 17;
    public static final byte UNDEFINED = 6;
    private static volatile boolean decodeHooks = false;
    private static final ClassMap<List<Transformer>> decodingHooks;
    private static volatile boolean encodeHooks = false;
    private static final ClassMap<List<Transformer>> encodingHooks;

    static {
        int[] iArr = new int[65535];
        FLAG_LOOKUP = iArr;
        iArr[103] = 256;
        iArr[105] = 2;
        iArr[109] = 8;
        iArr[115] = 32;
        iArr[99] = 128;
        iArr[120] = 4;
        iArr[100] = 1;
        iArr[116] = 16;
        iArr[117] = 64;
        encodingHooks = new ClassMap<>();
        decodingHooks = new ClassMap<>();
    }

    public static boolean hasEncodeHooks() {
        return encodeHooks;
    }

    public static boolean hasDecodeHooks() {
        return decodeHooks;
    }

    public static void addEncodingHook(Class<?> cls, Transformer transformer) {
        encodeHooks = true;
        ClassMap<List<Transformer>> classMap = encodingHooks;
        List<Transformer> copyOnWriteArrayList = classMap.get(cls);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            classMap.put(cls, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.add(transformer);
    }

    public static void addDecodingHook(Class<?> cls, Transformer transformer) {
        decodeHooks = true;
        ClassMap<List<Transformer>> classMap = decodingHooks;
        List<Transformer> copyOnWriteArrayList = classMap.get(cls);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            classMap.put(cls, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.add(transformer);
    }

    public static Object applyEncodingHooks(Object obj) {
        List<Transformer> list;
        if (hasEncodeHooks() && obj != null) {
            ClassMap<List<Transformer>> classMap = encodingHooks;
            if (classMap.size() != 0 && (list = classMap.get(obj.getClass())) != null) {
                Iterator<Transformer> it = list.iterator();
                Object objTransform = obj;
                while (it.hasNext()) {
                    objTransform = it.next().transform(obj);
                }
                return objTransform;
            }
        }
        return obj;
    }

    public static Object applyDecodingHooks(Object obj) {
        List<Transformer> list;
        if (hasDecodeHooks() && obj != null) {
            ClassMap<List<Transformer>> classMap = decodingHooks;
            if (classMap.size() != 0 && (list = classMap.get(obj.getClass())) != null) {
                Iterator<Transformer> it = list.iterator();
                Object objTransform = obj;
                while (it.hasNext()) {
                    objTransform = it.next().transform(obj);
                }
                return objTransform;
            }
        }
        return obj;
    }

    public static List<Transformer> getEncodingHooks(Class<?> cls) {
        return encodingHooks.get(cls);
    }

    public static void clearEncodingHooks() {
        encodeHooks = false;
        encodingHooks.clear();
    }

    public static void removeEncodingHooks(Class<?> cls) {
        encodingHooks.remove(cls);
    }

    public static void removeEncodingHook(Class<?> cls, Transformer transformer) {
        getEncodingHooks(cls).remove(transformer);
    }

    public static List<Transformer> getDecodingHooks(Class<?> cls) {
        return decodingHooks.get(cls);
    }

    public static void clearDecodingHooks() {
        decodeHooks = false;
        decodingHooks.clear();
    }

    public static void removeDecodingHooks(Class<?> cls) {
        decodingHooks.remove(cls);
    }

    public static void removeDecodingHook(Class<?> cls, Transformer transformer) {
        getDecodingHooks(cls).remove(transformer);
    }

    public static void clearAllHooks() {
        clearEncodingHooks();
        clearDecodingHooks();
    }

    public static byte[] encode(BSONObject bSONObject) {
        return new BasicBSONEncoder().encode(bSONObject);
    }

    public static BSONObject decode(byte[] bArr) {
        return new BasicBSONDecoder().readObject(bArr);
    }

    public static int regexFlags(String str) {
        if (str == null) {
            return 0;
        }
        int iRegexFlag = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            iRegexFlag |= regexFlag(c);
        }
        return iRegexFlag;
    }

    public static int regexFlag(char c) {
        int i = FLAG_LOOKUP[c];
        if (i != 0) {
            return i;
        }
        throw new IllegalArgumentException(String.format("Unrecognized flag [%c]", Character.valueOf(c)));
    }

    public static String regexFlags(int i) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            int[] iArr = FLAG_LOOKUP;
            if (i2 >= iArr.length) {
                break;
            }
            if ((iArr[i2] & i) > 0) {
                sb.append((char) i2);
                i -= iArr[i2];
            }
            i2++;
        }
        if (i > 0) {
            throw new IllegalArgumentException("Some flags could not be recognized.");
        }
        return sb.toString();
    }

    public static int toInt(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument shouldn't be null");
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue() ? 1 : 0;
        }
        throw new IllegalArgumentException("Can't convert: " + obj.getClass().getName() + " to int");
    }
}

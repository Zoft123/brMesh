package io.realm.internal;

import io.realm.internal.android.TypeUtils;
import io.realm.internal.core.NativeRealmAny;
import io.realm.internal.util.Pair;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class OsMap implements NativeObject {
    public static final int NOT_FOUND = -1;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final NativeContext context;
    private final long nativePtr;
    private final Table targetTable;

    private static native void nativeClear(long j);

    private static native boolean nativeContainsBinary(long j, byte[] bArr);

    private static native boolean nativeContainsBoolean(long j, boolean z);

    private static native boolean nativeContainsDate(long j, long j2);

    private static native boolean nativeContainsDecimal128(long j, long j2, long j3);

    private static native boolean nativeContainsDouble(long j, double d);

    private static native boolean nativeContainsFloat(long j, float f);

    private static native boolean nativeContainsKey(long j, String str);

    private static native boolean nativeContainsLong(long j, long j2);

    private static native boolean nativeContainsNull(long j);

    private static native boolean nativeContainsObjectId(long j, String str);

    private static native boolean nativeContainsRealmAny(long j, long j2);

    private static native boolean nativeContainsRealmModel(long j, long j2, long j3);

    private static native boolean nativeContainsString(long j, String str);

    private static native boolean nativeContainsUUID(long j, String str);

    private static native long[] nativeCreate(long j, long j2, long j3);

    private static native long nativeCreateAndPutEmbeddedObject(long j, String str);

    private static native long nativeFreeze(long j, long j2);

    private static native Object[] nativeGetEntryForModel(long j, int i);

    private static native Object[] nativeGetEntryForPrimitive(long j, int i);

    private static native Object[] nativeGetEntryForRealmAny(long j, int i);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetRealmAnyPtr(long j, String str);

    private static native long nativeGetRow(long j, String str);

    private static native Object nativeGetValue(long j, String str);

    private static native boolean nativeIsValid(long j);

    private static native long nativeKeys(long j);

    private static native void nativePutBinary(long j, String str, byte[] bArr);

    private static native void nativePutBoolean(long j, String str, boolean z);

    private static native void nativePutDate(long j, String str, long j2);

    private static native void nativePutDecimal128(long j, String str, long j2, long j3);

    private static native void nativePutDouble(long j, String str, double d);

    private static native void nativePutFloat(long j, String str, float f);

    private static native void nativePutLong(long j, String str, long j2);

    private static native void nativePutNull(long j, String str);

    private static native void nativePutObjectId(long j, String str, String str2);

    private static native void nativePutRealmAny(long j, String str, long j2);

    private static native void nativePutRow(long j, String str, long j2);

    private static native void nativePutString(long j, String str, String str2);

    private static native void nativePutUUID(long j, String str, String str2);

    private static native void nativeRemove(long j, String str);

    private static native long nativeSize(long j);

    private static native void nativeStartListening(long j, ObservableMap observableMap);

    private static native void nativeStopListening(long j);

    private static native long nativeValues(long j);

    public OsMap(UncheckedRow uncheckedRow, long j) {
        OsSharedRealm sharedRealm = uncheckedRow.getTable().getSharedRealm();
        long[] jArrNativeCreate = nativeCreate(sharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), j);
        this.nativePtr = jArrNativeCreate[0];
        if (jArrNativeCreate[1] != -1) {
            this.targetTable = new Table(sharedRealm, jArrNativeCreate[1]);
        } else {
            this.targetTable = null;
        }
        NativeContext nativeContext = sharedRealm.context;
        this.context = nativeContext;
        nativeContext.addReference(this);
    }

    private OsMap(OsSharedRealm osSharedRealm, long j, Table table) {
        this.nativePtr = j;
        this.targetTable = table;
        NativeContext nativeContext = osSharedRealm.context;
        this.context = nativeContext;
        nativeContext.addReference(this);
    }

    public Table getTargetTable() {
        return this.targetTable;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    public boolean containsKey(Object obj) {
        return nativeContainsKey(this.nativePtr, (String) obj);
    }

    public boolean isValid() {
        return nativeIsValid(this.nativePtr);
    }

    public boolean containsPrimitiveValue(@Nullable Object obj) {
        if (obj == null) {
            return nativeContainsNull(this.nativePtr);
        }
        if (obj instanceof Integer) {
            return nativeContainsLong(this.nativePtr, ((Integer) obj).longValue());
        }
        if (obj instanceof Long) {
            return nativeContainsLong(this.nativePtr, ((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return nativeContainsDouble(this.nativePtr, ((Double) obj).doubleValue());
        }
        if (obj instanceof Short) {
            return nativeContainsLong(this.nativePtr, ((Short) obj).longValue());
        }
        if (obj instanceof Byte) {
            return nativeContainsLong(this.nativePtr, ((Byte) obj).longValue());
        }
        if (obj instanceof Boolean) {
            return nativeContainsBoolean(this.nativePtr, ((Boolean) obj).booleanValue());
        }
        if (obj instanceof String) {
            return nativeContainsString(this.nativePtr, (String) obj);
        }
        if (obj instanceof Byte[]) {
            return nativeContainsBinary(this.nativePtr, TypeUtils.convertNonPrimitiveBinaryToPrimitive((Byte[]) obj));
        }
        if (obj instanceof byte[]) {
            return nativeContainsBinary(this.nativePtr, (byte[]) obj);
        }
        if (obj instanceof Float) {
            return nativeContainsFloat(this.nativePtr, ((Float) obj).floatValue());
        }
        if (obj instanceof UUID) {
            return nativeContainsUUID(this.nativePtr, obj.toString());
        }
        if (obj instanceof ObjectId) {
            return nativeContainsObjectId(this.nativePtr, ((ObjectId) obj).toString());
        }
        if (obj instanceof Date) {
            return nativeContainsDate(this.nativePtr, ((Date) obj).getTime());
        }
        if (obj instanceof Decimal128) {
            Decimal128 decimal128 = (Decimal128) obj;
            return nativeContainsDecimal128(this.nativePtr, decimal128.getHigh(), decimal128.getLow());
        }
        throw new IllegalArgumentException("Invalid object type: " + obj.getClass().getCanonicalName());
    }

    public boolean containsRealmAnyValue(long j) {
        return nativeContainsRealmAny(this.nativePtr, j);
    }

    public boolean containsRealmModel(long j, long j2) {
        return nativeContainsRealmModel(this.nativePtr, j, j2);
    }

    public void clear() {
        nativeClear(this.nativePtr);
    }

    public Pair<Table, Long> tableAndKeyPtrs() {
        return new Pair<>(this.targetTable, Long.valueOf(nativeKeys(this.nativePtr)));
    }

    public Pair<Table, Long> tableAndValuePtrs() {
        return new Pair<>(this.targetTable, Long.valueOf(nativeValues(this.nativePtr)));
    }

    public OsMap freeze(OsSharedRealm osSharedRealm) {
        return new OsMap(osSharedRealm, nativeFreeze(this.nativePtr, osSharedRealm.getNativePtr()), this.targetTable);
    }

    public void put(Object obj, @Nullable Object obj2) {
        if (obj2 == null) {
            try {
                nativePutNull(this.nativePtr, (String) obj);
                return;
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("Value cannot be null")) {
                    throw new NullPointerException(e.getMessage());
                }
                throw e;
            }
        }
        String canonicalName = obj2.getClass().getCanonicalName();
        if (Long.class.getCanonicalName().equals(canonicalName)) {
            nativePutLong(this.nativePtr, (String) obj, ((Long) obj2).longValue());
            return;
        }
        if (Integer.class.getCanonicalName().equals(canonicalName)) {
            nativePutLong(this.nativePtr, (String) obj, ((Integer) obj2).intValue());
            return;
        }
        if (Short.class.getCanonicalName().equals(canonicalName)) {
            nativePutLong(this.nativePtr, (String) obj, ((Short) obj2).shortValue());
            return;
        }
        if (Byte.class.getCanonicalName().equals(canonicalName)) {
            nativePutLong(this.nativePtr, (String) obj, ((Byte) obj2).byteValue());
            return;
        }
        if (Float.class.getCanonicalName().equals(canonicalName)) {
            nativePutFloat(this.nativePtr, (String) obj, ((Float) obj2).floatValue());
            return;
        }
        if (Double.class.getCanonicalName().equals(canonicalName)) {
            nativePutDouble(this.nativePtr, (String) obj, ((Double) obj2).doubleValue());
            return;
        }
        if (String.class.getCanonicalName().equals(canonicalName)) {
            nativePutString(this.nativePtr, (String) obj, (String) obj2);
            return;
        }
        if (Boolean.class.getCanonicalName().equals(canonicalName)) {
            nativePutBoolean(this.nativePtr, (String) obj, ((Boolean) obj2).booleanValue());
            return;
        }
        if (Date.class.getCanonicalName().equals(canonicalName)) {
            nativePutDate(this.nativePtr, (String) obj, ((Date) obj2).getTime());
            return;
        }
        if (Decimal128.class.getCanonicalName().equals(canonicalName)) {
            Decimal128 decimal128 = (Decimal128) obj2;
            nativePutDecimal128(this.nativePtr, (String) obj, decimal128.getHigh(), decimal128.getLow());
            return;
        }
        if (Byte[].class.getCanonicalName().equals(canonicalName)) {
            nativePutBinary(this.nativePtr, (String) obj, TypeUtils.convertNonPrimitiveBinaryToPrimitive((Byte[]) obj2));
            return;
        }
        if (byte[].class.getCanonicalName().equals(canonicalName)) {
            nativePutBinary(this.nativePtr, (String) obj, (byte[]) obj2);
            return;
        }
        if (ObjectId.class.getCanonicalName().equals(canonicalName)) {
            nativePutObjectId(this.nativePtr, (String) obj, ((ObjectId) obj2).toString());
        } else {
            if (UUID.class.getCanonicalName().equals(canonicalName)) {
                nativePutUUID(this.nativePtr, (String) obj, obj2.toString());
                return;
            }
            throw new UnsupportedOperationException("Class '" + canonicalName + "' not supported.");
        }
    }

    public void putRow(Object obj, long j) {
        nativePutRow(this.nativePtr, (String) obj, j);
    }

    public void putRealmAny(Object obj, long j) {
        nativePutRealmAny(this.nativePtr, (String) obj, j);
    }

    public void remove(Object obj) {
        nativeRemove(this.nativePtr, (String) obj);
    }

    public long getModelRowKey(Object obj) {
        return nativeGetRow(this.nativePtr, (String) obj);
    }

    @Nullable
    public Object get(Object obj) {
        return nativeGetValue(this.nativePtr, (String) obj);
    }

    public long getRealmAnyPtr(Object obj) {
        return nativeGetRealmAnyPtr(this.nativePtr, (String) obj);
    }

    public long createAndPutEmbeddedObject(Object obj) {
        return nativeCreateAndPutEmbeddedObject(this.nativePtr, (String) obj);
    }

    public <K> Pair<K, Object> getEntryForPrimitive(int i) {
        Object[] objArrNativeGetEntryForPrimitive = nativeGetEntryForPrimitive(this.nativePtr, i);
        return new Pair<>((String) objArrNativeGetEntryForPrimitive[0], objArrNativeGetEntryForPrimitive[1]);
    }

    public <K> Pair<K, Long> getKeyObjRowPair(int i) {
        Object[] objArrNativeGetEntryForModel = nativeGetEntryForModel(this.nativePtr, i);
        String str = (String) objArrNativeGetEntryForModel[0];
        Long l = (Long) objArrNativeGetEntryForModel[1];
        if (l.longValue() == -1) {
            return new Pair<>(str, -1L);
        }
        return new Pair<>(str, l);
    }

    public <K> Pair<K, NativeRealmAny> getKeyRealmAnyPair(int i) {
        Object[] objArrNativeGetEntryForRealmAny = nativeGetEntryForRealmAny(this.nativePtr, i);
        return new Pair<>((String) objArrNativeGetEntryForRealmAny[0], new NativeRealmAny(((Long) objArrNativeGetEntryForRealmAny[1]).longValue()));
    }

    public void startListening(ObservableMap observableMap) {
        nativeStartListening(this.nativePtr, observableMap);
    }

    public void stopListening() {
        nativeStopListening(this.nativePtr);
    }
}

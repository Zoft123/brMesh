package io.realm.internal.core;

import io.realm.RealmModel;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.UncheckedRow;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public final class NativeRealmAnyCollection implements NativeObject {
    private final long nativePtr;

    private static native long nativeCreateBinaryCollection(byte[][] bArr, boolean[] zArr);

    private static native long nativeCreateBooleanCollection(boolean[] zArr, boolean[] zArr2);

    private static native long nativeCreateDateCollection(long[] jArr, boolean[] zArr);

    private static native long nativeCreateDecimal128Collection(long[] jArr, long[] jArr2, boolean[] zArr);

    private static native long nativeCreateDoubleCollection(double[] dArr, boolean[] zArr);

    private static native long nativeCreateFloatCollection(float[] fArr, boolean[] zArr);

    private static native long nativeCreateIntegerCollection(long[] jArr, boolean[] zArr);

    private static native long nativeCreateObjectCollection(long[] jArr, boolean[] zArr);

    private static native long nativeCreateObjectIdCollection(String[] strArr, boolean[] zArr);

    private static native long nativeCreateRealmAnyCollection(long[] jArr, boolean[] zArr);

    private static native long nativeCreateStringCollection(String[] strArr, boolean[] zArr);

    private static native long nativeCreateUUIDCollection(String[] strArr, boolean[] zArr);

    private static native long nativeGetCollectionItem(long j, int i);

    private static native int nativeGetCollectionSize(long j);

    private static native long nativeGetFinalizerPtr();

    public static NativeRealmAnyCollection newBooleanCollection(Collection<Boolean> collection) {
        boolean[] zArr = new boolean[collection.size()];
        boolean[] zArr2 = new boolean[collection.size()];
        int i = 0;
        for (Boolean bool : collection) {
            if (bool != null) {
                zArr[i] = bool.booleanValue();
                zArr2[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateBooleanCollection(zArr, zArr2));
    }

    public static NativeRealmAnyCollection newIntegerCollection(Collection<? extends Number> collection) {
        long[] jArr = new long[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (Number number : collection) {
            if (number != null) {
                jArr[i] = number.longValue();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateIntegerCollection(jArr, zArr));
    }

    public static NativeRealmAnyCollection newFloatCollection(Collection<? extends Float> collection) {
        float[] fArr = new float[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (Float f : collection) {
            if (f != null) {
                fArr[i] = f.floatValue();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateFloatCollection(fArr, zArr));
    }

    public static NativeRealmAnyCollection newDoubleCollection(Collection<? extends Double> collection) {
        double[] dArr = new double[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (Double d : collection) {
            if (d != null) {
                dArr[i] = d.doubleValue();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateDoubleCollection(dArr, zArr));
    }

    public static NativeRealmAnyCollection newStringCollection(Collection<String> collection) {
        String[] strArr = new String[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (String str : collection) {
            if (str != null) {
                strArr[i] = str;
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateStringCollection(strArr, zArr));
    }

    public static NativeRealmAnyCollection newBinaryCollection(Collection<? extends byte[]> collection) {
        byte[][] bArr = new byte[collection.size()][];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (byte[] bArr2 : collection) {
            if (bArr2 != null) {
                bArr[i] = bArr2;
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateBinaryCollection(bArr, zArr));
    }

    public static NativeRealmAnyCollection newDateCollection(Collection<? extends Date> collection) {
        long[] jArr = new long[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (Date date : collection) {
            if (date != null) {
                jArr[i] = date.getTime();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateDateCollection(jArr, zArr));
    }

    public static NativeRealmAnyCollection newObjectIdCollection(Collection<? extends ObjectId> collection) {
        String[] strArr = new String[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (ObjectId objectId : collection) {
            if (objectId != null) {
                strArr[i] = objectId.toString();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateObjectIdCollection(strArr, zArr));
    }

    public static NativeRealmAnyCollection newDecimal128Collection(Collection<? extends Decimal128> collection) {
        long[] jArr = new long[collection.size()];
        long[] jArr2 = new long[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (Decimal128 decimal128 : collection) {
            if (decimal128 != null) {
                jArr[i] = decimal128.getLow();
                jArr2[i] = decimal128.getHigh();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateDecimal128Collection(jArr, jArr2, zArr));
    }

    public static NativeRealmAnyCollection newUUIDCollection(Collection<? extends UUID> collection) {
        String[] strArr = new String[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (UUID uuid : collection) {
            if (uuid != null) {
                strArr[i] = uuid.toString();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateUUIDCollection(strArr, zArr));
    }

    public static NativeRealmAnyCollection newRealmModelCollection(Collection<? extends RealmModel> collection) {
        long[] jArr = new long[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (RealmModel realmModel : collection) {
            if (realmModel != null) {
                jArr[i] = ((UncheckedRow) ((RealmObjectProxy) realmModel).realmGet$proxyState().getRow$realm()).getNativePtr();
                zArr[i] = true;
            }
            i++;
        }
        return new NativeRealmAnyCollection(nativeCreateObjectCollection(jArr, zArr));
    }

    public static NativeRealmAnyCollection newRealmAnyCollection(long[] jArr, boolean[] zArr) {
        return new NativeRealmAnyCollection(nativeCreateRealmAnyCollection(jArr, zArr));
    }

    private NativeRealmAnyCollection(long j) {
        this.nativePtr = j;
        NativeContext.dummyContext.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeGetFinalizerPtr();
    }

    public int getSize() {
        return nativeGetCollectionSize(this.nativePtr);
    }

    public NativeRealmAny getItem(int i) {
        return new NativeRealmAny(nativeGetCollectionItem(this.nativePtr, i));
    }
}

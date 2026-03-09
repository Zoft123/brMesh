package io.realm.internal;

import io.realm.SetChangeSet;
import io.realm.internal.ObservableSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class OsSet implements NativeObject, OsCollection {
    public static final int NOT_FOUND = -1;
    private static final int VALUE_FOUND = 1;
    private static final int VALUE_NOT_FOUND = 0;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final NativeContext context;
    private final long nativePtr;
    private final OsSharedRealm osSharedRealm;
    private final Table targetTable;

    public enum ExternalCollectionOperation {
        CONTAINS_ALL,
        ADD_ALL,
        REMOVE_ALL,
        RETAIN_ALL
    }

    private static native boolean nativeAddAllRealmAnyCollection(long j, long j2);

    private static native long[] nativeAddBinary(long j, byte[] bArr);

    private static native long[] nativeAddBoolean(long j, boolean z);

    private static native long[] nativeAddDate(long j, long j2);

    private static native long[] nativeAddDecimal128(long j, long j2, long j3);

    private static native long[] nativeAddDouble(long j, double d);

    private static native long[] nativeAddFloat(long j, float f);

    private static native long[] nativeAddLong(long j, long j2);

    private static native long[] nativeAddNull(long j);

    private static native long[] nativeAddObjectId(long j, String str);

    private static native long[] nativeAddRealmAny(long j, long j2);

    private static native long[] nativeAddRow(long j, long j2);

    private static native long[] nativeAddString(long j, String str);

    private static native long[] nativeAddUUID(long j, String str);

    private static native boolean nativeAsymmetricDifference(long j, long j2);

    private static native void nativeClear(long j);

    private static native boolean nativeContainsAll(long j, long j2);

    private static native boolean nativeContainsAllRealmAnyCollection(long j, long j2);

    private static native boolean nativeContainsBinary(long j, byte[] bArr);

    private static native boolean nativeContainsBoolean(long j, boolean z);

    private static native boolean nativeContainsDate(long j, long j2);

    private static native boolean nativeContainsDecimal128(long j, long j2, long j3);

    private static native boolean nativeContainsDouble(long j, double d);

    private static native boolean nativeContainsFloat(long j, float f);

    private static native boolean nativeContainsLong(long j, long j2);

    private static native boolean nativeContainsNull(long j);

    private static native boolean nativeContainsObjectId(long j, String str);

    private static native boolean nativeContainsRealmAny(long j, long j2);

    private static native boolean nativeContainsRow(long j, long j2);

    private static native boolean nativeContainsString(long j, String str);

    private static native boolean nativeContainsUUID(long j, String str);

    private static native long[] nativeCreate(long j, long j2, long j3);

    private static native void nativeDeleteAll(long j);

    private static native long nativeFreeze(long j, long j2);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetQuery(long j);

    private static native long nativeGetRealmAny(long j, int i);

    private static native long nativeGetRow(long j, int i);

    private static native Object nativeGetValueAtIndex(long j, int i);

    private static native boolean nativeIntersect(long j, long j2);

    private static native boolean nativeIsValid(long j);

    private static native boolean nativeRemoveAllRealmAnyCollection(long j, long j2);

    private static native long[] nativeRemoveBinary(long j, byte[] bArr);

    private static native long[] nativeRemoveBoolean(long j, boolean z);

    private static native long[] nativeRemoveDate(long j, long j2);

    private static native long[] nativeRemoveDecimal128(long j, long j2, long j3);

    private static native long[] nativeRemoveDouble(long j, double d);

    private static native long[] nativeRemoveFloat(long j, float f);

    private static native long[] nativeRemoveLong(long j, long j2);

    private static native long[] nativeRemoveNull(long j);

    private static native long[] nativeRemoveObjectId(long j, String str);

    private static native long[] nativeRemoveRealmAny(long j, long j2);

    private static native long[] nativeRemoveRow(long j, long j2);

    private static native long[] nativeRemoveString(long j, String str);

    private static native long[] nativeRemoveUUID(long j, String str);

    private static native boolean nativeRetainAllRealmAnyCollection(long j, long j2);

    private static native long nativeSize(long j);

    private static native void nativeStartListening(long j, ObservableSet observableSet);

    private static native void nativeStopListening(long j);

    private static native boolean nativeUnion(long j, long j2);

    public OsSet(UncheckedRow uncheckedRow, long j) {
        OsSharedRealm sharedRealm = uncheckedRow.getTable().getSharedRealm();
        this.osSharedRealm = sharedRealm;
        long[] jArrNativeCreate = nativeCreate(sharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), j);
        this.nativePtr = jArrNativeCreate[0];
        NativeContext nativeContext = sharedRealm.context;
        this.context = nativeContext;
        nativeContext.addReference(this);
        if (jArrNativeCreate[1] != 0) {
            this.targetTable = new Table(sharedRealm, jArrNativeCreate[1]);
        } else {
            this.targetTable = null;
        }
    }

    private OsSet(OsSharedRealm osSharedRealm, long j, @Nullable Table table) {
        this.osSharedRealm = osSharedRealm;
        this.nativePtr = j;
        NativeContext nativeContext = osSharedRealm.context;
        this.context = nativeContext;
        this.targetTable = table;
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

    @Override // io.realm.internal.OsCollection
    public boolean isValid() {
        return nativeIsValid(this.nativePtr);
    }

    public TableQuery getQuery() {
        return new TableQuery(this.context, this.targetTable, nativeGetQuery(this.nativePtr));
    }

    public void deleteAll() {
        nativeDeleteAll(this.nativePtr);
    }

    public Object getValueAtIndex(int i) {
        return nativeGetValueAtIndex(this.nativePtr, i);
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    /* JADX INFO: renamed from: io.realm.internal.OsSet$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation;

        static {
            int[] iArr = new int[ExternalCollectionOperation.values().length];
            $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation = iArr;
            try {
                iArr[ExternalCollectionOperation.CONTAINS_ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[ExternalCollectionOperation.ADD_ALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[ExternalCollectionOperation.REMOVE_ALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[ExternalCollectionOperation.RETAIN_ALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public boolean collectionFunnel(NativeRealmAnyCollection nativeRealmAnyCollection, ExternalCollectionOperation externalCollectionOperation) {
        int i = AnonymousClass1.$SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[externalCollectionOperation.ordinal()];
        if (i == 1) {
            return nativeContainsAllRealmAnyCollection(this.nativePtr, nativeRealmAnyCollection.getNativePtr());
        }
        if (i == 2) {
            return nativeAddAllRealmAnyCollection(this.nativePtr, nativeRealmAnyCollection.getNativePtr());
        }
        if (i == 3) {
            return nativeRemoveAllRealmAnyCollection(this.nativePtr, nativeRealmAnyCollection.getNativePtr());
        }
        if (i == 4) {
            return retainAllInternal(nativeRealmAnyCollection);
        }
        throw new IllegalStateException("Unexpected value: " + externalCollectionOperation);
    }

    public boolean contains(@Nullable Boolean bool) {
        if (bool == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsBoolean(this.nativePtr, bool.booleanValue());
    }

    public boolean add(@Nullable Boolean bool) {
        long[] jArrNativeAddBoolean;
        if (bool == null) {
            jArrNativeAddBoolean = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddBoolean = nativeAddBoolean(this.nativePtr, bool.booleanValue());
        }
        return jArrNativeAddBoolean[1] != 0;
    }

    public boolean remove(@Nullable Boolean bool) {
        long[] jArrNativeRemoveBoolean;
        if (bool == null) {
            jArrNativeRemoveBoolean = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveBoolean = nativeRemoveBoolean(this.nativePtr, bool.booleanValue());
        }
        return jArrNativeRemoveBoolean[1] == 1;
    }

    public boolean contains(@Nullable String str) {
        if (str == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsString(this.nativePtr, str);
    }

    public boolean add(@Nullable String str) {
        long[] jArrNativeAddString;
        if (str == null) {
            jArrNativeAddString = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddString = nativeAddString(this.nativePtr, str);
        }
        return jArrNativeAddString[1] != 0;
    }

    public boolean remove(@Nullable String str) {
        long[] jArrNativeRemoveString;
        if (str == null) {
            jArrNativeRemoveString = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveString = nativeRemoveString(this.nativePtr, str);
        }
        return jArrNativeRemoveString[1] == 1;
    }

    public boolean add(@Nullable Integer num) {
        long[] jArrNativeAddLong;
        if (num == null) {
            jArrNativeAddLong = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddLong = nativeAddLong(this.nativePtr, num.longValue());
        }
        return jArrNativeAddLong[1] != 0;
    }

    public boolean remove(@Nullable Integer num) {
        long[] jArrNativeRemoveLong;
        if (num == null) {
            jArrNativeRemoveLong = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveLong = nativeRemoveLong(this.nativePtr, num.longValue());
        }
        return jArrNativeRemoveLong[1] == 1;
    }

    public boolean contains(@Nullable Long l) {
        if (l == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsLong(this.nativePtr, l.longValue());
    }

    public boolean add(@Nullable Long l) {
        long[] jArrNativeAddLong;
        if (l == null) {
            jArrNativeAddLong = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddLong = nativeAddLong(this.nativePtr, l.longValue());
        }
        return jArrNativeAddLong[1] != 0;
    }

    public boolean remove(@Nullable Long l) {
        long[] jArrNativeRemoveLong;
        if (l == null) {
            jArrNativeRemoveLong = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveLong = nativeRemoveLong(this.nativePtr, l.longValue());
        }
        return jArrNativeRemoveLong[1] == 1;
    }

    public boolean add(@Nullable Short sh) {
        long[] jArrNativeAddLong;
        if (sh == null) {
            jArrNativeAddLong = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddLong = nativeAddLong(this.nativePtr, sh.longValue());
        }
        return jArrNativeAddLong[1] != 0;
    }

    public boolean remove(@Nullable Short sh) {
        long[] jArrNativeRemoveLong;
        if (sh == null) {
            jArrNativeRemoveLong = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveLong = nativeRemoveLong(this.nativePtr, sh.longValue());
        }
        return jArrNativeRemoveLong[1] == 1;
    }

    public boolean add(@Nullable Byte b) {
        long[] jArrNativeAddLong;
        if (b == null) {
            jArrNativeAddLong = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddLong = nativeAddLong(this.nativePtr, b.longValue());
        }
        return jArrNativeAddLong[1] != 0;
    }

    public boolean remove(@Nullable Byte b) {
        long[] jArrNativeRemoveLong;
        if (b == null) {
            jArrNativeRemoveLong = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveLong = nativeRemoveLong(this.nativePtr, b.longValue());
        }
        return jArrNativeRemoveLong[1] == 1;
    }

    public boolean contains(@Nullable Float f) {
        if (f == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsFloat(this.nativePtr, f.floatValue());
    }

    public boolean add(@Nullable Float f) {
        long[] jArrNativeAddFloat;
        if (f == null) {
            jArrNativeAddFloat = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddFloat = nativeAddFloat(this.nativePtr, f.floatValue());
        }
        return jArrNativeAddFloat[1] != 0;
    }

    public boolean remove(@Nullable Float f) {
        long[] jArrNativeRemoveFloat;
        if (f == null) {
            jArrNativeRemoveFloat = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveFloat = nativeRemoveFloat(this.nativePtr, f.floatValue());
        }
        return jArrNativeRemoveFloat[1] == 1;
    }

    public boolean contains(@Nullable Double d) {
        if (d == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsDouble(this.nativePtr, d.doubleValue());
    }

    public boolean add(@Nullable Double d) {
        long[] jArrNativeAddDouble;
        if (d == null) {
            jArrNativeAddDouble = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddDouble = nativeAddDouble(this.nativePtr, d.doubleValue());
        }
        return jArrNativeAddDouble[1] != 0;
    }

    public boolean remove(@Nullable Double d) {
        long[] jArrNativeRemoveDouble;
        if (d == null) {
            jArrNativeRemoveDouble = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveDouble = nativeRemoveDouble(this.nativePtr, d.doubleValue());
        }
        return jArrNativeRemoveDouble[1] == 1;
    }

    public boolean contains(@Nullable byte[] bArr) {
        if (bArr == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsBinary(this.nativePtr, bArr);
    }

    public boolean add(@Nullable byte[] bArr) {
        long[] jArrNativeAddBinary;
        if (bArr == null) {
            jArrNativeAddBinary = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddBinary = nativeAddBinary(this.nativePtr, bArr);
        }
        return jArrNativeAddBinary[1] != 0;
    }

    public boolean remove(@Nullable byte[] bArr) {
        long[] jArrNativeRemoveBinary;
        if (bArr == null) {
            jArrNativeRemoveBinary = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveBinary = nativeRemoveBinary(this.nativePtr, bArr);
        }
        return jArrNativeRemoveBinary[1] == 1;
    }

    public boolean contains(@Nullable Date date) {
        if (date == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsDate(this.nativePtr, date.getTime());
    }

    public boolean add(@Nullable Date date) {
        long[] jArrNativeAddDate;
        if (date == null) {
            jArrNativeAddDate = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddDate = nativeAddDate(this.nativePtr, date.getTime());
        }
        return jArrNativeAddDate[1] != 0;
    }

    public boolean remove(@Nullable Date date) {
        long[] jArrNativeRemoveDate;
        if (date == null) {
            jArrNativeRemoveDate = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveDate = nativeRemoveDate(this.nativePtr, date.getTime());
        }
        return jArrNativeRemoveDate[1] == 1;
    }

    public boolean contains(@Nullable Decimal128 decimal128) {
        if (decimal128 == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsDecimal128(this.nativePtr, decimal128.getLow(), decimal128.getHigh());
    }

    public boolean add(@Nullable Decimal128 decimal128) {
        long[] jArrNativeAddDecimal128;
        if (decimal128 == null) {
            jArrNativeAddDecimal128 = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddDecimal128 = nativeAddDecimal128(this.nativePtr, decimal128.getLow(), decimal128.getHigh());
        }
        return jArrNativeAddDecimal128[1] != 0;
    }

    public boolean remove(@Nullable Decimal128 decimal128) {
        long[] jArrNativeRemoveDecimal128;
        if (decimal128 == null) {
            jArrNativeRemoveDecimal128 = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveDecimal128 = nativeRemoveDecimal128(this.nativePtr, decimal128.getLow(), decimal128.getHigh());
        }
        return jArrNativeRemoveDecimal128[1] == 1;
    }

    public boolean contains(@Nullable ObjectId objectId) {
        if (objectId == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsObjectId(this.nativePtr, objectId.toString());
    }

    public boolean add(@Nullable ObjectId objectId) {
        long[] jArrNativeAddObjectId;
        if (objectId == null) {
            jArrNativeAddObjectId = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddObjectId = nativeAddObjectId(this.nativePtr, objectId.toString());
        }
        return jArrNativeAddObjectId[1] != 0;
    }

    public boolean remove(@Nullable ObjectId objectId) {
        long[] jArrNativeRemoveObjectId;
        if (objectId == null) {
            jArrNativeRemoveObjectId = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveObjectId = nativeRemoveObjectId(this.nativePtr, objectId.toString());
        }
        return jArrNativeRemoveObjectId[1] == 1;
    }

    public boolean contains(@Nullable UUID uuid) {
        if (uuid == null) {
            return nativeContainsNull(this.nativePtr);
        }
        return nativeContainsUUID(this.nativePtr, uuid.toString());
    }

    public boolean add(@Nullable UUID uuid) {
        long[] jArrNativeAddUUID;
        if (uuid == null) {
            jArrNativeAddUUID = nativeAddNull(this.nativePtr);
        } else {
            jArrNativeAddUUID = nativeAddUUID(this.nativePtr, uuid.toString());
        }
        return jArrNativeAddUUID[1] != 0;
    }

    public boolean remove(@Nullable UUID uuid) {
        long[] jArrNativeRemoveUUID;
        if (uuid == null) {
            jArrNativeRemoveUUID = nativeRemoveNull(this.nativePtr);
        } else {
            jArrNativeRemoveUUID = nativeRemoveUUID(this.nativePtr, uuid.toString());
        }
        return jArrNativeRemoveUUID[1] == 1;
    }

    public boolean containsRow(long j) {
        return nativeContainsRow(this.nativePtr, j);
    }

    public boolean addRow(long j) {
        return nativeAddRow(this.nativePtr, j)[1] != 0;
    }

    public boolean removeRow(long j) {
        return nativeRemoveRow(this.nativePtr, j)[1] != 0;
    }

    public long getRow(int i) {
        return nativeGetRow(this.nativePtr, i);
    }

    public boolean containsRealmAny(long j) {
        return nativeContainsRealmAny(this.nativePtr, j);
    }

    public boolean addRealmAny(long j) {
        return nativeAddRealmAny(this.nativePtr, j)[1] != 0;
    }

    public boolean removeRealmAny(long j) {
        return nativeRemoveRealmAny(this.nativePtr, j)[1] != 0;
    }

    public long getRealmAny(int i) {
        return nativeGetRealmAny(this.nativePtr, i);
    }

    public boolean containsAll(OsSet osSet) {
        return nativeContainsAll(this.nativePtr, osSet.getNativePtr());
    }

    public boolean union(OsSet osSet) {
        return nativeUnion(this.nativePtr, osSet.getNativePtr());
    }

    public boolean asymmetricDifference(OsSet osSet) {
        return nativeAsymmetricDifference(this.nativePtr, osSet.getNativePtr());
    }

    public boolean intersect(OsSet osSet) {
        return nativeIntersect(this.nativePtr, osSet.getNativePtr());
    }

    public void clear() {
        nativeClear(this.nativePtr);
    }

    public OsSet freeze(OsSharedRealm osSharedRealm) {
        return new OsSet(osSharedRealm, nativeFreeze(this.nativePtr, osSharedRealm.getNativePtr()), this.targetTable);
    }

    public void startListening(ObservableSet observableSet) {
        nativeStartListening(this.nativePtr, observableSet);
    }

    public void stopListening() {
        nativeStopListening(this.nativePtr);
    }

    public <T> void notifyChangeListeners(long j, ObserverPairList<ObservableSet.SetObserverPair<T>> observerPairList) {
        SetChangeSet setChangeSet = new SetChangeSet(new OsCollectionChangeSet(j, false));
        if (setChangeSet.isEmpty()) {
            return;
        }
        observerPairList.foreach(new ObservableSet.Callback(setChangeSet));
    }

    private boolean retainAllInternal(NativeRealmAnyCollection nativeRealmAnyCollection) {
        if (size() == 0) {
            return false;
        }
        if (nativeRealmAnyCollection.getSize() == 0) {
            clear();
            return true;
        }
        return nativeRetainAllRealmAnyCollection(this.nativePtr, nativeRealmAnyCollection.getNativePtr());
    }
}

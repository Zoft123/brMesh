package io.realm.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class Table implements NativeObject {
    public static final int CLASS_NAME_MAX_LENGTH;
    public static final long INFINITE = -1;
    public static final int MAX_BINARY_SIZE = 16777200;
    public static final int MAX_STRING_SIZE = 16777199;
    public static final boolean NOT_NULLABLE = false;
    public static final int NO_MATCH = -1;
    public static final boolean NULLABLE = true;
    private static final int TABLE_NAME_MAX_LENGTH = 63;
    private static final String TABLE_PREFIX;
    private static final long nativeFinalizerPtr;
    private final NativeContext context;
    private final long nativeTableRefPtr;
    private final OsSharedRealm sharedRealm;

    private native long nativeAddColumn(long j, int i, String str, boolean z);

    private native long nativeAddColumnDictionaryLink(long j, int i, String str, long j2);

    private native long nativeAddColumnLink(long j, int i, String str, long j2);

    private native long nativeAddColumnSetLink(long j, int i, String str, long j2);

    private native long nativeAddPrimitiveDictionaryColumn(long j, int i, String str, boolean z);

    private native long nativeAddPrimitiveListColumn(long j, int i, String str, boolean z);

    private native long nativeAddPrimitiveSetColumn(long j, int i, String str, boolean z);

    private native void nativeAddSearchIndex(long j, long j2);

    private native void nativeClear(long j);

    private native void nativeConvertColumnToNotNullable(long j, long j2, boolean z);

    private native void nativeConvertColumnToNullable(long j, long j2, boolean z);

    private native long nativeCountDouble(long j, long j2, double d);

    private native long nativeCountFloat(long j, long j2, float f);

    private native long nativeCountLong(long j, long j2, long j3);

    private native long nativeCountString(long j, long j2, String str);

    private native long nativeFindFirstBool(long j, long j2, boolean z);

    public static native long nativeFindFirstDecimal128(long j, long j2, long j3, long j4);

    private native long nativeFindFirstDouble(long j, long j2, double d);

    private native long nativeFindFirstFloat(long j, long j2, float f);

    public static native long nativeFindFirstInt(long j, long j2, long j3);

    public static native long nativeFindFirstNull(long j, long j2);

    public static native long nativeFindFirstObjectId(long j, long j2, String str);

    public static native long nativeFindFirstString(long j, long j2, String str);

    private native long nativeFindFirstTimestamp(long j, long j2, long j3);

    public static native long nativeFindFirstUUID(long j, long j2, String str);

    private static native long nativeFreeze(long j, long j2);

    private native boolean nativeGetBoolean(long j, long j2, long j3);

    private native byte[] nativeGetByteArray(long j, long j2, long j3);

    private native long nativeGetColumnCount(long j);

    private native long nativeGetColumnKey(long j, String str);

    private native String nativeGetColumnName(long j, long j2);

    private native String[] nativeGetColumnNames(long j);

    private native int nativeGetColumnType(long j, long j2);

    private native long[] nativeGetDecimal128(long j, long j2, long j3);

    private native double nativeGetDouble(long j, long j2, long j3);

    private static native long nativeGetFinalizerPtr();

    private native float nativeGetFloat(long j, long j2, long j3);

    private native long nativeGetLink(long j, long j2, long j3);

    private native long nativeGetLinkTarget(long j, long j2);

    private native long nativeGetLong(long j, long j2, long j3);

    private native String nativeGetName(long j);

    private native String nativeGetObjectId(long j, long j2, long j3);

    private native String nativeGetString(long j, long j2, long j3);

    private native long nativeGetTimestamp(long j, long j2, long j3);

    private native boolean nativeHasSameSchema(long j, long j2);

    private native boolean nativeHasSearchIndex(long j, long j2);

    public static native void nativeIncrementLong(long j, long j2, long j3, long j4);

    private native boolean nativeIsColumnNullable(long j, long j2);

    private static native boolean nativeIsEmbedded(long j);

    private native boolean nativeIsNull(long j, long j2, long j3);

    private native boolean nativeIsNullLink(long j, long j2, long j3);

    private native boolean nativeIsValid(long j);

    private native void nativeMoveLastOver(long j, long j2);

    public static native void nativeNullifyLink(long j, long j2, long j3);

    private native void nativeRemoveColumn(long j, long j2);

    private native void nativeRemoveSearchIndex(long j, long j2);

    private native void nativeRenameColumn(long j, long j2, String str);

    public static native void nativeSetBoolean(long j, long j2, long j3, boolean z, boolean z2);

    public static native void nativeSetByteArray(long j, long j2, long j3, byte[] bArr, boolean z);

    public static native void nativeSetDecimal128(long j, long j2, long j3, long j4, long j5, boolean z);

    public static native void nativeSetDouble(long j, long j2, long j3, double d, boolean z);

    private static native boolean nativeSetEmbedded(long j, boolean z, boolean z2);

    public static native void nativeSetFloat(long j, long j2, long j3, float f, boolean z);

    public static native void nativeSetLink(long j, long j2, long j3, long j4, boolean z);

    public static native void nativeSetLong(long j, long j2, long j3, long j4, boolean z);

    public static native void nativeSetNull(long j, long j2, long j3, boolean z);

    public static native void nativeSetObjectId(long j, long j2, long j3, String str, boolean z);

    public static native void nativeSetRealmAny(long j, long j2, long j3, long j4, boolean z);

    public static native void nativeSetString(long j, long j2, long j3, String str, boolean z);

    public static native void nativeSetTimestamp(long j, long j2, long j3, long j4, boolean z);

    public static native void nativeSetUUID(long j, long j2, long j3, String str, boolean z);

    private native long nativeSize(long j);

    private native long nativeWhere(long j);

    public Table getTable() {
        return this;
    }

    native long nativeGetRowPtr(long j, long j2);

    static {
        String tablePrefix = Util.getTablePrefix();
        TABLE_PREFIX = tablePrefix;
        CLASS_NAME_MAX_LENGTH = 63 - tablePrefix.length();
        nativeFinalizerPtr = nativeGetFinalizerPtr();
    }

    Table(OsSharedRealm osSharedRealm, long j) {
        NativeContext nativeContext = osSharedRealm.context;
        this.context = nativeContext;
        this.sharedRealm = osSharedRealm;
        this.nativeTableRefPtr = j;
        nativeContext.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativeTableRefPtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public boolean isValid() {
        long j = this.nativeTableRefPtr;
        return j != 0 && nativeIsValid(j);
    }

    private void verifyColumnName(String str) {
        if (str.length() > 63) {
            throw new IllegalArgumentException("Column names are currently limited to max 63 characters.");
        }
    }

    /* JADX INFO: renamed from: io.realm.internal.Table$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$RealmFieldType;

        static {
            int[] iArr = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr;
            try {
                iArr[RealmFieldType.INTEGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER_LIST.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN_LIST.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_LIST.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY_LIST.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE_LIST.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT_LIST.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE_LIST.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128_LIST.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID_LIST.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID_LIST.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED_LIST.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_INTEGER_MAP.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_BOOLEAN_MAP.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_STRING_MAP.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_BINARY_MAP.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DATE_MAP.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_FLOAT_MAP.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DOUBLE_MAP.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DECIMAL128_MAP.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_OBJECT_ID_MAP.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_UUID_MAP.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_MIXED_MAP.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER_SET.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN_SET.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_SET.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY_SET.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE_SET.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT_SET.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE_SET.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128_SET.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID_SET.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID_SET.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED_SET.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
        }
    }

    public long addColumn(RealmFieldType realmFieldType, String str, boolean z) {
        verifyColumnName(str);
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return nativeAddColumn(this.nativeTableRefPtr, realmFieldType.getNativeValue(), str, z);
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return nativeAddPrimitiveListColumn(this.nativeTableRefPtr, realmFieldType.getNativeValue() - 128, str, z);
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
            case 30:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
            case 32:
            case 33:
                return nativeAddPrimitiveDictionaryColumn(this.nativeTableRefPtr, realmFieldType.getNativeValue() - 512, str, z);
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                return nativeAddPrimitiveSetColumn(this.nativeTableRefPtr, realmFieldType.getNativeValue() - 256, str, z);
            default:
                throw new IllegalArgumentException("Unsupported type: " + realmFieldType);
        }
    }

    public long addColumn(RealmFieldType realmFieldType, String str) {
        return addColumn(realmFieldType, str, false);
    }

    public long addColumnLink(RealmFieldType realmFieldType, String str, Table table) {
        verifyColumnName(str);
        return nativeAddColumnLink(this.nativeTableRefPtr, realmFieldType.getNativeValue(), str, table.nativeTableRefPtr);
    }

    public long addColumnDictionaryLink(RealmFieldType realmFieldType, String str, Table table) {
        verifyColumnName(str);
        return nativeAddColumnDictionaryLink(this.nativeTableRefPtr, realmFieldType.getNativeValue(), str, table.nativeTableRefPtr);
    }

    public long addColumnSetLink(RealmFieldType realmFieldType, String str, Table table) {
        verifyColumnName(str);
        return nativeAddColumnSetLink(this.nativeTableRefPtr, realmFieldType.getNativeValue(), str, table.nativeTableRefPtr);
    }

    public void removeColumn(long j) {
        String className = getClassName();
        String columnName = getColumnName(j);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, getClassName());
        nativeRemoveColumn(this.nativeTableRefPtr, j);
        if (columnName.equals(primaryKeyForObject)) {
            OsObjectStore.setPrimaryKeyForObject(this.sharedRealm, className, null);
        }
    }

    public void renameColumn(long j, String str) {
        verifyColumnName(str);
        String strNativeGetColumnName = nativeGetColumnName(this.nativeTableRefPtr, j);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, getClassName());
        nativeRenameColumn(this.nativeTableRefPtr, j, str);
        if (strNativeGetColumnName.equals(primaryKeyForObject)) {
            try {
                OsObjectStore.setPrimaryKeyForObject(this.sharedRealm, getClassName(), str);
            } catch (Exception e) {
                nativeRenameColumn(this.nativeTableRefPtr, j, strNativeGetColumnName);
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isColumnNullable(long j) {
        return nativeIsColumnNullable(this.nativeTableRefPtr, j);
    }

    public void convertColumnToNullable(long j) {
        if (this.sharedRealm.isSyncRealm()) {
            throw new IllegalStateException("This method is only available for non-synchronized Realms");
        }
        nativeConvertColumnToNullable(this.nativeTableRefPtr, j, isPrimaryKey(j));
    }

    public void convertColumnToNotNullable(long j) {
        if (this.sharedRealm.isSyncRealm()) {
            throw new IllegalStateException("This method is only available for non-synchronized Realms");
        }
        nativeConvertColumnToNotNullable(this.nativeTableRefPtr, j, isPrimaryKey(j));
    }

    public long size() {
        return nativeSize(this.nativeTableRefPtr);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        checkImmutable();
        nativeClear(this.nativeTableRefPtr);
    }

    public long getColumnCount() {
        return nativeGetColumnCount(this.nativeTableRefPtr);
    }

    public String getColumnName(long j) {
        return nativeGetColumnName(this.nativeTableRefPtr, j);
    }

    public String[] getColumnNames() {
        return nativeGetColumnNames(this.nativeTableRefPtr);
    }

    public long getColumnKey(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Column name can not be null.");
        }
        return nativeGetColumnKey(this.nativeTableRefPtr, str);
    }

    public RealmFieldType getColumnType(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.nativeTableRefPtr, j));
    }

    public void moveLastOver(long j) {
        checkImmutable();
        nativeMoveLastOver(this.nativeTableRefPtr, j);
    }

    private boolean isPrimaryKey(long j) {
        return getColumnName(j).equals(OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, getClassName()));
    }

    public static void throwDuplicatePrimaryKeyException(Object obj) {
        throw new RealmPrimaryKeyConstraintException("Value already exists: " + obj);
    }

    public OsSharedRealm getSharedRealm() {
        return this.sharedRealm;
    }

    public long getLong(long j, long j2) {
        return nativeGetLong(this.nativeTableRefPtr, j, j2);
    }

    public boolean getBoolean(long j, long j2) {
        return nativeGetBoolean(this.nativeTableRefPtr, j, j2);
    }

    public float getFloat(long j, long j2) {
        return nativeGetFloat(this.nativeTableRefPtr, j, j2);
    }

    public double getDouble(long j, long j2) {
        return nativeGetDouble(this.nativeTableRefPtr, j, j2);
    }

    public Date getDate(long j, long j2) {
        return new Date(nativeGetTimestamp(this.nativeTableRefPtr, j, j2));
    }

    public String getString(long j, long j2) {
        return nativeGetString(this.nativeTableRefPtr, j, j2);
    }

    public byte[] getBinaryByteArray(long j, long j2) {
        return nativeGetByteArray(this.nativeTableRefPtr, j, j2);
    }

    public long getLink(long j, long j2) {
        return nativeGetLink(this.nativeTableRefPtr, j, j2);
    }

    public Table getLinkTarget(long j) {
        return new Table(this.sharedRealm, nativeGetLinkTarget(this.nativeTableRefPtr, j));
    }

    public UncheckedRow getUncheckedRow(long j) {
        return UncheckedRow.getByRowKey(this.context, this, j);
    }

    public UncheckedRow getUncheckedRowByPointer(long j) {
        return UncheckedRow.getByRowPointer(this.context, this, j);
    }

    public CheckedRow getCheckedRow(long j) {
        return CheckedRow.get(this.context, this, j);
    }

    public void setLong(long j, long j2, long j3, boolean z) {
        checkImmutable();
        nativeSetLong(this.nativeTableRefPtr, j, j2, j3, z);
    }

    public void incrementLong(long j, long j2, long j3) {
        checkImmutable();
        nativeIncrementLong(this.nativeTableRefPtr, j, j2, j3);
    }

    public void setBoolean(long j, long j2, boolean z, boolean z2) {
        checkImmutable();
        nativeSetBoolean(this.nativeTableRefPtr, j, j2, z, z2);
    }

    public void setFloat(long j, long j2, float f, boolean z) {
        checkImmutable();
        nativeSetFloat(this.nativeTableRefPtr, j, j2, f, z);
    }

    public void setDouble(long j, long j2, double d, boolean z) {
        checkImmutable();
        nativeSetDouble(this.nativeTableRefPtr, j, j2, d, z);
    }

    public void setDate(long j, long j2, Date date, boolean z) {
        if (date == null) {
            throw new IllegalArgumentException("Null Date is not allowed.");
        }
        checkImmutable();
        nativeSetTimestamp(this.nativeTableRefPtr, j, j2, date.getTime(), z);
    }

    public void setString(long j, long j2, @Nullable String str, boolean z) {
        checkImmutable();
        if (str == null) {
            nativeSetNull(this.nativeTableRefPtr, j, j2, z);
        } else {
            nativeSetString(this.nativeTableRefPtr, j, j2, str, z);
        }
    }

    public void setBinaryByteArray(long j, long j2, byte[] bArr, boolean z) {
        checkImmutable();
        nativeSetByteArray(this.nativeTableRefPtr, j, j2, bArr, z);
    }

    public void setDecimal128(long j, long j2, @Nullable Decimal128 decimal128, boolean z) {
        checkImmutable();
        if (decimal128 == null) {
            nativeSetNull(this.nativeTableRefPtr, j, j2, z);
        } else {
            nativeSetDecimal128(this.nativeTableRefPtr, j, j2, decimal128.getLow(), decimal128.getHigh(), z);
        }
    }

    public void setObjectId(long j, long j2, @Nullable ObjectId objectId, boolean z) {
        checkImmutable();
        if (objectId == null) {
            nativeSetNull(this.nativeTableRefPtr, j, j2, z);
        } else {
            nativeSetObjectId(this.nativeTableRefPtr, j, j2, objectId.toString(), z);
        }
    }

    public void setUUID(long j, long j2, @Nullable UUID uuid, boolean z) {
        checkImmutable();
        if (uuid == null) {
            nativeSetNull(this.nativeTableRefPtr, j, j2, z);
        } else {
            nativeSetUUID(this.nativeTableRefPtr, j, j2, uuid.toString(), z);
        }
    }

    public void setRealmAny(long j, long j2, long j3, boolean z) {
        checkImmutable();
        nativeSetRealmAny(this.nativeTableRefPtr, j, j2, j3, z);
    }

    public void setLink(long j, long j2, long j3, boolean z) {
        checkImmutable();
        nativeSetLink(this.nativeTableRefPtr, j, j2, j3, z);
    }

    public void setNull(long j, long j2, boolean z) {
        checkImmutable();
        nativeSetNull(this.nativeTableRefPtr, j, j2, z);
    }

    public void addSearchIndex(long j) {
        checkImmutable();
        nativeAddSearchIndex(this.nativeTableRefPtr, j);
    }

    public void removeSearchIndex(long j) {
        checkImmutable();
        nativeRemoveSearchIndex(this.nativeTableRefPtr, j);
    }

    public boolean hasSearchIndex(long j) {
        return nativeHasSearchIndex(this.nativeTableRefPtr, j);
    }

    public boolean isNullLink(long j, long j2) {
        return nativeIsNullLink(this.nativeTableRefPtr, j, j2);
    }

    public void nullifyLink(long j, long j2) {
        nativeNullifyLink(this.nativeTableRefPtr, j, j2);
    }

    boolean isImmutable() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        return (osSharedRealm == null || osSharedRealm.isInTransaction()) ? false : true;
    }

    void checkImmutable() {
        if (isImmutable()) {
            throwImmutable();
        }
    }

    public long count(long j, long j2) {
        return nativeCountLong(this.nativeTableRefPtr, j, j2);
    }

    public long count(long j, float f) {
        return nativeCountFloat(this.nativeTableRefPtr, j, f);
    }

    public long count(long j, double d) {
        return nativeCountDouble(this.nativeTableRefPtr, j, d);
    }

    public long count(long j, String str) {
        return nativeCountString(this.nativeTableRefPtr, j, str);
    }

    public TableQuery where() {
        return new TableQuery(this.context, this, nativeWhere(this.nativeTableRefPtr));
    }

    public long findFirstLong(long j, long j2) {
        return nativeFindFirstInt(this.nativeTableRefPtr, j, j2);
    }

    public long findFirstBoolean(long j, boolean z) {
        return nativeFindFirstBool(this.nativeTableRefPtr, j, z);
    }

    public long findFirstFloat(long j, float f) {
        return nativeFindFirstFloat(this.nativeTableRefPtr, j, f);
    }

    public long findFirstDouble(long j, double d) {
        return nativeFindFirstDouble(this.nativeTableRefPtr, j, d);
    }

    public long findFirstDate(long j, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("null is not supported");
        }
        return nativeFindFirstTimestamp(this.nativeTableRefPtr, j, date.getTime());
    }

    public long findFirstString(long j, String str) {
        if (str == null) {
            throw new IllegalArgumentException("null is not supported");
        }
        return nativeFindFirstString(this.nativeTableRefPtr, j, str);
    }

    public long findFirstDecimal128(long j, Decimal128 decimal128) {
        if (decimal128 == null) {
            throw new IllegalArgumentException("null is not supported");
        }
        return nativeFindFirstDecimal128(this.nativeTableRefPtr, j, decimal128.getLow(), decimal128.getHigh());
    }

    public long findFirstObjectId(long j, ObjectId objectId) {
        if (objectId == null) {
            throw new IllegalArgumentException("null is not supported");
        }
        return nativeFindFirstObjectId(this.nativeTableRefPtr, j, objectId.toString());
    }

    public long findFirstUUID(long j, UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("null is not supported");
        }
        return nativeFindFirstUUID(this.nativeTableRefPtr, j, uuid.toString());
    }

    public long findFirstNull(long j) {
        return nativeFindFirstNull(this.nativeTableRefPtr, j);
    }

    @Nullable
    public String getName() {
        return nativeGetName(this.nativeTableRefPtr);
    }

    public String getClassName() {
        String classNameForTable = getClassNameForTable(getName());
        if (Util.isEmptyString(classNameForTable)) {
            throw new IllegalStateException("This object class is no longer part of the schema for the Realm file. It is therefor not possible to access the schema name.");
        }
        return classNameForTable;
    }

    public String toString() {
        long columnCount = getColumnCount();
        String name = getName();
        StringBuilder sb = new StringBuilder("The Table ");
        if (name != null && !name.isEmpty()) {
            sb.append(getName());
            sb.append(" ");
        }
        sb.append("contains ");
        sb.append(columnCount);
        sb.append(" columns: ");
        String[] columnNames = getColumnNames();
        int length = columnNames.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            String str = columnNames[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(str);
            i++;
            z = false;
        }
        sb.append(". And ");
        sb.append(size());
        sb.append(" rows.");
        return sb.toString();
    }

    private static void throwImmutable() {
        throw new IllegalStateException("Cannot modify managed objects outside of a write transaction.");
    }

    public boolean hasSameSchema(Table table) {
        if (table == null) {
            throw new IllegalArgumentException("The argument cannot be null");
        }
        return nativeHasSameSchema(this.nativeTableRefPtr, table.nativeTableRefPtr);
    }

    public Table freeze(OsSharedRealm osSharedRealm) {
        if (!osSharedRealm.isFrozen()) {
            throw new IllegalArgumentException("Frozen Realm required");
        }
        return new Table(osSharedRealm, nativeFreeze(osSharedRealm.getNativePtr(), this.nativeTableRefPtr));
    }

    public boolean isEmbedded() {
        return nativeIsEmbedded(this.nativeTableRefPtr);
    }

    public boolean setEmbedded(boolean z) {
        return setEmbedded(z, false);
    }

    public boolean setEmbedded(boolean z, boolean z2) {
        return nativeSetEmbedded(this.nativeTableRefPtr, z, z2);
    }

    @Nullable
    public static String getClassNameForTable(@Nullable String str) {
        if (str == null) {
            return null;
        }
        String str2 = TABLE_PREFIX;
        return !str.startsWith(str2) ? str : str.substring(str2.length());
    }

    public static String getTableNameForClass(String str) {
        if (str == null) {
            return null;
        }
        return TABLE_PREFIX + str;
    }
}

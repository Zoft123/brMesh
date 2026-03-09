package io.realm.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.InputDeviceCompat;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.error.GattError;
import io.realm.RealmFieldType;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class Property implements NativeObject {
    public static final boolean INDEXED = true;
    public static final boolean PRIMARY_KEY = true;
    public static final boolean REQUIRED = true;
    public static final int TYPE_ARRAY = 128;
    public static final int TYPE_BOOL = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_DATE = 4;
    public static final int TYPE_DECIMAL128 = 11;
    public static final int TYPE_DICTIONARY = 512;
    public static final int TYPE_DOUBLE = 6;
    public static final int TYPE_FLOAT = 5;
    public static final int TYPE_INT = 0;
    public static final int TYPE_LINKING_OBJECTS = 8;
    public static final int TYPE_MIXED = 9;
    public static final int TYPE_NULLABLE = 64;
    public static final int TYPE_OBJECT = 7;
    public static final int TYPE_OBJECT_ID = 10;
    public static final int TYPE_REQUIRED = 0;
    public static final int TYPE_SET = 256;
    public static final int TYPE_STRING = 2;
    public static final int TYPE_UUID = 12;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private long nativePtr;

    static native long nativeCreateComputedLinkProperty(String str, String str2, String str3);

    static native long nativeCreatePersistedLinkProperty(String str, String str2, int i, String str3);

    static native long nativeCreatePersistedProperty(String str, String str2, int i, boolean z, boolean z2);

    private static native long nativeGetColumnKey(long j);

    private static native long nativeGetFinalizerPtr();

    private static native String nativeGetLinkedObjectName(long j);

    private static native int nativeGetType(long j);

    Property(long j) {
        this.nativePtr = j;
        NativeContext.dummyContext.addReference(this);
    }

    /* JADX INFO: renamed from: io.realm.internal.Property$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$RealmFieldType;

        static {
            int[] iArr = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr;
            try {
                iArr[RealmFieldType.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LINKING_OBJECTS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER_LIST.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN_LIST.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_LIST.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY_LIST.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE_LIST.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT_LIST.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128_LIST.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID_LIST.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID_LIST.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE_LIST.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED_LIST.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_MIXED_MAP.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_BOOLEAN_MAP.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_STRING_MAP.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_INTEGER_MAP.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_FLOAT_MAP.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DOUBLE_MAP.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_BINARY_MAP.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DATE_MAP.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DECIMAL128_MAP.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_OBJECT_ID_MAP.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_UUID_MAP.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_LINK_MAP.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN_SET.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_SET.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER_SET.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT_SET.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE_SET.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY_SET.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE_SET.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128_SET.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID_SET.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID_SET.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LINK_SET.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED_SET.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
        }
    }

    static int convertFromRealmFieldType(RealmFieldType realmFieldType, boolean z) {
        int i = 1;
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()]) {
            case 1:
                return 71;
            case 2:
                return GattError.GATT_ILLEGAL_PARAMETER;
            case 3:
                return GattError.GATT_PENDING;
            case 4:
                i = 0;
                break;
            case 5:
                break;
            case 6:
                i = 2;
                break;
            case 7:
                i = 3;
                break;
            case 8:
                i = 4;
                break;
            case 9:
                i = 5;
                break;
            case 10:
                i = 11;
                break;
            case 11:
                i = 10;
                break;
            case 12:
                i = 12;
                break;
            case 13:
                i = 9;
                break;
            case 14:
                i = 6;
                break;
            case 15:
                i = 128;
                break;
            case 16:
                i = GattError.GATT_INTERNAL_ERROR;
                break;
            case 17:
                i = GattError.GATT_WRONG_STATE;
                break;
            case 18:
                i = GattError.GATT_DB_FULL;
                break;
            case 19:
                i = GattError.GATT_BUSY;
                break;
            case 20:
                i = GattError.GATT_ERROR;
                break;
            case 21:
                i = GattError.GATT_INVALID_CFG;
                break;
            case 22:
                i = GattError.GATT_MORE;
                break;
            case 23:
                i = GattError.GATT_SERVICE_STARTED;
                break;
            case 24:
                i = GattError.GATT_CMD_STARTED;
                break;
            case 25:
                i = GattError.GATT_AUTH_FAIL;
                break;
            case 26:
                i = 521;
                break;
            case 27:
                i = InputDeviceCompat.SOURCE_DPAD;
                break;
            case 28:
                i = 514;
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                i = 512;
                break;
            case 30:
                i = 517;
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                i = 518;
                break;
            case 32:
                i = 515;
                break;
            case 33:
                i = 516;
                break;
            case 34:
                i = 523;
                break;
            case 35:
                i = 522;
                break;
            case 36:
                i = 524;
                break;
            case 37:
                i = 519;
                break;
            case 38:
                i = 257;
                break;
            case 39:
                i = 258;
                break;
            case 40:
                i = 256;
                break;
            case 41:
                i = 261;
                break;
            case 42:
                i = 262;
                break;
            case 43:
                i = 259;
                break;
            case 44:
                i = 260;
                break;
            case 45:
                i = 267;
                break;
            case 46:
                i = 266;
                break;
            case 47:
                i = 268;
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                return 263;
            case 49:
                i = 265;
                break;
            default:
                throw new IllegalArgumentException(String.format(Locale.US, "Unsupported filed type: '%s'.", realmFieldType.name()));
        }
        return i | (z ? 0 : 64);
    }

    private static RealmFieldType convertToRealmFieldType(int i) {
        int i2 = i & (-65);
        switch (i2) {
            case 0:
                return RealmFieldType.INTEGER;
            case 1:
                return RealmFieldType.BOOLEAN;
            case 2:
                return RealmFieldType.STRING;
            case 3:
                return RealmFieldType.BINARY;
            case 4:
                return RealmFieldType.DATE;
            case 5:
                return RealmFieldType.FLOAT;
            case 6:
                return RealmFieldType.DOUBLE;
            case 7:
                return RealmFieldType.OBJECT;
            default:
                switch (i2) {
                    case 9:
                        return RealmFieldType.MIXED;
                    case 10:
                        return RealmFieldType.OBJECT_ID;
                    case 11:
                        return RealmFieldType.DECIMAL128;
                    case 12:
                        return RealmFieldType.UUID;
                    default:
                        switch (i2) {
                            case 128:
                                return RealmFieldType.INTEGER_LIST;
                            case GattError.GATT_INTERNAL_ERROR /* 129 */:
                                return RealmFieldType.BOOLEAN_LIST;
                            case GattError.GATT_WRONG_STATE /* 130 */:
                                return RealmFieldType.STRING_LIST;
                            case GattError.GATT_DB_FULL /* 131 */:
                                return RealmFieldType.BINARY_LIST;
                            case GattError.GATT_BUSY /* 132 */:
                                return RealmFieldType.DATE_LIST;
                            case GattError.GATT_ERROR /* 133 */:
                                return RealmFieldType.FLOAT_LIST;
                            case GattError.GATT_CMD_STARTED /* 134 */:
                                return RealmFieldType.DOUBLE_LIST;
                            case GattError.GATT_ILLEGAL_PARAMETER /* 135 */:
                                return RealmFieldType.LIST;
                            case GattError.GATT_PENDING /* 136 */:
                                return RealmFieldType.LINKING_OBJECTS;
                            case GattError.GATT_AUTH_FAIL /* 137 */:
                                return RealmFieldType.MIXED_LIST;
                            case GattError.GATT_MORE /* 138 */:
                                return RealmFieldType.OBJECT_ID_LIST;
                            case GattError.GATT_INVALID_CFG /* 139 */:
                                return RealmFieldType.DECIMAL128_LIST;
                            case GattError.GATT_SERVICE_STARTED /* 140 */:
                                return RealmFieldType.UUID_LIST;
                            default:
                                switch (i2) {
                                    case 256:
                                        return RealmFieldType.INTEGER_SET;
                                    case 257:
                                        return RealmFieldType.BOOLEAN_SET;
                                    case 258:
                                        return RealmFieldType.STRING_SET;
                                    case 259:
                                        return RealmFieldType.BINARY_SET;
                                    case 260:
                                        return RealmFieldType.DATE_SET;
                                    case 261:
                                        return RealmFieldType.FLOAT_SET;
                                    case 262:
                                        return RealmFieldType.DOUBLE_SET;
                                    case 263:
                                        return RealmFieldType.LINK_SET;
                                    default:
                                        switch (i2) {
                                            case 265:
                                                return RealmFieldType.MIXED_SET;
                                            case 266:
                                                return RealmFieldType.OBJECT_ID_SET;
                                            case 267:
                                                return RealmFieldType.DECIMAL128_SET;
                                            case 268:
                                                return RealmFieldType.UUID_SET;
                                            default:
                                                switch (i2) {
                                                    case 512:
                                                        return RealmFieldType.STRING_TO_INTEGER_MAP;
                                                    case InputDeviceCompat.SOURCE_DPAD /* 513 */:
                                                        return RealmFieldType.STRING_TO_BOOLEAN_MAP;
                                                    case 514:
                                                        return RealmFieldType.STRING_TO_STRING_MAP;
                                                    case 515:
                                                        return RealmFieldType.STRING_TO_BINARY_MAP;
                                                    case 516:
                                                        return RealmFieldType.STRING_TO_DATE_MAP;
                                                    case 517:
                                                        return RealmFieldType.STRING_TO_FLOAT_MAP;
                                                    case 518:
                                                        return RealmFieldType.STRING_TO_DOUBLE_MAP;
                                                    case 519:
                                                        return RealmFieldType.STRING_TO_LINK_MAP;
                                                    default:
                                                        switch (i2) {
                                                            case 521:
                                                                return RealmFieldType.STRING_TO_MIXED_MAP;
                                                            case 522:
                                                                return RealmFieldType.STRING_TO_OBJECT_ID_MAP;
                                                            case 523:
                                                                return RealmFieldType.STRING_TO_DECIMAL128_MAP;
                                                            case 524:
                                                                return RealmFieldType.STRING_TO_UUID_MAP;
                                                            default:
                                                                throw new IllegalArgumentException(String.format(Locale.US, "Unsupported property type: '%d'", Integer.valueOf(i)));
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public RealmFieldType getType() {
        return convertToRealmFieldType(nativeGetType(this.nativePtr));
    }

    public String getLinkedObjectName() {
        return nativeGetLinkedObjectName(this.nativePtr);
    }

    public long getColumnKey() {
        return nativeGetColumnKey(this.nativePtr);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }
}

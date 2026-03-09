package io.realm;

import androidx.core.view.InputDeviceCompat;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.error.GattError;

/* JADX INFO: loaded from: classes4.dex */
public enum RealmFieldType {
    INTEGER(0),
    BOOLEAN(1),
    STRING(2),
    BINARY(4),
    DATE(8),
    FLOAT(9),
    DOUBLE(10),
    OBJECT(12),
    DECIMAL128(11),
    OBJECT_ID(15),
    UUID(17),
    MIXED(6),
    TYPED_LINK(16),
    LIST(13),
    LINKING_OBJECTS(14),
    INTEGER_LIST(128),
    BOOLEAN_LIST(GattError.GATT_INTERNAL_ERROR),
    STRING_LIST(GattError.GATT_WRONG_STATE),
    BINARY_LIST(GattError.GATT_BUSY),
    DATE_LIST(GattError.GATT_PENDING),
    FLOAT_LIST(GattError.GATT_AUTH_FAIL),
    DOUBLE_LIST(GattError.GATT_MORE),
    DECIMAL128_LIST(GattError.GATT_INVALID_CFG),
    OBJECT_ID_LIST(GattError.GATT_CONGESTED),
    UUID_LIST(145),
    MIXED_LIST(GattError.GATT_CMD_STARTED),
    STRING_TO_INTEGER_MAP(512),
    STRING_TO_BOOLEAN_MAP(InputDeviceCompat.SOURCE_DPAD),
    STRING_TO_STRING_MAP(514),
    STRING_TO_BINARY_MAP(516),
    STRING_TO_DATE_MAP(520),
    STRING_TO_FLOAT_MAP(521),
    STRING_TO_DOUBLE_MAP(522),
    STRING_TO_DECIMAL128_MAP(523),
    STRING_TO_OBJECT_ID_MAP(527),
    STRING_TO_UUID_MAP(529),
    STRING_TO_MIXED_MAP(518),
    STRING_TO_LINK_MAP(524),
    INTEGER_SET(256),
    BOOLEAN_SET(257),
    STRING_SET(258),
    BINARY_SET(260),
    DATE_SET(264),
    FLOAT_SET(265),
    DOUBLE_SET(266),
    DECIMAL128_SET(267),
    OBJECT_ID_SET(271),
    UUID_SET(273),
    LINK_SET(268),
    MIXED_SET(262);

    private static final RealmFieldType[] basicTypes = new RealmFieldType[18];
    private static final RealmFieldType[] listTypes = new RealmFieldType[18];
    private static final RealmFieldType[] mapTypes = new RealmFieldType[18];
    private static final RealmFieldType[] setTypes = new RealmFieldType[18];
    private final int nativeValue;

    static {
        for (RealmFieldType realmFieldType : values()) {
            int i = realmFieldType.nativeValue;
            if (i < 128) {
                basicTypes[i] = realmFieldType;
            } else if (i < 256) {
                listTypes[i - 128] = realmFieldType;
            } else {
                if (i < 512) {
                    setTypes[i - 256] = realmFieldType;
                } else {
                    mapTypes[i - 512] = realmFieldType;
                }
            }
        }
    }

    RealmFieldType(int i) {
        this.nativeValue = i;
    }

    public int getNativeValue() {
        return this.nativeValue;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004c A[FALL_THROUGH, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isValid(java.lang.Object r5) {
        /*
            Method dump skipped, instruction units count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.RealmFieldType.isValid(java.lang.Object):boolean");
    }

    public static RealmFieldType fromNativeValue(int i) {
        RealmFieldType realmFieldType;
        RealmFieldType realmFieldType2;
        RealmFieldType realmFieldType3;
        RealmFieldType realmFieldType4;
        if (i >= 0) {
            RealmFieldType[] realmFieldTypeArr = basicTypes;
            if (i < realmFieldTypeArr.length && (realmFieldType4 = realmFieldTypeArr[i]) != null) {
                return realmFieldType4;
            }
        }
        if (128 <= i && i < 256) {
            int i2 = i - 128;
            RealmFieldType[] realmFieldTypeArr2 = listTypes;
            if (i2 < realmFieldTypeArr2.length && (realmFieldType3 = realmFieldTypeArr2[i2]) != null) {
                return realmFieldType3;
            }
        }
        if (256 <= i && i < 512) {
            int i3 = i - 256;
            RealmFieldType[] realmFieldTypeArr3 = setTypes;
            if (i3 < realmFieldTypeArr3.length && (realmFieldType2 = realmFieldTypeArr3[i3]) != null) {
                return realmFieldType2;
            }
        }
        if (512 <= i) {
            int i4 = i - 512;
            RealmFieldType[] realmFieldTypeArr4 = mapTypes;
            if (i4 < realmFieldTypeArr4.length && (realmFieldType = realmFieldTypeArr4[i4]) != null) {
                return realmFieldType;
            }
        }
        throw new IllegalArgumentException("Invalid native Realm type: " + i);
    }
}

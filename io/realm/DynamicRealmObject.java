package io.realm;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.internal.OsList;
import io.realm.internal.OsMap;
import io.realm.internal.OsSet;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.android.JsonUtils;
import java.lang.reflect.GenericDeclaration;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class DynamicRealmObject extends RealmObject implements RealmObjectProxy {
    static final String MSG_LINK_QUERY_NOT_SUPPORTED = "Queries across relationships are not supported";
    private final ProxyState<DynamicRealmObject> proxyState;

    private enum CollectionType {
        LIST,
        DICTIONARY,
        SET
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
    }

    public DynamicRealmObject(RealmModel realmModel) {
        ProxyState<DynamicRealmObject> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        if (realmModel == null) {
            throw new IllegalArgumentException("A non-null object must be provided.");
        }
        if (realmModel instanceof DynamicRealmObject) {
            throw new IllegalArgumentException("The object is already a DynamicRealmObject: " + realmModel);
        }
        if (!RealmObject.isManaged(realmModel)) {
            throw new IllegalArgumentException("An object managed by Realm must be provided. This is an unmanaged object.");
        }
        if (!RealmObject.isValid(realmModel)) {
            throw new IllegalArgumentException("A valid object managed by Realm must be provided. This object was deleted.");
        }
        RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
        Row row$realm = realmObjectProxy.realmGet$proxyState().getRow$realm();
        proxyState.setRealm$realm(realmObjectProxy.realmGet$proxyState().getRealm$realm());
        proxyState.setRow$realm(((UncheckedRow) row$realm).convertToChecked());
        proxyState.setConstructionFinished();
    }

    DynamicRealmObject(BaseRealm baseRealm, Row row) {
        ProxyState<DynamicRealmObject> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(baseRealm);
        proxyState.setRow$realm(row);
        proxyState.setConstructionFinished();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E> E get(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (this.proxyState.getRow$realm().isNull(columnKey)) {
            return null;
        }
        RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(columnKey);
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[columnType.ordinal()]) {
            case 1:
                return (E) Boolean.valueOf(this.proxyState.getRow$realm().getBoolean(columnKey));
            case 2:
                return (E) Long.valueOf(this.proxyState.getRow$realm().getLong(columnKey));
            case 3:
                return (E) Float.valueOf(this.proxyState.getRow$realm().getFloat(columnKey));
            case 4:
                return (E) Double.valueOf(this.proxyState.getRow$realm().getDouble(columnKey));
            case 5:
                return (E) this.proxyState.getRow$realm().getString(columnKey);
            case 6:
                return (E) this.proxyState.getRow$realm().getBinaryByteArray(columnKey);
            case 7:
                return (E) this.proxyState.getRow$realm().getDate(columnKey);
            case 8:
                return (E) this.proxyState.getRow$realm().getDecimal128(columnKey);
            case 9:
                return (E) this.proxyState.getRow$realm().getObjectId(columnKey);
            case 10:
                return (E) getRealmAny(columnKey);
            case 11:
                return (E) this.proxyState.getRow$realm().getUUID(columnKey);
            case 12:
                return (E) getObject(str);
            case 13:
                return (E) getList(str);
            case 14:
                return (E) getDictionary(str, Integer.class);
            case 15:
                return (E) getDictionary(str, Boolean.class);
            case 16:
                return (E) getDictionary(str, String.class);
            case 17:
                return (E) getDictionary(str, byte[].class);
            case 18:
                return (E) getDictionary(str, Date.class);
            case 19:
                return (E) getDictionary(str, Float.class);
            case 20:
                return (E) getDictionary(str, Double.class);
            case 21:
                return (E) getDictionary(str, Decimal128.class);
            case 22:
                return (E) getDictionary(str, ObjectId.class);
            case 23:
                return (E) getDictionary(str, UUID.class);
            case 24:
                return (E) getDictionary(str, RealmAny.class);
            case 25:
                return (E) getDictionary(str);
            case 26:
                return (E) getRealmSet(str, Integer.class);
            case 27:
                return (E) getRealmSet(str, Boolean.class);
            case 28:
                return (E) getRealmSet(str, String.class);
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                return (E) getRealmSet(str, byte[].class);
            case 30:
                return (E) getRealmSet(str, Date.class);
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                return (E) getRealmSet(str, Float.class);
            case 32:
                return (E) getRealmSet(str, Double.class);
            case 33:
                return (E) getRealmSet(str, Decimal128.class);
            case 34:
                return (E) getRealmSet(str, ObjectId.class);
            case 35:
                return (E) getRealmSet(str, UUID.class);
            case 36:
                return (E) getRealmSet(str);
            case 37:
                return (E) getRealmSet(str, RealmAny.class);
            default:
                throw new IllegalStateException("Field type not supported: " + columnType);
        }
    }

    public boolean getBoolean(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            return this.proxyState.getRow$realm().getBoolean(columnKey);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.BOOLEAN);
            throw e;
        }
    }

    public int getInt(String str) {
        return (int) getLong(str);
    }

    public short getShort(String str) {
        return (short) getLong(str);
    }

    public long getLong(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            return this.proxyState.getRow$realm().getLong(columnKey);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.INTEGER);
            throw e;
        }
    }

    public byte getByte(String str) {
        return (byte) getLong(str);
    }

    public float getFloat(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            return this.proxyState.getRow$realm().getFloat(columnKey);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.FLOAT);
            throw e;
        }
    }

    public double getDouble(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            return this.proxyState.getRow$realm().getDouble(columnKey);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.DOUBLE);
            throw e;
        }
    }

    public byte[] getBlob(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            return this.proxyState.getRow$realm().getBinaryByteArray(columnKey);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.BINARY);
            throw e;
        }
    }

    public String getString(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            return this.proxyState.getRow$realm().getString(columnKey);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.STRING);
            throw e;
        }
    }

    public Date getDate(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        checkFieldType(str, columnKey, RealmFieldType.DATE);
        if (this.proxyState.getRow$realm().isNull(columnKey)) {
            return null;
        }
        return this.proxyState.getRow$realm().getDate(columnKey);
    }

    public Decimal128 getDecimal128(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        checkFieldType(str, columnKey, RealmFieldType.DECIMAL128);
        if (this.proxyState.getRow$realm().isNull(columnKey)) {
            return null;
        }
        return this.proxyState.getRow$realm().getDecimal128(columnKey);
    }

    public ObjectId getObjectId(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        checkFieldType(str, columnKey, RealmFieldType.OBJECT_ID);
        if (this.proxyState.getRow$realm().isNull(columnKey)) {
            return null;
        }
        return this.proxyState.getRow$realm().getObjectId(columnKey);
    }

    public RealmAny getRealmAny(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        checkFieldType(str, columnKey, RealmFieldType.MIXED);
        return getRealmAny(columnKey);
    }

    public UUID getUUID(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        checkFieldType(str, columnKey, RealmFieldType.UUID);
        if (this.proxyState.getRow$realm().isNull(columnKey)) {
            return null;
        }
        return this.proxyState.getRow$realm().getUUID(columnKey);
    }

    @Nullable
    public DynamicRealmObject getObject(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        checkFieldType(str, columnKey, RealmFieldType.OBJECT);
        if (this.proxyState.getRow$realm().isNullLink(columnKey)) {
            return null;
        }
        return new DynamicRealmObject(this.proxyState.getRealm$realm(), this.proxyState.getRow$realm().getTable().getLinkTarget(columnKey).getCheckedRow(this.proxyState.getRow$realm().getLink(columnKey)));
    }

    public RealmList<DynamicRealmObject> getList(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            OsList modelList = this.proxyState.getRow$realm().getModelList(columnKey);
            return new RealmList<>(modelList.getTargetTable().getClassName(), modelList, this.proxyState.getRealm$realm());
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.LIST);
            throw e;
        }
    }

    public <E> RealmList<E> getList(String str, Class<E> cls) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (cls == null) {
            throw new IllegalArgumentException("Non-null 'primitiveType' required.");
        }
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        RealmFieldType realmFieldTypePrimitiveTypeToRealmFieldType = primitiveTypeToRealmFieldType(CollectionType.LIST, cls);
        try {
            return new RealmList<>(cls, this.proxyState.getRow$realm().getValueList(columnKey, realmFieldTypePrimitiveTypeToRealmFieldType), this.proxyState.getRealm$realm());
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, realmFieldTypePrimitiveTypeToRealmFieldType);
            throw e;
        }
    }

    public RealmDictionary<DynamicRealmObject> getDictionary(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            OsMap modelMap = this.proxyState.getRow$realm().getModelMap(columnKey);
            return new RealmDictionary<>(this.proxyState.getRealm$realm(), modelMap, modelMap.getTargetTable().getClassName());
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.STRING_TO_LINK_MAP);
            throw e;
        }
    }

    public <E> RealmDictionary<E> getDictionary(String str, Class<E> cls) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (cls == null) {
            throw new IllegalArgumentException("Non-null 'primitiveType' required.");
        }
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        RealmFieldType realmFieldTypePrimitiveTypeToRealmFieldType = primitiveTypeToRealmFieldType(CollectionType.DICTIONARY, cls);
        try {
            return new RealmDictionary<>(this.proxyState.getRealm$realm(), this.proxyState.getRow$realm().getValueMap(columnKey, realmFieldTypePrimitiveTypeToRealmFieldType), cls);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, realmFieldTypePrimitiveTypeToRealmFieldType);
            throw e;
        }
    }

    public RealmSet<DynamicRealmObject> getRealmSet(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        try {
            OsSet modelSet = this.proxyState.getRow$realm().getModelSet(columnKey);
            return new RealmSet<>(this.proxyState.getRealm$realm(), modelSet, modelSet.getTargetTable().getClassName());
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, RealmFieldType.LINK_SET);
            throw e;
        }
    }

    public <E> RealmSet<E> getRealmSet(String str, Class<E> cls) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (cls == null) {
            throw new IllegalArgumentException("Non-null 'primitiveType' required.");
        }
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        RealmFieldType realmFieldTypePrimitiveTypeToRealmFieldType = primitiveTypeToRealmFieldType(CollectionType.SET, cls);
        try {
            return new RealmSet<>(this.proxyState.getRealm$realm(), this.proxyState.getRow$realm().getValueSet(columnKey, realmFieldTypePrimitiveTypeToRealmFieldType), cls);
        } catch (IllegalArgumentException e) {
            checkFieldType(str, columnKey, realmFieldTypePrimitiveTypeToRealmFieldType);
            throw e;
        }
    }

    private <E> RealmFieldType primitiveTypeToRealmFieldType(CollectionType collectionType, Class<E> cls) {
        int i;
        int iPrimitiveTypeToCoreType = primitiveTypeToCoreType(cls);
        int i2 = AnonymousClass1.$SwitchMap$io$realm$DynamicRealmObject$CollectionType[collectionType.ordinal()];
        if (i2 == 1) {
            i = iPrimitiveTypeToCoreType + 256;
        } else if (i2 == 2) {
            i = iPrimitiveTypeToCoreType + 512;
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException("Type not supported: " + collectionType);
            }
            i = iPrimitiveTypeToCoreType + 128;
        }
        return RealmFieldType.fromNativeValue(i);
    }

    /* JADX INFO: renamed from: io.realm.DynamicRealmObject$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$DynamicRealmObject$CollectionType;

        static {
            int[] iArr = new int[CollectionType.values().length];
            $SwitchMap$io$realm$DynamicRealmObject$CollectionType = iArr;
            try {
                iArr[CollectionType.SET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$DynamicRealmObject$CollectionType[CollectionType.DICTIONARY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$DynamicRealmObject$CollectionType[CollectionType.LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr2;
            try {
                iArr2[RealmFieldType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED.ordinal()] = 10;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID.ordinal()] = 11;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT.ordinal()] = 12;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LIST.ordinal()] = 13;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_INTEGER_MAP.ordinal()] = 14;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_BOOLEAN_MAP.ordinal()] = 15;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_STRING_MAP.ordinal()] = 16;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_BINARY_MAP.ordinal()] = 17;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DATE_MAP.ordinal()] = 18;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_FLOAT_MAP.ordinal()] = 19;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DOUBLE_MAP.ordinal()] = 20;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_DECIMAL128_MAP.ordinal()] = 21;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_OBJECT_ID_MAP.ordinal()] = 22;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_UUID_MAP.ordinal()] = 23;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_MIXED_MAP.ordinal()] = 24;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_TO_LINK_MAP.ordinal()] = 25;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER_SET.ordinal()] = 26;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN_SET.ordinal()] = 27;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_SET.ordinal()] = 28;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY_SET.ordinal()] = 29;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE_SET.ordinal()] = 30;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT_SET.ordinal()] = 31;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE_SET.ordinal()] = 32;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128_SET.ordinal()] = 33;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID_SET.ordinal()] = 34;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID_SET.ordinal()] = 35;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LINK_SET.ordinal()] = 36;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED_SET.ordinal()] = 37;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.LINKING_OBJECTS.ordinal()] = 38;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.INTEGER_LIST.ordinal()] = 39;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BOOLEAN_LIST.ordinal()] = 40;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.STRING_LIST.ordinal()] = 41;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.BINARY_LIST.ordinal()] = 42;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE_LIST.ordinal()] = 43;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT_LIST.ordinal()] = 44;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE_LIST.ordinal()] = 45;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128_LIST.ordinal()] = 46;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.OBJECT_ID_LIST.ordinal()] = 47;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.UUID_LIST.ordinal()] = 48;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED_LIST.ordinal()] = 49;
            } catch (NoSuchFieldError unused52) {
            }
        }
    }

    private <E> int primitiveTypeToCoreType(Class<E> cls) {
        if (cls.equals(Integer.class) || cls.equals(Long.class) || cls.equals(Short.class) || cls.equals(Byte.class)) {
            return 0;
        }
        if (cls.equals(Boolean.class)) {
            return 1;
        }
        if (cls.equals(String.class)) {
            return 2;
        }
        if (cls.equals(byte[].class)) {
            return 4;
        }
        if (cls.equals(Date.class)) {
            return 8;
        }
        if (cls.equals(Float.class)) {
            return 9;
        }
        if (cls.equals(Double.class)) {
            return 10;
        }
        if (cls.equals(Decimal128.class)) {
            return 11;
        }
        if (cls.equals(ObjectId.class)) {
            return 15;
        }
        if (cls.equals(UUID.class)) {
            return 17;
        }
        if (cls.equals(RealmAny.class)) {
            return 6;
        }
        throw new IllegalArgumentException("Unsupported element type. Only primitive types supported. Yours was: " + cls);
    }

    public boolean isNull(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[this.proxyState.getRow$realm().getColumnType(columnKey).ordinal()]) {
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
                return this.proxyState.getRow$realm().isNull(columnKey);
            case 12:
                return this.proxyState.getRow$realm().isNullLink(columnKey);
            default:
                return false;
        }
    }

    public boolean hasField(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null || str.isEmpty()) {
            return false;
        }
        return this.proxyState.getRow$realm().hasColumn(str);
    }

    public String[] getFieldNames() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getColumnNames();
    }

    public void set(String str, Object obj) {
        this.proxyState.getRealm$realm().checkIfValid();
        boolean z = obj instanceof String;
        String str2 = z ? (String) obj : null;
        RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(this.proxyState.getRow$realm().getColumnKey(str));
        if (z && columnType != RealmFieldType.STRING) {
            switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[columnType.ordinal()]) {
                case 1:
                    obj = Boolean.valueOf(Boolean.parseBoolean(str2));
                    break;
                case 2:
                    obj = Long.valueOf(Long.parseLong(str2));
                    break;
                case 3:
                    obj = Float.valueOf(Float.parseFloat(str2));
                    break;
                case 4:
                    obj = Double.valueOf(Double.parseDouble(str2));
                    break;
                case 5:
                case 6:
                default:
                    throw new IllegalArgumentException(String.format(Locale.US, "Field %s is not a String field, and the provide value could not be automatically converted: %s. Use a typedsetter instead", str, obj));
                case 7:
                    obj = JsonUtils.stringToDate(str2);
                    break;
                case 8:
                    obj = Decimal128.parse(str2);
                    break;
                case 9:
                    obj = new ObjectId(str2);
                    break;
                case 10:
                    obj = RealmAny.valueOf(str2);
                    break;
                case 11:
                    obj = UUID.fromString(str2);
                    break;
            }
        }
        if (obj == null) {
            setNull(str);
        } else {
            setValue(str, obj);
        }
    }

    private void setValue(String str, Object obj) {
        Class<?> cls = obj.getClass();
        if (cls == Boolean.class) {
            setBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (cls == Short.class) {
            setShort(str, ((Short) obj).shortValue());
            return;
        }
        if (cls == Integer.class) {
            setInt(str, ((Integer) obj).intValue());
            return;
        }
        if (cls == Long.class) {
            setLong(str, ((Long) obj).longValue());
            return;
        }
        if (cls == Byte.class) {
            setByte(str, ((Byte) obj).byteValue());
            return;
        }
        if (cls == Float.class) {
            setFloat(str, ((Float) obj).floatValue());
            return;
        }
        if (cls == Double.class) {
            setDouble(str, ((Double) obj).doubleValue());
            return;
        }
        if (cls == String.class) {
            setString(str, (String) obj);
            return;
        }
        if (obj instanceof Date) {
            setDate(str, (Date) obj);
            return;
        }
        if (obj instanceof byte[]) {
            setBlob(str, (byte[]) obj);
            return;
        }
        if (cls == DynamicRealmObject.class) {
            setObject(str, (DynamicRealmObject) obj);
            return;
        }
        if (cls == RealmList.class) {
            setList(str, (RealmList) obj);
            return;
        }
        if (cls == Decimal128.class) {
            setDecimal128(str, (Decimal128) obj);
            return;
        }
        if (cls == ObjectId.class) {
            setObjectId(str, (ObjectId) obj);
            return;
        }
        if (cls == UUID.class) {
            setUUID(str, (UUID) obj);
        } else if (cls == RealmAny.class) {
            setRealmAny(str, (RealmAny) obj);
        } else {
            throw new IllegalArgumentException("Value is of an type not supported: " + obj.getClass());
        }
    }

    public void setBoolean(String str, boolean z) {
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.proxyState.getRow$realm().getColumnKey(str), z);
    }

    public void setShort(String str, short s) {
        this.proxyState.getRealm$realm().checkIfValid();
        checkIsPrimaryKey(str);
        this.proxyState.getRow$realm().setLong(this.proxyState.getRow$realm().getColumnKey(str), s);
    }

    public void setInt(String str, int i) {
        this.proxyState.getRealm$realm().checkIfValid();
        checkIsPrimaryKey(str);
        this.proxyState.getRow$realm().setLong(this.proxyState.getRow$realm().getColumnKey(str), i);
    }

    public void setLong(String str, long j) {
        this.proxyState.getRealm$realm().checkIfValid();
        checkIsPrimaryKey(str);
        this.proxyState.getRow$realm().setLong(this.proxyState.getRow$realm().getColumnKey(str), j);
    }

    public void setByte(String str, byte b) {
        this.proxyState.getRealm$realm().checkIfValid();
        checkIsPrimaryKey(str);
        this.proxyState.getRow$realm().setLong(this.proxyState.getRow$realm().getColumnKey(str), b);
    }

    public void setFloat(String str, float f) {
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setFloat(this.proxyState.getRow$realm().getColumnKey(str), f);
    }

    public void setDouble(String str, double d) {
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setDouble(this.proxyState.getRow$realm().getColumnKey(str), d);
    }

    public void setString(String str, @Nullable String str2) {
        this.proxyState.getRealm$realm().checkIfValid();
        checkIsPrimaryKey(str);
        this.proxyState.getRow$realm().setString(this.proxyState.getRow$realm().getColumnKey(str), str2);
    }

    public void setBlob(String str, @Nullable byte[] bArr) {
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBinaryByteArray(this.proxyState.getRow$realm().getColumnKey(str), bArr);
    }

    public void setDate(String str, @Nullable Date date) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (date == null) {
            this.proxyState.getRow$realm().setNull(columnKey);
        } else {
            this.proxyState.getRow$realm().setDate(columnKey, date);
        }
    }

    public void setDecimal128(String str, @Nullable Decimal128 decimal128) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (decimal128 == null) {
            this.proxyState.getRow$realm().setNull(columnKey);
        } else {
            this.proxyState.getRow$realm().setDecimal128(columnKey, decimal128);
        }
    }

    public void setObjectId(String str, @Nullable ObjectId objectId) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (objectId == null) {
            this.proxyState.getRow$realm().setNull(columnKey);
        } else {
            this.proxyState.getRow$realm().setObjectId(columnKey, objectId);
        }
    }

    public void setRealmAny(String str, @Nullable RealmAny realmAny) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (realmAny == null) {
            this.proxyState.getRow$realm().setNull(columnKey);
        } else {
            this.proxyState.getRow$realm().setRealmAny(columnKey, realmAny.getNativePtr());
        }
    }

    public void setUUID(String str, @Nullable UUID uuid) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (uuid == null) {
            this.proxyState.getRow$realm().setNull(columnKey);
        } else {
            this.proxyState.getRow$realm().setUUID(columnKey, uuid);
        }
    }

    public void setObject(String str, @Nullable DynamicRealmObject dynamicRealmObject) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (dynamicRealmObject == null) {
            this.proxyState.getRow$realm().nullifyLink(columnKey);
            return;
        }
        if (dynamicRealmObject.proxyState.getRealm$realm() == null || dynamicRealmObject.proxyState.getRow$realm() == null) {
            throw new IllegalArgumentException("Cannot link to objects that are not part of the Realm.");
        }
        if (this.proxyState.getRealm$realm() != dynamicRealmObject.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("Cannot add an object from another Realm instance.");
        }
        Table linkTarget = this.proxyState.getRow$realm().getTable().getLinkTarget(columnKey);
        Table table = dynamicRealmObject.proxyState.getRow$realm().getTable();
        if (!linkTarget.hasSameSchema(table)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Type of object is wrong. Was %s, expected %s", table.getName(), linkTarget.getName()));
        }
        this.proxyState.getRow$realm().setLink(columnKey, dynamicRealmObject.proxyState.getRow$realm().getObjectKey());
    }

    public <E> void setList(String str, RealmList<E> realmList) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (realmList == null) {
            throw new IllegalArgumentException("Non-null 'list' required");
        }
        RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(this.proxyState.getRow$realm().getColumnKey(str));
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[columnType.ordinal()];
        if (i == 13) {
            if (!realmList.isEmpty()) {
                E eFirst = realmList.first();
                if (!(eFirst instanceof DynamicRealmObject) && RealmModel.class.isAssignableFrom(eFirst.getClass())) {
                    throw new IllegalArgumentException("RealmList must contain `DynamicRealmObject's, not Java model classes.");
                }
            }
            setModelList(str, realmList);
            return;
        }
        switch (i) {
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
            case 49:
                setValueList(str, realmList, columnType);
                return;
            default:
                throw new IllegalArgumentException(String.format("Field '%s' is not a list but a %s", str, columnType));
        }
    }

    private void setModelList(String str, RealmList<DynamicRealmObject> realmList) {
        boolean z;
        OsList modelList = this.proxyState.getRow$realm().getModelList(this.proxyState.getRow$realm().getColumnKey(str));
        Table targetTable = modelList.getTargetTable();
        String className = targetTable.getClassName();
        if (realmList.className == null && realmList.clazz == null) {
            z = false;
        } else {
            String className2 = realmList.className != null ? realmList.className : this.proxyState.getRealm$realm().getSchema().getTable(realmList.clazz).getClassName();
            if (!className.equals(className2)) {
                throw new IllegalArgumentException(String.format(Locale.US, "The elements in the list are not the proper type. Was %s expected %s.", className2, className));
            }
            z = true;
        }
        int size = realmList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            DynamicRealmObject dynamicRealmObject = realmList.get(i);
            if (dynamicRealmObject.realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element in 'list' must belong to the same Realm instance.");
            }
            if (!z && !targetTable.hasSameSchema(dynamicRealmObject.realmGet$proxyState().getRow$realm().getTable())) {
                throw new IllegalArgumentException(String.format(Locale.US, "Element at index %d is not the proper type. Was '%s' expected '%s'.", Integer.valueOf(i), dynamicRealmObject.realmGet$proxyState().getRow$realm().getTable().getClassName(), className));
            }
            jArr[i] = dynamicRealmObject.realmGet$proxyState().getRow$realm().getObjectKey();
        }
        modelList.removeAll();
        for (int i2 = 0; i2 < size; i2++) {
            modelList.addRow(jArr[i2]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <E> void setValueList(String str, RealmList<E> realmList, RealmFieldType realmFieldType) {
        GenericDeclaration genericDeclaration;
        OsList valueList = this.proxyState.getRow$realm().getValueList(this.proxyState.getRow$realm().getColumnKey(str), realmFieldType);
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()]) {
            case 39:
                genericDeclaration = Long.class;
                break;
            case 40:
                genericDeclaration = Boolean.class;
                break;
            case 41:
                genericDeclaration = String.class;
                break;
            case 42:
                genericDeclaration = byte[].class;
                break;
            case 43:
                genericDeclaration = Date.class;
                break;
            case 44:
                genericDeclaration = Float.class;
                break;
            case 45:
                genericDeclaration = Double.class;
                break;
            case 46:
                genericDeclaration = Decimal128.class;
                break;
            case 47:
                genericDeclaration = ObjectId.class;
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                genericDeclaration = UUID.class;
                break;
            case 49:
                genericDeclaration = RealmAny.class;
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + realmFieldType);
        }
        ManagedListOperator operator = getOperator(this.proxyState.getRealm$realm(), valueList, realmFieldType, genericDeclaration);
        if (realmList.isManaged() && valueList.size() == realmList.size()) {
            int size = realmList.size();
            Iterator<E> it = realmList.iterator();
            for (int i = 0; i < size; i++) {
                operator.set(i, it.next());
            }
            return;
        }
        valueList.removeAll();
        Iterator<E> it2 = realmList.iterator();
        while (it2.hasNext()) {
            operator.append(it2.next());
        }
    }

    private <E> ManagedListOperator<E> getOperator(BaseRealm baseRealm, OsList osList, RealmFieldType realmFieldType, Class<E> cls) {
        if (realmFieldType == RealmFieldType.STRING_LIST) {
            return new StringListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.INTEGER_LIST) {
            return new LongListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.BOOLEAN_LIST) {
            return new BooleanListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.BINARY_LIST) {
            return new BinaryListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.DOUBLE_LIST) {
            return new DoubleListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.FLOAT_LIST) {
            return new FloatListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.DATE_LIST) {
            return new DateListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.DECIMAL128_LIST) {
            return new Decimal128ListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.OBJECT_ID_LIST) {
            return new ObjectIdListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.UUID_LIST) {
            return new UUIDListOperator(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.MIXED_LIST) {
            return new RealmAnyListOperator(baseRealm, osList, cls);
        }
        throw new IllegalArgumentException("Unexpected list type: " + realmFieldType.name());
    }

    public <E> void setDictionary(String str, RealmDictionary<E> realmDictionary) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (realmDictionary == null) {
            throw new IllegalArgumentException("Non-null 'dictionary' required");
        }
        RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(this.proxyState.getRow$realm().getColumnKey(str));
        switch (columnType) {
            case STRING_TO_INTEGER_MAP:
            case STRING_TO_BOOLEAN_MAP:
            case STRING_TO_STRING_MAP:
            case STRING_TO_BINARY_MAP:
            case STRING_TO_DATE_MAP:
            case STRING_TO_FLOAT_MAP:
            case STRING_TO_DOUBLE_MAP:
            case STRING_TO_DECIMAL128_MAP:
            case STRING_TO_OBJECT_ID_MAP:
            case STRING_TO_UUID_MAP:
            case STRING_TO_MIXED_MAP:
                setValueDictionary(str, realmDictionary, columnType);
                return;
            case STRING_TO_LINK_MAP:
                setModelDictionary(str, realmDictionary);
                return;
            default:
                throw new IllegalArgumentException(String.format("Field '%s' is not a dictionary but a %s", str, columnType));
        }
    }

    private void setModelDictionary(String str, RealmDictionary<DynamicRealmObject> realmDictionary) {
        boolean z;
        OsMap modelMap = this.proxyState.getRow$realm().getModelMap(this.proxyState.getRow$realm().getColumnKey(str));
        Table targetTable = modelMap.getTargetTable();
        String className = targetTable.getClassName();
        if (realmDictionary.isManaged()) {
            String valueClassName = realmDictionary.getValueClassName() != null ? realmDictionary.getValueClassName() : this.proxyState.getRealm$realm().getSchema().getTable(realmDictionary.getValueClass()).getClassName();
            if (!className.equals(valueClassName)) {
                throw new IllegalArgumentException(String.format(Locale.US, "The elements in the dictionary are not the proper type. Was %s expected %s.", valueClassName, className));
            }
            z = true;
        } else {
            z = false;
        }
        RealmDictionary realmDictionary2 = new RealmDictionary();
        for (Map.Entry<String, DynamicRealmObject> entry : realmDictionary.entrySet()) {
            DynamicRealmObject value = entry.getValue();
            if (value.realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element in 'dictionary' must belong to the same Realm instance.");
            }
            if (!z && !targetTable.hasSameSchema(value.realmGet$proxyState().getRow$realm().getTable())) {
                throw new IllegalArgumentException(String.format(Locale.US, "Element with key %s is not the proper type. Was '%s' expected '%s'.", entry.getKey(), value.realmGet$proxyState().getRow$realm().getTable().getClassName(), className));
            }
            realmDictionary2.put(entry.getKey(), Long.valueOf(value.realmGet$proxyState().getRow$realm().getObjectKey()));
        }
        modelMap.clear();
        Iterator it = realmDictionary2.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it.next();
            modelMap.putRow(entry2.getKey(), ((Long) entry2.getValue()).longValue());
        }
    }

    private <E> void setValueDictionary(String str, RealmDictionary<E> realmDictionary, RealmFieldType realmFieldType) {
        Class cls;
        OsMap valueMap = this.proxyState.getRow$realm().getValueMap(this.proxyState.getRow$realm().getColumnKey(str), realmFieldType);
        switch (realmFieldType) {
            case STRING_TO_INTEGER_MAP:
                cls = Long.class;
                break;
            case STRING_TO_BOOLEAN_MAP:
                cls = Boolean.class;
                break;
            case STRING_TO_STRING_MAP:
                cls = String.class;
                break;
            case STRING_TO_BINARY_MAP:
                cls = byte[].class;
                break;
            case STRING_TO_DATE_MAP:
                cls = Date.class;
                break;
            case STRING_TO_FLOAT_MAP:
                cls = Float.class;
                break;
            case STRING_TO_DOUBLE_MAP:
                cls = Double.class;
                break;
            case STRING_TO_DECIMAL128_MAP:
                cls = Decimal128.class;
                break;
            case STRING_TO_OBJECT_ID_MAP:
                cls = ObjectId.class;
                break;
            case STRING_TO_UUID_MAP:
                cls = UUID.class;
                break;
            case STRING_TO_MIXED_MAP:
                cls = RealmAny.class;
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + realmFieldType);
        }
        RealmDictionary realmDictionary2 = new RealmDictionary(this.proxyState.getRealm$realm(), valueMap, cls);
        RealmDictionary realmDictionary3 = new RealmDictionary();
        for (Map.Entry<String, E> entry : realmDictionary.entrySet()) {
            realmDictionary3.put(entry.getKey(), entry.getValue());
        }
        valueMap.clear();
        Iterator it = realmDictionary3.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it.next();
            realmDictionary2.put((String) entry2.getKey(), entry2.getValue());
        }
    }

    public <E> void setRealmSet(String str, RealmSet<E> realmSet) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (realmSet == null) {
            throw new IllegalArgumentException("Non-null 'set' required");
        }
        RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(this.proxyState.getRow$realm().getColumnKey(str));
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[columnType.ordinal()]) {
            case 26:
            case 27:
            case 28:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
            case 30:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
                setValueSet(str, realmSet, columnType);
                return;
            case 36:
                setModelSet(str, realmSet);
                return;
            default:
                throw new IllegalArgumentException(String.format("Field '%s' is not a set but a %s", str, columnType));
        }
    }

    private void setModelSet(String str, RealmSet<DynamicRealmObject> realmSet) {
        boolean z;
        OsSet modelSet = this.proxyState.getRow$realm().getModelSet(this.proxyState.getRow$realm().getColumnKey(str));
        Table targetTable = modelSet.getTargetTable();
        String className = targetTable.getClassName();
        if (realmSet.isManaged()) {
            String valueClassName = realmSet.getValueClassName() != null ? realmSet.getValueClassName() : this.proxyState.getRealm$realm().getSchema().getTable(realmSet.getValueClass()).getClassName();
            if (!className.equals(valueClassName)) {
                throw new IllegalArgumentException(String.format(Locale.US, "The elements in the set are not the proper type. Was %s expected %s.", valueClassName, className));
            }
            z = true;
        } else {
            z = false;
        }
        RealmSet realmSet2 = new RealmSet();
        for (DynamicRealmObject dynamicRealmObject : realmSet) {
            if (dynamicRealmObject.realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element in 'set' must belong to the same Realm instance.");
            }
            if (!z && !targetTable.hasSameSchema(dynamicRealmObject.realmGet$proxyState().getRow$realm().getTable())) {
                throw new IllegalArgumentException(String.format(Locale.US, "Set contains an element with not the proper type. Was '%s' expected '%s'.", dynamicRealmObject.realmGet$proxyState().getRow$realm().getTable().getClassName(), className));
            }
            realmSet2.add(Long.valueOf(dynamicRealmObject.realmGet$proxyState().getRow$realm().getObjectKey()));
        }
        modelSet.clear();
        Iterator it = realmSet2.iterator();
        while (it.hasNext()) {
            modelSet.addRow(((Long) it.next()).longValue());
        }
    }

    private <E> void setValueSet(String str, RealmSet<E> realmSet, RealmFieldType realmFieldType) {
        Class cls;
        OsSet valueSet = this.proxyState.getRow$realm().getValueSet(this.proxyState.getRow$realm().getColumnKey(str), realmFieldType);
        switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()]) {
            case 26:
                cls = Number.class;
                break;
            case 27:
                cls = Boolean.class;
                break;
            case 28:
                cls = String.class;
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                cls = byte[].class;
                break;
            case 30:
                cls = Date.class;
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                cls = Float.class;
                break;
            case 32:
                cls = Double.class;
                break;
            case 33:
                cls = Decimal128.class;
                break;
            case 34:
                cls = ObjectId.class;
                break;
            case 35:
                cls = UUID.class;
                break;
            case 36:
            default:
                throw new IllegalArgumentException("Unsupported type: " + realmFieldType);
            case 37:
                cls = RealmAny.class;
                break;
        }
        RealmSet realmSet2 = new RealmSet(this.proxyState.getRealm$realm(), valueSet, cls);
        RealmSet realmSet3 = new RealmSet();
        realmSet3.addAll(realmSet);
        valueSet.clear();
        realmSet2.addAll(realmSet3);
    }

    public void setNull(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
        if (this.proxyState.getRow$realm().getColumnType(columnKey) == RealmFieldType.OBJECT) {
            this.proxyState.getRow$realm().nullifyLink(columnKey);
        } else {
            checkIsPrimaryKey(str);
            this.proxyState.getRow$realm().setNull(columnKey);
        }
    }

    public String getType() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getTable().getClassName();
    }

    public RealmFieldType getFieldType(String str) {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getColumnType(this.proxyState.getRow$realm().getColumnKey(str));
    }

    private void checkFieldType(String str, long j, RealmFieldType realmFieldType) {
        RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(j);
        if (columnType != realmFieldType) {
            RealmFieldType realmFieldType2 = RealmFieldType.INTEGER;
            String str2 = GlobalVariable.n;
            String str3 = (realmFieldType == realmFieldType2 || realmFieldType == RealmFieldType.OBJECT) ? GlobalVariable.n : "";
            if (columnType != RealmFieldType.INTEGER && columnType != RealmFieldType.OBJECT) {
                str2 = "";
            }
            throw new IllegalArgumentException(String.format(Locale.US, "'%s' is not a%s '%s', but a%s '%s'.", str, str3, realmFieldType, str2, columnType));
        }
    }

    public int hashCode() {
        this.proxyState.getRealm$realm().checkIfValid();
        String path = this.proxyState.getRealm$realm().getPath();
        String name = this.proxyState.getRow$realm().getTable().getName();
        long objectKey = this.proxyState.getRow$realm().getObjectKey();
        return ((((527 + (path != null ? path.hashCode() : 0)) * 31) + (name != null ? name.hashCode() : 0)) * 31) + ((int) ((objectKey >>> 32) ^ objectKey));
    }

    public boolean equals(Object obj) {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DynamicRealmObject dynamicRealmObject = (DynamicRealmObject) obj;
            String path = this.proxyState.getRealm$realm().getPath();
            String path2 = dynamicRealmObject.proxyState.getRealm$realm().getPath();
            if (path == null ? path2 != null : !path.equals(path2)) {
                return false;
            }
            String name = this.proxyState.getRow$realm().getTable().getName();
            String name2 = dynamicRealmObject.proxyState.getRow$realm().getTable().getName();
            if (name == null ? name2 != null : !name.equals(name2)) {
                return false;
            }
            if (this.proxyState.getRow$realm().getObjectKey() == dynamicRealmObject.proxyState.getRow$realm().getObjectKey()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (!this.proxyState.getRow$realm().isValid()) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder(this.proxyState.getRow$realm().getTable().getClassName() + " = dynamic[");
        String[] fieldNames = getFieldNames();
        int length = fieldNames.length;
        for (int i = 0; i < length; i++) {
            String str = fieldNames[i];
            long columnKey = this.proxyState.getRow$realm().getColumnKey(str);
            RealmFieldType columnType = this.proxyState.getRow$realm().getColumnType(columnKey);
            sb.append("{");
            sb.append(str);
            sb.append(":");
            int i2 = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[columnType.ordinal()];
            String str2 = GlobalVariable.nullColor;
            switch (i2) {
                case 1:
                    Object objValueOf = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        objValueOf = Boolean.valueOf(this.proxyState.getRow$realm().getBoolean(columnKey));
                    }
                    sb.append(objValueOf);
                    break;
                case 2:
                    Object objValueOf2 = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        objValueOf2 = Long.valueOf(this.proxyState.getRow$realm().getLong(columnKey));
                    }
                    sb.append(objValueOf2);
                    break;
                case 3:
                    Object objValueOf3 = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        objValueOf3 = Float.valueOf(this.proxyState.getRow$realm().getFloat(columnKey));
                    }
                    sb.append(objValueOf3);
                    break;
                case 4:
                    Object objValueOf4 = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        objValueOf4 = Double.valueOf(this.proxyState.getRow$realm().getDouble(columnKey));
                    }
                    sb.append(objValueOf4);
                    break;
                case 5:
                    sb.append(this.proxyState.getRow$realm().getString(columnKey));
                    break;
                case 6:
                    sb.append(Arrays.toString(this.proxyState.getRow$realm().getBinaryByteArray(columnKey)));
                    break;
                case 7:
                    Object date = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        date = this.proxyState.getRow$realm().getDate(columnKey);
                    }
                    sb.append(date);
                    break;
                case 8:
                    Object decimal128 = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        decimal128 = this.proxyState.getRow$realm().getDecimal128(columnKey);
                    }
                    sb.append(decimal128);
                    break;
                case 9:
                    Object objectId = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        objectId = this.proxyState.getRow$realm().getObjectId(columnKey);
                    }
                    sb.append(objectId);
                    break;
                case 10:
                    Object realmAny = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        realmAny = getRealmAny(columnKey);
                    }
                    sb.append(realmAny);
                    break;
                case 11:
                    Object uuid = str2;
                    if (!this.proxyState.getRow$realm().isNull(columnKey)) {
                        uuid = this.proxyState.getRow$realm().getUUID(columnKey);
                    }
                    sb.append(uuid);
                    break;
                case 12:
                    String className = str2;
                    if (!this.proxyState.getRow$realm().isNullLink(columnKey)) {
                        className = this.proxyState.getRow$realm().getTable().getLinkTarget(columnKey).getClassName();
                    }
                    sb.append(className);
                    break;
                case 13:
                    sb.append(String.format(Locale.US, "RealmList<%s>[%s]", this.proxyState.getRow$realm().getTable().getLinkTarget(columnKey).getClassName(), Long.valueOf(this.proxyState.getRow$realm().getModelList(columnKey).size())));
                    break;
                case 14:
                    sb.append(String.format(Locale.US, "RealmDictionary<Long>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 15:
                    sb.append(String.format(Locale.US, "RealmDictionary<Boolean>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 16:
                    sb.append(String.format(Locale.US, "RealmDictionary<String>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 17:
                    sb.append(String.format(Locale.US, "RealmDictionary<byte[]>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 18:
                    sb.append(String.format(Locale.US, "RealmDictionary<Date>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 19:
                    sb.append(String.format(Locale.US, "RealmDictionary<Float>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 20:
                    sb.append(String.format(Locale.US, "RealmDictionary<Double>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 21:
                    sb.append(String.format(Locale.US, "RealmDictionary<Decimal128>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 22:
                    sb.append(String.format(Locale.US, "RealmDictionary<ObjectId>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 23:
                    sb.append(String.format(Locale.US, "RealmDictionary<UUID>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 24:
                    sb.append(String.format(Locale.US, "RealmDictionary<RealmAny>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueMap(columnKey, columnType).size())));
                    break;
                case 25:
                    sb.append(String.format(Locale.US, "RealmDictionary<%s>[%s]", this.proxyState.getRow$realm().getTable().getLinkTarget(columnKey).getClassName(), Long.valueOf(this.proxyState.getRow$realm().getModelMap(columnKey).size())));
                    break;
                case 26:
                    sb.append(String.format(Locale.US, "RealmSet<Long>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 27:
                    sb.append(String.format(Locale.US, "RealmSet<Boolean>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 28:
                    sb.append(String.format(Locale.US, "RealmSet<String>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                    sb.append(String.format(Locale.US, "RealmSet<byte[]>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 30:
                    sb.append(String.format(Locale.US, "RealmSet<Date>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                    sb.append(String.format(Locale.US, "RealmSet<Float>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 32:
                    sb.append(String.format(Locale.US, "RealmSet<Double>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 33:
                    sb.append(String.format(Locale.US, "RealmSet<Decimal128>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 34:
                    sb.append(String.format(Locale.US, "RealmSet<ObjectId>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 35:
                    sb.append(String.format(Locale.US, "RealmSet<UUID>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 36:
                    sb.append(String.format(Locale.US, "RealmSet<%s>[%s]", this.proxyState.getRow$realm().getTable().getLinkTarget(columnKey).getClassName(), Long.valueOf(this.proxyState.getRow$realm().getModelSet(columnKey).size())));
                    break;
                case 37:
                    sb.append(String.format(Locale.US, "RealmSet<RealmAny>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueSet(columnKey, columnType).size())));
                    break;
                case 38:
                default:
                    sb.append("?");
                    break;
                case 39:
                    sb.append(String.format(Locale.US, "RealmList<Long>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 40:
                    sb.append(String.format(Locale.US, "RealmList<Boolean>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 41:
                    sb.append(String.format(Locale.US, "RealmList<String>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 42:
                    sb.append(String.format(Locale.US, "RealmList<byte[]>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 43:
                    sb.append(String.format(Locale.US, "RealmList<Date>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 44:
                    sb.append(String.format(Locale.US, "RealmList<Float>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 45:
                    sb.append(String.format(Locale.US, "RealmList<Double>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 46:
                    sb.append(String.format(Locale.US, "RealmList<Decimal128>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 47:
                    sb.append(String.format(Locale.US, "RealmList<ObjectId>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                    sb.append(String.format(Locale.US, "RealmList<UUID>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case 49:
                    sb.append(String.format(Locale.US, "RealmList<RealmAny>[%s]", Long.valueOf(this.proxyState.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
            }
            sb.append("},");
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append("]");
        return sb.toString();
    }

    private RealmAny getRealmAny(long j) {
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.proxyState.getRealm$realm(), this.proxyState.getRow$realm().getNativeRealmAny(j)));
    }

    public RealmResults<DynamicRealmObject> linkingObjects(String str, String str2) {
        DynamicRealm dynamicRealm = (DynamicRealm) this.proxyState.getRealm$realm();
        dynamicRealm.checkIfValid();
        this.proxyState.getRow$realm().checkIfAttached();
        RealmObjectSchema realmObjectSchema = dynamicRealm.getSchema().get(str);
        if (realmObjectSchema == null) {
            throw new IllegalArgumentException("Class not found: " + str);
        }
        if (str2 == null) {
            throw new IllegalArgumentException("Non-null 'srcFieldName' required.");
        }
        if (str2.contains(".")) {
            throw new IllegalArgumentException(MSG_LINK_QUERY_NOT_SUPPORTED);
        }
        RealmFieldType fieldType = realmObjectSchema.getFieldType(str2);
        if (fieldType != RealmFieldType.OBJECT && fieldType != RealmFieldType.LIST) {
            throw new IllegalArgumentException(String.format(Locale.US, "Unexpected field type: %1$s. Field type should be either %2$s.%3$s or %2$s.%4$s.", fieldType.name(), "RealmFieldType", RealmFieldType.OBJECT.name(), RealmFieldType.LIST.name()));
        }
        return RealmResults.createDynamicBacklinkResults(dynamicRealm, (UncheckedRow) this.proxyState.getRow$realm(), realmObjectSchema.getTable(), str2);
    }

    public DynamicRealm getDynamicRealm() {
        BaseRealm realm$realm = realmGet$proxyState().getRealm$realm();
        realm$realm.checkIfValid();
        if (!isValid()) {
            throw new IllegalStateException("the object is already deleted.");
        }
        return (DynamicRealm) realm$realm;
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState realmGet$proxyState() {
        return this.proxyState;
    }

    private void checkIsPrimaryKey(String str) {
        RealmObjectSchema schemaForClass = this.proxyState.getRealm$realm().getSchema().getSchemaForClass(getType());
        if (schemaForClass.hasPrimaryKey() && schemaForClass.getPrimaryKey().equals(str)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Primary key field '%s' cannot be changed after object was created.", str));
        }
    }
}

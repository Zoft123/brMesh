package io.realm.internal.objectstore;

import io.realm.ImportFlag;
import io.realm.MutableRealmInteger;
import io.realm.RealmAny;
import io.realm.RealmAnyNativeFunctionsImpl;
import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmSet;
import io.realm.internal.NativeContext;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmAnyNativeFunctions;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import java.io.Closeable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class OsObjectBuilder implements Closeable {
    private final long builderPtr;
    private final NativeContext context;
    private final boolean ignoreFieldsWithSameValue;
    private final long sharedRealmPtr;
    private final Table table;
    private final long tablePtr;
    private static ItemCallback<? extends RealmModel> objectItemCallback = new ItemCallback<RealmModel>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.1
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, RealmModel realmModel) {
            OsObjectBuilder.nativeAddIntegerListItem(j, ((UncheckedRow) ((RealmObjectProxy) realmModel).realmGet$proxyState().getRow$realm()).getNativePtr());
        }
    };
    private static ItemCallback<String> stringItemCallback = new ItemCallback<String>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.2
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, String str) {
            OsObjectBuilder.nativeAddStringListItem(j, str);
        }
    };
    private static ItemCallback<Byte> byteItemCallback = new ItemCallback<Byte>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.3
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Byte b) {
            OsObjectBuilder.nativeAddIntegerListItem(j, b.longValue());
        }
    };
    private static ItemCallback<Short> shortItemCallback = new ItemCallback<Short>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.4
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Short sh) {
            OsObjectBuilder.nativeAddIntegerListItem(j, sh.shortValue());
        }
    };
    private static ItemCallback<Integer> integerItemCallback = new ItemCallback<Integer>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.5
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Integer num) {
            OsObjectBuilder.nativeAddIntegerListItem(j, num.intValue());
        }
    };
    private static ItemCallback<Long> longItemCallback = new ItemCallback<Long>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.6
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Long l) {
            OsObjectBuilder.nativeAddIntegerListItem(j, l.longValue());
        }
    };
    private static ItemCallback<Boolean> booleanItemCallback = new ItemCallback<Boolean>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.7
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Boolean bool) {
            OsObjectBuilder.nativeAddBooleanListItem(j, bool.booleanValue());
        }
    };
    private static ItemCallback<Float> floatItemCallback = new ItemCallback<Float>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.8
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Float f) {
            OsObjectBuilder.nativeAddFloatListItem(j, f.floatValue());
        }
    };
    private static ItemCallback<Double> doubleItemCallback = new ItemCallback<Double>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.9
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Double d) {
            OsObjectBuilder.nativeAddDoubleListItem(j, d.doubleValue());
        }
    };
    private static ItemCallback<Date> dateItemCallback = new ItemCallback<Date>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.10
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Date date) {
            OsObjectBuilder.nativeAddDateListItem(j, date.getTime());
        }
    };
    private static ItemCallback<byte[]> byteArrayItemCallback = new ItemCallback<byte[]>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.11
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, byte[] bArr) {
            OsObjectBuilder.nativeAddByteArrayListItem(j, bArr);
        }
    };
    private static ItemCallback<MutableRealmInteger> mutableRealmIntegerItemCallback = new ItemCallback<MutableRealmInteger>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.12
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, MutableRealmInteger mutableRealmInteger) {
            Long l = mutableRealmInteger.get();
            if (l == null) {
                OsObjectBuilder.nativeAddNullListItem(j);
            } else {
                OsObjectBuilder.nativeAddIntegerListItem(j, l.longValue());
            }
        }
    };
    private static ItemCallback<Decimal128> decimal128ItemCallback = new ItemCallback<Decimal128>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.13
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Decimal128 decimal128) {
            OsObjectBuilder.nativeAddDecimal128ListItem(j, decimal128.getLow(), decimal128.getHigh());
        }
    };
    private static ItemCallback<ObjectId> objectIdItemCallback = new ItemCallback<ObjectId>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.14
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, ObjectId objectId) {
            OsObjectBuilder.nativeAddObjectIdListItem(j, objectId.toString());
        }
    };
    private static ItemCallback<UUID> uuidItemCallback = new ItemCallback<UUID>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.15
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, UUID uuid) {
            OsObjectBuilder.nativeAddUUIDListItem(j, uuid.toString());
        }
    };
    private static ItemCallback<Map.Entry<String, Boolean>> booleanMapItemCallback = new ItemCallback<Map.Entry<String, Boolean>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.16
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Boolean> entry) {
            OsObjectBuilder.nativeAddBooleanDictionaryEntry(j, entry.getKey(), entry.getValue().booleanValue());
        }
    };
    private static ItemCallback<Map.Entry<String, String>> stringMapItemCallback = new ItemCallback<Map.Entry<String, String>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.17
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, String> entry) {
            OsObjectBuilder.nativeAddStringDictionaryEntry(j, entry.getKey(), entry.getValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Integer>> integerMapItemCallback = new ItemCallback<Map.Entry<String, Integer>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.18
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Integer> entry) {
            OsObjectBuilder.nativeAddIntegerDictionaryEntry(j, entry.getKey(), entry.getValue().intValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Float>> floatMapItemCallback = new ItemCallback<Map.Entry<String, Float>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.19
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Float> entry) {
            OsObjectBuilder.nativeAddFloatDictionaryEntry(j, entry.getKey(), entry.getValue().floatValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Long>> longMapItemCallback = new ItemCallback<Map.Entry<String, Long>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.20
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Long> entry) {
            OsObjectBuilder.nativeAddIntegerDictionaryEntry(j, entry.getKey(), entry.getValue().longValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Short>> shortMapItemCallback = new ItemCallback<Map.Entry<String, Short>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.21
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Short> entry) {
            OsObjectBuilder.nativeAddIntegerDictionaryEntry(j, entry.getKey(), entry.getValue().shortValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Byte>> byteMapItemCallback = new ItemCallback<Map.Entry<String, Byte>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.22
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Byte> entry) {
            OsObjectBuilder.nativeAddIntegerDictionaryEntry(j, entry.getKey(), entry.getValue().byteValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Double>> doubleMapItemCallback = new ItemCallback<Map.Entry<String, Double>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.23
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Double> entry) {
            OsObjectBuilder.nativeAddDoubleDictionaryEntry(j, entry.getKey(), entry.getValue().doubleValue());
        }
    };
    private static ItemCallback<Map.Entry<String, byte[]>> binaryMapItemCallback = new ItemCallback<Map.Entry<String, byte[]>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.24
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, byte[]> entry) {
            OsObjectBuilder.nativeAddBinaryDictionaryEntry(j, entry.getKey(), entry.getValue());
        }
    };
    private static ItemCallback<Map.Entry<String, Date>> dateMapItemCallback = new ItemCallback<Map.Entry<String, Date>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.25
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Date> entry) {
            OsObjectBuilder.nativeAddDateDictionaryEntry(j, entry.getKey(), entry.getValue().getTime());
        }
    };
    private static ItemCallback<Map.Entry<String, Decimal128>> decimal128MapItemCallback = new ItemCallback<Map.Entry<String, Decimal128>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.26
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, Decimal128> entry) {
            OsObjectBuilder.nativeAddDecimal128DictionaryEntry(j, entry.getKey(), entry.getValue().getHigh(), entry.getValue().getLow());
        }
    };
    private static ItemCallback<Map.Entry<String, ObjectId>> objectIdMapItemCallback = new ItemCallback<Map.Entry<String, ObjectId>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.27
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, ObjectId> entry) {
            OsObjectBuilder.nativeAddObjectIdDictionaryEntry(j, entry.getKey(), entry.getValue().toString());
        }
    };
    private static ItemCallback<Map.Entry<String, UUID>> uuidMapItemCallback = new ItemCallback<Map.Entry<String, UUID>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.28
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, UUID> entry) {
            OsObjectBuilder.nativeAddUUIDDictionaryEntry(j, entry.getKey(), entry.getValue().toString());
        }
    };
    private static ItemCallback<Map.Entry<String, RealmAny>> realmAnyMapItemCallback = new ItemCallback<Map.Entry<String, RealmAny>>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.29
        private final RealmAnyNativeFunctions realmAnyNativeFunctions = new RealmAnyNativeFunctionsImpl();

        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Map.Entry<String, RealmAny> entry) {
            this.realmAnyNativeFunctions.handleItem(j, entry);
        }
    };
    private static ItemCallback<RealmAny> realmAnyItemCallback = new ItemCallback<RealmAny>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.30
        private final RealmAnyNativeFunctions realmAnyNativeFunctions = new RealmAnyNativeFunctionsImpl();

        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, RealmAny realmAny) {
            this.realmAnyNativeFunctions.handleItem(j, realmAny);
        }
    };
    private static ItemCallback<String> stringSetItemCallback = new ItemCallback<String>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.31
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, String str) {
            OsObjectBuilder.nativeAddStringSetItem(j, str);
        }
    };
    private static ItemCallback<Boolean> booleanSetItemCallback = new ItemCallback<Boolean>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.32
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Boolean bool) {
            OsObjectBuilder.nativeAddBooleanSetItem(j, bool.booleanValue());
        }
    };
    private static ItemCallback<Integer> integerSetItemCallback = new ItemCallback<Integer>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.33
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Integer num) {
            OsObjectBuilder.nativeAddIntegerSetItem(j, num.intValue());
        }
    };
    private static ItemCallback<Long> longSetItemCallback = new ItemCallback<Long>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.34
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Long l) {
            OsObjectBuilder.nativeAddIntegerSetItem(j, l.longValue());
        }
    };
    private static ItemCallback<Short> shortSetItemCallback = new ItemCallback<Short>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.35
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Short sh) {
            OsObjectBuilder.nativeAddIntegerSetItem(j, sh.shortValue());
        }
    };
    private static ItemCallback<Byte> byteSetItemCallback = new ItemCallback<Byte>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.36
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Byte b) {
            OsObjectBuilder.nativeAddIntegerSetItem(j, b.byteValue());
        }
    };
    private static ItemCallback<Float> floatSetItemCallback = new ItemCallback<Float>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.37
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Float f) {
            OsObjectBuilder.nativeAddFloatSetItem(j, f.floatValue());
        }
    };
    private static ItemCallback<Double> doubleSetItemCallback = new ItemCallback<Double>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.38
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Double d) {
            OsObjectBuilder.nativeAddDoubleSetItem(j, d.doubleValue());
        }
    };
    private static ItemCallback<byte[]> binarySetItemCallback = new ItemCallback<byte[]>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.39
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, byte[] bArr) {
            OsObjectBuilder.nativeAddByteArraySetItem(j, bArr);
        }
    };
    private static ItemCallback<Date> dateSetItemCallback = new ItemCallback<Date>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.40
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Date date) {
            OsObjectBuilder.nativeAddDateSetItem(j, date.getTime());
        }
    };
    private static ItemCallback<Decimal128> decimal128SetItemCallback = new ItemCallback<Decimal128>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.41
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, Decimal128 decimal128) {
            OsObjectBuilder.nativeAddDecimal128SetItem(j, decimal128.getLow(), decimal128.getHigh());
        }
    };
    private static ItemCallback<ObjectId> objectIdSetItemCallback = new ItemCallback<ObjectId>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.42
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, ObjectId objectId) {
            OsObjectBuilder.nativeAddObjectIdSetItem(j, objectId.toString());
        }
    };
    private static ItemCallback<UUID> uuidSetItemCallback = new ItemCallback<UUID>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.43
        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, UUID uuid) {
            OsObjectBuilder.nativeAddUUIDSetItem(j, uuid.toString());
        }
    };
    private static ItemCallback<RealmAny> realmAnySetItemCallback = new ItemCallback<RealmAny>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.44
        private final RealmAnyNativeFunctions realmAnyNativeFunctions = new RealmAnyNativeFunctionsImpl();

        @Override // io.realm.internal.objectstore.OsObjectBuilder.ItemCallback
        public void handleItem(long j, RealmAny realmAny) {
            this.realmAnyNativeFunctions.handleItem(j, realmAny);
        }
    };

    private interface ItemCallback<T> {
        void handleItem(long j, T t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddBinaryDictionaryEntry(long j, String str, byte[] bArr);

    private static native void nativeAddBoolean(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddBooleanDictionaryEntry(long j, String str, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddBooleanListItem(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddBooleanSetItem(long j, boolean z);

    private static native void nativeAddByteArray(long j, long j2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddByteArrayListItem(long j, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddByteArraySetItem(long j, byte[] bArr);

    private static native void nativeAddDate(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDateDictionaryEntry(long j, String str, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDateListItem(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDateSetItem(long j, long j2);

    private static native void nativeAddDecimal128(long j, long j2, long j3, long j4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDecimal128DictionaryEntry(long j, String str, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDecimal128ListItem(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDecimal128SetItem(long j, long j2, long j3);

    private static native void nativeAddDouble(long j, long j2, double d);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDoubleDictionaryEntry(long j, String str, double d);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDoubleListItem(long j, double d);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDoubleSetItem(long j, double d);

    private static native void nativeAddFloat(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddFloatDictionaryEntry(long j, String str, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddFloatListItem(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddFloatSetItem(long j, float f);

    private static native void nativeAddInteger(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddIntegerDictionaryEntry(long j, String str, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddIntegerListItem(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddIntegerSetItem(long j, long j2);

    private static native void nativeAddNull(long j, long j2);

    private static native void nativeAddNullDictionaryEntry(long j, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddNullListItem(long j);

    private static native void nativeAddNullSetItem(long j);

    private static native void nativeAddObject(long j, long j2, long j3);

    private static native void nativeAddObjectDictionaryEntry(long j, String str, long j2);

    private static native void nativeAddObjectId(long j, long j2, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddObjectIdDictionaryEntry(long j, String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddObjectIdListItem(long j, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddObjectIdSetItem(long j, String str);

    private static native void nativeAddObjectList(long j, long j2, long[] jArr);

    private static native void nativeAddObjectListItem(long j, long j2);

    private static native void nativeAddRealmAny(long j, long j2, long j3);

    public static native void nativeAddRealmAnyDictionaryEntry(long j, String str, long j2);

    public static native void nativeAddRealmAnyListItem(long j, long j2);

    private static native void nativeAddString(long j, long j2, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddStringDictionaryEntry(long j, String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddStringListItem(long j, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddStringSetItem(long j, String str);

    private static native void nativeAddUUID(long j, long j2, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddUUIDDictionaryEntry(long j, String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddUUIDListItem(long j, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddUUIDSetItem(long j, String str);

    private static native long nativeCreateBuilder();

    private static native long nativeCreateOrUpdateTopLevelObject(long j, long j2, long j3, boolean z, boolean z2);

    private static native void nativeDestroyBuilder(long j);

    private static native long nativeStartDictionary();

    private static native long nativeStartList(long j);

    private static native long nativeStartSet(long j);

    private static native void nativeStopDictionary(long j, long j2, long j3);

    private static native void nativeStopList(long j, long j2, long j3);

    private static native void nativeStopSet(long j, long j2, long j3);

    private static native long nativeUpdateEmbeddedObject(long j, long j2, long j3, long j4, boolean z);

    public OsObjectBuilder(Table table, Set<ImportFlag> set) {
        OsSharedRealm sharedRealm = table.getSharedRealm();
        this.sharedRealmPtr = sharedRealm.getNativePtr();
        this.table = table;
        table.getColumnNames();
        this.tablePtr = table.getNativePtr();
        this.builderPtr = nativeCreateBuilder();
        this.context = sharedRealm.context;
        this.ignoreFieldsWithSameValue = set.contains(ImportFlag.CHECK_SAME_VALUES_BEFORE_SET);
    }

    public void addInteger(long j, @Nullable Byte b) {
        if (b == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddInteger(this.builderPtr, j, b.byteValue());
        }
    }

    public void addInteger(long j, @Nullable Short sh) {
        if (sh == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddInteger(this.builderPtr, j, sh.shortValue());
        }
    }

    public void addInteger(long j, @Nullable Integer num) {
        if (num == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddInteger(this.builderPtr, j, num.intValue());
        }
    }

    public void addInteger(long j, @Nullable Long l) {
        if (l == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddInteger(this.builderPtr, j, l.longValue());
        }
    }

    public void addMutableRealmInteger(long j, @Nullable MutableRealmInteger mutableRealmInteger) {
        if (mutableRealmInteger == null || mutableRealmInteger.get() == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddInteger(this.builderPtr, j, mutableRealmInteger.get().longValue());
        }
    }

    public void addRealmAny(long j, long j2) {
        nativeAddRealmAny(this.builderPtr, j, j2);
    }

    public void addString(long j, @Nullable String str) {
        if (str == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddString(this.builderPtr, j, str);
        }
    }

    public void addFloat(long j, @Nullable Float f) {
        if (f == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddFloat(this.builderPtr, j, f.floatValue());
        }
    }

    public void addDouble(long j, @Nullable Double d) {
        if (d == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddDouble(this.builderPtr, j, d.doubleValue());
        }
    }

    public void addBoolean(long j, @Nullable Boolean bool) {
        if (bool == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddBoolean(this.builderPtr, j, bool.booleanValue());
        }
    }

    public void addDate(long j, @Nullable Date date) {
        if (date == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddDate(this.builderPtr, j, date.getTime());
        }
    }

    public void addByteArray(long j, @Nullable byte[] bArr) {
        if (bArr == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddByteArray(this.builderPtr, j, bArr);
        }
    }

    public void addDecimal128(long j, @Nullable Decimal128 decimal128) {
        if (decimal128 == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddDecimal128(this.builderPtr, j, decimal128.getLow(), decimal128.getHigh());
        }
    }

    public void addObjectId(long j, @Nullable ObjectId objectId) {
        if (objectId == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddObjectId(this.builderPtr, j, objectId.toString());
        }
    }

    public void addUUID(long j, @Nullable UUID uuid) {
        if (uuid == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddUUID(this.builderPtr, j, uuid.toString());
        }
    }

    public void addNull(long j) {
        nativeAddNull(this.builderPtr, j);
    }

    public void addObject(long j, @Nullable RealmModel realmModel) {
        if (realmModel == null) {
            nativeAddNull(this.builderPtr, j);
        } else {
            nativeAddObject(this.builderPtr, j, ((UncheckedRow) ((RealmObjectProxy) realmModel).realmGet$proxyState().getRow$realm()).getNativePtr());
        }
    }

    private <T> void addListItem(long j, long j2, @Nullable List<T> list, ItemCallback<T> itemCallback) {
        if (list != null) {
            long jNativeStartList = nativeStartList(list.size());
            boolean z = j2 == 0 || this.table.isColumnNullable(j2);
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                if (t == null) {
                    if (!z) {
                        throw new IllegalArgumentException("This 'RealmList' is not nullable. A non-null value is expected.");
                    }
                    nativeAddNullListItem(jNativeStartList);
                } else {
                    itemCallback.handleItem(jNativeStartList, t);
                }
            }
            nativeStopList(j, j2, jNativeStartList);
            return;
        }
        addEmptyList(j2);
    }

    public <T extends RealmModel> void addObjectList(long j, @Nullable RealmList<T> realmList) {
        if (realmList != null) {
            long[] jArr = new long[realmList.size()];
            for (int i = 0; i < realmList.size(); i++) {
                RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmList.get(i);
                if (realmObjectProxy == null) {
                    throw new IllegalArgumentException("Null values are not allowed in RealmLists containing Realm models");
                }
                jArr[i] = ((UncheckedRow) realmObjectProxy.realmGet$proxyState().getRow$realm()).getNativePtr();
            }
            nativeAddObjectList(this.builderPtr, j, jArr);
            return;
        }
        nativeAddObjectList(this.builderPtr, j, new long[0]);
    }

    public void addStringList(long j, RealmList<String> realmList) {
        addListItem(this.builderPtr, j, realmList, stringItemCallback);
    }

    public void addByteList(long j, RealmList<Byte> realmList) {
        addListItem(this.builderPtr, j, realmList, byteItemCallback);
    }

    public void addShortList(long j, RealmList<Short> realmList) {
        addListItem(this.builderPtr, j, realmList, shortItemCallback);
    }

    public void addIntegerList(long j, RealmList<Integer> realmList) {
        addListItem(this.builderPtr, j, realmList, integerItemCallback);
    }

    public void addLongList(long j, RealmList<Long> realmList) {
        addListItem(this.builderPtr, j, realmList, longItemCallback);
    }

    public void addBooleanList(long j, RealmList<Boolean> realmList) {
        addListItem(this.builderPtr, j, realmList, booleanItemCallback);
    }

    public void addFloatList(long j, RealmList<Float> realmList) {
        addListItem(this.builderPtr, j, realmList, floatItemCallback);
    }

    public void addDoubleList(long j, RealmList<Double> realmList) {
        addListItem(this.builderPtr, j, realmList, doubleItemCallback);
    }

    public void addDateList(long j, RealmList<Date> realmList) {
        addListItem(this.builderPtr, j, realmList, dateItemCallback);
    }

    public void addByteArrayList(long j, RealmList<byte[]> realmList) {
        addListItem(this.builderPtr, j, realmList, byteArrayItemCallback);
    }

    public void addMutableRealmIntegerList(long j, RealmList<MutableRealmInteger> realmList) {
        addListItem(this.builderPtr, j, realmList, mutableRealmIntegerItemCallback);
    }

    public void addDecimal128List(long j, RealmList<Decimal128> realmList) {
        addListItem(this.builderPtr, j, realmList, decimal128ItemCallback);
    }

    public void addObjectIdList(long j, RealmList<ObjectId> realmList) {
        addListItem(this.builderPtr, j, realmList, objectIdItemCallback);
    }

    public void addUUIDList(long j, RealmList<UUID> realmList) {
        addListItem(this.builderPtr, j, realmList, uuidItemCallback);
    }

    public void addRealmAnyList(long j, RealmList<RealmAny> realmList) {
        addListItem(this.builderPtr, j, realmList, realmAnyItemCallback);
    }

    private void addEmptyList(long j) {
        nativeStopList(this.builderPtr, j, nativeStartList(0L));
    }

    public void addBooleanValueDictionary(long j, RealmDictionary<Boolean> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, booleanMapItemCallback);
    }

    public void addIntegerValueDictionary(long j, RealmDictionary<Integer> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, integerMapItemCallback);
    }

    public void addFloatValueDictionary(long j, RealmDictionary<Float> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, floatMapItemCallback);
    }

    public void addLongValueDictionary(long j, RealmDictionary<Long> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, longMapItemCallback);
    }

    public void addShortValueDictionary(long j, RealmDictionary<Short> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, shortMapItemCallback);
    }

    public void addByteValueDictionary(long j, RealmDictionary<Byte> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, byteMapItemCallback);
    }

    public void addDoubleValueDictionary(long j, RealmDictionary<Double> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, doubleMapItemCallback);
    }

    public void addStringValueDictionary(long j, RealmDictionary<String> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, stringMapItemCallback);
    }

    public void addDateValueDictionary(long j, RealmDictionary<Date> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, dateMapItemCallback);
    }

    public void addDecimal128ValueDictionary(long j, RealmDictionary<Decimal128> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, decimal128MapItemCallback);
    }

    public void addBinaryValueDictionary(long j, RealmDictionary<byte[]> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, binaryMapItemCallback);
    }

    public void addObjectIdValueDictionary(long j, RealmDictionary<ObjectId> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, objectIdMapItemCallback);
    }

    public void addUUIDValueDictionary(long j, RealmDictionary<UUID> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, uuidMapItemCallback);
    }

    public void addRealmAnyValueDictionary(long j, RealmDictionary<RealmAny> realmDictionary) {
        addDictionaryItem(this.builderPtr, j, realmDictionary, realmAnyMapItemCallback);
    }

    public <T extends RealmModel> void addObjectDictionary(long j, @Nullable RealmDictionary<T> realmDictionary) {
        if (realmDictionary != null) {
            long jNativeStartDictionary = nativeStartDictionary();
            for (Map.Entry<String, T> entry : realmDictionary.entrySet()) {
                if (entry.getValue() == null) {
                    nativeAddNullDictionaryEntry(jNativeStartDictionary, entry.getKey());
                } else {
                    nativeAddObjectDictionaryEntry(jNativeStartDictionary, entry.getKey(), ((UncheckedRow) ((RealmObjectProxy) entry.getValue()).realmGet$proxyState().getRow$realm()).getNativePtr());
                }
            }
            nativeStopDictionary(this.builderPtr, j, jNativeStartDictionary);
            return;
        }
        addEmptyDictionary(j);
    }

    private <T> void addDictionaryItem(long j, long j2, @Nullable RealmDictionary<T> realmDictionary, ItemCallback<Map.Entry<String, T>> itemCallback) {
        if (realmDictionary != null) {
            long jNativeStartDictionary = nativeStartDictionary();
            for (Map.Entry<String, T> entry : realmDictionary.entrySet()) {
                if (entry.getValue() == null) {
                    nativeAddNullDictionaryEntry(jNativeStartDictionary, entry.getKey());
                } else {
                    itemCallback.handleItem(jNativeStartDictionary, entry);
                }
            }
            nativeStopDictionary(j, j2, jNativeStartDictionary);
            return;
        }
        addEmptyDictionary(j2);
    }

    private void addEmptyDictionary(long j) {
        nativeStopDictionary(this.builderPtr, j, nativeStartDictionary());
    }

    private void addEmptySet(long j) {
        nativeStopSet(this.builderPtr, j, nativeStartSet(0L));
    }

    public void addStringSet(long j, RealmSet<String> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, stringSetItemCallback);
    }

    public void addBooleanSet(long j, RealmSet<Boolean> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, booleanSetItemCallback);
    }

    public void addIntegerSet(long j, RealmSet<Integer> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, integerSetItemCallback);
    }

    public void addLongSet(long j, RealmSet<Long> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, longSetItemCallback);
    }

    public void addShortSet(long j, RealmSet<Short> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, shortSetItemCallback);
    }

    public void addByteSet(long j, RealmSet<Byte> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, byteSetItemCallback);
    }

    public void addFloatSet(long j, RealmSet<Float> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, floatSetItemCallback);
    }

    public void addDoubleSet(long j, RealmSet<Double> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, doubleSetItemCallback);
    }

    public void addBinarySet(long j, RealmSet<byte[]> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, binarySetItemCallback);
    }

    public void addDateSet(long j, RealmSet<Date> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, dateSetItemCallback);
    }

    public void addDecimal128Set(long j, RealmSet<Decimal128> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, decimal128SetItemCallback);
    }

    public void addObjectIdSet(long j, RealmSet<ObjectId> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, objectIdSetItemCallback);
    }

    public void addUUIDSet(long j, RealmSet<UUID> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, uuidSetItemCallback);
    }

    public void addRealmAnySet(long j, RealmSet<RealmAny> realmSet) {
        addSetItem(this.builderPtr, j, realmSet, realmAnySetItemCallback);
    }

    public <T extends RealmModel> void addObjectSet(long j, @Nullable RealmSet<T> realmSet) {
        if (realmSet != null) {
            long jNativeStartSet = nativeStartSet(realmSet.size());
            for (T t : realmSet) {
                if (t == null) {
                    throw new IllegalArgumentException("This 'RealmSet' is not nullable. A non-null value is expected.");
                }
                nativeAddObjectListItem(jNativeStartSet, ((UncheckedRow) ((RealmObjectProxy) t).realmGet$proxyState().getRow$realm()).getNativePtr());
            }
            nativeStopSet(this.builderPtr, j, jNativeStartSet);
            return;
        }
        addEmptySet(j);
    }

    private <T> void addSetItem(long j, long j2, @Nullable Set<T> set, ItemCallback<T> itemCallback) {
        if (set != null) {
            long jNativeStartSet = nativeStartSet(set.size());
            boolean z = j2 == 0 || this.table.isColumnNullable(j2);
            for (T t : set) {
                if (t == null) {
                    if (!z) {
                        throw new IllegalArgumentException("This 'RealmSet' is not nullable. A non-null value is expected.");
                    }
                    nativeAddNullSetItem(jNativeStartSet);
                } else {
                    itemCallback.handleItem(jNativeStartSet, t);
                }
            }
            nativeStopSet(j, j2, jNativeStartSet);
            return;
        }
        addEmptySet(j2);
    }

    public void updateExistingTopLevelObject() {
        try {
            nativeCreateOrUpdateTopLevelObject(this.sharedRealmPtr, this.tablePtr, this.builderPtr, true, this.ignoreFieldsWithSameValue);
        } finally {
            close();
        }
    }

    public void updateExistingEmbeddedObject(RealmObjectProxy realmObjectProxy) {
        try {
            nativeUpdateEmbeddedObject(this.sharedRealmPtr, this.tablePtr, this.builderPtr, realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey(), this.ignoreFieldsWithSameValue);
        } finally {
            close();
        }
    }

    public UncheckedRow createNewObject() {
        try {
            return new UncheckedRow(this.context, this.table, nativeCreateOrUpdateTopLevelObject(this.sharedRealmPtr, this.tablePtr, this.builderPtr, false, false));
        } finally {
            close();
        }
    }

    public long getNativePtr() {
        return this.builderPtr;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        nativeDestroyBuilder(this.builderPtr);
    }
}

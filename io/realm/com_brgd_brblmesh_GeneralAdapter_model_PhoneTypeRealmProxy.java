package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.BaseRealm;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy extends PhoneType implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private PhoneTypeColumnInfo columnInfo;
    private ProxyState<PhoneType> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "PhoneType";
    }

    static final class PhoneTypeColumnInfo extends ColumnInfo {
        long phoneTypeColKey;

        PhoneTypeColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(1);
            this.phoneTypeColKey = addColumnDetails(GlobalVariable.P_TYPE, GlobalVariable.P_TYPE, osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME));
        }

        PhoneTypeColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new PhoneTypeColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ((PhoneTypeColumnInfo) columnInfo2).phoneTypeColKey = ((PhoneTypeColumnInfo) columnInfo).phoneTypeColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (PhoneTypeColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<PhoneType> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.PhoneType, io.realm.com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface
    public int realmGet$phoneType() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.phoneTypeColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.PhoneType, io.realm.com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface
    public void realmSet$phoneType(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.phoneTypeColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.phoneTypeColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 1, 0);
        builder.addPersistedProperty("", GlobalVariable.P_TYPE, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static PhoneTypeColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new PhoneTypeColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static PhoneType createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        PhoneType phoneType = (PhoneType) realm.createObjectInternal(PhoneType.class, true, Collections.EMPTY_LIST);
        PhoneType phoneType2 = phoneType;
        if (!jSONObject.has(GlobalVariable.P_TYPE)) {
            return phoneType;
        }
        if (jSONObject.isNull(GlobalVariable.P_TYPE)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'phoneType' to null.");
        }
        phoneType2.realmSet$phoneType(jSONObject.getInt(GlobalVariable.P_TYPE));
        return phoneType;
    }

    public static PhoneType createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        PhoneType phoneType = new PhoneType();
        PhoneType phoneType2 = phoneType;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals(GlobalVariable.P_TYPE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    phoneType2.realmSet$phoneType(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'phoneType' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (PhoneType) realm.copyToRealm(phoneType, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(PhoneType.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy com_brgd_brblmesh_generaladapter_model_phonetyperealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_phonetyperealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PhoneType copyOrUpdate(Realm realm, PhoneTypeColumnInfo phoneTypeColumnInfo, PhoneType phoneType, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((phoneType instanceof RealmObjectProxy) && !RealmObject.isFrozen(phoneType)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) phoneType;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return phoneType;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(phoneType);
        if (realmModel != null) {
            return (PhoneType) realmModel;
        }
        return copy(realm, phoneTypeColumnInfo, phoneType, z, map, set);
    }

    public static PhoneType copy(Realm realm, PhoneTypeColumnInfo phoneTypeColumnInfo, PhoneType phoneType, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(phoneType);
        if (realmObjectProxy != null) {
            return (PhoneType) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(PhoneType.class), set);
        osObjectBuilder.addInteger(phoneTypeColumnInfo.phoneTypeColKey, Integer.valueOf(phoneType.realmGet$phoneType()));
        com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy com_brgd_brblmesh_generaladapter_model_phonetyperealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(phoneType, com_brgd_brblmesh_generaladapter_model_phonetyperealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_phonetyperealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, PhoneType phoneType, Map<RealmModel, Long> map) {
        if ((phoneType instanceof RealmObjectProxy) && !RealmObject.isFrozen(phoneType)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) phoneType;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(PhoneType.class);
        long nativePtr = table.getNativePtr();
        PhoneTypeColumnInfo phoneTypeColumnInfo = (PhoneTypeColumnInfo) realm.getSchema().getColumnInfo(PhoneType.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(phoneType, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, phoneTypeColumnInfo.phoneTypeColKey, jCreateRow, phoneType.realmGet$phoneType(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(PhoneType.class);
        long nativePtr = table.getNativePtr();
        PhoneTypeColumnInfo phoneTypeColumnInfo = (PhoneTypeColumnInfo) realm.getSchema().getColumnInfo(PhoneType.class);
        while (it.hasNext()) {
            RealmModel realmModel = (PhoneType) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, phoneTypeColumnInfo.phoneTypeColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface) realmModel).realmGet$phoneType(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, PhoneType phoneType, Map<RealmModel, Long> map) {
        if ((phoneType instanceof RealmObjectProxy) && !RealmObject.isFrozen(phoneType)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) phoneType;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(PhoneType.class);
        long nativePtr = table.getNativePtr();
        PhoneTypeColumnInfo phoneTypeColumnInfo = (PhoneTypeColumnInfo) realm.getSchema().getColumnInfo(PhoneType.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(phoneType, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, phoneTypeColumnInfo.phoneTypeColKey, jCreateRow, phoneType.realmGet$phoneType(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(PhoneType.class);
        long nativePtr = table.getNativePtr();
        PhoneTypeColumnInfo phoneTypeColumnInfo = (PhoneTypeColumnInfo) realm.getSchema().getColumnInfo(PhoneType.class);
        while (it.hasNext()) {
            RealmModel realmModel = (PhoneType) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, phoneTypeColumnInfo.phoneTypeColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface) realmModel).realmGet$phoneType(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PhoneType createDetachedCopy(PhoneType phoneType, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        PhoneType phoneType2;
        if (i > i2 || phoneType == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(phoneType);
        if (cacheData == null) {
            phoneType2 = new PhoneType();
            map.put(phoneType, new RealmObjectProxy.CacheData<>(i, phoneType2));
        } else {
            if (i >= cacheData.minDepth) {
                return (PhoneType) cacheData.object;
            }
            PhoneType phoneType3 = (PhoneType) cacheData.object;
            cacheData.minDepth = i;
            phoneType2 = phoneType3;
        }
        PhoneType phoneType4 = phoneType;
        phoneType2.realmSet$phoneType(phoneType4.realmGet$phoneType());
        return phoneType2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "PhoneType = proxy[{phoneType:" + realmGet$phoneType() + "}]";
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    public int hashCode() {
        String path = this.proxyState.getRealm$realm().getPath();
        String name = this.proxyState.getRow$realm().getTable().getName();
        long objectKey = this.proxyState.getRow$realm().getObjectKey();
        return ((((527 + (path != null ? path.hashCode() : 0)) * 31) + (name != null ? name.hashCode() : 0)) * 31) + ((int) ((objectKey >>> 32) ^ objectKey));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy com_brgd_brblmesh_generaladapter_model_phonetyperealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_phonetyperealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_phonetyperealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_phonetyperealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

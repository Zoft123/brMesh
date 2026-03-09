package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
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
public class com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy extends RadarPhoneType implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private RadarPhoneTypeColumnInfo columnInfo;
    private ProxyState<RadarPhoneType> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RadarPhoneType";
    }

    static final class RadarPhoneTypeColumnInfo extends ColumnInfo {
        long phoneTypeColKey;

        RadarPhoneTypeColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(1);
            this.phoneTypeColKey = addColumnDetails(GlobalVariable.P_TYPE, GlobalVariable.P_TYPE, osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME));
        }

        RadarPhoneTypeColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new RadarPhoneTypeColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ((RadarPhoneTypeColumnInfo) columnInfo2).phoneTypeColKey = ((RadarPhoneTypeColumnInfo) columnInfo).phoneTypeColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (RadarPhoneTypeColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<RadarPhoneType> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface
    public int realmGet$phoneType() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.phoneTypeColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface
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

    public static RadarPhoneTypeColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new RadarPhoneTypeColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static RadarPhoneType createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        RadarPhoneType radarPhoneType = (RadarPhoneType) realm.createObjectInternal(RadarPhoneType.class, true, Collections.EMPTY_LIST);
        RadarPhoneType radarPhoneType2 = radarPhoneType;
        if (!jSONObject.has(GlobalVariable.P_TYPE)) {
            return radarPhoneType;
        }
        if (jSONObject.isNull(GlobalVariable.P_TYPE)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'phoneType' to null.");
        }
        radarPhoneType2.realmSet$phoneType(jSONObject.getInt(GlobalVariable.P_TYPE));
        return radarPhoneType;
    }

    public static RadarPhoneType createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        RadarPhoneType radarPhoneType = new RadarPhoneType();
        RadarPhoneType radarPhoneType2 = radarPhoneType;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals(GlobalVariable.P_TYPE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarPhoneType2.realmSet$phoneType(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'phoneType' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (RadarPhoneType) realm.copyToRealm(radarPhoneType, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(RadarPhoneType.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarPhoneType copyOrUpdate(Realm realm, RadarPhoneTypeColumnInfo radarPhoneTypeColumnInfo, RadarPhoneType radarPhoneType, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((radarPhoneType instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarPhoneType)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarPhoneType;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return radarPhoneType;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(radarPhoneType);
        if (realmModel != null) {
            return (RadarPhoneType) realmModel;
        }
        return copy(realm, radarPhoneTypeColumnInfo, radarPhoneType, z, map, set);
    }

    public static RadarPhoneType copy(Realm realm, RadarPhoneTypeColumnInfo radarPhoneTypeColumnInfo, RadarPhoneType radarPhoneType, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(radarPhoneType);
        if (realmObjectProxy != null) {
            return (RadarPhoneType) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(RadarPhoneType.class), set);
        osObjectBuilder.addInteger(radarPhoneTypeColumnInfo.phoneTypeColKey, Integer.valueOf(radarPhoneType.realmGet$phoneType()));
        com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(radarPhoneType, com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, RadarPhoneType radarPhoneType, Map<RealmModel, Long> map) {
        if ((radarPhoneType instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarPhoneType)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarPhoneType;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarPhoneType.class);
        long nativePtr = table.getNativePtr();
        RadarPhoneTypeColumnInfo radarPhoneTypeColumnInfo = (RadarPhoneTypeColumnInfo) realm.getSchema().getColumnInfo(RadarPhoneType.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarPhoneType, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, radarPhoneTypeColumnInfo.phoneTypeColKey, jCreateRow, radarPhoneType.realmGet$phoneType(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarPhoneType.class);
        long nativePtr = table.getNativePtr();
        RadarPhoneTypeColumnInfo radarPhoneTypeColumnInfo = (RadarPhoneTypeColumnInfo) realm.getSchema().getColumnInfo(RadarPhoneType.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarPhoneType) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, radarPhoneTypeColumnInfo.phoneTypeColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface) realmModel).realmGet$phoneType(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, RadarPhoneType radarPhoneType, Map<RealmModel, Long> map) {
        if ((radarPhoneType instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarPhoneType)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarPhoneType;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarPhoneType.class);
        long nativePtr = table.getNativePtr();
        RadarPhoneTypeColumnInfo radarPhoneTypeColumnInfo = (RadarPhoneTypeColumnInfo) realm.getSchema().getColumnInfo(RadarPhoneType.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarPhoneType, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, radarPhoneTypeColumnInfo.phoneTypeColKey, jCreateRow, radarPhoneType.realmGet$phoneType(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarPhoneType.class);
        long nativePtr = table.getNativePtr();
        RadarPhoneTypeColumnInfo radarPhoneTypeColumnInfo = (RadarPhoneTypeColumnInfo) realm.getSchema().getColumnInfo(RadarPhoneType.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarPhoneType) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, radarPhoneTypeColumnInfo.phoneTypeColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface) realmModel).realmGet$phoneType(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarPhoneType createDetachedCopy(RadarPhoneType radarPhoneType, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        RadarPhoneType radarPhoneType2;
        if (i > i2 || radarPhoneType == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(radarPhoneType);
        if (cacheData == null) {
            radarPhoneType2 = new RadarPhoneType();
            map.put(radarPhoneType, new RealmObjectProxy.CacheData<>(i, radarPhoneType2));
        } else {
            if (i >= cacheData.minDepth) {
                return (RadarPhoneType) cacheData.object;
            }
            RadarPhoneType radarPhoneType3 = (RadarPhoneType) cacheData.object;
            cacheData.minDepth = i;
            radarPhoneType2 = radarPhoneType3;
        }
        RadarPhoneType radarPhoneType4 = radarPhoneType;
        radarPhoneType2.realmSet$phoneType(radarPhoneType4.realmGet$phoneType());
        return radarPhoneType2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "RadarPhoneType = proxy[{phoneType:" + realmGet$phoneType() + "}]";
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
        com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_radarphonetyperealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

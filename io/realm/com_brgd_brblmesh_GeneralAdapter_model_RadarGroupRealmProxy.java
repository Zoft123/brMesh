package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.BaseRealm;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy extends RadarGroup implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private RealmList<RadarDevice> bleDeviceRealmListRealmList;
    private RadarGroupColumnInfo columnInfo;
    private ProxyState<RadarGroup> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RadarGroup";
    }

    static final class RadarGroupColumnInfo extends ColumnInfo {
        long bleDeviceRealmListColKey;
        long fixedIdColKey;
        long fixedNameColKey;
        long indexColKey;

        RadarGroupColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.fixedIdColKey = addColumnDetails("fixedId", "fixedId", objectSchemaInfo);
            this.fixedNameColKey = addColumnDetails(GlobalVariable.FIXEDNAME, GlobalVariable.FIXEDNAME, objectSchemaInfo);
            this.indexColKey = addColumnDetails(GlobalVariable.INDEX, GlobalVariable.INDEX, objectSchemaInfo);
            this.bleDeviceRealmListColKey = addColumnDetails("bleDeviceRealmList", "bleDeviceRealmList", objectSchemaInfo);
        }

        RadarGroupColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new RadarGroupColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            RadarGroupColumnInfo radarGroupColumnInfo = (RadarGroupColumnInfo) columnInfo;
            RadarGroupColumnInfo radarGroupColumnInfo2 = (RadarGroupColumnInfo) columnInfo2;
            radarGroupColumnInfo2.fixedIdColKey = radarGroupColumnInfo.fixedIdColKey;
            radarGroupColumnInfo2.fixedNameColKey = radarGroupColumnInfo.fixedNameColKey;
            radarGroupColumnInfo2.indexColKey = radarGroupColumnInfo.indexColKey;
            radarGroupColumnInfo2.bleDeviceRealmListColKey = radarGroupColumnInfo.bleDeviceRealmListColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (RadarGroupColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<RadarGroup> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public int realmGet$fixedId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.fixedIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$fixedId(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.fixedIdColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.fixedIdColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public String realmGet$fixedName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.fixedNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$fixedName(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.fixedNameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.fixedNameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.fixedNameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.fixedNameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public int realmGet$index() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$index(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.indexColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.indexColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public RealmList<RadarDevice> realmGet$bleDeviceRealmList() {
        this.proxyState.getRealm$realm().checkIfValid();
        RealmList<RadarDevice> realmList = this.bleDeviceRealmListRealmList;
        if (realmList != null) {
            return realmList;
        }
        RealmList<RadarDevice> realmList2 = new RealmList<>((Class<RadarDevice>) RadarDevice.class, this.proxyState.getRow$realm().getModelList(this.columnInfo.bleDeviceRealmListColKey), this.proxyState.getRealm$realm());
        this.bleDeviceRealmListRealmList = realmList2;
        return realmList2;
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$bleDeviceRealmList(RealmList<RadarDevice> realmList) {
        int i = 0;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("bleDeviceRealmList")) {
                return;
            }
            if (realmList != null && !realmList.isManaged()) {
                Realm realm = (Realm) this.proxyState.getRealm$realm();
                RealmList<RadarDevice> realmList2 = new RealmList<>();
                for (RadarDevice radarDevice : realmList) {
                    if (radarDevice == null || RealmObject.isManaged(radarDevice)) {
                        realmList2.add(radarDevice);
                    } else {
                        realmList2.add((RadarDevice) realm.copyToRealm(radarDevice, new ImportFlag[0]));
                    }
                }
                realmList = realmList2;
            }
        }
        this.proxyState.getRealm$realm().checkIfValid();
        OsList modelList = this.proxyState.getRow$realm().getModelList(this.columnInfo.bleDeviceRealmListColKey);
        if (realmList != null && realmList.size() == modelList.size()) {
            int size = realmList.size();
            while (i < size) {
                RealmModel realmModel = (RadarDevice) realmList.get(i);
                this.proxyState.checkValidObject(realmModel);
                modelList.setRow(i, ((RealmObjectProxy) realmModel).realmGet$proxyState().getRow$realm().getObjectKey());
                i++;
            }
            return;
        }
        modelList.removeAll();
        if (realmList == null) {
            return;
        }
        int size2 = realmList.size();
        while (i < size2) {
            RealmModel realmModel2 = (RadarDevice) realmList.get(i);
            this.proxyState.checkValidObject(realmModel2);
            modelList.addRow(((RealmObjectProxy) realmModel2).realmGet$proxyState().getRow$realm().getObjectKey());
            i++;
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 4, 0);
        builder.addPersistedProperty("", "fixedId", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.FIXEDNAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.INDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedLinkProperty("", "bleDeviceRealmList", RealmFieldType.LIST, com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RadarGroupColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new RadarGroupColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static RadarGroup createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        ArrayList arrayList = new ArrayList(1);
        if (jSONObject.has("bleDeviceRealmList")) {
            arrayList.add("bleDeviceRealmList");
        }
        RadarGroup radarGroup = (RadarGroup) realm.createObjectInternal(RadarGroup.class, true, arrayList);
        RadarGroup radarGroup2 = radarGroup;
        if (jSONObject.has("fixedId")) {
            if (jSONObject.isNull("fixedId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
            }
            radarGroup2.realmSet$fixedId(jSONObject.getInt("fixedId"));
        }
        if (jSONObject.has(GlobalVariable.FIXEDNAME)) {
            if (jSONObject.isNull(GlobalVariable.FIXEDNAME)) {
                radarGroup2.realmSet$fixedName(null);
            } else {
                radarGroup2.realmSet$fixedName(jSONObject.getString(GlobalVariable.FIXEDNAME));
            }
        }
        if (jSONObject.has(GlobalVariable.INDEX)) {
            if (jSONObject.isNull(GlobalVariable.INDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
            }
            radarGroup2.realmSet$index(jSONObject.getInt(GlobalVariable.INDEX));
        }
        if (jSONObject.has("bleDeviceRealmList")) {
            if (jSONObject.isNull("bleDeviceRealmList")) {
                radarGroup2.realmSet$bleDeviceRealmList(null);
                return radarGroup;
            }
            radarGroup2.realmGet$bleDeviceRealmList().clear();
            JSONArray jSONArray = jSONObject.getJSONArray("bleDeviceRealmList");
            for (int i = 0; i < jSONArray.length(); i++) {
                radarGroup2.realmGet$bleDeviceRealmList().add(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createOrUpdateUsingJsonObject(realm, jSONArray.getJSONObject(i), z));
            }
        }
        return radarGroup;
    }

    public static RadarGroup createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        RadarGroup radarGroup = new RadarGroup();
        RadarGroup radarGroup2 = radarGroup;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("fixedId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarGroup2.realmSet$fixedId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.FIXEDNAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarGroup2.realmSet$fixedName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    radarGroup2.realmSet$fixedName(null);
                }
            } else if (strNextName.equals(GlobalVariable.INDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarGroup2.realmSet$index(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
                }
            } else if (strNextName.equals("bleDeviceRealmList")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    radarGroup2.realmSet$bleDeviceRealmList(null);
                } else {
                    radarGroup2.realmSet$bleDeviceRealmList(new RealmList<>());
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        radarGroup2.realmGet$bleDeviceRealmList().add(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createUsingJsonStream(realm, jsonReader));
                    }
                    jsonReader.endArray();
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (RadarGroup) realm.copyToRealm(radarGroup, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(RadarGroup.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy com_brgd_brblmesh_generaladapter_model_radargrouprealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_radargrouprealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarGroup copyOrUpdate(Realm realm, RadarGroupColumnInfo radarGroupColumnInfo, RadarGroup radarGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((radarGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return radarGroup;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(radarGroup);
        if (realmModel != null) {
            return (RadarGroup) realmModel;
        }
        return copy(realm, radarGroupColumnInfo, radarGroup, z, map, set);
    }

    public static RadarGroup copy(Realm realm, RadarGroupColumnInfo radarGroupColumnInfo, RadarGroup radarGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Realm realm2;
        boolean z2;
        Map<RealmModel, RealmObjectProxy> map2;
        Set<ImportFlag> set2;
        RealmModel realmModel = (RealmObjectProxy) map.get(radarGroup);
        if (realmModel != null) {
            return (RadarGroup) realmModel;
        }
        RadarGroup radarGroup2 = radarGroup;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(RadarGroup.class), set);
        osObjectBuilder.addInteger(radarGroupColumnInfo.fixedIdColKey, Integer.valueOf(radarGroup2.realmGet$fixedId()));
        osObjectBuilder.addString(radarGroupColumnInfo.fixedNameColKey, radarGroup2.realmGet$fixedName());
        osObjectBuilder.addInteger(radarGroupColumnInfo.indexColKey, Integer.valueOf(radarGroup2.realmGet$index()));
        com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(radarGroup, com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyNewProxyInstance);
        RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList = radarGroup2.realmGet$bleDeviceRealmList();
        if (realmListRealmGet$bleDeviceRealmList != null) {
            RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList2 = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyNewProxyInstance.realmGet$bleDeviceRealmList();
            realmListRealmGet$bleDeviceRealmList2.clear();
            int i = 0;
            while (i < realmListRealmGet$bleDeviceRealmList.size()) {
                RadarDevice radarDevice = realmListRealmGet$bleDeviceRealmList.get(i);
                RadarDevice radarDevice2 = (RadarDevice) map.get(radarDevice);
                if (radarDevice2 != null) {
                    realmListRealmGet$bleDeviceRealmList2.add(radarDevice2);
                    realm2 = realm;
                    z2 = z;
                    map2 = map;
                    set2 = set;
                } else {
                    realm2 = realm;
                    z2 = z;
                    map2 = map;
                    set2 = set;
                    realmListRealmGet$bleDeviceRealmList2.add(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.copyOrUpdate(realm2, (com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.RadarDeviceColumnInfo) realm.getSchema().getColumnInfo(RadarDevice.class), radarDevice, z2, map2, set2));
                }
                i++;
                realm = realm2;
                z = z2;
                map = map2;
                set = set2;
            }
        }
        return com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, RadarGroup radarGroup, Map<RealmModel, Long> map) {
        if ((radarGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarGroup.class);
        long nativePtr = table.getNativePtr();
        RadarGroupColumnInfo radarGroupColumnInfo = (RadarGroupColumnInfo) realm.getSchema().getColumnInfo(RadarGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarGroup, Long.valueOf(jCreateRow));
        RadarGroup radarGroup2 = radarGroup;
        Table.nativeSetLong(nativePtr, radarGroupColumnInfo.fixedIdColKey, jCreateRow, radarGroup2.realmGet$fixedId(), false);
        String strRealmGet$fixedName = radarGroup2.realmGet$fixedName();
        if (strRealmGet$fixedName != null) {
            Table.nativeSetString(nativePtr, radarGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
        }
        Table.nativeSetLong(nativePtr, radarGroupColumnInfo.indexColKey, jCreateRow, radarGroup2.realmGet$index(), false);
        RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList = radarGroup2.realmGet$bleDeviceRealmList();
        if (realmListRealmGet$bleDeviceRealmList != null) {
            OsList osList = new OsList(table.getUncheckedRow(jCreateRow), radarGroupColumnInfo.bleDeviceRealmListColKey);
            for (RadarDevice radarDevice : realmListRealmGet$bleDeviceRealmList) {
                Long lValueOf = map.get(radarDevice);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insert(realm, radarDevice, map));
                }
                osList.addRow(lValueOf.longValue());
            }
        }
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarGroup.class);
        long nativePtr = table.getNativePtr();
        RadarGroupColumnInfo radarGroupColumnInfo = (RadarGroupColumnInfo) realm.getSchema().getColumnInfo(RadarGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, radarGroupColumnInfo.fixedIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$fixedId(), false);
                String strRealmGet$fixedName = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$fixedName();
                if (strRealmGet$fixedName != null) {
                    Table.nativeSetString(nativePtr, radarGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
                }
                Table.nativeSetLong(nativePtr, radarGroupColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$index(), false);
                RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$bleDeviceRealmList();
                if (realmListRealmGet$bleDeviceRealmList != null) {
                    OsList osList = new OsList(table.getUncheckedRow(jCreateRow), radarGroupColumnInfo.bleDeviceRealmListColKey);
                    for (RadarDevice radarDevice : realmListRealmGet$bleDeviceRealmList) {
                        Long lValueOf = map.get(radarDevice);
                        if (lValueOf == null) {
                            lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insert(realm, radarDevice, map));
                        }
                        osList.addRow(lValueOf.longValue());
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, RadarGroup radarGroup, Map<RealmModel, Long> map) {
        if ((radarGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarGroup.class);
        long nativePtr = table.getNativePtr();
        RadarGroupColumnInfo radarGroupColumnInfo = (RadarGroupColumnInfo) realm.getSchema().getColumnInfo(RadarGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarGroup, Long.valueOf(jCreateRow));
        RadarGroup radarGroup2 = radarGroup;
        Table.nativeSetLong(nativePtr, radarGroupColumnInfo.fixedIdColKey, jCreateRow, radarGroup2.realmGet$fixedId(), false);
        String strRealmGet$fixedName = radarGroup2.realmGet$fixedName();
        if (strRealmGet$fixedName != null) {
            Table.nativeSetString(nativePtr, radarGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
        } else {
            Table.nativeSetNull(nativePtr, radarGroupColumnInfo.fixedNameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, radarGroupColumnInfo.indexColKey, jCreateRow, radarGroup2.realmGet$index(), false);
        OsList osList = new OsList(table.getUncheckedRow(jCreateRow), radarGroupColumnInfo.bleDeviceRealmListColKey);
        RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList = radarGroup2.realmGet$bleDeviceRealmList();
        if (realmListRealmGet$bleDeviceRealmList != null && realmListRealmGet$bleDeviceRealmList.size() == osList.size()) {
            int size = realmListRealmGet$bleDeviceRealmList.size();
            for (int i = 0; i < size; i++) {
                RadarDevice radarDevice = realmListRealmGet$bleDeviceRealmList.get(i);
                Long lValueOf = map.get(radarDevice);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, radarDevice, map));
                }
                osList.setRow(i, lValueOf.longValue());
            }
        } else {
            osList.removeAll();
            if (realmListRealmGet$bleDeviceRealmList != null) {
                for (RadarDevice radarDevice2 : realmListRealmGet$bleDeviceRealmList) {
                    Long lValueOf2 = map.get(radarDevice2);
                    if (lValueOf2 == null) {
                        lValueOf2 = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, radarDevice2, map));
                    }
                    osList.addRow(lValueOf2.longValue());
                }
            }
        }
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarGroup.class);
        long nativePtr = table.getNativePtr();
        RadarGroupColumnInfo radarGroupColumnInfo = (RadarGroupColumnInfo) realm.getSchema().getColumnInfo(RadarGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, radarGroupColumnInfo.fixedIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$fixedId(), false);
                String strRealmGet$fixedName = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$fixedName();
                if (strRealmGet$fixedName != null) {
                    Table.nativeSetString(nativePtr, radarGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
                } else {
                    Table.nativeSetNull(nativePtr, radarGroupColumnInfo.fixedNameColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, radarGroupColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$index(), false);
                OsList osList = new OsList(table.getUncheckedRow(jCreateRow), radarGroupColumnInfo.bleDeviceRealmListColKey);
                RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxyinterface.realmGet$bleDeviceRealmList();
                if (realmListRealmGet$bleDeviceRealmList != null && realmListRealmGet$bleDeviceRealmList.size() == osList.size()) {
                    int size = realmListRealmGet$bleDeviceRealmList.size();
                    for (int i = 0; i < size; i++) {
                        RadarDevice radarDevice = realmListRealmGet$bleDeviceRealmList.get(i);
                        Long lValueOf = map.get(radarDevice);
                        if (lValueOf == null) {
                            lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, radarDevice, map));
                        }
                        osList.setRow(i, lValueOf.longValue());
                    }
                } else {
                    osList.removeAll();
                    if (realmListRealmGet$bleDeviceRealmList != null) {
                        for (RadarDevice radarDevice2 : realmListRealmGet$bleDeviceRealmList) {
                            Long lValueOf2 = map.get(radarDevice2);
                            if (lValueOf2 == null) {
                                lValueOf2 = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, radarDevice2, map));
                            }
                            osList.addRow(lValueOf2.longValue());
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarGroup createDetachedCopy(RadarGroup radarGroup, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        RadarGroup radarGroup2;
        if (i > i2 || radarGroup == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(radarGroup);
        if (cacheData == null) {
            radarGroup2 = new RadarGroup();
            map.put(radarGroup, new RealmObjectProxy.CacheData<>(i, radarGroup2));
        } else {
            if (i >= cacheData.minDepth) {
                return (RadarGroup) cacheData.object;
            }
            RadarGroup radarGroup3 = (RadarGroup) cacheData.object;
            cacheData.minDepth = i;
            radarGroup2 = radarGroup3;
        }
        RadarGroup radarGroup4 = radarGroup2;
        RadarGroup radarGroup5 = radarGroup;
        radarGroup4.realmSet$fixedId(radarGroup5.realmGet$fixedId());
        radarGroup4.realmSet$fixedName(radarGroup5.realmGet$fixedName());
        radarGroup4.realmSet$index(radarGroup5.realmGet$index());
        if (i == i2) {
            radarGroup4.realmSet$bleDeviceRealmList(null);
            return radarGroup2;
        }
        RealmList<RadarDevice> realmListRealmGet$bleDeviceRealmList = radarGroup5.realmGet$bleDeviceRealmList();
        RealmList<RadarDevice> realmList = new RealmList<>();
        radarGroup4.realmSet$bleDeviceRealmList(realmList);
        int i3 = i + 1;
        int size = realmListRealmGet$bleDeviceRealmList.size();
        for (int i4 = 0; i4 < size; i4++) {
            realmList.add(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createDetachedCopy(realmListRealmGet$bleDeviceRealmList.get(i4), i3, i2, map));
        }
        return radarGroup2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("RadarGroup = proxy[{fixedId:");
        sb.append(realmGet$fixedId());
        sb.append("},{fixedName:");
        sb.append(realmGet$fixedName() != null ? realmGet$fixedName() : GlobalVariable.nullColor);
        sb.append("},{index:");
        sb.append(realmGet$index());
        sb.append("},{bleDeviceRealmList:RealmList<RadarDevice>[");
        sb.append(realmGet$bleDeviceRealmList().size());
        sb.append("]}]");
        return sb.toString();
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
        com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy com_brgd_brblmesh_generaladapter_model_radargrouprealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_radargrouprealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_radargrouprealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

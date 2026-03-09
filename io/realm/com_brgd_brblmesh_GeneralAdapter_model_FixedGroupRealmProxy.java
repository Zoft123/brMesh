package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.BaseRealm;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy;
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
public class com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy extends FixedGroup implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private RealmList<BleDevice> bleDeviceRealmListRealmList;
    private FixedGroupColumnInfo columnInfo;
    private ProxyState<FixedGroup> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "FixedGroup";
    }

    static final class FixedGroupColumnInfo extends ColumnInfo {
        long bleDeviceRealmListColKey;
        long fileNameColKey;
        long fixedIdColKey;
        long fixedNameColKey;
        long iconIndexColKey;
        long indexColKey;

        FixedGroupColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.fixedIdColKey = addColumnDetails("fixedId", "fixedId", objectSchemaInfo);
            this.fixedNameColKey = addColumnDetails(GlobalVariable.FIXEDNAME, GlobalVariable.FIXEDNAME, objectSchemaInfo);
            this.bleDeviceRealmListColKey = addColumnDetails("bleDeviceRealmList", "bleDeviceRealmList", objectSchemaInfo);
            this.indexColKey = addColumnDetails(GlobalVariable.INDEX, GlobalVariable.INDEX, objectSchemaInfo);
            this.fileNameColKey = addColumnDetails("fileName", "fileName", objectSchemaInfo);
            this.iconIndexColKey = addColumnDetails(GlobalVariable.ICONINDEX, GlobalVariable.ICONINDEX, objectSchemaInfo);
        }

        FixedGroupColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new FixedGroupColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            FixedGroupColumnInfo fixedGroupColumnInfo = (FixedGroupColumnInfo) columnInfo;
            FixedGroupColumnInfo fixedGroupColumnInfo2 = (FixedGroupColumnInfo) columnInfo2;
            fixedGroupColumnInfo2.fixedIdColKey = fixedGroupColumnInfo.fixedIdColKey;
            fixedGroupColumnInfo2.fixedNameColKey = fixedGroupColumnInfo.fixedNameColKey;
            fixedGroupColumnInfo2.bleDeviceRealmListColKey = fixedGroupColumnInfo.bleDeviceRealmListColKey;
            fixedGroupColumnInfo2.indexColKey = fixedGroupColumnInfo.indexColKey;
            fixedGroupColumnInfo2.fileNameColKey = fixedGroupColumnInfo.fileNameColKey;
            fixedGroupColumnInfo2.iconIndexColKey = fixedGroupColumnInfo.iconIndexColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (FixedGroupColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<FixedGroup> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public int realmGet$fixedId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.fixedIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public String realmGet$fixedName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.fixedNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public RealmList<BleDevice> realmGet$bleDeviceRealmList() {
        this.proxyState.getRealm$realm().checkIfValid();
        RealmList<BleDevice> realmList = this.bleDeviceRealmListRealmList;
        if (realmList != null) {
            return realmList;
        }
        RealmList<BleDevice> realmList2 = new RealmList<>((Class<BleDevice>) BleDevice.class, this.proxyState.getRow$realm().getModelList(this.columnInfo.bleDeviceRealmListColKey), this.proxyState.getRealm$realm());
        this.bleDeviceRealmListRealmList = realmList2;
        return realmList2;
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$bleDeviceRealmList(RealmList<BleDevice> realmList) {
        int i = 0;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("bleDeviceRealmList")) {
                return;
            }
            if (realmList != null && !realmList.isManaged()) {
                Realm realm = (Realm) this.proxyState.getRealm$realm();
                RealmList<BleDevice> realmList2 = new RealmList<>();
                for (BleDevice bleDevice : realmList) {
                    if (bleDevice == null || RealmObject.isManaged(bleDevice)) {
                        realmList2.add(bleDevice);
                    } else {
                        realmList2.add((BleDevice) realm.copyToRealm(bleDevice, new ImportFlag[0]));
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
                RealmModel realmModel = (BleDevice) realmList.get(i);
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
            RealmModel realmModel2 = (BleDevice) realmList.get(i);
            this.proxyState.checkValidObject(realmModel2);
            modelList.addRow(((RealmObjectProxy) realmModel2).realmGet$proxyState().getRow$realm().getObjectKey());
            i++;
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public int realmGet$index() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public String realmGet$fileName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.fileNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$fileName(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.fileNameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.fileNameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.fileNameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.fileNameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public int realmGet$iconIndex() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.iconIndexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.FixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$iconIndex(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.iconIndexColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.iconIndexColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 6, 0);
        builder.addPersistedProperty("", "fixedId", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.FIXEDNAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedLinkProperty("", "bleDeviceRealmList", RealmFieldType.LIST, com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        builder.addPersistedProperty("", GlobalVariable.INDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "fileName", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.ICONINDEX, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static FixedGroupColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new FixedGroupColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static FixedGroup createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        ArrayList arrayList = new ArrayList(1);
        if (jSONObject.has("bleDeviceRealmList")) {
            arrayList.add("bleDeviceRealmList");
        }
        FixedGroup fixedGroup = (FixedGroup) realm.createObjectInternal(FixedGroup.class, true, arrayList);
        FixedGroup fixedGroup2 = fixedGroup;
        if (jSONObject.has("fixedId")) {
            if (jSONObject.isNull("fixedId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
            }
            fixedGroup2.realmSet$fixedId(jSONObject.getInt("fixedId"));
        }
        if (jSONObject.has(GlobalVariable.FIXEDNAME)) {
            if (jSONObject.isNull(GlobalVariable.FIXEDNAME)) {
                fixedGroup2.realmSet$fixedName(null);
            } else {
                fixedGroup2.realmSet$fixedName(jSONObject.getString(GlobalVariable.FIXEDNAME));
            }
        }
        if (jSONObject.has("bleDeviceRealmList")) {
            if (jSONObject.isNull("bleDeviceRealmList")) {
                fixedGroup2.realmSet$bleDeviceRealmList(null);
            } else {
                fixedGroup2.realmGet$bleDeviceRealmList().clear();
                JSONArray jSONArray = jSONObject.getJSONArray("bleDeviceRealmList");
                for (int i = 0; i < jSONArray.length(); i++) {
                    fixedGroup2.realmGet$bleDeviceRealmList().add(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createOrUpdateUsingJsonObject(realm, jSONArray.getJSONObject(i), z));
                }
            }
        }
        if (jSONObject.has(GlobalVariable.INDEX)) {
            if (jSONObject.isNull(GlobalVariable.INDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
            }
            fixedGroup2.realmSet$index(jSONObject.getInt(GlobalVariable.INDEX));
        }
        if (jSONObject.has("fileName")) {
            if (jSONObject.isNull("fileName")) {
                fixedGroup2.realmSet$fileName(null);
            } else {
                fixedGroup2.realmSet$fileName(jSONObject.getString("fileName"));
            }
        }
        if (!jSONObject.has(GlobalVariable.ICONINDEX)) {
            return fixedGroup;
        }
        if (jSONObject.isNull(GlobalVariable.ICONINDEX)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'iconIndex' to null.");
        }
        fixedGroup2.realmSet$iconIndex(jSONObject.getInt(GlobalVariable.ICONINDEX));
        return fixedGroup;
    }

    public static FixedGroup createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        FixedGroup fixedGroup = new FixedGroup();
        FixedGroup fixedGroup2 = fixedGroup;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("fixedId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    fixedGroup2.realmSet$fixedId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.FIXEDNAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    fixedGroup2.realmSet$fixedName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    fixedGroup2.realmSet$fixedName(null);
                }
            } else if (strNextName.equals("bleDeviceRealmList")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    fixedGroup2.realmSet$bleDeviceRealmList(null);
                } else {
                    fixedGroup2.realmSet$bleDeviceRealmList(new RealmList<>());
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        fixedGroup2.realmGet$bleDeviceRealmList().add(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createUsingJsonStream(realm, jsonReader));
                    }
                    jsonReader.endArray();
                }
            } else if (strNextName.equals(GlobalVariable.INDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    fixedGroup2.realmSet$index(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
                }
            } else if (strNextName.equals("fileName")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    fixedGroup2.realmSet$fileName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    fixedGroup2.realmSet$fileName(null);
                }
            } else if (strNextName.equals(GlobalVariable.ICONINDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    fixedGroup2.realmSet$iconIndex(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'iconIndex' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (FixedGroup) realm.copyToRealm(fixedGroup, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(FixedGroup.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static FixedGroup copyOrUpdate(Realm realm, FixedGroupColumnInfo fixedGroupColumnInfo, FixedGroup fixedGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((fixedGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(fixedGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) fixedGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return fixedGroup;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(fixedGroup);
        if (realmModel != null) {
            return (FixedGroup) realmModel;
        }
        return copy(realm, fixedGroupColumnInfo, fixedGroup, z, map, set);
    }

    public static FixedGroup copy(Realm realm, FixedGroupColumnInfo fixedGroupColumnInfo, FixedGroup fixedGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Realm realm2;
        boolean z2;
        Map<RealmModel, RealmObjectProxy> map2;
        Set<ImportFlag> set2;
        RealmModel realmModel = (RealmObjectProxy) map.get(fixedGroup);
        if (realmModel != null) {
            return (FixedGroup) realmModel;
        }
        FixedGroup fixedGroup2 = fixedGroup;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(FixedGroup.class), set);
        osObjectBuilder.addInteger(fixedGroupColumnInfo.fixedIdColKey, Integer.valueOf(fixedGroup2.realmGet$fixedId()));
        osObjectBuilder.addString(fixedGroupColumnInfo.fixedNameColKey, fixedGroup2.realmGet$fixedName());
        osObjectBuilder.addInteger(fixedGroupColumnInfo.indexColKey, Integer.valueOf(fixedGroup2.realmGet$index()));
        osObjectBuilder.addString(fixedGroupColumnInfo.fileNameColKey, fixedGroup2.realmGet$fileName());
        osObjectBuilder.addInteger(fixedGroupColumnInfo.iconIndexColKey, Integer.valueOf(fixedGroup2.realmGet$iconIndex()));
        com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(fixedGroup, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyNewProxyInstance);
        RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList = fixedGroup2.realmGet$bleDeviceRealmList();
        if (realmListRealmGet$bleDeviceRealmList != null) {
            RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList2 = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyNewProxyInstance.realmGet$bleDeviceRealmList();
            realmListRealmGet$bleDeviceRealmList2.clear();
            int i = 0;
            while (i < realmListRealmGet$bleDeviceRealmList.size()) {
                BleDevice bleDevice = realmListRealmGet$bleDeviceRealmList.get(i);
                BleDevice bleDevice2 = (BleDevice) map.get(bleDevice);
                if (bleDevice2 != null) {
                    realmListRealmGet$bleDeviceRealmList2.add(bleDevice2);
                    realm2 = realm;
                    z2 = z;
                    map2 = map;
                    set2 = set;
                } else {
                    realm2 = realm;
                    z2 = z;
                    map2 = map;
                    set2 = set;
                    realmListRealmGet$bleDeviceRealmList2.add(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.copyOrUpdate(realm2, (com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.BleDeviceColumnInfo) realm.getSchema().getColumnInfo(BleDevice.class), bleDevice, z2, map2, set2));
                }
                i++;
                realm = realm2;
                z = z2;
                map = map2;
                set = set2;
            }
        }
        return com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, FixedGroup fixedGroup, Map<RealmModel, Long> map) {
        if ((fixedGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(fixedGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) fixedGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(FixedGroup.class);
        long nativePtr = table.getNativePtr();
        FixedGroupColumnInfo fixedGroupColumnInfo = (FixedGroupColumnInfo) realm.getSchema().getColumnInfo(FixedGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(fixedGroup, Long.valueOf(jCreateRow));
        FixedGroup fixedGroup2 = fixedGroup;
        Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.fixedIdColKey, jCreateRow, fixedGroup2.realmGet$fixedId(), false);
        String strRealmGet$fixedName = fixedGroup2.realmGet$fixedName();
        if (strRealmGet$fixedName != null) {
            Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
        }
        RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList = fixedGroup2.realmGet$bleDeviceRealmList();
        if (realmListRealmGet$bleDeviceRealmList != null) {
            OsList osList = new OsList(table.getUncheckedRow(jCreateRow), fixedGroupColumnInfo.bleDeviceRealmListColKey);
            for (BleDevice bleDevice : realmListRealmGet$bleDeviceRealmList) {
                Long lValueOf = map.get(bleDevice);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insert(realm, bleDevice, map));
                }
                osList.addRow(lValueOf.longValue());
            }
        }
        Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.indexColKey, jCreateRow, fixedGroup2.realmGet$index(), false);
        String strRealmGet$fileName = fixedGroup2.realmGet$fileName();
        if (strRealmGet$fileName != null) {
            Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fileNameColKey, jCreateRow, strRealmGet$fileName, false);
        }
        Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.iconIndexColKey, jCreateRow, fixedGroup2.realmGet$iconIndex(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(FixedGroup.class);
        long nativePtr = table.getNativePtr();
        FixedGroupColumnInfo fixedGroupColumnInfo = (FixedGroupColumnInfo) realm.getSchema().getColumnInfo(FixedGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (FixedGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.fixedIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$fixedId(), false);
                String strRealmGet$fixedName = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$fixedName();
                if (strRealmGet$fixedName != null) {
                    Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
                }
                RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$bleDeviceRealmList();
                if (realmListRealmGet$bleDeviceRealmList != null) {
                    OsList osList = new OsList(table.getUncheckedRow(jCreateRow), fixedGroupColumnInfo.bleDeviceRealmListColKey);
                    for (BleDevice bleDevice : realmListRealmGet$bleDeviceRealmList) {
                        Long lValueOf = map.get(bleDevice);
                        if (lValueOf == null) {
                            lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insert(realm, bleDevice, map));
                        }
                        osList.addRow(lValueOf.longValue());
                    }
                }
                Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$index(), false);
                String strRealmGet$fileName = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$fileName();
                if (strRealmGet$fileName != null) {
                    Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fileNameColKey, jCreateRow, strRealmGet$fileName, false);
                }
                Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.iconIndexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$iconIndex(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, FixedGroup fixedGroup, Map<RealmModel, Long> map) {
        if ((fixedGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(fixedGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) fixedGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(FixedGroup.class);
        long nativePtr = table.getNativePtr();
        FixedGroupColumnInfo fixedGroupColumnInfo = (FixedGroupColumnInfo) realm.getSchema().getColumnInfo(FixedGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(fixedGroup, Long.valueOf(jCreateRow));
        FixedGroup fixedGroup2 = fixedGroup;
        Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.fixedIdColKey, jCreateRow, fixedGroup2.realmGet$fixedId(), false);
        String strRealmGet$fixedName = fixedGroup2.realmGet$fixedName();
        if (strRealmGet$fixedName != null) {
            Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
        } else {
            Table.nativeSetNull(nativePtr, fixedGroupColumnInfo.fixedNameColKey, jCreateRow, false);
        }
        OsList osList = new OsList(table.getUncheckedRow(jCreateRow), fixedGroupColumnInfo.bleDeviceRealmListColKey);
        RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList = fixedGroup2.realmGet$bleDeviceRealmList();
        if (realmListRealmGet$bleDeviceRealmList != null && realmListRealmGet$bleDeviceRealmList.size() == osList.size()) {
            int size = realmListRealmGet$bleDeviceRealmList.size();
            for (int i = 0; i < size; i++) {
                BleDevice bleDevice = realmListRealmGet$bleDeviceRealmList.get(i);
                Long lValueOf = map.get(bleDevice);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, bleDevice, map));
                }
                osList.setRow(i, lValueOf.longValue());
            }
        } else {
            osList.removeAll();
            if (realmListRealmGet$bleDeviceRealmList != null) {
                for (BleDevice bleDevice2 : realmListRealmGet$bleDeviceRealmList) {
                    Long lValueOf2 = map.get(bleDevice2);
                    if (lValueOf2 == null) {
                        lValueOf2 = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, bleDevice2, map));
                    }
                    osList.addRow(lValueOf2.longValue());
                }
            }
        }
        Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.indexColKey, jCreateRow, fixedGroup2.realmGet$index(), false);
        String strRealmGet$fileName = fixedGroup2.realmGet$fileName();
        if (strRealmGet$fileName != null) {
            Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fileNameColKey, jCreateRow, strRealmGet$fileName, false);
        } else {
            Table.nativeSetNull(nativePtr, fixedGroupColumnInfo.fileNameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.iconIndexColKey, jCreateRow, fixedGroup2.realmGet$iconIndex(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table;
        long j;
        long j2;
        Table table2 = realm.getTable(FixedGroup.class);
        long nativePtr = table2.getNativePtr();
        FixedGroupColumnInfo fixedGroupColumnInfo = (FixedGroupColumnInfo) realm.getSchema().getColumnInfo(FixedGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (FixedGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table2);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.fixedIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$fixedId(), false);
                String strRealmGet$fixedName = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$fixedName();
                if (strRealmGet$fixedName != null) {
                    Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fixedNameColKey, jCreateRow, strRealmGet$fixedName, false);
                } else {
                    Table.nativeSetNull(nativePtr, fixedGroupColumnInfo.fixedNameColKey, jCreateRow, false);
                }
                OsList osList = new OsList(table2.getUncheckedRow(jCreateRow), fixedGroupColumnInfo.bleDeviceRealmListColKey);
                RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$bleDeviceRealmList();
                if (realmListRealmGet$bleDeviceRealmList != null && realmListRealmGet$bleDeviceRealmList.size() == osList.size()) {
                    int size = realmListRealmGet$bleDeviceRealmList.size();
                    int i = 0;
                    while (i < size) {
                        BleDevice bleDevice = realmListRealmGet$bleDeviceRealmList.get(i);
                        Long lValueOf = map.get(bleDevice);
                        if (lValueOf == null) {
                            lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, bleDevice, map));
                        }
                        osList.setRow(i, lValueOf.longValue());
                        i++;
                        realmListRealmGet$bleDeviceRealmList = realmListRealmGet$bleDeviceRealmList;
                        table2 = table2;
                        nativePtr = nativePtr;
                        jCreateRow = jCreateRow;
                    }
                    table = table2;
                    j = nativePtr;
                    j2 = jCreateRow;
                } else {
                    table = table2;
                    j = nativePtr;
                    j2 = jCreateRow;
                    osList.removeAll();
                    if (realmListRealmGet$bleDeviceRealmList != null) {
                        for (BleDevice bleDevice2 : realmListRealmGet$bleDeviceRealmList) {
                            Long lValueOf2 = map.get(bleDevice2);
                            if (lValueOf2 == null) {
                                lValueOf2 = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, bleDevice2, map));
                            }
                            osList.addRow(lValueOf2.longValue());
                        }
                    }
                }
                nativePtr = j;
                long j3 = j2;
                Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.indexColKey, j3, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$index(), false);
                String strRealmGet$fileName = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$fileName();
                if (strRealmGet$fileName != null) {
                    Table.nativeSetString(nativePtr, fixedGroupColumnInfo.fileNameColKey, j3, strRealmGet$fileName, false);
                } else {
                    Table.nativeSetNull(nativePtr, fixedGroupColumnInfo.fileNameColKey, j3, false);
                }
                Table.nativeSetLong(nativePtr, fixedGroupColumnInfo.iconIndexColKey, j3, com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxyinterface.realmGet$iconIndex(), false);
                table2 = table;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static FixedGroup createDetachedCopy(FixedGroup fixedGroup, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        FixedGroup fixedGroup2;
        if (i > i2 || fixedGroup == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(fixedGroup);
        if (cacheData == null) {
            fixedGroup2 = new FixedGroup();
            map.put(fixedGroup, new RealmObjectProxy.CacheData<>(i, fixedGroup2));
        } else {
            if (i >= cacheData.minDepth) {
                return (FixedGroup) cacheData.object;
            }
            FixedGroup fixedGroup3 = (FixedGroup) cacheData.object;
            cacheData.minDepth = i;
            fixedGroup2 = fixedGroup3;
        }
        FixedGroup fixedGroup4 = fixedGroup2;
        FixedGroup fixedGroup5 = fixedGroup;
        fixedGroup4.realmSet$fixedId(fixedGroup5.realmGet$fixedId());
        fixedGroup4.realmSet$fixedName(fixedGroup5.realmGet$fixedName());
        if (i == i2) {
            fixedGroup4.realmSet$bleDeviceRealmList(null);
        } else {
            RealmList<BleDevice> realmListRealmGet$bleDeviceRealmList = fixedGroup5.realmGet$bleDeviceRealmList();
            RealmList<BleDevice> realmList = new RealmList<>();
            fixedGroup4.realmSet$bleDeviceRealmList(realmList);
            int i3 = i + 1;
            int size = realmListRealmGet$bleDeviceRealmList.size();
            for (int i4 = 0; i4 < size; i4++) {
                realmList.add(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createDetachedCopy(realmListRealmGet$bleDeviceRealmList.get(i4), i3, i2, map));
            }
        }
        fixedGroup4.realmSet$index(fixedGroup5.realmGet$index());
        fixedGroup4.realmSet$fileName(fixedGroup5.realmGet$fileName());
        fixedGroup4.realmSet$iconIndex(fixedGroup5.realmGet$iconIndex());
        return fixedGroup2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("FixedGroup = proxy[{fixedId:");
        sb.append(realmGet$fixedId());
        sb.append("},{fixedName:");
        String strRealmGet$fixedName = realmGet$fixedName();
        String strRealmGet$fileName = GlobalVariable.nullColor;
        sb.append(strRealmGet$fixedName != null ? realmGet$fixedName() : GlobalVariable.nullColor);
        sb.append("},{bleDeviceRealmList:RealmList<BleDevice>[");
        sb.append(realmGet$bleDeviceRealmList().size());
        sb.append("]},{index:");
        sb.append(realmGet$index());
        sb.append("},{fileName:");
        if (realmGet$fileName() != null) {
            strRealmGet$fileName = realmGet$fileName();
        }
        sb.append(strRealmGet$fileName);
        sb.append("},{iconIndex:");
        sb.append(realmGet$iconIndex());
        sb.append("}]");
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
        com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_fixedgrouprealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

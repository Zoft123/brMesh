package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.BaseRealm;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy;
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
public class com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy extends Scene implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private SceneColumnInfo columnInfo;
    private ProxyState<Scene> proxyState;
    private RealmList<SceneDevice> sceneDeviceRealmListRealmList;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Scene";
    }

    static final class SceneColumnInfo extends ColumnInfo {
        long iconIndexColKey;
        long indexColKey;
        long sceneDeviceRealmListColKey;
        long sceneFileNameColKey;
        long sceneNameColKey;
        long sceneNumberColKey;

        SceneColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo("Scene");
            this.indexColKey = addColumnDetails(GlobalVariable.INDEX, GlobalVariable.INDEX, objectSchemaInfo);
            this.sceneNumberColKey = addColumnDetails(GlobalVariable.SCENENUM, GlobalVariable.SCENENUM, objectSchemaInfo);
            this.sceneNameColKey = addColumnDetails(GlobalVariable.SCENENAME, GlobalVariable.SCENENAME, objectSchemaInfo);
            this.sceneFileNameColKey = addColumnDetails("sceneFileName", "sceneFileName", objectSchemaInfo);
            this.iconIndexColKey = addColumnDetails(GlobalVariable.ICONINDEX, GlobalVariable.ICONINDEX, objectSchemaInfo);
            this.sceneDeviceRealmListColKey = addColumnDetails("sceneDeviceRealmList", "sceneDeviceRealmList", objectSchemaInfo);
        }

        SceneColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new SceneColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            SceneColumnInfo sceneColumnInfo = (SceneColumnInfo) columnInfo;
            SceneColumnInfo sceneColumnInfo2 = (SceneColumnInfo) columnInfo2;
            sceneColumnInfo2.indexColKey = sceneColumnInfo.indexColKey;
            sceneColumnInfo2.sceneNumberColKey = sceneColumnInfo.sceneNumberColKey;
            sceneColumnInfo2.sceneNameColKey = sceneColumnInfo.sceneNameColKey;
            sceneColumnInfo2.sceneFileNameColKey = sceneColumnInfo.sceneFileNameColKey;
            sceneColumnInfo2.iconIndexColKey = sceneColumnInfo.iconIndexColKey;
            sceneColumnInfo2.sceneDeviceRealmListColKey = sceneColumnInfo.sceneDeviceRealmListColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (SceneColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<Scene> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public int realmGet$index() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public int realmGet$sceneNumber() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.sceneNumberColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneNumber(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.sceneNumberColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.sceneNumberColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public String realmGet$sceneName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.sceneNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneName(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.sceneNameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.sceneNameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.sceneNameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.sceneNameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public String realmGet$sceneFileName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.sceneFileNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneFileName(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.sceneFileNameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.sceneFileNameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.sceneFileNameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.sceneFileNameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public int realmGet$iconIndex() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.iconIndexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public RealmList<SceneDevice> realmGet$sceneDeviceRealmList() {
        this.proxyState.getRealm$realm().checkIfValid();
        RealmList<SceneDevice> realmList = this.sceneDeviceRealmListRealmList;
        if (realmList != null) {
            return realmList;
        }
        RealmList<SceneDevice> realmList2 = new RealmList<>((Class<SceneDevice>) SceneDevice.class, this.proxyState.getRow$realm().getModelList(this.columnInfo.sceneDeviceRealmListColKey), this.proxyState.getRealm$realm());
        this.sceneDeviceRealmListRealmList = realmList2;
        return realmList2;
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Scene, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneDeviceRealmList(RealmList<SceneDevice> realmList) {
        int i = 0;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("sceneDeviceRealmList")) {
                return;
            }
            if (realmList != null && !realmList.isManaged()) {
                Realm realm = (Realm) this.proxyState.getRealm$realm();
                RealmList<SceneDevice> realmList2 = new RealmList<>();
                for (SceneDevice sceneDevice : realmList) {
                    if (sceneDevice == null || RealmObject.isManaged(sceneDevice)) {
                        realmList2.add(sceneDevice);
                    } else {
                        realmList2.add((SceneDevice) realm.copyToRealm(sceneDevice, new ImportFlag[0]));
                    }
                }
                realmList = realmList2;
            }
        }
        this.proxyState.getRealm$realm().checkIfValid();
        OsList modelList = this.proxyState.getRow$realm().getModelList(this.columnInfo.sceneDeviceRealmListColKey);
        if (realmList != null && realmList.size() == modelList.size()) {
            int size = realmList.size();
            while (i < size) {
                RealmModel realmModel = (SceneDevice) realmList.get(i);
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
            RealmModel realmModel2 = (SceneDevice) realmList.get(i);
            this.proxyState.checkValidObject(realmModel2);
            modelList.addRow(((RealmObjectProxy) realmModel2).realmGet$proxyState().getRow$realm().getObjectKey());
            i++;
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", "Scene", false, 6, 0);
        builder.addPersistedProperty("", GlobalVariable.INDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.SCENENUM, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.SCENENAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", "sceneFileName", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.ICONINDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedLinkProperty("", "sceneDeviceRealmList", RealmFieldType.LIST, com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static SceneColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new SceneColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return "Scene";
    }

    public static Scene createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        ArrayList arrayList = new ArrayList(1);
        if (jSONObject.has("sceneDeviceRealmList")) {
            arrayList.add("sceneDeviceRealmList");
        }
        Scene scene = (Scene) realm.createObjectInternal(Scene.class, true, arrayList);
        Scene scene2 = scene;
        if (jSONObject.has(GlobalVariable.INDEX)) {
            if (jSONObject.isNull(GlobalVariable.INDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
            }
            scene2.realmSet$index(jSONObject.getInt(GlobalVariable.INDEX));
        }
        if (jSONObject.has(GlobalVariable.SCENENUM)) {
            if (jSONObject.isNull(GlobalVariable.SCENENUM)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'sceneNumber' to null.");
            }
            scene2.realmSet$sceneNumber(jSONObject.getInt(GlobalVariable.SCENENUM));
        }
        if (jSONObject.has(GlobalVariable.SCENENAME)) {
            if (jSONObject.isNull(GlobalVariable.SCENENAME)) {
                scene2.realmSet$sceneName(null);
            } else {
                scene2.realmSet$sceneName(jSONObject.getString(GlobalVariable.SCENENAME));
            }
        }
        if (jSONObject.has("sceneFileName")) {
            if (jSONObject.isNull("sceneFileName")) {
                scene2.realmSet$sceneFileName(null);
            } else {
                scene2.realmSet$sceneFileName(jSONObject.getString("sceneFileName"));
            }
        }
        if (jSONObject.has(GlobalVariable.ICONINDEX)) {
            if (jSONObject.isNull(GlobalVariable.ICONINDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'iconIndex' to null.");
            }
            scene2.realmSet$iconIndex(jSONObject.getInt(GlobalVariable.ICONINDEX));
        }
        if (jSONObject.has("sceneDeviceRealmList")) {
            if (jSONObject.isNull("sceneDeviceRealmList")) {
                scene2.realmSet$sceneDeviceRealmList(null);
                return scene;
            }
            scene2.realmGet$sceneDeviceRealmList().clear();
            JSONArray jSONArray = jSONObject.getJSONArray("sceneDeviceRealmList");
            for (int i = 0; i < jSONArray.length(); i++) {
                scene2.realmGet$sceneDeviceRealmList().add(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createOrUpdateUsingJsonObject(realm, jSONArray.getJSONObject(i), z));
            }
        }
        return scene;
    }

    public static Scene createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        Scene scene = new Scene();
        Scene scene2 = scene;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.INDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    scene2.realmSet$index(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.SCENENUM)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    scene2.realmSet$sceneNumber(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sceneNumber' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.SCENENAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    scene2.realmSet$sceneName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    scene2.realmSet$sceneName(null);
                }
            } else if (strNextName.equals("sceneFileName")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    scene2.realmSet$sceneFileName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    scene2.realmSet$sceneFileName(null);
                }
            } else if (strNextName.equals(GlobalVariable.ICONINDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    scene2.realmSet$iconIndex(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'iconIndex' to null.");
                }
            } else if (strNextName.equals("sceneDeviceRealmList")) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    scene2.realmSet$sceneDeviceRealmList(null);
                } else {
                    scene2.realmSet$sceneDeviceRealmList(new RealmList<>());
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        scene2.realmGet$sceneDeviceRealmList().add(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createUsingJsonStream(realm, jsonReader));
                    }
                    jsonReader.endArray();
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (Scene) realm.copyToRealm(scene, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(Scene.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy com_brgd_brblmesh_generaladapter_model_scenerealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_scenerealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Scene copyOrUpdate(Realm realm, SceneColumnInfo sceneColumnInfo, Scene scene, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((scene instanceof RealmObjectProxy) && !RealmObject.isFrozen(scene)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) scene;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return scene;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(scene);
        if (realmModel != null) {
            return (Scene) realmModel;
        }
        return copy(realm, sceneColumnInfo, scene, z, map, set);
    }

    public static Scene copy(Realm realm, SceneColumnInfo sceneColumnInfo, Scene scene, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Realm realm2;
        boolean z2;
        Map<RealmModel, RealmObjectProxy> map2;
        Set<ImportFlag> set2;
        RealmModel realmModel = (RealmObjectProxy) map.get(scene);
        if (realmModel != null) {
            return (Scene) realmModel;
        }
        Scene scene2 = scene;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(Scene.class), set);
        osObjectBuilder.addInteger(sceneColumnInfo.indexColKey, Integer.valueOf(scene2.realmGet$index()));
        osObjectBuilder.addInteger(sceneColumnInfo.sceneNumberColKey, Integer.valueOf(scene2.realmGet$sceneNumber()));
        osObjectBuilder.addString(sceneColumnInfo.sceneNameColKey, scene2.realmGet$sceneName());
        osObjectBuilder.addString(sceneColumnInfo.sceneFileNameColKey, scene2.realmGet$sceneFileName());
        osObjectBuilder.addInteger(sceneColumnInfo.iconIndexColKey, Integer.valueOf(scene2.realmGet$iconIndex()));
        com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy com_brgd_brblmesh_generaladapter_model_scenerealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(scene, com_brgd_brblmesh_generaladapter_model_scenerealmproxyNewProxyInstance);
        RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList = scene2.realmGet$sceneDeviceRealmList();
        if (realmListRealmGet$sceneDeviceRealmList != null) {
            RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList2 = com_brgd_brblmesh_generaladapter_model_scenerealmproxyNewProxyInstance.realmGet$sceneDeviceRealmList();
            realmListRealmGet$sceneDeviceRealmList2.clear();
            int i = 0;
            while (i < realmListRealmGet$sceneDeviceRealmList.size()) {
                SceneDevice sceneDevice = realmListRealmGet$sceneDeviceRealmList.get(i);
                SceneDevice sceneDevice2 = (SceneDevice) map.get(sceneDevice);
                if (sceneDevice2 != null) {
                    realmListRealmGet$sceneDeviceRealmList2.add(sceneDevice2);
                    realm2 = realm;
                    z2 = z;
                    map2 = map;
                    set2 = set;
                } else {
                    realm2 = realm;
                    z2 = z;
                    map2 = map;
                    set2 = set;
                    realmListRealmGet$sceneDeviceRealmList2.add(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.copyOrUpdate(realm2, (com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.SceneDeviceColumnInfo) realm.getSchema().getColumnInfo(SceneDevice.class), sceneDevice, z2, map2, set2));
                }
                i++;
                realm = realm2;
                z = z2;
                map = map2;
                set = set2;
            }
        }
        return com_brgd_brblmesh_generaladapter_model_scenerealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, Scene scene, Map<RealmModel, Long> map) {
        if ((scene instanceof RealmObjectProxy) && !RealmObject.isFrozen(scene)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) scene;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Scene.class);
        long nativePtr = table.getNativePtr();
        SceneColumnInfo sceneColumnInfo = (SceneColumnInfo) realm.getSchema().getColumnInfo(Scene.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(scene, Long.valueOf(jCreateRow));
        Scene scene2 = scene;
        Table.nativeSetLong(nativePtr, sceneColumnInfo.indexColKey, jCreateRow, scene2.realmGet$index(), false);
        Table.nativeSetLong(nativePtr, sceneColumnInfo.sceneNumberColKey, jCreateRow, scene2.realmGet$sceneNumber(), false);
        String strRealmGet$sceneName = scene2.realmGet$sceneName();
        if (strRealmGet$sceneName != null) {
            Table.nativeSetString(nativePtr, sceneColumnInfo.sceneNameColKey, jCreateRow, strRealmGet$sceneName, false);
        }
        String strRealmGet$sceneFileName = scene2.realmGet$sceneFileName();
        if (strRealmGet$sceneFileName != null) {
            Table.nativeSetString(nativePtr, sceneColumnInfo.sceneFileNameColKey, jCreateRow, strRealmGet$sceneFileName, false);
        }
        Table.nativeSetLong(nativePtr, sceneColumnInfo.iconIndexColKey, jCreateRow, scene2.realmGet$iconIndex(), false);
        RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList = scene2.realmGet$sceneDeviceRealmList();
        if (realmListRealmGet$sceneDeviceRealmList != null) {
            OsList osList = new OsList(table.getUncheckedRow(jCreateRow), sceneColumnInfo.sceneDeviceRealmListColKey);
            for (SceneDevice sceneDevice : realmListRealmGet$sceneDeviceRealmList) {
                Long lValueOf = map.get(sceneDevice);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insert(realm, sceneDevice, map));
                }
                osList.addRow(lValueOf.longValue());
            }
        }
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Scene.class);
        long nativePtr = table.getNativePtr();
        SceneColumnInfo sceneColumnInfo = (SceneColumnInfo) realm.getSchema().getColumnInfo(Scene.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Scene) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, sceneColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$index(), false);
                Table.nativeSetLong(nativePtr, sceneColumnInfo.sceneNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneNumber(), false);
                String strRealmGet$sceneName = com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneName();
                if (strRealmGet$sceneName != null) {
                    Table.nativeSetString(nativePtr, sceneColumnInfo.sceneNameColKey, jCreateRow, strRealmGet$sceneName, false);
                }
                String strRealmGet$sceneFileName = com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneFileName();
                if (strRealmGet$sceneFileName != null) {
                    Table.nativeSetString(nativePtr, sceneColumnInfo.sceneFileNameColKey, jCreateRow, strRealmGet$sceneFileName, false);
                }
                Table.nativeSetLong(nativePtr, sceneColumnInfo.iconIndexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$iconIndex(), false);
                RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList = com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneDeviceRealmList();
                if (realmListRealmGet$sceneDeviceRealmList != null) {
                    OsList osList = new OsList(table.getUncheckedRow(jCreateRow), sceneColumnInfo.sceneDeviceRealmListColKey);
                    for (SceneDevice sceneDevice : realmListRealmGet$sceneDeviceRealmList) {
                        Long lValueOf = map.get(sceneDevice);
                        if (lValueOf == null) {
                            lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insert(realm, sceneDevice, map));
                        }
                        osList.addRow(lValueOf.longValue());
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, Scene scene, Map<RealmModel, Long> map) {
        if ((scene instanceof RealmObjectProxy) && !RealmObject.isFrozen(scene)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) scene;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Scene.class);
        long nativePtr = table.getNativePtr();
        SceneColumnInfo sceneColumnInfo = (SceneColumnInfo) realm.getSchema().getColumnInfo(Scene.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(scene, Long.valueOf(jCreateRow));
        Scene scene2 = scene;
        Table.nativeSetLong(nativePtr, sceneColumnInfo.indexColKey, jCreateRow, scene2.realmGet$index(), false);
        Table.nativeSetLong(nativePtr, sceneColumnInfo.sceneNumberColKey, jCreateRow, scene2.realmGet$sceneNumber(), false);
        String strRealmGet$sceneName = scene2.realmGet$sceneName();
        if (strRealmGet$sceneName != null) {
            Table.nativeSetString(nativePtr, sceneColumnInfo.sceneNameColKey, jCreateRow, strRealmGet$sceneName, false);
        } else {
            Table.nativeSetNull(nativePtr, sceneColumnInfo.sceneNameColKey, jCreateRow, false);
        }
        String strRealmGet$sceneFileName = scene2.realmGet$sceneFileName();
        if (strRealmGet$sceneFileName != null) {
            Table.nativeSetString(nativePtr, sceneColumnInfo.sceneFileNameColKey, jCreateRow, strRealmGet$sceneFileName, false);
        } else {
            Table.nativeSetNull(nativePtr, sceneColumnInfo.sceneFileNameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, sceneColumnInfo.iconIndexColKey, jCreateRow, scene2.realmGet$iconIndex(), false);
        OsList osList = new OsList(table.getUncheckedRow(jCreateRow), sceneColumnInfo.sceneDeviceRealmListColKey);
        RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList = scene2.realmGet$sceneDeviceRealmList();
        if (realmListRealmGet$sceneDeviceRealmList != null && realmListRealmGet$sceneDeviceRealmList.size() == osList.size()) {
            int size = realmListRealmGet$sceneDeviceRealmList.size();
            for (int i = 0; i < size; i++) {
                SceneDevice sceneDevice = realmListRealmGet$sceneDeviceRealmList.get(i);
                Long lValueOf = map.get(sceneDevice);
                if (lValueOf == null) {
                    lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, sceneDevice, map));
                }
                osList.setRow(i, lValueOf.longValue());
            }
        } else {
            osList.removeAll();
            if (realmListRealmGet$sceneDeviceRealmList != null) {
                for (SceneDevice sceneDevice2 : realmListRealmGet$sceneDeviceRealmList) {
                    Long lValueOf2 = map.get(sceneDevice2);
                    if (lValueOf2 == null) {
                        lValueOf2 = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, sceneDevice2, map));
                    }
                    osList.addRow(lValueOf2.longValue());
                }
            }
        }
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Scene.class);
        long nativePtr = table.getNativePtr();
        SceneColumnInfo sceneColumnInfo = (SceneColumnInfo) realm.getSchema().getColumnInfo(Scene.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Scene) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, sceneColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$index(), false);
                Table.nativeSetLong(nativePtr, sceneColumnInfo.sceneNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneNumber(), false);
                String strRealmGet$sceneName = com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneName();
                if (strRealmGet$sceneName != null) {
                    Table.nativeSetString(nativePtr, sceneColumnInfo.sceneNameColKey, jCreateRow, strRealmGet$sceneName, false);
                } else {
                    Table.nativeSetNull(nativePtr, sceneColumnInfo.sceneNameColKey, jCreateRow, false);
                }
                String strRealmGet$sceneFileName = com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneFileName();
                if (strRealmGet$sceneFileName != null) {
                    Table.nativeSetString(nativePtr, sceneColumnInfo.sceneFileNameColKey, jCreateRow, strRealmGet$sceneFileName, false);
                } else {
                    Table.nativeSetNull(nativePtr, sceneColumnInfo.sceneFileNameColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, sceneColumnInfo.iconIndexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$iconIndex(), false);
                OsList osList = new OsList(table.getUncheckedRow(jCreateRow), sceneColumnInfo.sceneDeviceRealmListColKey);
                RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList = com_brgd_brblmesh_generaladapter_model_scenerealmproxyinterface.realmGet$sceneDeviceRealmList();
                if (realmListRealmGet$sceneDeviceRealmList != null && realmListRealmGet$sceneDeviceRealmList.size() == osList.size()) {
                    int size = realmListRealmGet$sceneDeviceRealmList.size();
                    for (int i = 0; i < size; i++) {
                        SceneDevice sceneDevice = realmListRealmGet$sceneDeviceRealmList.get(i);
                        Long lValueOf = map.get(sceneDevice);
                        if (lValueOf == null) {
                            lValueOf = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, sceneDevice, map));
                        }
                        osList.setRow(i, lValueOf.longValue());
                    }
                } else {
                    osList.removeAll();
                    if (realmListRealmGet$sceneDeviceRealmList != null) {
                        for (SceneDevice sceneDevice2 : realmListRealmGet$sceneDeviceRealmList) {
                            Long lValueOf2 = map.get(sceneDevice2);
                            if (lValueOf2 == null) {
                                lValueOf2 = Long.valueOf(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, sceneDevice2, map));
                            }
                            osList.addRow(lValueOf2.longValue());
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Scene createDetachedCopy(Scene scene, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Scene scene2;
        if (i > i2 || scene == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(scene);
        if (cacheData == null) {
            scene2 = new Scene();
            map.put(scene, new RealmObjectProxy.CacheData<>(i, scene2));
        } else {
            if (i >= cacheData.minDepth) {
                return (Scene) cacheData.object;
            }
            Scene scene3 = (Scene) cacheData.object;
            cacheData.minDepth = i;
            scene2 = scene3;
        }
        Scene scene4 = scene2;
        Scene scene5 = scene;
        scene4.realmSet$index(scene5.realmGet$index());
        scene4.realmSet$sceneNumber(scene5.realmGet$sceneNumber());
        scene4.realmSet$sceneName(scene5.realmGet$sceneName());
        scene4.realmSet$sceneFileName(scene5.realmGet$sceneFileName());
        scene4.realmSet$iconIndex(scene5.realmGet$iconIndex());
        if (i == i2) {
            scene4.realmSet$sceneDeviceRealmList(null);
            return scene2;
        }
        RealmList<SceneDevice> realmListRealmGet$sceneDeviceRealmList = scene5.realmGet$sceneDeviceRealmList();
        RealmList<SceneDevice> realmList = new RealmList<>();
        scene4.realmSet$sceneDeviceRealmList(realmList);
        int i3 = i + 1;
        int size = realmListRealmGet$sceneDeviceRealmList.size();
        for (int i4 = 0; i4 < size; i4++) {
            realmList.add(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createDetachedCopy(realmListRealmGet$sceneDeviceRealmList.get(i4), i3, i2, map));
        }
        return scene2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("Scene = proxy[{index:");
        sb.append(realmGet$index());
        sb.append("},{sceneNumber:");
        sb.append(realmGet$sceneNumber());
        sb.append("},{sceneName:");
        String strRealmGet$sceneName = realmGet$sceneName();
        String strRealmGet$sceneFileName = GlobalVariable.nullColor;
        sb.append(strRealmGet$sceneName != null ? realmGet$sceneName() : GlobalVariable.nullColor);
        sb.append("},{sceneFileName:");
        if (realmGet$sceneFileName() != null) {
            strRealmGet$sceneFileName = realmGet$sceneFileName();
        }
        sb.append(strRealmGet$sceneFileName);
        sb.append("},{iconIndex:");
        sb.append(realmGet$iconIndex());
        sb.append("},{sceneDeviceRealmList:RealmList<SceneDevice>[");
        sb.append(realmGet$sceneDeviceRealmList().size());
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
        com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy com_brgd_brblmesh_generaladapter_model_scenerealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_scenerealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_scenerealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_scenerealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

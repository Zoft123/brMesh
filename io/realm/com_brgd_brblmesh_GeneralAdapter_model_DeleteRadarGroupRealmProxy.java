package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
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
public class com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy extends DeleteRadarGroup implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private DeleteRadarGroupColumnInfo columnInfo;
    private ProxyState<DeleteRadarGroup> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "DeleteRadarGroup";
    }

    static final class DeleteRadarGroupColumnInfo extends ColumnInfo {
        long fixedIdColKey;

        DeleteRadarGroupColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(1);
            this.fixedIdColKey = addColumnDetails("fixedId", "fixedId", osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME));
        }

        DeleteRadarGroupColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new DeleteRadarGroupColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ((DeleteRadarGroupColumnInfo) columnInfo2).fixedIdColKey = ((DeleteRadarGroupColumnInfo) columnInfo).fixedIdColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (DeleteRadarGroupColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<DeleteRadarGroup> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxyInterface
    public int realmGet$fixedId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.fixedIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxyInterface
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 1, 0);
        builder.addPersistedProperty("", "fixedId", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static DeleteRadarGroupColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new DeleteRadarGroupColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static DeleteRadarGroup createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        DeleteRadarGroup deleteRadarGroup = (DeleteRadarGroup) realm.createObjectInternal(DeleteRadarGroup.class, true, Collections.EMPTY_LIST);
        DeleteRadarGroup deleteRadarGroup2 = deleteRadarGroup;
        if (!jSONObject.has("fixedId")) {
            return deleteRadarGroup;
        }
        if (jSONObject.isNull("fixedId")) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
        }
        deleteRadarGroup2.realmSet$fixedId(jSONObject.getInt("fixedId"));
        return deleteRadarGroup;
    }

    public static DeleteRadarGroup createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        DeleteRadarGroup deleteRadarGroup = new DeleteRadarGroup();
        DeleteRadarGroup deleteRadarGroup2 = deleteRadarGroup;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals("fixedId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    deleteRadarGroup2.realmSet$fixedId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (DeleteRadarGroup) realm.copyToRealm(deleteRadarGroup, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(DeleteRadarGroup.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeleteRadarGroup copyOrUpdate(Realm realm, DeleteRadarGroupColumnInfo deleteRadarGroupColumnInfo, DeleteRadarGroup deleteRadarGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((deleteRadarGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteRadarGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteRadarGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return deleteRadarGroup;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(deleteRadarGroup);
        if (realmModel != null) {
            return (DeleteRadarGroup) realmModel;
        }
        return copy(realm, deleteRadarGroupColumnInfo, deleteRadarGroup, z, map, set);
    }

    public static DeleteRadarGroup copy(Realm realm, DeleteRadarGroupColumnInfo deleteRadarGroupColumnInfo, DeleteRadarGroup deleteRadarGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(deleteRadarGroup);
        if (realmObjectProxy != null) {
            return (DeleteRadarGroup) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(DeleteRadarGroup.class), set);
        osObjectBuilder.addInteger(deleteRadarGroupColumnInfo.fixedIdColKey, Integer.valueOf(deleteRadarGroup.realmGet$fixedId()));
        com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(deleteRadarGroup, com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, DeleteRadarGroup deleteRadarGroup, Map<RealmModel, Long> map) {
        if ((deleteRadarGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteRadarGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteRadarGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DeleteRadarGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteRadarGroupColumnInfo deleteRadarGroupColumnInfo = (DeleteRadarGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteRadarGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(deleteRadarGroup, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, deleteRadarGroupColumnInfo.fixedIdColKey, jCreateRow, deleteRadarGroup.realmGet$fixedId(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DeleteRadarGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteRadarGroupColumnInfo deleteRadarGroupColumnInfo = (DeleteRadarGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteRadarGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DeleteRadarGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, deleteRadarGroupColumnInfo.fixedIdColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxyInterface) realmModel).realmGet$fixedId(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, DeleteRadarGroup deleteRadarGroup, Map<RealmModel, Long> map) {
        if ((deleteRadarGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteRadarGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteRadarGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DeleteRadarGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteRadarGroupColumnInfo deleteRadarGroupColumnInfo = (DeleteRadarGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteRadarGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(deleteRadarGroup, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, deleteRadarGroupColumnInfo.fixedIdColKey, jCreateRow, deleteRadarGroup.realmGet$fixedId(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DeleteRadarGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteRadarGroupColumnInfo deleteRadarGroupColumnInfo = (DeleteRadarGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteRadarGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DeleteRadarGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, deleteRadarGroupColumnInfo.fixedIdColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxyInterface) realmModel).realmGet$fixedId(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeleteRadarGroup createDetachedCopy(DeleteRadarGroup deleteRadarGroup, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        DeleteRadarGroup deleteRadarGroup2;
        if (i > i2 || deleteRadarGroup == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(deleteRadarGroup);
        if (cacheData == null) {
            deleteRadarGroup2 = new DeleteRadarGroup();
            map.put(deleteRadarGroup, new RealmObjectProxy.CacheData<>(i, deleteRadarGroup2));
        } else {
            if (i >= cacheData.minDepth) {
                return (DeleteRadarGroup) cacheData.object;
            }
            DeleteRadarGroup deleteRadarGroup3 = (DeleteRadarGroup) cacheData.object;
            cacheData.minDepth = i;
            deleteRadarGroup2 = deleteRadarGroup3;
        }
        DeleteRadarGroup deleteRadarGroup4 = deleteRadarGroup;
        deleteRadarGroup2.realmSet$fixedId(deleteRadarGroup4.realmGet$fixedId());
        return deleteRadarGroup2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "DeleteRadarGroup = proxy[{fixedId:" + realmGet$fixedId() + "}]";
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
        com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_deleteradargrouprealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

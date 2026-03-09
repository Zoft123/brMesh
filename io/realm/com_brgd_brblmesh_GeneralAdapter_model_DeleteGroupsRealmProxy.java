package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
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
public class com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy extends DeleteGroups implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private DeleteGroupsColumnInfo columnInfo;
    private ProxyState<DeleteGroups> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "DeleteGroups";
    }

    static final class DeleteGroupsColumnInfo extends ColumnInfo {
        long groupIdColKey;

        DeleteGroupsColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(1);
            this.groupIdColKey = addColumnDetails(GlobalVariable.GROUPID, GlobalVariable.GROUPID, osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME));
        }

        DeleteGroupsColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new DeleteGroupsColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ((DeleteGroupsColumnInfo) columnInfo2).groupIdColKey = ((DeleteGroupsColumnInfo) columnInfo).groupIdColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (DeleteGroupsColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<DeleteGroups> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface
    public int realmGet$groupId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.groupIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface
    public void realmSet$groupId(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.groupIdColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.groupIdColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 1, 0);
        builder.addPersistedProperty("", GlobalVariable.GROUPID, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static DeleteGroupsColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new DeleteGroupsColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static DeleteGroups createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        DeleteGroups deleteGroups = (DeleteGroups) realm.createObjectInternal(DeleteGroups.class, true, Collections.EMPTY_LIST);
        DeleteGroups deleteGroups2 = deleteGroups;
        if (!jSONObject.has(GlobalVariable.GROUPID)) {
            return deleteGroups;
        }
        if (jSONObject.isNull(GlobalVariable.GROUPID)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
        }
        deleteGroups2.realmSet$groupId(jSONObject.getInt(GlobalVariable.GROUPID));
        return deleteGroups;
    }

    public static DeleteGroups createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        DeleteGroups deleteGroups = new DeleteGroups();
        DeleteGroups deleteGroups2 = deleteGroups;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals(GlobalVariable.GROUPID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    deleteGroups2.realmSet$groupId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (DeleteGroups) realm.copyToRealm(deleteGroups, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(DeleteGroups.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeleteGroups copyOrUpdate(Realm realm, DeleteGroupsColumnInfo deleteGroupsColumnInfo, DeleteGroups deleteGroups, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((deleteGroups instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteGroups)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteGroups;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return deleteGroups;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(deleteGroups);
        if (realmModel != null) {
            return (DeleteGroups) realmModel;
        }
        return copy(realm, deleteGroupsColumnInfo, deleteGroups, z, map, set);
    }

    public static DeleteGroups copy(Realm realm, DeleteGroupsColumnInfo deleteGroupsColumnInfo, DeleteGroups deleteGroups, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(deleteGroups);
        if (realmObjectProxy != null) {
            return (DeleteGroups) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(DeleteGroups.class), set);
        osObjectBuilder.addInteger(deleteGroupsColumnInfo.groupIdColKey, Integer.valueOf(deleteGroups.realmGet$groupId()));
        com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(deleteGroups, com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, DeleteGroups deleteGroups, Map<RealmModel, Long> map) {
        if ((deleteGroups instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteGroups)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteGroups;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DeleteGroups.class);
        long nativePtr = table.getNativePtr();
        DeleteGroupsColumnInfo deleteGroupsColumnInfo = (DeleteGroupsColumnInfo) realm.getSchema().getColumnInfo(DeleteGroups.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(deleteGroups, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, deleteGroupsColumnInfo.groupIdColKey, jCreateRow, deleteGroups.realmGet$groupId(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DeleteGroups.class);
        long nativePtr = table.getNativePtr();
        DeleteGroupsColumnInfo deleteGroupsColumnInfo = (DeleteGroupsColumnInfo) realm.getSchema().getColumnInfo(DeleteGroups.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DeleteGroups) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, deleteGroupsColumnInfo.groupIdColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface) realmModel).realmGet$groupId(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, DeleteGroups deleteGroups, Map<RealmModel, Long> map) {
        if ((deleteGroups instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteGroups)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteGroups;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DeleteGroups.class);
        long nativePtr = table.getNativePtr();
        DeleteGroupsColumnInfo deleteGroupsColumnInfo = (DeleteGroupsColumnInfo) realm.getSchema().getColumnInfo(DeleteGroups.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(deleteGroups, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, deleteGroupsColumnInfo.groupIdColKey, jCreateRow, deleteGroups.realmGet$groupId(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DeleteGroups.class);
        long nativePtr = table.getNativePtr();
        DeleteGroupsColumnInfo deleteGroupsColumnInfo = (DeleteGroupsColumnInfo) realm.getSchema().getColumnInfo(DeleteGroups.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DeleteGroups) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, deleteGroupsColumnInfo.groupIdColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface) realmModel).realmGet$groupId(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeleteGroups createDetachedCopy(DeleteGroups deleteGroups, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        DeleteGroups deleteGroups2;
        if (i > i2 || deleteGroups == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(deleteGroups);
        if (cacheData == null) {
            deleteGroups2 = new DeleteGroups();
            map.put(deleteGroups, new RealmObjectProxy.CacheData<>(i, deleteGroups2));
        } else {
            if (i >= cacheData.minDepth) {
                return (DeleteGroups) cacheData.object;
            }
            DeleteGroups deleteGroups3 = (DeleteGroups) cacheData.object;
            cacheData.minDepth = i;
            deleteGroups2 = deleteGroups3;
        }
        DeleteGroups deleteGroups4 = deleteGroups;
        deleteGroups2.realmSet$groupId(deleteGroups4.realmGet$groupId());
        return deleteGroups2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "DeleteGroups = proxy[{groupId:" + realmGet$groupId() + "}]";
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
        com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_deletegroupsrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

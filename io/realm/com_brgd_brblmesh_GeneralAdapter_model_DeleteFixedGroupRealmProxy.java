package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
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
public class com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy extends DeleteFixedGroup implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private DeleteFixedGroupColumnInfo columnInfo;
    private ProxyState<DeleteFixedGroup> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "DeleteFixedGroup";
    }

    static final class DeleteFixedGroupColumnInfo extends ColumnInfo {
        long fixedIdColKey;

        DeleteFixedGroupColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(1);
            this.fixedIdColKey = addColumnDetails("fixedId", "fixedId", osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME));
        }

        DeleteFixedGroupColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new DeleteFixedGroupColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ((DeleteFixedGroupColumnInfo) columnInfo2).fixedIdColKey = ((DeleteFixedGroupColumnInfo) columnInfo).fixedIdColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (DeleteFixedGroupColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<DeleteFixedGroup> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface
    public int realmGet$fixedId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.fixedIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface
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

    public static DeleteFixedGroupColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new DeleteFixedGroupColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static DeleteFixedGroup createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        DeleteFixedGroup deleteFixedGroup = (DeleteFixedGroup) realm.createObjectInternal(DeleteFixedGroup.class, true, Collections.EMPTY_LIST);
        DeleteFixedGroup deleteFixedGroup2 = deleteFixedGroup;
        if (!jSONObject.has("fixedId")) {
            return deleteFixedGroup;
        }
        if (jSONObject.isNull("fixedId")) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
        }
        deleteFixedGroup2.realmSet$fixedId(jSONObject.getInt("fixedId"));
        return deleteFixedGroup;
    }

    public static DeleteFixedGroup createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        DeleteFixedGroup deleteFixedGroup = new DeleteFixedGroup();
        DeleteFixedGroup deleteFixedGroup2 = deleteFixedGroup;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals("fixedId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    deleteFixedGroup2.realmSet$fixedId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (DeleteFixedGroup) realm.copyToRealm(deleteFixedGroup, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(DeleteFixedGroup.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeleteFixedGroup copyOrUpdate(Realm realm, DeleteFixedGroupColumnInfo deleteFixedGroupColumnInfo, DeleteFixedGroup deleteFixedGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((deleteFixedGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteFixedGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteFixedGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return deleteFixedGroup;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(deleteFixedGroup);
        if (realmModel != null) {
            return (DeleteFixedGroup) realmModel;
        }
        return copy(realm, deleteFixedGroupColumnInfo, deleteFixedGroup, z, map, set);
    }

    public static DeleteFixedGroup copy(Realm realm, DeleteFixedGroupColumnInfo deleteFixedGroupColumnInfo, DeleteFixedGroup deleteFixedGroup, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(deleteFixedGroup);
        if (realmObjectProxy != null) {
            return (DeleteFixedGroup) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(DeleteFixedGroup.class), set);
        osObjectBuilder.addInteger(deleteFixedGroupColumnInfo.fixedIdColKey, Integer.valueOf(deleteFixedGroup.realmGet$fixedId()));
        com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(deleteFixedGroup, com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, DeleteFixedGroup deleteFixedGroup, Map<RealmModel, Long> map) {
        if ((deleteFixedGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteFixedGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteFixedGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DeleteFixedGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteFixedGroupColumnInfo deleteFixedGroupColumnInfo = (DeleteFixedGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteFixedGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(deleteFixedGroup, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, deleteFixedGroupColumnInfo.fixedIdColKey, jCreateRow, deleteFixedGroup.realmGet$fixedId(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DeleteFixedGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteFixedGroupColumnInfo deleteFixedGroupColumnInfo = (DeleteFixedGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteFixedGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DeleteFixedGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, deleteFixedGroupColumnInfo.fixedIdColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface) realmModel).realmGet$fixedId(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, DeleteFixedGroup deleteFixedGroup, Map<RealmModel, Long> map) {
        if ((deleteFixedGroup instanceof RealmObjectProxy) && !RealmObject.isFrozen(deleteFixedGroup)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) deleteFixedGroup;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DeleteFixedGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteFixedGroupColumnInfo deleteFixedGroupColumnInfo = (DeleteFixedGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteFixedGroup.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(deleteFixedGroup, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, deleteFixedGroupColumnInfo.fixedIdColKey, jCreateRow, deleteFixedGroup.realmGet$fixedId(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DeleteFixedGroup.class);
        long nativePtr = table.getNativePtr();
        DeleteFixedGroupColumnInfo deleteFixedGroupColumnInfo = (DeleteFixedGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteFixedGroup.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DeleteFixedGroup) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, deleteFixedGroupColumnInfo.fixedIdColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface) realmModel).realmGet$fixedId(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeleteFixedGroup createDetachedCopy(DeleteFixedGroup deleteFixedGroup, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        DeleteFixedGroup deleteFixedGroup2;
        if (i > i2 || deleteFixedGroup == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(deleteFixedGroup);
        if (cacheData == null) {
            deleteFixedGroup2 = new DeleteFixedGroup();
            map.put(deleteFixedGroup, new RealmObjectProxy.CacheData<>(i, deleteFixedGroup2));
        } else {
            if (i >= cacheData.minDepth) {
                return (DeleteFixedGroup) cacheData.object;
            }
            DeleteFixedGroup deleteFixedGroup3 = (DeleteFixedGroup) cacheData.object;
            cacheData.minDepth = i;
            deleteFixedGroup2 = deleteFixedGroup3;
        }
        DeleteFixedGroup deleteFixedGroup4 = deleteFixedGroup;
        deleteFixedGroup2.realmSet$fixedId(deleteFixedGroup4.realmGet$fixedId());
        return deleteFixedGroup2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "DeleteFixedGroup = proxy[{fixedId:" + realmGet$fixedId() + "}]";
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
        com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_deletefixedgrouprealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

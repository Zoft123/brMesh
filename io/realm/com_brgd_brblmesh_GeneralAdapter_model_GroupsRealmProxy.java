package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
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
public class com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy extends Groups implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private GroupsColumnInfo columnInfo;
    private ProxyState<Groups> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Groups";
    }

    static final class GroupsColumnInfo extends ColumnInfo {
        long groupIdColKey;
        long groupNameColKey;
        long indexColKey;

        GroupsColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.groupIdColKey = addColumnDetails(GlobalVariable.GROUPID, GlobalVariable.GROUPID, objectSchemaInfo);
            this.groupNameColKey = addColumnDetails(GlobalVariable.G_NAME, GlobalVariable.G_NAME, objectSchemaInfo);
            this.indexColKey = addColumnDetails(GlobalVariable.INDEX, GlobalVariable.INDEX, objectSchemaInfo);
        }

        GroupsColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new GroupsColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            GroupsColumnInfo groupsColumnInfo = (GroupsColumnInfo) columnInfo;
            GroupsColumnInfo groupsColumnInfo2 = (GroupsColumnInfo) columnInfo2;
            groupsColumnInfo2.groupIdColKey = groupsColumnInfo.groupIdColKey;
            groupsColumnInfo2.groupNameColKey = groupsColumnInfo.groupNameColKey;
            groupsColumnInfo2.indexColKey = groupsColumnInfo.indexColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (GroupsColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<Groups> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Groups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public int realmGet$groupId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.groupIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Groups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Groups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public String realmGet$groupName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.groupNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Groups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public void realmSet$groupName(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.groupNameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.groupNameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.groupNameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.groupNameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Groups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public int realmGet$index() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Groups, io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 3, 0);
        builder.addPersistedProperty("", GlobalVariable.GROUPID, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.G_NAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.INDEX, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static GroupsColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new GroupsColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static Groups createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        Groups groups = (Groups) realm.createObjectInternal(Groups.class, true, Collections.EMPTY_LIST);
        Groups groups2 = groups;
        if (jSONObject.has(GlobalVariable.GROUPID)) {
            if (jSONObject.isNull(GlobalVariable.GROUPID)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
            }
            groups2.realmSet$groupId(jSONObject.getInt(GlobalVariable.GROUPID));
        }
        if (jSONObject.has(GlobalVariable.G_NAME)) {
            if (jSONObject.isNull(GlobalVariable.G_NAME)) {
                groups2.realmSet$groupName(null);
            } else {
                groups2.realmSet$groupName(jSONObject.getString(GlobalVariable.G_NAME));
            }
        }
        if (!jSONObject.has(GlobalVariable.INDEX)) {
            return groups;
        }
        if (jSONObject.isNull(GlobalVariable.INDEX)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
        }
        groups2.realmSet$index(jSONObject.getInt(GlobalVariable.INDEX));
        return groups;
    }

    public static Groups createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        Groups groups = new Groups();
        Groups groups2 = groups;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.GROUPID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    groups2.realmSet$groupId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.G_NAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    groups2.realmSet$groupName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    groups2.realmSet$groupName(null);
                }
            } else if (strNextName.equals(GlobalVariable.INDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    groups2.realmSet$index(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (Groups) realm.copyToRealm(groups, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(Groups.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy com_brgd_brblmesh_generaladapter_model_groupsrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_groupsrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Groups copyOrUpdate(Realm realm, GroupsColumnInfo groupsColumnInfo, Groups groups, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((groups instanceof RealmObjectProxy) && !RealmObject.isFrozen(groups)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) groups;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return groups;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(groups);
        if (realmModel != null) {
            return (Groups) realmModel;
        }
        return copy(realm, groupsColumnInfo, groups, z, map, set);
    }

    public static Groups copy(Realm realm, GroupsColumnInfo groupsColumnInfo, Groups groups, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(groups);
        if (realmObjectProxy != null) {
            return (Groups) realmObjectProxy;
        }
        Groups groups2 = groups;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(Groups.class), set);
        osObjectBuilder.addInteger(groupsColumnInfo.groupIdColKey, Integer.valueOf(groups2.realmGet$groupId()));
        osObjectBuilder.addString(groupsColumnInfo.groupNameColKey, groups2.realmGet$groupName());
        osObjectBuilder.addInteger(groupsColumnInfo.indexColKey, Integer.valueOf(groups2.realmGet$index()));
        com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy com_brgd_brblmesh_generaladapter_model_groupsrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(groups, com_brgd_brblmesh_generaladapter_model_groupsrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_groupsrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, Groups groups, Map<RealmModel, Long> map) {
        if ((groups instanceof RealmObjectProxy) && !RealmObject.isFrozen(groups)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) groups;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Groups.class);
        long nativePtr = table.getNativePtr();
        GroupsColumnInfo groupsColumnInfo = (GroupsColumnInfo) realm.getSchema().getColumnInfo(Groups.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(groups, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, groupsColumnInfo.groupIdColKey, jCreateRow, r11.realmGet$groupId(), false);
        String strRealmGet$groupName = groups.realmGet$groupName();
        if (strRealmGet$groupName != null) {
            Table.nativeSetString(nativePtr, groupsColumnInfo.groupNameColKey, jCreateRow, strRealmGet$groupName, false);
        }
        Table.nativeSetLong(nativePtr, groupsColumnInfo.indexColKey, jCreateRow, r11.realmGet$index(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Groups.class);
        long nativePtr = table.getNativePtr();
        GroupsColumnInfo groupsColumnInfo = (GroupsColumnInfo) realm.getSchema().getColumnInfo(Groups.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Groups) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, groupsColumnInfo.groupIdColKey, jCreateRow, r11.realmGet$groupId(), false);
                String strRealmGet$groupName = ((com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface) realmModel).realmGet$groupName();
                if (strRealmGet$groupName != null) {
                    Table.nativeSetString(nativePtr, groupsColumnInfo.groupNameColKey, jCreateRow, strRealmGet$groupName, false);
                }
                Table.nativeSetLong(nativePtr, groupsColumnInfo.indexColKey, jCreateRow, r11.realmGet$index(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, Groups groups, Map<RealmModel, Long> map) {
        if ((groups instanceof RealmObjectProxy) && !RealmObject.isFrozen(groups)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) groups;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Groups.class);
        long nativePtr = table.getNativePtr();
        GroupsColumnInfo groupsColumnInfo = (GroupsColumnInfo) realm.getSchema().getColumnInfo(Groups.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(groups, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, groupsColumnInfo.groupIdColKey, jCreateRow, r11.realmGet$groupId(), false);
        String strRealmGet$groupName = groups.realmGet$groupName();
        if (strRealmGet$groupName != null) {
            Table.nativeSetString(nativePtr, groupsColumnInfo.groupNameColKey, jCreateRow, strRealmGet$groupName, false);
        } else {
            Table.nativeSetNull(nativePtr, groupsColumnInfo.groupNameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, groupsColumnInfo.indexColKey, jCreateRow, r11.realmGet$index(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Groups.class);
        long nativePtr = table.getNativePtr();
        GroupsColumnInfo groupsColumnInfo = (GroupsColumnInfo) realm.getSchema().getColumnInfo(Groups.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Groups) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, groupsColumnInfo.groupIdColKey, jCreateRow, r11.realmGet$groupId(), false);
                String strRealmGet$groupName = ((com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface) realmModel).realmGet$groupName();
                if (strRealmGet$groupName != null) {
                    Table.nativeSetString(nativePtr, groupsColumnInfo.groupNameColKey, jCreateRow, strRealmGet$groupName, false);
                } else {
                    Table.nativeSetNull(nativePtr, groupsColumnInfo.groupNameColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, groupsColumnInfo.indexColKey, jCreateRow, r11.realmGet$index(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Groups createDetachedCopy(Groups groups, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Groups groups2;
        if (i > i2 || groups == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(groups);
        if (cacheData == null) {
            groups2 = new Groups();
            map.put(groups, new RealmObjectProxy.CacheData<>(i, groups2));
        } else {
            if (i >= cacheData.minDepth) {
                return (Groups) cacheData.object;
            }
            Groups groups3 = (Groups) cacheData.object;
            cacheData.minDepth = i;
            groups2 = groups3;
        }
        Groups groups4 = groups2;
        Groups groups5 = groups;
        groups4.realmSet$groupId(groups5.realmGet$groupId());
        groups4.realmSet$groupName(groups5.realmGet$groupName());
        groups4.realmSet$index(groups5.realmGet$index());
        return groups2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("Groups = proxy[{groupId:");
        sb.append(realmGet$groupId());
        sb.append("},{groupName:");
        sb.append(realmGet$groupName() != null ? realmGet$groupName() : GlobalVariable.nullColor);
        sb.append("},{index:");
        sb.append(realmGet$index());
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
        com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy com_brgd_brblmesh_generaladapter_model_groupsrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_groupsrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_groupsrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_groupsrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

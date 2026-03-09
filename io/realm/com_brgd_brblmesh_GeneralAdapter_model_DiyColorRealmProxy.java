package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
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
public class com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy extends DiyColor implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private DiyColorColumnInfo columnInfo;
    private ProxyState<DiyColor> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "DiyColor";
    }

    static final class DiyColorColumnInfo extends ColumnInfo {
        long colorIdentifierColKey;
        long colorValueColKey;
        long diyColorRColKey;

        DiyColorColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo("DiyColor");
            this.colorValueColKey = addColumnDetails(GlobalVariable.colorValue, GlobalVariable.colorValue, objectSchemaInfo);
            this.colorIdentifierColKey = addColumnDetails("colorIdentifier", "colorIdentifier", objectSchemaInfo);
            this.diyColorRColKey = addColumnDetails("diyColorR", "diyColorR", objectSchemaInfo);
        }

        DiyColorColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new DiyColorColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            DiyColorColumnInfo diyColorColumnInfo = (DiyColorColumnInfo) columnInfo;
            DiyColorColumnInfo diyColorColumnInfo2 = (DiyColorColumnInfo) columnInfo2;
            diyColorColumnInfo2.colorValueColKey = diyColorColumnInfo.colorValueColKey;
            diyColorColumnInfo2.colorIdentifierColKey = diyColorColumnInfo.colorIdentifierColKey;
            diyColorColumnInfo2.diyColorRColKey = diyColorColumnInfo.diyColorRColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (DiyColorColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<DiyColor> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public int realmGet$colorValue() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.colorValueColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public void realmSet$colorValue(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.colorValueColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.colorValueColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public String realmGet$colorIdentifier() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.colorIdentifierColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public void realmSet$colorIdentifier(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.colorIdentifierColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.colorIdentifierColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.colorIdentifierColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.colorIdentifierColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public int realmGet$diyColorR() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.diyColorRColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.DiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public void realmSet$diyColorR(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.diyColorRColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.diyColorRColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", "DiyColor", false, 3, 0);
        builder.addPersistedProperty("", GlobalVariable.colorValue, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "colorIdentifier", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", "diyColorR", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static DiyColorColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new DiyColorColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return "DiyColor";
    }

    public static DiyColor createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        DiyColor diyColor = (DiyColor) realm.createObjectInternal(DiyColor.class, true, Collections.EMPTY_LIST);
        DiyColor diyColor2 = diyColor;
        if (jSONObject.has(GlobalVariable.colorValue)) {
            if (jSONObject.isNull(GlobalVariable.colorValue)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'colorValue' to null.");
            }
            diyColor2.realmSet$colorValue(jSONObject.getInt(GlobalVariable.colorValue));
        }
        if (jSONObject.has("colorIdentifier")) {
            if (jSONObject.isNull("colorIdentifier")) {
                diyColor2.realmSet$colorIdentifier(null);
            } else {
                diyColor2.realmSet$colorIdentifier(jSONObject.getString("colorIdentifier"));
            }
        }
        if (!jSONObject.has("diyColorR")) {
            return diyColor;
        }
        if (jSONObject.isNull("diyColorR")) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'diyColorR' to null.");
        }
        diyColor2.realmSet$diyColorR(jSONObject.getInt("diyColorR"));
        return diyColor;
    }

    public static DiyColor createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        DiyColor diyColor = new DiyColor();
        DiyColor diyColor2 = diyColor;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.colorValue)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    diyColor2.realmSet$colorValue(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'colorValue' to null.");
                }
            } else if (strNextName.equals("colorIdentifier")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    diyColor2.realmSet$colorIdentifier(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    diyColor2.realmSet$colorIdentifier(null);
                }
            } else if (strNextName.equals("diyColorR")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    diyColor2.realmSet$diyColorR(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'diyColorR' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (DiyColor) realm.copyToRealm(diyColor, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(DiyColor.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy com_brgd_brblmesh_generaladapter_model_diycolorrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_diycolorrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DiyColor copyOrUpdate(Realm realm, DiyColorColumnInfo diyColorColumnInfo, DiyColor diyColor, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((diyColor instanceof RealmObjectProxy) && !RealmObject.isFrozen(diyColor)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) diyColor;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return diyColor;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(diyColor);
        if (realmModel != null) {
            return (DiyColor) realmModel;
        }
        return copy(realm, diyColorColumnInfo, diyColor, z, map, set);
    }

    public static DiyColor copy(Realm realm, DiyColorColumnInfo diyColorColumnInfo, DiyColor diyColor, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(diyColor);
        if (realmObjectProxy != null) {
            return (DiyColor) realmObjectProxy;
        }
        DiyColor diyColor2 = diyColor;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(DiyColor.class), set);
        osObjectBuilder.addInteger(diyColorColumnInfo.colorValueColKey, Integer.valueOf(diyColor2.realmGet$colorValue()));
        osObjectBuilder.addString(diyColorColumnInfo.colorIdentifierColKey, diyColor2.realmGet$colorIdentifier());
        osObjectBuilder.addInteger(diyColorColumnInfo.diyColorRColKey, Integer.valueOf(diyColor2.realmGet$diyColorR()));
        com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy com_brgd_brblmesh_generaladapter_model_diycolorrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(diyColor, com_brgd_brblmesh_generaladapter_model_diycolorrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_diycolorrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, DiyColor diyColor, Map<RealmModel, Long> map) {
        if ((diyColor instanceof RealmObjectProxy) && !RealmObject.isFrozen(diyColor)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) diyColor;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DiyColor.class);
        long nativePtr = table.getNativePtr();
        DiyColorColumnInfo diyColorColumnInfo = (DiyColorColumnInfo) realm.getSchema().getColumnInfo(DiyColor.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(diyColor, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, diyColorColumnInfo.colorValueColKey, jCreateRow, r11.realmGet$colorValue(), false);
        String strRealmGet$colorIdentifier = diyColor.realmGet$colorIdentifier();
        if (strRealmGet$colorIdentifier != null) {
            Table.nativeSetString(nativePtr, diyColorColumnInfo.colorIdentifierColKey, jCreateRow, strRealmGet$colorIdentifier, false);
        }
        Table.nativeSetLong(nativePtr, diyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DiyColor.class);
        long nativePtr = table.getNativePtr();
        DiyColorColumnInfo diyColorColumnInfo = (DiyColorColumnInfo) realm.getSchema().getColumnInfo(DiyColor.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DiyColor) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, diyColorColumnInfo.colorValueColKey, jCreateRow, r11.realmGet$colorValue(), false);
                String strRealmGet$colorIdentifier = ((com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface) realmModel).realmGet$colorIdentifier();
                if (strRealmGet$colorIdentifier != null) {
                    Table.nativeSetString(nativePtr, diyColorColumnInfo.colorIdentifierColKey, jCreateRow, strRealmGet$colorIdentifier, false);
                }
                Table.nativeSetLong(nativePtr, diyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, DiyColor diyColor, Map<RealmModel, Long> map) {
        if ((diyColor instanceof RealmObjectProxy) && !RealmObject.isFrozen(diyColor)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) diyColor;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(DiyColor.class);
        long nativePtr = table.getNativePtr();
        DiyColorColumnInfo diyColorColumnInfo = (DiyColorColumnInfo) realm.getSchema().getColumnInfo(DiyColor.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(diyColor, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, diyColorColumnInfo.colorValueColKey, jCreateRow, r11.realmGet$colorValue(), false);
        String strRealmGet$colorIdentifier = diyColor.realmGet$colorIdentifier();
        if (strRealmGet$colorIdentifier != null) {
            Table.nativeSetString(nativePtr, diyColorColumnInfo.colorIdentifierColKey, jCreateRow, strRealmGet$colorIdentifier, false);
        } else {
            Table.nativeSetNull(nativePtr, diyColorColumnInfo.colorIdentifierColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, diyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(DiyColor.class);
        long nativePtr = table.getNativePtr();
        DiyColorColumnInfo diyColorColumnInfo = (DiyColorColumnInfo) realm.getSchema().getColumnInfo(DiyColor.class);
        while (it.hasNext()) {
            RealmModel realmModel = (DiyColor) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, diyColorColumnInfo.colorValueColKey, jCreateRow, r11.realmGet$colorValue(), false);
                String strRealmGet$colorIdentifier = ((com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface) realmModel).realmGet$colorIdentifier();
                if (strRealmGet$colorIdentifier != null) {
                    Table.nativeSetString(nativePtr, diyColorColumnInfo.colorIdentifierColKey, jCreateRow, strRealmGet$colorIdentifier, false);
                } else {
                    Table.nativeSetNull(nativePtr, diyColorColumnInfo.colorIdentifierColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, diyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DiyColor createDetachedCopy(DiyColor diyColor, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        DiyColor diyColor2;
        if (i > i2 || diyColor == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(diyColor);
        if (cacheData == null) {
            diyColor2 = new DiyColor();
            map.put(diyColor, new RealmObjectProxy.CacheData<>(i, diyColor2));
        } else {
            if (i >= cacheData.minDepth) {
                return (DiyColor) cacheData.object;
            }
            DiyColor diyColor3 = (DiyColor) cacheData.object;
            cacheData.minDepth = i;
            diyColor2 = diyColor3;
        }
        DiyColor diyColor4 = diyColor2;
        DiyColor diyColor5 = diyColor;
        diyColor4.realmSet$colorValue(diyColor5.realmGet$colorValue());
        diyColor4.realmSet$colorIdentifier(diyColor5.realmGet$colorIdentifier());
        diyColor4.realmSet$diyColorR(diyColor5.realmGet$diyColorR());
        return diyColor2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("DiyColor = proxy[{colorValue:");
        sb.append(realmGet$colorValue());
        sb.append("},{colorIdentifier:");
        sb.append(realmGet$colorIdentifier() != null ? realmGet$colorIdentifier() : GlobalVariable.nullColor);
        sb.append("},{diyColorR:");
        sb.append(realmGet$diyColorR());
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
        com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy com_brgd_brblmesh_generaladapter_model_diycolorrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_diycolorrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_diycolorrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_diycolorrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

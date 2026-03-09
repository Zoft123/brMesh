package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.RadarTimer;
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
public class com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy extends RadarTimer implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private RadarTimerColumnInfo columnInfo;
    private ProxyState<RadarTimer> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RadarTimer";
    }

    static final class RadarTimerColumnInfo extends ColumnInfo {
        long addrColKey;
        long hour1ColKey;
        long hour2ColKey;
        long indexNumColKey;
        long isEnableColKey;
        long min1ColKey;
        long min2ColKey;
        long out_briColKey;

        RadarTimerColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(8);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.indexNumColKey = addColumnDetails("indexNum", "indexNum", objectSchemaInfo);
            this.hour1ColKey = addColumnDetails(GlobalVariable.HOUR1, GlobalVariable.HOUR1, objectSchemaInfo);
            this.min1ColKey = addColumnDetails(GlobalVariable.MIN1, GlobalVariable.MIN1, objectSchemaInfo);
            this.hour2ColKey = addColumnDetails(GlobalVariable.HOUR2, GlobalVariable.HOUR2, objectSchemaInfo);
            this.min2ColKey = addColumnDetails(GlobalVariable.MIN2, GlobalVariable.MIN2, objectSchemaInfo);
            this.isEnableColKey = addColumnDetails(GlobalVariable.ISENABLE, GlobalVariable.ISENABLE, objectSchemaInfo);
            this.out_briColKey = addColumnDetails(GlobalVariable.OUT_BRI, GlobalVariable.OUT_BRI, objectSchemaInfo);
            this.addrColKey = addColumnDetails(GlobalVariable.ADDR, GlobalVariable.ADDR, objectSchemaInfo);
        }

        RadarTimerColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new RadarTimerColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            RadarTimerColumnInfo radarTimerColumnInfo = (RadarTimerColumnInfo) columnInfo;
            RadarTimerColumnInfo radarTimerColumnInfo2 = (RadarTimerColumnInfo) columnInfo2;
            radarTimerColumnInfo2.indexNumColKey = radarTimerColumnInfo.indexNumColKey;
            radarTimerColumnInfo2.hour1ColKey = radarTimerColumnInfo.hour1ColKey;
            radarTimerColumnInfo2.min1ColKey = radarTimerColumnInfo.min1ColKey;
            radarTimerColumnInfo2.hour2ColKey = radarTimerColumnInfo.hour2ColKey;
            radarTimerColumnInfo2.min2ColKey = radarTimerColumnInfo.min2ColKey;
            radarTimerColumnInfo2.isEnableColKey = radarTimerColumnInfo.isEnableColKey;
            radarTimerColumnInfo2.out_briColKey = radarTimerColumnInfo.out_briColKey;
            radarTimerColumnInfo2.addrColKey = radarTimerColumnInfo.addrColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (RadarTimerColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<RadarTimer> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$indexNum() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexNumColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$indexNum(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.indexNumColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.indexNumColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$hour1() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.hour1ColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$hour1(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.hour1ColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.hour1ColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$min1() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.min1ColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$min1(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.min1ColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.min1ColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$hour2() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.hour2ColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$hour2(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.hour2ColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.hour2ColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$min2() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.min2ColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$min2(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.min2ColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.min2ColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public boolean realmGet$isEnable() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isEnableColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$isEnable(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isEnableColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isEnableColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$out_bri() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.out_briColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$out_bri(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.out_briColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.out_briColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$addr() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.addrColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarTimer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$addr(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.addrColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.addrColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 8, 0);
        builder.addPersistedProperty("", "indexNum", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.HOUR1, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MIN1, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.HOUR2, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MIN2, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ISENABLE, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.OUT_BRI, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ADDR, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RadarTimerColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new RadarTimerColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static RadarTimer createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        RadarTimer radarTimer = (RadarTimer) realm.createObjectInternal(RadarTimer.class, true, Collections.EMPTY_LIST);
        RadarTimer radarTimer2 = radarTimer;
        if (jSONObject.has("indexNum")) {
            if (jSONObject.isNull("indexNum")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'indexNum' to null.");
            }
            radarTimer2.realmSet$indexNum(jSONObject.getInt("indexNum"));
        }
        if (jSONObject.has(GlobalVariable.HOUR1)) {
            if (jSONObject.isNull(GlobalVariable.HOUR1)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'hour1' to null.");
            }
            radarTimer2.realmSet$hour1(jSONObject.getInt(GlobalVariable.HOUR1));
        }
        if (jSONObject.has(GlobalVariable.MIN1)) {
            if (jSONObject.isNull(GlobalVariable.MIN1)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'min1' to null.");
            }
            radarTimer2.realmSet$min1(jSONObject.getInt(GlobalVariable.MIN1));
        }
        if (jSONObject.has(GlobalVariable.HOUR2)) {
            if (jSONObject.isNull(GlobalVariable.HOUR2)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'hour2' to null.");
            }
            radarTimer2.realmSet$hour2(jSONObject.getInt(GlobalVariable.HOUR2));
        }
        if (jSONObject.has(GlobalVariable.MIN2)) {
            if (jSONObject.isNull(GlobalVariable.MIN2)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'min2' to null.");
            }
            radarTimer2.realmSet$min2(jSONObject.getInt(GlobalVariable.MIN2));
        }
        if (jSONObject.has(GlobalVariable.ISENABLE)) {
            if (jSONObject.isNull(GlobalVariable.ISENABLE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isEnable' to null.");
            }
            radarTimer2.realmSet$isEnable(jSONObject.getBoolean(GlobalVariable.ISENABLE));
        }
        if (jSONObject.has(GlobalVariable.OUT_BRI)) {
            if (jSONObject.isNull(GlobalVariable.OUT_BRI)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'out_bri' to null.");
            }
            radarTimer2.realmSet$out_bri(jSONObject.getInt(GlobalVariable.OUT_BRI));
        }
        if (!jSONObject.has(GlobalVariable.ADDR)) {
            return radarTimer;
        }
        if (jSONObject.isNull(GlobalVariable.ADDR)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
        }
        radarTimer2.realmSet$addr(jSONObject.getInt(GlobalVariable.ADDR));
        return radarTimer;
    }

    public static RadarTimer createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        RadarTimer radarTimer = new RadarTimer();
        RadarTimer radarTimer2 = radarTimer;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("indexNum")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$indexNum(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'indexNum' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.HOUR1)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$hour1(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'hour1' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MIN1)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$min1(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'min1' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.HOUR2)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$hour2(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'hour2' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MIN2)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$min2(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'min2' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ISENABLE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$isEnable(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isEnable' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.OUT_BRI)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$out_bri(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'out_bri' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ADDR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarTimer2.realmSet$addr(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (RadarTimer) realm.copyToRealm(radarTimer, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(RadarTimer.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy com_brgd_brblmesh_generaladapter_model_radartimerrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_radartimerrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarTimer copyOrUpdate(Realm realm, RadarTimerColumnInfo radarTimerColumnInfo, RadarTimer radarTimer, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((radarTimer instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarTimer)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarTimer;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return radarTimer;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(radarTimer);
        if (realmModel != null) {
            return (RadarTimer) realmModel;
        }
        return copy(realm, radarTimerColumnInfo, radarTimer, z, map, set);
    }

    public static RadarTimer copy(Realm realm, RadarTimerColumnInfo radarTimerColumnInfo, RadarTimer radarTimer, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(radarTimer);
        if (realmObjectProxy != null) {
            return (RadarTimer) realmObjectProxy;
        }
        RadarTimer radarTimer2 = radarTimer;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(RadarTimer.class), set);
        osObjectBuilder.addInteger(radarTimerColumnInfo.indexNumColKey, Integer.valueOf(radarTimer2.realmGet$indexNum()));
        osObjectBuilder.addInteger(radarTimerColumnInfo.hour1ColKey, Integer.valueOf(radarTimer2.realmGet$hour1()));
        osObjectBuilder.addInteger(radarTimerColumnInfo.min1ColKey, Integer.valueOf(radarTimer2.realmGet$min1()));
        osObjectBuilder.addInteger(radarTimerColumnInfo.hour2ColKey, Integer.valueOf(radarTimer2.realmGet$hour2()));
        osObjectBuilder.addInteger(radarTimerColumnInfo.min2ColKey, Integer.valueOf(radarTimer2.realmGet$min2()));
        osObjectBuilder.addBoolean(radarTimerColumnInfo.isEnableColKey, Boolean.valueOf(radarTimer2.realmGet$isEnable()));
        osObjectBuilder.addInteger(radarTimerColumnInfo.out_briColKey, Integer.valueOf(radarTimer2.realmGet$out_bri()));
        osObjectBuilder.addInteger(radarTimerColumnInfo.addrColKey, Integer.valueOf(radarTimer2.realmGet$addr()));
        com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy com_brgd_brblmesh_generaladapter_model_radartimerrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(radarTimer, com_brgd_brblmesh_generaladapter_model_radartimerrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_radartimerrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, RadarTimer radarTimer, Map<RealmModel, Long> map) {
        if ((radarTimer instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarTimer)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarTimer;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarTimer.class);
        long nativePtr = table.getNativePtr();
        RadarTimerColumnInfo radarTimerColumnInfo = (RadarTimerColumnInfo) realm.getSchema().getColumnInfo(RadarTimer.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarTimer, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.indexNumColKey, jCreateRow, r11.realmGet$indexNum(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour1ColKey, jCreateRow, r11.realmGet$hour1(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min1ColKey, jCreateRow, r11.realmGet$min1(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour2ColKey, jCreateRow, r11.realmGet$hour2(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min2ColKey, jCreateRow, r11.realmGet$min2(), false);
        Table.nativeSetBoolean(nativePtr, radarTimerColumnInfo.isEnableColKey, jCreateRow, radarTimer.realmGet$isEnable(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.out_briColKey, jCreateRow, r11.realmGet$out_bri(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.addrColKey, jCreateRow, r11.realmGet$addr(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarTimer.class);
        long nativePtr = table.getNativePtr();
        RadarTimerColumnInfo radarTimerColumnInfo = (RadarTimerColumnInfo) realm.getSchema().getColumnInfo(RadarTimer.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarTimer) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.indexNumColKey, jCreateRow, r11.realmGet$indexNum(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour1ColKey, jCreateRow, r11.realmGet$hour1(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min1ColKey, jCreateRow, r11.realmGet$min1(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour2ColKey, jCreateRow, r11.realmGet$hour2(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min2ColKey, jCreateRow, r11.realmGet$min2(), false);
                Table.nativeSetBoolean(nativePtr, radarTimerColumnInfo.isEnableColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface) realmModel).realmGet$isEnable(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.out_briColKey, jCreateRow, r11.realmGet$out_bri(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.addrColKey, jCreateRow, r11.realmGet$addr(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, RadarTimer radarTimer, Map<RealmModel, Long> map) {
        if ((radarTimer instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarTimer)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarTimer;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarTimer.class);
        long nativePtr = table.getNativePtr();
        RadarTimerColumnInfo radarTimerColumnInfo = (RadarTimerColumnInfo) realm.getSchema().getColumnInfo(RadarTimer.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarTimer, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.indexNumColKey, jCreateRow, r11.realmGet$indexNum(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour1ColKey, jCreateRow, r11.realmGet$hour1(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min1ColKey, jCreateRow, r11.realmGet$min1(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour2ColKey, jCreateRow, r11.realmGet$hour2(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min2ColKey, jCreateRow, r11.realmGet$min2(), false);
        Table.nativeSetBoolean(nativePtr, radarTimerColumnInfo.isEnableColKey, jCreateRow, radarTimer.realmGet$isEnable(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.out_briColKey, jCreateRow, r11.realmGet$out_bri(), false);
        Table.nativeSetLong(nativePtr, radarTimerColumnInfo.addrColKey, jCreateRow, r11.realmGet$addr(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarTimer.class);
        long nativePtr = table.getNativePtr();
        RadarTimerColumnInfo radarTimerColumnInfo = (RadarTimerColumnInfo) realm.getSchema().getColumnInfo(RadarTimer.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarTimer) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.indexNumColKey, jCreateRow, r11.realmGet$indexNum(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour1ColKey, jCreateRow, r11.realmGet$hour1(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min1ColKey, jCreateRow, r11.realmGet$min1(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.hour2ColKey, jCreateRow, r11.realmGet$hour2(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.min2ColKey, jCreateRow, r11.realmGet$min2(), false);
                Table.nativeSetBoolean(nativePtr, radarTimerColumnInfo.isEnableColKey, jCreateRow, ((com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface) realmModel).realmGet$isEnable(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.out_briColKey, jCreateRow, r11.realmGet$out_bri(), false);
                Table.nativeSetLong(nativePtr, radarTimerColumnInfo.addrColKey, jCreateRow, r11.realmGet$addr(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarTimer createDetachedCopy(RadarTimer radarTimer, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        RadarTimer radarTimer2;
        if (i > i2 || radarTimer == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(radarTimer);
        if (cacheData == null) {
            radarTimer2 = new RadarTimer();
            map.put(radarTimer, new RealmObjectProxy.CacheData<>(i, radarTimer2));
        } else {
            if (i >= cacheData.minDepth) {
                return (RadarTimer) cacheData.object;
            }
            RadarTimer radarTimer3 = (RadarTimer) cacheData.object;
            cacheData.minDepth = i;
            radarTimer2 = radarTimer3;
        }
        RadarTimer radarTimer4 = radarTimer2;
        RadarTimer radarTimer5 = radarTimer;
        radarTimer4.realmSet$indexNum(radarTimer5.realmGet$indexNum());
        radarTimer4.realmSet$hour1(radarTimer5.realmGet$hour1());
        radarTimer4.realmSet$min1(radarTimer5.realmGet$min1());
        radarTimer4.realmSet$hour2(radarTimer5.realmGet$hour2());
        radarTimer4.realmSet$min2(radarTimer5.realmGet$min2());
        radarTimer4.realmSet$isEnable(radarTimer5.realmGet$isEnable());
        radarTimer4.realmSet$out_bri(radarTimer5.realmGet$out_bri());
        radarTimer4.realmSet$addr(radarTimer5.realmGet$addr());
        return radarTimer2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "RadarTimer = proxy[{indexNum:" + realmGet$indexNum() + "},{hour1:" + realmGet$hour1() + "},{min1:" + realmGet$min1() + "},{hour2:" + realmGet$hour2() + "},{min2:" + realmGet$min2() + "},{isEnable:" + realmGet$isEnable() + "},{out_bri:" + realmGet$out_bri() + "},{addr:" + realmGet$addr() + "}]";
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
        com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy com_brgd_brblmesh_generaladapter_model_radartimerrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_radartimerrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_radartimerrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_radartimerrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

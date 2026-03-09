package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
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
public class com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy extends RadarDevice implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private RadarDeviceColumnInfo columnInfo;
    private ProxyState<RadarDevice> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RadarDevice";
    }

    static final class RadarDeviceColumnInfo extends ColumnInfo {
        long addrColKey;
        long didColKey;
        long groupIdColKey;
        long indexColKey;
        long isConfigAlexaColKey;
        long isSupportAlexaEnableColKey;
        long isSupportFixedGroupColKey;
        long keyColKey;
        long nameColKey;
        long typeColKey;
        long versionColKey;

        RadarDeviceColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(11);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.didColKey = addColumnDetails(GlobalVariable.DID, GlobalVariable.DID, objectSchemaInfo);
            this.addrColKey = addColumnDetails(GlobalVariable.ADDR, GlobalVariable.ADDR, objectSchemaInfo);
            this.groupIdColKey = addColumnDetails(GlobalVariable.GROUPID, GlobalVariable.GROUPID, objectSchemaInfo);
            this.keyColKey = addColumnDetails(GlobalVariable.KEY, GlobalVariable.KEY, objectSchemaInfo);
            this.nameColKey = addColumnDetails(GlobalVariable.NAME, GlobalVariable.NAME, objectSchemaInfo);
            this.typeColKey = addColumnDetails(GlobalVariable.TYPE, GlobalVariable.TYPE, objectSchemaInfo);
            this.versionColKey = addColumnDetails(GlobalVariable.VERSION, GlobalVariable.VERSION, objectSchemaInfo);
            this.indexColKey = addColumnDetails(GlobalVariable.INDEX, GlobalVariable.INDEX, objectSchemaInfo);
            this.isSupportAlexaEnableColKey = addColumnDetails("isSupportAlexaEnable", "isSupportAlexaEnable", objectSchemaInfo);
            this.isConfigAlexaColKey = addColumnDetails("isConfigAlexa", "isConfigAlexa", objectSchemaInfo);
            this.isSupportFixedGroupColKey = addColumnDetails("isSupportFixedGroup", "isSupportFixedGroup", objectSchemaInfo);
        }

        RadarDeviceColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new RadarDeviceColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            RadarDeviceColumnInfo radarDeviceColumnInfo = (RadarDeviceColumnInfo) columnInfo;
            RadarDeviceColumnInfo radarDeviceColumnInfo2 = (RadarDeviceColumnInfo) columnInfo2;
            radarDeviceColumnInfo2.didColKey = radarDeviceColumnInfo.didColKey;
            radarDeviceColumnInfo2.addrColKey = radarDeviceColumnInfo.addrColKey;
            radarDeviceColumnInfo2.groupIdColKey = radarDeviceColumnInfo.groupIdColKey;
            radarDeviceColumnInfo2.keyColKey = radarDeviceColumnInfo.keyColKey;
            radarDeviceColumnInfo2.nameColKey = radarDeviceColumnInfo.nameColKey;
            radarDeviceColumnInfo2.typeColKey = radarDeviceColumnInfo.typeColKey;
            radarDeviceColumnInfo2.versionColKey = radarDeviceColumnInfo.versionColKey;
            radarDeviceColumnInfo2.indexColKey = radarDeviceColumnInfo.indexColKey;
            radarDeviceColumnInfo2.isSupportAlexaEnableColKey = radarDeviceColumnInfo.isSupportAlexaEnableColKey;
            radarDeviceColumnInfo2.isConfigAlexaColKey = radarDeviceColumnInfo.isConfigAlexaColKey;
            radarDeviceColumnInfo2.isSupportFixedGroupColKey = radarDeviceColumnInfo.isSupportFixedGroupColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (RadarDeviceColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<RadarDevice> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$did() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.didColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$did(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.didColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.didColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.didColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.didColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$addr() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.addrColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$groupId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.groupIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$key() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.keyColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$key(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.keyColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.keyColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.keyColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.keyColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.nameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$name(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.nameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.nameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.nameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.nameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.typeColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$type(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.typeColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.typeColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$version() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.versionColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$version(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.versionColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.versionColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.versionColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.versionColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$index() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public boolean realmGet$isSupportAlexaEnable() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportAlexaEnableColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$isSupportAlexaEnable(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSupportAlexaEnableColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSupportAlexaEnableColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public boolean realmGet$isConfigAlexa() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isConfigAlexaColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$isConfigAlexa(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isConfigAlexaColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isConfigAlexaColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public boolean realmGet$isSupportFixedGroup() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportFixedGroupColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.RadarDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$isSupportFixedGroup(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSupportFixedGroupColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSupportFixedGroupColKey, z);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 11, 0);
        builder.addPersistedProperty("", GlobalVariable.DID, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.ADDR, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.GROUPID, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.KEY, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.NAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.TYPE, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.VERSION, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.INDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "isSupportAlexaEnable", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "isConfigAlexa", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "isSupportFixedGroup", RealmFieldType.BOOLEAN, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RadarDeviceColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new RadarDeviceColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static RadarDevice createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        RadarDevice radarDevice = (RadarDevice) realm.createObjectInternal(RadarDevice.class, true, Collections.EMPTY_LIST);
        RadarDevice radarDevice2 = radarDevice;
        if (jSONObject.has(GlobalVariable.DID)) {
            if (jSONObject.isNull(GlobalVariable.DID)) {
                radarDevice2.realmSet$did(null);
            } else {
                radarDevice2.realmSet$did(jSONObject.getString(GlobalVariable.DID));
            }
        }
        if (jSONObject.has(GlobalVariable.ADDR)) {
            if (jSONObject.isNull(GlobalVariable.ADDR)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
            }
            radarDevice2.realmSet$addr(jSONObject.getInt(GlobalVariable.ADDR));
        }
        if (jSONObject.has(GlobalVariable.GROUPID)) {
            if (jSONObject.isNull(GlobalVariable.GROUPID)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
            }
            radarDevice2.realmSet$groupId(jSONObject.getInt(GlobalVariable.GROUPID));
        }
        if (jSONObject.has(GlobalVariable.KEY)) {
            if (jSONObject.isNull(GlobalVariable.KEY)) {
                radarDevice2.realmSet$key(null);
            } else {
                radarDevice2.realmSet$key(jSONObject.getString(GlobalVariable.KEY));
            }
        }
        if (jSONObject.has(GlobalVariable.NAME)) {
            if (jSONObject.isNull(GlobalVariable.NAME)) {
                radarDevice2.realmSet$name(null);
            } else {
                radarDevice2.realmSet$name(jSONObject.getString(GlobalVariable.NAME));
            }
        }
        if (jSONObject.has(GlobalVariable.TYPE)) {
            if (jSONObject.isNull(GlobalVariable.TYPE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
            }
            radarDevice2.realmSet$type(jSONObject.getInt(GlobalVariable.TYPE));
        }
        if (jSONObject.has(GlobalVariable.VERSION)) {
            if (jSONObject.isNull(GlobalVariable.VERSION)) {
                radarDevice2.realmSet$version(null);
            } else {
                radarDevice2.realmSet$version(jSONObject.getString(GlobalVariable.VERSION));
            }
        }
        if (jSONObject.has(GlobalVariable.INDEX)) {
            if (jSONObject.isNull(GlobalVariable.INDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
            }
            radarDevice2.realmSet$index(jSONObject.getInt(GlobalVariable.INDEX));
        }
        if (jSONObject.has("isSupportAlexaEnable")) {
            if (jSONObject.isNull("isSupportAlexaEnable")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportAlexaEnable' to null.");
            }
            radarDevice2.realmSet$isSupportAlexaEnable(jSONObject.getBoolean("isSupportAlexaEnable"));
        }
        if (jSONObject.has("isConfigAlexa")) {
            if (jSONObject.isNull("isConfigAlexa")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isConfigAlexa' to null.");
            }
            radarDevice2.realmSet$isConfigAlexa(jSONObject.getBoolean("isConfigAlexa"));
        }
        if (!jSONObject.has("isSupportFixedGroup")) {
            return radarDevice;
        }
        if (jSONObject.isNull("isSupportFixedGroup")) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportFixedGroup' to null.");
        }
        radarDevice2.realmSet$isSupportFixedGroup(jSONObject.getBoolean("isSupportFixedGroup"));
        return radarDevice;
    }

    public static RadarDevice createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        RadarDevice radarDevice = new RadarDevice();
        RadarDevice radarDevice2 = radarDevice;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.DID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$did(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    radarDevice2.realmSet$did(null);
                }
            } else if (strNextName.equals(GlobalVariable.ADDR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$addr(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.GROUPID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$groupId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.KEY)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$key(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    radarDevice2.realmSet$key(null);
                }
            } else if (strNextName.equals(GlobalVariable.NAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    radarDevice2.realmSet$name(null);
                }
            } else if (strNextName.equals(GlobalVariable.TYPE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$type(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.VERSION)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$version(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    radarDevice2.realmSet$version(null);
                }
            } else if (strNextName.equals(GlobalVariable.INDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$index(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
                }
            } else if (strNextName.equals("isSupportAlexaEnable")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$isSupportAlexaEnable(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportAlexaEnable' to null.");
                }
            } else if (strNextName.equals("isConfigAlexa")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$isConfigAlexa(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isConfigAlexa' to null.");
                }
            } else if (strNextName.equals("isSupportFixedGroup")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    radarDevice2.realmSet$isSupportFixedGroup(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportFixedGroup' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (RadarDevice) realm.copyToRealm(radarDevice, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(RadarDevice.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_radardevicerealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_radardevicerealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarDevice copyOrUpdate(Realm realm, RadarDeviceColumnInfo radarDeviceColumnInfo, RadarDevice radarDevice, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((radarDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return radarDevice;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(radarDevice);
        if (realmModel != null) {
            return (RadarDevice) realmModel;
        }
        return copy(realm, radarDeviceColumnInfo, radarDevice, z, map, set);
    }

    public static RadarDevice copy(Realm realm, RadarDeviceColumnInfo radarDeviceColumnInfo, RadarDevice radarDevice, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(radarDevice);
        if (realmObjectProxy != null) {
            return (RadarDevice) realmObjectProxy;
        }
        RadarDevice radarDevice2 = radarDevice;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(RadarDevice.class), set);
        osObjectBuilder.addString(radarDeviceColumnInfo.didColKey, radarDevice2.realmGet$did());
        osObjectBuilder.addInteger(radarDeviceColumnInfo.addrColKey, Integer.valueOf(radarDevice2.realmGet$addr()));
        osObjectBuilder.addInteger(radarDeviceColumnInfo.groupIdColKey, Integer.valueOf(radarDevice2.realmGet$groupId()));
        osObjectBuilder.addString(radarDeviceColumnInfo.keyColKey, radarDevice2.realmGet$key());
        osObjectBuilder.addString(radarDeviceColumnInfo.nameColKey, radarDevice2.realmGet$name());
        osObjectBuilder.addInteger(radarDeviceColumnInfo.typeColKey, Integer.valueOf(radarDevice2.realmGet$type()));
        osObjectBuilder.addString(radarDeviceColumnInfo.versionColKey, radarDevice2.realmGet$version());
        osObjectBuilder.addInteger(radarDeviceColumnInfo.indexColKey, Integer.valueOf(radarDevice2.realmGet$index()));
        osObjectBuilder.addBoolean(radarDeviceColumnInfo.isSupportAlexaEnableColKey, Boolean.valueOf(radarDevice2.realmGet$isSupportAlexaEnable()));
        osObjectBuilder.addBoolean(radarDeviceColumnInfo.isConfigAlexaColKey, Boolean.valueOf(radarDevice2.realmGet$isConfigAlexa()));
        osObjectBuilder.addBoolean(radarDeviceColumnInfo.isSupportFixedGroupColKey, Boolean.valueOf(radarDevice2.realmGet$isSupportFixedGroup()));
        com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(radarDevice, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, RadarDevice radarDevice, Map<RealmModel, Long> map) {
        if ((radarDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarDevice.class);
        long nativePtr = table.getNativePtr();
        RadarDeviceColumnInfo radarDeviceColumnInfo = (RadarDeviceColumnInfo) realm.getSchema().getColumnInfo(RadarDevice.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarDevice, Long.valueOf(jCreateRow));
        RadarDevice radarDevice2 = radarDevice;
        String strRealmGet$did = radarDevice2.realmGet$did();
        if (strRealmGet$did != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
        }
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.addrColKey, jCreateRow, radarDevice2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.groupIdColKey, jCreateRow, radarDevice2.realmGet$groupId(), false);
        String strRealmGet$key = radarDevice2.realmGet$key();
        if (strRealmGet$key != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
        }
        String strRealmGet$name = radarDevice2.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        }
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.typeColKey, jCreateRow, radarDevice2.realmGet$type(), false);
        String strRealmGet$version = radarDevice2.realmGet$version();
        if (strRealmGet$version != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
        }
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.indexColKey, jCreateRow, radarDevice2.realmGet$index(), false);
        Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, radarDevice2.realmGet$isSupportAlexaEnable(), false);
        Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, radarDevice2.realmGet$isConfigAlexa(), false);
        Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, radarDevice2.realmGet$isSupportFixedGroup(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarDevice.class);
        long nativePtr = table.getNativePtr();
        RadarDeviceColumnInfo radarDeviceColumnInfo = (RadarDeviceColumnInfo) realm.getSchema().getColumnInfo(RadarDevice.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarDevice) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface) realmModel;
                String strRealmGet$did = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$did();
                if (strRealmGet$did != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
                }
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.groupIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$groupId(), false);
                String strRealmGet$key = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$key();
                if (strRealmGet$key != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
                }
                String strRealmGet$name = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                }
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.typeColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$type(), false);
                String strRealmGet$version = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$version();
                if (strRealmGet$version != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
                }
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$index(), false);
                Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$isSupportAlexaEnable(), false);
                Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$isConfigAlexa(), false);
                Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$isSupportFixedGroup(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, RadarDevice radarDevice, Map<RealmModel, Long> map) {
        if ((radarDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(radarDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) radarDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(RadarDevice.class);
        long nativePtr = table.getNativePtr();
        RadarDeviceColumnInfo radarDeviceColumnInfo = (RadarDeviceColumnInfo) realm.getSchema().getColumnInfo(RadarDevice.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(radarDevice, Long.valueOf(jCreateRow));
        RadarDevice radarDevice2 = radarDevice;
        String strRealmGet$did = radarDevice2.realmGet$did();
        if (strRealmGet$did != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
        } else {
            Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.didColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.addrColKey, jCreateRow, radarDevice2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.groupIdColKey, jCreateRow, radarDevice2.realmGet$groupId(), false);
        String strRealmGet$key = radarDevice2.realmGet$key();
        if (strRealmGet$key != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
        } else {
            Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.keyColKey, jCreateRow, false);
        }
        String strRealmGet$name = radarDevice2.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        } else {
            Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.nameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.typeColKey, jCreateRow, radarDevice2.realmGet$type(), false);
        String strRealmGet$version = radarDevice2.realmGet$version();
        if (strRealmGet$version != null) {
            Table.nativeSetString(nativePtr, radarDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
        } else {
            Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.versionColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.indexColKey, jCreateRow, radarDevice2.realmGet$index(), false);
        Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, radarDevice2.realmGet$isSupportAlexaEnable(), false);
        Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, radarDevice2.realmGet$isConfigAlexa(), false);
        Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, radarDevice2.realmGet$isSupportFixedGroup(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(RadarDevice.class);
        long nativePtr = table.getNativePtr();
        RadarDeviceColumnInfo radarDeviceColumnInfo = (RadarDeviceColumnInfo) realm.getSchema().getColumnInfo(RadarDevice.class);
        while (it.hasNext()) {
            RealmModel realmModel = (RadarDevice) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface) realmModel;
                String strRealmGet$did = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$did();
                if (strRealmGet$did != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
                } else {
                    Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.didColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.groupIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$groupId(), false);
                String strRealmGet$key = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$key();
                if (strRealmGet$key != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
                } else {
                    Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.keyColKey, jCreateRow, false);
                }
                String strRealmGet$name = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                } else {
                    Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.nameColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.typeColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$type(), false);
                String strRealmGet$version = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$version();
                if (strRealmGet$version != null) {
                    Table.nativeSetString(nativePtr, radarDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
                } else {
                    Table.nativeSetNull(nativePtr, radarDeviceColumnInfo.versionColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, radarDeviceColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$index(), false);
                Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$isSupportAlexaEnable(), false);
                Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$isConfigAlexa(), false);
                Table.nativeSetBoolean(nativePtr, radarDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_radardevicerealmproxyinterface.realmGet$isSupportFixedGroup(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static RadarDevice createDetachedCopy(RadarDevice radarDevice, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        RadarDevice radarDevice2;
        if (i > i2 || radarDevice == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(radarDevice);
        if (cacheData == null) {
            radarDevice2 = new RadarDevice();
            map.put(radarDevice, new RealmObjectProxy.CacheData<>(i, radarDevice2));
        } else {
            if (i >= cacheData.minDepth) {
                return (RadarDevice) cacheData.object;
            }
            RadarDevice radarDevice3 = (RadarDevice) cacheData.object;
            cacheData.minDepth = i;
            radarDevice2 = radarDevice3;
        }
        RadarDevice radarDevice4 = radarDevice2;
        RadarDevice radarDevice5 = radarDevice;
        radarDevice4.realmSet$did(radarDevice5.realmGet$did());
        radarDevice4.realmSet$addr(radarDevice5.realmGet$addr());
        radarDevice4.realmSet$groupId(radarDevice5.realmGet$groupId());
        radarDevice4.realmSet$key(radarDevice5.realmGet$key());
        radarDevice4.realmSet$name(radarDevice5.realmGet$name());
        radarDevice4.realmSet$type(radarDevice5.realmGet$type());
        radarDevice4.realmSet$version(radarDevice5.realmGet$version());
        radarDevice4.realmSet$index(radarDevice5.realmGet$index());
        radarDevice4.realmSet$isSupportAlexaEnable(radarDevice5.realmGet$isSupportAlexaEnable());
        radarDevice4.realmSet$isConfigAlexa(radarDevice5.realmGet$isConfigAlexa());
        radarDevice4.realmSet$isSupportFixedGroup(radarDevice5.realmGet$isSupportFixedGroup());
        return radarDevice2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("RadarDevice = proxy[{did:");
        String strRealmGet$did = realmGet$did();
        String strRealmGet$version = GlobalVariable.nullColor;
        sb.append(strRealmGet$did != null ? realmGet$did() : GlobalVariable.nullColor);
        sb.append("},{addr:");
        sb.append(realmGet$addr());
        sb.append("},{groupId:");
        sb.append(realmGet$groupId());
        sb.append("},{key:");
        sb.append(realmGet$key() != null ? realmGet$key() : GlobalVariable.nullColor);
        sb.append("},{name:");
        sb.append(realmGet$name() != null ? realmGet$name() : GlobalVariable.nullColor);
        sb.append("},{type:");
        sb.append(realmGet$type());
        sb.append("},{version:");
        if (realmGet$version() != null) {
            strRealmGet$version = realmGet$version();
        }
        sb.append(strRealmGet$version);
        sb.append("},{index:");
        sb.append(realmGet$index());
        sb.append("},{isSupportAlexaEnable:");
        sb.append(realmGet$isSupportAlexaEnable());
        sb.append("},{isConfigAlexa:");
        sb.append(realmGet$isConfigAlexa());
        sb.append("},{isSupportFixedGroup:");
        sb.append(realmGet$isSupportFixedGroup());
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
        com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_radardevicerealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_radardevicerealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_radardevicerealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
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
public class com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy extends SceneDevice implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private SceneDeviceColumnInfo columnInfo;
    private ProxyState<SceneDevice> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "SceneDevice";
    }

    static final class SceneDeviceColumnInfo extends ColumnInfo {
        long colorColKey;
        long customModIdColKey;
        long didColKey;
        long lightnessColKey;
        long modBrightnessColKey;
        long modNumberColKey;
        long modSpeedColKey;
        long sceneNumberColKey;
        long tempColKey;
        long tempSpeedColKey;

        SceneDeviceColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(10);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.didColKey = addColumnDetails(GlobalVariable.DID, GlobalVariable.DID, objectSchemaInfo);
            this.sceneNumberColKey = addColumnDetails(GlobalVariable.SCENENUM, GlobalVariable.SCENENUM, objectSchemaInfo);
            this.colorColKey = addColumnDetails(TypedValues.Custom.S_COLOR, TypedValues.Custom.S_COLOR, objectSchemaInfo);
            this.lightnessColKey = addColumnDetails(GlobalVariable.LIGHTNESS, GlobalVariable.LIGHTNESS, objectSchemaInfo);
            this.tempColKey = addColumnDetails(GlobalVariable.TEMP, GlobalVariable.TEMP, objectSchemaInfo);
            this.modNumberColKey = addColumnDetails(GlobalVariable.MODNUM, GlobalVariable.MODNUM, objectSchemaInfo);
            this.modSpeedColKey = addColumnDetails(GlobalVariable.MODSPEED, GlobalVariable.MODSPEED, objectSchemaInfo);
            this.customModIdColKey = addColumnDetails(GlobalVariable.customModId, GlobalVariable.customModId, objectSchemaInfo);
            this.tempSpeedColKey = addColumnDetails("tempSpeed", "tempSpeed", objectSchemaInfo);
            this.modBrightnessColKey = addColumnDetails(GlobalVariable.MOD_BRIGHTNESS, GlobalVariable.MOD_BRIGHTNESS, objectSchemaInfo);
        }

        SceneDeviceColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new SceneDeviceColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            SceneDeviceColumnInfo sceneDeviceColumnInfo = (SceneDeviceColumnInfo) columnInfo;
            SceneDeviceColumnInfo sceneDeviceColumnInfo2 = (SceneDeviceColumnInfo) columnInfo2;
            sceneDeviceColumnInfo2.didColKey = sceneDeviceColumnInfo.didColKey;
            sceneDeviceColumnInfo2.sceneNumberColKey = sceneDeviceColumnInfo.sceneNumberColKey;
            sceneDeviceColumnInfo2.colorColKey = sceneDeviceColumnInfo.colorColKey;
            sceneDeviceColumnInfo2.lightnessColKey = sceneDeviceColumnInfo.lightnessColKey;
            sceneDeviceColumnInfo2.tempColKey = sceneDeviceColumnInfo.tempColKey;
            sceneDeviceColumnInfo2.modNumberColKey = sceneDeviceColumnInfo.modNumberColKey;
            sceneDeviceColumnInfo2.modSpeedColKey = sceneDeviceColumnInfo.modSpeedColKey;
            sceneDeviceColumnInfo2.customModIdColKey = sceneDeviceColumnInfo.customModIdColKey;
            sceneDeviceColumnInfo2.tempSpeedColKey = sceneDeviceColumnInfo.tempSpeedColKey;
            sceneDeviceColumnInfo2.modBrightnessColKey = sceneDeviceColumnInfo.modBrightnessColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (SceneDeviceColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<SceneDevice> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public String realmGet$did() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.didColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$sceneNumber() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.sceneNumberColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$color() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.colorColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$color(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.colorColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.colorColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$lightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.lightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$lightness(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.lightnessColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.lightnessColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$temp() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.tempColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$temp(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.tempColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.tempColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$modNumber() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.modNumberColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$modNumber(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.modNumberColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.modNumberColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$modSpeed() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.modSpeedColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$modSpeed(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.modSpeedColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.modSpeedColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public String realmGet$customModId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.customModIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$customModId(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.customModIdColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.customModIdColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.customModIdColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.customModIdColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$tempSpeed() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.tempSpeedColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$tempSpeed(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.tempSpeedColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.tempSpeedColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$modBrightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.modBrightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.SceneDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$modBrightness(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.modBrightnessColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.modBrightnessColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 10, 0);
        builder.addPersistedProperty("", GlobalVariable.DID, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.SCENENUM, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", TypedValues.Custom.S_COLOR, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.LIGHTNESS, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.TEMP, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MODNUM, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MODSPEED, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.customModId, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", "tempSpeed", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MOD_BRIGHTNESS, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static SceneDeviceColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new SceneDeviceColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static SceneDevice createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        SceneDevice sceneDevice = (SceneDevice) realm.createObjectInternal(SceneDevice.class, true, Collections.EMPTY_LIST);
        SceneDevice sceneDevice2 = sceneDevice;
        if (jSONObject.has(GlobalVariable.DID)) {
            if (jSONObject.isNull(GlobalVariable.DID)) {
                sceneDevice2.realmSet$did(null);
            } else {
                sceneDevice2.realmSet$did(jSONObject.getString(GlobalVariable.DID));
            }
        }
        if (jSONObject.has(GlobalVariable.SCENENUM)) {
            if (jSONObject.isNull(GlobalVariable.SCENENUM)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'sceneNumber' to null.");
            }
            sceneDevice2.realmSet$sceneNumber(jSONObject.getInt(GlobalVariable.SCENENUM));
        }
        if (jSONObject.has(TypedValues.Custom.S_COLOR)) {
            if (jSONObject.isNull(TypedValues.Custom.S_COLOR)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'color' to null.");
            }
            sceneDevice2.realmSet$color(jSONObject.getInt(TypedValues.Custom.S_COLOR));
        }
        if (jSONObject.has(GlobalVariable.LIGHTNESS)) {
            if (jSONObject.isNull(GlobalVariable.LIGHTNESS)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'lightness' to null.");
            }
            sceneDevice2.realmSet$lightness(jSONObject.getInt(GlobalVariable.LIGHTNESS));
        }
        if (jSONObject.has(GlobalVariable.TEMP)) {
            if (jSONObject.isNull(GlobalVariable.TEMP)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'temp' to null.");
            }
            sceneDevice2.realmSet$temp(jSONObject.getInt(GlobalVariable.TEMP));
        }
        if (jSONObject.has(GlobalVariable.MODNUM)) {
            if (jSONObject.isNull(GlobalVariable.MODNUM)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'modNumber' to null.");
            }
            sceneDevice2.realmSet$modNumber(jSONObject.getInt(GlobalVariable.MODNUM));
        }
        if (jSONObject.has(GlobalVariable.MODSPEED)) {
            if (jSONObject.isNull(GlobalVariable.MODSPEED)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'modSpeed' to null.");
            }
            sceneDevice2.realmSet$modSpeed(jSONObject.getInt(GlobalVariable.MODSPEED));
        }
        if (jSONObject.has(GlobalVariable.customModId)) {
            if (jSONObject.isNull(GlobalVariable.customModId)) {
                sceneDevice2.realmSet$customModId(null);
            } else {
                sceneDevice2.realmSet$customModId(jSONObject.getString(GlobalVariable.customModId));
            }
        }
        if (jSONObject.has("tempSpeed")) {
            if (jSONObject.isNull("tempSpeed")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'tempSpeed' to null.");
            }
            sceneDevice2.realmSet$tempSpeed(jSONObject.getInt("tempSpeed"));
        }
        if (!jSONObject.has(GlobalVariable.MOD_BRIGHTNESS)) {
            return sceneDevice;
        }
        if (jSONObject.isNull(GlobalVariable.MOD_BRIGHTNESS)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'modBrightness' to null.");
        }
        sceneDevice2.realmSet$modBrightness(jSONObject.getInt(GlobalVariable.MOD_BRIGHTNESS));
        return sceneDevice;
    }

    public static SceneDevice createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        SceneDevice sceneDevice = new SceneDevice();
        SceneDevice sceneDevice2 = sceneDevice;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.DID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$did(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    sceneDevice2.realmSet$did(null);
                }
            } else if (strNextName.equals(GlobalVariable.SCENENUM)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$sceneNumber(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sceneNumber' to null.");
                }
            } else if (strNextName.equals(TypedValues.Custom.S_COLOR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$color(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'color' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.LIGHTNESS)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$lightness(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'lightness' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.TEMP)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$temp(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'temp' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MODNUM)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$modNumber(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modNumber' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MODSPEED)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$modSpeed(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modSpeed' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.customModId)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$customModId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    sceneDevice2.realmSet$customModId(null);
                }
            } else if (strNextName.equals("tempSpeed")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$tempSpeed(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'tempSpeed' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MOD_BRIGHTNESS)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    sceneDevice2.realmSet$modBrightness(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modBrightness' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (SceneDevice) realm.copyToRealm(sceneDevice, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(SceneDevice.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SceneDevice copyOrUpdate(Realm realm, SceneDeviceColumnInfo sceneDeviceColumnInfo, SceneDevice sceneDevice, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((sceneDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(sceneDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) sceneDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return sceneDevice;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(sceneDevice);
        if (realmModel != null) {
            return (SceneDevice) realmModel;
        }
        return copy(realm, sceneDeviceColumnInfo, sceneDevice, z, map, set);
    }

    public static SceneDevice copy(Realm realm, SceneDeviceColumnInfo sceneDeviceColumnInfo, SceneDevice sceneDevice, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(sceneDevice);
        if (realmObjectProxy != null) {
            return (SceneDevice) realmObjectProxy;
        }
        SceneDevice sceneDevice2 = sceneDevice;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(SceneDevice.class), set);
        osObjectBuilder.addString(sceneDeviceColumnInfo.didColKey, sceneDevice2.realmGet$did());
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.sceneNumberColKey, Integer.valueOf(sceneDevice2.realmGet$sceneNumber()));
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.colorColKey, Integer.valueOf(sceneDevice2.realmGet$color()));
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.lightnessColKey, Integer.valueOf(sceneDevice2.realmGet$lightness()));
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.tempColKey, Integer.valueOf(sceneDevice2.realmGet$temp()));
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.modNumberColKey, Integer.valueOf(sceneDevice2.realmGet$modNumber()));
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.modSpeedColKey, Integer.valueOf(sceneDevice2.realmGet$modSpeed()));
        osObjectBuilder.addString(sceneDeviceColumnInfo.customModIdColKey, sceneDevice2.realmGet$customModId());
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.tempSpeedColKey, Integer.valueOf(sceneDevice2.realmGet$tempSpeed()));
        osObjectBuilder.addInteger(sceneDeviceColumnInfo.modBrightnessColKey, Integer.valueOf(sceneDevice2.realmGet$modBrightness()));
        com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(sceneDevice, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, SceneDevice sceneDevice, Map<RealmModel, Long> map) {
        if ((sceneDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(sceneDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) sceneDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(SceneDevice.class);
        long nativePtr = table.getNativePtr();
        SceneDeviceColumnInfo sceneDeviceColumnInfo = (SceneDeviceColumnInfo) realm.getSchema().getColumnInfo(SceneDevice.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(sceneDevice, Long.valueOf(jCreateRow));
        SceneDevice sceneDevice2 = sceneDevice;
        String strRealmGet$did = sceneDevice2.realmGet$did();
        if (strRealmGet$did != null) {
            Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
        }
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.sceneNumberColKey, jCreateRow, sceneDevice2.realmGet$sceneNumber(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.colorColKey, jCreateRow, sceneDevice2.realmGet$color(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.lightnessColKey, jCreateRow, sceneDevice2.realmGet$lightness(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempColKey, jCreateRow, sceneDevice2.realmGet$temp(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modNumberColKey, jCreateRow, sceneDevice2.realmGet$modNumber(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modSpeedColKey, jCreateRow, sceneDevice2.realmGet$modSpeed(), false);
        String strRealmGet$customModId = sceneDevice2.realmGet$customModId();
        if (strRealmGet$customModId != null) {
            Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
        }
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempSpeedColKey, jCreateRow, sceneDevice2.realmGet$tempSpeed(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modBrightnessColKey, jCreateRow, sceneDevice2.realmGet$modBrightness(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(SceneDevice.class);
        long nativePtr = table.getNativePtr();
        SceneDeviceColumnInfo sceneDeviceColumnInfo = (SceneDeviceColumnInfo) realm.getSchema().getColumnInfo(SceneDevice.class);
        while (it.hasNext()) {
            RealmModel realmModel = (SceneDevice) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface) realmModel;
                String strRealmGet$did = com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$did();
                if (strRealmGet$did != null) {
                    Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
                }
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.sceneNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$sceneNumber(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.colorColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$color(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.lightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$lightness(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$temp(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$modNumber(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modSpeedColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$modSpeed(), false);
                String strRealmGet$customModId = com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$customModId();
                if (strRealmGet$customModId != null) {
                    Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
                }
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempSpeedColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$tempSpeed(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modBrightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$modBrightness(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, SceneDevice sceneDevice, Map<RealmModel, Long> map) {
        if ((sceneDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(sceneDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) sceneDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(SceneDevice.class);
        long nativePtr = table.getNativePtr();
        SceneDeviceColumnInfo sceneDeviceColumnInfo = (SceneDeviceColumnInfo) realm.getSchema().getColumnInfo(SceneDevice.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(sceneDevice, Long.valueOf(jCreateRow));
        SceneDevice sceneDevice2 = sceneDevice;
        String strRealmGet$did = sceneDevice2.realmGet$did();
        if (strRealmGet$did != null) {
            Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
        } else {
            Table.nativeSetNull(nativePtr, sceneDeviceColumnInfo.didColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.sceneNumberColKey, jCreateRow, sceneDevice2.realmGet$sceneNumber(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.colorColKey, jCreateRow, sceneDevice2.realmGet$color(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.lightnessColKey, jCreateRow, sceneDevice2.realmGet$lightness(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempColKey, jCreateRow, sceneDevice2.realmGet$temp(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modNumberColKey, jCreateRow, sceneDevice2.realmGet$modNumber(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modSpeedColKey, jCreateRow, sceneDevice2.realmGet$modSpeed(), false);
        String strRealmGet$customModId = sceneDevice2.realmGet$customModId();
        if (strRealmGet$customModId != null) {
            Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
        } else {
            Table.nativeSetNull(nativePtr, sceneDeviceColumnInfo.customModIdColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempSpeedColKey, jCreateRow, sceneDevice2.realmGet$tempSpeed(), false);
        Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modBrightnessColKey, jCreateRow, sceneDevice2.realmGet$modBrightness(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(SceneDevice.class);
        long nativePtr = table.getNativePtr();
        SceneDeviceColumnInfo sceneDeviceColumnInfo = (SceneDeviceColumnInfo) realm.getSchema().getColumnInfo(SceneDevice.class);
        while (it.hasNext()) {
            RealmModel realmModel = (SceneDevice) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface) realmModel;
                String strRealmGet$did = com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$did();
                if (strRealmGet$did != null) {
                    Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
                } else {
                    Table.nativeSetNull(nativePtr, sceneDeviceColumnInfo.didColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.sceneNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$sceneNumber(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.colorColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$color(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.lightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$lightness(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$temp(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$modNumber(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modSpeedColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$modSpeed(), false);
                String strRealmGet$customModId = com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$customModId();
                if (strRealmGet$customModId != null) {
                    Table.nativeSetString(nativePtr, sceneDeviceColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
                } else {
                    Table.nativeSetNull(nativePtr, sceneDeviceColumnInfo.customModIdColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.tempSpeedColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$tempSpeed(), false);
                Table.nativeSetLong(nativePtr, sceneDeviceColumnInfo.modBrightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxyinterface.realmGet$modBrightness(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SceneDevice createDetachedCopy(SceneDevice sceneDevice, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        SceneDevice sceneDevice2;
        if (i > i2 || sceneDevice == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(sceneDevice);
        if (cacheData == null) {
            sceneDevice2 = new SceneDevice();
            map.put(sceneDevice, new RealmObjectProxy.CacheData<>(i, sceneDevice2));
        } else {
            if (i >= cacheData.minDepth) {
                return (SceneDevice) cacheData.object;
            }
            SceneDevice sceneDevice3 = (SceneDevice) cacheData.object;
            cacheData.minDepth = i;
            sceneDevice2 = sceneDevice3;
        }
        SceneDevice sceneDevice4 = sceneDevice2;
        SceneDevice sceneDevice5 = sceneDevice;
        sceneDevice4.realmSet$did(sceneDevice5.realmGet$did());
        sceneDevice4.realmSet$sceneNumber(sceneDevice5.realmGet$sceneNumber());
        sceneDevice4.realmSet$color(sceneDevice5.realmGet$color());
        sceneDevice4.realmSet$lightness(sceneDevice5.realmGet$lightness());
        sceneDevice4.realmSet$temp(sceneDevice5.realmGet$temp());
        sceneDevice4.realmSet$modNumber(sceneDevice5.realmGet$modNumber());
        sceneDevice4.realmSet$modSpeed(sceneDevice5.realmGet$modSpeed());
        sceneDevice4.realmSet$customModId(sceneDevice5.realmGet$customModId());
        sceneDevice4.realmSet$tempSpeed(sceneDevice5.realmGet$tempSpeed());
        sceneDevice4.realmSet$modBrightness(sceneDevice5.realmGet$modBrightness());
        return sceneDevice2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("SceneDevice = proxy[{did:");
        String strRealmGet$did = realmGet$did();
        String strRealmGet$customModId = GlobalVariable.nullColor;
        sb.append(strRealmGet$did != null ? realmGet$did() : GlobalVariable.nullColor);
        sb.append("},{sceneNumber:");
        sb.append(realmGet$sceneNumber());
        sb.append("},{color:");
        sb.append(realmGet$color());
        sb.append("},{lightness:");
        sb.append(realmGet$lightness());
        sb.append("},{temp:");
        sb.append(realmGet$temp());
        sb.append("},{modNumber:");
        sb.append(realmGet$modNumber());
        sb.append("},{modSpeed:");
        sb.append(realmGet$modSpeed());
        sb.append("},{customModId:");
        if (realmGet$customModId() != null) {
            strRealmGet$customModId = realmGet$customModId();
        }
        sb.append(strRealmGet$customModId);
        sb.append("},{tempSpeed:");
        sb.append(realmGet$tempSpeed());
        sb.append("},{modBrightness:");
        sb.append(realmGet$modBrightness());
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
        com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_scenedevicerealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

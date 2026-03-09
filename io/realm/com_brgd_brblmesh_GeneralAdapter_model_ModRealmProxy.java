package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
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
public class com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy extends Mod implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private ModColumnInfo columnInfo;
    private ProxyState<Mod> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Mod";
    }

    static final class ModColumnInfo extends ColumnInfo {
        long addrColKey;
        long brightnessColKey;
        long customModIdColKey;
        long customModNameColKey;
        long endBrightnessColKey;
        long isFlashColKey;
        long isSleepColKey;
        long minuteColKey;
        long modNumberColKey;
        long speedColKey;
        long startBrightnessColKey;
        long stateValueColKey;
        long warmValueColKey;

        ModColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(13);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo("Mod");
            this.modNumberColKey = addColumnDetails(GlobalVariable.MODNUM, GlobalVariable.MODNUM, objectSchemaInfo);
            this.speedColKey = addColumnDetails(GlobalVariable.SPEED, GlobalVariable.SPEED, objectSchemaInfo);
            this.addrColKey = addColumnDetails(GlobalVariable.ADDR, GlobalVariable.ADDR, objectSchemaInfo);
            this.startBrightnessColKey = addColumnDetails(GlobalVariable.STARTBRI, GlobalVariable.STARTBRI, objectSchemaInfo);
            this.endBrightnessColKey = addColumnDetails(GlobalVariable.ENDBRI, GlobalVariable.ENDBRI, objectSchemaInfo);
            this.minuteColKey = addColumnDetails(GlobalVariable.MINUTE, GlobalVariable.MINUTE, objectSchemaInfo);
            this.stateValueColKey = addColumnDetails("stateValue", "stateValue", objectSchemaInfo);
            this.warmValueColKey = addColumnDetails(GlobalVariable.WARMVALUE, GlobalVariable.WARMVALUE, objectSchemaInfo);
            this.isSleepColKey = addColumnDetails("isSleep", "isSleep", objectSchemaInfo);
            this.isFlashColKey = addColumnDetails(GlobalVariable.ISFLASH, GlobalVariable.ISFLASH, objectSchemaInfo);
            this.customModIdColKey = addColumnDetails(GlobalVariable.customModId, GlobalVariable.customModId, objectSchemaInfo);
            this.customModNameColKey = addColumnDetails(GlobalVariable.CUSTOMMODNAME, GlobalVariable.CUSTOMMODNAME, objectSchemaInfo);
            this.brightnessColKey = addColumnDetails(GlobalVariable.BRIGHTNESS, GlobalVariable.BRIGHTNESS, objectSchemaInfo);
        }

        ModColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new ModColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ModColumnInfo modColumnInfo = (ModColumnInfo) columnInfo;
            ModColumnInfo modColumnInfo2 = (ModColumnInfo) columnInfo2;
            modColumnInfo2.modNumberColKey = modColumnInfo.modNumberColKey;
            modColumnInfo2.speedColKey = modColumnInfo.speedColKey;
            modColumnInfo2.addrColKey = modColumnInfo.addrColKey;
            modColumnInfo2.startBrightnessColKey = modColumnInfo.startBrightnessColKey;
            modColumnInfo2.endBrightnessColKey = modColumnInfo.endBrightnessColKey;
            modColumnInfo2.minuteColKey = modColumnInfo.minuteColKey;
            modColumnInfo2.stateValueColKey = modColumnInfo.stateValueColKey;
            modColumnInfo2.warmValueColKey = modColumnInfo.warmValueColKey;
            modColumnInfo2.isSleepColKey = modColumnInfo.isSleepColKey;
            modColumnInfo2.isFlashColKey = modColumnInfo.isFlashColKey;
            modColumnInfo2.customModIdColKey = modColumnInfo.customModIdColKey;
            modColumnInfo2.customModNameColKey = modColumnInfo.customModNameColKey;
            modColumnInfo2.brightnessColKey = modColumnInfo.brightnessColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (ModColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<Mod> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$modNumber() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.modNumberColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$speed() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.speedColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$speed(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.speedColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.speedColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$addr() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.addrColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$startBrightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.startBrightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$startBrightness(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.startBrightnessColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.startBrightnessColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$endBrightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.endBrightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$endBrightness(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.endBrightnessColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.endBrightnessColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$minute() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.minuteColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$minute(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.minuteColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.minuteColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$stateValue() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.stateValueColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$stateValue(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.stateValueColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.stateValueColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$warmValue() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.warmValueColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$warmValue(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.warmValueColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.warmValueColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public boolean realmGet$isSleep() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSleepColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$isSleep(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSleepColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSleepColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public boolean realmGet$isFlash() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isFlashColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$isFlash(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isFlashColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isFlashColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public String realmGet$customModId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.customModIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public String realmGet$customModName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.customModNameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$customModName(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.customModNameColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.customModNameColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.customModNameColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.customModNameColKey, str);
        }
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$brightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.brightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Mod, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$brightness(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.brightnessColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.brightnessColKey, i);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", "Mod", false, 13, 0);
        builder.addPersistedProperty("", GlobalVariable.MODNUM, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.SPEED, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ADDR, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.STARTBRI, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ENDBRI, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MINUTE, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "stateValue", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.WARMVALUE, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "isSleep", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ISFLASH, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.customModId, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.CUSTOMMODNAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.BRIGHTNESS, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ModColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new ModColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return "Mod";
    }

    public static Mod createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        Mod mod = (Mod) realm.createObjectInternal(Mod.class, true, Collections.EMPTY_LIST);
        Mod mod2 = mod;
        if (jSONObject.has(GlobalVariable.MODNUM)) {
            if (jSONObject.isNull(GlobalVariable.MODNUM)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'modNumber' to null.");
            }
            mod2.realmSet$modNumber(jSONObject.getInt(GlobalVariable.MODNUM));
        }
        if (jSONObject.has(GlobalVariable.SPEED)) {
            if (jSONObject.isNull(GlobalVariable.SPEED)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'speed' to null.");
            }
            mod2.realmSet$speed(jSONObject.getInt(GlobalVariable.SPEED));
        }
        if (jSONObject.has(GlobalVariable.ADDR)) {
            if (jSONObject.isNull(GlobalVariable.ADDR)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
            }
            mod2.realmSet$addr(jSONObject.getInt(GlobalVariable.ADDR));
        }
        if (jSONObject.has(GlobalVariable.STARTBRI)) {
            if (jSONObject.isNull(GlobalVariable.STARTBRI)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'startBrightness' to null.");
            }
            mod2.realmSet$startBrightness(jSONObject.getInt(GlobalVariable.STARTBRI));
        }
        if (jSONObject.has(GlobalVariable.ENDBRI)) {
            if (jSONObject.isNull(GlobalVariable.ENDBRI)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'endBrightness' to null.");
            }
            mod2.realmSet$endBrightness(jSONObject.getInt(GlobalVariable.ENDBRI));
        }
        if (jSONObject.has(GlobalVariable.MINUTE)) {
            if (jSONObject.isNull(GlobalVariable.MINUTE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'minute' to null.");
            }
            mod2.realmSet$minute(jSONObject.getInt(GlobalVariable.MINUTE));
        }
        if (jSONObject.has("stateValue")) {
            if (jSONObject.isNull("stateValue")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'stateValue' to null.");
            }
            mod2.realmSet$stateValue(jSONObject.getInt("stateValue"));
        }
        if (jSONObject.has(GlobalVariable.WARMVALUE)) {
            if (jSONObject.isNull(GlobalVariable.WARMVALUE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'warmValue' to null.");
            }
            mod2.realmSet$warmValue(jSONObject.getInt(GlobalVariable.WARMVALUE));
        }
        if (jSONObject.has("isSleep")) {
            if (jSONObject.isNull("isSleep")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSleep' to null.");
            }
            mod2.realmSet$isSleep(jSONObject.getBoolean("isSleep"));
        }
        if (jSONObject.has(GlobalVariable.ISFLASH)) {
            if (jSONObject.isNull(GlobalVariable.ISFLASH)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isFlash' to null.");
            }
            mod2.realmSet$isFlash(jSONObject.getBoolean(GlobalVariable.ISFLASH));
        }
        if (jSONObject.has(GlobalVariable.customModId)) {
            if (jSONObject.isNull(GlobalVariable.customModId)) {
                mod2.realmSet$customModId(null);
            } else {
                mod2.realmSet$customModId(jSONObject.getString(GlobalVariable.customModId));
            }
        }
        if (jSONObject.has(GlobalVariable.CUSTOMMODNAME)) {
            if (jSONObject.isNull(GlobalVariable.CUSTOMMODNAME)) {
                mod2.realmSet$customModName(null);
            } else {
                mod2.realmSet$customModName(jSONObject.getString(GlobalVariable.CUSTOMMODNAME));
            }
        }
        if (!jSONObject.has(GlobalVariable.BRIGHTNESS)) {
            return mod;
        }
        if (jSONObject.isNull(GlobalVariable.BRIGHTNESS)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'brightness' to null.");
        }
        mod2.realmSet$brightness(jSONObject.getInt(GlobalVariable.BRIGHTNESS));
        return mod;
    }

    public static Mod createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        Mod mod = new Mod();
        Mod mod2 = mod;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.MODNUM)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$modNumber(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modNumber' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.SPEED)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$speed(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'speed' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ADDR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$addr(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.STARTBRI)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$startBrightness(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'startBrightness' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ENDBRI)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$endBrightness(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'endBrightness' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MINUTE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$minute(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'minute' to null.");
                }
            } else if (strNextName.equals("stateValue")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$stateValue(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'stateValue' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.WARMVALUE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$warmValue(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'warmValue' to null.");
                }
            } else if (strNextName.equals("isSleep")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$isSleep(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSleep' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ISFLASH)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$isFlash(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isFlash' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.customModId)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$customModId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    mod2.realmSet$customModId(null);
                }
            } else if (strNextName.equals(GlobalVariable.CUSTOMMODNAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$customModName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    mod2.realmSet$customModName(null);
                }
            } else if (strNextName.equals(GlobalVariable.BRIGHTNESS)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    mod2.realmSet$brightness(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'brightness' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (Mod) realm.copyToRealm(mod, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(Mod.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy com_brgd_brblmesh_generaladapter_model_modrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_modrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Mod copyOrUpdate(Realm realm, ModColumnInfo modColumnInfo, Mod mod, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((mod instanceof RealmObjectProxy) && !RealmObject.isFrozen(mod)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) mod;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return mod;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(mod);
        if (realmModel != null) {
            return (Mod) realmModel;
        }
        return copy(realm, modColumnInfo, mod, z, map, set);
    }

    public static Mod copy(Realm realm, ModColumnInfo modColumnInfo, Mod mod, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(mod);
        if (realmObjectProxy != null) {
            return (Mod) realmObjectProxy;
        }
        Mod mod2 = mod;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(Mod.class), set);
        osObjectBuilder.addInteger(modColumnInfo.modNumberColKey, Integer.valueOf(mod2.realmGet$modNumber()));
        osObjectBuilder.addInteger(modColumnInfo.speedColKey, Integer.valueOf(mod2.realmGet$speed()));
        osObjectBuilder.addInteger(modColumnInfo.addrColKey, Integer.valueOf(mod2.realmGet$addr()));
        osObjectBuilder.addInteger(modColumnInfo.startBrightnessColKey, Integer.valueOf(mod2.realmGet$startBrightness()));
        osObjectBuilder.addInteger(modColumnInfo.endBrightnessColKey, Integer.valueOf(mod2.realmGet$endBrightness()));
        osObjectBuilder.addInteger(modColumnInfo.minuteColKey, Integer.valueOf(mod2.realmGet$minute()));
        osObjectBuilder.addInteger(modColumnInfo.stateValueColKey, Integer.valueOf(mod2.realmGet$stateValue()));
        osObjectBuilder.addInteger(modColumnInfo.warmValueColKey, Integer.valueOf(mod2.realmGet$warmValue()));
        osObjectBuilder.addBoolean(modColumnInfo.isSleepColKey, Boolean.valueOf(mod2.realmGet$isSleep()));
        osObjectBuilder.addBoolean(modColumnInfo.isFlashColKey, Boolean.valueOf(mod2.realmGet$isFlash()));
        osObjectBuilder.addString(modColumnInfo.customModIdColKey, mod2.realmGet$customModId());
        osObjectBuilder.addString(modColumnInfo.customModNameColKey, mod2.realmGet$customModName());
        osObjectBuilder.addInteger(modColumnInfo.brightnessColKey, Integer.valueOf(mod2.realmGet$brightness()));
        com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy com_brgd_brblmesh_generaladapter_model_modrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(mod, com_brgd_brblmesh_generaladapter_model_modrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_modrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, Mod mod, Map<RealmModel, Long> map) {
        if ((mod instanceof RealmObjectProxy) && !RealmObject.isFrozen(mod)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) mod;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Mod.class);
        long nativePtr = table.getNativePtr();
        ModColumnInfo modColumnInfo = (ModColumnInfo) realm.getSchema().getColumnInfo(Mod.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(mod, Long.valueOf(jCreateRow));
        Mod mod2 = mod;
        Table.nativeSetLong(nativePtr, modColumnInfo.modNumberColKey, jCreateRow, mod2.realmGet$modNumber(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.speedColKey, jCreateRow, mod2.realmGet$speed(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.addrColKey, jCreateRow, mod2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.startBrightnessColKey, jCreateRow, mod2.realmGet$startBrightness(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.endBrightnessColKey, jCreateRow, mod2.realmGet$endBrightness(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.minuteColKey, jCreateRow, mod2.realmGet$minute(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.stateValueColKey, jCreateRow, mod2.realmGet$stateValue(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.warmValueColKey, jCreateRow, mod2.realmGet$warmValue(), false);
        Table.nativeSetBoolean(nativePtr, modColumnInfo.isSleepColKey, jCreateRow, mod2.realmGet$isSleep(), false);
        Table.nativeSetBoolean(nativePtr, modColumnInfo.isFlashColKey, jCreateRow, mod2.realmGet$isFlash(), false);
        String strRealmGet$customModId = mod2.realmGet$customModId();
        if (strRealmGet$customModId != null) {
            Table.nativeSetString(nativePtr, modColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
        }
        String strRealmGet$customModName = mod2.realmGet$customModName();
        if (strRealmGet$customModName != null) {
            Table.nativeSetString(nativePtr, modColumnInfo.customModNameColKey, jCreateRow, strRealmGet$customModName, false);
        }
        Table.nativeSetLong(nativePtr, modColumnInfo.brightnessColKey, jCreateRow, mod2.realmGet$brightness(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Mod.class);
        long nativePtr = table.getNativePtr();
        ModColumnInfo modColumnInfo = (ModColumnInfo) realm.getSchema().getColumnInfo(Mod.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Mod) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, modColumnInfo.modNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$modNumber(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.speedColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$speed(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.startBrightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$startBrightness(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.endBrightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$endBrightness(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.minuteColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$minute(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.stateValueColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$stateValue(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.warmValueColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$warmValue(), false);
                Table.nativeSetBoolean(nativePtr, modColumnInfo.isSleepColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$isSleep(), false);
                Table.nativeSetBoolean(nativePtr, modColumnInfo.isFlashColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$isFlash(), false);
                String strRealmGet$customModId = com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$customModId();
                if (strRealmGet$customModId != null) {
                    Table.nativeSetString(nativePtr, modColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
                }
                String strRealmGet$customModName = com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$customModName();
                if (strRealmGet$customModName != null) {
                    Table.nativeSetString(nativePtr, modColumnInfo.customModNameColKey, jCreateRow, strRealmGet$customModName, false);
                }
                Table.nativeSetLong(nativePtr, modColumnInfo.brightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$brightness(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, Mod mod, Map<RealmModel, Long> map) {
        if ((mod instanceof RealmObjectProxy) && !RealmObject.isFrozen(mod)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) mod;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Mod.class);
        long nativePtr = table.getNativePtr();
        ModColumnInfo modColumnInfo = (ModColumnInfo) realm.getSchema().getColumnInfo(Mod.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(mod, Long.valueOf(jCreateRow));
        Mod mod2 = mod;
        Table.nativeSetLong(nativePtr, modColumnInfo.modNumberColKey, jCreateRow, mod2.realmGet$modNumber(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.speedColKey, jCreateRow, mod2.realmGet$speed(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.addrColKey, jCreateRow, mod2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.startBrightnessColKey, jCreateRow, mod2.realmGet$startBrightness(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.endBrightnessColKey, jCreateRow, mod2.realmGet$endBrightness(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.minuteColKey, jCreateRow, mod2.realmGet$minute(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.stateValueColKey, jCreateRow, mod2.realmGet$stateValue(), false);
        Table.nativeSetLong(nativePtr, modColumnInfo.warmValueColKey, jCreateRow, mod2.realmGet$warmValue(), false);
        Table.nativeSetBoolean(nativePtr, modColumnInfo.isSleepColKey, jCreateRow, mod2.realmGet$isSleep(), false);
        Table.nativeSetBoolean(nativePtr, modColumnInfo.isFlashColKey, jCreateRow, mod2.realmGet$isFlash(), false);
        String strRealmGet$customModId = mod2.realmGet$customModId();
        if (strRealmGet$customModId != null) {
            Table.nativeSetString(nativePtr, modColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
        } else {
            Table.nativeSetNull(nativePtr, modColumnInfo.customModIdColKey, jCreateRow, false);
        }
        String strRealmGet$customModName = mod2.realmGet$customModName();
        if (strRealmGet$customModName != null) {
            Table.nativeSetString(nativePtr, modColumnInfo.customModNameColKey, jCreateRow, strRealmGet$customModName, false);
        } else {
            Table.nativeSetNull(nativePtr, modColumnInfo.customModNameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, modColumnInfo.brightnessColKey, jCreateRow, mod2.realmGet$brightness(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Mod.class);
        long nativePtr = table.getNativePtr();
        ModColumnInfo modColumnInfo = (ModColumnInfo) realm.getSchema().getColumnInfo(Mod.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Mod) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, modColumnInfo.modNumberColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$modNumber(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.speedColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$speed(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.startBrightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$startBrightness(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.endBrightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$endBrightness(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.minuteColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$minute(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.stateValueColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$stateValue(), false);
                Table.nativeSetLong(nativePtr, modColumnInfo.warmValueColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$warmValue(), false);
                Table.nativeSetBoolean(nativePtr, modColumnInfo.isSleepColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$isSleep(), false);
                Table.nativeSetBoolean(nativePtr, modColumnInfo.isFlashColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$isFlash(), false);
                String strRealmGet$customModId = com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$customModId();
                if (strRealmGet$customModId != null) {
                    Table.nativeSetString(nativePtr, modColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
                } else {
                    Table.nativeSetNull(nativePtr, modColumnInfo.customModIdColKey, jCreateRow, false);
                }
                String strRealmGet$customModName = com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$customModName();
                if (strRealmGet$customModName != null) {
                    Table.nativeSetString(nativePtr, modColumnInfo.customModNameColKey, jCreateRow, strRealmGet$customModName, false);
                } else {
                    Table.nativeSetNull(nativePtr, modColumnInfo.customModNameColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, modColumnInfo.brightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_modrealmproxyinterface.realmGet$brightness(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Mod createDetachedCopy(Mod mod, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Mod mod2;
        if (i > i2 || mod == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(mod);
        if (cacheData == null) {
            mod2 = new Mod();
            map.put(mod, new RealmObjectProxy.CacheData<>(i, mod2));
        } else {
            if (i >= cacheData.minDepth) {
                return (Mod) cacheData.object;
            }
            Mod mod3 = (Mod) cacheData.object;
            cacheData.minDepth = i;
            mod2 = mod3;
        }
        Mod mod4 = mod2;
        Mod mod5 = mod;
        mod4.realmSet$modNumber(mod5.realmGet$modNumber());
        mod4.realmSet$speed(mod5.realmGet$speed());
        mod4.realmSet$addr(mod5.realmGet$addr());
        mod4.realmSet$startBrightness(mod5.realmGet$startBrightness());
        mod4.realmSet$endBrightness(mod5.realmGet$endBrightness());
        mod4.realmSet$minute(mod5.realmGet$minute());
        mod4.realmSet$stateValue(mod5.realmGet$stateValue());
        mod4.realmSet$warmValue(mod5.realmGet$warmValue());
        mod4.realmSet$isSleep(mod5.realmGet$isSleep());
        mod4.realmSet$isFlash(mod5.realmGet$isFlash());
        mod4.realmSet$customModId(mod5.realmGet$customModId());
        mod4.realmSet$customModName(mod5.realmGet$customModName());
        mod4.realmSet$brightness(mod5.realmGet$brightness());
        return mod2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("Mod = proxy[{modNumber:");
        sb.append(realmGet$modNumber());
        sb.append("},{speed:");
        sb.append(realmGet$speed());
        sb.append("},{addr:");
        sb.append(realmGet$addr());
        sb.append("},{startBrightness:");
        sb.append(realmGet$startBrightness());
        sb.append("},{endBrightness:");
        sb.append(realmGet$endBrightness());
        sb.append("},{minute:");
        sb.append(realmGet$minute());
        sb.append("},{stateValue:");
        sb.append(realmGet$stateValue());
        sb.append("},{warmValue:");
        sb.append(realmGet$warmValue());
        sb.append("},{isSleep:");
        sb.append(realmGet$isSleep());
        sb.append("},{isFlash:");
        sb.append(realmGet$isFlash());
        sb.append("},{customModId:");
        String strRealmGet$customModId = realmGet$customModId();
        String strRealmGet$customModName = GlobalVariable.nullColor;
        sb.append(strRealmGet$customModId != null ? realmGet$customModId() : GlobalVariable.nullColor);
        sb.append("},{customModName:");
        if (realmGet$customModName() != null) {
            strRealmGet$customModName = realmGet$customModName();
        }
        sb.append(strRealmGet$customModName);
        sb.append("},{brightness:");
        sb.append(realmGet$brightness());
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
        com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy com_brgd_brblmesh_generaladapter_model_modrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_modrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_modrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_modrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

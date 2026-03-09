package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
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
public class com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy extends BleDevice implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private BleDeviceColumnInfo columnInfo;
    private ProxyState<BleDevice> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "BleDevice";
    }

    static final class BleDeviceColumnInfo extends ColumnInfo {
        long addrColKey;
        long beenMainColKey;
        long createTimeColKey;
        long didColKey;
        long fixedIdColKey;
        long groupIdColKey;
        long groupIndexColKey;
        long indexColKey;
        long isConfigAlexaColKey;
        long isSupportAlexaEnableColKey;
        long isSupportFixedGroupColKey;
        long isSupportGroupMainColKey;
        long isSupportModeLightnessColKey;
        long isSupportSceneControlColKey;
        long isSupportTimerStatusColKey;
        long keyColKey;
        long nameColKey;
        long otaTagColKey;
        long typeColKey;
        long versionColKey;

        BleDeviceColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(20);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.didColKey = addColumnDetails(GlobalVariable.DID, GlobalVariable.DID, objectSchemaInfo);
            this.addrColKey = addColumnDetails(GlobalVariable.ADDR, GlobalVariable.ADDR, objectSchemaInfo);
            this.createTimeColKey = addColumnDetails("createTime", "createTime", objectSchemaInfo);
            this.groupIdColKey = addColumnDetails(GlobalVariable.GROUPID, GlobalVariable.GROUPID, objectSchemaInfo);
            this.keyColKey = addColumnDetails(GlobalVariable.KEY, GlobalVariable.KEY, objectSchemaInfo);
            this.nameColKey = addColumnDetails(GlobalVariable.NAME, GlobalVariable.NAME, objectSchemaInfo);
            this.typeColKey = addColumnDetails(GlobalVariable.TYPE, GlobalVariable.TYPE, objectSchemaInfo);
            this.versionColKey = addColumnDetails(GlobalVariable.VERSION, GlobalVariable.VERSION, objectSchemaInfo);
            this.indexColKey = addColumnDetails(GlobalVariable.INDEX, GlobalVariable.INDEX, objectSchemaInfo);
            this.groupIndexColKey = addColumnDetails("groupIndex", "groupIndex", objectSchemaInfo);
            this.isSupportAlexaEnableColKey = addColumnDetails("isSupportAlexaEnable", "isSupportAlexaEnable", objectSchemaInfo);
            this.isConfigAlexaColKey = addColumnDetails("isConfigAlexa", "isConfigAlexa", objectSchemaInfo);
            this.isSupportFixedGroupColKey = addColumnDetails("isSupportFixedGroup", "isSupportFixedGroup", objectSchemaInfo);
            this.isSupportGroupMainColKey = addColumnDetails("isSupportGroupMain", "isSupportGroupMain", objectSchemaInfo);
            this.beenMainColKey = addColumnDetails(GlobalVariable.BEENMAIN, GlobalVariable.BEENMAIN, objectSchemaInfo);
            this.fixedIdColKey = addColumnDetails("fixedId", "fixedId", objectSchemaInfo);
            this.isSupportTimerStatusColKey = addColumnDetails(GlobalVariable.S_T_STATUS, GlobalVariable.S_T_STATUS, objectSchemaInfo);
            this.isSupportSceneControlColKey = addColumnDetails(GlobalVariable.S_SCENE_CTRL, GlobalVariable.S_SCENE_CTRL, objectSchemaInfo);
            this.otaTagColKey = addColumnDetails("otaTag", "otaTag", objectSchemaInfo);
            this.isSupportModeLightnessColKey = addColumnDetails(GlobalVariable.S_MODE_LIGHTNESS, GlobalVariable.S_MODE_LIGHTNESS, objectSchemaInfo);
        }

        BleDeviceColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new BleDeviceColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            BleDeviceColumnInfo bleDeviceColumnInfo = (BleDeviceColumnInfo) columnInfo;
            BleDeviceColumnInfo bleDeviceColumnInfo2 = (BleDeviceColumnInfo) columnInfo2;
            bleDeviceColumnInfo2.didColKey = bleDeviceColumnInfo.didColKey;
            bleDeviceColumnInfo2.addrColKey = bleDeviceColumnInfo.addrColKey;
            bleDeviceColumnInfo2.createTimeColKey = bleDeviceColumnInfo.createTimeColKey;
            bleDeviceColumnInfo2.groupIdColKey = bleDeviceColumnInfo.groupIdColKey;
            bleDeviceColumnInfo2.keyColKey = bleDeviceColumnInfo.keyColKey;
            bleDeviceColumnInfo2.nameColKey = bleDeviceColumnInfo.nameColKey;
            bleDeviceColumnInfo2.typeColKey = bleDeviceColumnInfo.typeColKey;
            bleDeviceColumnInfo2.versionColKey = bleDeviceColumnInfo.versionColKey;
            bleDeviceColumnInfo2.indexColKey = bleDeviceColumnInfo.indexColKey;
            bleDeviceColumnInfo2.groupIndexColKey = bleDeviceColumnInfo.groupIndexColKey;
            bleDeviceColumnInfo2.isSupportAlexaEnableColKey = bleDeviceColumnInfo.isSupportAlexaEnableColKey;
            bleDeviceColumnInfo2.isConfigAlexaColKey = bleDeviceColumnInfo.isConfigAlexaColKey;
            bleDeviceColumnInfo2.isSupportFixedGroupColKey = bleDeviceColumnInfo.isSupportFixedGroupColKey;
            bleDeviceColumnInfo2.isSupportGroupMainColKey = bleDeviceColumnInfo.isSupportGroupMainColKey;
            bleDeviceColumnInfo2.beenMainColKey = bleDeviceColumnInfo.beenMainColKey;
            bleDeviceColumnInfo2.fixedIdColKey = bleDeviceColumnInfo.fixedIdColKey;
            bleDeviceColumnInfo2.isSupportTimerStatusColKey = bleDeviceColumnInfo.isSupportTimerStatusColKey;
            bleDeviceColumnInfo2.isSupportSceneControlColKey = bleDeviceColumnInfo.isSupportSceneControlColKey;
            bleDeviceColumnInfo2.otaTagColKey = bleDeviceColumnInfo.otaTagColKey;
            bleDeviceColumnInfo2.isSupportModeLightnessColKey = bleDeviceColumnInfo.isSupportModeLightnessColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (BleDeviceColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<BleDevice> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$did() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.didColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$addr() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.addrColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public long realmGet$createTime() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.createTimeColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$createTime(long j) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.createTimeColKey, row$realm.getObjectKey(), j, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.createTimeColKey, j);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$groupId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.groupIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$key() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.keyColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$name() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.nameColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.typeColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$version() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.versionColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$index() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$groupIndex() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.groupIndexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$groupIndex(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.groupIndexColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.groupIndexColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportAlexaEnable() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportAlexaEnableColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isConfigAlexa() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isConfigAlexaColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportFixedGroup() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportFixedGroupColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportGroupMain() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportGroupMainColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportGroupMain(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSupportGroupMainColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSupportGroupMainColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$beenMain() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.beenMainColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$beenMain(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.beenMainColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.beenMainColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$fixedId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.fixedIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportTimerStatus() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportTimerStatusColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportTimerStatus(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSupportTimerStatusColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSupportTimerStatusColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportSceneControl() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportSceneControlColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportSceneControl(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSupportSceneControlColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSupportSceneControlColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$otaTag() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.otaTagColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$otaTag(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.otaTagColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.otaTagColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportModeLightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isSupportModeLightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.BleDevice, io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportModeLightness(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isSupportModeLightnessColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isSupportModeLightnessColKey, z);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 20, 0);
        builder.addPersistedProperty("", GlobalVariable.DID, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.ADDR, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "createTime", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.GROUPID, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.KEY, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.NAME, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.TYPE, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.VERSION, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("", GlobalVariable.INDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "groupIndex", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "isSupportAlexaEnable", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "isConfigAlexa", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "isSupportFixedGroup", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "isSupportGroupMain", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.BEENMAIN, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "fixedId", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.S_T_STATUS, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.S_SCENE_CTRL, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", "otaTag", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.S_MODE_LIGHTNESS, RealmFieldType.BOOLEAN, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static BleDeviceColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new BleDeviceColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static BleDevice createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        BleDevice bleDevice = (BleDevice) realm.createObjectInternal(BleDevice.class, true, Collections.EMPTY_LIST);
        BleDevice bleDevice2 = bleDevice;
        if (jSONObject.has(GlobalVariable.DID)) {
            if (jSONObject.isNull(GlobalVariable.DID)) {
                bleDevice2.realmSet$did(null);
            } else {
                bleDevice2.realmSet$did(jSONObject.getString(GlobalVariable.DID));
            }
        }
        if (jSONObject.has(GlobalVariable.ADDR)) {
            if (jSONObject.isNull(GlobalVariable.ADDR)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
            }
            bleDevice2.realmSet$addr(jSONObject.getInt(GlobalVariable.ADDR));
        }
        if (jSONObject.has("createTime")) {
            if (jSONObject.isNull("createTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'createTime' to null.");
            }
            bleDevice2.realmSet$createTime(jSONObject.getLong("createTime"));
        }
        if (jSONObject.has(GlobalVariable.GROUPID)) {
            if (jSONObject.isNull(GlobalVariable.GROUPID)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
            }
            bleDevice2.realmSet$groupId(jSONObject.getInt(GlobalVariable.GROUPID));
        }
        if (jSONObject.has(GlobalVariable.KEY)) {
            if (jSONObject.isNull(GlobalVariable.KEY)) {
                bleDevice2.realmSet$key(null);
            } else {
                bleDevice2.realmSet$key(jSONObject.getString(GlobalVariable.KEY));
            }
        }
        if (jSONObject.has(GlobalVariable.NAME)) {
            if (jSONObject.isNull(GlobalVariable.NAME)) {
                bleDevice2.realmSet$name(null);
            } else {
                bleDevice2.realmSet$name(jSONObject.getString(GlobalVariable.NAME));
            }
        }
        if (jSONObject.has(GlobalVariable.TYPE)) {
            if (jSONObject.isNull(GlobalVariable.TYPE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
            }
            bleDevice2.realmSet$type(jSONObject.getInt(GlobalVariable.TYPE));
        }
        if (jSONObject.has(GlobalVariable.VERSION)) {
            if (jSONObject.isNull(GlobalVariable.VERSION)) {
                bleDevice2.realmSet$version(null);
            } else {
                bleDevice2.realmSet$version(jSONObject.getString(GlobalVariable.VERSION));
            }
        }
        if (jSONObject.has(GlobalVariable.INDEX)) {
            if (jSONObject.isNull(GlobalVariable.INDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
            }
            bleDevice2.realmSet$index(jSONObject.getInt(GlobalVariable.INDEX));
        }
        if (jSONObject.has("groupIndex")) {
            if (jSONObject.isNull("groupIndex")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'groupIndex' to null.");
            }
            bleDevice2.realmSet$groupIndex(jSONObject.getInt("groupIndex"));
        }
        if (jSONObject.has("isSupportAlexaEnable")) {
            if (jSONObject.isNull("isSupportAlexaEnable")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportAlexaEnable' to null.");
            }
            bleDevice2.realmSet$isSupportAlexaEnable(jSONObject.getBoolean("isSupportAlexaEnable"));
        }
        if (jSONObject.has("isConfigAlexa")) {
            if (jSONObject.isNull("isConfigAlexa")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isConfigAlexa' to null.");
            }
            bleDevice2.realmSet$isConfigAlexa(jSONObject.getBoolean("isConfigAlexa"));
        }
        if (jSONObject.has("isSupportFixedGroup")) {
            if (jSONObject.isNull("isSupportFixedGroup")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportFixedGroup' to null.");
            }
            bleDevice2.realmSet$isSupportFixedGroup(jSONObject.getBoolean("isSupportFixedGroup"));
        }
        if (jSONObject.has("isSupportGroupMain")) {
            if (jSONObject.isNull("isSupportGroupMain")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportGroupMain' to null.");
            }
            bleDevice2.realmSet$isSupportGroupMain(jSONObject.getBoolean("isSupportGroupMain"));
        }
        if (jSONObject.has(GlobalVariable.BEENMAIN)) {
            if (jSONObject.isNull(GlobalVariable.BEENMAIN)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'beenMain' to null.");
            }
            bleDevice2.realmSet$beenMain(jSONObject.getBoolean(GlobalVariable.BEENMAIN));
        }
        if (jSONObject.has("fixedId")) {
            if (jSONObject.isNull("fixedId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
            }
            bleDevice2.realmSet$fixedId(jSONObject.getInt("fixedId"));
        }
        if (jSONObject.has(GlobalVariable.S_T_STATUS)) {
            if (jSONObject.isNull(GlobalVariable.S_T_STATUS)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportTimerStatus' to null.");
            }
            bleDevice2.realmSet$isSupportTimerStatus(jSONObject.getBoolean(GlobalVariable.S_T_STATUS));
        }
        if (jSONObject.has(GlobalVariable.S_SCENE_CTRL)) {
            if (jSONObject.isNull(GlobalVariable.S_SCENE_CTRL)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportSceneControl' to null.");
            }
            bleDevice2.realmSet$isSupportSceneControl(jSONObject.getBoolean(GlobalVariable.S_SCENE_CTRL));
        }
        if (jSONObject.has("otaTag")) {
            if (jSONObject.isNull("otaTag")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'otaTag' to null.");
            }
            bleDevice2.realmSet$otaTag(jSONObject.getInt("otaTag"));
        }
        if (!jSONObject.has(GlobalVariable.S_MODE_LIGHTNESS)) {
            return bleDevice;
        }
        if (jSONObject.isNull(GlobalVariable.S_MODE_LIGHTNESS)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportModeLightness' to null.");
        }
        bleDevice2.realmSet$isSupportModeLightness(jSONObject.getBoolean(GlobalVariable.S_MODE_LIGHTNESS));
        return bleDevice;
    }

    public static BleDevice createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        BleDevice bleDevice = new BleDevice();
        BleDevice bleDevice2 = bleDevice;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.DID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$did(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    bleDevice2.realmSet$did(null);
                }
            } else if (strNextName.equals(GlobalVariable.ADDR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$addr(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
                }
            } else if (strNextName.equals("createTime")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$createTime(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'createTime' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.GROUPID)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$groupId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'groupId' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.KEY)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$key(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    bleDevice2.realmSet$key(null);
                }
            } else if (strNextName.equals(GlobalVariable.NAME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    bleDevice2.realmSet$name(null);
                }
            } else if (strNextName.equals(GlobalVariable.TYPE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$type(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.VERSION)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$version(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    bleDevice2.realmSet$version(null);
                }
            } else if (strNextName.equals(GlobalVariable.INDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$index(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'index' to null.");
                }
            } else if (strNextName.equals("groupIndex")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$groupIndex(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'groupIndex' to null.");
                }
            } else if (strNextName.equals("isSupportAlexaEnable")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isSupportAlexaEnable(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportAlexaEnable' to null.");
                }
            } else if (strNextName.equals("isConfigAlexa")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isConfigAlexa(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isConfigAlexa' to null.");
                }
            } else if (strNextName.equals("isSupportFixedGroup")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isSupportFixedGroup(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportFixedGroup' to null.");
                }
            } else if (strNextName.equals("isSupportGroupMain")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isSupportGroupMain(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportGroupMain' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.BEENMAIN)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$beenMain(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'beenMain' to null.");
                }
            } else if (strNextName.equals("fixedId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$fixedId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'fixedId' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.S_T_STATUS)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isSupportTimerStatus(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportTimerStatus' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.S_SCENE_CTRL)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isSupportSceneControl(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportSceneControl' to null.");
                }
            } else if (strNextName.equals("otaTag")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$otaTag(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'otaTag' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.S_MODE_LIGHTNESS)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    bleDevice2.realmSet$isSupportModeLightness(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSupportModeLightness' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (BleDevice) realm.copyToRealm(bleDevice, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(BleDevice.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_bledevicerealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_bledevicerealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BleDevice copyOrUpdate(Realm realm, BleDeviceColumnInfo bleDeviceColumnInfo, BleDevice bleDevice, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((bleDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(bleDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) bleDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return bleDevice;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(bleDevice);
        if (realmModel != null) {
            return (BleDevice) realmModel;
        }
        return copy(realm, bleDeviceColumnInfo, bleDevice, z, map, set);
    }

    public static BleDevice copy(Realm realm, BleDeviceColumnInfo bleDeviceColumnInfo, BleDevice bleDevice, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(bleDevice);
        if (realmObjectProxy != null) {
            return (BleDevice) realmObjectProxy;
        }
        BleDevice bleDevice2 = bleDevice;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(BleDevice.class), set);
        osObjectBuilder.addString(bleDeviceColumnInfo.didColKey, bleDevice2.realmGet$did());
        osObjectBuilder.addInteger(bleDeviceColumnInfo.addrColKey, Integer.valueOf(bleDevice2.realmGet$addr()));
        osObjectBuilder.addInteger(bleDeviceColumnInfo.createTimeColKey, Long.valueOf(bleDevice2.realmGet$createTime()));
        osObjectBuilder.addInteger(bleDeviceColumnInfo.groupIdColKey, Integer.valueOf(bleDevice2.realmGet$groupId()));
        osObjectBuilder.addString(bleDeviceColumnInfo.keyColKey, bleDevice2.realmGet$key());
        osObjectBuilder.addString(bleDeviceColumnInfo.nameColKey, bleDevice2.realmGet$name());
        osObjectBuilder.addInteger(bleDeviceColumnInfo.typeColKey, Integer.valueOf(bleDevice2.realmGet$type()));
        osObjectBuilder.addString(bleDeviceColumnInfo.versionColKey, bleDevice2.realmGet$version());
        osObjectBuilder.addInteger(bleDeviceColumnInfo.indexColKey, Integer.valueOf(bleDevice2.realmGet$index()));
        osObjectBuilder.addInteger(bleDeviceColumnInfo.groupIndexColKey, Integer.valueOf(bleDevice2.realmGet$groupIndex()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isSupportAlexaEnableColKey, Boolean.valueOf(bleDevice2.realmGet$isSupportAlexaEnable()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isConfigAlexaColKey, Boolean.valueOf(bleDevice2.realmGet$isConfigAlexa()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isSupportFixedGroupColKey, Boolean.valueOf(bleDevice2.realmGet$isSupportFixedGroup()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isSupportGroupMainColKey, Boolean.valueOf(bleDevice2.realmGet$isSupportGroupMain()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.beenMainColKey, Boolean.valueOf(bleDevice2.realmGet$beenMain()));
        osObjectBuilder.addInteger(bleDeviceColumnInfo.fixedIdColKey, Integer.valueOf(bleDevice2.realmGet$fixedId()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isSupportTimerStatusColKey, Boolean.valueOf(bleDevice2.realmGet$isSupportTimerStatus()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isSupportSceneControlColKey, Boolean.valueOf(bleDevice2.realmGet$isSupportSceneControl()));
        osObjectBuilder.addInteger(bleDeviceColumnInfo.otaTagColKey, Integer.valueOf(bleDevice2.realmGet$otaTag()));
        osObjectBuilder.addBoolean(bleDeviceColumnInfo.isSupportModeLightnessColKey, Boolean.valueOf(bleDevice2.realmGet$isSupportModeLightness()));
        com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(bleDevice, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, BleDevice bleDevice, Map<RealmModel, Long> map) {
        if ((bleDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(bleDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) bleDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(BleDevice.class);
        long nativePtr = table.getNativePtr();
        BleDeviceColumnInfo bleDeviceColumnInfo = (BleDeviceColumnInfo) realm.getSchema().getColumnInfo(BleDevice.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(bleDevice, Long.valueOf(jCreateRow));
        BleDevice bleDevice2 = bleDevice;
        String strRealmGet$did = bleDevice2.realmGet$did();
        if (strRealmGet$did != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
        }
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.addrColKey, jCreateRow, bleDevice2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.createTimeColKey, jCreateRow, bleDevice2.realmGet$createTime(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIdColKey, jCreateRow, bleDevice2.realmGet$groupId(), false);
        String strRealmGet$key = bleDevice2.realmGet$key();
        if (strRealmGet$key != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
        }
        String strRealmGet$name = bleDevice2.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        }
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.typeColKey, jCreateRow, bleDevice2.realmGet$type(), false);
        String strRealmGet$version = bleDevice2.realmGet$version();
        if (strRealmGet$version != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
        }
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.indexColKey, jCreateRow, bleDevice2.realmGet$index(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIndexColKey, jCreateRow, bleDevice2.realmGet$groupIndex(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, bleDevice2.realmGet$isSupportAlexaEnable(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, bleDevice2.realmGet$isConfigAlexa(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, bleDevice2.realmGet$isSupportFixedGroup(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportGroupMainColKey, jCreateRow, bleDevice2.realmGet$isSupportGroupMain(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.beenMainColKey, jCreateRow, bleDevice2.realmGet$beenMain(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.fixedIdColKey, jCreateRow, bleDevice2.realmGet$fixedId(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportTimerStatusColKey, jCreateRow, bleDevice2.realmGet$isSupportTimerStatus(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportSceneControlColKey, jCreateRow, bleDevice2.realmGet$isSupportSceneControl(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.otaTagColKey, jCreateRow, bleDevice2.realmGet$otaTag(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportModeLightnessColKey, jCreateRow, bleDevice2.realmGet$isSupportModeLightness(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(BleDevice.class);
        long nativePtr = table.getNativePtr();
        BleDeviceColumnInfo bleDeviceColumnInfo = (BleDeviceColumnInfo) realm.getSchema().getColumnInfo(BleDevice.class);
        while (it.hasNext()) {
            RealmModel realmModel = (BleDevice) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface) realmModel;
                String strRealmGet$did = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$did();
                if (strRealmGet$did != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
                }
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.createTimeColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$createTime(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$groupId(), false);
                String strRealmGet$key = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$key();
                if (strRealmGet$key != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
                }
                String strRealmGet$name = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                }
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.typeColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$type(), false);
                String strRealmGet$version = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$version();
                if (strRealmGet$version != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
                }
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$index(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIndexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$groupIndex(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportAlexaEnable(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isConfigAlexa(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportFixedGroup(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportGroupMainColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportGroupMain(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.beenMainColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$beenMain(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.fixedIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$fixedId(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportTimerStatusColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportTimerStatus(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportSceneControlColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportSceneControl(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.otaTagColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$otaTag(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportModeLightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportModeLightness(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, BleDevice bleDevice, Map<RealmModel, Long> map) {
        if ((bleDevice instanceof RealmObjectProxy) && !RealmObject.isFrozen(bleDevice)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) bleDevice;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(BleDevice.class);
        long nativePtr = table.getNativePtr();
        BleDeviceColumnInfo bleDeviceColumnInfo = (BleDeviceColumnInfo) realm.getSchema().getColumnInfo(BleDevice.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(bleDevice, Long.valueOf(jCreateRow));
        BleDevice bleDevice2 = bleDevice;
        String strRealmGet$did = bleDevice2.realmGet$did();
        if (strRealmGet$did != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
        } else {
            Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.didColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.addrColKey, jCreateRow, bleDevice2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.createTimeColKey, jCreateRow, bleDevice2.realmGet$createTime(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIdColKey, jCreateRow, bleDevice2.realmGet$groupId(), false);
        String strRealmGet$key = bleDevice2.realmGet$key();
        if (strRealmGet$key != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
        } else {
            Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.keyColKey, jCreateRow, false);
        }
        String strRealmGet$name = bleDevice2.realmGet$name();
        if (strRealmGet$name != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
        } else {
            Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.nameColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.typeColKey, jCreateRow, bleDevice2.realmGet$type(), false);
        String strRealmGet$version = bleDevice2.realmGet$version();
        if (strRealmGet$version != null) {
            Table.nativeSetString(nativePtr, bleDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
        } else {
            Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.versionColKey, jCreateRow, false);
        }
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.indexColKey, jCreateRow, bleDevice2.realmGet$index(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIndexColKey, jCreateRow, bleDevice2.realmGet$groupIndex(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, bleDevice2.realmGet$isSupportAlexaEnable(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, bleDevice2.realmGet$isConfigAlexa(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, bleDevice2.realmGet$isSupportFixedGroup(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportGroupMainColKey, jCreateRow, bleDevice2.realmGet$isSupportGroupMain(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.beenMainColKey, jCreateRow, bleDevice2.realmGet$beenMain(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.fixedIdColKey, jCreateRow, bleDevice2.realmGet$fixedId(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportTimerStatusColKey, jCreateRow, bleDevice2.realmGet$isSupportTimerStatus(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportSceneControlColKey, jCreateRow, bleDevice2.realmGet$isSupportSceneControl(), false);
        Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.otaTagColKey, jCreateRow, bleDevice2.realmGet$otaTag(), false);
        Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportModeLightnessColKey, jCreateRow, bleDevice2.realmGet$isSupportModeLightness(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(BleDevice.class);
        long nativePtr = table.getNativePtr();
        BleDeviceColumnInfo bleDeviceColumnInfo = (BleDeviceColumnInfo) realm.getSchema().getColumnInfo(BleDevice.class);
        while (it.hasNext()) {
            RealmModel realmModel = (BleDevice) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface) realmModel;
                String strRealmGet$did = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$did();
                if (strRealmGet$did != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.didColKey, jCreateRow, strRealmGet$did, false);
                } else {
                    Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.didColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.createTimeColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$createTime(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$groupId(), false);
                String strRealmGet$key = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$key();
                if (strRealmGet$key != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.keyColKey, jCreateRow, strRealmGet$key, false);
                } else {
                    Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.keyColKey, jCreateRow, false);
                }
                String strRealmGet$name = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$name();
                if (strRealmGet$name != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.nameColKey, jCreateRow, strRealmGet$name, false);
                } else {
                    Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.nameColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.typeColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$type(), false);
                String strRealmGet$version = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$version();
                if (strRealmGet$version != null) {
                    Table.nativeSetString(nativePtr, bleDeviceColumnInfo.versionColKey, jCreateRow, strRealmGet$version, false);
                } else {
                    Table.nativeSetNull(nativePtr, bleDeviceColumnInfo.versionColKey, jCreateRow, false);
                }
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.indexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$index(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.groupIndexColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$groupIndex(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportAlexaEnableColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportAlexaEnable(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isConfigAlexaColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isConfigAlexa(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportFixedGroupColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportFixedGroup(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportGroupMainColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportGroupMain(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.beenMainColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$beenMain(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.fixedIdColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$fixedId(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportTimerStatusColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportTimerStatus(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportSceneControlColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportSceneControl(), false);
                Table.nativeSetLong(nativePtr, bleDeviceColumnInfo.otaTagColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$otaTag(), false);
                Table.nativeSetBoolean(nativePtr, bleDeviceColumnInfo.isSupportModeLightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_bledevicerealmproxyinterface.realmGet$isSupportModeLightness(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BleDevice createDetachedCopy(BleDevice bleDevice, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        BleDevice bleDevice2;
        if (i > i2 || bleDevice == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(bleDevice);
        if (cacheData == null) {
            bleDevice2 = new BleDevice();
            map.put(bleDevice, new RealmObjectProxy.CacheData<>(i, bleDevice2));
        } else {
            if (i >= cacheData.minDepth) {
                return (BleDevice) cacheData.object;
            }
            BleDevice bleDevice3 = (BleDevice) cacheData.object;
            cacheData.minDepth = i;
            bleDevice2 = bleDevice3;
        }
        BleDevice bleDevice4 = bleDevice2;
        BleDevice bleDevice5 = bleDevice;
        bleDevice4.realmSet$did(bleDevice5.realmGet$did());
        bleDevice4.realmSet$addr(bleDevice5.realmGet$addr());
        bleDevice4.realmSet$createTime(bleDevice5.realmGet$createTime());
        bleDevice4.realmSet$groupId(bleDevice5.realmGet$groupId());
        bleDevice4.realmSet$key(bleDevice5.realmGet$key());
        bleDevice4.realmSet$name(bleDevice5.realmGet$name());
        bleDevice4.realmSet$type(bleDevice5.realmGet$type());
        bleDevice4.realmSet$version(bleDevice5.realmGet$version());
        bleDevice4.realmSet$index(bleDevice5.realmGet$index());
        bleDevice4.realmSet$groupIndex(bleDevice5.realmGet$groupIndex());
        bleDevice4.realmSet$isSupportAlexaEnable(bleDevice5.realmGet$isSupportAlexaEnable());
        bleDevice4.realmSet$isConfigAlexa(bleDevice5.realmGet$isConfigAlexa());
        bleDevice4.realmSet$isSupportFixedGroup(bleDevice5.realmGet$isSupportFixedGroup());
        bleDevice4.realmSet$isSupportGroupMain(bleDevice5.realmGet$isSupportGroupMain());
        bleDevice4.realmSet$beenMain(bleDevice5.realmGet$beenMain());
        bleDevice4.realmSet$fixedId(bleDevice5.realmGet$fixedId());
        bleDevice4.realmSet$isSupportTimerStatus(bleDevice5.realmGet$isSupportTimerStatus());
        bleDevice4.realmSet$isSupportSceneControl(bleDevice5.realmGet$isSupportSceneControl());
        bleDevice4.realmSet$otaTag(bleDevice5.realmGet$otaTag());
        bleDevice4.realmSet$isSupportModeLightness(bleDevice5.realmGet$isSupportModeLightness());
        return bleDevice2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("BleDevice = proxy[{did:");
        String strRealmGet$did = realmGet$did();
        String strRealmGet$version = GlobalVariable.nullColor;
        sb.append(strRealmGet$did != null ? realmGet$did() : GlobalVariable.nullColor);
        sb.append("},{addr:");
        sb.append(realmGet$addr());
        sb.append("},{createTime:");
        sb.append(realmGet$createTime());
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
        sb.append("},{groupIndex:");
        sb.append(realmGet$groupIndex());
        sb.append("},{isSupportAlexaEnable:");
        sb.append(realmGet$isSupportAlexaEnable());
        sb.append("},{isConfigAlexa:");
        sb.append(realmGet$isConfigAlexa());
        sb.append("},{isSupportFixedGroup:");
        sb.append(realmGet$isSupportFixedGroup());
        sb.append("},{isSupportGroupMain:");
        sb.append(realmGet$isSupportGroupMain());
        sb.append("},{beenMain:");
        sb.append(realmGet$beenMain());
        sb.append("},{fixedId:");
        sb.append(realmGet$fixedId());
        sb.append("},{isSupportTimerStatus:");
        sb.append(realmGet$isSupportTimerStatus());
        sb.append("},{isSupportSceneControl:");
        sb.append(realmGet$isSupportSceneControl());
        sb.append("},{otaTag:");
        sb.append(realmGet$otaTag());
        sb.append("},{isSupportModeLightness:");
        sb.append(realmGet$isSupportModeLightness());
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
        com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy com_brgd_brblmesh_generaladapter_model_bledevicerealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_bledevicerealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_bledevicerealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
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
public class com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy extends Timer implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private TimerColumnInfo columnInfo;
    private ProxyState<Timer> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Timer";
    }

    static final class TimerColumnInfo extends ColumnInfo {
        long actionColKey;
        long addrColKey;
        long blueColKey;
        long brightnessColKey;
        long coldColKey;
        long colorColKey;
        long greenColKey;
        long hourColKey;
        long indexNumColKey;
        long isEnableColKey;
        long isRepeatColKey;
        long minuteColKey;
        long redColKey;
        long warmColKey;

        TimerColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(14);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo("Timer");
            this.indexNumColKey = addColumnDetails("indexNum", "indexNum", objectSchemaInfo);
            this.hourColKey = addColumnDetails(GlobalVariable.HOUR, GlobalVariable.HOUR, objectSchemaInfo);
            this.minuteColKey = addColumnDetails(GlobalVariable.MINUTE, GlobalVariable.MINUTE, objectSchemaInfo);
            this.actionColKey = addColumnDetails(GlobalVariable.ACTION, GlobalVariable.ACTION, objectSchemaInfo);
            this.isRepeatColKey = addColumnDetails(GlobalVariable.ISREPEAT, GlobalVariable.ISREPEAT, objectSchemaInfo);
            this.isEnableColKey = addColumnDetails(GlobalVariable.ISENABLE, GlobalVariable.ISENABLE, objectSchemaInfo);
            this.addrColKey = addColumnDetails(GlobalVariable.ADDR, GlobalVariable.ADDR, objectSchemaInfo);
            this.brightnessColKey = addColumnDetails(GlobalVariable.BRIGHTNESS, GlobalVariable.BRIGHTNESS, objectSchemaInfo);
            this.redColKey = addColumnDetails(GlobalVariable.RED, GlobalVariable.RED, objectSchemaInfo);
            this.greenColKey = addColumnDetails(GlobalVariable.GREEN, GlobalVariable.GREEN, objectSchemaInfo);
            this.blueColKey = addColumnDetails(GlobalVariable.BLUE, GlobalVariable.BLUE, objectSchemaInfo);
            this.coldColKey = addColumnDetails(GlobalVariable.COLD, GlobalVariable.COLD, objectSchemaInfo);
            this.warmColKey = addColumnDetails(GlobalVariable.WARM, GlobalVariable.WARM, objectSchemaInfo);
            this.colorColKey = addColumnDetails(TypedValues.Custom.S_COLOR, TypedValues.Custom.S_COLOR, objectSchemaInfo);
        }

        TimerColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new TimerColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            TimerColumnInfo timerColumnInfo = (TimerColumnInfo) columnInfo;
            TimerColumnInfo timerColumnInfo2 = (TimerColumnInfo) columnInfo2;
            timerColumnInfo2.indexNumColKey = timerColumnInfo.indexNumColKey;
            timerColumnInfo2.hourColKey = timerColumnInfo.hourColKey;
            timerColumnInfo2.minuteColKey = timerColumnInfo.minuteColKey;
            timerColumnInfo2.actionColKey = timerColumnInfo.actionColKey;
            timerColumnInfo2.isRepeatColKey = timerColumnInfo.isRepeatColKey;
            timerColumnInfo2.isEnableColKey = timerColumnInfo.isEnableColKey;
            timerColumnInfo2.addrColKey = timerColumnInfo.addrColKey;
            timerColumnInfo2.brightnessColKey = timerColumnInfo.brightnessColKey;
            timerColumnInfo2.redColKey = timerColumnInfo.redColKey;
            timerColumnInfo2.greenColKey = timerColumnInfo.greenColKey;
            timerColumnInfo2.blueColKey = timerColumnInfo.blueColKey;
            timerColumnInfo2.coldColKey = timerColumnInfo.coldColKey;
            timerColumnInfo2.warmColKey = timerColumnInfo.warmColKey;
            timerColumnInfo2.colorColKey = timerColumnInfo.colorColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (TimerColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<Timer> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$indexNum() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.indexNumColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$hour() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.hourColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$hour(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.hourColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.hourColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$minute() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.minuteColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$action() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.actionColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$action(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.actionColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.actionColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public boolean realmGet$isRepeat() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isRepeatColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$isRepeat(boolean z) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setBoolean(this.columnInfo.isRepeatColKey, row$realm.getObjectKey(), z, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.isRepeatColKey, z);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public boolean realmGet$isEnable() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.isEnableColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$addr() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.addrColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$brightness() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.brightnessColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
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

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$red() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.redColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$red(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.redColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.redColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$green() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.greenColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$green(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.greenColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.greenColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$blue() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.blueColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$blue(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.blueColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.blueColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$cold() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.coldColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$cold(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.coldColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.coldColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$warm() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.warmColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$warm(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.warmColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.warmColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$color() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.colorColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.Timer, io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", "Timer", false, 14, 0);
        builder.addPersistedProperty("", "indexNum", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.HOUR, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.MINUTE, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ACTION, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ISREPEAT, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ISENABLE, RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.ADDR, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.BRIGHTNESS, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.RED, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.GREEN, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.BLUE, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.COLD, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.WARM, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", TypedValues.Custom.S_COLOR, RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static TimerColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new TimerColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return "Timer";
    }

    public static Timer createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        Timer timer = (Timer) realm.createObjectInternal(Timer.class, true, Collections.EMPTY_LIST);
        Timer timer2 = timer;
        if (jSONObject.has("indexNum")) {
            if (jSONObject.isNull("indexNum")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'indexNum' to null.");
            }
            timer2.realmSet$indexNum(jSONObject.getInt("indexNum"));
        }
        if (jSONObject.has(GlobalVariable.HOUR)) {
            if (jSONObject.isNull(GlobalVariable.HOUR)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'hour' to null.");
            }
            timer2.realmSet$hour(jSONObject.getInt(GlobalVariable.HOUR));
        }
        if (jSONObject.has(GlobalVariable.MINUTE)) {
            if (jSONObject.isNull(GlobalVariable.MINUTE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'minute' to null.");
            }
            timer2.realmSet$minute(jSONObject.getInt(GlobalVariable.MINUTE));
        }
        if (jSONObject.has(GlobalVariable.ACTION)) {
            if (jSONObject.isNull(GlobalVariable.ACTION)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'action' to null.");
            }
            timer2.realmSet$action(jSONObject.getInt(GlobalVariable.ACTION));
        }
        if (jSONObject.has(GlobalVariable.ISREPEAT)) {
            if (jSONObject.isNull(GlobalVariable.ISREPEAT)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isRepeat' to null.");
            }
            timer2.realmSet$isRepeat(jSONObject.getBoolean(GlobalVariable.ISREPEAT));
        }
        if (jSONObject.has(GlobalVariable.ISENABLE)) {
            if (jSONObject.isNull(GlobalVariable.ISENABLE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isEnable' to null.");
            }
            timer2.realmSet$isEnable(jSONObject.getBoolean(GlobalVariable.ISENABLE));
        }
        if (jSONObject.has(GlobalVariable.ADDR)) {
            if (jSONObject.isNull(GlobalVariable.ADDR)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
            }
            timer2.realmSet$addr(jSONObject.getInt(GlobalVariable.ADDR));
        }
        if (jSONObject.has(GlobalVariable.BRIGHTNESS)) {
            if (jSONObject.isNull(GlobalVariable.BRIGHTNESS)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'brightness' to null.");
            }
            timer2.realmSet$brightness(jSONObject.getInt(GlobalVariable.BRIGHTNESS));
        }
        if (jSONObject.has(GlobalVariable.RED)) {
            if (jSONObject.isNull(GlobalVariable.RED)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'red' to null.");
            }
            timer2.realmSet$red(jSONObject.getInt(GlobalVariable.RED));
        }
        if (jSONObject.has(GlobalVariable.GREEN)) {
            if (jSONObject.isNull(GlobalVariable.GREEN)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'green' to null.");
            }
            timer2.realmSet$green(jSONObject.getInt(GlobalVariable.GREEN));
        }
        if (jSONObject.has(GlobalVariable.BLUE)) {
            if (jSONObject.isNull(GlobalVariable.BLUE)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'blue' to null.");
            }
            timer2.realmSet$blue(jSONObject.getInt(GlobalVariable.BLUE));
        }
        if (jSONObject.has(GlobalVariable.COLD)) {
            if (jSONObject.isNull(GlobalVariable.COLD)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'cold' to null.");
            }
            timer2.realmSet$cold(jSONObject.getInt(GlobalVariable.COLD));
        }
        if (jSONObject.has(GlobalVariable.WARM)) {
            if (jSONObject.isNull(GlobalVariable.WARM)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'warm' to null.");
            }
            timer2.realmSet$warm(jSONObject.getInt(GlobalVariable.WARM));
        }
        if (!jSONObject.has(TypedValues.Custom.S_COLOR)) {
            return timer;
        }
        if (jSONObject.isNull(TypedValues.Custom.S_COLOR)) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'color' to null.");
        }
        timer2.realmSet$color(jSONObject.getInt(TypedValues.Custom.S_COLOR));
        return timer;
    }

    public static Timer createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        Timer timer = new Timer();
        Timer timer2 = timer;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals("indexNum")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$indexNum(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'indexNum' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.HOUR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$hour(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'hour' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.MINUTE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$minute(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'minute' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ACTION)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$action(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'action' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ISREPEAT)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$isRepeat(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isRepeat' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ISENABLE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$isEnable(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isEnable' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.ADDR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$addr(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'addr' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.BRIGHTNESS)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$brightness(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'brightness' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.RED)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$red(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'red' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.GREEN)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$green(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'green' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.BLUE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$blue(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'blue' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.COLD)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$cold(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'cold' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.WARM)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$warm(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'warm' to null.");
                }
            } else if (strNextName.equals(TypedValues.Custom.S_COLOR)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    timer2.realmSet$color(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'color' to null.");
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (Timer) realm.copyToRealm(timer, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(Timer.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy com_brgd_brblmesh_generaladapter_model_timerrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_timerrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Timer copyOrUpdate(Realm realm, TimerColumnInfo timerColumnInfo, Timer timer, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((timer instanceof RealmObjectProxy) && !RealmObject.isFrozen(timer)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) timer;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return timer;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(timer);
        if (realmModel != null) {
            return (Timer) realmModel;
        }
        return copy(realm, timerColumnInfo, timer, z, map, set);
    }

    public static Timer copy(Realm realm, TimerColumnInfo timerColumnInfo, Timer timer, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(timer);
        if (realmObjectProxy != null) {
            return (Timer) realmObjectProxy;
        }
        Timer timer2 = timer;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(Timer.class), set);
        osObjectBuilder.addInteger(timerColumnInfo.indexNumColKey, Integer.valueOf(timer2.realmGet$indexNum()));
        osObjectBuilder.addInteger(timerColumnInfo.hourColKey, Integer.valueOf(timer2.realmGet$hour()));
        osObjectBuilder.addInteger(timerColumnInfo.minuteColKey, Integer.valueOf(timer2.realmGet$minute()));
        osObjectBuilder.addInteger(timerColumnInfo.actionColKey, Integer.valueOf(timer2.realmGet$action()));
        osObjectBuilder.addBoolean(timerColumnInfo.isRepeatColKey, Boolean.valueOf(timer2.realmGet$isRepeat()));
        osObjectBuilder.addBoolean(timerColumnInfo.isEnableColKey, Boolean.valueOf(timer2.realmGet$isEnable()));
        osObjectBuilder.addInteger(timerColumnInfo.addrColKey, Integer.valueOf(timer2.realmGet$addr()));
        osObjectBuilder.addInteger(timerColumnInfo.brightnessColKey, Integer.valueOf(timer2.realmGet$brightness()));
        osObjectBuilder.addInteger(timerColumnInfo.redColKey, Integer.valueOf(timer2.realmGet$red()));
        osObjectBuilder.addInteger(timerColumnInfo.greenColKey, Integer.valueOf(timer2.realmGet$green()));
        osObjectBuilder.addInteger(timerColumnInfo.blueColKey, Integer.valueOf(timer2.realmGet$blue()));
        osObjectBuilder.addInteger(timerColumnInfo.coldColKey, Integer.valueOf(timer2.realmGet$cold()));
        osObjectBuilder.addInteger(timerColumnInfo.warmColKey, Integer.valueOf(timer2.realmGet$warm()));
        osObjectBuilder.addInteger(timerColumnInfo.colorColKey, Integer.valueOf(timer2.realmGet$color()));
        com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy com_brgd_brblmesh_generaladapter_model_timerrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(timer, com_brgd_brblmesh_generaladapter_model_timerrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_timerrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, Timer timer, Map<RealmModel, Long> map) {
        if ((timer instanceof RealmObjectProxy) && !RealmObject.isFrozen(timer)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) timer;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Timer.class);
        long nativePtr = table.getNativePtr();
        TimerColumnInfo timerColumnInfo = (TimerColumnInfo) realm.getSchema().getColumnInfo(Timer.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(timer, Long.valueOf(jCreateRow));
        Timer timer2 = timer;
        Table.nativeSetLong(nativePtr, timerColumnInfo.indexNumColKey, jCreateRow, timer2.realmGet$indexNum(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.hourColKey, jCreateRow, timer2.realmGet$hour(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.minuteColKey, jCreateRow, timer2.realmGet$minute(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.actionColKey, jCreateRow, timer2.realmGet$action(), false);
        Table.nativeSetBoolean(nativePtr, timerColumnInfo.isRepeatColKey, jCreateRow, timer2.realmGet$isRepeat(), false);
        Table.nativeSetBoolean(nativePtr, timerColumnInfo.isEnableColKey, jCreateRow, timer2.realmGet$isEnable(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.addrColKey, jCreateRow, timer2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.brightnessColKey, jCreateRow, timer2.realmGet$brightness(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.redColKey, jCreateRow, timer2.realmGet$red(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.greenColKey, jCreateRow, timer2.realmGet$green(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.blueColKey, jCreateRow, timer2.realmGet$blue(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.coldColKey, jCreateRow, timer2.realmGet$cold(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.warmColKey, jCreateRow, timer2.realmGet$warm(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.colorColKey, jCreateRow, timer2.realmGet$color(), false);
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Timer.class);
        long nativePtr = table.getNativePtr();
        TimerColumnInfo timerColumnInfo = (TimerColumnInfo) realm.getSchema().getColumnInfo(Timer.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Timer) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, timerColumnInfo.indexNumColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$indexNum(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.hourColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$hour(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.minuteColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$minute(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.actionColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$action(), false);
                Table.nativeSetBoolean(nativePtr, timerColumnInfo.isRepeatColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$isRepeat(), false);
                Table.nativeSetBoolean(nativePtr, timerColumnInfo.isEnableColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$isEnable(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.brightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$brightness(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.redColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$red(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.greenColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$green(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.blueColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$blue(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.coldColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$cold(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.warmColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$warm(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.colorColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$color(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, Timer timer, Map<RealmModel, Long> map) {
        if ((timer instanceof RealmObjectProxy) && !RealmObject.isFrozen(timer)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) timer;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(Timer.class);
        long nativePtr = table.getNativePtr();
        TimerColumnInfo timerColumnInfo = (TimerColumnInfo) realm.getSchema().getColumnInfo(Timer.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(timer, Long.valueOf(jCreateRow));
        Timer timer2 = timer;
        Table.nativeSetLong(nativePtr, timerColumnInfo.indexNumColKey, jCreateRow, timer2.realmGet$indexNum(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.hourColKey, jCreateRow, timer2.realmGet$hour(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.minuteColKey, jCreateRow, timer2.realmGet$minute(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.actionColKey, jCreateRow, timer2.realmGet$action(), false);
        Table.nativeSetBoolean(nativePtr, timerColumnInfo.isRepeatColKey, jCreateRow, timer2.realmGet$isRepeat(), false);
        Table.nativeSetBoolean(nativePtr, timerColumnInfo.isEnableColKey, jCreateRow, timer2.realmGet$isEnable(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.addrColKey, jCreateRow, timer2.realmGet$addr(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.brightnessColKey, jCreateRow, timer2.realmGet$brightness(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.redColKey, jCreateRow, timer2.realmGet$red(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.greenColKey, jCreateRow, timer2.realmGet$green(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.blueColKey, jCreateRow, timer2.realmGet$blue(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.coldColKey, jCreateRow, timer2.realmGet$cold(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.warmColKey, jCreateRow, timer2.realmGet$warm(), false);
        Table.nativeSetLong(nativePtr, timerColumnInfo.colorColKey, jCreateRow, timer2.realmGet$color(), false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(Timer.class);
        long nativePtr = table.getNativePtr();
        TimerColumnInfo timerColumnInfo = (TimerColumnInfo) realm.getSchema().getColumnInfo(Timer.class);
        while (it.hasNext()) {
            RealmModel realmModel = (Timer) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface = (com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface) realmModel;
                Table.nativeSetLong(nativePtr, timerColumnInfo.indexNumColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$indexNum(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.hourColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$hour(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.minuteColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$minute(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.actionColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$action(), false);
                Table.nativeSetBoolean(nativePtr, timerColumnInfo.isRepeatColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$isRepeat(), false);
                Table.nativeSetBoolean(nativePtr, timerColumnInfo.isEnableColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$isEnable(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.addrColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$addr(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.brightnessColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$brightness(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.redColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$red(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.greenColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$green(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.blueColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$blue(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.coldColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$cold(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.warmColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$warm(), false);
                Table.nativeSetLong(nativePtr, timerColumnInfo.colorColKey, jCreateRow, com_brgd_brblmesh_generaladapter_model_timerrealmproxyinterface.realmGet$color(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Timer createDetachedCopy(Timer timer, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Timer timer2;
        if (i > i2 || timer == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(timer);
        if (cacheData == null) {
            timer2 = new Timer();
            map.put(timer, new RealmObjectProxy.CacheData<>(i, timer2));
        } else {
            if (i >= cacheData.minDepth) {
                return (Timer) cacheData.object;
            }
            Timer timer3 = (Timer) cacheData.object;
            cacheData.minDepth = i;
            timer2 = timer3;
        }
        Timer timer4 = timer2;
        Timer timer5 = timer;
        timer4.realmSet$indexNum(timer5.realmGet$indexNum());
        timer4.realmSet$hour(timer5.realmGet$hour());
        timer4.realmSet$minute(timer5.realmGet$minute());
        timer4.realmSet$action(timer5.realmGet$action());
        timer4.realmSet$isRepeat(timer5.realmGet$isRepeat());
        timer4.realmSet$isEnable(timer5.realmGet$isEnable());
        timer4.realmSet$addr(timer5.realmGet$addr());
        timer4.realmSet$brightness(timer5.realmGet$brightness());
        timer4.realmSet$red(timer5.realmGet$red());
        timer4.realmSet$green(timer5.realmGet$green());
        timer4.realmSet$blue(timer5.realmGet$blue());
        timer4.realmSet$cold(timer5.realmGet$cold());
        timer4.realmSet$warm(timer5.realmGet$warm());
        timer4.realmSet$color(timer5.realmGet$color());
        return timer2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "Timer = proxy[{indexNum:" + realmGet$indexNum() + "},{hour:" + realmGet$hour() + "},{minute:" + realmGet$minute() + "},{action:" + realmGet$action() + "},{isRepeat:" + realmGet$isRepeat() + "},{isEnable:" + realmGet$isEnable() + "},{addr:" + realmGet$addr() + "},{brightness:" + realmGet$brightness() + "},{red:" + realmGet$red() + "},{green:" + realmGet$green() + "},{blue:" + realmGet$blue() + "},{cold:" + realmGet$cold() + "},{warm:" + realmGet$warm() + "},{color:" + realmGet$color() + "}]";
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
        com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy com_brgd_brblmesh_generaladapter_model_timerrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_timerrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_timerrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_timerrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}

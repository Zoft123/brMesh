package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import cn.com.broadlink.blelight.BLSBleLight;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarTimer;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.AESOperator;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class OnekeyResetActivity extends FatherActivity {
    private int delayTime = 1000;
    private int executeCount;
    String jsonStr;
    Dialog loadingDialog;
    private Timer mTimer;
    Realm realm;
    TextView titleView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_onekey_reset);
        reset();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    private void reset() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        String stringExtra = getIntent().getStringExtra(GlobalVariable.toImportCtrl);
        this.jsonStr = stringExtra;
        if (stringExtra != null) {
            TextView textView = (TextView) findViewById(R.id.reset_title);
            this.titleView = textView;
            textView.setVisibility(4);
            this.executeCount = 0;
            this.delayTime = DisDoubleClickListener.MIN_CLICK_DELAY_TIME;
            startTimer();
            try {
                handleImportData((HashMap) JSON.parseObject(AESOperator.getInstance().decrypt(this.jsonStr), HashMap.class));
                return;
            } catch (Exception e) {
                Log.d("AESOperator", "解密字符串出错：" + e);
                return;
            }
        }
        this.delayTime = 2000;
        startTimer();
        BLSBleLight.removeAllDevice();
        BLSBleLight.removeAllRoomScenes();
        String lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
        BLSBleLight.setBLEControlKey(lowerCase);
        this.realm.beginTransaction();
        if (Tool.isToRadar()) {
            this.realm.where(RadarTimer.class).findAll().deleteAllFromRealm();
            this.realm.where(RadarGroup.class).findAll().deleteAllFromRealm();
            this.realm.where(DeleteRadarGroup.class).findAll().deleteAllFromRealm();
            this.realm.where(RadarDevice.class).findAll().deleteAllFromRealm();
            RadarPhoneType radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
            if (radarPhoneType != null) {
                radarPhoneType.setPhoneType(3);
            }
            if (((RadarGroup) this.realm.where(RadarGroup.class).equalTo("fixedId", (Integer) 0).findFirst()) == null) {
                RadarGroup radarGroup = (RadarGroup) this.realm.createObject(RadarGroup.class);
                radarGroup.setFixedId(0);
                radarGroup.setFixedName(getString(R.string.GroupAll));
                radarGroup.setIndex(0);
            }
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.radarLightnessValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.radarColorTempValue);
            SharePreferenceUtils.put(this, GlobalVariable.RADAR_CTRLKEY_TAG, lowerCase);
        } else {
            this.realm.where(com.brgd.brblmesh.GeneralAdapter.model.Timer.class).findAll().deleteAllFromRealm();
            this.realm.where(SceneDevice.class).findAll().deleteAllFromRealm();
            RealmResults<Scene> realmResultsFindAll = this.realm.where(Scene.class).findAll();
            for (Scene scene : realmResultsFindAll) {
                if (scene.getSceneFileName() != null) {
                    Tool.deleteFile(this, scene.getSceneFileName() + GlobalVariable.sceneImgFileName);
                }
            }
            realmResultsFindAll.deleteAllFromRealm();
            this.realm.where(Groups.class).findAll().deleteAllFromRealm();
            this.realm.where(DeleteGroups.class).findAll().deleteAllFromRealm();
            RealmResults<FixedGroup> realmResultsFindAll2 = this.realm.where(FixedGroup.class).findAll();
            RealmResults realmResultsFindAll3 = this.realm.where(DeleteFixedGroup.class).findAll();
            for (FixedGroup fixedGroup : realmResultsFindAll2) {
                if (fixedGroup.getFileName() != null) {
                    Tool.deleteFile(this, fixedGroup.getFileName() + GlobalVariable.sceneImgFileName);
                }
            }
            realmResultsFindAll2.deleteAllFromRealm();
            realmResultsFindAll3.deleteAllFromRealm();
            this.realm.where(Mod.class).notEqualTo(GlobalVariable.addr, (Integer) 0).findAll().deleteAllFromRealm();
            this.realm.where(BleDevice.class).findAll().deleteAllFromRealm();
            PhoneType phoneType = (PhoneType) this.realm.where(PhoneType.class).findFirst();
            if (phoneType != null) {
                phoneType.setPhoneType(3);
            }
            if (((Groups) this.realm.where(Groups.class).equalTo(GlobalVariable.groupId, (Integer) 0).findFirst()) == null) {
                Groups groups = (Groups) this.realm.createObject(Groups.class);
                groups.setGroupId(0);
                groups.setGroupName(getString(R.string.GroupAll));
                groups.setIndex(0);
            }
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.colorValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.lightnessValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.colorTempValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.bindKey);
            SharePreferenceUtils.put(this, GlobalVariable.ctrlKey, lowerCase);
        }
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.showAlexaGuide, false);
        this.realm.commitTransaction();
    }

    private void startTimer() {
        stopTimer();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Intent intent;
                if (OnekeyResetActivity.this.jsonStr != null) {
                    if (OnekeyResetActivity.this.executeCount == 16) {
                        loadDialogUtils.closeDialog(OnekeyResetActivity.this.loadingDialog);
                        OnekeyResetActivity.this.loadingDialog = null;
                        OnekeyResetActivity.this.stopTimer();
                        OnekeyResetActivity.this.startActivity(new Intent(OnekeyResetActivity.this, (Class<?>) MainActivity.class));
                        OnekeyResetActivity.this.finish();
                        return;
                    }
                    return;
                }
                loadDialogUtils.closeDialog(OnekeyResetActivity.this.loadingDialog);
                OnekeyResetActivity.this.loadingDialog = null;
                OnekeyResetActivity.this.stopTimer();
                if (Tool.isToRadar()) {
                    intent = new Intent(OnekeyResetActivity.this, (Class<?>) RadarMainActivity.class);
                } else {
                    intent = new Intent(OnekeyResetActivity.this, (Class<?>) MainActivity.class);
                }
                OnekeyResetActivity.this.startActivity(intent);
                OnekeyResetActivity.this.finish();
            }
        }, this.delayTime);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    private void handleImportData(HashMap<String, Object> map) {
        clearAllAction();
        String str = (String) map.get(GlobalVariable.R_CTRLKEY);
        if (str != null) {
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, str);
        }
        String str2 = (String) map.get(GlobalVariable.CTRLKEY);
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.ctrlKey, str2);
        if (Tool.isToRadar()) {
            BLSBleLight.setBLEControlKey(str);
        } else {
            BLSBleLight.setBLEControlKey(str2);
        }
        BLSBleLight.removeAllDevice();
        BLSBleLight.removeAllRoomScenes();
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.resetScene, GlobalVariable.resetScene);
        setAllData(map);
    }

    private void clearAllAction() {
        this.realm.beginTransaction();
        this.realm.where(RadarDevice.class).findAll().deleteAllFromRealm();
        this.realm.where(RadarGroup.class).findAll().deleteAllFromRealm();
        this.realm.where(DeleteRadarGroup.class).findAll().deleteAllFromRealm();
        this.realm.where(RadarTimer.class).findAll().deleteAllFromRealm();
        RadarPhoneType radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
        if (radarPhoneType != null) {
            radarPhoneType.setPhoneType(3);
        }
        this.realm.where(Groups.class).findAll().deleteAllFromRealm();
        this.realm.where(BleDevice.class).findAll().deleteAllFromRealm();
        this.realm.where(com.brgd.brblmesh.GeneralAdapter.model.Timer.class).findAll().deleteAllFromRealm();
        this.realm.where(DiyColor.class).notEqualTo("colorIdentifier", GlobalVariable.myColor).notEqualTo("colorIdentifier", GlobalVariable.myColorTemp).notEqualTo("colorIdentifier", GlobalVariable.modDefaultColor).findAll().deleteAllFromRealm();
        this.realm.where(ModDiyColor.class).findAll().deleteAllFromRealm();
        this.realm.where(Mod.class).findAll().deleteAllFromRealm();
        this.realm.where(SceneDevice.class).findAll().deleteAllFromRealm();
        RealmResults<Scene> realmResultsFindAll = this.realm.where(Scene.class).findAll();
        for (Scene scene : realmResultsFindAll) {
            if (scene.getSceneFileName() != null) {
                Tool.deleteFile(this, scene.getSceneFileName() + GlobalVariable.sceneImgFileName);
            }
        }
        realmResultsFindAll.deleteAllFromRealm();
        this.realm.where(DeleteGroups.class).findAll().deleteAllFromRealm();
        RealmResults<FixedGroup> realmResultsFindAll2 = this.realm.where(FixedGroup.class).findAll();
        for (FixedGroup fixedGroup : realmResultsFindAll2) {
            if (fixedGroup.getFileName() != null) {
                Tool.deleteFile(this, fixedGroup.getFileName() + GlobalVariable.sceneImgFileName);
            }
        }
        realmResultsFindAll2.deleteAllFromRealm();
        this.realm.where(DeleteFixedGroup.class).findAll().deleteAllFromRealm();
        PhoneType phoneType = (PhoneType) this.realm.where(PhoneType.class).findFirst();
        if (phoneType != null) {
            phoneType.setPhoneType(3);
        }
        this.realm.commitTransaction();
    }

    private void setAllData(HashMap<String, Object> map) {
        setRadarDevices(map);
        setRadarGroups(map);
        setDeleteRadarGroups(map);
        setRadarTimers(map);
        setRadarPhoneType(map);
        setGroups(map);
        setBleDevices(map);
        setDiyColors(map);
        setModDiyColors(map);
        setMods(map);
        setSceneDevices(map);
        setScenes(map);
        setPhoneType(map);
        setDeleteGroups(map);
        setFixGroups(map);
        setDeleteFixGroups(map);
    }

    private void setRadarDevices(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.RADAR_DV);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda32
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setRadarDevices$0(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda33
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setRadarDevices$1();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setRadarDevices$0(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            RadarDevice radarDevice = (RadarDevice) realm.createObject(RadarDevice.class);
            radarDevice.setDid((String) jSONObject.get(GlobalVariable.DID));
            radarDevice.setName((String) jSONObject.get(GlobalVariable.NAME));
            radarDevice.setKey((String) jSONObject.get(GlobalVariable.KEY));
            radarDevice.setVersion((String) jSONObject.get(GlobalVariable.VERSION));
            radarDevice.setAddr(((Integer) jSONObject.get(GlobalVariable.ADDR)).intValue());
            radarDevice.setType(((Integer) jSONObject.get(GlobalVariable.TYPE)).intValue());
            radarDevice.setGroupId(((Integer) jSONObject.get(GlobalVariable.GROUPID)).intValue());
            radarDevice.setIndex(((Integer) jSONObject.get(GlobalVariable.INDEX)).intValue());
            radarDevice.setSupportAlexaEnable(((Boolean) jSONObject.get("isSupportAlexaEnable")).booleanValue());
            radarDevice.setConfigAlexa(((Boolean) jSONObject.get("isConfigAlexa")).booleanValue());
            radarDevice.setSupportFixedGroup(((Boolean) jSONObject.get("isSupportFixedGroup")).booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRadarDevices$1() {
        this.executeCount++;
    }

    private void setRadarGroups(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.RADAR_GP);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda30
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setRadarGroups$2(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda31
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setRadarGroups$3();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setRadarGroups$2(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            RadarGroup radarGroup = (RadarGroup) realm.createObject(RadarGroup.class);
            radarGroup.setFixedId(((Integer) jSONObject.get("fixedId")).intValue());
            radarGroup.setFixedName((String) jSONObject.get(GlobalVariable.FIXEDNAME));
            radarGroup.setIndex(((Integer) jSONObject.get(GlobalVariable.INDEX)).intValue());
            Iterator<Object> it2 = ((JSONArray) Objects.requireNonNull((JSONArray) jSONObject.get(GlobalVariable.BLEDVARR))).iterator();
            while (it2.hasNext()) {
                RadarDevice radarDevice = (RadarDevice) realm.where(RadarDevice.class).equalTo(GlobalVariable.did, (String) ((JSONObject) it2.next()).get(GlobalVariable.DID)).findFirst();
                if (radarDevice != null) {
                    radarGroup.getBleDeviceRealmList().add(radarDevice);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRadarGroups$3() {
        this.executeCount++;
    }

    private void setDeleteRadarGroups(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.D_RADAR_G);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda7
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setDeleteRadarGroups$4(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda8
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setDeleteRadarGroups$5();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setDeleteRadarGroups$4(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            ((DeleteRadarGroup) realm.createObject(DeleteRadarGroup.class)).setFixedId(((Integer) ((JSONObject) it.next()).get("fixedId")).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeleteRadarGroups$5() {
        this.executeCount++;
    }

    private void setRadarTimers(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.RADAR_T);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda25
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setRadarTimers$6(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda26
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setRadarTimers$7();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setRadarTimers$6(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            RadarTimer radarTimer = (RadarTimer) realm.createObject(RadarTimer.class);
            radarTimer.setIndexNum(((Integer) jSONObject.get("indexNum")).intValue());
            radarTimer.setHour1(((Integer) jSONObject.get(GlobalVariable.HOUR1)).intValue());
            radarTimer.setMin1(((Integer) jSONObject.get(GlobalVariable.MIN1)).intValue());
            radarTimer.setHour2(((Integer) jSONObject.get(GlobalVariable.HOUR2)).intValue());
            radarTimer.setMin2(((Integer) jSONObject.get(GlobalVariable.MIN2)).intValue());
            radarTimer.setEnable(((Boolean) jSONObject.get(GlobalVariable.ISENABLE)).booleanValue());
            radarTimer.setOut_bri(((Integer) jSONObject.get(GlobalVariable.OUT_BRI)).intValue());
            RadarDevice radarDevice = (RadarDevice) realm.where(RadarDevice.class).equalTo(GlobalVariable.did, (String) jSONObject.get(GlobalVariable.DID)).findFirst();
            if (radarDevice != null) {
                radarTimer.setAddr(radarDevice.getAddr());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRadarTimers$7() {
        this.executeCount++;
    }

    private void setRadarPhoneType(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.RADAR_P);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda20
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setRadarPhoneType$8(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda21
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setRadarPhoneType$9();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setRadarPhoneType$8(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            RadarPhoneType radarPhoneType = (RadarPhoneType) realm.where(RadarPhoneType.class).findFirst();
            if (radarPhoneType != null) {
                radarPhoneType.setPhoneType(((Integer) jSONObject.get(GlobalVariable.P_TYPE)).intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRadarPhoneType$9() {
        this.executeCount++;
    }

    private void setGroups(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.GROUP);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda3
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setGroups$10(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda4
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setGroups$11();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setGroups$10(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            Groups groups = (Groups) realm.createObject(Groups.class);
            groups.setGroupId(((Integer) jSONObject.get(GlobalVariable.GROUPID)).intValue());
            groups.setGroupName((String) jSONObject.get(GlobalVariable.G_NAME));
            groups.setIndex(((Integer) jSONObject.get(GlobalVariable.INDEX)).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroups$11() {
        this.executeCount++;
    }

    private void setBleDevices(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.BLEDV);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda14
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setBleDevices$12(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda15
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setBleDevices$13();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setBleDevices$12(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            BleDevice bleDevice = (BleDevice) realm.createObject(BleDevice.class);
            bleDevice.setDid((String) jSONObject.get(GlobalVariable.DID));
            bleDevice.setName((String) jSONObject.get(GlobalVariable.NAME));
            bleDevice.setKey((String) jSONObject.get(GlobalVariable.KEY));
            bleDevice.setVersion((String) jSONObject.get(GlobalVariable.VERSION));
            bleDevice.setAddr(((Integer) jSONObject.get(GlobalVariable.ADDR)).intValue());
            bleDevice.setType(((Integer) jSONObject.get(GlobalVariable.TYPE)).intValue());
            bleDevice.setGroupId(((Integer) jSONObject.get(GlobalVariable.GROUPID)).intValue());
            bleDevice.setIndex(((Integer) jSONObject.get(GlobalVariable.INDEX)).intValue());
            bleDevice.setGroupIndex(((Integer) jSONObject.get("groupIndex")).intValue());
            if (jSONObject.containsKey("isSupportAlexaEnable")) {
                bleDevice.setSupportAlexaEnable(((Boolean) jSONObject.get("isSupportAlexaEnable")).booleanValue());
            }
            if (jSONObject.containsKey("isConfigAlexa")) {
                bleDevice.setConfigAlexa(((Boolean) jSONObject.get("isConfigAlexa")).booleanValue());
            }
            if (jSONObject.containsKey("isSupportFixedGroup")) {
                bleDevice.setSupportFixedGroup(((Boolean) jSONObject.get("isSupportFixedGroup")).booleanValue());
            }
            if (jSONObject.containsKey("isSupportGroupMain")) {
                bleDevice.setSupportGroupMain(((Boolean) jSONObject.get("isSupportGroupMain")).booleanValue());
            }
            if (jSONObject.containsKey(GlobalVariable.BEENMAIN)) {
                bleDevice.setBeenMain(((Boolean) jSONObject.get(GlobalVariable.BEENMAIN)).booleanValue());
            }
            if (jSONObject.containsKey("fixedId")) {
                bleDevice.setFixedId(((Integer) jSONObject.get("fixedId")).intValue());
            }
            if (jSONObject.containsKey(GlobalVariable.S_T_STATUS)) {
                bleDevice.setSupportTimerStatus(((Boolean) jSONObject.get(GlobalVariable.S_T_STATUS)).booleanValue());
            }
            if (jSONObject.containsKey(GlobalVariable.S_SCENE_CTRL)) {
                bleDevice.setSupportSceneControl(((Boolean) jSONObject.get(GlobalVariable.S_SCENE_CTRL)).booleanValue());
            }
            if (jSONObject.containsKey("otaTag")) {
                bleDevice.setOtaTag(((Integer) jSONObject.get("otaTag")).intValue());
            }
            if (jSONObject.containsKey(GlobalVariable.S_MODE_LIGHTNESS)) {
                bleDevice.setSupportModeLightness(((Boolean) jSONObject.get(GlobalVariable.S_MODE_LIGHTNESS)).booleanValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBleDevices$13() {
        this.executeCount++;
    }

    private void setTimers(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get("Timer");
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda5
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setTimers$14(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda6
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setTimers$15();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setTimers$14(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            com.brgd.brblmesh.GeneralAdapter.model.Timer timer = (com.brgd.brblmesh.GeneralAdapter.model.Timer) realm.createObject(com.brgd.brblmesh.GeneralAdapter.model.Timer.class);
            timer.setIndexNum(((Integer) jSONObject.get("indexNum")).intValue());
            timer.setHour(((Integer) jSONObject.get(GlobalVariable.HOUR)).intValue());
            timer.setMinute(((Integer) jSONObject.get(GlobalVariable.MINUTE)).intValue());
            timer.setAction(((Integer) jSONObject.get(GlobalVariable.ACTION)).intValue());
            timer.setRepeat(((Boolean) jSONObject.get(GlobalVariable.ISREPEAT)).booleanValue());
            timer.setEnable(((Boolean) jSONObject.get(GlobalVariable.ISENABLE)).booleanValue());
            BleDevice bleDevice = (BleDevice) realm.where(BleDevice.class).equalTo(GlobalVariable.did, (String) jSONObject.get(GlobalVariable.DID)).findFirst();
            if (bleDevice != null) {
                timer.setAddr(bleDevice.getAddr());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTimers$15() {
        this.executeCount++;
    }

    private void setDiyColors(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.DIYC);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda28
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setDiyColors$16(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda29
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setDiyColors$17();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setDiyColors$16(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            DiyColor diyColor = (DiyColor) realm.createObject(DiyColor.class);
            String str = (String) jSONObject.get("colorIdentifier");
            if (Objects.equals(str, "my")) {
                str = GlobalVariable.myColor;
            } else if (Objects.equals(str, "diy")) {
                str = "DiyColor";
            } else if (Objects.equals(str, "myColorTemp")) {
                str = GlobalVariable.myColorTemp;
            } else if (Objects.equals(str, "diyColorTemp")) {
                str = GlobalVariable.diyColorTemp;
            } else if (Objects.equals(str, "default")) {
                str = GlobalVariable.modDefaultColor;
            }
            String str2 = (String) jSONObject.get(GlobalVariable.COLORSTR);
            if (Objects.equals(str2, GlobalVariable.nullColor)) {
                diyColor.setColorValue(-2);
            } else if (Objects.equals(str2, GlobalVariable.WHITE_HEX)) {
                diyColor.setColorValue(-3);
            } else if (Objects.equals(str, GlobalVariable.diyColorTemp)) {
                diyColor.setColorValue(Integer.parseInt((String) Objects.requireNonNull(str2)));
            } else {
                diyColor.setColorValue(Tool.hexStrToColor(str2));
            }
            if (!Objects.equals(str, GlobalVariable.diyColorTemp)) {
                String str3 = (String) jSONObject.get(GlobalVariable.COLORSTRR);
                if (Objects.equals(str3, GlobalVariable.nullColor)) {
                    diyColor.setDiyColorR(-2);
                } else if (Objects.equals(str3, GlobalVariable.WHITE_HEX)) {
                    diyColor.setDiyColorR(-3);
                } else {
                    diyColor.setDiyColorR(Tool.hexStrToColor(str3));
                }
            }
            diyColor.setColorIdentifier(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDiyColors$17() {
        this.executeCount++;
    }

    private void setModDiyColors(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.MODDIYC);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda23
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setModDiyColors$18(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda24
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setModDiyColors$19();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setModDiyColors$18(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            ModDiyColor modDiyColor = (ModDiyColor) realm.createObject(ModDiyColor.class);
            modDiyColor.setModNumber(((Integer) jSONObject.get(GlobalVariable.MODNUM)).intValue());
            modDiyColor.setColorIndex(((Integer) jSONObject.get(GlobalVariable.COLORINDEX)).intValue());
            String str = (String) jSONObject.get(GlobalVariable.customModId);
            if (str != null) {
                modDiyColor.setCustomModId(str);
            }
            String str2 = (String) jSONObject.get(GlobalVariable.COLORSTR);
            if (Objects.equals(str2, GlobalVariable.nullColor)) {
                modDiyColor.setDiyColor(-2);
            } else if (Objects.equals(str2, GlobalVariable.WHITE_HEX)) {
                modDiyColor.setDiyColor(-3);
            } else {
                modDiyColor.setDiyColor(Tool.hexStrToColor(str2));
            }
            String str3 = (String) jSONObject.get(GlobalVariable.COLORSTRR);
            if (Objects.equals(str3, GlobalVariable.nullColor)) {
                modDiyColor.setDiyColorR(-2);
            } else if (Objects.equals(str3, GlobalVariable.WHITE_HEX)) {
                modDiyColor.setDiyColorR(-3);
            } else {
                modDiyColor.setDiyColorR(Tool.hexStrToColor(str3));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setModDiyColors$19() {
        this.executeCount++;
    }

    private void setMods(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get("Mod");
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda0
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setMods$20(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda11
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setMods$21();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setMods$20(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            Mod mod = (Mod) realm.createObject(Mod.class);
            mod.setModNumber(((Integer) jSONObject.get(GlobalVariable.MODNUM)).intValue());
            mod.setSpeed(((Integer) jSONObject.get(GlobalVariable.SPEED)).intValue());
            mod.setAddr(((Integer) jSONObject.get(GlobalVariable.ADDR)).intValue());
            mod.setStartBrightness(((Integer) jSONObject.get(GlobalVariable.STARTBRI)).intValue());
            mod.setEndBrightness(((Integer) jSONObject.get(GlobalVariable.ENDBRI)).intValue());
            mod.setMinute(((Integer) jSONObject.get(GlobalVariable.MINUTE)).intValue());
            String str = (String) jSONObject.get(GlobalVariable.COLORSTR);
            if (Objects.equals(str, GlobalVariable.nullColor)) {
                mod.setStateValue(-2);
            } else if (Objects.equals(str, GlobalVariable.WHITE_HEX)) {
                mod.setStateValue(-3);
            } else {
                mod.setStateValue(Tool.hexStrToColor(str));
            }
            mod.setWarmValue(((Integer) jSONObject.get(GlobalVariable.WARMVALUE)).intValue());
            mod.setSleep(((Integer) jSONObject.get("isSleep")).intValue() == 1);
            String str2 = (String) jSONObject.get(GlobalVariable.customModId);
            if (str2 != null) {
                mod.setFlash(((Boolean) jSONObject.get(GlobalVariable.ISFLASH)).booleanValue());
                mod.setCustomModId(str2);
                mod.setCustomModName((String) jSONObject.get(GlobalVariable.CUSTOMMODNAME));
            }
            if (jSONObject.containsKey(GlobalVariable.BRIGHTNESS)) {
                mod.setBrightness(((Integer) jSONObject.get(GlobalVariable.BRIGHTNESS)).intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMods$21() {
        this.executeCount++;
    }

    private void setSceneDevices(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.SCENEDV);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda9
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setSceneDevices$22(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda10
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setSceneDevices$23();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setSceneDevices$22(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            SceneDevice sceneDevice = (SceneDevice) realm.createObject(SceneDevice.class);
            sceneDevice.setDid((String) jSONObject.get(GlobalVariable.DID));
            sceneDevice.setSceneNumber(((Integer) jSONObject.get(GlobalVariable.SCENENUM)).intValue());
            String str = (String) jSONObject.get(GlobalVariable.COLORSTR);
            if (Objects.equals(str, GlobalVariable.nullColor)) {
                sceneDevice.setColor(-2);
            } else if (Objects.equals(str, GlobalVariable.WHITE_HEX)) {
                sceneDevice.setColor(-3);
            } else {
                sceneDevice.setColor(Tool.hexStrToColor(str));
            }
            sceneDevice.setLightness(((Integer) jSONObject.get(GlobalVariable.LIGHTNESS)).intValue());
            sceneDevice.setTemp(((Integer) jSONObject.get(GlobalVariable.TEMP)).intValue());
            sceneDevice.setModNumber(((Integer) jSONObject.get(GlobalVariable.MODNUM)).intValue());
            sceneDevice.setModSpeed(((Integer) jSONObject.get(GlobalVariable.MODSPEED)).intValue());
            String str2 = (String) jSONObject.get(GlobalVariable.customModId);
            if (str2 != null) {
                sceneDevice.setCustomModId(str2);
            }
            if (jSONObject.containsKey(GlobalVariable.MOD_BRIGHTNESS)) {
                sceneDevice.setModBrightness(((Integer) jSONObject.get(GlobalVariable.MOD_BRIGHTNESS)).intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSceneDevices$23() {
        this.executeCount++;
    }

    private void setScenes(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get("Scene");
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda16
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setScenes$24(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda17
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setScenes$25();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setScenes$24(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            Scene scene = (Scene) realm.createObject(Scene.class);
            scene.setIndex(((Integer) jSONObject.get(GlobalVariable.INDEX)).intValue());
            scene.setSceneNumber(((Integer) jSONObject.get(GlobalVariable.SCENENUM)).intValue());
            scene.setSceneName((String) jSONObject.get(GlobalVariable.SCENENAME));
            scene.setIconIndex(((Integer) jSONObject.get(GlobalVariable.ICONINDEX)).intValue());
            Iterator<Object> it2 = ((JSONArray) Objects.requireNonNull((JSONArray) jSONObject.get(GlobalVariable.SCENEDVARR))).iterator();
            while (it2.hasNext()) {
                SceneDevice sceneDevice = (SceneDevice) realm.where(SceneDevice.class).equalTo(GlobalVariable.did, (String) ((JSONObject) it2.next()).get(GlobalVariable.DID)).findFirst();
                if (sceneDevice != null) {
                    scene.getSceneDeviceRealmList().add(sceneDevice);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScenes$25() {
        this.executeCount++;
    }

    private void setPhoneType(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.PHONE_T);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda18
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setPhoneType$26(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda19
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setPhoneType$27();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setPhoneType$26(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            ((PhoneType) realm.where(PhoneType.class).findFirst()).setPhoneType(((Integer) ((JSONObject) it.next()).get(GlobalVariable.P_TYPE)).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPhoneType$27() {
        this.executeCount++;
    }

    private void setDeleteGroups(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.D_GROUP);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda1
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setDeleteGroups$28(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda2
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setDeleteGroups$29();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setDeleteGroups$28(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            ((DeleteGroups) realm.createObject(DeleteGroups.class)).setGroupId(((Integer) ((JSONObject) it.next()).get(GlobalVariable.GROUPID)).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeleteGroups$29() {
        this.executeCount++;
    }

    private void setFixGroups(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.FIXGROUP);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda22
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setFixGroups$30(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda27
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setFixGroups$31();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setFixGroups$30(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            FixedGroup fixedGroup = (FixedGroup) realm.createObject(FixedGroup.class);
            fixedGroup.setFixedId(((Integer) jSONObject.get("fixedId")).intValue());
            fixedGroup.setFixedName((String) jSONObject.get(GlobalVariable.FIXEDNAME));
            fixedGroup.setIndex(((Integer) jSONObject.get(GlobalVariable.INDEX)).intValue());
            fixedGroup.setIconIndex(((Integer) jSONObject.get(GlobalVariable.ICONINDEX)).intValue());
            Iterator<Object> it2 = ((JSONArray) Objects.requireNonNull((JSONArray) jSONObject.get(GlobalVariable.BLEDVARR))).iterator();
            while (it2.hasNext()) {
                BleDevice bleDevice = (BleDevice) realm.where(BleDevice.class).equalTo(GlobalVariable.did, (String) ((JSONObject) it2.next()).get(GlobalVariable.DID)).findFirst();
                if (bleDevice != null) {
                    fixedGroup.getBleDeviceRealmList().add(bleDevice);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFixGroups$31() {
        this.executeCount++;
    }

    private void setDeleteFixGroups(HashMap<String, Object> map) {
        final JSONArray jSONArray = (JSONArray) map.get(GlobalVariable.D_FIXGROUP);
        if (jSONArray == null || jSONArray.isEmpty()) {
            this.executeCount++;
        } else {
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda12
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    OnekeyResetActivity.lambda$setDeleteFixGroups$32(jSONArray, realm);
                }
            }, new Realm.Transaction.OnSuccess() { // from class: com.brgd.brblmesh.Main.Activity.OnekeyResetActivity$$ExternalSyntheticLambda13
                @Override // io.realm.Realm.Transaction.OnSuccess
                public final void onSuccess() {
                    this.f$0.lambda$setDeleteFixGroups$33();
                }
            });
        }
    }

    static /* synthetic */ void lambda$setDeleteFixGroups$32(JSONArray jSONArray, Realm realm) {
        Iterator<Object> it = jSONArray.iterator();
        while (it.hasNext()) {
            ((DeleteFixedGroup) realm.createObject(DeleteFixedGroup.class)).setFixedId(((Integer) ((JSONObject) it.next()).get("fixedId")).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeleteFixGroups$33() {
        this.executeCount++;
    }
}

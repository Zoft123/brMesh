package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarTimer;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import com.google.zxing.Result;
import com.king.camera.scan.AnalyzeResult;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.analyze.Analyzer;
import com.king.zxing.BarcodeCameraScanActivity;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import com.king.zxing.analyze.MultiFormatAnalyzer;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class CustomCaptureActivity extends BarcodeCameraScanActivity implements View.OnClickListener {
    ImageView backView;
    private PhoneType phoneType;
    private RadarPhoneType radarPhoneType;
    private Realm realm;
    private final int GET_SCAN_RESULT = 101;
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.king.camera.scan.BaseCameraScanActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        enableEdgeToEdge();
        super.onCreate(bundle);
        Realm.init(getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        this.radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
        ImageView imageView = (ImageView) findViewById(R.id.ivLeft);
        this.backView = imageView;
        imageView.setOnClickListener(this);
    }

    private void enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsControllerCompat.show(WindowInsetsCompat.Type.statusBars());
        windowInsetsControllerCompat.setSystemBarsBehavior(2);
        windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
    }

    @Override // com.king.camera.scan.BaseCameraScanActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    @Override // com.king.camera.scan.BaseCameraScanActivity
    public void initCameraScan(CameraScan<Result> cameraScan) {
        super.initCameraScan(cameraScan);
        cameraScan.setPlayBeep(true);
    }

    @Override // com.king.zxing.BarcodeCameraScanActivity, com.king.camera.scan.BaseCameraScanActivity
    public Analyzer<Result> createAnalyzer() {
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(DecodeFormatManager.QR_CODE_HINTS).setFullAreaScan(false).setAreaRectRatio(0.8f).setAreaRectVerticalOffset(0).setAreaRectHorizontalOffset(0);
        return new MultiFormatAnalyzer(decodeConfig);
    }

    @Override // com.king.zxing.BarcodeCameraScanActivity, com.king.camera.scan.BaseCameraScanActivity
    public int getLayoutId() {
        return R.layout.custom_capture_activity;
    }

    @Override // com.king.camera.scan.CameraScan.OnScanResultCallback
    public void onScanResultCallback(AnalyzeResult<Result> analyzeResult) {
        getCameraScan().setAnalyzeImage(false);
        this.myHandler.sendMessage(this.myHandler.obtainMessage(101, analyzeResult.getResult().getText()));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        finish();
    }

    private void reset() {
        this.realm.beginTransaction();
        if (Tool.isToRadar()) {
            this.realm.where(RadarTimer.class).findAll().deleteAllFromRealm();
            this.realm.where(RadarDevice.class).findAll().deleteAllFromRealm();
            this.realm.where(RadarGroup.class).notEqualTo("fixedId", (Integer) 0).findAll().deleteAllFromRealm();
            this.realm.where(DeleteRadarGroup.class).findAll().deleteAllFromRealm();
            this.radarPhoneType.setPhoneType(3);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.radarLightnessValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.radarColorTempValue);
        } else {
            this.realm.where(Timer.class).findAll().deleteAllFromRealm();
            this.realm.where(SceneDevice.class).findAll().deleteAllFromRealm();
            this.realm.where(Scene.class).findAll().deleteAllFromRealm();
            this.realm.where(BleDevice.class).findAll().deleteAllFromRealm();
            this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).findAll().deleteAllFromRealm();
            this.realm.where(DeleteGroups.class).findAll().deleteAllFromRealm();
            RealmResults realmResultsFindAll = this.realm.where(FixedGroup.class).findAll();
            RealmResults realmResultsFindAll2 = this.realm.where(DeleteFixedGroup.class).findAll();
            realmResultsFindAll.deleteAllFromRealm();
            realmResultsFindAll2.deleteAllFromRealm();
            this.phoneType.setPhoneType(3);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.colorValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.lightnessValue);
            SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.colorTempValue);
        }
        this.realm.commitTransaction();
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.showAlexaGuide, false);
        BLSBleLight.removeAllDevice();
        BLSBleLight.removeAllRoomScenes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0248 A[Catch: JSONException -> 0x02c7, TryCatch #3 {JSONException -> 0x02c7, blocks: (B:87:0x01d5, B:89:0x01e0, B:91:0x01e9, B:93:0x01f0, B:95:0x01f6, B:113:0x023e, B:115:0x0248, B:117:0x024e), top: B:141:0x01d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0276 A[Catch: JSONException -> 0x02c3, TryCatch #0 {JSONException -> 0x02c3, blocks: (B:119:0x025b, B:121:0x0276, B:122:0x0280, B:124:0x02a1), top: B:135:0x025b }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02a1 A[Catch: JSONException -> 0x02c3, TRY_LEAVE, TryCatch #0 {JSONException -> 0x02c3, blocks: (B:119:0x025b, B:121:0x0276, B:122:0x0280, B:124:0x02a1), top: B:135:0x025b }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02b9 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleShareMessage(java.lang.String r23) {
        /*
            Method dump skipped, instruction units count: 753
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.brgd.brblmesh.Main.Activity.CustomCaptureActivity.handleShareMessage(java.lang.String):void");
    }

    public static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            CustomCaptureActivity customCaptureActivity = (CustomCaptureActivity) this.weakReference.get();
            int i = message.what;
            Objects.requireNonNull(customCaptureActivity);
            if (i == 101) {
                customCaptureActivity.handleShareMessage((String) message.obj);
            }
        }
    }
}

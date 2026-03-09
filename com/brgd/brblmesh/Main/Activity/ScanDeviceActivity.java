package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.helper.BLEFastconHelper;
import cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback;
import cn.com.broadlink.blelight.interfaces.OnDevScanCallback;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.ScanConfigAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.MyDevWrapperBean;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalThreadPools;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.concurrent.ConcurrentHashMap;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ScanDeviceActivity extends FatherActivity {
    public static final int SAVE_DEVICE = 101;
    private int AllBleDeviceCount;
    TextView actionBarBg;
    ImageView backBtn;
    private ConstraintLayout configLayout;
    private ImageView configView;
    private ConstraintLayout configurationLayout;
    private ImageView confirmBtn;
    private TextView confirmTextView;
    TextView finishBtn;
    private boolean isReceive;
    private Timer mTimer;
    private PhoneType phoneType;
    private int provisionDoneCount;
    private RadarPhoneType radarPhoneType;
    private Realm realm;
    private ScanConfigAdapter scanConfigAdapter;
    RecyclerView scanListView;
    private Timer scanTimer;
    private TextView searchProgressText;
    TextView searchTipText;
    private Timer updateListTimer;
    ImageView video;
    private final CopyOnWriteArrayList<MyDevWrapperBean> scanBleList = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, MyDevWrapperBean> deviceMap = new ConcurrentHashMap<>();
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.search_finish || id == R.id.search_back) {
                ScanDeviceActivity.this.back();
                return;
            }
            if (id == R.id.search_video) {
                ScanDeviceActivity.this.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/FoJ_2-KVDFg")), "Browse"));
                return;
            }
            if (id == R.id.search_confirm_text) {
                if (ScanDeviceActivity.this.confirmBtn.isSelected()) {
                    ScanDeviceActivity.this.confirmBtn.setSelected(false);
                    ScanDeviceActivity.this.confirmTextView.setTextColor(ContextCompat.getColor(ScanDeviceActivity.this.getApplicationContext(), R.color.colorConfirmGray));
                    ScanDeviceActivity.this.configView.setSelected(false);
                    return;
                } else {
                    ScanDeviceActivity.this.confirmBtn.setSelected(true);
                    ScanDeviceActivity.this.confirmTextView.setTextColor(ContextCompat.getColor(ScanDeviceActivity.this.getApplicationContext(), R.color.colorSelect));
                    ScanDeviceActivity.this.configView.setSelected(true);
                    return;
                }
            }
            if (id == R.id.search_config_add) {
                if (ScanDeviceActivity.this.configView.isSelected()) {
                    ScanDeviceActivity.this.actionBarBg.setText(R.string.AddDevices);
                    ScanDeviceActivity.this.video.setVisibility(8);
                    ScanDeviceActivity.this.configLayout.setVisibility(8);
                    ScanDeviceActivity.this.finishBtn.setVisibility(0);
                    ScanDeviceActivity.this.finishBtn.setText(R.string.Finish);
                    ScanDeviceActivity.this.searchTipText.setVisibility(0);
                    ScanDeviceActivity.this.provisionDoneCount = 0;
                    ScanDeviceActivity.this.searchProgressText.setText(ScanDeviceActivity.this.getString(R.string.configProgress) + " " + ScanDeviceActivity.this.provisionDoneCount + "/" + ScanDeviceActivity.this.scanBleList.size());
                    ScanDeviceActivity.this.startConfigAnim();
                    ScanDeviceActivity.this.searchDevices();
                    return;
                }
                GlobalToast.showText(ScanDeviceActivity.this.getApplicationContext(), R.string.confirmLight, 0);
            }
        }
    };
    private final Runnable updateListRunnable = new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity.5
        @Override // java.lang.Runnable
        public void run() {
            ScanDeviceActivity.this.scanConfigAdapter.notifyDataSetChanged();
            ScanDeviceActivity.this.searchProgressText.setText(ScanDeviceActivity.this.getString(R.string.configProgress) + " " + ScanDeviceActivity.this.provisionDoneCount + "/" + ScanDeviceActivity.this.scanBleList.size());
        }
    };
    private final OnDevScanCallback mOnDevScanCallback = new OnDevScanCallback() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity.6
        @Override // cn.com.broadlink.blelight.interfaces.OnDevScanCallback
        public void onCallback(BLEDeviceInfo bLEDeviceInfo) {
            if (ScanDeviceActivity.this.deviceMap.containsKey(bLEDeviceInfo.did) || BLSBleLight.queryDeviceInfoWithDid(bLEDeviceInfo.did) != null || ScanDeviceActivity.this.AllBleDeviceCount + ScanDeviceActivity.this.scanBleList.size() >= 250 || ScanDeviceActivity.this.isMisMatchType(bLEDeviceInfo)) {
                return;
            }
            MyDevWrapperBean myDevWrapperBean = new MyDevWrapperBean(bLEDeviceInfo);
            ScanDeviceActivity.this.scanBleList.add(myDevWrapperBean);
            ScanDeviceActivity.this.deviceMap.put(bLEDeviceInfo.did, myDevWrapperBean);
        }
    };
    final OnDevHeartBeatCallback mHeartbeatCallback = new AnonymousClass7();
    private boolean mIsStop = false;
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_scan_device);
        initView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        handleDestroy();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        this.radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
        this.AllBleDeviceCount = BLSBleLight.queryDeviceAll().size();
        TextView textView = (TextView) findViewById(R.id.search_actionBar_bg);
        this.actionBarBg = textView;
        textView.setText(R.string.AddTeach);
        ImageView imageView = (ImageView) findViewById(R.id.search_video);
        this.video = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView2 = (TextView) findViewById(R.id.search_finish);
        this.finishBtn = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        this.searchTipText = (TextView) findViewById(R.id.search_tip);
        ImageView imageView2 = (ImageView) findViewById(R.id.search_back);
        this.backBtn = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.configLayout = (ConstraintLayout) findViewById(R.id.search_config_layout);
        TextView textView3 = (TextView) findViewById(R.id.search_confirm_text);
        this.confirmTextView = textView3;
        textView3.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView3 = (ImageView) findViewById(R.id.search_confirm_bt);
        this.confirmBtn = imageView3;
        imageView3.setSelected(false);
        ImageView imageView4 = (ImageView) findViewById(R.id.search_config_add);
        this.configView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.configView.setSelected(false);
        this.configurationLayout = (ConstraintLayout) findViewById(R.id.search_loading_layout1);
        this.searchProgressText = (TextView) findViewById(R.id.search_count);
        this.scanListView = (RecyclerView) findViewById(R.id.search_listView);
        ScanConfigAdapter scanConfigAdapter = new ScanConfigAdapter(this, this.scanBleList);
        this.scanConfigAdapter = scanConfigAdapter;
        this.scanListView.setAdapter(scanConfigAdapter);
        this.scanListView.setLayoutManager(new MyLinearLayoutManager(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void back() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startConfigAnim() {
        this.configurationLayout.setVisibility(0);
    }

    private void stopConfigAnim() {
        this.configurationLayout.setVisibility(8);
    }

    private void checkPermission() {
        if (Tool.bleNotOnToast(getApplicationContext())) {
            back();
        }
        if (Tool.checkLocationPermission()) {
            return;
        }
        GlobalToast.showText(getApplicationContext(), R.string.locationAddressRequest, 1);
        back();
    }

    private void handleDestroy() {
        stopSearchDevices();
        stopConfigAnim();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveBleDevice(final MyDevWrapperBean myDevWrapperBean) {
        if (Tool.isToRadar()) {
            this.realm.beginTransaction();
            if (this.radarPhoneType.getPhoneType() == 3) {
                this.radarPhoneType.setPhoneType(1);
            }
            this.realm.commitTransaction();
            this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity$$ExternalSyntheticLambda0
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    this.f$0.lambda$saveBleDevice$0(myDevWrapperBean, realm);
                }
            });
            return;
        }
        this.realm.beginTransaction();
        if (this.phoneType.getPhoneType() == 3) {
            this.phoneType.setPhoneType(1);
        }
        this.realm.commitTransaction();
        this.realm.executeTransactionAsync(new Realm.Transaction() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity$$ExternalSyntheticLambda1
            @Override // io.realm.Realm.Transaction
            public final void execute(Realm realm) {
                this.f$0.lambda$saveBleDevice$1(myDevWrapperBean, realm);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBleDevice$0(MyDevWrapperBean myDevWrapperBean, Realm realm) {
        if (((RadarDevice) realm.where(RadarDevice.class).equalTo(GlobalVariable.did, myDevWrapperBean.deviceInfo.did).findFirst()) == null) {
            int iCount = (int) realm.where(RadarDevice.class).count();
            RadarDevice radarDevice = (RadarDevice) realm.createObject(RadarDevice.class);
            radarDevice.setDid(myDevWrapperBean.deviceInfo.did);
            radarDevice.setAddr(myDevWrapperBean.deviceInfo.addr);
            radarDevice.setGroupId(myDevWrapperBean.deviceInfo.groupId);
            radarDevice.setKey(myDevWrapperBean.deviceInfo.key);
            radarDevice.setName(getString(R.string.Light1) + " " + myDevWrapperBean.deviceInfo.did.substring(8));
            radarDevice.setType(myDevWrapperBean.deviceInfo.type);
            radarDevice.setVersion(myDevWrapperBean.deviceInfo.version);
            radarDevice.setIndex(iCount);
            radarDevice.setSupportAlexaEnable(myDevWrapperBean.deviceInfo.supportAlexaEnable());
            radarDevice.setConfigAlexa(false);
            radarDevice.setSupportFixedGroup(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBleDevice$1(MyDevWrapperBean myDevWrapperBean, Realm realm) {
        if (((BleDevice) realm.where(BleDevice.class).equalTo(GlobalVariable.did, myDevWrapperBean.deviceInfo.did).findFirst()) == null) {
            int iCount = (int) realm.where(BleDevice.class).count();
            BleDevice bleDevice = (BleDevice) realm.createObject(BleDevice.class);
            bleDevice.setDid(myDevWrapperBean.deviceInfo.did);
            bleDevice.setAddr(myDevWrapperBean.deviceInfo.addr);
            bleDevice.setCreateTime(myDevWrapperBean.deviceInfo.createTime);
            bleDevice.setGroupId(myDevWrapperBean.deviceInfo.groupId);
            bleDevice.setKey(myDevWrapperBean.deviceInfo.key);
            bleDevice.setName(getString(R.string.Light1) + " " + myDevWrapperBean.deviceInfo.did.substring(8));
            bleDevice.setType(myDevWrapperBean.deviceInfo.type);
            bleDevice.setVersion(myDevWrapperBean.deviceInfo.version);
            bleDevice.setIndex(iCount);
            bleDevice.setGroupIndex(-1);
            bleDevice.setSupportAlexaEnable(myDevWrapperBean.deviceInfo.supportAlexaEnable());
            boolean z = false;
            bleDevice.setConfigAlexa(false);
            bleDevice.setSupportFixedGroup(true);
            bleDevice.setSupportGroupMain(myDevWrapperBean.deviceInfo.supportGroupMainDev());
            bleDevice.setBeenMain(false);
            bleDevice.setFixedId(0);
            bleDevice.setSupportTimerStatus(Tool.isSupportTimerStatus(myDevWrapperBean.deviceInfo));
            if (myDevWrapperBean.deviceInfo.supportSceneControl() && myDevWrapperBean.deviceInfo.type != 43049) {
                z = true;
            }
            bleDevice.setSupportSceneControl(z);
            bleDevice.setSupportModeLightness(Tool.isSupportModeLightness(myDevWrapperBean.deviceInfo));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchDevices() {
        BLSBleLight.setOnDevScanCallback(this.mOnDevScanCallback);
        BLSBleLight.setOnHeartBeatCallback(this.mHeartbeatCallback);
        this.isReceive = false;
        startScanTimer();
        startTimer();
        startUpdateListTimer();
        this.mIsStop = false;
        new AddDevThread().start();
    }

    private void stopSearchDevices() {
        BLSBleLight.stopBleReceiveServiceDelay();
        stopScanTimer();
        this.mIsStop = true;
        stopTimer();
        stopUpdateListTimer();
        BLSBleLight.setOnDevScanCallback(null);
        BLSBleLight.setOnHeartBeatCallback(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startReceiveService() {
        this.isReceive = true;
        BLSBleLight.startBleReceiveService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopReceiveService() {
        this.isReceive = false;
        BLSBleLight.stopBleReceiveService();
    }

    private void startTimer() {
        stopTimer();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                BLSBleLight.sendStartDiscoverPackage();
            }
        }, 300L, 3000L);
    }

    private void stopTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    private void startUpdateListTimer() {
        stopUpdateListTimer();
        Timer timer = new Timer();
        this.updateListTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ScanDeviceActivity.this.myHandler.removeCallbacks(ScanDeviceActivity.this.updateListRunnable);
                ScanDeviceActivity.this.myHandler.post(ScanDeviceActivity.this.updateListRunnable);
            }
        }, 1000L, 1000L);
    }

    private void stopUpdateListTimer() {
        Timer timer = this.updateListTimer;
        if (timer != null) {
            timer.cancel();
            this.updateListTimer.purge();
            this.updateListTimer = null;
        }
    }

    private void startScanTimer() {
        stopScanTimer();
        Timer timer = new Timer();
        this.scanTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity.4
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (ScanDeviceActivity.this.isReceive) {
                    ScanDeviceActivity.this.stopReceiveService();
                } else {
                    ScanDeviceActivity.this.startReceiveService();
                }
            }
        }, 0L, 5000L);
    }

    private void stopScanTimer() {
        Timer timer = this.scanTimer;
        if (timer != null) {
            timer.cancel();
            this.scanTimer.purge();
            this.scanTimer = null;
        }
    }

    /* JADX INFO: renamed from: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity$7, reason: invalid class name */
    class AnonymousClass7 implements OnDevHeartBeatCallback {
        AnonymousClass7() {
        }

        @Override // cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback
        public void onCallback(final int i, int i2, final String str) {
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ScanDeviceActivity$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCallback$0(i, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallback$0(int i, String str) {
            for (MyDevWrapperBean myDevWrapperBean : ScanDeviceActivity.this.scanBleList) {
                if (myDevWrapperBean.deviceInfo.addr == i && myDevWrapperBean.state != 2) {
                    myDevWrapperBean.state = 2;
                    myDevWrapperBean.deviceInfo.version = str;
                    if (!myDevWrapperBean.isUpdateState) {
                        myDevWrapperBean.isUpdateState = true;
                        ScanDeviceActivity.this.myHandler.sendMessage(ScanDeviceActivity.this.myHandler.obtainMessage(101, myDevWrapperBean));
                        ScanDeviceActivity.this.provisionDoneCount++;
                    }
                }
            }
        }
    }

    private class AddDevThread extends Thread {
        int sentCnt;

        private AddDevThread() {
            this.sentCnt = 1;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            while (!ScanDeviceActivity.this.mIsStop) {
                this.sentCnt = 0;
                for (MyDevWrapperBean myDevWrapperBean : ScanDeviceActivity.this.scanBleList) {
                    if (myDevWrapperBean.state != 2) {
                        if (myDevWrapperBean.state != 1) {
                            myDevWrapperBean.deviceInfo.addr = BLSBleLight.generateOneDeviceAddr();
                        }
                        BLSBleLight.sendDiscResWithDevice(myDevWrapperBean.deviceInfo);
                        myDevWrapperBean.state = 1;
                        SystemClock.sleep(200L);
                        this.sentCnt++;
                    }
                }
                if (this.sentCnt == 0) {
                    BLEFastconHelper.getInstance().sendStartScan();
                    SystemClock.sleep(500L);
                } else {
                    SystemClock.sleep(1000L);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMisMatchType(BLEDeviceInfo bLEDeviceInfo) {
        if (!Tool.isToRadar() || bLEDeviceInfo.type == 44601) {
            return !Tool.isToRadar() && bLEDeviceInfo.type == 44601;
        }
        return true;
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ScanDeviceActivity scanDeviceActivity = (ScanDeviceActivity) this.weakReference.get();
            if (message.what == 101) {
                scanDeviceActivity.saveBleDevice((MyDevWrapperBean) message.obj);
            }
        }
    }
}

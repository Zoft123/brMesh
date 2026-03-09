package com.brgd.brblmesh.Main.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.OtaDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.NonSlideSeekBar;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.ble.BleManager;
import com.brgd.brblmesh.GeneralClass.ble.BleMessageEvent;
import com.brgd.brblmesh.GeneralClass.ble.BleOTA;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class OtaActivity extends FatherActivity {
    ImageView backView;
    private List<BleDevice> bleDeviceList;
    private BleManager bleManager;
    private int connectCount;
    BleDevice currentOtaDv;
    RecyclerView deviceRecyclerView;
    private boolean isUpdating;
    private BluetoothAdapter mBleAdapter;
    private NonSlideSeekBar nonSlideSeekBar;
    AlertDialog otaAlertDialog;
    private OtaDeviceAdapter otaDeviceAdapter;
    private TextView progressText;
    private ConstraintLayout progressView;
    private Realm realm;
    private boolean updateSuccess;
    private BleOTA bleOTA = null;
    private boolean ota_file_ready = false;
    private final String[] fileNameArr = {"2lu", "4lu", "5lu281005", "5lu572211", "5lu361608", "5lu241005", "5lu180804", "5lu502010", "5lu361206", "5lu180603", "5lu231005", "5lu170603", "5lu462010", "5lu673216", "5lu341608", "5lu682211", "5lu572010"};
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.ota_back) {
                OtaActivity.this.finish();
            }
        }
    };
    private final Runnable otaActionRunnable = new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity.2
        @Override // java.lang.Runnable
        public void run() {
            if (OtaActivity.this.ota_file_ready) {
                OtaActivity.this.bleOTA.start();
                return;
            }
            OtaActivity.this.isUpdating = false;
            OtaActivity.this.updateSuccess = false;
            if (OtaActivity.this.bleManager.isConnected()) {
                OtaActivity.this.bleManager.disconnect().enqueue();
            }
            OtaActivity.this.hideProgressView();
            GlobalToast.showText(OtaActivity.this.getApplicationContext(), R.string.file_error, 1);
        }
    };
    private final Runnable otaFailDisconnectRunnable = new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity.3
        @Override // java.lang.Runnable
        public void run() {
            if (OtaActivity.this.bleManager.isConnected()) {
                OtaActivity.this.bleManager.disconnect().enqueue();
            }
        }
    };
    private final Runnable otaDoneRunnable = new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity.4
        @Override // java.lang.Runnable
        public void run() {
            OtaActivity.this.realm.beginTransaction();
            if (OtaActivity.this.updateSuccess) {
                OtaActivity.this.currentOtaDv.setOtaTag(0);
                OtaActivity.this.currentOtaDv.setVersion(Tool.updateNewVersion(OtaActivity.this.currentOtaDv.getVersion()));
                OtaActivity.this.bleDeviceList.remove(OtaActivity.this.currentOtaDv);
            } else {
                OtaActivity.this.currentOtaDv.setOtaTag(2);
            }
            OtaActivity.this.realm.commitTransaction();
            OtaActivity.this.otaDeviceAdapter.notifyDataSetChanged();
            OtaActivity.this.hideProgressView();
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ota);
        initView();
        initBleOta();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        if (EventBus.getDefault().isRegistered(this)) {
            return;
        }
        EventBus.getDefault().register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.bleManager.isConnected()) {
            this.bleManager.disconnect().enqueue();
        }
        this.bleManager.close();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initBleOta() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService("bluetooth");
        if (bluetoothManager != null) {
            this.mBleAdapter = bluetoothManager.getAdapter();
        }
        BleManager bleManager = new BleManager(this);
        this.bleManager = bleManager;
        bleManager.setConnectionObserver(new connectionObserver());
        this.bleOTA = new BleOTA(this, this.bleManager, this.mBleAdapter);
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.ota_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.bleDeviceList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.ota_device_recyclerView);
        OtaDeviceAdapter otaDeviceAdapter = new OtaDeviceAdapter(this, this.bleDeviceList);
        this.otaDeviceAdapter = otaDeviceAdapter;
        this.deviceRecyclerView.setAdapter(otaDeviceAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.otaDeviceAdapter.setOnUpgradeClickListener(new OtaDeviceAdapter.OnUpgradeClickListener() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralAdapter.OtaDeviceAdapter.OnUpgradeClickListener
            public final void onUpgradeClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        this.bleDeviceList.addAll(this.realm.where(BleDevice.class).notEqualTo("otaTag", (Integer) 0).findAll().sort(GlobalVariable.index));
        OtaDeviceAdapter otaDeviceAdapter2 = this.otaDeviceAdapter;
        if (otaDeviceAdapter2 != null) {
            otaDeviceAdapter2.notifyDataSetChanged();
        }
        this.progressView = (ConstraintLayout) findViewById(R.id.progress_bg);
        this.nonSlideSeekBar = (NonSlideSeekBar) findViewById(R.id.progress_seekBar);
        this.progressText = (TextView) findViewById(R.id.progress_text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
            GlobalToast.showText(getApplicationContext(), R.string.bleCA, 1);
            return;
        }
        if (Tool.bleNotOnToast(getApplicationContext())) {
            return;
        }
        this.currentOtaDv = this.bleDeviceList.get(i);
        this.ota_file_ready = false;
        this.connectCount = 0;
        this.isUpdating = false;
        this.updateSuccess = false;
        otaDialog();
    }

    private void showProgressView() {
        this.progressView.setVisibility(0);
        this.nonSlideSeekBar.setProgress(0);
        this.progressText.setText(getString(R.string.configProgress) + " 0%");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideProgressView() {
        this.progressView.setVisibility(4);
    }

    private void updateProgress(int i) {
        this.nonSlideSeekBar.setProgress(i);
        this.progressText.setText(getString(R.string.configProgress) + " " + i + "%");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBleMessageEvent(BleMessageEvent bleMessageEvent) {
        int i = bleMessageEvent.type;
        if (i == 2) {
            this.myHandler.postDelayed(this.otaFailDisconnectRunnable, 500L);
            this.myHandler.postDelayed(this.otaDoneRunnable, 3500L);
            this.isUpdating = false;
            this.updateSuccess = false;
            GlobalToast.showText(getApplicationContext(), R.string.ota_fail, 1);
            return;
        }
        if (i == 7) {
            updateProgress(bleMessageEvent.number);
            return;
        }
        if (i != 4) {
            if (i != 5) {
                return;
            }
            this.ota_file_ready = true;
        } else {
            this.myHandler.postDelayed(this.otaDoneRunnable, 3000L);
            this.isUpdating = false;
            this.updateSuccess = true;
            GlobalToast.showText(getApplicationContext(), R.string.ota_success, 1);
        }
    }

    public class connectionObserver implements ConnectionObserver {
        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceConnected(BluetoothDevice bluetoothDevice) {
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceConnecting(BluetoothDevice bluetoothDevice) {
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceConnectionUpdated(int i, int i2, int i3) {
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceDisconnected(BluetoothDevice bluetoothDevice, int i) {
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceDisconnecting(BluetoothDevice bluetoothDevice) {
        }

        public connectionObserver() {
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceFailedToConnect(BluetoothDevice bluetoothDevice, int i) {
            OtaActivity.this.myHandler.postDelayed(OtaActivity.this.otaFailDisconnectRunnable, 500L);
            OtaActivity.this.myHandler.postDelayed(OtaActivity.this.otaDoneRunnable, 3500L);
            OtaActivity.this.isUpdating = false;
            OtaActivity.this.updateSuccess = false;
            GlobalToast.showText(OtaActivity.this.getApplicationContext(), R.string.fail_connect, 1);
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.observer.ConnectionObserver
        public void onDeviceReady(BluetoothDevice bluetoothDevice) {
            OtaActivity.this.bleManager.setMTU(23);
            OtaActivity.this.bleManager.setDeviceMac(bluetoothDevice.getAddress());
            if (OtaActivity.this.connectCount == 0) {
                OtaActivity.this.connectCount++;
                OtaActivity.this.isUpdating = true;
                OtaActivity.this.myHandler.postDelayed(OtaActivity.this.otaActionRunnable, 2000L);
            }
        }
    }

    public void connectBleDevice(String str) {
        this.bleManager.connect(this.mBleAdapter.getRemoteDevice(str)).retry(3, 100).useAutoConnect(false).enqueue();
    }

    public void connectBleDeviceAction(String str, boolean z) {
        String macAddress = Tool.formatMacAddress(str);
        if (z) {
            macAddress = Tool.incrementLastOctet(macAddress);
        }
        connectBleDevice(macAddress);
    }

    private void otaConnect(boolean z) throws Throwable {
        String str;
        if (this.currentOtaDv.getType() == 43051) {
            str = this.fileNameArr[0];
        } else if (this.currentOtaDv.getType() == 43169) {
            str = this.fileNameArr[1];
        } else if (this.currentOtaDv.getType() == 43050) {
            str = this.fileNameArr[Tool.getVersionType(this.currentOtaDv.getVersion(), 1) + 1];
        } else {
            str = null;
        }
        if (str == null) {
            GlobalToast.showText(getApplicationContext(), R.string.file_error, 1);
            return;
        }
        File fileFindFile = Tool.findFile(getApplicationContext(), str);
        if (fileFindFile == null) {
            GlobalToast.showText(getApplicationContext(), R.string.file_error, 1);
        } else {
            if (fileFindFile.exists()) {
                this.bleOTA.fileOpen(this, Uri.fromFile(fileFindFile));
                connectBleDeviceAction(this.currentOtaDv.getDid(), z);
                return;
            }
            GlobalToast.showText(getApplicationContext(), R.string.file_error, 1);
        }
    }

    private void otaDialog() {
        BluetoothAdapter bluetoothAdapter = this.mBleAdapter;
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            GlobalToast.showText(getApplicationContext(), R.string.enableBle, 1);
            return;
        }
        if (this.otaAlertDialog == null) {
            this.otaAlertDialog = new AlertDialog.Builder(this).setMessage(R.string.ota_tip).setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) throws Throwable {
                    this.f$0.lambda$otaDialog$1(dialogInterface, i);
                }
            }).setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.OtaActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) throws Throwable {
                    this.f$0.lambda$otaDialog$2(dialogInterface, i);
                }
            }).create();
        }
        this.otaAlertDialog.show();
        this.otaAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$otaDialog$1(DialogInterface dialogInterface, int i) throws Throwable {
        otaConnect(true);
        this.otaAlertDialog.cancel();
        this.otaAlertDialog = null;
        showProgressView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$otaDialog$2(DialogInterface dialogInterface, int i) throws Throwable {
        otaConnect(false);
        this.otaAlertDialog.cancel();
        this.otaAlertDialog = null;
        showProgressView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getRepeatCount() == 0 && this.isUpdating) {
            GlobalToast.showText(getApplicationContext(), R.string.ota_updating, 1);
            return false;
        }
        return super.onKeyDown(i, keyEvent);
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
        }

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }
    }
}

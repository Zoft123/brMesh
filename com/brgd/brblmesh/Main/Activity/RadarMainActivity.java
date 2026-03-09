package com.brgd.brblmesh.Main.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.Main.Fragment.RadarDvFragment;
import com.brgd.brblmesh.Main.Fragment.ThirdFragment;
import com.brgd.brblmesh.Main.Interface.FirstFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RadarMainActivity extends FatherActivity implements BottomNavigationBar.OnTabSelectedListener {
    ActivityResultLauncher<String[]> QRPermissionLauncher;
    AlertDialog alertDialog;
    BottomNavigationItem allDevicesItem;
    AlertDialog bleAlertDialog;
    AlertDialog bleCASAlertDialog;
    BottomNavigationBar bottomNavigationBar;
    public int currentTabPosition;
    ActivityResultLauncher<String[]> enableBleCASLauncher;
    ActivityResultLauncher<Intent> enableBleLauncher;
    private FirstFragmentListener firstFragmentListener;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    AlertDialog locationAlertDialog;
    ActivityResultLauncher<String[]> locationPermissionLauncher;
    AlertDialog locationServiceAlertDialog;
    ActivityResultLauncher<Intent> locationServiceLauncher;
    private BluetoothStateBroadcastReceive mReceive;
    BottomNavigationItem moreItem;
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);
    AlertDialog needBleCASAlertDialog;
    ActivityResultLauncher<String> pickJsonFileLauncher;
    AlertDialog qrPermissionAlertDialog;
    private RadarDvFragment radarDvFragment;
    private Realm realm;
    private ThirdFragment thirdFragment;

    static /* synthetic */ void lambda$registerActivityResultLauncher$4(ActivityResult activityResult) {
    }

    @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
    public void onTabReselected(int i) {
    }

    @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
    public void onTabUnselected(int i) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_radar_main);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        initView();
        registerActivityResultLauncher();
        registerBluetoothReceiver();
        enableBluetooth();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (Tool.isAPI31()) {
            if (GlobalBluetooth.getInstance().isBleEnable() && Tool.checkBleCASPermission()) {
                GlobalBluetooth.getInstance().broadcastAppTimeSync();
                return;
            }
            return;
        }
        GlobalBluetooth.getInstance().broadcastAppTimeSync();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterBluetoothReceiver();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void initView() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        this.bottomNavigationBar = bottomNavigationBar;
        bottomNavigationBar.setMode(1);
        this.bottomNavigationBar.setBarBackgroundColor(R.color.colorWhite);
        this.bottomNavigationBar.setActiveColor(R.color.colorSelect);
        this.bottomNavigationBar.setInActiveColor(R.color.colorText);
        ViewGroup.LayoutParams layoutParams = this.bottomNavigationBar.getChildAt(0).getLayoutParams();
        layoutParams.height = -1;
        this.bottomNavigationBar.getChildAt(0).setLayoutParams(layoutParams);
        this.allDevicesItem = new BottomNavigationItem(R.drawable.all_dv_select, R.string.All).setInactiveIconResource(R.drawable.all_dv_normal);
        this.moreItem = new BottomNavigationItem(R.drawable.more_select, R.string.More).setInactiveIconResource(R.drawable.more_normal);
        this.bottomNavigationBar.addItem(this.allDevicesItem).addItem(this.moreItem).setFirstSelectedPosition(0).initialise();
        Tool.setBottomNavigationItem(this, this.bottomNavigationBar, 10, 23, 14);
        this.bottomNavigationBar.setTabSelectedListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.fragmentManager = supportFragmentManager;
        this.fragmentTransaction = supportFragmentManager.beginTransaction();
        this.radarDvFragment = new RadarDvFragment();
        this.fragmentTransaction.add(R.id.main_content, this.radarDvFragment);
        this.fragmentTransaction.commit();
        this.firstFragmentListener = this.radarDvFragment;
    }

    private void hideFragment(Fragment fragment, FragmentTransaction fragmentTransaction) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
    public void onTabSelected(int i) {
        this.currentTabPosition = i;
        this.fragmentTransaction = null;
        FragmentTransaction fragmentTransactionBeginTransaction = this.fragmentManager.beginTransaction();
        this.fragmentTransaction = fragmentTransactionBeginTransaction;
        if (i == 0) {
            hideFragment(this.thirdFragment, fragmentTransactionBeginTransaction);
            RadarDvFragment radarDvFragment = this.radarDvFragment;
            if (radarDvFragment == null) {
                this.radarDvFragment = new RadarDvFragment();
                this.fragmentTransaction.add(R.id.main_content, this.radarDvFragment);
            } else if (radarDvFragment.isHidden()) {
                this.fragmentTransaction.show(this.radarDvFragment);
            }
            if (this.firstFragmentListener == null) {
                this.firstFragmentListener = this.radarDvFragment;
            }
        } else if (i == 1) {
            hideFragment(this.radarDvFragment, fragmentTransactionBeginTransaction);
            ThirdFragment thirdFragment = this.thirdFragment;
            if (thirdFragment == null) {
                this.thirdFragment = new ThirdFragment();
                this.fragmentTransaction.add(R.id.main_content, this.thirdFragment);
            } else if (thirdFragment.isHidden()) {
                this.fragmentTransaction.show(this.thirdFragment);
            }
        }
        this.fragmentTransaction.commit();
    }

    private void registerBluetoothReceiver() {
        if (this.mReceive == null) {
            this.mReceive = new BluetoothStateBroadcastReceive();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        if (Build.VERSION.SDK_INT >= 34) {
            registerReceiver(this.mReceive, intentFilter, 4);
        } else {
            registerReceiver(this.mReceive, intentFilter);
        }
    }

    private void unregisterBluetoothReceiver() {
        BluetoothStateBroadcastReceive bluetoothStateBroadcastReceive = this.mReceive;
        if (bluetoothStateBroadcastReceive != null) {
            unregisterReceiver(bluetoothStateBroadcastReceive);
            this.mReceive = null;
        }
    }

    private class BluetoothStateBroadcastReceive extends BroadcastReceiver {
        private BluetoothStateBroadcastReceive() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "android.bluetooth.adapter.action.STATE_CHANGED") && intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0) == 10) {
                if (Tool.isAPI31()) {
                    RadarMainActivity.this.enableBluetooth();
                } else {
                    RadarMainActivity.this.bleDialog();
                }
            }
        }
    }

    public void toScanDevice() {
        if (Tool.checkCameraPermission()) {
            qrScanDevice();
        } else {
            QRPermission();
        }
    }

    private void qrScanDevice() {
        startActivity(new Intent(this, (Class<?>) CustomCaptureActivity.class));
    }

    public static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
        }

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }
    }

    public void enableBleCAS() {
        if (Tool.isAPI31()) {
            this.enableBleCASLauncher.launch(new String[]{"android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.BLUETOOTH_SCAN"});
        }
    }

    private void enableBle() {
        this.enableBleLauncher.launch(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
    }

    private void enableLocationService() {
        this.locationServiceLauncher.launch(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    private void locationPermission() {
        this.locationPermissionLauncher.launch(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"});
    }

    private void toGetPermission() {
        this.getPermissionLauncher.launch(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
    }

    private void QRPermission() {
        this.QRPermissionLauncher.launch(new String[]{"android.permission.CAMERA"});
    }

    public void chooseFile() {
        this.pickJsonFileLauncher.launch("application/json");
    }

    private void registerActivityResultLauncher() {
        this.enableBleCASLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda11
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$0((Map) obj);
            }
        });
        this.enableBleLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda14
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$1((ActivityResult) obj);
            }
        });
        this.locationServiceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda15
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$2((ActivityResult) obj);
            }
        });
        this.locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda16
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((Map) obj);
            }
        });
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda17
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                RadarMainActivity.lambda$registerActivityResultLauncher$4((ActivityResult) obj);
            }
        });
        this.QRPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda18
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$5((Map) obj);
            }
        });
        this.pickJsonFileLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda19
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$6((Uri) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$0(Map map) {
        if (map.get("android.permission.BLUETOOTH_CONNECT") == null || map.get("android.permission.BLUETOOTH_ADVERTISE") == null || map.get("android.permission.BLUETOOTH_SCAN") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.BLUETOOTH_CONNECT"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.BLUETOOTH_ADVERTISE"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.BLUETOOTH_SCAN"))).equals(true)) {
            getBluetoothAuth();
        } else {
            bleCASDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$1(ActivityResult activityResult) {
        if (!GlobalBluetooth.getInstance().isBleEnable()) {
            bleDialog();
        } else {
            GlobalBluetooth.getInstance().broadcastAppTimeSync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$2(ActivityResult activityResult) {
        if (Tool.checkLocationServiceEnable(this)) {
            return;
        }
        GlobalToast.showText(getApplicationContext(), R.string.locationServiceRequest, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$3(Map map) {
        if (map.get("android.permission.ACCESS_FINE_LOCATION") == null || map.get("android.permission.ACCESS_COARSE_LOCATION") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_FINE_LOCATION"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_COARSE_LOCATION"))).equals(true)) {
            FirstFragmentListener firstFragmentListener = this.firstFragmentListener;
            if (firstFragmentListener != null) {
                firstFragmentListener.toScanDevice();
                return;
            }
            return;
        }
        locationDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$5(Map map) {
        if (map.get("android.permission.CAMERA") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.CAMERA"))).equals(true)) {
                qrScanDevice();
            } else {
                qrPermissionDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$6(Uri uri) {
        if (uri != null) {
            handleJsonFile(uri);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableBluetooth() {
        if (GlobalBluetooth.getInstance().isSupportBle()) {
            if (Tool.isAPI31()) {
                checkBleCAS();
                return;
            } else {
                getBluetoothAuth();
                return;
            }
        }
        GlobalToast.showText(getApplicationContext(), R.string.unSupportBle, 1);
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity.1
            @Override // java.lang.Runnable
            public void run() {
                RadarMainActivity.this.finish();
            }
        }, 2500L);
    }

    private void checkBleCAS() {
        if (Tool.checkBleCASPermission()) {
            getBluetoothAuth();
        } else {
            needBleCASDialog();
        }
    }

    private void getBluetoothAuth() {
        if (GlobalBluetooth.getInstance().isBleEnable()) {
            return;
        }
        bleDialog();
    }

    public void getLocationPermission() {
        if (Tool.checkLocationServiceEnable(this)) {
            if (Tool.checkLocationPermission()) {
                FirstFragmentListener firstFragmentListener = this.firstFragmentListener;
                if (firstFragmentListener != null) {
                    firstFragmentListener.toScanDevice();
                    return;
                }
                return;
            }
            locationPermission();
            return;
        }
        locationServiceDialog();
    }

    public void needBleCASDialog() {
        if (this.needBleCASAlertDialog == null) {
            this.needBleCASAlertDialog = new AlertDialog.Builder(this).setMessage(R.string.needBleAdvertise).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$needBleCASDialog$7(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda13
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$needBleCASDialog$8(dialogInterface, i);
                }
            }).create();
        }
        this.needBleCASAlertDialog.show();
        this.needBleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$needBleCASDialog$7(DialogInterface dialogInterface, int i) {
        enableBleCAS();
        this.needBleCASAlertDialog.cancel();
        this.needBleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$needBleCASDialog$8(DialogInterface dialogInterface, int i) {
        this.needBleCASAlertDialog.cancel();
        this.needBleCASAlertDialog = null;
    }

    public void bleCASDialog() {
        if (this.bleCASAlertDialog == null) {
            this.bleCASAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.bleCA).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$9(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$10(dialogInterface, i);
                }
            }).create();
        }
        this.bleCASAlertDialog.show();
        this.bleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$9(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$10(DialogInterface dialogInterface, int i) {
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bleDialog() {
        if (this.bleAlertDialog == null) {
            this.bleAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.enableBle).setMessage(R.string.enableBleMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleDialog$11(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleDialog$12(dialogInterface, i);
                }
            }).create();
        }
        this.bleAlertDialog.show();
        this.bleAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleDialog$11(DialogInterface dialogInterface, int i) {
        enableBle();
        this.bleAlertDialog.cancel();
        this.bleAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleDialog$12(DialogInterface dialogInterface, int i) {
        this.bleAlertDialog.cancel();
        this.bleAlertDialog = null;
        bleDialog();
    }

    private void locationServiceDialog() {
        if (this.locationServiceAlertDialog == null) {
            this.locationServiceAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationServiceRequest).setMessage(R.string.locationServiceRequestMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda20
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$13(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda21
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$14(dialogInterface, i);
                }
            }).create();
        }
        this.locationServiceAlertDialog.show();
        this.locationServiceAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$13(DialogInterface dialogInterface, int i) {
        enableLocationService();
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$14(DialogInterface dialogInterface, int i) {
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationAddressRequestMessage).setMessage(R.string.locationAddressRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$15(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$16(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$15(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$16(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    public void qrDialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.qrCodeRequestMessage1).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrDialog$17(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrDialog$18(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrDialog$17(DialogInterface dialogInterface, int i) {
        toScanDevice();
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrDialog$18(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    private void qrPermissionDialog() {
        if (this.qrPermissionAlertDialog == null) {
            this.qrPermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.qrCodeRequestMessage).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrPermissionDialog$19(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarMainActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrPermissionDialog$20(dialogInterface, i);
                }
            }).create();
        }
        this.qrPermissionAlertDialog.show();
        this.qrPermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrPermissionDialog$19(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.qrPermissionAlertDialog.cancel();
        this.qrPermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrPermissionDialog$20(DialogInterface dialogInterface, int i) {
        this.qrPermissionAlertDialog.cancel();
        this.qrPermissionAlertDialog = null;
    }

    private void handleJsonFile(Uri uri) {
        try {
            InputStream inputStreamOpenInputStream = getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                try {
                    byte[] bArr = new byte[inputStreamOpenInputStream.available()];
                    inputStreamOpenInputStream.read(bArr);
                    String str = new String(bArr, StandardCharsets.UTF_8);
                    Intent intent = new Intent(this, (Class<?>) OnekeyResetActivity.class);
                    intent.putExtra(GlobalVariable.toImportCtrl, str);
                    startActivity(intent);
                    finish();
                } finally {
                }
            }
            if (inputStreamOpenInputStream != null) {
                inputStreamOpenInputStream.close();
            }
        } catch (IOException e) {
            Log.d("handleJsonFile", "读取数据流出错：" + e);
        }
    }
}

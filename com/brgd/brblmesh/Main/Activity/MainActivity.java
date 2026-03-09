package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.com.broadlink.blelight.BLSBleLight;
import com.alibaba.fastjson.asm.Opcodes;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralClass.DevOtaCloudHelper;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalThreadPools;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Fragment.FirstFragment;
import com.brgd.brblmesh.Main.Fragment.SecondFragment;
import com.brgd.brblmesh.Main.Fragment.ThirdFragment;
import com.brgd.brblmesh.Main.Interface.FirstFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends FatherActivity implements BottomNavigationBar.OnTabSelectedListener {
    private static final int OTA_STATUS1 = 101;
    private static final int OTA_STATUS2 = 102;
    private static final int RE_TAB_BAR = 103;
    ActivityResultLauncher<String[]> QRPermissionLauncher;
    AlertDialog alertDialog;
    private BottomNavigationItem allDevicesItem;
    AlertDialog bleAlertDialog;
    AlertDialog bleCASAlertDialog;
    private List<BleDevice> bleDeviceList;
    private BottomNavigationBar bottomNavigationBar;
    String ctrlKey;
    public int currentTabPosition;
    ActivityResultLauncher<String[]> enableBleCASLauncher;
    ActivityResultLauncher<Intent> enableBleLauncher;
    private FirstFragment firstFragment;
    private FirstFragmentListener firstFragmentListener;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    private boolean isGroupCtrl;
    private boolean isLoadView;
    Dialog loadingDialog;
    AlertDialog locationAlertDialog;
    ActivityResultLauncher<String[]> locationPermissionLauncher;
    AlertDialog locationServiceAlertDialog;
    ActivityResultLauncher<Intent> locationServiceLauncher;
    private BluetoothStateBroadcastReceive mReceive;
    private BottomNavigationItem moreItem;
    AlertDialog needBleCASAlertDialog;
    private PhoneType phoneType;
    ActivityResultLauncher<String> pickJsonFileLauncher;
    AlertDialog qrPermissionAlertDialog;
    private Realm realm;
    private BottomNavigationItem sceneItem;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private boolean viewIsAppear;
    private final String[] fileNameArr = {"2lu", "4lu", "5lu281005", "5lu572211", "5lu361608", "5lu241005", "5lu180804", "5lu502010", "5lu361206", "5lu180603", "5lu231005", "5lu170603", "5lu462010", "5lu673216", "5lu341608", "5lu682211", "5lu572010"};
    private final ConcurrentHashMap<String, String> deviceMap = new ConcurrentHashMap<>();
    private int taskCount = 0;
    private int downloadCount = 0;
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    static /* synthetic */ void lambda$registerActivityResultLauncher$5(ActivityResult activityResult) {
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
        setContentView(R.layout.activity_main);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        handleIntent(getIntent());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.isLoadView) {
            if (this.phoneType.getPhoneType() == 2) {
                BottomNavigationItem bottomNavigationItem = this.sceneItem;
                if (bottomNavigationItem != null) {
                    this.bottomNavigationBar.removeItem(bottomNavigationItem).setFirstSelectedPosition(0).initialise();
                    this.sceneItem = null;
                }
            } else {
                if (this.sceneItem == null) {
                    this.sceneItem = new BottomNavigationItem(R.drawable.scene_select, R.string.Scene).setInactiveIconResource(R.drawable.scene_normal);
                    this.bottomNavigationBar.removeItem(this.allDevicesItem).removeItem(this.moreItem);
                    this.bottomNavigationBar.addItem(this.allDevicesItem).addItem(this.sceneItem).addItem(this.moreItem).setFirstSelectedPosition(0).initialise();
                }
                this.bottomNavigationBar.removeItem(this.moreItem);
                if (hasNeedOTA()) {
                    this.moreItem = new BottomNavigationItem(R.drawable.more_red_select, R.string.More).setInactiveIconResource(R.drawable.more_red_normal);
                } else {
                    this.moreItem = new BottomNavigationItem(R.drawable.more_select, R.string.More).setInactiveIconResource(R.drawable.more_normal);
                }
                this.bottomNavigationBar.addItem(this.moreItem).setFirstSelectedPosition(this.currentTabPosition).initialise();
            }
            if (Tool.isAPI31()) {
                if (GlobalBluetooth.getInstance().isBleEnable() && Tool.checkBleCASPermission()) {
                    GlobalBluetooth.getInstance().broadcastAppTimeSync();
                }
            } else {
                GlobalBluetooth.getInstance().broadcastAppTimeSync();
            }
            this.viewIsAppear = true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.viewIsAppear = false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterBluetoothReceiver();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        if ("android.intent.action.VIEW".equals(action) && !Objects.equals(data, null)) {
            if (this.loadingDialog == null) {
                this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Sure);
            }
            this.ctrlKey = (String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.ctrlKey, "");
            List<String> pathSegments = data.getPathSegments();
            if (!Objects.equals(pathSegments, null) && pathSegments.size() == 2 && !Objects.equals(this.ctrlKey, null)) {
                setCtrlKey();
                String str = pathSegments.get(0);
                String str2 = pathSegments.get(1);
                if (Objects.equals(str, GlobalVariable.ACTION)) {
                    if (Objects.equals(str2, "scene")) {
                        String queryParameter = data.getQueryParameter("scene");
                        if (!Objects.equals(queryParameter, null)) {
                            Iterator it = this.realm.where(Scene.class).findAll().iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Scene scene = (Scene) it.next();
                                if (queryParameter.equalsIgnoreCase(scene.getSceneName())) {
                                    GlobalBluetooth.getInstance().enableScene(scene.getSceneNumber());
                                    break;
                                }
                            }
                        }
                    } else {
                        String queryParameter2 = data.getQueryParameter("dv_name");
                        if (!Objects.equals(queryParameter2, null)) {
                            Iterator it2 = this.realm.where(Groups.class).findAll().iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                Groups groups = (Groups) it2.next();
                                if (queryParameter2.equalsIgnoreCase(groups.getGroupName())) {
                                    this.isGroupCtrl = true;
                                    if (Objects.equals(str2, DebugKt.DEBUG_PROPERTY_VALUE_ON)) {
                                        GlobalBluetooth.getInstance().groupOnOff(43050, groups.getGroupId(), true);
                                    } else if (Objects.equals(str2, DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                                        GlobalBluetooth.getInstance().groupOnOff(43050, groups.getGroupId(), false);
                                    } else if (Objects.equals(str2, TypedValues.Custom.S_COLOR)) {
                                        String queryParameter3 = data.getQueryParameter(GlobalVariable.colorValue);
                                        if (!Objects.equals(queryParameter3, null)) {
                                            colorCtrl(groups.getGroupId(), false, queryParameter3.toLowerCase());
                                        }
                                    } else if (Objects.equals(str2, GlobalVariable.BRIGHTNESS)) {
                                        String queryParameter4 = data.getQueryParameter("brightnessValue");
                                        if (!Objects.equals(queryParameter4, null)) {
                                            brightnessCtrl(groups.getGroupId(), false, Integer.parseInt(queryParameter4));
                                        }
                                    }
                                }
                            }
                            if (!this.isGroupCtrl) {
                                Iterator it3 = this.realm.where(BleDevice.class).findAll().iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        break;
                                    }
                                    BleDevice bleDevice = (BleDevice) it3.next();
                                    if (queryParameter2.equalsIgnoreCase(bleDevice.getName())) {
                                        if (Objects.equals(str2, DebugKt.DEBUG_PROPERTY_VALUE_ON)) {
                                            GlobalBluetooth.getInstance().singleOnOff(bleDevice.getAddr(), true);
                                        } else if (Objects.equals(str2, DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                                            GlobalBluetooth.getInstance().singleOnOff(bleDevice.getAddr(), false);
                                        } else if (Objects.equals(str2, TypedValues.Custom.S_COLOR)) {
                                            String queryParameter5 = data.getQueryParameter(GlobalVariable.colorValue);
                                            if (!Objects.equals(queryParameter5, null)) {
                                                colorCtrl(bleDevice.getAddr(), true, queryParameter5.toLowerCase());
                                            }
                                        } else if (Objects.equals(str2, GlobalVariable.BRIGHTNESS)) {
                                            String queryParameter6 = data.getQueryParameter("brightnessValue");
                                            if (!Objects.equals(queryParameter6, null)) {
                                                brightnessCtrl(bleDevice.getAddr(), true, Integer.parseInt(queryParameter6));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleIntent$0();
                }
            }, 2000L);
            return;
        }
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$0() {
        loadDialogUtils.closeDialog(this.loadingDialog);
        this.loadingDialog = null;
        finishAndRemoveTask();
    }

    private int getColorFromString(String str) {
        HashMap map = new HashMap();
        map.put(GlobalVariable.RED, String.valueOf(-65536));
        map.put(GlobalVariable.GREEN, String.valueOf(CustomColor.GREEN));
        map.put(GlobalVariable.BLUE, String.valueOf(CustomColor.BLUE));
        map.put("orange", String.valueOf(Color.rgb(255, Opcodes.IF_ACMPEQ, 0)));
        map.put("yellow", String.valueOf(-256));
        map.put("cyan", String.valueOf(CustomColor.CYAN));
        map.put("purple", String.valueOf(Color.rgb(128, 0, 128)));
        map.put("magenta", String.valueOf(CustomColor.MAGENTA));
        map.put("crimson", String.valueOf(Color.rgb(220, 20, 60)));
        map.put("salmon", String.valueOf(Color.rgb(255, 110, 0)));
        map.put("gold", String.valueOf(Color.rgb(255, Opcodes.IF_ICMPNE, 0)));
        map.put("turquoise", String.valueOf(Color.rgb(0, 255, 80)));
        map.put("sky blue", String.valueOf(Color.rgb(70, 230, 255)));
        map.put("lavender", String.valueOf(Color.rgb(75, 0, 255)));
        map.put("pink", String.valueOf(Color.rgb(255, 90, 70)));
        String str2 = (String) map.get(str);
        if (Objects.equals(str2, null)) {
            return -2;
        }
        return Integer.parseInt(str2);
    }

    private int getColorTempFromString(String str) {
        HashMap map = new HashMap();
        map.put("cool white", String.valueOf(0));
        map.put("daylight white", String.valueOf(20));
        map.put("soft white", String.valueOf(80));
        map.put("warm white", String.valueOf(100));
        String str2 = (String) map.get(str);
        if (Objects.equals(str2, null)) {
            return -2;
        }
        return Integer.parseInt(str2);
    }

    private void colorCtrl(int i, boolean z, String str) {
        int iIntValue = (int) ((((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.lightnessValue, 100)).intValue() / 100.0f) * 127.0f);
        int colorTempFromString = getColorTempFromString(str);
        int colorFromString = getColorFromString(str);
        if (Objects.equals(str, "white")) {
            if (z) {
                GlobalBluetooth.getInstance().singleWhite(i, iIntValue);
                return;
            } else {
                GlobalBluetooth.getInstance().groupWhite(43050, i, iIntValue);
                return;
            }
        }
        if (colorTempFromString != -2) {
            int i2 = (int) ((colorTempFromString / 100.0f) * 255.0f);
            int i3 = 255 - i2;
            if (z) {
                GlobalBluetooth.getInstance().singleTempCtrl(i, iIntValue, i2, i3);
                return;
            } else {
                GlobalBluetooth.getInstance().groupTempCtrl(43050, i, iIntValue, i2, i3);
                return;
            }
        }
        if (colorFromString != -2) {
            int iRed = Color.red(colorFromString);
            int iGreen = Color.green(colorFromString);
            int iBlue = Color.blue(colorFromString);
            if (z) {
                GlobalBluetooth.getInstance().singleRgbCtrl(i, iIntValue, iRed, iGreen, iBlue);
            } else {
                GlobalBluetooth.getInstance().groupRgbCtrl(43050, i, iIntValue, iRed, iGreen, iBlue);
            }
        }
    }

    private void brightnessCtrl(int i, boolean z, int i2) {
        int i3 = (int) ((i2 / 100.0f) * 127.0f);
        if (z) {
            GlobalBluetooth.getInstance().singleLightness(i, i3);
        } else {
            GlobalBluetooth.getInstance().groupLightness(43050, i, i3);
        }
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.lightnessValue, Integer.valueOf(i2));
    }

    private void setCtrlKey() {
        BLSBleLight.init(getApplication(), false);
        BLSBleLight.setNeedRemoteCtrl(false);
        BLSBleLight.setShowDialogWhenPermInvalid(false);
        BLSBleLight.setShowDialogWhenBlueToothNeedRestart(false);
        BLSBleLight.setBLEControlKey(this.ctrlKey);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void init() {
        this.phoneType = (PhoneType) this.realm.where(PhoneType.class).findFirst();
        initView();
        this.isLoadView = true;
        registerActivityResultLauncher();
        registerBluetoothReceiver();
        enableBluetooth();
        compareVersion();
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
        if (this.phoneType.getPhoneType() == 2) {
            this.allDevicesItem = new BottomNavigationItem(R.drawable.all_dv_select, R.string.All).setInactiveIconResource(R.drawable.all_dv_normal);
            this.moreItem = new BottomNavigationItem(R.drawable.more_select, R.string.More).setInactiveIconResource(R.drawable.more_normal);
            this.bottomNavigationBar.addItem(this.allDevicesItem).addItem(this.moreItem).setFirstSelectedPosition(0).initialise();
        } else {
            this.allDevicesItem = new BottomNavigationItem(R.drawable.all_dv_select, R.string.All).setInactiveIconResource(R.drawable.all_dv_normal);
            this.sceneItem = new BottomNavigationItem(R.drawable.scene_select, R.string.Scene).setInactiveIconResource(R.drawable.scene_normal);
            this.moreItem = new BottomNavigationItem(R.drawable.more_select, R.string.More).setInactiveIconResource(R.drawable.more_normal);
            this.bottomNavigationBar.addItem(this.allDevicesItem).addItem(this.sceneItem).addItem(this.moreItem).setFirstSelectedPosition(0).initialise();
        }
        Tool.setBottomNavigationItem(this, this.bottomNavigationBar, 10, 23, 14);
        this.bottomNavigationBar.setTabSelectedListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.fragmentManager = supportFragmentManager;
        this.fragmentTransaction = supportFragmentManager.beginTransaction();
        this.firstFragment = new FirstFragment();
        this.fragmentTransaction.add(R.id.main_content, this.firstFragment);
        this.fragmentTransaction.commit();
        this.firstFragmentListener = this.firstFragment;
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
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        if (this.phoneType.getPhoneType() == 2) {
            if (i == 0) {
                hideFragment(this.thirdFragment, this.fragmentTransaction);
                FirstFragment firstFragment = this.firstFragment;
                if (firstFragment == null) {
                    this.firstFragment = new FirstFragment();
                    this.fragmentTransaction.add(R.id.main_content, this.firstFragment);
                } else if (firstFragment.isHidden()) {
                    this.fragmentTransaction.show(this.firstFragment);
                }
                if (this.firstFragmentListener == null) {
                    this.firstFragmentListener = this.firstFragment;
                }
            } else if (i == 1) {
                hideFragment(this.firstFragment, this.fragmentTransaction);
                ThirdFragment thirdFragment = this.thirdFragment;
                if (thirdFragment == null) {
                    this.thirdFragment = new ThirdFragment();
                    this.fragmentTransaction.add(R.id.main_content, this.thirdFragment);
                } else if (thirdFragment.isHidden()) {
                    this.fragmentTransaction.show(this.thirdFragment);
                }
            }
        } else if (i == 0) {
            hideFragment(this.secondFragment, this.fragmentTransaction);
            hideFragment(this.thirdFragment, this.fragmentTransaction);
            FirstFragment firstFragment2 = this.firstFragment;
            if (firstFragment2 == null) {
                this.firstFragment = new FirstFragment();
                this.fragmentTransaction.add(R.id.main_content, this.firstFragment);
            } else if (firstFragment2.isHidden()) {
                this.fragmentTransaction.show(this.firstFragment);
            }
            if (this.firstFragmentListener == null) {
                this.firstFragmentListener = this.firstFragment;
            }
        } else if (i == 1) {
            hideFragment(this.firstFragment, this.fragmentTransaction);
            hideFragment(this.thirdFragment, this.fragmentTransaction);
            SecondFragment secondFragment = this.secondFragment;
            if (secondFragment == null) {
                this.secondFragment = new SecondFragment();
                this.fragmentTransaction.add(R.id.main_content, this.secondFragment);
            } else if (secondFragment.isHidden()) {
                this.fragmentTransaction.show(this.secondFragment);
            }
        } else if (i == 2) {
            hideFragment(this.firstFragment, this.fragmentTransaction);
            hideFragment(this.secondFragment, this.fragmentTransaction);
            ThirdFragment thirdFragment2 = this.thirdFragment;
            if (thirdFragment2 == null) {
                this.thirdFragment = new ThirdFragment();
                this.fragmentTransaction.add(R.id.main_content, this.thirdFragment);
            } else if (thirdFragment2.isHidden()) {
                this.fragmentTransaction.show(this.thirdFragment);
                this.thirdFragment.refreshMoreModel();
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
                    MainActivity.this.enableBluetooth();
                } else {
                    MainActivity.this.bleDialog();
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

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            MainActivity mainActivity = (MainActivity) this.weakReference.get();
            if (message.what == 101) {
                Bundle bundle = (Bundle) message.obj;
                mainActivity.modifyOtaStatus1(bundle.getString(GlobalVariable.version), bundle.getInt(GlobalVariable.type));
            } else if (message.what == 102) {
                Bundle bundle2 = (Bundle) message.obj;
                mainActivity.modifyOtaStatus2(bundle2.getString(GlobalVariable.version), bundle2.getInt(GlobalVariable.type));
            } else if (message.what == 103) {
                mainActivity.refreshTabBar();
            }
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
        this.enableBleCASLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda22
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$1((Map) obj);
            }
        });
        this.enableBleLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda23
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$2((ActivityResult) obj);
            }
        });
        this.locationServiceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((ActivityResult) obj);
            }
        });
        this.locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$4((Map) obj);
            }
        });
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda3
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.lambda$registerActivityResultLauncher$5((ActivityResult) obj);
            }
        });
        this.QRPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda4
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$6((Map) obj);
            }
        });
        this.pickJsonFileLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$7((Uri) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$1(Map map) {
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
    public /* synthetic */ void lambda$registerActivityResultLauncher$2(ActivityResult activityResult) {
        if (!GlobalBluetooth.getInstance().isBleEnable()) {
            bleDialog();
        } else {
            GlobalBluetooth.getInstance().broadcastAppTimeSync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$3(ActivityResult activityResult) {
        if (Tool.checkLocationServiceEnable(this)) {
            return;
        }
        GlobalToast.showText(getApplicationContext(), R.string.locationServiceRequest, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$4(Map map) {
        if (map.get("android.permission.ACCESS_FINE_LOCATION") == null || map.get("android.permission.ACCESS_COARSE_LOCATION") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_FINE_LOCATION"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_COARSE_LOCATION"))).equals(true)) {
            if (this.firstFragmentListener != null) {
                if (this.firstFragment.isAdd) {
                    this.firstFragmentListener.toScanDevice();
                    return;
                }
                Intent intent = new Intent(this, (Class<?>) GroupDevicesActivity.class);
                intent.putExtra(GlobalVariable.toGroupDevice, this.firstFragment.isNewIn);
                startActivity(intent);
                return;
            }
            return;
        }
        locationDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$6(Map map) {
        if (map.get("android.permission.CAMERA") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.CAMERA"))).equals(true)) {
                qrScanDevice();
            } else {
                qrPermissionDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$7(Uri uri) {
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
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity.1
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.finish();
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
                if (this.firstFragmentListener != null) {
                    if (this.firstFragment.isAdd) {
                        this.firstFragmentListener.toScanDevice();
                        return;
                    }
                    Intent intent = new Intent(this, (Class<?>) GroupDevicesActivity.class);
                    intent.putExtra(GlobalVariable.toGroupDevice, this.firstFragment.isNewIn);
                    startActivity(intent);
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
            this.needBleCASAlertDialog = new AlertDialog.Builder(this).setMessage(R.string.needBleAdvertise).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda20
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$needBleCASDialog$8(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda21
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$needBleCASDialog$9(dialogInterface, i);
                }
            }).create();
        }
        this.needBleCASAlertDialog.show();
        this.needBleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$needBleCASDialog$8(DialogInterface dialogInterface, int i) {
        enableBleCAS();
        this.needBleCASAlertDialog.cancel();
        this.needBleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$needBleCASDialog$9(DialogInterface dialogInterface, int i) {
        this.needBleCASAlertDialog.cancel();
        this.needBleCASAlertDialog = null;
    }

    public void bleCASDialog() {
        if (this.bleCASAlertDialog == null) {
            this.bleCASAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.bleCA).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda17
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$10(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda18
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$11(dialogInterface, i);
                }
            }).create();
        }
        this.bleCASAlertDialog.show();
        this.bleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$10(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$11(DialogInterface dialogInterface, int i) {
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bleDialog() {
        if (this.bleAlertDialog == null) {
            this.bleAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.enableBle).setMessage(R.string.enableBleMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleDialog$12(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleDialog$13(dialogInterface, i);
                }
            }).create();
        }
        this.bleAlertDialog.show();
        this.bleAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleDialog$12(DialogInterface dialogInterface, int i) {
        enableBle();
        this.bleAlertDialog.cancel();
        this.bleAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleDialog$13(DialogInterface dialogInterface, int i) {
        this.bleAlertDialog.cancel();
        this.bleAlertDialog = null;
        bleDialog();
    }

    private void locationServiceDialog() {
        if (this.locationServiceAlertDialog == null) {
            this.locationServiceAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationServiceRequest).setMessage(R.string.locationServiceRequestMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$14(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda16
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$15(dialogInterface, i);
                }
            }).create();
        }
        this.locationServiceAlertDialog.show();
        this.locationServiceAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$14(DialogInterface dialogInterface, int i) {
        enableLocationService();
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$15(DialogInterface dialogInterface, int i) {
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationAddressRequestMessage).setMessage(R.string.locationAddressRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda14
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$16(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda15
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$17(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$16(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$17(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    public void qrDialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.qrCodeRequestMessage1).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrDialog$18(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrDialog$19(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrDialog$18(DialogInterface dialogInterface, int i) {
        toScanDevice();
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrDialog$19(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    private void qrPermissionDialog() {
        if (this.qrPermissionAlertDialog == null) {
            this.qrPermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.qrCodeRequestMessage).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrPermissionDialog$20(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda13
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$qrPermissionDialog$21(dialogInterface, i);
                }
            }).create();
        }
        this.qrPermissionAlertDialog.show();
        this.qrPermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrPermissionDialog$20(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.qrPermissionAlertDialog.cancel();
        this.qrPermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$qrPermissionDialog$21(DialogInterface dialogInterface, int i) {
        this.qrPermissionAlertDialog.cancel();
        this.qrPermissionAlertDialog = null;
    }

    private void handleJsonFile(Uri uri) {
        Uri uri2;
        int columnIndex;
        if (Objects.equals(uri.getScheme(), "content")) {
            uri2 = uri;
            Cursor cursorQuery = getContentResolver().query(uri2, null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst() && (columnIndex = cursorQuery.getColumnIndex("_display_name")) != -1 && !cursorQuery.getString(columnIndex).contains(GlobalVariable.EXPORT_DIRECTORY)) {
                        Toast.makeText(getApplicationContext(), R.string.errorFile, 1).show();
                        if (cursorQuery != null) {
                            cursorQuery.close();
                            return;
                        }
                        return;
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } else {
            uri2 = uri;
        }
        if (Objects.equals(uri2.getScheme(), "file") && !new File((String) Objects.requireNonNull(uri2.getPath())).getName().contains(GlobalVariable.EXPORT_DIRECTORY)) {
            Toast.makeText(getApplicationContext(), R.string.errorFile, 1).show();
            return;
        }
        try {
            InputStream inputStreamOpenInputStream = getContentResolver().openInputStream(uri2);
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

    private boolean hasNeedOTA() {
        return !this.realm.where(BleDevice.class).notEqualTo("otaTag", (Integer) 0).findAll().isEmpty();
    }

    private void compareVersion() {
        if (this.phoneType.getPhoneType() != 1) {
            return;
        }
        RealmResults realmResultsFindAll = this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).findAll();
        List<BleDevice> list = this.bleDeviceList;
        if (list == null) {
            this.bleDeviceList = new ArrayList();
        } else {
            list.clear();
        }
        if (realmResultsFindAll.isEmpty()) {
            return;
        }
        this.deviceMap.clear();
        this.bleDeviceList.addAll(realmResultsFindAll);
        for (BleDevice bleDevice : this.bleDeviceList) {
            String version = bleDevice.getVersion();
            if (Tool.getVersionType(version, 4) >= 70 && Tool.getVersionType(version, 0) == 6) {
                int type = bleDevice.getType();
                Integer numValueOf = Integer.valueOf(type);
                numValueOf.getClass();
                String strValueOf = type == 43050 ? version : String.valueOf(numValueOf);
                numValueOf.getClass();
                if (type == 43050) {
                    version = String.valueOf(numValueOf);
                }
                if (!this.deviceMap.containsKey(strValueOf)) {
                    this.deviceMap.put(strValueOf, version);
                }
            }
        }
        if (this.deviceMap.isEmpty()) {
            return;
        }
        this.taskCount = 0;
        this.downloadCount = 0;
        GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.MainActivity$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$compareVersion$22();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$compareVersion$22() {
        int i;
        String str;
        int versionType;
        for (String str2 : this.deviceMap.keySet()) {
            if (Objects.equals(str2, String.valueOf(43051))) {
                i = Integer.parseInt(str2);
                str2 = this.deviceMap.get(str2);
                str = this.fileNameArr[0];
            } else if (Objects.equals(str2, String.valueOf(43169))) {
                i = Integer.parseInt(str2);
                str2 = this.deviceMap.get(str2);
                str = this.fileNameArr[1];
            } else {
                i = Integer.parseInt((String) Objects.requireNonNull(this.deviceMap.get(str2)));
                if (Tool.getVersionType(str2, 1) < 16) {
                    str = this.fileNameArr[Tool.getVersionType(str2, 1) + 1];
                    versionType = Tool.getVersionType(str2, 1);
                    if (str2 != null || str == null) {
                        taskCountAction();
                    } else {
                        String areaHost = Tool.getAreaHost();
                        DevOtaCloudHelper.OtaVersionResult devOtaVersion = DevOtaCloudHelper.getDevOtaVersion(areaHost, GlobalVariable.LID, i, versionType, str2);
                        if (devOtaVersion.status == 0 && devOtaVersion.data != null) {
                            String[] strArrSplit = devOtaVersion.data.version.split("\\.");
                            if (Integer.parseInt(strArrSplit[1]) == versionType) {
                                int versionType2 = Tool.getVersionType(str2, 4);
                                int i2 = Integer.parseInt(strArrSplit[0]);
                                if (i2 > versionType2) {
                                    File appFile = Tool.getAppFile(getApplicationContext());
                                    String str3 = str + "_" + versionType2 + GlobalVariable.ZIP;
                                    File file = new File(appFile, str3);
                                    File file2 = new File(appFile, str + "_" + i2 + GlobalVariable.ZIP);
                                    if (!file2.exists()) {
                                        if (file.exists()) {
                                            Tool.deleteFile(this, str3);
                                        }
                                        if (DevOtaCloudHelper.downloadOtaZip(areaHost, GlobalVariable.LID, devOtaVersion.data, file2.getAbsolutePath()) == 0) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString(GlobalVariable.version, str2);
                                            bundle.putInt(GlobalVariable.type, i);
                                            MyHandler myHandler = this.myHandler;
                                            myHandler.sendMessage(myHandler.obtainMessage(101, bundle));
                                            this.downloadCount++;
                                            taskCountAction();
                                        } else {
                                            taskCountAction();
                                        }
                                    } else {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString(GlobalVariable.version, str2);
                                        bundle2.putInt(GlobalVariable.type, i);
                                        MyHandler myHandler2 = this.myHandler;
                                        myHandler2.sendMessage(myHandler2.obtainMessage(102, bundle2));
                                    }
                                } else {
                                    taskCountAction();
                                }
                            } else {
                                taskCountAction();
                            }
                        } else {
                            taskCountAction();
                        }
                    }
                } else {
                    str = null;
                }
            }
            versionType = 1;
            if (str2 != null) {
            }
            taskCountAction();
        }
    }

    private void taskCountAction() {
        int i = this.taskCount + 1;
        this.taskCount = i;
        if (i == this.deviceMap.size() && this.downloadCount > 0 && this.viewIsAppear) {
            MyHandler myHandler = this.myHandler;
            myHandler.sendMessage(myHandler.obtainMessage(103));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void modifyOtaStatus1(String str, int i) {
        RealmResults realmResultsFindAll = this.realm.where(BleDevice.class).equalTo(GlobalVariable.type, Integer.valueOf(i)).equalTo(GlobalVariable.version, str).findAll();
        this.realm.beginTransaction();
        Iterator it = realmResultsFindAll.iterator();
        while (it.hasNext()) {
            ((BleDevice) it.next()).setOtaTag(1);
        }
        this.realm.commitTransaction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void modifyOtaStatus2(String str, int i) {
        RealmResults<BleDevice> realmResultsFindAll = this.realm.where(BleDevice.class).equalTo(GlobalVariable.type, Integer.valueOf(i)).equalTo(GlobalVariable.version, str).findAll();
        this.realm.beginTransaction();
        for (BleDevice bleDevice : realmResultsFindAll) {
            if (bleDevice.getOtaTag() != 1) {
                bleDevice.setOtaTag(1);
                this.downloadCount++;
            }
        }
        this.realm.commitTransaction();
        taskCountAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTabBar() {
        if (hasNeedOTA()) {
            this.bottomNavigationBar.removeItem(this.moreItem);
            BottomNavigationItem inactiveIconResource = new BottomNavigationItem(R.drawable.more_red_select, R.string.More).setInactiveIconResource(R.drawable.more_red_normal);
            this.moreItem = inactiveIconResource;
            this.bottomNavigationBar.addItem(inactiveIconResource).setFirstSelectedPosition(this.currentTabPosition).initialise();
        }
    }
}

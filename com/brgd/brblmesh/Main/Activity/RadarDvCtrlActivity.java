package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.alibaba.fastjson.asm.Opcodes;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Fragment.RadarColorFragment;
import com.brgd.brblmesh.Main.Fragment.RadarSetFragment;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RadarDvCtrlActivity extends FatherActivity implements BottomNavigationBar.OnTabSelectedListener {
    AlertDialog alertDialog;
    ImageView backView;
    AlertDialog bleCASAlertDialog;
    BottomNavigationBar bottomNavigationBar;
    BottomNavigationItem colorItem;
    public int currentTabPosition;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    private Dialog loadingDialog;
    AlertDialog locationAlertDialog;
    ActivityResultLauncher<String[]> locationPermissionLauncher;
    AlertDialog locationServiceAlertDialog;
    ActivityResultLauncher<Intent> locationServiceLauncher;
    public AlertDialog mcPermissionAlertDialog;
    ActivityResultLauncher<String[]> mcPermissionLauncher;
    public AlertDialog musicPermissionAlertDialog;
    ActivityResultLauncher<String[]> musicPermissionLauncher;
    private RadarColorFragment radarColorFragment;
    private RadarSetFragment radarSetFragment;
    private Realm realm;
    TextView saveTextView;
    BottomNavigationItem setItem;
    ImageView titleBgView;
    private TextView titleView;
    public int viewType = 43050;
    private int groupId = -1;
    private int tempGroupId = -1;
    private int type = -1;
    private int addr = -1;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.ctrl_back) {
                RadarDvCtrlActivity.this.back();
            } else if (id == R.id.ctrl_save) {
                RadarDvCtrlActivity.this.saveRadarAction();
            }
        }
    };
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);
    public boolean hasRequestLocation = false;

    static /* synthetic */ void lambda$registerActivityResultLauncher$2(ActivityResult activityResult) {
    }

    private void radarSetCmd(int i, int i2, int i3, int i4) {
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
        setContentView(R.layout.activity_radar_dv_ctrl);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra(GlobalVariable.groupToCtrl);
        if (bundleExtra != null) {
            this.groupId = bundleExtra.getInt(GlobalVariable.groupId);
            this.type = bundleExtra.getInt(GlobalVariable.type);
            this.tempGroupId = bundleExtra.getInt(GlobalVariable.tempGroupId);
            this.viewType = bundleExtra.getInt(GlobalVariable.viewType);
        } else {
            Bundle bundleExtra2 = intent.getBundleExtra(GlobalVariable.toCtrl);
            this.addr = ((Bundle) Objects.requireNonNull(bundleExtra2)).getInt(GlobalVariable.ctrlAddr);
            this.viewType = bundleExtra2.getInt(GlobalVariable.viewType);
        }
        initView();
        registerActivityResultLauncher();
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
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initView() {
        this.titleBgView = (ImageView) findViewById(R.id.ctrl_title_bg);
        ImageView imageView = (ImageView) findViewById(R.id.ctrl_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.ctrl_save);
        this.saveTextView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        this.titleView = (TextView) findViewById(R.id.ctrl_title);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.ctrl_bottom_navigation_bar);
        this.bottomNavigationBar = bottomNavigationBar;
        bottomNavigationBar.setMode(1);
        this.bottomNavigationBar.setBarBackgroundColor(R.color.colorWhite);
        this.bottomNavigationBar.setActiveColor(R.color.colorSelect);
        this.bottomNavigationBar.setInActiveColor(R.color.colorText);
        this.colorItem = new BottomNavigationItem(R.drawable.color_select, R.string.Colour).setInactiveIconResource(R.drawable.color_normal);
        this.setItem = new BottomNavigationItem(R.drawable.mod_select, R.string.Setting).setInactiveIconResource(R.drawable.mod_normal);
        ViewGroup.LayoutParams layoutParams = this.bottomNavigationBar.getChildAt(0).getLayoutParams();
        layoutParams.height = -1;
        this.bottomNavigationBar.getChildAt(0).setLayoutParams(layoutParams);
        this.bottomNavigationBar.addItem(this.colorItem).addItem(this.setItem).setFirstSelectedPosition(0).initialise();
        Tool.setBottomNavigationItem(this, this.bottomNavigationBar, 10, 25, 14);
        this.bottomNavigationBar.setTabSelectedListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.fragmentManager = supportFragmentManager;
        this.fragmentTransaction = supportFragmentManager.beginTransaction();
        RadarColorFragment radarColorFragment = new RadarColorFragment();
        this.radarColorFragment = radarColorFragment;
        radarColorFragment.addr = this.addr;
        int i = this.groupId;
        if (i != -1 || this.tempGroupId != -1) {
            this.radarColorFragment.groupId = i;
            this.radarColorFragment.tempGroupId = this.tempGroupId;
            this.radarColorFragment.type = this.type;
        }
        this.fragmentTransaction.add(R.id.ctrl_main_content, this.radarColorFragment);
        this.fragmentTransaction.commit();
        this.titleView.setText(R.string.Colour);
    }

    private void hideFragment(Fragment fragment, FragmentTransaction fragmentTransaction) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
    public void onTabSelected(int i) {
        if (Tool.bleNotOnToast(getApplicationContext())) {
            back();
            return;
        }
        this.currentTabPosition = i;
        this.fragmentTransaction = null;
        FragmentTransaction fragmentTransactionBeginTransaction = this.fragmentManager.beginTransaction();
        this.fragmentTransaction = fragmentTransactionBeginTransaction;
        if (i == 0) {
            hideFragment(this.radarSetFragment, fragmentTransactionBeginTransaction);
            RadarColorFragment radarColorFragment = this.radarColorFragment;
            if (radarColorFragment == null) {
                RadarColorFragment radarColorFragment2 = new RadarColorFragment();
                this.radarColorFragment = radarColorFragment2;
                radarColorFragment2.addr = this.addr;
                int i2 = this.groupId;
                if (i2 != -1 || this.tempGroupId != -1) {
                    this.radarColorFragment.groupId = i2;
                    this.radarColorFragment.tempGroupId = this.tempGroupId;
                    this.radarColorFragment.type = this.type;
                }
                this.fragmentTransaction.add(R.id.ctrl_main_content, this.radarColorFragment);
            } else if (radarColorFragment.isHidden()) {
                this.fragmentTransaction.show(this.radarColorFragment);
            }
            this.titleView.setText(R.string.Colour);
            this.saveTextView.setVisibility(4);
        } else if (i == 1) {
            hideFragment(this.radarColorFragment, fragmentTransactionBeginTransaction);
            RadarSetFragment radarSetFragment = this.radarSetFragment;
            if (radarSetFragment == null) {
                RadarSetFragment radarSetFragment2 = new RadarSetFragment();
                this.radarSetFragment = radarSetFragment2;
                radarSetFragment2.addr = this.addr;
                int i3 = this.groupId;
                if (i3 != -1 || this.tempGroupId != -1) {
                    this.radarSetFragment.groupId = i3;
                    this.radarSetFragment.tempGroupId = this.tempGroupId;
                    this.radarSetFragment.type = this.type;
                }
                this.fragmentTransaction.add(R.id.ctrl_main_content, this.radarSetFragment);
            } else if (radarSetFragment.isHidden()) {
                this.fragmentTransaction.show(this.radarSetFragment);
            }
            this.titleView.setText(R.string.Setting);
            this.saveTextView.setVisibility(0);
        }
        this.fragmentTransaction.commit();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void back() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveRadarAction() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity.2
            @Override // java.lang.Runnable
            public void run() {
                loadDialogUtils.closeDialog(RadarDvCtrlActivity.this.loadingDialog);
                RadarDvCtrlActivity.this.loadingDialog = null;
                RadarDvCtrlActivity.this.finish();
            }
        }, 1000L);
        if (this.radarSetFragment.enableOnOff.isSelected()) {
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_ENABLE_STR, true);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_LUX_INDEX, Integer.valueOf(this.radarSetFragment.currentLuxIndex + 1));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_ENABLE_LIGHTNESS, Integer.valueOf(this.radarSetFragment.currentBrightnessProgress1));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_ENABLE_TIME_INDEX, Integer.valueOf(this.radarSetFragment.currentDurationIndex + 1));
            if (this.radarSetFragment.noBodyOnOff.isSelected()) {
                SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_NOBODY_ACTION_STR, Integer.valueOf(this.radarSetFragment.currentBrightnessProgress2));
            } else {
                SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_NOBODY_ACTION_STR, 0);
            }
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_DISABLE_LIGHTNESS, 0);
            radarSetCmd(1, (int) ((this.radarSetFragment.currentBrightnessProgress1 / 100.0f) * 127.0f), this.radarSetFragment.noBodyOnOff.isSelected() ? (int) ((this.radarSetFragment.currentBrightnessProgress2 / 100.0f) * 127.0f) : 0, new int[]{15, 30, 60, 90, 120, Opcodes.GETFIELD, 300}[this.radarSetFragment.currentDurationIndex]);
            return;
        }
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_ENABLE_STR, false);
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_DISABLE_LIGHTNESS, Integer.valueOf(this.radarSetFragment.currentBrightnessProgress));
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_LUX_INDEX, 0);
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_ENABLE_LIGHTNESS, 0);
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_ENABLE_TIME_INDEX, 0);
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_NOBODY_ACTION_STR, 0);
        radarSetCmd(0, 0, 0, 0);
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

    private void musicPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            this.musicPermissionLauncher.launch(new String[]{"android.permission.READ_MEDIA_AUDIO", "android.permission.RECORD_AUDIO"});
        } else {
            this.musicPermissionLauncher.launch(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"});
        }
    }

    private void mcPermission() {
        this.mcPermissionLauncher.launch(new String[]{"android.permission.RECORD_AUDIO"});
    }

    private void registerActivityResultLauncher() {
        this.locationServiceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda13
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$0((ActivityResult) obj);
            }
        });
        this.locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda14
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$1((Map) obj);
            }
        });
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda15
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                RadarDvCtrlActivity.lambda$registerActivityResultLauncher$2((ActivityResult) obj);
            }
        });
        this.musicPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda16
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((Map) obj);
            }
        });
        this.mcPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$4((Map) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$0(ActivityResult activityResult) {
        if (Tool.checkLocationServiceEnable(this)) {
            return;
        }
        GlobalToast.showText(getApplicationContext(), R.string.locationServiceRequest, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$1(Map map) {
        if (map.get("android.permission.ACCESS_FINE_LOCATION") == null || map.get("android.permission.ACCESS_COARSE_LOCATION") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_FINE_LOCATION"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_COARSE_LOCATION"))).equals(true)) {
            return;
        }
        locationDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$3(Map map) {
        if (Build.VERSION.SDK_INT >= 33) {
            if (map.get("android.permission.READ_MEDIA_AUDIO") == null || map.get("android.permission.RECORD_AUDIO") == null) {
                return;
            }
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_MEDIA_AUDIO"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.RECORD_AUDIO"))).equals(true)) {
                return;
            }
            musicPermissionDialog();
            return;
        }
        if (map.get("android.permission.READ_EXTERNAL_STORAGE") == null || map.get("android.permission.RECORD_AUDIO") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_EXTERNAL_STORAGE"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.RECORD_AUDIO"))).equals(true)) {
            return;
        }
        musicPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$4(Map map) {
        if (map.get("android.permission.RECORD_AUDIO") == null || ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.RECORD_AUDIO"))).equals(true)) {
            return;
        }
        mcPermissionDialog();
    }

    public void getLocationPermission() {
        if (Tool.checkLocationServiceEnable(this)) {
            if (Tool.checkLocationPermission() || this.hasRequestLocation) {
                return;
            }
            locationPermission();
            this.hasRequestLocation = true;
            return;
        }
        locationServiceDialog();
    }

    private void locationServiceDialog() {
        if (this.locationServiceAlertDialog == null) {
            this.locationServiceAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationServiceRequest).setMessage(R.string.locationServiceRequestMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$5(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$6(dialogInterface, i);
                }
            }).create();
        }
        this.locationServiceAlertDialog.show();
        this.locationServiceAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$5(DialogInterface dialogInterface, int i) {
        enableLocationService();
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$6(DialogInterface dialogInterface, int i) {
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationAddressRequestMessage).setMessage(R.string.locationAddressRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$7(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$8(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$7(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$8(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    private void dialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.locationAddressRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$9(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$10(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$9(DialogInterface dialogInterface, int i) {
        getLocationPermission();
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$10(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    public void musicPermissionDialog() {
        if (this.musicPermissionAlertDialog == null) {
            this.musicPermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.readRequestMessage).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$musicPermissionDialog$11(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$musicPermissionDialog$12(dialogInterface, i);
                }
            }).create();
        }
        this.musicPermissionAlertDialog.show();
        this.musicPermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$musicPermissionDialog$11(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.musicPermissionAlertDialog.cancel();
        this.musicPermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$musicPermissionDialog$12(DialogInterface dialogInterface, int i) {
        this.musicPermissionAlertDialog.cancel();
        this.musicPermissionAlertDialog = null;
    }

    public void mcPermissionDialog() {
        if (this.mcPermissionAlertDialog == null) {
            this.mcPermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.microPhoneRequestMessage).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$mcPermissionDialog$13(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$mcPermissionDialog$14(dialogInterface, i);
                }
            }).create();
        }
        this.mcPermissionAlertDialog.show();
        this.mcPermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mcPermissionDialog$13(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.mcPermissionAlertDialog.cancel();
        this.mcPermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mcPermissionDialog$14(DialogInterface dialogInterface, int i) {
        this.mcPermissionAlertDialog.cancel();
        this.mcPermissionAlertDialog = null;
    }

    public void bleCASDialog() {
        if (this.bleCASAlertDialog == null) {
            this.bleCASAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.bleCA).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$15(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$16(dialogInterface, i);
                }
            }).create();
        }
        this.bleCASAlertDialog.show();
        this.bleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$15(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$16(DialogInterface dialogInterface, int i) {
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        back();
        return false;
    }
}

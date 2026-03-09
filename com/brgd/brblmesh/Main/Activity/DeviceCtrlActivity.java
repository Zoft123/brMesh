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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.MusicThemeAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.MusicTheme;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Fragment.ColourFragment;
import com.brgd.brblmesh.Main.Fragment.ModFragment;
import com.brgd.brblmesh.Main.Fragment.MusicFragment;
import com.brgd.brblmesh.Main.Fragment.RhythmFragment;
import com.brgd.brblmesh.Main.Fragment.TimerFragment;
import com.brgd.brblmesh.Main.Interface.ColourFragmentListener;
import com.brgd.brblmesh.Main.Interface.MusicFragmentListener;
import com.brgd.brblmesh.Main.Interface.RhythmFragmentListener;
import com.brgd.brblmesh.Main.Interface.TimerFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DeviceCtrlActivity extends FatherActivity implements BottomNavigationBar.OnTabSelectedListener {
    AlertDialog alertDialog;
    ImageView backView;
    AlertDialog bleCASAlertDialog;
    private BleDevice bleDevice;
    private List<BleDevice> bleDeviceList;
    BottomNavigationBar bottomNavigationBar;
    BottomNavigationItem colorItem;
    private ColourFragment colourFragment;
    private ColourFragmentListener colourFragmentListener;
    ConstraintLayout constraintLayout;
    public int currentTabPosition;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private TextView deviceNameText;
    ImageView dismissView;
    private ImageView editView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    private boolean isSupportTimerStatus;
    private View layoutView;
    private Dialog loadingDialog;
    AlertDialog locationAlertDialog;
    ActivityResultLauncher<String[]> locationPermissionLauncher;
    AlertDialog locationServiceAlertDialog;
    ActivityResultLauncher<Intent> locationServiceLauncher;
    public AlertDialog mcPermissionAlertDialog;
    ActivityResultLauncher<String[]> mcPermissionLauncher;
    private ModFragment modFragment;
    BottomNavigationItem modItem;
    private MusicFragment musicFragment;
    private MusicFragmentListener musicFragmentListener;
    BottomNavigationItem musicItem;
    public AlertDialog musicPermissionAlertDialog;
    ActivityResultLauncher<String[]> musicPermissionLauncher;
    private MusicThemeAdapter musicThemeAdapter;
    private List<MusicTheme> musicThemeList;
    private TextView musicThemeTextView;
    private ConstraintLayout nameBtn;
    private String nameStr;
    private TextView nameText;
    private PopupWindow popupWindow;
    private Realm realm;
    private ConstraintLayout renameLayout;
    private RhythmFragment rhythmFragment;
    private RhythmFragmentListener rhythmFragmentListener;
    BottomNavigationItem rhythmItem;
    TextView saveTextView;
    ImageView saveView;
    RecyclerView themeRecyclerView;
    public ImageView themeView;
    private TimerFragment timerFragment;
    private TimerFragmentListener timerFragmentListener;
    BottomNavigationItem timingItem;
    ImageView titleBgView;
    private TextView titleView;
    public int viewType = 43050;
    private int groupId = -1;
    private int tempGroupId = -1;
    private int type = -1;
    private int addr = -1;
    private int sceneNumber = -1;
    private int currentSelectTab = 0;
    public final int UPDATE_PROGRESS = 101;
    int[] themeArr = {R.string.MyColorTemp, R.string.Hallowmas1, R.string.Hallowmas2, R.string.Hallowmas3, R.string.Hallowmas4, R.string.Hallowmas5};
    private boolean isSupportSleepMod = false;
    private boolean isInclude0912 = false;
    private boolean isSupportModeLightness = false;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity.4
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int iIntValue;
            int id = view.getId();
            if (id == R.id.ctrl_back) {
                DeviceCtrlActivity.this.back();
                return;
            }
            if (id == R.id.ctrl_save) {
                DeviceCtrlActivity.this.saveSceneAction();
                return;
            }
            if (id == R.id.ctrl_edit) {
                if (DeviceCtrlActivity.this.editView.isSelected()) {
                    DeviceCtrlActivity.this.editView.setSelected(false);
                    DeviceCtrlActivity.this.colourFragment.colorPickerLayout.setVisibility(0);
                    DeviceCtrlActivity.this.colourFragment.colorSeekBarLayout.setVisibility(4);
                    return;
                } else {
                    DeviceCtrlActivity.this.editView.setSelected(true);
                    DeviceCtrlActivity.this.colourFragment.colorPickerLayout.setVisibility(4);
                    DeviceCtrlActivity.this.colourFragment.colorSeekBarLayout.setVisibility(0);
                    if (DeviceCtrlActivity.this.colourFragmentListener != null) {
                        DeviceCtrlActivity.this.colourFragmentListener.upDateRgbValue();
                        return;
                    }
                    return;
                }
            }
            if (id == R.id.ctrl_music_theme) {
                if (DeviceCtrlActivity.this.themeView.isSelected()) {
                    DeviceCtrlActivity.this.themeView.setSelected(false);
                    DeviceCtrlActivity.this.popupWindow.dismiss();
                    return;
                }
                DeviceCtrlActivity.this.themeView.setSelected(true);
                DeviceCtrlActivity.this.popupWindow.showAsDropDown(DeviceCtrlActivity.this.titleView, 0, 70);
                if (DeviceCtrlActivity.this.currentTabPosition == 2) {
                    iIntValue = ((Integer) SharePreferenceUtils.get(DeviceCtrlActivity.this.getApplicationContext(), GlobalVariable.musicTheme, -1)).intValue();
                } else {
                    iIntValue = DeviceCtrlActivity.this.currentTabPosition == 3 ? ((Integer) SharePreferenceUtils.get(DeviceCtrlActivity.this.getApplicationContext(), GlobalVariable.rhythmTheme, -1)).intValue() : 0;
                }
                Iterator it = DeviceCtrlActivity.this.musicThemeList.iterator();
                while (it.hasNext()) {
                    ((MusicTheme) it.next()).isSelect = false;
                }
                ((MusicTheme) DeviceCtrlActivity.this.musicThemeList.get(iIntValue)).isSelect = true;
                DeviceCtrlActivity.this.musicThemeAdapter.notifyDataSetChanged();
                DeviceCtrlActivity.this.setThemeViewValue(iIntValue);
                return;
            }
            if (id == R.id.ctrl_nameBtn) {
                DeviceCtrlActivity.this.handleDeviceRename();
                return;
            }
            if (id == R.id.device_rename_delete) {
                DeviceCtrlActivity.this.deviceNameEdit.setText("");
                DeviceCtrlActivity.this.deviceNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.device_rename_save) {
                String string = DeviceCtrlActivity.this.deviceNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                Iterator it2 = DeviceCtrlActivity.this.bleDeviceList.iterator();
                while (it2.hasNext()) {
                    if (strTrim.equalsIgnoreCase(((BleDevice) it2.next()).getName())) {
                        GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.nameExist, 0);
                        return;
                    }
                }
                if (DeviceCtrlActivity.this.bleDevice != null) {
                    DeviceCtrlActivity.this.realm.beginTransaction();
                    DeviceCtrlActivity.this.bleDevice.setName(strTrim);
                    DeviceCtrlActivity.this.realm.commitTransaction();
                }
                DeviceCtrlActivity.this.closeRenameView();
                GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.SaveSuccess, 0);
                DeviceCtrlActivity deviceCtrlActivity = DeviceCtrlActivity.this;
                deviceCtrlActivity.nameStr = deviceCtrlActivity.bleDevice.getName();
                DeviceCtrlActivity.this.nameText.setText(DeviceCtrlActivity.this.nameStr);
                return;
            }
            if (id == R.id.device_rename_dismiss) {
                DeviceCtrlActivity.this.closeRenameView();
            }
        }
    };
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);
    public boolean hasRequestLocation = false;

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
        setContentView(R.layout.activity_device_ctrl);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        this.bleDeviceList = new ArrayList();
        this.bleDeviceList.addAll(this.realm.where(BleDevice.class).findAll());
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra(GlobalVariable.sceneToCtrl);
        Bundle bundleExtra2 = intent.getBundleExtra(GlobalVariable.groupToCtrl);
        if (bundleExtra != null) {
            this.sceneNumber = bundleExtra.getInt(GlobalVariable.sceneNumber);
            this.addr = bundleExtra.getInt(GlobalVariable.sceneDeviceSelect);
            this.viewType = bundleExtra.getInt(GlobalVariable.viewType);
            this.isSupportTimerStatus = bundleExtra.getBoolean(GlobalVariable.timerStatus);
            this.isInclude0912 = bundleExtra.getBoolean(GlobalVariable.isInclude0912);
            this.isSupportModeLightness = bundleExtra.getBoolean(GlobalVariable.S_MODE_LIGHTNESS);
        } else if (bundleExtra2 != null) {
            this.groupId = bundleExtra2.getInt(GlobalVariable.groupId);
            this.type = bundleExtra2.getInt(GlobalVariable.type);
            this.tempGroupId = bundleExtra2.getInt(GlobalVariable.tempGroupId);
            this.viewType = bundleExtra2.getInt(GlobalVariable.viewType);
            this.isSupportTimerStatus = bundleExtra2.getBoolean(GlobalVariable.timerStatus);
            this.nameStr = bundleExtra2.getString(GlobalVariable.name);
            this.isInclude0912 = bundleExtra2.getBoolean(GlobalVariable.isInclude0912);
            this.isSupportModeLightness = bundleExtra2.getBoolean(GlobalVariable.S_MODE_LIGHTNESS);
        } else {
            Bundle bundleExtra3 = intent.getBundleExtra(GlobalVariable.toCtrl);
            this.addr = ((Bundle) Objects.requireNonNull(bundleExtra3)).getInt(GlobalVariable.ctrlAddr);
            this.viewType = bundleExtra3.getInt(GlobalVariable.viewType);
            this.isSupportTimerStatus = bundleExtra3.getBoolean(GlobalVariable.timerStatus);
            this.isSupportSleepMod = bundleExtra3.getBoolean("isSleep");
            this.bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
            this.isInclude0912 = bundleExtra3.getBoolean(GlobalVariable.isInclude0912);
            this.isSupportModeLightness = bundleExtra3.getBoolean(GlobalVariable.S_MODE_LIGHTNESS);
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
        ImageView imageView2 = (ImageView) findViewById(R.id.ctrl_music_theme);
        this.themeView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView3 = (ImageView) findViewById(R.id.ctrl_edit);
        this.editView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.ctrl_nameBtn);
        this.nameBtn = constraintLayout;
        constraintLayout.setOnClickListener(this.disDoubleClickListener);
        this.nameText = (TextView) findViewById(R.id.ctrl_nameText);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.ctrl_bottom_navigation_bar);
        this.bottomNavigationBar = bottomNavigationBar;
        bottomNavigationBar.setMode(1);
        this.bottomNavigationBar.setBarBackgroundColor(R.color.colorWhite);
        this.bottomNavigationBar.setActiveColor(R.color.colorSelect);
        this.bottomNavigationBar.setInActiveColor(R.color.colorText);
        this.colorItem = new BottomNavigationItem(R.drawable.color_select, R.string.Colour).setInactiveIconResource(R.drawable.color_normal);
        this.modItem = new BottomNavigationItem(R.drawable.mod_select, R.string.Mod).setInactiveIconResource(R.drawable.mod_normal);
        ViewGroup.LayoutParams layoutParams = this.bottomNavigationBar.getChildAt(0).getLayoutParams();
        layoutParams.height = -1;
        this.bottomNavigationBar.getChildAt(0).setLayoutParams(layoutParams);
        if (this.sceneNumber != -1) {
            int i = this.viewType;
            if (i == 43049 || i == 43051) {
                this.bottomNavigationBar.addItem(this.colorItem).setFirstSelectedPosition(0).initialise();
            } else {
                this.bottomNavigationBar.addItem(this.colorItem).addItem(this.modItem).setFirstSelectedPosition(0).initialise();
            }
            this.saveTextView.setVisibility(0);
            this.titleView.setVisibility(4);
        } else if (this.groupId != -1 || this.tempGroupId != -1) {
            int i2 = this.viewType;
            if (i2 == 43049 || i2 == 43051) {
                this.bottomNavigationBar.addItem(this.colorItem).setFirstSelectedPosition(0).initialise();
            } else {
                this.musicItem = new BottomNavigationItem(R.drawable.music_select, R.string.Music).setInactiveIconResource(R.drawable.music_normal);
                this.rhythmItem = new BottomNavigationItem(R.drawable.micro_select, R.string.Rhythm).setInactiveIconResource(R.drawable.micro_normal);
                this.bottomNavigationBar.addItem(this.colorItem).addItem(this.modItem).addItem(this.musicItem).addItem(this.rhythmItem).setFirstSelectedPosition(0).initialise();
            }
            this.titleView.setText(this.nameStr);
        } else {
            this.timingItem = new BottomNavigationItem(R.drawable.timer_select, R.string.Timer).setInactiveIconResource(R.drawable.timer_normal);
            int i3 = this.viewType;
            if ((i3 == 43049 || i3 == 43051) && !this.isSupportSleepMod) {
                this.bottomNavigationBar.addItem(this.colorItem).addItem(this.timingItem).setFirstSelectedPosition(0).initialise();
            } else if ((i3 == 43049 || i3 == 43051) && this.isSupportSleepMod) {
                this.bottomNavigationBar.addItem(this.colorItem).addItem(this.modItem).addItem(this.timingItem).setFirstSelectedPosition(0).initialise();
            } else {
                this.musicItem = new BottomNavigationItem(R.drawable.music_select, R.string.Music).setInactiveIconResource(R.drawable.music_normal);
                this.rhythmItem = new BottomNavigationItem(R.drawable.micro_select, R.string.Rhythm).setInactiveIconResource(R.drawable.micro_normal);
                this.bottomNavigationBar.addItem(this.colorItem).addItem(this.modItem).addItem(this.musicItem).addItem(this.rhythmItem).addItem(this.timingItem).setFirstSelectedPosition(0).initialise();
            }
            this.titleView.setVisibility(4);
            this.nameBtn.setVisibility(0);
            this.nameText.setText(this.bleDevice.getName());
        }
        Tool.setBottomNavigationItem(this, this.bottomNavigationBar, 10, 25, 14);
        this.bottomNavigationBar.setTabSelectedListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.fragmentManager = supportFragmentManager;
        this.fragmentTransaction = supportFragmentManager.beginTransaction();
        ColourFragment colourFragment = new ColourFragment();
        this.colourFragment = colourFragment;
        if (this.colourFragmentListener == null) {
            this.colourFragmentListener = colourFragment;
        }
        int i4 = this.sceneNumber;
        if (i4 != -1) {
            colourFragment.sceneNumber = i4;
        }
        this.colourFragment.addr = this.addr;
        int i5 = this.groupId;
        if (i5 != -1 || this.tempGroupId != -1) {
            this.colourFragment.groupId = i5;
            this.colourFragment.tempGroupId = this.tempGroupId;
            this.colourFragment.type = this.type;
        }
        this.fragmentTransaction.add(R.id.ctrl_main_content, this.colourFragment);
        this.fragmentTransaction.commit();
        int i6 = this.viewType;
        if (i6 == 43049 || i6 == 43051) {
            this.editView.setVisibility(4);
        } else {
            this.editView.setVisibility(0);
            this.editView.setImageResource(R.drawable.rgb_switch_bt);
        }
        this.currentSelectTab = 0;
        this.constraintLayout = (ConstraintLayout) findViewById(R.id.music_theme_layout);
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.music_theme, (ViewGroup) this.constraintLayout, false);
        this.layoutView = viewInflate;
        this.musicThemeTextView = (TextView) viewInflate.findViewById(R.id.music_theme_text);
        this.musicThemeList = new ArrayList();
        this.themeRecyclerView = (RecyclerView) this.layoutView.findViewById(R.id.music_theme_recyclerView);
        MusicThemeAdapter musicThemeAdapter = new MusicThemeAdapter(this, this.musicThemeList);
        this.musicThemeAdapter = musicThemeAdapter;
        this.themeRecyclerView.setAdapter(musicThemeAdapter);
        this.themeRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.musicThemeAdapter.setOnItemClickListener(new MusicThemeAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.MusicThemeAdapter.OnItemClickListener
            public void onClick(View view, int i7) {
                Iterator it = DeviceCtrlActivity.this.musicThemeList.iterator();
                while (it.hasNext()) {
                    ((MusicTheme) it.next()).isSelect = false;
                }
                ((MusicTheme) DeviceCtrlActivity.this.musicThemeList.get(i7)).isSelect = true;
                DeviceCtrlActivity.this.musicThemeAdapter.notifyDataSetChanged();
                if (DeviceCtrlActivity.this.currentTabPosition == 2) {
                    SharePreferenceUtils.put(DeviceCtrlActivity.this.getApplicationContext(), GlobalVariable.musicTheme, Integer.valueOf(i7));
                    DeviceCtrlActivity.this.musicFragment.currentThemeIndex = i7;
                } else if (DeviceCtrlActivity.this.currentTabPosition == 3) {
                    SharePreferenceUtils.put(DeviceCtrlActivity.this.getApplicationContext(), GlobalVariable.rhythmTheme, Integer.valueOf(i7));
                    DeviceCtrlActivity.this.rhythmFragment.currentThemeIndex = i7;
                }
                DeviceCtrlActivity.this.setThemeViewValue(i7);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.MusicThemeAdapter.OnItemClickListener
            public void editClick(View view, int i7) {
                Intent intent = new Intent(DeviceCtrlActivity.this, (Class<?>) MusicThemeActivity.class);
                Bundle bundle = new Bundle();
                if (DeviceCtrlActivity.this.currentTabPosition == 2) {
                    bundle.putInt(GlobalVariable.modNumber, i7);
                    if (DeviceCtrlActivity.this.musicFragmentListener != null) {
                        DeviceCtrlActivity.this.musicFragmentListener.endMusic();
                    }
                } else if (DeviceCtrlActivity.this.currentTabPosition == 3) {
                    bundle.putInt(GlobalVariable.modNumber, i7 + 6);
                    if (DeviceCtrlActivity.this.rhythmFragmentListener != null) {
                        DeviceCtrlActivity.this.rhythmFragmentListener.stopVoice();
                    }
                }
                bundle.putInt(GlobalVariable.addr, DeviceCtrlActivity.this.addr);
                bundle.putInt(GlobalVariable.groupId, DeviceCtrlActivity.this.groupId);
                bundle.putInt(GlobalVariable.tempGroupId, DeviceCtrlActivity.this.tempGroupId);
                bundle.putInt(GlobalVariable.type, DeviceCtrlActivity.this.type);
                intent.putExtra(GlobalVariable.toMusicThemeCtrl, bundle);
                DeviceCtrlActivity.this.startActivity(intent);
            }
        });
        for (int i7 = 0; i7 < this.themeArr.length; i7++) {
            MusicTheme musicTheme = new MusicTheme();
            musicTheme.index = i7;
            musicTheme.isSelect = false;
            this.musicThemeList.add(musicTheme);
        }
        if (((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.musicTheme, -1)).intValue() == -1) {
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.musicTheme, 0);
        }
        if (((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.rhythmTheme, -1)).intValue() == -1) {
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.rhythmTheme, 0);
        }
        this.titleBgView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                DeviceCtrlActivity.this.titleBgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                DeviceCtrlActivity.this.popupWindow = new PopupWindow(DeviceCtrlActivity.this);
                DeviceCtrlActivity.this.popupWindow.setHeight(Tool.dpToPx(DeviceCtrlActivity.this.getApplicationContext(), 480.0f));
                DeviceCtrlActivity.this.popupWindow.setWidth(DeviceCtrlActivity.this.titleBgView.getWidth());
                DeviceCtrlActivity.this.popupWindow.setContentView(DeviceCtrlActivity.this.layoutView);
                DeviceCtrlActivity.this.popupWindow.setTouchable(true);
                DeviceCtrlActivity.this.popupWindow.setFocusable(true);
                DeviceCtrlActivity.this.popupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(DeviceCtrlActivity.this.getApplicationContext(), R.color.colorAlpha));
                DeviceCtrlActivity.this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity.2.1
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        DeviceCtrlActivity.this.themeView.setSelected(false);
                    }
                });
            }
        });
        this.renameLayout = (ConstraintLayout) findViewById(R.id.device_rename_editView);
        this.deviceNameText = (TextView) findViewById(R.id.device_rename_name);
        this.deviceNameEdit = (EditText) findViewById(R.id.device_rename_edit);
        ImageView imageView4 = (ImageView) findViewById(R.id.device_rename_delete);
        this.deviceNameDelete = imageView4;
        imageView4.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i8, int i9, int i10) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i8, int i9, int i10) {
                if (charSequence.length() > 0) {
                    if (DeviceCtrlActivity.this.delete_deviceName_tag) {
                        DeviceCtrlActivity.this.deviceNameDelete.setSelected(true);
                        DeviceCtrlActivity.this.deviceNameDelete.setEnabled(true);
                        DeviceCtrlActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(DeviceCtrlActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                DeviceCtrlActivity.this.deviceNameDelete.setSelected(false);
                DeviceCtrlActivity.this.deviceNameDelete.setEnabled(false);
                DeviceCtrlActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.device_rename_save);
        this.saveView = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView6 = (ImageView) findViewById(R.id.device_rename_dismiss);
        this.dismissView = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceRename() {
        this.deviceNameText.setText(this.bleDevice.getName());
        this.deviceNameEdit.setText(this.bleDevice.getName());
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setThemeViewValue(int i) {
        this.musicThemeTextView.setText(getString(R.string.Theme) + getString(this.themeArr[i]));
    }

    private void hideFragment(Fragment fragment, FragmentTransaction fragmentTransaction) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
    public void onTabSelected(int i) {
        ModFragment modFragment;
        if (Tool.bleNotOnToast(getApplicationContext())) {
            back();
            return;
        }
        closeRenameView();
        this.currentTabPosition = i;
        this.fragmentTransaction = null;
        FragmentTransaction fragmentTransactionBeginTransaction = this.fragmentManager.beginTransaction();
        this.fragmentTransaction = fragmentTransactionBeginTransaction;
        if (this.sceneNumber != -1) {
            if (i == 0) {
                hideFragment(this.modFragment, fragmentTransactionBeginTransaction);
                ColourFragment colourFragment = this.colourFragment;
                if (colourFragment == null) {
                    ColourFragment colourFragment2 = new ColourFragment();
                    this.colourFragment = colourFragment2;
                    int i2 = this.sceneNumber;
                    if (i2 != -1) {
                        colourFragment2.sceneNumber = i2;
                    }
                    this.colourFragment.addr = this.addr;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.colourFragment);
                } else if (colourFragment.isHidden()) {
                    this.fragmentTransaction.show(this.colourFragment);
                }
                int i3 = this.viewType;
                if (i3 != 43049 && i3 != 43051) {
                    this.editView.setVisibility(0);
                    this.editView.setImageResource(R.drawable.rgb_switch_bt);
                }
                if (this.colourFragmentListener == null) {
                    this.colourFragmentListener = this.colourFragment;
                }
                if (this.sceneNumber != -1 && (modFragment = this.modFragment) != null && modFragment.currentMod != null) {
                    handleColourAndMod();
                }
            } else if (i == 1) {
                hideFragment(this.colourFragment, fragmentTransactionBeginTransaction);
                ModFragment modFragment2 = this.modFragment;
                if (modFragment2 == null) {
                    ModFragment modFragment3 = new ModFragment();
                    this.modFragment = modFragment3;
                    int i4 = this.sceneNumber;
                    if (i4 != -1) {
                        modFragment3.sceneNumber = i4;
                    }
                    this.modFragment.addr = this.addr;
                    this.modFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                    this.modFragment.isSupportSleepMod = this.isSupportSleepMod;
                    this.modFragment.isInclude0912 = this.isInclude0912;
                    this.modFragment.isSupportModeLightness = this.isSupportModeLightness;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.modFragment);
                } else if (modFragment2.isHidden()) {
                    this.fragmentTransaction.show(this.modFragment);
                }
                this.editView.setVisibility(4);
            }
        } else if (this.groupId != -1 || this.tempGroupId != -1) {
            this.themeView.setVisibility(4);
            this.editView.setVisibility(4);
            if (i == 0) {
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                ColourFragment colourFragment3 = this.colourFragment;
                if (colourFragment3 == null) {
                    ColourFragment colourFragment4 = new ColourFragment();
                    this.colourFragment = colourFragment4;
                    int i5 = this.groupId;
                    if (i5 != -1 || this.tempGroupId != -1) {
                        colourFragment4.groupId = i5;
                        this.colourFragment.tempGroupId = this.tempGroupId;
                        this.colourFragment.type = this.type;
                    }
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.colourFragment);
                } else if (colourFragment3.isHidden()) {
                    this.fragmentTransaction.show(this.colourFragment);
                }
                this.titleView.setText(this.nameStr);
                int i6 = this.viewType;
                if (i6 != 43049 && i6 != 43051) {
                    this.editView.setVisibility(0);
                    this.editView.setImageResource(R.drawable.rgb_switch_bt);
                }
                if (this.colourFragmentListener == null) {
                    this.colourFragmentListener = this.colourFragment;
                }
                MusicFragmentListener musicFragmentListener = this.musicFragmentListener;
                if (musicFragmentListener != null) {
                    musicFragmentListener.endMusic();
                }
                RhythmFragmentListener rhythmFragmentListener = this.rhythmFragmentListener;
                if (rhythmFragmentListener != null) {
                    rhythmFragmentListener.stopVoice();
                }
            } else if (i == 1) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                ModFragment modFragment4 = this.modFragment;
                if (modFragment4 == null) {
                    ModFragment modFragment5 = new ModFragment();
                    this.modFragment = modFragment5;
                    int i7 = this.groupId;
                    if (i7 != -1 || this.tempGroupId != -1) {
                        modFragment5.groupId = i7;
                        this.modFragment.tempGroupId = this.tempGroupId;
                    }
                    this.modFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                    this.modFragment.isInclude0912 = this.isInclude0912;
                    this.modFragment.isSupportModeLightness = this.isSupportModeLightness;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.modFragment);
                } else if (modFragment4.isHidden()) {
                    this.fragmentTransaction.show(this.modFragment);
                }
                this.titleView.setText(R.string.Mod);
                MusicFragmentListener musicFragmentListener2 = this.musicFragmentListener;
                if (musicFragmentListener2 != null) {
                    musicFragmentListener2.endMusic();
                }
                RhythmFragmentListener rhythmFragmentListener2 = this.rhythmFragmentListener;
                if (rhythmFragmentListener2 != null) {
                    rhythmFragmentListener2.stopVoice();
                }
            } else if (i == 2) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                MusicFragment musicFragment = this.musicFragment;
                if (musicFragment == null) {
                    MusicFragment musicFragment2 = new MusicFragment();
                    this.musicFragment = musicFragment2;
                    int i8 = this.groupId;
                    if (i8 != -1 || this.tempGroupId != -1) {
                        musicFragment2.groupId = i8;
                        this.musicFragment.tempGroupId = this.tempGroupId;
                        this.musicFragment.type = this.type;
                    }
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.musicFragment);
                } else if (musicFragment.isHidden()) {
                    this.fragmentTransaction.show(this.musicFragment);
                    checkRW();
                }
                this.titleView.setText(R.string.Music);
                if (this.musicFragmentListener == null) {
                    this.musicFragmentListener = this.musicFragment;
                }
                RhythmFragmentListener rhythmFragmentListener3 = this.rhythmFragmentListener;
                if (rhythmFragmentListener3 != null) {
                    rhythmFragmentListener3.stopVoice();
                }
                this.themeView.setVisibility(0);
            } else if (i == 3) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                RhythmFragment rhythmFragment = this.rhythmFragment;
                if (rhythmFragment == null) {
                    RhythmFragment rhythmFragment2 = new RhythmFragment();
                    this.rhythmFragment = rhythmFragment2;
                    int i9 = this.groupId;
                    if (i9 != -1 || this.tempGroupId != -1) {
                        rhythmFragment2.groupId = i9;
                        this.rhythmFragment.tempGroupId = this.tempGroupId;
                        this.rhythmFragment.type = this.type;
                    }
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.rhythmFragment);
                } else if (rhythmFragment.isHidden()) {
                    this.fragmentTransaction.show(this.rhythmFragment);
                    checkMicroPhone();
                }
                this.titleView.setText(R.string.Rhythm);
                if (this.rhythmFragmentListener == null) {
                    this.rhythmFragmentListener = this.rhythmFragment;
                }
                MusicFragmentListener musicFragmentListener3 = this.musicFragmentListener;
                if (musicFragmentListener3 != null) {
                    musicFragmentListener3.endMusic();
                }
                this.themeView.setVisibility(0);
            }
        } else {
            this.themeView.setVisibility(4);
            this.titleView.setVisibility(0);
            this.nameBtn.setVisibility(4);
            this.editView.setVisibility(4);
            int i10 = this.viewType;
            if ((i10 == 43049 || i10 == 43051) && !this.isSupportSleepMod) {
                if (i == 0) {
                    hideFragment(this.modFragment, this.fragmentTransaction);
                    hideFragment(this.musicFragment, this.fragmentTransaction);
                    hideFragment(this.rhythmFragment, this.fragmentTransaction);
                    hideFragment(this.timerFragment, this.fragmentTransaction);
                    ColourFragment colourFragment5 = this.colourFragment;
                    if (colourFragment5 == null) {
                        ColourFragment colourFragment6 = new ColourFragment();
                        this.colourFragment = colourFragment6;
                        colourFragment6.addr = this.addr;
                        this.fragmentTransaction.add(R.id.ctrl_main_content, this.colourFragment);
                    } else if (colourFragment5.isHidden()) {
                        this.fragmentTransaction.show(this.colourFragment);
                    }
                    this.nameBtn.setVisibility(0);
                    this.nameText.setText(this.bleDevice.getName());
                    this.titleView.setVisibility(4);
                    int i11 = this.viewType;
                    if (i11 != 43049 && i11 != 43051) {
                        this.editView.setVisibility(0);
                        this.editView.setImageResource(R.drawable.rgb_switch_bt);
                    }
                    if (this.colourFragmentListener == null) {
                        this.colourFragmentListener = this.colourFragment;
                    }
                    MusicFragmentListener musicFragmentListener4 = this.musicFragmentListener;
                    if (musicFragmentListener4 != null) {
                        musicFragmentListener4.endMusic();
                    }
                    RhythmFragmentListener rhythmFragmentListener4 = this.rhythmFragmentListener;
                    if (rhythmFragmentListener4 != null) {
                        rhythmFragmentListener4.stopVoice();
                    }
                } else if (i == 1) {
                    hideFragment(this.colourFragment, this.fragmentTransaction);
                    hideFragment(this.modFragment, this.fragmentTransaction);
                    hideFragment(this.musicFragment, this.fragmentTransaction);
                    hideFragment(this.rhythmFragment, this.fragmentTransaction);
                    TimerFragment timerFragment = this.timerFragment;
                    if (timerFragment == null) {
                        TimerFragment timerFragment2 = new TimerFragment();
                        this.timerFragment = timerFragment2;
                        timerFragment2.addr = this.addr;
                        this.timerFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                        this.timerFragment.isSupportSleepMod = this.isSupportSleepMod;
                        this.fragmentTransaction.add(R.id.ctrl_main_content, this.timerFragment);
                        this.hasRequestLocation = false;
                        if (Tool.isAPI31()) {
                            if (!Tool.checkBleCASPermission()) {
                                bleCASDialog();
                            } else if (Tool.checkLocationPermission()) {
                                getLocationPermission();
                            } else {
                                dialog();
                            }
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    } else if (timerFragment.isHidden()) {
                        this.fragmentTransaction.show(this.timerFragment);
                        this.hasRequestLocation = false;
                        if (Tool.isAPI31()) {
                            if (!Tool.checkBleCASPermission()) {
                                bleCASDialog();
                            } else if (Tool.checkLocationPermission()) {
                                getLocationPermission();
                            } else {
                                dialog();
                            }
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    }
                    TimerFragment timerFragment3 = this.timerFragment;
                    if (timerFragment3 != null) {
                        this.timerFragmentListener = timerFragment3;
                    }
                    this.titleView.setText(R.string.Timer);
                    MusicFragmentListener musicFragmentListener5 = this.musicFragmentListener;
                    if (musicFragmentListener5 != null) {
                        musicFragmentListener5.endMusic();
                    }
                    RhythmFragmentListener rhythmFragmentListener5 = this.rhythmFragmentListener;
                    if (rhythmFragmentListener5 != null) {
                        rhythmFragmentListener5.stopVoice();
                    }
                }
            } else if ((i10 == 43049 || i10 == 43051) && this.isSupportSleepMod) {
                if (i == 0) {
                    hideFragment(this.modFragment, this.fragmentTransaction);
                    hideFragment(this.musicFragment, this.fragmentTransaction);
                    hideFragment(this.rhythmFragment, this.fragmentTransaction);
                    hideFragment(this.timerFragment, this.fragmentTransaction);
                    ColourFragment colourFragment7 = this.colourFragment;
                    if (colourFragment7 == null) {
                        ColourFragment colourFragment8 = new ColourFragment();
                        this.colourFragment = colourFragment8;
                        colourFragment8.addr = this.addr;
                        this.fragmentTransaction.add(R.id.ctrl_main_content, this.colourFragment);
                    } else if (colourFragment7.isHidden()) {
                        this.fragmentTransaction.show(this.colourFragment);
                    }
                    this.nameBtn.setVisibility(0);
                    this.nameText.setText(this.bleDevice.getName());
                    this.titleView.setVisibility(4);
                    int i12 = this.viewType;
                    if (i12 != 43049 && i12 != 43051) {
                        this.editView.setVisibility(0);
                        this.editView.setImageResource(R.drawable.rgb_switch_bt);
                    }
                    if (this.colourFragmentListener == null) {
                        this.colourFragmentListener = this.colourFragment;
                    }
                    MusicFragmentListener musicFragmentListener6 = this.musicFragmentListener;
                    if (musicFragmentListener6 != null) {
                        musicFragmentListener6.endMusic();
                    }
                    RhythmFragmentListener rhythmFragmentListener6 = this.rhythmFragmentListener;
                    if (rhythmFragmentListener6 != null) {
                        rhythmFragmentListener6.stopVoice();
                    }
                } else if (i == 1) {
                    hideFragment(this.colourFragment, this.fragmentTransaction);
                    hideFragment(this.musicFragment, this.fragmentTransaction);
                    hideFragment(this.rhythmFragment, this.fragmentTransaction);
                    hideFragment(this.timerFragment, this.fragmentTransaction);
                    ModFragment modFragment6 = this.modFragment;
                    if (modFragment6 == null) {
                        ModFragment modFragment7 = new ModFragment();
                        this.modFragment = modFragment7;
                        modFragment7.addr = this.addr;
                        this.modFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                        this.modFragment.isSupportSleepMod = this.isSupportSleepMod;
                        this.modFragment.isInclude0912 = this.isInclude0912;
                        this.modFragment.isSupportModeLightness = this.isSupportModeLightness;
                        this.fragmentTransaction.add(R.id.ctrl_main_content, this.modFragment);
                    } else if (modFragment6.isHidden()) {
                        this.fragmentTransaction.show(this.modFragment);
                    }
                    this.titleView.setText(R.string.Mod);
                    MusicFragmentListener musicFragmentListener7 = this.musicFragmentListener;
                    if (musicFragmentListener7 != null) {
                        musicFragmentListener7.endMusic();
                    }
                    RhythmFragmentListener rhythmFragmentListener7 = this.rhythmFragmentListener;
                    if (rhythmFragmentListener7 != null) {
                        rhythmFragmentListener7.stopVoice();
                    }
                } else if (i == 2) {
                    hideFragment(this.colourFragment, this.fragmentTransaction);
                    hideFragment(this.modFragment, this.fragmentTransaction);
                    hideFragment(this.musicFragment, this.fragmentTransaction);
                    hideFragment(this.rhythmFragment, this.fragmentTransaction);
                    TimerFragment timerFragment4 = this.timerFragment;
                    if (timerFragment4 == null) {
                        TimerFragment timerFragment5 = new TimerFragment();
                        this.timerFragment = timerFragment5;
                        timerFragment5.addr = this.addr;
                        this.timerFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                        this.timerFragment.isSupportSleepMod = this.isSupportSleepMod;
                        this.fragmentTransaction.add(R.id.ctrl_main_content, this.timerFragment);
                        this.hasRequestLocation = false;
                        if (Tool.isAPI31()) {
                            if (!Tool.checkBleCASPermission()) {
                                bleCASDialog();
                            } else if (Tool.checkLocationPermission()) {
                                getLocationPermission();
                            } else {
                                dialog();
                            }
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    } else if (timerFragment4.isHidden()) {
                        this.fragmentTransaction.show(this.timerFragment);
                        this.hasRequestLocation = false;
                        if (Tool.isAPI31()) {
                            if (!Tool.checkBleCASPermission()) {
                                bleCASDialog();
                            } else if (Tool.checkLocationPermission()) {
                                getLocationPermission();
                            } else {
                                dialog();
                            }
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    }
                    TimerFragment timerFragment6 = this.timerFragment;
                    if (timerFragment6 != null) {
                        this.timerFragmentListener = timerFragment6;
                    }
                    this.titleView.setText(R.string.Timer);
                    MusicFragmentListener musicFragmentListener8 = this.musicFragmentListener;
                    if (musicFragmentListener8 != null) {
                        musicFragmentListener8.endMusic();
                    }
                    RhythmFragmentListener rhythmFragmentListener8 = this.rhythmFragmentListener;
                    if (rhythmFragmentListener8 != null) {
                        rhythmFragmentListener8.stopVoice();
                    }
                }
            } else if (i == 0) {
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                hideFragment(this.timerFragment, this.fragmentTransaction);
                ColourFragment colourFragment9 = this.colourFragment;
                if (colourFragment9 == null) {
                    ColourFragment colourFragment10 = new ColourFragment();
                    this.colourFragment = colourFragment10;
                    colourFragment10.addr = this.addr;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.colourFragment);
                } else if (colourFragment9.isHidden()) {
                    this.fragmentTransaction.show(this.colourFragment);
                }
                this.nameBtn.setVisibility(0);
                this.nameText.setText(this.bleDevice.getName());
                this.titleView.setVisibility(4);
                int i13 = this.viewType;
                if (i13 != 43049 && i13 != 43051) {
                    this.editView.setVisibility(0);
                    this.editView.setImageResource(R.drawable.rgb_switch_bt);
                }
                if (this.colourFragmentListener == null) {
                    this.colourFragmentListener = this.colourFragment;
                }
                MusicFragmentListener musicFragmentListener9 = this.musicFragmentListener;
                if (musicFragmentListener9 != null) {
                    musicFragmentListener9.endMusic();
                }
                RhythmFragmentListener rhythmFragmentListener9 = this.rhythmFragmentListener;
                if (rhythmFragmentListener9 != null) {
                    rhythmFragmentListener9.stopVoice();
                }
            } else if (i == 1) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                hideFragment(this.timerFragment, this.fragmentTransaction);
                ModFragment modFragment8 = this.modFragment;
                if (modFragment8 == null) {
                    ModFragment modFragment9 = new ModFragment();
                    this.modFragment = modFragment9;
                    modFragment9.addr = this.addr;
                    this.modFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                    this.modFragment.isSupportSleepMod = this.isSupportSleepMod;
                    this.modFragment.isInclude0912 = this.isInclude0912;
                    this.modFragment.isSupportModeLightness = this.isSupportModeLightness;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.modFragment);
                } else if (modFragment8.isHidden()) {
                    this.fragmentTransaction.show(this.modFragment);
                }
                this.titleView.setText(R.string.Mod);
                MusicFragmentListener musicFragmentListener10 = this.musicFragmentListener;
                if (musicFragmentListener10 != null) {
                    musicFragmentListener10.endMusic();
                }
                RhythmFragmentListener rhythmFragmentListener10 = this.rhythmFragmentListener;
                if (rhythmFragmentListener10 != null) {
                    rhythmFragmentListener10.stopVoice();
                }
            } else if (i == 2) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                hideFragment(this.timerFragment, this.fragmentTransaction);
                MusicFragment musicFragment3 = this.musicFragment;
                if (musicFragment3 == null) {
                    MusicFragment musicFragment4 = new MusicFragment();
                    this.musicFragment = musicFragment4;
                    musicFragment4.addr = this.addr;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.musicFragment);
                } else if (musicFragment3.isHidden()) {
                    this.fragmentTransaction.show(this.musicFragment);
                    checkRW();
                }
                this.titleView.setText(R.string.Music);
                if (this.musicFragmentListener == null) {
                    this.musicFragmentListener = this.musicFragment;
                }
                RhythmFragmentListener rhythmFragmentListener11 = this.rhythmFragmentListener;
                if (rhythmFragmentListener11 != null) {
                    rhythmFragmentListener11.stopVoice();
                }
                this.themeView.setVisibility(0);
            } else if (i == 3) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                hideFragment(this.timerFragment, this.fragmentTransaction);
                RhythmFragment rhythmFragment3 = this.rhythmFragment;
                if (rhythmFragment3 == null) {
                    RhythmFragment rhythmFragment4 = new RhythmFragment();
                    this.rhythmFragment = rhythmFragment4;
                    rhythmFragment4.addr = this.addr;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.rhythmFragment);
                } else if (rhythmFragment3.isHidden()) {
                    this.fragmentTransaction.show(this.rhythmFragment);
                    checkMicroPhone();
                }
                this.titleView.setText(R.string.Rhythm);
                if (this.rhythmFragmentListener == null) {
                    this.rhythmFragmentListener = this.rhythmFragment;
                }
                MusicFragmentListener musicFragmentListener11 = this.musicFragmentListener;
                if (musicFragmentListener11 != null) {
                    musicFragmentListener11.endMusic();
                }
                this.themeView.setVisibility(0);
            } else if (i == 4) {
                hideFragment(this.colourFragment, this.fragmentTransaction);
                hideFragment(this.modFragment, this.fragmentTransaction);
                hideFragment(this.musicFragment, this.fragmentTransaction);
                hideFragment(this.rhythmFragment, this.fragmentTransaction);
                TimerFragment timerFragment7 = this.timerFragment;
                if (timerFragment7 == null) {
                    TimerFragment timerFragment8 = new TimerFragment();
                    this.timerFragment = timerFragment8;
                    timerFragment8.addr = this.addr;
                    this.timerFragment.isSupportTimerStatus = this.isSupportTimerStatus;
                    this.timerFragment.isSupportSleepMod = this.isSupportSleepMod;
                    this.fragmentTransaction.add(R.id.ctrl_main_content, this.timerFragment);
                    this.hasRequestLocation = false;
                    if (Tool.isAPI31()) {
                        if (!Tool.checkBleCASPermission()) {
                            bleCASDialog();
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    } else if (Tool.checkLocationPermission()) {
                        getLocationPermission();
                    } else {
                        dialog();
                    }
                } else if (timerFragment7.isHidden()) {
                    this.fragmentTransaction.show(this.timerFragment);
                    this.hasRequestLocation = false;
                    if (Tool.isAPI31()) {
                        if (!Tool.checkBleCASPermission()) {
                            bleCASDialog();
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    } else if (Tool.checkLocationPermission()) {
                        getLocationPermission();
                    } else {
                        dialog();
                    }
                }
                TimerFragment timerFragment9 = this.timerFragment;
                if (timerFragment9 != null) {
                    this.timerFragmentListener = timerFragment9;
                }
                this.titleView.setText(R.string.Timer);
                MusicFragmentListener musicFragmentListener12 = this.musicFragmentListener;
                if (musicFragmentListener12 != null) {
                    musicFragmentListener12.endMusic();
                }
                RhythmFragmentListener rhythmFragmentListener12 = this.rhythmFragmentListener;
                if (rhythmFragmentListener12 != null) {
                    rhythmFragmentListener12.stopVoice();
                }
            }
        }
        this.fragmentTransaction.commit();
        this.currentSelectTab = i;
    }

    private void handleColourAndMod() {
        ModFragment modFragment = this.modFragment;
        if (modFragment == null || modFragment.currentMod == null) {
            return;
        }
        this.colourFragment.currentSceneColor = -2;
        this.colourFragment.currentSceneLightness = -1;
    }

    private void createSceneDevice(int i, int i2, int i3, int i4, String str, boolean z) {
        BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
        if (bleDevice != null) {
            SceneDevice sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.did, bleDevice.getDid()).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.sceneNumber)).findFirst();
            this.realm.beginTransaction();
            if (sceneDevice == null) {
                sceneDevice = (SceneDevice) this.realm.createObject(SceneDevice.class);
            }
            sceneDevice.setDid(bleDevice.getDid());
            sceneDevice.setSceneNumber(this.sceneNumber);
            sceneDevice.setColor(i);
            sceneDevice.setTemp(i2);
            sceneDevice.setLightness(i3);
            sceneDevice.setModNumber(i4);
            sceneDevice.setCustomModId(str);
            if (sceneDevice.getTempSpeed() != 0) {
                sceneDevice.setModSpeed(sceneDevice.getTempSpeed());
            } else {
                ModFragment modFragment = this.modFragment;
                if (modFragment != null && modFragment.currentMod != null) {
                    sceneDevice.setModSpeed(this.modFragment.currentMod.getSpeed());
                }
            }
            sceneDevice.setTempSpeed(0);
            if (z) {
                sceneDevice.setModBrightness(0);
            }
            this.realm.commitTransaction();
        }
    }

    public static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            DeviceCtrlActivity deviceCtrlActivity = (DeviceCtrlActivity) this.weakReference.get();
            int i = message.what;
            Objects.requireNonNull(deviceCtrlActivity);
            if (i != 101 || deviceCtrlActivity.musicFragmentListener == null) {
                return;
            }
            deviceCtrlActivity.musicFragmentListener.updateProgress();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void back() {
        if (this.saveTextView.getVisibility() == 4) {
            MusicFragmentListener musicFragmentListener = this.musicFragmentListener;
            if (musicFragmentListener != null) {
                musicFragmentListener.endMusic();
            }
            RhythmFragmentListener rhythmFragmentListener = this.rhythmFragmentListener;
            if (rhythmFragmentListener != null) {
                rhythmFragmentListener.stopVoice();
            }
            finish();
            return;
        }
        saveSceneAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveSceneAction() {
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), 0.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.saveSceneChange);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda14
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$saveSceneAction$0(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new CustomDialog.onCancelClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda15
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onCancelClickListener
            public final void onCancelClick() {
                this.f$0.lambda$saveSceneAction$1(customDialog);
            }
        });
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneAction$0(CustomDialog customDialog) {
        customDialog.dismiss();
        addDeviceToScene();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneAction$1(CustomDialog customDialog) {
        customDialog.dismiss();
        this.myHandler.post(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleCancelAddSceneDevices();
            }
        });
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.finish();
            }
        }, 100L);
    }

    public void addDeviceToScene() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleAddSceneDevicesResult();
            }
        }, 2000L);
        GlobalBluetooth.getInstance().addScene(this.addr, this.sceneNumber);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddSceneDevicesResult() {
        DeviceCtrlActivity deviceCtrlActivity;
        int i = this.colourFragment.currentSceneColor;
        int i2 = this.colourFragment.currentSceneLightness;
        int i3 = this.colourFragment.currentSceneColorTemp;
        int i4 = this.currentSelectTab;
        if (i4 == 0) {
            if (i != -2 || i2 != -1 || i3 != -1) {
                deviceCtrlActivity = this;
                deviceCtrlActivity.createSceneDevice(i, i3, i2, 0, null, true);
            } else {
                ModFragment modFragment = this.modFragment;
                if (modFragment == null || modFragment.currentMod == null) {
                    deviceCtrlActivity = this;
                } else {
                    deviceCtrlActivity = this;
                    deviceCtrlActivity.createSceneDevice(i, i3, i2, this.modFragment.currentMod.getModNumber(), this.modFragment.currentMod.getCustomModId(), false);
                }
            }
        } else if (i4 == 1) {
            if (this.modFragment.currentMod != null) {
                createSceneDevice(-2, -1, -1, this.modFragment.currentMod.getModNumber(), this.modFragment.currentMod.getCustomModId(), false);
            } else if (i != -2 || i2 != -1 || i3 != -1) {
                deviceCtrlActivity = this;
                deviceCtrlActivity.createSceneDevice(i, i3, i2, 0, null, true);
            }
            deviceCtrlActivity = this;
        } else {
            deviceCtrlActivity = this;
        }
        Dialog dialog = deviceCtrlActivity.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            deviceCtrlActivity.loadingDialog = null;
        }
        GlobalToast.showText(getApplicationContext(), R.string.SaveSuccess, 0);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCancelAddSceneDevices() {
        SceneDevice sceneDevice;
        ModFragment modFragment;
        BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
        if (bleDevice == null || (sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.did, bleDevice.getDid()).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.sceneNumber)).findFirst()) == null || (modFragment = this.modFragment) == null || modFragment.currentMod == null) {
            return;
        }
        this.realm.beginTransaction();
        if (this.isSupportModeLightness && ((this.modFragment.currentMod.getModNumber() == 24 || this.modFragment.currentMod.getModNumber() == 25 || (this.modFragment.currentMod.getCustomModId() != null && this.modFragment.currentMod.isFlash())) && sceneDevice.getModBrightness() != 0)) {
            sceneDevice.setModBrightness(0);
        }
        this.realm.commitTransaction();
    }

    public void checkRW() {
        MusicFragment musicFragment = this.musicFragment;
        if (musicFragment != null) {
            musicFragment.isGetPermission = false;
        }
        if (Tool.checkMusicPermission()) {
            MusicFragmentListener musicFragmentListener = this.musicFragmentListener;
            if (musicFragmentListener != null) {
                musicFragmentListener.refreshSongList();
                return;
            }
            return;
        }
        if (this.musicFragment.hasRequestRead) {
            return;
        }
        musicPermission();
        this.musicFragment.hasRequestRead = true;
    }

    public void checkMicroPhone() {
        RhythmFragment rhythmFragment = this.rhythmFragment;
        if (rhythmFragment != null) {
            rhythmFragment.isGetPermission = false;
        }
        if (Tool.checkMicroPhonePermission()) {
            RhythmFragmentListener rhythmFragmentListener = this.rhythmFragmentListener;
            if (rhythmFragmentListener != null) {
                rhythmFragmentListener.microPhoneOK();
                return;
            }
            return;
        }
        if (this.rhythmFragment.hasRequestMC) {
            return;
        }
        mcPermission();
        this.rhythmFragment.hasRequestMC = true;
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
        this.locationServiceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda18
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$2((ActivityResult) obj);
            }
        });
        this.locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda19
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((Map) obj);
            }
        });
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda20
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                DeviceCtrlActivity.lambda$registerActivityResultLauncher$4((ActivityResult) obj);
            }
        });
        this.musicPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda21
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$5((Map) obj);
            }
        });
        this.mcPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$6((Map) obj);
            }
        });
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
            TimerFragmentListener timerFragmentListener = this.timerFragmentListener;
            if (timerFragmentListener != null) {
                timerFragmentListener.locationPermissionIsOK();
                return;
            }
            return;
        }
        locationDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$5(Map map) {
        if (Build.VERSION.SDK_INT >= 33) {
            if (map.get("android.permission.READ_MEDIA_AUDIO") == null || map.get("android.permission.RECORD_AUDIO") == null) {
                return;
            }
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_MEDIA_AUDIO"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.RECORD_AUDIO"))).equals(true)) {
                MusicFragmentListener musicFragmentListener = this.musicFragmentListener;
                if (musicFragmentListener != null) {
                    musicFragmentListener.refreshSongList();
                    return;
                }
                return;
            }
            musicPermissionDialog();
            return;
        }
        if (map.get("android.permission.READ_EXTERNAL_STORAGE") == null || map.get("android.permission.RECORD_AUDIO") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_EXTERNAL_STORAGE"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.RECORD_AUDIO"))).equals(true)) {
            MusicFragmentListener musicFragmentListener2 = this.musicFragmentListener;
            if (musicFragmentListener2 != null) {
                musicFragmentListener2.refreshSongList();
                return;
            }
            return;
        }
        musicPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$6(Map map) {
        if (map.get("android.permission.RECORD_AUDIO") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.RECORD_AUDIO"))).equals(true)) {
                RhythmFragmentListener rhythmFragmentListener = this.rhythmFragmentListener;
                if (rhythmFragmentListener != null) {
                    rhythmFragmentListener.microPhoneOK();
                    return;
                }
                return;
            }
            mcPermissionDialog();
        }
    }

    public void getLocationPermission() {
        if (Tool.checkLocationServiceEnable(this)) {
            if (Tool.checkLocationPermission()) {
                TimerFragmentListener timerFragmentListener = this.timerFragmentListener;
                if (timerFragmentListener != null) {
                    timerFragmentListener.locationPermissionIsOK();
                    return;
                }
                return;
            }
            if (this.hasRequestLocation) {
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
            this.locationServiceAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationServiceRequest).setMessage(R.string.locationServiceRequestMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$7(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda13
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$8(dialogInterface, i);
                }
            }).create();
        }
        this.locationServiceAlertDialog.show();
        this.locationServiceAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$7(DialogInterface dialogInterface, int i) {
        enableLocationService();
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$8(DialogInterface dialogInterface, int i) {
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationAddressRequestMessage).setMessage(R.string.locationAddressRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda16
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$9(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda17
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$10(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$9(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$10(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    private void dialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.locationAddressRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$11(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$12(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$11(DialogInterface dialogInterface, int i) {
        getLocationPermission();
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$12(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    public void musicPermissionDialog() {
        if (this.musicPermissionAlertDialog == null) {
            this.musicPermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.readRequestMessage).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$musicPermissionDialog$13(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$musicPermissionDialog$14(dialogInterface, i);
                }
            }).create();
        }
        this.musicPermissionAlertDialog.show();
        this.musicPermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$musicPermissionDialog$13(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.musicFragment.hasRequestRead = false;
        this.musicPermissionAlertDialog.cancel();
        this.musicPermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$musicPermissionDialog$14(DialogInterface dialogInterface, int i) {
        this.musicPermissionAlertDialog.cancel();
        this.musicPermissionAlertDialog = null;
    }

    public void mcPermissionDialog() {
        if (this.mcPermissionAlertDialog == null) {
            this.mcPermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.microPhoneRequestMessage).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$mcPermissionDialog$15(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$mcPermissionDialog$16(dialogInterface, i);
                }
            }).create();
        }
        this.mcPermissionAlertDialog.show();
        this.mcPermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mcPermissionDialog$15(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.rhythmFragment.hasRequestMC = false;
        this.mcPermissionAlertDialog.cancel();
        this.mcPermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mcPermissionDialog$16(DialogInterface dialogInterface, int i) {
        this.mcPermissionAlertDialog.cancel();
        this.mcPermissionAlertDialog = null;
    }

    public void bleCASDialog() {
        if (this.bleCASAlertDialog == null) {
            this.bleCASAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.bleCA).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$17(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$18(dialogInterface, i);
                }
            }).create();
        }
        this.bleCASAlertDialog.show();
        this.bleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$17(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$18(DialogInterface dialogInterface, int i) {
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

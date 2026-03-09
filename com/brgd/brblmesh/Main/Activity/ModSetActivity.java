package com.brgd.brblmesh.Main.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.bean.BLESceneInfo;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter;
import com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ModSetActivity extends FatherActivity implements SeekBar.OnSeekBarChangeListener, ColorObserver, View.OnClickListener {
    ImageView backView;
    ImageView blueDownView;
    private SeekBar blueSeekBar;
    private String blueStr;
    ImageView blueUpView;
    private TextView blueValue;
    ImageView brightnessDownView;
    private ConstraintLayout brightnessLayout;
    private SeekBar brightnessSeekBar;
    ImageView brightnessUpView;
    private TextView brightnessValue;
    public ConstraintLayout colorPickerLayout;
    public ConstraintLayout colorSeekBarLayout;
    private ColorWheelView colorWheelView;
    private int currentBlueProgress;
    private int currentBrightness;
    private int currentColor;
    private ColorSelector currentDefaultColorSelector;
    private int currentGreenProgress;
    private Mod currentMod;
    private int currentRedProgress;
    private int currentSpeed;
    DiyColorAdapter defaultColorAdapter;
    private List<DiyColor> defaultColorList;
    TextView defaultColorText;
    RecyclerView defaultRecyclerView;
    TextView deleteView;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private TextView deviceNameText;
    ImageView dismissView;
    TextView diyColorText;
    RecyclerView diyRecyclerView;
    private ImageView editView;
    ImageView greenDownView;
    private SeekBar greenSeekBar;
    private String greenStr;
    ImageView greenUpView;
    private TextView greenValue;
    private boolean isInclude0912;
    private boolean isNewMode;
    private boolean isShowBrightness;
    private boolean isSupportModeLightness;
    ImageView leftBtView;
    private ModDiyColorAdapter modDiyColorAdapter;
    private List<ModDiyColor> modDiyColorList;
    ConstraintLayout nameBtn;
    private TextView nameText;
    Realm realm;
    ImageView redDownView;
    private SeekBar redSeekBar;
    private String redStr;
    ImageView redUpView;
    private TextView redValue;
    private ConstraintLayout renameLayout;
    ImageView rightBtView;
    ImageView saveView;
    private SceneDevice sceneDevice;
    ImageView speedDownView;
    private SeekBar speedSeekBar;
    ImageView speedUpView;
    private TextView speedValue;
    TextView titleView;
    ConstraintLayout topLayout;
    int[] defaultColorArr = {-3, -65536, CustomColor.GREEN, CustomColor.BLUE, -256, CustomColor.MAGENTA, CustomColor.CYAN};
    private int addr = -1;
    private int groupId = -1;
    private int tempGroupId = -1;
    public int sceneNumber = -1;
    int maxJump = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    int minFade = 1;
    int step = 2;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ModSetActivity.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.modSet_back) {
                ModSetActivity.this.finish();
                return;
            }
            if (id == R.id.modSet_edit) {
                if (ModSetActivity.this.editView.isSelected()) {
                    ModSetActivity.this.editView.setSelected(false);
                    ModSetActivity.this.colorPickerLayout.setVisibility(0);
                    ModSetActivity.this.colorSeekBarLayout.setVisibility(4);
                    return;
                } else {
                    ModSetActivity.this.editView.setSelected(true);
                    ModSetActivity.this.colorPickerLayout.setVisibility(4);
                    ModSetActivity.this.colorSeekBarLayout.setVisibility(0);
                    ModSetActivity.this.upDateRgbValue();
                    return;
                }
            }
            if (id == R.id.modSet_toRightBt) {
                if (ModSetActivity.this.currentColor != -1) {
                    ModSetActivity.this.colorWheelView.moveSelector(false);
                    return;
                }
                return;
            }
            if (id == R.id.modSet_toLeftBt) {
                if (ModSetActivity.this.currentColor != -1) {
                    ModSetActivity.this.colorWheelView.moveSelector(true);
                    return;
                }
                return;
            }
            if (id == R.id.modSpeed_speed_down) {
                if (ModSetActivity.this.currentSpeed > 1) {
                    ModSetActivity.this.currentSpeed--;
                }
                ModSetActivity.this.speedSeekBar.setProgress(ModSetActivity.this.currentSpeed - 1);
                ModSetActivity.this.setModSpeed();
                return;
            }
            if (id == R.id.modSpeed_speed_up) {
                if (ModSetActivity.this.currentSpeed < 100) {
                    ModSetActivity.this.currentSpeed++;
                }
                ModSetActivity.this.speedSeekBar.setProgress(ModSetActivity.this.currentSpeed - 1);
                ModSetActivity.this.setModSpeed();
                return;
            }
            if (id == R.id.ctrl_nameBtn) {
                ModSetActivity.this.handleDeviceRename();
                return;
            }
            if (id == R.id.device_rename_delete) {
                ModSetActivity.this.deviceNameEdit.setText("");
                ModSetActivity.this.deviceNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.device_rename_save) {
                String string = ModSetActivity.this.deviceNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                Iterator it = ModSetActivity.this.realm.where(Mod.class).equalTo(GlobalVariable.modNumber, (Integer) 0).findAll().iterator();
                while (it.hasNext()) {
                    if (strTrim.equalsIgnoreCase(((Mod) it.next()).getCustomModName())) {
                        GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.nameExist, 0);
                        return;
                    }
                }
                if (ModSetActivity.this.currentMod != null) {
                    ModSetActivity.this.realm.beginTransaction();
                    ModSetActivity.this.currentMod.setCustomModName(strTrim);
                    ModSetActivity.this.realm.commitTransaction();
                }
                ModSetActivity.this.closeRenameView();
                GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.SaveSuccess, 0);
                ModSetActivity.this.nameText.setText(ModSetActivity.this.currentMod.getCustomModName());
                return;
            }
            if (id == R.id.device_rename_dismiss) {
                ModSetActivity.this.closeRenameView();
                return;
            }
            if (id == R.id.modSet_delete) {
                ModSetActivity.this.deleteMod();
                return;
            }
            if (id == R.id.modSpeed_brightness_down) {
                if (ModSetActivity.this.currentBrightness > 1) {
                    ModSetActivity.this.currentBrightness--;
                }
                ModSetActivity.this.brightnessSeekBar.setProgress(ModSetActivity.this.currentBrightness - 1);
                ModSetActivity.this.setModSpeed();
                return;
            }
            if (id == R.id.modSpeed_brightness_up) {
                if (ModSetActivity.this.currentBrightness < 100) {
                    ModSetActivity.this.currentBrightness++;
                }
                ModSetActivity.this.brightnessSeekBar.setProgress(ModSetActivity.this.currentBrightness - 1);
                ModSetActivity.this.setModSpeed();
            }
        }
    };
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mod_set);
        initView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.colorWheelView.subscribe(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.colorWheelView.unsubscribe(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.myHandler.removeCallbacksAndMessages(this);
        this.colorWheelView.unsubscribe(this);
        this.realm.close();
    }

    private void initView() {
        BleDevice bleDevice;
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        Bundle bundleExtra = getIntent().getBundleExtra(GlobalVariable.toModSetCtrl);
        if (bundleExtra != null) {
            int i = bundleExtra.getInt(GlobalVariable.modNumber);
            if (i != 0) {
                this.currentMod = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(i)).findFirst();
            } else {
                String string = bundleExtra.getString(GlobalVariable.customModId);
                if (string != null) {
                    this.currentMod = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.customModId, string).findFirst();
                }
            }
            this.addr = bundleExtra.getInt(GlobalVariable.addr);
            this.groupId = bundleExtra.getInt(GlobalVariable.groupId);
            this.tempGroupId = bundleExtra.getInt(GlobalVariable.tempGroupId);
            this.sceneNumber = bundleExtra.getInt(GlobalVariable.sceneNumber);
            this.isInclude0912 = bundleExtra.getBoolean(GlobalVariable.isInclude0912);
            this.isSupportModeLightness = bundleExtra.getBoolean(GlobalVariable.S_MODE_LIGHTNESS);
        }
        ImageView imageView = (ImageView) findViewById(R.id.modSet_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.titleView = (TextView) findViewById(R.id.modSet_title);
        ImageView imageView2 = (ImageView) findViewById(R.id.modSet_edit);
        this.editView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        if (this.currentMod.getCustomModId() != null && this.sceneNumber == -1) {
            this.titleView.setVisibility(4);
            ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.ctrl_nameBtn);
            this.nameBtn = constraintLayout;
            constraintLayout.setVisibility(0);
            this.nameBtn.setOnClickListener(this.disDoubleClickListener);
            TextView textView = (TextView) findViewById(R.id.ctrl_nameText);
            this.nameText = textView;
            textView.setText(this.currentMod.getCustomModName());
            TextView textView2 = (TextView) findViewById(R.id.modSet_delete);
            this.deleteView = textView2;
            textView2.setVisibility(0);
            this.deleteView.setOnClickListener(this.disDoubleClickListener);
        }
        this.topLayout = (ConstraintLayout) findViewById(R.id.modSet_topView);
        this.colorPickerLayout = (ConstraintLayout) findViewById(R.id.modSet_colorPickerBg);
        ColorWheelView colorWheelView = (ColorWheelView) findViewById(R.id.modSet_colorPicker);
        this.colorWheelView = colorWheelView;
        colorWheelView.subscribe(this);
        this.colorWheelView.setColor(-65536, false);
        this.currentColor = -65536;
        this.colorSeekBarLayout = (ConstraintLayout) findViewById(R.id.modSet_colorSeekBarBg);
        SeekBar seekBar = (SeekBar) findViewById(R.id.modSet_RedSeekBar);
        this.redSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.redSeekBar.setMax(255);
        this.currentRedProgress = 0;
        this.redSeekBar.setProgress(0);
        this.redValue = (TextView) findViewById(R.id.modSet_RedTextValue);
        ImageView imageView3 = (ImageView) findViewById(R.id.modSet_Red_up);
        this.redUpView = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.modSet_Red_down);
        this.redDownView = imageView4;
        imageView4.setOnClickListener(this);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.modSet_GreenSeekBar);
        this.greenSeekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.greenSeekBar.setMax(255);
        this.currentGreenProgress = 0;
        this.greenSeekBar.setProgress(0);
        this.greenValue = (TextView) findViewById(R.id.modSet_GreenTextValue);
        ImageView imageView5 = (ImageView) findViewById(R.id.modSet_Green_up);
        this.greenUpView = imageView5;
        imageView5.setOnClickListener(this);
        ImageView imageView6 = (ImageView) findViewById(R.id.modSet_Green_down);
        this.greenDownView = imageView6;
        imageView6.setOnClickListener(this);
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.modSet_BlueSeekBar);
        this.blueSeekBar = seekBar3;
        seekBar3.setOnSeekBarChangeListener(this);
        this.blueSeekBar.setMax(255);
        this.currentBlueProgress = 0;
        this.blueSeekBar.setProgress(0);
        this.blueValue = (TextView) findViewById(R.id.modSet_BlueTextValue);
        ImageView imageView7 = (ImageView) findViewById(R.id.modSet_Blue_up);
        this.blueUpView = imageView7;
        imageView7.setOnClickListener(this);
        ImageView imageView8 = (ImageView) findViewById(R.id.modSet_Blue_down);
        this.blueDownView = imageView8;
        imageView8.setOnClickListener(this);
        ImageView imageView9 = (ImageView) findViewById(R.id.modSet_toRightBt);
        this.leftBtView = imageView9;
        imageView9.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView10 = (ImageView) findViewById(R.id.modSet_toLeftBt);
        this.rightBtView = imageView10;
        imageView10.setOnClickListener(this.disDoubleClickListener);
        this.defaultColorText = (TextView) findViewById(R.id.modSet_default_text);
        this.defaultRecyclerView = (RecyclerView) findViewById(R.id.modSet_default_recyclerView);
        this.defaultRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        this.defaultColorList = new ArrayList();
        DiyColorAdapter diyColorAdapter = new DiyColorAdapter(this, this.defaultColorList);
        this.defaultColorAdapter = diyColorAdapter;
        this.defaultRecyclerView.setAdapter(diyColorAdapter);
        this.defaultColorAdapter.setOnItemClickListener(new DiyColorAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ModSetActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter.OnItemClickListener
            public final void OnItemClick(View view, int i2) {
                this.f$0.lambda$initView$0(view, i2);
            }
        });
        RealmResults realmResultsFindAll = this.realm.where(DiyColor.class).equalTo("colorIdentifier", GlobalVariable.modDefaultColor).findAll();
        if (realmResultsFindAll.isEmpty()) {
            this.realm.beginTransaction();
            for (int i2 : this.defaultColorArr) {
                DiyColor diyColor = (DiyColor) this.realm.createObject(DiyColor.class);
                diyColor.setColorIdentifier(GlobalVariable.modDefaultColor);
                diyColor.setColorValue(i2);
                this.defaultColorList.add(diyColor);
            }
            this.realm.commitTransaction();
        } else {
            this.defaultColorList.addAll(realmResultsFindAll);
        }
        this.defaultColorAdapter.notifyDataSetChanged();
        this.diyColorText = (TextView) findViewById(R.id.mod_diyColor_text);
        this.diyRecyclerView = (RecyclerView) findViewById(R.id.mod_diyColor_recyclerView);
        this.diyRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        this.modDiyColorList = new ArrayList();
        ModDiyColorAdapter modDiyColorAdapter = new ModDiyColorAdapter(this, this.modDiyColorList);
        this.modDiyColorAdapter = modDiyColorAdapter;
        this.diyRecyclerView.setAdapter(modDiyColorAdapter);
        this.modDiyColorAdapter.setOnItemClickListener(new AnonymousClass1());
        if (this.currentMod.getModNumber() > 21 || this.currentMod.getCustomModId() != null) {
            this.defaultColorText.setVisibility(0);
            this.defaultRecyclerView.setVisibility(0);
            this.diyColorText.setVisibility(0);
            this.diyRecyclerView.setVisibility(0);
            boolean z = this.currentMod.getModNumber() == 24 || this.currentMod.getModNumber() == 25 || this.currentMod.getModNumber() == 28 || this.currentMod.getModNumber() == 29 || this.currentMod.getCustomModId() != null;
            this.isNewMode = z;
            if (z) {
                this.defaultColorText.setVisibility(8);
                this.defaultRecyclerView.setVisibility(8);
            } else {
                this.editView.setVisibility(8);
                this.topLayout.setVisibility(8);
            }
            handleDiyColorData();
        } else {
            this.defaultColorText.setVisibility(4);
            this.defaultRecyclerView.setVisibility(4);
            this.diyColorText.setVisibility(4);
            this.diyRecyclerView.setVisibility(4);
            this.editView.setVisibility(8);
            this.topLayout.setVisibility(8);
        }
        this.speedValue = (TextView) findViewById(R.id.modSpeed_speedValue);
        ImageView imageView11 = (ImageView) findViewById(R.id.modSpeed_speed_up);
        this.speedUpView = imageView11;
        imageView11.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView12 = (ImageView) findViewById(R.id.modSpeed_speed_down);
        this.speedDownView = imageView12;
        imageView12.setOnClickListener(this.disDoubleClickListener);
        SeekBar seekBar4 = (SeekBar) findViewById(R.id.modSpeed_speedSeekBar);
        this.speedSeekBar = seekBar4;
        seekBar4.setOnSeekBarChangeListener(this);
        this.speedSeekBar.setMax(99);
        this.currentSpeed = this.currentMod.getSpeed();
        this.isShowBrightness = this.isSupportModeLightness && (this.currentMod.getModNumber() == 24 || this.currentMod.getModNumber() == 25 || (this.currentMod.getCustomModId() != null && this.currentMod.isFlash()));
        this.brightnessLayout = (ConstraintLayout) findViewById(R.id.modSpeed_brightnessSeekBarLayout);
        this.brightnessValue = (TextView) findViewById(R.id.modSpeed_brightnessValue);
        ImageView imageView13 = (ImageView) findViewById(R.id.modSpeed_brightness_up);
        this.brightnessUpView = imageView13;
        imageView13.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView14 = (ImageView) findViewById(R.id.modSpeed_brightness_down);
        this.brightnessDownView = imageView14;
        imageView14.setOnClickListener(this.disDoubleClickListener);
        SeekBar seekBar5 = (SeekBar) findViewById(R.id.modSpeed_brightnessSeekBar);
        this.brightnessSeekBar = seekBar5;
        seekBar5.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar.setMax(99);
        this.currentBrightness = this.currentMod.getBrightness() == 0 ? 100 : this.currentMod.getBrightness();
        if (this.sceneNumber != -1 && (bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst()) != null) {
            SceneDevice sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.sceneNumber)).equalTo(GlobalVariable.did, bleDevice.getDid()).findFirst();
            this.sceneDevice = sceneDevice;
            if (sceneDevice != null && sceneDevice.getTempSpeed() != 0) {
                this.currentSpeed = this.sceneDevice.getTempSpeed();
            }
            SceneDevice sceneDevice2 = this.sceneDevice;
            if (sceneDevice2 != null && sceneDevice2.getModBrightness() != 0) {
                this.currentBrightness = this.sceneDevice.getModBrightness();
            }
        }
        this.speedSeekBar.setProgress(this.currentSpeed - 1);
        if (this.isShowBrightness) {
            this.brightnessLayout.setVisibility(0);
            this.brightnessSeekBar.setProgress(this.currentBrightness - 1);
        }
        this.renameLayout = (ConstraintLayout) findViewById(R.id.device_rename_editView);
        this.deviceNameText = (TextView) findViewById(R.id.device_rename_name);
        this.deviceNameEdit = (EditText) findViewById(R.id.device_rename_edit);
        ImageView imageView15 = (ImageView) findViewById(R.id.device_rename_delete);
        this.deviceNameDelete = imageView15;
        imageView15.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.ModSetActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                if (charSequence.length() > 0) {
                    if (ModSetActivity.this.delete_deviceName_tag) {
                        ModSetActivity.this.deviceNameDelete.setSelected(true);
                        ModSetActivity.this.deviceNameDelete.setEnabled(true);
                        ModSetActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                ModSetActivity.this.deviceNameDelete.setSelected(false);
                ModSetActivity.this.deviceNameDelete.setEnabled(false);
                ModSetActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView16 = (ImageView) findViewById(R.id.device_rename_save);
        this.saveView = imageView16;
        imageView16.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView17 = (ImageView) findViewById(R.id.device_rename_dismiss);
        this.dismissView = imageView17;
        imageView17.setOnClickListener(this.disDoubleClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        ColorSelector colorSelector = this.currentDefaultColorSelector;
        if (colorSelector != null) {
            colorSelector.setBorderSelectorColor(0);
            this.currentDefaultColorSelector = null;
        }
        this.currentDefaultColorSelector = (ColorSelector) view;
        DiyColor diyColor = this.defaultColorList.get(i);
        if (diyColor.getColorValue() == -3) {
            this.currentDefaultColorSelector.setBorderSelectorColor(CustomColor.SELECT);
        } else {
            this.currentDefaultColorSelector.setBorderSelectorColor(-3);
        }
        this.currentColor = diyColor.getColorValue();
    }

    /* JADX INFO: renamed from: com.brgd.brblmesh.Main.Activity.ModSetActivity$1, reason: invalid class name */
    class AnonymousClass1 implements ModDiyColorAdapter.OnItemClickListener {
        AnonymousClass1() {
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter.OnItemClickListener
        public void OnItemAddClick(View view, int i) {
            if (ModSetActivity.this.defaultRecyclerView.getVisibility() == 8) {
                ModDiyColor modDiyColor = (ModDiyColor) ModSetActivity.this.modDiyColorList.get(i);
                if (modDiyColor.getDiyColor() == -2) {
                    ModSetActivity.this.realm.beginTransaction();
                    if (Tool.isRGBW(ModSetActivity.this.currentColor)) {
                        modDiyColor.setDiyColor(ModSetActivity.this.currentColor);
                    } else {
                        modDiyColor.setDiyColor(ModSetActivity.this.colorWheelView.getSelectorColor(ModSetActivity.this.currentColor));
                    }
                    modDiyColor.setDiyColorR(ModSetActivity.this.currentColor);
                    ModSetActivity.this.realm.commitTransaction();
                    ModSetActivity.this.modDiyColorAdapter.notifyDataSetChanged();
                    if (ModSetActivity.this.currentMod.getCustomModId() == null) {
                        ModSetActivity modSetActivity = ModSetActivity.this;
                        modSetActivity.enableModCommand(modSetActivity.speedExchange(modSetActivity.currentSpeed));
                        return;
                    } else {
                        ModSetActivity modSetActivity2 = ModSetActivity.this;
                        modSetActivity2.customModCommand(modSetActivity2.speedExchange(modSetActivity2.currentSpeed));
                        return;
                    }
                }
                return;
            }
            if (ModSetActivity.this.currentDefaultColorSelector != null) {
                ModDiyColor modDiyColor2 = (ModDiyColor) ModSetActivity.this.modDiyColorList.get(i);
                if (modDiyColor2.getDiyColor() == -2) {
                    ModSetActivity.this.realm.beginTransaction();
                    modDiyColor2.setDiyColor(ModSetActivity.this.currentColor);
                    modDiyColor2.setDiyColorR(ModSetActivity.this.currentColor);
                    ModSetActivity.this.realm.commitTransaction();
                    ModSetActivity.this.modDiyColorAdapter.notifyDataSetChanged();
                    if (ModSetActivity.this.currentMod.getCustomModId() == null) {
                        ModSetActivity modSetActivity3 = ModSetActivity.this;
                        modSetActivity3.enableModCommand(modSetActivity3.speedExchange(modSetActivity3.currentSpeed));
                        return;
                    } else {
                        ModSetActivity modSetActivity4 = ModSetActivity.this;
                        modSetActivity4.customModCommand(modSetActivity4.speedExchange(modSetActivity4.currentSpeed));
                        return;
                    }
                }
                return;
            }
            GlobalToast.showText(ModSetActivity.this.getApplicationContext(), R.string.selectColor, 1);
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter.OnItemClickListener
        public void OnItemDeleteClick(View view, final int i) {
            final CustomDialog customDialog = new CustomDialog(ModSetActivity.this);
            Window window = customDialog.getWindow();
            WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
            attributes.x = 0;
            attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), ModSetActivity.this.diyRecyclerView.getWidth() / 8.0f);
            window.setAttributes(attributes);
            customDialog.setTitle(R.string.prompt);
            customDialog.setMessage(R.string.confirmDelete);
            customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ModSetActivity$1$$ExternalSyntheticLambda0
                @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
                public final void onSureClick() {
                    this.f$0.lambda$OnItemDeleteClick$0(customDialog, i);
                }
            });
            customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
            customDialog.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnItemDeleteClick$0(CustomDialog customDialog, int i) {
            customDialog.dismiss();
            ModDiyColor modDiyColor = (ModDiyColor) ModSetActivity.this.modDiyColorList.get(i);
            ModSetActivity.this.realm.beginTransaction();
            modDiyColor.setDiyColor(-2);
            modDiyColor.setDiyColorR(-2);
            ModSetActivity.this.realm.commitTransaction();
            ModSetActivity.this.modDiyColorAdapter.notifyDataSetChanged();
            if (ModSetActivity.this.currentMod.getCustomModId() == null) {
                ModSetActivity modSetActivity = ModSetActivity.this;
                modSetActivity.enableModCommand(modSetActivity.speedExchange(modSetActivity.currentSpeed));
            } else {
                ModSetActivity modSetActivity2 = ModSetActivity.this;
                modSetActivity2.customModCommand(modSetActivity2.speedExchange(modSetActivity2.currentSpeed));
            }
        }
    }

    private void handleDiyColorData() {
        RealmResults realmResultsFindAll;
        this.modDiyColorList.clear();
        int i = this.isNewMode ? 4 : 7;
        if (this.currentMod.getCustomModId() == null) {
            realmResultsFindAll = this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(this.currentMod.getModNumber())).findAll();
        } else {
            realmResultsFindAll = this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.customModId, this.currentMod.getCustomModId()).findAll();
        }
        if (realmResultsFindAll.isEmpty()) {
            this.realm.beginTransaction();
            for (int i2 = 0; i2 < i; i2++) {
                ModDiyColor modDiyColor = (ModDiyColor) this.realm.createObject(ModDiyColor.class);
                modDiyColor.setModNumber(this.currentMod.getModNumber());
                modDiyColor.setColorIndex(i2);
                modDiyColor.setDiyColor(-2);
                modDiyColor.setDiyColorR(-2);
                if (this.currentMod.getCustomModId() != null) {
                    modDiyColor.setCustomModId(this.currentMod.getCustomModId());
                }
                this.modDiyColorList.add(modDiyColor);
            }
            this.realm.commitTransaction();
        } else {
            this.modDiyColorList.addAll(realmResultsFindAll);
        }
        if (this.isNewMode && (this.isInclude0912 || this.isSupportModeLightness)) {
            this.modDiyColorList.remove(3);
        }
        this.modDiyColorAdapter.notifyDataSetChanged();
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver
    public void onColor(int i, boolean z, boolean z2) {
        if (z) {
            this.currentColor = i;
            setColorCommand();
            if (this.colorSeekBarLayout.getVisibility() == 0) {
                upDateRgbValue();
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar == this.redSeekBar) {
            int progress = seekBar.getProgress();
            this.currentRedProgress = progress;
            if (this.currentGreenProgress == 0 && this.currentBlueProgress == 0 && progress == 0) {
                int i2 = progress + 1;
                this.currentRedProgress = i2;
                this.redSeekBar.setProgress(i2);
            }
            String strValueOf = String.valueOf(this.currentRedProgress);
            this.redStr = strValueOf;
            TextView textView = this.redValue;
            if (textView != null) {
                textView.setText(strValueOf);
                return;
            }
            return;
        }
        if (seekBar == this.greenSeekBar) {
            int progress2 = seekBar.getProgress();
            this.currentGreenProgress = progress2;
            if (this.currentRedProgress == 0 && this.currentBlueProgress == 0 && progress2 == 0) {
                int i3 = progress2 + 1;
                this.currentGreenProgress = i3;
                this.greenSeekBar.setProgress(i3);
            }
            String strValueOf2 = String.valueOf(this.currentGreenProgress);
            this.greenStr = strValueOf2;
            TextView textView2 = this.greenValue;
            if (textView2 != null) {
                textView2.setText(strValueOf2);
                return;
            }
            return;
        }
        if (seekBar == this.blueSeekBar) {
            int progress3 = seekBar.getProgress();
            this.currentBlueProgress = progress3;
            if (this.currentRedProgress == 0 && this.currentGreenProgress == 0 && progress3 == 0) {
                int i4 = progress3 + 1;
                this.currentBlueProgress = i4;
                this.blueSeekBar.setProgress(i4);
            }
            String strValueOf3 = String.valueOf(this.currentBlueProgress);
            this.blueStr = strValueOf3;
            TextView textView3 = this.blueValue;
            if (textView3 != null) {
                textView3.setText(strValueOf3);
                return;
            }
            return;
        }
        if (seekBar == this.speedSeekBar) {
            int progress4 = seekBar.getProgress() + 1;
            this.currentSpeed = progress4;
            this.speedValue.setText(String.valueOf(progress4));
        } else if (seekBar == this.brightnessSeekBar) {
            int progress5 = seekBar.getProgress() + 1;
            this.currentBrightness = progress5;
            this.brightnessValue.setText(String.valueOf(progress5));
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == this.redSeekBar) {
            int progress = seekBar.getProgress();
            this.currentRedProgress = progress;
            String strValueOf = String.valueOf(progress);
            this.redStr = strValueOf;
            this.redValue.setText(strValueOf);
            setRgbColor();
            return;
        }
        if (seekBar == this.greenSeekBar) {
            int progress2 = seekBar.getProgress();
            this.currentGreenProgress = progress2;
            String strValueOf2 = String.valueOf(progress2);
            this.greenStr = strValueOf2;
            this.greenValue.setText(strValueOf2);
            setRgbColor();
            return;
        }
        if (seekBar == this.blueSeekBar) {
            int progress3 = seekBar.getProgress();
            this.currentBlueProgress = progress3;
            String strValueOf3 = String.valueOf(progress3);
            this.blueStr = strValueOf3;
            this.blueValue.setText(strValueOf3);
            setRgbColor();
            return;
        }
        if (seekBar == this.speedSeekBar) {
            int progress4 = seekBar.getProgress() + 1;
            this.currentSpeed = progress4;
            this.speedValue.setText(String.valueOf(progress4));
            setModSpeed();
            return;
        }
        if (seekBar == this.brightnessSeekBar) {
            int progress5 = seekBar.getProgress() + 1;
            this.currentBrightness = progress5;
            this.brightnessValue.setText(String.valueOf(progress5));
            setModSpeed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceRename() {
        this.deviceNameText.setText(this.currentMod.getCustomModName());
        this.deviceNameEdit.setText(this.currentMod.getCustomModName());
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
    }

    public void deleteMod() {
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.topLayout.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.deleteMod);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ModSetActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$deleteMod$1(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteMod$1(CustomDialog customDialog) {
        customDialog.dismiss();
        this.realm.beginTransaction();
        this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.customModId, this.currentMod.getCustomModId()).findAll().deleteAllFromRealm();
        this.currentMod.deleteFromRealm();
        this.realm.commitTransaction();
        finish();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int i;
        int i2;
        int i3;
        int id = view.getId();
        if (id == R.id.modSet_Red_up) {
            int i4 = this.currentRedProgress;
            if (i4 < 255) {
                int i5 = i4 + 1;
                this.currentRedProgress = i5;
                this.redSeekBar.setProgress(i5);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.modSet_Red_down) {
            if (!(this.currentGreenProgress == 0 && this.currentBlueProgress == 0 && this.currentRedProgress == 1) && (i3 = this.currentRedProgress) > 0) {
                int i6 = i3 - 1;
                this.currentRedProgress = i6;
                this.redSeekBar.setProgress(i6);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.modSet_Green_up) {
            int i7 = this.currentGreenProgress;
            if (i7 < 255) {
                int i8 = i7 + 1;
                this.currentGreenProgress = i8;
                this.greenSeekBar.setProgress(i8);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.modSet_Green_down) {
            if (!(this.currentRedProgress == 0 && this.currentBlueProgress == 0 && this.currentGreenProgress == 1) && (i2 = this.currentGreenProgress) > 0) {
                int i9 = i2 - 1;
                this.currentGreenProgress = i9;
                this.greenSeekBar.setProgress(i9);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.modSet_Blue_up) {
            int i10 = this.currentBlueProgress;
            if (i10 < 255) {
                int i11 = i10 + 1;
                this.currentBlueProgress = i11;
                this.blueSeekBar.setProgress(i11);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.modSet_Blue_down) {
            if (!(this.currentRedProgress == 0 && this.currentGreenProgress == 0 && this.currentBlueProgress == 1) && (i = this.currentBlueProgress) > 0) {
                int i12 = i - 1;
                this.currentBlueProgress = i12;
                this.blueSeekBar.setProgress(i12);
                setRgbColor();
            }
        }
    }

    private void setRgbColor() {
        this.colorWheelView.setColor1(Color.rgb(this.currentRedProgress, this.currentGreenProgress, this.currentBlueProgress), false);
    }

    public void upDateRgbValue() {
        int i = this.currentColor;
        if (i == -3) {
            this.currentRedProgress = 0;
            this.currentGreenProgress = 0;
            this.currentBlueProgress = 0;
        } else {
            this.currentRedProgress = Color.red(i);
            this.currentGreenProgress = Color.green(this.currentColor);
            this.currentBlueProgress = Color.blue(this.currentColor);
        }
        this.redSeekBar.setProgress(this.currentRedProgress);
        String strValueOf = String.valueOf(this.currentRedProgress);
        this.redStr = strValueOf;
        this.redValue.setText(strValueOf);
        this.greenSeekBar.setProgress(this.currentGreenProgress);
        String strValueOf2 = String.valueOf(this.currentGreenProgress);
        this.greenStr = strValueOf2;
        this.greenValue.setText(strValueOf2);
        this.blueSeekBar.setProgress(this.currentBlueProgress);
        String strValueOf3 = String.valueOf(this.currentBlueProgress);
        this.blueStr = strValueOf3;
        this.blueValue.setText(strValueOf3);
    }

    private void setColorCommand() {
        int iRed = Color.red(this.currentColor);
        int iGreen = Color.green(this.currentColor);
        int iBlue = Color.blue(this.currentColor);
        if (iRed == 255 && iGreen == 255 && iBlue == 255) {
            iBlue = 254;
        }
        int i = iBlue;
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().singleRgbCtrl(this.addr, 127, iRed, iGreen, i);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupRgbCtrl(43050, this.tempGroupId, 127, iRed, iGreen, i);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupRgbCtrl(43050, this.groupId, 127, iRed, iGreen, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setModSpeed() {
        SceneDevice sceneDevice;
        this.realm.beginTransaction();
        if (this.sceneNumber != -1 && (sceneDevice = this.sceneDevice) != null) {
            sceneDevice.setTempSpeed(this.currentSpeed);
            if (this.isShowBrightness) {
                this.sceneDevice.setModBrightness(this.currentBrightness);
            }
        } else {
            this.currentMod.setSpeed(this.currentSpeed);
            if (this.isShowBrightness) {
                this.currentMod.setBrightness(this.currentBrightness);
            }
        }
        this.realm.commitTransaction();
        if (this.currentMod.getCustomModId() == null) {
            enableModCommand(speedExchange(this.currentSpeed));
        } else {
            customModCommand(speedExchange(this.currentSpeed));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int speedExchange(int i) {
        if (this.currentMod.getCustomModId() != null) {
            if (this.currentMod.isFlash()) {
                return this.maxJump - ((i - 1) * this.step);
            }
            return this.minFade + (i - 1);
        }
        if (this.currentMod.getModNumber() < 5 || ((this.currentMod.getModNumber() > 17 && this.currentMod.getModNumber() < 22) || this.currentMod.getModNumber() > 25)) {
            return this.minFade + (i - 1);
        }
        return this.maxJump - ((i - 1) * this.step);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void enableModCommand(int i) {
        BLESceneInfo bLESceneInfoFullColor;
        switch (this.currentMod.getModNumber()) {
            case 1:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().fullColor(i);
                break;
            case 2:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().prColor(i);
                break;
            case 3:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gbColor(i);
                break;
            case 4:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().yrColor(i);
                break;
            case 5:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().jumpFullColor(i);
                break;
            case 6:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgbJumpColor(i);
                break;
            case 7:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().ygJumpColor(i);
                break;
            case 8:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().pgJumpColor(i);
                break;
            case 9:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().pbJumpColor(i);
                break;
            case 10:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().ypJumpColor(i);
                break;
            case 11:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgJumpColor(i);
                break;
            case 12:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rbJumpColor(i);
                break;
            case 13:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gbJumpColor(i);
                break;
            case 14:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rJumpColor(i);
                break;
            case 15:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gJumpColor(i);
                break;
            case 16:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().bJumpColor(i);
                break;
            case 17:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgbWhiteJumpColor(i);
                break;
            case 18:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgColor(i);
                break;
            case 19:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().bwColor(i);
                break;
            case 20:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gwColor(i);
                break;
            case 21:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rwColor(i);
                break;
            case 22:
            case 23:
            case 24:
            case 25:
                if (this.defaultRecyclerView.getVisibility() == 8) {
                    enableNewMod(1, i, this.modDiyColorList);
                    bLESceneInfoFullColor = null;
                } else {
                    bLESceneInfoFullColor = GlobalBluetooth.getInstance().diyJumpColor(this.modDiyColorList, i);
                }
                break;
            case 26:
            case 27:
            case 28:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                if (this.defaultRecyclerView.getVisibility() == 8) {
                    enableNewMod(0, i, this.modDiyColorList);
                    bLESceneInfoFullColor = null;
                } else {
                    bLESceneInfoFullColor = GlobalBluetooth.getInstance().diyColor(this.modDiyColorList, i);
                }
                break;
            default:
                bLESceneInfoFullColor = null;
                break;
        }
        if (bLESceneInfoFullColor != null) {
            enableMod(bLESceneInfoFullColor);
        }
    }

    private void enableMod(BLESceneInfo bLESceneInfo) {
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().modSingleCtrl(this.addr, bLESceneInfo);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().modGroupCtrl(this.tempGroupId, bLESceneInfo);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().modGroupCtrl(this.groupId, bLESceneInfo);
        }
    }

    private void enableNewMod(int i, int i2, List<ModDiyColor> list) {
        int i3 = (int) ((this.currentBrightness / 100.0f) * 127.0f);
        if (this.addr != -1) {
            if (this.isShowBrightness) {
                GlobalBluetooth.getInstance().diyColorLChange(0, this.addr, i, i2, i3, list);
                return;
            } else {
                GlobalBluetooth.getInstance().diyColorChange(0, this.addr, i, i2, list);
                return;
            }
        }
        if (this.tempGroupId != -1) {
            if (this.isShowBrightness) {
                GlobalBluetooth.getInstance().diyColorLChange(1, this.tempGroupId, i, i2, i3, list);
                return;
            } else {
                GlobalBluetooth.getInstance().diyColorChange(1, this.tempGroupId, i, i2, list);
                return;
            }
        }
        if (this.groupId != -1) {
            if (this.isShowBrightness) {
                GlobalBluetooth.getInstance().diyColorLChange(1, this.groupId, i, i2, i3, list);
            } else {
                GlobalBluetooth.getInstance().diyColorChange(1, this.groupId, i, i2, list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void customModCommand(int i) {
        if (this.currentMod.isFlash()) {
            enableNewMod(1, i, this.modDiyColorList);
        } else {
            enableNewMod(0, i, this.modDiyColorList);
        }
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
}

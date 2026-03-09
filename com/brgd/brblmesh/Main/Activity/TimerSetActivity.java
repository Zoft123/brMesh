package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo;
import cn.com.broadlink.blelight.bean.BLETimeInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import cn.com.broadlink.blelight.interfaces.OnDevColorTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevTimerCallback;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import com.brgd.brblmesh.GeneralClass.CycleWheelView;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class TimerSetActivity extends FatherActivity {
    ImageView backView;
    private ArrayList<BLERgbcwTimerInfo> bleRgbcwTimerInfos;
    private int blue;
    private int brightness;
    private TextView brightnessText;
    private int cold;
    private int color;
    ImageView colorCtrlView;
    private ColorSelector colorSelector;
    private TextView colorText;
    private int currentAction;
    private int currentHour;
    private int currentMinute;
    private Timer currentTimer;
    CycleWheelView cycleWheelView1;
    CycleWheelView cycleWheelView2;
    private int green;
    private int indexNum;
    private boolean isNot65V;
    private boolean isRepeat;
    public boolean isSupportTimerStatus;
    private ImageView lineView;
    private BLETimerAllInfo mBleTimerAllInfo;
    private java.util.Timer mTimer;
    private Dialog modifyDialog;
    private TextView onOffText;
    private ImageView onOffView;
    private Realm realm;
    private int red;
    private TextView repeatText;
    private ImageView repeatView;
    ImageView saveView;
    private ConstraintLayout stateLayout;
    private List<Timer> timerList;
    private int warm;
    private int addr = -1;
    public int type = -1;
    private final int SET_OK = 101;
    private final int TIME_OUT = 102;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.timerSet_back) {
                TimerSetActivity.this.finish();
                return;
            }
            if (id == R.id.timerSet_repeat_onOff) {
                if (TimerSetActivity.this.repeatView.isSelected()) {
                    TimerSetActivity.this.repeatView.setSelected(false);
                    TimerSetActivity.this.repeatText.setText(R.string.once);
                    return;
                } else {
                    TimerSetActivity.this.repeatView.setSelected(true);
                    TimerSetActivity.this.repeatText.setText(R.string.repeat);
                    return;
                }
            }
            if (id == R.id.timerSet_power_onOff) {
                if (TimerSetActivity.this.onOffView.isSelected()) {
                    TimerSetActivity.this.onOffView.setSelected(false);
                    TimerSetActivity.this.onOffText.setText(R.string.off);
                    if (TimerSetActivity.this.isSupportTimerStatus && TimerSetActivity.this.isNot65V) {
                        TimerSetActivity.this.stateLayout.setVisibility(4);
                        TimerSetActivity.this.brightness = 0;
                        TimerSetActivity.this.red = 0;
                        TimerSetActivity.this.green = 0;
                        TimerSetActivity.this.blue = 0;
                        TimerSetActivity.this.warm = 0;
                        TimerSetActivity.this.cold = 0;
                        return;
                    }
                    return;
                }
                TimerSetActivity.this.onOffView.setSelected(true);
                TimerSetActivity.this.onOffText.setText(R.string.on);
                if (TimerSetActivity.this.isSupportTimerStatus && TimerSetActivity.this.isNot65V) {
                    TimerSetActivity.this.stateLayout.setVisibility(0);
                    TimerSetActivity.this.handleOpenAction();
                    return;
                }
                return;
            }
            if (id == R.id.timerSet_save) {
                for (Timer timer : TimerSetActivity.this.timerList) {
                    if (timer.getHour() == TimerSetActivity.this.currentHour && timer.getMinute() == TimerSetActivity.this.currentMinute && timer.getIndexNum() != TimerSetActivity.this.currentTimer.getIndexNum() && timer.isEnable()) {
                        GlobalToast.showText(TimerSetActivity.this.getApplicationContext(), R.string.canNotSaveSameTimerTask, 1);
                        return;
                    }
                }
                TimerSetActivity.this.startReceiveService();
                TimerSetActivity.this.createTimer();
                return;
            }
            if (id == R.id.timerSet_ctrl) {
                Intent intent = new Intent(TimerSetActivity.this, (Class<?>) TimerSetColorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.addr, TimerSetActivity.this.addr);
                bundle.putInt("indexNum", TimerSetActivity.this.indexNum);
                intent.putExtra(GlobalVariable.checkTimer, bundle);
                TimerSetActivity.this.startActivity(intent);
            }
        }
    };
    private int timeCount = 0;
    private final OnDevTimerCallback mOnDevTimerCallback = new OnDevTimerCallback() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetActivity.3
        @Override // cn.com.broadlink.blelight.interfaces.OnDevTimerCallback
        public void onCallback(int i, BLETimerAllInfo bLETimerAllInfo) {
            if (bLETimerAllInfo.addr != TimerSetActivity.this.addr) {
                return;
            }
            BLETimeInfo bLETimeInfo = bLETimerAllInfo.schedInfoList.get(TimerSetActivity.this.indexNum);
            if (bLETimeInfo.enable == 1 && bLETimeInfo.onoff == TimerSetActivity.this.currentAction && bLETimeInfo.repeat == TimerSetActivity.this.isRepeat && bLETimeInfo.hour == TimerSetActivity.this.currentHour && bLETimeInfo.min == TimerSetActivity.this.currentMinute) {
                TimerSetActivity.this.stopReceiveService();
                TimerSetActivity.this.stopTimer();
                TimerSetActivity.this.myHandler.sendMessage(TimerSetActivity.this.myHandler.obtainMessage(101, bLETimerAllInfo));
            }
        }
    };
    private final OnDevColorTimerCallback mOnDevColorTimerCallback = new OnDevColorTimerCallback() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetActivity.4
        @Override // cn.com.broadlink.blelight.interfaces.OnDevColorTimerCallback
        public void onCallback(int i, ArrayList<BLERgbcwTimerInfo> arrayList) {
            if (i != TimerSetActivity.this.addr) {
                return;
            }
            BLERgbcwTimerInfo bLERgbcwTimerInfo = arrayList.get(TimerSetActivity.this.indexNum);
            if (bLERgbcwTimerInfo.time.enable == 1 && bLERgbcwTimerInfo.time.onoff == TimerSetActivity.this.currentAction && bLERgbcwTimerInfo.time.repeat == TimerSetActivity.this.isRepeat && bLERgbcwTimerInfo.time.hour == TimerSetActivity.this.currentHour && bLERgbcwTimerInfo.time.min == TimerSetActivity.this.currentMinute) {
                TimerSetActivity.this.stopReceiveService();
                TimerSetActivity.this.stopTimer();
                TimerSetActivity.this.myHandler.sendMessage(TimerSetActivity.this.myHandler.obtainMessage(101, arrayList));
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_timer_set);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.timerSet_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.cycleWheelView1 = (CycleWheelView) findViewById(R.id.cycleWheelView1);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                arrayList.add(GlobalVariable.ILLUMINATION + i);
            } else {
                arrayList.add(String.valueOf(i));
            }
        }
        this.cycleWheelView1.setLabels(arrayList);
        try {
            this.cycleWheelView1.setWheelSize(5);
        } catch (CycleWheelView.CycleWheelViewException e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
        this.cycleWheelView1.setDivider(CustomColor.DIVIDER, Tool.sp2px(this, 1.0f));
        this.cycleWheelView1.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.CycleWheelView.WheelItemSelectedListener
            public final void onItemSelected(int i2, String str) {
                this.f$0.lambda$onCreate$0(i2, str);
            }
        });
        this.cycleWheelView2 = (CycleWheelView) findViewById(R.id.cycleWheelView2);
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            if (i2 < 10) {
                arrayList2.add(GlobalVariable.ILLUMINATION + i2);
            } else {
                arrayList2.add(String.valueOf(i2));
            }
        }
        this.cycleWheelView2.setLabels(arrayList2);
        try {
            this.cycleWheelView2.setWheelSize(5);
        } catch (CycleWheelView.CycleWheelViewException e2) {
            Log.d("printStackTrace", "printStackTrace" + e2);
        }
        this.cycleWheelView2.setDivider(CustomColor.DIVIDER, Tool.sp2px(this, 1.0f));
        this.cycleWheelView2.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.CycleWheelView.WheelItemSelectedListener
            public final void onItemSelected(int i3, String str) {
                this.f$0.lambda$onCreate$1(i3, str);
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.timerSet_repeat_onOff);
        this.repeatView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.repeatText = (TextView) findViewById(R.id.timerSet_repeat_text);
        ImageView imageView3 = (ImageView) findViewById(R.id.timerSet_power_onOff);
        this.onOffView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        this.onOffText = (TextView) findViewById(R.id.timerSet_power_onOffText);
        ImageView imageView4 = (ImageView) findViewById(R.id.timerSet_save);
        this.saveView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.stateLayout = (ConstraintLayout) findViewById(R.id.timerSet_ctrl_layout);
        ImageView imageView5 = (ImageView) findViewById(R.id.timerSet_ctrl);
        this.colorCtrlView = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        this.colorText = (TextView) findViewById(R.id.timerSet_colorText);
        this.colorSelector = (ColorSelector) findViewById(R.id.timerSet_colorBtn);
        this.lineView = (ImageView) findViewById(R.id.timerSet_line);
        this.brightnessText = (TextView) findViewById(R.id.timerSet_lightness);
        this.timerList = new ArrayList();
        Bundle bundleExtra = getIntent().getBundleExtra(GlobalVariable.checkTimer);
        if (bundleExtra != null) {
            this.addr = bundleExtra.getInt(GlobalVariable.tdAddr);
            this.indexNum = bundleExtra.getInt(GlobalVariable.timerIndex);
            this.isSupportTimerStatus = bundleExtra.getBoolean(GlobalVariable.timerStatus);
            BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
            if (bleDevice != null) {
                this.type = bleDevice.getType();
                this.isNot65V = Tool.isNot65V(bleDevice.getVersion());
            }
            if (this.isSupportTimerStatus && this.isNot65V) {
                this.bleRgbcwTimerInfos = bundleExtra.getParcelableArrayList(GlobalVariable.timerAllInfo);
            } else {
                this.mBleTimerAllInfo = (BLETimerAllInfo) bundleExtra.getParcelable(GlobalVariable.timerAllInfo);
            }
            this.timerList.addAll(this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findAll());
            Timer timer = (Timer) this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("indexNum", Integer.valueOf(this.indexNum)).findFirst();
            this.currentTimer = timer;
            if (timer != null) {
                this.currentHour = timer.getHour();
                this.currentMinute = this.currentTimer.getMinute();
                this.currentAction = this.currentTimer.getAction();
                this.isRepeat = this.currentTimer.isRepeat();
                if (this.isSupportTimerStatus && this.isNot65V) {
                    this.brightness = this.currentTimer.getBrightness();
                    this.warm = this.currentTimer.getWarm();
                    this.cold = this.currentTimer.getCold();
                    this.red = this.currentTimer.getRed();
                    this.green = this.currentTimer.getGreen();
                    this.blue = this.currentTimer.getBlue();
                }
                this.cycleWheelView1.setSelection(this.currentHour);
                this.cycleWheelView2.setSelection(this.currentMinute);
                if (this.isRepeat) {
                    this.repeatText.setText(R.string.repeat);
                    this.repeatView.setSelected(true);
                } else {
                    this.repeatText.setText(R.string.once);
                    this.repeatView.setSelected(false);
                }
                int i3 = this.currentAction;
                if (i3 == 0) {
                    this.onOffText.setText(R.string.off);
                    this.onOffView.setSelected(false);
                } else if (i3 == 1) {
                    this.onOffText.setText(R.string.on);
                    this.onOffView.setSelected(true);
                    if (this.isSupportTimerStatus && this.isNot65V) {
                        this.stateLayout.setVisibility(0);
                        handleOpenAction();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(int i, String str) {
        this.currentHour = Integer.parseInt(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(int i, String str) {
        this.currentMinute = Integer.parseInt(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.isSupportTimerStatus && this.isNot65V) {
            handleOpenAction();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        stopReceiveService();
        stopTimer();
        Dialog dialog = this.modifyDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.modifyDialog = null;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        SharePreferenceUtils.remove(this, GlobalVariable.tempColorValue);
        SharePreferenceUtils.remove(this, GlobalVariable.tempColorValueR);
        SharePreferenceUtils.remove(this, GlobalVariable.tempColorTempValue);
        SharePreferenceUtils.remove(this, GlobalVariable.tempLightnessValue);
        stopReceiveService();
        stopTimer();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void openDefaultSate() {
        int i = this.type;
        if (i == 43049) {
            this.brightness = 100;
            this.colorText.setVisibility(8);
            this.lineView.setVisibility(8);
            this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
            return;
        }
        if (i == 43051) {
            float f = 50 / 100.0f;
            int i2 = (int) (255.0f * f);
            this.warm = i2;
            this.cold = 255 - i2;
            this.colorText.setText(R.string.State);
            this.colorSelector.setVisibility(0);
            this.colorSelector.setSelectorColor(ColorUtils.blendARGB(-1, CustomColor.W_WHITE, f));
            this.colorSelector.setBorderSelectorColor(CustomColor.SELECT);
        } else {
            this.red = 255;
            this.green = 0;
            this.blue = 0;
            this.color = -65536;
            this.colorText.setText(R.string.State);
            this.colorSelector.setVisibility(0);
            this.colorSelector.setSelectorColor(this.color);
        }
        this.brightness = 100;
        this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOpenAction() {
        this.colorSelector.setBorderSelectorColor(0);
        if (this.currentTimer.getWarm() != 0 || this.currentTimer.getCold() != 0) {
            this.warm = this.currentTimer.getWarm();
            this.cold = this.currentTimer.getCold();
        }
        if (this.currentTimer.getBrightness() != 0) {
            this.brightness = this.currentTimer.getBrightness();
        }
        if (this.currentTimer.getRed() != 0 || this.currentTimer.getGreen() != 0 || this.currentTimer.getBlue() != 0) {
            this.red = this.currentTimer.getRed();
            this.green = this.currentTimer.getGreen();
            this.blue = this.currentTimer.getBlue();
            this.color = this.currentTimer.getColor();
        }
        if (this.brightness == 0 && this.red == 0 && this.green == 0 && this.blue == 0 && this.warm == 0 && this.cold == 0 && !SharePreferenceUtils.contains(this, GlobalVariable.tempColorTempValue) && !SharePreferenceUtils.contains(this, GlobalVariable.tempColorValue) && !SharePreferenceUtils.contains(this, GlobalVariable.tempLightnessValue)) {
            openDefaultSate();
            return;
        }
        int i = this.type;
        if (i == 43050) {
            this.colorText.setText(R.string.State);
            this.colorSelector.setVisibility(0);
            if (SharePreferenceUtils.contains(this, GlobalVariable.tempColorValue)) {
                this.color = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorValue, -1)).intValue();
                int iIntValue = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorValueR, -1)).intValue();
                this.red = Color.red(iIntValue);
                this.green = Color.green(iIntValue);
                this.blue = Color.blue(iIntValue);
                if (Tool.isRGBW(iIntValue)) {
                    this.colorSelector.setSelectorColor(Color.argb(25, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
                } else {
                    this.colorSelector.setSelectorColor(this.color);
                }
            } else {
                int i2 = this.red;
                if (i2 != 0 || this.green != 0 || this.blue != 0) {
                    if (Tool.isRGBW(Color.rgb(i2, this.green, this.blue))) {
                        this.colorSelector.setSelectorColor(Color.argb(25, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
                    } else {
                        this.colorSelector.setSelectorColor(this.color);
                    }
                } else if (SharePreferenceUtils.contains(this, GlobalVariable.tempColorTempValue)) {
                    float fIntValue = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorTempValue, -1)).intValue() / 100.0f;
                    this.colorSelector.setSelectorColor(ColorUtils.blendARGB(-1, CustomColor.W_WHITE, fIntValue));
                    this.colorSelector.setBorderSelectorColor(CustomColor.SELECT);
                    int i3 = (int) (fIntValue * 255.0f);
                    this.warm = i3;
                    this.cold = 255 - i3;
                } else {
                    if (this.warm != 0 || this.cold != 0) {
                        this.colorSelector.setSelectorColor(ColorUtils.blendARGB(-1, CustomColor.W_WHITE, ((int) ((r0 / 255.0f) * 100.0f)) / 100.0f));
                        this.colorSelector.setBorderSelectorColor(CustomColor.SELECT);
                    }
                }
            }
            if (SharePreferenceUtils.contains(this, GlobalVariable.tempLightnessValue)) {
                this.brightness = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempLightnessValue, -1)).intValue();
                this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
                return;
            }
            if (this.brightness != 0) {
                this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
                return;
            }
            return;
        }
        if (i == 43169 || i == 43168) {
            this.colorText.setText(R.string.State);
            this.colorSelector.setVisibility(0);
            if (SharePreferenceUtils.contains(this, GlobalVariable.tempColorValue)) {
                this.color = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorValue, -1)).intValue();
                int iIntValue2 = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorValueR, -1)).intValue();
                this.red = Color.red(iIntValue2);
                this.green = Color.green(iIntValue2);
                this.blue = Color.blue(iIntValue2);
                if (Tool.isRGBW(iIntValue2)) {
                    this.colorSelector.setSelectorColor(Color.argb(25, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
                } else {
                    this.colorSelector.setSelectorColor(this.color);
                }
            } else {
                int i4 = this.red;
                if (i4 != 0 || this.green != 0 || this.blue != 0) {
                    if (Tool.isRGBW(Color.rgb(i4, this.green, this.blue))) {
                        this.colorSelector.setSelectorColor(Color.argb(25, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
                    } else {
                        this.colorSelector.setSelectorColor(this.color);
                    }
                }
            }
            if (SharePreferenceUtils.contains(this, GlobalVariable.tempLightnessValue)) {
                this.brightness = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempLightnessValue, -1)).intValue();
                this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
                return;
            }
            if (this.brightness != 0) {
                this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
                return;
            }
            return;
        }
        if (i != 43051) {
            if (i == 43049) {
                this.colorText.setVisibility(8);
                this.lineView.setVisibility(8);
                if (SharePreferenceUtils.contains(this, GlobalVariable.tempLightnessValue)) {
                    this.brightness = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempLightnessValue, -1)).intValue();
                    this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
                    return;
                }
                if (this.brightness != 0) {
                    this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
                    return;
                }
                return;
            }
            return;
        }
        this.colorText.setText(R.string.State);
        this.colorSelector.setVisibility(0);
        if (SharePreferenceUtils.contains(this, GlobalVariable.tempColorTempValue)) {
            float fIntValue2 = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorTempValue, -1)).intValue() / 100.0f;
            this.colorSelector.setSelectorColor(ColorUtils.blendARGB(-1, CustomColor.W_WHITE, fIntValue2));
            this.colorSelector.setBorderSelectorColor(CustomColor.SELECT);
            int i5 = (int) (fIntValue2 * 255.0f);
            this.warm = i5;
            this.cold = 255 - i5;
        } else {
            if (this.warm != 0 || this.cold != 0) {
                this.colorSelector.setSelectorColor(ColorUtils.blendARGB(-1, CustomColor.W_WHITE, ((int) ((r0 / 255.0f) * 100.0f)) / 100.0f));
                this.colorSelector.setBorderSelectorColor(CustomColor.SELECT);
            }
        }
        if (SharePreferenceUtils.contains(this, GlobalVariable.tempLightnessValue)) {
            this.brightness = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempLightnessValue, -1)).intValue();
            this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
            return;
        }
        if (this.brightness != 0) {
            this.brightnessText.setText(getString(R.string.brightness) + " : " + this.brightness + "%");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTimer() {
        if (this.modifyDialog == null) {
            this.modifyDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.isRepeat = this.repeatView.isSelected();
        if (this.onOffView.isSelected()) {
            this.currentAction = 1;
        } else {
            this.currentAction = 0;
        }
        if (this.isSupportTimerStatus && this.isNot65V) {
            BLERgbcwTimerInfo bLERgbcwTimerInfo = this.bleRgbcwTimerInfos.get(this.indexNum);
            bLERgbcwTimerInfo.time.hour = this.currentHour;
            bLERgbcwTimerInfo.time.min = this.currentMinute;
            bLERgbcwTimerInfo.time.repeat = this.isRepeat ? 1 : 0;
            bLERgbcwTimerInfo.time.onoff = this.currentAction;
            bLERgbcwTimerInfo.time.enable = 1;
            bLERgbcwTimerInfo.bri = Math.round((this.brightness / 100.0f) * 127.0f);
            if (this.type == 43049) {
                bLERgbcwTimerInfo.time.type = 1;
                bLERgbcwTimerInfo.green = 127;
                bLERgbcwTimerInfo.red = 127;
            }
            if (this.type == 43051) {
                bLERgbcwTimerInfo.time.type = 1;
                bLERgbcwTimerInfo.green = this.warm;
                bLERgbcwTimerInfo.red = this.cold;
            }
            if (this.type == 43169) {
                bLERgbcwTimerInfo.time.type = 0;
                int i = this.red;
                int i2 = this.green;
                float f = i + i2 + this.blue;
                bLERgbcwTimerInfo.green = (int) ((i2 / f) * 255.0f);
                bLERgbcwTimerInfo.red = (int) ((i / f) * 255.0f);
            }
        } else {
            BLETimeInfo bLETimeInfo = this.mBleTimerAllInfo.schedInfoList.get(this.indexNum);
            bLETimeInfo.hour = this.currentHour;
            bLETimeInfo.min = this.currentMinute;
            bLETimeInfo.repeat = this.isRepeat ? 1 : 0;
            bLETimeInfo.onoff = this.currentAction;
            bLETimeInfo.enable = 1;
            this.mBleTimerAllInfo.lcTime = new BLETimeLcInfo();
            this.mBleTimerAllInfo.lcTime.hour = Integer.parseInt(Tool.getCurrentHour());
            this.mBleTimerAllInfo.lcTime.min = Integer.parseInt(Tool.getCurrentMinute());
            GlobalBluetooth.getInstance().timerSettingWithDevice(this.addr, this.mBleTimerAllInfo);
        }
        this.timeCount = 0;
        startTimer();
    }

    public void startReceiveService() {
        BLSBleLight.checkPermission();
        BLSBleLight.startBleReceiveService();
        if (this.isSupportTimerStatus && this.isNot65V) {
            BLSBleLight.setOnDevColorTimerCallback(this.mOnDevColorTimerCallback);
        } else {
            BLSBleLight.setOnTimerCallback(this.mOnDevTimerCallback);
        }
    }

    public void stopReceiveService() {
        BLSBleLight.stopBleReceiveServiceDelay();
        if (this.isSupportTimerStatus && this.isNot65V) {
            BLSBleLight.setOnDevColorTimerCallback(null);
        } else {
            BLSBleLight.setOnTimerCallback(null);
        }
    }

    private void startTimer() {
        stopTimer();
        java.util.Timer timer = new java.util.Timer();
        this.mTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetActivity.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (TimerSetActivity.this.timeCount < 12) {
                    TimerSetActivity.this.timeCount++;
                    GlobalBluetooth.getInstance().timerSettingWithDevice(TimerSetActivity.this.addr, TimerSetActivity.this.mBleTimerAllInfo);
                } else {
                    TimerSetActivity.this.stopReceiveService();
                    TimerSetActivity.this.stopTimer();
                    TimerSetActivity.this.myHandler.sendMessage(TimerSetActivity.this.myHandler.obtainMessage(102));
                }
            }
        }, 900L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTimer() {
        java.util.Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    public void updateTimer() {
        Timer timer = (Timer) this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("indexNum", Integer.valueOf(this.indexNum)).findFirst();
        this.realm.beginTransaction();
        if (timer == null) {
            timer = (Timer) this.realm.createObject(Timer.class);
            timer.setIndexNum(this.indexNum);
            timer.setAddr(this.addr);
        }
        timer.setHour(this.currentHour);
        timer.setMinute(this.currentMinute);
        timer.setAction(this.currentAction);
        timer.setEnable(true);
        timer.setRepeat(this.isRepeat > 0);
        if (this.isSupportTimerStatus && this.isNot65V) {
            timer.setBrightness(this.brightness);
            if (this.type == 43049) {
                timer.setWarm(0);
                timer.setCold(0);
            }
            if (this.type == 43051) {
                timer.setWarm(this.warm);
                timer.setCold(this.cold);
            }
            if (this.type == 43169) {
                timer.setRed(this.red);
                timer.setGreen(this.green);
                timer.setBlue(this.blue);
                timer.setColor(this.color);
            }
        }
        this.realm.commitTransaction();
        Dialog dialog = this.modifyDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.modifyDialog = null;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeout() {
        Dialog dialog = this.modifyDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.modifyDialog = null;
        }
        GlobalToast.showText(getApplicationContext(), R.string.tryAgain, 1);
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            TimerSetActivity timerSetActivity = (TimerSetActivity) this.weakReference.get();
            int i = message.what;
            Objects.requireNonNull(timerSetActivity);
            if (i == 101) {
                timerSetActivity.updateTimer();
                return;
            }
            int i2 = message.what;
            Objects.requireNonNull(timerSetActivity);
            if (i2 == 102) {
                timerSetActivity.handleTimeout();
            }
        }
    }
}

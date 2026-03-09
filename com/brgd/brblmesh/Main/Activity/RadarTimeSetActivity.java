package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.CycleWheelView;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyScrollView;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class RadarTimeSetActivity extends FatherActivity implements SeekBar.OnSeekBarChangeListener {
    ImageView backView;
    ImageView brightnessDownView;
    private SeekBar brightnessSeekBar;
    private String brightnessStr;
    TextView brightnessText;
    ImageView brightnessUpView;
    private TextView brightnessValue;
    TextView cancelView;
    private int currentBrightnessProgress;
    private int currentHourValue1;
    private int currentHourValue2;
    private int currentHourValue3;
    private int currentHourValue4;
    private int currentMinValue1;
    private int currentMinValue2;
    private int currentMinValue3;
    private int currentMinValue4;
    CycleWheelView cycleWheelView1;
    CycleWheelView cycleWheelView2;
    private ConstraintLayout dateBgLayout;
    ImageView dateView;
    private int enable;
    private ImageView enableOnOff;
    private TextView endView;
    private ImageView executionBottomLine;
    private TextView executionFixedTest;
    private ImageView executionOnOff;
    private Dialog loadingDialog;
    private int out_lightness;
    private Realm realm;
    TextView saveTextView;
    TextView saveView;
    private MyScrollView scrollView;
    private TextView startView;
    private ImageView timeSetBg1;
    private ImageView timeSetBg2;
    private TextView timeSetValue1;
    String timeSetValue1Str;
    private TextView timeSetValue2;
    String timeSetValue2Str;
    ImageView titleBgView;
    public int viewType = 43050;
    private int groupId = -1;
    private int tempGroupId = -1;
    private int type = -1;
    private int addr = -1;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarTimeSetActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.radarTimeSet_back) {
                RadarTimeSetActivity.this.finish();
                return;
            }
            if (id == R.id.radarTimeSet_save) {
                RadarTimeSetActivity.this.saveAction();
                return;
            }
            if (id == R.id.radarTimeSet_enableBg_onOff) {
                if (RadarTimeSetActivity.this.enableOnOff.isSelected()) {
                    RadarTimeSetActivity.this.enableOnOff.setSelected(false);
                    RadarTimeSetActivity.this.scrollView.setVisibility(4);
                    return;
                } else {
                    RadarTimeSetActivity.this.enableOnOff.setSelected(true);
                    RadarTimeSetActivity.this.scrollView.setVisibility(0);
                    return;
                }
            }
            if (id == R.id.radarTimeSet_executionOnOff) {
                if (RadarTimeSetActivity.this.executionOnOff.isSelected()) {
                    RadarTimeSetActivity.this.executionOnOff.setSelected(false);
                    RadarTimeSetActivity.this.executionFixedTest.setText(RadarTimeSetActivity.this.getString(R.string.OffLight));
                    RadarTimeSetActivity.this.executionBottomLine.setVisibility(0);
                    RadarTimeSetActivity.this.brightnessText.setVisibility(8);
                    RadarTimeSetActivity.this.brightnessValue.setVisibility(8);
                    RadarTimeSetActivity.this.brightnessSeekBar.setVisibility(8);
                    RadarTimeSetActivity.this.brightnessUpView.setVisibility(8);
                    RadarTimeSetActivity.this.brightnessDownView.setVisibility(8);
                    return;
                }
                RadarTimeSetActivity.this.executionOnOff.setSelected(true);
                RadarTimeSetActivity.this.executionFixedTest.setText(RadarTimeSetActivity.this.getString(R.string.Fixlightness));
                RadarTimeSetActivity.this.executionBottomLine.setVisibility(8);
                RadarTimeSetActivity.this.brightnessText.setVisibility(0);
                RadarTimeSetActivity.this.brightnessValue.setVisibility(0);
                RadarTimeSetActivity.this.brightnessSeekBar.setVisibility(0);
                RadarTimeSetActivity.this.brightnessUpView.setVisibility(0);
                RadarTimeSetActivity.this.brightnessDownView.setVisibility(0);
                return;
            }
            if (id == R.id.radarSet_off_brightness_up) {
                if (RadarTimeSetActivity.this.currentBrightnessProgress < 100) {
                    RadarTimeSetActivity.this.currentBrightnessProgress++;
                }
                RadarTimeSetActivity.this.brightnessSeekBar.setProgress(RadarTimeSetActivity.this.currentBrightnessProgress - 1);
                return;
            }
            if (id == R.id.radarSet_off_brightness_down) {
                if (RadarTimeSetActivity.this.currentBrightnessProgress > 1) {
                    RadarTimeSetActivity.this.currentBrightnessProgress--;
                }
                RadarTimeSetActivity.this.brightnessSeekBar.setProgress(RadarTimeSetActivity.this.currentBrightnessProgress - 1);
                return;
            }
            if (id == R.id.radarTimeSet_timeSetBg1) {
                RadarTimeSetActivity.this.timeSetBg1.setSelected(true);
                RadarTimeSetActivity.this.showTimeSelectView();
                return;
            }
            if (id == R.id.radarTimeSet_timeSetBg2) {
                RadarTimeSetActivity.this.timeSetBg2.setSelected(true);
                RadarTimeSetActivity.this.showTimeSelectView();
                return;
            }
            if (id == R.id.radarTimeSet_dateBg) {
                RadarTimeSetActivity.this.hideTimeSelectView();
                RadarTimeSetActivity.this.getStoreTimeValue();
                return;
            }
            if (id == R.id.radarTimeSet_dateStartTime) {
                RadarTimeSetActivity.this.clickStarTimeAction();
                return;
            }
            if (id == R.id.radarTimeSet_dateEndTime) {
                RadarTimeSetActivity.this.endView.setSelected(true);
                RadarTimeSetActivity.this.endView.setTextColor(RadarTimeSetActivity.this.getColor(R.color.colorText));
                RadarTimeSetActivity.this.startView.setSelected(false);
                RadarTimeSetActivity.this.startView.setTextColor(RadarTimeSetActivity.this.getColor(R.color.colorTextOffline));
                if (RadarTimeSetActivity.this.timeSetBg1.isSelected()) {
                    RadarTimeSetActivity.this.cycleWheelView1.setSelection(RadarTimeSetActivity.this.currentHourValue2);
                    RadarTimeSetActivity.this.cycleWheelView2.setSelection(RadarTimeSetActivity.this.currentMinValue2);
                    return;
                } else {
                    if (RadarTimeSetActivity.this.timeSetBg2.isSelected()) {
                        RadarTimeSetActivity.this.cycleWheelView1.setSelection(RadarTimeSetActivity.this.currentHourValue4);
                        RadarTimeSetActivity.this.cycleWheelView2.setSelection(RadarTimeSetActivity.this.currentMinValue4);
                        return;
                    }
                    return;
                }
            }
            if (id == R.id.radarTimeSet_dateCancel) {
                RadarTimeSetActivity.this.hideTimeSelectView();
                RadarTimeSetActivity.this.getStoreTimeValue();
            } else if (id == R.id.radarTimeSet_dateSave) {
                RadarTimeSetActivity.this.hideTimeSelectView();
                RadarTimeSetActivity.this.refreshTimeSetValue();
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
        setContentView(R.layout.activity_radar_time_set);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        Bundle bundleExtra = intent.getBundleExtra(GlobalVariable.groupToCtrl);
        if (bundleExtra != null) {
            this.groupId = bundleExtra.getInt(GlobalVariable.groupId);
            this.tempGroupId = bundleExtra.getInt(GlobalVariable.tempGroupId);
        } else {
            this.addr = ((Bundle) Objects.requireNonNull(intent.getBundleExtra(GlobalVariable.toCtrl))).getInt(GlobalVariable.ctrlAddr);
        }
        initView();
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
        this.titleBgView = (ImageView) findViewById(R.id.radarTimeSet_title_bg);
        ImageView imageView = (ImageView) findViewById(R.id.radarTimeSet_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.radarTimeSet_save);
        this.saveTextView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.radarTimeSet_enableBg_onOff);
        this.enableOnOff = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.scrollView = (MyScrollView) findViewById(R.id.radarTimeSet_scrollview);
        ImageView imageView3 = (ImageView) findViewById(R.id.radarTimeSet_timeSetBg1);
        this.timeSetBg1 = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        this.timeSetValue1 = (TextView) findViewById(R.id.radarTimeSet_timeSetText1Value);
        ImageView imageView4 = (ImageView) findViewById(R.id.radarTimeSet_timeSetBg2);
        this.timeSetBg2 = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.timeSetValue2 = (TextView) findViewById(R.id.radarTimeSet_timeSetText2Value);
        ImageView imageView5 = (ImageView) findViewById(R.id.radarTimeSet_executionOnOff);
        this.executionOnOff = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        this.executionFixedTest = (TextView) findViewById(R.id.radarTimeSet_executionFixText);
        this.executionBottomLine = (ImageView) findViewById(R.id.radarTimeSet_executionBottomLine);
        this.brightnessText = (TextView) findViewById(R.id.radarTimeSet_execution_brightnessText);
        this.brightnessValue = (TextView) findViewById(R.id.radarTimeSet_execution_brightnessTextValue);
        SeekBar seekBar = (SeekBar) findViewById(R.id.radarTimeSet_execution_brightnessSeekBar);
        this.brightnessSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar.setMax(99);
        ImageView imageView6 = (ImageView) findViewById(R.id.radarTimeSet_execution_brightness_up);
        this.brightnessUpView = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView7 = (ImageView) findViewById(R.id.radarTimeSet_execution_brightness_down);
        this.brightnessDownView = imageView7;
        imageView7.setOnClickListener(this.disDoubleClickListener);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.radarTimeSet_dateBg);
        this.dateBgLayout = constraintLayout;
        constraintLayout.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView8 = (ImageView) findViewById(R.id.radarTimeSet_dateView);
        this.dateView = imageView8;
        imageView8.setOnClickListener(this.disDoubleClickListener);
        TextView textView2 = (TextView) findViewById(R.id.radarTimeSet_dateStartTime);
        this.startView = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        TextView textView3 = (TextView) findViewById(R.id.radarTimeSet_dateEndTime);
        this.endView = textView3;
        textView3.setOnClickListener(this.disDoubleClickListener);
        TextView textView4 = (TextView) findViewById(R.id.radarTimeSet_dateCancel);
        this.cancelView = textView4;
        textView4.setOnClickListener(this.disDoubleClickListener);
        TextView textView5 = (TextView) findViewById(R.id.radarTimeSet_dateSave);
        this.saveView = textView5;
        textView5.setOnClickListener(this.disDoubleClickListener);
        this.cycleWheelView1 = (CycleWheelView) findViewById(R.id.radarTimeSet_cycleWheelView1);
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
            this.cycleWheelView1.setWheelSize(3);
        } catch (CycleWheelView.CycleWheelViewException e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
        this.cycleWheelView1.setDivider(CustomColor.DIVIDER, Tool.sp2px(this, 0.5f));
        this.cycleWheelView1.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarTimeSetActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.CycleWheelView.WheelItemSelectedListener
            public final void onItemSelected(int i2, String str) {
                this.f$0.lambda$initView$0(i2, str);
            }
        });
        this.cycleWheelView2 = (CycleWheelView) findViewById(R.id.radarTimeSet_cycleWheelView2);
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
            this.cycleWheelView2.setWheelSize(3);
        } catch (CycleWheelView.CycleWheelViewException e2) {
            Log.d("printStackTrace", "printStackTrace" + e2);
        }
        this.cycleWheelView2.setDivider(CustomColor.DIVIDER, Tool.sp2px(this, 0.5f));
        this.cycleWheelView2.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarTimeSetActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.CycleWheelView.WheelItemSelectedListener
            public final void onItemSelected(int i3, String str) {
                this.f$0.lambda$initView$1(i3, str);
            }
        });
        handleData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, String str) {
        if (this.startView.isSelected()) {
            if (this.timeSetBg1.isSelected()) {
                this.currentHourValue1 = Integer.parseInt(str);
                return;
            } else {
                if (this.timeSetBg2.isSelected()) {
                    this.currentHourValue3 = Integer.parseInt(str);
                    return;
                }
                return;
            }
        }
        if (this.endView.isSelected()) {
            if (this.timeSetBg1.isSelected()) {
                this.currentHourValue2 = Integer.parseInt(str);
            } else if (this.timeSetBg2.isSelected()) {
                this.currentHourValue4 = Integer.parseInt(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, String str) {
        if (this.startView.isSelected()) {
            if (this.timeSetBg1.isSelected()) {
                this.currentMinValue1 = Integer.parseInt(str);
                return;
            } else {
                if (this.timeSetBg2.isSelected()) {
                    this.currentMinValue3 = Integer.parseInt(str);
                    return;
                }
                return;
            }
        }
        if (this.endView.isSelected()) {
            if (this.timeSetBg1.isSelected()) {
                this.currentMinValue2 = Integer.parseInt(str);
            } else if (this.timeSetBg2.isSelected()) {
                this.currentMinValue4 = Integer.parseInt(str);
            }
        }
    }

    private void handleData() {
        if (!((Boolean) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_ENABLE_STR, false)).booleanValue()) {
            this.scrollView.setVisibility(4);
        } else {
            this.scrollView.setVisibility(0);
            this.enableOnOff.setSelected(true);
        }
        this.currentBrightnessProgress = 100;
        if (((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_OUT_LIGHTNESS, 0)).intValue() != 0) {
            this.currentBrightnessProgress = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_OUT_LIGHTNESS, 0)).intValue();
            this.executionFixedTest.setText(getString(R.string.Fixlightness));
            this.executionOnOff.setSelected(true);
            this.executionBottomLine.setVisibility(8);
            this.brightnessText.setVisibility(0);
            this.brightnessValue.setVisibility(0);
            this.brightnessSeekBar.setVisibility(0);
            this.brightnessUpView.setVisibility(0);
            this.brightnessDownView.setVisibility(0);
        }
        this.brightnessSeekBar.setProgress(this.currentBrightnessProgress - 1);
        getStoreTimeValue();
        refreshTimeSetValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getStoreTimeValue() {
        this.currentHourValue1 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR1, 0)).intValue();
        this.currentMinValue1 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN1, 0)).intValue();
        this.currentHourValue2 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR2, 0)).intValue();
        this.currentMinValue2 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN2, 0)).intValue();
        this.currentHourValue3 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR3, 0)).intValue();
        this.currentMinValue3 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN3, 0)).intValue();
        this.currentHourValue4 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR4, 0)).intValue();
        this.currentMinValue4 = ((Integer) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN4, 0)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTimeSetValue() {
        String str = Tool.getTimeStr(this.currentHourValue1) + ":" + Tool.getTimeStr(this.currentMinValue1) + "-" + Tool.getTimeStr(this.currentHourValue2) + ":" + Tool.getTimeStr(this.currentMinValue2);
        this.timeSetValue1Str = str;
        this.timeSetValue1.setText(str);
        String str2 = Tool.getTimeStr(this.currentHourValue3) + ":" + Tool.getTimeStr(this.currentMinValue3) + "-" + Tool.getTimeStr(this.currentHourValue4) + ":" + Tool.getTimeStr(this.currentMinValue4);
        this.timeSetValue2Str = str2;
        this.timeSetValue2.setText(str2);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress + "%";
            this.brightnessStr = str;
            this.brightnessValue.setText(str);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress + "%";
            this.brightnessStr = str;
            this.brightnessValue.setText(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickStarTimeAction() {
        this.startView.setSelected(true);
        this.startView.setTextColor(getColor(R.color.colorText));
        this.endView.setSelected(false);
        this.endView.setTextColor(getColor(R.color.colorTextOffline));
        if (this.timeSetBg1.isSelected()) {
            this.cycleWheelView1.setSelection(this.currentHourValue1);
            this.cycleWheelView2.setSelection(this.currentMinValue1);
        } else if (this.timeSetBg2.isSelected()) {
            this.cycleWheelView1.setSelection(this.currentHourValue3);
            this.cycleWheelView2.setSelection(this.currentMinValue3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTimeSelectView() {
        this.dateBgLayout.setVisibility(0);
        clickStarTimeAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideTimeSelectView() {
        this.dateBgLayout.setVisibility(4);
        this.timeSetBg1.setSelected(false);
        this.timeSetBg2.setSelected(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveAction() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.RadarTimeSetActivity.2
            @Override // java.lang.Runnable
            public void run() {
                loadDialogUtils.closeDialog(RadarTimeSetActivity.this.loadingDialog);
                RadarTimeSetActivity.this.loadingDialog = null;
                RadarTimeSetActivity.this.finish();
            }
        }, 1000L);
        if (this.enableOnOff.isSelected()) {
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_ENABLE_STR, true);
            if (this.executionOnOff.isSelected()) {
                SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_OUT_LIGHTNESS, Integer.valueOf(this.currentBrightnessProgress));
            } else {
                SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_OUT_LIGHTNESS, 0);
            }
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR1, Integer.valueOf(this.currentHourValue1));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN1, Integer.valueOf(this.currentMinValue1));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR2, Integer.valueOf(this.currentHourValue2));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN2, Integer.valueOf(this.currentMinValue2));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR3, Integer.valueOf(this.currentHourValue3));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN3, Integer.valueOf(this.currentMinValue3));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR4, Integer.valueOf(this.currentHourValue4));
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN4, Integer.valueOf(this.currentMinValue4));
            this.out_lightness = 0;
            if (this.executionOnOff.isSelected()) {
                this.out_lightness = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
            }
        } else {
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_ENABLE_STR, false);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_OUT_LIGHTNESS, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR1, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN1, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR2, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN2, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR3, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN3, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_HOUR4, 0);
            SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_TIME_MIN4, 0);
            this.out_lightness = 0;
        }
        this.enable = 1;
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

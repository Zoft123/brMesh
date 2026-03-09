package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralClass.CycleWheelView;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SleepModActivity extends FatherActivity implements SeekBar.OnSeekBarChangeListener, ColorObserver, ColorWheelView.OnSetColorListener {
    ImageView backView;
    ImageView brightnessDownView1;
    ImageView brightnessDownView2;
    private SeekBar brightnessSeekBar1;
    private SeekBar brightnessSeekBar2;
    private String brightnessStr1;
    private String brightnessStr2;
    TextView brightnessText1;
    ImageView brightnessUpView1;
    ImageView brightnessUpView2;
    private TextView brightnessValue1;
    private TextView brightnessValue2;
    public int currentBrightnessProgress1;
    public int currentBrightnessProgress2;
    private int currentMinute;
    private Mod currentMod;
    CycleWheelView cycleWheelView;
    private ImageView endBottomLine;
    private TextView endFixedText;
    public ImageView endOnOff;
    private boolean isSleep;
    private Dialog loadingDialog;
    private Realm realm;
    TextView saveTextView;
    private String timeStr;
    private TextView timeView;
    ImageView titleBgView;
    TextView titleView;
    int addr = -1;
    public int sceneNumber = -1;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.SleepModActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.ctrl_back) {
                SleepModActivity.this.finish();
                return;
            }
            if (id == R.id.ctrl_save) {
                SleepModActivity.this.saveRadarAction();
                return;
            }
            if (id == R.id.sleepMod_endOnOff) {
                if (SleepModActivity.this.endOnOff.isSelected()) {
                    SleepModActivity.this.endOnOff.setSelected(false);
                    SleepModActivity.this.endFixedText.setText(SleepModActivity.this.getString(R.string.OffLight));
                    SleepModActivity.this.endBottomLine.setVisibility(0);
                    SleepModActivity.this.brightnessValue2.setVisibility(4);
                    SleepModActivity.this.brightnessSeekBar2.setVisibility(8);
                    SleepModActivity.this.brightnessUpView2.setVisibility(8);
                    SleepModActivity.this.brightnessDownView2.setVisibility(8);
                    return;
                }
                SleepModActivity.this.endOnOff.setSelected(true);
                SleepModActivity.this.endFixedText.setText(SleepModActivity.this.getString(R.string.Fixlightness));
                SleepModActivity.this.endBottomLine.setVisibility(8);
                SleepModActivity.this.brightnessValue2.setVisibility(0);
                SleepModActivity.this.brightnessSeekBar2.setVisibility(0);
                SleepModActivity.this.brightnessUpView2.setVisibility(0);
                SleepModActivity.this.brightnessDownView2.setVisibility(0);
                if (SleepModActivity.this.currentBrightnessProgress2 == 0) {
                    SleepModActivity.this.currentBrightnessProgress2 = 1;
                }
                SleepModActivity.this.brightnessSeekBar2.setProgress(SleepModActivity.this.currentBrightnessProgress2 - 1);
                return;
            }
            if (id == R.id.sleepMod_start_brightness_up) {
                if (SleepModActivity.this.currentBrightnessProgress1 < 100) {
                    SleepModActivity.this.currentBrightnessProgress1++;
                }
                SleepModActivity.this.brightnessSeekBar1.setProgress(SleepModActivity.this.currentBrightnessProgress1 - 1);
                return;
            }
            if (id == R.id.sleepMod_start_brightness_down) {
                if (SleepModActivity.this.currentBrightnessProgress1 > 1) {
                    SleepModActivity.this.currentBrightnessProgress1--;
                }
                SleepModActivity.this.brightnessSeekBar1.setProgress(SleepModActivity.this.currentBrightnessProgress1 - 1);
                return;
            }
            if (id == R.id.sleepMod_end_brightness_up) {
                if (SleepModActivity.this.currentBrightnessProgress2 < 100) {
                    SleepModActivity.this.currentBrightnessProgress2++;
                }
                SleepModActivity.this.brightnessSeekBar2.setProgress(SleepModActivity.this.currentBrightnessProgress2 - 1);
                return;
            }
            if (id == R.id.sleepMod_end_brightness_down) {
                if (SleepModActivity.this.currentBrightnessProgress2 > 1) {
                    SleepModActivity.this.currentBrightnessProgress2--;
                }
                SleepModActivity.this.brightnessSeekBar2.setProgress(SleepModActivity.this.currentBrightnessProgress2 - 1);
            }
        }
    };
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView.OnSetColorListener
    public void OnSetColor(int i) {
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver
    public void onColor(int i, boolean z, boolean z2) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sleep_mod);
        initView();
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
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        Bundle bundleExtra = getIntent().getBundleExtra(GlobalVariable.toModSetCtrl);
        if (bundleExtra != null) {
            this.addr = bundleExtra.getInt(GlobalVariable.addr);
            int i = bundleExtra.getInt(GlobalVariable.modNumber);
            this.sceneNumber = bundleExtra.getInt(GlobalVariable.sceneNumber);
            this.isSleep = i == 30;
            this.currentMod = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("isSleep", Boolean.valueOf(this.isSleep)).findFirst();
        }
        this.titleBgView = (ImageView) findViewById(R.id.ctrl_title_bg);
        ImageView imageView = (ImageView) findViewById(R.id.ctrl_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.ctrl_save);
        this.saveTextView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        TextView textView2 = (TextView) findViewById(R.id.ctrl_title);
        this.titleView = textView2;
        if (this.isSleep) {
            textView2.setText(R.string.SleepMode);
        } else {
            textView2.setText(R.string.WakeUpMode);
        }
        this.brightnessText1 = (TextView) findViewById(R.id.sleepMod_start_brightnessText);
        this.brightnessValue1 = (TextView) findViewById(R.id.sleepMod_start_brightnessTextValue);
        SeekBar seekBar = (SeekBar) findViewById(R.id.sleepMod_start_brightnessSeekBar);
        this.brightnessSeekBar1 = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar1.setMax(99);
        ImageView imageView2 = (ImageView) findViewById(R.id.sleepMod_start_brightness_up);
        this.brightnessUpView1 = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView3 = (ImageView) findViewById(R.id.sleepMod_start_brightness_down);
        this.brightnessDownView1 = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        this.brightnessValue2 = (TextView) findViewById(R.id.sleepMod_end_brightnessTextValue);
        this.endFixedText = (TextView) findViewById(R.id.sleepMod_endFixText);
        ImageView imageView4 = (ImageView) findViewById(R.id.sleepMod_endOnOff);
        this.endOnOff = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.endBottomLine = (ImageView) findViewById(R.id.sleepMod_endBottomLine);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.sleepMod_end_brightnessSeekBar);
        this.brightnessSeekBar2 = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar2.setMax(99);
        ImageView imageView5 = (ImageView) findViewById(R.id.sleepMod_end_brightness_up);
        this.brightnessUpView2 = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView6 = (ImageView) findViewById(R.id.sleepMod_end_brightness_down);
        this.brightnessDownView2 = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
        if (!this.isSleep) {
            this.endFixedText.setVisibility(8);
            this.endOnOff.setVisibility(8);
            this.endBottomLine.setVisibility(8);
        }
        this.timeView = (TextView) findViewById(R.id.sleepMod_timeText);
        this.cycleWheelView = (CycleWheelView) findViewById(R.id.sleepMod_cycleWheelView);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 1; i2 <= 100; i2++) {
            if (i2 < 10) {
                arrayList.add(GlobalVariable.ILLUMINATION + i2);
            } else {
                arrayList.add(String.valueOf(i2));
            }
        }
        this.cycleWheelView.setLabels(arrayList);
        this.cycleWheelView.setCycleEnable(false);
        try {
            this.cycleWheelView.setWheelSize(3);
        } catch (CycleWheelView.CycleWheelViewException e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
        this.cycleWheelView.setDivider(CustomColor.DIVIDER, Tool.sp2px(this, 0.5f));
        this.cycleWheelView.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() { // from class: com.brgd.brblmesh.Main.Activity.SleepModActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.CycleWheelView.WheelItemSelectedListener
            public final void onItemSelected(int i3, String str) {
                this.f$0.lambda$initView$0(i3, str);
            }
        });
        handleData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, String str) {
        this.currentMinute = Integer.parseInt(str);
        String str2 = getString(R.string.sleepTime) + "  " + this.currentMinute + " " + getString(R.string.minute);
        this.timeStr = str2;
        this.timeView.setText(str2);
    }

    private void handleData() {
        int startBrightness = this.currentMod.getStartBrightness();
        this.currentBrightnessProgress1 = startBrightness;
        this.brightnessSeekBar1.setProgress(startBrightness - 1);
        int endBrightness = this.currentMod.getEndBrightness();
        this.currentBrightnessProgress2 = endBrightness;
        if (endBrightness != 0) {
            this.endFixedText.setText(getString(R.string.Fixlightness));
            this.endOnOff.setSelected(true);
            this.endBottomLine.setVisibility(8);
            this.brightnessValue2.setVisibility(0);
            this.brightnessSeekBar2.setVisibility(0);
            this.brightnessUpView2.setVisibility(0);
            this.brightnessDownView2.setVisibility(0);
            this.brightnessSeekBar2.setProgress(this.currentBrightnessProgress2 - 1);
        }
        this.currentMinute = this.currentMod.getMinute();
        String str = getString(R.string.sleepTime) + "  " + this.currentMinute + " " + getString(R.string.minute);
        this.timeStr = str;
        this.timeView.setText(str);
        this.cycleWheelView.setSelection(this.currentMinute - 1);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress1 = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress1 + "%";
            this.brightnessStr1 = str;
            this.brightnessValue1.setText(str);
            return;
        }
        if (seekBar == this.brightnessSeekBar2) {
            this.currentBrightnessProgress2 = seekBar.getProgress() + 1;
            String str2 = this.currentBrightnessProgress2 + "%";
            this.brightnessStr2 = str2;
            this.brightnessValue2.setText(str2);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress1 = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress1 + "%";
            this.brightnessStr1 = str;
            this.brightnessValue1.setText(str);
            return;
        }
        if (seekBar == this.brightnessSeekBar2) {
            this.currentBrightnessProgress2 = seekBar.getProgress() + 1;
            String str2 = this.currentBrightnessProgress2 + "%";
            this.brightnessStr2 = str2;
            this.brightnessValue2.setText(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveRadarAction() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.SleepModActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveRadarAction$1();
            }
        }, 2000L);
        if (!this.endOnOff.isSelected() && this.isSleep) {
            this.currentBrightnessProgress2 = 0;
        }
        this.realm.beginTransaction();
        this.currentMod.setStartBrightness(this.currentBrightnessProgress1);
        this.currentMod.setEndBrightness(this.currentBrightnessProgress2);
        this.currentMod.setMinute(this.currentMinute);
        this.realm.commitTransaction();
        GlobalBluetooth.getInstance().controlDelaySceneWithDevice(this.addr, this.currentBrightnessProgress1, this.currentBrightnessProgress2, this.currentMinute);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveRadarAction$1() {
        loadDialogUtils.closeDialog(this.loadingDialog);
        this.loadingDialog = null;
        finish();
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

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        finish();
        return false;
    }
}

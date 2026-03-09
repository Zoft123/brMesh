package com.brgd.brblmesh.Main.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView;
import com.brgd.brblmesh.Main.Interface.ColourFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;

/* JADX INFO: loaded from: classes.dex */
public class TimerSetColorActivity extends FatherActivity implements SeekBar.OnSeekBarChangeListener, ColorObserver, ColourFragmentListener, View.OnClickListener {
    ImageView backView;
    ImageView blueDownView;
    private SeekBar blueSeekBar;
    private String blueStr;
    ImageView blueUpView;
    private TextView blueValue;
    ImageView brightnessDownView;
    ImageView brightnessDownView1;
    private SeekBar brightnessSeekBar;
    private SeekBar brightnessSeekBar1;
    private String brightnessStr;
    ImageView brightnessUpView;
    ImageView brightnessUpView1;
    private TextView brightnessValue;
    private TextView brightnessValue1;
    private String coldStr;
    private TextView coldText;
    private TextView coldText1;
    private TextView coldValue;
    private TextView coldValue1;
    public ConstraintLayout colorPickerLayout;
    public ConstraintLayout colorSeekBarLayout;
    private ConstraintLayout colorTempLayout;
    private ColorWheelView colorWheelView;
    private int currentBlueProgress;
    private int currentBrightnessProgress;
    private int currentColdParam;
    private int currentColor;
    private int currentGreenProgress;
    private int currentLightnessParam;
    private int currentRedProgress;
    private int currentTempProgress;
    private Timer currentTimer;
    private int currentWarmParam;
    private ImageView editView;
    ImageView greenDownView;
    private SeekBar greenSeekBar;
    private String greenStr;
    ImageView greenUpView;
    private TextView greenValue;
    ImageView leftBtView;
    private Realm realm;
    ImageView redDownView;
    private SeekBar redSeekBar;
    private String redStr;
    ImageView redUpView;
    private TextView redValue;
    ImageView rightBtView;
    private ConstraintLayout seekBarLayout;
    ImageView tempDownView;
    ImageView tempDownView1;
    private SeekBar tempSeekBar;
    private SeekBar tempSeekBar1;
    ImageView tempUpView;
    ImageView tempUpView1;
    private String warmStr;
    private TextView warmText;
    private TextView warmText1;
    private TextView warmValue;
    private TextView warmValue1;
    public int addr = -1;
    public int type = -1;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.TimerSetColorActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.timerSetColor_back) {
                TimerSetColorActivity.this.finish();
                return;
            }
            if (id == R.id.timerSetColor_edit) {
                if (TimerSetColorActivity.this.editView.isSelected()) {
                    TimerSetColorActivity.this.editView.setSelected(false);
                    TimerSetColorActivity.this.colorPickerLayout.setVisibility(0);
                    TimerSetColorActivity.this.colorSeekBarLayout.setVisibility(4);
                    return;
                } else {
                    TimerSetColorActivity.this.editView.setSelected(true);
                    TimerSetColorActivity.this.colorPickerLayout.setVisibility(4);
                    TimerSetColorActivity.this.colorSeekBarLayout.setVisibility(0);
                    TimerSetColorActivity.this.upDateRgbValue();
                    return;
                }
            }
            if (id == R.id.timerSetColor_toRightBt) {
                if (TimerSetColorActivity.this.currentColor != -1) {
                    TimerSetColorActivity.this.colorWheelView.moveSelector(false);
                    return;
                }
                return;
            }
            if (id == R.id.timerSetColor_toLeftBt) {
                if (TimerSetColorActivity.this.currentColor != -1) {
                    TimerSetColorActivity.this.colorWheelView.moveSelector(true);
                    return;
                }
                return;
            }
            if (id == R.id.timerSetColor_temp_up) {
                if (TimerSetColorActivity.this.currentTempProgress > 0) {
                    TimerSetColorActivity.this.currentTempProgress--;
                }
                TimerSetColorActivity.this.tempSeekBar.setProgress(100 - TimerSetColorActivity.this.currentTempProgress);
                TimerSetColorActivity.this.setColorTemp();
                return;
            }
            if (id == R.id.timerSetColor_temp_up1) {
                if (TimerSetColorActivity.this.currentTempProgress > 0) {
                    TimerSetColorActivity.this.currentTempProgress--;
                }
                TimerSetColorActivity.this.tempSeekBar1.setProgress(100 - TimerSetColorActivity.this.currentTempProgress);
                TimerSetColorActivity.this.setColorTemp();
                return;
            }
            if (id == R.id.timerSetColor_temp_down) {
                if (TimerSetColorActivity.this.currentTempProgress < 100) {
                    TimerSetColorActivity.this.currentTempProgress++;
                }
                TimerSetColorActivity.this.tempSeekBar.setProgress(100 - TimerSetColorActivity.this.currentTempProgress);
                TimerSetColorActivity.this.setColorTemp();
                return;
            }
            if (id == R.id.timerSetColor_temp_down1) {
                if (TimerSetColorActivity.this.currentTempProgress < 100) {
                    TimerSetColorActivity.this.currentTempProgress++;
                }
                TimerSetColorActivity.this.tempSeekBar1.setProgress(100 - TimerSetColorActivity.this.currentTempProgress);
                TimerSetColorActivity.this.setColorTemp();
                return;
            }
            if (id == R.id.timerSetColor_brightness_up) {
                if (TimerSetColorActivity.this.currentBrightnessProgress < 100) {
                    TimerSetColorActivity.this.currentBrightnessProgress++;
                }
                TimerSetColorActivity.this.brightnessSeekBar.setProgress(TimerSetColorActivity.this.currentBrightnessProgress - 1);
                TimerSetColorActivity.this.setLightness();
                return;
            }
            if (id == R.id.timerSetColor_brightness_up1) {
                if (TimerSetColorActivity.this.currentBrightnessProgress < 100) {
                    TimerSetColorActivity.this.currentBrightnessProgress++;
                }
                TimerSetColorActivity.this.brightnessSeekBar1.setProgress(TimerSetColorActivity.this.currentBrightnessProgress - 1);
                TimerSetColorActivity.this.setLightness();
                return;
            }
            if (id == R.id.timerSetColor_brightness_down) {
                if (TimerSetColorActivity.this.currentBrightnessProgress > 1) {
                    TimerSetColorActivity.this.currentBrightnessProgress--;
                }
                TimerSetColorActivity.this.brightnessSeekBar.setProgress(TimerSetColorActivity.this.currentBrightnessProgress - 1);
                TimerSetColorActivity.this.setLightness();
                return;
            }
            if (id == R.id.timerSetColor_brightness_down1) {
                if (TimerSetColorActivity.this.currentBrightnessProgress > 1) {
                    TimerSetColorActivity.this.currentBrightnessProgress--;
                }
                TimerSetColorActivity.this.brightnessSeekBar1.setProgress(TimerSetColorActivity.this.currentBrightnessProgress - 1);
                TimerSetColorActivity.this.setLightness();
            }
        }
    };

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_timer_set_color);
        initView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.colorWheelView.unsubscribe(this);
        this.realm.close();
    }

    private void initView() {
        ImageView imageView = (ImageView) findViewById(R.id.timerSetColor_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.timerSetColor_edit);
        this.editView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        Bundle bundleExtra = getIntent().getBundleExtra(GlobalVariable.checkTimer);
        if (bundleExtra != null) {
            this.addr = bundleExtra.getInt(GlobalVariable.addr);
            this.currentTimer = (Timer) this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("indexNum", Integer.valueOf(bundleExtra.getInt("indexNum"))).findFirst();
        }
        BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
        if (bleDevice != null) {
            this.type = bleDevice.getType();
        }
        this.colorPickerLayout = (ConstraintLayout) findViewById(R.id.timerSetColor_colorPickerBg);
        ColorWheelView colorWheelView = (ColorWheelView) findViewById(R.id.timerSetColor_colorPicker);
        this.colorWheelView = colorWheelView;
        colorWheelView.subscribe(this);
        this.colorWheelView.setColor(-65536, false);
        this.currentColor = -65536;
        this.colorSeekBarLayout = (ConstraintLayout) findViewById(R.id.timerSetColor_colorSeekBarBg);
        SeekBar seekBar = (SeekBar) findViewById(R.id.timerSetColor_RedSeekBar);
        this.redSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.redSeekBar.setMax(255);
        this.currentRedProgress = 0;
        this.redSeekBar.setProgress(0);
        this.redValue = (TextView) findViewById(R.id.timerSetColor_RedTextValue);
        ImageView imageView3 = (ImageView) findViewById(R.id.timerSetColor_Red_up);
        this.redUpView = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.timerSetColor_Red_down);
        this.redDownView = imageView4;
        imageView4.setOnClickListener(this);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.timerSetColor_GreenSeekBar);
        this.greenSeekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.greenSeekBar.setMax(255);
        this.currentGreenProgress = 0;
        this.greenSeekBar.setProgress(0);
        this.greenValue = (TextView) findViewById(R.id.timerSetColor_GreenTextValue);
        ImageView imageView5 = (ImageView) findViewById(R.id.timerSetColor_Green_up);
        this.greenUpView = imageView5;
        imageView5.setOnClickListener(this);
        ImageView imageView6 = (ImageView) findViewById(R.id.timerSetColor_Green_down);
        this.greenDownView = imageView6;
        imageView6.setOnClickListener(this);
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.timerSetColor_BlueSeekBar);
        this.blueSeekBar = seekBar3;
        seekBar3.setOnSeekBarChangeListener(this);
        this.blueSeekBar.setMax(255);
        this.currentBlueProgress = 0;
        this.blueSeekBar.setProgress(0);
        this.blueValue = (TextView) findViewById(R.id.timerSetColor_BlueTextValue);
        ImageView imageView7 = (ImageView) findViewById(R.id.timerSetColor_Blue_up);
        this.blueUpView = imageView7;
        imageView7.setOnClickListener(this);
        ImageView imageView8 = (ImageView) findViewById(R.id.timerSetColor_Blue_down);
        this.blueDownView = imageView8;
        imageView8.setOnClickListener(this);
        ImageView imageView9 = (ImageView) findViewById(R.id.timerSetColor_toRightBt);
        this.leftBtView = imageView9;
        imageView9.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView10 = (ImageView) findViewById(R.id.timerSetColor_toLeftBt);
        this.rightBtView = imageView10;
        imageView10.setOnClickListener(this.disDoubleClickListener);
        this.seekBarLayout = (ConstraintLayout) findViewById(R.id.timerSetColor_seekBarLayout);
        this.warmText = (TextView) findViewById(R.id.timerSetColor_warmText);
        this.warmValue = (TextView) findViewById(R.id.timerSetColor_warmTextValue);
        this.coldText = (TextView) findViewById(R.id.timerSetColor_coldText);
        this.coldValue = (TextView) findViewById(R.id.timerSetColor_coldTextValue);
        SeekBar seekBar4 = (SeekBar) findViewById(R.id.timerSetColor_tempSeekBar);
        this.tempSeekBar = seekBar4;
        seekBar4.setOnSeekBarChangeListener(this);
        this.tempSeekBar.setMax(100);
        this.currentTempProgress = 50;
        this.tempSeekBar.setProgress(50);
        ImageView imageView11 = (ImageView) findViewById(R.id.timerSetColor_temp_up);
        this.tempUpView = imageView11;
        imageView11.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView12 = (ImageView) findViewById(R.id.timerSetColor_temp_down);
        this.tempDownView = imageView12;
        imageView12.setOnClickListener(this.disDoubleClickListener);
        this.tempSeekBar.setVisibility(0);
        this.warmText.setVisibility(0);
        this.warmValue.setVisibility(0);
        this.coldText.setVisibility(0);
        this.coldValue.setVisibility(0);
        this.tempUpView.setVisibility(0);
        this.tempDownView.setVisibility(0);
        this.brightnessValue = (TextView) findViewById(R.id.timerSetColor_brightnessTextValue);
        SeekBar seekBar5 = (SeekBar) findViewById(R.id.timerSetColor_brightnessSeekBar);
        this.brightnessSeekBar = seekBar5;
        seekBar5.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar.setMax(99);
        this.currentBrightnessProgress = 100;
        this.brightnessSeekBar.setProgress(100 - 1);
        ImageView imageView13 = (ImageView) findViewById(R.id.timerSetColor_brightness_up);
        this.brightnessUpView = imageView13;
        imageView13.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView14 = (ImageView) findViewById(R.id.timerSetColor_brightness_down);
        this.brightnessDownView = imageView14;
        imageView14.setOnClickListener(this.disDoubleClickListener);
        this.colorTempLayout = (ConstraintLayout) findViewById(R.id.timerSetColor_tempSeekBarLayout);
        this.warmText1 = (TextView) findViewById(R.id.timerSetColor_warmText1);
        this.warmValue1 = (TextView) findViewById(R.id.timerSetColor_warmTextValue1);
        this.coldText1 = (TextView) findViewById(R.id.timerSetColor_coldText1);
        this.coldValue1 = (TextView) findViewById(R.id.timerSetColor_coldTextValue1);
        SeekBar seekBar6 = (SeekBar) findViewById(R.id.timerSetColor_tempSeekBar1);
        this.tempSeekBar1 = seekBar6;
        seekBar6.setOnSeekBarChangeListener(this);
        this.tempSeekBar1.setMax(100);
        this.tempSeekBar1.setProgress(this.currentTempProgress);
        ImageView imageView15 = (ImageView) findViewById(R.id.timerSetColor_temp_up1);
        this.tempUpView1 = imageView15;
        imageView15.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView16 = (ImageView) findViewById(R.id.timerSetColor_temp_down1);
        this.tempDownView1 = imageView16;
        imageView16.setOnClickListener(this.disDoubleClickListener);
        this.brightnessValue1 = (TextView) findViewById(R.id.timerSetColor_brightnessTextValue1);
        SeekBar seekBar7 = (SeekBar) findViewById(R.id.timerSetColor_brightnessSeekBar1);
        this.brightnessSeekBar1 = seekBar7;
        seekBar7.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar1.setMax(99);
        this.brightnessSeekBar1.setProgress(this.currentBrightnessProgress - 1);
        ImageView imageView17 = (ImageView) findViewById(R.id.timerSetColor_brightness_up1);
        this.brightnessUpView1 = imageView17;
        imageView17.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView18 = (ImageView) findViewById(R.id.timerSetColor_brightness_down1);
        this.brightnessDownView1 = imageView18;
        imageView18.setOnClickListener(this.disDoubleClickListener);
        if (SharePreferenceUtils.contains(this, GlobalVariable.tempColorTempValue)) {
            this.currentTempProgress = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorTempValue, -1)).intValue();
        } else if (this.currentTimer.getWarm() != 0 || this.currentTimer.getCold() != 0) {
            this.currentTempProgress = Math.round((this.currentTimer.getWarm() / 255.0f) * 100.0f);
        }
        this.tempSeekBar.setProgress(100 - this.currentTempProgress);
        this.tempSeekBar1.setProgress(100 - this.currentTempProgress);
        int i = (int) ((this.currentTempProgress / 100.0f) * 255.0f);
        this.currentWarmParam = i;
        this.currentColdParam = 255 - i;
        if (SharePreferenceUtils.contains(this, GlobalVariable.tempLightnessValue)) {
            this.currentBrightnessProgress = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempLightnessValue, -1)).intValue();
        } else if (this.currentTimer.getBrightness() != 0) {
            this.currentBrightnessProgress = this.currentTimer.getBrightness();
        }
        this.brightnessSeekBar.setProgress(this.currentBrightnessProgress - 1);
        this.brightnessSeekBar1.setProgress(this.currentBrightnessProgress - 1);
        this.currentLightnessParam = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
        if (SharePreferenceUtils.contains(this, GlobalVariable.tempColorValue)) {
            this.currentColor = ((Integer) SharePreferenceUtils.get(this, GlobalVariable.tempColorValueR, -1)).intValue();
        } else if (this.currentTimer.getRed() != 0 || this.currentTimer.getGreen() != 0 || this.currentTimer.getBlue() != 0) {
            this.currentRedProgress = this.currentTimer.getRed();
            this.currentGreenProgress = this.currentTimer.getGreen();
            int blue = this.currentTimer.getBlue();
            this.currentBlueProgress = blue;
            this.currentColor = Color.rgb(this.currentRedProgress, this.currentGreenProgress, blue);
        }
        this.colorWheelView.setColor(this.currentColor, false);
        handleViewVisibility();
    }

    private void handleViewVisibility() {
        this.editView.setVisibility(0);
        int i = this.type;
        if (i == 43169) {
            this.tempSeekBar.setVisibility(8);
            this.warmValue.setVisibility(8);
            this.warmText.setVisibility(8);
            this.coldText.setVisibility(8);
            this.coldValue.setVisibility(8);
            this.tempUpView.setVisibility(8);
            this.tempDownView.setVisibility(8);
            this.seekBarLayout.setScrollbarFadingEnabled(true);
            return;
        }
        if (i == 43168) {
            this.tempSeekBar.setVisibility(8);
            this.warmValue.setVisibility(8);
            this.warmText.setVisibility(8);
            this.coldText.setVisibility(8);
            this.coldValue.setVisibility(8);
            this.tempUpView.setVisibility(8);
            this.tempDownView.setVisibility(8);
            return;
        }
        if (i == 43051) {
            this.colorPickerLayout.setVisibility(8);
            this.colorSeekBarLayout.setVisibility(8);
            this.seekBarLayout.setVisibility(8);
            this.colorTempLayout.setVisibility(0);
            this.colorTempLayout.setScrollbarFadingEnabled(true);
            this.editView.setVisibility(4);
            return;
        }
        if (i == 43049) {
            this.colorPickerLayout.setVisibility(8);
            this.colorSeekBarLayout.setVisibility(8);
            this.seekBarLayout.setVisibility(8);
            this.colorTempLayout.setVisibility(0);
            this.colorTempLayout.setScrollbarFadingEnabled(true);
            this.warmText1.setVisibility(4);
            this.warmValue1.setVisibility(4);
            this.coldText1.setVisibility(4);
            this.coldValue1.setVisibility(4);
            this.tempUpView1.setVisibility(4);
            this.tempDownView1.setVisibility(4);
            this.tempSeekBar1.setVisibility(4);
            this.editView.setVisibility(4);
        }
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
        if (seekBar == this.tempSeekBar) {
            int progress = 100 - seekBar.getProgress();
            this.currentTempProgress = progress;
            String strValueOf = String.valueOf(progress);
            this.warmStr = strValueOf;
            this.warmValue.setText(strValueOf);
            String strValueOf2 = String.valueOf(100 - this.currentTempProgress);
            this.coldStr = strValueOf2;
            this.coldValue.setText(strValueOf2);
            return;
        }
        if (seekBar == this.tempSeekBar1) {
            int progress2 = 100 - seekBar.getProgress();
            this.currentTempProgress = progress2;
            String strValueOf3 = String.valueOf(progress2);
            this.warmStr = strValueOf3;
            this.warmValue1.setText(strValueOf3);
            String strValueOf4 = String.valueOf(100 - this.currentTempProgress);
            this.coldStr = strValueOf4;
            this.coldValue1.setText(strValueOf4);
            return;
        }
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress + "%";
            this.brightnessStr = str;
            this.brightnessValue.setText(str);
            return;
        }
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str2 = this.currentBrightnessProgress + "%";
            this.brightnessStr = str2;
            this.brightnessValue1.setText(str2);
            return;
        }
        if (seekBar == this.redSeekBar) {
            int progress3 = seekBar.getProgress();
            this.currentRedProgress = progress3;
            if (this.currentGreenProgress == 0 && this.currentBlueProgress == 0 && progress3 == 0) {
                int i2 = progress3 + 1;
                this.currentRedProgress = i2;
                this.redSeekBar.setProgress(i2);
            }
            String strValueOf5 = String.valueOf(this.currentRedProgress);
            this.redStr = strValueOf5;
            TextView textView = this.redValue;
            if (textView != null) {
                textView.setText(strValueOf5);
                return;
            }
            return;
        }
        if (seekBar == this.greenSeekBar) {
            int progress4 = seekBar.getProgress();
            this.currentGreenProgress = progress4;
            if (this.currentRedProgress == 0 && this.currentBlueProgress == 0 && progress4 == 0) {
                int i3 = progress4 + 1;
                this.currentGreenProgress = i3;
                this.greenSeekBar.setProgress(i3);
            }
            String strValueOf6 = String.valueOf(this.currentGreenProgress);
            this.greenStr = strValueOf6;
            TextView textView2 = this.greenValue;
            if (textView2 != null) {
                textView2.setText(strValueOf6);
                return;
            }
            return;
        }
        if (seekBar == this.blueSeekBar) {
            int progress5 = seekBar.getProgress();
            this.currentBlueProgress = progress5;
            if (this.currentRedProgress == 0 && this.currentGreenProgress == 0 && progress5 == 0) {
                int i4 = progress5 + 1;
                this.currentBlueProgress = i4;
                this.blueSeekBar.setProgress(i4);
            }
            String strValueOf7 = String.valueOf(this.currentBlueProgress);
            this.blueStr = strValueOf7;
            TextView textView3 = this.blueValue;
            if (textView3 != null) {
                textView3.setText(strValueOf7);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == this.tempSeekBar) {
            int progress = 100 - seekBar.getProgress();
            this.currentTempProgress = progress;
            String strValueOf = String.valueOf(progress);
            this.warmStr = strValueOf;
            this.warmValue.setText(strValueOf);
            String strValueOf2 = String.valueOf(100 - this.currentTempProgress);
            this.coldStr = strValueOf2;
            this.coldValue.setText(strValueOf2);
            setColorTemp();
            return;
        }
        if (seekBar == this.tempSeekBar1) {
            int progress2 = 100 - seekBar.getProgress();
            this.currentTempProgress = progress2;
            String strValueOf3 = String.valueOf(progress2);
            this.warmStr = strValueOf3;
            this.warmValue1.setText(strValueOf3);
            String strValueOf4 = String.valueOf(100 - this.currentTempProgress);
            this.coldStr = strValueOf4;
            this.coldValue1.setText(strValueOf4);
            setColorTemp();
            return;
        }
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress + "%";
            this.brightnessStr = str;
            this.brightnessValue.setText(str);
            setLightness();
            return;
        }
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str2 = this.currentBrightnessProgress + "%";
            this.brightnessStr = str2;
            this.brightnessValue1.setText(str2);
            setLightness();
            return;
        }
        if (seekBar == this.redSeekBar) {
            int progress3 = seekBar.getProgress();
            this.currentRedProgress = progress3;
            String strValueOf5 = String.valueOf(progress3);
            this.redStr = strValueOf5;
            this.redValue.setText(strValueOf5);
            setRgbColor();
            return;
        }
        if (seekBar == this.greenSeekBar) {
            int progress4 = seekBar.getProgress();
            this.currentGreenProgress = progress4;
            String strValueOf6 = String.valueOf(progress4);
            this.greenStr = strValueOf6;
            this.greenValue.setText(strValueOf6);
            setRgbColor();
            return;
        }
        if (seekBar == this.blueSeekBar) {
            int progress5 = seekBar.getProgress();
            this.currentBlueProgress = progress5;
            String strValueOf7 = String.valueOf(progress5);
            this.blueStr = strValueOf7;
            this.blueValue.setText(strValueOf7);
            setRgbColor();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int i;
        int i2;
        int i3;
        int id = view.getId();
        if (id == R.id.timerSetColor_Red_up) {
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
        if (id == R.id.timerSetColor_Red_down) {
            if (!(this.currentGreenProgress == 0 && this.currentBlueProgress == 0 && this.currentRedProgress == 1) && (i3 = this.currentRedProgress) > 0) {
                int i6 = i3 - 1;
                this.currentRedProgress = i6;
                this.redSeekBar.setProgress(i6);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.timerSetColor_Green_up) {
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
        if (id == R.id.timerSetColor_Green_down) {
            if (!(this.currentRedProgress == 0 && this.currentBlueProgress == 0 && this.currentGreenProgress == 1) && (i2 = this.currentGreenProgress) > 0) {
                int i9 = i2 - 1;
                this.currentGreenProgress = i9;
                this.greenSeekBar.setProgress(i9);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.timerSetColor_Blue_up) {
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
        if (id == R.id.timerSetColor_Blue_down) {
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

    @Override // com.brgd.brblmesh.Main.Interface.ColourFragmentListener
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
        GlobalBluetooth.getInstance().singleRgbCtrl(this.addr, this.currentLightnessParam, iRed, iGreen, iBlue);
        SharePreferenceUtils.put(this, GlobalVariable.tempColorValue, Integer.valueOf(this.colorWheelView.getSelectorColor(this.currentColor)));
        SharePreferenceUtils.put(this, GlobalVariable.tempColorValueR, Integer.valueOf(this.currentColor));
        SharePreferenceUtils.remove(this, GlobalVariable.tempColorTempValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLightness() {
        this.currentLightnessParam = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
        GlobalBluetooth.getInstance().singleLightness(this.addr, this.currentLightnessParam);
        SharePreferenceUtils.put(this, GlobalVariable.tempLightnessValue, Integer.valueOf(this.currentBrightnessProgress));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColorTemp() {
        int i = (int) ((this.currentTempProgress / 100.0f) * 255.0f);
        this.currentWarmParam = i;
        this.currentColdParam = 255 - i;
        this.currentLightnessParam = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
        GlobalBluetooth.getInstance().singleTempCtrl(this.addr, this.currentLightnessParam, this.currentWarmParam, this.currentColdParam);
        SharePreferenceUtils.put(this, GlobalVariable.tempColorTempValue, Integer.valueOf(this.currentTempProgress));
        SharePreferenceUtils.remove(this, GlobalVariable.tempColorValue);
        SharePreferenceUtils.remove(this, GlobalVariable.tempColorValueR);
    }
}

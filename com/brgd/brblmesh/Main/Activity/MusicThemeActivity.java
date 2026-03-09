package com.brgd.brblmesh.Main.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MusicThemeActivity extends FatherActivity implements SeekBar.OnSeekBarChangeListener, ColorObserver, View.OnClickListener {
    ImageView backView;
    ImageView blueDownView;
    private SeekBar blueSeekBar;
    private String blueStr;
    ImageView blueUpView;
    private TextView blueValue;
    public ConstraintLayout colorPickerLayout;
    public ConstraintLayout colorSeekBarLayout;
    private ColorWheelView colorWheelView;
    private int currentBlueProgress;
    private int currentColor;
    private int currentGreenProgress;
    private int currentRedProgress;
    TextView diyColorText;
    RecyclerView diyRecyclerView;
    private ImageView editView;
    ImageView greenDownView;
    private SeekBar greenSeekBar;
    private String greenStr;
    ImageView greenUpView;
    private TextView greenValue;
    ImageView leftBtView;
    private ModDiyColorAdapter modDiyColorAdapter;
    private List<ModDiyColor> modDiyColorList;
    Realm realm;
    ImageView redDownView;
    private SeekBar redSeekBar;
    private String redStr;
    ImageView redUpView;
    private TextView redValue;
    ImageView rightBtView;
    TextView titleView;
    ConstraintLayout topLayout;
    public int addr = -1;
    public int groupId = -1;
    public int tempGroupId = -1;
    public int type = -1;
    private int currentDIYNum = 1;
    int[] themeArr = {R.string.MyColorTemp, R.string.Hallowmas1, R.string.Hallowmas2, R.string.Hallowmas3, R.string.Hallowmas4, R.string.Hallowmas5};
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MusicThemeActivity.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.musicTheme_back) {
                MusicThemeActivity.this.finish();
                return;
            }
            if (id == R.id.musicTheme_edit) {
                if (MusicThemeActivity.this.editView.isSelected()) {
                    MusicThemeActivity.this.editView.setSelected(false);
                    MusicThemeActivity.this.colorPickerLayout.setVisibility(0);
                    MusicThemeActivity.this.colorSeekBarLayout.setVisibility(4);
                    return;
                } else {
                    MusicThemeActivity.this.editView.setSelected(true);
                    MusicThemeActivity.this.colorPickerLayout.setVisibility(4);
                    MusicThemeActivity.this.colorSeekBarLayout.setVisibility(0);
                    MusicThemeActivity.this.upDateRgbValue();
                    return;
                }
            }
            if (id == R.id.musicTheme_toRightBt) {
                if (MusicThemeActivity.this.currentColor != -1) {
                    MusicThemeActivity.this.colorWheelView.moveSelector(false);
                }
            } else {
                if (id != R.id.musicTheme_toLeftBt || MusicThemeActivity.this.currentColor == -1) {
                    return;
                }
                MusicThemeActivity.this.colorWheelView.moveSelector(true);
            }
        }
    };

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_music_theme);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        Bundle bundleExtra = getIntent().getBundleExtra(GlobalVariable.toMusicThemeCtrl);
        if (bundleExtra != null) {
            this.currentDIYNum = bundleExtra.getInt(GlobalVariable.modNumber);
            this.addr = bundleExtra.getInt(GlobalVariable.addr);
            this.groupId = bundleExtra.getInt(GlobalVariable.groupId);
            this.tempGroupId = bundleExtra.getInt(GlobalVariable.tempGroupId);
            this.type = bundleExtra.getInt(GlobalVariable.type);
        }
        ImageView imageView = (ImageView) findViewById(R.id.musicTheme_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.musicTheme_title);
        this.titleView = textView;
        int i = this.currentDIYNum;
        if (i > 5) {
            textView.setText(this.themeArr[i - 6]);
        } else {
            textView.setText(this.themeArr[i]);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.musicTheme_edit);
        this.editView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.topLayout = (ConstraintLayout) findViewById(R.id.musicTheme_topView);
        this.colorPickerLayout = (ConstraintLayout) findViewById(R.id.musicTheme_colorPickerBg);
        ColorWheelView colorWheelView = (ColorWheelView) findViewById(R.id.musicTheme_colorPicker);
        this.colorWheelView = colorWheelView;
        colorWheelView.subscribe(this);
        this.colorWheelView.setColor(-65536, false);
        this.currentColor = -65536;
        this.colorSeekBarLayout = (ConstraintLayout) findViewById(R.id.musicTheme_colorSeekBarBg);
        SeekBar seekBar = (SeekBar) findViewById(R.id.musicTheme_RedSeekBar);
        this.redSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.redSeekBar.setMax(255);
        this.currentRedProgress = 0;
        this.redSeekBar.setProgress(0);
        this.redValue = (TextView) findViewById(R.id.musicTheme_RedTextValue);
        ImageView imageView3 = (ImageView) findViewById(R.id.musicTheme_Red_up);
        this.redUpView = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.musicTheme_Red_down);
        this.redDownView = imageView4;
        imageView4.setOnClickListener(this);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.musicTheme_GreenSeekBar);
        this.greenSeekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.greenSeekBar.setMax(255);
        this.currentGreenProgress = 0;
        this.greenSeekBar.setProgress(0);
        this.greenValue = (TextView) findViewById(R.id.musicTheme_GreenTextValue);
        ImageView imageView5 = (ImageView) findViewById(R.id.musicTheme_Green_up);
        this.greenUpView = imageView5;
        imageView5.setOnClickListener(this);
        ImageView imageView6 = (ImageView) findViewById(R.id.musicTheme_Green_down);
        this.greenDownView = imageView6;
        imageView6.setOnClickListener(this);
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.musicTheme_BlueSeekBar);
        this.blueSeekBar = seekBar3;
        seekBar3.setOnSeekBarChangeListener(this);
        this.blueSeekBar.setMax(255);
        this.currentBlueProgress = 0;
        this.blueSeekBar.setProgress(0);
        this.blueValue = (TextView) findViewById(R.id.musicTheme_BlueTextValue);
        ImageView imageView7 = (ImageView) findViewById(R.id.musicTheme_Blue_up);
        this.blueUpView = imageView7;
        imageView7.setOnClickListener(this);
        ImageView imageView8 = (ImageView) findViewById(R.id.musicTheme_Blue_down);
        this.blueDownView = imageView8;
        imageView8.setOnClickListener(this);
        ImageView imageView9 = (ImageView) findViewById(R.id.musicTheme_toRightBt);
        this.leftBtView = imageView9;
        imageView9.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView10 = (ImageView) findViewById(R.id.musicTheme_toLeftBt);
        this.rightBtView = imageView10;
        imageView10.setOnClickListener(this.disDoubleClickListener);
        this.diyColorText = (TextView) findViewById(R.id.musicTheme_diyColor_text);
        this.diyRecyclerView = (RecyclerView) findViewById(R.id.musicTheme_diyColor_recyclerView);
        this.diyRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        this.modDiyColorList = new ArrayList();
        ModDiyColorAdapter modDiyColorAdapter = new ModDiyColorAdapter(this, this.modDiyColorList);
        this.modDiyColorAdapter = modDiyColorAdapter;
        this.diyRecyclerView.setAdapter(modDiyColorAdapter);
        this.modDiyColorAdapter.setOnItemClickListener(new AnonymousClass1());
        handleDiyColorData();
    }

    /* JADX INFO: renamed from: com.brgd.brblmesh.Main.Activity.MusicThemeActivity$1, reason: invalid class name */
    class AnonymousClass1 implements ModDiyColorAdapter.OnItemClickListener {
        AnonymousClass1() {
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter.OnItemClickListener
        public void OnItemAddClick(View view, int i) {
            ModDiyColor modDiyColor = (ModDiyColor) MusicThemeActivity.this.modDiyColorList.get(i);
            if (modDiyColor.getDiyColor() == -2) {
                MusicThemeActivity.this.realm.beginTransaction();
                if (Tool.isRGBW(MusicThemeActivity.this.currentColor)) {
                    modDiyColor.setDiyColor(MusicThemeActivity.this.currentColor);
                } else {
                    modDiyColor.setDiyColor(MusicThemeActivity.this.colorWheelView.getSelectorColor(MusicThemeActivity.this.currentColor));
                }
                modDiyColor.setDiyColorR(MusicThemeActivity.this.currentColor);
                MusicThemeActivity.this.realm.commitTransaction();
                MusicThemeActivity.this.modDiyColorAdapter.notifyDataSetChanged();
            }
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter.OnItemClickListener
        public void OnItemDeleteClick(View view, final int i) {
            final CustomDialog customDialog = new CustomDialog(MusicThemeActivity.this);
            Window window = customDialog.getWindow();
            WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
            attributes.x = 0;
            attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), MusicThemeActivity.this.diyRecyclerView.getWidth() / 8.0f);
            window.setAttributes(attributes);
            customDialog.setTitle(R.string.prompt);
            customDialog.setMessage(R.string.confirmDelete);
            customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.MusicThemeActivity$1$$ExternalSyntheticLambda0
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
            ModDiyColor modDiyColor = (ModDiyColor) MusicThemeActivity.this.modDiyColorList.get(i);
            MusicThemeActivity.this.realm.beginTransaction();
            modDiyColor.setDiyColor(-2);
            modDiyColor.setDiyColorR(-2);
            MusicThemeActivity.this.realm.commitTransaction();
            MusicThemeActivity.this.modDiyColorAdapter.notifyDataSetChanged();
        }
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
        this.realm.close();
    }

    private void handleDiyColorData() {
        this.modDiyColorList.clear();
        RealmResults realmResultsFindAll = this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(this.currentDIYNum)).findAll();
        if (realmResultsFindAll.isEmpty()) {
            this.realm.beginTransaction();
            for (int i = 0; i < 8; i++) {
                ModDiyColor modDiyColor = (ModDiyColor) this.realm.createObject(ModDiyColor.class);
                modDiyColor.setModNumber(this.currentDIYNum);
                modDiyColor.setColorIndex(i);
                modDiyColor.setDiyColor(-2);
                modDiyColor.setDiyColorR(-2);
                this.modDiyColorList.add(modDiyColor);
            }
            this.realm.commitTransaction();
        } else {
            this.modDiyColorList.addAll(realmResultsFindAll);
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
            }
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
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int i;
        int i2;
        int i3;
        int id = view.getId();
        if (id == R.id.musicTheme_Red_up) {
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
        if (id == R.id.musicTheme_Red_down) {
            if (!(this.currentGreenProgress == 0 && this.currentBlueProgress == 0 && this.currentRedProgress == 1) && (i3 = this.currentRedProgress) > 0) {
                int i6 = i3 - 1;
                this.currentRedProgress = i6;
                this.redSeekBar.setProgress(i6);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.musicTheme_Green_up) {
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
        if (id == R.id.musicTheme_Green_down) {
            if (!(this.currentRedProgress == 0 && this.currentBlueProgress == 0 && this.currentGreenProgress == 1) && (i2 = this.currentGreenProgress) > 0) {
                int i9 = i2 - 1;
                this.currentGreenProgress = i9;
                this.greenSeekBar.setProgress(i9);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.musicTheme_Blue_up) {
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
        if (id == R.id.musicTheme_Blue_down) {
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
            GlobalBluetooth.getInstance().singleRgbCtrl(this.addr, 100, iRed, iGreen, i);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupRgbCtrl(this.type, this.tempGroupId, 100, iRed, iGreen, i);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupRgbCtrl(this.type, this.groupId, 100, iRed, iGreen, i);
        }
    }
}

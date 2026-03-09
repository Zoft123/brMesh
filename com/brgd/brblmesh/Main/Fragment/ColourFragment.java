package com.brgd.brblmesh.Main.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.ColorTempAdapter;
import com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorObserver;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity;
import com.brgd.brblmesh.Main.Interface.ColourFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ColourFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, ColorObserver, ColorWheelView.OnSetColorListener, ColourFragmentListener, View.OnClickListener {
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
    private TextView brightnessText;
    ImageView brightnessUpView;
    ImageView brightnessUpView1;
    private TextView brightnessValue;
    private TextView brightnessValue1;
    public ConstraintLayout colorOnOffBgLayout;
    public ConstraintLayout colorPickerLayout;
    public ConstraintLayout colorSeekBarLayout;
    ConstraintLayout colorTempScrollView;
    private ColorWheelView colorWheelView;
    private int currentBlueProgress;
    private int currentBrightnessProgress;
    private int currentColdParam;
    private int currentColor;
    private int currentDiyColorIndex;
    private ColorSelector currentDiyColorSelector;
    private int currentDiyColorTempIndex;
    private ColorSelector currentDiyColorTempSelector;
    private int currentGreenProgress;
    private int currentLightnessParam;
    private ColorSelector currentMyColorSelector;
    private ColorSelector currentMyColorTempSelector;
    private int currentRedProgress;
    private int currentTempProgress;
    private int currentWarmParam;
    private DeviceCtrlActivity deviceCtrlActivity;
    private DiyColorAdapter diyColorAdapter;
    private List<DiyColor> diyColorList;
    private ColorTempAdapter diyColorTempAdapter;
    private List<DiyColor> diyColorTempList;
    TextView diyColorTempText;
    TextView diyColorTempValue;
    TextView diyColorText;
    RecyclerView diyRecyclerView;
    RecyclerView diyTempRecyclerView;
    ImageView greenDownView;
    private SeekBar greenSeekBar;
    private String greenStr;
    ImageView greenUpView;
    private TextView greenValue;
    ImageView leftBtView;
    DiyColorAdapter myColorAdapter;
    private List<DiyColor> myColorList;
    DiyColorAdapter myColorTempAdapter;
    List<DiyColor> myColorTempList;
    TextView myColorTempText;
    TextView myColorTempValue;
    TextView myColorText;
    RecyclerView myRecyclerView;
    RecyclerView myTempRecyclerView;
    ImageView offView;
    ImageView onView;
    private Realm realm;
    ImageView redDownView;
    private SeekBar redSeekBar;
    private String redStr;
    ImageView redUpView;
    private TextView redValue;
    ImageView rightBtView;
    public SceneDevice sceneDevice;
    ConstraintLayout scrollView;
    ImageView tempDownView;
    ImageView tempDownView1;
    private SeekBar tempSeekBar;
    private SeekBar tempSeekBar1;
    ImageView tempUpView;
    ImageView tempUpView1;
    ImageView topOffView;
    ImageView topOnView;
    private String warmStr;
    TextView warmText;
    TextView warmText1;
    private TextView warmValue;
    private TextView warmValue1;
    public int addr = -1;
    public int groupId = -1;
    public int tempGroupId = -1;
    public int type = -1;
    public int sceneNumber = -1;
    public int currentSceneColor = -2;
    public int currentSceneColorTemp = -1;
    public int currentSceneLightness = -1;
    int[] diyColorArr = {CustomColor.diy1, CustomColor.diy2, CustomColor.diy3, CustomColor.diy4};
    int[] colorArr = {-3, -65536, CustomColor.GREEN, CustomColor.BLUE};
    int[] diyColorTempArr = {80, 60, 40, 20};
    int[] colorTempArr = {CustomColor.W_WHITE, CustomColor.S_WHITE, CustomColor.N_WHITE, CustomColor.D_WHITE, CustomColor.C_WHITE};
    int tempStart = 2700;
    int tempStep = 33;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ColourFragment.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.colour_toRightBt) {
                if (ColourFragment.this.currentColor != -1) {
                    ColourFragment.this.colorWheelView.moveSelector(false);
                    return;
                }
                return;
            }
            if (id == R.id.colour_toLeftBt) {
                if (ColourFragment.this.currentColor != -1) {
                    ColourFragment.this.colorWheelView.moveSelector(true);
                    return;
                }
                return;
            }
            if (id == R.id.colour_on || id == R.id.colour_topOn) {
                ColourFragment.this.onOffClick(true);
                return;
            }
            if (id == R.id.colour_off || id == R.id.colour_topOff) {
                ColourFragment.this.onOffClick(false);
                return;
            }
            if (id == R.id.colour_temp_up) {
                if (ColourFragment.this.currentTempProgress > 0) {
                    ColourFragment.this.currentTempProgress--;
                }
                ColourFragment.this.tempSeekBar.setProgress(100 - ColourFragment.this.currentTempProgress);
                ColourFragment.this.setColorTemp();
                return;
            }
            if (id == R.id.colour_temp_up1) {
                if (ColourFragment.this.currentTempProgress > 0) {
                    ColourFragment.this.currentTempProgress--;
                }
                ColourFragment.this.tempSeekBar1.setProgress(100 - ColourFragment.this.currentTempProgress);
                ColourFragment.this.setColorTemp();
                if (ColourFragment.this.currentMyColorTempSelector != null) {
                    ColourFragment.this.currentMyColorTempSelector.setBorderSelectorColor(0);
                    ColourFragment.this.currentMyColorTempSelector = null;
                    ColourFragment.this.myColorTempValue.setVisibility(4);
                }
                if (ColourFragment.this.currentDiyColorTempSelector != null) {
                    if (ColourFragment.this.sceneNumber != -1) {
                        ColourFragment.this.currentDiyColorTempSelector.setBorderSelectorColor(0);
                        ColourFragment.this.currentDiyColorTempSelector = null;
                        ColourFragment.this.diyColorTempValue.setVisibility(4);
                        return;
                    } else {
                        DiyColor diyColor = (DiyColor) ColourFragment.this.diyColorTempList.get(ColourFragment.this.currentDiyColorTempIndex);
                        ColourFragment.this.realm.beginTransaction();
                        diyColor.setColorValue(ColourFragment.this.currentTempProgress);
                        ColourFragment.this.realm.commitTransaction();
                        ColourFragment.this.diyColorTempAdapter.notifyDataSetChanged();
                        ColourFragment.this.diyColorTempValue.setText(ColourFragment.this.warmStr);
                        return;
                    }
                }
                return;
            }
            if (id == R.id.colour_temp_down) {
                if (ColourFragment.this.currentTempProgress < 100) {
                    ColourFragment.this.currentTempProgress++;
                }
                ColourFragment.this.tempSeekBar.setProgress(100 - ColourFragment.this.currentTempProgress);
                ColourFragment.this.setColorTemp();
                return;
            }
            if (id == R.id.colour_temp_down1) {
                if (ColourFragment.this.currentTempProgress < 100) {
                    ColourFragment.this.currentTempProgress++;
                }
                ColourFragment.this.tempSeekBar1.setProgress(100 - ColourFragment.this.currentTempProgress);
                ColourFragment.this.setColorTemp();
                if (ColourFragment.this.currentMyColorTempSelector != null) {
                    ColourFragment.this.currentMyColorTempSelector.setBorderSelectorColor(0);
                    ColourFragment.this.currentMyColorTempSelector = null;
                    ColourFragment.this.myColorTempValue.setVisibility(4);
                }
                if (ColourFragment.this.currentDiyColorTempSelector != null) {
                    if (ColourFragment.this.sceneNumber != -1) {
                        ColourFragment.this.currentDiyColorTempSelector.setBorderSelectorColor(0);
                        ColourFragment.this.currentDiyColorTempSelector = null;
                        ColourFragment.this.diyColorTempValue.setVisibility(4);
                        return;
                    } else {
                        DiyColor diyColor2 = (DiyColor) ColourFragment.this.diyColorTempList.get(ColourFragment.this.currentDiyColorTempIndex);
                        ColourFragment.this.realm.beginTransaction();
                        diyColor2.setColorValue(ColourFragment.this.currentTempProgress);
                        ColourFragment.this.realm.commitTransaction();
                        ColourFragment.this.diyColorTempAdapter.notifyDataSetChanged();
                        ColourFragment.this.diyColorTempValue.setText(ColourFragment.this.warmStr);
                        return;
                    }
                }
                return;
            }
            if (id == R.id.colour_brightness_up) {
                if (ColourFragment.this.currentBrightnessProgress < 100) {
                    ColourFragment.this.currentBrightnessProgress++;
                }
                ColourFragment.this.brightnessSeekBar.setProgress(ColourFragment.this.currentBrightnessProgress - 1);
                ColourFragment.this.setLightness();
                return;
            }
            if (id == R.id.colour_brightness_up1) {
                if (ColourFragment.this.currentBrightnessProgress < 100) {
                    ColourFragment.this.currentBrightnessProgress++;
                }
                ColourFragment.this.brightnessSeekBar1.setProgress(ColourFragment.this.currentBrightnessProgress - 1);
                ColourFragment.this.setLightness();
                return;
            }
            if (id == R.id.colour_brightness_down) {
                if (ColourFragment.this.currentBrightnessProgress > 1) {
                    ColourFragment.this.currentBrightnessProgress--;
                }
                ColourFragment.this.brightnessSeekBar.setProgress(ColourFragment.this.currentBrightnessProgress - 1);
                ColourFragment.this.setLightness();
                return;
            }
            if (id == R.id.colour_brightness_down1) {
                if (ColourFragment.this.currentBrightnessProgress > 1) {
                    ColourFragment.this.currentBrightnessProgress--;
                }
                ColourFragment.this.brightnessSeekBar1.setProgress(ColourFragment.this.currentBrightnessProgress - 1);
                ColourFragment.this.setLightness();
            }
        }
    };

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.deviceCtrlActivity = (DeviceCtrlActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_colour, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.colorWheelView.unsubscribe(this);
        this.colorWheelView.removeOnSetColorListener();
        this.realm.close();
    }

    private void initView(View view) {
        int i;
        Realm.init(this.deviceCtrlActivity.getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        this.colorPickerLayout = (ConstraintLayout) view.findViewById(R.id.colour_colorPickerBg);
        ColorWheelView colorWheelView = (ColorWheelView) view.findViewById(R.id.colour_colorPicker);
        this.colorWheelView = colorWheelView;
        this.currentColor = colorWheelView.getColor();
        this.colorWheelView.subscribe(this);
        this.colorWheelView.setOnSetColorListener(this);
        this.colorSeekBarLayout = (ConstraintLayout) view.findViewById(R.id.colour_colorSeekBarBg);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.colour_RedSeekBar);
        this.redSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.redSeekBar.setMax(255);
        this.currentRedProgress = 255;
        this.redSeekBar.setProgress(255);
        this.redValue = (TextView) view.findViewById(R.id.colour_RedTextValue);
        ImageView imageView = (ImageView) view.findViewById(R.id.colour_Red_up);
        this.redUpView = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.colour_Red_down);
        this.redDownView = imageView2;
        imageView2.setOnClickListener(this);
        SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.colour_GreenSeekBar);
        this.greenSeekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.greenSeekBar.setMax(255);
        this.currentGreenProgress = 0;
        this.greenSeekBar.setProgress(0);
        this.greenValue = (TextView) view.findViewById(R.id.colour_GreenTextValue);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.colour_Green_up);
        this.greenUpView = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.colour_Green_down);
        this.greenDownView = imageView4;
        imageView4.setOnClickListener(this);
        SeekBar seekBar3 = (SeekBar) view.findViewById(R.id.colour_BlueSeekBar);
        this.blueSeekBar = seekBar3;
        seekBar3.setOnSeekBarChangeListener(this);
        this.blueSeekBar.setMax(255);
        this.currentBlueProgress = 0;
        this.blueSeekBar.setProgress(0);
        this.blueValue = (TextView) view.findViewById(R.id.colour_BlueTextValue);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.colour_Blue_up);
        this.blueUpView = imageView5;
        imageView5.setOnClickListener(this);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.colour_Blue_down);
        this.blueDownView = imageView6;
        imageView6.setOnClickListener(this);
        ImageView imageView7 = (ImageView) view.findViewById(R.id.colour_on);
        this.onView = imageView7;
        imageView7.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView8 = (ImageView) view.findViewById(R.id.colour_off);
        this.offView = imageView8;
        imageView8.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView9 = (ImageView) view.findViewById(R.id.colour_toRightBt);
        this.leftBtView = imageView9;
        imageView9.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView10 = (ImageView) view.findViewById(R.id.colour_toLeftBt);
        this.rightBtView = imageView10;
        imageView10.setOnClickListener(this.disDoubleClickListener);
        this.scrollView = (ConstraintLayout) view.findViewById(R.id.colour_scrollView);
        this.currentDiyColorIndex = -1;
        this.diyColorText = (TextView) view.findViewById(R.id.colour_diy_text);
        this.diyRecyclerView = (RecyclerView) view.findViewById(R.id.colour_diy_recyclerView);
        this.diyRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), this.colorArr.length));
        this.diyColorList = new ArrayList();
        DiyColorAdapter diyColorAdapter = new DiyColorAdapter(getActivity(), this.diyColorList);
        this.diyColorAdapter = diyColorAdapter;
        this.diyRecyclerView.setAdapter(diyColorAdapter);
        this.diyColorAdapter.setOnItemClickListener(new DiyColorAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ColourFragment$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i2) {
                this.f$0.lambda$initView$0(view2, i2);
            }
        });
        this.myColorText = (TextView) view.findViewById(R.id.colour_my_text);
        this.myRecyclerView = (RecyclerView) view.findViewById(R.id.colour_my_recyclerView);
        this.myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), this.colorArr.length));
        this.myColorList = new ArrayList();
        DiyColorAdapter diyColorAdapter2 = new DiyColorAdapter(getActivity(), this.myColorList);
        this.myColorAdapter = diyColorAdapter2;
        this.myRecyclerView.setAdapter(diyColorAdapter2);
        this.myColorAdapter.setOnItemClickListener(new DiyColorAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ColourFragment$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i2) {
                this.f$0.lambda$initView$1(view2, i2);
            }
        });
        this.warmText = (TextView) view.findViewById(R.id.colour_warmText);
        this.warmValue = (TextView) view.findViewById(R.id.colour_warmTextValue);
        SeekBar seekBar4 = (SeekBar) view.findViewById(R.id.colour_tempSeekBar);
        this.tempSeekBar = seekBar4;
        seekBar4.setOnSeekBarChangeListener(this);
        this.tempSeekBar.setMax(100);
        int i2 = 50;
        this.currentTempProgress = 50;
        this.tempSeekBar.setProgress(50);
        ImageView imageView11 = (ImageView) view.findViewById(R.id.colour_temp_up);
        this.tempUpView = imageView11;
        imageView11.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView12 = (ImageView) view.findViewById(R.id.colour_temp_down);
        this.tempDownView = imageView12;
        imageView12.setOnClickListener(this.disDoubleClickListener);
        this.tempSeekBar.setVisibility(0);
        this.warmText.setVisibility(0);
        this.warmValue.setVisibility(0);
        this.tempUpView.setVisibility(0);
        this.tempDownView.setVisibility(0);
        this.brightnessText = (TextView) view.findViewById(R.id.colour_brightnessText);
        this.brightnessValue = (TextView) view.findViewById(R.id.colour_brightnessTextValue);
        SeekBar seekBar5 = (SeekBar) view.findViewById(R.id.colour_brightnessSeekBar);
        this.brightnessSeekBar = seekBar5;
        seekBar5.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar.setMax(99);
        this.currentBrightnessProgress = 100;
        this.brightnessSeekBar.setProgress(100 - 1);
        ImageView imageView13 = (ImageView) view.findViewById(R.id.colour_brightness_up);
        this.brightnessUpView = imageView13;
        imageView13.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView14 = (ImageView) view.findViewById(R.id.colour_brightness_down);
        this.brightnessDownView = imageView14;
        imageView14.setOnClickListener(this.disDoubleClickListener);
        RealmResults realmResultsFindAll = this.realm.where(DiyColor.class).equalTo("colorIdentifier", "DiyColor").findAll();
        if (realmResultsFindAll.isEmpty()) {
            this.realm.beginTransaction();
            int[] iArr = this.diyColorArr;
            int length = iArr.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = iArr[i3];
                int i5 = i2;
                DiyColor diyColor = (DiyColor) this.realm.createObject(DiyColor.class);
                diyColor.setColorIdentifier("DiyColor");
                diyColor.setColorValue(i4);
                diyColor.setDiyColorR(i4);
                this.diyColorList.add(diyColor);
                i3++;
                i2 = i5;
            }
            i = i2;
            this.realm.commitTransaction();
        } else {
            i = 50;
            this.diyColorList.addAll(realmResultsFindAll);
        }
        this.diyColorAdapter.notifyDataSetChanged();
        RealmResults realmResultsFindAll2 = this.realm.where(DiyColor.class).equalTo("colorIdentifier", GlobalVariable.myColor).findAll();
        if (realmResultsFindAll2.isEmpty()) {
            this.realm.beginTransaction();
            for (int i6 : this.colorArr) {
                DiyColor diyColor2 = (DiyColor) this.realm.createObject(DiyColor.class);
                diyColor2.setColorIdentifier(GlobalVariable.myColor);
                diyColor2.setColorValue(i6);
                this.myColorList.add(diyColor2);
            }
            this.realm.commitTransaction();
        } else {
            this.myColorList.addAll(realmResultsFindAll2);
        }
        this.myColorAdapter.notifyDataSetChanged();
        this.colorTempScrollView = (ConstraintLayout) view.findViewById(R.id.colour_temp_scrollView);
        this.warmText1 = (TextView) view.findViewById(R.id.colour_warmText1);
        this.warmValue1 = (TextView) view.findViewById(R.id.colour_warmTextValue1);
        SeekBar seekBar6 = (SeekBar) view.findViewById(R.id.colour_tempSeekBar1);
        this.tempSeekBar1 = seekBar6;
        seekBar6.setOnSeekBarChangeListener(this);
        this.tempSeekBar1.setMax(100);
        this.tempSeekBar1.setProgress(this.currentTempProgress);
        ImageView imageView15 = (ImageView) view.findViewById(R.id.colour_temp_up1);
        this.tempUpView1 = imageView15;
        imageView15.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView16 = (ImageView) view.findViewById(R.id.colour_temp_down1);
        this.tempDownView1 = imageView16;
        imageView16.setOnClickListener(this.disDoubleClickListener);
        this.brightnessValue1 = (TextView) view.findViewById(R.id.colour_brightnessTextValue1);
        SeekBar seekBar7 = (SeekBar) view.findViewById(R.id.colour_brightnessSeekBar1);
        this.brightnessSeekBar1 = seekBar7;
        seekBar7.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar1.setMax(99);
        this.brightnessSeekBar1.setProgress(this.currentBrightnessProgress - 1);
        ImageView imageView17 = (ImageView) view.findViewById(R.id.colour_brightness_up1);
        this.brightnessUpView1 = imageView17;
        imageView17.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView18 = (ImageView) view.findViewById(R.id.colour_brightness_down1);
        this.brightnessDownView1 = imageView18;
        imageView18.setOnClickListener(this.disDoubleClickListener);
        this.currentDiyColorTempIndex = -1;
        this.diyColorTempText = (TextView) view.findViewById(R.id.colour_diyTemp_text);
        this.diyColorTempValue = (TextView) view.findViewById(R.id.colour_diyTempTextValue);
        this.diyTempRecyclerView = (RecyclerView) view.findViewById(R.id.colour_diyTemp_recyclerView);
        this.diyTempRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), this.colorArr.length));
        this.diyColorTempList = new ArrayList();
        ColorTempAdapter colorTempAdapter = new ColorTempAdapter(getActivity(), this.diyColorTempList);
        this.diyColorTempAdapter = colorTempAdapter;
        this.diyTempRecyclerView.setAdapter(colorTempAdapter);
        this.diyColorTempAdapter.setOnItemClickListener(new ColorTempAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ColourFragment$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralAdapter.ColorTempAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i7) {
                this.f$0.lambda$initView$2(view2, i7);
            }
        });
        this.myColorTempText = (TextView) view.findViewById(R.id.colour_myTemp_text);
        this.myColorTempValue = (TextView) view.findViewById(R.id.colour_myTempTextValue);
        this.myTempRecyclerView = (RecyclerView) view.findViewById(R.id.colour_myTemp_recyclerView);
        this.myTempRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), this.colorTempArr.length));
        this.myColorTempList = new ArrayList();
        DiyColorAdapter diyColorAdapter3 = new DiyColorAdapter(getActivity(), this.myColorTempList);
        this.myColorTempAdapter = diyColorAdapter3;
        this.myTempRecyclerView.setAdapter(diyColorAdapter3);
        this.myColorTempAdapter.setOnItemClickListener(new DiyColorAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ColourFragment$$ExternalSyntheticLambda3
            @Override // com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i7) {
                this.f$0.lambda$initView$3(view2, i7);
            }
        });
        this.colorOnOffBgLayout = (ConstraintLayout) view.findViewById(R.id.colour_OnOff_bg);
        ImageView imageView19 = (ImageView) view.findViewById(R.id.colour_topOn);
        this.topOnView = imageView19;
        imageView19.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView20 = (ImageView) view.findViewById(R.id.colour_topOff);
        this.topOffView = imageView20;
        imageView20.setOnClickListener(this.disDoubleClickListener);
        RealmResults realmResultsFindAll3 = this.realm.where(DiyColor.class).equalTo("colorIdentifier", GlobalVariable.diyColorTemp).findAll();
        if (realmResultsFindAll3.isEmpty()) {
            this.realm.beginTransaction();
            for (int i7 : this.diyColorTempArr) {
                DiyColor diyColor3 = (DiyColor) this.realm.createObject(DiyColor.class);
                diyColor3.setColorIdentifier(GlobalVariable.diyColorTemp);
                diyColor3.setColorValue(i7);
                this.diyColorTempList.add(diyColor3);
            }
            this.realm.commitTransaction();
        } else {
            this.diyColorTempList.addAll(realmResultsFindAll3);
        }
        this.diyColorTempAdapter.notifyDataSetChanged();
        RealmResults realmResultsSort = this.realm.where(DiyColor.class).equalTo("colorIdentifier", GlobalVariable.myColorTemp).findAll().sort(GlobalVariable.colorValue);
        if (realmResultsSort.isEmpty()) {
            this.realm.beginTransaction();
            for (int i8 : this.colorTempArr) {
                DiyColor diyColor4 = (DiyColor) this.realm.createObject(DiyColor.class);
                diyColor4.setColorIdentifier(GlobalVariable.myColorTemp);
                diyColor4.setColorValue(i8);
                this.myColorTempList.add(diyColor4);
            }
            this.realm.commitTransaction();
        } else if (realmResultsSort.size() == 3) {
            this.realm.beginTransaction();
            realmResultsSort.deleteAllFromRealm();
            for (int i9 : this.colorTempArr) {
                DiyColor diyColor5 = (DiyColor) this.realm.createObject(DiyColor.class);
                diyColor5.setColorIdentifier(GlobalVariable.myColorTemp);
                diyColor5.setColorValue(i9);
                this.myColorTempList.add(diyColor5);
            }
            this.realm.commitTransaction();
        } else {
            this.myColorTempList.addAll(realmResultsSort);
        }
        this.myColorTempAdapter.notifyDataSetChanged();
        int iIntValue = ((Integer) SharePreferenceUtils.get(this.deviceCtrlActivity, GlobalVariable.colorTempValue, Integer.valueOf(i))).intValue();
        this.currentTempProgress = iIntValue;
        this.tempSeekBar.setProgress(100 - iIntValue);
        this.tempSeekBar1.setProgress(100 - this.currentTempProgress);
        int i10 = (int) ((this.currentTempProgress / 100.0f) * 255.0f);
        this.currentWarmParam = i10;
        this.currentColdParam = 255 - i10;
        int iIntValue2 = ((Integer) SharePreferenceUtils.get(this.deviceCtrlActivity, GlobalVariable.lightnessValue, 100)).intValue();
        this.currentBrightnessProgress = iIntValue2;
        this.brightnessSeekBar.setProgress(iIntValue2 - 1);
        this.brightnessSeekBar1.setProgress(this.currentBrightnessProgress - 1);
        this.currentLightnessParam = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
        int iIntValue3 = ((Integer) SharePreferenceUtils.get(this.deviceCtrlActivity, GlobalVariable.colorValue, -65536)).intValue();
        this.currentColor = iIntValue3;
        if (iIntValue3 == -3) {
            this.colorWheelView.setColor(-1, false);
        } else {
            this.colorWheelView.setColor(iIntValue3, false);
        }
        if (this.sceneNumber != -1) {
            SceneDevice sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.sceneNumber)).equalTo(GlobalVariable.did, ((BleDevice) Objects.requireNonNull((BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst())).getDid()).findFirst();
            this.sceneDevice = sceneDevice;
            if (sceneDevice != null) {
                if (sceneDevice.getColor() != -2) {
                    int color = this.sceneDevice.getColor();
                    this.currentSceneColor = color;
                    if (color == -3) {
                        this.colorWheelView.setColor(-1, false);
                    } else {
                        this.colorWheelView.setColor(color, false);
                    }
                }
                if (this.sceneDevice.getTemp() != -1) {
                    int temp = this.sceneDevice.getTemp();
                    this.currentSceneColorTemp = temp;
                    this.tempSeekBar.setProgress(100 - temp);
                    this.tempSeekBar1.setProgress(100 - this.currentSceneColorTemp);
                }
            }
        }
        handleViewVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        ColorSelector colorSelector = this.currentDiyColorSelector;
        if (colorSelector != null) {
            colorSelector.setBorderSelectorColor(0);
            this.currentDiyColorSelector = null;
            this.currentDiyColorIndex = -1;
        }
        this.currentDiyColorSelector = (ColorSelector) view;
        this.currentDiyColorIndex = i;
        DiyColor diyColor = this.diyColorList.get(i);
        if (diyColor.getDiyColorR() != 0) {
            this.colorWheelView.setColor1(diyColor.getDiyColorR(), false);
        } else {
            this.colorWheelView.setColor1(diyColor.getColorValue(), false);
        }
        if (Tool.isRGBW(diyColor.getColorValue())) {
            this.currentDiyColorSelector.setBorderSelectorColor(CustomColor.SELECT);
        } else {
            this.currentDiyColorSelector.setBorderSelectorColor(-1);
        }
        ColorSelector colorSelector2 = this.currentMyColorSelector;
        if (colorSelector2 != null) {
            colorSelector2.setBorderSelectorColor(0);
            this.currentMyColorSelector = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view, int i) {
        ColorSelector colorSelector = this.currentMyColorSelector;
        if (colorSelector != null) {
            colorSelector.setBorderSelectorColor(0);
            this.currentMyColorSelector = null;
        }
        this.currentMyColorSelector = (ColorSelector) view;
        DiyColor diyColor = this.myColorList.get(i);
        if (diyColor.getColorValue() == -3) {
            this.currentMyColorSelector.setBorderSelectorColor(CustomColor.SELECT);
            this.colorWheelView.setColor(-1, false);
            setWhiteCommand();
        } else {
            this.currentMyColorSelector.setBorderSelectorColor(-3);
            this.colorWheelView.setColor1(diyColor.getColorValue(), false);
        }
        ColorSelector colorSelector2 = this.currentDiyColorSelector;
        if (colorSelector2 != null) {
            colorSelector2.setBorderSelectorColor(0);
            this.currentDiyColorSelector = null;
            this.currentDiyColorIndex = -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view, int i) {
        ColorSelector colorSelector = this.currentDiyColorTempSelector;
        if (colorSelector != null) {
            colorSelector.setBorderSelectorColor(0);
            this.currentDiyColorTempSelector = null;
            this.currentDiyColorTempIndex = -1;
        }
        ColorSelector colorSelector2 = (ColorSelector) view;
        this.currentDiyColorTempSelector = colorSelector2;
        colorSelector2.setBorderSelectorColor(-1);
        this.currentDiyColorTempIndex = i;
        this.currentTempProgress = this.diyColorTempList.get(i).getColorValue();
        setColorTemp();
        this.tempSeekBar1.setProgress(100 - this.currentTempProgress);
        this.diyColorTempValue.setVisibility(0);
        this.diyColorTempValue.setText(this.warmStr);
        ColorSelector colorSelector3 = this.currentMyColorTempSelector;
        if (colorSelector3 != null) {
            colorSelector3.setBorderSelectorColor(0);
            this.currentMyColorTempSelector = null;
            this.myColorTempValue.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view, int i) {
        ColorSelector colorSelector = this.currentMyColorTempSelector;
        if (colorSelector != null) {
            colorSelector.setBorderSelectorColor(0);
            this.currentMyColorTempSelector = null;
        }
        ColorSelector colorSelector2 = (ColorSelector) view;
        this.currentMyColorTempSelector = colorSelector2;
        colorSelector2.setBorderSelectorColor(-3);
        ColorSelector colorSelector3 = this.currentDiyColorTempSelector;
        if (colorSelector3 != null) {
            colorSelector3.setBorderSelectorColor(0);
            this.currentDiyColorTempSelector = null;
            this.currentDiyColorTempIndex = -1;
            this.diyColorTempValue.setVisibility(4);
        }
        this.currentTempProgress = 100;
        String string = getString(R.string.warmWhite);
        if (i == 1) {
            this.currentTempProgress = 80;
            string = getString(R.string.softWhite);
        } else if (i == 2) {
            this.currentTempProgress = 50;
            string = getString(R.string.white);
        } else if (i == 3) {
            this.currentTempProgress = 20;
            string = getString(R.string.daylightWhite);
        } else if (i == 4) {
            this.currentTempProgress = 0;
            string = getString(R.string.coldWhite);
        }
        setColorTemp();
        this.tempSeekBar1.setProgress(100 - this.currentTempProgress);
        this.myColorTempValue.setVisibility(0);
        this.myColorTempValue.setText(string);
    }

    private void handleViewVisibility() {
        if (this.deviceCtrlActivity.viewType == 43169) {
            this.tempSeekBar.setVisibility(8);
            this.warmValue.setVisibility(8);
            this.warmText.setVisibility(8);
            this.tempUpView.setVisibility(8);
            this.tempDownView.setVisibility(8);
            this.scrollView.setScrollbarFadingEnabled(true);
            return;
        }
        if (this.deviceCtrlActivity.viewType == 43168) {
            this.tempSeekBar.setVisibility(8);
            this.warmValue.setVisibility(8);
            this.warmText.setVisibility(8);
            this.tempUpView.setVisibility(8);
            this.tempDownView.setVisibility(8);
            this.brightnessText.setVisibility(8);
            this.brightnessSeekBar.setVisibility(8);
            this.brightnessValue.setVisibility(8);
            this.brightnessDownView.setVisibility(8);
            this.brightnessUpView.setVisibility(8);
            return;
        }
        if (this.deviceCtrlActivity.viewType == 43051) {
            this.colorPickerLayout.setVisibility(8);
            this.colorSeekBarLayout.setVisibility(8);
            this.scrollView.setVisibility(8);
            this.colorTempScrollView.setVisibility(0);
            this.colorTempScrollView.setScrollbarFadingEnabled(true);
            return;
        }
        if (this.deviceCtrlActivity.viewType == 43049) {
            this.colorPickerLayout.setVisibility(8);
            this.colorSeekBarLayout.setVisibility(8);
            this.scrollView.setVisibility(8);
            this.colorTempScrollView.setVisibility(0);
            this.colorTempScrollView.setScrollbarFadingEnabled(true);
            this.warmText1.setVisibility(4);
            this.warmValue1.setVisibility(4);
            this.tempUpView1.setVisibility(4);
            this.tempDownView1.setVisibility(4);
            this.tempSeekBar1.setVisibility(4);
            this.diyColorTempText.setVisibility(4);
            this.diyTempRecyclerView.setVisibility(4);
            this.myColorTempText.setVisibility(4);
            this.myTempRecyclerView.setVisibility(4);
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

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorWheelView.OnSetColorListener
    public void OnSetColor(int i) {
        ColorSelector colorSelector = this.currentMyColorSelector;
        if (colorSelector != null && i != colorSelector.getCurrentSelectorColor()) {
            this.currentMyColorSelector.setBorderSelectorColor(0);
            this.currentMyColorSelector = null;
        }
        ColorSelector colorSelector2 = this.currentDiyColorSelector;
        if (colorSelector2 != null) {
            if (this.sceneNumber != -1) {
                colorSelector2.setBorderSelectorColor(0);
                this.currentDiyColorSelector = null;
                return;
            }
            int selectorColor = this.colorWheelView.getSelectorColor(this.currentColor);
            DiyColor diyColor = this.diyColorList.get(this.currentDiyColorIndex);
            this.realm.beginTransaction();
            diyColor.setColorValue(selectorColor);
            diyColor.setDiyColorR(i);
            this.realm.commitTransaction();
            this.diyColorAdapter.notifyDataSetChanged();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar == this.tempSeekBar) {
            this.currentTempProgress = 100 - seekBar.getProgress();
            String str = (this.tempStart + (seekBar.getProgress() * this.tempStep)) + "K";
            this.warmStr = str;
            this.warmValue.setText(str);
            return;
        }
        if (seekBar == this.tempSeekBar1) {
            this.currentTempProgress = 100 - seekBar.getProgress();
            String str2 = (this.tempStart + (seekBar.getProgress() * this.tempStep)) + "K";
            this.warmStr = str2;
            this.warmValue1.setText(str2);
            return;
        }
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str3 = this.currentBrightnessProgress + "%";
            this.brightnessStr = str3;
            this.brightnessValue.setText(str3);
            return;
        }
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str4 = this.currentBrightnessProgress + "%";
            this.brightnessStr = str4;
            this.brightnessValue1.setText(str4);
            return;
        }
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
        if (seekBar == this.tempSeekBar) {
            this.currentTempProgress = 100 - seekBar.getProgress();
            String str = (this.tempStart + (seekBar.getProgress() * this.tempStep)) + "K";
            this.warmStr = str;
            this.warmValue.setText(str);
            setColorTemp();
            return;
        }
        if (seekBar == this.tempSeekBar1) {
            this.currentTempProgress = 100 - seekBar.getProgress();
            String str2 = (this.tempStart + (seekBar.getProgress() * this.tempStep)) + "K";
            this.warmStr = str2;
            this.warmValue1.setText(str2);
            setColorTemp();
            ColorSelector colorSelector = this.currentMyColorTempSelector;
            if (colorSelector != null) {
                colorSelector.setBorderSelectorColor(0);
                this.currentMyColorTempSelector = null;
                this.myColorTempValue.setVisibility(4);
            }
            ColorSelector colorSelector2 = this.currentDiyColorTempSelector;
            if (colorSelector2 != null) {
                if (this.sceneNumber != -1) {
                    colorSelector2.setBorderSelectorColor(0);
                    this.currentDiyColorTempSelector = null;
                    this.diyColorTempValue.setVisibility(4);
                    return;
                } else {
                    DiyColor diyColor = this.diyColorTempList.get(this.currentDiyColorTempIndex);
                    this.realm.beginTransaction();
                    diyColor.setColorValue(this.currentTempProgress);
                    this.realm.commitTransaction();
                    this.diyColorTempAdapter.notifyDataSetChanged();
                    this.diyColorTempValue.setText(this.warmStr);
                    return;
                }
            }
            return;
        }
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str3 = this.currentBrightnessProgress + "%";
            this.brightnessStr = str3;
            this.brightnessValue.setText(str3);
            setLightness();
            return;
        }
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str4 = this.currentBrightnessProgress + "%";
            this.brightnessStr = str4;
            this.brightnessValue1.setText(str4);
            setLightness();
            return;
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public void onOffClick(boolean z) {
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().singleOnOff(this.addr, z);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupOnOff(this.type, this.tempGroupId, z);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupOnOff(this.type, this.groupId, z);
        }
        if (this.sceneNumber == -1 || z) {
            return;
        }
        this.currentSceneColor = -2;
        this.currentSceneLightness = -1;
        this.currentSceneColorTemp = -1;
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
            GlobalBluetooth.getInstance().singleRgbCtrl(this.addr, this.currentLightnessParam, iRed, iGreen, i);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupRgbCtrl(this.type, this.tempGroupId, this.currentLightnessParam, iRed, iGreen, i);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupRgbCtrl(this.type, this.groupId, this.currentLightnessParam, iRed, iGreen, i);
        }
        if (this.sceneNumber != -1) {
            this.currentSceneColor = this.currentColor;
        } else {
            SharePreferenceUtils.put(this.deviceCtrlActivity, GlobalVariable.colorValue, Integer.valueOf(this.currentColor));
        }
    }

    private void setWhiteCommand() {
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().singleWhite(this.addr, this.currentLightnessParam);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupWhite(this.type, this.tempGroupId, this.currentLightnessParam);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupWhite(this.type, this.groupId, this.currentLightnessParam);
        }
        if (this.sceneNumber != -1) {
            this.currentSceneColor = -3;
        } else {
            SharePreferenceUtils.put(this.deviceCtrlActivity, GlobalVariable.colorValue, -3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLightness() {
        this.currentLightnessParam = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().singleLightness(this.addr, this.currentLightnessParam);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupLightness(this.type, this.tempGroupId, this.currentLightnessParam);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupLightness(this.type, this.groupId, this.currentLightnessParam);
        }
        if (this.sceneNumber != -1) {
            this.currentSceneLightness = this.currentBrightnessProgress;
        } else {
            SharePreferenceUtils.put(this.deviceCtrlActivity, GlobalVariable.lightnessValue, Integer.valueOf(this.currentBrightnessProgress));
        }
    }

    private void setRgbColor() {
        int iRgb = Color.rgb(this.currentRedProgress, this.currentGreenProgress, this.currentBlueProgress);
        if (iRgb == -16777216) {
            onOffClick(false);
            this.currentColor = iRgb;
            this.colorWheelView.setColor(iRgb, false);
            ColorSelector colorSelector = this.currentMyColorSelector;
            if (colorSelector != null && iRgb != colorSelector.getCurrentSelectorColor()) {
                this.currentMyColorSelector.setBorderSelectorColor(0);
                this.currentMyColorSelector = null;
            }
            ColorSelector colorSelector2 = this.currentDiyColorSelector;
            if (colorSelector2 != null) {
                colorSelector2.setBorderSelectorColor(0);
                this.currentDiyColorSelector = null;
            }
            if (this.sceneNumber != -1) {
                this.currentSceneColor = -2;
                this.currentSceneLightness = -1;
                return;
            } else {
                SharePreferenceUtils.put(this.deviceCtrlActivity, GlobalVariable.colorValue, Integer.valueOf(this.currentColor));
                return;
            }
        }
        if (Tool.isRGBW(iRgb)) {
            this.colorWheelView.setColor1(iRgb, false);
            ColorSelector colorSelector3 = this.currentMyColorSelector;
            if (colorSelector3 != null && iRgb != colorSelector3.getCurrentSelectorColor()) {
                this.currentMyColorSelector.setBorderSelectorColor(0);
                this.currentMyColorSelector = null;
            }
            ColorSelector colorSelector4 = this.currentDiyColorSelector;
            if (colorSelector4 != null) {
                if (this.sceneNumber != -1) {
                    colorSelector4.setBorderSelectorColor(0);
                    this.currentDiyColorSelector = null;
                    return;
                }
                DiyColor diyColor = this.diyColorList.get(this.currentDiyColorIndex);
                this.realm.beginTransaction();
                diyColor.setColorValue(iRgb);
                diyColor.setDiyColorR(iRgb);
                this.realm.commitTransaction();
                this.diyColorAdapter.notifyDataSetChanged();
                this.currentDiyColorSelector.setBorderSelectorColor(CustomColor.SELECT);
                return;
            }
            return;
        }
        this.colorWheelView.setColor1(iRgb, false);
        ColorSelector colorSelector5 = this.currentMyColorSelector;
        if (colorSelector5 != null && iRgb != colorSelector5.getCurrentSelectorColor()) {
            this.currentMyColorSelector.setBorderSelectorColor(0);
            this.currentMyColorSelector = null;
        }
        ColorSelector colorSelector6 = this.currentDiyColorSelector;
        if (colorSelector6 != null) {
            if (this.sceneNumber != -1) {
                colorSelector6.setBorderSelectorColor(0);
                this.currentDiyColorSelector = null;
                return;
            }
            int i = this.currentRedProgress;
            if (i != 0 && this.currentGreenProgress == 0 && this.currentBlueProgress == 0) {
                iRgb = -65536;
            } else if (i == 0 && this.currentGreenProgress != 0 && this.currentBlueProgress == 0) {
                iRgb = CustomColor.GREEN;
            } else if (i == 0 && this.currentGreenProgress == 0 && this.currentBlueProgress != 0) {
                iRgb = CustomColor.BLUE;
            }
            this.colorWheelView.setColor(iRgb, false);
            int selectorColor = this.colorWheelView.getSelectorColor(this.currentColor);
            DiyColor diyColor2 = this.diyColorList.get(this.currentDiyColorIndex);
            this.realm.beginTransaction();
            diyColor2.setColorValue(selectorColor);
            diyColor2.setDiyColorR(iRgb);
            this.realm.commitTransaction();
            this.diyColorAdapter.notifyDataSetChanged();
            this.currentDiyColorSelector.setBorderSelectorColor(-1);
        }
    }

    @Override // com.brgd.brblmesh.Main.Interface.ColourFragmentListener
    public void upDateRgbValue() {
        if (this.sceneNumber != -1) {
            int i = this.currentSceneColor;
            if (i == -3 || i == -2) {
                int i2 = this.currentColor;
                if (i2 == -3) {
                    return;
                }
                this.currentRedProgress = Color.red(i2);
                this.currentGreenProgress = Color.green(this.currentColor);
                this.currentBlueProgress = Color.blue(this.currentColor);
            } else {
                this.currentRedProgress = Color.red(i);
                this.currentGreenProgress = Color.green(this.currentSceneColor);
                this.currentBlueProgress = Color.blue(this.currentSceneColor);
            }
        } else {
            int i3 = this.currentColor;
            if (i3 == -3) {
                return;
            }
            this.currentRedProgress = Color.red(i3);
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int i;
        int i2;
        int i3;
        int id = view.getId();
        if (id == R.id.colour_Red_up) {
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
        if (id == R.id.colour_Red_down) {
            if (!(this.currentGreenProgress == 0 && this.currentBlueProgress == 0 && this.currentRedProgress == 1) && (i3 = this.currentRedProgress) > 0) {
                int i6 = i3 - 1;
                this.currentRedProgress = i6;
                this.redSeekBar.setProgress(i6);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.colour_Green_up) {
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
        if (id == R.id.colour_Green_down) {
            if (!(this.currentRedProgress == 0 && this.currentBlueProgress == 0 && this.currentGreenProgress == 1) && (i2 = this.currentGreenProgress) > 0) {
                int i9 = i2 - 1;
                this.currentGreenProgress = i9;
                this.greenSeekBar.setProgress(i9);
                setRgbColor();
                return;
            }
            return;
        }
        if (id == R.id.colour_Blue_up) {
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
        if (id == R.id.colour_Blue_down) {
            if (!(this.currentRedProgress == 0 && this.currentGreenProgress == 0 && this.currentBlueProgress == 1) && (i = this.currentBlueProgress) > 0) {
                int i12 = i - 1;
                this.currentBlueProgress = i12;
                this.blueSeekBar.setProgress(i12);
                setRgbColor();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColorTemp() {
        int i = (int) ((this.currentTempProgress / 100.0f) * 255.0f);
        this.currentWarmParam = i;
        this.currentColdParam = 255 - i;
        this.currentLightnessParam = (int) ((this.currentBrightnessProgress / 100.0f) * 127.0f);
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().singleTempCtrl(this.addr, this.currentLightnessParam, this.currentWarmParam, this.currentColdParam);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().groupTempCtrl(this.type, this.tempGroupId, this.currentLightnessParam, this.currentWarmParam, this.currentColdParam);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().groupTempCtrl(this.type, this.groupId, this.currentLightnessParam, this.currentWarmParam, this.currentColdParam);
        }
        if (this.sceneNumber != -1) {
            this.currentSceneColorTemp = this.currentTempProgress;
        } else {
            SharePreferenceUtils.put(this.deviceCtrlActivity, GlobalVariable.colorTempValue, Integer.valueOf(this.currentTempProgress));
        }
    }
}

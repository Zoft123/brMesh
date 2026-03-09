package com.brgd.brblmesh.Main.Fragment;

import android.content.Context;
import android.content.Intent;
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
import com.brgd.brblmesh.GeneralAdapter.DurationAdapter;
import com.brgd.brblmesh.GeneralAdapter.ModSpeedAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.ModSpeed;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.MyScrollView;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity;
import com.brgd.brblmesh.Main.Activity.RadarTimeSetActivity;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RadarSetFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    ImageView brightnessDownView;
    ImageView brightnessDownView1;
    ImageView brightnessDownView2;
    private SeekBar brightnessSeekBar;
    private SeekBar brightnessSeekBar1;
    private SeekBar brightnessSeekBar2;
    private String brightnessStr;
    private String brightnessStr1;
    private String brightnessStr2;
    TextView brightnessText;
    TextView brightnessText1;
    TextView brightnessText2;
    ImageView brightnessUpView;
    ImageView brightnessUpView1;
    ImageView brightnessUpView2;
    private TextView brightnessValue;
    private TextView brightnessValue1;
    private TextView brightnessValue2;
    public int currentBrightnessProgress;
    public int currentBrightnessProgress1;
    public int currentBrightnessProgress2;
    ModSpeed currentDuration;
    public int currentDurationIndex;
    ModSpeed currentLux;
    public int currentLuxIndex;
    DurationAdapter durationAdapter;
    private List<ModSpeed> durationList;
    RecyclerView durationRecyclerView;
    private ConstraintLayout enableOffLayout;
    public ImageView enableOnOff;
    private MyScrollView enableOnScrollView;
    ModSpeedAdapter luxAdapter;
    private List<ModSpeed> luxList;
    RecyclerView luxRecyclerView;
    private ImageView noBodyBottomLine;
    private TextView noBodyFixedText;
    public ImageView noBodyOnOff;
    private RadarDvCtrlActivity radarDvCtrlActivity;
    private Realm realm;
    ImageView toTimeSetBg;
    public int addr = -1;
    public int groupId = -1;
    public int tempGroupId = -1;
    public int type = -1;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarSetFragment.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.radarSet_toTimeSetBg) {
                Intent intent = new Intent(RadarSetFragment.this.radarDvCtrlActivity, (Class<?>) RadarTimeSetActivity.class);
                Bundle bundle = new Bundle();
                if (RadarSetFragment.this.addr != -1) {
                    bundle.putInt(GlobalVariable.ctrlAddr, RadarSetFragment.this.addr);
                    intent.putExtra(GlobalVariable.toCtrl, bundle);
                } else if (RadarSetFragment.this.groupId != -1 || RadarSetFragment.this.tempGroupId != -1) {
                    bundle.putInt(GlobalVariable.groupId, RadarSetFragment.this.groupId);
                    bundle.putInt(GlobalVariable.tempGroupId, RadarSetFragment.this.tempGroupId);
                    bundle.putInt(GlobalVariable.type, RadarSetFragment.this.type);
                    intent.putExtra(GlobalVariable.groupToCtrl, bundle);
                }
                RadarSetFragment.this.radarDvCtrlActivity.startActivity(intent);
                return;
            }
            if (id == R.id.radarSet_enableBg_onOff) {
                if (RadarSetFragment.this.enableOnOff.isSelected()) {
                    RadarSetFragment.this.enableOnOff.setSelected(false);
                    RadarSetFragment.this.enableOffLayout.setVisibility(0);
                    RadarSetFragment.this.enableOnScrollView.setVisibility(4);
                    return;
                } else {
                    RadarSetFragment.this.enableOnOff.setSelected(true);
                    RadarSetFragment.this.enableOffLayout.setVisibility(4);
                    RadarSetFragment.this.enableOnScrollView.setVisibility(0);
                    return;
                }
            }
            if (id == R.id.radarSet_noBodyOnOff) {
                if (RadarSetFragment.this.noBodyOnOff.isSelected()) {
                    RadarSetFragment.this.noBodyOnOff.setSelected(false);
                    RadarSetFragment.this.noBodyFixedText.setText(RadarSetFragment.this.getString(R.string.OffLight));
                    RadarSetFragment.this.noBodyBottomLine.setVisibility(0);
                    RadarSetFragment.this.brightnessText2.setVisibility(8);
                    RadarSetFragment.this.brightnessValue2.setVisibility(8);
                    RadarSetFragment.this.brightnessSeekBar2.setVisibility(8);
                    RadarSetFragment.this.brightnessUpView2.setVisibility(8);
                    RadarSetFragment.this.brightnessDownView2.setVisibility(8);
                    return;
                }
                RadarSetFragment.this.noBodyOnOff.setSelected(true);
                RadarSetFragment.this.noBodyFixedText.setText(RadarSetFragment.this.getString(R.string.Fixlightness));
                RadarSetFragment.this.noBodyBottomLine.setVisibility(8);
                RadarSetFragment.this.brightnessText2.setVisibility(0);
                RadarSetFragment.this.brightnessValue2.setVisibility(0);
                RadarSetFragment.this.brightnessSeekBar2.setVisibility(0);
                RadarSetFragment.this.brightnessUpView2.setVisibility(0);
                RadarSetFragment.this.brightnessDownView2.setVisibility(0);
                return;
            }
            if (id == R.id.radarSet_off_brightness_up) {
                if (RadarSetFragment.this.currentBrightnessProgress < 100) {
                    RadarSetFragment.this.currentBrightnessProgress++;
                }
                RadarSetFragment.this.brightnessSeekBar.setProgress(RadarSetFragment.this.currentBrightnessProgress - 1);
                return;
            }
            if (id == R.id.radarSet_off_brightness_down) {
                if (RadarSetFragment.this.currentBrightnessProgress > 1) {
                    RadarSetFragment.this.currentBrightnessProgress--;
                }
                RadarSetFragment.this.brightnessSeekBar.setProgress(RadarSetFragment.this.currentBrightnessProgress - 1);
                return;
            }
            if (id == R.id.radarSet_body_brightness_up) {
                if (RadarSetFragment.this.currentBrightnessProgress1 < 100) {
                    RadarSetFragment.this.currentBrightnessProgress1++;
                }
                RadarSetFragment.this.brightnessSeekBar1.setProgress(RadarSetFragment.this.currentBrightnessProgress1 - 1);
                return;
            }
            if (id == R.id.radarSet_body_brightness_down) {
                if (RadarSetFragment.this.currentBrightnessProgress1 > 1) {
                    RadarSetFragment.this.currentBrightnessProgress1--;
                }
                RadarSetFragment.this.brightnessSeekBar1.setProgress(RadarSetFragment.this.currentBrightnessProgress1 - 1);
                return;
            }
            if (id == R.id.radarSet_noBody_brightness_up) {
                if (RadarSetFragment.this.currentBrightnessProgress2 < 100) {
                    RadarSetFragment.this.currentBrightnessProgress2++;
                }
                RadarSetFragment.this.brightnessSeekBar2.setProgress(RadarSetFragment.this.currentBrightnessProgress2 - 1);
                return;
            }
            if (id == R.id.radarSet_noBody_brightness_down) {
                if (RadarSetFragment.this.currentBrightnessProgress2 > 1) {
                    RadarSetFragment.this.currentBrightnessProgress2--;
                }
                RadarSetFragment.this.brightnessSeekBar2.setProgress(RadarSetFragment.this.currentBrightnessProgress2 - 1);
            }
        }
    };

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.radarDvCtrlActivity = (RadarDvCtrlActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_radar_set, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
    }

    private void initView(View view) {
        Realm.init(GlobalApplication.getMyApplication().getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) view.findViewById(R.id.radarSet_toTimeSetBg);
        this.toTimeSetBg = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.radarSet_enableBg_onOff);
        this.enableOnOff = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.enableOffLayout = (ConstraintLayout) view.findViewById(R.id.radarSet_enableOffBg);
        this.brightnessText = (TextView) view.findViewById(R.id.radarSet_off_brightnessText);
        this.brightnessValue = (TextView) view.findViewById(R.id.radarSet_off_brightnessTextValue);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.radarSet_off_brightnessSeekBar);
        this.brightnessSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar.setMax(99);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.radarSet_off_brightness_up);
        this.brightnessUpView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.radarSet_off_brightness_down);
        this.brightnessDownView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.enableOnScrollView = (MyScrollView) view.findViewById(R.id.radarSet_enableScrollview);
        this.luxRecyclerView = (RecyclerView) view.findViewById(R.id.radarSet_LuxRecyclerView);
        this.luxList = new ArrayList();
        int i = 0;
        while (i < 5) {
            ModSpeed modSpeed = new ModSpeed();
            i++;
            modSpeed.value = i;
            modSpeed.isSelect = false;
            this.luxList.add(modSpeed);
        }
        this.luxRecyclerView.setLayoutManager(new MyLinearLayoutManager(this.radarDvCtrlActivity, 0, false));
        ModSpeedAdapter modSpeedAdapter = new ModSpeedAdapter(this.radarDvCtrlActivity, this.luxList);
        this.luxAdapter = modSpeedAdapter;
        this.luxRecyclerView.setAdapter(modSpeedAdapter);
        this.luxAdapter.setOnItemClickListener(new ModSpeedAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarSetFragment$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.ModSpeedAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i2) {
                this.f$0.lambda$initView$0(view2, i2);
            }
        });
        this.durationRecyclerView = (RecyclerView) view.findViewById(R.id.radarSet_durationRecyclerView);
        this.durationList = new ArrayList();
        int i2 = 0;
        while (i2 < 7) {
            ModSpeed modSpeed2 = new ModSpeed();
            i2++;
            modSpeed2.value = i2;
            modSpeed2.isSelect = false;
            this.durationList.add(modSpeed2);
        }
        this.durationRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        DurationAdapter durationAdapter = new DurationAdapter(this.radarDvCtrlActivity, this.durationList);
        this.durationAdapter = durationAdapter;
        this.durationRecyclerView.setAdapter(durationAdapter);
        this.durationAdapter.setOnItemClickListener(new DurationAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarSetFragment$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralAdapter.DurationAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i3) {
                this.f$0.lambda$initView$1(view2, i3);
            }
        });
        this.brightnessText1 = (TextView) view.findViewById(R.id.radarSet_body_brightnessText);
        this.brightnessValue1 = (TextView) view.findViewById(R.id.radarSet_body_brightnessTextValue);
        SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.radarSet_body_brightnessSeekBar);
        this.brightnessSeekBar1 = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar1.setMax(99);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.radarSet_body_brightness_up);
        this.brightnessUpView1 = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.radarSet_body_brightness_down);
        this.brightnessDownView1 = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
        this.noBodyFixedText = (TextView) view.findViewById(R.id.radarSet_noBodyFixText);
        ImageView imageView7 = (ImageView) view.findViewById(R.id.radarSet_noBodyOnOff);
        this.noBodyOnOff = imageView7;
        imageView7.setOnClickListener(this.disDoubleClickListener);
        this.noBodyBottomLine = (ImageView) view.findViewById(R.id.radarSet_noBodyBottomLine);
        this.brightnessText2 = (TextView) view.findViewById(R.id.radarSet_noBody_brightnessText);
        this.brightnessValue2 = (TextView) view.findViewById(R.id.radarSet_noBody_brightnessTextValue);
        SeekBar seekBar3 = (SeekBar) view.findViewById(R.id.radarSet_noBody_brightnessSeekBar);
        this.brightnessSeekBar2 = seekBar3;
        seekBar3.setOnSeekBarChangeListener(this);
        this.brightnessSeekBar2.setMax(99);
        ImageView imageView8 = (ImageView) view.findViewById(R.id.radarSet_noBody_brightness_up);
        this.brightnessUpView2 = imageView8;
        imageView8.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView9 = (ImageView) view.findViewById(R.id.radarSet_noBody_brightness_down);
        this.brightnessDownView2 = imageView9;
        imageView9.setOnClickListener(this.disDoubleClickListener);
        handleData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        this.currentLux.isSelect = false;
        this.currentLuxIndex = i;
        ModSpeed modSpeed = this.luxList.get(i);
        this.currentLux = modSpeed;
        modSpeed.isSelect = true;
        this.luxAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view, int i) {
        this.currentDuration.isSelect = false;
        this.currentDurationIndex = i;
        ModSpeed modSpeed = this.durationList.get(i);
        this.currentDuration = modSpeed;
        modSpeed.isSelect = true;
        this.durationAdapter.notifyDataSetChanged();
    }

    private void handleData() {
        if (!((Boolean) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_ENABLE_STR, false)).booleanValue()) {
            this.enableOnScrollView.setVisibility(4);
        } else {
            this.enableOnScrollView.setVisibility(0);
            this.enableOnOff.setSelected(true);
            this.enableOffLayout.setVisibility(4);
        }
        this.currentBrightnessProgress = 100;
        if (((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_DISABLE_LIGHTNESS, 0)).intValue() != 0) {
            this.currentBrightnessProgress = ((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_DISABLE_LIGHTNESS, 0)).intValue();
        }
        this.brightnessSeekBar.setProgress(this.currentBrightnessProgress - 1);
        this.currentBrightnessProgress1 = 100;
        if (((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_ENABLE_LIGHTNESS, 0)).intValue() != 0) {
            this.currentBrightnessProgress1 = ((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_ENABLE_LIGHTNESS, 0)).intValue();
        }
        this.brightnessSeekBar1.setProgress(this.currentBrightnessProgress1 - 1);
        this.currentBrightnessProgress2 = 100;
        if (((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_NOBODY_ACTION_STR, 0)).intValue() != 0) {
            this.currentBrightnessProgress2 = ((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_NOBODY_ACTION_STR, 0)).intValue();
            this.noBodyFixedText.setText(getString(R.string.Fixlightness));
            this.noBodyOnOff.setSelected(true);
            this.noBodyBottomLine.setVisibility(8);
            this.brightnessText2.setVisibility(0);
            this.brightnessValue2.setVisibility(0);
            this.brightnessSeekBar2.setVisibility(0);
            this.brightnessUpView2.setVisibility(0);
            this.brightnessDownView2.setVisibility(0);
        }
        this.brightnessSeekBar2.setProgress(this.currentBrightnessProgress2 - 1);
        this.currentLuxIndex = 0;
        if (((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_LUX_INDEX, 0)).intValue() != 0) {
            this.currentLuxIndex = ((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_LUX_INDEX, 0)).intValue() - 1;
        }
        ModSpeed modSpeed = this.luxList.get(this.currentLuxIndex);
        this.currentLux = modSpeed;
        modSpeed.isSelect = true;
        this.luxAdapter.notifyDataSetChanged();
        this.currentDurationIndex = 0;
        if (((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_ENABLE_TIME_INDEX, 0)).intValue() != 0) {
            this.currentDurationIndex = ((Integer) SharePreferenceUtils.get(this.radarDvCtrlActivity, GlobalVariable.RADAR_ENABLE_TIME_INDEX, 0)).intValue() - 1;
        }
        ModSpeed modSpeed2 = this.durationList.get(this.currentDurationIndex);
        this.currentDuration = modSpeed2;
        modSpeed2.isSelect = true;
        this.durationAdapter.notifyDataSetChanged();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress + "%";
            this.brightnessStr = str;
            this.brightnessValue.setText(str);
            return;
        }
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress1 = seekBar.getProgress() + 1;
            String str2 = this.currentBrightnessProgress1 + "%";
            this.brightnessStr1 = str2;
            this.brightnessValue1.setText(str2);
            return;
        }
        if (seekBar == this.brightnessSeekBar2) {
            this.currentBrightnessProgress2 = seekBar.getProgress() + 1;
            String str3 = this.currentBrightnessProgress2 + "%";
            this.brightnessStr2 = str3;
            this.brightnessValue2.setText(str3);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == this.brightnessSeekBar) {
            this.currentBrightnessProgress = seekBar.getProgress() + 1;
            String str = this.currentBrightnessProgress + "%";
            this.brightnessStr = str;
            this.brightnessValue.setText(str);
            return;
        }
        if (seekBar == this.brightnessSeekBar1) {
            this.currentBrightnessProgress1 = seekBar.getProgress() + 1;
            String str2 = this.currentBrightnessProgress1 + "%";
            this.brightnessStr1 = str2;
            this.brightnessValue1.setText(str2);
            return;
        }
        if (seekBar == this.brightnessSeekBar2) {
            this.currentBrightnessProgress2 = seekBar.getProgress() + 1;
            String str3 = this.currentBrightnessProgress2 + "%";
            this.brightnessStr2 = str3;
            this.brightnessValue2.setText(str3);
        }
    }
}

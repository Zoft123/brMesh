package com.brgd.brblmesh.Main.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.camera.video.AudioStats;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralClass.AudioTool;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyService;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.WaveView.WaveView;
import com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity;
import com.brgd.brblmesh.Main.Interface.RhythmFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class RhythmFragment extends Fragment implements RhythmFragmentListener {
    private DeviceCtrlActivity deviceCtrlActivity;
    private Intent foregroundIntent;
    public boolean isGetPermission;
    private Timer mTimer;
    private ImageView microPhoneBtn;
    private List<Integer> modDiyColorList1;
    private List<Integer> modDiyColorList2;
    private List<Integer> modDiyColorList3;
    private List<Integer> modDiyColorList4;
    private List<Integer> modDiyColorList5;
    private Realm realm;
    private WaveView waveView;
    public int addr = -1;
    public int groupId = -1;
    public int tempGroupId = -1;
    public int type = -1;
    public boolean hasRequestMC = false;
    private final AudioTool audioTool = new AudioTool();
    public int currentThemeIndex = 0;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RhythmFragment.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.rhythm_logo) {
                if (Tool.checkMicroPhonePermission()) {
                    if (RhythmFragment.this.microPhoneBtn.isSelected()) {
                        RhythmFragment.this.stopVoice();
                        return;
                    } else {
                        RhythmFragment.this.startVoice();
                        return;
                    }
                }
                RhythmFragment.this.deviceCtrlActivity.mcPermissionDialog();
            }
        }
    };
    private int speed = 2000;
    private int lightness = 65;
    private long currentTime = 0;

    @Override // com.brgd.brblmesh.Main.Interface.RhythmFragmentListener
    public void microPhoneOK() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.deviceCtrlActivity = (DeviceCtrlActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_rhythm, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!Tool.checkMicroPhonePermission()) {
            stopVoice();
        }
        if (this.deviceCtrlActivity.currentTabPosition == 3) {
            this.deviceCtrlActivity.checkMicroPhone();
            refreshDIYData();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
        Intent intent = this.foregroundIntent;
        if (intent != null) {
            this.deviceCtrlActivity.stopService(intent);
            this.foregroundIntent = null;
        }
    }

    private void initView(View view) {
        Realm.init(this.deviceCtrlActivity.getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) view.findViewById(R.id.rhythm_logo);
        this.microPhoneBtn = imageView;
        imageView.setSelected(false);
        this.microPhoneBtn.setOnClickListener(this.disDoubleClickListener);
        this.waveView = (WaveView) view.findViewById(R.id.rhythm_waveView);
        this.currentThemeIndex = ((Integer) SharePreferenceUtils.get(this.deviceCtrlActivity.getApplicationContext(), GlobalVariable.rhythmTheme, -1)).intValue();
        this.modDiyColorList1 = new ArrayList();
        this.modDiyColorList2 = new ArrayList();
        this.modDiyColorList3 = new ArrayList();
        this.modDiyColorList4 = new ArrayList();
        this.modDiyColorList5 = new ArrayList();
    }

    private void refreshDIYData() {
        for (int i = 7; i < 12; i++) {
            RealmResults<ModDiyColor> realmResultsFindAll = this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(i)).findAll();
            switch (i) {
                case 7:
                    this.modDiyColorList1.clear();
                    for (ModDiyColor modDiyColor : realmResultsFindAll) {
                        if (modDiyColor.getDiyColorR() != -2 && modDiyColor.getDiyColorR() != 0) {
                            this.modDiyColorList1.add(Integer.valueOf(modDiyColor.getDiyColorR()));
                        } else if (modDiyColor.getDiyColor() != -2) {
                            this.modDiyColorList1.add(Integer.valueOf(modDiyColor.getDiyColor()));
                        }
                    }
                    break;
                case 8:
                    this.modDiyColorList2.clear();
                    for (ModDiyColor modDiyColor2 : realmResultsFindAll) {
                        if (modDiyColor2.getDiyColorR() != -2 && modDiyColor2.getDiyColorR() != 0) {
                            this.modDiyColorList2.add(Integer.valueOf(modDiyColor2.getDiyColorR()));
                        } else if (modDiyColor2.getDiyColor() != -2) {
                            this.modDiyColorList2.add(Integer.valueOf(modDiyColor2.getDiyColor()));
                        }
                    }
                    break;
                case 9:
                    this.modDiyColorList3.clear();
                    for (ModDiyColor modDiyColor3 : realmResultsFindAll) {
                        if (modDiyColor3.getDiyColorR() != -2 && modDiyColor3.getDiyColorR() != 0) {
                            this.modDiyColorList3.add(Integer.valueOf(modDiyColor3.getDiyColorR()));
                        } else if (modDiyColor3.getDiyColor() != -2) {
                            this.modDiyColorList3.add(Integer.valueOf(modDiyColor3.getDiyColor()));
                        }
                    }
                    break;
                case 10:
                    this.modDiyColorList4.clear();
                    for (ModDiyColor modDiyColor4 : realmResultsFindAll) {
                        if (modDiyColor4.getDiyColorR() != -2 && modDiyColor4.getDiyColorR() != 0) {
                            this.modDiyColorList4.add(Integer.valueOf(modDiyColor4.getDiyColorR()));
                        } else if (modDiyColor4.getDiyColor() != -2) {
                            this.modDiyColorList4.add(Integer.valueOf(modDiyColor4.getDiyColor()));
                        }
                    }
                    break;
                case 11:
                    this.modDiyColorList5.clear();
                    for (ModDiyColor modDiyColor5 : realmResultsFindAll) {
                        if (modDiyColor5.getDiyColorR() != -2 && modDiyColor5.getDiyColorR() != 0) {
                            this.modDiyColorList5.add(Integer.valueOf(modDiyColor5.getDiyColorR()));
                        } else if (modDiyColor5.getDiyColor() != -2) {
                            this.modDiyColorList5.add(Integer.valueOf(modDiyColor5.getDiyColor()));
                        }
                    }
                    break;
            }
        }
    }

    private void startTimer() {
        stopTimer();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Fragment.RhythmFragment.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                RhythmFragment.this.handleVoice();
            }
        }, 0L, 100L);
    }

    private void stopTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleVoice() {
        int i;
        if (this.audioTool.volume <= 15.0d) {
            i = 3600000;
        } else {
            if (this.audioTool.volume > 15.0d && this.audioTool.volume <= 40.0d) {
                this.waveView.SPEED = 0.8f;
                this.waveView.SPEEDX = 1.2f;
                this.lightness = 120;
            } else if (this.audioTool.volume > 40.0d && this.audioTool.volume <= 45.0d) {
                this.waveView.SPEED = 2.8f;
                this.waveView.SPEEDX = 2.2f;
                this.lightness = 65;
                i = 1600;
            } else if (this.audioTool.volume > 45.0d && this.audioTool.volume <= 50.0d) {
                this.waveView.SPEED = 3.8f;
                this.waveView.SPEEDX = 3.2f;
                this.lightness = 120;
                i = 1000;
            } else if (this.audioTool.volume > 50.0d && this.audioTool.volume <= 55.0d) {
                this.waveView.SPEED = 5.8f;
                this.waveView.SPEEDX = 5.2f;
                this.lightness = 65;
                i = 600;
            } else if (this.audioTool.volume > 55.0d && this.audioTool.volume <= 60.0d) {
                this.waveView.SPEED = 6.8f;
                this.waveView.SPEEDX = 6.2f;
                this.lightness = 120;
                i = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            } else if (this.audioTool.volume > 60.0d) {
                this.waveView.SPEED = 8.8f;
                this.waveView.SPEEDX = 8.2f;
                this.lightness = 65;
                i = 100;
            }
            i = 2000;
        }
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long j = timeInMillis - this.currentTime;
        int i2 = this.speed;
        if (j >= i2 || i < i2) {
            this.currentTime = timeInMillis;
            this.speed = i;
            if (this.audioTool.volume > AudioStats.AUDIO_AMPLITUDE_NONE) {
                sendRhythmCtrlCmd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startVoice() {
        this.microPhoneBtn.setSelected(true);
        this.audioTool.getNoiseLevel();
        startTimer();
        this.waveView.startWaveLine();
        if (this.foregroundIntent == null) {
            this.foregroundIntent = new Intent(this.deviceCtrlActivity, (Class<?>) MyService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                this.deviceCtrlActivity.startForegroundService(this.foregroundIntent);
            } else {
                this.deviceCtrlActivity.startService(this.foregroundIntent);
            }
        }
    }

    @Override // com.brgd.brblmesh.Main.Interface.RhythmFragmentListener
    public void stopVoice() {
        this.microPhoneBtn.setSelected(false);
        this.audioTool.stopNoiseLevel();
        stopTimer();
        this.waveView.stopWaveLine();
        Intent intent = this.foregroundIntent;
        if (intent != null) {
            this.deviceCtrlActivity.stopService(intent);
            this.foregroundIntent = null;
        }
    }

    private void sendRhythmCtrlCmd() {
        int randomColor1;
        Tool.getRandomColor();
        int i = this.currentThemeIndex;
        if (i == 1) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList1);
        } else if (i == 2) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList2);
        } else if (i == 3) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList3);
        } else if (i == 4) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList4);
        } else if (i == 5) {
            randomColor1 = Tool.getRandomColor1(this.modDiyColorList5);
        } else {
            randomColor1 = Tool.getRandomColor();
        }
        if (randomColor1 == -1) {
            return;
        }
        int iRed = Color.red(randomColor1);
        int iGreen = Color.green(randomColor1);
        int iBlue = Color.blue(randomColor1);
        int i2 = this.addr;
        if (i2 != -1) {
            BLSBleLight.controlWithDevice(i2, GlobalBluetooth.getInstance().rgbCommand(true, this.lightness, iRed, iGreen, iBlue), 100);
            return;
        }
        int i3 = this.tempGroupId;
        if (i3 != -1) {
            BLSBleLight.groupControlWithType(this.type, i3, GlobalBluetooth.getInstance().rgbCommand(true, this.lightness, iRed, iGreen, iBlue), 100);
            return;
        }
        int i4 = this.groupId;
        if (i4 != -1) {
            BLSBleLight.groupControlWithType(this.type, i4, GlobalBluetooth.getInstance().rgbCommand(true, this.lightness, iRed, iGreen, iBlue), 100);
        }
    }
}

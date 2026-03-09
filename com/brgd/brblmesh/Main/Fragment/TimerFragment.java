package com.brgd.brblmesh.Main.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo;
import cn.com.broadlink.blelight.bean.BLETimeInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import cn.com.broadlink.blelight.interfaces.OnDevColorTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevTimerCallback;
import com.brgd.brblmesh.GeneralAdapter.TimerAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalThreadPools;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity;
import com.brgd.brblmesh.Main.Activity.TimerSetActivity;
import com.brgd.brblmesh.Main.Interface.TimerFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class TimerFragment extends Fragment implements TimerFragmentListener {
    private ArrayList<BLERgbcwTimerInfo> bleRgbcwTimerInfos;
    private Timer currentTimer;
    private DeviceCtrlActivity deviceCtrlActivity;
    private boolean isEnable;
    private boolean isNot65V;
    public boolean isSupportTimerStatus;
    ConstraintLayout loadingLayout;
    private BLETimerAllInfo mBleTimerAllInfo;
    private java.util.Timer mTimer;
    private Dialog modifyDialog;
    private Realm realm;
    private TimerAdapter timerAdapter;
    private List<Timer> timerList;
    RecyclerView timerRecyclerView;
    private int type;
    public int addr = -1;
    private final int UPDATE_TIMER = 101;
    private final int UPDATE_NEW_TIMER = 102;
    private final int TIMEOUT = 103;
    private boolean isModifying = false;
    private boolean isReceiveModifyCallback = false;
    public boolean isSupportSleepMod = false;
    private int timeout = 0;
    private final OnDevTimerCallback mOnDevTimerCallback = new OnDevTimerCallback() { // from class: com.brgd.brblmesh.Main.Fragment.TimerFragment.3
        @Override // cn.com.broadlink.blelight.interfaces.OnDevTimerCallback
        public void onCallback(int i, BLETimerAllInfo bLETimerAllInfo) {
            if (bLETimerAllInfo.addr != TimerFragment.this.addr) {
                return;
            }
            TimerFragment.this.stopReceiveService();
            TimerFragment.this.stopTimer();
            TimerFragment.this.myHandler.sendMessage(TimerFragment.this.myHandler.obtainMessage(101, bLETimerAllInfo));
            if (TimerFragment.this.isModifying && bLETimerAllInfo.schedInfoList.get(TimerFragment.this.currentTimer.getIndexNum()).enable == TimerFragment.this.isEnable) {
                TimerFragment.this.isModifying = false;
                TimerFragment.this.isReceiveModifyCallback = true;
            }
        }
    };
    private final OnDevColorTimerCallback mOnDevColorTimerCallback = new OnDevColorTimerCallback() { // from class: com.brgd.brblmesh.Main.Fragment.TimerFragment.4
        @Override // cn.com.broadlink.blelight.interfaces.OnDevColorTimerCallback
        public void onCallback(int i, ArrayList<BLERgbcwTimerInfo> arrayList) {
            TimerFragment.this.stopReceiveService();
            TimerFragment.this.stopTimer();
            TimerFragment.this.myHandler.sendMessage(TimerFragment.this.myHandler.obtainMessage(102, i, i, arrayList));
            if (TimerFragment.this.isModifying && arrayList.get(TimerFragment.this.currentTimer.getIndexNum()).time.enable == TimerFragment.this.isEnable) {
                TimerFragment.this.isModifying = false;
                TimerFragment.this.isReceiveModifyCallback = true;
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.deviceCtrlActivity = (DeviceCtrlActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_timer, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        int i;
        super.onResume();
        int i2 = this.type;
        if (((i2 == 43049 || i2 == 43051) && this.deviceCtrlActivity.currentTabPosition == 1 && !this.isSupportSleepMod) || ((((i = this.type) == 43049 || i == 43051) && this.deviceCtrlActivity.currentTabPosition == 2 && this.isSupportSleepMod) || this.deviceCtrlActivity.currentTabPosition == 4)) {
            if (Tool.isAPI31()) {
                if (Tool.checkBleCASPermission() && Tool.checkLocationPermission()) {
                    this.deviceCtrlActivity.getLocationPermission();
                    return;
                }
                return;
            }
            if (Tool.checkLocationPermission()) {
                this.deviceCtrlActivity.getLocationPermission();
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        stopReceiveService();
        stopTimer();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        stopReceiveService();
        stopTimer();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initView(View view) {
        Realm.init(this.deviceCtrlActivity.getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        BleDevice bleDevice = (BleDevice) defaultInstance.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
        if (bleDevice != null) {
            this.type = bleDevice.getType();
            this.isNot65V = Tool.isNot65V(bleDevice.getVersion());
        }
        this.loadingLayout = (ConstraintLayout) view.findViewById(R.id.timer_loading_layout1);
        this.timerRecyclerView = (RecyclerView) view.findViewById(R.id.timer_list_recyclerView);
        this.timerRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        this.timerList = new ArrayList();
        TimerAdapter timerAdapter = new TimerAdapter(getActivity(), this.timerList);
        this.timerAdapter = timerAdapter;
        this.timerRecyclerView.setAdapter(timerAdapter);
        this.timerAdapter.setOnItemClickListener(new TimerAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.TimerFragment.1
            @Override // com.brgd.brblmesh.GeneralAdapter.TimerAdapter.OnItemClickListener
            public void OnItemLongClick(View view2, int i) {
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.TimerAdapter.OnItemClickListener
            public void OnItemClick(View view2, int i) {
                Timer timer = (Timer) TimerFragment.this.timerList.get(i);
                Intent intent = new Intent(TimerFragment.this.getActivity(), (Class<?>) TimerSetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.tdAddr, TimerFragment.this.addr);
                bundle.putInt(GlobalVariable.timerIndex, timer.getIndexNum());
                if (TimerFragment.this.isSupportTimerStatus && TimerFragment.this.isNot65V) {
                    bundle.putParcelableArrayList(GlobalVariable.timerAllInfo, TimerFragment.this.bleRgbcwTimerInfos);
                } else {
                    bundle.putParcelable(GlobalVariable.timerAllInfo, TimerFragment.this.mBleTimerAllInfo);
                }
                bundle.putBoolean(GlobalVariable.timerStatus, TimerFragment.this.isSupportTimerStatus);
                intent.putExtra(GlobalVariable.checkTimer, bundle);
                TimerFragment.this.startActivity(intent);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.TimerAdapter.OnItemClickListener
            public void OnActionClick(View view2, int i) {
                if (TimerFragment.this.modifyDialog == null) {
                    TimerFragment timerFragment = TimerFragment.this;
                    timerFragment.modifyDialog = loadDialogUtils.createLoadingDialog(timerFragment.getActivity(), R.string.Loading);
                }
                TimerFragment timerFragment2 = TimerFragment.this;
                timerFragment2.currentTimer = (Timer) timerFragment2.timerList.get(i);
                TimerFragment.this.isEnable = !r3.currentTimer.isEnable();
                if (TimerFragment.this.isSupportTimerStatus && TimerFragment.this.isNot65V) {
                    ((BLERgbcwTimerInfo) TimerFragment.this.bleRgbcwTimerInfos.get(TimerFragment.this.currentTimer.getIndexNum())).time.enable = TimerFragment.this.isEnable ? 1 : 0;
                } else {
                    TimerFragment.this.mBleTimerAllInfo.schedInfoList.get(TimerFragment.this.currentTimer.getIndexNum()).enable = TimerFragment.this.isEnable ? 1 : 0;
                    TimerFragment.this.mBleTimerAllInfo.lcTime = new BLETimeLcInfo();
                    TimerFragment.this.mBleTimerAllInfo.lcTime.hour = Integer.parseInt(Tool.getCurrentHour());
                    TimerFragment.this.mBleTimerAllInfo.lcTime.min = Integer.parseInt(Tool.getCurrentMinute());
                }
                TimerFragment.this.startReceiveService();
                TimerFragment.this.isModifying = true;
                TimerFragment.this.isReceiveModifyCallback = false;
                TimerFragment.this.modifyTimer();
            }
        });
    }

    private void refreshData() {
        this.timerList.clear();
        RealmResults realmResultsFindAll = this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findAll();
        if (!realmResultsFindAll.isEmpty()) {
            this.timerList.addAll(realmResultsFindAll);
        }
        this.timerAdapter.notifyDataSetChanged();
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
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Fragment.TimerFragment.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (TimerFragment.this.timeout > 12) {
                    TimerFragment.this.stopReceiveService();
                    TimerFragment.this.stopTimer();
                    TimerFragment.this.myHandler.sendMessage(TimerFragment.this.myHandler.obtainMessage(103));
                } else if (TimerFragment.this.addr != -1) {
                    if (TimerFragment.this.isSupportTimerStatus && TimerFragment.this.isNot65V) {
                        GlobalBluetooth.getInstance().controlColorTimerGet(TimerFragment.this.addr);
                    } else {
                        GlobalBluetooth.getInstance().timerQueryWithDevice(TimerFragment.this.addr);
                    }
                }
                TimerFragment.this.timeout++;
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
    public void modifyTimer() {
        GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.TimerFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$modifyTimer$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$modifyTimer$0() {
        if (sendModifyTimer()) {
            return;
        }
        stopReceiveService();
        MyHandler myHandler = this.myHandler;
        myHandler.sendMessage(myHandler.obtainMessage(103));
    }

    private boolean sendModifyTimer() {
        for (int i = 0; i < 15; i++) {
            if (this.isSupportTimerStatus && this.isNot65V) {
                GlobalBluetooth.getInstance().controlColorTimerSet(this.addr, this.bleRgbcwTimerInfos);
            } else {
                GlobalBluetooth.getInstance().timerSettingWithDevice(this.addr, this.mBleTimerAllInfo);
            }
            for (int i2 = 0; i2 < 9; i2++) {
                SystemClock.sleep(100L);
                if (this.isReceiveModifyCallback) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTimerList(BLETimerAllInfo bLETimerAllInfo) {
        if (bLETimerAllInfo.addr == this.addr) {
            this.mBleTimerAllInfo = bLETimerAllInfo;
            ArrayList<BLETimeInfo> arrayList = bLETimerAllInfo.schedInfoList;
            for (int i = 0; i < arrayList.size(); i++) {
                BLETimeInfo bLETimeInfo = arrayList.get(i);
                Timer timer = (Timer) this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("indexNum", Integer.valueOf(i)).findFirst();
                this.realm.beginTransaction();
                if (timer == null) {
                    timer = (Timer) this.realm.createObject(Timer.class);
                    timer.setIndexNum(i);
                    timer.setAddr(this.addr);
                }
                timer.setHour(bLETimeInfo.hour);
                timer.setMinute(bLETimeInfo.min);
                timer.setAction(bLETimeInfo.onoff);
                boolean z = true;
                timer.setEnable(bLETimeInfo.enable > 0);
                if (bLETimeInfo.repeat <= 0) {
                    z = false;
                }
                timer.setRepeat(z);
                this.realm.commitTransaction();
            }
            refreshData();
            this.loadingLayout.setVisibility(4);
            Dialog dialog = this.modifyDialog;
            if (dialog != null) {
                loadDialogUtils.closeDialog(dialog);
                this.modifyDialog = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNewTimerList(int i, ArrayList<BLERgbcwTimerInfo> arrayList) {
        if (i == this.addr) {
            this.bleRgbcwTimerInfos = arrayList;
            for (int i2 = 0; i2 < this.bleRgbcwTimerInfos.size(); i2++) {
                BLERgbcwTimerInfo bLERgbcwTimerInfo = this.bleRgbcwTimerInfos.get(i2);
                Timer timer = (Timer) this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("indexNum", Integer.valueOf(i2)).findFirst();
                this.realm.beginTransaction();
                if (timer == null) {
                    timer = (Timer) this.realm.createObject(Timer.class);
                    timer.setIndexNum(i2);
                    timer.setAddr(this.addr);
                }
                timer.setHour(bLERgbcwTimerInfo.time.hour);
                timer.setMinute(bLERgbcwTimerInfo.time.min);
                timer.setAction(bLERgbcwTimerInfo.time.onoff);
                boolean z = true;
                timer.setRepeat(bLERgbcwTimerInfo.time.repeat > 0);
                timer.setBrightness(Math.round((bLERgbcwTimerInfo.bri / 127.0f) * 100.0f));
                if (bLERgbcwTimerInfo.time.type == 0) {
                    timer.setRed(bLERgbcwTimerInfo.red);
                    timer.setGreen(bLERgbcwTimerInfo.green);
                } else if (this.type == 43049) {
                    timer.setWarm(0);
                    timer.setCold(0);
                } else {
                    timer.setWarm(bLERgbcwTimerInfo.green);
                    timer.setCold(bLERgbcwTimerInfo.red);
                }
                if (bLERgbcwTimerInfo.time.enable <= 0) {
                    z = false;
                }
                timer.setEnable(z);
                this.realm.commitTransaction();
            }
            refreshData();
            this.loadingLayout.setVisibility(4);
            Dialog dialog = this.modifyDialog;
            if (dialog != null) {
                loadDialogUtils.closeDialog(dialog);
                this.modifyDialog = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeout() {
        Dialog dialog = this.modifyDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.modifyDialog = null;
        }
        GlobalToast.showText(GlobalApplication.getMyApplication(), R.string.tryAgain, 1);
    }

    @Override // com.brgd.brblmesh.Main.Interface.TimerFragmentListener
    public void locationPermissionIsOK() {
        startReceiveService();
        if (this.modifyDialog == null) {
            this.modifyDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
        }
        this.timeout = 0;
        startTimer();
        if (this.addr != -1) {
            if (this.isSupportTimerStatus && this.isNot65V) {
                GlobalBluetooth.getInstance().controlColorTimerGet(this.addr);
            } else {
                GlobalBluetooth.getInstance().timerQueryWithDevice(this.addr);
            }
        }
        if (this.timerList.isEmpty()) {
            this.loadingLayout.setVisibility(0);
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            TimerFragment timerFragment = (TimerFragment) this.weakReference.get();
            Objects.requireNonNull(timerFragment);
            if (101 == message.what) {
                timerFragment.updateTimerList((BLETimerAllInfo) message.obj);
                return;
            }
            Objects.requireNonNull(timerFragment);
            if (103 == message.what) {
                timerFragment.handleTimeout();
                return;
            }
            Objects.requireNonNull(timerFragment);
            if (102 == message.what) {
                timerFragment.updateNewTimerList(message.arg1, (ArrayList) message.obj);
            }
        }
    }
}

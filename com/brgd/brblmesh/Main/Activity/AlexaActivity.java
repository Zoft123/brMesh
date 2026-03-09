package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.AlexaDevice;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import com.cunoraz.gifview.library.GifView;
import io.realm.Realm;
import io.realm.RealmResults;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AlexaActivity extends FatherActivity {
    private static final int TIP = 102;
    private static final int UPDATE = 101;
    TextView activeView;
    ImageView backView;
    private AlexaDeviceAdapter configAdapter;
    private ConstraintLayout configLayout;
    private List<AlexaDevice> configList;
    RecyclerView configRecyclerView;
    private ImageView configView;
    private ImageView confirmBtn;
    private TextView confirmTextView;
    GifView gifView;
    private Dialog loadingDialog;
    ImageView okView1;
    Realm realm;
    private List<AlexaDevice> selectList;
    private ConstraintLayout showTipLayout1;
    TextView titleView;
    ImageView video;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AlexaActivity.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.alexa_back) {
                AlexaActivity.this.finish();
                return;
            }
            if (id == R.id.alexa_video) {
                AlexaActivity.this.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/KVlRBSFhSrg")), "Browse"));
                return;
            }
            if (id == R.id.alexa_active) {
                if (AlexaActivity.this.selectList.isEmpty()) {
                    GlobalToast.showText(AlexaActivity.this.getApplicationContext(), R.string.selectDevice, 0);
                    return;
                } else {
                    AlexaActivity.this.startConfigAlexaState();
                    return;
                }
            }
            if (id == R.id.alexa_confirm_text) {
                if (AlexaActivity.this.confirmBtn.isSelected()) {
                    AlexaActivity.this.confirmBtn.setSelected(false);
                    AlexaActivity.this.confirmTextView.setTextColor(ContextCompat.getColor(AlexaActivity.this.getApplicationContext(), R.color.colorConfirmGray));
                    AlexaActivity.this.configView.setSelected(false);
                    return;
                } else {
                    AlexaActivity.this.confirmBtn.setSelected(true);
                    AlexaActivity.this.confirmTextView.setTextColor(ContextCompat.getColor(AlexaActivity.this.getApplicationContext(), R.color.colorSelect));
                    AlexaActivity.this.configView.setSelected(true);
                    return;
                }
            }
            if (id == R.id.alexa_config_in) {
                if (AlexaActivity.this.configView.isSelected()) {
                    AlexaActivity.this.configLayout.setVisibility(8);
                    AlexaActivity.this.activeView.setVisibility(0);
                    AlexaActivity.this.titleView.setText(R.string.alexa);
                    return;
                }
                GlobalToast.showText(AlexaActivity.this.getApplicationContext(), R.string.confirmLight, 0);
                return;
            }
            if (id == R.id.new_show_save1) {
                AlexaActivity.this.finish();
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alexa);
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
        ImageView imageView = (ImageView) findViewById(R.id.alexa_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.titleView = (TextView) findViewById(R.id.alexa_title);
        ImageView imageView2 = (ImageView) findViewById(R.id.alexa_video);
        this.video = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.alexa_active);
        this.activeView = textView;
        textView.setVisibility(4);
        this.activeView.setOnClickListener(this.disDoubleClickListener);
        this.configLayout = (ConstraintLayout) findViewById(R.id.alexa_teach_layout);
        GifView gifView = (GifView) findViewById(R.id.alexa_teach_gif);
        this.gifView = gifView;
        gifView.setGifResource(R.drawable.light_gif);
        TextView textView2 = (TextView) findViewById(R.id.alexa_confirm_text);
        this.confirmTextView = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView3 = (ImageView) findViewById(R.id.alexa_confirm_bt);
        this.confirmBtn = imageView3;
        imageView3.setSelected(false);
        ImageView imageView4 = (ImageView) findViewById(R.id.alexa_config_in);
        this.configView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.configView.setSelected(false);
        this.selectList = new ArrayList();
        initConfigView();
        refreshDevices();
        this.showTipLayout1 = (ConstraintLayout) findViewById(R.id.new_show_View1);
        ImageView imageView5 = (ImageView) findViewById(R.id.new_show_save1);
        this.okView1 = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
    }

    private void initConfigView() {
        this.configList = new ArrayList();
        this.configRecyclerView = (RecyclerView) findViewById(R.id.alexa_config_recyclerView);
        this.configRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        AlexaDeviceAdapter alexaDeviceAdapter = new AlexaDeviceAdapter(this, this.configList);
        this.configAdapter = alexaDeviceAdapter;
        this.configRecyclerView.setAdapter(alexaDeviceAdapter);
        this.configAdapter.setOnPowerClickListener(new AlexaDeviceAdapter.OnPowerClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AlexaActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.OnPowerClickListener
            public void onOffClick(View view, int i, Boolean bool) {
                GlobalBluetooth.getInstance().singleOnOff(((AlexaDevice) AlexaActivity.this.configList.get(i)).addr, bool.booleanValue());
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.OnPowerClickListener
            public void onOptionClick(View view, int i) {
                AlexaActivity.this.configSelect(i);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.OnPowerClickListener
            public void onSelectClick(View view, int i) {
                AlexaActivity.this.configSelect(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configSelect(int i) {
        AlexaDevice alexaDevice = this.configList.get(i);
        if (alexaDevice.isSelect) {
            alexaDevice.isSelect = false;
            this.selectList.remove(alexaDevice);
            this.configAdapter.notifyItemChanged(i);
        } else {
            if (this.selectList.size() == 4) {
                GlobalToast.showText(getApplicationContext(), R.string.enableMaxValue, 0);
                return;
            }
            alexaDevice.isSelect = true;
            this.selectList.add(alexaDevice);
            this.configAdapter.notifyItemChanged(i);
        }
    }

    private void refreshDevices() {
        this.configList.clear();
        this.selectList.clear();
        if (Tool.isToRadar()) {
            RealmResults realmResultsSort = this.realm.where(RadarDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).equalTo("isConfigAlexa", (Boolean) false).findAll().sort(GlobalVariable.index);
            RealmResults realmResultsSort2 = this.realm.where(RadarDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).equalTo("isConfigAlexa", (Boolean) true).findAll().sort(GlobalVariable.index);
            RealmResults<RadarDevice> realmResultsFindAll = this.realm.where(RadarDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).findAll();
            if (realmResultsFindAll.size() == realmResultsSort2.size()) {
                for (RadarDevice radarDevice : realmResultsFindAll) {
                    this.realm.beginTransaction();
                    radarDevice.setConfigAlexa(false);
                    this.realm.commitTransaction();
                    AlexaDevice alexaDevice = new AlexaDevice();
                    alexaDevice.did = radarDevice.getDid();
                    alexaDevice.addr = radarDevice.getAddr();
                    alexaDevice.name = radarDevice.getName();
                    alexaDevice.type = radarDevice.getType();
                    alexaDevice.isConfig = radarDevice.isConfigAlexa();
                    alexaDevice.isSelect = false;
                    this.configList.add(alexaDevice);
                }
            } else {
                for (int i = 0; i < realmResultsSort.size(); i++) {
                    RadarDevice radarDevice2 = (RadarDevice) realmResultsSort.get(i);
                    if (radarDevice2 != null) {
                        AlexaDevice alexaDevice2 = new AlexaDevice();
                        alexaDevice2.did = radarDevice2.getDid();
                        alexaDevice2.addr = radarDevice2.getAddr();
                        alexaDevice2.name = radarDevice2.getName();
                        alexaDevice2.type = radarDevice2.getType();
                        alexaDevice2.isConfig = radarDevice2.isConfigAlexa();
                        alexaDevice2.isSelect = false;
                        this.configList.add(alexaDevice2);
                    }
                }
                for (int i2 = 0; i2 < realmResultsSort2.size(); i2++) {
                    RadarDevice radarDevice3 = (RadarDevice) realmResultsSort2.get(i2);
                    if (radarDevice3 != null) {
                        AlexaDevice alexaDevice3 = new AlexaDevice();
                        alexaDevice3.did = radarDevice3.getDid();
                        alexaDevice3.addr = radarDevice3.getAddr();
                        alexaDevice3.name = radarDevice3.getName();
                        alexaDevice3.type = radarDevice3.getType();
                        alexaDevice3.isConfig = radarDevice3.isConfigAlexa();
                        alexaDevice3.isSelect = false;
                        this.configList.add(alexaDevice3);
                    }
                }
            }
        } else {
            RealmResults realmResultsSort3 = this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).equalTo("isConfigAlexa", (Boolean) false).findAll().sort(GlobalVariable.index);
            RealmResults realmResultsSort4 = this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).equalTo("isConfigAlexa", (Boolean) true).findAll().sort(GlobalVariable.index);
            RealmResults<BleDevice> realmResultsFindAll2 = this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).findAll();
            if (realmResultsFindAll2.size() == realmResultsSort4.size()) {
                for (BleDevice bleDevice : realmResultsFindAll2) {
                    this.realm.beginTransaction();
                    bleDevice.setConfigAlexa(false);
                    this.realm.commitTransaction();
                    AlexaDevice alexaDevice4 = new AlexaDevice();
                    alexaDevice4.did = bleDevice.getDid();
                    alexaDevice4.addr = bleDevice.getAddr();
                    alexaDevice4.name = bleDevice.getName();
                    alexaDevice4.type = bleDevice.getType();
                    alexaDevice4.isConfig = bleDevice.isConfigAlexa();
                    alexaDevice4.isSelect = false;
                    alexaDevice4.isBeenMain = bleDevice.isBeenMain();
                    this.configList.add(alexaDevice4);
                }
            } else {
                for (int i3 = 0; i3 < realmResultsSort3.size(); i3++) {
                    BleDevice bleDevice2 = (BleDevice) realmResultsSort3.get(i3);
                    if (bleDevice2 != null) {
                        AlexaDevice alexaDevice5 = new AlexaDevice();
                        alexaDevice5.did = bleDevice2.getDid();
                        alexaDevice5.addr = bleDevice2.getAddr();
                        alexaDevice5.name = bleDevice2.getName();
                        alexaDevice5.type = bleDevice2.getType();
                        alexaDevice5.isConfig = bleDevice2.isConfigAlexa();
                        alexaDevice5.isSelect = false;
                        alexaDevice5.isBeenMain = bleDevice2.isBeenMain();
                        this.configList.add(alexaDevice5);
                    }
                }
                for (int i4 = 0; i4 < realmResultsSort4.size(); i4++) {
                    BleDevice bleDevice3 = (BleDevice) realmResultsSort4.get(i4);
                    if (bleDevice3 != null) {
                        AlexaDevice alexaDevice6 = new AlexaDevice();
                        alexaDevice6.did = bleDevice3.getDid();
                        alexaDevice6.addr = bleDevice3.getAddr();
                        alexaDevice6.name = bleDevice3.getName();
                        alexaDevice6.type = bleDevice3.getType();
                        alexaDevice6.isConfig = bleDevice3.isConfigAlexa();
                        alexaDevice6.isSelect = false;
                        alexaDevice6.isBeenMain = bleDevice3.isBeenMain();
                        this.configList.add(alexaDevice6);
                    }
                }
            }
        }
        this.configAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startConfigAlexaState() {
        new setAlexaStateThread().start();
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
    }

    private class setAlexaStateThread extends Thread {
        private setAlexaStateThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            for (AlexaDevice alexaDevice : new ArrayList(AlexaActivity.this.selectList)) {
                BLSBleLight.controlSetAlexaState(alexaDevice.addr, 1);
                AlexaActivity.this.selectList.remove(alexaDevice);
                AlexaActivity.this.myHandler.sendMessage(AlexaActivity.this.myHandler.obtainMessage(101, alexaDevice));
                SystemClock.sleep(2000L);
            }
            if (AlexaActivity.this.loadingDialog != null) {
                loadDialogUtils.closeDialog(AlexaActivity.this.loadingDialog);
                AlexaActivity.this.loadingDialog = null;
            }
            AlexaActivity.this.myHandler.sendMessage(AlexaActivity.this.myHandler.obtainMessage(102));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateState(AlexaDevice alexaDevice) {
        this.configList.remove(alexaDevice);
        alexaDevice.isConfig = true;
        alexaDevice.isSelect = false;
        this.configList.add(alexaDevice);
        this.configAdapter.notifyDataSetChanged();
        BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(alexaDevice.addr)).findFirst();
        if (bleDevice != null) {
            this.realm.beginTransaction();
            bleDevice.setConfigAlexa(true);
            this.realm.commitTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAlexa() {
        this.showTipLayout1.setVisibility(0);
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AlexaActivity alexaActivity = (AlexaActivity) this.weakReference.get();
            if (message.what == 101) {
                alexaActivity.updateState((AlexaDevice) message.obj);
            } else if (message.what == 102) {
                alexaActivity.handleAlexa();
            }
        }
    }
}

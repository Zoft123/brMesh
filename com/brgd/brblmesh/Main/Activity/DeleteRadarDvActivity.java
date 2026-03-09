package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.RadarDvSortAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarTimer;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeleteRadarDvActivity extends FatherActivity {
    ImageView backView;
    private List<RadarDevice> bleDeviceList;
    private int currentResetDeviceIndex;
    RecyclerView deviceRecyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarDvActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.delete_devices_back) {
                DeleteRadarDvActivity.this.finish();
            }
        }
    };
    public final DeleteDevicesActivity.MyHandler myHandler = new DeleteDevicesActivity.MyHandler(Looper.getMainLooper(), this);
    private RadarDvSortAdapter radarDvSortAdapter;
    private Realm realm;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_delete_radar_dv);
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
        ImageView imageView = (ImageView) findViewById(R.id.delete_devices_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        initDeviceListView();
        refreshGroupDevices();
    }

    private void initDeviceListView() {
        this.bleDeviceList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.delete_devices_recyclerView);
        RadarDvSortAdapter radarDvSortAdapter = new RadarDvSortAdapter(this, this.bleDeviceList);
        this.radarDvSortAdapter = radarDvSortAdapter;
        this.deviceRecyclerView.setAdapter(radarDvSortAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.radarDvSortAdapter.setOnItemClickListener(new RadarDvSortAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarDvActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDvSortAdapter.OnItemClickListener
            public final void onClick(View view, int i) {
                this.f$0.lambda$initDeviceListView$0(view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceListView$0(View view, int i) {
        handleDeleteDevice(i);
    }

    private void refreshGroupDevices() {
        this.bleDeviceList.clear();
        this.bleDeviceList.addAll(this.realm.where(RadarDevice.class).findAll().sort(GlobalVariable.index));
        Iterator<RadarDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isDelete = true;
        }
        RadarDvSortAdapter radarDvSortAdapter = this.radarDvSortAdapter;
        if (radarDvSortAdapter != null) {
            radarDvSortAdapter.notifyDataSetChanged();
        }
    }

    private void handleDeleteDevice(int i) {
        this.currentResetDeviceIndex = i;
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.deviceRecyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.confirmDelete);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarDvActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$handleDeleteDevice$1(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteDevice$1(CustomDialog customDialog) {
        customDialog.dismiss();
        handleDeleteDevice();
    }

    private void handleDeleteDevice() {
        RadarDevice radarDevice = this.bleDeviceList.get(this.currentResetDeviceIndex);
        int index = radarDevice.getIndex();
        RealmResults realmResultsFindAll = this.realm.where(RadarTimer.class).equalTo(GlobalVariable.addr, Integer.valueOf(radarDevice.getAddr())).findAll();
        this.realm.beginTransaction();
        realmResultsFindAll.deleteAllFromRealm();
        this.realm.commitTransaction();
        for (RadarGroup radarGroup : this.realm.where(RadarGroup.class).notEqualTo("fixedId", (Integer) 0).findAll()) {
            for (RadarDevice radarDevice2 : radarGroup.getBleDeviceRealmList()) {
                if (radarDevice2.getDid().equals(radarDevice.getDid())) {
                    this.realm.beginTransaction();
                    radarGroup.getBleDeviceRealmList().remove(radarDevice2);
                    this.realm.commitTransaction();
                }
            }
        }
        this.realm.beginTransaction();
        radarDevice.deleteFromRealm();
        this.realm.commitTransaction();
        this.bleDeviceList.remove(this.currentResetDeviceIndex);
        this.radarDvSortAdapter.notifyDataSetChanged();
        if (((int) this.realm.where(RadarDevice.class).count()) == 0) {
            clearAllDevices();
            refreshGroupDevices();
            return;
        }
        this.realm.beginTransaction();
        for (RadarDevice radarDevice3 : this.bleDeviceList) {
            if (radarDevice3.getIndex() > index) {
                radarDevice3.setIndex(radarDevice3.getIndex() - 1);
            }
        }
        this.realm.commitTransaction();
    }

    private void clearAllDevices() {
        this.realm.beginTransaction();
        this.realm.where(RadarTimer.class).findAll().deleteAllFromRealm();
        this.realm.where(RadarDevice.class).findAll().deleteAllFromRealm();
        RadarPhoneType radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
        if (radarPhoneType != null) {
            radarPhoneType.setPhoneType(3);
        }
        this.realm.commitTransaction();
        SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.radarLightnessValue);
        SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.radarColorTempValue);
        BLSBleLight.removeAllDevice();
        BLSBleLight.removeAllRoomScenes();
        String lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
        SharePreferenceUtils.put(this, GlobalVariable.RADAR_CTRLKEY_TAG, lowerCase);
        BLSBleLight.setBLEControlKey(lowerCase);
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

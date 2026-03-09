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
import com.brgd.brblmesh.GeneralAdapter.BleDeviceSortAdapter;
import com.brgd.brblmesh.GeneralAdapter.GroupsAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeleteDevicesActivity extends FatherActivity {
    ImageView backView;
    private List<BleDevice> bleDeviceList;
    private BleDeviceSortAdapter bleDeviceSortAdapter;
    private Groups currentGroups;
    private int currentGroupsSelectIndex;
    private int currentResetDeviceIndex;
    RecyclerView deviceRecyclerView;
    private GroupsAdapter groupsAdapter;
    private List<Groups> groupsList;
    RecyclerView groupsRecyclerView;
    PhoneType phoneType;
    private Realm realm;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.delete_devices_back) {
                DeleteDevicesActivity.this.finish();
            }
        }
    };
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_delete_devices);
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
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        ImageView imageView = (ImageView) findViewById(R.id.delete_devices_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        initGroupView();
        initDeviceListView();
        queryGroup();
        refreshGroupDevices();
    }

    private void initGroupView() {
        this.groupsList = new ArrayList();
        this.groupsRecyclerView = (RecyclerView) findViewById(R.id.delete_devices_group_recyclerView);
        this.groupsRecyclerView.setLayoutManager(new MyLinearLayoutManager(this, 0, false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(this, this.groupsList);
        this.groupsAdapter = groupsAdapter;
        this.groupsRecyclerView.setAdapter(groupsAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new GroupsAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralAdapter.GroupsAdapter.OnItemButtonClickListener
            public final void OnItemButtonClick(View view, int i) {
                this.f$0.lambda$initGroupView$0(view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupView$0(View view, int i) {
        this.currentGroupsSelectIndex = i;
        this.currentGroups.isSelect = false;
        Groups groups = this.groupsList.get(this.currentGroupsSelectIndex);
        this.currentGroups = groups;
        groups.isSelect = true;
        this.groupsAdapter.notifyDataSetChanged();
        refreshGroupDevices();
    }

    private void initDeviceListView() {
        this.bleDeviceList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.delete_devices_recyclerView);
        BleDeviceSortAdapter bleDeviceSortAdapter = new BleDeviceSortAdapter(this, this.bleDeviceList);
        this.bleDeviceSortAdapter = bleDeviceSortAdapter;
        this.deviceRecyclerView.setAdapter(bleDeviceSortAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.bleDeviceSortAdapter.setOnItemClickListener(new BleDeviceSortAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity$$ExternalSyntheticLambda3
            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceSortAdapter.OnItemClickListener
            public final void onClick(View view, int i) {
                this.f$0.lambda$initDeviceListView$1(view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceListView$1(View view, int i) {
        if (this.bleDeviceList.get(i).getOtaTag() == 2) {
            GlobalToast.showText(getApplicationContext(), R.string.ota_fail_delete, 1);
        } else {
            handleDeleteDevice(i);
        }
    }

    private void queryGroup() {
        this.groupsList.clear();
        RealmResults realmResultsSort = this.realm.where(Groups.class).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            Groups groups = this.groupsList.get(this.currentGroupsSelectIndex);
            this.currentGroups = groups;
            groups.isSelect = true;
        }
        this.groupsAdapter.notifyDataSetChanged();
    }

    private void refreshGroupDevices() {
        RealmResults realmResultsSort;
        this.bleDeviceList.clear();
        if (this.currentGroups.getGroupId() == 0) {
            realmResultsSort = this.realm.where(BleDevice.class).findAll().sort(GlobalVariable.index);
        } else {
            realmResultsSort = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll().sort("groupIndex");
        }
        this.bleDeviceList.addAll(realmResultsSort);
        Iterator<BleDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isDelete = true;
        }
        BleDeviceSortAdapter bleDeviceSortAdapter = this.bleDeviceSortAdapter;
        if (bleDeviceSortAdapter != null) {
            bleDeviceSortAdapter.notifyDataSetChanged();
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
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$handleDeleteDevice$2(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteDevice$2(CustomDialog customDialog) {
        customDialog.dismiss();
        handleDeleteDevice();
    }

    private void handleDeleteDevice() {
        BleDevice bleDevice = this.bleDeviceList.get(this.currentResetDeviceIndex);
        int index = bleDevice.getIndex();
        int groupIndex = bleDevice.getGroupIndex();
        int groupId = bleDevice.getGroupId();
        RealmResults realmResultsFindAll = this.realm.where(Timer.class).equalTo(GlobalVariable.addr, Integer.valueOf(bleDevice.getAddr())).findAll();
        this.realm.beginTransaction();
        realmResultsFindAll.deleteAllFromRealm();
        this.realm.commitTransaction();
        Iterator it = this.realm.where(Scene.class).findAll().iterator();
        while (it.hasNext()) {
            SceneDevice sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(((Scene) it.next()).getSceneNumber())).equalTo(GlobalVariable.did, bleDevice.getDid()).findFirst();
            if (sceneDevice != null) {
                this.realm.beginTransaction();
                sceneDevice.deleteFromRealm();
                this.realm.commitTransaction();
            }
        }
        for (Mod mod : this.realm.where(Mod.class).equalTo(GlobalVariable.addr, Integer.valueOf(bleDevice.getAddr())).findAll()) {
            this.realm.beginTransaction();
            mod.deleteFromRealm();
            this.realm.commitTransaction();
        }
        this.realm.beginTransaction();
        bleDevice.deleteFromRealm();
        this.realm.commitTransaction();
        this.bleDeviceList.remove(this.currentResetDeviceIndex);
        this.bleDeviceSortAdapter.notifyDataSetChanged();
        if (((int) this.realm.where(BleDevice.class).count()) == 0) {
            clearAllDevices();
            queryGroup();
            refreshGroupDevices();
            return;
        }
        this.realm.beginTransaction();
        for (BleDevice bleDevice2 : this.bleDeviceList) {
            if (bleDevice2.getIndex() > index) {
                bleDevice2.setIndex(bleDevice2.getIndex() - 1);
            }
            if (bleDevice2.getGroupIndex() > groupIndex && bleDevice2.getGroupId() == groupId) {
                bleDevice2.setGroupIndex(bleDevice2.getGroupIndex() - 1);
            }
        }
        this.realm.commitTransaction();
    }

    private void clearAllDevices() {
        this.realm.beginTransaction();
        this.realm.where(Timer.class).findAll().deleteAllFromRealm();
        this.realm.where(SceneDevice.class).findAll().deleteAllFromRealm();
        this.realm.where(Mod.class).notEqualTo(GlobalVariable.addr, (Integer) 0).findAll().deleteAllFromRealm();
        this.realm.where(BleDevice.class).findAll().deleteAllFromRealm();
        PhoneType phoneType = (PhoneType) this.realm.where(PhoneType.class).findFirst();
        if (phoneType != null) {
            phoneType.setPhoneType(3);
        }
        this.realm.commitTransaction();
        SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.colorValue);
        SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.lightnessValue);
        SharePreferenceUtils.remove(getApplicationContext(), GlobalVariable.colorTempValue);
        BLSBleLight.removeAllDevice();
        BLSBleLight.removeAllRoomScenes();
        String lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
        SharePreferenceUtils.put(this, GlobalVariable.ctrlKey, lowerCase);
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

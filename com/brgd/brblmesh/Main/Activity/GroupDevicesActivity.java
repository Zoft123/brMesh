package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.AddBleDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.AllBleDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.GroupsAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalThreadPools;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GroupDevicesActivity extends FatherActivity {
    private static final int TIME_OUT = 102;
    private static final int UPDATE_GROUP_ID = 101;
    private AddBleDeviceAdapter addBleDeviceAdapter;
    RecyclerView addDeviceRecyclerView;
    private List<BleDevice> addDevicesList;
    private int addr;
    private AllBleDeviceAdapter allBleDeviceAdapter;
    RecyclerView allDeviceRecyclerView;
    private List<BleDevice> allDevicesList;
    ImageView backView;
    private BleDevice currentBleDevice;
    private int currentGroupId;
    private Groups currentGroups;
    private int currentGroupsSelectIndex;
    private GroupsAdapter groupsAdapter;
    private List<Groups> groupsList;
    RecyclerView groupsRecyclerView;
    private boolean isAddGroup;
    private List<BleDevice> list;
    private Dialog loadingDialog;
    private PhoneType phoneType;
    private Realm realm;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.GroupDevicesActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.group_devices_back) {
                GroupDevicesActivity.this.finish();
            }
        }
    };
    private boolean isReceiveCallback = false;
    final OnDevHeartBeatCallback mHeartbeatCallback = new OnDevHeartBeatCallback() { // from class: com.brgd.brblmesh.Main.Activity.GroupDevicesActivity.2
        @Override // cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback
        public void onCallback(int i, int i2, String str) {
            if (GroupDevicesActivity.this.isReceiveCallback) {
                return;
            }
            boolean z = false;
            int groupId = GroupDevicesActivity.this.isAddGroup ? GroupDevicesActivity.this.currentGroups.getGroupId() : 0;
            if (i2 == GroupDevicesActivity.this.currentBleDevice.getGroupId()) {
                return;
            }
            GroupDevicesActivity groupDevicesActivity = GroupDevicesActivity.this;
            if (i == groupDevicesActivity.currentBleDevice.getAddr() && i2 == groupId) {
                z = true;
            }
            groupDevicesActivity.isReceiveCallback = z;
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_group_devices);
        initView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.phoneType.getPhoneType() == 1) {
            stopReceiveService();
        }
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.phoneType.getPhoneType() == 1) {
            stopReceiveService();
        }
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        this.list = new ArrayList();
        ImageView imageView = (ImageView) findViewById(R.id.group_devices_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.addDevicesList = new ArrayList();
        this.addBleDeviceAdapter = new AddBleDeviceAdapter(this, this.addDevicesList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.group_devices_inGroup_recyclerView);
        this.addDeviceRecyclerView = recyclerView;
        recyclerView.setAdapter(this.addBleDeviceAdapter);
        this.addDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.addBleDeviceAdapter.setOnItemClickListener(new AddBleDeviceAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.GroupDevicesActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralAdapter.AddBleDeviceAdapter.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        this.allDevicesList = new ArrayList();
        this.allBleDeviceAdapter = new AllBleDeviceAdapter(this, this.allDevicesList);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.group_devices_not_inGroup_recyclerView);
        this.allDeviceRecyclerView = recyclerView2;
        recyclerView2.setAdapter(this.allBleDeviceAdapter);
        this.allDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.allBleDeviceAdapter.setOnItemClickListener(new AllBleDeviceAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.GroupDevicesActivity$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralAdapter.AllBleDeviceAdapter.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$initView$1(view, i);
            }
        });
        initGroupView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        this.currentBleDevice = this.addDevicesList.get(i);
        if (this.phoneType.getPhoneType() == 1) {
            this.isAddGroup = false;
            this.addr = this.currentBleDevice.getAddr();
            this.isReceiveCallback = false;
            startReceiveService();
            setGroupId();
            if (this.loadingDialog == null) {
                this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
                return;
            }
            return;
        }
        otherPhoneDeleteFromGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view, int i) {
        this.currentBleDevice = this.allDevicesList.get(i);
        if (this.phoneType.getPhoneType() == 1) {
            this.isAddGroup = true;
            this.addr = this.currentBleDevice.getAddr();
            this.currentGroupId = this.currentGroups.getGroupId();
            this.isReceiveCallback = false;
            startReceiveService();
            setGroupId();
            if (this.loadingDialog == null) {
                this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
                return;
            }
            return;
        }
        otherPhoneAddToGroup();
    }

    private void initGroupView() {
        this.groupsList = new ArrayList();
        this.groupsRecyclerView = (RecyclerView) findViewById(R.id.group_devices_group_recyclerView);
        this.groupsRecyclerView.setLayoutManager(new MyLinearLayoutManager(this, 0, false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(this, this.groupsList);
        this.groupsAdapter = groupsAdapter;
        this.groupsRecyclerView.setAdapter(groupsAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new GroupsAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.GroupDevicesActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.GroupsAdapter.OnItemButtonClickListener
            public final void OnItemButtonClick(View view, int i) {
                this.f$0.lambda$initGroupView$2(view, i);
            }
        });
        queryGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupView$2(View view, int i) {
        this.currentGroups.isSelect = false;
        this.currentGroupsSelectIndex = i;
        Groups groups = this.groupsList.get(i);
        this.currentGroups = groups;
        groups.isSelect = true;
        this.groupsAdapter.notifyDataSetChanged();
        refreshGroup();
    }

    private void queryGroup() {
        RealmResults realmResultsSort = this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).findAll().sort(GlobalVariable.index);
        if (realmResultsSort.isEmpty()) {
            return;
        }
        this.groupsList.addAll(realmResultsSort);
        if (getIntent().getBooleanExtra(GlobalVariable.toGroupDevice, false)) {
            this.currentGroupsSelectIndex = this.groupsList.size() - 1;
        } else {
            this.currentGroupsSelectIndex = 0;
        }
        Groups groups = this.groupsList.get(this.currentGroupsSelectIndex);
        this.currentGroups = groups;
        groups.isSelect = true;
        this.groupsAdapter.notifyDataSetChanged();
        refreshGroup();
    }

    private void refreshGroup() {
        this.addDevicesList.clear();
        this.addDevicesList.addAll(this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll().sort("groupIndex"));
        this.addBleDeviceAdapter.notifyDataSetChanged();
        this.allDevicesList.clear();
        this.allDevicesList.addAll(this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, (Integer) 0).findAll().sort(GlobalVariable.index));
        for (Groups groups : this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).notEqualTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll().sort(GlobalVariable.index)) {
            RealmResults realmResultsSort = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(groups.getGroupId())).findAll().sort("groupIndex");
            this.list.clear();
            this.list.addAll(realmResultsSort);
            Iterator<BleDevice> it = this.list.iterator();
            while (it.hasNext()) {
                it.next().groupName = groups.getGroupName();
            }
            this.allDevicesList.addAll(this.list);
        }
        this.allBleDeviceAdapter.notifyDataSetChanged();
    }

    private void handleGroupIndex(int i) {
        RealmResults<BleDevice> realmResultsFindAll = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll();
        this.realm.beginTransaction();
        for (BleDevice bleDevice : realmResultsFindAll) {
            if (bleDevice.getGroupIndex() > i) {
                bleDevice.setGroupIndex(bleDevice.getGroupIndex() - 1);
            }
        }
        this.realm.commitTransaction();
    }

    private void startReceiveService() {
        BLSBleLight.checkPermission();
        BLSBleLight.startBleReceiveService();
        BLSBleLight.setOnHeartBeatCallback(this.mHeartbeatCallback);
    }

    private void stopReceiveService() {
        BLSBleLight.stopBleReceiveServiceDelay();
        BLSBleLight.setOnHeartBeatCallback(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetGroupId() {
        if (this.phoneType.getPhoneType() == 1) {
            stopReceiveService();
        }
        if (this.isAddGroup) {
            addToGroup();
        } else {
            deleteFromGroup();
        }
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
    }

    private void deleteFromGroup() {
        int groupIndex = this.currentBleDevice.getGroupIndex();
        this.realm.beginTransaction();
        this.currentBleDevice.setGroupId(0);
        this.currentBleDevice.setGroupIndex(-1);
        this.realm.commitTransaction();
        handleGroupIndex(groupIndex);
        refreshGroup();
    }

    private void otherPhoneDeleteFromGroup() {
        int groupIndex = this.currentBleDevice.getGroupIndex();
        this.realm.beginTransaction();
        this.currentBleDevice.setGroupId(0);
        this.currentBleDevice.setGroupIndex(-1);
        this.realm.commitTransaction();
        handleGroupIndex(groupIndex);
        refreshGroup();
    }

    private void addToGroup() {
        int iCount = (int) this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).count();
        this.realm.beginTransaction();
        this.currentBleDevice.setGroupId(this.currentGroups.getGroupId());
        this.currentBleDevice.setGroupIndex(iCount);
        this.realm.commitTransaction();
        refreshGroup();
    }

    private void otherPhoneAddToGroup() {
        int iCount = (int) this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).count();
        this.realm.beginTransaction();
        this.currentBleDevice.setGroupId(this.currentGroups.getGroupId());
        this.currentBleDevice.setGroupIndex(iCount);
        this.realm.commitTransaction();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeout() {
        if (this.phoneType.getPhoneType() == 1) {
            stopReceiveService();
        }
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
        GlobalToast.showText(getApplicationContext(), R.string.tryAgain, 1);
    }

    private void setGroupId() {
        GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.GroupDevicesActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setGroupId$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupId$3() {
        if (sendSetGroupId()) {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(101));
        } else {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(102));
        }
    }

    private boolean sendSetGroupId() {
        boolean groupAddrWithDevice;
        for (int i = 0; i < 15; i++) {
            if (this.isAddGroup) {
                groupAddrWithDevice = BLSBleLight.setGroupAddrWithDevice(this.addr, this.currentGroupId);
            } else {
                groupAddrWithDevice = BLSBleLight.setGroupAddrWithDevice(this.addr, 0);
            }
            if (!groupAddrWithDevice) {
                return false;
            }
            for (int i2 = 0; i2 < 9; i2++) {
                SystemClock.sleep(100L);
                if (this.isReceiveCallback) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            GroupDevicesActivity groupDevicesActivity = (GroupDevicesActivity) this.weakReference.get();
            if (message.what == 101) {
                groupDevicesActivity.handleSetGroupId();
            } else if (message.what == 102) {
                groupDevicesActivity.handleTimeout();
            }
        }
    }
}

package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.BleDeviceSortAdapter;
import com.brgd.brblmesh.GeneralAdapter.GroupsAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.ListenerInterceptor;
import com.brgd.brblmesh.GeneralClass.MyItemTouchHelper;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DevicesSortActivity extends FatherActivity implements MyItemTouchHelper.OnItemTouchListener {
    ImageView backView;
    private List<BleDevice> bleDeviceList;
    private BleDeviceSortAdapter bleDeviceSortAdapter;
    private Groups currentGroups;
    private int currentGroupsSelectIndex;
    RecyclerView deviceRecyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DevicesSortActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.devices_sort_back) {
                DevicesSortActivity.this.finish();
            }
        }
    };
    private GroupsAdapter groupsAdapter;
    private List<Groups> groupsList;
    RecyclerView groupsRecyclerView;
    Realm realm;

    @Override // com.brgd.brblmesh.GeneralClass.MyItemTouchHelper.OnItemTouchListener
    public void onSwiped(int i) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_devices_sort);
        initView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.devices_sort_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        initGroupView();
        initDeviceListView();
        queryGroup();
        refreshGroupDevices();
    }

    private void initGroupView() {
        this.groupsList = new ArrayList();
        this.groupsRecyclerView = (RecyclerView) findViewById(R.id.device_sort_group_recyclerView);
        this.groupsRecyclerView.setLayoutManager(new MyLinearLayoutManager(this, 0, false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(this, this.groupsList);
        this.groupsAdapter = groupsAdapter;
        this.groupsRecyclerView.setAdapter(groupsAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new GroupsAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DevicesSortActivity$$ExternalSyntheticLambda0
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
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.device_sort_recyclerView);
        BleDeviceSortAdapter bleDeviceSortAdapter = new BleDeviceSortAdapter(this, this.bleDeviceList);
        this.bleDeviceSortAdapter = bleDeviceSortAdapter;
        this.deviceRecyclerView.setAdapter(bleDeviceSortAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        MyItemTouchHelper myItemTouchHelper = new MyItemTouchHelper(this);
        myItemTouchHelper.setSort(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(this.deviceRecyclerView);
        new ListenerInterceptor(itemTouchHelper).setDoDrag(true);
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
            it.next().isDelete = false;
        }
        BleDeviceSortAdapter bleDeviceSortAdapter = this.bleDeviceSortAdapter;
        if (bleDeviceSortAdapter != null) {
            bleDeviceSortAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.brgd.brblmesh.GeneralClass.MyItemTouchHelper.OnItemTouchListener
    public boolean onMove(int i, int i2) {
        if (i < i2) {
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                Collections.swap(this.bleDeviceList, i3, i4);
                i3 = i4;
            }
        } else {
            for (int i5 = i; i5 > i2; i5--) {
                Collections.swap(this.bleDeviceList, i5, i5 - 1);
            }
        }
        this.bleDeviceSortAdapter.notifyItemMoved(i, i2);
        BleDevice bleDevice = this.bleDeviceList.get(i);
        BleDevice bleDevice2 = this.bleDeviceList.get(i2);
        if (this.currentGroups.getGroupId() == 0) {
            int index = bleDevice.getIndex();
            int index2 = bleDevice2.getIndex();
            this.realm.beginTransaction();
            bleDevice.setIndex(index2);
            bleDevice2.setIndex(index);
            this.realm.commitTransaction();
            return true;
        }
        int groupIndex = bleDevice.getGroupIndex();
        int groupIndex2 = bleDevice2.getGroupIndex();
        this.realm.beginTransaction();
        bleDevice.setGroupIndex(groupIndex2);
        bleDevice2.setGroupIndex(groupIndex);
        this.realm.commitTransaction();
        return true;
    }
}

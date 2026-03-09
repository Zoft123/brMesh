package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.BleDeviceSharedAdapter;
import com.brgd.brblmesh.GeneralAdapter.GroupsAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ShareActivity extends FatherActivity {
    TextView allBtn;
    ImageView backView;
    private List<BleDevice> bleDeviceList;
    private BleDeviceSharedAdapter bleDeviceSharedAdapter;
    private Groups currentGroups;
    private int currentGroupsSelectIndex;
    RecyclerView deviceRecyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ShareActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.devices_share_back) {
                ShareActivity.this.finish();
                return;
            }
            if (id == R.id.device_shared_toShare) {
                if (ShareActivity.this.selectDidList.isEmpty()) {
                    GlobalToast.showText(ShareActivity.this.getApplicationContext(), R.string.selectDevice, 0);
                    return;
                }
                if (ShareActivity.this.selectDidList.size() > 6) {
                    GlobalToast.showText(ShareActivity.this.getApplicationContext(), R.string.shareMaxValue, 0);
                    return;
                }
                Intent intent = new Intent(ShareActivity.this, (Class<?>) SharedScanActivity.class);
                intent.putStringArrayListExtra(GlobalVariable.toShare, new ArrayList<>(ShareActivity.this.selectDidList));
                ShareActivity.this.startActivity(intent);
                return;
            }
            if (id == R.id.device_share_show_save1) {
                ShareActivity.this.tipLayout.setVisibility(4);
            }
        }
    };
    private GroupsAdapter groupsAdapter;
    private List<Groups> groupsList;
    RecyclerView groupsRecyclerView;
    ImageView okView;
    Realm realm;
    private List<String> selectDidList;
    private ConstraintLayout tipLayout;
    TextView toShareView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_share);
        initView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        refreshGroupDevices();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.devices_share_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.devices_share_all);
        this.allBtn = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        TextView textView2 = (TextView) findViewById(R.id.device_shared_toShare);
        this.toShareView = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        this.tipLayout = (ConstraintLayout) findViewById(R.id.device_share_show_View1);
        ImageView imageView2 = (ImageView) findViewById(R.id.device_share_show_save1);
        this.okView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        initGroupView();
        initDeviceListView();
        queryGroup();
        refreshGroupDevices();
    }

    private void initGroupView() {
        this.groupsList = new ArrayList();
        this.groupsRecyclerView = (RecyclerView) findViewById(R.id.device_share_group_recyclerView);
        this.groupsRecyclerView.setLayoutManager(new MyLinearLayoutManager(this, 0, false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(this, this.groupsList);
        this.groupsAdapter = groupsAdapter;
        this.groupsRecyclerView.setAdapter(groupsAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new GroupsAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ShareActivity$$ExternalSyntheticLambda1
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
        this.selectDidList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.device_share_recyclerView);
        BleDeviceSharedAdapter bleDeviceSharedAdapter = new BleDeviceSharedAdapter(this, this.bleDeviceList);
        this.bleDeviceSharedAdapter = bleDeviceSharedAdapter;
        this.deviceRecyclerView.setAdapter(bleDeviceSharedAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.bleDeviceSharedAdapter.setOnItemClickListener(new BleDeviceSharedAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ShareActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceSharedAdapter.OnItemClickListener
            public final void onClick(View view, int i, View view2, TextView textView) {
                this.f$0.lambda$initDeviceListView$1(view, i, view2, textView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceListView$1(View view, int i, View view2, TextView textView) {
        BleDevice bleDevice = this.bleDeviceList.get(i);
        if (view.isSelected()) {
            bleDevice.isSelect = false;
            this.selectDidList.remove(bleDevice.getDid());
            view.setSelected(false);
            view2.setSelected(false);
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            return;
        }
        if (this.selectDidList.size() == 6) {
            GlobalToast.showText(getApplicationContext(), R.string.shareMaxValue, 0);
            return;
        }
        bleDevice.isSelect = true;
        this.selectDidList.add(bleDevice.getDid());
        view.setSelected(true);
        view2.setSelected(true);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    private void queryGroup() {
        this.groupsList.clear();
        RealmResults realmResultsSort = this.realm.where(Groups.class).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            if (((Groups) this.realm.where(Groups.class).equalTo(GlobalVariable.index, Integer.valueOf(this.currentGroupsSelectIndex)).findFirst()) == null) {
                this.currentGroupsSelectIndex = 0;
            }
            Groups groups = this.groupsList.get(this.currentGroupsSelectIndex);
            this.currentGroups = groups;
            groups.isSelect = true;
        }
        this.groupsAdapter.notifyDataSetChanged();
    }

    private void refreshGroupDevices() {
        this.bleDeviceList.clear();
        this.selectDidList.clear();
        RealmResults realmResultsSort = this.realm.where(BleDevice.class).findAll().sort(GlobalVariable.index);
        RealmResults realmResultsSort2 = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll().sort("groupIndex");
        if (this.currentGroups.getGroupId() == 0) {
            this.bleDeviceList.addAll(realmResultsSort);
        } else {
            this.bleDeviceList.addAll(realmResultsSort2);
        }
        Iterator<BleDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isSelect = false;
        }
        BleDeviceSharedAdapter bleDeviceSharedAdapter = this.bleDeviceSharedAdapter;
        if (bleDeviceSharedAdapter != null) {
            bleDeviceSharedAdapter.notifyDataSetChanged();
        }
    }
}

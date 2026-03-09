package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.RadarDvSortAdapter;
import com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
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
public class RadarDvSortActivity extends FatherActivity implements MyItemTouchHelper.OnItemTouchListener {
    ImageView backView;
    private List<RadarDevice> bleDeviceList;
    private RadarGroup currentGroups;
    private int currentGroupsSelectIndex;
    RecyclerView deviceRecyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvSortActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.devices_sort_back) {
                RadarDvSortActivity.this.finish();
            }
        }
    };
    private List<RadarGroup> groupsList;
    RecyclerView groupsRecyclerView;
    private RadarDvSortAdapter radarDvSortAdapter;
    private RadarGroupAdapter radarGroupAdapter;
    Realm realm;

    @Override // com.brgd.brblmesh.GeneralClass.MyItemTouchHelper.OnItemTouchListener
    public void onSwiped(int i) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_radar_dv_sort);
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
        RadarGroupAdapter radarGroupAdapter = new RadarGroupAdapter(this, this.groupsList);
        this.radarGroupAdapter = radarGroupAdapter;
        this.groupsRecyclerView.setAdapter(radarGroupAdapter);
        this.currentGroupsSelectIndex = 0;
        this.radarGroupAdapter.setOnItemButtonClickListener(new RadarGroupAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvSortActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter.OnItemButtonClickListener
            public final void OnItemButtonClick(View view, int i) {
                this.f$0.lambda$initGroupView$0(view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupView$0(View view, int i) {
        this.currentGroupsSelectIndex = i;
        this.currentGroups.isSelect = false;
        RadarGroup radarGroup = this.groupsList.get(this.currentGroupsSelectIndex);
        this.currentGroups = radarGroup;
        radarGroup.isSelect = true;
        this.radarGroupAdapter.notifyDataSetChanged();
        refreshGroupDevices();
    }

    private void initDeviceListView() {
        this.bleDeviceList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.device_sort_recyclerView);
        RadarDvSortAdapter radarDvSortAdapter = new RadarDvSortAdapter(this, this.bleDeviceList);
        this.radarDvSortAdapter = radarDvSortAdapter;
        this.deviceRecyclerView.setAdapter(radarDvSortAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        MyItemTouchHelper myItemTouchHelper = new MyItemTouchHelper(this);
        myItemTouchHelper.setSort(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(this.deviceRecyclerView);
        new ListenerInterceptor(itemTouchHelper).setDoDrag(true);
    }

    private void queryGroup() {
        this.groupsList.clear();
        RealmResults realmResultsSort = this.realm.where(RadarGroup.class).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            RadarGroup radarGroup = this.groupsList.get(this.currentGroupsSelectIndex);
            this.currentGroups = radarGroup;
            radarGroup.isSelect = true;
        }
        this.radarGroupAdapter.notifyDataSetChanged();
    }

    private void refreshGroupDevices() {
        this.bleDeviceList.clear();
        if (this.currentGroups.getFixedId() == 0) {
            this.bleDeviceList.addAll(this.realm.where(RadarDevice.class).findAll().sort(GlobalVariable.index));
        } else {
            this.bleDeviceList.addAll(this.currentGroups.getBleDeviceRealmList());
        }
        Iterator<RadarDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isDelete = false;
        }
        RadarDvSortAdapter radarDvSortAdapter = this.radarDvSortAdapter;
        if (radarDvSortAdapter != null) {
            radarDvSortAdapter.notifyDataSetChanged();
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
        this.radarDvSortAdapter.notifyItemMoved(i, i2);
        RadarDevice radarDevice = this.bleDeviceList.get(i);
        RadarDevice radarDevice2 = this.bleDeviceList.get(i2);
        if (this.currentGroups.getFixedId() == 0) {
            int index = radarDevice.getIndex();
            int index2 = radarDevice2.getIndex();
            this.realm.beginTransaction();
            radarDevice.setIndex(index2);
            radarDevice2.setIndex(index);
            this.realm.commitTransaction();
            return true;
        }
        int iIndexOf = this.currentGroups.getBleDeviceRealmList().indexOf(radarDevice);
        int iIndexOf2 = this.currentGroups.getBleDeviceRealmList().indexOf(radarDevice2);
        this.realm.beginTransaction();
        this.currentGroups.getBleDeviceRealmList().set(iIndexOf2, radarDevice);
        this.currentGroups.getBleDeviceRealmList().set(iIndexOf, radarDevice2);
        this.realm.commitTransaction();
        return true;
    }
}

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
import com.brgd.brblmesh.GeneralAdapter.RadarDvShareAdapter;
import com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
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
public class ShareRadarDvActivity extends FatherActivity {
    TextView allBtn;
    ImageView backView;
    private List<RadarDevice> bleDeviceList;
    private RadarGroup currentGroups;
    private int currentGroupsSelectIndex;
    RecyclerView deviceRecyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ShareRadarDvActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.devices_share_back) {
                ShareRadarDvActivity.this.finish();
                return;
            }
            if (id == R.id.device_shared_toShare) {
                if (ShareRadarDvActivity.this.selectDidList.isEmpty()) {
                    GlobalToast.showText(ShareRadarDvActivity.this.getApplicationContext(), R.string.selectDevice, 0);
                    return;
                }
                if (ShareRadarDvActivity.this.selectDidList.size() > 6) {
                    GlobalToast.showText(ShareRadarDvActivity.this.getApplicationContext(), R.string.shareMaxValue, 0);
                    return;
                }
                Intent intent = new Intent(ShareRadarDvActivity.this, (Class<?>) SharedScanActivity.class);
                intent.putStringArrayListExtra(GlobalVariable.toShare, new ArrayList<>(ShareRadarDvActivity.this.selectDidList));
                ShareRadarDvActivity.this.startActivity(intent);
                return;
            }
            if (id == R.id.device_share_show_save1) {
                ShareRadarDvActivity.this.tipLayout.setVisibility(4);
            }
        }
    };
    private RadarGroupAdapter groupsAdapter;
    private List<RadarGroup> groupsList;
    RecyclerView groupsRecyclerView;
    ImageView okView;
    private RadarDvShareAdapter radarDvShareAdapter;
    Realm realm;
    private List<String> selectDidList;
    private ConstraintLayout tipLayout;
    TextView toShareView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_share_radar_dv);
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
        RadarGroupAdapter radarGroupAdapter = new RadarGroupAdapter(this, this.groupsList);
        this.groupsAdapter = radarGroupAdapter;
        this.groupsRecyclerView.setAdapter(radarGroupAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new RadarGroupAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ShareRadarDvActivity$$ExternalSyntheticLambda1
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
        this.groupsAdapter.notifyDataSetChanged();
        refreshGroupDevices();
    }

    private void initDeviceListView() {
        this.bleDeviceList = new ArrayList();
        this.selectDidList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) findViewById(R.id.device_share_recyclerView);
        RadarDvShareAdapter radarDvShareAdapter = new RadarDvShareAdapter(this, this.bleDeviceList);
        this.radarDvShareAdapter = radarDvShareAdapter;
        this.deviceRecyclerView.setAdapter(radarDvShareAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.radarDvShareAdapter.setOnItemClickListener(new RadarDvShareAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ShareRadarDvActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDvShareAdapter.OnItemClickListener
            public final void onClick(View view, int i, View view2, TextView textView) {
                this.f$0.lambda$initDeviceListView$1(view, i, view2, textView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceListView$1(View view, int i, View view2, TextView textView) {
        RadarDevice radarDevice = this.bleDeviceList.get(i);
        if (view.isSelected()) {
            radarDevice.isSelect = false;
            this.selectDidList.remove(radarDevice.getDid());
            view.setSelected(false);
            view2.setSelected(false);
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorText));
            return;
        }
        if (this.selectDidList.size() == 6) {
            GlobalToast.showText(getApplicationContext(), R.string.shareMaxValue, 0);
            return;
        }
        radarDevice.isSelect = true;
        this.selectDidList.add(radarDevice.getDid());
        view.setSelected(true);
        view2.setSelected(true);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    private void queryGroup() {
        this.groupsList.clear();
        RealmResults realmResultsSort = this.realm.where(RadarGroup.class).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            if (((RadarGroup) this.realm.where(RadarGroup.class).equalTo(GlobalVariable.index, Integer.valueOf(this.currentGroupsSelectIndex)).findFirst()) == null) {
                this.currentGroupsSelectIndex = 0;
            }
            RadarGroup radarGroup = this.groupsList.get(this.currentGroupsSelectIndex);
            this.currentGroups = radarGroup;
            radarGroup.isSelect = true;
        }
        this.groupsAdapter.notifyDataSetChanged();
    }

    private void refreshGroupDevices() {
        this.bleDeviceList.clear();
        this.selectDidList.clear();
        if (this.currentGroups.getFixedId() == 0) {
            this.bleDeviceList.addAll(this.realm.where(RadarDevice.class).findAll().sort(GlobalVariable.index));
        } else {
            this.bleDeviceList.addAll(this.currentGroups.getBleDeviceRealmList());
        }
        Iterator<RadarDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isSelect = false;
        }
        RadarDvShareAdapter radarDvShareAdapter = this.radarDvShareAdapter;
        if (radarDvShareAdapter != null) {
            radarDvShareAdapter.notifyDataSetChanged();
        }
    }
}

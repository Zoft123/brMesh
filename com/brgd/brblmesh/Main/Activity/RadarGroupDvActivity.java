package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.InGroupAdapter;
import com.brgd.brblmesh.GeneralAdapter.NotInGroupAdapter;
import com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RadarGroupDvActivity extends FatherActivity {
    RecyclerView addDeviceRecyclerView;
    private List<RadarDevice> addDevicesList;
    private List<RadarDevice> addList;
    private TextView addView;
    RecyclerView allDeviceRecyclerView;
    private List<RadarDevice> allDevicesList;
    ImageView backView;
    private TextView cancelView;
    private TextView cancelView1;
    private int currentGroupId;
    private int currentGroupsSelectIndex;
    private RadarDevice currentRadarDv;
    private RadarGroup currentRadarGroup;
    private List<RadarDevice> deleteList;
    private TextView deleteView;
    private List<RadarDevice> devicesList;
    private InGroupAdapter inGroupAdapter;
    private Dialog loadingDialog;
    private TextView multiSelectView;
    private TextView multiSelectView1;
    private NotInGroupAdapter notInGroupAdapter;
    private RadarGroupAdapter radarGroupAdapter;
    private List<RadarGroup> radarGroupList;
    private Realm realm;
    RecyclerView recyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupDvActivity.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.fixed_devices_back) {
                RadarGroupDvActivity.this.finish();
                return;
            }
            if (id == R.id.fixed_devices_multiSelect1) {
                if (RadarGroupDvActivity.this.addDevicesList.isEmpty()) {
                    GlobalToast.showText(RadarGroupDvActivity.this.getApplicationContext(), R.string.noDevices, 1);
                    return;
                }
                RadarGroupDvActivity.this.multiSelectView1.setVisibility(4);
                RadarGroupDvActivity.this.deleteView.setVisibility(0);
                RadarGroupDvActivity.this.cancelView1.setVisibility(0);
                for (RadarDevice radarDevice : RadarGroupDvActivity.this.addDevicesList) {
                    radarDevice.isGroupStatus = true;
                    radarDevice.isSelect = false;
                }
                RadarGroupDvActivity.this.deleteList.clear();
                RadarGroupDvActivity.this.inGroupAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_delete) {
                if (RadarGroupDvActivity.this.deleteList.isEmpty()) {
                    GlobalToast.showText(RadarGroupDvActivity.this.getApplicationContext(), R.string.selectDevice, 1);
                    return;
                }
                RadarGroupDvActivity.this.multiSelectView1.setVisibility(0);
                RadarGroupDvActivity.this.deleteView.setVisibility(4);
                RadarGroupDvActivity.this.cancelView1.setVisibility(4);
                RadarGroupDvActivity.this.multiSelectView.setVisibility(0);
                RadarGroupDvActivity.this.addView.setVisibility(4);
                RadarGroupDvActivity.this.cancelView.setVisibility(4);
                RadarGroupDvActivity.this.deleteDevicesFromFixedGroup();
                return;
            }
            if (id == R.id.fixed_devices_cancel1) {
                RadarGroupDvActivity.this.multiSelectView1.setVisibility(0);
                RadarGroupDvActivity.this.deleteView.setVisibility(4);
                RadarGroupDvActivity.this.cancelView1.setVisibility(4);
                for (RadarDevice radarDevice2 : RadarGroupDvActivity.this.addDevicesList) {
                    radarDevice2.isGroupStatus = false;
                    radarDevice2.isSelect = false;
                }
                RadarGroupDvActivity.this.deleteList.clear();
                RadarGroupDvActivity.this.inGroupAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_multiSelect) {
                if (RadarGroupDvActivity.this.allDevicesList.isEmpty()) {
                    GlobalToast.showText(RadarGroupDvActivity.this.getApplicationContext(), R.string.noDevices, 1);
                    return;
                }
                RadarGroupDvActivity.this.multiSelectView.setVisibility(4);
                RadarGroupDvActivity.this.addView.setVisibility(0);
                RadarGroupDvActivity.this.cancelView.setVisibility(0);
                for (RadarDevice radarDevice3 : RadarGroupDvActivity.this.allDevicesList) {
                    radarDevice3.isGroupStatus = true;
                    radarDevice3.isSelect = false;
                }
                RadarGroupDvActivity.this.addList.clear();
                RadarGroupDvActivity.this.notInGroupAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_add) {
                if (RadarGroupDvActivity.this.addList.isEmpty()) {
                    GlobalToast.showText(RadarGroupDvActivity.this.getApplicationContext(), R.string.selectDevice, 1);
                    return;
                }
                RadarGroupDvActivity.this.multiSelectView.setVisibility(0);
                RadarGroupDvActivity.this.addView.setVisibility(4);
                RadarGroupDvActivity.this.cancelView.setVisibility(4);
                RadarGroupDvActivity.this.multiSelectView1.setVisibility(0);
                RadarGroupDvActivity.this.deleteView.setVisibility(4);
                RadarGroupDvActivity.this.cancelView1.setVisibility(4);
                RadarGroupDvActivity.this.addDevicesToFixedGroup();
                return;
            }
            if (id == R.id.fixed_devices_cancel) {
                RadarGroupDvActivity.this.multiSelectView.setVisibility(0);
                RadarGroupDvActivity.this.addView.setVisibility(4);
                RadarGroupDvActivity.this.cancelView.setVisibility(4);
                for (RadarDevice radarDevice4 : RadarGroupDvActivity.this.allDevicesList) {
                    radarDevice4.isGroupStatus = false;
                    radarDevice4.isSelect = false;
                }
                RadarGroupDvActivity.this.addList.clear();
                RadarGroupDvActivity.this.notInGroupAdapter.notifyDataSetChanged();
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_radar_group_dv);
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
        this.devicesList = new ArrayList();
        this.deleteList = new ArrayList();
        this.addList = new ArrayList();
        ImageView imageView = (ImageView) findViewById(R.id.fixed_devices_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.fixed_devices_multiSelect1);
        this.multiSelectView1 = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        TextView textView2 = (TextView) findViewById(R.id.fixed_devices_delete);
        this.deleteView = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        TextView textView3 = (TextView) findViewById(R.id.fixed_devices_cancel1);
        this.cancelView1 = textView3;
        textView3.setOnClickListener(this.disDoubleClickListener);
        TextView textView4 = (TextView) findViewById(R.id.fixed_devices_multiSelect);
        this.multiSelectView = textView4;
        textView4.setOnClickListener(this.disDoubleClickListener);
        TextView textView5 = (TextView) findViewById(R.id.fixed_devices_add);
        this.addView = textView5;
        textView5.setOnClickListener(this.disDoubleClickListener);
        TextView textView6 = (TextView) findViewById(R.id.fixed_devices_cancel);
        this.cancelView = textView6;
        textView6.setOnClickListener(this.disDoubleClickListener);
        this.addDevicesList = new ArrayList();
        this.inGroupAdapter = new InGroupAdapter(this, this.addDevicesList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fixed_devices_inGroup_recyclerView);
        this.addDeviceRecyclerView = recyclerView;
        recyclerView.setAdapter(this.inGroupAdapter);
        this.addDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.inGroupAdapter.setOnItemClickListener(new InGroupAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupDvActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.InGroupAdapter.OnItemClickListener
            public void onItemClick(View view, int i) {
                RadarGroupDvActivity.this.deleteFromFixedGroup(i);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.InGroupAdapter.OnItemClickListener
            public void optionClick(View view, int i) {
                RadarDevice radarDevice = (RadarDevice) RadarGroupDvActivity.this.addDevicesList.get(i);
                if (radarDevice.isSelect) {
                    radarDevice.isSelect = false;
                    RadarGroupDvActivity.this.deleteList.remove(radarDevice);
                } else {
                    radarDevice.isSelect = true;
                    RadarGroupDvActivity.this.deleteList.add(radarDevice);
                }
                RadarGroupDvActivity.this.inGroupAdapter.notifyDataSetChanged();
            }
        });
        this.allDevicesList = new ArrayList();
        this.notInGroupAdapter = new NotInGroupAdapter(this, this.allDevicesList);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.fixed_devices_not_inGroup_recyclerView);
        this.allDeviceRecyclerView = recyclerView2;
        recyclerView2.setAdapter(this.notInGroupAdapter);
        this.allDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.notInGroupAdapter.setOnItemClickListener(new NotInGroupAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupDvActivity.2
            @Override // com.brgd.brblmesh.GeneralAdapter.NotInGroupAdapter.OnItemClickListener
            public void onItemClick(View view, int i) {
                RadarGroupDvActivity.this.addToFixedGroup(i);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.NotInGroupAdapter.OnItemClickListener
            public void optionClick(View view, int i) {
                RadarDevice radarDevice = (RadarDevice) RadarGroupDvActivity.this.allDevicesList.get(i);
                if (radarDevice.isSelect) {
                    radarDevice.isSelect = false;
                    RadarGroupDvActivity.this.addList.remove(radarDevice);
                } else {
                    radarDevice.isSelect = true;
                    RadarGroupDvActivity.this.addList.add(radarDevice);
                }
                RadarGroupDvActivity.this.notInGroupAdapter.notifyDataSetChanged();
            }
        });
        initGroupView();
    }

    private void initGroupView() {
        this.radarGroupList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.fixed_devices_group_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this, 0, false));
        RadarGroupAdapter radarGroupAdapter = new RadarGroupAdapter(this, this.radarGroupList);
        this.radarGroupAdapter = radarGroupAdapter;
        this.recyclerView.setAdapter(radarGroupAdapter);
        this.currentGroupsSelectIndex = 0;
        this.radarGroupAdapter.setOnItemButtonClickListener(new RadarGroupAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupDvActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter.OnItemButtonClickListener
            public final void OnItemButtonClick(View view, int i) {
                this.f$0.lambda$initGroupView$0(view, i);
            }
        });
        queryGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupView$0(View view, int i) {
        this.currentRadarGroup.isSelect = false;
        this.currentGroupsSelectIndex = i;
        RadarGroup radarGroup = this.radarGroupList.get(i);
        this.currentRadarGroup = radarGroup;
        radarGroup.isSelect = true;
        this.currentGroupId = this.currentRadarGroup.getFixedId();
        this.radarGroupAdapter.notifyDataSetChanged();
        refreshGroup();
    }

    private void queryGroup() {
        RealmResults realmResultsFindAll = this.realm.where(RadarGroup.class).notEqualTo("fixedId", (Integer) 0).findAll();
        if (realmResultsFindAll.isEmpty()) {
            return;
        }
        this.radarGroupList.addAll(realmResultsFindAll);
        if (getIntent().getBooleanExtra(GlobalVariable.toGroupDevice, false)) {
            this.currentGroupsSelectIndex = this.radarGroupList.size() - 1;
        } else {
            this.currentGroupsSelectIndex = 0;
        }
        RadarGroup radarGroup = this.radarGroupList.get(this.currentGroupsSelectIndex);
        this.currentRadarGroup = radarGroup;
        radarGroup.isSelect = true;
        this.currentGroupId = this.currentRadarGroup.getFixedId();
        this.radarGroupAdapter.notifyDataSetChanged();
        refreshGroup();
    }

    private void refreshGroup() {
        this.addDevicesList.clear();
        this.addDevicesList.addAll(this.currentRadarGroup.getBleDeviceRealmList());
        this.inGroupAdapter.notifyDataSetChanged();
        this.devicesList.clear();
        this.devicesList.addAll(this.realm.where(RadarDevice.class).equalTo("isSupportFixedGroup", (Boolean) true).findAll().sort(GlobalVariable.index));
        this.allDevicesList.clear();
        Iterator<RadarDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            this.devicesList.remove(it.next());
        }
        this.allDevicesList.addAll(this.devicesList);
        this.notInGroupAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFromFixedGroup(int i) {
        this.currentRadarDv = this.addDevicesList.get(i);
        ArrayList arrayList = new ArrayList();
        Iterator<RadarDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        arrayList2.remove(this.addDevicesList.indexOf(this.currentRadarDv));
        BLSBleLight.removeFixGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList), Tool.getListToArray(arrayList2));
        loading100();
        deleteFromGroup();
    }

    private void deleteFromGroup() {
        this.realm.beginTransaction();
        this.currentRadarGroup.getBleDeviceRealmList().remove(this.currentRadarDv);
        this.realm.commitTransaction();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDevicesFromFixedGroup() {
        ArrayList arrayList = new ArrayList();
        Iterator<RadarDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList(this.addDevicesList);
        Iterator<RadarDevice> it2 = this.deleteList.iterator();
        while (it2.hasNext()) {
            arrayList3.remove(it2.next());
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList2.add(Integer.valueOf(((RadarDevice) it3.next()).getAddr()));
        }
        BLSBleLight.removeFixGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList), Tool.getListToArray(arrayList2));
        loading100();
        this.realm.beginTransaction();
        Iterator<RadarDevice> it4 = this.deleteList.iterator();
        while (it4.hasNext()) {
            this.currentRadarGroup.getBleDeviceRealmList().remove(it4.next());
        }
        this.realm.commitTransaction();
        for (RadarDevice radarDevice : this.addDevicesList) {
            radarDevice.isGroupStatus = false;
            radarDevice.isSelect = false;
        }
        this.deleteList.clear();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToFixedGroup(int i) {
        this.currentRadarDv = this.allDevicesList.get(i);
        ArrayList arrayList = new ArrayList();
        Iterator<RadarDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        arrayList.add(Integer.valueOf(this.currentRadarDv.getAddr()));
        BLSBleLight.generateFixedGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList));
        loading100();
        addToGroup();
    }

    private void addToGroup() {
        this.realm.beginTransaction();
        this.currentRadarGroup.getBleDeviceRealmList().add(this.currentRadarDv);
        this.realm.commitTransaction();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDevicesToFixedGroup() {
        ArrayList arrayList = new ArrayList();
        Iterator<RadarDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        Iterator<RadarDevice> it2 = this.addList.iterator();
        while (it2.hasNext()) {
            arrayList.add(Integer.valueOf(it2.next().getAddr()));
        }
        BLSBleLight.generateFixedGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList));
        loading100();
        this.realm.beginTransaction();
        Iterator<RadarDevice> it3 = this.addList.iterator();
        while (it3.hasNext()) {
            this.currentRadarGroup.getBleDeviceRealmList().add(it3.next());
        }
        this.realm.commitTransaction();
        for (RadarDevice radarDevice : this.allDevicesList) {
            radarDevice.isGroupStatus = false;
            radarDevice.isSelect = false;
        }
        this.addList.clear();
        refreshGroup();
    }

    private void loading100() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupDvActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loading100$1();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loading100$1() {
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
    }

    private static class MyHandler extends Handler {
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

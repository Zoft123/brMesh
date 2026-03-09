package com.brgd.brblmesh.Main.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import com.brgd.brblmesh.GeneralAdapter.DeviceTypeAdapter;
import com.brgd.brblmesh.GeneralAdapter.RadarDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.DeviceType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Activity.AlexaActivity;
import com.brgd.brblmesh.Main.Activity.AlexaGuideActivity;
import com.brgd.brblmesh.Main.Activity.RadarDvCtrlActivity;
import com.brgd.brblmesh.Main.Activity.RadarMainActivity;
import com.brgd.brblmesh.Main.Activity.RadarSetUpActivity;
import com.brgd.brblmesh.Main.Activity.ScanDeviceActivity;
import com.brgd.brblmesh.Main.Interface.FirstFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RadarDvFragment extends Fragment implements FirstFragmentListener {
    TextView actionBarView;
    private List<Integer> addrList;
    ImageView allOffBtn;
    ImageView allOnBtn;
    private ConstraintLayout allOnOffLayout;
    private ImageView allSelectBtn;
    private TextView allSelectText;
    private TextView batchCtrlBtn;
    private List<RadarDevice> bleDeviceList;
    private ImageView ctrlGroupBtn;
    private DeviceType currentDeviceType;
    private RadarGroup currentGroups;
    private int currentGroupsSelectIndex;
    private RecyclerView deviceRecyclerView;
    private DeviceTypeAdapter deviceTypeAdapter;
    private List<DeviceType> deviceTypeList;
    RecyclerView deviceTypeRecyclerView;
    ImageView enableBtn;
    TextView enableText;
    private ImageView groupCtrlBtn;
    private TextView groupCtrlText;
    private ImageView groupLeftBtn;
    private RadarGroupAdapter groupsAdapter;
    private List<RadarGroup> groupsList;
    RecyclerView groupsRecyclerView;
    ImageView groupsSettingView;
    TextView hiView;
    private boolean isGroupCtrl;
    AlertDialog locationAlertDialog;
    private RadarDeviceAdapter radarDeviceAdapter;
    private RadarMainActivity radarMainActivity;
    private RadarPhoneType radarPhoneType;
    private Realm realm;
    ImageView refreshBtn;
    private List<RadarDevice> selectList;
    private int pwrType = 0;
    private int cctType = 0;
    private int rgbType = 0;
    private int rgbwType = 0;
    private int rgbcwType = 0;
    private int radarType = 0;
    private int viewType = 43050;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                RadarDvFragment.this.radarMainActivity.bleCASDialog();
                return;
            }
            if (Tool.bleNotOnToast(RadarDvFragment.this.radarMainActivity.getApplicationContext())) {
                return;
            }
            int id = view.getId();
            if (id == R.id.devices_group_ctrl) {
                if (RadarDvFragment.this.groupCtrlBtn.isSelected()) {
                    RadarDvFragment.this.singleControl();
                } else {
                    RadarDvFragment.this.groupControl();
                }
                RadarDvFragment.this.deviceRecyclerView.scrollToPosition(0);
                return;
            }
            if (id == R.id.devices_group_ctrl_right) {
                RadarDvFragment.this.groupToControl();
                return;
            }
            if (id == R.id.device_allOn) {
                RadarDvFragment.this.allOn();
                return;
            }
            if (id == R.id.device_allOff) {
                RadarDvFragment.this.allOff();
                return;
            }
            if (id == R.id.device_allSelect) {
                if (RadarDvFragment.this.allSelectBtn.isSelected()) {
                    RadarDvFragment.this.cancelAllSelect();
                    return;
                } else {
                    RadarDvFragment.this.allSelect();
                    return;
                }
            }
            if (id == R.id.devices_batch_ctrl) {
                RadarDvFragment.this.toBatchControl();
                return;
            }
            if (id == R.id.search_add) {
                RadarDvFragment.this.singleControl();
                RadarDvFragment.this.deviceRecyclerView.scrollToPosition(0);
                RadarDvFragment.this.searchClick();
                return;
            }
            if (id == R.id.devices_group_setting) {
                RadarDvFragment.this.startActivity(new Intent(RadarDvFragment.this.getActivity(), (Class<?>) RadarSetUpActivity.class));
                return;
            }
            if (id == R.id.devices_enable_alexa) {
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    RadarDvFragment.this.radarMainActivity.bleCASDialog();
                    return;
                }
                if (Tool.bleNotOnToast(RadarDvFragment.this.radarMainActivity.getApplicationContext())) {
                    return;
                }
                if (!((Boolean) SharePreferenceUtils.get(RadarDvFragment.this.getActivity(), GlobalVariable.showAlexaGuide, false)).booleanValue()) {
                    RadarDvFragment.this.startActivity(new Intent(RadarDvFragment.this.getActivity(), (Class<?>) AlexaGuideActivity.class));
                } else {
                    RadarDvFragment.this.startActivity(new Intent(RadarDvFragment.this.getActivity(), (Class<?>) AlexaActivity.class));
                }
            }
        }
    };

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.radarMainActivity = (RadarMainActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_radar_dv, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        isMainPhone();
        if (this.isGroupCtrl) {
            singleControl();
        }
        queryGroup();
        refreshGroupDevices();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
    }

    private void isMainPhone() {
        if (this.radarPhoneType.getPhoneType() == 1) {
            this.refreshBtn.setVisibility(0);
            this.hiView.setVisibility(8);
            this.actionBarView.setText(R.string.Main1);
        } else if (this.radarPhoneType.getPhoneType() == 2) {
            this.refreshBtn.setVisibility(4);
            this.hiView.setVisibility(8);
            this.actionBarView.setText(R.string.Sub);
        } else {
            this.refreshBtn.setVisibility(0);
            this.hiView.setVisibility(0);
            this.actionBarView.setText("");
        }
    }

    private void initView(View view) {
        Realm.init(this.radarMainActivity.getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.radarPhoneType = (RadarPhoneType) defaultInstance.where(RadarPhoneType.class).findFirst();
        this.actionBarView = (TextView) view.findViewById(R.id.devices_actionBar_bg);
        ImageView imageView = (ImageView) view.findViewById(R.id.search_add);
        this.refreshBtn = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.devices_enable_alexa);
        this.enableBtn = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.enableText = (TextView) view.findViewById(R.id.devices_enable_alexaText);
        this.hiView = (TextView) view.findViewById(R.id.devices_hi);
        initGroupView(view);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.devices_group_setting);
        this.groupsSettingView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        this.groupCtrlBtn = (ImageView) view.findViewById(R.id.devices_group_ctrl);
        this.groupLeftBtn = (ImageView) view.findViewById(R.id.device_group_left);
        this.groupCtrlText = (TextView) view.findViewById(R.id.device_group_text);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.devices_group_ctrl_right);
        this.ctrlGroupBtn = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        this.allOnOffLayout = (ConstraintLayout) view.findViewById(R.id.devices_allOnOff_bg);
        this.allOnBtn = (ImageView) view.findViewById(R.id.device_allOn);
        this.allOffBtn = (ImageView) view.findViewById(R.id.device_allOff);
        this.allSelectBtn = (ImageView) view.findViewById(R.id.device_allSelect);
        this.allSelectText = (TextView) view.findViewById(R.id.device_allSelectText);
        this.groupCtrlBtn.setOnClickListener(this.disDoubleClickListener);
        this.allOnBtn.setOnClickListener(this.disDoubleClickListener);
        this.allOffBtn.setOnClickListener(this.disDoubleClickListener);
        this.allSelectBtn.setOnClickListener(this.disDoubleClickListener);
        initDeviceListView(view);
        TextView textView = (TextView) view.findViewById(R.id.devices_batch_ctrl);
        this.batchCtrlBtn = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        this.selectList = new ArrayList();
        resetType();
        this.deviceTypeRecyclerView = (RecyclerView) view.findViewById(R.id.device_type_recyclerView);
        this.deviceTypeList = new ArrayList();
        this.deviceTypeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        DeviceTypeAdapter deviceTypeAdapter = new DeviceTypeAdapter(getActivity(), this.deviceTypeList);
        this.deviceTypeAdapter = deviceTypeAdapter;
        this.deviceTypeRecyclerView.setAdapter(deviceTypeAdapter);
        this.deviceTypeAdapter.setOnItemClickListener(new DeviceTypeAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda7
            @Override // com.brgd.brblmesh.GeneralAdapter.DeviceTypeAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i) {
                this.f$0.lambda$initView$0(view2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        if (this.isGroupCtrl) {
            singleControl();
        }
        this.currentDeviceType.isSelect = false;
        DeviceType deviceType = this.deviceTypeList.get(i);
        this.currentDeviceType = deviceType;
        deviceType.isSelect = true;
        this.deviceTypeAdapter.notifyDataSetChanged();
        refreshTypeDevice();
    }

    private void initGroupView(View view) {
        this.groupsList = new ArrayList();
        this.groupsRecyclerView = (RecyclerView) view.findViewById(R.id.devices_group_recyclerView);
        this.groupsRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity(), 0, false));
        RadarGroupAdapter radarGroupAdapter = new RadarGroupAdapter(getActivity(), this.groupsList);
        this.groupsAdapter = radarGroupAdapter;
        this.groupsRecyclerView.setAdapter(radarGroupAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new RadarGroupAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda4
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarGroupAdapter.OnItemButtonClickListener
            public final void OnItemButtonClick(View view2, int i) {
                this.f$0.lambda$initGroupView$1(view2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupView$1(View view, int i) {
        if (this.isGroupCtrl) {
            singleControl();
        }
        this.currentGroupsSelectIndex = i;
        this.currentGroups.isSelect = false;
        RadarGroup radarGroup = this.groupsList.get(this.currentGroupsSelectIndex);
        this.currentGroups = radarGroup;
        radarGroup.isSelect = true;
        this.groupsAdapter.notifyDataSetChanged();
        refreshGroupDevices();
    }

    private void initDeviceListView(View view) {
        initDeviceListViewUserBigMoment(view);
    }

    private void initDeviceListViewUserBigMoment(View view) {
        this.bleDeviceList = new ArrayList();
        this.addrList = new ArrayList();
        this.deviceRecyclerView = (RecyclerView) view.findViewById(R.id.devices_device_recyclerView);
        RadarDeviceAdapter radarDeviceAdapter = new RadarDeviceAdapter(getActivity(), this.bleDeviceList);
        this.radarDeviceAdapter = radarDeviceAdapter;
        this.deviceRecyclerView.setAdapter(radarDeviceAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        this.radarDeviceAdapter.setOnPowerClickListener(new RadarDeviceAdapter.OnPowerClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment.1
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDeviceAdapter.OnPowerClickListener
            public void onOffClick(View view2, int i, Boolean bool) {
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    RadarDvFragment.this.radarMainActivity.bleCASDialog();
                } else {
                    if (Tool.bleNotOnToast(RadarDvFragment.this.radarMainActivity.getApplicationContext())) {
                        return;
                    }
                    GlobalBluetooth.getInstance().singleOnOff(((RadarDevice) RadarDvFragment.this.bleDeviceList.get(i)).getAddr(), bool.booleanValue());
                }
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDeviceAdapter.OnPowerClickListener
            public void onOptionClick(View view2, int i, View view3) {
                RadarDevice radarDevice = (RadarDevice) RadarDvFragment.this.bleDeviceList.get(i);
                if (RadarDvFragment.this.isGroupCtrl) {
                    int iIntValue = 0;
                    if (view2.isSelected()) {
                        radarDevice.isSelect = false;
                        view2.setSelected(false);
                        view3.setSelected(false);
                        Iterator it = RadarDvFragment.this.addrList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer num = (Integer) it.next();
                            if (num.intValue() == radarDevice.getAddr()) {
                                RadarDvFragment.this.addrList.remove(num);
                                break;
                            }
                        }
                    } else {
                        radarDevice.isSelect = true;
                        view2.setSelected(true);
                        view3.setSelected(true);
                        Iterator it2 = RadarDvFragment.this.addrList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            Integer num2 = (Integer) it2.next();
                            if (num2.intValue() == radarDevice.getAddr()) {
                                iIntValue = num2.intValue();
                                break;
                            }
                        }
                        if (iIntValue == 0) {
                            RadarDvFragment.this.addrList.add(Integer.valueOf(radarDevice.getAddr()));
                        }
                    }
                    RadarDvFragment.this.radarDeviceAdapter.notifyItemChanged(i);
                }
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDeviceAdapter.OnPowerClickListener
            public void onSelectClick(View view2, int i, View view3) {
                RadarDevice radarDevice = (RadarDevice) RadarDvFragment.this.bleDeviceList.get(i);
                if (RadarDvFragment.this.isGroupCtrl) {
                    int iIntValue = 0;
                    if (view2.isSelected()) {
                        radarDevice.isSelect = false;
                        view2.setSelected(false);
                        view3.setSelected(false);
                        Iterator it = RadarDvFragment.this.addrList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer num = (Integer) it.next();
                            if (num.intValue() == radarDevice.getAddr()) {
                                RadarDvFragment.this.addrList.remove(num);
                                break;
                            }
                        }
                    } else {
                        radarDevice.isSelect = true;
                        view2.setSelected(true);
                        view3.setSelected(true);
                        Iterator it2 = RadarDvFragment.this.addrList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            Integer num2 = (Integer) it2.next();
                            if (num2.intValue() == radarDevice.getAddr()) {
                                iIntValue = num2.intValue();
                                break;
                            }
                        }
                        if (iIntValue == 0) {
                            RadarDvFragment.this.addrList.add(Integer.valueOf(radarDevice.getAddr()));
                        }
                    }
                    RadarDvFragment.this.radarDeviceAdapter.notifyItemChanged(i);
                    return;
                }
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    RadarDvFragment.this.radarMainActivity.bleCASDialog();
                    return;
                }
                if (Tool.bleNotOnToast(RadarDvFragment.this.radarMainActivity.getApplicationContext())) {
                    return;
                }
                Intent intent = new Intent(RadarDvFragment.this.getActivity(), (Class<?>) RadarDvCtrlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.ctrlAddr, radarDevice.getAddr());
                bundle.putInt(GlobalVariable.viewType, radarDevice.getType());
                intent.putExtra(GlobalVariable.toCtrl, bundle);
                RadarDvFragment.this.startActivity(intent);
            }
        });
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
        BLSBleLight.removeAllDevice();
        RealmResults<RadarDevice> realmResultsSort = this.realm.where(RadarDevice.class).findAll().sort(GlobalVariable.index);
        for (RadarDevice radarDevice : realmResultsSort) {
            BLEDeviceInfo bLEDeviceInfo = new BLEDeviceInfo();
            bLEDeviceInfo.did = radarDevice.getDid();
            bLEDeviceInfo.addr = radarDevice.getAddr();
            bLEDeviceInfo.type = radarDevice.getType();
            BLSBleLight.addDevice(bLEDeviceInfo);
        }
        if (this.currentGroups.getFixedId() == 0) {
            this.bleDeviceList.addAll(realmResultsSort);
        } else {
            this.bleDeviceList.addAll(this.currentGroups.getBleDeviceRealmList());
        }
        RadarDeviceAdapter radarDeviceAdapter = this.radarDeviceAdapter;
        if (radarDeviceAdapter != null) {
            radarDeviceAdapter.notifyDataSetChanged();
        }
        refreshDeviceType();
        if (this.radarPhoneType.getPhoneType() == 1) {
            if (((int) this.realm.where(RadarDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).count()) != 0) {
                this.enableBtn.setVisibility(0);
                this.enableText.setVisibility(0);
                return;
            }
            return;
        }
        this.enableBtn.setVisibility(4);
        this.enableText.setVisibility(4);
    }

    private void refreshDeviceType() {
        int i;
        this.deviceTypeList.clear();
        this.deviceTypeAdapter.notifyDataSetChanged();
        if (this.bleDeviceList.isEmpty()) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (RadarDevice radarDevice : this.bleDeviceList) {
            if (radarDevice.getType() == 43049) {
                i2++;
            } else if (radarDevice.getType() == 43051) {
                i3++;
            } else if (radarDevice.getType() == 43168) {
                i4++;
            } else if (radarDevice.getType() == 43169) {
                i5++;
            } else if (radarDevice.getType() == 43050) {
                i6++;
            } else if (radarDevice.getType() == 44601) {
                i7++;
            }
        }
        this.deviceTypeList.clear();
        DeviceType deviceType = new DeviceType();
        deviceType.name = getString(R.string.Mix);
        deviceType.type = 0;
        this.deviceTypeList.add(deviceType);
        if (i2 > 0) {
            DeviceType deviceType2 = new DeviceType();
            deviceType2.name = getString(R.string.pwr);
            deviceType2.type = 43049;
            this.deviceTypeList.add(deviceType2);
        }
        if (i3 > 0) {
            DeviceType deviceType3 = new DeviceType();
            deviceType3.name = getString(R.string.cct);
            deviceType3.type = 43051;
            this.deviceTypeList.add(deviceType3);
        }
        if (i4 > 0) {
            DeviceType deviceType4 = new DeviceType();
            deviceType4.name = getString(R.string.rgb);
            deviceType4.type = 43168;
            this.deviceTypeList.add(deviceType4);
        }
        if (i5 > 0) {
            DeviceType deviceType5 = new DeviceType();
            deviceType5.name = getString(R.string.rgbw);
            deviceType5.type = 43169;
            this.deviceTypeList.add(deviceType5);
        }
        if (i6 > 0) {
            DeviceType deviceType6 = new DeviceType();
            deviceType6.name = getString(R.string.rgbcw);
            deviceType6.type = 43050;
            this.deviceTypeList.add(deviceType6);
        }
        if (i7 > 0) {
            DeviceType deviceType7 = new DeviceType();
            deviceType7.name = getString(R.string.radar);
            deviceType7.type = 44601;
            this.deviceTypeList.add(deviceType7);
        }
        if (this.deviceTypeList.size() == 2) {
            this.deviceTypeRecyclerView.setVisibility(8);
            i = 0;
        } else {
            i = 0;
            this.deviceTypeRecyclerView.setVisibility(0);
        }
        DeviceType deviceType8 = this.deviceTypeList.get(i);
        this.currentDeviceType = deviceType8;
        deviceType8.isSelect = true;
        this.deviceTypeAdapter.notifyDataSetChanged();
    }

    private void refreshTypeDevice() {
        this.bleDeviceList.clear();
        if (this.currentDeviceType.type == 0) {
            if (this.currentGroups.getFixedId() == 0) {
                this.bleDeviceList.addAll(this.realm.where(RadarDevice.class).findAll().sort(GlobalVariable.index));
            } else {
                this.bleDeviceList.addAll(this.currentGroups.getBleDeviceRealmList());
            }
        } else if (this.currentGroups.getFixedId() == 0) {
            this.bleDeviceList.addAll(this.realm.where(RadarDevice.class).equalTo(GlobalVariable.type, Integer.valueOf(this.currentDeviceType.type)).findAll().sort(GlobalVariable.index));
        } else {
            for (RadarDevice radarDevice : this.currentGroups.getBleDeviceRealmList()) {
                if (radarDevice.getType() == this.currentDeviceType.type) {
                    this.bleDeviceList.add(radarDevice);
                }
            }
        }
        RadarDeviceAdapter radarDeviceAdapter = this.radarDeviceAdapter;
        if (radarDeviceAdapter != null) {
            radarDeviceAdapter.notifyDataSetChanged();
        }
    }

    private void resetType() {
        this.selectList.clear();
        this.pwrType = 0;
        this.cctType = 0;
        this.rgbType = 0;
        this.rgbwType = 0;
        this.rgbcwType = 0;
        this.radarType = 0;
        this.viewType = 43050;
    }

    private void queryType(List<RadarDevice> list) {
        int i;
        int i2;
        Iterator<RadarDevice> it = list.iterator();
        while (it.hasNext()) {
            switch (it.next().getType()) {
                case 43049:
                    if (this.pwrType == 0) {
                        this.pwrType = 1;
                    }
                    break;
                case 43050:
                    if (this.rgbcwType == 0) {
                        this.rgbcwType = 1;
                    }
                    break;
                case 43051:
                    if (this.cctType == 0) {
                        this.cctType = 1;
                    }
                    break;
                case 43168:
                    if (this.rgbType == 0) {
                        this.rgbType = 1;
                    }
                    break;
                case 43169:
                    if (this.rgbwType == 0) {
                        this.rgbwType = 1;
                    }
                    break;
                case 44601:
                    if (this.radarType == 0) {
                        this.radarType = 1;
                    }
                    break;
            }
        }
        if (this.rgbcwType != 0 || (((i = this.rgbwType) != 0 && (this.cctType != 0 || this.radarType != 0)) || ((i2 = this.rgbType) != 0 && (this.cctType != 0 || this.radarType != 0)))) {
            this.viewType = 43050;
            return;
        }
        if (i != 0 || (i2 != 0 && this.pwrType != 0)) {
            this.viewType = 43169;
            return;
        }
        if (i2 != 0) {
            this.viewType = 43168;
            return;
        }
        if (this.cctType != 0) {
            this.viewType = 43051;
        } else if (this.pwrType != 0) {
            this.viewType = 43049;
        } else if (this.radarType != 0) {
            this.viewType = 44601;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void groupControl() {
        if (this.bleDeviceList.size() > 1) {
            this.addrList.clear();
            resetType();
            this.groupCtrlBtn.setSelected(true);
            this.groupLeftBtn.setSelected(true);
            this.groupCtrlText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorWhite));
            this.groupCtrlText.setText(getString(R.string.SingleCtrl));
            this.ctrlGroupBtn.setVisibility(8);
            this.allOnOffLayout.setVisibility(8);
            this.allSelectBtn.setVisibility(0);
            this.allSelectBtn.setSelected(false);
            this.allSelectText.setVisibility(0);
            this.allSelectText.setText(getString(R.string.AllSelect));
            this.allSelectText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorText));
            this.batchCtrlBtn.setVisibility(0);
            this.isGroupCtrl = true;
            for (RadarDevice radarDevice : this.bleDeviceList) {
                radarDevice.isGroupStatus = true;
                radarDevice.isSelect = false;
            }
            this.radarDeviceAdapter.notifyDataSetChanged();
            return;
        }
        if (this.bleDeviceList.size() == 1) {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.atLeastTwoCtrl, 0);
        } else {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.noDevices, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void singleControl() {
        this.groupCtrlBtn.setSelected(false);
        this.groupLeftBtn.setSelected(false);
        this.groupCtrlText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorText));
        this.groupCtrlText.setText(getString(R.string.GroupCtrl));
        this.ctrlGroupBtn.setVisibility(0);
        this.allOnOffLayout.setVisibility(0);
        this.allSelectBtn.setVisibility(8);
        this.allSelectText.setVisibility(8);
        this.batchCtrlBtn.setVisibility(8);
        this.isGroupCtrl = false;
        for (RadarDevice radarDevice : this.bleDeviceList) {
            radarDevice.isGroupStatus = false;
            radarDevice.isSelect = false;
        }
        this.radarDeviceAdapter.notifyDataSetChanged();
        this.addrList.clear();
        resetType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void groupToControl() {
        if (!this.bleDeviceList.isEmpty()) {
            resetType();
            queryType(this.bleDeviceList);
            final Intent intent = new Intent(getActivity(), (Class<?>) RadarDvCtrlActivity.class);
            if (this.radarPhoneType.getPhoneType() == 1 && this.currentDeviceType.type == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.groupId, this.currentGroups.getFixedId());
                bundle.putInt(GlobalVariable.type, 43050);
                bundle.putInt(GlobalVariable.tempGroupId, -1);
                bundle.putInt(GlobalVariable.viewType, this.viewType);
                intent.putExtra(GlobalVariable.groupToCtrl, bundle);
                startActivity(intent);
                return;
            }
            this.addrList.clear();
            Iterator<RadarDevice> it = this.bleDeviceList.iterator();
            while (it.hasNext()) {
                this.addrList.add(Integer.valueOf(it.next().getAddr()));
            }
            int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
            final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
            if (iBroadcastTempGroupId > 0) {
                this.radarMainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$groupToControl$2(dialogCreateLoadingDialog, intent);
                    }
                }, iBroadcastTempGroupId);
                return;
            } else {
                loadDialogUtils.closeDialog(dialogCreateLoadingDialog);
                GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
                return;
            }
        }
        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.noDevicesInGroup, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$groupToControl$2(Dialog dialog, Intent intent) {
        loadDialogUtils.closeDialog(dialog);
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalVariable.groupId, -1);
        bundle.putInt(GlobalVariable.type, 43050);
        bundle.putInt(GlobalVariable.tempGroupId, 253);
        bundle.putInt(GlobalVariable.viewType, this.viewType);
        intent.putExtra(GlobalVariable.groupToCtrl, bundle);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allOn() {
        if (this.bleDeviceList.isEmpty()) {
            return;
        }
        if (this.radarPhoneType.getPhoneType() == 1 && this.currentDeviceType.type == 0) {
            GlobalBluetooth.getInstance().groupOnOff(43050, this.currentGroups.getFixedId(), true);
            return;
        }
        this.addrList.clear();
        Iterator<RadarDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            this.addrList.add(Integer.valueOf(it.next().getAddr()));
        }
        int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
        final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
        if (iBroadcastTempGroupId > 0) {
            this.radarMainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    RadarDvFragment.lambda$allOn$3(dialogCreateLoadingDialog);
                }
            }, iBroadcastTempGroupId);
        } else {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
        }
    }

    static /* synthetic */ void lambda$allOn$3(Dialog dialog) {
        loadDialogUtils.closeDialog(dialog);
        GlobalBluetooth.getInstance().groupOnOff(43050, 253, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allOff() {
        if (this.bleDeviceList.isEmpty()) {
            return;
        }
        if (this.radarPhoneType.getPhoneType() == 1 && this.currentDeviceType.type == 0) {
            GlobalBluetooth.getInstance().groupOnOff(43050, this.currentGroups.getFixedId(), false);
            return;
        }
        this.addrList.clear();
        Iterator<RadarDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            this.addrList.add(Integer.valueOf(it.next().getAddr()));
        }
        int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
        final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
        if (iBroadcastTempGroupId > 0) {
            this.radarMainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    RadarDvFragment.lambda$allOff$4(dialogCreateLoadingDialog);
                }
            }, iBroadcastTempGroupId);
        } else {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
        }
    }

    static /* synthetic */ void lambda$allOff$4(Dialog dialog) {
        loadDialogUtils.closeDialog(dialog);
        GlobalBluetooth.getInstance().groupOnOff(43050, 253, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allSelect() {
        this.allSelectBtn.setSelected(true);
        this.allSelectText.setText(getString(R.string.Cancel));
        this.allSelectText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorWhite));
        this.addrList.clear();
        for (RadarDevice radarDevice : this.bleDeviceList) {
            radarDevice.isSelect = true;
            this.addrList.add(Integer.valueOf(radarDevice.getAddr()));
        }
        this.radarDeviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAllSelect() {
        this.allSelectBtn.setSelected(false);
        this.allSelectText.setText(getString(R.string.AllSelect));
        this.allSelectText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorText));
        Iterator<RadarDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isSelect = false;
        }
        this.addrList.clear();
        this.radarDeviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toBatchControl() {
        if (this.addrList.size() > 1) {
            if (this.radarPhoneType.getPhoneType() == 2 || ((this.radarPhoneType.getPhoneType() == 1 && this.addrList.size() != this.bleDeviceList.size()) || this.currentDeviceType.type != 0)) {
                resetType();
                for (RadarDevice radarDevice : this.bleDeviceList) {
                    if (radarDevice.isSelect) {
                        this.selectList.add(radarDevice);
                    }
                }
                queryType(this.selectList);
                int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
                final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
                if (iBroadcastTempGroupId > 0) {
                    this.radarMainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$toBatchControl$5(dialogCreateLoadingDialog);
                        }
                    }, iBroadcastTempGroupId);
                    return;
                } else {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
                    return;
                }
            }
            if (this.radarPhoneType.getPhoneType() == 1 && this.bleDeviceList.size() == this.addrList.size()) {
                groupToControl();
                return;
            }
            return;
        }
        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.atLeastTwoCtrl, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toBatchControl$5(Dialog dialog) {
        loadDialogUtils.closeDialog(dialog);
        Intent intent = new Intent(getActivity(), (Class<?>) RadarDvCtrlActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalVariable.groupId, -1);
        bundle.putInt(GlobalVariable.type, 43050);
        bundle.putInt(GlobalVariable.tempGroupId, 253);
        bundle.putInt(GlobalVariable.viewType, this.viewType);
        intent.putExtra(GlobalVariable.groupToCtrl, bundle);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchClick() {
        if (((int) this.realm.where(RadarDevice.class).count()) >= 250) {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.addDeviceMax, 0);
            return;
        }
        if (Tool.isAPI31()) {
            toScanDevice();
        } else if (Tool.checkLocationPermission()) {
            this.radarMainActivity.getLocationPermission();
        } else {
            locationDialog();
        }
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this.radarMainActivity).setMessage(R.string.locationAddressRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$6(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.RadarDvFragment$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$7(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$6(DialogInterface dialogInterface, int i) {
        this.radarMainActivity.getLocationPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$7(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    @Override // com.brgd.brblmesh.Main.Interface.FirstFragmentListener
    public void toScanDevice() {
        BLSBleLight.checkPermission();
        startActivity(new Intent(getActivity(), (Class<?>) ScanDeviceActivity.class));
    }
}

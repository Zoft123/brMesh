package com.brgd.brblmesh.Main.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.DeviceTypeAdapter;
import com.brgd.brblmesh.GeneralAdapter.GroupsAdapter;
import com.brgd.brblmesh.GeneralAdapter.SetUpAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
import com.brgd.brblmesh.GeneralAdapter.model.DeviceType;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
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
import com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity;
import com.brgd.brblmesh.Main.Activity.DeleteGroupActivity;
import com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity;
import com.brgd.brblmesh.Main.Activity.DevicesRenameActivity;
import com.brgd.brblmesh.Main.Activity.DevicesSortActivity;
import com.brgd.brblmesh.Main.Activity.GroupDevicesActivity;
import com.brgd.brblmesh.Main.Activity.GroupRenameActivity;
import com.brgd.brblmesh.Main.Activity.GroupSortActivity;
import com.brgd.brblmesh.Main.Activity.MainActivity;
import com.brgd.brblmesh.Main.Activity.ScanDeviceActivity;
import com.brgd.brblmesh.Main.Interface.FirstFragmentListener;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FirstFragment extends Fragment implements FirstFragmentListener {
    TextView actionBarView;
    private List<Integer> addrList;
    ImageView allOffBtn;
    ImageView allOnBtn;
    private ConstraintLayout allOnOffLayout;
    private ImageView allSelectBtn;
    private TextView allSelectText;
    private TextView batchCtrlBtn;
    private BleDeviceAdapter bleDeviceAdapter;
    private List<BleDevice> bleDeviceList;
    private ImageView ctrlGroupBtn;
    DeleteGroups currentDeleteGroups;
    private DeviceType currentDeviceType;
    private Groups currentGroups;
    int currentGroupsId;
    private int currentGroupsSelectIndex;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private RecyclerView deviceRecyclerView;
    private DeviceTypeAdapter deviceTypeAdapter;
    private List<DeviceType> deviceTypeList;
    RecyclerView deviceTypeRecyclerView;
    ImageView dismissView;
    ImageView enableBtn;
    TextView enableText;
    private ImageView groupCtrlBtn;
    private TextView groupCtrlText;
    private ImageView groupLeftBtn;
    private GroupsAdapter groupsAdapter;
    private List<Groups> groupsList;
    RecyclerView groupsRecyclerView;
    ImageView groupsSettingView;
    TextView hiView;
    public boolean isAdd;
    private boolean isGroupCtrl;
    private boolean isInclude0912;
    public boolean isNewIn;
    private boolean isSupportModeLightness;
    private boolean isSupportTimerStatus;
    AlertDialog locationAlertDialog;
    private MainActivity mainActivity;
    private PhoneType phoneType;
    private Realm realm;
    RecyclerView recyclerView;
    ImageView refreshBtn;
    private ConstraintLayout renameLayout;
    ImageView saveView;
    private List<BleDevice> selectList;
    SetUpAdapter setUpAdapter;
    List<String> setUpModelList;
    private ConstraintLayout setupLayout;
    private int pwrType = 0;
    private int cctType = 0;
    private int rgbType = 0;
    private int rgbwType = 0;
    private int rgbcwType = 0;
    private int viewType = 43050;
    private final int[] titleArr = {R.string.setTitle1, R.string.setTitle2, R.string.setTitle3, R.string.setTitle4, R.string.setTitle5, R.string.setTitle6, R.string.setTitle7, R.string.setTitle8};
    int groupSize = 0;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                FirstFragment.this.mainActivity.bleCASDialog();
                return;
            }
            if (Tool.bleNotOnToast(FirstFragment.this.mainActivity.getApplicationContext())) {
                return;
            }
            int id = view.getId();
            if (id == R.id.devices_group_ctrl) {
                if (FirstFragment.this.groupCtrlBtn.isSelected()) {
                    FirstFragment.this.singleControl();
                } else {
                    FirstFragment.this.groupControl();
                }
                FirstFragment.this.deviceRecyclerView.scrollToPosition(0);
                return;
            }
            if (id == R.id.devices_group_ctrl_right) {
                FirstFragment.this.groupToControl();
                return;
            }
            if (id == R.id.device_allOn) {
                FirstFragment.this.allOn();
                return;
            }
            if (id == R.id.device_allOff) {
                FirstFragment.this.allOff();
                return;
            }
            if (id == R.id.device_allSelect) {
                if (FirstFragment.this.allSelectBtn.isSelected()) {
                    FirstFragment.this.cancelAllSelect();
                    return;
                } else {
                    FirstFragment.this.allSelect();
                    return;
                }
            }
            if (id == R.id.devices_batch_ctrl) {
                FirstFragment.this.toBatchControl();
                return;
            }
            if (id == R.id.search_add) {
                FirstFragment.this.singleControl();
                FirstFragment.this.deviceRecyclerView.scrollToPosition(0);
                FirstFragment.this.searchClick();
                return;
            }
            if (id == R.id.devices_enable_alexa) {
                if (!((Boolean) SharePreferenceUtils.get(FirstFragment.this.getActivity(), GlobalVariable.showAlexaGuide, false)).booleanValue()) {
                    FirstFragment.this.startActivity(new Intent(FirstFragment.this.getActivity(), (Class<?>) AlexaGuideActivity.class));
                    return;
                } else {
                    FirstFragment.this.startActivity(new Intent(FirstFragment.this.getActivity(), (Class<?>) AlexaActivity.class));
                    return;
                }
            }
            if (id == R.id.devices_group_setting) {
                FirstFragment.this.setupLayout.setVisibility(0);
                return;
            }
            if (id == R.id.devices_setup_list) {
                FirstFragment.this.setupLayout.setVisibility(4);
                return;
            }
            if (id == R.id.new_group_rename_delete) {
                FirstFragment.this.deviceNameEdit.setText("");
                FirstFragment.this.deviceNameDelete.setSelected(false);
            } else if (id == R.id.new_group_rename_save) {
                FirstFragment.this.saveNewGroup();
            } else if (id == R.id.new_group_rename_dismiss) {
                FirstFragment.this.closeCreateGroupView();
            }
        }
    };

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_first, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        handleNullBug();
        isMainPhone();
        if (this.isGroupCtrl) {
            singleControl();
        }
        queryGroup();
        refreshGroupDevices();
        this.setupLayout.setVisibility(4);
        closeCreateGroupView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            this.setupLayout.setVisibility(4);
            closeCreateGroupView();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
    }

    private void handleNullBug() {
        this.realm.beginTransaction();
        if (((int) this.realm.where(BleDevice.class).isNull(GlobalVariable.did).count()) != 0) {
            this.realm.where(BleDevice.class).isNull(GlobalVariable.did).findAll().deleteAllFromRealm();
        }
        if (((int) this.realm.where(BleDevice.class).count()) == 0) {
            this.phoneType.setPhoneType(3);
        }
        this.realm.commitTransaction();
    }

    private void isMainPhone() {
        if (this.phoneType.getPhoneType() == 1) {
            this.refreshBtn.setVisibility(0);
            this.hiView.setVisibility(8);
            this.actionBarView.setText(R.string.Main1);
        } else if (this.phoneType.getPhoneType() == 2) {
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
        Realm.init(this.mainActivity.getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
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
        this.deviceTypeAdapter.setOnItemClickListener(new DeviceTypeAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda6
            @Override // com.brgd.brblmesh.GeneralAdapter.DeviceTypeAdapter.OnItemClickListener
            public final void OnItemClick(View view2, int i) {
                this.f$0.lambda$initView$0(view2, i);
            }
        });
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.devices_setup_list);
        this.setupLayout = constraintLayout;
        constraintLayout.setOnClickListener(this.disDoubleClickListener);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.devices_setup_recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mainActivity));
        this.setUpModelList = new ArrayList();
        for (int i : this.titleArr) {
            this.setUpModelList.add(getString(i));
        }
        SetUpAdapter setUpAdapter = new SetUpAdapter(this.mainActivity, this.setUpModelList);
        this.setUpAdapter = setUpAdapter;
        this.recyclerView.setAdapter(setUpAdapter);
        this.setUpAdapter.setOnItemClickListener(new SetUpAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda7
            @Override // com.brgd.brblmesh.GeneralAdapter.SetUpAdapter.OnItemClickListener
            public final void onItemClick(View view2, int i2) {
                this.f$0.lambda$initView$1(view2, i2);
            }
        });
        this.renameLayout = (ConstraintLayout) view.findViewById(R.id.new_group_editView);
        this.deviceNameEdit = (EditText) view.findViewById(R.id.new_group_rename_edit);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.new_group_rename_delete);
        this.deviceNameDelete = imageView5;
        imageView5.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                if (charSequence.length() > 0) {
                    if (FirstFragment.this.delete_deviceName_tag) {
                        FirstFragment.this.deviceNameDelete.setSelected(true);
                        FirstFragment.this.deviceNameDelete.setEnabled(true);
                        FirstFragment.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(FirstFragment.this.mainActivity.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                FirstFragment.this.deviceNameDelete.setSelected(false);
                FirstFragment.this.deviceNameDelete.setEnabled(false);
                FirstFragment.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView6 = (ImageView) view.findViewById(R.id.new_group_rename_save);
        this.saveView = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView7 = (ImageView) view.findViewById(R.id.new_group_rename_dismiss);
        this.dismissView = imageView7;
        imageView7.setOnClickListener(this.disDoubleClickListener);
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view, int i) {
        handleClick(i);
    }

    private void initGroupView(View view) {
        this.groupsList = new ArrayList();
        this.groupsRecyclerView = (RecyclerView) view.findViewById(R.id.devices_group_recyclerView);
        this.groupsRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity(), 0, false));
        GroupsAdapter groupsAdapter = new GroupsAdapter(getActivity(), this.groupsList);
        this.groupsAdapter = groupsAdapter;
        this.groupsRecyclerView.setAdapter(groupsAdapter);
        this.currentGroupsSelectIndex = 0;
        this.groupsAdapter.setOnItemButtonClickListener(new GroupsAdapter.OnItemButtonClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralAdapter.GroupsAdapter.OnItemButtonClickListener
            public final void OnItemButtonClick(View view2, int i) {
                this.f$0.lambda$initGroupView$2(view2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initGroupView$2(View view, int i) {
        if (this.isGroupCtrl) {
            singleControl();
        }
        this.currentGroupsSelectIndex = i;
        this.currentGroups.isSelect = false;
        Groups groups = this.groupsList.get(this.currentGroupsSelectIndex);
        this.currentGroups = groups;
        groups.isSelect = true;
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
        BleDeviceAdapter bleDeviceAdapter = new BleDeviceAdapter(getActivity(), this.bleDeviceList);
        this.bleDeviceAdapter = bleDeviceAdapter;
        this.deviceRecyclerView.setAdapter(bleDeviceAdapter);
        this.deviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        this.bleDeviceAdapter.setOnPowerClickListener(new BleDeviceAdapter.OnPowerClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment.2
            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.OnPowerClickListener
            public void onOffClick(View view2, int i, Boolean bool) {
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    FirstFragment.this.mainActivity.bleCASDialog();
                } else {
                    if (Tool.bleNotOnToast(FirstFragment.this.mainActivity.getApplicationContext())) {
                        return;
                    }
                    GlobalBluetooth.getInstance().singleOnOff(((BleDevice) FirstFragment.this.bleDeviceList.get(i)).getAddr(), bool.booleanValue());
                }
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.OnPowerClickListener
            public void onOptionClick(View view2, int i, View view3) {
                BleDevice bleDevice = (BleDevice) FirstFragment.this.bleDeviceList.get(i);
                if (FirstFragment.this.isGroupCtrl) {
                    int iIntValue = 0;
                    if (view2.isSelected()) {
                        bleDevice.isSelect = false;
                        view2.setSelected(false);
                        view3.setSelected(false);
                        Iterator it = FirstFragment.this.addrList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer num = (Integer) it.next();
                            if (num.intValue() == bleDevice.getAddr()) {
                                FirstFragment.this.addrList.remove(num);
                                break;
                            }
                        }
                    } else {
                        bleDevice.isSelect = true;
                        view2.setSelected(true);
                        view3.setSelected(true);
                        Iterator it2 = FirstFragment.this.addrList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            Integer num2 = (Integer) it2.next();
                            if (num2.intValue() == bleDevice.getAddr()) {
                                iIntValue = num2.intValue();
                                break;
                            }
                        }
                        if (iIntValue == 0) {
                            FirstFragment.this.addrList.add(Integer.valueOf(bleDevice.getAddr()));
                        }
                    }
                    FirstFragment.this.bleDeviceAdapter.notifyItemChanged(i);
                }
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.OnPowerClickListener
            public void onSelectClick(View view2, int i, View view3) {
                BleDevice bleDevice = (BleDevice) FirstFragment.this.bleDeviceList.get(i);
                int iIntValue = 0;
                if (FirstFragment.this.isGroupCtrl) {
                    if (view2.isSelected()) {
                        bleDevice.isSelect = false;
                        view2.setSelected(false);
                        view3.setSelected(false);
                        Iterator it = FirstFragment.this.addrList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer num = (Integer) it.next();
                            if (num.intValue() == bleDevice.getAddr()) {
                                FirstFragment.this.addrList.remove(num);
                                break;
                            }
                        }
                    } else {
                        bleDevice.isSelect = true;
                        view2.setSelected(true);
                        view3.setSelected(true);
                        Iterator it2 = FirstFragment.this.addrList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            Integer num2 = (Integer) it2.next();
                            if (num2.intValue() == bleDevice.getAddr()) {
                                iIntValue = num2.intValue();
                                break;
                            }
                        }
                        if (iIntValue == 0) {
                            FirstFragment.this.addrList.add(Integer.valueOf(bleDevice.getAddr()));
                        }
                    }
                    FirstFragment.this.bleDeviceAdapter.notifyItemChanged(i);
                    return;
                }
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    FirstFragment.this.mainActivity.bleCASDialog();
                    return;
                }
                if (Tool.bleNotOnToast(FirstFragment.this.mainActivity.getApplicationContext())) {
                    return;
                }
                Intent intent = new Intent(FirstFragment.this.getActivity(), (Class<?>) DeviceCtrlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.ctrlAddr, bleDevice.getAddr());
                bundle.putInt(GlobalVariable.viewType, bleDevice.getType());
                bundle.putBoolean(GlobalVariable.timerStatus, bleDevice.isSupportTimerStatus());
                bundle.putBoolean("isSleep", bleDevice.isSupportSceneControl() && !Tool.isNot65V(bleDevice.getVersion()));
                bundle.putBoolean(GlobalVariable.isInclude0912, Tool.isChip0912_20(bleDevice.getVersion()));
                bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, bleDevice.isSupportModeLightness());
                intent.putExtra(GlobalVariable.toCtrl, bundle);
                FirstFragment.this.startActivity(intent);
            }
        });
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
        BLSBleLight.removeAllDevice();
        RealmResults<BleDevice> realmResultsSort = this.realm.where(BleDevice.class).findAll().sort(GlobalVariable.index);
        for (BleDevice bleDevice : realmResultsSort) {
            BLEDeviceInfo bLEDeviceInfo = new BLEDeviceInfo();
            bLEDeviceInfo.did = bleDevice.getDid();
            bLEDeviceInfo.addr = bleDevice.getAddr();
            bLEDeviceInfo.type = bleDevice.getType();
            BLSBleLight.addDevice(bLEDeviceInfo);
        }
        RealmResults realmResultsSort2 = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll().sort("groupIndex");
        if (this.currentGroups.getGroupId() == 0) {
            this.bleDeviceList.addAll(realmResultsSort);
        } else {
            this.bleDeviceList.addAll(realmResultsSort2);
        }
        BleDeviceAdapter bleDeviceAdapter = this.bleDeviceAdapter;
        if (bleDeviceAdapter != null) {
            bleDeviceAdapter.notifyDataSetChanged();
        }
        refreshDeviceType();
        if (this.phoneType.getPhoneType() == 1) {
            if (((int) this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).count()) != 0) {
                this.enableBtn.setVisibility(0);
                this.enableText.setVisibility(0);
                return;
            } else {
                this.enableBtn.setVisibility(4);
                this.enableText.setVisibility(4);
                return;
            }
        }
        this.enableBtn.setVisibility(4);
        this.enableText.setVisibility(4);
    }

    private void refreshDeviceType() {
        this.deviceTypeList.clear();
        this.deviceTypeAdapter.notifyDataSetChanged();
        if (this.bleDeviceList.isEmpty()) {
            return;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (BleDevice bleDevice : this.bleDeviceList) {
            if (bleDevice.getType() == 43049) {
                i++;
            } else if (bleDevice.getType() == 43051) {
                i2++;
            } else if (bleDevice.getType() == 43168) {
                i3++;
            } else if (bleDevice.getType() == 43169) {
                i4++;
            } else if (bleDevice.getType() == 43050) {
                i5++;
            }
        }
        this.deviceTypeList.clear();
        DeviceType deviceType = new DeviceType();
        deviceType.name = getString(R.string.Mix);
        deviceType.type = 0;
        this.deviceTypeList.add(deviceType);
        if (i > 0) {
            DeviceType deviceType2 = new DeviceType();
            deviceType2.name = getString(R.string.pwr);
            deviceType2.type = 43049;
            this.deviceTypeList.add(deviceType2);
        }
        if (i2 > 0) {
            DeviceType deviceType3 = new DeviceType();
            deviceType3.name = getString(R.string.cct);
            deviceType3.type = 43051;
            this.deviceTypeList.add(deviceType3);
        }
        if (i3 > 0) {
            DeviceType deviceType4 = new DeviceType();
            deviceType4.name = getString(R.string.rgb);
            deviceType4.type = 43168;
            this.deviceTypeList.add(deviceType4);
        }
        if (i4 > 0) {
            DeviceType deviceType5 = new DeviceType();
            deviceType5.name = getString(R.string.rgbw);
            deviceType5.type = 43169;
            this.deviceTypeList.add(deviceType5);
        }
        if (i5 > 0) {
            DeviceType deviceType6 = new DeviceType();
            deviceType6.name = getString(R.string.rgbcw);
            deviceType6.type = 43050;
            this.deviceTypeList.add(deviceType6);
        }
        if (this.deviceTypeList.size() == 2) {
            this.deviceTypeRecyclerView.setVisibility(8);
        } else {
            this.deviceTypeRecyclerView.setVisibility(0);
        }
        DeviceType deviceType7 = this.deviceTypeList.get(0);
        this.currentDeviceType = deviceType7;
        deviceType7.isSelect = true;
        this.deviceTypeAdapter.notifyDataSetChanged();
    }

    private void refreshTypeDevice() {
        RealmResults realmResultsSort;
        this.bleDeviceList.clear();
        if (this.currentDeviceType.type == 0) {
            if (this.currentGroups.getGroupId() == 0) {
                realmResultsSort = this.realm.where(BleDevice.class).findAll().sort(GlobalVariable.index);
            } else {
                realmResultsSort = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).findAll().sort("groupIndex");
            }
        } else if (this.currentGroups.getGroupId() == 0) {
            realmResultsSort = this.realm.where(BleDevice.class).equalTo(GlobalVariable.type, Integer.valueOf(this.currentDeviceType.type)).findAll().sort(GlobalVariable.index);
        } else {
            realmResultsSort = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentGroups.getGroupId())).equalTo(GlobalVariable.type, Integer.valueOf(this.currentDeviceType.type)).findAll().sort("groupIndex");
        }
        this.bleDeviceList.addAll(realmResultsSort);
        BleDeviceAdapter bleDeviceAdapter = this.bleDeviceAdapter;
        if (bleDeviceAdapter != null) {
            bleDeviceAdapter.notifyDataSetChanged();
        }
    }

    private void resetType() {
        this.selectList.clear();
        this.pwrType = 0;
        this.cctType = 0;
        this.rgbType = 0;
        this.rgbwType = 0;
        this.rgbcwType = 0;
        this.viewType = 43050;
    }

    private void queryType(List<BleDevice> list) {
        int i;
        int i2;
        Iterator<BleDevice> it = list.iterator();
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
            }
        }
        if (this.rgbcwType != 0 || (((i = this.rgbwType) != 0 && this.cctType != 0) || ((i2 = this.rgbType) != 0 && this.cctType != 0))) {
            this.viewType = 43050;
            return;
        }
        if (i != 0 || (i2 != 0 && this.pwrType != 0)) {
            this.viewType = 43169;
            return;
        }
        if (i2 != 0) {
            this.viewType = 43168;
        } else if (this.cctType != 0) {
            this.viewType = 43051;
        } else if (this.pwrType != 0) {
            this.viewType = 43049;
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
            for (BleDevice bleDevice : this.bleDeviceList) {
                bleDevice.isGroupStatus = true;
                bleDevice.isSelect = false;
            }
            this.bleDeviceAdapter.notifyDataSetChanged();
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
        for (BleDevice bleDevice : this.bleDeviceList) {
            bleDevice.isGroupStatus = false;
            bleDevice.isSelect = false;
        }
        this.bleDeviceAdapter.notifyDataSetChanged();
        this.addrList.clear();
        resetType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void groupToControl() {
        if (!this.bleDeviceList.isEmpty()) {
            this.isSupportTimerStatus = false;
            Iterator<BleDevice> it = this.bleDeviceList.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!it.next().isSupportTimerStatus()) {
                        break;
                    }
                } else {
                    this.isSupportTimerStatus = true;
                    break;
                }
            }
            this.isInclude0912 = false;
            Iterator<BleDevice> it2 = this.bleDeviceList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                } else if (Tool.isChip0912_20(it2.next().getVersion())) {
                    this.isInclude0912 = true;
                    break;
                }
            }
            this.isSupportModeLightness = false;
            Iterator<BleDevice> it3 = this.bleDeviceList.iterator();
            while (true) {
                if (it3.hasNext()) {
                    if (!it3.next().isSupportModeLightness()) {
                        break;
                    }
                } else {
                    this.isSupportModeLightness = true;
                    break;
                }
            }
            resetType();
            queryType(this.bleDeviceList);
            final Intent intent = new Intent(getActivity(), (Class<?>) DeviceCtrlActivity.class);
            if (this.phoneType.getPhoneType() == 1 && this.currentDeviceType.type == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.groupId, this.currentGroups.getGroupId());
                bundle.putInt(GlobalVariable.type, 43050);
                bundle.putInt(GlobalVariable.tempGroupId, -1);
                bundle.putInt(GlobalVariable.viewType, this.viewType);
                bundle.putBoolean(GlobalVariable.timerStatus, this.isSupportTimerStatus);
                bundle.putString(GlobalVariable.name, this.currentGroups.getGroupName());
                bundle.putBoolean(GlobalVariable.isInclude0912, this.isInclude0912);
                bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, this.isSupportModeLightness);
                intent.putExtra(GlobalVariable.groupToCtrl, bundle);
                startActivity(intent);
                return;
            }
            this.addrList.clear();
            Iterator<BleDevice> it4 = this.bleDeviceList.iterator();
            while (it4.hasNext()) {
                this.addrList.add(Integer.valueOf(it4.next().getAddr()));
            }
            final String str = this.currentGroups.getGroupName() + "(" + this.addrList.size() + " " + getString(R.string.devices) + ")";
            int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
            final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
            if (iBroadcastTempGroupId > 0) {
                this.mainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$groupToControl$3(dialogCreateLoadingDialog, str, intent);
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
    public /* synthetic */ void lambda$groupToControl$3(Dialog dialog, String str, Intent intent) {
        loadDialogUtils.closeDialog(dialog);
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalVariable.groupId, -1);
        bundle.putInt(GlobalVariable.type, 43050);
        bundle.putInt(GlobalVariable.tempGroupId, 253);
        bundle.putInt(GlobalVariable.viewType, this.viewType);
        bundle.putBoolean(GlobalVariable.timerStatus, this.isSupportTimerStatus);
        bundle.putString(GlobalVariable.name, str);
        bundle.putBoolean(GlobalVariable.isInclude0912, this.isInclude0912);
        bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, this.isSupportModeLightness);
        intent.putExtra(GlobalVariable.groupToCtrl, bundle);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allOn() {
        if (this.bleDeviceList.isEmpty()) {
            return;
        }
        if (this.phoneType.getPhoneType() == 1 && this.currentDeviceType.type == 0) {
            GlobalBluetooth.getInstance().groupOnOff(43050, this.currentGroups.getGroupId(), true);
            return;
        }
        this.addrList.clear();
        Iterator<BleDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            this.addrList.add(Integer.valueOf(it.next().getAddr()));
        }
        int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
        final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
        if (iBroadcastTempGroupId > 0) {
            this.mainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FirstFragment.lambda$allOn$4(dialogCreateLoadingDialog);
                }
            }, iBroadcastTempGroupId);
        } else {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
        }
    }

    static /* synthetic */ void lambda$allOn$4(Dialog dialog) {
        loadDialogUtils.closeDialog(dialog);
        GlobalBluetooth.getInstance().groupOnOff(43050, 253, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allOff() {
        if (this.bleDeviceList.isEmpty()) {
            return;
        }
        if (this.phoneType.getPhoneType() == 1 && this.currentDeviceType.type == 0) {
            GlobalBluetooth.getInstance().groupOnOff(43050, this.currentGroups.getGroupId(), false);
            return;
        }
        this.addrList.clear();
        Iterator<BleDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            this.addrList.add(Integer.valueOf(it.next().getAddr()));
        }
        int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
        final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
        if (iBroadcastTempGroupId > 0) {
            this.mainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FirstFragment.lambda$allOff$5(dialogCreateLoadingDialog);
                }
            }, iBroadcastTempGroupId);
        } else {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
        }
    }

    static /* synthetic */ void lambda$allOff$5(Dialog dialog) {
        loadDialogUtils.closeDialog(dialog);
        GlobalBluetooth.getInstance().groupOnOff(43050, 253, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allSelect() {
        this.allSelectBtn.setSelected(true);
        this.allSelectText.setText(getString(R.string.Cancel));
        this.allSelectText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorWhite));
        this.addrList.clear();
        for (BleDevice bleDevice : this.bleDeviceList) {
            bleDevice.isSelect = true;
            this.addrList.add(Integer.valueOf(bleDevice.getAddr()));
        }
        this.bleDeviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAllSelect() {
        this.allSelectBtn.setSelected(false);
        this.allSelectText.setText(getString(R.string.AllSelect));
        this.allSelectText.setTextColor(ContextCompat.getColor(GlobalApplication.getMyApplication().getApplicationContext(), R.color.colorText));
        Iterator<BleDevice> it = this.bleDeviceList.iterator();
        while (it.hasNext()) {
            it.next().isSelect = false;
        }
        this.addrList.clear();
        this.bleDeviceAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toBatchControl() {
        int i = 0;
        if (this.addrList.size() > 1) {
            if (this.phoneType.getPhoneType() == 2 || ((this.phoneType.getPhoneType() == 1 && this.addrList.size() != this.bleDeviceList.size()) || this.currentDeviceType.type != 0)) {
                this.isSupportTimerStatus = false;
                this.isInclude0912 = false;
                this.isSupportModeLightness = false;
                resetType();
                int i2 = 0;
                int i3 = 0;
                for (BleDevice bleDevice : this.bleDeviceList) {
                    if (bleDevice.isSelect) {
                        this.selectList.add(bleDevice);
                        if (!bleDevice.isSupportTimerStatus() && i == 0) {
                            i++;
                        }
                        if (Tool.isChip0912_20(bleDevice.getVersion()) && i2 == 0) {
                            i2++;
                        }
                        if (!bleDevice.isSupportModeLightness() && i3 == 0) {
                            i3++;
                        }
                    }
                }
                queryType(this.selectList);
                if (i == 0) {
                    this.isSupportTimerStatus = true;
                }
                if (i2 > 0) {
                    this.isInclude0912 = true;
                }
                if (i3 == 0) {
                    this.isSupportModeLightness = true;
                }
                final String str = this.currentGroups.getGroupName() + "(" + this.addrList.size() + " " + getString(R.string.devices) + ")";
                int iBroadcastTempGroupId = BLSBleLight.broadcastTempGroupId(Tool.getListToArray(this.addrList));
                final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
                if (iBroadcastTempGroupId > 0) {
                    this.mainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$toBatchControl$6(dialogCreateLoadingDialog, str);
                        }
                    }, iBroadcastTempGroupId);
                    return;
                } else {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.actionTimeOut, 1);
                    return;
                }
            }
            if (this.phoneType.getPhoneType() == 1 && this.bleDeviceList.size() == this.addrList.size()) {
                groupToControl();
                return;
            }
            return;
        }
        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.atLeastTwoCtrl, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toBatchControl$6(Dialog dialog, String str) {
        loadDialogUtils.closeDialog(dialog);
        Intent intent = new Intent(getActivity(), (Class<?>) DeviceCtrlActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalVariable.groupId, -1);
        bundle.putInt(GlobalVariable.type, 43050);
        bundle.putInt(GlobalVariable.tempGroupId, 253);
        bundle.putInt(GlobalVariable.viewType, this.viewType);
        bundle.putBoolean(GlobalVariable.timerStatus, this.isSupportTimerStatus);
        bundle.putString(GlobalVariable.name, str);
        bundle.putBoolean(GlobalVariable.isInclude0912, this.isInclude0912);
        bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, this.isSupportModeLightness);
        intent.putExtra(GlobalVariable.groupToCtrl, bundle);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchClick() {
        if (((int) this.realm.where(BleDevice.class).count()) >= 250) {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.addDeviceMax, 0);
            return;
        }
        this.isAdd = true;
        if (Tool.checkLocationPermission()) {
            this.mainActivity.getLocationPermission();
        } else {
            locationDialog();
        }
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this.mainActivity).setMessage(R.string.locationAddressRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$7(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$8(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$7(DialogInterface dialogInterface, int i) {
        this.mainActivity.getLocationPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$8(DialogInterface dialogInterface, int i) {
        if (this.isNewIn && !this.isAdd) {
            queryGroup();
            refreshGroupDevices();
            this.setupLayout.setVisibility(4);
        }
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    @Override // com.brgd.brblmesh.Main.Interface.FirstFragmentListener
    public void toScanDevice() {
        BLSBleLight.checkPermission();
        startActivity(new Intent(getActivity(), (Class<?>) ScanDeviceActivity.class));
    }

    private void showCreateGroupView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCreateGroupView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNewGroup() {
        String string = this.deviceNameEdit.getText().toString();
        if (string.isEmpty()) {
            GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.enterValid, 0);
            return;
        }
        if (Tool.checkStringIsSpaces(string)) {
            GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.canNotSpaces, 0);
            return;
        }
        String strTrim = string.trim();
        if (Tool.checkStringIsSpaces(strTrim)) {
            GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.canNotSpaces, 0);
            return;
        }
        if (strTrim.length() > 26) {
            GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.InputTip, 0);
            return;
        }
        Iterator it = this.realm.where(Groups.class).findAll().iterator();
        while (it.hasNext()) {
            if (strTrim.equalsIgnoreCase(((Groups) it.next()).getGroupName())) {
                GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.nameExist, 0);
                return;
            }
        }
        createGroup();
        final CustomDialog customDialog = new CustomDialog(this.mainActivity);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), 0.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.createGroupSuccess);
        customDialog.setMessage(R.string.addDeviceToGroup);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda9
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$saveNewGroup$9(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.later, new CustomDialog.onCancelClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.FirstFragment$$ExternalSyntheticLambda10
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onCancelClickListener
            public final void onCancelClick() {
                this.f$0.lambda$saveNewGroup$10(customDialog);
            }
        });
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveNewGroup$9(CustomDialog customDialog) {
        customDialog.dismiss();
        this.isNewIn = true;
        if (this.phoneType.getPhoneType() == 1) {
            this.isAdd = false;
            if (Tool.checkLocationPermission()) {
                this.mainActivity.getLocationPermission();
                return;
            } else {
                locationDialog();
                return;
            }
        }
        Intent intent = new Intent(this.mainActivity, (Class<?>) GroupDevicesActivity.class);
        intent.putExtra(GlobalVariable.toGroupDevice, this.isNewIn);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveNewGroup$10(CustomDialog customDialog) {
        queryGroup();
        refreshGroupDevices();
        this.setupLayout.setVisibility(4);
        customDialog.dismiss();
    }

    private void createGroup() {
        this.groupSize = (int) this.realm.where(Groups.class).count();
        DeleteGroups deleteGroups = (DeleteGroups) this.realm.where(DeleteGroups.class).findFirst();
        this.currentDeleteGroups = deleteGroups;
        if (deleteGroups != null) {
            this.currentGroupsId = deleteGroups.getGroupId();
        } else {
            this.currentGroupsId = this.groupSize;
        }
        this.realm.beginTransaction();
        Groups groups = (Groups) this.realm.createObject(Groups.class);
        groups.setIndex(this.groupSize);
        groups.setGroupName(this.deviceNameEdit.getText().toString());
        groups.setGroupId(this.currentGroupsId);
        this.realm.commitTransaction();
        if (this.currentDeleteGroups != null) {
            this.realm.beginTransaction();
            this.currentDeleteGroups.deleteFromRealm();
            this.realm.commitTransaction();
        }
        closeCreateGroupView();
    }

    private void handleClick(int i) {
        if (Tool.bleNotOnToast(this.mainActivity.getApplicationContext())) {
            return;
        }
        switch (i) {
            case 0:
                if (((int) this.realm.where(Groups.class).count()) == 128) {
                    GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.maxGroupNum, 0);
                } else {
                    showCreateGroupView();
                }
                break;
            case 1:
                if (((int) this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).count()) != 0) {
                    this.isNewIn = false;
                    if (this.phoneType.getPhoneType() == 1) {
                        this.isAdd = false;
                        if (Tool.checkLocationPermission()) {
                            this.mainActivity.getLocationPermission();
                        } else {
                            locationDialog();
                        }
                    } else {
                        Intent intent = new Intent(this.mainActivity, (Class<?>) GroupDevicesActivity.class);
                        intent.putExtra(GlobalVariable.toGroupDevice, this.isNewIn);
                        startActivity(intent);
                    }
                } else {
                    GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.NoGroupTip, 0);
                }
                break;
            case 2:
                startActivity(new Intent(this.mainActivity, (Class<?>) GroupSortActivity.class));
                break;
            case 3:
                startActivity(new Intent(this.mainActivity, (Class<?>) GroupRenameActivity.class));
                break;
            case 4:
                startActivity(new Intent(this.mainActivity, (Class<?>) DeleteGroupActivity.class));
                break;
            case 5:
                startActivity(new Intent(this.mainActivity, (Class<?>) DevicesSortActivity.class));
                break;
            case 6:
                startActivity(new Intent(this.mainActivity, (Class<?>) DevicesRenameActivity.class));
                break;
            case 7:
                startActivity(new Intent(this.mainActivity, (Class<?>) DeleteDevicesActivity.class));
                break;
        }
    }
}

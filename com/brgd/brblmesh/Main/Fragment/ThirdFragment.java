package com.brgd.brblmesh.Main.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSONObject;
import com.brgd.brblmesh.GeneralActivity.LaunchActivity;
import com.brgd.brblmesh.GeneralAdapter.MoreAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.MoreModel;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarTimer;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.AESOperator;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalThreadPools;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Activity.AboutAssistantActivity;
import com.brgd.brblmesh.Main.Activity.AlexaActivity;
import com.brgd.brblmesh.Main.Activity.AlexaGuideActivity;
import com.brgd.brblmesh.Main.Activity.BindActivity;
import com.brgd.brblmesh.Main.Activity.DeleteDevicesActivity$$ExternalSyntheticLambda1;
import com.brgd.brblmesh.Main.Activity.InstructionsActivity;
import com.brgd.brblmesh.Main.Activity.InstructionsAlexaActivity;
import com.brgd.brblmesh.Main.Activity.MainActivity;
import com.brgd.brblmesh.Main.Activity.OnekeyResetActivity;
import com.brgd.brblmesh.Main.Activity.OtaActivity;
import com.brgd.brblmesh.Main.Activity.RadarMainActivity;
import com.brgd.brblmesh.Main.Activity.ShareActivity;
import com.brgd.brblmesh.Main.Activity.ShareRadarDvActivity;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import j$.util.Objects;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class ThirdFragment extends Fragment {
    private int bleDeviceCount;
    private int fixedCount;
    Dialog loadingDialog;
    private Timer mTimer;
    private MainActivity mainActivity;
    private int mainDvCount;
    private HashMap<String, Object> map;
    MoreAdapter moreAdapter;
    RecyclerView moreListRecyclerView;
    List<MoreModel> moreModelList;
    private PhoneType phoneType;
    private int queryCount = 0;
    private int radarDeviceCount;
    private RadarMainActivity radarMainActivity;
    private RadarPhoneType radarPhoneType;
    Realm realm;

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (Tool.isToRadar()) {
            this.radarMainActivity = (RadarMainActivity) context;
        } else {
            this.mainActivity = (MainActivity) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_third, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.phoneType = (PhoneType) this.realm.where(PhoneType.class).findFirst();
        this.radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
        this.bleDeviceCount = (int) this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).count();
        this.radarDeviceCount = (int) this.realm.where(RadarDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).count();
        this.mainDvCount = (int) this.realm.where(BleDevice.class).equalTo("isSupportGroupMain", (Boolean) true).count();
        this.fixedCount = (int) this.realm.where(BleDevice.class).equalTo("isSupportFixedGroup", (Boolean) true).count();
        refreshMoreModel();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
    }

    private void initView(View view) {
        Realm.init(GlobalApplication.getMyApplication().getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        this.radarPhoneType = (RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst();
        this.bleDeviceCount = (int) this.realm.where(BleDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).count();
        this.radarDeviceCount = (int) this.realm.where(RadarDevice.class).equalTo("isSupportAlexaEnable", (Boolean) true).count();
        this.mainDvCount = (int) this.realm.where(BleDevice.class).equalTo("isSupportGroupMain", (Boolean) true).count();
        this.fixedCount = (int) this.realm.where(BleDevice.class).equalTo("isSupportFixedGroup", (Boolean) true).count();
        this.moreListRecyclerView = (RecyclerView) view.findViewById(R.id.more_list_recyclerView);
        this.moreListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.moreModelList = new ArrayList();
        MoreAdapter moreAdapter = new MoreAdapter(getActivity(), this.moreModelList);
        this.moreAdapter = moreAdapter;
        this.moreListRecyclerView.setAdapter(moreAdapter);
        this.moreAdapter.setOnMoreClickListener(new MoreAdapter.OnMoreClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda38
            @Override // com.brgd.brblmesh.GeneralAdapter.MoreAdapter.OnMoreClickListener
            public final void onMoreClick(View view2, int i) {
                this.f$0.lambda$initView$0(view2, i);
            }
        });
        refreshMoreModel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        Intent intent;
        if (i == 0) {
            startActivity(new Intent(getActivity(), (Class<?>) AboutAssistantActivity.class));
            return;
        }
        if (i == 1) {
            startActivity(new Intent(getActivity(), (Class<?>) InstructionsActivity.class));
            return;
        }
        if (i == 2) {
            if ((!Tool.isToRadar() && this.phoneType.getPhoneType() == 1) || (Tool.isToRadar() && this.radarPhoneType.getPhoneType() == 1)) {
                if (Tool.isToRadar()) {
                    intent = new Intent(getActivity(), (Class<?>) ShareRadarDvActivity.class);
                } else {
                    intent = new Intent(getActivity(), (Class<?>) ShareActivity.class);
                }
                startActivity(intent);
                return;
            }
            if (Tool.checkCameraPermission()) {
                if (Tool.isToRadar()) {
                    this.radarMainActivity.toScanDevice();
                    return;
                } else {
                    this.mainActivity.toScanDevice();
                    return;
                }
            }
            if (Tool.isToRadar()) {
                this.radarMainActivity.qrDialog();
                return;
            } else {
                this.mainActivity.qrDialog();
                return;
            }
        }
        if (i == 3) {
            startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("http://www.gdszbrgd.com/news/html/?435.html")), "Browse"));
            return;
        }
        if (i == 4) {
            if ((!Tool.isToRadar() && this.phoneType.getPhoneType() == 1) || (Tool.isToRadar() && this.radarPhoneType.getPhoneType() == 1)) {
                if (this.bleDeviceCount != 0) {
                    if (this.mainDvCount != 0 && this.fixedCount > 1) {
                        startActivity(new Intent(getActivity(), (Class<?>) BindActivity.class));
                        return;
                    } else {
                        GlobalToast.showText(getActivity(), R.string.noSupportBind, 1);
                        return;
                    }
                }
                oneKeyReset();
                return;
            }
            oneKeyReset();
            return;
        }
        if (i != 5) {
            if (i == 6) {
                if ((!Tool.isToRadar() && this.phoneType.getPhoneType() == 1) || (Tool.isToRadar() && this.radarPhoneType.getPhoneType() == 1)) {
                    if (this.bleDeviceCount != 0) {
                        startActivity(new Intent(getActivity(), (Class<?>) InstructionsAlexaActivity.class));
                        return;
                    } else {
                        exitCtrl();
                        return;
                    }
                }
                exitCtrl();
                return;
            }
            if (i == 7) {
                oneKeyReset();
                return;
            }
            if (i == 8) {
                exportAction();
                return;
            } else if (i == 9) {
                toOTA();
                return;
            } else {
                if (i == 10) {
                    exitCtrl();
                    return;
                }
                return;
            }
        }
        if ((!Tool.isToRadar() && this.phoneType.getPhoneType() == 1) || (Tool.isToRadar() && this.radarPhoneType.getPhoneType() == 1)) {
            if (this.bleDeviceCount != 0) {
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    if (Tool.isToRadar()) {
                        this.radarMainActivity.bleCASDialog();
                        return;
                    } else {
                        this.mainActivity.bleCASDialog();
                        return;
                    }
                }
                if (Tool.isToRadar()) {
                    if (Tool.bleNotOnToast(this.radarMainActivity.getApplicationContext())) {
                        return;
                    }
                } else if (Tool.bleNotOnToast(this.mainActivity.getApplicationContext())) {
                    return;
                }
                if (!((Boolean) SharePreferenceUtils.get(getActivity(), GlobalVariable.showAlexaGuide, false)).booleanValue()) {
                    startActivity(new Intent(getActivity(), (Class<?>) AlexaGuideActivity.class));
                    return;
                } else {
                    startActivity(new Intent(getActivity(), (Class<?>) AlexaActivity.class));
                    return;
                }
            }
            exportAction();
            return;
        }
        importAction();
    }

    public void oneKeyReset() {
        if (((int) this.realm.where(BleDevice.class).equalTo("otaTag", (Integer) 2).count()) > 0) {
            GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.ota_fail_deletes, 1);
            return;
        }
        final CustomDialog customDialog = new CustomDialog(getActivity());
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.moreListRecyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.confirmReset);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda20
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$oneKeyReset$1(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$oneKeyReset$1(CustomDialog customDialog) {
        customDialog.dismiss();
        if (Tool.isToRadar()) {
            startActivity(new Intent(this.radarMainActivity, (Class<?>) OnekeyResetActivity.class));
            this.radarMainActivity.finish();
        } else {
            startActivity(new Intent(this.mainActivity, (Class<?>) OnekeyResetActivity.class));
            this.mainActivity.finish();
        }
    }

    private void exportAction() {
        final CustomDialog customDialog = new CustomDialog(getActivity());
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.moreListRecyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.exportTip);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda35
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$exportAction$2(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$exportAction$2(CustomDialog customDialog) {
        customDialog.dismiss();
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(requireActivity(), R.string.Loading);
        }
        this.queryCount = 0;
        startTimer();
        initExportData();
    }

    private void initExportData() {
        if (this.map == null) {
            this.map = new HashMap<>();
        }
        this.map.clear();
        this.map.put(GlobalVariable.CTRLKEY, (String) SharePreferenceUtils.get(requireActivity().getApplicationContext(), GlobalVariable.ctrlKey, ""));
        this.map.put(GlobalVariable.R_CTRLKEY, (String) SharePreferenceUtils.get(requireActivity().getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, ""));
        getRadarDevices(this.map);
        getRadarGroups(this.map);
        getDeleteRadarGroups(this.map);
        getRadarTimer(this.map);
        getRadarPhoneType(this.map);
        getGroups(this.map);
        getBleDevices(this.map);
        getDiyColors(this.map);
        getModDiyColors(this.map);
        getMods(this.map);
        getScenes(this.map);
        getPhoneType(this.map);
        getSceneDevices(this.map);
        getDeleteGroups(this.map);
        getFixGroups(this.map);
        getDeleteFixGroups(this.map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exportData() {
        GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$exportData$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$exportData$3() {
        String string = new JSONObject(this.map).toString();
        try {
            string = AESOperator.getInstance().encrypt(string);
        } catch (Exception e) {
            Log.e("exportData", "转json字符串错误: ", e);
        }
        File appFile = Tool.getAppFile(requireActivity());
        String str = GlobalVariable.EXPORT_DIRECTORY + Tool.getFileName() + GlobalVariable.EXPORT_FILETYPE;
        File file = new File(appFile, str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.close();
        } catch (IOException e2) {
            Log.e("exportData", "写入文件错误: ", e2);
        }
        loadDialogUtils.closeDialog(this.loadingDialog);
        this.loadingDialog = null;
        shareFile(file);
        String str2 = (String) SharePreferenceUtils.get(requireActivity(), GlobalVariable.EXPORT_DIRECTORY, "");
        if (str2 != null) {
            Tool.deleteFile(requireActivity(), str2);
        }
        SharePreferenceUtils.put(requireActivity(), GlobalVariable.EXPORT_DIRECTORY, str);
    }

    public void shareFile(File file) {
        Uri uriForFile = FileProvider.getUriForFile(requireActivity(), requireActivity().getApplicationContext().getPackageName() + ".fileProvider", file);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        intent.setType("application/json");
        startActivity(Intent.createChooser(intent, "Exporting backups files"));
    }

    private void startTimer() {
        stopTimer();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (ThirdFragment.this.queryCount == 16) {
                    ThirdFragment.this.stopTimer();
                    ThirdFragment.this.exportData();
                }
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    private void getRadarDevices(final HashMap<String, Object> map) {
        this.realm.where(RadarDevice.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda24
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getRadarDevices$5(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarDevices$5(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getRadarDevices$4(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarDevices$4(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RadarDevice radarDevice = (RadarDevice) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.DID, radarDevice.getDid());
            map2.put(GlobalVariable.NAME, radarDevice.getName());
            map2.put(GlobalVariable.KEY, radarDevice.getKey());
            map2.put(GlobalVariable.VERSION, radarDevice.getVersion());
            map2.put(GlobalVariable.ADDR, Integer.valueOf(radarDevice.getAddr()));
            map2.put(GlobalVariable.TYPE, Integer.valueOf(radarDevice.getType()));
            map2.put(GlobalVariable.GROUPID, Integer.valueOf(radarDevice.getGroupId()));
            map2.put(GlobalVariable.INDEX, Integer.valueOf(radarDevice.getIndex()));
            map2.put("isSupportAlexaEnable", Boolean.valueOf(radarDevice.isSupportAlexaEnable()));
            map2.put("isConfigAlexa", Boolean.valueOf(radarDevice.isConfigAlexa()));
            map2.put("isSupportFixedGroup", Boolean.valueOf(radarDevice.isSupportFixedGroup()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.RADAR_DV, arrayList);
        this.queryCount++;
    }

    private void getRadarGroups(final HashMap<String, Object> map) {
        this.realm.where(RadarGroup.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda12
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getRadarGroups$7(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarGroups$7(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getRadarGroups$6(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarGroups$6(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RadarGroup radarGroup = (RadarGroup) it.next();
            HashMap map2 = new HashMap();
            map2.put("fixedId", Integer.valueOf(radarGroup.getFixedId()));
            map2.put(GlobalVariable.FIXEDNAME, radarGroup.getFixedName());
            map2.put(GlobalVariable.INDEX, Integer.valueOf(radarGroup.getIndex()));
            ArrayList arrayList2 = new ArrayList();
            for (RadarDevice radarDevice : radarGroup.getBleDeviceRealmList()) {
                HashMap map3 = new HashMap();
                map3.put(GlobalVariable.DID, radarDevice.getDid());
                arrayList2.add(map3);
            }
            map2.put(GlobalVariable.BLEDVARR, arrayList2);
            arrayList.add(map2);
        }
        map.put(GlobalVariable.RADAR_GP, arrayList);
        this.queryCount++;
    }

    private void getDeleteRadarGroups(final HashMap<String, Object> map) {
        this.realm.where(DeleteRadarGroup.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda34
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getDeleteRadarGroups$9(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeleteRadarGroups$9(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getDeleteRadarGroups$8(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeleteRadarGroups$8(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DeleteRadarGroup deleteRadarGroup = (DeleteRadarGroup) it.next();
            HashMap map2 = new HashMap();
            map2.put("fixedId", Integer.valueOf(deleteRadarGroup.getFixedId()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.D_RADAR_G, arrayList);
        this.queryCount++;
    }

    private void getRadarTimer(final HashMap<String, Object> map) {
        this.realm.where(RadarTimer.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda15
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getRadarTimer$11(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarTimer$11(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getRadarTimer$10(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarTimer$10(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RadarTimer radarTimer = (RadarTimer) it.next();
            HashMap map2 = new HashMap();
            map2.put("indexNum", Integer.valueOf(radarTimer.getIndexNum()));
            map2.put(GlobalVariable.HOUR1, Integer.valueOf(radarTimer.getHour1()));
            map2.put(GlobalVariable.MIN1, Integer.valueOf(radarTimer.getMin1()));
            map2.put(GlobalVariable.HOUR2, Integer.valueOf(radarTimer.getHour2()));
            map2.put(GlobalVariable.MIN2, Integer.valueOf(radarTimer.getMin2()));
            map2.put(GlobalVariable.ISENABLE, Boolean.valueOf(radarTimer.isEnable()));
            map2.put(GlobalVariable.OUT_BRI, Integer.valueOf(radarTimer.getOut_bri()));
            RadarDevice radarDevice = (RadarDevice) Realm.getDefaultInstance().where(RadarDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(radarTimer.getAddr())).findFirst();
            if (radarDevice != null) {
                map2.put(GlobalVariable.DID, radarDevice.getDid());
            }
            arrayList.add(map2);
        }
        map.put(GlobalVariable.RADAR_T, arrayList);
        this.queryCount++;
    }

    private void getRadarPhoneType(final HashMap<String, Object> map) {
        this.realm.where(RadarPhoneType.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda16
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getRadarPhoneType$13(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarPhoneType$13(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getRadarPhoneType$12(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRadarPhoneType$12(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RadarPhoneType radarPhoneType = (RadarPhoneType) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.P_TYPE, Integer.valueOf(radarPhoneType.getPhoneType()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.RADAR_P, arrayList);
        this.queryCount++;
    }

    private void getGroups(final HashMap<String, Object> map) {
        this.realm.where(Groups.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda17
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getGroups$15(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getGroups$15(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getGroups$14(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getGroups$14(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Groups groups = (Groups) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.GROUPID, Integer.valueOf(groups.getGroupId()));
            map2.put(GlobalVariable.G_NAME, groups.getGroupName());
            map2.put(GlobalVariable.INDEX, Integer.valueOf(groups.getIndex()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.GROUP, arrayList);
        this.queryCount++;
    }

    private void getBleDevices(final HashMap<String, Object> map) {
        this.realm.where(BleDevice.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda8
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getBleDevices$17(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getBleDevices$17(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getBleDevices$16(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getBleDevices$16(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BleDevice bleDevice = (BleDevice) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.DID, bleDevice.getDid());
            map2.put(GlobalVariable.NAME, bleDevice.getName());
            map2.put(GlobalVariable.KEY, bleDevice.getKey());
            map2.put(GlobalVariable.VERSION, bleDevice.getVersion());
            map2.put(GlobalVariable.ADDR, Integer.valueOf(bleDevice.getAddr()));
            map2.put(GlobalVariable.TYPE, Integer.valueOf(bleDevice.getType()));
            map2.put(GlobalVariable.GROUPID, Integer.valueOf(bleDevice.getGroupId()));
            map2.put(GlobalVariable.INDEX, Integer.valueOf(bleDevice.getIndex()));
            map2.put("groupIndex", Integer.valueOf(bleDevice.getGroupIndex()));
            map2.put("isSupportAlexaEnable", Boolean.valueOf(bleDevice.isSupportAlexaEnable()));
            map2.put("isConfigAlexa", Boolean.valueOf(bleDevice.isConfigAlexa()));
            map2.put("isSupportFixedGroup", Boolean.valueOf(bleDevice.isSupportFixedGroup()));
            map2.put("isSupportGroupMain", Boolean.valueOf(bleDevice.isSupportGroupMain()));
            map2.put(GlobalVariable.BEENMAIN, Boolean.valueOf(bleDevice.isBeenMain()));
            map2.put("fixedId", Integer.valueOf(bleDevice.getFixedId()));
            map2.put(GlobalVariable.S_T_STATUS, Boolean.valueOf(bleDevice.isSupportTimerStatus()));
            map2.put(GlobalVariable.S_SCENE_CTRL, Boolean.valueOf(bleDevice.isSupportSceneControl()));
            map2.put("otaTag", Integer.valueOf(bleDevice.getOtaTag()));
            map2.put(GlobalVariable.S_MODE_LIGHTNESS, Boolean.valueOf(bleDevice.isSupportModeLightness()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.BLEDV, arrayList);
        this.queryCount++;
    }

    private void getTimers(final HashMap<String, Object> map) {
        this.realm.where(com.brgd.brblmesh.GeneralAdapter.model.Timer.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda25
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getTimers$19(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTimers$19(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getTimers$18(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTimers$18(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            com.brgd.brblmesh.GeneralAdapter.model.Timer timer = (com.brgd.brblmesh.GeneralAdapter.model.Timer) it.next();
            HashMap map2 = new HashMap();
            map2.put("indexNum", Integer.valueOf(timer.getIndexNum()));
            map2.put(GlobalVariable.HOUR, Integer.valueOf(timer.getHour()));
            map2.put(GlobalVariable.MINUTE, Integer.valueOf(timer.getMinute()));
            map2.put(GlobalVariable.ACTION, Integer.valueOf(timer.getAction()));
            map2.put(GlobalVariable.ISREPEAT, Boolean.valueOf(timer.isRepeat()));
            map2.put(GlobalVariable.ISENABLE, Boolean.valueOf(timer.isEnable()));
            BleDevice bleDevice = (BleDevice) Realm.getDefaultInstance().where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(timer.getAddr())).findFirst();
            if (bleDevice != null) {
                map2.put(GlobalVariable.DID, bleDevice.getDid());
            }
            arrayList.add(map2);
        }
        map.put("Timer", arrayList);
        this.queryCount++;
    }

    private void getDiyColors(final HashMap<String, Object> map) {
        this.realm.where(DiyColor.class).notEqualTo("colorIdentifier", GlobalVariable.myColor).notEqualTo("colorIdentifier", GlobalVariable.myColorTemp).notEqualTo("colorIdentifier", GlobalVariable.modDefaultColor).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda0
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getDiyColors$21(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDiyColors$21(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getDiyColors$20(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDiyColors$20(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DiyColor diyColor = (DiyColor) it.next();
            HashMap map2 = new HashMap();
            String colorIdentifier = diyColor.getColorIdentifier();
            if (diyColor.getColorValue() == -2) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.nullColor);
            } else if (diyColor.getColorValue() == -3) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.WHITE_HEX);
            } else if (Objects.equals(colorIdentifier, GlobalVariable.diyColorTemp)) {
                map2.put(GlobalVariable.COLORSTR, String.valueOf(diyColor.getColorValue()));
            } else {
                map2.put(GlobalVariable.COLORSTR, Tool.colorToHexStr(diyColor.getColorValue()));
            }
            if (!Objects.equals(colorIdentifier, GlobalVariable.diyColorTemp)) {
                if (diyColor.getDiyColorR() == -2 || diyColor.getDiyColorR() == 0) {
                    map2.put(GlobalVariable.COLORSTRR, GlobalVariable.nullColor);
                } else if (diyColor.getDiyColorR() == -3) {
                    map2.put(GlobalVariable.COLORSTRR, GlobalVariable.WHITE_HEX);
                } else {
                    map2.put(GlobalVariable.COLORSTRR, Tool.colorToHexStr(diyColor.getDiyColorR()));
                }
            }
            if (Objects.equals(colorIdentifier, GlobalVariable.myColor)) {
                colorIdentifier = "my";
            } else if (Objects.equals(colorIdentifier, "DiyColor")) {
                colorIdentifier = "diy";
            } else if (Objects.equals(colorIdentifier, GlobalVariable.myColorTemp)) {
                colorIdentifier = "myColorTemp";
            } else if (Objects.equals(colorIdentifier, GlobalVariable.diyColorTemp)) {
                colorIdentifier = "diyColorTemp";
            } else if (Objects.equals(colorIdentifier, GlobalVariable.modDefaultColor)) {
                colorIdentifier = "default";
            }
            map2.put("colorIdentifier", colorIdentifier);
            arrayList.add(map2);
        }
        map.put(GlobalVariable.DIYC, arrayList);
        this.queryCount++;
    }

    private void getModDiyColors(final HashMap<String, Object> map) {
        this.realm.where(ModDiyColor.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda5
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getModDiyColors$23(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getModDiyColors$23(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getModDiyColors$22(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getModDiyColors$22(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ModDiyColor modDiyColor = (ModDiyColor) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.MODNUM, Integer.valueOf(modDiyColor.getModNumber()));
            map2.put(GlobalVariable.COLORINDEX, Integer.valueOf(modDiyColor.getColorIndex()));
            if (modDiyColor.getCustomModId() != null) {
                map2.put(GlobalVariable.customModId, modDiyColor.getCustomModId());
            }
            if (modDiyColor.getDiyColor() == -2) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.nullColor);
            } else if (modDiyColor.getDiyColor() == -3) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.WHITE_HEX);
            } else {
                map2.put(GlobalVariable.COLORSTR, Tool.colorToHexStr(modDiyColor.getDiyColor()));
            }
            if (modDiyColor.getDiyColorR() == -2 || modDiyColor.getDiyColorR() == 0) {
                map2.put(GlobalVariable.COLORSTRR, GlobalVariable.nullColor);
            } else if (modDiyColor.getDiyColorR() == -3) {
                map2.put(GlobalVariable.COLORSTRR, GlobalVariable.WHITE_HEX);
            } else {
                map2.put(GlobalVariable.COLORSTRR, Tool.colorToHexStr(modDiyColor.getDiyColorR()));
            }
            arrayList.add(map2);
        }
        map.put(GlobalVariable.MODDIYC, arrayList);
        this.queryCount++;
    }

    private void getMods(final HashMap<String, Object> map) {
        this.realm.where(Mod.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda13
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getMods$25(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMods$25(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getMods$24(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMods$24(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Mod mod = (Mod) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.MODNUM, Integer.valueOf(mod.getModNumber()));
            map2.put(GlobalVariable.SPEED, Integer.valueOf(mod.getSpeed()));
            map2.put(GlobalVariable.ADDR, Integer.valueOf(mod.getAddr()));
            map2.put(GlobalVariable.STARTBRI, Integer.valueOf(mod.getStartBrightness()));
            map2.put(GlobalVariable.ENDBRI, Integer.valueOf(mod.getEndBrightness()));
            map2.put(GlobalVariable.MINUTE, Integer.valueOf(mod.getMinute()));
            if (mod.getStateValue() == -2 || mod.getStateValue() == 0) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.nullColor);
            } else if (mod.getStateValue() == -3) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.WHITE_HEX);
            } else {
                map2.put(GlobalVariable.COLORSTR, Tool.colorToHexStr(mod.getStateValue()));
            }
            map2.put(GlobalVariable.WARMVALUE, Integer.valueOf(mod.getWarmValue()));
            map2.put("isSleep", Integer.valueOf(mod.isSleep() ? 1 : 0));
            if (mod.getCustomModId() != null) {
                map2.put(GlobalVariable.ISFLASH, Boolean.valueOf(mod.isFlash()));
                map2.put(GlobalVariable.customModId, mod.getCustomModId());
                map2.put(GlobalVariable.CUSTOMMODNAME, mod.getCustomModName());
            }
            map2.put(GlobalVariable.BRIGHTNESS, Integer.valueOf(mod.getBrightness()));
            arrayList.add(map2);
        }
        map.put("Mod", arrayList);
        this.queryCount++;
    }

    private void getScenes(final HashMap<String, Object> map) {
        this.realm.where(Scene.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda31
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getScenes$27(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getScenes$27(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getScenes$26(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getScenes$26(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Scene scene = (Scene) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.INDEX, Integer.valueOf(scene.getIndex()));
            map2.put(GlobalVariable.SCENENUM, Integer.valueOf(scene.getSceneNumber()));
            map2.put(GlobalVariable.SCENENAME, scene.getSceneName());
            map2.put(GlobalVariable.ICONINDEX, Integer.valueOf(scene.getIconIndex()));
            ArrayList arrayList2 = new ArrayList();
            for (SceneDevice sceneDevice : scene.getSceneDeviceRealmList()) {
                HashMap map3 = new HashMap();
                map3.put(GlobalVariable.DID, sceneDevice.getDid());
                arrayList2.add(map3);
            }
            map2.put(GlobalVariable.SCENEDVARR, arrayList2);
            arrayList.add(map2);
        }
        map.put("Scene", arrayList);
        this.queryCount++;
    }

    private void getPhoneType(final HashMap<String, Object> map) {
        this.realm.where(PhoneType.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda3
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getPhoneType$29(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPhoneType$29(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getPhoneType$28(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPhoneType$28(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PhoneType phoneType = (PhoneType) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.P_TYPE, Integer.valueOf(phoneType.getPhoneType()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.PHONE_T, arrayList);
        this.queryCount++;
    }

    private void getSceneDevices(final HashMap<String, Object> map) {
        this.realm.where(SceneDevice.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda10
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getSceneDevices$31(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSceneDevices$31(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getSceneDevices$30(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSceneDevices$30(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SceneDevice sceneDevice = (SceneDevice) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.DID, sceneDevice.getDid());
            map2.put(GlobalVariable.SCENENUM, Integer.valueOf(sceneDevice.getSceneNumber()));
            if (sceneDevice.getColor() == -2) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.nullColor);
            } else if (sceneDevice.getColor() == -3) {
                map2.put(GlobalVariable.COLORSTR, GlobalVariable.WHITE_HEX);
            } else {
                map2.put(GlobalVariable.COLORSTR, Tool.colorToHexStr(sceneDevice.getColor()));
            }
            map2.put(GlobalVariable.LIGHTNESS, Integer.valueOf(sceneDevice.getLightness()));
            map2.put(GlobalVariable.TEMP, Integer.valueOf(sceneDevice.getTemp()));
            map2.put(GlobalVariable.MODNUM, Integer.valueOf(sceneDevice.getModNumber()));
            map2.put(GlobalVariable.MODSPEED, Integer.valueOf(sceneDevice.getModSpeed()));
            if (sceneDevice.getCustomModId() != null) {
                map2.put(GlobalVariable.customModId, sceneDevice.getCustomModId());
            }
            map2.put(GlobalVariable.MOD_BRIGHTNESS, Integer.valueOf(sceneDevice.getModBrightness()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.SCENEDV, arrayList);
        this.queryCount++;
    }

    private void getDeleteGroups(final HashMap<String, Object> map) {
        this.realm.where(DeleteGroups.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda19
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getDeleteGroups$33(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeleteGroups$33(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getDeleteGroups$32(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeleteGroups$32(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DeleteGroups deleteGroups = (DeleteGroups) it.next();
            HashMap map2 = new HashMap();
            map2.put(GlobalVariable.GROUPID, Integer.valueOf(deleteGroups.getGroupId()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.D_GROUP, arrayList);
        this.queryCount++;
    }

    private void getFixGroups(final HashMap<String, Object> map) {
        this.realm.where(FixedGroup.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda4
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getFixGroups$35(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFixGroups$35(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getFixGroups$34(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFixGroups$34(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            FixedGroup fixedGroup = (FixedGroup) it.next();
            HashMap map2 = new HashMap();
            map2.put("fixedId", Integer.valueOf(fixedGroup.getFixedId()));
            map2.put(GlobalVariable.FIXEDNAME, fixedGroup.getFixedName());
            ArrayList arrayList2 = new ArrayList();
            for (BleDevice bleDevice : fixedGroup.getBleDeviceRealmList()) {
                HashMap map3 = new HashMap();
                map3.put(GlobalVariable.DID, bleDevice.getDid());
                arrayList2.add(map3);
            }
            map2.put(GlobalVariable.BLEDVARR, arrayList2);
            map2.put(GlobalVariable.INDEX, Integer.valueOf(fixedGroup.getIndex()));
            map2.put(GlobalVariable.ICONINDEX, Integer.valueOf(fixedGroup.getIconIndex()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.FIXGROUP, arrayList);
        this.queryCount++;
    }

    private void getDeleteFixGroups(final HashMap<String, Object> map) {
        this.realm.where(DeleteFixedGroup.class).findAllAsync().addChangeListener(new RealmChangeListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda27
            @Override // io.realm.RealmChangeListener
            public final void onChange(Object obj) {
                this.f$0.lambda$getDeleteFixGroups$37(map, (RealmResults) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeleteFixGroups$37(final HashMap map, RealmResults realmResults) {
        if (realmResults.isLoaded()) {
            final List listCopyFromRealm = this.realm.copyFromRealm(realmResults);
            GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getDeleteFixGroups$36(listCopyFromRealm, map);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeleteFixGroups$36(List list, HashMap map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DeleteFixedGroup deleteFixedGroup = (DeleteFixedGroup) it.next();
            HashMap map2 = new HashMap();
            map2.put("fixedId", Integer.valueOf(deleteFixedGroup.getFixedId()));
            arrayList.add(map2);
        }
        map.put(GlobalVariable.D_FIXGROUP, arrayList);
        this.queryCount++;
    }

    private void importAction() {
        final CustomDialog customDialog = new CustomDialog(getActivity());
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.moreListRecyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.importTip);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda39
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$importAction$38(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$importAction$38(CustomDialog customDialog) {
        customDialog.dismiss();
        importData();
    }

    private void importData() {
        if (Tool.isToRadar()) {
            this.radarMainActivity.chooseFile();
        } else {
            this.mainActivity.chooseFile();
        }
    }

    public void exitCtrl() {
        final CustomDialog customDialog = new CustomDialog(getActivity());
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.moreListRecyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.ExitConfirm);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ThirdFragment$$ExternalSyntheticLambda22
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$exitCtrl$39(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$exitCtrl$39(CustomDialog customDialog) {
        customDialog.dismiss();
        if (Tool.isToRadar()) {
            SharePreferenceUtils.remove(this.radarMainActivity.getApplicationContext(), GlobalVariable.RECORD);
            startActivity(new Intent(this.radarMainActivity, (Class<?>) LaunchActivity.class));
            this.radarMainActivity.finish();
        } else {
            SharePreferenceUtils.remove(this.mainActivity.getApplicationContext(), GlobalVariable.RECORD);
            startActivity(new Intent(this.mainActivity, (Class<?>) LaunchActivity.class));
            this.mainActivity.finish();
        }
    }

    private boolean hasNeedOTA() {
        return !this.realm.where(BleDevice.class).notEqualTo("otaTag", (Integer) 0).findAll().isEmpty();
    }

    private void toOTA() {
        if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
            this.mainActivity.bleCASDialog();
            return;
        }
        if (Tool.bleNotOnToast(this.mainActivity.getApplicationContext())) {
            return;
        }
        if (hasNeedOTA()) {
            this.mainActivity.startActivity(new Intent(this.mainActivity, (Class<?>) OtaActivity.class));
        } else {
            GlobalToast.showText(this.mainActivity.getApplicationContext(), R.string.noUpgradeTip, 1);
        }
    }

    public void refreshMoreModel() {
        int[] iArr;
        int[] iArr2;
        if ((!Tool.isToRadar() && this.phoneType.getPhoneType() == 1) || (Tool.isToRadar() && this.radarPhoneType.getPhoneType() == 1)) {
            if (this.bleDeviceCount != 0) {
                iArr = new int[]{R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_share, R.drawable.more_private, R.drawable.more_share, R.drawable.more_share, R.drawable.more_instructions, R.drawable.more_reset, R.drawable.more_reset, R.drawable.more_reset};
                iArr2 = new int[]{R.string.AboutAssistant, R.string.Instructions, R.string.SharedDevices, R.string.privateContent2, R.string.bindCtrl, R.string.alexa, R.string.AboutAlexa, R.string.onekeyReset, R.string.exportConfig, R.string.firmware_upgrade};
            } else {
                iArr = new int[]{R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_share, R.drawable.more_private, R.drawable.more_reset, R.drawable.more_reset};
                iArr2 = new int[]{R.string.AboutAssistant, R.string.Instructions, R.string.SharedDevices, R.string.privateContent2, R.string.onekeyReset, R.string.exportConfig};
            }
        } else {
            iArr = new int[]{R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_scan, R.drawable.more_private, R.drawable.more_reset, R.drawable.more_reset};
            iArr2 = new int[]{R.string.AboutAssistant, R.string.Instructions, R.string.Scan, R.string.privateContent2, R.string.onekeyReset, R.string.importConfig};
        }
        this.moreModelList.clear();
        for (int i = 0; i < iArr.length; i++) {
            MoreModel moreModel = new MoreModel();
            moreModel.imgResourceId = iArr[i];
            moreModel.titleNameId = iArr2[i];
            if (i == 9) {
                if (hasNeedOTA()) {
                    moreModel.isSelect = true;
                    this.moreModelList.add(moreModel);
                }
            } else {
                this.moreModelList.add(moreModel);
            }
        }
        this.moreAdapter.notifyDataSetChanged();
    }
}

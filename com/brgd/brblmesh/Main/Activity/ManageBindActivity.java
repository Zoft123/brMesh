package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.helper.BLEFastconHelper;
import cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.BindingAdapter;
import com.brgd.brblmesh.GeneralAdapter.DialogArrayAdapter;
import com.brgd.brblmesh.GeneralAdapter.NoBindingAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralClass.BitmapUtils;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalThreadPools;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.ImageUtils;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ManageBindActivity extends FatherActivity {
    public static final int CHECK_PERMISSION = 103;
    public static final int IMG_RESULT = 104;
    private static final int TIME_OUT = 102;
    private static final int UPDATE_GROUP_ID = 101;
    ActivityResultLauncher<String[]> CMPermissionLauncher;
    ActivityResultLauncher<String[]> IMGPermissionLauncher;
    RecyclerView addDeviceRecyclerView;
    private List<BleDevice> addDevicesList;
    private List<BleDevice> addList;
    private TextView addView;
    private int addr;
    AlertDialog alertDialog;
    RecyclerView allDeviceRecyclerView;
    private List<BleDevice> allDevicesList;
    private TextView allSelectView;
    private TextView allSelectView1;
    ImageView backView;
    private BindingAdapter bindingAdapter;
    ActivityResultLauncher<Intent> cameraLauncher;
    private TextView cancelView;
    private TextView cancelView1;
    private BleDevice currentBleDevice;
    private FixedGroup currentFixedGroup;
    private int currentGroupId;
    private List<BleDevice> deleteList;
    private TextView deleteView;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private TextView deviceNameText;
    private List<BleDevice> devicesList;
    ImageView dismissView;
    TextView fixDeleteView;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    ImageView iconBg;
    ImageView iconView;
    private Uri imageUri;
    private boolean isSetMainDv;
    private boolean isUsePhoto;
    private Dialog loadingDialog;
    private TextView multiSelectView;
    private TextView multiSelectView1;
    ImageView nameBg;
    TextView nameValueText;
    private NoBindingAdapter noBindingAdapter;
    ActivityResultLauncher<String> photoLauncher;
    private Realm realm;
    private ConstraintLayout renameLayout;
    ImageView saveView;
    AlertDialog scenePermissionAlertDialog;
    private final int[] iconImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9, R.drawable.scene10, R.drawable.scene11, R.drawable.scene12, R.drawable.scene13, R.drawable.scene14, R.drawable.scene15, R.drawable.scene16, R.drawable.scene17, R.drawable.scene18, R.drawable.scene19, R.drawable.scene20, R.drawable.scene21, R.drawable.scene22, R.drawable.scene23, R.drawable.scene24, R.drawable.scene25, R.drawable.scene26, R.drawable.scene27, R.drawable.scene28, R.drawable.scene29, R.drawable.scene30};
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity.4
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.fixed_devices_back) {
                ManageBindActivity.this.finish();
                return;
            }
            if (id == R.id.fixed_devices_multiSelect1) {
                if (ManageBindActivity.this.addDevicesList.isEmpty()) {
                    GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.noDevices, 1);
                    return;
                }
                ManageBindActivity.this.multiSelectView1.setVisibility(4);
                ManageBindActivity.this.allSelectView1.setVisibility(4);
                ManageBindActivity.this.deleteView.setVisibility(0);
                ManageBindActivity.this.cancelView1.setVisibility(0);
                for (BleDevice bleDevice : ManageBindActivity.this.addDevicesList) {
                    bleDevice.isGroupStatus = true;
                    bleDevice.isSelect = false;
                }
                ManageBindActivity.this.deleteList.clear();
                ManageBindActivity.this.bindingAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_allSelect1) {
                if (ManageBindActivity.this.addDevicesList.isEmpty()) {
                    GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.noDevices, 1);
                    return;
                }
                ManageBindActivity.this.multiSelectView1.setVisibility(4);
                ManageBindActivity.this.allSelectView1.setVisibility(4);
                ManageBindActivity.this.deleteView.setVisibility(0);
                ManageBindActivity.this.cancelView1.setVisibility(0);
                ManageBindActivity.this.deleteList.clear();
                for (BleDevice bleDevice2 : ManageBindActivity.this.addDevicesList) {
                    bleDevice2.isGroupStatus = true;
                    bleDevice2.isSelect = true;
                    ManageBindActivity.this.deleteList.add(bleDevice2);
                }
                ManageBindActivity.this.bindingAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_delete) {
                if (ManageBindActivity.this.deleteList.isEmpty()) {
                    GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.selectDevice, 1);
                    return;
                }
                for (BleDevice bleDevice3 : ManageBindActivity.this.deleteList) {
                    if (bleDevice3.isBeenMain() && bleDevice3.getFixedId() == ManageBindActivity.this.currentFixedGroup.getFixedId()) {
                        GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.cancelFixedMains, 1);
                        return;
                    }
                }
                ManageBindActivity.this.multiSelectView1.setVisibility(0);
                ManageBindActivity.this.allSelectView1.setVisibility(0);
                ManageBindActivity.this.deleteView.setVisibility(4);
                ManageBindActivity.this.cancelView1.setVisibility(4);
                ManageBindActivity.this.multiSelectView.setVisibility(0);
                ManageBindActivity.this.allSelectView.setVisibility(0);
                ManageBindActivity.this.addView.setVisibility(4);
                ManageBindActivity.this.cancelView.setVisibility(4);
                ManageBindActivity.this.deleteDevicesFromFixedGroup();
                return;
            }
            if (id == R.id.fixed_devices_cancel1) {
                ManageBindActivity.this.multiSelectView1.setVisibility(0);
                ManageBindActivity.this.allSelectView1.setVisibility(0);
                ManageBindActivity.this.deleteView.setVisibility(4);
                ManageBindActivity.this.cancelView1.setVisibility(4);
                for (BleDevice bleDevice4 : ManageBindActivity.this.addDevicesList) {
                    bleDevice4.isGroupStatus = false;
                    bleDevice4.isSelect = false;
                }
                ManageBindActivity.this.deleteList.clear();
                ManageBindActivity.this.bindingAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_multiSelect) {
                if (ManageBindActivity.this.allDevicesList.isEmpty()) {
                    GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.noDevices, 1);
                    return;
                }
                ManageBindActivity.this.multiSelectView.setVisibility(4);
                ManageBindActivity.this.allSelectView.setVisibility(4);
                ManageBindActivity.this.addView.setVisibility(0);
                ManageBindActivity.this.cancelView.setVisibility(0);
                for (BleDevice bleDevice5 : ManageBindActivity.this.allDevicesList) {
                    bleDevice5.isGroupStatus = true;
                    bleDevice5.isSelect = false;
                }
                ManageBindActivity.this.addList.clear();
                ManageBindActivity.this.noBindingAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_allSelect) {
                if (ManageBindActivity.this.allDevicesList.isEmpty()) {
                    GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.noDevices, 1);
                    return;
                }
                ManageBindActivity.this.multiSelectView.setVisibility(4);
                ManageBindActivity.this.allSelectView.setVisibility(4);
                ManageBindActivity.this.addView.setVisibility(0);
                ManageBindActivity.this.cancelView.setVisibility(0);
                ManageBindActivity.this.addList.clear();
                for (BleDevice bleDevice6 : ManageBindActivity.this.allDevicesList) {
                    bleDevice6.isGroupStatus = true;
                    bleDevice6.isSelect = true;
                    ManageBindActivity.this.addList.add(bleDevice6);
                }
                ManageBindActivity.this.noBindingAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_devices_add) {
                if (ManageBindActivity.this.addList.isEmpty()) {
                    GlobalToast.showText(ManageBindActivity.this.getApplicationContext(), R.string.selectDevice, 1);
                    return;
                }
                ManageBindActivity.this.multiSelectView.setVisibility(0);
                ManageBindActivity.this.allSelectView.setVisibility(0);
                ManageBindActivity.this.addView.setVisibility(4);
                ManageBindActivity.this.cancelView.setVisibility(4);
                ManageBindActivity.this.multiSelectView1.setVisibility(0);
                ManageBindActivity.this.allSelectView1.setVisibility(0);
                ManageBindActivity.this.deleteView.setVisibility(4);
                ManageBindActivity.this.cancelView1.setVisibility(4);
                ManageBindActivity.this.addDevicesToFixedGroup();
                return;
            }
            if (id == R.id.fixed_devices_cancel) {
                ManageBindActivity.this.multiSelectView.setVisibility(0);
                ManageBindActivity.this.allSelectView.setVisibility(0);
                ManageBindActivity.this.addView.setVisibility(4);
                ManageBindActivity.this.cancelView.setVisibility(4);
                for (BleDevice bleDevice7 : ManageBindActivity.this.allDevicesList) {
                    bleDevice7.isGroupStatus = false;
                    bleDevice7.isSelect = false;
                }
                ManageBindActivity.this.addList.clear();
                ManageBindActivity.this.noBindingAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.fixed_delete) {
                ManageBindActivity.this.deleteFixGroup();
                return;
            }
            if (id == R.id.add_scene_iconBg) {
                ManageBindActivity.this.showImgChoice();
                return;
            }
            if (id == R.id.add_scene_nameBg) {
                ManageBindActivity.this.deviceNameText.setText(ManageBindActivity.this.currentFixedGroup.getFixedName());
                ManageBindActivity.this.deviceNameEdit.setText(ManageBindActivity.this.currentFixedGroup.getFixedName());
                ManageBindActivity.this.renameLayout.setVisibility(0);
                return;
            }
            if (id == R.id.scene_rename_delete) {
                ManageBindActivity.this.deviceNameEdit.setText("");
                ManageBindActivity.this.deviceNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.scene_rename_save) {
                String string = ManageBindActivity.this.deviceNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                if (!strTrim.equalsIgnoreCase(ManageBindActivity.this.currentFixedGroup.getFixedName())) {
                    Iterator it = ManageBindActivity.this.realm.where(FixedGroup.class).findAll().iterator();
                    while (it.hasNext()) {
                        if (strTrim.equalsIgnoreCase(((FixedGroup) it.next()).getFixedName())) {
                            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.nameExist, 0);
                            return;
                        }
                    }
                    ManageBindActivity.this.realm.beginTransaction();
                    ManageBindActivity.this.currentFixedGroup.setFixedName(strTrim);
                    ManageBindActivity.this.realm.commitTransaction();
                    ManageBindActivity.this.nameValueText.setText(ManageBindActivity.this.currentFixedGroup.getFixedName());
                }
                ManageBindActivity.this.closeRenameView();
                return;
            }
            if (id == R.id.scene_rename_dismiss) {
                ManageBindActivity.this.closeRenameView();
            }
        }
    };
    private boolean isReceiveCallback = false;
    final OnDevHeartBeatCallback mHeartbeatCallback = new OnDevHeartBeatCallback() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity.5
        @Override // cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback
        public void onCallback(int i, int i2, String str) {
            if (ManageBindActivity.this.isReceiveCallback) {
                return;
            }
            ManageBindActivity manageBindActivity = ManageBindActivity.this;
            manageBindActivity.isReceiveCallback = i == manageBindActivity.currentBleDevice.getAddr();
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    static /* synthetic */ void lambda$registerActivityResultLauncher$6(ActivityResult activityResult) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_manage_bind);
        initView();
        registerActivityResultLauncher();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        refreshIcon();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        stopReceiveService();
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        stopReceiveService();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        FixedGroup fixedGroup = (FixedGroup) this.realm.where(FixedGroup.class).equalTo("fixedId", Integer.valueOf(getIntent().getIntExtra(GlobalVariable.toFixGroupCtrl, 0))).findFirst();
        this.currentFixedGroup = fixedGroup;
        this.currentGroupId = ((FixedGroup) Objects.requireNonNull(fixedGroup)).getFixedId();
        this.devicesList = new ArrayList();
        this.deleteList = new ArrayList();
        this.addList = new ArrayList();
        ImageView imageView = (ImageView) findViewById(R.id.fixed_devices_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.fixed_delete);
        this.fixDeleteView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.add_scene_iconBg);
        this.iconBg = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.iconView = (ImageView) findViewById(R.id.add_scene_icon);
        ImageView imageView3 = (ImageView) findViewById(R.id.add_scene_nameBg);
        this.nameBg = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        this.nameValueText = (TextView) findViewById(R.id.add_scene_nameValue);
        TextView textView2 = (TextView) findViewById(R.id.fixed_devices_multiSelect1);
        this.multiSelectView1 = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        TextView textView3 = (TextView) findViewById(R.id.fixed_devices_allSelect1);
        this.allSelectView1 = textView3;
        textView3.setOnClickListener(this.disDoubleClickListener);
        TextView textView4 = (TextView) findViewById(R.id.fixed_devices_delete);
        this.deleteView = textView4;
        textView4.setOnClickListener(this.disDoubleClickListener);
        TextView textView5 = (TextView) findViewById(R.id.fixed_devices_cancel1);
        this.cancelView1 = textView5;
        textView5.setOnClickListener(this.disDoubleClickListener);
        TextView textView6 = (TextView) findViewById(R.id.fixed_devices_multiSelect);
        this.multiSelectView = textView6;
        textView6.setOnClickListener(this.disDoubleClickListener);
        TextView textView7 = (TextView) findViewById(R.id.fixed_devices_allSelect);
        this.allSelectView = textView7;
        textView7.setOnClickListener(this.disDoubleClickListener);
        TextView textView8 = (TextView) findViewById(R.id.fixed_devices_add);
        this.addView = textView8;
        textView8.setOnClickListener(this.disDoubleClickListener);
        TextView textView9 = (TextView) findViewById(R.id.fixed_devices_cancel);
        this.cancelView = textView9;
        textView9.setOnClickListener(this.disDoubleClickListener);
        this.addDevicesList = new ArrayList();
        this.bindingAdapter = new BindingAdapter(this, this.addDevicesList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fixed_devices_inGroup_recyclerView);
        this.addDeviceRecyclerView = recyclerView;
        recyclerView.setAdapter(this.bindingAdapter);
        this.addDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.bindingAdapter.setOnItemClickListener(new BindingAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.BindingAdapter.OnItemClickListener
            public void onItemClick(View view, int i) {
                ManageBindActivity.this.deleteFromFixedGroup(i);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.BindingAdapter.OnItemClickListener
            public void onSetMainClick(View view, int i) {
                ManageBindActivity.this.setMainDv(i);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.BindingAdapter.OnItemClickListener
            public void optionClick(View view, int i) {
                BleDevice bleDevice = (BleDevice) ManageBindActivity.this.addDevicesList.get(i);
                if (bleDevice.isSelect) {
                    bleDevice.isSelect = false;
                    ManageBindActivity.this.deleteList.remove(bleDevice);
                } else {
                    bleDevice.isSelect = true;
                    ManageBindActivity.this.deleteList.add(bleDevice);
                }
                ManageBindActivity.this.bindingAdapter.notifyDataSetChanged();
            }
        });
        this.allDevicesList = new ArrayList();
        this.noBindingAdapter = new NoBindingAdapter(this, this.allDevicesList);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.fixed_devices_not_inGroup_recyclerView);
        this.allDeviceRecyclerView = recyclerView2;
        recyclerView2.setAdapter(this.noBindingAdapter);
        this.allDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.noBindingAdapter.setOnItemClickListener(new NoBindingAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity.2
            @Override // com.brgd.brblmesh.GeneralAdapter.NoBindingAdapter.OnItemClickListener
            public void onItemClick(View view, int i) {
                ManageBindActivity.this.addToFixedGroup(i);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.NoBindingAdapter.OnItemClickListener
            public void optionClick(View view, int i) {
                BleDevice bleDevice = (BleDevice) ManageBindActivity.this.allDevicesList.get(i);
                if (bleDevice.isSelect) {
                    bleDevice.isSelect = false;
                    ManageBindActivity.this.addList.remove(bleDevice);
                } else {
                    bleDevice.isSelect = true;
                    ManageBindActivity.this.addList.add(bleDevice);
                }
                ManageBindActivity.this.noBindingAdapter.notifyDataSetChanged();
            }
        });
        this.renameLayout = (ConstraintLayout) findViewById(R.id.scene_rename_editView);
        this.deviceNameText = (TextView) findViewById(R.id.scene_rename_name);
        this.deviceNameEdit = (EditText) findViewById(R.id.scene_rename_edit);
        ImageView imageView4 = (ImageView) findViewById(R.id.scene_rename_delete);
        this.deviceNameDelete = imageView4;
        imageView4.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (ManageBindActivity.this.delete_deviceName_tag) {
                        ManageBindActivity.this.deviceNameDelete.setSelected(true);
                        ManageBindActivity.this.deviceNameDelete.setEnabled(true);
                        ManageBindActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                ManageBindActivity.this.deviceNameDelete.setSelected(false);
                ManageBindActivity.this.deviceNameDelete.setEnabled(false);
                ManageBindActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.scene_rename_save);
        this.saveView = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView6 = (ImageView) findViewById(R.id.scene_rename_dismiss);
        this.dismissView = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
        if (this.currentFixedGroup.getFixedId() <= 196) {
            this.fixDeleteView.setVisibility(4);
        }
        refreshIcon();
        refreshGroup();
        isShowTip();
    }

    private void isShowTip() {
        if (SharePreferenceUtils.contains(getApplicationContext(), GlobalVariable.bindKey)) {
            return;
        }
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), 0.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.BindTip);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda17
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                customDialog.dismiss();
            }
        });
        customDialog.setOnCancelClickListener(R.string.AlexaGuideTitle3, new CustomDialog.onCancelClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onCancelClickListener
            public final void onCancelClick() {
                this.f$0.lambda$isShowTip$0(customDialog);
            }
        });
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isShowTip$0(CustomDialog customDialog) {
        customDialog.dismiss();
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.bindKey, 1);
    }

    private void refreshGroup() {
        this.addDevicesList.clear();
        this.addDevicesList.addAll(this.currentFixedGroup.getBleDeviceRealmList());
        Iterator<BleDevice> it = this.addDevicesList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BleDevice next = it.next();
            if (next.isBeenMain()) {
                next.isCurrentFixedGroup = next.getFixedId() == this.currentFixedGroup.getFixedId();
            }
        }
        this.bindingAdapter.notifyDataSetChanged();
        this.devicesList.clear();
        this.devicesList.addAll(this.realm.where(BleDevice.class).equalTo("isSupportFixedGroup", (Boolean) true).findAll().sort(GlobalVariable.index));
        this.allDevicesList.clear();
        Iterator<BleDevice> it2 = this.addDevicesList.iterator();
        while (it2.hasNext()) {
            this.devicesList.remove(it2.next());
        }
        this.allDevicesList.addAll(this.devicesList);
        this.noBindingAdapter.notifyDataSetChanged();
    }

    private void refreshIcon() {
        FixedGroup fixedGroup = this.currentFixedGroup;
        if (fixedGroup != null) {
            if (fixedGroup.getFileName() != null) {
                File file = new File(Tool.getAppFile(this), this.currentFixedGroup.getFileName() + GlobalVariable.sceneImgFileName);
                if (file.exists()) {
                    this.iconView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }
            } else if (this.currentFixedGroup.getIconIndex() != 0) {
                this.iconView.setImageResource(this.iconImgArr[this.currentFixedGroup.getIconIndex() - 1]);
            }
            this.nameValueText.setText(this.currentFixedGroup.getFixedName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFixGroup() {
        for (BleDevice bleDevice : this.currentFixedGroup.getBleDeviceRealmList()) {
            if (bleDevice.isBeenMain() && bleDevice.getFixedId() == this.currentFixedGroup.getFixedId()) {
                GlobalToast.showText(getApplicationContext(), R.string.deleteBindTip, 1);
                return;
            }
        }
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.iconBg.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.confirmDelete);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$deleteFixGroup$2(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFixGroup$2(CustomDialog customDialog) {
        customDialog.dismiss();
        ArrayList arrayList = new ArrayList();
        Iterator<BleDevice> it = this.currentFixedGroup.getBleDeviceRealmList().iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        BLSBleLight.removeFixGroupWithGroupId(this.currentFixedGroup.getFixedId(), Tool.getListToArray(arrayList), Tool.getListToArray(new ArrayList()));
        handleDeleteGroup();
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteFixGroup$1();
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFixGroup$1() {
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
        finish();
    }

    private void handleDeleteGroup() {
        DeleteFixedGroup deleteFixedGroup = (DeleteFixedGroup) this.realm.where(DeleteFixedGroup.class).equalTo("fixedId", Integer.valueOf(this.currentFixedGroup.getFixedId())).findFirst();
        this.realm.beginTransaction();
        if (deleteFixedGroup == null) {
            deleteFixedGroup = (DeleteFixedGroup) this.realm.createObject(DeleteFixedGroup.class);
        }
        deleteFixedGroup.setFixedId(this.currentFixedGroup.getFixedId());
        this.realm.commitTransaction();
        Tool.deleteFile(this, this.currentFixedGroup.getFileName() + GlobalVariable.sceneImgFileName);
        this.realm.beginTransaction();
        this.currentFixedGroup.deleteFromRealm();
        this.realm.commitTransaction();
        handleAfterDeleteGroup();
    }

    private void handleAfterDeleteGroup() {
        if (((int) this.realm.where(FixedGroup.class).count()) == 0) {
            RealmResults realmResultsFindAll = this.realm.where(DeleteFixedGroup.class).findAll();
            if (realmResultsFindAll.isEmpty()) {
                return;
            }
            this.realm.beginTransaction();
            realmResultsFindAll.deleteAllFromRealm();
            this.realm.commitTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
    }

    private void usePhoto() {
        if (Tool.checkReadStoragePermission()) {
            openAlbum();
        } else {
            this.isUsePhoto = true;
            dialog();
        }
    }

    private void useCamera() {
        if (Tool.checkCameraPermission()) {
            takePhoto();
        } else {
            this.isUsePhoto = false;
            dialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sceneModifyImg() {
        if (this.isUsePhoto) {
            if (Tool.checkReadStoragePermission()) {
                openAlbum();
                return;
            } else {
                IMGPermission();
                return;
            }
        }
        if (Tool.checkCameraPermission()) {
            takePhoto();
        } else {
            CMPermission();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showImgChoice() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String string = getString(R.string.takePicture);
        builder.setAdapter(new DialogArrayAdapter(this, R.layout.dialog_list_item, new String[]{getString(R.string.templates), getString(R.string.Picture), string}), new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showImgChoice$3(builder, dialogInterface, i);
            }
        });
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showImgChoice$3(AlertDialog.Builder builder, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            toTemplates();
        } else if (i == 1) {
            usePhoto();
        } else if (i == 2) {
            useCamera();
        }
        builder.create().dismiss();
    }

    private void takePhoto() {
        String str = System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str);
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("relative_path", Environment.DIRECTORY_DCIM);
            this.imageUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            this.imageUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileProvider", new File(((File) Objects.requireNonNull(getApplicationContext().getExternalCacheDir())).getAbsolutePath(), str));
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", this.imageUri);
        this.cameraLauncher.launch(intent);
    }

    private void openAlbum() {
        this.photoLauncher.launch("image/*");
    }

    public void toTemplates() {
        Intent intent = new Intent(this, (Class<?>) TemplatesActivity.class);
        intent.putExtra("fixedId", this.currentFixedGroup.getFixedId());
        startActivity(intent);
    }

    public void handleSceneImg(Bitmap bitmap) throws Throwable {
        saveImage(BitmapUtils.circleBitmap(BitmapUtils.zoom(bitmap, 202.0f, 202.0f)));
        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.setSuccess, 0);
        refreshIcon();
    }

    private void saveImage(Bitmap bitmap) throws Throwable {
        StringBuilder sb;
        File appFile = Tool.getAppFile(this);
        String fileName = Tool.getFileName();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(appFile, fileName + GlobalVariable.sceneImgFileName));
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    if (this.currentFixedGroup.getFileName() != null) {
                        Tool.deleteFile(this, this.currentFixedGroup.getFileName() + GlobalVariable.sceneImgFileName);
                    }
                    this.realm.beginTransaction();
                    this.currentFixedGroup.setFileName(fileName);
                    this.realm.commitTransaction();
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                        e = e;
                        sb = new StringBuilder("printStackTrace");
                        sb.append(e);
                        Log.d("printStackTrace", sb.toString());
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    Log.d("printStackTrace", "printStackTrace" + e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e = e3;
                            sb = new StringBuilder("printStackTrace");
                            sb.append(e);
                            Log.d("printStackTrace", sb.toString());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            Log.d("printStackTrace", "printStackTrace" + e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
        }
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
    public void deleteFromFixedGroup(int i) {
        BleDevice bleDevice = this.addDevicesList.get(i);
        this.currentBleDevice = bleDevice;
        if (bleDevice.isBeenMain() && this.currentBleDevice.getFixedId() == this.currentFixedGroup.getFixedId()) {
            GlobalToast.showText(getApplicationContext(), R.string.cancelFixedMain, 1);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<BleDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        arrayList2.remove(this.addDevicesList.indexOf(this.currentBleDevice));
        BLSBleLight.removeFixGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList), Tool.getListToArray(arrayList2));
        loading3000();
        deleteFromGroup();
    }

    private void deleteFromGroup() {
        this.realm.beginTransaction();
        this.currentFixedGroup.getBleDeviceRealmList().remove(this.currentBleDevice);
        if (this.currentBleDevice.isBeenMain() && this.currentBleDevice.getFixedId() == this.currentFixedGroup.getFixedId()) {
            this.currentBleDevice.setBeenMain(false);
            this.currentBleDevice.setFixedId(0);
        }
        this.realm.commitTransaction();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDevicesFromFixedGroup() {
        ArrayList arrayList = new ArrayList();
        Iterator<BleDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList(this.addDevicesList);
        Iterator<BleDevice> it2 = this.deleteList.iterator();
        while (it2.hasNext()) {
            arrayList3.remove(it2.next());
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList2.add(Integer.valueOf(((BleDevice) it3.next()).getAddr()));
        }
        BLSBleLight.removeFixGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList), Tool.getListToArray(arrayList2));
        loading3000();
        this.realm.beginTransaction();
        for (BleDevice bleDevice : this.deleteList) {
            this.currentFixedGroup.getBleDeviceRealmList().remove(bleDevice);
            if (bleDevice.isBeenMain() && bleDevice.getFixedId() == this.currentFixedGroup.getFixedId()) {
                bleDevice.setBeenMain(false);
                bleDevice.setFixedId(0);
            }
        }
        this.realm.commitTransaction();
        for (BleDevice bleDevice2 : this.addDevicesList) {
            bleDevice2.isGroupStatus = false;
            bleDevice2.isSelect = false;
        }
        this.deleteList.clear();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToFixedGroup(int i) {
        this.currentBleDevice = this.allDevicesList.get(i);
        ArrayList arrayList = new ArrayList();
        Iterator<BleDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        arrayList.add(Integer.valueOf(this.currentBleDevice.getAddr()));
        BLSBleLight.generateFixedGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList));
        loading3000();
        addToGroup();
    }

    private void addToGroup() {
        this.realm.beginTransaction();
        this.currentFixedGroup.getBleDeviceRealmList().add(this.currentBleDevice);
        this.realm.commitTransaction();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDevicesToFixedGroup() {
        ArrayList arrayList = new ArrayList();
        Iterator<BleDevice> it = this.addDevicesList.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        Iterator<BleDevice> it2 = this.addList.iterator();
        while (it2.hasNext()) {
            arrayList.add(Integer.valueOf(it2.next().getAddr()));
        }
        BLSBleLight.generateFixedGroupWithGroupId(this.currentGroupId, Tool.getListToArray(arrayList));
        loading3000();
        this.realm.beginTransaction();
        Iterator<BleDevice> it3 = this.addList.iterator();
        while (it3.hasNext()) {
            this.currentFixedGroup.getBleDeviceRealmList().add(it3.next());
        }
        this.realm.commitTransaction();
        for (BleDevice bleDevice : this.allDevicesList) {
            bleDevice.isGroupStatus = false;
            bleDevice.isSelect = false;
        }
        this.addList.clear();
        refreshGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMainDv(int i) {
        BleDevice bleDevice = this.addDevicesList.get(i);
        for (BleDevice bleDevice2 : this.addDevicesList) {
            if (bleDevice2.isBeenMain() && bleDevice2.getFixedId() == this.currentFixedGroup.getFixedId() && bleDevice != bleDevice2) {
                GlobalToast.showText(getApplicationContext(), R.string.hasMainDevice, 1);
                return;
            }
        }
        if (bleDevice.isBeenMain() && bleDevice.getFixedId() != this.currentFixedGroup.getFixedId()) {
            if (((FixedGroup) this.realm.where(FixedGroup.class).equalTo("fixedId", Integer.valueOf(bleDevice.getFixedId())).findFirst()) != null) {
                GlobalToast.showText(getApplicationContext(), R.string.beenMainDevice, 1);
                return;
            }
            this.realm.beginTransaction();
            bleDevice.setBeenMain(false);
            bleDevice.setFixedId(0);
            bleDevice.isCurrentFixedGroup = false;
            this.realm.commitTransaction();
        }
        startReceiveService();
        BleDevice bleDevice3 = this.addDevicesList.get(i);
        this.currentBleDevice = bleDevice3;
        this.addr = bleDevice3.getAddr();
        this.isReceiveCallback = false;
        boolean z = !this.currentBleDevice.isBeenMain();
        this.isSetMainDv = z;
        setGroupMainDv(z);
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
    }

    private void setGroupMainDv(final boolean z) {
        GlobalThreadPools.getInstance().execute(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setGroupMainDv$4(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupMainDv$4(boolean z) {
        if (sendSetGroupMainDv(z)) {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(101));
        } else {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(102));
        }
    }

    private boolean sendSetGroupMainDv(boolean z) {
        int i = 0;
        while (i < 15) {
            boolean z2 = z;
            if (!BLEFastconHelper.getInstance().controlSetGroupMainDev(this.addr, this.currentGroupId, z2, false, true)) {
                return false;
            }
            for (int i2 = 0; i2 < 9; i2++) {
                SystemClock.sleep(100L);
                if (this.isReceiveCallback) {
                    return true;
                }
            }
            i++;
            z = z2;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetGroupMainDv() {
        stopReceiveService();
        this.realm.beginTransaction();
        this.currentBleDevice.setBeenMain(this.isSetMainDv);
        if (this.isSetMainDv) {
            this.currentBleDevice.setFixedId(this.currentFixedGroup.getFixedId());
            this.currentBleDevice.isCurrentFixedGroup = true;
        } else {
            this.currentBleDevice.setFixedId(0);
            this.currentBleDevice.isCurrentFixedGroup = false;
        }
        this.realm.commitTransaction();
        this.bindingAdapter.notifyDataSetChanged();
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeout() {
        stopReceiveService();
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
        GlobalToast.showText(getApplicationContext(), R.string.tryAgain, 1);
    }

    private void loading3000() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loading3000$5();
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loading3000$5() {
        Dialog dialog = this.loadingDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.loadingDialog = null;
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Throwable {
            ManageBindActivity manageBindActivity = (ManageBindActivity) this.weakReference.get();
            if (message.what == 101) {
                manageBindActivity.handleSetGroupMainDv();
                return;
            }
            if (message.what == 102) {
                manageBindActivity.handleTimeout();
            } else if (message.what == 103) {
                manageBindActivity.sceneModifyImg();
            } else if (message.what == 104) {
                manageBindActivity.handleSceneImg((Bitmap) message.obj);
            }
        }
    }

    private void toGetPermission() {
        this.getPermissionLauncher.launch(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
    }

    private void IMGPermission() {
        if (Build.VERSION.SDK_INT >= 34) {
            this.IMGPermissionLauncher.launch(new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"});
        } else if (Build.VERSION.SDK_INT == 33) {
            this.IMGPermissionLauncher.launch(new String[]{"android.permission.READ_MEDIA_IMAGES"});
        } else {
            this.IMGPermissionLauncher.launch(new String[]{"android.permission.READ_EXTERNAL_STORAGE"});
        }
    }

    private void CMPermission() {
        this.CMPermissionLauncher.launch(new String[]{"android.permission.CAMERA"});
    }

    private void registerActivityResultLauncher() {
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda10
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ManageBindActivity.lambda$registerActivityResultLauncher$6((ActivityResult) obj);
            }
        });
        this.IMGPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda11
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$7((Map) obj);
            }
        });
        this.CMPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda12
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$8((Map) obj);
            }
        });
        this.cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda13
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$10((ActivityResult) obj);
            }
        });
        this.photoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda14
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$12((Uri) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$7(Map map) {
        if (Build.VERSION.SDK_INT >= 34) {
            if (map.get("android.permission.READ_MEDIA_VISUAL_USER_SELECTED") != null) {
                if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_MEDIA_VISUAL_USER_SELECTED"))).equals(true)) {
                    openAlbum();
                    return;
                } else {
                    scenePermissionDialog();
                    return;
                }
            }
            return;
        }
        if (Build.VERSION.SDK_INT == 33) {
            if (map.get("android.permission.READ_MEDIA_IMAGES") != null) {
                if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_MEDIA_IMAGES"))).equals(true)) {
                    openAlbum();
                    return;
                } else {
                    scenePermissionDialog();
                    return;
                }
            }
            return;
        }
        if (map.get("android.permission.READ_EXTERNAL_STORAGE") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_EXTERNAL_STORAGE"))).equals(true)) {
                openAlbum();
            } else {
                scenePermissionDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$8(Map map) {
        if (map.get("android.permission.CAMERA") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.CAMERA"))).equals(true)) {
                takePhoto();
            } else {
                scenePermissionDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$10(ActivityResult activityResult) {
        if (activityResult == null || activityResult.getResultCode() == 0) {
            return;
        }
        new Thread(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$registerActivityResultLauncher$9();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$9() {
        try {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(104, ImageUtils.processImage(this.imageUri, this)));
        } catch (IOException e) {
            Log.e("IOException: ", String.valueOf(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$12(final Uri uri) {
        if (uri == null) {
            return;
        }
        new Thread(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$registerActivityResultLauncher$11(uri);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$11(Uri uri) {
        try {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(104, ImageUtils.processImage(uri, this)));
        } catch (IOException e) {
            Log.e("IOException: ", String.valueOf(e));
        }
    }

    private void dialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.sceneRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$13(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$14(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$13(DialogInterface dialogInterface, int i) {
        this.myHandler.sendEmptyMessage(103);
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$14(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    private void scenePermissionDialog() {
        if (this.scenePermissionAlertDialog == null) {
            this.scenePermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.sceneRequestMessage).setMessage(R.string.sceneRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$scenePermissionDialog$15(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ManageBindActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$scenePermissionDialog$16(dialogInterface, i);
                }
            }).create();
        }
        this.scenePermissionAlertDialog.show();
        this.scenePermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scenePermissionDialog$15(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.scenePermissionAlertDialog.cancel();
        this.scenePermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scenePermissionDialog$16(DialogInterface dialogInterface, int i) {
        this.scenePermissionAlertDialog.cancel();
        this.scenePermissionAlertDialog = null;
    }
}

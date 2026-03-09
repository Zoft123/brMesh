package com.brgd.brblmesh.Main.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.SetUpAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.SetUpModel;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RadarSetUpActivity extends FatherActivity {
    AlertDialog alertDialog;
    ImageView backView;
    AlertDialog bleCASAlertDialog;
    DeleteRadarGroup currentDeleteGroups;
    int currentGroupsId;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    ImageView dismissView;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    private boolean isNewIn;
    AlertDialog locationAlertDialog;
    ActivityResultLauncher<String[]> locationPermissionLauncher;
    AlertDialog locationServiceAlertDialog;
    ActivityResultLauncher<Intent> locationServiceLauncher;
    private RadarPhoneType radarPhoneType;
    private Realm realm;
    RecyclerView recyclerView;
    private ConstraintLayout renameLayout;
    ImageView saveView;
    SetUpAdapter setUpAdapter;
    List<SetUpModel> setUpModelList;
    private final int[] imgArr = {R.drawable.group_add, R.drawable.group_devices, R.drawable.sort, R.drawable.rename, R.drawable.group_delete, R.drawable.sort, R.drawable.rename, R.drawable.group_delete};
    private final int[] titleArr = {R.string.setTitle1, R.string.setTitle2, R.string.setTitle3, R.string.setTitle4, R.string.setTitle5, R.string.setTitle6, R.string.setTitle7, R.string.setTitle8};
    int groupSize = 0;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.setup_back) {
                RadarSetUpActivity.this.finish();
                return;
            }
            if (id == R.id.new_group_rename_delete) {
                RadarSetUpActivity.this.deviceNameEdit.setText("");
                RadarSetUpActivity.this.deviceNameDelete.setSelected(false);
            } else if (id == R.id.new_group_rename_save) {
                RadarSetUpActivity.this.saveNewGroup();
            } else if (id == R.id.new_group_rename_dismiss) {
                RadarSetUpActivity.this.closeCreateGroupView();
            }
        }
    };
    public final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    static /* synthetic */ void lambda$registerActivityResultLauncher$5(ActivityResult activityResult) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_radar_set_up);
        initView();
        registerActivityResultLauncher();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.radarPhoneType = (RadarPhoneType) defaultInstance.where(RadarPhoneType.class).findFirst();
        ImageView imageView = (ImageView) findViewById(R.id.setup_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.renameLayout = (ConstraintLayout) findViewById(R.id.new_group_editView);
        this.deviceNameEdit = (EditText) findViewById(R.id.new_group_rename_edit);
        ImageView imageView2 = (ImageView) findViewById(R.id.new_group_rename_delete);
        this.deviceNameDelete = imageView2;
        imageView2.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (RadarSetUpActivity.this.delete_deviceName_tag) {
                        RadarSetUpActivity.this.deviceNameDelete.setSelected(true);
                        RadarSetUpActivity.this.deviceNameDelete.setEnabled(true);
                        RadarSetUpActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(RadarSetUpActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                RadarSetUpActivity.this.deviceNameDelete.setSelected(false);
                RadarSetUpActivity.this.deviceNameDelete.setEnabled(false);
                RadarSetUpActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.new_group_rename_save);
        this.saveView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView4 = (ImageView) findViewById(R.id.new_group_rename_dismiss);
        this.dismissView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
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
            GlobalToast.showText(getApplicationContext(), R.string.enterValid, 0);
            return;
        }
        if (Tool.checkStringIsSpaces(string)) {
            GlobalToast.showText(getApplicationContext(), R.string.canNotSpaces, 0);
            return;
        }
        String strTrim = string.trim();
        if (Tool.checkStringIsSpaces(strTrim)) {
            GlobalToast.showText(getApplicationContext(), R.string.canNotSpaces, 0);
            return;
        }
        if (strTrim.length() > 26) {
            GlobalToast.showText(getApplicationContext(), R.string.InputTip, 0);
            return;
        }
        Iterator it = this.realm.where(RadarGroup.class).findAll().iterator();
        while (it.hasNext()) {
            if (strTrim.equalsIgnoreCase(((RadarGroup) it.next()).getFixedName())) {
                GlobalToast.showText(getApplicationContext(), R.string.nameExist, 0);
                return;
            }
        }
        createGroup();
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), 0.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.createGroupSuccess);
        customDialog.setMessage(R.string.addDeviceToGroup);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$saveNewGroup$0(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.later, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveNewGroup$0(CustomDialog customDialog) {
        customDialog.dismiss();
        this.isNewIn = true;
        if (this.radarPhoneType.getPhoneType() == 1) {
            if (Tool.isAPI31()) {
                if (Tool.checkBleCASPermission()) {
                    Intent intent = new Intent(this, (Class<?>) RadarGroupDvActivity.class);
                    intent.putExtra(GlobalVariable.toGroupDevice, true);
                    startActivity(intent);
                    return;
                }
                bleCASDialog();
                return;
            }
            if (Tool.checkLocationPermission()) {
                getLocationPermission();
                return;
            } else {
                dialog();
                return;
            }
        }
        Intent intent2 = new Intent(this, (Class<?>) RadarGroupDvActivity.class);
        intent2.putExtra(GlobalVariable.toGroupDevice, true);
        startActivity(intent2);
    }

    private void createGroup() {
        this.groupSize = (int) this.realm.where(RadarGroup.class).count();
        DeleteRadarGroup deleteRadarGroup = (DeleteRadarGroup) this.realm.where(DeleteRadarGroup.class).findFirst();
        this.currentDeleteGroups = deleteRadarGroup;
        if (deleteRadarGroup != null) {
            this.currentGroupsId = deleteRadarGroup.getFixedId();
        } else {
            this.currentGroupsId = this.groupSize + 193;
        }
        this.realm.beginTransaction();
        RadarGroup radarGroup = (RadarGroup) this.realm.createObject(RadarGroup.class);
        radarGroup.setIndex(this.groupSize);
        radarGroup.setFixedName(this.deviceNameEdit.getText().toString());
        radarGroup.setFixedId(this.currentGroupsId);
        this.realm.commitTransaction();
        if (this.currentDeleteGroups != null) {
            this.realm.beginTransaction();
            this.currentDeleteGroups.deleteFromRealm();
            this.realm.commitTransaction();
        }
        closeCreateGroupView();
    }

    private void handleClick(int i) {
        if (Tool.bleNotOnToast(getApplicationContext())) {
            finish();
        }
        switch (i) {
            case 0:
                if (((int) this.realm.where(RadarGroup.class).count()) == 59) {
                    GlobalToast.showText(getApplicationContext(), R.string.maxGroupNum, 0);
                } else {
                    showCreateGroupView();
                }
                break;
            case 1:
                if (((int) this.realm.where(RadarGroup.class).count()) != 0) {
                    this.isNewIn = false;
                    if (this.radarPhoneType.getPhoneType() == 1) {
                        if (Tool.isAPI31()) {
                            if (Tool.checkBleCASPermission()) {
                                startActivity(new Intent(this, (Class<?>) RadarGroupDvActivity.class));
                            } else {
                                bleCASDialog();
                            }
                        } else if (Tool.checkLocationPermission()) {
                            getLocationPermission();
                        } else {
                            dialog();
                        }
                    } else {
                        startActivity(new Intent(this, (Class<?>) RadarGroupDvActivity.class));
                    }
                } else {
                    GlobalToast.showText(getApplicationContext(), R.string.NoGroupTip, 0);
                }
                break;
            case 2:
                startActivity(new Intent(this, (Class<?>) RadarGroupSortActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, (Class<?>) RadarGroupRenameActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, (Class<?>) DeleteRadarGroupActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, (Class<?>) RadarDvSortActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, (Class<?>) RadarDvRenameActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, (Class<?>) DeleteRadarDvActivity.class));
                break;
        }
    }

    private void dialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.locationAddressRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$1(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$2(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$1(DialogInterface dialogInterface, int i) {
        getLocationPermission();
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$2(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    public static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
        }

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }
    }

    private void enableLocationService() {
        this.locationServiceLauncher.launch(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    private void locationPermission() {
        this.locationPermissionLauncher.launch(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"});
    }

    private void toGetPermission() {
        this.getPermissionLauncher.launch(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
    }

    private void registerActivityResultLauncher() {
        this.locationServiceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda10
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((ActivityResult) obj);
            }
        });
        this.locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda11
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$4((Map) obj);
            }
        });
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                RadarSetUpActivity.lambda$registerActivityResultLauncher$5((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$3(ActivityResult activityResult) {
        if (Tool.checkLocationServiceEnable(this)) {
            return;
        }
        GlobalToast.showText(getApplicationContext(), R.string.locationServiceRequest, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$4(Map map) {
        if (map.get("android.permission.ACCESS_FINE_LOCATION") == null || map.get("android.permission.ACCESS_COARSE_LOCATION") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_FINE_LOCATION"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_COARSE_LOCATION"))).equals(true)) {
            Intent intent = new Intent(this, (Class<?>) RadarGroupDvActivity.class);
            if (this.isNewIn) {
                intent.putExtra(GlobalVariable.toGroupDevice, true);
            }
            startActivity(intent);
            return;
        }
        locationDialog();
    }

    public void getLocationPermission() {
        if (Tool.checkLocationServiceEnable(this)) {
            if (Tool.checkLocationPermission()) {
                Intent intent = new Intent(this, (Class<?>) RadarGroupDvActivity.class);
                if (this.isNewIn) {
                    intent.putExtra(GlobalVariable.toGroupDevice, true);
                }
                startActivity(intent);
                return;
            }
            locationPermission();
            return;
        }
        locationServiceDialog();
    }

    private void locationServiceDialog() {
        if (this.locationServiceAlertDialog == null) {
            this.locationServiceAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationServiceRequest).setMessage(R.string.locationServiceRequestMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$6(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$7(dialogInterface, i);
                }
            }).create();
        }
        this.locationServiceAlertDialog.show();
        this.locationServiceAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$6(DialogInterface dialogInterface, int i) {
        enableLocationService();
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$7(DialogInterface dialogInterface, int i) {
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationAddressRequestMessage).setMessage(R.string.locationAddressRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$8(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$9(dialogInterface, i);
                }
            }).create();
        }
        this.locationAlertDialog.show();
        this.locationAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$8(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$9(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    public void bleCASDialog() {
        if (this.bleCASAlertDialog == null) {
            this.bleCASAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.bleCA).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$10(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarSetUpActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$11(dialogInterface, i);
                }
            }).create();
        }
        this.bleCASAlertDialog.show();
        this.bleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$10(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$11(DialogInterface dialogInterface, int i) {
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }
}

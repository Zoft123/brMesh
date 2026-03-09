package com.brgd.brblmesh.Main.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.BindAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BindActivity extends FatherActivity {
    AlertDialog alertDialog;
    ImageView backView;
    BindAdapter bindAdapter;
    AlertDialog bleCASAlertDialog;
    DeleteFixedGroup currentDeleteFixedGroup;
    private FixedGroup currentFixGroup;
    int currentFixedId;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    ImageView dismissView;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    ImageView instructionView;
    private boolean isAdd;
    private List<FixedGroup> list;
    AlertDialog locationAlertDialog;
    ActivityResultLauncher<String[]> locationPermissionLauncher;
    AlertDialog locationServiceAlertDialog;
    ActivityResultLauncher<Intent> locationServiceLauncher;
    private Realm realm;
    private RealmResults<FixedGroup> realmResults;
    RecyclerView recyclerView;
    private ConstraintLayout renameLayout;
    ImageView saveView;
    int groupSize = 0;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.bind_back) {
                BindActivity.this.finish();
                return;
            }
            if (id == R.id.bind_instruction) {
                BindActivity.this.startActivity(new Intent(BindActivity.this, (Class<?>) AboutBindActivity.class));
            } else if (id == R.id.new_group_rename_delete) {
                BindActivity.this.deviceNameEdit.setText("");
                BindActivity.this.deviceNameDelete.setSelected(false);
            } else if (id == R.id.new_group_rename_save) {
                BindActivity.this.saveNewGroup();
            } else if (id == R.id.new_group_rename_dismiss) {
                BindActivity.this.closeCreateGroupView();
            }
        }
    };

    static /* synthetic */ void lambda$registerActivityResultLauncher$4(ActivityResult activityResult) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bind);
        initView();
        registerActivityResultLauncher();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        refreshFixGroup();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        setupFixGroup();
        ImageView imageView = (ImageView) findViewById(R.id.bind_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.bind_instruction);
        this.instructionView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.recyclerView = (RecyclerView) findViewById(R.id.bind_list);
        this.list = new ArrayList();
        this.bindAdapter = new BindAdapter(this, this.list);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.recyclerView.setAdapter(this.bindAdapter);
        this.bindAdapter.setOnItemClickListener(new BindAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.BindAdapter.OnItemClickListener
            public void onItemClick(View view, int i) {
                if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                    BindActivity.this.bleCASDialog();
                    return;
                }
                if (Tool.bleNotOnToast(BindActivity.this.getApplicationContext())) {
                    return;
                }
                BindActivity bindActivity = BindActivity.this;
                bindActivity.currentFixGroup = (FixedGroup) bindActivity.list.get(i);
                BindActivity bindActivity2 = BindActivity.this;
                bindActivity2.isAdd = bindActivity2.currentFixGroup.getFixedId() == 0;
                if (Tool.checkLocationPermission()) {
                    BindActivity.this.getLocationPermission();
                } else {
                    BindActivity.this.dialog();
                }
            }
        });
        this.renameLayout = (ConstraintLayout) findViewById(R.id.new_group_editView);
        this.deviceNameEdit = (EditText) findViewById(R.id.new_group_rename_edit);
        ImageView imageView3 = (ImageView) findViewById(R.id.new_group_rename_delete);
        this.deviceNameDelete = imageView3;
        imageView3.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (BindActivity.this.delete_deviceName_tag) {
                        BindActivity.this.deviceNameDelete.setSelected(true);
                        BindActivity.this.deviceNameDelete.setEnabled(true);
                        BindActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(BindActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                BindActivity.this.deviceNameDelete.setSelected(false);
                BindActivity.this.deviceNameDelete.setEnabled(false);
                BindActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.new_group_rename_save);
        this.saveView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView5 = (ImageView) findViewById(R.id.new_group_rename_dismiss);
        this.dismissView = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        refreshFixGroup();
    }

    private void refreshFixGroup() {
        this.list.clear();
        RealmResults<FixedGroup> realmResultsFindAll = this.realm.where(FixedGroup.class).findAll();
        this.realmResults = realmResultsFindAll;
        for (FixedGroup fixedGroup : realmResultsFindAll) {
            if (fixedGroup.getFixedName().contains("Binding")) {
                this.realm.beginTransaction();
                fixedGroup.setFixedName(fixedGroup.getFixedName().replace("Binding", "Room"));
                this.realm.commitTransaction();
            }
        }
        this.list.addAll(this.realmResults);
        FixedGroup fixedGroup2 = new FixedGroup();
        fixedGroup2.setFixedId(0);
        this.list.add(fixedGroup2);
        this.bindAdapter.notifyDataSetChanged();
    }

    private void setupFixGroup() {
        if (((int) this.realm.where(FixedGroup.class).count()) == 0) {
            int i = 0;
            while (i < 4) {
                this.realm.beginTransaction();
                FixedGroup fixedGroup = (FixedGroup) this.realm.createObject(FixedGroup.class);
                fixedGroup.setFixedId(i + 193);
                StringBuilder sb = new StringBuilder("Binding ");
                i++;
                sb.append(i);
                fixedGroup.setFixedName(sb.toString());
                fixedGroup.setIndex(i);
                fixedGroup.setIconIndex(i);
                this.realm.commitTransaction();
            }
        }
    }

    private void editFixGroup() {
        Intent intent = new Intent(this, (Class<?>) ManageBindActivity.class);
        intent.putExtra(GlobalVariable.toFixGroupCtrl, this.currentFixGroup.getFixedId());
        startActivity(intent);
    }

    private void addFixGroup() {
        if (this.realmResults.size() == 30) {
            GlobalToast.showText(getApplicationContext(), R.string.maxFixedNum, 0);
        } else {
            showCreateGroupView();
        }
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
        Iterator it = this.realmResults.iterator();
        while (it.hasNext()) {
            if (strTrim.equalsIgnoreCase(((FixedGroup) it.next()).getFixedName())) {
                GlobalToast.showText(getApplicationContext(), R.string.nameExist, 0);
                return;
            }
        }
        createGroup();
    }

    private void createGroup() {
        this.groupSize = this.realmResults.size();
        DeleteFixedGroup deleteFixedGroup = (DeleteFixedGroup) this.realm.where(DeleteFixedGroup.class).findFirst();
        this.currentDeleteFixedGroup = deleteFixedGroup;
        if (deleteFixedGroup != null) {
            this.currentFixedId = deleteFixedGroup.getFixedId();
        } else {
            this.currentFixedId = this.groupSize + 193;
        }
        this.realm.beginTransaction();
        FixedGroup fixedGroup = (FixedGroup) this.realm.createObject(FixedGroup.class);
        fixedGroup.setFixedId(this.currentFixedId);
        fixedGroup.setFixedName(this.deviceNameEdit.getText().toString());
        fixedGroup.setIndex(this.groupSize + 1);
        fixedGroup.setIconIndex(this.groupSize + 1);
        this.realm.commitTransaction();
        if (this.currentDeleteFixedGroup != null) {
            this.realm.beginTransaction();
            this.currentDeleteFixedGroup.deleteFromRealm();
            this.realm.commitTransaction();
        }
        closeCreateGroupView();
        Intent intent = new Intent(this, (Class<?>) ManageBindActivity.class);
        intent.putExtra(GlobalVariable.toFixGroupCtrl, this.currentFixedId);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.locationAddressRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$0(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$1(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$0(DialogInterface dialogInterface, int i) {
        getLocationPermission();
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$1(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
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
        this.locationServiceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda9
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$2((ActivityResult) obj);
            }
        });
        this.locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda10
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((Map) obj);
            }
        });
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BindActivity.lambda$registerActivityResultLauncher$4((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$2(ActivityResult activityResult) {
        if (Tool.checkLocationServiceEnable(this)) {
            return;
        }
        GlobalToast.showText(getApplicationContext(), R.string.locationServiceRequest, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$3(Map map) {
        if (map.get("android.permission.ACCESS_FINE_LOCATION") == null || map.get("android.permission.ACCESS_COARSE_LOCATION") == null) {
            return;
        }
        if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_FINE_LOCATION"))).equals(true) && ((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.ACCESS_COARSE_LOCATION"))).equals(true)) {
            if (this.isAdd) {
                addFixGroup();
                return;
            } else {
                editFixGroup();
                return;
            }
        }
        locationDialog();
    }

    public void getLocationPermission() {
        if (Tool.checkLocationServiceEnable(this)) {
            if (Tool.checkLocationPermission()) {
                if (this.isAdd) {
                    addFixGroup();
                    return;
                } else {
                    editFixGroup();
                    return;
                }
            }
            locationPermission();
            return;
        }
        locationServiceDialog();
    }

    private void locationServiceDialog() {
        if (this.locationServiceAlertDialog == null) {
            this.locationServiceAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationServiceRequest).setMessage(R.string.locationServiceRequestMessage).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$5(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationServiceDialog$6(dialogInterface, i);
                }
            }).create();
        }
        this.locationServiceAlertDialog.show();
        this.locationServiceAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$5(DialogInterface dialogInterface, int i) {
        enableLocationService();
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationServiceDialog$6(DialogInterface dialogInterface, int i) {
        this.locationServiceAlertDialog.cancel();
        this.locationServiceAlertDialog = null;
    }

    private void locationDialog() {
        if (this.locationAlertDialog == null) {
            this.locationAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.locationAddressRequestMessage).setMessage(R.string.locationAddressRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$locationDialog$7(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda4
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
        toGetPermission();
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$locationDialog$8(DialogInterface dialogInterface, int i) {
        this.locationAlertDialog.cancel();
        this.locationAlertDialog = null;
    }

    public void bleCASDialog() {
        if (this.bleCASAlertDialog == null) {
            this.bleCASAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.bleCA).setMessage(R.string.noPerMission).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$9(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.BindActivity$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$bleCASDialog$10(dialogInterface, i);
                }
            }).create();
        }
        this.bleCASAlertDialog.show();
        this.bleCASAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$9(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bleCASDialog$10(DialogInterface dialogInterface, int i) {
        this.bleCASAlertDialog.cancel();
        this.bleCASAlertDialog = null;
    }
}

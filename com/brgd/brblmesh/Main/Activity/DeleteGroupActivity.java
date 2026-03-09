package com.brgd.brblmesh.Main.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.GroupsListAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeleteGroupActivity extends FatherActivity {
    private static final int DELETE_GROUP = 101;
    AlertDialog alertDialog;
    ImageView backView;
    private List<BleDevice> bleDeviceList;
    private Groups currentDeleteGroups;
    private int currentDeleteGroupsIndex;
    private List<Groups> groupsList;
    private GroupsListAdapter groupsListAdapter;
    private PhoneType phoneType;
    Realm realm;
    private RecyclerView recyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteGroupActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.delete_group_back) {
                DeleteGroupActivity.this.finish();
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_delete_group);
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
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        ImageView imageView = (ImageView) findViewById(R.id.delete_group_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.groupsList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.delete_group_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        GroupsListAdapter groupsListAdapter = new GroupsListAdapter(this, this.groupsList);
        this.groupsListAdapter = groupsListAdapter;
        this.recyclerView.setAdapter(groupsListAdapter);
        this.groupsListAdapter.setOnItemClickListener(new GroupsListAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteGroupActivity$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralAdapter.GroupsListAdapter.OnItemClickListener
            public final void onClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        this.currentDeleteGroupsIndex = -1;
        this.bleDeviceList = new ArrayList();
        refreshData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        this.currentDeleteGroupsIndex = i;
        handleDeleteClick();
    }

    private void refreshData() {
        this.groupsList.clear();
        RealmResults realmResultsSort = this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            for (Groups groups : this.groupsList) {
                groups.isDelete = true;
                groups.isSort = false;
                groups.isRename = false;
            }
        }
        this.groupsListAdapter.notifyDataSetChanged();
    }

    private void handleDeleteClick() {
        this.currentDeleteGroups = this.groupsList.get(this.currentDeleteGroupsIndex);
        final RealmResults realmResultsFindAll = this.realm.where(BleDevice.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentDeleteGroups.getGroupId())).findAll();
        if (this.phoneType.getPhoneType() == 1 && !realmResultsFindAll.isEmpty()) {
            tipDialog();
            return;
        }
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.recyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.confirmDelete);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteGroupActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$handleDeleteClick$1(customDialog, realmResultsFindAll);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteClick$1(CustomDialog customDialog, RealmResults realmResults) {
        customDialog.dismiss();
        this.bleDeviceList.clear();
        this.bleDeviceList.addAll(realmResults);
        handleDeleteGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeleteGroup() {
        if (!this.bleDeviceList.isEmpty()) {
            deleteGroup();
            return;
        }
        DeleteGroups deleteGroups = (DeleteGroups) this.realm.where(DeleteGroups.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentDeleteGroups.getGroupId())).findFirst();
        this.realm.beginTransaction();
        if (deleteGroups == null) {
            deleteGroups = (DeleteGroups) this.realm.createObject(DeleteGroups.class);
        }
        deleteGroups.setGroupId(this.currentDeleteGroups.getGroupId());
        this.realm.commitTransaction();
        int i = this.currentDeleteGroupsIndex;
        this.realm.beginTransaction();
        this.currentDeleteGroups.deleteFromRealm();
        this.realm.commitTransaction();
        handleAfterDeleteGroup(i);
    }

    private void handleAfterDeleteGroup(int i) {
        RealmResults<Groups> realmResultsSort = this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).findAll().sort(GlobalVariable.index);
        if (realmResultsSort.isEmpty()) {
            RealmResults realmResultsFindAll = this.realm.where(DeleteGroups.class).findAll();
            if (!realmResultsFindAll.isEmpty()) {
                this.realm.beginTransaction();
                realmResultsFindAll.deleteAllFromRealm();
                this.realm.commitTransaction();
            }
        } else {
            this.realm.beginTransaction();
            for (Groups groups : realmResultsSort) {
                if (groups.getIndex() > i) {
                    groups.setIndex(groups.getIndex() - 1);
                }
            }
            this.realm.commitTransaction();
        }
        refreshData();
    }

    private void deleteGroup() {
        for (BleDevice bleDevice : this.bleDeviceList) {
            this.realm.beginTransaction();
            bleDevice.setGroupId(0);
            bleDevice.setGroupIndex(-1);
            this.realm.commitTransaction();
        }
        DeleteGroups deleteGroups = (DeleteGroups) this.realm.where(DeleteGroups.class).equalTo(GlobalVariable.groupId, Integer.valueOf(this.currentDeleteGroups.getGroupId())).findFirst();
        this.realm.beginTransaction();
        if (deleteGroups == null) {
            deleteGroups = (DeleteGroups) this.realm.createObject(DeleteGroups.class);
        }
        deleteGroups.setGroupId(this.currentDeleteGroups.getGroupId());
        this.realm.commitTransaction();
        int i = this.currentDeleteGroupsIndex;
        this.realm.beginTransaction();
        this.currentDeleteGroups.deleteFromRealm();
        this.realm.commitTransaction();
        handleAfterDeleteGroup(i);
    }

    private void tipDialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setTitle(R.string.prompt).setMessage(R.string.needMoveDevice).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteGroupActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$tipDialog$2(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tipDialog$2(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            DeleteGroupActivity deleteGroupActivity = (DeleteGroupActivity) this.weakReference.get();
            if (message.what == 101) {
                deleteGroupActivity.handleDeleteGroup();
            }
        }
    }
}

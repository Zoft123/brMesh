package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.RadarGrouplistAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralClass.CustomDialog;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeleteRadarGroupActivity extends FatherActivity {
    ImageView backView;
    private int currentDeleteGroupsIndex;
    private RadarGroup currentRadarGroup;
    private Dialog loadingDialog;
    private List<RadarGroup> radarGroupList;
    private RadarGrouplistAdapter radarGrouplistAdapter;
    Realm realm;
    private RecyclerView recyclerView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarGroupActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.delete_radarGroup_back) {
                DeleteRadarGroupActivity.this.finish();
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_delete_radar_group);
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
        ImageView imageView = (ImageView) findViewById(R.id.delete_radarGroup_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.radarGroupList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.delete_radarGroup_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        RadarGrouplistAdapter radarGrouplistAdapter = new RadarGrouplistAdapter(this, this.radarGroupList);
        this.radarGrouplistAdapter = radarGrouplistAdapter;
        this.recyclerView.setAdapter(radarGrouplistAdapter);
        this.radarGrouplistAdapter.setOnItemClickListener(new RadarGrouplistAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarGroupActivity$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarGrouplistAdapter.OnItemClickListener
            public final void onClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        this.currentDeleteGroupsIndex = -1;
        refreshData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        this.currentDeleteGroupsIndex = i;
        handleDeleteClick();
    }

    private void refreshData() {
        this.radarGroupList.clear();
        RealmResults realmResultsSort = this.realm.where(RadarGroup.class).notEqualTo("fixedId", (Integer) 0).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.radarGroupList.addAll(realmResultsSort);
            for (RadarGroup radarGroup : this.radarGroupList) {
                radarGroup.isDelete = true;
                radarGroup.isSort = false;
                radarGroup.isRename = false;
            }
        }
        this.radarGrouplistAdapter.notifyDataSetChanged();
    }

    private void handleDeleteClick() {
        this.currentRadarGroup = this.radarGroupList.get(this.currentDeleteGroupsIndex);
        final CustomDialog customDialog = new CustomDialog(this);
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams attributes = ((Window) Objects.requireNonNull(window)).getAttributes();
        attributes.x = 0;
        attributes.y = -Tool.dpToPx(GlobalApplication.getMyApplication().getApplicationContext(), this.recyclerView.getWidth() / 8.0f);
        window.setAttributes(attributes);
        customDialog.setTitle(R.string.prompt);
        customDialog.setMessage(R.string.confirmDelete);
        customDialog.setOnSureClickListener(R.string.Sure, new CustomDialog.onSureClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarGroupActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.CustomDialog.onSureClickListener
            public final void onSureClick() {
                this.f$0.lambda$handleDeleteClick$1(customDialog);
            }
        });
        customDialog.setOnCancelClickListener(R.string.Cancel, new DeleteDevicesActivity$$ExternalSyntheticLambda1(customDialog));
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteClick$1(CustomDialog customDialog) {
        customDialog.dismiss();
        ArrayList arrayList = new ArrayList();
        Iterator<RadarDevice> it = this.currentRadarGroup.getBleDeviceRealmList().iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getAddr()));
        }
        BLSBleLight.removeFixGroupWithGroupId(this.currentRadarGroup.getFixedId(), Tool.getListToArray(arrayList), Tool.getListToArray(new ArrayList()));
        loading100();
        handleDeleteGroup();
    }

    private void handleDeleteGroup() {
        DeleteRadarGroup deleteRadarGroup = (DeleteRadarGroup) this.realm.where(DeleteRadarGroup.class).equalTo("fixedId", Integer.valueOf(this.currentRadarGroup.getFixedId())).findFirst();
        this.realm.beginTransaction();
        if (deleteRadarGroup == null) {
            deleteRadarGroup = (DeleteRadarGroup) this.realm.createObject(DeleteRadarGroup.class);
        }
        deleteRadarGroup.setFixedId(this.currentRadarGroup.getFixedId());
        this.realm.commitTransaction();
        int i = this.currentDeleteGroupsIndex;
        this.realm.beginTransaction();
        this.currentRadarGroup.deleteFromRealm();
        this.realm.commitTransaction();
        handleAfterDeleteGroup(i);
    }

    private void handleAfterDeleteGroup(int i) {
        RealmResults<RadarGroup> realmResultsSort = this.realm.where(RadarGroup.class).notEqualTo("fixedId", (Integer) 0).findAll().sort(GlobalVariable.index);
        if (realmResultsSort.isEmpty()) {
            RealmResults realmResultsFindAll = this.realm.where(DeleteRadarGroup.class).findAll();
            if (!realmResultsFindAll.isEmpty()) {
                this.realm.beginTransaction();
                realmResultsFindAll.deleteAllFromRealm();
                this.realm.commitTransaction();
            }
        } else {
            this.realm.beginTransaction();
            for (RadarGroup radarGroup : realmResultsSort) {
                if (radarGroup.getIndex() > i) {
                    radarGroup.setIndex(radarGroup.getIndex() - 1);
                }
            }
            this.realm.commitTransaction();
        }
        refreshData();
    }

    private void loading100() {
        if (this.loadingDialog == null) {
            this.loadingDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.DeleteRadarGroupActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loading100$2();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loading100$2() {
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

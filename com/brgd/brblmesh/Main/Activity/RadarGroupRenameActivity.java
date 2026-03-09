package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.RadarGrouplistAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RadarGroupRenameActivity extends FatherActivity {
    ImageView backView;
    private RadarGroup currentGroups;
    private boolean delete_groupName_tag;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupRenameActivity.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.group_rename_back) {
                RadarGroupRenameActivity.this.finish();
                return;
            }
            if (id == R.id.group_rename_delete) {
                RadarGroupRenameActivity.this.groupNameEdit.setText("");
                RadarGroupRenameActivity.this.groupNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.group_rename_save) {
                String string = RadarGroupRenameActivity.this.groupNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                Iterator it = RadarGroupRenameActivity.this.groupsList.iterator();
                while (it.hasNext()) {
                    if (strTrim.equalsIgnoreCase(((RadarGroup) it.next()).getFixedName())) {
                        GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.nameExist, 0);
                        return;
                    }
                }
                if (RadarGroupRenameActivity.this.currentGroups != null) {
                    RadarGroupRenameActivity.this.realm.beginTransaction();
                    RadarGroupRenameActivity.this.currentGroups.setFixedName(strTrim);
                    RadarGroupRenameActivity.this.realm.commitTransaction();
                }
                RadarGroupRenameActivity.this.closeRenameView();
                GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.SaveSuccess, 0);
                RadarGroupRenameActivity.this.radarGrouplistAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.group_rename_dismiss) {
                RadarGroupRenameActivity.this.closeRenameView();
            }
        }
    };
    ImageView dismissView;
    private ImageView groupNameDelete;
    private EditText groupNameEdit;
    private TextView groupNameText;
    private List<RadarGroup> groupsList;
    private RadarGrouplistAdapter radarGrouplistAdapter;
    Realm realm;
    RecyclerView recyclerView;
    private ConstraintLayout renameLayout;
    ImageView saveView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_radar_group_rename);
        initView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.group_rename_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.groupsList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.group_rename_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        RadarGrouplistAdapter radarGrouplistAdapter = new RadarGrouplistAdapter(this, this.groupsList);
        this.radarGrouplistAdapter = radarGrouplistAdapter;
        this.recyclerView.setAdapter(radarGrouplistAdapter);
        this.radarGrouplistAdapter.setOnItemClickListener(new RadarGrouplistAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupRenameActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarGrouplistAdapter.OnItemClickListener
            public final void onClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        RealmResults realmResultsSort = this.realm.where(RadarGroup.class).notEqualTo("fixedId", (Integer) 0).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            for (RadarGroup radarGroup : this.groupsList) {
                radarGroup.isRename = true;
                radarGroup.isSort = false;
                radarGroup.isDelete = false;
            }
            this.radarGrouplistAdapter.notifyDataSetChanged();
        }
        this.renameLayout = (ConstraintLayout) findViewById(R.id.group_rename_editView);
        this.groupNameText = (TextView) findViewById(R.id.group_rename_name);
        this.groupNameEdit = (EditText) findViewById(R.id.group_rename_edit);
        ImageView imageView2 = (ImageView) findViewById(R.id.group_rename_delete);
        this.groupNameDelete = imageView2;
        imageView2.setSelected(false);
        this.groupNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_groupName_tag = true;
        this.groupNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.RadarGroupRenameActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (RadarGroupRenameActivity.this.delete_groupName_tag) {
                        RadarGroupRenameActivity.this.groupNameDelete.setSelected(true);
                        RadarGroupRenameActivity.this.groupNameDelete.setEnabled(true);
                        RadarGroupRenameActivity.this.delete_groupName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(RadarGroupRenameActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                RadarGroupRenameActivity.this.groupNameDelete.setSelected(false);
                RadarGroupRenameActivity.this.groupNameDelete.setEnabled(false);
                RadarGroupRenameActivity.this.delete_groupName_tag = true;
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.group_rename_save);
        this.saveView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView4 = (ImageView) findViewById(R.id.group_rename_dismiss);
        this.dismissView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        handleGroupRename(i);
    }

    private void handleGroupRename(int i) {
        RadarGroup radarGroup = this.groupsList.get(i);
        this.currentGroups = radarGroup;
        this.groupNameText.setText(radarGroup.getFixedName());
        this.groupNameEdit.setText(this.currentGroups.getFixedName());
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.groupNameEdit.setText("");
        this.renameLayout.setVisibility(8);
        this.currentGroups = null;
    }
}

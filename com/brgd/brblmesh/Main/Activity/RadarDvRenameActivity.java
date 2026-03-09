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
import com.brgd.brblmesh.GeneralAdapter.RadarDvRenameAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
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
public class RadarDvRenameActivity extends FatherActivity {
    ImageView backView;
    private List<RadarDevice> bleDeviceList;
    private RadarDevice currentRadarDv;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private TextView deviceNameText;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvRenameActivity.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.devices_rename_back) {
                RadarDvRenameActivity.this.finish();
                return;
            }
            if (id == R.id.device_rename_delete) {
                RadarDvRenameActivity.this.deviceNameEdit.setText("");
                RadarDvRenameActivity.this.deviceNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.device_rename_save) {
                String string = RadarDvRenameActivity.this.deviceNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                Iterator it = RadarDvRenameActivity.this.bleDeviceList.iterator();
                while (it.hasNext()) {
                    if (strTrim.equalsIgnoreCase(((RadarDevice) it.next()).getName())) {
                        GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.nameExist, 0);
                        return;
                    }
                }
                if (RadarDvRenameActivity.this.currentRadarDv != null) {
                    RadarDvRenameActivity.this.realm.beginTransaction();
                    RadarDvRenameActivity.this.currentRadarDv.setName(strTrim);
                    RadarDvRenameActivity.this.realm.commitTransaction();
                }
                RadarDvRenameActivity.this.closeRenameView();
                GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.SaveSuccess, 0);
                RadarDvRenameActivity.this.radarDvRenameAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.device_rename_dismiss) {
                RadarDvRenameActivity.this.closeRenameView();
            }
        }
    };
    ImageView dismissView;
    private RadarDvRenameAdapter radarDvRenameAdapter;
    Realm realm;
    RecyclerView recyclerView;
    private ConstraintLayout renameLayout;
    ImageView saveView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_radar_dv_rename);
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
        ImageView imageView = (ImageView) findViewById(R.id.devices_rename_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.bleDeviceList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.device_rename_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        RadarDvRenameAdapter radarDvRenameAdapter = new RadarDvRenameAdapter(this, this.bleDeviceList);
        this.radarDvRenameAdapter = radarDvRenameAdapter;
        this.recyclerView.setAdapter(radarDvRenameAdapter);
        this.radarDvRenameAdapter.setOnItemClickListener(new RadarDvRenameAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvRenameActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDvRenameAdapter.OnItemClickListener
            public void onOffClick(View view, int i, boolean z) {
                GlobalBluetooth.getInstance().singleOnOff(((RadarDevice) RadarDvRenameActivity.this.bleDeviceList.get(i)).getAddr(), z);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.RadarDvRenameAdapter.OnItemClickListener
            public void onRenameClick(View view, int i) {
                RadarDvRenameActivity.this.handleDeviceRename(i);
            }
        });
        RealmResults realmResultsSort = this.realm.where(RadarDevice.class).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.bleDeviceList.addAll(realmResultsSort);
            this.radarDvRenameAdapter.notifyDataSetChanged();
        }
        this.renameLayout = (ConstraintLayout) findViewById(R.id.device_rename_editView);
        this.deviceNameText = (TextView) findViewById(R.id.device_rename_name);
        this.deviceNameEdit = (EditText) findViewById(R.id.device_rename_edit);
        ImageView imageView2 = (ImageView) findViewById(R.id.device_rename_delete);
        this.deviceNameDelete = imageView2;
        imageView2.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.RadarDvRenameActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (RadarDvRenameActivity.this.delete_deviceName_tag) {
                        RadarDvRenameActivity.this.deviceNameDelete.setSelected(true);
                        RadarDvRenameActivity.this.deviceNameDelete.setEnabled(true);
                        RadarDvRenameActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(RadarDvRenameActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                RadarDvRenameActivity.this.deviceNameDelete.setSelected(false);
                RadarDvRenameActivity.this.deviceNameDelete.setEnabled(false);
                RadarDvRenameActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.device_rename_save);
        this.saveView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView4 = (ImageView) findViewById(R.id.device_rename_dismiss);
        this.dismissView = imageView4;
        imageView4.setOnClickListener(this.disDoubleClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceRename(int i) {
        RadarDevice radarDevice = this.bleDeviceList.get(i);
        this.currentRadarDv = radarDevice;
        this.deviceNameText.setText(radarDevice.getName());
        this.deviceNameEdit.setText(this.currentRadarDv.getName());
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
        this.currentRadarDv = null;
    }
}

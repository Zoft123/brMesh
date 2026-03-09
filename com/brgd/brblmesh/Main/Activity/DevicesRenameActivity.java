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
import com.brgd.brblmesh.GeneralAdapter.BleDeviceRenameAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
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
public class DevicesRenameActivity extends FatherActivity {
    ImageView backView;
    private List<BleDevice> bleDeviceList;
    private BleDeviceRenameAdapter bleDeviceRenameAdapter;
    private BleDevice currentBleDevice;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private TextView deviceNameText;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DevicesRenameActivity.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.devices_rename_back) {
                DevicesRenameActivity.this.finish();
                return;
            }
            if (id == R.id.device_rename_delete) {
                DevicesRenameActivity.this.deviceNameEdit.setText("");
                DevicesRenameActivity.this.deviceNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.device_rename_save) {
                String string = DevicesRenameActivity.this.deviceNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                Iterator it = DevicesRenameActivity.this.bleDeviceList.iterator();
                while (it.hasNext()) {
                    if (strTrim.equalsIgnoreCase(((BleDevice) it.next()).getName())) {
                        GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.nameExist, 0);
                        return;
                    }
                }
                if (DevicesRenameActivity.this.currentBleDevice != null) {
                    DevicesRenameActivity.this.realm.beginTransaction();
                    DevicesRenameActivity.this.currentBleDevice.setName(strTrim);
                    DevicesRenameActivity.this.realm.commitTransaction();
                }
                DevicesRenameActivity.this.closeRenameView();
                GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.SaveSuccess, 0);
                DevicesRenameActivity.this.bleDeviceRenameAdapter.notifyDataSetChanged();
                return;
            }
            if (id == R.id.device_rename_dismiss) {
                DevicesRenameActivity.this.closeRenameView();
            }
        }
    };
    ImageView dismissView;
    PhoneType phoneType;
    Realm realm;
    RecyclerView recyclerView;
    private ConstraintLayout renameLayout;
    ImageView saveView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_devices_rename);
        initView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        this.phoneType = (PhoneType) defaultInstance.where(PhoneType.class).findFirst();
        ImageView imageView = (ImageView) findViewById(R.id.devices_rename_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.bleDeviceList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.device_rename_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        BleDeviceRenameAdapter bleDeviceRenameAdapter = new BleDeviceRenameAdapter(this, this.bleDeviceList);
        this.bleDeviceRenameAdapter = bleDeviceRenameAdapter;
        this.recyclerView.setAdapter(bleDeviceRenameAdapter);
        this.bleDeviceRenameAdapter.setOnItemClickListener(new BleDeviceRenameAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.DevicesRenameActivity.1
            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceRenameAdapter.OnItemClickListener
            public void onOffClick(View view, int i, boolean z) {
                GlobalBluetooth.getInstance().singleOnOff(((BleDevice) DevicesRenameActivity.this.bleDeviceList.get(i)).getAddr(), z);
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.BleDeviceRenameAdapter.OnItemClickListener
            public void onRenameClick(View view, int i) {
                DevicesRenameActivity.this.handleDeviceRename(i);
            }
        });
        RealmResults realmResultsSort = this.realm.where(BleDevice.class).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.bleDeviceList.addAll(realmResultsSort);
            this.bleDeviceRenameAdapter.notifyDataSetChanged();
        }
        this.renameLayout = (ConstraintLayout) findViewById(R.id.device_rename_editView);
        this.deviceNameText = (TextView) findViewById(R.id.device_rename_name);
        this.deviceNameEdit = (EditText) findViewById(R.id.device_rename_edit);
        ImageView imageView2 = (ImageView) findViewById(R.id.device_rename_delete);
        this.deviceNameDelete = imageView2;
        imageView2.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.DevicesRenameActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (DevicesRenameActivity.this.delete_deviceName_tag) {
                        DevicesRenameActivity.this.deviceNameDelete.setSelected(true);
                        DevicesRenameActivity.this.deviceNameDelete.setEnabled(true);
                        DevicesRenameActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(DevicesRenameActivity.this.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                DevicesRenameActivity.this.deviceNameDelete.setSelected(false);
                DevicesRenameActivity.this.deviceNameDelete.setEnabled(false);
                DevicesRenameActivity.this.delete_deviceName_tag = true;
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
        BleDevice bleDevice = this.bleDeviceList.get(i);
        this.currentBleDevice = bleDevice;
        this.deviceNameText.setText(bleDevice.getName());
        this.deviceNameEdit.setText(this.currentBleDevice.getName());
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
        this.currentBleDevice = null;
    }
}

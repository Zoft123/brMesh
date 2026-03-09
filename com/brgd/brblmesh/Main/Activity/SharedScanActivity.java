package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.ShareBleDevice;
import com.brgd.brblmesh.GeneralClass.AESOperator;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import com.king.zxing.util.CodeUtils;
import io.realm.Realm;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class SharedScanActivity extends FatherActivity {
    ImageView backView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.SharedScanActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.share_scan_back) {
                SharedScanActivity.this.finish();
            }
        }
    };
    Realm realm;
    ImageView shareView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_shared_scan);
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
        ImageView imageView = (ImageView) findViewById(R.id.share_scan_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.shareView = (ImageView) findViewById(R.id.share_scan_content);
        getQrCode();
    }

    private void getQrCode() {
        String str;
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(GlobalVariable.toShare);
        JSONArray jSONArray = new JSONArray();
        int i = 3;
        int i2 = 43049;
        if (Tool.isToRadar()) {
            Iterator it = ((ArrayList) Objects.requireNonNull(stringArrayListExtra)).iterator();
            while (it.hasNext()) {
                RadarDevice radarDevice = (RadarDevice) this.realm.where(RadarDevice.class).equalTo(GlobalVariable.did, (String) it.next()).findFirst();
                if (radarDevice != null) {
                    ShareBleDevice shareBleDevice = new ShareBleDevice();
                    shareBleDevice.d = radarDevice.getDid();
                    shareBleDevice.a = radarDevice.getAddr();
                    if (radarDevice.getType() == 43050) {
                        shareBleDevice.t = 0;
                    } else if (radarDevice.getType() == 43169) {
                        shareBleDevice.t = 1;
                    } else if (radarDevice.getType() == 43168) {
                        shareBleDevice.t = 2;
                    } else if (radarDevice.getType() == 43051) {
                        shareBleDevice.t = 3;
                    } else if (radarDevice.getType() == i2) {
                        shareBleDevice.t = 4;
                    } else if (radarDevice.getType() == 44601) {
                        shareBleDevice.t = 5;
                    }
                    shareBleDevice.n = radarDevice.getName();
                    shareBleDevice.v = radarDevice.getVersion();
                    jSONArray.put(JSON.toJSON(shareBleDevice));
                }
                i2 = 43049;
            }
            str = (String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, "");
        } else {
            Iterator it2 = ((ArrayList) Objects.requireNonNull(stringArrayListExtra)).iterator();
            while (it2.hasNext()) {
                BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.did, (String) it2.next()).findFirst();
                if (bleDevice != null) {
                    ShareBleDevice shareBleDevice2 = new ShareBleDevice();
                    shareBleDevice2.d = bleDevice.getDid();
                    shareBleDevice2.a = bleDevice.getAddr();
                    if (bleDevice.getType() == 43050) {
                        shareBleDevice2.t = 0;
                    } else if (bleDevice.getType() == 43169) {
                        shareBleDevice2.t = 1;
                    } else if (bleDevice.getType() == 43168) {
                        shareBleDevice2.t = 2;
                    } else if (bleDevice.getType() == 43051) {
                        shareBleDevice2.t = i;
                    } else {
                        if (bleDevice.getType() == 43049) {
                            shareBleDevice2.t = 4;
                        }
                        shareBleDevice2.n = bleDevice.getName();
                        shareBleDevice2.v = bleDevice.getVersion();
                        jSONArray.put(JSON.toJSON(shareBleDevice2));
                    }
                    shareBleDevice2.n = bleDevice.getName();
                    shareBleDevice2.v = bleDevice.getVersion();
                    jSONArray.put(JSON.toJSON(shareBleDevice2));
                }
                i = 3;
            }
            str = (String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.ctrlKey, "");
        }
        try {
            this.shareView.setImageBitmap(CodeUtils.createQRCode(AESOperator.getInstance().encrypt(jSONArray.toString() + ";" + str), 400));
        } catch (Exception e) {
            Log.d("printStackTrace", "printStackTrace" + e);
        }
    }
}

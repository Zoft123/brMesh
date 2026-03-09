package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class ProductTypeActivity extends FatherActivity {
    private TextView currentTextView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.ProductTypeActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            String lowerCase;
            Intent intent;
            int id = view.getId();
            if (id == R.id.product_type_remember) {
                if (ProductTypeActivity.this.rememberBtn.isSelected()) {
                    ProductTypeActivity.this.rememberBtn.setSelected(false);
                    ProductTypeActivity.this.rememberTextView.setTextColor(ContextCompat.getColor(ProductTypeActivity.this.getApplicationContext(), R.color.colorConfirmGray));
                    SharePreferenceUtils.remove(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.RECORD);
                    return;
                } else {
                    ProductTypeActivity.this.rememberBtn.setSelected(true);
                    ProductTypeActivity.this.rememberTextView.setTextColor(ContextCompat.getColor(ProductTypeActivity.this.getApplicationContext(), R.color.colorSelect));
                    SharePreferenceUtils.put(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.RECORD, GlobalVariable.RECORD);
                    return;
                }
            }
            if (id == R.id.product_type_illText) {
                if (ProductTypeActivity.this.currentTextView == ProductTypeActivity.this.illTextView) {
                    ProductTypeActivity.this.currentTextView.setSelected(false);
                    ProductTypeActivity.this.currentTextView = null;
                    return;
                }
                if (ProductTypeActivity.this.currentTextView != null) {
                    ProductTypeActivity.this.currentTextView.setSelected(false);
                }
                ProductTypeActivity productTypeActivity = ProductTypeActivity.this;
                productTypeActivity.currentTextView = productTypeActivity.illTextView;
                ProductTypeActivity.this.currentTextView.setSelected(true);
                return;
            }
            if (id == R.id.product_type_senseText) {
                if (ProductTypeActivity.this.currentTextView == ProductTypeActivity.this.senseTextView) {
                    ProductTypeActivity.this.currentTextView.setSelected(false);
                    ProductTypeActivity.this.currentTextView = null;
                    return;
                }
                if (ProductTypeActivity.this.currentTextView != null) {
                    ProductTypeActivity.this.currentTextView.setSelected(false);
                }
                ProductTypeActivity productTypeActivity2 = ProductTypeActivity.this;
                productTypeActivity2.currentTextView = productTypeActivity2.senseTextView;
                ProductTypeActivity.this.currentTextView.setSelected(true);
                return;
            }
            if (id == R.id.product_type_enter) {
                if (ProductTypeActivity.this.currentTextView == null) {
                    GlobalToast.showText(ProductTypeActivity.this.getApplicationContext(), R.string.SelectType, 0);
                    return;
                }
                if (ProductTypeActivity.this.currentTextView == ProductTypeActivity.this.illTextView) {
                    SharePreferenceUtils.put(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.CHOICE_TYPE, GlobalVariable.ILLUMINATION);
                    lowerCase = (String) SharePreferenceUtils.get(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.ctrlKey, "");
                    if (lowerCase == null || lowerCase.equals("") || lowerCase.length() < 8) {
                        lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
                        SharePreferenceUtils.put(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.ctrlKey, lowerCase);
                    }
                    intent = new Intent(ProductTypeActivity.this, (Class<?>) MainActivity.class);
                } else {
                    SharePreferenceUtils.put(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.CHOICE_TYPE, GlobalVariable.RADAR);
                    lowerCase = (String) SharePreferenceUtils.get(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, "");
                    if (lowerCase == null || lowerCase.equals("") || lowerCase.length() < 8) {
                        lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
                        SharePreferenceUtils.put(ProductTypeActivity.this.getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, lowerCase);
                    }
                    intent = new Intent(ProductTypeActivity.this, (Class<?>) RadarMainActivity.class);
                }
                BLSBleLight.setBLEControlKey(lowerCase);
                ProductTypeActivity.this.startActivity(intent);
                ProductTypeActivity.this.finish();
            }
        }
    };
    ImageView enterView;
    private TextView illTextView;
    TextView lastIllTextView;
    TextView lastSenseTextView;
    private ImageView rememberBtn;
    private TextView rememberTextView;
    private TextView senseTextView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_product_type);
        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.product_type_illText);
        this.illTextView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        this.lastIllTextView = (TextView) findViewById(R.id.product_type_lastIllText);
        TextView textView2 = (TextView) findViewById(R.id.product_type_senseText);
        this.senseTextView = textView2;
        textView2.setOnClickListener(this.disDoubleClickListener);
        this.lastSenseTextView = (TextView) findViewById(R.id.product_type_lastSenseText);
        this.rememberTextView = (TextView) findViewById(R.id.product_type_remember);
        this.rememberBtn = (ImageView) findViewById(R.id.product_type_remember_bt);
        this.rememberTextView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView = (ImageView) findViewById(R.id.product_type_enter);
        this.enterView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        String str = (String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.CHOICE_TYPE, "");
        if (str == null || str.equals("")) {
            return;
        }
        if (str.equals(GlobalVariable.ILLUMINATION)) {
            this.lastIllTextView.setVisibility(0);
        } else if (str.equals(GlobalVariable.RADAR)) {
            this.lastSenseTextView.setVisibility(0);
        }
    }
}

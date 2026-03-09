package com.brgd.brblmesh.GeneralActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import cn.com.broadlink.blelight.BLSBleLight;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.Main.Activity.MainActivity;
import com.brgd.brblmesh.Main.Activity.ProductTypeActivity;
import com.brgd.brblmesh.Main.Activity.RadarMainActivity;
import com.brgd.brblmesh.R;
import io.realm.Realm;

/* JADX INFO: loaded from: classes.dex */
public class LaunchActivity extends AppCompatActivity {
    ImageView agreeView;
    TextView confirmTextView;
    ImageView confirmView;
    TextView contentTextView;
    ImageView denyView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralActivity.LaunchActivity.2
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.launch_confirm_text) {
                if (LaunchActivity.this.confirmView.isSelected()) {
                    LaunchActivity.this.confirmView.setSelected(false);
                    LaunchActivity.this.agreeView.setSelected(false);
                    return;
                } else {
                    LaunchActivity.this.confirmView.setSelected(true);
                    LaunchActivity.this.agreeView.setSelected(true);
                    return;
                }
            }
            if (id == R.id.launch_private_agree) {
                if (!LaunchActivity.this.agreeView.isSelected()) {
                    GlobalToast.showText(LaunchActivity.this.getApplicationContext(), R.string.confirmLight, 0);
                    return;
                } else {
                    LaunchActivity.this.handlePrivateClick();
                    return;
                }
            }
            if (id == R.id.launch_private_deny) {
                LaunchActivity.this.handlePrivateClick();
            }
        }
    };
    ConstraintLayout privateConstraintLayout;
    private Realm realm;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        enableEdgeToEdge();
        super.onCreate(bundle);
        setContentView(R.layout.activity_launch);
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        if (!TextUtils.isEmpty((String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.redPrivate, ""))) {
            init();
        } else {
            initView();
        }
    }

    private void enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsControllerCompat.show(WindowInsetsCompat.Type.statusBars());
        windowInsetsControllerCompat.setSystemBarsBehavior(2);
        windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
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
        this.realm.close();
    }

    private void initView() {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.launch_private_View);
        this.privateConstraintLayout = constraintLayout;
        constraintLayout.setVisibility(0);
        this.contentTextView = (TextView) findViewById(R.id.launch_private_content);
        ImageView imageView = (ImageView) findViewById(R.id.launch_private_deny);
        this.denyView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.launch_private_agree);
        this.agreeView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.confirmView = (ImageView) findViewById(R.id.launch_confirm_bt);
        TextView textView = (TextView) findViewById(R.id.launch_confirm_text);
        this.confirmTextView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getString(R.string.privateContent1));
        SpannableString spannableString = new SpannableString(getString(R.string.privateContent2));
        spannableString.setSpan(new ClickableSpan() { // from class: com.brgd.brblmesh.GeneralActivity.LaunchActivity.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                LaunchActivity.this.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("http://www.gdszbrgd.com/news/html/?435.html")), "Browse"));
            }
        }, 0, spannableString.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(CustomColor.SELECT), 0, spannableString.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString);
        spannableStringBuilder.append((CharSequence) getString(R.string.privateContent3));
        this.contentTextView.setMovementMethod(LinkMovementMethod.getInstance());
        this.contentTextView.setText(spannableStringBuilder);
        this.contentTextView.setHighlightColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAlpha));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePrivateClick() {
        this.privateConstraintLayout.setVisibility(8);
        SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.redPrivate, GlobalVariable.redPrivate);
        init();
    }

    private void init() {
        String lowerCase;
        Intent intent;
        initPhoneType();
        initGroup();
        if (Tool.isToChoiceType()) {
            startActivity(new Intent(this, (Class<?>) ProductTypeActivity.class));
            finish();
            return;
        }
        if (Tool.isToRadar()) {
            lowerCase = (String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, "");
            if (lowerCase == null || lowerCase.equals("") || lowerCase.length() < 8) {
                lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
                SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.RADAR_CTRLKEY_TAG, lowerCase);
            }
            intent = new Intent(this, (Class<?>) RadarMainActivity.class);
        } else {
            lowerCase = (String) SharePreferenceUtils.get(getApplicationContext(), GlobalVariable.ctrlKey, "");
            if (lowerCase == null || lowerCase.equals("") || lowerCase.length() < 8) {
                lowerCase = Tool.stringToHexString(Tool.getRandomString(8)).substring(0, 8).toLowerCase();
                SharePreferenceUtils.put(getApplicationContext(), GlobalVariable.ctrlKey, lowerCase);
            }
            intent = new Intent(this, (Class<?>) MainActivity.class);
        }
        BLSBleLight.setBLEControlKey(lowerCase);
        startActivity(intent);
        finish();
    }

    private void initPhoneType() {
        if (((PhoneType) this.realm.where(PhoneType.class).findFirst()) == null) {
            this.realm.beginTransaction();
            ((PhoneType) this.realm.createObject(PhoneType.class)).setPhoneType(3);
            this.realm.commitTransaction();
        }
        if (((RadarPhoneType) this.realm.where(RadarPhoneType.class).findFirst()) == null) {
            this.realm.beginTransaction();
            ((RadarPhoneType) this.realm.createObject(RadarPhoneType.class)).setPhoneType(3);
            this.realm.commitTransaction();
        }
    }

    private void initGroup() {
        if (((Groups) this.realm.where(Groups.class).equalTo(GlobalVariable.groupId, (Integer) 0).findFirst()) == null) {
            this.realm.beginTransaction();
            Groups groups = (Groups) this.realm.createObject(Groups.class);
            groups.setGroupId(0);
            groups.setGroupName(getString(R.string.GroupAll));
            groups.setIndex(0);
            this.realm.commitTransaction();
        }
        if (((RadarGroup) this.realm.where(RadarGroup.class).equalTo("fixedId", (Integer) 0).findFirst()) == null) {
            this.realm.beginTransaction();
            RadarGroup radarGroup = (RadarGroup) this.realm.createObject(RadarGroup.class);
            radarGroup.setFixedId(0);
            radarGroup.setFixedName(getString(R.string.GroupAll));
            radarGroup.setIndex(0);
            this.realm.commitTransaction();
        }
    }
}

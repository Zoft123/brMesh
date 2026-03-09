package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class AlexaGuideActivity extends FatherActivity {
    ImageView confirmBtn;
    TextView confirmView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AlexaGuideActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.alexa_guide_next) {
                if (AlexaGuideActivity.this.confirmBtn.isSelected()) {
                    SharePreferenceUtils.put(AlexaGuideActivity.this, GlobalVariable.showAlexaGuide, true);
                }
                AlexaGuideActivity.this.startActivity(new Intent(AlexaGuideActivity.this, (Class<?>) AlexaActivity.class));
                AlexaGuideActivity.this.finish();
                return;
            }
            if (id == R.id.alexa_guide_confirm) {
                if (AlexaGuideActivity.this.confirmBtn.isSelected()) {
                    AlexaGuideActivity.this.confirmBtn.setSelected(false);
                    AlexaGuideActivity.this.confirmView.setTextColor(AlexaGuideActivity.this.getColor(R.color.colorConfirmGray));
                } else {
                    AlexaGuideActivity.this.confirmBtn.setSelected(true);
                    AlexaGuideActivity.this.confirmView.setTextColor(AlexaGuideActivity.this.getColor(R.color.colorSelect));
                }
            }
        }
    };
    TextView nextView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alexa_guide);
        TextView textView = (TextView) findViewById(R.id.alexa_guide_next);
        this.nextView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        this.confirmView = (TextView) findViewById(R.id.alexa_guide_confirm);
        this.confirmBtn = (ImageView) findViewById(R.id.alexa_guide_confirm_bt);
        this.confirmView.setOnClickListener(this.disDoubleClickListener);
    }
}

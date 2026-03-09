package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class Instructions9Activity extends FatherActivity {
    ImageView backView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.Instructions9Activity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.instruct9_back) {
                Instructions9Activity.this.finish();
            }
        }
    };

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_instructions9);
        ImageView imageView = (ImageView) findViewById(R.id.instruct9_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
    }
}

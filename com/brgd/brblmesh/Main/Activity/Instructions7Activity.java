package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class Instructions7Activity extends FatherActivity {
    ImageView backView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.Instructions7Activity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.instruct7_back) {
                Instructions7Activity.this.finish();
            } else if (id == R.id.instruct7_video) {
                Instructions7Activity.this.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/oBqWAzP2oOE")), "Browse"));
            }
        }
    };
    ImageView video;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_instructions7);
        ImageView imageView = (ImageView) findViewById(R.id.instruct7_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.instruct7_video);
        this.video = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
    }
}

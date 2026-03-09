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
public class Instructions4Activity extends FatherActivity {
    ImageView backView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.Instructions4Activity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.instruct4_back) {
                Instructions4Activity.this.finish();
            } else if (id == R.id.instruct4_video) {
                Instructions4Activity.this.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/ZoOqoLZHKTk")), "Browse"));
            }
        }
    };
    ImageView video;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_instructions4);
        ImageView imageView = (ImageView) findViewById(R.id.instruct4_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.instruct4_video);
        this.video = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
    }
}

package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.MoreAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.MoreModel;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class InstructionsActivity extends FatherActivity {
    MoreAdapter adapter;
    ImageView backView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.InstructionsActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.instruction_back) {
                InstructionsActivity.this.finish();
            }
        }
    };
    List<MoreModel> list;
    RecyclerView recyclerView;
    TextView versionCode;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_instructions);
        initView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        ImageView imageView = (ImageView) findViewById(R.id.instruction_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.versionCode = (TextView) findViewById(R.id.instruction_versionCode);
        this.versionCode.setText(String.valueOf(Tool.getVersionCode(this)));
        this.recyclerView = (RecyclerView) findViewById(R.id.instruction_list_recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.list = new ArrayList();
        MoreAdapter moreAdapter = new MoreAdapter(this, this.list);
        this.adapter = moreAdapter;
        this.recyclerView.setAdapter(moreAdapter);
        this.adapter.setOnMoreClickListener(new MoreAdapter.OnMoreClickListener() { // from class: com.brgd.brblmesh.Main.Activity.InstructionsActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.MoreAdapter.OnMoreClickListener
            public final void onMoreClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        refreshList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        Intent intent;
        if (i == 0) {
            startActivity(new Intent(this, (Class<?>) Instructions4Activity.class));
            return;
        }
        if (i == 1) {
            if (Tool.isToRadar()) {
                intent = new Intent(this, (Class<?>) Instructions6Activity.class);
            } else {
                intent = new Intent(this, (Class<?>) Instructions5Activity.class);
            }
            startActivity(intent);
            return;
        }
        if (i == 2) {
            startActivity(new Intent(this, (Class<?>) Instructions6Activity.class));
            return;
        }
        if (i == 3) {
            startActivity(new Intent(this, (Class<?>) Instructions7Activity.class));
        } else if (i == 4) {
            startActivity(new Intent(this, (Class<?>) AboutBindActivity.class));
        } else if (i == 5) {
            startActivity(new Intent(this, (Class<?>) Instructions10Activity.class));
        }
    }

    public void refreshList() {
        int[] iArr;
        int[] iArr2;
        if (Tool.isToRadar()) {
            iArr2 = new int[]{R.drawable.more_instructions, R.drawable.more_instructions};
            iArr = new int[]{R.string.AboutTimer, R.string.ShareScan};
        } else {
            int[] iArr3 = {R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_instructions};
            iArr = new int[]{R.string.AboutTimer, R.string.AboutScene, R.string.ShareScan, R.string.musicTheme, R.string.aboutBinding, R.string.AboutBackups};
            iArr2 = iArr3;
        }
        this.list.clear();
        for (int i = 0; i < iArr2.length; i++) {
            MoreModel moreModel = new MoreModel();
            moreModel.imgResourceId = iArr2[i];
            moreModel.titleNameId = iArr[i];
            this.list.add(moreModel);
        }
        this.adapter.notifyDataSetChanged();
    }
}

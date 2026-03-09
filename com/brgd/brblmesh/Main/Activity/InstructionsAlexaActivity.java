package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.MoreAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.MoreModel;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class InstructionsAlexaActivity extends FatherActivity {
    MoreAdapter adapter;
    ImageView backView;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.InstructionsAlexaActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.instruction_alexa_back) {
                InstructionsAlexaActivity.this.finish();
            }
        }
    };
    List<MoreModel> list;
    RecyclerView recyclerView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_instructions_alexa);
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
        ImageView imageView = (ImageView) findViewById(R.id.instruction_alexa_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.recyclerView = (RecyclerView) findViewById(R.id.instruction_alexa_list_recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.list = new ArrayList();
        MoreAdapter moreAdapter = new MoreAdapter(this, this.list);
        this.adapter = moreAdapter;
        this.recyclerView.setAdapter(moreAdapter);
        this.adapter.setOnMoreClickListener(new MoreAdapter.OnMoreClickListener() { // from class: com.brgd.brblmesh.Main.Activity.InstructionsAlexaActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.MoreAdapter.OnMoreClickListener
            public final void onMoreClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        refreshList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        if (i == 0) {
            startActivity(new Intent(this, (Class<?>) AlexaCmdActivity.class));
            return;
        }
        if (i == 1) {
            startActivity(new Intent(this, (Class<?>) Instructions9Activity.class));
        } else if (i == 2) {
            startActivity(new Intent(this, (Class<?>) Instructions8Activity.class));
        } else if (i == 3) {
            startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/KVlRBSFhSrg")), "Browse"));
        }
    }

    public void refreshList() {
        int[] iArr = {R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_instructions, R.drawable.more_instructions};
        int[] iArr2 = {R.string.alexaCommand, R.string.AlexaGuideTitle2, R.string.AlexaUseExp, R.string.aboutActivateAlexa};
        this.list.clear();
        for (int i = 0; i < 4; i++) {
            MoreModel moreModel = new MoreModel();
            moreModel.imgResourceId = iArr[i];
            moreModel.titleNameId = iArr2[i];
            this.list.add(moreModel);
        }
        this.adapter.notifyDataSetChanged();
    }
}

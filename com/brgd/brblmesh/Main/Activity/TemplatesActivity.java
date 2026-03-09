package com.brgd.brblmesh.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.IconAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.MoreModel;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TemplatesActivity extends FatherActivity {
    ImageView backView;
    private FixedGroup currentFixGroup;
    private MoreModel currentModel;
    private Scene currentScene;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.TemplatesActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.templates_back) {
                TemplatesActivity.this.finish();
                return;
            }
            if (id == R.id.templates_save) {
                if (TemplatesActivity.this.currentModel == null) {
                    GlobalToast.showText(TemplatesActivity.this.getApplicationContext(), R.string.selectIcon, 0);
                    return;
                }
                if (TemplatesActivity.this.currentScene != null) {
                    if (TemplatesActivity.this.currentScene.getSceneFileName() != null) {
                        Tool.deleteFile(TemplatesActivity.this, TemplatesActivity.this.currentScene.getSceneFileName() + GlobalVariable.sceneImgFileName);
                    }
                    TemplatesActivity.this.realm.beginTransaction();
                    TemplatesActivity.this.currentScene.setIconIndex(TemplatesActivity.this.currentModel.titleNameId);
                    TemplatesActivity.this.currentScene.setSceneFileName(null);
                    TemplatesActivity.this.realm.commitTransaction();
                } else if (TemplatesActivity.this.currentFixGroup != null) {
                    if (TemplatesActivity.this.currentFixGroup.getFileName() != null) {
                        Tool.deleteFile(TemplatesActivity.this, TemplatesActivity.this.currentFixGroup.getFileName() + GlobalVariable.sceneImgFileName);
                    }
                    TemplatesActivity.this.realm.beginTransaction();
                    TemplatesActivity.this.currentFixGroup.setIconIndex(TemplatesActivity.this.currentModel.titleNameId);
                    TemplatesActivity.this.currentFixGroup.setFileName(null);
                    TemplatesActivity.this.realm.commitTransaction();
                }
                TemplatesActivity.this.finish();
            }
        }
    };
    private IconAdapter iconAdapter;
    RecyclerView iconRecyclerView;
    private List<MoreModel> list;
    private Realm realm;
    TextView saveView;

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_templates);
        initView();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.templates_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        TextView textView = (TextView) findViewById(R.id.templates_save);
        this.saveView = textView;
        textView.setOnClickListener(this.disDoubleClickListener);
        this.iconRecyclerView = (RecyclerView) findViewById(R.id.templates_list);
        this.list = new ArrayList();
        this.iconAdapter = new IconAdapter(this, this.list);
        this.iconRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        this.iconRecyclerView.setAdapter(this.iconAdapter);
        this.iconAdapter.setOnIconClickListener(new IconAdapter.OnIconClickListener() { // from class: com.brgd.brblmesh.Main.Activity.TemplatesActivity$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralAdapter.IconAdapter.OnIconClickListener
            public final void onIconClick(View view, int i) {
                this.f$0.lambda$initView$0(view, i);
            }
        });
        int i = 0;
        while (i < 30) {
            MoreModel moreModel = new MoreModel();
            i++;
            moreModel.titleNameId = i;
            moreModel.isSelect = false;
            this.list.add(moreModel);
        }
        this.iconAdapter.notifyDataSetChanged();
        handleData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view, int i) {
        MoreModel moreModel = this.currentModel;
        if (moreModel != null) {
            moreModel.isSelect = false;
        }
        MoreModel moreModel2 = this.list.get(i);
        this.currentModel = moreModel2;
        moreModel2.isSelect = true;
        this.iconAdapter.notifyDataSetChanged();
    }

    private void handleData() {
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("fixedId", 0);
        if (intExtra != 0) {
            this.currentFixGroup = (FixedGroup) this.realm.where(FixedGroup.class).equalTo("fixedId", Integer.valueOf(intExtra)).findFirst();
        } else {
            this.currentScene = (Scene) this.realm.where(Scene.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(intent.getIntExtra(GlobalVariable.sceneNumber, 0))).findFirst();
        }
    }
}

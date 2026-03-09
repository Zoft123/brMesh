package com.king.zxing;

import com.google.zxing.Result;
import com.king.camera.scan.BaseCameraScanActivity;
import com.king.camera.scan.analyze.Analyzer;
import com.king.view.viewfinderview.ViewfinderView;
import com.king.zxing.analyze.MultiFormatAnalyzer;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BarcodeCameraScanActivity extends BaseCameraScanActivity<Result> {
    protected ViewfinderView viewfinderView;

    @Override // com.king.camera.scan.BaseCameraScanActivity
    public void initUI() {
        int viewfinderViewId = getViewfinderViewId();
        if (viewfinderViewId != -1 && viewfinderViewId != 0) {
            this.viewfinderView = (ViewfinderView) findViewById(viewfinderViewId);
        }
        super.initUI();
    }

    @Override // com.king.camera.scan.BaseCameraScanActivity
    public Analyzer<Result> createAnalyzer() {
        return new MultiFormatAnalyzer();
    }

    @Override // com.king.camera.scan.BaseCameraScanActivity
    public int getLayoutId() {
        return R.layout.zxl_camera_scan;
    }

    public int getViewfinderViewId() {
        return R.id.viewfinderView;
    }
}

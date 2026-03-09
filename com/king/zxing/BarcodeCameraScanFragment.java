package com.king.zxing;

import com.google.zxing.Result;
import com.king.camera.scan.BaseCameraScanFragment;
import com.king.camera.scan.analyze.Analyzer;
import com.king.view.viewfinderview.ViewfinderView;
import com.king.zxing.analyze.MultiFormatAnalyzer;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BarcodeCameraScanFragment extends BaseCameraScanFragment<Result> {
    protected ViewfinderView viewfinderView;

    @Override // com.king.camera.scan.BaseCameraScanFragment
    public void initUI() {
        int viewfinderViewId = getViewfinderViewId();
        if (viewfinderViewId != -1 && viewfinderViewId != 0) {
            this.viewfinderView = (ViewfinderView) getRootView().findViewById(viewfinderViewId);
        }
        super.initUI();
    }

    @Override // com.king.camera.scan.BaseCameraScanFragment
    public Analyzer<Result> createAnalyzer() {
        return new MultiFormatAnalyzer();
    }

    @Override // com.king.camera.scan.BaseCameraScanFragment
    public int getLayoutId() {
        return R.layout.zxl_camera_scan;
    }

    public int getViewfinderViewId() {
        return R.id.viewfinderView;
    }
}

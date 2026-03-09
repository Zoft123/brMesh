package com.brgd.brblmesh.GeneralClass;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class CustomDialog extends Dialog {
    private Button cancelBt;
    private int cancelResource;
    private int messageResource;
    private TextView messageText;
    private onCancelClickListener onCancelClickListener;
    private onSureClickListener onSureClickListener;
    private Button sureBt;
    private int sureResource;
    private int titleResource;
    private TextView titleText;

    public interface onCancelClickListener {
        void onCancelClick();
    }

    public interface onSureClickListener {
        void onSureClick();
    }

    public CustomDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.customdialog);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        this.sureBt.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralClass.CustomDialog.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (CustomDialog.this.onSureClickListener != null) {
                    CustomDialog.this.onSureClickListener.onSureClick();
                }
            }
        });
        this.cancelBt.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralClass.CustomDialog.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (CustomDialog.this.onCancelClickListener != null) {
                    CustomDialog.this.onCancelClickListener.onCancelClick();
                }
            }
        });
    }

    private void initData() {
        this.titleText.setText(this.titleResource);
        this.messageText.setText(this.messageResource);
        this.sureBt.setText(this.sureResource);
        this.cancelBt.setText(this.cancelResource);
    }

    private void initView() {
        this.sureBt = (Button) findViewById(R.id.sureBt);
        this.cancelBt = (Button) findViewById(R.id.cancelBt);
        this.titleText = (TextView) findViewById(R.id.title);
        this.messageText = (TextView) findViewById(R.id.message);
    }

    @Override // android.app.Dialog
    public void setTitle(int i) {
        this.titleResource = i;
    }

    public void setMessage(int i) {
        this.messageResource = i;
    }

    public void setOnCancelClickListener(int i, onCancelClickListener oncancelclicklistener) {
        this.cancelResource = i;
        this.onCancelClickListener = oncancelclicklistener;
    }

    public void setOnSureClickListener(int i, onSureClickListener onsureclicklistener) {
        this.sureResource = i;
        this.onSureClickListener = onsureclicklistener;
    }
}

package cn.com.broadlink.blelight.interfaces;

/* JADX INFO: loaded from: classes.dex */
public interface OnGatewayConfigCallback {
    void onConfigResult(int i, String str, String str2);

    void onStopped();

    void onTimeout();
}

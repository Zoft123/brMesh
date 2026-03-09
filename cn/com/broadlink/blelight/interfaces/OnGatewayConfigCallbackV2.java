package cn.com.broadlink.blelight.interfaces;

/* JADX INFO: loaded from: classes.dex */
public interface OnGatewayConfigCallbackV2 {
    void onStateResult(int i, int i2, int i3);

    void onStopped();

    void onTimeout();

    void onTokenReturn(int i, String str, String str2, boolean z);
}

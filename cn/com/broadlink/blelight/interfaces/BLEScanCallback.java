package cn.com.broadlink.blelight.interfaces;

import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;

/* JADX INFO: loaded from: classes.dex */
public interface BLEScanCallback {
    void onDevCallback(BLEDeviceInfo bLEDeviceInfo);

    void onDevCtrlFullFrame(int i, byte[] bArr);

    void onDevSign(int i, String str);

    void onHeartBeat(int i, int i2, String str);

    void onTimerListCallback(BLETimerAllInfo bLETimerAllInfo);
}

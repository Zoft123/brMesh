package cn.com.broadlink.blelight.interfaces;

import cn.com.broadlink.blelight.bean.BLEAppTimeInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface OnDevRtcTimerCallback {
    void onTimerCallback(int i, int i2, List<Integer> list, int i3, int i4, BLEAppTimeInfo bLEAppTimeInfo);

    void onTimerCmdCallback(int i, int i2, int i3, int i4, byte[] bArr);
}

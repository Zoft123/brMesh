package cn.com.broadlink.blelight.interfaces;

import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.bean.BLESceneInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IBLSBleLight {
    boolean addDevice(BLEDeviceInfo bLEDeviceInfo);

    int bathControlWithDeviceList(List<BLEDeviceInfo> list, String str);

    int controlWithDevice(int i, String str);

    String generateLightCommandWithState(boolean z, int i, int i2, int i3, int i4);

    String generateLightQuickControlCommand();

    int generateOneDeviceAddr();

    int groupControlWithType(int i, int i2, String str);

    boolean modifyDeviceInfo(BLEDeviceInfo bLEDeviceInfo);

    int musicColorControlWithGroupId(int i, int i2, String str);

    int musicControlWithGroupId(int i, String str);

    BLEDeviceInfo queryDeviceInfoWithDid(String str);

    List<BLEDeviceInfo> queryDevicesWithGroupId(int i);

    void removeAllDevice();

    boolean removeDevice(String str);

    int sceneControlWithDevice(int i, BLESceneInfo bLESceneInfo);

    int sceneControlWithGroupId(int i, BLESceneInfo bLESceneInfo);

    int sendDiscResWithDevice(BLEDeviceInfo bLEDeviceInfo);

    int sendStartDiscoverPackage();

    int setBLEControlKey(String str);

    int setGroupAddrWithDevice(BLEDeviceInfo bLEDeviceInfo, int i);

    boolean startBleReceiveService();

    boolean stopBleReceiveService();

    int timerQueryWithDevice(int i, BLETimeLcInfo bLETimeLcInfo);

    int timerSettingWithDevice(int i, BLETimerAllInfo bLETimerAllInfo);
}

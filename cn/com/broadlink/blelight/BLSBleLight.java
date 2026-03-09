package cn.com.broadlink.blelight;

import android.app.Application;
import cn.com.broadlink.blelight.bean.BLEAppTimeInfo;
import cn.com.broadlink.blelight.bean.BLEDelaySceneInfo;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.bean.BLEGatewayConfigInfo;
import cn.com.broadlink.blelight.bean.BLEModeRgbcwBean;
import cn.com.broadlink.blelight.bean.BLERadar24GAllInfo;
import cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo;
import cn.com.broadlink.blelight.bean.BLERoomSceneInfo;
import cn.com.broadlink.blelight.bean.BLESceneInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import cn.com.broadlink.blelight.helper.BLCloudCodeHelper;
import cn.com.broadlink.blelight.helper.BLEFastconHelper;
import cn.com.broadlink.blelight.helper.DevSignHelper;
import cn.com.broadlink.blelight.helper.GatewayRemoteCtrlHelper;
import cn.com.broadlink.blelight.interfaces.OnAlexaStateCallback;
import cn.com.broadlink.blelight.interfaces.OnDevColorTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback;
import cn.com.broadlink.blelight.interfaces.OnDevRtcTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevScanCallback;
import cn.com.broadlink.blelight.interfaces.OnDevStateCallback;
import cn.com.broadlink.blelight.interfaces.OnDevStateRssiCallback;
import cn.com.broadlink.blelight.interfaces.OnDevTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnGatewayConfigCallback;
import cn.com.broadlink.blelight.interfaces.OnGatewayConfigCallbackV2;
import cn.com.broadlink.blelight.interfaces.OnPassThroughCallback;
import cn.com.broadlink.blelight.interfaces.OnReceiveDevCtrlCallback;
import cn.com.broadlink.blelight.interfaces.OnReceiveFullFrameCallback;
import cn.com.broadlink.blelight.interfaces.OnSendFailCallback;
import cn.com.broadlink.blelight.interfaces.OnWorkTimeCallback;
import cn.com.broadlink.blelight.jni.BLEUtil;
import cn.com.broadlink.blelight.util.EAppUtils;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.ELogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public class BLSBleLight {
    public static void init(Application application) {
        ELogUtils.i("BLEBleLight", "init: 1.2.0.master.97b015a.20250916_1618");
        EAppUtils.init(application);
        BLEFastconHelper.getInstance();
    }

    public static void init(Application application, boolean z) {
        ELogUtils.i("BLEBleLight", "init: 1.2.0.master.97b015a.20250916_1618");
        EAppUtils.init(application);
        BLEFastconHelper.getInstance().setCheckPermBeforeSendCmd(z);
    }

    public static void setLogLevel(int i) {
        ELogUtils.setLogLevel(i);
    }

    public static void setServerInfo(String str, String str2) {
        GatewayRemoteCtrlHelper.getInstance().init(str, str2);
        DevSignHelper.getInstance().init(str, str2);
    }

    public static boolean isNeedRemoteCtrl() {
        return GatewayRemoteCtrlHelper.getInstance().isNeedRemoteCtrl();
    }

    public static void setNeedRemoteCtrl(boolean z) {
        GatewayRemoteCtrlHelper.getInstance().setNeedRemoteCtrl(z);
    }

    public static void setShowDialogWhenPermInvalid(boolean z) {
        BLEFastconHelper.getInstance().setShowPermDialog(z);
    }

    public static void setShowDialogWhenBlueToothNeedRestart(boolean z) {
        BLEFastconHelper.getInstance().setShowSendFailDialog(z);
    }

    public static boolean checkPermission() {
        return BLEFastconHelper.getInstance().checkPerm();
    }

    public static void setOnDevScanCallback(OnDevScanCallback onDevScanCallback) {
        BLEFastconHelper.getInstance().setOnDevScanCallback(onDevScanCallback);
    }

    public static void setOnAlexaStateCallback(OnAlexaStateCallback onAlexaStateCallback) {
        BLEFastconHelper.getInstance().setOnAlexaStateCallback(onAlexaStateCallback);
    }

    public static void setOnDevStateCallback(OnDevStateCallback onDevStateCallback) {
        BLEFastconHelper.getInstance().setOnDevStateCallback(onDevStateCallback);
    }

    public static void setOnDevStateRssiCallback(OnDevStateRssiCallback onDevStateRssiCallback) {
        BLEFastconHelper.getInstance().setOnDevStateRssiCallback(onDevStateRssiCallback);
    }

    public static void setOnWorkTimeCallback(OnWorkTimeCallback onWorkTimeCallback) {
        BLEFastconHelper.getInstance().setOnWorkTimeCallback(onWorkTimeCallback);
    }

    public static void setOnReceiveDevCtrlCallback(OnReceiveDevCtrlCallback onReceiveDevCtrlCallback) {
        BLEFastconHelper.getInstance().setOnReceiveDevCtrlCallback(onReceiveDevCtrlCallback);
    }

    public static void setOnReceiveFullFrameCallback(OnReceiveFullFrameCallback onReceiveFullFrameCallback) {
        BLEFastconHelper.getInstance().setOnReceiveFullFrameCallback(onReceiveFullFrameCallback);
    }

    public static void setOnPassThroughCallback(OnPassThroughCallback onPassThroughCallback) {
        BLEFastconHelper.getInstance().setOnPassThroughCallback(onPassThroughCallback);
    }

    public static void setOnTimerCallback(OnDevTimerCallback onDevTimerCallback) {
        BLEFastconHelper.getInstance().setOnTimerCallback(onDevTimerCallback);
    }

    public static void setOnHeartBeatCallback(OnDevHeartBeatCallback onDevHeartBeatCallback) {
        BLEFastconHelper.getInstance().setOnHeartBeatCallback(onDevHeartBeatCallback);
    }

    public static int setBLEControlKey(String str) {
        BLEFastconHelper.getInstance().setPhoneKey(EConvertUtils.hexStr2Bytes(str));
        return 0;
    }

    public static boolean startBleReceiveService() {
        return BLEFastconHelper.getInstance().startScanBLEDevices();
    }

    public static boolean stopBleReceiveService() {
        return BLEFastconHelper.getInstance().stopScanBLEDevices();
    }

    public static void stopBleReceiveServiceDelay() {
        BLEFastconHelper.getInstance().startCloseBLETimer();
    }

    public static int generateOneDeviceAddr() {
        return BLEFastconHelper.getInstance().devAppendAddr(false);
    }

    public static int generateOneDeviceAddr(boolean z) {
        return BLEFastconHelper.getInstance().devAppendAddr(z);
    }

    public static int acGatewayAppendAddr(int i, boolean z) {
        return BLEFastconHelper.getInstance().acGatewayAppendAddr(i, z);
    }

    public static boolean addDevice(BLEDeviceInfo bLEDeviceInfo) {
        return BLEFastconHelper.getInstance().addDevice(bLEDeviceInfo);
    }

    public static boolean updateOnlineState(String str, int i) {
        return BLEFastconHelper.getInstance().updateOnlineState(str, i);
    }

    public static void initDeviceList(List<BLEDeviceInfo> list) {
        BLEFastconHelper.getInstance().setDevList(list, true);
    }

    public static void initDeviceList(List<BLEDeviceInfo> list, boolean z) {
        BLEFastconHelper.getInstance().setDevList(list, z);
    }

    public static BLEDeviceInfo queryDeviceInfoWithDid(String str) {
        return BLEFastconHelper.getInstance().getDevById(str);
    }

    public static List<BLEDeviceInfo> queryDevicesWithGroupId(int i) {
        return BLEFastconHelper.getInstance().getDevByGroupId(i);
    }

    public static List<BLEDeviceInfo> queryDeviceAll() {
        return BLEFastconHelper.getInstance().getDevList();
    }

    public static boolean modifyDeviceInfo(BLEDeviceInfo bLEDeviceInfo) {
        return BLEFastconHelper.getInstance().updDevInfo(bLEDeviceInfo);
    }

    public static boolean removeDevice(String str) {
        return BLEFastconHelper.getInstance().delDevice(str);
    }

    public static void removeAllDevice() {
        BLEFastconHelper.getInstance().getDevList().clear();
    }

    public static boolean sendStartDiscoverPackage() {
        return BLEFastconHelper.getInstance().sendStartScan();
    }

    public static boolean sendDiscResWithDevice(BLEDeviceInfo bLEDeviceInfo) {
        return BLEFastconHelper.getInstance().sendDiscRes(bLEDeviceInfo);
    }

    public static boolean setGroupAddrWithDevice(int i, int i2) {
        return BLEFastconHelper.getInstance().controlSetGroupAddr(i, i2);
    }

    public static boolean setShortAddrWithDevice(String str, int i) {
        return BLEFastconHelper.getInstance().controlSetShortAddr(str, i);
    }

    public static boolean controlDelaySceneWithDevice(int i, BLEDelaySceneInfo bLEDelaySceneInfo) {
        return BLEFastconHelper.getInstance().controlDelayScene(i, bLEDelaySceneInfo);
    }

    public static boolean controlDelaySceneWithGroup(int i, BLEDelaySceneInfo bLEDelaySceneInfo) {
        return BLEFastconHelper.getInstance().controlGroupDelayScene(i, bLEDelaySceneInfo);
    }

    public static boolean controlWithDevice(int i, String str) {
        return BLEFastconHelper.getInstance().controlLightSingle(i, str, 0);
    }

    public static boolean controlWithDevice(int i, String str, int i2) {
        return BLEFastconHelper.getInstance().controlLightSingle(i, str, i2);
    }

    public static boolean controlClearRm(int i) {
        return BLEFastconHelper.getInstance().controlClearRm(i);
    }

    public static boolean groupControlWithType(int i, int i2, String str) {
        return BLEFastconHelper.getInstance().controlLightGroup(i, i2, EConvertUtils.hexStr2Bytes(str));
    }

    public static boolean groupControlWithType(int i, int i2, String str, int i3) {
        return BLEFastconHelper.getInstance().controlLightGroup(i, i2, EConvertUtils.hexStr2Bytes(str), i3);
    }

    public static boolean bathControlWithDeviceList(int[] iArr, String str) {
        return BLEFastconHelper.getInstance().controlLightBatch(iArr, 2, 150, EConvertUtils.hexStr2Bytes(str), 0);
    }

    public static boolean bathControlWithDeviceList(int[] iArr, String str, int i) {
        return BLEFastconHelper.getInstance().controlLightBatch(iArr, 2, 150, EConvertUtils.hexStr2Bytes(str), i);
    }

    public static boolean bathControlWithDeviceList(int[] iArr, String str, int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlLightBatch(iArr, i, i2, EConvertUtils.hexStr2Bytes(str), i3);
    }

    public static boolean musicControlWithGroupId(int i, String str) {
        return BLEFastconHelper.getInstance().controlLightMusic(i, EConvertUtils.hexStr2Bytes(str), 0);
    }

    public static boolean musicControlWithGroupId(int i, String str, int i2) {
        return BLEFastconHelper.getInstance().controlLightMusic(i, EConvertUtils.hexStr2Bytes(str), i2);
    }

    public static boolean musicControlWithGroupId(int i, String str, int i2, boolean z) {
        return BLEFastconHelper.getInstance().controlLightMusic(i, EConvertUtils.hexStr2Bytes(str), i2, z);
    }

    public static boolean musicControlWithGroupId(int i, String str, int i2, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlLightMusic(i, EConvertUtils.hexStr2Bytes(str), i2, z, z2);
    }

    public static boolean musicColorControlWithGroupId(int i, int i2, String str) {
        return BLEFastconHelper.getInstance().controlLightGroup(i, i2, EConvertUtils.hexStr2Bytes(str));
    }

    public static boolean sceneControlWithDevice(int i, BLESceneInfo bLESceneInfo) {
        return BLEFastconHelper.getInstance().controlLightScene(i, bLESceneInfo.jiffies, bLESceneInfo.parseScene());
    }

    public static boolean sceneControlWithGroupId(int i, BLESceneInfo bLESceneInfo) {
        return BLEFastconHelper.getInstance().controlLightGroupScene(i, bLESceneInfo.jiffies, bLESceneInfo.parseScene());
    }

    public static boolean timerQueryWithDevice(int i) {
        return BLEFastconHelper.getInstance().controlLightTimerGet(i);
    }

    public static boolean timerQueryWithDevice(int i, BLETimeLcInfo bLETimeLcInfo) {
        return BLEFastconHelper.getInstance().controlLightTimerGet(i, bLETimeLcInfo);
    }

    public static boolean timerSettingWithDevice(int i, BLETimerAllInfo bLETimerAllInfo) {
        return BLEFastconHelper.getInstance().controlLightTimerSet(i, bLETimerAllInfo);
    }

    public static String generateLightCommandWithState(boolean z, int i, int i2, int i3, int i4) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, i2, i3, i4, 0, 0, 1);
    }

    public static String generateLightCommandWithStateWithoutCalc(boolean z, int i, int i2, int i3, int i4) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, i2, i3, i4, 0, 0, true, 255, 1, false, false);
    }

    public static String generateLightCommandWithState(boolean z, int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, 0, 0, 0, i2, i3, 2);
    }

    public static String generateLightCommandWithState(boolean z, int i, int i2, int i3, int i4, int i5, int i6) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, i2, i3, i4, i5, i6, true, 255, 10, false, false);
    }

    public static String generateLightCommandWithState(boolean z, int i) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, 0, 0, 0, 0, 0, 3);
    }

    public static String generateLightCommandWithState(boolean z) {
        return generateLightCommandPwrWithBri(z, 0);
    }

    public static String generateLightCommandPwrWithBri(boolean z, int i) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, 0, 0, 0, 0, 0, 4);
    }

    public static String generateBatchLightCommandWithState(boolean z, int i) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, 0, 0, 0, 0, 0, 3, true);
    }

    public static String generateBatchLightCommandWithState(boolean z) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, 0, 0, 0, 0, 0, 0, 4, true);
    }

    public static String generateComposeCommandWithState(boolean z, int i, int i2, int i3, int i4, int i5, int i6, boolean z2, int i7) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, i2, i3, i4, i5, i6, z2, i7, 7, false);
    }

    public static String generateComposeCommandWithState(boolean z, int i) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(false, 0, 0, 0, 0, 0, 0, z, i, 6, false);
    }

    public static String generateComposeCommandWithState(boolean z) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(false, 0, 0, 0, 0, 0, 0, z, 0, 5, false);
    }

    public static String generateComposeCommandWithState(boolean z, int i, boolean z2, int i2) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, i, 0, 0, 0, 0, 0, z2, i2, 8, false);
    }

    public static String generateComposeCommandWithState(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(true, 0, i, i2, i3, 0, 0, true, 0, 9, false);
    }

    public static String generateLightAllCommandWithState(boolean z) {
        return BLEFastconHelper.getInstance().genSingleLightCommand(z, 0, 0, 0, 0, 0, 0, z, 0, 8, false);
    }

    public static String generateDimmingLightWarmOn(int i) {
        byte[] bArr = new byte[6];
        bArr[0] = (byte) ((i & 255) + 128);
        bArr[4] = -1;
        return EConvertUtils.bytes2HexStr(bArr);
    }

    public static String generateDimmingLightColdOn(int i) {
        byte[] bArr = new byte[6];
        bArr[0] = (byte) ((i & 255) + 128);
        bArr[5] = -1;
        return EConvertUtils.bytes2HexStr(bArr);
    }

    public static String generateDimmingLightColdWarmOn(int i) {
        return EConvertUtils.bytes2HexStr(new byte[]{(byte) ((i & 255) + 128), 0, 0, 0, -1, -1});
    }

    public static String generateNILightStatus(boolean z) {
        byte[] bArr = new byte[7];
        bArr[6] = (byte) (z ? 128 : 0);
        return EConvertUtils.bytes2HexStr(bArr);
    }

    public static void initRoomSceneList(List<BLERoomSceneInfo> list) {
        BLEFastconHelper.getInstance().setGroupSceneList(list);
    }

    public static int generateOneRoomSceneIdWithRoomId(int i) {
        return BLEFastconHelper.getInstance().appendSceneId(i);
    }

    public static boolean addRoomScene(BLERoomSceneInfo bLERoomSceneInfo) {
        return BLEFastconHelper.getInstance().addRoomScene(bLERoomSceneInfo);
    }

    public static boolean modifyRoomScene(BLERoomSceneInfo bLERoomSceneInfo) {
        return BLEFastconHelper.getInstance().modifyRoomScene(bLERoomSceneInfo);
    }

    public static boolean removeRoomScene(int i) {
        return BLEFastconHelper.getInstance().removeRoomScene(i);
    }

    public static void removeAllRoomScenes() {
        BLEFastconHelper.getInstance().removeAllRoomScenes();
    }

    public static BLERoomSceneInfo getRoomSceneInfoWithSceneId(int i) {
        return BLEFastconHelper.getInstance().queryRoomSceneInfoBySceneId(i);
    }

    public static boolean roomSceneControlWithSceneId(int i) {
        return BLEFastconHelper.getInstance().controlExecuteScene(i);
    }

    public static boolean roomSceneControlWithSceneId(int i, int i2) {
        return BLEFastconHelper.getInstance().controlExecuteScene(i, i2);
    }

    public static boolean roomSceneDeleteWithSceneId(int i) {
        return BLEFastconHelper.getInstance().controlDeleteScene2DevOrPanel(0, i);
    }

    public static boolean roomSceneDeleteWithDeviceAddr(int i, int i2) {
        return BLEFastconHelper.getInstance().controlDeleteScene2DevOrPanel(i, i2);
    }

    public static boolean pannelSceneDeleteWithShortAddr(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlDeleteScene2PanelBtn(i, i2, i3);
    }

    public static boolean pannelSceneExceptWithShortAddr(int i, int i2) {
        return BLEFastconHelper.getInstance().controlExceptScene2Dev(i, i2);
    }

    public static boolean resetSceneCmd2Panel(int i, int i2) {
        return BLEFastconHelper.getInstance().resetSceneCmd2Panel(i, i2);
    }

    public static boolean roomSceneAddWithPannelAddr(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlCreateOrUpdateScene2DevOrPanel(i, i3, new byte[]{(byte) i2});
    }

    public static boolean roomSceneAddWithPannelAddr(int i, int i2, String str) {
        return BLEFastconHelper.getInstance().controlUpdateSceneCmd2Panel(i, i2, EConvertUtils.hexStr2Bytes(str));
    }

    public static boolean roomSceneAddWithDeviceAddr(int i, int i2, String str, int i3) {
        return BLEFastconHelper.getInstance().controlCreateOrUpdateScene2DevOrPanel(i, i2, EConvertUtils.hexStr2Bytes(str), i3);
    }

    public static String genGatewayCloudCode(String str, String str2, int i, String str3) {
        return BLCloudCodeHelper.genCode(str, str2, i, str3);
    }

    public static String genFamilyGatewayCloudCode(String str, String str2, int i, String str3) {
        return BLCloudCodeHelper.genFamilyCode(str, str2, i, str3);
    }

    public static int broadcastTempGroupId(int[] iArr) {
        return BLEFastconHelper.getInstance().broadcastTempGroupId(iArr);
    }

    public static int broadcastTempGroupId(int[] iArr, short s, int i, int i2) {
        return BLEFastconHelper.getInstance().broadcastTempGroupId(253, iArr, s, i, i2);
    }

    public static int[] getFixedGroupIdPool(boolean z) {
        int[] iArr = new int[60];
        for (int i = 0; i < 60; i++) {
            iArr[i] = i + 193;
        }
        if (z) {
            for (int i2 = 0; i2 < 60; i2++) {
                for (int i3 = 0; i3 < 60; i3++) {
                    int iNextInt = new Random().nextInt(60);
                    int i4 = iArr[i3];
                    iArr[i3] = iArr[iNextInt];
                    iArr[iNextInt] = i4;
                }
            }
        }
        return iArr;
    }

    public static int generateFixedGroupWithGroupId(int i, int[] iArr) {
        return BLEFastconHelper.getInstance().broadcastFixedGroupId(i, iArr, 150, 3);
    }

    public static int removeFixGroupWithGroupId(int i, int[] iArr, int[] iArr2) {
        return BLEFastconHelper.getInstance().removeFixGroupWithGroupId(i, iArr, iArr2, 150, 3);
    }

    public static int supperPanelBindWithAddr(int i, int i2, int i3, int i4, int i5) {
        return BLEFastconHelper.getInstance().supperPanelBindWithAddr(i, i2, i3, i4, i5);
    }

    public static int supperPanelBindWithAddr(int i, int i2, int i3, int i4, int i5, String str) {
        return BLEFastconHelper.getInstance().supperPanelBindWithAddr(i, i2, i3, i4, i5, str);
    }

    public static int supperPanelBindWithKind(int i, int i2, int i3, int i4, int i5, String str) {
        return BLEFastconHelper.getInstance().supperPanelBindWithKind(i, i2, i3, i4, i5, str);
    }

    public static int supperPanelBindWithAddr(int i, int i2, int i3, int i4) {
        return BLEFastconHelper.getInstance().supperPanelBindWithAddr(i, i3, i2, i4);
    }

    public static int supperPanelBindWithAddr(int i, int i2, int i3, int i4, String str) {
        return BLEFastconHelper.getInstance().supperPanelBindWithAddr(i, i3, i2, i4, str);
    }

    public static int supperPanelUnBindWithGroup(int i, int i2, int i3, int i4) {
        return BLEFastconHelper.getInstance().supperPanelUnBindWithGroup(i, i2, i3, i4);
    }

    public static int supperPanelUnBindWithDev(int i, int i2, int i3, int i4, int i5) {
        return BLEFastconHelper.getInstance().supperPanelUnBindWithDev(i, i2, i3, i4, i5);
    }

    public static boolean supperPanelBindWithAddr(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().supperPanelBindWithAddr(i, i2, i3);
    }

    public static boolean controlQueryAlexaState(int i) {
        return BLEFastconHelper.getInstance().controlQueryAlexaState(i);
    }

    public static boolean controlSetAlexaState(int i, int i2) {
        return BLEFastconHelper.getInstance().controlSetAlexaState(i, i2);
    }

    public static boolean controlSetBackLight(int i, int i2) {
        return BLEFastconHelper.getInstance().controlSetBackLight(i, i2);
    }

    public static boolean controlSetSensorRecoverTime(int i, int i2) {
        return BLEFastconHelper.getInstance().controlSetSensorRecoverTime(i, i2);
    }

    public static boolean passThroughControlWithAddr(int i, String str, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().passThroughControlWithAddr(i, str, z, z2);
    }

    public static boolean passThroughControlWithAddr(int i, String str, boolean z) {
        return BLEFastconHelper.getInstance().passThroughControlWithAddr(i, str, z);
    }

    public static boolean passThroughControlWithAddr(int i, String str) {
        return BLEFastconHelper.getInstance().passThroughControlWithAddr(i, str, false);
    }

    public static boolean queryDeviceStatusWithAddrs(int[] iArr, byte b, boolean z, int i) {
        return BLEFastconHelper.getInstance().queryDeviceStatusWithAddrs(iArr, i, b, z);
    }

    public static boolean queryDeviceStatusWithAddrs(int[] iArr, byte b) {
        return BLEFastconHelper.getInstance().queryDeviceStatusWithAddrs(iArr, b, true);
    }

    public static boolean queryDeviceStatusWithAddrs(int[] iArr, byte b, boolean z) {
        return BLEFastconHelper.getInstance().queryDeviceStatusWithAddrs(iArr, b, z);
    }

    public static boolean queryDeviceStatusWithAddrs(int[] iArr) {
        return BLEFastconHelper.getInstance().queryDeviceStatusWithAddrs(iArr, BSON.CODE_W_SCOPE, true);
    }

    public static int startGatewayConfigWithAddress(int i, BLEGatewayConfigInfo bLEGatewayConfigInfo, long j, OnGatewayConfigCallback onGatewayConfigCallback) {
        return BLEFastconHelper.getInstance().startGatewayConfigWithAddress(i, bLEGatewayConfigInfo, j, onGatewayConfigCallback);
    }

    public static int startGatewayConfigWithAddressV2(int i, BLEGatewayConfigInfo bLEGatewayConfigInfo, long j, OnGatewayConfigCallbackV2 onGatewayConfigCallbackV2) {
        return BLEFastconHelper.getInstance().startGatewayConfigWithAddressV2(i, bLEGatewayConfigInfo, j, onGatewayConfigCallbackV2);
    }

    public static boolean stopGatewayConfig() {
        return BLEFastconHelper.getInstance().stopGatewayConfig();
    }

    public static boolean isGatewayConfigSending() {
        return BLEFastconHelper.getInstance().isDeviceConfigSending();
    }

    public static boolean controlRoomSceneAddFixGroup(int i, int i2, int i3, byte[] bArr) {
        return BLEFastconHelper.getInstance().controlRoomSceneAddFixGroup(i, i2, i3, bArr);
    }

    public static boolean controlRoomSceneDeleteFixGroup(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlRoomSceneDeleteFixGroup(i, i2, i3);
    }

    public static boolean controlVirDevBind(int i, int i2, int i3, int i4) {
        return BLEFastconHelper.getInstance().controlVirDevBind(i, i2, i3, i4);
    }

    public static boolean controlVirDevUnBind(int i, int i2) {
        return BLEFastconHelper.getInstance().controlVirDevUnBind(i, i2);
    }

    public static boolean controlDevHistoryQuery(int i, int i2, boolean z) {
        return BLEFastconHelper.getInstance().controlDevHistoryQuery(i, i2, z);
    }

    public static boolean controlDevHistoryClear(int i, boolean z) {
        return BLEFastconHelper.getInstance().controlDevHistoryClear(i, z);
    }

    public static boolean controlColorChange(int i, int i2, int i3, int i4, int i5, int i6, ArrayList<BLEModeRgbcwBean> arrayList, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRgbcwMode(i, i2, i3, i4, i5, i6, arrayList, z, z2);
    }

    public static boolean controlRgbcwModeV2(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, ArrayList<BLEModeRgbcwBean> arrayList, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRgbcwModeV2(i, i2, i3, i4, i5, i6, i7, i8, i9, arrayList, z, z2);
    }

    public static boolean controlColorTimerSet(int i, int i2, ArrayList<BLERgbcwTimerInfo> arrayList, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRgbcwTimerSet(i, i2, arrayList, z, z2);
    }

    public static boolean controlColorTimerGet(int i, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRgbcwTimerGet(i, z, z2);
    }

    public static void setOnDevColorTimerCallback(OnDevColorTimerCallback onDevColorTimerCallback) {
        BLEFastconHelper.getInstance().setOnColorTimerCallback(onDevColorTimerCallback);
    }

    public static boolean controlSetGatewayUdpParam(int i, int i2, int i3, int i4) {
        return BLEFastconHelper.getInstance().controlSetGatewayUdpParam(0, i, i2, i3, i4, true);
    }

    public static boolean controlLightCfgLock(int i, int i2, boolean z) {
        return BLEFastconHelper.getInstance().controlLightCfgLock(i, i2, z, true, true);
    }

    public static boolean controlLightCfgLock(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlLightCfgLock(i, i2, i3, true, true);
    }

    public static boolean controlLightPwrCut(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlLightPwrCut(i, i2, i3, true, true);
    }

    public static boolean controlLightDevSlowRise(int i, int i2) {
        return BLEFastconHelper.getInstance().controlLightSlowRise(0, 0, i, i2, true, true);
    }

    public static boolean controlLightGroupSlowRise(int i, int i2, int i3) {
        return BLEFastconHelper.getInstance().controlLightSlowRise(1, i2, i, i3, true, true);
    }

    public static boolean controlLightAtomSwitch(int i, int i2, boolean z) {
        return BLEFastconHelper.getInstance().controlLightAtomSwitch(i, i2, z, true, true);
    }

    public static boolean controlCarParkSelfLearnParam(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlCarParkSelfLearnParam(i, i2, i3, i4, i5, i6, i7, z, z2);
    }

    public static boolean controlCarParkRadarParam(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlCarParkRadarParam(i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, z, z2);
    }

    public static boolean controlCarParkSelfLearnParamClear(int i, int i2, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlCarParkSelfLearnParamClear(i, i2, z, z2);
    }

    public static boolean controlCarParkResetPubKey(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlCarParkResetPubKey(i, i2, i3, i4, z, z2);
    }

    public static boolean radarSettingWithType(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return BLEFastconHelper.getInstance().controlSetCarPartRadarParam(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public static boolean radar24gSettingWithType(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return BLEFastconHelper.getInstance().controlSet24gRadarParam(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public static boolean radarTimerQueryWithDeviceAddr(int i) {
        return BLEFastconHelper.getInstance().control24gWorkTimerGet(i);
    }

    public static boolean radarTimerSettingWithType(BLERadar24GAllInfo bLERadar24GAllInfo) {
        return BLEFastconHelper.getInstance().control24gWorkTimerSet(bLERadar24GAllInfo);
    }

    public static void setOnSendFailCallback(OnSendFailCallback onSendFailCallback) {
        BLEFastconHelper.getInstance().setOnSendFailCallback(onSendFailCallback);
    }

    public static List<String> generateTCCommandWithCount(int i) {
        return BLEFastconHelper.getInstance().generateTCCommandWithCount(i);
    }

    public static List<String> generateAokCommand() {
        return BLEFastconHelper.getInstance().generateAokCommand();
    }

    public static List<String> generateAokCommand(int i) {
        return BLEFastconHelper.getInstance().generateAokCommand(i);
    }

    public static List<String> generateSrCommand() {
        return BLEFastconHelper.getInstance().generateSrCommand();
    }

    public static List<String> generateSrCommand(int i) {
        return BLEFastconHelper.getInstance().generateSrCommand(i);
    }

    public static void enableJniLog(boolean z) {
        BLEUtil.setLogEnable(z ? 1 : 0);
    }

    public static boolean controlRmApEnable(int i, int i2, int i3, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRmApEnable(i, i2, i3, z, z2);
    }

    public static boolean controlDeleteDev(int i, int i2, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlDeleteDev(i, i2, z, z2);
    }

    public static boolean controlSetAdcSelfCheckParam(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlSetAdcSelfCheckParam(i, i2, i3, i4, i5, z, z2);
    }

    public static void removeFamily() {
        DevSignHelper.clearSign();
    }

    public static boolean controlRtcTimerSetTime(int i, int i2, int i3, BLEAppTimeInfo bLEAppTimeInfo, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerSetTime(i, i2, i3, bLEAppTimeInfo, z, z2);
    }

    public static boolean controlRtcTimerCmdOthers(int i, int i2, int i3, int i4, int i5, int i6, byte[] bArr, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerCmdOthers(i, i2, i3, i4, i5, i6, bArr, z, z2);
    }

    public static boolean controlRtcTimerCmdSelf(int i, int i2, int i3, int i4, byte[] bArr, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerCmdSelf(i, i2, i3, i4, bArr, z, z2);
    }

    public static boolean controlRtcTimerEnable(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerEnable(i, i2, i3, i4, z, z2);
    }

    public static boolean controlRtcTimerDelete(int i, int i2, int i3, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerDelete(i, i2, i3, z, z2);
    }

    public static boolean controlRtcTimerGet(int i, int i2, int i3, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerGet(i, i2, i3, z, z2);
    }

    public static boolean controlRtcTimerGetCmd(int i, int i2, int i3, boolean z, boolean z2) {
        return BLEFastconHelper.getInstance().controlRtcTimerGetCmd(i, i2, i3, z, z2);
    }

    public static boolean controlByPassWithAddr(int i, byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        return BLEFastconHelper.getInstance().controlPassThroughWithType(46, EAppUtils.mergeArrays(new byte[]{0, (byte) (i % 256)}, bArr), true, i / 256, z, z2);
    }

    public static void setOnDevRtcTimerCallback(OnDevRtcTimerCallback onDevRtcTimerCallback) {
        BLEFastconHelper.getInstance().setOnDevRtcTimerCallback(onDevRtcTimerCallback);
    }

    public static String getFamilyId() {
        return GatewayRemoteCtrlHelper.getFamilyId();
    }

    public static void setFamilyId(String str) {
        GatewayRemoteCtrlHelper.setFamilyId(str);
    }

    public static void setHttpHeaderParam(String str, String str2, String str3, String str4) {
        GatewayRemoteCtrlHelper.setLoginSession(str2);
        GatewayRemoteCtrlHelper.setUserId(str);
        GatewayRemoteCtrlHelper.setUUID(str3);
        GatewayRemoteCtrlHelper.setCompanyId(str4);
    }
}

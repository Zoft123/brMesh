package com.brgd.brblmesh.GeneralClass;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLEDelaySceneInfo;
import cn.com.broadlink.blelight.bean.BLEModeRgbcwBean;
import cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo;
import cn.com.broadlink.blelight.bean.BLESceneInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import cn.com.broadlink.blelight.helper.BLEFastconHelper;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GlobalBluetooth {
    public static final int CCT_TYPE = 43051;
    public static final int COLOR_TEMPERATURE = 255;
    public static final int DEFAULT_GROUP_ID = 0;
    public static final int DEFAULT_LIGHTNESS = 127;
    public static final int MAX_DEVICE = 250;
    public static final int MAX_FIXED_GROUP_ID = 252;
    public static final int MAX_FIXED_SIZE = 59;
    public static final int MAX_GROUP = 128;
    public static final int MIN_FIXED_GROUP_ID = 193;
    public static final int PWR_TYPE = 43049;
    public static final int RADAR_TYPE = 44601;
    public static final int RGBCW_TYPE = 43050;
    public static final int RGBW_TYPE = 43169;
    public static final int RGB_TYPE = 43168;
    public static final String SCENE_CMD = "";
    public static final int TEMP_GROUP_ID = 253;
    public static final int TIMEOUT_IN_MS = 1000;
    public static final int WHITE_PARAM = 127;
    public static final int WHITE_RGB_PARAM = 85;

    private static class GlobalBluetoothHolder {
        public static final GlobalBluetooth INSTANCE = new GlobalBluetooth();

        private GlobalBluetoothHolder() {
        }
    }

    private GlobalBluetooth() {
    }

    public static GlobalBluetooth getInstance() {
        return GlobalBluetoothHolder.INSTANCE;
    }

    public boolean isSupportBle() {
        return BluetoothAdapter.getDefaultAdapter() != null;
    }

    public boolean isBleEnable() {
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public void singleControl(int i, String str) {
        BLSBleLight.controlWithDevice(i, str, 100);
    }

    public void groupControl(int i, int i2, String str) {
        BLSBleLight.groupControlWithType(i, i2, str, 100);
    }

    public String onOffCommand(boolean z) {
        return BLSBleLight.generateLightCommandWithState(z);
    }

    public String lightnessCommand(int i) {
        return BLSBleLight.generateLightCommandWithState(true, i);
    }

    public String whiteRgbCommand(int i) {
        return BLSBleLight.generateLightCommandWithState(true, i, 85, 85, 85);
    }

    public String whiteCommand(int i) {
        return BLSBleLight.generateLightCommandWithState(true, i, 127, 127);
    }

    public String rgbCommand(boolean z, int i, int i2, int i3, int i4) {
        return BLSBleLight.generateLightCommandWithStateWithoutCalc(z, i, i2, i3, i4);
    }

    public String tempCommand(boolean z, int i, int i2, int i3) {
        return BLSBleLight.generateLightCommandWithState(z, i, i2, i3);
    }

    public void singleOnOff(int i, boolean z) {
        singleControl(i, onOffCommand(z));
    }

    public void groupOnOff(int i, int i2, boolean z) {
        groupControl(i, i2, onOffCommand(z));
    }

    public void singleLightness(int i, int i2) {
        singleControl(i, lightnessCommand(i2));
    }

    public void groupLightness(int i, int i2, int i3) {
        groupControl(i, i2, lightnessCommand(i3));
    }

    public void singleWhite(int i, int i2) {
        singleControl(i, whiteCommand(i2));
    }

    public void groupWhite(int i, int i2, int i3) {
        groupControl(i, i2, whiteCommand(i3));
    }

    public void singleWhiteRgb(int i, int i2) {
        singleControl(i, whiteRgbCommand(i2));
    }

    public void groupWhiteRgb(int i, int i2, int i3) {
        groupControl(i, i2, whiteRgbCommand(i3));
    }

    public void singleRgbCtrl(int i, int i2, int i3, int i4, int i5) {
        singleControl(i, rgbCommand(true, i2, i3, i4, i5));
    }

    public void groupRgbCtrl(int i, int i2, int i3, int i4, int i5, int i6) {
        groupControl(i, i2, rgbCommand(true, i3, i4, i5, i6));
    }

    public void singleTempCtrl(int i, int i2, int i3, int i4) {
        singleControl(i, tempCommand(true, i2, i3, i4));
    }

    public void groupTempCtrl(int i, int i2, int i3, int i4, int i5) {
        groupControl(i, i2, tempCommand(true, i3, i4, i5));
    }

    public void modSingleCtrl(int i, BLESceneInfo bLESceneInfo) {
        bLESceneInfo.grayscale = 1;
        BLSBleLight.sceneControlWithDevice(i, bLESceneInfo);
    }

    public void modGroupCtrl(int i, BLESceneInfo bLESceneInfo) {
        bLESceneInfo.grayscale = 1;
        BLSBleLight.sceneControlWithGroupId(i, bLESceneInfo);
    }

    public BLESceneInfo fullColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        BLESceneInfo.RgbStatus rgbStatus3 = new BLESceneInfo.RgbStatus();
        rgbStatus3.red = false;
        rgbStatus3.green = false;
        rgbStatus3.blue = true;
        BLESceneInfo.RgbStatus rgbStatus4 = new BLESceneInfo.RgbStatus();
        rgbStatus4.red = false;
        rgbStatus4.green = true;
        rgbStatus4.blue = true;
        BLESceneInfo.RgbStatus rgbStatus5 = new BLESceneInfo.RgbStatus();
        rgbStatus5.red = false;
        rgbStatus5.green = true;
        rgbStatus5.blue = false;
        BLESceneInfo.RgbStatus rgbStatus6 = new BLESceneInfo.RgbStatus();
        rgbStatus6.red = true;
        rgbStatus6.green = true;
        rgbStatus6.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        arrayList.add(rgbStatus3);
        arrayList.add(rgbStatus4);
        arrayList.add(rgbStatus5);
        arrayList.add(rgbStatus6);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo prColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = false;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo gbColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = false;
        rgbStatus.green = true;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        BLESceneInfo.RgbStatus rgbStatus3 = new BLESceneInfo.RgbStatus();
        rgbStatus3.red = false;
        rgbStatus3.green = true;
        rgbStatus3.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        arrayList.add(rgbStatus3);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo yrColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = true;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = false;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo jumpFullColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        BLESceneInfo.RgbStatus rgbStatus3 = new BLESceneInfo.RgbStatus();
        rgbStatus3.red = false;
        rgbStatus3.green = false;
        rgbStatus3.blue = true;
        BLESceneInfo.RgbStatus rgbStatus4 = new BLESceneInfo.RgbStatus();
        rgbStatus4.red = false;
        rgbStatus4.green = true;
        rgbStatus4.blue = true;
        BLESceneInfo.RgbStatus rgbStatus5 = new BLESceneInfo.RgbStatus();
        rgbStatus5.red = false;
        rgbStatus5.green = true;
        rgbStatus5.blue = false;
        BLESceneInfo.RgbStatus rgbStatus6 = new BLESceneInfo.RgbStatus();
        rgbStatus6.red = true;
        rgbStatus6.green = true;
        rgbStatus6.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        arrayList.add(rgbStatus3);
        arrayList.add(rgbStatus4);
        arrayList.add(rgbStatus5);
        arrayList.add(rgbStatus6);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rgbJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = true;
        rgbStatus2.blue = false;
        BLESceneInfo.RgbStatus rgbStatus3 = new BLESceneInfo.RgbStatus();
        rgbStatus3.red = false;
        rgbStatus3.green = false;
        rgbStatus3.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        arrayList.add(rgbStatus3);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo ygJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = true;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = true;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo pgJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = true;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo pbJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo ypJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = true;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rgJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = true;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rbJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo gbJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = false;
        rgbStatus.green = true;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo gJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = false;
        rgbStatus.green = true;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo bJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = false;
        rgbStatus.green = false;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rgbWhiteJumpColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = true;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = false;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rgColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = false;
        rgbStatus2.green = true;
        rgbStatus2.blue = false;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo bwColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = false;
        rgbStatus.green = false;
        rgbStatus.blue = true;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = true;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo gwColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = false;
        rgbStatus.green = true;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = true;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo rwColor(int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
        rgbStatus.red = true;
        rgbStatus.green = false;
        rgbStatus.blue = false;
        BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
        rgbStatus2.red = true;
        rgbStatus2.green = true;
        rgbStatus2.blue = true;
        arrayList.add(rgbStatus);
        arrayList.add(rgbStatus2);
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo diyJumpColor(List<ModDiyColor> list, int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 1;
        ArrayList arrayList = new ArrayList();
        for (ModDiyColor modDiyColor : list) {
            if (modDiyColor.getDiyColor() != -2) {
                BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
                int diyColor = modDiyColor.getDiyColor();
                if (diyColor == -16776961) {
                    rgbStatus.red = false;
                    rgbStatus.green = false;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -16711936) {
                    rgbStatus.red = false;
                    rgbStatus.green = true;
                    rgbStatus.blue = false;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -16711681) {
                    rgbStatus.red = false;
                    rgbStatus.green = true;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -65536) {
                    rgbStatus.red = true;
                    rgbStatus.green = false;
                    rgbStatus.blue = false;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -65281) {
                    rgbStatus.red = true;
                    rgbStatus.green = false;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -256) {
                    rgbStatus.red = true;
                    rgbStatus.green = true;
                    rgbStatus.blue = false;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -3) {
                    rgbStatus.red = true;
                    rgbStatus.green = true;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (arrayList.size() == 1) {
            BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
            rgbStatus2.red = false;
            rgbStatus2.green = false;
            rgbStatus2.blue = false;
            arrayList.add(rgbStatus2);
        }
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public BLESceneInfo diyColor(List<ModDiyColor> list, int i) {
        BLESceneInfo bLESceneInfo = new BLESceneInfo();
        bLESceneInfo.jiffies = i;
        bLESceneInfo.mode = 0;
        ArrayList arrayList = new ArrayList();
        for (ModDiyColor modDiyColor : list) {
            if (modDiyColor.getDiyColor() != -2) {
                BLESceneInfo.RgbStatus rgbStatus = new BLESceneInfo.RgbStatus();
                int diyColor = modDiyColor.getDiyColor();
                if (diyColor == -16776961) {
                    rgbStatus.red = false;
                    rgbStatus.green = false;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -16711936) {
                    rgbStatus.red = false;
                    rgbStatus.green = true;
                    rgbStatus.blue = false;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -16711681) {
                    rgbStatus.red = false;
                    rgbStatus.green = true;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -65536) {
                    rgbStatus.red = true;
                    rgbStatus.green = false;
                    rgbStatus.blue = false;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -65281) {
                    rgbStatus.red = true;
                    rgbStatus.green = false;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -256) {
                    rgbStatus.red = true;
                    rgbStatus.green = true;
                    rgbStatus.blue = false;
                    arrayList.add(rgbStatus);
                } else if (diyColor == -3) {
                    rgbStatus.red = true;
                    rgbStatus.green = true;
                    rgbStatus.blue = true;
                    arrayList.add(rgbStatus);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (arrayList.size() == 1) {
            BLESceneInfo.RgbStatus rgbStatus2 = new BLESceneInfo.RgbStatus();
            rgbStatus2.red = false;
            rgbStatus2.green = false;
            rgbStatus2.blue = false;
            arrayList.add(rgbStatus2);
        }
        bLESceneInfo.statusList = arrayList;
        return bLESceneInfo;
    }

    public void diyColorChange(int i, int i2, int i3, int i4, List<ModDiyColor> list) {
        ArrayList arrayList = new ArrayList();
        for (ModDiyColor modDiyColor : list) {
            if (modDiyColor.getDiyColorR() != -2) {
                BLEModeRgbcwBean bLEModeRgbcwBean = new BLEModeRgbcwBean();
                bLEModeRgbcwBean.r = Color.red(modDiyColor.getDiyColorR());
                bLEModeRgbcwBean.g = Color.green(modDiyColor.getDiyColorR());
                bLEModeRgbcwBean.b = Color.blue(modDiyColor.getDiyColorR());
                arrayList.add(bLEModeRgbcwBean);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() == 1) {
            BLEModeRgbcwBean bLEModeRgbcwBean2 = new BLEModeRgbcwBean();
            bLEModeRgbcwBean2.r = Color.red(0);
            bLEModeRgbcwBean2.g = Color.green(0);
            bLEModeRgbcwBean2.b = Color.blue(0);
            arrayList.add(bLEModeRgbcwBean2);
        }
        BLSBleLight.controlColorChange(i, i2, 0, 0, i3, i4, arrayList, false, true);
    }

    public void diyColorLChange(int i, int i2, int i3, int i4, int i5, List<ModDiyColor> list) {
        ArrayList arrayList = new ArrayList();
        for (ModDiyColor modDiyColor : list) {
            if (modDiyColor.getDiyColorR() != -2) {
                BLEModeRgbcwBean bLEModeRgbcwBean = new BLEModeRgbcwBean();
                bLEModeRgbcwBean.r = Color.red(modDiyColor.getDiyColorR());
                bLEModeRgbcwBean.g = Color.green(modDiyColor.getDiyColorR());
                bLEModeRgbcwBean.b = Color.blue(modDiyColor.getDiyColorR());
                arrayList.add(bLEModeRgbcwBean);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() == 1) {
            BLEModeRgbcwBean bLEModeRgbcwBean2 = new BLEModeRgbcwBean();
            bLEModeRgbcwBean2.r = Color.red(0);
            bLEModeRgbcwBean2.g = Color.green(0);
            bLEModeRgbcwBean2.b = Color.blue(0);
            arrayList.add(bLEModeRgbcwBean2);
        }
        BLSBleLight.controlRgbcwModeV2(i, i2, arrayList.size(), 0, 0, 0, i3, i4, i5, arrayList, false, true);
    }

    public void addScene(int i, int i2) {
        BLSBleLight.roomSceneAddWithDeviceAddr(i, i2, "", 1000);
    }

    public void deleteScene(int i, int i2) {
        BLSBleLight.roomSceneDeleteWithDeviceAddr(i, i2);
    }

    public void enableScene(int i) {
        BLSBleLight.roomSceneControlWithSceneId(i);
    }

    public void timerSettingWithDevice(int i, BLETimerAllInfo bLETimerAllInfo) {
        BLSBleLight.timerSettingWithDevice(i, bLETimerAllInfo);
    }

    public void timerQueryWithDevice(int i) {
        BLETimeLcInfo bLETimeLcInfo = new BLETimeLcInfo();
        bLETimeLcInfo.hour = Integer.parseInt(Tool.getCurrentHour());
        bLETimeLcInfo.min = Integer.parseInt(Tool.getCurrentMinute());
        BLSBleLight.timerQueryWithDevice(i, bLETimeLcInfo);
    }

    public void broadcastAppTimeSync() {
        BLEFastconHelper.getInstance().broadcastAppTimeSync();
    }

    public void controlColorTimerGet(int i) {
        BLSBleLight.controlColorTimerGet(i, false, false);
    }

    public void controlColorTimerSet(int i, ArrayList<BLERgbcwTimerInfo> arrayList) {
        BLSBleLight.controlColorTimerSet(0, i, arrayList, false, true);
    }

    public void controlDelaySceneWithDevice(int i, int i2, int i3, int i4) {
        int i5 = ((int) (i2 * ((127 - r2) / (i2 > i3 ? 100.0f : 99.0f)))) + (i2 > i3 ? 0 : 1);
        int i6 = (int) (i3 * 1.27f);
        int i7 = i5 > i6 ? -1 : 1;
        int iAbs = Math.abs(i6 - i5);
        if (iAbs == 0) {
            iAbs = 1;
        }
        int iMax = Math.max(1, (i4 * 60) / iAbs);
        if (i7 == -1 && i3 == 1 && (i7 * iAbs) + i5 < 2) {
            if (iAbs > 1) {
                iAbs--;
            }
            if (i5 < 4) {
                i5 = 2;
            }
        }
        if (i7 == -1 && i3 == 0 && i5 == 1) {
            iAbs = 2;
        }
        int i8 = (i7 == 1 && i2 == 1 && i3 == 3) ? 2 : iAbs;
        BLEDelaySceneInfo bLEDelaySceneInfo = new BLEDelaySceneInfo();
        bLEDelaySceneInfo.time = 0;
        bLEDelaySceneInfo.cold = 0;
        bLEDelaySceneInfo.warm = 0;
        bLEDelaySceneInfo.start = i5;
        bLEDelaySceneInfo.step = i7;
        bLEDelaySceneInfo.count = i8;
        bLEDelaySceneInfo.interval = iMax;
        BLSBleLight.controlDelaySceneWithDevice(i, bLEDelaySceneInfo);
    }

    public void controlDelaySceneWithGroup(int i, int i2, int i3, int i4) {
        int i5 = ((int) (i2 * ((127 - r2) / (i2 > i3 ? 100.0f : 99.0f)))) + (i2 > i3 ? 0 : 1);
        int i6 = (int) (i3 * 1.27f);
        int i7 = i5 > i6 ? -1 : 1;
        int iAbs = Math.abs(i6 - i5);
        if (iAbs == 0) {
            iAbs = 1;
        }
        int iMax = Math.max(1, (i4 * 60) / iAbs);
        if (i7 == -1 && i3 == 1 && (i7 * iAbs) + i5 < 2) {
            if (iAbs > 1) {
                iAbs--;
            }
            if (i5 < 4) {
                i5 = 2;
            }
        }
        if (i7 == -1 && i3 == 0 && i5 == 1) {
            iAbs = 2;
        }
        int i8 = (i7 == 1 && i2 == 1 && i3 == 3) ? 2 : iAbs;
        BLEDelaySceneInfo bLEDelaySceneInfo = new BLEDelaySceneInfo();
        bLEDelaySceneInfo.time = 0;
        bLEDelaySceneInfo.cold = 0;
        bLEDelaySceneInfo.warm = 0;
        bLEDelaySceneInfo.start = i5;
        bLEDelaySceneInfo.step = i7;
        bLEDelaySceneInfo.count = i8;
        bLEDelaySceneInfo.interval = iMax;
        BLSBleLight.controlDelaySceneWithGroup(i, bLEDelaySceneInfo);
    }
}

package cn.com.broadlink.blelight.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import cn.com.broadlink.blelight.R;
import cn.com.broadlink.blelight.bean.BLEAppTimeInfo;
import cn.com.broadlink.blelight.bean.BLEDelaySceneInfo;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import cn.com.broadlink.blelight.bean.BLEGatewayConfigInfo;
import cn.com.broadlink.blelight.bean.BLEModeRgbcwBean;
import cn.com.broadlink.blelight.bean.BLERadar24GAllInfo;
import cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo;
import cn.com.broadlink.blelight.bean.BLERoomSceneInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import cn.com.broadlink.blelight.bean.BLEWorkTimerAllInfo;
import cn.com.broadlink.blelight.bean.RmSubDevRetInfo;
import cn.com.broadlink.blelight.helper.DevSignHelper;
import cn.com.broadlink.blelight.interfaces.BLEScanCallback;
import cn.com.broadlink.blelight.interfaces.On24gWorkTimeCallback;
import cn.com.broadlink.blelight.interfaces.OnAlexaStateCallback;
import cn.com.broadlink.blelight.interfaces.OnDevColorTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevHeartBeatCallback;
import cn.com.broadlink.blelight.interfaces.OnDevRtcTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevScanCallback;
import cn.com.broadlink.blelight.interfaces.OnDevStateCallback;
import cn.com.broadlink.blelight.interfaces.OnDevStateRssiCallback;
import cn.com.broadlink.blelight.interfaces.OnDevTimerCallback;
import cn.com.broadlink.blelight.interfaces.OnDevUploadCallback;
import cn.com.broadlink.blelight.interfaces.OnGatewayConfigCallback;
import cn.com.broadlink.blelight.interfaces.OnGatewayConfigCallbackV2;
import cn.com.broadlink.blelight.interfaces.OnPassThroughCallback;
import cn.com.broadlink.blelight.interfaces.OnReceiveDevCtrlCallback;
import cn.com.broadlink.blelight.interfaces.OnReceiveFullFrameCallback;
import cn.com.broadlink.blelight.interfaces.OnSendFailCallback;
import cn.com.broadlink.blelight.interfaces.OnWorkTimeCallback;
import cn.com.broadlink.blelight.jni.BLEUtil;
import cn.com.broadlink.blelight.util.BLSpanUtils;
import cn.com.broadlink.blelight.util.EAlertUtils;
import cn.com.broadlink.blelight.util.EAppUtils;
import cn.com.broadlink.blelight.util.EConvertUtils;
import cn.com.broadlink.blelight.util.EEncryptUtils;
import cn.com.broadlink.blelight.util.ELogUtils;
import cn.com.broadlink.blelight.util.EPermissionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import j$.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class BLEFastconHelper {
    private static final int ANDROID_PAYLOAD_OFFSET = 7;
    public static final int BLE_BATCH_CTRL_MAX_CNT = 15;
    public static final int BLE_BATCH_CTRL_MAX_CNT_IN_DEV = 5;
    public static final int BLE_BATH_TEMP_GROUP_DEVICE_MAX = 104;
    public static final int BLE_CW_MAX = 255;
    public static final int BLE_CW_MIN = 0;
    public static final int BLE_DEV_GARAGE_DOOR = 45733;
    public static final int BLE_DEV_GARAGE_DOOR_1 = 45767;
    public static final String BLE_DEV_NAME_BEACON_WSD = "XL08";
    public static final int BLE_DEV_TIMER = 45798;
    public static final int BLE_DEV_TYPE_AIR_CLOUD_BUTLER = 44603;
    public static final int BLE_DEV_TYPE_BEACON_WSD = 44274;
    public static final int BLE_DEV_TYPE_BED = 45522;
    public static final int BLE_DEV_TYPE_CAMP_LAMP = 44483;
    public static final int BLE_DEV_TYPE_CARD_POWER = 43992;
    public static final int BLE_DEV_TYPE_COMMON_MOTOR = 44243;
    public static final int BLE_DEV_TYPE_CURTAIN = 43499;
    public static final int BLE_DEV_TYPE_CURTAIN_DREAM = 44113;
    public static final int BLE_DEV_TYPE_CURTAIN_OPEN_CLOSE = 44111;
    public static final int BLE_DEV_TYPE_CURTAIN_RM = 45153;
    public static final int BLE_DEV_TYPE_CURTAIN_ROLL = 44112;
    public static final int BLE_DEV_TYPE_CURTAIN_SHADE_RM = 45610;
    public static final int BLE_DEV_TYPE_CURTAIN_WSD_BLIND = 44724;
    public static final int BLE_DEV_TYPE_CURTAIN_WSD_DAY_NIGHT = 44725;
    public static final int BLE_DEV_TYPE_CURTAIN_WSD_DUAL = 44726;
    public static final int BLE_DEV_TYPE_DOOR_LOCK = 45127;
    public static final int BLE_DEV_TYPE_DOOR_MAGLEV = 45035;
    public static final int BLE_DEV_TYPE_DRYING_PANEL3 = 45165;
    public static final int BLE_DEV_TYPE_DRYING_RACK = 44007;
    public static final int BLE_DEV_TYPE_DRYING_RACK_WSD = 44727;
    public static final int BLE_DEV_TYPE_DRYING_RACK_WSD_DUAL = 44729;
    public static final int BLE_DEV_TYPE_DRYING_RACK_WSD_FULL = 44728;
    public static final int BLE_DEV_TYPE_ELE_METER = 45512;
    public static final int BLE_DEV_TYPE_FAN = 44049;
    public static final int BLE_DEV_TYPE_GATEWAY = 43500;
    public static final int BLE_DEV_TYPE_GATEWAY_1 = 45106;
    public static final int BLE_DEV_TYPE_GATEWAY_AC = 43756;
    public static final int BLE_DEV_TYPE_GATEWAY_IHG = 10058;
    public static final int BLE_DEV_TYPE_GATEWAY_LAN = 44602;
    public static final int BLE_DEV_TYPE_GATEWAY_OEM_BLACK_BEAN = 21028;
    public static final int BLE_DEV_TYPE_GATEWAY_OEM_RMAC = 21025;
    public static final int BLE_DEV_TYPE_GATEWAY_RMAC = 44939;
    public static final int BLE_DEV_TYPE_GATEWAY_TOIC_PAD = 45525;
    public static final int BLE_DEV_TYPE_JUMP_ROPE = 44313;
    public static final int BLE_DEV_TYPE_LIGHT_BURDEN_CW = 43754;
    public static final int BLE_DEV_TYPE_LIGHT_BURDEN_W = 43759;
    public static final int BLE_DEV_TYPE_LIGHT_CCT = 43051;
    public static final int BLE_DEV_TYPE_LIGHT_CCT_FSL_1 = 45648;
    public static final int BLE_DEV_TYPE_LIGHT_CCT_GREE_1 = 45548;
    public static final int BLE_DEV_TYPE_LIGHT_CCT_GREE_2 = 45544;
    public static final int BLE_DEV_TYPE_LIGHT_CCT_GREE_3 = 45546;
    public static final int BLE_DEV_TYPE_LIGHT_CCT_GREE_4 = 45550;
    public static final int BLE_DEV_TYPE_LIGHT_CCT_GREE_5 = 45552;
    public static final int BLE_DEV_TYPE_LIGHT_COMPOSE = 43709;
    public static final int BLE_DEV_TYPE_LIGHT_CW_CW = 44495;
    public static final int BLE_DEV_TYPE_LIGHT_CW_CW_LS = 44838;
    public static final int BLE_DEV_TYPE_LIGHT_CW_RGB = 44493;
    public static final int BLE_DEV_TYPE_LIGHT_DAZZLE_NEW = 44935;
    public static final int BLE_DEV_TYPE_LIGHT_OEM_HEXAGON = 44235;
    public static final int BLE_DEV_TYPE_LIGHT_PWR = 43049;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_GREE_1 = 45543;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_GREE_2 = 45545;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_GREE_3 = 45547;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_GREE_4 = 45549;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_GREE_5 = 45551;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_SELF_LEARN = 44876;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_SELF_LEARN_1 = 45776;
    public static final int BLE_DEV_TYPE_LIGHT_PWR_SELF_LEARN_ADC = 45559;
    public static final int BLE_DEV_TYPE_LIGHT_RGB = 43168;
    public static final int BLE_DEV_TYPE_LIGHT_RGBCW = 43050;
    public static final int BLE_DEV_TYPE_LIGHT_RGBW = 43169;
    public static final int BLE_DEV_TYPE_LIGHT_W_24G = 44601;
    public static final int BLE_DEV_TYPE_LIGHT_W_24G_QLM = 44060;
    public static final int BLE_DEV_TYPE_LIGHT_W_CW = 43745;
    public static final int BLE_DEV_TYPE_LIGHT_W_HIGH_V = 45655;
    public static final int BLE_DEV_TYPE_META_PAD = 43518;
    public static final int BLE_DEV_TYPE_META_PAD_ABILITY = 44199;
    public static final int BLE_DEV_TYPE_META_PAD_KNOB = 45764;
    public static final int BLE_DEV_TYPE_META_PAD_SINGLE = 44211;
    public static final int BLE_DEV_TYPE_PANEL_4 = 43473;
    public static final int BLE_DEV_TYPE_PANEL_4_WIRELESS = 43472;
    public static final int BLE_DEV_TYPE_PANEL_6 = 43461;
    public static final int BLE_DEV_TYPE_PANEL_6_WIRELESS = 43459;
    public static final int BLE_DEV_TYPE_PANEL_8 = 43733;
    public static final int BLE_DEV_TYPE_PANEL_8_2 = 43734;
    public static final int BLE_DEV_TYPE_RELAY_1 = 43525;
    public static final int BLE_DEV_TYPE_RELAY_1_OEM_FSL = 44624;
    public static final int BLE_DEV_TYPE_RELAY_2 = 43474;
    public static final int BLE_DEV_TYPE_RELAY_2_OEM_JP = 45746;
    public static final int BLE_DEV_TYPE_RELAY_3 = 43463;
    public static final int BLE_DEV_TYPE_RELAY_3_WIRELESS = 43462;
    public static final int BLE_DEV_TYPE_RELAY_4 = 43680;
    public static final int BLE_DEV_TYPE_RELAY_CURTAIN_1 = 44606;
    public static final int BLE_DEV_TYPE_RELAY_CURTAIN_2 = 44607;
    public static final int BLE_DEV_TYPE_SENSOR_DOOR = 43505;
    public static final int BLE_DEV_TYPE_SENSOR_FOUR_LINE = 44209;
    public static final int BLE_DEV_TYPE_SENSOR_GAS = 44880;
    public static final int BLE_DEV_TYPE_SENSOR_ILLUMINANCE = 44571;
    public static final int BLE_DEV_TYPE_SENSOR_ILLUMINANCE_LS = 45652;
    public static final int BLE_DEV_TYPE_SENSOR_IR = 43516;
    public static final int BLE_DEV_TYPE_SENSOR_OEM_MMC = 43951;
    public static final int BLE_DEV_TYPE_SENSOR_RADAR = 43808;
    public static final int BLE_DEV_TYPE_SENSOR_RADAR_24G = 44619;
    public static final int BLE_DEV_TYPE_SENSOR_RADAR_24G_1 = 45477;
    public static final int BLE_DEV_TYPE_SENSOR_RAIN = 45659;
    public static final int BLE_DEV_TYPE_SENSOR_SMOKE = 44895;
    public static final int BLE_DEV_TYPE_SENSOR_SOS = 44207;
    public static final int BLE_DEV_TYPE_SENSOR_SOUND_LIGHT = 45558;
    public static final int BLE_DEV_TYPE_SENSOR_WATER = 43791;
    public static final int BLE_DEV_TYPE_SMART_PAD_8 = 43981;
    public static final int BLE_DEV_TYPE_SOCKET_1 = 44181;
    public static final int BLE_DEV_TYPE_SOCKET_1_OEM_JP = 45124;
    public static final int BLE_DEV_TYPE_SOCKET_2 = 44180;
    public static final int BLE_DEV_TYPE_SOCKET_3 = 44167;
    public static final int BLE_DEV_TYPE_SOCKET_3_OEM_JP = 45485;
    public static final int BLE_DEV_TYPE_SOCKET_4 = 44164;
    public static final int BLE_DEV_TYPE_SOCKET_5 = 44165;
    public static final int BLE_DEV_TYPE_SOCKET_5_OEM_JP = 45495;
    public static final int BLE_DEV_TYPE_SOCKET_6 = 44166;
    public static final int BLE_DEV_TYPE_TC_1 = 45399;
    public static final int BLE_DEV_TYPE_TC_2 = 45401;
    public static final int BLE_DEV_TYPE_TC_3 = 45402;
    public static final int BLE_DEV_TYPE_THERMOSTAT = 43919;
    public static final int BLE_DEV_TYPE_TV_BACKLIGHT = 43990;
    public static final int BLE_DEV_TYPE_VIRTUAL_MULTI_PANEL = 44063;
    public static final int BLE_DEV_VIDEO_DOOR_LOCK = 70731;
    public static final int BLE_DEV_VIDEO_DOOR_LOCK_MS = 70759;
    private static final int BLE_FASTCON_GATEWAY_ADDRESS = 255;
    private static final int BLE_FASTCON_HEADER_LENGTH = 4;
    private static final int BLE_FASTCON_MAX_LENGTH = 16;
    private static final int BLE_FASTCON_NEW_MAX_LENGTH = 22;
    private static final int BLE_FASTCON_NEW_MAX_LENGTH_PATH_THROUGH = 25;
    private static final int BLE_FASTCON_PAYLOAD_MAX_LENGTH = 12;
    private static final int BLE_FASTCON_PAYLOAD_NEW_MAX_LENGTH = 18;
    private static final int BLE_FASTCON_TYPE_CONTROL_REQ = 5;
    private static final int BLE_FASTCON_TYPE_CONTROL_RES = 6;
    private static final int BLE_FASTCON_TYPE_DISCOVER_REQ = 1;
    private static final int BLE_FASTCON_TYPE_DISCOVER_RES = 2;
    private static final int BLE_FASTCON_TYPE_DISCOVER_TOGGLE = 0;
    private static final int BLE_FASTCON_TYPE_REMOTE_CTRL = 7;
    private static final int BLE_FASTCON_TYPE_UPLOAD_REQ = 3;
    private static final int BLE_FASTCON_TYPE_UPLOAD_RES = 4;
    public static final int BLE_JIFFIES_CNT_PER_SEC = 64;
    public static final int BLE_JIFFIES_MAX = 101;
    public static final int BLE_JIFFIES_MIN = 1;
    public static final int BLE_LIGHTNESS_MAX = 127;
    public static final int BLE_LIGHTNESS_MIN = 5;
    public static final int BLE_ROOM_SCENE_SHORT_ADDRESS_MAX = 255;
    public static final int BLE_TEMP_GROUP_DEV_CONTROL_ID = 253;
    public static final int BLE_TEMP_GROUP_DEV_MUSIC_ID = 254;
    public static final int BLE_TEMP_GROUP_DEV_RESERVE_ID = 255;
    public static final int MAX_ADDRESS_CNT = 255;
    public static final int RESEND_CNT = -1;
    private static final String TAG = "jyq_helper";
    public static final int VISUALIZER_CNT = 8;
    private static volatile BLEFastconHelper instance;
    private static MyHandler sHandler;
    private BluetoothAdapter mAdapter;
    private AdvertiseCallback mAdvertiseCallback;
    private AdvertiseData mAdvertiseData;
    private AdvertiseSettings mAdvertiseSettings;
    private BluetoothAdapter.LeScanCallback mCallback;
    private Timer mCloseBLEReceiveTimer;
    private int mConfigCookie;
    private BluetoothManager mManager;
    private On24gWorkTimeCallback mOn24gTimeCallback;
    private OnAlexaStateCallback mOnAlexaStateCallback;
    private OnGatewayConfigCallback mOnBLEConfigCallback;
    private OnGatewayConfigCallbackV2 mOnBLEConfigCallbackV2;
    private OnDevColorTimerCallback mOnColorTimerCallback;
    private OnReceiveDevCtrlCallback mOnDevCtrlCallback;
    private OnDevScanCallback mOnDevScanCallback;
    private OnDevStateCallback mOnDevStateCallback;
    private OnDevStateRssiCallback mOnDevStateRssiCallback;
    private OnDevUploadCallback mOnDevUploadCallback;
    private OnDevHeartBeatCallback mOnHearBeatCallback;
    private OnPassThroughCallback mOnPassThroughCallback;
    private OnReceiveFullFrameCallback mOnReceiveFullFrameCallback;
    private OnDevRtcTimerCallback mOnRtcTimerCallback;
    private OnSendFailCallback mOnSendFailCallback;
    private OnDevTimerCallback mOnTimerCallback;
    private OnWorkTimeCallback mOnWorkTimeCallback;
    private AlertDialog mPermAlertDialog;
    private RepeatPackageFilterHelper mRepeatPackageFilter;
    private BluetoothLeAdvertiser myAdvertiser;
    public static final int[] BLE_ROOM_SCENE_DEFAULT = {240, 241, 242, 243};
    private static final byte[] BLE_FASTCON_ADDRESS = {-63, -62, -61};
    private static int BLE_CMD_RETRY_CNT = 1;
    private static int BLE_CMD_SEND_TIME = PathInterpolatorCompat.MAX_NUM_POINTS;
    public static final byte[] CMD_LIGHT_ON = {-1};
    public static final byte[] CMD_GROUP_LIGHT_ON = {127};
    public static final byte[] CMD_LIGHT_OFF = {0};
    private static int sSendSeq = 0;
    private byte[] mPhoneKey = {-95, -94, -93, -92};
    private Queue<Integer> mQueueDevAddress = null;
    private Queue<Integer> mQueueDevAddress4096 = null;
    private List<BLEDeviceInfo> mDevList = new ArrayList();
    private boolean mUseFixedSeq = false;
    private final Map<Integer, Integer> mRssiCache = new TreeMap();
    private final Map<Integer, BLEDeviceInfo> mPairedDevMap = new TreeMap();
    private int mGlobalForwardFlag = 0;
    private List<BLERoomSceneInfo> mGroupSceneList = new ArrayList();
    public int mSendCnt = 0;
    private boolean mIsShowPermDialog = true;
    private boolean mIsShowBLEStateDialog = true;
    private boolean mIsShowSendFailDialog = false;
    private boolean mIsCheckPermBeforeSendCmd = true;
    private boolean mIsGatewayRemoteDebugMode = false;
    private boolean mIsGatewayRemoteDebugModeSendBLE = false;
    private boolean mFullFrameCallbackFlag = false;
    private Timer mThrottleTimer = null;
    private long mEnableWatchDog = 0;
    private HashMap<Integer, BLEDeviceInfo> groupMap = new HashMap<>();
    private List<Integer> keyList = new ArrayList();
    private boolean mIsSingleMode = true;
    private float[] mAvgZ = null;
    private long[] mFFTSendCnt = null;
    private int index = 0;
    private volatile boolean mIsSending = false;
    private volatile boolean mStopSendCfgCmd = false;
    private volatile boolean mReceivedState1 = false;

    public static class BLE_ALEXA_STATE {
        public static final int CLEAR = 2;
        public static final int OFF = 0;
        public static final int ON = 1;
    }

    public static class BLE_FIX_GROUP_KIND {
        public static final int AC = 4;
        public static final int CURTAIN = 2;
        public static final int GATEWAY = 5;
        public static final int LIGHT = 1;
        public static final int PANEL = 6;
        public static final int SENSOR = 7;
        public static final int SWITCH = 3;
    }

    public static class RM_SUB_DEV_TYPE {
        public static final int AC = 43919;
        public static final int AIR = 45662;
        public static final int AMP = 45661;
        public static final int CAMERA = 45664;
        public static final int CURTAIN = 45153;
        public static final int DEV_433 = 45534;
        public static final int DEV_IR = 44850;
        public static final int DE_HUMIDIFIER = 45668;
        public static final int DOOR = 44849;
        public static final int DOOR_LOCK = 44848;
        public static final int DVD = 44847;
        public static final int FAN = 44846;
        public static final int HEATER = 45663;
        public static final int HUMIDIFIER = 45666;
        public static final int LIGHT = 45152;
        public static final int PROJECTOR = 44845;
        public static final int RACK = 45669;
        public static final int SHADE = 45610;
        public static final int SMART_BOX = 45657;
        public static final int SOUND = 45660;
        public static final int SWEEP_ROBOT = 45667;
        public static final int SWITCH = 43459;
        public static final int TEMPMETER = 43951;
        public static final int TV = 44843;
        public static final int TV_BOX = 44844;
    }

    public static final class RM_SUB_TYPE {
        public static int AC = 1;
        public static int AIR = 19;
        public static int AMP = 18;
        public static int CAMERA = 20;
        public static int CURTAIN = 5;
        public static int DE_HUMIDIFIER = 22;
        public static int DOOR = 11;
        public static int DOOR_LOCK = 10;
        public static int DVD = 8;
        public static int FAN = 6;
        public static int HEATER = 15;
        public static int HUMIDIFIER = 14;
        public static int LIGHT = 7;
        public static int OTHERS = 12;
        public static int PROJECTOR = 4;
        public static int RACK = 9;
        public static int SOUND = 17;
        public static int SWEEP_ROBOT = 21;
        public static int SWITCH = 13;
        public static int TEMPMETER = 16;
        public static int TV = 2;
        public static int TV_BOX = 3;
    }

    public static final class SUB_TYPE {
        public static int ATOM_SWITCH = 12;
        public static int CONFIG_LOCK = 10;
        public static int PWR_CUT = 11;
    }

    private enum TlvType {
        kBLEDeviceMac,
        kBLEDeviceType,
        kBLEDeviceVersion,
        kBLECurrentState,
        kBLEWifiName,
        kBLEWifiPwd,
        kBLEDeviceHost,
        kBLEDeviceToken,
        kBLEFamilyInfo,
        kBLEConfigInfo,
        kBLEAppCookie,
        kBLEHostCode
    }

    public static int genSceneId(int i, int i2, int i3) {
        return (i << 16) | (i2 << 8) | i3;
    }

    public static int getGroupCtrlTypeFromKind(int i) {
        if (i == 1) {
            return 43050;
        }
        if (i == 2) {
            return BLE_DEV_TYPE_CURTAIN;
        }
        if (i == 3) {
            return 43459;
        }
        if (i != 5) {
            return 0;
        }
        return BLE_DEV_TYPE_GATEWAY;
    }

    public static int getRelayKeyCnt(int i) {
        switch (i) {
            case BLE_DEV_TYPE_RELAY_3_WIRELESS /* 43462 */:
            case BLE_DEV_TYPE_RELAY_3 /* 43463 */:
            case BLE_DEV_TYPE_SOCKET_3 /* 44167 */:
            case BLE_DEV_TYPE_TC_3 /* 45402 */:
            case BLE_DEV_TYPE_SOCKET_3_OEM_JP /* 45485 */:
                break;
            case BLE_DEV_TYPE_RELAY_2 /* 43474 */:
            case BLE_DEV_TYPE_SOCKET_2 /* 44180 */:
            case BLE_DEV_TYPE_RELAY_CURTAIN_1 /* 44606 */:
            case BLE_DEV_TYPE_TC_2 /* 45401 */:
            case BLE_DEV_TYPE_RELAY_2_OEM_JP /* 45746 */:
                break;
            case BLE_DEV_TYPE_RELAY_1 /* 43525 */:
            case BLE_DEV_TYPE_SOCKET_1 /* 44181 */:
            case BLE_DEV_TYPE_SOCKET_1_OEM_JP /* 45124 */:
            case BLE_DEV_TYPE_TC_1 /* 45399 */:
            case BLE_DEV_GARAGE_DOOR /* 45733 */:
            case BLE_DEV_GARAGE_DOOR_1 /* 45767 */:
                break;
            case BLE_DEV_TYPE_RELAY_4 /* 43680 */:
            case BLE_DEV_TYPE_SOCKET_4 /* 44164 */:
            case BLE_DEV_TYPE_RELAY_CURTAIN_2 /* 44607 */:
                break;
            case BLE_DEV_TYPE_SOCKET_5 /* 44165 */:
            case BLE_DEV_TYPE_SOCKET_5_OEM_JP /* 45495 */:
                break;
            case BLE_DEV_TYPE_SOCKET_6 /* 44166 */:
                break;
        }
        return 1;
    }

    public static boolean isAc(int i) {
        return i == 43756 || i == 43919;
    }

    public static boolean isBeaconDev(int i) {
        return i == 44274;
    }

    public static boolean isCurtain(int i) {
        return i == 43499 || i == 44113 || i == 44112 || i == 44243 || i == 44111 || i == 44724 || i == 44726 || i == 44725 || i == 45153 || i == 45610;
    }

    public static boolean isGateway(int i) {
        return i == 43500 || i == 45106 || i == 10058 || i == 44939 || i == 21028 || i == 45525 || i == 44602 || i == 21025;
    }

    public static boolean isLight(int i) {
        return i == 43709 || i == 43051 || i == 43049 || i == 44876 || i == 45776 || i == 45559 || i == 44601 || i == 44060 || i == 43168 || i == 43050 || i == 43169 || i == 43745 || i == 44493 || i == 44495 || i == 44838 || i == 43759 || i == 43754 || i == 44235 || i == 44483 || i == 44935 || i == 45543 || i == 45545 || i == 45547 || i == 45549 || i == 45551 || i == 45548 || i == 45544 || i == 45546 || i == 45550 || i == 45552 || i == 45655;
    }

    public static boolean isPanel(int i) {
        return i == 43473 || i == 43472 || i == 43461 || i == 43733 || i == 43734 || i == 43459 || i == 43518 || i == 44199 || i == 45764 || i == 44211;
    }

    public static boolean isQMLight(int i) {
        return i == 44601 || i == 44060 || i == 44876 || i == 45776 || i == 45559;
    }

    public static boolean isRelayCurtainPanel(int i) {
        return i == 44606 || i == 44607;
    }

    public static boolean isRelayPanel(int i) {
        return i == 43474 || i == 45746 || i == 43525 || i == 44624 || i == 43463 || i == 43462 || i == 44063 || i == 43680 || i == 45399 || i == 45401 || i == 45402 || i == 45733 || i == 45767;
    }

    public static boolean isRm(int i) {
        return i == 44939 || i == 21028 || i == 21025;
    }

    public static boolean isSensor(int i) {
        return i == 43505 || i == 43516 || i == 43791 || i == 45659 || i == 43808 || i == 44207 || i == 43951 || i == 44571 || i == 45652 || i == 44603 || i == 44895 || i == 44880;
    }

    public static boolean isSmartPad(int i) {
        return i == 43981;
    }

    public static boolean isSocket(int i) {
        return i == 44181 || i == 45124 || i == 45485 || i == 45495 || i == 44180 || i == 44167 || i == 44164 || i == 44165 || i == 44166;
    }

    public static boolean isSupperPanel(int i) {
        return i == 43518 || i == 44199 || i == 45764 || i == 44211;
    }

    public static boolean isSyncBindPanel(int i) {
        return i == 44063;
    }

    public void setPhoneKey(byte[] bArr) {
        byte[] bArr2 = this.mPhoneKey;
        if (bArr2[0] == bArr[0] && bArr2[1] == bArr[1] && bArr2[2] == bArr[2] && bArr2[3] == bArr[3]) {
            return;
        }
        this.mPairedDevMap.clear();
        this.mPhoneKey = bArr;
    }

    public byte[] getPhoneKey() {
        return this.mPhoneKey;
    }

    public List<BLEDeviceInfo> getDevList() {
        return this.mDevList;
    }

    public boolean isUseFixedSeq() {
        return this.mUseFixedSeq;
    }

    public void setUseFixedSeq(boolean z) {
        this.mUseFixedSeq = z;
    }

    public int getCachedRssi(int i) {
        Map<Integer, Integer> map = this.mRssiCache;
        if (map == null || !map.containsKey(Integer.valueOf(i))) {
            return 0;
        }
        return this.mRssiCache.get(Integer.valueOf(i)).intValue();
    }

    public BLEDeviceInfo getDevByAddr(int i) {
        Map<Integer, BLEDeviceInfo> map = this.mPairedDevMap;
        if (map == null || !map.containsKey(Integer.valueOf(i))) {
            return null;
        }
        return this.mPairedDevMap.get(Integer.valueOf(i));
    }

    public void clearPairedDevMap() {
        Map<Integer, BLEDeviceInfo> map = this.mPairedDevMap;
        if (map != null) {
            map.clear();
        }
    }

    public int getGlobalForwardFlag() {
        ELogUtils.w("jyq_forward", "flag: " + this.mGlobalForwardFlag);
        return this.mGlobalForwardFlag;
    }

    public void setGlobalForwardFlag(int i) {
        this.mGlobalForwardFlag = i;
    }

    public void setDevList(List<BLEDeviceInfo> list, boolean z) {
        if (list == null) {
            this.mDevList = new ArrayList();
        } else {
            ArrayList arrayList = new ArrayList();
            this.mDevList = arrayList;
            arrayList.addAll(list);
        }
        if (z) {
            initAddressQueueWithDevList();
            initAddressQueueWithDevList4096();
        }
    }

    public BLEDeviceInfo getDefaultGateway() {
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (isGateway(bLEDeviceInfo) && !TextUtils.isEmpty(bLEDeviceInfo.token) && bLEDeviceInfo.onlineState == 0) {
                ELogUtils.i("jyq_server", "return online gateway: " + bLEDeviceInfo.did);
                return bLEDeviceInfo;
            }
        }
        for (BLEDeviceInfo bLEDeviceInfo2 : this.mDevList) {
            if (isGateway(bLEDeviceInfo2) && !TextUtils.isEmpty(bLEDeviceInfo2.token)) {
                ELogUtils.i("jyq_server", "return first gateway: " + bLEDeviceInfo2.did);
                return bLEDeviceInfo2;
            }
        }
        ELogUtils.w("jyq_server", "no gateway found");
        return null;
    }

    public List<BLEDeviceInfo> getGatewayList() {
        ArrayList arrayList = new ArrayList();
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (isGateway(bLEDeviceInfo)) {
                arrayList.add(bLEDeviceInfo);
            }
        }
        return arrayList;
    }

    public List<BLERoomSceneInfo> getGroupSceneList() {
        return this.mGroupSceneList;
    }

    public void setGroupSceneList(List<BLERoomSceneInfo> list) {
        this.mGroupSceneList = list;
    }

    public void setOnDevScanCallback(OnDevScanCallback onDevScanCallback) {
        this.mOnDevScanCallback = onDevScanCallback;
    }

    public void setOnAlexaStateCallback(OnAlexaStateCallback onAlexaStateCallback) {
        this.mOnAlexaStateCallback = onAlexaStateCallback;
    }

    public void setOnPassThroughCallback(OnPassThroughCallback onPassThroughCallback) {
        this.mOnPassThroughCallback = onPassThroughCallback;
    }

    public void setOnTimerCallback(OnDevTimerCallback onDevTimerCallback) {
        this.mOnTimerCallback = onDevTimerCallback;
    }

    public void setOnColorTimerCallback(OnDevColorTimerCallback onDevColorTimerCallback) {
        this.mOnColorTimerCallback = onDevColorTimerCallback;
    }

    public void setOnDevRtcTimerCallback(OnDevRtcTimerCallback onDevRtcTimerCallback) {
        this.mOnRtcTimerCallback = onDevRtcTimerCallback;
    }

    public void setOnHeartBeatCallback(OnDevHeartBeatCallback onDevHeartBeatCallback) {
        this.mOnHearBeatCallback = onDevHeartBeatCallback;
    }

    public void setOnDevStateCallback(OnDevStateCallback onDevStateCallback) {
        this.mOnDevStateCallback = onDevStateCallback;
    }

    public void setOnDevStateRssiCallback(OnDevStateRssiCallback onDevStateRssiCallback) {
        this.mOnDevStateRssiCallback = onDevStateRssiCallback;
    }

    public void setOnWorkTimeCallback(OnWorkTimeCallback onWorkTimeCallback) {
        this.mOnWorkTimeCallback = onWorkTimeCallback;
    }

    public void setOn24gTimeCallback(On24gWorkTimeCallback on24gWorkTimeCallback) {
        this.mOn24gTimeCallback = on24gWorkTimeCallback;
    }

    public void setOnDevUploadCallback(OnDevUploadCallback onDevUploadCallback) {
        this.mOnDevUploadCallback = onDevUploadCallback;
    }

    public void setOnReceiveDevCtrlCallback(OnReceiveDevCtrlCallback onReceiveDevCtrlCallback) {
        this.mOnDevCtrlCallback = onReceiveDevCtrlCallback;
    }

    public void setOnSendFailCallback(OnSendFailCallback onSendFailCallback) {
        this.mOnSendFailCallback = onSendFailCallback;
    }

    public void setOnReceiveFullFrameCallback(OnReceiveFullFrameCallback onReceiveFullFrameCallback) {
        this.mOnReceiveFullFrameCallback = onReceiveFullFrameCallback;
    }

    public void resetSendCnt() {
        this.mSendCnt = 0;
    }

    public boolean isShowPermDialog() {
        return this.mIsShowPermDialog;
    }

    public void setShowPermDialog(boolean z) {
        this.mIsShowPermDialog = z;
    }

    public boolean isShowSendFailDialog() {
        return this.mIsShowSendFailDialog;
    }

    public void setShowSendFailDialog(boolean z) {
        this.mIsShowSendFailDialog = z;
    }

    public boolean checkPermBeforeSendCmd() {
        return this.mIsCheckPermBeforeSendCmd;
    }

    public void setCheckPermBeforeSendCmd(boolean z) {
        this.mIsCheckPermBeforeSendCmd = z;
    }

    public static void setSendParam(int i, int i2) {
        BLE_CMD_RETRY_CNT = i;
        BLE_CMD_SEND_TIME = i2;
    }

    public boolean isGatewayRemoteDebugMode() {
        return this.mIsGatewayRemoteDebugMode;
    }

    public void setGatewayRemoteDebugMode(boolean z) {
        this.mIsGatewayRemoteDebugMode = z;
    }

    public boolean isGatewayRemoteDebugModeSendBLE() {
        return this.mIsGatewayRemoteDebugModeSendBLE;
    }

    public void setGatewayRemoteDebugModeSendBLE(boolean z) {
        this.mIsGatewayRemoteDebugModeSendBLE = z;
    }

    public boolean isFullFrameCallbackFlag() {
        return this.mFullFrameCallbackFlag;
    }

    public void setFullFrameCallbackFlag(boolean z) {
        this.mFullFrameCallbackFlag = z;
    }

    public static final BLEFastconHelper getInstance() {
        if (instance == null) {
            synchronized (BLEFastconHelper.class) {
                if (instance == null) {
                    instance = new BLEFastconHelper();
                }
            }
        }
        return instance;
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [cn.com.broadlink.blelight.helper.BLEFastconHelper$3] */
    BLEFastconHelper() {
        sSendSeq = (int) (System.currentTimeMillis() % 256);
        this.mCallback = new BluetoothAdapter.LeScanCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.1
            @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                BLEFastconHelper.this.doDealWithScanResult(bluetoothDevice, i, bArr);
            }
        };
        this.mAdvertiseCallback = new AdvertiseCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.2
            @Override // android.bluetooth.le.AdvertiseCallback
            public void onStartSuccess(AdvertiseSettings advertiseSettings) {
                super.onStartSuccess(advertiseSettings);
                ELogUtils.d(BLEFastconHelper.TAG, "Advertise start succeeds: " + advertiseSettings.toString());
            }

            @Override // android.bluetooth.le.AdvertiseCallback
            public void onStartFailure(int i) {
                super.onStartFailure(i);
                ELogUtils.e(BLEFastconHelper.TAG, "Advertise start failed: " + i);
                if (BLEFastconHelper.this.mIsShowSendFailDialog) {
                    BLEFastconHelper.this.showPermAlert(R.string.alert_ble_send_fail, new Intent("android.settings.BLUETOOTH_SETTINGS"));
                }
                if (BLEFastconHelper.this.mOnSendFailCallback != null) {
                    BLEFastconHelper.this.mOnSendFailCallback.onCallback(i);
                }
            }
        };
        new Thread() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                Looper.prepare();
                MyHandler unused = BLEFastconHelper.sHandler = BLEFastconHelper.this.new MyHandler(Looper.myLooper());
                Looper.loop();
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDealWithScanResult(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        String strBytes2HexString = bytes2HexString(bArr);
        if (this.mRepeatPackageFilter == null) {
            this.mRepeatPackageFilter = new RepeatPackageFilterHelper();
        }
        if (!this.mRepeatPackageFilter.filter(strBytes2HexString)) {
            ELogUtils.v(TAG, "onLeScan(repeat,ignored): " + bluetoothDevice.getAddress() + " - " + strBytes2HexString);
            return;
        }
        String name = bluetoothDevice.getName();
        if (!TextUtils.isEmpty(name)) {
            ELogUtils.i(TAG, "onLeScan: " + name + " - " + bArr.length + " - " + strBytes2HexString);
        } else {
            ELogUtils.v(TAG, "onLeScan: " + bluetoothDevice.getAddress() + " - " + bArr.length + " - " + strBytes2HexString);
        }
        if (this.mOnHearBeatCallback == null && this.mOnDevScanCallback == null && this.mOnTimerCallback == null && this.mOnAlexaStateCallback == null && this.mOnPassThroughCallback == null && this.mOnDevStateCallback == null && this.mOnColorTimerCallback == null && this.mOnReceiveFullFrameCallback == null && this.mOnBLEConfigCallback == null && this.mOnBLEConfigCallbackV2 == null && this.mOnWorkTimeCallback == null && this.mOnDevUploadCallback == null && this.mOn24gTimeCallback == null && this.mOnDevStateRssiCallback == null && this.mOnDevCtrlCallback == null && this.mOnRtcTimerCallback == null) {
            Log.v(TAG, "onLeScan: throw out, for all callback is null.");
            return;
        }
        if (!TextUtils.isEmpty(name) && name.toUpperCase().startsWith(BLE_DEV_NAME_BEACON_WSD)) {
            if (this.mOnDevScanCallback == null && this.mOnDevUploadCallback == null) {
                return;
            }
            ELogUtils.d(TAG, "beacon: " + strBytes2HexString);
            if (strBytes2HexString.startsWith("0201040709584c303830310cff")) {
                String strSubstring = strBytes2HexString.substring(36, 48);
                if (this.mOnDevScanCallback != null) {
                    BLEDeviceInfo bLEDeviceInfo = new BLEDeviceInfo();
                    bLEDeviceInfo.name = name;
                    bLEDeviceInfo.type = BLE_DEV_TYPE_BEACON_WSD;
                    bLEDeviceInfo.addr = devAppendAddr(true);
                    bLEDeviceInfo.did = strSubstring;
                    bLEDeviceInfo.mac = EAppUtils.did2Mac(bLEDeviceInfo.did);
                    bLEDeviceInfo.version = "1.0.0.0.0";
                    this.mOnDevScanCallback.onCallback(bLEDeviceInfo);
                }
                OnDevUploadCallback onDevUploadCallback = this.mOnDevUploadCallback;
                if (onDevUploadCallback != null) {
                    onDevUploadCallback.onCallback(strSubstring, strBytes2HexString.substring(26, 48));
                    return;
                }
                return;
            }
            return;
        }
        if (bArr.length < 23) {
            return;
        }
        String strSubstring2 = strBytes2HexString.substring(6);
        String str = name + " - " + bluetoothDevice.getAddress();
        if ((strSubstring2.startsWith("13fff0ff") || strSubstring2.startsWith("1bfff0ff")) && !strSubstring2.startsWith("1bfff0ffa55a") && !strBytes2HexString.contains("f0ff6db64368931d")) {
            parseOldProtocolPayload(str, Arrays.copyOfRange(bArr, 7, (strSubstring2.startsWith("1bfff0ff") ? 8 : 0) + 23), true, i);
            return;
        }
        if (strSubstring2.startsWith("1bfff0ffa55a")) {
            parseNewProtocolPayload(str, Arrays.copyOfRange(bArr, 9, 31), true, i);
            return;
        }
        if (strBytes2HexString.contains("f0ff6db64368931d")) {
            byte[] devCtrlUpload = parseDevCtrlUpload(Arrays.copyOfRange(bArr, 7, 31));
            if (devCtrlUpload != null) {
                parseOldProtocolPayload(str + "-devCtrl", devCtrlUpload, true, i);
                return;
            }
            return;
        }
        if (strBytes2HexString.contains("f0ffa55a")) {
            parsePassThroughPayload(str, Arrays.copyOfRange(bArr, 6, 31), true, i);
        }
    }

    public void parseReceivePackage(String str, byte[] bArr) {
        if (bArr == null) {
            return;
        }
        if (bArr.length == 16 || bArr.length == 24) {
            parseOldProtocolPayload(str, bArr, false, 0);
            return;
        }
        if (bArr.length == 22) {
            parseNewProtocolPayload(str, bArr, false, 0);
            return;
        }
        if (bArr.length == 25) {
            parsePassThroughPayload(str, bArr, false, 0);
            return;
        }
        ELogUtils.w(TAG, "onLeScan: " + String.format(Locale.ENGLISH, "[%s] parse fail: len: %d, data: %s", str, Integer.valueOf(bArr.length), bytes2HexString(bArr)));
    }

    private byte[] parseDevCtrlUpload(byte[] bArr) {
        ELogUtils.d(TAG, "onLeScan: " + String.format(Locale.ENGLISH, "parseDevCtrlUpload: len: %d, data: %s", Integer.valueOf(bArr.length), EConvertUtils.bytes2HexStr(bArr)));
        byte[] bArr2 = new byte[16];
        if (BLEUtil.whiten_data(bArr, bArr.length, bArr2) < 0) {
            return null;
        }
        return bArr2;
    }

    private void parseNewProtocolPayload(String str, byte[] bArr, boolean z, final int i) {
        ELogUtils.d(TAG, "onLeScan: " + String.format(Locale.ENGLISH, "[%s] new protocol: len: %d, data: %s", str, Integer.valueOf(bArr.length), bytes2HexString(bArr)));
        BLEUtil.parse_ble_broadcast_new(bArr, bArr.length, this.mPhoneKey, new OnPassThroughCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.4
            @Override // cn.com.broadlink.blelight.interfaces.OnPassThroughCallback
            public void onCallback(int i2, byte[] bArr2) {
                BLEWorkTimerAllInfo bLEWorkTimerAllInfo;
                ArrayList<BLERgbcwTimerInfo> arrayList;
                BLERadar24GAllInfo bLERadar24GAllInfo;
                BLEAppTimeInfo bLEAppTimeInfo;
                int i3;
                int i4;
                BLEFastconHelper.this.mRssiCache.put(Integer.valueOf(i2), Integer.valueOf(i));
                ELogUtils.i(BLEFastconHelper.TAG, "parseNewProtocolPayload().onCallback: " + String.format(Locale.ENGLISH, "[%d] data: %s", Integer.valueOf(i2), EConvertUtils.bytes2HexStr(bArr2)));
                byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr2, 1, bArr2.length);
                byte b = bArr2[0];
                if (b != 0) {
                    if (b != 1) {
                        if (b != 10) {
                            switch (b) {
                                case 12:
                                    if (BLEFastconHelper.this.mOnColorTimerCallback != null && (arrayList = BLEUtil.parse_rgbcw_timer_upload(bArrCopyOfRange)) != null) {
                                        BLEFastconHelper.this.mOnColorTimerCallback.onCallback(i2, arrayList);
                                        break;
                                    }
                                    break;
                                case 13:
                                    if (BLEFastconHelper.this.mOn24gTimeCallback != null && (bLERadar24GAllInfo = BLEUtil.parse_24g_work_time_upload(i2, bArrCopyOfRange)) != null) {
                                        BLEFastconHelper.this.mOn24gTimeCallback.onCallback(i2, bLERadar24GAllInfo);
                                        break;
                                    }
                                    break;
                                case 14:
                                    if (BLEFastconHelper.this.mOnRtcTimerCallback != null) {
                                        int i5 = bArrCopyOfRange[0] & 255;
                                        if (bArrCopyOfRange.length < 3) {
                                            ELogUtils.w(BLEFastconHelper.TAG, "收到RTC定时上报（14），长度少于3，忽略。");
                                        } else {
                                            ArrayList arrayList2 = new ArrayList();
                                            byte[] bArrLittleEndian = EConvertUtils.littleEndian(Arrays.copyOfRange(bArrCopyOfRange, 1, 3));
                                            for (int i6 = 0; i6 < bArrLittleEndian.length && i6 <= 16; i6++) {
                                                byte b2 = bArrLittleEndian[i6];
                                                for (int i7 = 0; i7 < 8; i7++) {
                                                    if ((b2 & (1 << i7)) != 0) {
                                                        arrayList2.add(Integer.valueOf((i6 * 8) + i7 + 1));
                                                    }
                                                }
                                            }
                                            if (bArrCopyOfRange.length == 12) {
                                                int i8 = bArrCopyOfRange[3] & 255;
                                                int i9 = bArrCopyOfRange[4] & 255;
                                                BLEAppTimeInfo bLEAppTimeInfo2 = new BLEAppTimeInfo();
                                                bLEAppTimeInfo2.year = bArrCopyOfRange[5] & 255;
                                                bLEAppTimeInfo2.month = bArrCopyOfRange[6] & 255;
                                                bLEAppTimeInfo2.day = bArrCopyOfRange[7] & 255;
                                                bLEAppTimeInfo2.week = bArrCopyOfRange[8] & 255;
                                                bLEAppTimeInfo2.hour = bArrCopyOfRange[9] & 255;
                                                bLEAppTimeInfo2.min = bArrCopyOfRange[10] & 255;
                                                bLEAppTimeInfo2.sec = bArrCopyOfRange[11] & 255;
                                                i4 = i9;
                                                bLEAppTimeInfo = bLEAppTimeInfo2;
                                                i3 = i8;
                                            } else {
                                                bLEAppTimeInfo = null;
                                                i3 = 0;
                                                i4 = 0;
                                            }
                                            BLEFastconHelper.this.mOnRtcTimerCallback.onTimerCallback(i2, i5, arrayList2, i3, i4, bLEAppTimeInfo);
                                        }
                                    }
                                    break;
                                case 15:
                                    if (BLEFastconHelper.this.mOnRtcTimerCallback != null) {
                                        if (bArrCopyOfRange.length >= 4) {
                                            BLEFastconHelper.this.mOnRtcTimerCallback.onTimerCmdCallback(i2, bArrCopyOfRange[0] & 255, bArrCopyOfRange[1] & 255, bArrCopyOfRange[2] & 255, Arrays.copyOfRange(bArrCopyOfRange, 3, bArrCopyOfRange.length));
                                        } else {
                                            ELogUtils.w(BLEFastconHelper.TAG, "收到RTC定时上报（15），长度少于4，忽略。");
                                        }
                                    }
                                    break;
                                default:
                                    if (BLEFastconHelper.this.mOnDevStateCallback != null) {
                                        BLEFastconHelper.this.mOnDevStateCallback.onCallback(i2, bArr2[0], bArrCopyOfRange);
                                    }
                                    if (BLEFastconHelper.this.mOnDevStateRssiCallback != null) {
                                        BLEFastconHelper.this.mOnDevStateRssiCallback.onCallback(i2, bArr2[0], bArrCopyOfRange, i);
                                    }
                                    break;
                            }
                            return;
                        }
                        if (BLEFastconHelper.this.mOnWorkTimeCallback == null || (bLEWorkTimerAllInfo = BLEUtil.parse_work_time_upload(i2, bArrCopyOfRange)) == null) {
                            return;
                        }
                        BLEFastconHelper.this.mOnWorkTimeCallback.onCallback(i2, bLEWorkTimerAllInfo);
                        return;
                    }
                } else if (BLEFastconHelper.this.mOnBLEConfigCallback == null) {
                    if (BLEFastconHelper.this.mOnBLEConfigCallbackV2 != null && bArrCopyOfRange.length >= 7 && bArrCopyOfRange[0] == 65 && bArrCopyOfRange[1] == 84 && bArrCopyOfRange[2] == 43) {
                        byte b3 = bArrCopyOfRange[3];
                        if (b3 == 0 || b3 == 1) {
                            int i10 = ((bArrCopyOfRange[6] & 255) * 256) + (bArrCopyOfRange[5] & 255);
                            boolean z2 = b3 == 0;
                            if (i10 == BLEFastconHelper.this.mConfigCookie) {
                                String strSubstring = EConvertUtils.bytes2HexStr(bArrCopyOfRange).substring(14);
                                String strBytes2HexStr = EConvertUtils.bytes2HexStr(EEncryptUtils.md5(i10 + strSubstring));
                                if (BLEFastconHelper.this.mOnBLEConfigCallbackV2 != null) {
                                    BLEFastconHelper.this.mOnBLEConfigCallbackV2.onTokenReturn(i2, strSubstring, strBytes2HexStr, z2);
                                }
                                BLEFastconHelper.this.mReceivedState1 = true;
                                if (z2) {
                                    BLEFastconHelper.this.mIsSending = false;
                                    if (BLEFastconHelper.this.mOnBLEConfigCallbackV2 != null) {
                                        BLEFastconHelper.this.mOnBLEConfigCallbackV2.onStopped();
                                    }
                                }
                            } else {
                                ELogUtils.d("jyq_gateway", String.format(Locale.ENGLISH, "callback---->type1, address[%d], cookie[%d, %d], data[%s]", Integer.valueOf(i2), Integer.valueOf(i10), Integer.valueOf(BLEFastconHelper.this.mConfigCookie), EConvertUtils.bytes2HexStr(bArrCopyOfRange)));
                            }
                        } else if (b3 == 2) {
                            int i11 = bArrCopyOfRange[5] & 255;
                            byte b4 = bArrCopyOfRange[6];
                            ELogUtils.d("jyq_gateway", String.format(Locale.ENGLISH, "callback---->type2,  address[%d], state[%d], error[%d]", Integer.valueOf(i2), Integer.valueOf(i11), Integer.valueOf(b4)));
                            if (BLEFastconHelper.this.mReceivedState1) {
                                BLEFastconHelper.this.mStopSendCfgCmd = true;
                                if (i11 > 0 && BLEFastconHelper.this.mOnBLEConfigCallbackV2 != null) {
                                    BLEFastconHelper.this.mOnBLEConfigCallbackV2.onStateResult(i2, i11, b4);
                                }
                            } else {
                                ELogUtils.i(BLEFastconHelper.TAG, "not received state1, ignore state2");
                                return;
                            }
                        }
                    }
                } else if (bArrCopyOfRange != null && bArrCopyOfRange.length == 13 && bArrCopyOfRange[0] == 65 && bArrCopyOfRange[1] == 84 && bArrCopyOfRange[2] == 43 && bArrCopyOfRange[3] == 0 && bArrCopyOfRange[4] == 8) {
                    int i12 = ((bArrCopyOfRange[6] & 255) * 256) + (bArrCopyOfRange[5] & 255);
                    if (i12 == BLEFastconHelper.this.mConfigCookie) {
                        String strSubstring2 = EConvertUtils.bytes2HexStr(bArrCopyOfRange).substring(14);
                        BLEFastconHelper.this.mOnBLEConfigCallback.onConfigResult(i2, strSubstring2, EConvertUtils.bytes2HexStr(EEncryptUtils.md5(i12 + strSubstring2)));
                    } else {
                        ELogUtils.d("jyq_gateway", String.format(Locale.ENGLISH, "callback----> address[%d], cookie[%d, %d], data[%s]", Integer.valueOf(i2), Integer.valueOf(i12), Integer.valueOf(BLEFastconHelper.this.mConfigCookie), EConvertUtils.bytes2HexStr(bArrCopyOfRange)));
                    }
                }
                if (BLEFastconHelper.this.mOnAlexaStateCallback == null || bArr2.length < 3) {
                    return;
                }
                BLEFastconHelper.this.mOnAlexaStateCallback.onCallback(i2, bArr2[2] & 255);
            }
        }, new OnDevScanCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.5
            @Override // cn.com.broadlink.blelight.interfaces.OnDevScanCallback
            public void onCallback(BLEDeviceInfo bLEDeviceInfo) {
                if (bLEDeviceInfo != null) {
                    BLEFastconHelper.this.mRssiCache.put(Integer.valueOf(bLEDeviceInfo.addr), Integer.valueOf(i));
                }
                if (BLEFastconHelper.this.mOnDevScanCallback != null) {
                    bLEDeviceInfo.name = BLEFastconHelper.genDefaultName(bLEDeviceInfo);
                    BLEFastconHelper.this.mOnDevScanCallback.onCallback(bLEDeviceInfo);
                }
            }
        }, isFullFrameCallbackFlag() ? new OnReceiveFullFrameCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.6
            @Override // cn.com.broadlink.blelight.interfaces.OnReceiveFullFrameCallback
            public void onCallback(byte[] bArr2) {
                ELogUtils.i(BLEFastconHelper.TAG, "onReceiveFullFrameNew: " + EConvertUtils.bytes2HexStr(bArr2));
                if (BLEFastconHelper.this.mOnReceiveFullFrameCallback != null) {
                    BLEFastconHelper.this.mOnReceiveFullFrameCallback.onCallback(bArr2);
                }
            }
        } : null, z ? 1 : 0);
    }

    private void parsePassThroughPayload(String str, byte[] bArr, boolean z, final int i) {
        ELogUtils.d(TAG, "onLeScan: " + String.format(Locale.ENGLISH, "[%s] pass through: len: %d, data: %s", str, Integer.valueOf(bArr.length), bytes2HexString(bArr)));
        BLEUtil.parse_ble_broadcast_pass_through(bArr, bArr.length, this.mPhoneKey, new OnPassThroughCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.7
            @Override // cn.com.broadlink.blelight.interfaces.OnPassThroughCallback
            public void onCallback(int i2, byte[] bArr2) {
                BLEFastconHelper.this.mRssiCache.put(Integer.valueOf(i2), Integer.valueOf(i));
                if (BLEFastconHelper.this.mOnPassThroughCallback != null) {
                    BLEFastconHelper.this.mOnPassThroughCallback.onCallback(i2, bArr2);
                }
            }
        }, z ? 1 : 0);
    }

    private void parseOldProtocolPayload(String str, byte[] bArr, boolean z, final int i) {
        ELogUtils.d(TAG, "onLeScan: " + String.format(Locale.ENGLISH, "[%s] old protocol: len: %d, data: %s", str, Integer.valueOf(bArr.length), bytes2HexString(bArr)));
        BLEUtil.parse_ble_broadcast(bArr, bArr.length, this.mPhoneKey, new BLEScanCallback() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.8
            @Override // cn.com.broadlink.blelight.interfaces.BLEScanCallback
            public void onDevCallback(BLEDeviceInfo bLEDeviceInfo) {
                if (bLEDeviceInfo != null) {
                    BLEFastconHelper.this.mRssiCache.put(Integer.valueOf(bLEDeviceInfo.addr), Integer.valueOf(i));
                }
                if (BLEFastconHelper.this.mOnDevScanCallback != null) {
                    bLEDeviceInfo.name = BLEFastconHelper.genDefaultName(bLEDeviceInfo);
                    BLEFastconHelper.this.mOnDevScanCallback.onCallback(bLEDeviceInfo);
                }
            }

            @Override // cn.com.broadlink.blelight.interfaces.BLEScanCallback
            public void onTimerListCallback(BLETimerAllInfo bLETimerAllInfo) {
                ELogUtils.d(BLEFastconHelper.TAG, "onTimerListCallback: " + JSON.toJSONString(bLETimerAllInfo));
                if (BLEFastconHelper.this.mOnTimerCallback != null) {
                    BLEFastconHelper.this.mOnTimerCallback.onCallback(bLETimerAllInfo.addr, bLETimerAllInfo);
                }
            }

            @Override // cn.com.broadlink.blelight.interfaces.BLEScanCallback
            public void onHeartBeat(int i2, int i3, String str2) {
                Log.w(BLEFastconHelper.TAG, "onHeartBeat: " + i2 + ", " + i3 + ", " + str2);
                StringBuilder sb = new StringBuilder("onHeartBeat.mOnHearBeatCallback: ");
                sb.append(BLEFastconHelper.this.mOnHearBeatCallback);
                Log.w(BLEFastconHelper.TAG, sb.toString());
                BLEFastconHelper.this.mRssiCache.put(Integer.valueOf(i2), Integer.valueOf(i));
                if (BLEFastconHelper.this.mOnHearBeatCallback != null) {
                    BLEFastconHelper.this.mOnHearBeatCallback.onCallback(i2, i3, str2);
                }
            }

            @Override // cn.com.broadlink.blelight.interfaces.BLEScanCallback
            public void onDevSign(int i2, String str2) {
                Log.w(BLEFastconHelper.TAG, "onDevSign: " + i2 + ", " + str2);
                BLEDeviceInfo devByAddr = BLEFastconHelper.this.getDevByAddr(i2);
                if (devByAddr != null) {
                    devByAddr.sign = str2;
                }
            }

            @Override // cn.com.broadlink.blelight.interfaces.BLEScanCallback
            public void onDevCtrlFullFrame(int i2, byte[] bArr2) {
                ELogUtils.i(BLEFastconHelper.TAG, "onReceiveFullFrameOld: " + EConvertUtils.bytes2HexStr(bArr2));
                if (BLEFastconHelper.this.mOnReceiveFullFrameCallback != null) {
                    BLEFastconHelper.this.mOnReceiveFullFrameCallback.onCallback(bArr2);
                }
                if (BLEFastconHelper.this.mOnDevCtrlCallback == null || bArr2 == null || bArr2.length <= 5) {
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(GlobalVariable.TYPE, (Object) Integer.valueOf(i2));
                if (i2 == 11) {
                    if (bArr2.length >= 8) {
                        jSONObject.put("sceneId", (Object) Integer.valueOf(((bArr2[5] & 255) << 16) | ((bArr2[6] & 255) << 8) | (bArr2[7] & 255)));
                        BLEFastconHelper.this.mOnDevCtrlCallback.onCallback(jSONObject.toString());
                        return;
                    }
                    return;
                }
                jSONObject.put("fullFrame", (Object) EConvertUtils.bytes2HexStr(bArr2));
                BLEFastconHelper.this.mOnDevCtrlCallback.onCallback(jSONObject.toString());
            }
        }, z ? 1 : 0);
    }

    public void initAddressQueueWithDevList() {
        this.mQueueDevAddress = new LinkedList();
        for (int i = 1; i < 255; i++) {
            if (!isDevAddressUsed(i)) {
                this.mQueueDevAddress.offer(Integer.valueOf(i));
            }
        }
        ELogUtils.v("jyq_add_dev", "init: " + JSON.toJSONString(this.mQueueDevAddress));
    }

    public void initAddressQueueWithDevList4096() {
        this.mQueueDevAddress4096 = new LinkedList();
        for (int i = 1; i < 16; i++) {
            for (int i2 = 1; i2 < 255; i2++) {
                int i3 = (i * 256) + i2;
                if (!isDevAddressUsed(i3)) {
                    this.mQueueDevAddress4096.offer(Integer.valueOf(i3));
                }
            }
        }
    }

    private boolean isDevAddressUsed(int i) {
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo != null && bLEDeviceInfo.addr == i) {
                return true;
            }
        }
        return false;
    }

    public BLEDeviceInfo getDevById(String str) {
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo.did.equals(str)) {
                return bLEDeviceInfo;
            }
        }
        return null;
    }

    public List<BLEDeviceInfo> getDevByGroupId(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            arrayList.addAll(this.mDevList);
            return arrayList;
        }
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo.groupId == i) {
                arrayList.add(bLEDeviceInfo);
            }
        }
        return arrayList;
    }

    public String genSingleLightCommand(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return genSingleLightCommand(z, i, i2, i3, i4, i5, i6, i7, false);
    }

    public String genSingleLightCommand(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2) {
        return genSingleLightCommand(z, i, i2, i3, i4, i5, i6, true, 255, i7, z2);
    }

    public String genSingleLightCommand(boolean z, int i, int i2, int i3, int i4, int i5, int i6, boolean z2, int i7, int i8, boolean z3) {
        return genSingleLightCommand(z, i, i2, i3, i4, i5, i6, z2, i7, i8, z3, true);
    }

    public String genSingleLightCommand(boolean z, int i, int i2, int i3, int i4, int i5, int i6, boolean z2, int i7, int i8, boolean z3, boolean z4) {
        byte[] bArr;
        byte[] bArr2 = new byte[0];
        int i9 = i4 & 255;
        int i10 = i2 & 255;
        int i11 = i3 & 255;
        float f = z4 ? 255.0f / ((i9 + i10) + i11) : 1.0f;
        switch (i8) {
            case 1:
                bArr2 = new byte[]{(byte) ((z ? 128 : 0) + (i & 127)), (byte) (i9 * f), (byte) (i10 * f), (byte) (i11 * f), 0, 0};
                break;
            case 2:
                bArr2 = new byte[]{(byte) ((z ? 128 : 0) + (i & 127)), 0, 0, 0, (byte) i5, (byte) i6};
                break;
            case 3:
                if (z3) {
                    bArr2 = new byte[]{(byte) (z ? i & 127 : 0), 0, 0, 0, 0, 0};
                } else {
                    bArr2 = new byte[]{(byte) (z ? i & 127 : 0)};
                }
                break;
            case 4:
                if (z3) {
                    bArr2 = new byte[]{(byte) (z ? (i & 127) + 128 : 0), 0, 0, 0, 0, 0};
                } else {
                    bArr2 = new byte[]{(byte) (z ? (i & 127) + 128 : 0)};
                }
                break;
            case 5:
                bArr2 = new byte[]{0, 0, 0, 0, -1, -1, (byte) (z2 ? 128 : 0)};
                break;
            case 6:
                bArr2 = new byte[]{0, 0, 0, 0, -1, -1, (byte) ((z2 ? 128 : 0) + (i7 & 127))};
                break;
            case 7:
                bArr = new byte[]{(byte) ((z ? 128 : 0) + (i & 127)), (byte) (i9 * f), (byte) (i10 * f), (byte) (i11 * f), (byte) i5, (byte) i6, (byte) ((z2 ? 128 : 0) + (i7 & 127))};
                bArr2 = bArr;
                break;
            case 8:
                bArr2 = new byte[]{(byte) ((z ? 128 : 0) + (i & 127)), -1, -1, -1, -1, -1, (byte) ((z2 ? 128 : 0) + (i7 & 127))};
                break;
            case 9:
                bArr2 = new byte[]{-1, (byte) (i9 * f), (byte) (i10 * f), (byte) (i11 * f), -1, -1, ByteCompanionObject.MIN_VALUE};
                break;
            case 10:
                bArr = new byte[]{(byte) ((z ? 128 : 0) + (i & 127)), (byte) (i9 * f), (byte) (i10 * f), (byte) (i11 * f), (byte) i5, (byte) i6};
                bArr2 = bArr;
                break;
        }
        ELogUtils.d("jyq_device", String.format(Locale.ENGLISH, "getCmd:%s, info:%s", EConvertUtils.bytes2HexStr(bArr2), toString()));
        return bytes2HexString(bArr2);
    }

    public void startCloseBLETimer() {
        stopCloseBLETimer();
        Timer timer = new Timer();
        this.mCloseBLEReceiveTimer = timer;
        timer.schedule(new TimerTask() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.9
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                BLEFastconHelper.getInstance().stopScanBLEDevices();
                BLEFastconHelper.this.mCloseBLEReceiveTimer = null;
            }
        }, 10000L);
    }

    public void stopCloseBLETimer() {
        Timer timer = this.mCloseBLEReceiveTimer;
        if (timer != null) {
            timer.cancel();
            this.mCloseBLEReceiveTimer = null;
        }
    }

    public boolean startScanBLEDevices() {
        stopCloseBLETimer();
        if (this.mAdapter == null) {
            checkPerm();
        }
        BluetoothAdapter bluetoothAdapter = this.mAdapter;
        if (bluetoothAdapter == null) {
            return false;
        }
        Log.w(TAG, "start LeScanCmd: " + bluetoothAdapter.startLeScan(this.mCallback));
        return true;
    }

    public boolean stopScanBLEDevices() {
        if (DevSignHelper.getDevSignFlag()) {
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            JSONObject localSign = DevSignHelper.getLocalSign();
            if (localSign != null && localSign.containsKey("devs")) {
                JSONObject jSONObject = localSign.getJSONObject("devs");
                for (String str : jSONObject.keySet()) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                    if (jSONObject2.containsKey("code") && jSONObject2.getIntValue("code") == 1) {
                        DevSignHelper.DevSignPayload.DevlistBean devlistBean = new DevSignHelper.DevSignPayload.DevlistBean();
                        devlistBean.did = str;
                        devlistBean.isgateway = jSONObject2.getBoolean("isgateway");
                        devlistBean.sign = jSONObject2.getString("sign");
                        arrayList.add(devlistBean);
                        hashSet.add(str);
                        ELogUtils.d("jyq_sign", "toCheckSignList add from cache: " + JSON.toJSONString(devlistBean));
                    }
                }
            }
            Map<Integer, BLEDeviceInfo> map = this.mPairedDevMap;
            if (map != null && map.values() != null) {
                for (BLEDeviceInfo bLEDeviceInfo : this.mPairedDevMap.values()) {
                    if (!hashSet.contains(bLEDeviceInfo.did) && !TextUtils.isEmpty(bLEDeviceInfo.sign)) {
                        DevSignHelper.DevSignPayload.DevlistBean devlistBean2 = new DevSignHelper.DevSignPayload.DevlistBean();
                        devlistBean2.did = bLEDeviceInfo.did;
                        devlistBean2.isgateway = Boolean.valueOf(isGateway(bLEDeviceInfo));
                        devlistBean2.sign = bLEDeviceInfo.sign;
                        arrayList.add(devlistBean2);
                        ELogUtils.d("jyq_sign", "toCheckSignList add from pair map: " + JSON.toJSONString(devlistBean2));
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                DevSignHelper.getInstance().checkDevSign(arrayList);
            }
        }
        if (this.mAdapter == null) {
            checkPerm();
        }
        BluetoothAdapter bluetoothAdapter = this.mAdapter;
        if (bluetoothAdapter == null) {
            return false;
        }
        bluetoothAdapter.stopLeScan(this.mCallback);
        ELogUtils.d(TAG, "stop LeScan: ");
        return true;
    }

    public boolean sendStartScan() {
        byte[] bArr = new byte[12];
        bArr[0] = 0;
        return sendCommand(0, bArr, null, BLE_CMD_RETRY_CNT, -1, false, false, false, 0);
    }

    public boolean sendDiscRes(BLEDeviceInfo bLEDeviceInfo) {
        byte[] bArr;
        this.mPairedDevMap.put(Integer.valueOf(bLEDeviceInfo.addr), bLEDeviceInfo);
        boolean z = bLEDeviceInfo.addr > 256 || bLEDeviceInfo.type == 43756 || bLEDeviceInfo.high > 2;
        if (z) {
            bArr = new byte[18];
            BLEUtil.package_disc_res2(parseStringToByte(bLEDeviceInfo.did), bLEDeviceInfo.addr, bLEDeviceInfo.groupId, 1, this.mPhoneKey, bArr);
        } else {
            bArr = new byte[12];
            BLEUtil.package_disc_res(parseStringToByte(bLEDeviceInfo.did), bLEDeviceInfo.addr, 1, this.mPhoneKey, bArr);
        }
        return sendCommand(2, bArr, parseStringToByte(bLEDeviceInfo.key), BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, false, false, z, bLEDeviceInfo.addr / 256);
    }

    public boolean controlLightSingle(int i, String str, int i2) {
        byte[] stringToByte = parseStringToByte(str);
        byte[] bArr = new byte[12];
        BLEUtil.package_device_control(i, stringToByte, stringToByte.length, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, i2, true, i > 256, i / 256);
    }

    public boolean controlLightSinglePwr(BLEDeviceInfo bLEDeviceInfo) {
        boolean z = bLEDeviceInfo.addr > 256;
        byte[] bArr = new byte[z ? 18 : 12];
        byte[] bArr2 = CMD_LIGHT_ON;
        BLEUtil.package_device_control(bLEDeviceInfo.addr, bArr2, bArr2.length, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, true, z, bLEDeviceInfo.addr / 256);
    }

    public boolean controlLightGroupPwr(int i, boolean z) {
        byte[] bArr = new byte[12];
        byte[] bArr2 = z ? CMD_GROUP_LIGHT_ON : CMD_LIGHT_OFF;
        BLEUtil.package_broadcast_control(43050, i, bArr2, bArr2.length, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, true, false, 0);
    }

    public boolean controlClearRm(int i) {
        byte[] bArr = new byte[12];
        BLEUtil.package_clear_rm_list(i, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, false, false, i > 256, i / 256);
    }

    public boolean controlLightGroup(int i, int i2, byte[] bArr) {
        return controlLightGroup(i, i2, bArr, 0);
    }

    public boolean controlLightGroup(int i, int i2, byte[] bArr, int i3) {
        return controlLightGroup(i, i2, bArr, i3, true, true);
    }

    public boolean controlLightGroup(int i, int i2, byte[] bArr, int i3, boolean z, boolean z2) {
        return controlLightGroup(i, i2, bArr, i3, z, z2, false);
    }

    public boolean controlLightGroup(int i, int i2, byte[] bArr, int i3, boolean z, boolean z2, boolean z3) {
        byte[] bArr2 = new byte[z3 ? 18 : 12];
        BLEUtil.package_broadcast_control(i, i2, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, i3, z2, z3, 0);
    }

    public boolean controlLightMusic(int i, byte[] bArr, int i2) {
        byte[] bArr2 = new byte[12];
        BLEUtil.package_music_control(i, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, 1, BLE_CMD_SEND_TIME, this.mIsSingleMode, i2, false, false, 0);
    }

    public boolean controlLightMusic(int i, byte[] bArr, int i2, boolean z) {
        byte[] bArr2 = new byte[12];
        BLEUtil.package_music_control(i, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, 1, BLE_CMD_SEND_TIME, z, i2, false, false, 0);
    }

    public boolean controlLightMusic(int i, byte[] bArr, int i2, boolean z, boolean z2) {
        byte[] bArr2 = new byte[12];
        BLEUtil.package_music_control(i, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, 1, BLE_CMD_SEND_TIME, z, i2, false, false, 0, z2);
    }

    public boolean controlLightScene(int i, int i2, byte[] bArr) {
        byte[] bArr2 = new byte[12];
        BLEUtil.package_scene_control(i, i2, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, true, i > 256, i / 256);
    }

    public boolean controlLightGroupScene(int i, int i2, byte[] bArr) {
        byte[] bArr2 = new byte[12];
        BLEUtil.package_group_scene_control(i, i2, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, true, false, 0);
    }

    public boolean controlSetGroupAddr(int i, int i2) {
        byte[] bArr = new byte[12];
        BLEUtil.package_set_group_addr(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, 3, 300, false, false, i > 256, i / 256);
    }

    public boolean controlSetShortAddr(String str, int i) {
        byte[] bArr = new byte[12];
        BLEUtil.package_set_short_addr(EConvertUtils.hexStr2Bytes(str), i, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, false, false, i > 256, i / 256);
    }

    public boolean controlDelayScene(int i, BLEDelaySceneInfo bLEDelaySceneInfo) {
        byte[] bArr = new byte[12];
        BLEUtil.package_delayed_control(i, bLEDelaySceneInfo.time, bLEDelaySceneInfo.start, bLEDelaySceneInfo.interval, bLEDelaySceneInfo.step, bLEDelaySceneInfo.count, bLEDelaySceneInfo.cold, bLEDelaySceneInfo.warm, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, true, i > 256, i / 256);
    }

    public boolean controlGroupDelayScene(int i, BLEDelaySceneInfo bLEDelaySceneInfo) {
        byte[] bArr = new byte[12];
        BLEUtil.package_delayed_group_control(i, bLEDelaySceneInfo.time, bLEDelaySceneInfo.start, bLEDelaySceneInfo.interval, bLEDelaySceneInfo.step, bLEDelaySceneInfo.count, bLEDelaySceneInfo.cold, bLEDelaySceneInfo.warm, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, true, false, 0);
    }

    public boolean controlLightBatch(int[] iArr, int i, int i2, byte[] bArr, int i3) {
        List<HighArrays> listCalcHighArrays = calcHighArrays(iArr, false);
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < listCalcHighArrays.size(); i4++) {
            BatchPayload batchPayloadControlLightBatchSingle = controlLightBatchSingle(listCalcHighArrays.get(i4).addrs, bArr, listCalcHighArrays.get(i4).high);
            if (batchPayloadControlLightBatchSingle != null) {
                arrayList.add(batchPayloadControlLightBatchSingle);
            }
        }
        return sendBatchCommand(arrayList, i, i2, true, i3, true);
    }

    public BatchPayload controlLightBatchSingle(int[] iArr, byte[] bArr, int i) {
        int iCeil = (int) Math.ceil((iArr.length * 1.0f) / 5.0f);
        byte[][] bArr2 = new byte[iCeil][];
        Arrays.sort(iArr);
        int i2 = 0;
        while (i2 < iCeil) {
            byte[] bArr3 = new byte[12];
            int length = i2 < iCeil + (-1) ? 5 : iArr.length - (i2 * 5);
            byte[] bArr4 = new byte[5];
            for (int i3 = 0; i3 < 5; i3++) {
                if (i3 < length) {
                    bArr4[i3] = (byte) iArr[(i2 * 5) + i3];
                }
            }
            BLEUtil.package_batch_control(bArr4, 5, bArr, bArr.length, bArr3);
            bArr2[i2] = bArr3;
            i2++;
        }
        return new BatchPayload(bArr2, i > 0, i);
    }

    public boolean sendBatchCommand(List<BatchPayload> list, int i, int i2, boolean z, int i3, boolean z2) {
        if (i3 > 0) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.mEnableWatchDog;
            boolean z3 = jCurrentTimeMillis > 2000;
            if (this.mThrottleTimer == null || z3) {
                ELogUtils.i("jyq_music", "sendCommand command->" + jCurrentTimeMillis);
                if (!doSendBatchCommand(list, i, i2, z, z2)) {
                    return false;
                }
                if (z3) {
                    this.mEnableWatchDog = System.currentTimeMillis();
                    return true;
                }
                Timer timer = this.mThrottleTimer;
                if (timer != null) {
                    timer.cancel();
                }
                Timer timer2 = new Timer();
                this.mThrottleTimer = timer2;
                timer2.schedule(new TimerTask() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.10
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        BLEFastconHelper.this.mThrottleTimer = null;
                        BLEFastconHelper.this.mEnableWatchDog = System.currentTimeMillis();
                    }
                }, i3);
                return true;
            }
            ELogUtils.w("jyq_music", "sendCommand throttle throw out.");
            return false;
        }
        return doSendBatchCommand(list, i, i2, z, z2);
    }

    public boolean controlLightTimerGet(int i) {
        byte[] bArr = new byte[12];
        BLETimeLcInfo bLETimeLcInfo = new BLETimeLcInfo();
        bLETimeLcInfo.init();
        BLEUtil.package_timer_get(i, bLETimeLcInfo, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, false, i > 256, i / 256);
    }

    public boolean controlLightTimerGet(int i, BLETimeLcInfo bLETimeLcInfo) {
        byte[] bArr = new byte[12];
        BLEUtil.package_timer_get(i, bLETimeLcInfo, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, true, i > 256, i / 256);
    }

    public boolean controlLightTimerSet(int i, BLETimerAllInfo bLETimerAllInfo) {
        byte[] bArr = new byte[12];
        bLETimerAllInfo.lcTime = new BLETimeLcInfo();
        bLETimerAllInfo.lcTime.init();
        BLEUtil.package_timer_set(i, bLETimerAllInfo, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, false, i > 256, i / 256);
    }

    public boolean controlParkWorkTimerGet(int i) {
        byte[] bArr = new byte[18];
        BLEUtil.package_work_timer_get(i % 256, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, true, true, i / 256);
    }

    public boolean controlParkWorkTimerSet(BLEWorkTimerAllInfo bLEWorkTimerAllInfo) {
        byte[] bArr = new byte[18];
        bLEWorkTimerAllInfo.lcTime.init();
        if (BLEUtil.package_work_timer_set(bLEWorkTimerAllInfo, bArr) <= 0) {
            return false;
        }
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, true, false, true, bLEWorkTimerAllInfo.addr / 256);
    }

    public boolean controlExecuteScene(int i) {
        return controlExecuteScene(i, 0);
    }

    public boolean controlExecuteScene(int i, int i2) {
        byte[] bArr = new byte[12];
        BLEUtil.package_room_scene_control(i, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, i2, true, false, 0);
    }

    public boolean controlCreateOrUpdateScene2DevOrPanel(int i, int i2, byte[] bArr) {
        return controlCreateOrUpdateScene2DevOrPanel(i, i2, bArr, 2000);
    }

    public boolean controlCreateOrUpdateScene2DevOrPanel(int i, int i2, byte[] bArr, int i3) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[12];
        BLEUtil.package_room_scene_add(i, i2, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, i3, true, false, i > 256, i / 256);
    }

    public boolean controlDeleteScene2DevOrPanel(int i, int i2) {
        byte[] bArr = new byte[12];
        BLEUtil.package_room_scene_delete(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, i > 256, i / 256);
    }

    public boolean controlUpdateSceneCmd2Panel(int i, int i2, byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[12];
        BLEUtil.package_room_scene_modify_default_command(i, i2, 43050, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, i > 256, i / 256);
    }

    public boolean controlDeleteScene2PanelBtn(int i, int i2, int i3) {
        byte[] bArr = new byte[12];
        BLEUtil.package_panel_scene_delete(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, i > 256, i / 256);
    }

    public boolean controlExceptScene2Dev(int i, int i2) {
        byte[] bArr = new byte[12];
        BLEUtil.package_panel_scene_except(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, i > 256, i / 256);
    }

    public boolean resetSceneCmd2Panel(int i, int i2) {
        byte[] bArr = new byte[12];
        BLEUtil.package_panel_reset_default_command(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, i > 256, i / 256);
    }

    public boolean broadcastAppTimeSync() {
        byte[] bArr = new byte[12];
        BLEUtil.package_sync_time_info(0, BLEAppTimeInfo.createDefault(), bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, false, 0);
    }

    public boolean broadcastAppTimeSync(boolean z) {
        byte[] bArr = new byte[12];
        BLEUtil.package_sync_time_info(0, BLEAppTimeInfo.createDefault(), bArr);
        byte[] bArr2 = this.mPhoneKey;
        return sendCommand(5, bArr, bArr2, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, bArr2 != null, 0, false, false, 0, z);
    }

    public int broadcastTempGroupId(int[] iArr) {
        return broadcastTempGroupId(253, iArr, (short) 0, 150, 3);
    }

    public boolean controlQueryAlexaState(int i) {
        byte[] bArr = new byte[18];
        BLEUtil.package_alexa_query_command(i, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, true, true, i / 256);
    }

    public boolean controlSetAlexaState(int i, int i2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_alexa_set_command(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, true, true, i / 256);
    }

    public boolean controlSetBackLight(int i, int i2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_back_light(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, true, true, i / 256);
    }

    public boolean controlSetSensorRecoverTime(int i, int i2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_sensor_recover_time(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, true, true, i / 256);
    }

    public boolean controlSetCarPartRadarParam(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        byte[] bArr = new byte[18];
        BLEUtil.package_sensor_radar_param(i, i2, i3, i4, i5, i6, i7, i8, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, true, true, i2 / 256);
    }

    public boolean controlSet24gRadarParam(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        byte[] bArr = new byte[18];
        BLEUtil.package_24g_radar_param(i, i2, i3, (i4 >> 16) & 255, i4 & 255, i5, (i6 >> 16) & 255, i6 & 255, i7, (i8 >> 16) & 255, i8 & 255, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, true, true, i2 / 256);
    }

    public boolean control24gWorkTimerGet(int i) {
        byte[] bArr = new byte[18];
        BLEUtil.package_24g_work_timer_get(i % 256, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, false, true, true, i / 256);
    }

    public boolean control24gWorkTimerSet(BLERadar24GAllInfo bLERadar24GAllInfo) {
        byte[] bArr = new byte[18];
        if (BLEUtil.package_24g_work_timer_set(bLERadar24GAllInfo, bArr) <= 0) {
            return false;
        }
        return sendCommand(5, bArr, this.mPhoneKey, 3, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, true, false, true, bLERadar24GAllInfo.addr / 256);
    }

    public boolean passThroughControlWithAddr(int i, String str, boolean z) {
        return passThroughControlWithAddr(i, str, true, z);
    }

    public boolean passThroughControlWithAddr(int i, String str, boolean z, boolean z2) {
        byte[] bArr = new byte[z ? 18 : 12];
        byte[] bArrHexStr2Bytes = EConvertUtils.hexStr2Bytes(str);
        BLEUtil.package_device_control(i, bArrHexStr2Bytes, bArrHexStr2Bytes.length, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, 0, true, z, i / 256);
    }

    public boolean passThrough(String str) {
        return passThrough(str, 0, true, true);
    }

    public boolean passThrough(String str, int i, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str) || str.length() > 36) {
            return false;
        }
        return sendCommand(5, EConvertUtils.hexStr2Bytes(str), this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, 0, true, z, i);
    }

    public boolean configIhg(int i, byte[] bArr, boolean z) {
        byte[] bArr2 = new byte[18];
        BLEUtil.package_device_control(i, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, 0, false, true, -1);
    }

    public boolean controlRoomSceneAddFixGroup(int i, int i2, int i3, byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[18];
        BLEUtil.package_group_scene_add(i, i2, i3, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, true, i / 256);
    }

    public boolean controlRoomSceneDeleteFixGroup(int i, int i2, int i3) {
        byte[] bArr = new byte[18];
        BLEUtil.package_group_scene_delete(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, true, i / 256);
    }

    public boolean controlVirDevBind(int i, int i2, int i3, int i4) {
        byte[] bArr = new byte[18];
        BLEUtil.package_vir_dev_bind(i, i2, i3, i4, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, true, i / 256);
    }

    public boolean controlVirDevUnBind(int i, int i2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_vir_dev_unbind(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, false, true, i / 256);
    }

    public boolean controlYedRadarPassThrough(int i, int i2, byte[] bArr, boolean z) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[18];
        BLEUtil.package_yrd_radar_pass_through(i, i2, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, false, true, i2 / 256);
    }

    public boolean controlForDeviceV2(int i, byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[18];
        BLEUtil.package_device_control_v2(i, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i / 256);
    }

    public boolean controlForGroupV2(int i, int i2, byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[18];
        BLEUtil.package_group_control_v2(i, i2, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, 0);
    }

    public boolean controlLightSetParam(int i, int i2, int i3, byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[18];
        BLEUtil.package_light_param_setting(i, i2, i3, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i3 / 256);
    }

    public boolean controlSetGroupMainDev(int i, int i2, boolean z, boolean z2, boolean z3) {
        byte[] bArr = new byte[18];
        BLEUtil.package_group_main_dev(i, i2, z ? 1 : 0, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z3, z2, true, i / 256);
    }

    public boolean controlSetGatewayUdpParam(int i, int i2, int i3, int i4, int i5, boolean z) {
        byte[] bArr = new byte[18];
        BLEUtil.package_gateway_udp_setting(i, i2, i3, i4, i5, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, false, true, i2 / 256);
    }

    public boolean controlDevHistoryQuery(int i, int i2, boolean z) {
        byte[] bArr = new byte[18];
        BLEUtil.package_device_history_query(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, false, true, i / 256);
    }

    public boolean controlDevHistoryClear(int i, boolean z) {
        byte[] bArr = new byte[18];
        BLEUtil.package_device_history_clear(i, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, false, true, i / 256);
    }

    public boolean controlLightCfgLock(int i, int i2, int i3, boolean z, boolean z2) {
        return controlLightSetParam(SUB_TYPE.CONFIG_LOCK, i, i2, new byte[]{(byte) i3}, z, z2);
    }

    public boolean controlLightCfgLock(int i, int i2, boolean z, boolean z2, boolean z3) {
        return controlLightCfgLock(i, i2, z ? 8 : 0, z2, z3);
    }

    public boolean controlLightPwrCut(int i, int i2, int i3, boolean z, boolean z2) {
        return controlLightSetParam(SUB_TYPE.PWR_CUT, i, i2, new byte[]{(byte) i3}, z, z2);
    }

    public boolean controlLightAtomSwitch(int i, int i2, boolean z, boolean z2, boolean z3) {
        return controlLightSetParam(SUB_TYPE.ATOM_SWITCH, i, i2, new byte[]{z ? (byte) 1 : (byte) 0}, z2, z3);
    }

    public boolean controlLightSlowRise(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        byte[] bArr = {(byte) i4};
        if (i == 0) {
            return controlForDeviceV2(i3, bArr, z, z2);
        }
        if (i != 1) {
            return false;
        }
        return controlForGroupV2(i3, i2, bArr, z, z2);
    }

    public boolean controlRgbcwMode(int i, int i2, int i3, int i4, int i5, int i6, ArrayList<BLEModeRgbcwBean> arrayList, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_device_rgbcw_mode(i, i2, i3, i4, i5, i6, arrayList, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRgbcwModeV2(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, ArrayList<BLEModeRgbcwBean> arrayList, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_device_rgbcw_mode_v2(i, i2, i3, i4, i5, i6, i7, i8, i9, arrayList, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRgbcwTimerSet(int i, int i2, ArrayList<BLERgbcwTimerInfo> arrayList, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rgbcw_timer_set(i, i2, arrayList, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRgbcwTimerGet(int i, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rgbcw_timer_get(i, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i / 256);
    }

    public boolean controlSensorGroupSet(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_sensor_group_set(i, i2, i3, i4, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, 0);
    }

    public boolean controlCarParkGroupForward(int i, int i2, int i3, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_car_park_forward(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public boolean controlCarParkSelfLearnParam(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_car_park_self_learn_param(i, i2, i3, i4, i5, i6, i7, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public boolean controlCarParkRadarParam(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_car_park_radar_param(i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public boolean controlCarParkSelfLearnParamClear(int i, int i2, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_car_park_clear_self_learn_param(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public boolean controlCarParkResetPubKey(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_car_park_reset_to_public_key(i, i2, i3, i4, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public boolean controlCarParkTriggerUpload(byte[] bArr, int i, boolean z, boolean z2) {
        return controlRemoteCmd(bArr, 11, new byte[]{(byte) i}, z, z2);
    }

    public boolean controlRemoteCmd(byte[] bArr, int i, byte[] bArr2, boolean z, boolean z2) {
        byte[] bArr3 = new byte[bArr2.length + 7];
        bArr3[0] = (byte) (((bArr2.length + 6) << 4) | 2);
        bArr3[1] = 0;
        bArr3[2] = 0;
        bArr3[3] = 0;
        bArr3[4] = 0;
        bArr3[5] = 0;
        bArr3[6] = (byte) i;
        System.arraycopy(bArr2, 0, bArr3, 7, bArr2.length);
        return doSendCommandWithSrcAddr(7, bArr3, bArr, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, true, 0, z2 ? 0 : 91, true);
    }

    public boolean controlCarParkHasHuman(byte[] bArr, int i, int i2, boolean z) {
        byte[] bArr2 = new byte[18];
        BLEUtil.package_pass_through_control(0, 1, new byte[]{bArr[0], bArr[1], bArr[2], bArr[3], (byte) (i & 255), (byte) ((i >> 8) & 255), (byte) i2}, 7, bArr2);
        return doSendCommandWithSrcAddr(7, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, true, 0, 170, true);
    }

    public boolean controlResetDev(boolean z) {
        return controlRemoteCmd(new byte[]{-53, 86, 50, 23}, 5, new byte[]{1, 0}, z, true);
    }

    public boolean controlTrigOta(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, boolean z, boolean z2) {
        byte[] bArr2 = new byte[18];
        BLEUtil.package_trig_dev_ota(i, i2, i3, i4, i5, bArr, i6, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, 0);
    }

    public boolean controlPassThroughWithType(int i, byte[] bArr, boolean z, int i2, boolean z2, boolean z3) {
        byte[] bArr2 = new byte[z ? 18 : 12];
        BLEUtil.package_pass_through_control(z ? 1 : 0, i, bArr, bArr.length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z3, z2, z, i2);
    }

    public boolean controlPassThroughWithTypeBatch(int i, byte[][] bArr, int i2, int i3, boolean z, int i4, boolean z2) {
        byte[][] bArr2 = new byte[bArr.length][];
        for (int i5 = 0; i5 < bArr.length; i5++) {
            byte[] bArr3 = bArr[i5];
            byte[] bArr4 = new byte[z ? 18 : 12];
            BLEUtil.package_pass_through_control(z ? 1 : 0, i, bArr3, bArr3.length, bArr4);
            bArr2[i5] = bArr4;
        }
        BatchPayload batchPayload = new BatchPayload(bArr2, z, i4);
        ArrayList arrayList = new ArrayList();
        arrayList.add(batchPayload);
        return doSendBatchCommand(arrayList, i2, i3, true, z2);
    }

    public boolean controlSetAdcSelfCheckParam(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        int i6 = (i5 * 4095) / 3300;
        return controlPassThroughWithType(44, new byte[]{(byte) i, (byte) i2, (byte) i3, (byte) i4, (byte) (i6 & 255), (byte) ((i6 >> 8) & 255)}, true, i2 / 256, z, z2);
    }

    public boolean controlTrigOta(int i, String str, int i2, boolean z, boolean z2) {
        if (str == null || str.split("\\.").length != 5) {
            return false;
        }
        String[] strArrSplit = str.split("\\.");
        int i3 = Integer.parseInt(strArrSplit[0]);
        int i4 = Integer.parseInt(strArrSplit[1]);
        int i5 = Integer.parseInt(strArrSplit[2]);
        int i6 = Integer.parseInt(strArrSplit[3]);
        return controlTrigOta(i, i3, i4, Integer.parseInt(strArrSplit[4]), 0, new byte[]{(byte) (i6 & 255), (byte) ((i6 >> 8) & 255), (byte) ((i6 >> 16) & 255), (byte) ((i6 >> 24) & 255), (byte) (i5 & 255), (byte) ((i5 >> 8) & 255)}, i2, z, z2);
    }

    private static class HighArrays {
        public int[] addrs;
        public int high;

        public HighArrays(int[] iArr, int i) {
            this.addrs = iArr;
            this.high = i;
        }

        public HighArrays() {
            this.addrs = new int[0];
            this.high = 0;
        }
    }

    private static class BatchPayload {
        public int high;
        public boolean isNewProtocol;
        public byte[][] payload;

        public BatchPayload(byte[][] bArr, boolean z, int i) {
            this.payload = bArr;
            this.isNewProtocol = z;
            this.high = i;
        }
    }

    private List<HighArrays> calcHighArrays(int[] iArr, boolean z) {
        ArrayList arrayList = new ArrayList();
        Arrays.sort(iArr);
        HashMap map = new HashMap();
        for (int i : iArr) {
            int i2 = i / 256;
            int i3 = i % 256;
            if (!map.containsKey(Integer.valueOf(i2))) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(Integer.valueOf(i3));
                map.put(Integer.valueOf(i2), arrayList2);
            } else {
                ((List) map.get(Integer.valueOf(i2))).add(Integer.valueOf(i3));
            }
        }
        for (Integer num : map.keySet()) {
            List list = (List) map.get(num);
            int[] iArr2 = new int[list.size()];
            for (int i4 = 0; i4 < list.size(); i4++) {
                iArr2[i4] = ((Integer) list.get(i4)).intValue();
            }
            Arrays.sort(iArr2);
            arrayList.add(new HighArrays(iArr2, num.intValue()));
        }
        ELogUtils.d("jyq_group", String.format(Locale.ENGLISH, "calcHighArrays input: cnt[%d]:  %s", Integer.valueOf(iArr.length), JSON.toJSONString(iArr)));
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            ELogUtils.d("jyq_group", String.format(Locale.ENGLISH, "calcHighArrays output: cnt[%d, %d]: %s", Integer.valueOf(arrayList.size()), Integer.valueOf(i5), JSON.toJSONString(arrayList.get(i5))));
        }
        Collections.sort(arrayList, new Comparator<HighArrays>() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.11
            @Override // java.util.Comparator
            public int compare(HighArrays highArrays, HighArrays highArrays2) {
                return highArrays.high - highArrays2.high;
            }
        });
        return arrayList;
    }

    public boolean queryDeviceStatusWithAddrs(int[] iArr, byte b, boolean z) {
        return queryDeviceStatusWithAddrs(iArr, 300, b, z);
    }

    public boolean queryDeviceStatusWithAddrs(int[] iArr, int i, byte b, boolean z) {
        if (iArr == null) {
            return queryDeviceStatusWithAddrsOnce(null, b, z);
        }
        List<HighArrays> listCalcHighArrays = calcHighArrays(iArr, false);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < listCalcHighArrays.size(); i2++) {
            BatchPayload batchPayloadQueryDeviceStatusWithAddrsSingle = queryDeviceStatusWithAddrsSingle(listCalcHighArrays.get(i2).addrs, b, listCalcHighArrays.get(i2).high);
            if (batchPayloadQueryDeviceStatusWithAddrsSingle != null) {
                arrayList.add(batchPayloadQueryDeviceStatusWithAddrsSingle);
            }
        }
        return sendBatchCommand(arrayList, 1, i, z, 0, true);
    }

    public BatchPayload queryDeviceStatusWithAddrsSingle(int[] iArr, byte b, int i) {
        if (iArr.length > 254) {
            return null;
        }
        Arrays.sort(iArr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ArrayList());
        arrayList.add(new ArrayList());
        arrayList.add(new ArrayList());
        for (int i2 : iArr) {
            int i3 = i2 - iArr[0];
            int i4 = 0;
            while (true) {
                if (i4 < arrayList.size()) {
                    int i5 = i4 + 1;
                    if (i3 < i5 * 104 && i3 >= i4 * 104) {
                        ((List) arrayList.get(i4)).add(Byte.valueOf((byte) (i2 & 255)));
                        break;
                    }
                    i4 = i5;
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            List list = (List) arrayList.get(i6);
            if (!list.isEmpty()) {
                byte[] bArr = new byte[list.size()];
                for (int i7 = 0; i7 < list.size(); i7++) {
                    bArr[i7] = ((Byte) list.get(i7)).byteValue();
                }
                byte[] bArr2 = new byte[18];
                BLEUtil.package_query_dev_state(bArr, b & 255, bArr2);
                arrayList2.add(bArr2);
            }
        }
        byte[][] bArr3 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, arrayList2.size(), 18);
        for (int i8 = 0; i8 < arrayList2.size(); i8++) {
            bArr3[i8] = (byte[]) arrayList2.get(i8);
        }
        return new BatchPayload(bArr3, true, i);
    }

    public boolean queryDeviceStatusWithAddrsOnce(byte[] bArr, byte b, boolean z) {
        byte[] bArr2 = new byte[18];
        BLEUtil.package_query_dev_state(bArr, b & 255, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z, 0, false, true, 0);
    }

    public boolean controlRmApEnable(int i, int i2, int i3, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rmac_ap_enable(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public boolean controlDeleteDev(int i, int i2, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rmac_delete_device(i, i2, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i == 0 ? i2 / 256 : 0);
    }

    public int broadcastTempGroupId(int i, int[] iArr, short s, int i2, int i3) {
        List<HighArrays> listCalcHighArrays = calcHighArrays(iArr, true);
        ArrayList arrayList = new ArrayList();
        if (s == 0) {
            s = (short) System.currentTimeMillis();
        }
        int length = 0;
        for (int i4 = 0; i4 < listCalcHighArrays.size(); i4++) {
            BatchPayload batchPayloadBroadcastTempGroupIdSingle = broadcastTempGroupIdSingle(i, listCalcHighArrays.get(i4).addrs, s, listCalcHighArrays.get(i4).high);
            if (batchPayloadBroadcastTempGroupIdSingle != null) {
                arrayList.add(batchPayloadBroadcastTempGroupIdSingle);
                length += i2 * i3 * batchPayloadBroadcastTempGroupIdSingle.payload.length;
            }
        }
        if (sendBatchCommand(arrayList, i3, i2, true, 0, false)) {
            return length;
        }
        return -1;
    }

    public BatchPayload broadcastTempGroupIdSingle(int i, int[] iArr, short s, int i2) {
        if (iArr.length > 254 || iArr.length == 0) {
            return null;
        }
        Arrays.sort(iArr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ArrayList());
        arrayList.add(new ArrayList());
        arrayList.add(new ArrayList());
        for (int i3 : iArr) {
            int i4 = i3 - iArr[0];
            int i5 = 0;
            while (true) {
                if (i5 < arrayList.size()) {
                    int i6 = i5 + 1;
                    if (i4 < i6 * 104 && i4 >= i5 * 104) {
                        ((List) arrayList.get(i5)).add(Byte.valueOf((byte) (i3 & 255)));
                        break;
                    }
                    i5 = i6;
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i7 = 0; i7 < arrayList.size(); i7++) {
            List list = (List) arrayList.get(i7);
            if (!list.isEmpty()) {
                int size = list.size();
                byte[] bArr = new byte[size];
                for (int i8 = 0; i8 < list.size(); i8++) {
                    bArr[i8] = ((Byte) list.get(i8)).byteValue();
                }
                byte[] bArr2 = new byte[18];
                ELogUtils.d("jyq_group", String.format(Locale.ENGLISH, "Group high[%d], address: %s", Integer.valueOf(i2), Arrays.toString(bArr)));
                BLEUtil.package_batch_temp_group(bArr, size, i, s, bArr2);
                arrayList2.add(bArr2);
            }
        }
        byte[][] bArr3 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, arrayList2.size(), 18);
        for (int i9 = 0; i9 < arrayList2.size(); i9++) {
            bArr3[i9] = (byte[]) arrayList2.get(i9);
        }
        return new BatchPayload(bArr3, true, i2);
    }

    public int broadcastFixedGroupId(int i, int[] iArr, int i2, int i3) {
        List<HighArrays> listCalcHighArrays = calcHighArrays(iArr, true);
        ArrayList arrayList = new ArrayList();
        int length = 0;
        for (int i4 = 0; i4 < listCalcHighArrays.size(); i4++) {
            HighArrays highArrays = listCalcHighArrays.get(i4);
            BatchPayload batchPayloadBroadcastFixedGroupIdSingle = broadcastFixedGroupIdSingle(i, highArrays.addrs, highArrays.high);
            if (batchPayloadBroadcastFixedGroupIdSingle != null) {
                arrayList.add(batchPayloadBroadcastFixedGroupIdSingle);
                length += i2 * i3 * batchPayloadBroadcastFixedGroupIdSingle.payload.length;
            }
        }
        if (sendBatchCommand(arrayList, i3, i2, true, 0, false)) {
            return length;
        }
        return -1;
    }

    public int removeFixGroupWithGroupId(int i, int[] iArr, int[] iArr2, int i2, int i3) {
        HighArrays highArrays;
        List<HighArrays> listCalcHighArrays = calcHighArrays(iArr2, true);
        List<HighArrays> listCalcHighArrays2 = calcHighArrays(iArr, true);
        ArrayList arrayList = new ArrayList();
        int length = 0;
        for (int i4 = 0; i4 < listCalcHighArrays.size(); i4++) {
            HighArrays highArrays2 = listCalcHighArrays.get(i4);
            if (i4 >= listCalcHighArrays2.size()) {
                highArrays = new HighArrays();
            } else {
                highArrays = listCalcHighArrays2.get(i4);
            }
            BatchPayload batchPayloadBroadcastRemoveFixedGroupIdSingle = broadcastRemoveFixedGroupIdSingle(i, highArrays.addrs, highArrays2.addrs, highArrays.high);
            if (batchPayloadBroadcastRemoveFixedGroupIdSingle != null) {
                arrayList.add(batchPayloadBroadcastRemoveFixedGroupIdSingle);
                length += i2 * i3 * batchPayloadBroadcastRemoveFixedGroupIdSingle.payload.length;
            }
        }
        if (sendBatchCommand(arrayList, i3, i2, true, 0, false)) {
            return length;
        }
        return -1;
    }

    public BatchPayload broadcastFixedGroupIdSingle(int i, int[] iArr, int i2) {
        if (iArr == null || iArr.length > 254 || iArr.length == 0) {
            return null;
        }
        Arrays.sort(iArr);
        int i3 = iArr[0];
        int i4 = ((iArr[iArr.length - 1] - i3) / 104) + 1;
        ArrayList arrayList = new ArrayList();
        int[] iArr2 = new int[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            iArr2[i5] = (i5 * 104) + i3;
            arrayList.add(new ArrayList());
        }
        for (int i6 : iArr) {
            int i7 = 0;
            while (true) {
                if (i7 >= i4) {
                    break;
                }
                int i8 = iArr2[i7];
                if (i6 >= i8 && i6 < i8 + 104) {
                    ((List) arrayList.get(i7)).add(Byte.valueOf((byte) (i6 & 255)));
                    break;
                }
                i7++;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i9 = 0; i9 < arrayList.size(); i9++) {
            List list = (List) arrayList.get(i9);
            if (!list.isEmpty()) {
                int size = list.size();
                byte[] bArr = new byte[size];
                for (int i10 = 0; i10 < list.size(); i10++) {
                    bArr[i10] = ((Byte) list.get(i10)).byteValue();
                }
                ELogUtils.d("jyq_group", String.format(Locale.ENGLISH, "Group high[%d], address: %s", Integer.valueOf(i2), EConvertUtils.bytes2InrStr(bArr)));
                byte[] bArr2 = new byte[18];
                BLEUtil.package_fixed_group_create(bArr, iArr2[i9], size, i, bArr2);
                arrayList2.add(bArr2);
            }
        }
        byte[][] bArr3 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, arrayList2.size(), 18);
        for (int i11 = 0; i11 < arrayList2.size(); i11++) {
            bArr3[i11] = (byte[]) arrayList2.get(i11);
        }
        return new BatchPayload(bArr3, true, i2);
    }

    public BatchPayload broadcastRemoveFixedGroupIdSingle(int i, int[] iArr, int[] iArr2, int i2) {
        if (iArr2 == null || iArr2.length > 254 || iArr2.length == 0) {
            return null;
        }
        Arrays.sort(iArr2);
        Arrays.sort(iArr);
        int length = iArr.length;
        byte[] bArr = new byte[length];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            bArr[i3] = (byte) iArr[i3];
        }
        int i4 = iArr2[0];
        int i5 = ((iArr2[iArr2.length - 1] - i4) / 104) + 1;
        ArrayList arrayList = new ArrayList();
        int[] iArr3 = new int[i5];
        for (int i6 = 0; i6 < i5; i6++) {
            iArr3[i6] = (i6 * 104) + i4;
            arrayList.add(new ArrayList());
        }
        for (int i7 : iArr2) {
            int i8 = 0;
            while (true) {
                if (i8 >= i5) {
                    break;
                }
                int i9 = iArr3[i8];
                if (i7 >= i9 && i7 < i9 + 104) {
                    ((List) arrayList.get(i8)).add(Byte.valueOf((byte) (i7 & 255)));
                    break;
                }
                i8++;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i10 = 0; i10 < arrayList.size(); i10++) {
            List list = (List) arrayList.get(i10);
            if (!list.isEmpty()) {
                int size = list.size();
                byte[] bArr2 = new byte[size];
                for (int i11 = 0; i11 < list.size(); i11++) {
                    bArr2[i11] = ((Byte) list.get(i11)).byteValue();
                }
                ELogUtils.d("jyq_group", String.format(Locale.ENGLISH, "Group high[%d], address: %s", Integer.valueOf(i2), EConvertUtils.bytes2InrStr(bArr2)));
                byte[] bArr3 = new byte[18];
                BLEUtil.package_fixed_group_remove(bArr2, size, bArr, length, i, bArr3);
                arrayList2.add(bArr3);
            }
        }
        byte[][] bArr4 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, arrayList2.size(), 18);
        for (int i12 = 0; i12 < arrayList2.size(); i12++) {
            bArr4[i12] = (byte[]) arrayList2.get(i12);
        }
        return new BatchPayload(bArr4, true, i2);
    }

    private boolean isContain(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public void sortDev() {
        try {
            Collections.sort(this.mDevList, new Comparator<BLEDeviceInfo>() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.12
                @Override // java.util.Comparator
                public int compare(BLEDeviceInfo bLEDeviceInfo, BLEDeviceInfo bLEDeviceInfo2) {
                    return bLEDeviceInfo.addr - bLEDeviceInfo2.addr;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int supperPanelBindWithAddr(int i, int i2, int i3, int i4, int i5) {
        return supperPanelBindWithAddr(i, i2, i3, i4, i5, null);
    }

    public int supperPanelBindWithAddr(int i, int i2, int i3, int i4, int i5, String str) {
        int groupKindByDevType = getGroupKindByDevType(i5);
        return doSuperPanelBind(i, i2, groupKindByDevType, genSceneId(i3, groupKindByDevType, i4), EConvertUtils.hexStr2Bytes(str));
    }

    public int supperPanelBindWithKind(int i, int i2, int i3, int i4, int i5, String str) {
        return doSuperPanelBind(i, i2, i5, genSceneId(i3, i5, i4), EConvertUtils.hexStr2Bytes(str));
    }

    public int supperPanelBindWithAddr(int i, int i2, int i3, int i4) {
        return supperPanelBindWithAddr(i, i2, i3, i4, (String) null);
    }

    public int supperPanelBindWithAddr(int i, int i2, int i3, int i4, String str) {
        return doSuperPanelBind(i, i3, i4, genSceneId(i2, i4, 0), EConvertUtils.hexStr2Bytes(str));
    }

    private int doSuperPanelBind(int i, int i2, int i3, int i4) {
        return doSuperPanelBind(i, i2, i3, i4, null);
    }

    private int doSuperPanelBind(int i, int i2, int i3, int i4, byte[] bArr) {
        if (supperPanelBindWithAddr(i, (i2 & 15) + ((i3 << 4) & 240), i4, bArr)) {
            return i4;
        }
        return -1;
    }

    public int supperPanelUnBindWithGroup(int i, int i2, int i3, int i4) {
        return doSuperPanelUnbind(i, i3, i4, genSceneId(i2, i4, 0));
    }

    public int supperPanelUnBindWithDev(int i, int i2, int i3, int i4, int i5) {
        int groupKindByDevType = getGroupKindByDevType(i5);
        return doSuperPanelUnbind(i, i2, groupKindByDevType, genSceneId(i3, groupKindByDevType, i4));
    }

    private int doSuperPanelUnbind(int i, int i2, int i3, int i4) {
        if (controlDeleteScene2PanelBtn(i, i4, (i2 & 15) + ((i3 << 4) & 240))) {
            return i4;
        }
        return -1;
    }

    public boolean supperPanelBindWithAddr(int i, int i2, int i3) {
        return supperPanelBindWithAddr(i, i2, i3, (byte[]) null);
    }

    public boolean supperPanelBindWithAddr(int i, int i2, int i3, byte[] bArr) {
        byte[] bArr2 = new byte[12];
        int length = bArr != null ? bArr.length + 1 : 1;
        byte[] bArr3 = new byte[length];
        bArr3[0] = (byte) (i2 & 255);
        if (bArr != null) {
            int i4 = 0;
            while (i4 < bArr.length) {
                int i5 = i4 + 1;
                bArr3[i5] = bArr[i4];
                i4 = i5;
            }
        }
        BLEUtil.package_super_panel_bind(i % 256, i3, bArr3, length, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, true, 0, false, i > 256, i / 256);
    }

    public synchronized int devRemoveAddr(int i) {
        try {
            if (i > 255) {
                if (this.mQueueDevAddress4096 == null) {
                    initAddressQueueWithDevList4096();
                }
                this.mQueueDevAddress4096.remove(Integer.valueOf(i));
            } else {
                if (this.mQueueDevAddress == null) {
                    initAddressQueueWithDevList();
                }
                this.mQueueDevAddress.remove(Integer.valueOf(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public synchronized int devAppendAddr(boolean z) {
        int iIntValue;
        try {
            if (z) {
                if (this.mQueueDevAddress4096 == null) {
                    initAddressQueueWithDevList4096();
                }
                Integer numPoll = this.mQueueDevAddress4096.poll();
                iIntValue = numPoll != null ? numPoll.intValue() : -1;
                ELogUtils.v("jyq_add_dev_4096", "poll: " + JSON.toJSONString(this.mQueueDevAddress4096));
            } else {
                if (this.mQueueDevAddress == null) {
                    initAddressQueueWithDevList();
                }
                Integer numPoll2 = this.mQueueDevAddress.poll();
                iIntValue = numPoll2 != null ? numPoll2.intValue() : -1;
                ELogUtils.v("jyq_add_dev", "poll: " + JSON.toJSONString(this.mQueueDevAddress));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iIntValue;
    }

    public synchronized int acGatewayAppendAddr(int i, boolean z) {
        int i2 = z ? 16 : 1;
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 1; i4 < 255 - i; i4++) {
                int i5 = (i3 * 256) + i4;
                try {
                    if (isNotUsed(i5, i)) {
                        ELogUtils.v("jyq_add_dev_ac_gateway", "startAddr: " + i5);
                        if (z) {
                            if (this.mQueueDevAddress4096 == null) {
                                initAddressQueueWithDevList4096();
                            }
                            for (int i6 = i5; i6 < i5 + i; i6++) {
                                this.mQueueDevAddress4096.remove(Integer.valueOf(i6));
                            }
                        } else {
                            if (this.mQueueDevAddress == null) {
                                initAddressQueueWithDevList();
                            }
                            for (int i7 = i5; i7 < i5 + i; i7++) {
                                this.mQueueDevAddress.remove(Integer.valueOf(i7));
                            }
                        }
                        ELogUtils.i("jyq_add_dev", "ac: " + JSON.toJSONString(this.mQueueDevAddress));
                        return i5;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    private boolean isNotUsed(int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (isDevAddressUsed(i + i3)) {
                return false;
            }
        }
        return true;
    }

    public synchronized int appendSceneId(int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList<BLERoomSceneInfo> arrayList2 = new ArrayList<>();
        getRoomSceneList(i, arrayList2);
        for (int i2 = 17; i2 <= 255; i2++) {
            if (!isGroupSceneIdUsed(i2, arrayList2)) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        if (arrayList.isEmpty()) {
            return -1;
        }
        int iNextInt = new Random().nextInt(arrayList.size());
        int iIntValue = (65536 * i) + ((Integer) arrayList.get(iNextInt)).intValue();
        ELogUtils.d("jyq_add", String.format(Locale.ENGLISH, "appendSceneId[%d], seq: %d, roomid: %d", Integer.valueOf(iIntValue), Integer.valueOf(iNextInt), Integer.valueOf(i)));
        return iIntValue;
    }

    private boolean isGroupSceneIdUsed(int i, ArrayList<BLERoomSceneInfo> arrayList) {
        for (int i2 : BLE_ROOM_SCENE_DEFAULT) {
            if (i == i2) {
                return true;
            }
        }
        Iterator<BLERoomSceneInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if ((it.next().sceneId & 255) == i) {
                return true;
            }
        }
        return false;
    }

    public boolean addDevice(BLEDeviceInfo bLEDeviceInfo) {
        if (isDevAddressUsed(bLEDeviceInfo.addr)) {
            return false;
        }
        this.mDevList.add(bLEDeviceInfo);
        return true;
    }

    public boolean updateOnlineState(String str, int i) {
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo.did.equalsIgnoreCase(str)) {
                bLEDeviceInfo.onlineState = i;
                return true;
            }
        }
        return false;
    }

    public boolean addRoomScene(BLERoomSceneInfo bLERoomSceneInfo) {
        this.mGroupSceneList.add(bLERoomSceneInfo);
        return true;
    }

    public boolean modifyRoomScene(BLERoomSceneInfo bLERoomSceneInfo) {
        Iterator<BLERoomSceneInfo> it = this.mGroupSceneList.iterator();
        while (it.hasNext()) {
            if (it.next().sceneId == bLERoomSceneInfo.sceneId) {
                try {
                    bLERoomSceneInfo.m482clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeRoomScene(int i) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.mGroupSceneList.size()) {
                i2 = -1;
                break;
            }
            if (this.mGroupSceneList.get(i2).sceneId == i) {
                break;
            }
            i2++;
        }
        if (i2 < 0) {
            return false;
        }
        this.mGroupSceneList.remove(i2);
        return true;
    }

    public void removeAllRoomScenes() {
        this.mGroupSceneList.clear();
    }

    public BLERoomSceneInfo queryRoomSceneInfoBySceneId(int i) {
        for (BLERoomSceneInfo bLERoomSceneInfo : this.mGroupSceneList) {
            if (bLERoomSceneInfo.sceneId == i) {
                return bLERoomSceneInfo;
            }
        }
        return null;
    }

    private boolean getRoomDevList(int i, List<BLEDeviceInfo> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo.groupId == i) {
                list.add(bLEDeviceInfo);
            }
        }
        return true;
    }

    private boolean getRoomSceneList(int i, List<BLERoomSceneInfo> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();
        for (BLERoomSceneInfo bLERoomSceneInfo : this.mGroupSceneList) {
            if (bLERoomSceneInfo.roomId == i) {
                list.add(bLERoomSceneInfo);
            }
        }
        return true;
    }

    public boolean delDevice(String str) {
        int i = 0;
        while (true) {
            if (i >= this.mDevList.size()) {
                i = -1;
                break;
            }
            if (this.mDevList.get(i).did.equals(str)) {
                break;
            }
            i++;
        }
        this.mDevList.size();
        if (i < 0) {
            return false;
        }
        this.mDevList.remove(i);
        return true;
    }

    public boolean updDevInfo(BLEDeviceInfo bLEDeviceInfo) {
        int i = 0;
        while (true) {
            if (i >= this.mDevList.size()) {
                i = -1;
                break;
            }
            if (this.mDevList.get(i).did.equals(bLEDeviceInfo.did)) {
                break;
            }
            i++;
        }
        if (i < 0) {
            return false;
        }
        this.mDevList.set(i, bLEDeviceInfo);
        return true;
    }

    public boolean updDevName(String str, String str2) {
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo.did.equals(str)) {
                bLEDeviceInfo.name = str2;
            }
        }
        return true;
    }

    public boolean updDevGroup(String str, int i) {
        for (BLEDeviceInfo bLEDeviceInfo : this.mDevList) {
            if (bLEDeviceInfo.did.equals(str)) {
                bLEDeviceInfo.groupId = i;
            }
        }
        return true;
    }

    private boolean BT_Adv_init() {
        BluetoothManager bluetoothManager = (BluetoothManager) EAppUtils.getApp().getSystemService("bluetooth");
        this.mManager = bluetoothManager;
        if (bluetoothManager == null) {
            ELogUtils.e(TAG, "BluetoothManager is null");
            return false;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        this.mAdapter = adapter;
        if (adapter == null) {
            ELogUtils.e(TAG, "BluetoothAdapter is null");
            return false;
        }
        BluetoothLeAdvertiser bluetoothLeAdvertiser = adapter.getBluetoothLeAdvertiser();
        this.myAdvertiser = bluetoothLeAdvertiser;
        if (bluetoothLeAdvertiser == null) {
            ELogUtils.e(TAG, "myAdvertiser is null");
            return false;
        }
        this.mAdvertiseSettings = new AdvertiseSettings.Builder().setAdvertiseMode(2).setConnectable(true).setTimeout(0).setTxPowerLevel(3).build();
        return true;
    }

    public void setBleTxMode(int i) {
        Log.d(TAG, "setBleTxMode()---> " + i);
        this.mAdvertiseSettings = new AdvertiseSettings.Builder().setAdvertiseMode(2).setConnectable(true).setTimeout(0).setTxPowerLevel(i).build();
    }

    public void setBleConnectable(boolean z) {
        Log.d(TAG, "setBleConnectable()---> " + z);
        this.mAdvertiseSettings = new AdvertiseSettings.Builder().setAdvertiseMode(2).setConnectable(z).setTimeout(0).setTxPowerLevel(3).build();
    }

    public void setAdvertiseSettings(AdvertiseSettings advertiseSettings) {
        Log.d(TAG, "setMyAdvertiseSettings()---> ");
        this.mAdvertiseSettings = advertiseSettings;
    }

    public boolean checkPerm() {
        if (this.mIsGatewayRemoteDebugMode && !this.mIsGatewayRemoteDebugModeSendBLE) {
            return true;
        }
        if (checkPermBeforeSendCmd()) {
            return checkLocationEnable();
        }
        return BT_Adv_init();
    }

    private boolean checkLocationEnable() {
        if (!isLocServiceEnable(EAppUtils.getApp())) {
            showPermAlert(R.string.alert_location_disabled, new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            return false;
        }
        return checkBluetoothState();
    }

    private boolean checkBluetoothState() {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            showPermAlert(R.string.alert_ble_disabled, new Intent("android.settings.BLUETOOTH_SETTINGS"));
            return false;
        }
        BT_Adv_init();
        if (isLocPerm()) {
            return true;
        }
        showPermAlert(R.string.alert_perm_location, getPermSettingIntent());
        return false;
    }

    private boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager != null) {
            return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
        }
        return false;
    }

    private boolean isLocPerm() {
        return EPermissionUtils.isGranted("android.permission.ACCESS_COARSE_LOCATION");
    }

    public void dismissPermDialog() {
        AlertDialog alertDialog = this.mPermAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void showPermAlert(final int i, final Intent intent) {
        final Activity topActivity;
        if (this.mIsShowPermDialog && (topActivity = EAppUtils.getTopActivity()) != null) {
            topActivity.runOnUiThread(new Runnable() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.13
                @Override // java.lang.Runnable
                public void run() {
                    if (BLEFastconHelper.this.mPermAlertDialog == null || !BLEFastconHelper.this.mPermAlertDialog.isShowing()) {
                        BLEFastconHelper.this.mPermAlertDialog = EAlertUtils.showSimpleDialog(EAppUtils.getString(i), EAppUtils.getString(intent == null ? R.string.common_sure : R.string.common_go_setting), topActivity.getString(R.string.common_cancel), new DialogInterface.OnClickListener() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.13.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i2) {
                                if (intent != null) {
                                    intent.addFlags(268435456);
                                    EAppUtils.getApp().startActivity(intent);
                                } else {
                                    EAppUtils.exitApp();
                                }
                            }
                        }, (DialogInterface.OnClickListener) null);
                        if (BLEFastconHelper.this.mPermAlertDialog != null) {
                            BLEFastconHelper.this.mPermAlertDialog.setCancelable(false);
                        }
                    }
                }
            });
        }
    }

    public Intent getPermSettingIntent() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", EAppUtils.getAppPackageName(), null));
        return intent;
    }

    public void stopSending() {
        sHandler.removeMessages(0);
        sHandler.removeMessages(2);
        if (this.myAdvertiser != null) {
            doStopSend();
        }
    }

    private void doStopSend() {
        ELogUtils.d(TAG, "doStopSend().stopAdvertising--->");
        AdvertiseCallback advertiseCallback = this.mAdvertiseCallback;
        if (advertiseCallback != null) {
            this.myAdvertiser.stopAdvertising(advertiseCallback);
        }
    }

    public boolean sendCommand(int i, byte[] bArr, byte[] bArr2, int i2, int i3, boolean z, boolean z2, boolean z3, int i4) {
        return sendCommand(i, bArr, bArr2, i2, i3, z, bArr2 != null, 0, z2, z3, i4, true);
    }

    public boolean sendCommand(int i, byte[] bArr, byte[] bArr2, int i2, int i3, boolean z, int i4, boolean z2, boolean z3, int i5) {
        return sendCommand(i, bArr, bArr2, i2, i3, z, bArr2 != null, i4, z2, z3, i5, true);
    }

    public boolean sendCommand(int i, byte[] bArr, byte[] bArr2, int i2, int i3, boolean z, int i4, boolean z2, boolean z3, int i5, boolean z4) {
        return sendCommand(i, bArr, bArr2, i2, i3, z, bArr2 != null, i4, z2, z3, i5, z4);
    }

    public boolean sendCommand(int i, byte[] bArr, byte[] bArr2, int i2, int i3, boolean z, boolean z2, int i4, boolean z3, boolean z4, int i5, boolean z5) {
        if (i4 > 0) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.mEnableWatchDog;
            boolean z6 = jCurrentTimeMillis > 2000;
            if (this.mThrottleTimer == null || z6) {
                ELogUtils.i("jyq_music", "sendCommand command->" + jCurrentTimeMillis);
                if (!doSendCommand(i, bArr, bArr2, i2, i3, z, z2, z3, z4, i5, z5)) {
                    return false;
                }
                if (z6) {
                    this.mEnableWatchDog = System.currentTimeMillis();
                    return true;
                }
                try {
                    Timer timer = this.mThrottleTimer;
                    if (timer != null) {
                        timer.cancel();
                        this.mThrottleTimer = null;
                    }
                    Timer timer2 = new Timer();
                    this.mThrottleTimer = timer2;
                    timer2.schedule(new TimerTask() { // from class: cn.com.broadlink.blelight.helper.BLEFastconHelper.14
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            ELogUtils.d("jyq_music", "sendCommand throttle flag change");
                            BLEFastconHelper.this.mThrottleTimer = null;
                            BLEFastconHelper.this.mEnableWatchDog = System.currentTimeMillis();
                        }
                    }, i4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            ELogUtils.w("jyq_music", "sendCommand throttle throw out->" + jCurrentTimeMillis);
            return false;
        }
        return doSendCommand(i, bArr, bArr2, i2, i3, z, z2, z3, z4, i5, z5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v3, types: [int] */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r17v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r17v14 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r21v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v3, types: [cn.com.broadlink.blelight.helper.BLEFastconHelper$MyHandler] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private boolean doSendBatchCommand(List<BatchPayload> list, int i, int i2, boolean z, boolean z2) {
        int i3;
        int i4;
        ?? r17;
        int i5;
        int i6;
        ?? r10 = 0;
        if (!DevSignHelper.getDevSignFlag()) {
            ELogUtils.e("jyq", "DevSignFlag is false!");
            return false;
        }
        if (list == null) {
            return false;
        }
        int i7 = 2;
        sHandler.removeMessages(2);
        sHandler.removeMessages(0);
        int i8 = 1;
        int i9 = 0;
        int i10 = 1;
        while (i9 < list.size()) {
            BatchPayload batchPayload = list.get(i9);
            byte[][] bArr = batchPayload.payload;
            boolean z3 = batchPayload.isNewProtocol;
            int i11 = batchPayload.high;
            byte[][][] bArr2 = new byte[bArr.length][][];
            int i12 = r10 == true ? 1 : 0;
            while (i12 < bArr.length) {
                int i13 = i12;
                byte[][][] bArr3 = bArr2;
                bArr3[i13 == true ? 1 : 0] = getPayloadWithInnerRetry(5, bArr[i13 == true ? 1 : 0], i11, this.mPhoneKey, z, true, z3);
                i12 = (i13 == true ? 1 : 0) + 1;
                bArr2 = bArr3;
            }
            byte[][][] bArr4 = bArr2;
            int i14 = 3;
            if (this.mIsGatewayRemoteDebugMode && !this.mIsGatewayRemoteDebugModeSendBLE) {
                if (getDefaultGateway() != null) {
                    sHandler.removeMessages(3);
                    for (int i15 = r10 == true ? 1 : 0; i15 < bArr.length; i15++) {
                        sHandler.sendMessageDelayed(sHandler.obtainMessage(3, r10 == true ? 1 : 0, r10 == true ? 1 : 0, bArr4[i15][r10 == true ? 1 : 0]), ((bArr.length * i9) + i15) * 1000);
                    }
                }
                r17 = r10 == true ? 1 : 0;
            } else {
                if (!GatewayRemoteCtrlHelper.getInstance().isNeedRemoteCtrl() || getDefaultGateway() == null) {
                    boolean z4 = r10 == true ? 1 : 0;
                    i3 = i7;
                    i4 = z4 ? 1 : 0;
                    r17 = z4;
                } else {
                    sHandler.removeMessages(3);
                    int i16 = r10 == true ? 1 : 0;
                    i4 = i16;
                    ?? r102 = r10;
                    while (i16 < bArr.length) {
                        sHandler.sendMessageDelayed(sHandler.obtainMessage(3, r102, r102, bArr4[i16][r102]), ((bArr.length * i9) + i16) * 1000);
                        i16++;
                        i4 = i8;
                        r102 = r102 == true ? 1 : 0;
                        i7 = i7;
                    }
                    r17 = r102;
                    i3 = i7;
                }
                if (checkPermBeforeSendCmd()) {
                    if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                        if (i4 == 0) {
                            showPermAlert(R.string.alert_ble_disabled, new Intent("android.settings.BLUETOOTH_SETTINGS"));
                        }
                        return r17;
                    }
                    if (!isLocPerm()) {
                        if (i4 == 0) {
                            showPermAlert(R.string.alert_perm_location, getPermSettingIntent());
                        }
                        return r17;
                    }
                    if (!isLocServiceEnable(EAppUtils.getApp())) {
                        showPermAlert(R.string.alert_location_disabled, new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                        return r17;
                    }
                }
                int i17 = r17 == true ? 1 : 0;
                while (i17 < bArr.length) {
                    int i18 = r17 == true ? 1 : 0;
                    while (i18 < i) {
                        int i19 = i10 * i2;
                        i10++;
                        if (i9 == list.size() - i8 && i17 == bArr.length - i8 && i18 == i - 1) {
                            Locale locale = Locale.ENGLISH;
                            Integer numValueOf = Integer.valueOf(i9);
                            Integer numValueOf2 = Integer.valueOf(i17);
                            Integer numValueOf3 = Integer.valueOf(i18);
                            i5 = i8;
                            Object[] objArr = new Object[i14];
                            objArr[r17 == true ? 1 : 0] = numValueOf;
                            objArr[i5] = numValueOf2;
                            objArr[i3] = numValueOf3;
                            ELogUtils.w("jyq_send_batch", String.format(locale, "lastFrame reached k[%d], i[%d], j[%d]", objArr));
                            i6 = i2;
                        } else {
                            i5 = i8;
                            i6 = r17 == true ? 1 : 0;
                        }
                        int i20 = i17;
                        Message messageObtainMessage = sHandler.obtainMessage(i3, i6, ((z2 ? 1 : 0) << 1) + (z3 ? 1 : 0) + ((i11 > 0 ? i5 : r17 == true ? 1 : 0) << 2), bArr4[i17][i5]);
                        Locale locale2 = Locale.ENGLISH;
                        int i21 = i5;
                        Object[] objArr2 = new Object[i21];
                        objArr2[r17 == true ? 1 : 0] = Integer.valueOf(i19);
                        ELogUtils.w("jyq_send_batch", String.format(locale2, "message delay [%d]", objArr2));
                        sHandler.sendMessageDelayed(messageObtainMessage, i19);
                        this.mSendCnt += i21;
                        i18++;
                        i17 = i20 == true ? 1 : 0;
                        i14 = 3;
                        i8 = 1;
                        i3 = 2;
                    }
                    i17 = (i17 == true ? 1 : 0) + 1;
                    i14 = 3;
                    i8 = 1;
                    i3 = 2;
                }
            }
            i9++;
            r10 = r17;
            i7 = 2;
            i8 = 1;
        }
        return i8;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean doSendCommand(int i, byte[] bArr, byte[] bArr2, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, int i4, boolean z5) {
        byte b;
        BLEDeviceInfo defaultGateway;
        if (!DevSignHelper.getDevSignFlag()) {
            ELogUtils.e("jyq", "DevSignFlag is false!");
            return false;
        }
        sHandler.removeMessages(2);
        sHandler.removeMessages(0);
        byte[][] payloadWithInnerRetry = getPayloadWithInnerRetry(i, bArr, Math.max(i4, 0), bArr2, z, z2, z4);
        if (this.mIsGatewayRemoteDebugMode && !this.mIsGatewayRemoteDebugModeSendBLE) {
            BLEDeviceInfo defaultGateway2 = getDefaultGateway();
            if (defaultGateway2 != null) {
                GatewayRemoteCtrlHelper.getInstance().controlRequestForce(defaultGateway2, payloadWithInnerRetry[0]);
            }
            return true;
        }
        if (!z3 || (defaultGateway = getDefaultGateway()) == null) {
            b = false;
        } else {
            GatewayRemoteCtrlHelper.getInstance().controlRequest(defaultGateway, payloadWithInnerRetry[0]);
            b = true;
        }
        if (checkPermBeforeSendCmd() && z5) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                if (b == false) {
                    showPermAlert(R.string.alert_ble_disabled, new Intent("android.settings.BLUETOOTH_SETTINGS"));
                }
                return false;
            }
            if (!isLocPerm()) {
                if (b == false) {
                    showPermAlert(R.string.alert_perm_location, getPermSettingIntent());
                }
                return false;
            }
            if (!isLocServiceEnable(EAppUtils.getApp())) {
                showPermAlert(R.string.alert_location_disabled, new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                return false;
            }
        }
        int i5 = 1;
        while (i5 < i2 + 1) {
            sHandler.sendMessageDelayed(sHandler.obtainMessage(2, i5 == i2 ? i3 : 0, (z4 ? 1 : 0) + ((z3 ? 1 : 0) << 1) + ((i4 > 0 ? 1 : 0) << 2), payloadWithInnerRetry[1]), (i5 - 1) * i3);
            i5++;
        }
        this.mSendCnt++;
        return true;
    }

    private boolean doSendCommandWithSrcAddr(int i, byte[] bArr, byte[] bArr2, int i2, int i3, boolean z, boolean z2, int i4, int i5, boolean z3) {
        if (!DevSignHelper.getDevSignFlag()) {
            ELogUtils.e("jyq", "DevSignFlag is false!");
            return false;
        }
        sHandler.removeMessages(2);
        sHandler.removeMessages(0);
        byte[] payloadWithSrcAddr = getPayloadWithSrcAddr(i, bArr, Math.max(i4, 0), bArr2, z, i5, z2, false);
        int i6 = 1;
        while (i6 < i2 + 1) {
            sHandler.sendMessageDelayed(sHandler.obtainMessage(2, i6 == i2 ? i3 : 0, (z2 ? 1 : 0) + ((i4 > 0 ? 1 : 0) << 2) + ((z3 ? 1 : 0) << 3), payloadWithSrcAddr), ((long) (i6 - 1)) * ((long) i3));
            i6++;
        }
        this.mSendCnt++;
        return true;
    }

    public boolean sendFullFrame(byte[] bArr, byte[] bArr2, boolean z, boolean z2) {
        if (checkBLEEnable(false)) {
            return false;
        }
        byte[] bArr3 = new byte[z ? 22 : 16];
        if (z) {
            byte[] bArr4 = new byte[18];
            for (int i = 0; i < bArr2.length; i++) {
                bArr4[i] = bArr2[i];
            }
            BLEUtil.package_ble_fastcon_body_with_header(bArr, bArr4, 18, bArr3, this.mPhoneKey);
        } else {
            BLEUtil.package_ble_fastcon_body_with_header(bArr, bArr2, bArr2.length, bArr3, this.mPhoneKey);
        }
        sHandler.sendMessage(sHandler.obtainMessage(2, PathInterpolatorCompat.MAX_NUM_POINTS, (z ? 1 : 0) + ((z2 ? 1 : 0) << 2), bArr3));
        return true;
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            super.handleMessage(message);
            int i = message.what;
            if (i == 0) {
                BLEFastconHelper.this.stopSending();
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                byte[] bArr = (byte[]) message.obj;
                BLEDeviceInfo defaultGateway = BLEFastconHelper.this.getDefaultGateway();
                if (defaultGateway != null) {
                    GatewayRemoteCtrlHelper.getInstance().controlRequest(defaultGateway, bArr);
                    return;
                }
                return;
            }
            byte[] bArr2 = (byte[]) message.obj;
            int i2 = message.arg2;
            boolean z2 = false;
            boolean z3 = ((i2 >> 3) & 1) == 1;
            boolean z4 = ((i2 >> 2) & 1) == 1;
            if (((i2 >> 1) & 1) == 1) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
            if ((i2 & 1) == 1) {
                z = true;
            }
            BLEFastconHelper.this.doSendCommand(bArr2, message.arg1, z2, z, z4, !z3);
        }
    }

    private byte[][] getPayloadWithInnerRetry(int i, byte[] bArr, int i2, byte[] bArr2, boolean z, boolean z2, boolean z3) {
        return getPayloadWithInnerRetry(i, bArr, i2, bArr2, z, z2, z3, false);
    }

    private byte[][] getPayloadWithInnerRetry(int i, byte[] bArr, int i2, byte[] bArr2, boolean z, boolean z2, boolean z3, boolean z4) {
        byte[] bArr3;
        byte[] bArr4;
        byte[] bArr5;
        byte[] bArr6;
        Log.i(TAG, String.format(Locale.ENGLISH, "getPayloadWithInnerRetry---> payload:%s,  key: %s", bytes2HexString(bArr), bytes2HexString(bArr2)));
        int i3 = sSendSeq;
        if (!this.mUseFixedSeq) {
            if (this.mSendCnt < 5 && i2 > 1) {
                i3 = (i3 + 10) % 255;
            } else {
                int i4 = i3 + 1;
                sSendSeq = i4;
                if (i4 == 0 || i4 == 256) {
                    sSendSeq = 1;
                }
                i3 = sSendSeq;
            }
        }
        int i5 = i3;
        byte[] bArr7 = bArr == null ? new byte[0] : bArr;
        Log.i(TAG, String.format(Locale.ENGLISH, "getPayloadWithInnerRetry---> mSendCnt:%d,  sSendSeq:%d,  seq:%d", Integer.valueOf(this.mSendCnt), Integer.valueOf(sSendSeq), Integer.valueOf(i5)));
        int forwardFlag = getForwardFlag(z);
        if (z4) {
            byte[] bArr8 = new byte[bArr7.length + 4];
            bArr6 = new byte[bArr7.length + 4];
            int i6 = i2 % 255;
            BLEUtil.package_ble_fastcon_body_nor_encryp(i, i6, i5, z2 ? bArr2[3] : (byte) 255, forwardFlag, bArr7, bArr7.length, bArr8);
            BLEUtil.package_ble_fastcon_body(i, i6, i5, z2 ? bArr2[3] : (byte) 255, forwardFlag, bArr7, bArr7.length, bArr6, bArr2);
            bArr5 = bArr8;
        } else {
            byte[] bArr9 = new byte[z3 ? 22 : 16];
            byte[] bArr10 = new byte[z3 ? 22 : 16];
            if (z3) {
                byte[] bArr11 = new byte[18];
                for (int i7 = 0; i7 < bArr7.length; i7++) {
                    bArr11[i7] = bArr7[i7];
                }
                int i8 = i2 % 255;
                BLEUtil.package_ble_fastcon_body_nor_encryp(i, i8, i5, z2 ? bArr2[3] : (byte) 255, forwardFlag, bArr11, 18, bArr9);
                bArr3 = bArr9;
                bArr4 = bArr10;
                BLEUtil.package_ble_fastcon_body(i, i8, i5, z2 ? bArr2[3] : (byte) 255, forwardFlag, bArr11, 18, bArr4, bArr2);
            } else {
                bArr3 = bArr9;
                int i9 = i2 % 255;
                BLEUtil.package_ble_fastcon_body_nor_encryp(i, i9, i5, z2 ? bArr2[3] : (byte) 255, forwardFlag, bArr7, bArr7.length, bArr3);
                bArr4 = bArr10;
                BLEUtil.package_ble_fastcon_body(i, i9, i5, z2 ? bArr2[3] : (byte) 255, forwardFlag, bArr7, bArr7.length, bArr4, bArr2);
            }
            bArr5 = bArr3;
            bArr6 = bArr4;
        }
        return new byte[][]{bArr5, bArr6};
    }

    private int getForwardFlag(boolean z) {
        int globalForwardFlag = getGlobalForwardFlag();
        return globalForwardFlag != 0 ? (globalForwardFlag == 1 || globalForwardFlag != 2) ? 0 : 1 : z ? 1 : 0;
    }

    private byte[] getPayloadWithSrcAddr(int i, byte[] bArr, int i2, byte[] bArr2, boolean z, int i3, boolean z2, boolean z3) {
        int i4;
        Log.i(TAG, String.format(Locale.ENGLISH, "getPayloadWithSrcAddr---> payload:%s,  key: %s", bytes2HexString(bArr), bytes2HexString(bArr2)));
        if (this.mSendCnt < 5 && i2 > 1) {
            i4 = (sSendSeq + 10) % 255;
        } else {
            int i5 = sSendSeq + 1;
            sSendSeq = i5;
            if (i5 == 0 || i5 == 256) {
                sSendSeq = 1;
            }
            i4 = sSendSeq;
        }
        int i6 = i4;
        byte[] bArr3 = bArr == null ? new byte[0] : bArr;
        Log.i(TAG, String.format(Locale.ENGLISH, "getPayloadWithInnerRetry---> mSendCnt:%d,  sSendSeq:%d,  seq:%d", Integer.valueOf(this.mSendCnt), Integer.valueOf(sSendSeq), Integer.valueOf(i6)));
        int forwardFlag = getForwardFlag(z);
        if (z3) {
            byte[] bArr4 = new byte[bArr3.length + 4];
            BLEUtil.package_ble_fastcon_body(i, i2 % 255, i6, i3, forwardFlag, bArr3, bArr3.length, bArr4, bArr2);
            return bArr4;
        }
        byte[] bArr5 = new byte[z2 ? 22 : 16];
        if (z2) {
            byte[] bArr6 = new byte[18];
            for (int i7 = 0; i7 < bArr3.length; i7++) {
                bArr6[i7] = bArr3[i7];
            }
            BLEUtil.package_ble_fastcon_body(i, i2 % 255, i6, i3, forwardFlag, bArr6, 18, bArr5, bArr2);
            return bArr5;
        }
        BLEUtil.package_ble_fastcon_body(i, i2 % 255, i6, i3, forwardFlag, bArr3, bArr3.length, bArr5, bArr2);
        return bArr5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSendCommand(byte[] bArr, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        byte[] bArr2;
        if (this.myAdvertiser != null) {
            doStopSend();
        }
        sHandler.removeMessages(0);
        if (bArr == null) {
            bArr = new byte[0];
        }
        if (z2) {
            bArr2 = new byte[bArr.length + 2];
            BLEUtil.bl_get_ble_payload(bArr, bArr.length, bArr2, z3, z4);
        } else {
            byte[] bArr3 = BLE_FASTCON_ADDRESS;
            byte[] bArr4 = new byte[bArr3.length + bArr.length + 5];
            BLEUtil.get_rf_payload(bArr3, bArr3.length, bArr, bArr.length, bArr4);
            bArr2 = bArr4;
        }
        if (this.myAdvertiser == null) {
            if (checkPermBeforeSendCmd()) {
                if (!checkPerm()) {
                    return;
                }
            } else {
                BT_Adv_init();
            }
        }
        if (this.myAdvertiser == null) {
            showPermAlert(R.string.alert_perm_location, getPermSettingIntent());
            ELogUtils.e(TAG, "myAdvertiser is null");
            return;
        }
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            ELogUtils.w(TAG, "BT is disabled, send task ignored.");
            return;
        }
        Log.i(TAG, String.format(Locale.ENGLISH, "send---> payload:%s, calculatedPayload:%s, len:%d", EConvertUtils.bytes2HexStr(bArr), EConvertUtils.bytes2HexStr(bArr2), Integer.valueOf(bArr2.length)));
        AdvertiseSettings advertiseSettings = this.mAdvertiseSettings;
        if (advertiseSettings != null && !advertiseSettings.isConnectable()) {
            byte[] bArr5 = new byte[bArr2.length + 3];
            bArr5[0] = 0;
            bArr5[1] = 0;
            bArr5[2] = 0;
            System.arraycopy(bArr2, 0, bArr5, 3, bArr2.length);
            this.mAdvertiseData = new AdvertiseData.Builder().addManufacturerData(65520, bArr5).build();
        } else {
            this.mAdvertiseData = new AdvertiseData.Builder().addManufacturerData(65520, bArr2).build();
        }
        try {
            this.myAdvertiser.startAdvertising(this.mAdvertiseSettings, this.mAdvertiseData, this.mAdvertiseCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ELogUtils.i(String.format(Locale.ENGLISH, "send time[%d]", Integer.valueOf(i)));
        if (i > 0) {
            sHandler.sendEmptyMessageDelayed(0, i);
        }
    }

    private boolean checkBLEEnable(boolean z) {
        if ((this.mIsGatewayRemoteDebugMode && !this.mIsGatewayRemoteDebugModeSendBLE) || BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            return false;
        }
        if (z && getDefaultGateway() != null) {
            return true;
        }
        showPermAlert(R.string.alert_ble_disabled, new Intent("android.settings.BLUETOOTH_SETTINGS"));
        return true;
    }

    public static byte[] parseStringToByte(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i3), 16);
            i++;
            i2 = i3;
        }
        return bArr;
    }

    public static String bytes2HexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toLowerCase();
    }

    public void onVolumeChange() {
        this.mAvgZ = new float[8];
        this.mFFTSendCnt = new long[8];
    }

    public void initFftParam(int i, boolean z) {
        this.groupMap.clear();
        this.keyList.clear();
        this.mAvgZ = new float[8];
        this.mFFTSendCnt = new long[8];
        this.mIsSingleMode = z;
        if (z) {
            ELogUtils.w(TAG, "single mode");
            this.keyList.add(1);
            return;
        }
        List<BLEDeviceInfo> arrayList = new ArrayList<>();
        if (i == 0) {
            arrayList = this.mDevList;
        } else {
            getRoomDevList(i, arrayList);
        }
        for (BLEDeviceInfo bLEDeviceInfo : arrayList) {
            this.groupMap.put(Integer.valueOf(Math.max(0, bLEDeviceInfo.addr - 1) % 8), bLEDeviceInfo);
        }
        Iterator<Integer> it = this.groupMap.keySet().iterator();
        while (it.hasNext()) {
            this.keyList.add(it.next());
        }
        Collections.sort(this.keyList);
        ELogUtils.w(TAG, "fft room:" + JSON.toJSONString(this.keyList));
    }

    public void initFftParam(List<Integer> list, boolean z) {
        this.groupMap.clear();
        this.keyList.clear();
        this.mAvgZ = new float[8];
        this.mFFTSendCnt = new long[8];
        this.mIsSingleMode = z;
        if (z || list == null) {
            ELogUtils.w(TAG, "single mode");
            this.keyList.add(1);
            return;
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            this.groupMap.put(Integer.valueOf(Math.max(0, it.next().intValue() - 1) % 8), null);
        }
        Iterator<Integer> it2 = this.groupMap.keySet().iterator();
        while (it2.hasNext()) {
            this.keyList.add(it2.next());
        }
        Collections.sort(this.keyList);
        ELogUtils.w(TAG, "fft fixed group: " + JSON.toJSONString(this.keyList));
    }

    public int getFFTSize() {
        return this.keyList.size();
    }

    public void parseDataFromFft(byte[] bArr, byte[] bArr2) {
        int size = this.keyList.size();
        if (size == 0) {
            return;
        }
        int length = (bArr.length / 2) + 1;
        float[] fArr = new float[length];
        fArr[0] = Math.abs((int) bArr[0]);
        fArr[bArr.length / 2] = Math.abs((int) bArr[1]);
        int i = 1;
        int i2 = 1;
        while (i < bArr.length - 1) {
            fArr[i2] = (float) Math.hypot(bArr[i], bArr[i + 1]);
            i += 2;
            i2++;
        }
        float f = (length * 1.0f) / size;
        int i3 = 0;
        for (Integer num : this.keyList) {
            int i4 = (int) (i3 * f);
            i3++;
            float fAve = ave(Arrays.copyOfRange(fArr, i4, (int) (i3 * f)));
            if (fAve != 0.0f) {
                fAve = Math.abs(fAve - 1.0f);
            }
            int i5 = fAve <= this.mAvgZ[num.intValue()] * 0.5f ? 0 : fAve < this.mAvgZ[num.intValue()] ? 15 : fAve < this.mAvgZ[num.intValue()] * 1.5f ? 30 : 127;
            if (fAve > 0.0f) {
                this.mAvgZ[num.intValue()] = ((this.mAvgZ[num.intValue()] * this.mFFTSendCnt[num.intValue()]) + fAve) / (this.mFFTSendCnt[num.intValue()] + 1);
                long[] jArr = this.mFFTSendCnt;
                int iIntValue = num.intValue();
                jArr[iIntValue] = jArr[iIntValue] + 1;
            }
            bArr2[num.intValue()] = (byte) i5;
        }
        if (this.mIsSingleMode) {
            Arrays.fill(bArr2, bArr2[1]);
        }
        ELogUtils.i(TAG, "fft data--->: " + Arrays.toString(bArr2));
    }

    private float ave(float[] fArr) {
        float f = 0.0f;
        for (float f2 : fArr) {
            f += f2;
        }
        return f / fArr.length;
    }

    public byte[] getMusicColorCmd(int i, boolean z) {
        int[] iArr = {16711680, 16776960, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 65535, 255, 16711935};
        int i2 = this.index;
        int i3 = iArr[i2 % 6];
        if (i2 >= 6) {
            this.index = 0;
        } else {
            this.index = i2 + 1;
        }
        return new byte[]{(byte) ((z ? 128 : 0) + i), (byte) Color.blue(i3), (byte) Color.red(i3), (byte) Color.green(i3), 0, 0};
    }

    public static String genDefaultName(BLEDeviceInfo bLEDeviceInfo) {
        if (bLEDeviceInfo == null) {
            return null;
        }
        return getTypeName(bLEDeviceInfo.type) + " " + bLEDeviceInfo.did.substring(bLEDeviceInfo.did.length() - 4);
    }

    public static String getTypeName(int i) {
        Activity topActivity = EAppUtils.getTopActivity();
        if (topActivity == null) {
            return String.format(Locale.ENGLISH, "dev[%d]", Integer.valueOf(i));
        }
        switch (i) {
            case BLE_DEV_TYPE_GATEWAY_IHG /* 10058 */:
            case BLE_DEV_TYPE_GATEWAY /* 43500 */:
            case BLE_DEV_TYPE_GATEWAY_LAN /* 44602 */:
            case BLE_DEV_TYPE_GATEWAY_1 /* 45106 */:
                return topActivity.getString(R.string.device_type_name_gateway);
            case BLE_DEV_TYPE_GATEWAY_OEM_RMAC /* 21025 */:
            case BLE_DEV_TYPE_GATEWAY_OEM_BLACK_BEAN /* 21028 */:
            case BLE_DEV_TYPE_GATEWAY_RMAC /* 44939 */:
                return topActivity.getString(R.string.device_type_rmac);
            case 43049:
            case BLE_DEV_TYPE_LIGHT_W_24G_QLM /* 44060 */:
            case 44601:
            case BLE_DEV_TYPE_LIGHT_PWR_SELF_LEARN /* 44876 */:
            case BLE_DEV_TYPE_LIGHT_PWR_GREE_1 /* 45543 */:
            case BLE_DEV_TYPE_LIGHT_PWR_GREE_2 /* 45545 */:
            case BLE_DEV_TYPE_LIGHT_PWR_GREE_3 /* 45547 */:
            case BLE_DEV_TYPE_LIGHT_PWR_GREE_4 /* 45549 */:
            case BLE_DEV_TYPE_LIGHT_PWR_GREE_5 /* 45551 */:
            case BLE_DEV_TYPE_LIGHT_PWR_SELF_LEARN_ADC /* 45559 */:
            case BLE_DEV_TYPE_LIGHT_PWR_SELF_LEARN_1 /* 45776 */:
                return topActivity.getString(R.string.device_type_name_smart_light);
            case 43050:
                return topActivity.getString(R.string.device_type_name_rgbcw_light);
            case 43051:
            case BLE_DEV_TYPE_LIGHT_CCT_GREE_2 /* 45544 */:
            case BLE_DEV_TYPE_LIGHT_CCT_GREE_3 /* 45546 */:
            case BLE_DEV_TYPE_LIGHT_CCT_GREE_1 /* 45548 */:
            case BLE_DEV_TYPE_LIGHT_CCT_GREE_4 /* 45550 */:
            case BLE_DEV_TYPE_LIGHT_CCT_GREE_5 /* 45552 */:
            case BLE_DEV_TYPE_LIGHT_CCT_FSL_1 /* 45648 */:
                return topActivity.getString(R.string.device_type_name_cct_light);
            case 43168:
                return topActivity.getString(R.string.device_type_name_rgb_light);
            case 43169:
                return topActivity.getString(R.string.device_type_name_rgbw_light);
            case 43459:
                return topActivity.getString(R.string.device_type_name_panel_six_wifi);
            case BLE_DEV_TYPE_PANEL_6 /* 43461 */:
                return topActivity.getString(R.string.device_type_name_panel_six_line);
            case BLE_DEV_TYPE_RELAY_3_WIRELESS /* 43462 */:
                return topActivity.getString(R.string.device_type_name_panel_three_wifi);
            case BLE_DEV_TYPE_RELAY_3 /* 43463 */:
                return topActivity.getString(R.string.device_type_name_panel_three_line);
            case BLE_DEV_TYPE_PANEL_4_WIRELESS /* 43472 */:
                return topActivity.getString(R.string.device_type_name_panel_four_wifi);
            case BLE_DEV_TYPE_PANEL_4 /* 43473 */:
                return topActivity.getString(R.string.device_type_name_panel_four_line);
            case BLE_DEV_TYPE_RELAY_2 /* 43474 */:
            case BLE_DEV_TYPE_RELAY_2_OEM_JP /* 45746 */:
                return topActivity.getString(R.string.device_type_name_panel_two_wifi);
            case BLE_DEV_TYPE_CURTAIN /* 43499 */:
            case BLE_DEV_TYPE_CURTAIN_OPEN_CLOSE /* 44111 */:
            case BLE_DEV_TYPE_CURTAIN_ROLL /* 44112 */:
            case BLE_DEV_TYPE_CURTAIN_DREAM /* 44113 */:
            case BLE_DEV_TYPE_CURTAIN_WSD_BLIND /* 44724 */:
            case BLE_DEV_TYPE_CURTAIN_WSD_DAY_NIGHT /* 44725 */:
            case BLE_DEV_TYPE_CURTAIN_WSD_DUAL /* 44726 */:
            case 45153:
            case 45610:
                return topActivity.getString(R.string.device_type_name_curtain);
            case BLE_DEV_TYPE_SENSOR_DOOR /* 43505 */:
            case BLE_DEV_TYPE_SENSOR_GAS /* 44880 */:
            case BLE_DEV_TYPE_SENSOR_SMOKE /* 44895 */:
                return topActivity.getString(R.string.device_type_name_sensor);
            case BLE_DEV_TYPE_SENSOR_IR /* 43516 */:
            case BLE_DEV_TYPE_SENSOR_RADAR /* 43808 */:
            case BLE_DEV_TYPE_SENSOR_RADAR_24G /* 44619 */:
            case BLE_DEV_TYPE_SENSOR_RADAR_24G_1 /* 45477 */:
                return topActivity.getString(R.string.device_type_name_sensor_ir);
            case BLE_DEV_TYPE_META_PAD /* 43518 */:
            case BLE_DEV_TYPE_META_PAD_ABILITY /* 44199 */:
            case BLE_DEV_TYPE_META_PAD_SINGLE /* 44211 */:
                return topActivity.getString(R.string.device_type_name_universal);
            case BLE_DEV_TYPE_RELAY_1 /* 43525 */:
            case BLE_DEV_TYPE_RELAY_1_OEM_FSL /* 44624 */:
                return topActivity.getString(R.string.device_type_name_panel_one_wifi);
            case BLE_DEV_TYPE_RELAY_4 /* 43680 */:
                return topActivity.getString(R.string.device_type_name_panel_four_relay);
            case BLE_DEV_TYPE_LIGHT_COMPOSE /* 43709 */:
                return topActivity.getString(R.string.device_type_name_compose_light);
            case BLE_DEV_TYPE_PANEL_8 /* 43733 */:
            case BLE_DEV_TYPE_PANEL_8_2 /* 43734 */:
                return topActivity.getString(R.string.device_type_name_panel_eight_line);
            case BLE_DEV_TYPE_LIGHT_W_CW /* 43745 */:
                return topActivity.getString(R.string.device_type_name_w_cw_light);
            case BLE_DEV_TYPE_LIGHT_BURDEN_CW /* 43754 */:
            case BLE_DEV_TYPE_LIGHT_BURDEN_W /* 43759 */:
                return topActivity.getString(R.string.device_type_name_negative_ions_light);
            case BLE_DEV_TYPE_GATEWAY_AC /* 43756 */:
                return topActivity.getString(R.string.device_type_name_gateway_ac);
            case BLE_DEV_TYPE_SENSOR_WATER /* 43791 */:
            case BLE_DEV_TYPE_SENSOR_RAIN /* 45659 */:
                return topActivity.getString(R.string.device_type_name_sensor);
            case 43919:
                return topActivity.getString(R.string.device_type_name_thermostat);
            case 43951:
                return topActivity.getString(R.string.device_type_name_sensor_wsd);
            case BLE_DEV_TYPE_SMART_PAD_8 /* 43981 */:
            case BLE_DEV_TYPE_GATEWAY_TOIC_PAD /* 45525 */:
                return topActivity.getString(R.string.device_type_name_smart_pad);
            case BLE_DEV_TYPE_TV_BACKLIGHT /* 43990 */:
                return topActivity.getString(R.string.device_type_name_tv_backlight);
            case BLE_DEV_TYPE_CARD_POWER /* 43992 */:
                return topActivity.getString(R.string.device_type_name_card_power);
            case BLE_DEV_TYPE_DRYING_RACK /* 44007 */:
            case BLE_DEV_TYPE_DRYING_RACK_WSD /* 44727 */:
            case BLE_DEV_TYPE_DRYING_RACK_WSD_FULL /* 44728 */:
            case BLE_DEV_TYPE_DRYING_RACK_WSD_DUAL /* 44729 */:
                return topActivity.getString(R.string.device_type_name_drying_rack);
            case BLE_DEV_TYPE_FAN /* 44049 */:
                return topActivity.getString(R.string.device_type_name_fan);
            case BLE_DEV_TYPE_SOCKET_4 /* 44164 */:
            case BLE_DEV_TYPE_SOCKET_5 /* 44165 */:
            case BLE_DEV_TYPE_SOCKET_6 /* 44166 */:
            case BLE_DEV_TYPE_SOCKET_3 /* 44167 */:
            case BLE_DEV_TYPE_SOCKET_2 /* 44180 */:
            case BLE_DEV_TYPE_SOCKET_1 /* 44181 */:
            case BLE_DEV_TYPE_SOCKET_1_OEM_JP /* 45124 */:
            case BLE_DEV_TYPE_SOCKET_3_OEM_JP /* 45485 */:
            case BLE_DEV_TYPE_SOCKET_5_OEM_JP /* 45495 */:
                return topActivity.getString(R.string.device_type_name_socket);
            case BLE_DEV_TYPE_SENSOR_SOS /* 44207 */:
                return topActivity.getString(R.string.device_type_name_sensor_sos);
            case BLE_DEV_TYPE_SENSOR_FOUR_LINE /* 44209 */:
            case BLE_DEV_TYPE_DRYING_PANEL3 /* 45165 */:
                return topActivity.getString(R.string.device_type_name_sensor_four_line);
            case BLE_DEV_TYPE_LIGHT_OEM_HEXAGON /* 44235 */:
                return topActivity.getString(R.string.device_type_name_light_oem_hexagon);
            case BLE_DEV_TYPE_COMMON_MOTOR /* 44243 */:
                return topActivity.getString(R.string.device_type_name_common_motor);
            case BLE_DEV_TYPE_JUMP_ROPE /* 44313 */:
                return topActivity.getString(R.string.device_type_name_jump_rope);
            case BLE_DEV_TYPE_CAMP_LAMP /* 44483 */:
                return topActivity.getString(R.string.device_type_camp_lamp);
            case BLE_DEV_TYPE_LIGHT_CW_RGB /* 44493 */:
                return topActivity.getString(R.string.device_type_name_compose_light);
            case BLE_DEV_TYPE_LIGHT_CW_CW /* 44495 */:
            case BLE_DEV_TYPE_LIGHT_CW_CW_LS /* 44838 */:
                return topActivity.getString(R.string.device_type_name_cct_light);
            case BLE_DEV_TYPE_SENSOR_ILLUMINANCE /* 44571 */:
            case BLE_DEV_TYPE_SENSOR_ILLUMINANCE_LS /* 45652 */:
                return topActivity.getString(R.string.device_type_name_sensor_llumin);
            case BLE_DEV_TYPE_AIR_CLOUD_BUTLER /* 44603 */:
                return topActivity.getString(R.string.device_type_air_cloud_butler);
            case BLE_DEV_TYPE_RELAY_CURTAIN_1 /* 44606 */:
                return topActivity.getString(R.string.device_type_relay_curtain_1);
            case BLE_DEV_TYPE_RELAY_CURTAIN_2 /* 44607 */:
                return topActivity.getString(R.string.device_type_relay_curtain_2);
            case BLE_DEV_TYPE_LIGHT_DAZZLE_NEW /* 44935 */:
                return topActivity.getString(R.string.device_type_dazzle_strip);
            case BLE_DEV_TYPE_DOOR_MAGLEV /* 45035 */:
                return topActivity.getString(R.string.device_type_door_laglev);
            case BLE_DEV_TYPE_DOOR_LOCK /* 45127 */:
                return topActivity.getString(R.string.device_type_door_lock);
            case BLE_DEV_TYPE_TC_1 /* 45399 */:
                return topActivity.getString(R.string.device_type_tc_1);
            case BLE_DEV_TYPE_TC_2 /* 45401 */:
                return topActivity.getString(R.string.device_type_tc_2);
            case BLE_DEV_TYPE_TC_3 /* 45402 */:
                return topActivity.getString(R.string.device_type_tc_3);
            case BLE_DEV_TYPE_ELE_METER /* 45512 */:
                return topActivity.getString(R.string.device_type_door_ele_meter);
            case BLE_DEV_TYPE_BED /* 45522 */:
                return topActivity.getString(R.string.device_type_bed);
            case BLE_DEV_TYPE_SENSOR_SOUND_LIGHT /* 45558 */:
                return topActivity.getString(R.string.device_type_alarm_sound_light);
            case BLE_DEV_TYPE_LIGHT_W_HIGH_V /* 45655 */:
                return topActivity.getString(R.string.device_type_light_w_high_v);
            case BLE_DEV_GARAGE_DOOR /* 45733 */:
                return topActivity.getString(R.string.device_type_garage_door);
            case BLE_DEV_TYPE_META_PAD_KNOB /* 45764 */:
                return topActivity.getString(R.string.device_type_remote_knob);
            case BLE_DEV_GARAGE_DOOR_1 /* 45767 */:
                return topActivity.getString(R.string.device_type_garage_door_1);
            case BLE_DEV_TIMER /* 45798 */:
                return topActivity.getString(R.string.device_type_timer);
            case BLE_DEV_VIDEO_DOOR_LOCK /* 70731 */:
            case BLE_DEV_VIDEO_DOOR_LOCK_MS /* 70759 */:
                return topActivity.getString(R.string.device_type_video_door_lock);
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static boolean isToggleDevType(int i) {
        return isPanel(i) || isSensor(i) || i == 44209;
    }

    public static boolean isNeedWakeUp(BLEDeviceInfo bLEDeviceInfo) {
        if (bLEDeviceInfo == null) {
            return false;
        }
        if (bLEDeviceInfo.type == 43472 || bLEDeviceInfo.type == 43733 || bLEDeviceInfo.type == 43459 || bLEDeviceInfo.type == 43518 || bLEDeviceInfo.type == 45764 || bLEDeviceInfo.type == 44211) {
            return true;
        }
        return !(!isSensor(bLEDeviceInfo.type) || bLEDeviceInfo.type == 44207 || bLEDeviceInfo.type == 43808 || bLEDeviceInfo.type == 44880) || bLEDeviceInfo.supportLowPower();
    }

    public static boolean isRelaySupperPanel(BLEDeviceInfo bLEDeviceInfo) {
        return bLEDeviceInfo != null && isRelayPanel(bLEDeviceInfo.type) && bLEDeviceInfo.supportSupperPanel();
    }

    public static boolean isGateway(BLEDeviceInfo bLEDeviceInfo) {
        if (bLEDeviceInfo == null) {
            return false;
        }
        if (isGateway(bLEDeviceInfo.type) || bLEDeviceInfo.supportGateway()) {
            return true;
        }
        return bLEDeviceInfo.type == 43981 && bLEDeviceInfo.version != null;
    }

    public static CharSequence parseDesc(String str) {
        String string;
        if (!TextUtils.isEmpty(str)) {
            byte[] bArrHexStr2Bytes = EConvertUtils.hexStr2Bytes(str);
            if (bArrHexStr2Bytes.length == 1) {
                if (str.equals("00")) {
                    return String.format(Locale.ENGLISH, "%s%s", EAppUtils.getString(R.string.sdk_common_dev), EAppUtils.getString(R.string.sdk_common_off));
                }
                if ((bArrHexStr2Bytes[0] & 127) == 0) {
                    return String.format(Locale.ENGLISH, "%s%s", EAppUtils.getString(R.string.sdk_common_dev), EAppUtils.getString(R.string.sdk_common_on));
                }
                return String.format(Locale.ENGLISH, "%s%s %s%d%%", EAppUtils.getString(R.string.sdk_common_dev), EAppUtils.getString(R.string.sdk_common_on), EAppUtils.getString(R.string.sdk_common_lightness), Integer.valueOf((int) Math.max(1.0d, Math.ceil((r13 * 100.0f) / 127.0f))));
            }
            if (bArrHexStr2Bytes.length == 6) {
                int iMax = (int) Math.max(1.0d, Math.ceil(((bArrHexStr2Bytes[0] & 127) * 100.0f) / 127.0f));
                byte b = bArrHexStr2Bytes[1];
                if (b == 0 && bArrHexStr2Bytes[2] == 0 && bArrHexStr2Bytes[3] == 0) {
                    int i = bArrHexStr2Bytes[4] & 255;
                    if (i < 127) {
                        string = EAppUtils.getString(R.string.sdk_common_cold_light);
                    } else if (i == 127) {
                        string = EAppUtils.getString(R.string.sdk_common_white_light);
                    } else {
                        string = EAppUtils.getString(R.string.sdk_common_warm_light);
                    }
                    return String.format(Locale.ENGLISH, "%s%s %s%d%% %s", EAppUtils.getString(R.string.sdk_common_dev), EAppUtils.getString(R.string.sdk_common_on), EAppUtils.getString(R.string.sdk_common_lightness), Integer.valueOf(iMax), string);
                }
                return new BLSpanUtils().append(String.format(Locale.ENGLISH, "%s%s %s%d%% %s", EAppUtils.getString(R.string.sdk_common_dev), EAppUtils.getString(R.string.sdk_common_on), EAppUtils.getString(R.string.sdk_common_lightness), Integer.valueOf(iMax), EAppUtils.getString(R.string.sdk_common_color))).append(EAppUtils.getString(R.string.sdk_char_round_point)).setForegroundColor(Color.argb(255, bArrHexStr2Bytes[2] & 255, bArrHexStr2Bytes[3] & 255, b & 255)).create();
            }
            return "";
        }
        return "";
    }

    public static int getGroupKindByDevType(int i) {
        if (isLight(i)) {
            return 1;
        }
        if (isCurtain(i)) {
            return 2;
        }
        if (isRelayPanel(i)) {
            return 3;
        }
        if (isAc(i)) {
            return 4;
        }
        if (isGateway(i)) {
            return 5;
        }
        return isSensor(i) ? 7 : 0;
    }

    public int startGatewayConfigWithAddress(int i, BLEGatewayConfigInfo bLEGatewayConfigInfo, long j, OnGatewayConfigCallback onGatewayConfigCallback) {
        if (this.mIsSending) {
            ELogUtils.w("jyq_gateway", "Config is sending already, you can call stopDeviceConfig() to stop it.");
            return -1;
        }
        if (i <= 0 || bLEGatewayConfigInfo == null || onGatewayConfigCallback == null) {
            ELogUtils.w("jyq_gateway", "Param error.");
            return -3;
        }
        this.mOnBLEConfigCallback = onGatewayConfigCallback;
        this.mOnBLEConfigCallbackV2 = null;
        return doConfigGateway(i, bLEGatewayConfigInfo, j);
    }

    public int startGatewayConfigWithAddressV2(int i, BLEGatewayConfigInfo bLEGatewayConfigInfo, long j, OnGatewayConfigCallbackV2 onGatewayConfigCallbackV2) {
        if (this.mIsSending) {
            ELogUtils.w("jyq_gateway", "Config is sending already, you can call stopDeviceConfig() to stop it.");
            return -1;
        }
        if (i <= 0 || bLEGatewayConfigInfo == null || onGatewayConfigCallbackV2 == null) {
            ELogUtils.w("jyq_gateway", "Param error.");
            return -3;
        }
        this.mOnBLEConfigCallbackV2 = onGatewayConfigCallbackV2;
        this.mOnBLEConfigCallback = null;
        return doConfigGateway(i, bLEGatewayConfigInfo, j);
    }

    private int doConfigGateway(int i, BLEGatewayConfigInfo bLEGatewayConfigInfo, long j) {
        this.mIsSending = true;
        this.mStopSendCfgCmd = false;
        this.mReceivedState1 = false;
        int iGenNextCookie = genNextCookie();
        byte[] bArr = {(byte) (iGenNextCookie & 255), (byte) ((iGenNextCookie & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)};
        StringBuilder sb = new StringBuilder();
        sb.append(new BleTlv((byte) TlvType.kBLEWifiName.ordinal(), bLEGatewayConfigInfo.getSsidBytes()).getHex());
        sb.append(new BleTlv((byte) TlvType.kBLEWifiPwd.ordinal(), bLEGatewayConfigInfo.getPasswordBytes()).getHex());
        sb.append(new BleTlv((byte) TlvType.kBLEAppCookie.ordinal(), bArr).getHex());
        if (!TextUtils.isEmpty(bLEGatewayConfigInfo.getHost())) {
            sb.append(new BleTlv((byte) TlvType.kBLEDeviceHost.ordinal(), bLEGatewayConfigInfo.getHost().getBytes(StandardCharsets.UTF_8)).getHex());
        } else {
            sb.append(new BleTlv((byte) TlvType.kBLEHostCode.ordinal(), new byte[]{(byte) bLEGatewayConfigInfo.getHostId()}).getHex());
        }
        String string = sb.toString();
        byte[] bArrHexStr2Bytes = EConvertUtils.hexStr2Bytes(string);
        ELogUtils.d("jyq_gateway", String.format(Locale.ENGLISH, "allInfo: %s", string));
        byte[][] bArrPackage_gateway_config = BLEUtil.package_gateway_config(bArrHexStr2Bytes);
        if (bArrPackage_gateway_config == null) {
            ELogUtils.e("jyq_gateway", "start config fail, for param error");
            return -2;
        }
        for (int i2 = 0; i2 < bArrPackage_gateway_config.length; i2++) {
            ELogUtils.d("jyq_gateway", String.format(Locale.ENGLISH, "payload[%d]=%s", Integer.valueOf(bArrPackage_gateway_config[i2].length), EConvertUtils.bytes2HexStr(bArrPackage_gateway_config[i2])));
        }
        new SendConfigThread(i, bArrPackage_gateway_config, j).start();
        return 0;
    }

    public boolean stopGatewayConfig() {
        OnGatewayConfigCallback onGatewayConfigCallback = this.mOnBLEConfigCallback;
        if (onGatewayConfigCallback != null) {
            onGatewayConfigCallback.onStopped();
        }
        OnGatewayConfigCallbackV2 onGatewayConfigCallbackV2 = this.mOnBLEConfigCallbackV2;
        if (onGatewayConfigCallbackV2 != null) {
            onGatewayConfigCallbackV2.onStopped();
        }
        this.mOnBLEConfigCallback = null;
        this.mOnBLEConfigCallbackV2 = null;
        this.mIsSending = false;
        this.mStopSendCfgCmd = true;
        return true;
    }

    public boolean isDeviceConfigSending() {
        return this.mIsSending;
    }

    private int genNextCookie() {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() % PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        this.mConfigCookie = iCurrentTimeMillis;
        return iCurrentTimeMillis == 165 ? genNextCookie() : iCurrentTimeMillis;
    }

    class SendConfigThread extends Thread {
        int addr;
        int mSendCnt = 0;
        byte[][] payload;
        long startTimeStamp;
        long timeout;

        public SendConfigThread(int i, byte[][] bArr, long j) {
            this.addr = i;
            this.payload = bArr;
            this.timeout = j;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            this.startTimeStamp = System.currentTimeMillis();
            ELogUtils.w("jyq_gateway", "SendConfigThread in: " + this.startTimeStamp);
            while (BLEFastconHelper.this.mIsSending) {
                if (this.mSendCnt <= 3) {
                    if (BLEFastconHelper.this.mStopSendCfgCmd) {
                        ELogUtils.w("jyq_gateway", "mStopSendCfgCmd is true, wont send config cmd");
                    } else {
                        ELogUtils.w("jyq_gateway", "send count ->" + this.mSendCnt);
                        for (byte[] bArr : this.payload) {
                            BLEFastconHelper.this.configIhg(this.addr, bArr, true);
                            for (int i = 0; i < 10 && BLEFastconHelper.this.mIsSending && !BLEFastconHelper.this.mStopSendCfgCmd; i++) {
                                SystemClock.sleep(100L);
                            }
                            if (BLEFastconHelper.this.mStopSendCfgCmd) {
                                break;
                            }
                        }
                    }
                } else {
                    ELogUtils.w("jyq_gateway", "send count > 3, wont send config cmd");
                }
                long jCurrentTimeMillis = System.currentTimeMillis() - this.startTimeStamp;
                if (jCurrentTimeMillis >= this.timeout) {
                    ELogUtils.w("jyq_gateway", "SendConfigThread timeout: " + this.timeout);
                    if (BLEFastconHelper.this.mOnBLEConfigCallback != null) {
                        BLEFastconHelper.this.mOnBLEConfigCallback.onTimeout();
                    }
                    if (BLEFastconHelper.this.mOnBLEConfigCallbackV2 != null) {
                        BLEFastconHelper.this.mOnBLEConfigCallbackV2.onTimeout();
                    }
                    BLEFastconHelper.this.stopGatewayConfig();
                } else {
                    ELogUtils.w("jyq_gateway", String.format(Locale.ENGLISH, "timeDiff: %d, timeout: %d", Long.valueOf(jCurrentTimeMillis), Long.valueOf(this.timeout)));
                }
                this.mSendCnt++;
                for (int i2 = 0; i2 < 100 && BLEFastconHelper.this.mIsSending; i2++) {
                    SystemClock.sleep(50L);
                }
            }
            ELogUtils.w("jyq_gateway", "SendConfigThread out!");
        }
    }

    private static class BleTlv {
        public byte length;
        public byte type;
        public byte[] val;

        public BleTlv(byte b, byte[] bArr) {
            this.type = b;
            this.val = bArr;
            this.length = (byte) (bArr == null ? 0 : bArr.length);
        }

        public String getHex() {
            return EConvertUtils.to16(this.type) + EConvertUtils.to16(this.length) + EConvertUtils.bytes2HexStr(this.val);
        }
    }

    public String getSingleControlPayload(int i, byte[] bArr) {
        return getSingleControlPayload(i, bArr, true);
    }

    public String getSingleControlPayload(int i, byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length + 2];
        BLEUtil.package_device_control(i, bArr, bArr.length, bArr2);
        return EConvertUtils.bytes2HexStr(getPayloadWithInnerRetry(5, bArr2, i / 256, this.mPhoneKey, z, true, i > 256, true)[0]);
    }

    public String getGroupControlPayload(int i, int i2, byte[] bArr) {
        return getGroupControlPayload(i, i2, bArr, true);
    }

    public String getGroupControlPayload(int i, int i2, byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length + 4];
        BLEUtil.package_broadcast_control(i2, i, bArr, bArr.length, bArr2);
        return EConvertUtils.bytes2HexStr(getPayloadWithInnerRetry(5, bArr2, 0, this.mPhoneKey, z, true, false, true)[0]);
    }

    public String getScenePayload(int i) {
        return getScenePayload(i, true);
    }

    public String getScenePayload(int i, boolean z) {
        byte[] bArr = new byte[12];
        return EConvertUtils.bytes2HexStr(Arrays.copyOfRange(getPayloadWithInnerRetry(5, bArr, 0, this.mPhoneKey, z, true, false, true)[0], 0, BLEUtil.package_room_scene_control(i, bArr) + 4));
    }

    public String getRemotePayload(String str) {
        BLEDeviceInfo defaultGateway = getDefaultGateway();
        if (defaultGateway != null) {
            return GatewayRemoteCtrlHelper.genRemotePayload(defaultGateway, EConvertUtils.hexStr2Bytes(str));
        }
        return null;
    }

    public static String getRemotePayload(BLEDeviceInfo bLEDeviceInfo, String str) {
        if (bLEDeviceInfo != null) {
            return GatewayRemoteCtrlHelper.genRemotePayload(bLEDeviceInfo, EConvertUtils.hexStr2Bytes(str));
        }
        return null;
    }

    public static String genNewSelfKey() {
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) (new Random().nextInt(999) + 1);
        }
        return EConvertUtils.bytes2HexStr(bArr);
    }

    public byte[] generateRmAcAddSubDevWithIndex(int i, int i2, int i3) {
        byte[] bArr = new byte[256];
        int iGenerateRmacAddSubdevWithIndex = BLEUtil.generateRmacAddSubdevWithIndex(i, i2, i3, sSendSeq, bArr);
        sSendSeq++;
        return Arrays.copyOfRange(bArr, 0, iGenerateRmacAddSubdevWithIndex);
    }

    public byte[] generateRmAcRemoveSubDevWithIndex(int i) {
        byte[] bArr = new byte[256];
        int iGenerateRmacRemoveSubdevWithIndex = BLEUtil.generateRmacRemoveSubdevWithIndex(i, sSendSeq, bArr);
        sSendSeq++;
        return Arrays.copyOfRange(bArr, 0, iGenerateRmacRemoveSubdevWithIndex);
    }

    public byte[] generateRmAcQuerySubDevWithIndex(int i) {
        byte[] bArr = new byte[256];
        int iGenerateRmacQuerySubdevWithIndex = BLEUtil.generateRmacQuerySubdevWithIndex(i, sSendSeq, bArr);
        sSendSeq++;
        return Arrays.copyOfRange(bArr, 0, iGenerateRmacQuerySubdevWithIndex);
    }

    public byte[][] generateRmAcSendDataListWithFileData(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        return getRmAcPacketArrays(i, EAppUtils.mergeArrays(generateRmAcHeader(bArr.length, i2), bArr));
    }

    public byte[][] generateRmAcSendDataListWithCommand(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        return getRmAcPacketArrays(i, generateRmAcControlCmd(bArr, bArr.length, i2));
    }

    private byte[] generateRmAcControlCmd(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i + 64];
        return Arrays.copyOfRange(bArr2, 0, BLEUtil.package_rmac_commandData(i2, bArr, i, bArr2));
    }

    public byte[][] getRmAcPacketArrays(int i, byte[] bArr) {
        int length;
        byte[] bArrCopyOfRange;
        if (bArr.length % i == 0) {
            length = bArr.length / i;
        } else {
            length = (bArr.length / i) + 1;
        }
        int i2 = length;
        byte[][] bArr2 = new byte[i2][];
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 != i2 - 1) {
                bArrCopyOfRange = Arrays.copyOfRange(bArr, i3 * i, (i3 + 1) * i);
            } else {
                bArrCopyOfRange = Arrays.copyOfRange(bArr, i3 * i, bArr.length);
            }
            byte[] bArr3 = bArrCopyOfRange;
            bArr2[i3] = generateRmAcOuterPacket(bArr3, bArr3.length, i2, i3, sSendSeq);
        }
        sSendSeq++;
        return bArr2;
    }

    public byte[] generateRmAcOuterPacket(byte[] bArr, int i, int i2, int i3, int i4) {
        byte[] bArr2 = new byte[i + 64];
        return Arrays.copyOfRange(bArr2, 0, BLEUtil.package_rmac_send(bArr, i, i2, i3, i4, bArr2));
    }

    public byte[] generateRmAcHeader(int i, int i2) {
        byte[] bArr = new byte[64];
        int iPackage_rmac_headerData = BLEUtil.package_rmac_headerData(i, i2, bArr);
        sSendSeq++;
        return Arrays.copyOfRange(bArr, 0, iPackage_rmac_headerData);
    }

    public List<String> generateTCCommandWithCount(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = i == 1 ? 2 : (i + 1) * 2;
        int[] iArr = new int[i2];
        iArr[0] = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        for (int i3 = 1; i3 < i2; i3++) {
            iArr[i3] = iArr[0] + i3;
        }
        byte[] bArr = new byte[1200];
        int iSend_tc2_learn_hander = BLEUtil.send_tc2_learn_hander(iArr, i2, bArr);
        if (iSend_tc2_learn_hander > 0) {
            arrayList.add(EConvertUtils.bytes2HexStr(Arrays.copyOfRange(bArr, 0, iSend_tc2_learn_hander)));
        }
        for (int i4 = 0; i4 < i2; i4++) {
            byte[] bArr2 = new byte[100];
            int iSend_tc2_ctrl_hander = BLEUtil.send_tc2_ctrl_hander(iArr[i4], bArr2);
            if (iSend_tc2_ctrl_hander > 0) {
                arrayList.add(EConvertUtils.bytes2HexStr(Arrays.copyOfRange(bArr2, 0, iSend_tc2_ctrl_hander)));
            }
        }
        if (i == 1) {
            arrayList.add((String) arrayList.get(1));
            arrayList.add((String) arrayList.get(2));
        }
        ELogUtils.d("jyq_tc", String.format(Locale.ENGLISH, "cnt[%d], number[%d], seedArr=%s", Integer.valueOf(i), Integer.valueOf(i2), Arrays.toString(iArr)));
        ELogUtils.d("jyq_tc", String.format(Locale.ENGLISH, "ret cmd = %s", JSON.toJSONString(arrayList)));
        return arrayList;
    }

    public List<String> generateAokCommand() {
        return doGenerateAokCommand(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE));
    }

    public List<String> generateAokCommand(int i) {
        return doGenerateAokCommand(i);
    }

    private static ArrayList<String> doGenerateAokCommand(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < 4; i2++) {
            byte[] bArr = new byte[1200];
            int iSend_aok_ctrl_handler = BLEUtil.send_aok_ctrl_handler(i, i2, 1200, bArr);
            if (iSend_aok_ctrl_handler > 0) {
                arrayList.add(EConvertUtils.bytes2HexStr(Arrays.copyOfRange(bArr, 0, iSend_aok_ctrl_handler)));
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            String str = arrayList.get(i3);
            ELogUtils.d("jyq_aok", String.format(Locale.ENGLISH, "seed = %d, len=%d, ret cmd[%d] = %s", Integer.valueOf(i), Integer.valueOf(str.length() / 2), Integer.valueOf(i3), str));
        }
        return arrayList;
    }

    public List<String> generateSrCommand() {
        return doGenerateSrCommand(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE));
    }

    public List<String> generateSrCommand(int i) {
        return doGenerateSrCommand(i);
    }

    private static ArrayList<String> doGenerateSrCommand(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        int[] iArr = {128, 1, 3, 2};
        for (int i2 = 0; i2 < 4; i2++) {
            byte[] bArr = new byte[1200];
            int iSend_sr_ctrl_handler = BLEUtil.send_sr_ctrl_handler(i, iArr[i2], 1200, bArr);
            if (iSend_sr_ctrl_handler > 0) {
                arrayList.add(EConvertUtils.bytes2HexStr(Arrays.copyOfRange(bArr, 0, iSend_sr_ctrl_handler)));
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            String str = arrayList.get(i3);
            ELogUtils.d("jyq_sr", String.format(Locale.ENGLISH, "seed = %d, len=%d, ret cmd[%d] = %s", Integer.valueOf(i), Integer.valueOf(str.length() / 2), Integer.valueOf(i3), str));
        }
        return arrayList;
    }

    public RmSubDevRetInfo parseRmAcReturn(byte[] bArr) {
        return BLEUtil.parse_rmac_subdev_ret(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x004b  */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:71)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:71)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getRmSubTypeByDevType(int r0) {
        /*
            switch(r0) {
                case 43459: goto L54;
                case 43463: goto L54;
                case 43474: goto L54;
                case 43525: goto L54;
                case 43680: goto L54;
                case 43919: goto L51;
                case 43951: goto L4e;
                case 45399: goto L54;
                case 45534: goto L4b;
                case 45610: goto L48;
                case 45657: goto L45;
                case 45746: goto L54;
                default: goto L3;
            }
        L3:
            switch(r0) {
                case 44843: goto L42;
                case 44844: goto L45;
                case 44845: goto L3f;
                case 44846: goto L3c;
                case 44847: goto L39;
                case 44848: goto L36;
                case 44849: goto L33;
                case 44850: goto L4b;
                default: goto L6;
            }
        L6:
            switch(r0) {
                case 45152: goto L30;
                case 45153: goto L48;
                default: goto L9;
            }
        L9:
            switch(r0) {
                case 45401: goto L54;
                case 45402: goto L54;
                default: goto Lc;
            }
        Lc:
            switch(r0) {
                case 45660: goto L2d;
                case 45661: goto L2a;
                case 45662: goto L27;
                case 45663: goto L24;
                case 45664: goto L21;
                default: goto Lf;
            }
        Lf:
            switch(r0) {
                case 45666: goto L1e;
                case 45667: goto L1b;
                case 45668: goto L18;
                case 45669: goto L15;
                default: goto L12;
            }
        L12:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.OTHERS
            return r0
        L15:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.RACK
            return r0
        L18:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.DE_HUMIDIFIER
            return r0
        L1b:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.SWEEP_ROBOT
            return r0
        L1e:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.HUMIDIFIER
            return r0
        L21:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.CAMERA
            return r0
        L24:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.HEATER
            return r0
        L27:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.AIR
            return r0
        L2a:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.AMP
            return r0
        L2d:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.SOUND
            return r0
        L30:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.LIGHT
            return r0
        L33:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.DOOR
            return r0
        L36:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.DOOR_LOCK
            return r0
        L39:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.DVD
            return r0
        L3c:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.FAN
            return r0
        L3f:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.PROJECTOR
            return r0
        L42:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.TV
            return r0
        L45:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.TV_BOX
            return r0
        L48:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.CURTAIN
            return r0
        L4b:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.OTHERS
            return r0
        L4e:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.TEMPMETER
            return r0
        L51:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.AC
            return r0
        L54:
            int r0 = cn.com.broadlink.blelight.helper.BLEFastconHelper.RM_SUB_TYPE.SWITCH
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.broadlink.blelight.helper.BLEFastconHelper.getRmSubTypeByDevType(int):int");
    }

    public void setShowBLEStateDialog(boolean z) {
        this.mIsShowBLEStateDialog = z;
    }

    public boolean controlRtcTimerSetTime(int i, int i2, int i3, BLEAppTimeInfo bLEAppTimeInfo, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rtc_set_time(i, i2, i3, bLEAppTimeInfo, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRtcTimerCmdOthers(int i, int i2, int i3, int i4, int i5, int i6, byte[] bArr, boolean z, boolean z2) {
        byte[] bArr2 = new byte[18];
        BLEUtil.package_rtc_set_cmd_others(i, i2, i3, i4, i5, i6, bArr, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRtcTimerCmdSelf(int i, int i2, int i3, int i4, byte[] bArr, boolean z, boolean z2) {
        byte[] bArr2 = new byte[18];
        BLEUtil.package_rtc_set_cmd_self(i, i2, i3, i4, bArr, bArr2);
        return sendCommand(5, bArr2, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRtcTimerEnable(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rtc_set_enable(i, i2, i3, i4, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRtcTimerDelete(int i, int i2, int i3, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rtc_delete(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRtcTimerGet(int i, int i2, int i3, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rtc_get(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }

    public boolean controlRtcTimerGetCmd(int i, int i2, int i3, boolean z, boolean z2) {
        byte[] bArr = new byte[18];
        BLEUtil.package_rtc_get_cmd(i, i2, i3, bArr);
        return sendCommand(5, bArr, this.mPhoneKey, BLE_CMD_RETRY_CNT, BLE_CMD_SEND_TIME, z2, z, true, i2 / 256);
    }
}

package cn.com.broadlink.blelight.constant;

/* JADX INFO: loaded from: classes.dex */
public class GatewayConfigConstants {

    public class ErrorCode {
        public static final int BLE_DEV_ERR_AUTH_FAIL = -3;
        public static final int BLE_DEV_ERR_DNS_FAIL = -4;
        public static final int BLE_DEV_ERR_HEARTBEAT_FAIL = -7;
        public static final int BLE_DEV_ERR_LICENSE_STEP_1 = -5;
        public static final int BLE_DEV_ERR_LICENSE_STEP_2 = -6;
        public static final int BLE_DEV_ERR_SCAN_FAIL = -2;
        public static final int BLE_DEV_ERR_TIMEOUT = -1;
        public static final int BLE_DEV_NO_ERR = 0;

        public ErrorCode() {
        }
    }

    public class DevState {
        public static final int BLE_DEV_STATE_CONFIG = 1;
        public static final int BLE_DEV_STATE_CONNECTED_AP = 3;
        public static final int BLE_DEV_STATE_CONNECTED_CLOUD = 4;
        public static final int BLE_DEV_STATE_CONNECTING_AP = 2;
        public static final int BLE_DEV_STATE_IDLE = 0;
        public static final int BLE_DEV_STATE_WAIT_FOR_ADDRESS_SETTING = 5;

        public DevState() {
        }
    }
}

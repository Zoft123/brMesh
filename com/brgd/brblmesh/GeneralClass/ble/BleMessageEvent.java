package com.brgd.brblmesh.GeneralClass.ble;

/* JADX INFO: loaded from: classes.dex */
public class BleMessageEvent {
    public static final int TYPE_ATM_UPDATE_GRAPH = 8;
    public static final int TYPE_DIS_INFO = 6;
    public static final int TYPE_MTU_UPDATE = 1;
    public static final int TYPE_OTA_CONFIG_FILE = 5;
    public static final int TYPE_OTA_ERROR_MESSAGE = 2;
    public static final int TYPE_OTA_FINISH = 4;
    public static final int TYPE_OTA_MESSAGE = 3;
    public static final int TYPE_OTA_PROGRESS = 7;
    public int errorCode;
    public String errorMessage;
    public String message;
    public int number;
    public int type;

    public BleMessageEvent(int i, int i2) {
        this.type = i;
        this.number = i2;
    }

    public BleMessageEvent(int i) {
        this.type = i;
    }

    public BleMessageEvent(int i, String str) {
        this.type = i;
        this.message = str;
    }

    public BleMessageEvent(int i, String str, int i2) {
        this.type = i;
        this.errorMessage = str;
        this.errorCode = i2;
    }
}

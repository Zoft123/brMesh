package cn.com.broadlink.blelight.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class EPermissionConstants {
    public static final String CALENDAR = "android.permission-group.CALENDAR";
    public static final String CAMERA = "android.permission-group.CAMERA";
    public static final String CONTACTS = "android.permission-group.CONTACTS";
    private static final String[] GROUP_CALENDAR = {"android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"};
    private static final String[] GROUP_CAMERA = {"android.permission.CAMERA"};
    private static final String[] GROUP_CONTACTS = {"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};
    private static final String[] GROUP_LOCATION = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private static final String[] GROUP_MICROPHONE = {"android.permission.RECORD_AUDIO"};
    private static final String[] GROUP_PHONE = {"android.permission.READ_PHONE_STATE", "android.permission.READ_PHONE_NUMBERS", "android.permission.CALL_PHONE", "android.permission.ANSWER_PHONE_CALLS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "com.android.voicemail.permission.ADD_VOICEMAIL", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS"};
    private static final String[] GROUP_SENSORS = {"android.permission.BODY_SENSORS"};
    private static final String[] GROUP_SMS = {"android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS"};
    private static final String[] GROUP_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static final String LOCATION = "android.permission-group.LOCATION";
    public static final String MICROPHONE = "android.permission-group.MICROPHONE";
    public static final String PHONE = "android.permission-group.PHONE";
    public static final String SENSORS = "android.permission-group.SENSORS";
    public static final String SMS = "android.permission-group.SMS";
    public static final String STORAGE = "android.permission-group.STORAGE";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Permission {
    }

    public static String[] getPermissions(String str) {
        str.hashCode();
        switch (str) {
            case "android.permission-group.CONTACTS":
                return GROUP_CONTACTS;
            case "android.permission-group.PHONE":
                return GROUP_PHONE;
            case "android.permission-group.CALENDAR":
                return GROUP_CALENDAR;
            case "android.permission-group.CAMERA":
                return GROUP_CAMERA;
            case "android.permission-group.SENSORS":
                return GROUP_SENSORS;
            case "android.permission-group.LOCATION":
                return GROUP_LOCATION;
            case "android.permission-group.STORAGE":
                return GROUP_STORAGE;
            case "android.permission-group.MICROPHONE":
                return GROUP_MICROPHONE;
            case "android.permission-group.SMS":
                return GROUP_SMS;
            default:
                return new String[]{str};
        }
    }
}

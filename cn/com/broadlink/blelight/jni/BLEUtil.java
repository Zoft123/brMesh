package cn.com.broadlink.blelight.jni;

import cn.com.broadlink.blelight.bean.BLEAppTimeInfo;
import cn.com.broadlink.blelight.bean.BLEModeRgbcwBean;
import cn.com.broadlink.blelight.bean.BLERadar24GAllInfo;
import cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo;
import cn.com.broadlink.blelight.bean.BLETimeLcInfo;
import cn.com.broadlink.blelight.bean.BLETimerAllInfo;
import cn.com.broadlink.blelight.bean.BLEWorkTimerAllInfo;
import cn.com.broadlink.blelight.bean.RmSubDevRetInfo;
import cn.com.broadlink.blelight.interfaces.BLEScanCallback;
import cn.com.broadlink.blelight.interfaces.OnDevScanCallback;
import cn.com.broadlink.blelight.interfaces.OnPassThroughCallback;
import cn.com.broadlink.blelight.interfaces.OnReceiveFullFrameCallback;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BLEUtil {
    public static native int bl_get_ble_payload(byte[] bArr, int i, byte[] bArr2, boolean z, boolean z2);

    public static native int generateRmacAddSubdevWithIndex(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int generateRmacQuerySubdevWithIndex(int i, int i2, byte[] bArr);

    public static native int generateRmacRemoveSubdevWithIndex(int i, int i2, byte[] bArr);

    public static native byte[] get_rf_payload(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3);

    public static native int header_encrypt(byte[] bArr, int i, byte[] bArr2);

    public static native int package_24g_radar_param(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, byte[] bArr);

    public static native int package_24g_work_timer_get(int i, byte[] bArr);

    public static native int package_24g_work_timer_set(BLERadar24GAllInfo bLERadar24GAllInfo, byte[] bArr);

    public static native int package_alexa_query_command(int i, byte[] bArr);

    public static native int package_alexa_set_command(int i, int i2, byte[] bArr);

    public static native int package_back_light(int i, int i2, byte[] bArr);

    public static native int package_batch_control(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3);

    public static native int package_batch_temp_group(byte[] bArr, int i, int i2, short s, byte[] bArr2);

    public static native int package_ble_fastcon_body(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, byte[] bArr2, byte[] bArr3);

    public static native int package_ble_fastcon_body_nor_encryp(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, byte[] bArr2);

    public static native int package_ble_fastcon_body_with_header(byte[] bArr, byte[] bArr2, int i, byte[] bArr3, byte[] bArr4);

    public static native int package_broadcast_control(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_car_park_clear_self_learn_param(int i, int i2, byte[] bArr);

    public static native int package_car_park_forward(int i, int i2, int i3, byte[] bArr);

    public static native int package_car_park_radar_param(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, byte[] bArr);

    public static native int package_car_park_reset_to_public_key(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int package_car_park_self_learn_param(int i, int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr);

    public static native int package_clear_rm_list(int i, byte[] bArr);

    public static native int package_delayed_control(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr);

    public static native int package_delayed_group_control(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr);

    public static native int package_device_control(int i, byte[] bArr, int i2, byte[] bArr2);

    public static native int package_device_control_v2(int i, byte[] bArr, int i2, byte[] bArr2);

    public static native int package_device_history_clear(int i, byte[] bArr);

    public static native int package_device_history_query(int i, int i2, byte[] bArr);

    public static native int package_device_rgbcw_mode(int i, int i2, int i3, int i4, int i5, int i6, ArrayList<BLEModeRgbcwBean> arrayList, byte[] bArr);

    public static native int package_device_rgbcw_mode_v2(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, ArrayList<BLEModeRgbcwBean> arrayList, byte[] bArr);

    public static native int package_disc_res(byte[] bArr, int i, int i2, byte[] bArr2, byte[] bArr3);

    public static native int package_disc_res2(byte[] bArr, int i, int i2, int i3, byte[] bArr2, byte[] bArr3);

    public static native int package_fixed_group_create(byte[] bArr, int i, int i2, int i3, byte[] bArr2);

    public static native int package_fixed_group_remove(byte[] bArr, int i, byte[] bArr2, int i2, int i3, byte[] bArr3);

    public static native byte[][] package_gateway_config(byte[] bArr);

    public static native int package_gateway_udp_setting(int i, int i2, int i3, int i4, int i5, byte[] bArr);

    public static native int package_group_control_v2(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_group_main_dev(int i, int i2, int i3, byte[] bArr);

    public static native int package_group_scene_add(int i, int i2, int i3, byte[] bArr, int i4, byte[] bArr2);

    public static native int package_group_scene_control(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_group_scene_delete(int i, int i2, int i3, byte[] bArr);

    public static native int package_lan_cmd(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_light_param_setting(int i, int i2, int i3, byte[] bArr, int i4, byte[] bArr2);

    public static native int package_music_control(int i, byte[] bArr, int i2, byte[] bArr2);

    public static native int package_open_sdk_cmd(int i, byte[] bArr, int i2, byte[] bArr2);

    public static native int package_panel_reset_default_command(int i, int i2, byte[] bArr);

    public static native int package_panel_scene_delete(int i, int i2, int i3, byte[] bArr);

    public static native int package_panel_scene_except(int i, int i2, byte[] bArr);

    public static native int package_pass_through_control(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_passt_hrough(int i, byte[] bArr, byte[] bArr2);

    public static native int package_query_dev_state(byte[] bArr, int i, byte[] bArr2);

    public static native int package_rgbcw_timer_get(int i, byte[] bArr);

    public static native int package_rgbcw_timer_set(int i, int i2, ArrayList<BLERgbcwTimerInfo> arrayList, byte[] bArr);

    public static native int package_rmac_ap_enable(int i, int i2, int i3, byte[] bArr);

    public static native int package_rmac_commandData(int i, byte[] bArr, int i2, byte[] bArr2);

    public static native int package_rmac_delete_device(int i, int i2, byte[] bArr);

    public static native int package_rmac_headerData(int i, int i2, byte[] bArr);

    public static native int package_rmac_send(byte[] bArr, int i, int i2, int i3, int i4, byte[] bArr2);

    public static native int package_room_scene_add(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_room_scene_control(int i, byte[] bArr);

    public static native int package_room_scene_delete(int i, int i2, byte[] bArr);

    public static native int package_room_scene_modify_default_command(int i, int i2, int i3, byte[] bArr, int i4, byte[] bArr2);

    public static native int package_room_scene_query(int i, int i2, int i3, byte[] bArr);

    public static native int package_rtc_delete(int i, int i2, int i3, byte[] bArr);

    public static native int package_rtc_get(int i, int i2, int i3, byte[] bArr);

    public static native int package_rtc_get_cmd(int i, int i2, int i3, byte[] bArr);

    public static native int package_rtc_set_cmd_others(int i, int i2, int i3, int i4, int i5, int i6, byte[] bArr, byte[] bArr2);

    public static native int package_rtc_set_cmd_self(int i, int i2, int i3, int i4, byte[] bArr, byte[] bArr2);

    public static native int package_rtc_set_enable(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int package_rtc_set_time(int i, int i2, int i3, BLEAppTimeInfo bLEAppTimeInfo, byte[] bArr);

    public static native int package_scene_control(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_sensor_group_set(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int package_sensor_radar_param(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr);

    public static native int package_sensor_recover_time(int i, int i2, byte[] bArr);

    public static native int package_set_group_addr(int i, int i2, byte[] bArr);

    public static native int package_set_short_addr(byte[] bArr, int i, byte[] bArr2);

    public static native int package_super_panel_bind(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native int package_sync_time_info(int i, BLEAppTimeInfo bLEAppTimeInfo, byte[] bArr);

    public static native int package_timer_get(int i, BLETimeLcInfo bLETimeLcInfo, byte[] bArr);

    public static native int package_timer_set(int i, BLETimerAllInfo bLETimerAllInfo, byte[] bArr);

    public static native int package_trig_dev_ota(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, byte[] bArr2);

    public static native int package_vir_dev_bind(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int package_vir_dev_unbind(int i, int i2, byte[] bArr);

    public static native int package_work_timer_get(int i, byte[] bArr);

    public static native int package_work_timer_set(BLEWorkTimerAllInfo bLEWorkTimerAllInfo, byte[] bArr);

    public static native int package_yrd_radar_pass_through(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    public static native BLERadar24GAllInfo parse_24g_work_time_upload(int i, byte[] bArr);

    public static native int parse_ble_broadcast(byte[] bArr, int i, byte[] bArr2, BLEScanCallback bLEScanCallback, int i2);

    public static native int parse_ble_broadcast_new(byte[] bArr, int i, byte[] bArr2, OnPassThroughCallback onPassThroughCallback, OnDevScanCallback onDevScanCallback, OnReceiveFullFrameCallback onReceiveFullFrameCallback, int i2);

    public static native int parse_ble_broadcast_pass_through(byte[] bArr, int i, byte[] bArr2, OnPassThroughCallback onPassThroughCallback, int i2);

    public static native ArrayList<BLERgbcwTimerInfo> parse_rgbcw_timer_upload(byte[] bArr);

    public static native RmSubDevRetInfo parse_rmac_subdev_ret(byte[] bArr);

    public static native BLEWorkTimerAllInfo parse_work_time_upload(int i, byte[] bArr);

    public static native int send_aok_ctrl_handler(int i, int i2, int i3, byte[] bArr);

    public static native int send_sr_ctrl_handler(int i, int i2, int i3, byte[] bArr);

    public static native int send_tc2_ctrl_hander(int i, byte[] bArr);

    public static native int send_tc2_learn_hander(int[] iArr, int i, byte[] bArr);

    public static native void setLogEnable(int i);

    public static native int whiten_data(byte[] bArr, int i, byte[] bArr2);

    static {
        System.loadLibrary("broadlink_ble");
    }
}

package com.brgd.brblmesh.GeneralClass.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.WriteProgressCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;
import com.brgd.brblmesh.GeneralClass.ble.utility.CRC32_2;
import com.brgd.brblmesh.GeneralClass.ble.utility.FileHelper;
import com.brgd.brblmesh.GeneralClass.ble.utility.ZipHelper;
import j$.util.Objects;
import java.io.File;
import java.util.concurrent.locks.LockSupport;
import org.bson.BSON;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class BleOTA {
    private final BleManager bleManager;
    private final BluetoothAdapter mBleAdapter;
    Context mContext;
    private Thread thread;
    private final byte OTA_CMD_CONN_PARAM_UPDATE = 1;
    private final byte OTA_CMD_MTU_UPDATE = 2;
    private final byte OTA_CMD_VERSION = 3;
    private final byte OTA_CMD_CREATE_OTA_SETTING = 4;
    private final byte OTA_CMD_CREATE_OTA_IMAGE = 5;
    private final byte OTA_CMD_VALIDATE_OTA_IMAGE = 6;
    private final byte OTA_CMD_ACTIVATE_OTA_IMAGE = 7;
    private final byte OTA_CMD_JUMP_IMAGE_UPDATE = 8;
    private int ota_error = 0;
    private int app1_bin_size = 0;
    private int app2_bin_size = 0;
    private int image_update_bin_size = 0;
    private int image_update_version = 0;
    private int dfu_setting_size = 0;
    private int ota_selection = 0;
    private String unzipFilePath = null;
    private String dfu_image_path = null;
    private int dfu_image_size = 0;
    private int dfu_image_sent = 0;
    int image_offset = 0;
    int image_size = 0;
    int image_crc = 0;

    public BleOTA(Context context, BleManager bleManager, BluetoothAdapter bluetoothAdapter) {
        this.bleManager = bleManager;
        this.mBleAdapter = bluetoothAdapter;
        this.mContext = context;
    }

    public void fileOpen(Context context, Uri uri) throws Throwable {
        String file;
        String filePathByUri = FileHelper.getFilePathByUri(context, uri);
        this.unzipFilePath = new File((String) Objects.requireNonNull(FileHelper.getFilePathByUri(context, uri))).getParent() + "/OTA";
        File file2 = new File(this.unzipFilePath);
        if (file2.exists()) {
            FileHelper.deleteRecursive(file2);
        }
        int i = 0;
        if (file2.mkdir()) {
            try {
                ZipHelper.UnZipFolder(filePathByUri, this.unzipFilePath);
                file = FileHelper.readFile(new File(this.unzipFilePath + "/config.txt").getPath());
            } catch (Exception e) {
                Log.e(GlobalVariable.BLE_TAG, "fileOpen error: " + e);
                file = null;
            }
            File file3 = new File(this.unzipFilePath + "/APP1.bin");
            File file4 = new File(this.unzipFilePath + "/APP2.bin");
            File file5 = new File(this.unzipFilePath + "/ImageUpdate.bin");
            File file6 = new File(this.unzipFilePath + "/dfu_setting.dat");
            if (file3.exists()) {
                this.app1_bin_size = (int) file3.length();
            }
            if (file4.exists()) {
                this.app2_bin_size = (int) file4.length();
            }
            if (file5.exists() && !Objects.equals(file, null)) {
                String[] strArrSplit = file.split(System.lineSeparator());
                while (i < strArrSplit.length && !strArrSplit[i].contains("IMAGE_UPDATE_VERSION")) {
                    i++;
                }
                this.image_update_version = Integer.valueOf(strArrSplit[i].split(":")[1].substring(3, 11), 16).intValue();
                this.image_update_bin_size = (int) file5.length();
            }
            if (file6.exists()) {
                this.dfu_setting_size = (int) file6.length();
            }
            EventBus.getDefault().postSticky(new BleMessageEvent(5, file));
            return;
        }
        Toast.makeText(context, " failed to create DFU_file folder", 0).show();
    }

    private boolean threadBlock(long j, String str) {
        long jNanoTime = System.nanoTime();
        long j2 = j * 1000000;
        LockSupport.parkNanos(this.thread, j2);
        if (System.nanoTime() - jNanoTime <= j2) {
            return true;
        }
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(null);
        EventBus.getDefault().post(new BleMessageEvent(2, str + " timeout", this.ota_error));
        return false;
    }

    private void threadBlock(long j) {
        LockSupport.parkNanos(this.thread, j * 1000000);
    }

    public void start() {
        Thread thread = new Thread(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$start$0();
            }
        });
        this.thread = thread;
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0() {
        if (this.bleManager.data_out_char != null && this.bleManager.data_in_char != null) {
            mesh_app_jump_imageupdate();
            if (!threadBlock(5000L, "mesh_app_jump_imageupdate") || this.ota_error > 0) {
                return;
            }
            this.bleManager.disconnect().enqueue();
            for (int i = 0; i < 5; i++) {
                threadBlock(1000L);
                if (!this.bleManager.isConnected()) {
                    break;
                }
            }
            threadBlock(3000L);
            byte[] deviceMac = this.bleManager.getDeviceMac();
            deviceMac[5] = (byte) (deviceMac[5] + 1);
            reconnect_to_image_update(deviceMac);
            if (!threadBlock(10000L, "reconnect_to_image_update")) {
                return;
            }
        }
        if (update_images(2048, 40)) {
            EventBus.getDefault().post(new BleMessageEvent(4));
            this.bleManager.disconnect().enqueue();
        }
    }

    private void reconnect_to_image_update(byte[] bArr) {
        BluetoothDevice remoteDevice = this.mBleAdapter.getRemoteDevice(bArr);
        if (remoteDevice != null) {
            this.bleManager.connect(remoteDevice).retry(50, 100).useAutoConnect(false).before(new BeforeCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.3
                @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback
                public void onRequestStarted(BluetoothDevice bluetoothDevice) {
                }
            }).done(new SuccessCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.2
                @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback
                public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
                    LockSupport.unpark(BleOTA.this.thread);
                }
            }).fail(new FailCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.1
                @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback
                public void onRequestFailed(BluetoothDevice bluetoothDevice, int i) {
                    EventBus.getDefault().post(new BleMessageEvent(2, "reconnect_to_image_update", 1));
                }
            }).enqueue();
        }
    }

    private boolean update_images(int i, int i2) {
        this.image_offset = 0;
        this.image_size = 0;
        this.image_crc = 0;
        this.dfu_image_sent = 0;
        connection_update(15, i2, 0, DisDoubleClickListener.MIN_CLICK_DELAY_TIME);
        if (!threadBlock(5000L, "connection_update") || this.ota_error > 0) {
            return false;
        }
        if (this.bleManager.getMTU() < 244) {
            mtu_update(247);
            if (!threadBlock(1000L, "mtu_update") || this.ota_error > 0) {
                return false;
            }
            this.bleManager.overrideMtu(247);
            this.bleManager.setMTU(247);
        }
        version_request(this.app1_bin_size, this.app2_bin_size, this.image_update_bin_size, this.image_update_version);
        if (!threadBlock(10000L, "version_request") || this.ota_error > 0) {
            return false;
        }
        if (this.ota_selection == 4) {
            jump_to_image_update();
            return threadBlock(5000L, "jump_to_image_update") && this.ota_error <= 0;
        }
        create_ota_setting_transfer(this.dfu_setting_size);
        if (!threadBlock(1000L, "create_ota_setting_transfer") || this.ota_error > 0) {
            return false;
        }
        send_setting_data(FileHelper.readFile(this.unzipFilePath + "/dfu_setting.dat", 0, this.dfu_setting_size));
        if (!threadBlock(30000L, "send_setting_data") || this.ota_error > 0) {
            return false;
        }
        while (true) {
            int i3 = this.image_offset;
            int i4 = this.dfu_image_size;
            if (i3 < i4) {
                int iMin = Math.min(i4 - i3, i);
                this.image_size = iMin;
                byte[] file = FileHelper.readFile(this.dfu_image_path, this.image_offset, iMin);
                int iFast = CRC32_2.fast((byte[]) Objects.requireNonNull(file));
                this.image_crc = iFast;
                create_ota_image_transfer(this.image_offset, this.image_size, iFast);
                if (!threadBlock(5000L, "create_ota_image_transfer") || this.ota_error > 0) {
                    return false;
                }
                send_image_data(file);
                if (!threadBlock(20000L, "send_image_data") || this.ota_error > 0) {
                    return false;
                }
                this.image_offset += this.image_size;
            } else {
                validate_new_image();
                if (!threadBlock(10000L, "validate_new_image") || this.ota_error > 0) {
                    return false;
                }
                activate_new_image();
                return threadBlock(1000L, "activate_new_image") && this.ota_error <= 0;
            }
        }
    }

    private void mesh_app_jump_imageupdate() {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.data_in_char).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda9
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$mesh_app_jump_imageupdate$1(bluetoothDevice, data);
            }
        });
        byte[] bArr = {BSON.NUMBER_LONG, 8};
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.data_out_char, bArr).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mesh_app_jump_imageupdate$1(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "jump_to_image_update", this.ota_error));
        }
        if (data.getValue()[0] == 8) {
            LockSupport.unpark(this.thread);
        }
    }

    private void jump_to_image_update() {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda8
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$jump_to_image_update$2(bluetoothDevice, data);
            }
        });
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_cc, new byte[]{8}).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$jump_to_image_update$2(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "jump_to_image_update", this.ota_error));
        }
        if (data.getValue()[0] == 8) {
            LockSupport.unpark(this.thread);
        }
    }

    private void connection_update(int i, int i2, int i3, int i4) {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda2
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$connection_update$3(bluetoothDevice, data);
            }
        });
        byte[] bArr = {1, (byte) (i >> 8), (byte) i, (byte) (i2 >> 8), (byte) i2, (byte) (i3 >> 8), (byte) i3, (byte) (i4 >> 8), (byte) i4};
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_cc, bArr).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connection_update$3(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "connection_update", this.ota_error));
        }
        if (data.getValue()[0] == 1) {
            LockSupport.unpark(this.thread);
        }
    }

    private void mtu_update(int i) {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda0
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$mtu_update$4(bluetoothDevice, data);
            }
        });
        byte[] bArr = {2, (byte) (i >> 8), (byte) i};
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_cc, bArr).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mtu_update$4(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "mtu_update", this.ota_error));
        }
        if (data.getValue()[0] == 2) {
            LockSupport.unpark(this.thread);
        }
    }

    private void version_request(final int i, final int i2, final int i3, int i4) {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda5
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$version_request$5(i, i2, i3, bluetoothDevice, data);
            }
        });
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_cc, new byte[]{3, (byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i, (byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2, (byte) (i3 >> 24), (byte) (i3 >> 16), (byte) (i3 >> 8), (byte) i3, (byte) (i4 >> 24), (byte) (i4 >> 16), (byte) (i4 >> 8), (byte) i4}).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$version_request$5(int i, int i2, int i3, BluetoothDevice bluetoothDevice, Data data) {
        this.ota_error = 0;
        byte b = Objects.equals(data.getValue(), null) ? (byte) 5 : data.getValue()[13];
        this.ota_selection = b;
        if (b == 1) {
            this.dfu_image_path = this.unzipFilePath + "/APP1.bin";
            this.dfu_image_size = i;
        } else if (b == 2) {
            this.dfu_image_path = this.unzipFilePath + "/APP2.bin";
            this.dfu_image_size = i2;
        } else if (b == 3) {
            this.dfu_image_path = this.unzipFilePath + "/ImageUpdate.bin";
            this.dfu_image_size = i3;
        }
        int i4 = this.ota_selection;
        if (i4 >= 1 && i4 <= 4) {
            if (data.getValue()[0] == 3) {
                LockSupport.unpark(this.thread);
            }
        } else {
            this.ota_error = 1;
            EventBus.getDefault().post(new BleMessageEvent(2, "version_request", 1));
        }
    }

    private void create_ota_setting_transfer(int i) {
        byte[] bArr = {4, (byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i};
        BleManager bleManager = this.bleManager;
        bleManager.writeCharacteristic(bleManager.ius_cc, bArr).done(new SuccessCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.4
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback
            public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
                LockSupport.unpark(BleOTA.this.thread);
            }
        }).enqueue();
    }

    private void send_setting_data(byte[] bArr) {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda6
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$send_setting_data$6(bluetoothDevice, data);
            }
        });
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_rc, bArr).split(new WriteProgressCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.6
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.WriteProgressCallback
            public void onPacketSent(BluetoothDevice bluetoothDevice, byte[] bArr2, int i) {
            }
        }).done(new SuccessCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.5
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback
            public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$send_setting_data$6(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "send_setting_data", this.ota_error));
        }
        if (data.getValue()[0] == 4) {
            LockSupport.unpark(this.thread);
        }
    }

    private void create_ota_image_transfer(int i, int i2, int i3) {
        byte[] bArr = {5, (byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i, (byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2, (byte) (i3 >> 24), (byte) (i3 >> 16), (byte) (i3 >> 8), (byte) i3};
        BleManager bleManager = this.bleManager;
        bleManager.writeCharacteristic(bleManager.ius_cc, bArr).done(new SuccessCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.7
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback
            public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
                LockSupport.unpark(BleOTA.this.thread);
            }
        }).enqueue();
    }

    private void send_image_data(byte[] bArr) {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda4
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$send_image_data$7(bluetoothDevice, data);
            }
        });
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_rc, bArr).split(new WriteProgressCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.9
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.WriteProgressCallback
            public void onPacketSent(BluetoothDevice bluetoothDevice, byte[] bArr2, int i) {
                BleOTA.this.dfu_image_sent += ((byte[]) Objects.requireNonNull(bArr2)).length;
                EventBus.getDefault().post(new BleMessageEvent(7, (BleOTA.this.dfu_image_sent * 100) / BleOTA.this.dfu_image_size));
            }
        }).done(new SuccessCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA.8
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback
            public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$send_image_data$7(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "send_setting_data", this.ota_error));
        }
        if (data.getValue()[0] == 5) {
            LockSupport.unpark(this.thread);
        }
    }

    private void validate_new_image() {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda7
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$validate_new_image$8(bluetoothDevice, data);
            }
        });
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_cc, new byte[]{6}).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$validate_new_image$8(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "validate_new_image", this.ota_error));
        }
        if (data.getValue()[0] == 6) {
            LockSupport.unpark(this.thread);
        }
    }

    private void activate_new_image() {
        BleManager bleManager = this.bleManager;
        bleManager.setNotificationCallback(bleManager.ius_cc).with(new DataReceivedCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.BleOTA$$ExternalSyntheticLambda1
            @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
            public final void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
                this.f$0.lambda$activate_new_image$9(bluetoothDevice, data);
            }
        });
        BleManager bleManager2 = this.bleManager;
        bleManager2.writeCharacteristic(bleManager2.ius_cc, new byte[]{7}).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$activate_new_image$9(BluetoothDevice bluetoothDevice, Data data) {
        byte[] value = data.getValue();
        byte b = Objects.equals(value, null) ? (byte) 1 : value[1];
        this.ota_error = b;
        if (b > 0) {
            EventBus.getDefault().post(new BleMessageEvent(2, "activate_new_image", this.ota_error));
        }
        if (data.getValue()[0] == 7) {
            LockSupport.unpark(this.thread);
        }
    }
}

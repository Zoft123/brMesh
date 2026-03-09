package com.brgd.brblmesh.GeneralClass.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManager;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BleManager extends com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManager {
    public byte[] address;
    public BluetoothGattCharacteristic data_in_char;
    public BluetoothGattCharacteristic data_out_char;
    public BluetoothGattCharacteristic ius_cc;
    public BluetoothGattCharacteristic ius_rc;
    private int mtu;
    private static final UUID IUS_SERVICE_UUID = UUID.fromString("11110001-1111-1111-1111-111111111111");
    private static final UUID IUS_RC_UUID = UUID.fromString("11110002-1111-1111-1111-111111111111");
    private static final UUID IUS_CC_UUID = UUID.fromString("11110003-1111-1111-1111-111111111111");
    private static final UUID PROVISION_SERVICE_UUID = UUID.fromString("00001827-0000-1000-8000-00805F9B34FB");
    private static final UUID PROVISION_DATA_IN_UUID = UUID.fromString("00002ADC-0000-1000-8000-00805F9B34FB");
    private static final UUID PROVISION_DATA_OUT_UUID = UUID.fromString("00002ADB-0000-1000-8000-00805F9B34FB");

    public BleManager(Context context) {
        super(context);
        this.ius_rc = null;
        this.ius_cc = null;
        this.data_in_char = null;
        this.data_out_char = null;
        this.address = new byte[6];
        this.mtu = 23;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManager
    protected BleManager.BleManagerGattCallback getGattCallback() {
        return new gattCallback();
    }

    public int getMTU() {
        return this.mtu - 3;
    }

    public void setMTU(int i) {
        this.mtu = i;
    }

    private byte hexStringToByte(String str) {
        char[] charArray = str.toCharArray();
        return (byte) (toByte(charArray[1]) | (toByte(charArray[0]) << 4));
    }

    private byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public void setDeviceMac(String str) {
        String[] strArrSplit = str.split(":");
        for (int i = 0; i < 6; i++) {
            this.address[i] = hexStringToByte(strArrSplit[i]);
        }
    }

    public byte[] getDeviceMac() {
        return this.address;
    }

    private class gattCallback extends BleManager.BleManagerGattCallback {
        private gattCallback() {
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManagerHandler
        protected void initialize() {
            if (BleManager.this.ius_cc != null) {
                BleManager bleManager = BleManager.this;
                bleManager.enableNotifications(bleManager.ius_cc).enqueue();
            }
            if (BleManager.this.data_in_char != null) {
                BleManager bleManager2 = BleManager.this;
                bleManager2.enableNotifications(bleManager2.data_in_char).enqueue();
            }
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManagerHandler
        public boolean isRequiredServiceSupported(BluetoothGatt bluetoothGatt) {
            BluetoothGattService service = bluetoothGatt.getService(BleManager.IUS_SERVICE_UUID);
            if (service != null) {
                BleManager.this.ius_rc = service.getCharacteristic(BleManager.IUS_RC_UUID);
                BleManager.this.ius_cc = service.getCharacteristic(BleManager.IUS_CC_UUID);
            }
            BluetoothGattService service2 = bluetoothGatt.getService(BleManager.PROVISION_SERVICE_UUID);
            if (service2 == null) {
                return true;
            }
            BleManager.this.data_in_char = service2.getCharacteristic(BleManager.PROVISION_DATA_IN_UUID);
            BleManager.this.data_out_char = service2.getCharacteristic(BleManager.PROVISION_DATA_OUT_UUID);
            return true;
        }

        @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManagerHandler
        protected void onDeviceDisconnected() {
            BleManager.this.ius_rc = null;
            BleManager.this.ius_cc = null;
            BleManager.this.data_in_char = null;
            BleManager.this.data_out_char = null;
        }
    }
}

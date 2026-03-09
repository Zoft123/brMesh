package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface BleManagerCallbacks {

    /* JADX INFO: renamed from: com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManagerCallbacks$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        @Deprecated
        public static void $default$onBatteryValueReceived(BleManagerCallbacks _this, BluetoothDevice bluetoothDevice, int i) {
        }

        @Deprecated
        public static boolean $default$shouldEnableBatteryLevelNotifications(BleManagerCallbacks _this, BluetoothDevice bluetoothDevice) {
            return false;
        }
    }

    @Deprecated
    void onBatteryValueReceived(BluetoothDevice bluetoothDevice, int i);

    @Deprecated
    void onBonded(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onBondingFailed(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onBondingRequired(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onDeviceConnected(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onDeviceConnecting(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onDeviceDisconnected(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onDeviceDisconnecting(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onDeviceNotSupported(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onDeviceReady(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onError(BluetoothDevice bluetoothDevice, String str, int i);

    @Deprecated
    void onLinkLossOccurred(BluetoothDevice bluetoothDevice);

    @Deprecated
    void onServicesDiscovered(BluetoothDevice bluetoothDevice, boolean z);

    @Deprecated
    boolean shouldEnableBatteryLevelNotifications(BluetoothDevice bluetoothDevice);
}

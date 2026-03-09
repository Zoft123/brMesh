package com.brgd.brblmesh.GeneralClass.ble.bleTool.observer;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface ServerObserver {
    void onDeviceConnectedToServer(BluetoothDevice bluetoothDevice);

    void onDeviceDisconnectedFromServer(BluetoothDevice bluetoothDevice);

    void onServerReady();
}

package com.brgd.brblmesh.GeneralClass.ble.bleTool.callback;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface ConnectionPriorityCallback {
    void onConnectionUpdated(BluetoothDevice bluetoothDevice, int i, int i2, int i3);
}

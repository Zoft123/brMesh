package com.brgd.brblmesh.GeneralClass.ble.bleTool.callback;

import android.bluetooth.BluetoothDevice;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;

/* JADX INFO: loaded from: classes.dex */
public interface DataReceivedCallback {
    void onDataReceived(BluetoothDevice bluetoothDevice, Data data);
}

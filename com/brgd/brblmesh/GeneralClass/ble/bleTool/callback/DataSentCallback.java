package com.brgd.brblmesh.GeneralClass.ble.bleTool.callback;

import android.bluetooth.BluetoothDevice;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;

/* JADX INFO: loaded from: classes.dex */
public interface DataSentCallback {
    void onDataSent(BluetoothDevice bluetoothDevice, Data data);
}

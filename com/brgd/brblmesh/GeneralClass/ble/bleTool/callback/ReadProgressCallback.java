package com.brgd.brblmesh.GeneralClass.ble.bleTool.callback;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface ReadProgressCallback {
    void onPacketReceived(BluetoothDevice bluetoothDevice, byte[] bArr, int i);
}

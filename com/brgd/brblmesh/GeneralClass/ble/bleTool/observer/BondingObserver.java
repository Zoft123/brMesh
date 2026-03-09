package com.brgd.brblmesh.GeneralClass.ble.bleTool.observer;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface BondingObserver {
    void onBonded(BluetoothDevice bluetoothDevice);

    void onBondingFailed(BluetoothDevice bluetoothDevice);

    void onBondingRequired(BluetoothDevice bluetoothDevice);
}

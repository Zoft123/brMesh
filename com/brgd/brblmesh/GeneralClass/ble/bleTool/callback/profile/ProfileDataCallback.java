package com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile;

import android.bluetooth.BluetoothDevice;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;

/* JADX INFO: loaded from: classes.dex */
public interface ProfileDataCallback extends DataReceivedCallback {

    /* JADX INFO: renamed from: com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile.ProfileDataCallback$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onInvalidDataReceived(ProfileDataCallback _this, BluetoothDevice bluetoothDevice, Data data) {
        }
    }

    void onInvalidDataReceived(BluetoothDevice bluetoothDevice, Data data);
}

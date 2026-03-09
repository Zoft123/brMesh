package com.brgd.brblmesh.GeneralClass.ble.bleTool.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.RssiCallback;

/* JADX INFO: loaded from: classes.dex */
public class RssiResult implements RssiCallback, Parcelable {
    public static final Parcelable.Creator<RssiResult> CREATOR = new Parcelable.Creator<RssiResult>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.response.RssiResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RssiResult createFromParcel(Parcel parcel) {
            return new RssiResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RssiResult[] newArray(int i) {
            return new RssiResult[i];
        }
    };
    private BluetoothDevice device;
    private int rssi;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.RssiCallback
    public void onRssiRead(BluetoothDevice bluetoothDevice, int i) {
        this.device = bluetoothDevice;
        this.rssi = i;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    public int getRssi() {
        return this.rssi;
    }

    protected RssiResult(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.rssi = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeInt(this.rssi);
    }
}

package com.brgd.brblmesh.GeneralClass.ble.bleTool.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.MtuCallback;

/* JADX INFO: loaded from: classes.dex */
public class MtuResult implements MtuCallback, Parcelable {
    public static final Parcelable.Creator<MtuResult> CREATOR = new Parcelable.Creator<MtuResult>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.response.MtuResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MtuResult createFromParcel(Parcel parcel) {
            return new MtuResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MtuResult[] newArray(int i) {
            return new MtuResult[i];
        }
    };
    private BluetoothDevice device;
    private int mtu;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.MtuCallback
    public void onMtuChanged(BluetoothDevice bluetoothDevice, int i) {
        this.device = bluetoothDevice;
        this.mtu = i;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    public int getMtu() {
        return this.mtu;
    }

    protected MtuResult(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.mtu = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeInt(this.mtu);
    }
}

package com.brgd.brblmesh.GeneralClass.ble.bleTool.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;

/* JADX INFO: loaded from: classes.dex */
public class ReadResponse implements DataReceivedCallback, Parcelable {
    public static final Parcelable.Creator<ReadResponse> CREATOR = new Parcelable.Creator<ReadResponse>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.response.ReadResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReadResponse createFromParcel(Parcel parcel) {
            return new ReadResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReadResponse[] newArray(int i) {
            return new ReadResponse[i];
        }
    };
    private Data data;
    private BluetoothDevice device;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ReadResponse() {
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        this.device = bluetoothDevice;
        this.data = data;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    public Data getRawData() {
        return this.data;
    }

    protected ReadResponse(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.data = (Data) parcel.readParcelable(Data.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeParcelable(this.data, i);
    }
}

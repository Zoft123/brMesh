package com.brgd.brblmesh.GeneralClass.ble.bleTool.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataSentCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;

/* JADX INFO: loaded from: classes.dex */
public class WriteResponse implements DataSentCallback, Parcelable {
    public static final Parcelable.Creator<WriteResponse> CREATOR = new Parcelable.Creator<WriteResponse>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.response.WriteResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WriteResponse createFromParcel(Parcel parcel) {
            return new WriteResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WriteResponse[] newArray(int i) {
            return new WriteResponse[i];
        }
    };
    private Data data;
    private BluetoothDevice device;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataSentCallback
    public void onDataSent(BluetoothDevice bluetoothDevice, Data data) {
        this.device = bluetoothDevice;
        this.data = data;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    public Data getRawData() {
        return this.data;
    }

    protected WriteResponse(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.data = (Data) parcel.readParcelable(Data.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeParcelable(this.data, i);
    }
}

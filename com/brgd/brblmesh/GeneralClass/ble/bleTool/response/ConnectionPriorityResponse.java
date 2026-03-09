package com.brgd.brblmesh.GeneralClass.ble.bleTool.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.ConnectionPriorityCallback;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionPriorityResponse implements ConnectionPriorityCallback, Parcelable {
    public static final Parcelable.Creator<ConnectionPriorityResponse> CREATOR = new Parcelable.Creator<ConnectionPriorityResponse>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.response.ConnectionPriorityResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectionPriorityResponse createFromParcel(Parcel parcel) {
            return new ConnectionPriorityResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectionPriorityResponse[] newArray(int i) {
            return new ConnectionPriorityResponse[i];
        }
    };
    private BluetoothDevice device;
    private int interval;
    private int latency;
    private int supervisionTimeout;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.ConnectionPriorityCallback
    public void onConnectionUpdated(BluetoothDevice bluetoothDevice, int i, int i2, int i3) {
        this.device = bluetoothDevice;
        this.interval = i;
        this.latency = i2;
        this.supervisionTimeout = i3;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    public int getConnectionInterval() {
        return this.interval;
    }

    public int getSlaveLatency() {
        return this.latency;
    }

    public int getSupervisionTimeout() {
        return this.supervisionTimeout;
    }

    protected ConnectionPriorityResponse(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.interval = parcel.readInt();
        this.latency = parcel.readInt();
        this.supervisionTimeout = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeInt(this.interval);
        parcel.writeInt(this.latency);
        parcel.writeInt(this.supervisionTimeout);
    }
}

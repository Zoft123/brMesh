package com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.response.ReadResponse;

/* JADX INFO: loaded from: classes.dex */
public class ProfileReadResponse extends ReadResponse implements ProfileDataCallback, Parcelable {
    public static final Parcelable.Creator<ProfileReadResponse> CREATOR = new Parcelable.Creator<ProfileReadResponse>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile.ProfileReadResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProfileReadResponse createFromParcel(Parcel parcel) {
            return new ProfileReadResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProfileReadResponse[] newArray(int i) {
            return new ProfileReadResponse[i];
        }
    };
    private boolean valid;

    public ProfileReadResponse() {
        this.valid = true;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile.ProfileDataCallback
    public void onInvalidDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        this.valid = false;
    }

    public boolean isValid() {
        return this.valid;
    }

    protected ProfileReadResponse(Parcel parcel) {
        super(parcel);
        this.valid = true;
        this.valid = parcel.readByte() != 0;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.response.ReadResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.valid ? (byte) 1 : (byte) 0);
    }
}

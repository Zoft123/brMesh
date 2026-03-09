package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLEBaseResult implements Parcelable {
    public String msg;
    public int status;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEBaseResult() {
    }

    public BLEBaseResult(int i, String str) {
        this.status = i;
        this.msg = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.status);
        parcel.writeString(this.msg);
    }

    protected BLEBaseResult(Parcel parcel) {
        this.status = parcel.readInt();
        this.msg = parcel.readString();
    }
}

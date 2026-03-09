package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLERgbcwTimerInfo implements Parcelable {
    public static final Parcelable.Creator<BLERgbcwTimerInfo> CREATOR = new Parcelable.Creator<BLERgbcwTimerInfo>() { // from class: cn.com.broadlink.blelight.bean.BLERgbcwTimerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERgbcwTimerInfo createFromParcel(Parcel parcel) {
            return new BLERgbcwTimerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERgbcwTimerInfo[] newArray(int i) {
            return new BLERgbcwTimerInfo[i];
        }
    };
    public int bri;
    public int green;
    public int red;
    public BLERgbcwTimeInfo time;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.bri);
        parcel.writeInt(this.red);
        parcel.writeInt(this.green);
        parcel.writeParcelable(this.time, i);
    }

    public BLERgbcwTimerInfo() {
        this.time = new BLERgbcwTimeInfo();
    }

    protected BLERgbcwTimerInfo(Parcel parcel) {
        this.time = new BLERgbcwTimeInfo();
        this.bri = parcel.readInt();
        this.red = parcel.readInt();
        this.green = parcel.readInt();
        this.time = (BLERgbcwTimeInfo) parcel.readParcelable(BLERgbcwTimeInfo.class.getClassLoader());
    }
}

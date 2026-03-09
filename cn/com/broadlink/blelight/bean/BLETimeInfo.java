package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLETimeInfo implements Parcelable {
    public static final Parcelable.Creator<BLETimeInfo> CREATOR = new Parcelable.Creator<BLETimeInfo>() { // from class: cn.com.broadlink.blelight.bean.BLETimeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLETimeInfo createFromParcel(Parcel parcel) {
            return new BLETimeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLETimeInfo[] newArray(int i) {
            return new BLETimeInfo[i];
        }
    };
    public int enable;
    public int hour;
    public int min;
    public int onoff;
    public int repeat;
    public int res;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String parseTime() {
        return String.format("%02d:%02d", Integer.valueOf(this.hour), Integer.valueOf(this.min));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.hour);
        parcel.writeInt(this.enable);
        parcel.writeInt(this.onoff);
        parcel.writeInt(this.repeat);
        parcel.writeInt(this.min);
        parcel.writeInt(this.res);
    }

    public BLETimeInfo() {
    }

    protected BLETimeInfo(Parcel parcel) {
        this.hour = parcel.readInt();
        this.enable = parcel.readInt();
        this.onoff = parcel.readInt();
        this.repeat = parcel.readInt();
        this.min = parcel.readInt();
        this.res = parcel.readInt();
    }
}

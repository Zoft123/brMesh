package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import cn.com.broadlink.blelight.util.EDateUtils;

/* JADX INFO: loaded from: classes.dex */
public class BLETimeLcInfo implements Parcelable {
    public static final Parcelable.Creator<BLETimeLcInfo> CREATOR = new Parcelable.Creator<BLETimeLcInfo>() { // from class: cn.com.broadlink.blelight.bean.BLETimeLcInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLETimeLcInfo createFromParcel(Parcel parcel) {
            return new BLETimeLcInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLETimeLcInfo[] newArray(int i) {
            return new BLETimeLcInfo[i];
        }
    };
    public int adj;
    public int hour;
    public int min;
    public int res;
    public int week;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLETimeLcInfo() {
    }

    public void init() {
        this.week = EDateUtils.getWeek();
        this.hour = EDateUtils.getCurrrentHour();
        this.min = EDateUtils.getCurrrentMin();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.week);
        parcel.writeInt(this.adj);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.min);
        parcel.writeInt(this.res);
    }

    protected BLETimeLcInfo(Parcel parcel) {
        this.week = parcel.readInt();
        this.adj = parcel.readInt();
        this.hour = parcel.readInt();
        this.min = parcel.readInt();
        this.res = parcel.readInt();
    }
}

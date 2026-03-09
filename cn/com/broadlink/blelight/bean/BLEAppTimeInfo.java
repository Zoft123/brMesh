package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class BLEAppTimeInfo implements Parcelable {
    public static final Parcelable.Creator<BLEAppTimeInfo> CREATOR = new Parcelable.Creator<BLEAppTimeInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEAppTimeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEAppTimeInfo createFromParcel(Parcel parcel) {
            return new BLEAppTimeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEAppTimeInfo[] newArray(int i) {
            return new BLEAppTimeInfo[i];
        }
    };
    public int day;
    public int hour;
    public int min;
    public int month;
    public int sec;
    public int week;
    public int year;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
        parcel.writeInt(this.week);
        parcel.writeInt(this.hour);
        parcel.writeInt(this.min);
        parcel.writeInt(this.sec);
    }

    public BLEAppTimeInfo() {
    }

    public static BLEAppTimeInfo createDefault() {
        BLEAppTimeInfo bLEAppTimeInfo = new BLEAppTimeInfo();
        Calendar calendar = Calendar.getInstance();
        bLEAppTimeInfo.year = calendar.get(1) - 2000;
        bLEAppTimeInfo.month = calendar.get(2) + 1;
        bLEAppTimeInfo.day = calendar.get(5);
        bLEAppTimeInfo.week = calendar.get(7) - 1;
        bLEAppTimeInfo.hour = calendar.get(11);
        bLEAppTimeInfo.min = calendar.get(12);
        bLEAppTimeInfo.sec = calendar.get(13);
        return bLEAppTimeInfo;
    }

    protected BLEAppTimeInfo(Parcel parcel) {
        this.year = parcel.readInt();
        this.month = parcel.readInt();
        this.day = parcel.readInt();
        this.week = parcel.readInt();
        this.hour = parcel.readInt();
        this.min = parcel.readInt();
        this.sec = parcel.readInt();
    }
}

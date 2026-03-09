package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BLEWorkTimerAllInfo implements Parcelable {
    public static final Parcelable.Creator<BLEWorkTimerAllInfo> CREATOR = new Parcelable.Creator<BLEWorkTimerAllInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEWorkTimerAllInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEWorkTimerAllInfo createFromParcel(Parcel parcel) {
            return new BLEWorkTimerAllInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEWorkTimerAllInfo[] newArray(int i) {
            return new BLEWorkTimerAllInfo[i];
        }
    };
    public int addr;
    public int addrType;
    public BLETimeLcInfo lcTime;
    public int outBri;
    public ArrayList<BLEWorkTimeInfo> schedInfoList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEWorkTimerAllInfo() {
        this.schedInfoList = new ArrayList<>();
    }

    public BLEWorkTimerAllInfo(int i, int i2, boolean z) {
        this.schedInfoList = new ArrayList<>();
        this.addrType = i;
        this.addr = i2;
        BLETimeLcInfo bLETimeLcInfo = new BLETimeLcInfo();
        this.lcTime = bLETimeLcInfo;
        bLETimeLcInfo.init();
        this.lcTime.res = z ? 1 : 0;
    }

    public BLEWorkTimerAllInfo(int i, int i2, boolean z, ArrayList<BLEWorkTimeInfo> arrayList) {
        new ArrayList();
        this.addrType = i;
        this.addr = i2;
        this.schedInfoList = arrayList;
        BLETimeLcInfo bLETimeLcInfo = new BLETimeLcInfo();
        this.lcTime = bLETimeLcInfo;
        bLETimeLcInfo.init();
        this.lcTime.res = z ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.addrType);
        parcel.writeInt(this.addr);
        parcel.writeInt(this.outBri);
        parcel.writeTypedList(this.schedInfoList);
        parcel.writeParcelable(this.lcTime, i);
    }

    protected BLEWorkTimerAllInfo(Parcel parcel) {
        this.schedInfoList = new ArrayList<>();
        this.addrType = parcel.readInt();
        this.addr = parcel.readInt();
        this.outBri = parcel.readInt();
        this.schedInfoList = parcel.createTypedArrayList(BLEWorkTimeInfo.CREATOR);
        this.lcTime = (BLETimeLcInfo) parcel.readParcelable(BLETimeLcInfo.class.getClassLoader());
    }
}

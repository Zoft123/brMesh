package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BLETimerAllInfo implements Parcelable {
    public static final Parcelable.Creator<BLETimerAllInfo> CREATOR = new Parcelable.Creator<BLETimerAllInfo>() { // from class: cn.com.broadlink.blelight.bean.BLETimerAllInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLETimerAllInfo createFromParcel(Parcel parcel) {
            return new BLETimerAllInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLETimerAllInfo[] newArray(int i) {
            return new BLETimerAllInfo[i];
        }
    };
    public int addr;
    public BLETimeLcInfo lcTime;
    public ArrayList<BLETimeInfo> schedInfoList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLETimerAllInfo() {
        this.schedInfoList = new ArrayList<>();
    }

    public BLETimerAllInfo(int i, ArrayList<BLETimeInfo> arrayList) {
        new ArrayList();
        this.addr = i;
        this.schedInfoList = arrayList;
        BLETimeLcInfo bLETimeLcInfo = new BLETimeLcInfo();
        this.lcTime = bLETimeLcInfo;
        bLETimeLcInfo.init();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.addr);
        parcel.writeTypedList(this.schedInfoList);
        parcel.writeParcelable(this.lcTime, i);
    }

    protected BLETimerAllInfo(Parcel parcel) {
        this.schedInfoList = new ArrayList<>();
        this.addr = parcel.readInt();
        this.schedInfoList = parcel.createTypedArrayList(BLETimeInfo.CREATOR);
        this.lcTime = (BLETimeLcInfo) parcel.readParcelable(BLETimeLcInfo.class.getClassLoader());
    }
}

package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BLERadar24GAllInfo implements Parcelable {
    public static final Parcelable.Creator<BLERadar24GAllInfo> CREATOR = new Parcelable.Creator<BLERadar24GAllInfo>() { // from class: cn.com.broadlink.blelight.bean.BLERadar24GAllInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERadar24GAllInfo createFromParcel(Parcel parcel) {
            return new BLERadar24GAllInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERadar24GAllInfo[] newArray(int i) {
            return new BLERadar24GAllInfo[i];
        }
    };
    public int addr;
    public int addrType;
    public int onOff;
    public int outSceneId;
    public ArrayList<BLEWorkTimeInfo> schedInfoList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLERadar24GAllInfo() {
        this.schedInfoList = new ArrayList<>();
    }

    public BLERadar24GAllInfo(int i, int i2, int i3, int i4, ArrayList<BLEWorkTimeInfo> arrayList) {
        new ArrayList();
        this.onOff = i3;
        this.outSceneId = i4;
        this.addrType = i;
        this.addr = i2;
        this.schedInfoList = arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.addrType);
        parcel.writeInt(this.addr);
        parcel.writeInt(this.onOff);
        parcel.writeInt(this.outSceneId);
        parcel.writeTypedList(this.schedInfoList);
    }

    protected BLERadar24GAllInfo(Parcel parcel) {
        this.schedInfoList = new ArrayList<>();
        this.addrType = parcel.readInt();
        this.addr = parcel.readInt();
        this.onOff = parcel.readInt();
        this.outSceneId = parcel.readInt();
        this.schedInfoList = parcel.createTypedArrayList(BLEWorkTimeInfo.CREATOR);
    }
}

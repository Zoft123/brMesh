package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLEDelaySceneInfo implements Parcelable {
    public static final Parcelable.Creator<BLEDelaySceneInfo> CREATOR = new Parcelable.Creator<BLEDelaySceneInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEDelaySceneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEDelaySceneInfo createFromParcel(Parcel parcel) {
            return new BLEDelaySceneInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEDelaySceneInfo[] newArray(int i) {
            return new BLEDelaySceneInfo[i];
        }
    };
    public int cold;
    public int count;
    public int interval;
    public int start;
    public int step;
    public int time;
    public int warm;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEDelaySceneInfo() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.time);
        parcel.writeInt(this.start);
        parcel.writeInt(this.interval);
        parcel.writeInt(this.step);
        parcel.writeInt(this.count);
        parcel.writeInt(this.cold);
        parcel.writeInt(this.warm);
    }

    protected BLEDelaySceneInfo(Parcel parcel) {
        this.time = parcel.readInt();
        this.start = parcel.readInt();
        this.interval = parcel.readInt();
        this.step = parcel.readInt();
        this.count = parcel.readInt();
        this.cold = parcel.readInt();
        this.warm = parcel.readInt();
    }
}

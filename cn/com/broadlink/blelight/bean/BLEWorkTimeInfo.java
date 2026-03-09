package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLEWorkTimeInfo implements Parcelable, Cloneable {
    public static final Parcelable.Creator<BLEWorkTimeInfo> CREATOR = new Parcelable.Creator<BLEWorkTimeInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEWorkTimeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEWorkTimeInfo createFromParcel(Parcel parcel) {
            return new BLEWorkTimeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEWorkTimeInfo[] newArray(int i) {
            return new BLEWorkTimeInfo[i];
        }
    };
    public int enable;
    public int hourEnd;
    public int hourStart;
    public int index;
    public int minEnd;
    public int minStart;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEWorkTimeInfo() {
    }

    public BLEWorkTimeInfo(int i) {
        this.index = i;
    }

    public String parseTime() {
        return String.format("%02d:%02d - %02d:%02d", Integer.valueOf(this.hourStart), Integer.valueOf(this.minStart), Integer.valueOf(this.hourEnd), Integer.valueOf(this.minEnd));
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BLEWorkTimeInfo m483clone() throws CloneNotSupportedException {
        return (BLEWorkTimeInfo) super.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.enable);
        parcel.writeInt(this.index);
        parcel.writeInt(this.hourStart);
        parcel.writeInt(this.minStart);
        parcel.writeInt(this.hourEnd);
        parcel.writeInt(this.minEnd);
    }

    protected BLEWorkTimeInfo(Parcel parcel) {
        this.enable = parcel.readInt();
        this.index = parcel.readInt();
        this.hourStart = parcel.readInt();
        this.minStart = parcel.readInt();
        this.hourEnd = parcel.readInt();
        this.minEnd = parcel.readInt();
    }
}

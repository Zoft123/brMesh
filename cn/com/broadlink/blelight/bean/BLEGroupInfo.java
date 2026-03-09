package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLEGroupInfo implements Cloneable, Parcelable {
    public static final Parcelable.Creator<BLEGroupInfo> CREATOR = new Parcelable.Creator<BLEGroupInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGroupInfo createFromParcel(Parcel parcel) {
            return new BLEGroupInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGroupInfo[] newArray(int i) {
            return new BLEGroupInfo[i];
        }
    };
    public int groupId;
    public String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEGroupInfo() {
    }

    public BLEGroupInfo(int i, String str) {
        this.groupId = i;
        this.name = str;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BLEGroupInfo m481clone() throws CloneNotSupportedException {
        return (BLEGroupInfo) super.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.groupId);
        parcel.writeString(this.name);
    }

    protected BLEGroupInfo(Parcel parcel) {
        this.groupId = parcel.readInt();
        this.name = parcel.readString();
    }
}

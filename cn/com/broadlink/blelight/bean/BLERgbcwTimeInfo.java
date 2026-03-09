package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLERgbcwTimeInfo extends BLETimeInfo {
    public static final Parcelable.Creator<BLERgbcwTimeInfo> CREATOR = new Parcelable.Creator<BLERgbcwTimeInfo>() { // from class: cn.com.broadlink.blelight.bean.BLERgbcwTimeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERgbcwTimeInfo createFromParcel(Parcel parcel) {
            return new BLERgbcwTimeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERgbcwTimeInfo[] newArray(int i) {
            return new BLERgbcwTimeInfo[i];
        }
    };
    public int type;

    @Override // cn.com.broadlink.blelight.bean.BLETimeInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // cn.com.broadlink.blelight.bean.BLETimeInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.type);
    }

    public BLERgbcwTimeInfo() {
    }

    protected BLERgbcwTimeInfo(Parcel parcel) {
        super(parcel);
        this.type = parcel.readInt();
    }
}

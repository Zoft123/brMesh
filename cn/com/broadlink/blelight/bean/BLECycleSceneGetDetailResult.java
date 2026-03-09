package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLECycleSceneGetDetailResult extends BLEBaseResult implements Parcelable {
    public static final Parcelable.Creator<BLECycleSceneGetDetailResult> CREATOR = new Parcelable.Creator<BLECycleSceneGetDetailResult>() { // from class: cn.com.broadlink.blelight.bean.BLECycleSceneGetDetailResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneGetDetailResult createFromParcel(Parcel parcel) {
            return new BLECycleSceneGetDetailResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneGetDetailResult[] newArray(int i) {
            return new BLECycleSceneGetDetailResult[i];
        }
    };
    public BLECycleSceneInfo data;

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLECycleSceneGetDetailResult() {
    }

    public BLECycleSceneGetDetailResult(BLECycleSceneInfo bLECycleSceneInfo) {
        this.data = bLECycleSceneInfo;
    }

    public BLECycleSceneGetDetailResult(int i, String str) {
        super(i, str);
    }

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.data, i);
    }

    protected BLECycleSceneGetDetailResult(Parcel parcel) {
        super(parcel);
        this.data = (BLECycleSceneInfo) parcel.readParcelable(BLECycleSceneInfo.class.getClassLoader());
    }
}

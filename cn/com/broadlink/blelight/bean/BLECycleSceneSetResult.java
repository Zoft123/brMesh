package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLECycleSceneSetResult extends BLEBaseResult implements Parcelable {
    public static final Parcelable.Creator<BLECycleSceneSetResult> CREATOR = new Parcelable.Creator<BLECycleSceneSetResult>() { // from class: cn.com.broadlink.blelight.bean.BLECycleSceneSetResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneSetResult createFromParcel(Parcel parcel) {
            return new BLECycleSceneSetResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneSetResult[] newArray(int i) {
            return new BLECycleSceneSetResult[i];
        }
    };
    public int cscene_idx;

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLECycleSceneSetResult() {
    }

    public BLECycleSceneSetResult(int i, String str) {
        super(i, str);
    }

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.cscene_idx);
    }

    protected BLECycleSceneSetResult(Parcel parcel) {
        super(parcel);
        this.cscene_idx = parcel.readInt();
    }
}

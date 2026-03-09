package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLECycleSceneGetResult extends BLEBaseResult implements Parcelable {
    public static final Parcelable.Creator<BLECycleSceneGetResult> CREATOR = new Parcelable.Creator<BLECycleSceneGetResult>() { // from class: cn.com.broadlink.blelight.bean.BLECycleSceneGetResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneGetResult createFromParcel(Parcel parcel) {
            return new BLECycleSceneGetResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneGetResult[] newArray(int i) {
            return new BLECycleSceneGetResult[i];
        }
    };
    public List<BLECycleSceneInfo> list;

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLECycleSceneGetResult() {
        this.list = new ArrayList();
    }

    public BLECycleSceneGetResult(int i, String str) {
        super(i, str);
        this.list = new ArrayList();
    }

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.list);
    }

    protected BLECycleSceneGetResult(Parcel parcel) {
        super(parcel);
        this.list = new ArrayList();
        this.list = parcel.createTypedArrayList(BLECycleSceneInfo.CREATOR);
    }
}

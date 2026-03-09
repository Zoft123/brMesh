package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLEGWTimerGetResult extends BLEBaseResult implements Parcelable {
    public static final Parcelable.Creator<BLEGWTimerGetResult> CREATOR = new Parcelable.Creator<BLEGWTimerGetResult>() { // from class: cn.com.broadlink.blelight.bean.BLEGWTimerGetResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGWTimerGetResult createFromParcel(Parcel parcel) {
            return new BLEGWTimerGetResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGWTimerGetResult[] newArray(int i) {
            return new BLEGWTimerGetResult[i];
        }
    };
    public int index;
    public List<Integer> period_limits;
    public List<BLEGWTimerInfo> timerlist;
    public int total;
    public String ver;

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEGWTimerGetResult() {
        this.period_limits = new ArrayList();
        this.timerlist = new ArrayList();
    }

    public BLEGWTimerGetResult(int i, String str) {
        super(i, str);
        this.period_limits = new ArrayList();
        this.timerlist = new ArrayList();
    }

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.ver);
        parcel.writeInt(this.total);
        parcel.writeInt(this.index);
        parcel.writeList(this.period_limits);
        parcel.writeTypedList(this.timerlist);
    }

    protected BLEGWTimerGetResult(Parcel parcel) {
        super(parcel);
        this.period_limits = new ArrayList();
        this.timerlist = new ArrayList();
        this.ver = parcel.readString();
        this.total = parcel.readInt();
        this.index = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.period_limits = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader());
        this.timerlist = parcel.createTypedArrayList(BLEGWTimerInfo.CREATOR);
    }
}

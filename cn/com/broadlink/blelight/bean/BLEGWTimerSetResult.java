package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLEGWTimerSetResult extends BLEBaseResult implements Parcelable {
    public static final Parcelable.Creator<BLEGWTimerSetResult> CREATOR = new Parcelable.Creator<BLEGWTimerSetResult>() { // from class: cn.com.broadlink.blelight.bean.BLEGWTimerSetResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGWTimerSetResult createFromParcel(Parcel parcel) {
            return new BLEGWTimerSetResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGWTimerSetResult[] newArray(int i) {
            return new BLEGWTimerSetResult[i];
        }
    };
    public List<Integer> idlist;
    public String ver;

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEGWTimerSetResult() {
        this.idlist = new ArrayList();
    }

    public BLEGWTimerSetResult(int i, String str) {
        super(i, str);
        this.idlist = new ArrayList();
    }

    @Override // cn.com.broadlink.blelight.bean.BLEBaseResult, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.ver);
        parcel.writeList(this.idlist);
    }

    protected BLEGWTimerSetResult(Parcel parcel) {
        super(parcel);
        this.idlist = new ArrayList();
        this.ver = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.idlist = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader());
    }
}

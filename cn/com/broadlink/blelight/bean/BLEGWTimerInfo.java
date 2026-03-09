package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.core.motion.utils.TypedValues;

/* JADX INFO: loaded from: classes.dex */
public class BLEGWTimerInfo implements Parcelable {
    public static final Parcelable.Creator<BLEGWTimerInfo> CREATOR = new Parcelable.Creator<BLEGWTimerInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEGWTimerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGWTimerInfo createFromParcel(Parcel parcel) {
            return new BLEGWTimerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGWTimerInfo[] newArray(int i) {
            return new BLEGWTimerInfo[i];
        }
    };
    public String cmd;
    public int en;
    public int id;
    public String name;
    public String time;
    public String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEGWTimerInfo() {
        this.id = -1;
        this.type = TypedValues.CycleType.S_WAVE_PERIOD;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.en);
        parcel.writeString(this.name);
        parcel.writeString(this.type);
        parcel.writeString(this.time);
        parcel.writeString(this.cmd);
    }

    protected BLEGWTimerInfo(Parcel parcel) {
        this.id = -1;
        this.type = TypedValues.CycleType.S_WAVE_PERIOD;
        this.id = parcel.readInt();
        this.en = parcel.readInt();
        this.name = parcel.readString();
        this.type = parcel.readString();
        this.time = parcel.readString();
        this.cmd = parcel.readString();
    }
}

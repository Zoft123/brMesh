package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLECycleSceneInfo implements Parcelable, Cloneable {
    public static final Parcelable.Creator<BLECycleSceneInfo> CREATOR = new Parcelable.Creator<BLECycleSceneInfo>() { // from class: cn.com.broadlink.blelight.bean.BLECycleSceneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneInfo createFromParcel(Parcel parcel) {
            return new BLECycleSceneInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneInfo[] newArray(int i) {
            return new BLECycleSceneInfo[i];
        }
    };
    public int cnt;
    public List<BLECycleSceneCommand> commands;
    public boolean forward;
    public int idx;
    public String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLECycleSceneInfo() {
        this.cnt = 1;
        this.forward = true;
        this.commands = new ArrayList();
    }

    public BLECycleSceneInfo(int i, int i2, String str) {
        this.cnt = 1;
        this.forward = true;
        this.commands = new ArrayList();
        this.idx = i;
        this.cnt = i2;
        this.name = str;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BLECycleSceneInfo m479clone() throws CloneNotSupportedException {
        return (BLECycleSceneInfo) super.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.idx);
        parcel.writeInt(this.cnt);
        parcel.writeString(this.name);
        parcel.writeByte(this.forward ? (byte) 1 : (byte) 0);
        parcel.writeTypedList(this.commands);
    }

    protected BLECycleSceneInfo(Parcel parcel) {
        this.cnt = 1;
        this.forward = true;
        this.commands = new ArrayList();
        this.idx = parcel.readInt();
        this.cnt = parcel.readInt();
        this.name = parcel.readString();
        this.forward = parcel.readByte() != 0;
        this.commands = parcel.createTypedArrayList(BLECycleSceneCommand.CREATOR);
    }
}

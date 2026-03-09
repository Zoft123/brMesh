package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLECycleSceneCommand implements Parcelable, Cloneable {
    public static final Parcelable.Creator<BLECycleSceneCommand> CREATOR = new Parcelable.Creator<BLECycleSceneCommand>() { // from class: cn.com.broadlink.blelight.bean.BLECycleSceneCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneCommand createFromParcel(Parcel parcel) {
            return new BLECycleSceneCommand(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLECycleSceneCommand[] newArray(int i) {
            return new BLECycleSceneCommand[i];
        }
    };
    public String command;
    public int exeId;
    public short interval;
    public int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLECycleSceneCommand() {
        this.type = 0;
        this.interval = (short) 5;
    }

    public BLECycleSceneCommand(int i, short s, String str) {
        this.type = i;
        this.interval = s;
        this.command = str;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BLECycleSceneCommand m478clone() throws CloneNotSupportedException {
        return (BLECycleSceneCommand) super.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeInt(this.interval);
        parcel.writeString(this.command);
        parcel.writeInt(this.exeId);
    }

    protected BLECycleSceneCommand(Parcel parcel) {
        this.type = 0;
        this.interval = (short) 5;
        this.type = parcel.readInt();
        this.interval = (short) parcel.readInt();
        this.command = parcel.readString();
        this.exeId = parcel.readInt();
    }
}

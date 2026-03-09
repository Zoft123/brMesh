package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLERoomSceneInfo implements Cloneable, Parcelable {
    public static final Parcelable.Creator<BLERoomSceneInfo> CREATOR = new Parcelable.Creator<BLERoomSceneInfo>() { // from class: cn.com.broadlink.blelight.bean.BLERoomSceneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERoomSceneInfo createFromParcel(Parcel parcel) {
            return new BLERoomSceneInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLERoomSceneInfo[] newArray(int i) {
            return new BLERoomSceneInfo[i];
        }
    };
    public static final int TYPE_CURRENT = 2;
    public static final int TYPE_CUSTOM = 1;
    public static final int TYPE_ROOM_CTRL = 0;
    public int backColor;
    public String command;
    public String image;
    public boolean isChanged;
    public String name;
    public int orderInRoom;
    public int roomId;
    public int sceneId;
    public int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLERoomSceneInfo() {
        this.isChanged = false;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BLERoomSceneInfo m482clone() throws CloneNotSupportedException {
        return (BLERoomSceneInfo) super.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.sceneId);
        parcel.writeInt(this.roomId);
        parcel.writeInt(this.type);
        parcel.writeInt(this.orderInRoom);
        parcel.writeString(this.name);
        parcel.writeString(this.image);
        parcel.writeInt(this.backColor);
        parcel.writeString(this.command);
        parcel.writeByte(this.isChanged ? (byte) 1 : (byte) 0);
    }

    protected BLERoomSceneInfo(Parcel parcel) {
        this.isChanged = false;
        this.sceneId = parcel.readInt();
        this.roomId = parcel.readInt();
        this.type = parcel.readInt();
        this.orderInRoom = parcel.readInt();
        this.name = parcel.readString();
        this.image = parcel.readString();
        this.backColor = parcel.readInt();
        this.command = parcel.readString();
        this.isChanged = parcel.readByte() != 0;
    }
}

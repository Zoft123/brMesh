package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLESceneInfo implements Parcelable {
    public static final Parcelable.Creator<BLESceneInfo> CREATOR = new Parcelable.Creator<BLESceneInfo>() { // from class: cn.com.broadlink.blelight.bean.BLESceneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLESceneInfo createFromParcel(Parcel parcel) {
            return new BLESceneInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLESceneInfo[] newArray(int i) {
            return new BLESceneInfo[i];
        }
    };
    public int grayscale;
    public int jiffies;
    public int mode;
    public List<RgbStatus> statusList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLESceneInfo() {
        this.statusList = new ArrayList();
    }

    public byte[] parseScene() {
        byte[] bArr = new byte[this.statusList.size()];
        for (int i = 0; i < this.statusList.size(); i++) {
            RgbStatus rgbStatus = this.statusList.get(i);
            byte b = (byte) ((rgbStatus.cold ? 16 : 0) + (rgbStatus.warm ? 8 : 0) + (rgbStatus.green ? 4 : 0) + (rgbStatus.red ? 2 : 0) + (rgbStatus.blue ? 1 : 0));
            bArr[i] = b;
            if (i == 0) {
                int i2 = this.mode == 1 ? 128 : 0;
                if (this.grayscale == 1) {
                    i2 |= 64;
                }
                bArr[i] = (byte) (b + i2);
            }
        }
        return bArr;
    }

    public static class RgbStatus implements Parcelable {
        public static final Parcelable.Creator<RgbStatus> CREATOR = new Parcelable.Creator<RgbStatus>() { // from class: cn.com.broadlink.blelight.bean.BLESceneInfo.RgbStatus.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RgbStatus createFromParcel(Parcel parcel) {
                return new RgbStatus(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RgbStatus[] newArray(int i) {
                return new RgbStatus[i];
            }
        };
        public boolean blue;
        public boolean cold;
        public boolean green;
        public boolean red;
        public boolean warm;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RgbStatus() {
        }

        public RgbStatus(boolean z, boolean z2, boolean z3) {
            this.red = z;
            this.green = z2;
            this.blue = z3;
        }

        public RgbStatus(boolean z, boolean z2) {
            this.cold = z;
            this.warm = z2;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.red ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.green ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.blue ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.cold ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.warm ? (byte) 1 : (byte) 0);
        }

        protected RgbStatus(Parcel parcel) {
            this.red = parcel.readByte() != 0;
            this.green = parcel.readByte() != 0;
            this.blue = parcel.readByte() != 0;
            this.cold = parcel.readByte() != 0;
            this.warm = parcel.readByte() != 0;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.jiffies);
        parcel.writeInt(this.mode);
        parcel.writeInt(this.grayscale);
        parcel.writeTypedList(this.statusList);
    }

    protected BLESceneInfo(Parcel parcel) {
        this.statusList = new ArrayList();
        this.jiffies = parcel.readInt();
        this.mode = parcel.readInt();
        this.grayscale = parcel.readInt();
        this.statusList = parcel.createTypedArrayList(RgbStatus.CREATOR);
    }
}

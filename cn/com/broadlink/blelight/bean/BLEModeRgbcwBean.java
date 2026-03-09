package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BLEModeRgbcwBean implements Parcelable {
    public static final Parcelable.Creator<BLEModeRgbcwBean> CREATOR = new Parcelable.Creator<BLEModeRgbcwBean>() { // from class: cn.com.broadlink.blelight.bean.BLEModeRgbcwBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEModeRgbcwBean createFromParcel(Parcel parcel) {
            return new BLEModeRgbcwBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEModeRgbcwBean[] newArray(int i) {
            return new BLEModeRgbcwBean[i];
        }
    };
    public int b;
    public int c;
    public int g;
    public int r;
    public int w;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.r);
        parcel.writeInt(this.g);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.w);
    }

    public BLEModeRgbcwBean() {
    }

    public BLEModeRgbcwBean(int i, int i2, int i3) {
        this.r = i;
        this.g = i2;
        this.b = i3;
    }

    public BLEModeRgbcwBean(int i, int i2) {
        this.c = i;
        this.w = i2;
    }

    protected BLEModeRgbcwBean(Parcel parcel) {
        this.r = parcel.readInt();
        this.g = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.w = parcel.readInt();
    }
}

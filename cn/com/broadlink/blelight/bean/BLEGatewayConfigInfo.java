package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class BLEGatewayConfigInfo implements Parcelable {
    public static final Parcelable.Creator<BLEGatewayConfigInfo> CREATOR = new Parcelable.Creator<BLEGatewayConfigInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEGatewayConfigInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGatewayConfigInfo createFromParcel(Parcel parcel) {
            return new BLEGatewayConfigInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEGatewayConfigInfo[] newArray(int i) {
            return new BLEGatewayConfigInfo[i];
        }
    };
    private String host;
    private int hostId;
    private String password;
    private String ssid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEGatewayConfigInfo() {
    }

    public BLEGatewayConfigInfo(String str, String str2, int i) {
        this.ssid = str;
        this.password = str2;
        this.hostId = i;
    }

    public BLEGatewayConfigInfo(String str, String str2, String str3) {
        this.ssid = str;
        this.password = str2;
        this.host = str3;
    }

    public byte[] getSsidBytes() {
        String str = this.ssid;
        if (str == null) {
            return null;
        }
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public byte[] getPasswordBytes() {
        String str = this.password;
        if (str == null) {
            return null;
        }
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public int getHostId() {
        return this.hostId;
    }

    public void setHostId(int i) {
        this.hostId = i;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String str) {
        this.host = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.password);
        parcel.writeString(this.host);
        parcel.writeInt(this.hostId);
    }

    protected BLEGatewayConfigInfo(Parcel parcel) {
        this.ssid = parcel.readString();
        this.password = parcel.readString();
        this.host = parcel.readString();
        this.hostId = parcel.readInt();
    }
}

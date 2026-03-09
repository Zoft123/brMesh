package cn.com.broadlink.blelight.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import cn.com.broadlink.blelight.helper.BLEFastconHelper;
import cn.com.broadlink.blelight.util.UnsignedIntegerUtils;

/* JADX INFO: loaded from: classes.dex */
public class BLEDeviceInfo implements Cloneable, Parcelable {
    public static final Parcelable.Creator<BLEDeviceInfo> CREATOR = new Parcelable.Creator<BLEDeviceInfo>() { // from class: cn.com.broadlink.blelight.bean.BLEDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEDeviceInfo createFromParcel(Parcel parcel) {
            return new BLEDeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BLEDeviceInfo[] newArray(int i) {
            return new BLEDeviceInfo[i];
        }
    };
    private static final int META_PAD_AC = 4;
    private static final int META_PAD_CURTAIN = 2;
    private static final int META_PAD_IHG = 5;
    private static final int META_PAD_LIGHT = 1;
    private static final int META_PAD_SCENE = 0;
    private static final int META_PAD_SWITCH = 3;
    public int addr;
    public int cnt;
    public long createTime;
    public String did;
    public String extendInfo;
    public int groupId;
    public int high;
    public String key;
    public String mac;
    public String name;
    public int onlineState;
    public String sign;
    public String token;
    public int type;
    public String version;
    public String wifiDid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BLEDeviceInfo() {
        this.onlineState = -1;
    }

    public boolean supportDefaultScene() {
        if (BLEFastconHelper.isRelayPanel(this.type)) {
            return true;
        }
        return BLEFastconHelper.isLight(this.type) && parseVersionMask() >= 48;
    }

    public boolean supportFloor() {
        return parseVersionMask() >= 53;
    }

    public boolean supportFixedGroup() {
        return BLEFastconHelper.isLight(this.type) ? parseVersionMask() >= 54 : BLEFastconHelper.isCurtain(this.type) && parseVersionMask() >= 21;
    }

    public boolean supportController() {
        if (this.type == 43919) {
            return false;
        }
        return isSupportFun(0, 0);
    }

    public boolean supportTimer() {
        return isSupportFun(0, 1);
    }

    public boolean supportSceneControl() {
        return isSupportFun(0, 5);
    }

    public boolean supportRoomSceneMode() {
        return isSupportFun(0, 3);
    }

    public boolean supportColorSwitch() {
        return isSupportFun(0, 4);
    }

    public boolean supportColorJump() {
        return isSupportFun(1, 0);
    }

    public boolean supportRoomSceneExcept() {
        return isSupportFun(1, 1);
    }

    public boolean supportTempGroup() {
        return isSupportFun(1, 2);
    }

    public boolean supportStatusUpload() {
        return isSupportFun(1, 3);
    }

    public boolean supportGateway() {
        return isSupportFun(1, 4);
    }

    public boolean supportCwSwitch() {
        return isSupportFun(1, 5);
    }

    public boolean supportSyncBind() {
        return isSupportFun(2, 1);
    }

    public boolean supportSupperPanel() {
        return isSupportFun(2, 2);
    }

    public boolean supportAlexaEnable() {
        if (this.type == 43919) {
            return false;
        }
        return isSupportFun(3, 0);
    }

    public boolean supportWhiteMode() {
        return isSupportFun(3, 1);
    }

    public boolean supportDazzle() {
        return isSupportFun(3, 2);
    }

    public boolean supportGroupScene() {
        return isSupportFun(3, 3);
    }

    public boolean supportSlowRise() {
        return isSupportFun(3, 4);
    }

    public boolean supportRadarUartSetting() {
        return isSupportFun(3, 5);
    }

    public boolean supportPwrCut() {
        return isSupportFun(3, 6);
    }

    public boolean supportAtomSwitch() {
        return isSupportFun(3, 7);
    }

    public boolean supportCfgLock() {
        return isSupportFun(4, 0);
    }

    public boolean supportFanLight() {
        return isSupportFun(4, 1);
    }

    public boolean supportMicOpen() {
        return isSupportFun(4, 2);
    }

    public boolean supportGroupMainDev() {
        return isSupportFun(4, 4);
    }

    public boolean supportColorTimer() {
        return isSupportFun(4, 6);
    }

    public int metaPadSupportSceneCnt() {
        return parseMetaPadSupportCnt(0);
    }

    public int metaPadSupportSwitchCnt() {
        if (44199 == this.type || BLEFastconHelper.isRelaySupperPanel(this)) {
            return parseMetaPadSupportCnt(1);
        }
        return parseMetaPadSupportCnt(3);
    }

    public int metaPadSupportLightCnt() {
        return parseMetaPadSupportCnt(1);
    }

    public int metaPadSupportCurtainCnt() {
        return parseMetaPadSupportCnt(2);
    }

    public int metaPadSupportAcCnt() {
        return parseMetaPadSupportCnt(4);
    }

    public int metaPadSupportGWCnt() {
        return parseMetaPadSupportCnt(5);
    }

    public boolean isLineIhg() {
        return metaPadSupportGWCnt() == 1;
    }

    public boolean isIhgType() {
        return this.type == 10058;
    }

    public boolean supportBackLight() {
        return isSupportFun(2, 0);
    }

    public boolean supportLowPower() {
        if (this.type == 44199) {
            return isSupportFun(0, 0);
        }
        return false;
    }

    public int parseVersionMask() {
        try {
            return UnsignedIntegerUtils.parseUnsignedInt(this.version.split("\\.")[r0.length - 1]);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int minBrightness() {
        boolean zIsSupportFun = isSupportFun(0, 6);
        boolean zIsSupportFun2 = isSupportFun(0, 7);
        if (!zIsSupportFun && !zIsSupportFun2) {
            return 2;
        }
        if (!zIsSupportFun || zIsSupportFun2) {
            return (zIsSupportFun || !zIsSupportFun2) ? 20 : 13;
        }
        return 7;
    }

    public int sceneCntLimit() {
        int i = this.type;
        if (i == 10058 || i == 44602 || i == 44939 || i == 21028 || i == 45525) {
            return 999;
        }
        if (i != 43500 && i != 45106) {
            if (i == 43919) {
                return 16;
            }
            int i2 = ((isSupportFun(1, 7) ? 1 : 0) * 2) + (isSupportFun(1, 6) ? 1 : 0);
            i = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : 64 : 32 : 16 : 10;
            Log.i("jyq_limit", "sceneCntLimit: " + i);
        }
        return i;
    }

    public int parseFactoryCode() {
        try {
            return UnsignedIntegerUtils.parseUnsignedInt(this.version.split("\\.")[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean supportChangeAddr() {
        int i = this.type;
        return (i == 43049 || i == 43051) ? false : true;
    }

    public int parseVersionAbility1() {
        String str;
        if (!BLEFastconHelper.isSupperPanel(this.type) && (str = this.version) != null) {
            try {
                String[] strArrSplit = str.split("\\.");
                if (strArrSplit.length < 4) {
                    return 0;
                }
                if (strArrSplit.length == 5) {
                    return UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[3]);
                }
                return UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int parseVersionAbility2() {
        String str;
        if (!BLEFastconHelper.isSupperPanel(this.type) && (str = this.version) != null) {
            try {
                String[] strArrSplit = str.split("\\.");
                if (strArrSplit.length == 5) {
                    return UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[2]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private boolean isSupportFun(int i, int i2) {
        int unsignedInt;
        if (this.type == 43919) {
            return true;
        }
        String str = this.version;
        if (str == null) {
            return false;
        }
        try {
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit.length < 4) {
                return false;
            }
            if (strArrSplit.length != 5) {
                unsignedInt = UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[2]);
            } else if (i < 4) {
                unsignedInt = UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[3]);
            } else {
                unsignedInt = UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[2]);
                i -= 4;
            }
            return (((unsignedInt >> (i * 8)) & 255) & (1 << i2)) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int parseMetaPadSupportCnt(int i) {
        String str = this.version;
        if (str == null) {
            return 0;
        }
        try {
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit.length < 4) {
                return 0;
            }
            int unsignedInt = UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[3]);
            int unsignedInt2 = UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[2]);
            if (this.type == 44199) {
                int i2 = unsignedInt >> 24;
                int i3 = (i2 & 240) >> 4;
                int i4 = i2 & 15;
                if (i3 == i) {
                    return i4;
                }
            } else if (BLEFastconHelper.isRelaySupperPanel(this)) {
                int i5 = unsignedInt >> 24;
                int i6 = (i5 & 240) >> 4;
                int i7 = i5 & 15;
                if (i6 == i) {
                    return i7;
                }
                for (int i8 = 0; i8 < 2; i8++) {
                    int i9 = unsignedInt2 >> (i8 * 8);
                    int i10 = (i9 & 240) >> 4;
                    int i11 = i9 & 15;
                    if (i10 == i) {
                        return i11;
                    }
                }
            } else {
                for (int i12 = 0; i12 < 4; i12++) {
                    int i13 = unsignedInt >> (i12 * 8);
                    int i14 = (i13 & 240) >> 4;
                    int i15 = i13 & 15;
                    if (i14 == i) {
                        return i15;
                    }
                }
            }
            int unsignedInt3 = UnsignedIntegerUtils.parseUnsignedInt(strArrSplit[2]);
            for (int i16 = 0; i16 < 2; i16++) {
                int i17 = unsignedInt3 >> (i16 * 8);
                int i18 = (i17 & 240) >> 4;
                int i19 = i17 & 15;
                if (i18 == i) {
                    return i19;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean is4096() {
        return this.high == 3 || this.addr > 255;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BLEDeviceInfo m480clone() throws CloneNotSupportedException {
        return (BLEDeviceInfo) super.clone();
    }

    public String toString() {
        return "BLEDeviceInfo{did='" + this.did + "', onoff=" + this.addr + ", name='" + this.name + "', key='" + this.key + "', type=" + this.type + ", high=" + this.high + ", cnt=" + this.cnt + ", groupId=" + this.groupId + ", createTime=" + this.createTime + ", version='" + this.version + "', mac='" + this.mac + "', token='" + this.token + "', extendInfo='" + this.extendInfo + "', onlineState=" + this.onlineState + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.did);
        parcel.writeString(this.wifiDid);
        parcel.writeInt(this.addr);
        parcel.writeString(this.name);
        parcel.writeString(this.key);
        parcel.writeInt(this.type);
        parcel.writeInt(this.high);
        parcel.writeInt(this.cnt);
        parcel.writeInt(this.groupId);
        parcel.writeLong(this.createTime);
        parcel.writeString(this.version);
        parcel.writeString(this.mac);
        parcel.writeString(this.token);
        parcel.writeString(this.extendInfo);
        parcel.writeString(this.sign);
        parcel.writeInt(this.onlineState);
    }

    protected BLEDeviceInfo(Parcel parcel) {
        this.onlineState = -1;
        this.did = parcel.readString();
        this.wifiDid = parcel.readString();
        this.addr = parcel.readInt();
        this.name = parcel.readString();
        this.key = parcel.readString();
        this.type = parcel.readInt();
        this.high = parcel.readInt();
        this.cnt = parcel.readInt();
        this.groupId = parcel.readInt();
        this.createTime = parcel.readLong();
        this.version = parcel.readString();
        this.mac = parcel.readString();
        this.token = parcel.readString();
        this.extendInfo = parcel.readString();
        this.sign = parcel.readString();
        this.onlineState = parcel.readInt();
    }
}

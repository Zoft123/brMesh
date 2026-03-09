package com.brgd.brblmesh.GeneralClass.ble.bleTool.data;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public class Data implements Parcelable {
    public static final int FORMAT_FLOAT = 52;
    public static final int FORMAT_SFLOAT = 50;
    public static final int FORMAT_SINT16 = 34;
    public static final int FORMAT_SINT24 = 35;
    public static final int FORMAT_SINT32 = 36;
    public static final int FORMAT_SINT8 = 33;
    public static final int FORMAT_UINT16 = 18;
    public static final int FORMAT_UINT24 = 19;
    public static final int FORMAT_UINT32 = 20;
    public static final int FORMAT_UINT8 = 17;
    protected byte[] mValue;
    private static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Data createFromParcel(Parcel parcel) {
            return new Data(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Data[] newArray(int i) {
            return new Data[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface FloatFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LongFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ValueFormat {
    }

    public static int getTypeLen(int i) {
        return i & 15;
    }

    private static int unsignedByteToInt(byte b) {
        return b & 255;
    }

    private static long unsignedByteToLong(byte b) {
        return ((long) b) & 255;
    }

    private static int unsignedToSigned(int i, int i2) {
        int i3 = 1 << (i2 - 1);
        return (i & i3) != 0 ? (i3 - (i & (i3 - 1))) * (-1) : i;
    }

    private static long unsignedToSigned(long j, int i) {
        int i2 = 1 << (i - 1);
        long j2 = i2;
        return (j & j2) != 0 ? (j2 - (j & ((long) (i2 - 1)))) * (-1) : j;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Data() {
        this.mValue = null;
    }

    public Data(byte[] bArr) {
        this.mValue = bArr;
    }

    public static Data from(String str) {
        return new Data(str.getBytes());
    }

    public static Data from(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return new Data(bluetoothGattCharacteristic.getValue());
    }

    public static Data from(BluetoothGattDescriptor bluetoothGattDescriptor) {
        return new Data(bluetoothGattDescriptor.getValue());
    }

    public static Data opCode(byte b) {
        return new Data(new byte[]{b});
    }

    public static Data opCode(byte b, byte b2) {
        return new Data(new byte[]{b, b2});
    }

    public byte[] getValue() {
        return this.mValue;
    }

    public String getStringValue(int i) {
        byte[] bArr = this.mValue;
        if (bArr == null || i > bArr.length) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length - i];
        int i2 = 0;
        while (true) {
            byte[] bArr3 = this.mValue;
            if (i2 != bArr3.length - i) {
                bArr2[i2] = bArr3[i + i2];
                i2++;
            } else {
                return new String(bArr2);
            }
        }
    }

    public int size() {
        byte[] bArr = this.mValue;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    public String toString() {
        if (size() == 0) {
            return "";
        }
        char[] cArr = new char[(this.mValue.length * 3) - 1];
        int i = 0;
        while (true) {
            byte[] bArr = this.mValue;
            if (i < bArr.length) {
                byte b = bArr[i];
                int i2 = i * 3;
                char[] cArr2 = HEX_ARRAY;
                cArr[i2] = cArr2[(b & 255) >>> 4];
                cArr[i2 + 1] = cArr2[b & BSON.CODE_W_SCOPE];
                if (i != bArr.length - 1) {
                    cArr[i2 + 2] = '-';
                }
                i++;
            } else {
                return "(0x) ".concat(new String(cArr));
            }
        }
    }

    public Byte getByte(int i) {
        if (i + 1 > size()) {
            return null;
        }
        return Byte.valueOf(this.mValue[i]);
    }

    public Integer getIntValue(int i, int i2) {
        if (getTypeLen(i) + i2 > size()) {
            return null;
        }
        switch (i) {
            case 17:
                break;
            case 18:
                byte[] bArr = this.mValue;
                break;
            case 19:
                byte[] bArr2 = this.mValue;
                break;
            case 20:
                byte[] bArr3 = this.mValue;
                break;
            default:
                switch (i) {
                    case 34:
                        byte[] bArr4 = this.mValue;
                        break;
                    case 35:
                        byte[] bArr5 = this.mValue;
                        break;
                    case 36:
                        byte[] bArr6 = this.mValue;
                        break;
                }
                break;
        }
        return null;
    }

    public Long getLongValue(int i, int i2) {
        if (getTypeLen(i) + i2 > size()) {
            return null;
        }
        if (i == 20) {
            byte[] bArr = this.mValue;
            return Long.valueOf(unsignedBytesToLong(bArr[i2], bArr[i2 + 1], bArr[i2 + 2], bArr[i2 + 3]));
        }
        if (i != 36) {
            return null;
        }
        byte[] bArr2 = this.mValue;
        return Long.valueOf(unsignedToSigned(unsignedBytesToLong(bArr2[i2], bArr2[i2 + 1], bArr2[i2 + 2], bArr2[i2 + 3]), 32));
    }

    public Float getFloatValue(int i, int i2) {
        if (getTypeLen(i) + i2 > size()) {
            return null;
        }
        if (i == 50) {
            byte[] bArr = this.mValue;
            byte b = bArr[i2 + 1];
            if (b == 7 && bArr[i2] == -2) {
                return Float.valueOf(Float.POSITIVE_INFINITY);
            }
            if ((b == 7 && bArr[i2] == -1) || ((b == 8 && bArr[i2] == 0) || (b == 8 && bArr[i2] == 1))) {
                return Float.valueOf(Float.NaN);
            }
            if (b == 8 && bArr[i2] == 2) {
                return Float.valueOf(Float.NEGATIVE_INFINITY);
            }
            return Float.valueOf(bytesToFloat(bArr[i2], b));
        }
        if (i != 52) {
            return null;
        }
        byte[] bArr2 = this.mValue;
        byte b2 = bArr2[i2 + 3];
        if (b2 == 0) {
            byte b3 = bArr2[i2 + 2];
            if (b3 == 127 && bArr2[i2 + 1] == -1) {
                byte b4 = bArr2[i2];
                if (b4 == -2) {
                    return Float.valueOf(Float.POSITIVE_INFINITY);
                }
                if (b4 == -1) {
                    return Float.valueOf(Float.NaN);
                }
            } else if (b3 == -128 && bArr2[i2 + 1] == 0) {
                byte b5 = bArr2[i2];
                if (b5 == 0 || b5 == 1) {
                    return Float.valueOf(Float.NaN);
                }
                if (b5 == 2) {
                    return Float.valueOf(Float.NEGATIVE_INFINITY);
                }
            }
        }
        return Float.valueOf(bytesToFloat(bArr2[i2], bArr2[i2 + 1], bArr2[i2 + 2], b2));
    }

    private static int unsignedBytesToInt(byte b, byte b2) {
        return unsignedByteToInt(b) + (unsignedByteToInt(b2) << 8);
    }

    private static int unsignedBytesToInt(byte b, byte b2, byte b3, byte b4) {
        return unsignedByteToInt(b) + (unsignedByteToInt(b2) << 8) + (unsignedByteToInt(b3) << 16) + (unsignedByteToInt(b4) << 24);
    }

    private static long unsignedBytesToLong(byte b, byte b2, byte b3, byte b4) {
        return unsignedByteToLong(b) + (unsignedByteToLong(b2) << 8) + (unsignedByteToLong(b3) << 16) + (unsignedByteToLong(b4) << 24);
    }

    private static float bytesToFloat(byte b, byte b2) {
        return (float) (((double) unsignedToSigned(unsignedByteToInt(b) + ((unsignedByteToInt(b2) & 15) << 8), 12)) * Math.pow(10.0d, unsignedToSigned(unsignedByteToInt(b2) >> 4, 4)));
    }

    private static float bytesToFloat(byte b, byte b2, byte b3, byte b4) {
        return (float) (((double) unsignedToSigned(unsignedByteToInt(b) + (unsignedByteToInt(b2) << 8) + (unsignedByteToInt(b3) << 16), 24)) * Math.pow(10.0d, b4));
    }

    protected Data(Parcel parcel) {
        this.mValue = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.mValue);
    }
}

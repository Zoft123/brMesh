package org.bson.internal;

import java.util.UUID;
import org.bson.BSONException;
import org.bson.BsonBinarySubType;
import org.bson.BsonSerializationException;
import org.bson.UuidRepresentation;

/* JADX INFO: loaded from: classes4.dex */
public final class UuidHelper {
    private static void writeLongToArrayBigEndian(byte[] bArr, int i, long j) {
        bArr[i + 7] = (byte) (j & 255);
        bArr[i + 6] = (byte) ((j >> 8) & 255);
        bArr[i + 5] = (byte) ((j >> 16) & 255);
        bArr[i + 4] = (byte) ((j >> 24) & 255);
        bArr[i + 3] = (byte) ((j >> 32) & 255);
        bArr[i + 2] = (byte) ((j >> 40) & 255);
        bArr[i + 1] = (byte) ((j >> 48) & 255);
        bArr[i] = (byte) ((j >> 56) & 255);
    }

    private static long readLongFromArrayBigEndian(byte[] bArr, int i) {
        return ((((long) bArr[i]) & 255) << 56) | (((long) bArr[i + 7]) & 255) | ((((long) bArr[i + 6]) & 255) << 8) | ((((long) bArr[i + 5]) & 255) << 16) | ((((long) bArr[i + 4]) & 255) << 24) | ((((long) bArr[i + 3]) & 255) << 32) | ((((long) bArr[i + 2]) & 255) << 40) | ((((long) bArr[i + 1]) & 255) << 48);
    }

    private static void reverseByteArray(byte[] bArr, int i, int i2) {
        for (int i3 = (i2 + i) - 1; i < i3; i3--) {
            byte b = bArr[i];
            bArr[i] = bArr[i3];
            bArr[i3] = b;
            i++;
        }
    }

    public static byte[] encodeUuidToBinary(UUID uuid, UuidRepresentation uuidRepresentation) {
        byte[] bArr = new byte[16];
        writeLongToArrayBigEndian(bArr, 0, uuid.getMostSignificantBits());
        writeLongToArrayBigEndian(bArr, 8, uuid.getLeastSignificantBits());
        int i = AnonymousClass1.$SwitchMap$org$bson$UuidRepresentation[uuidRepresentation.ordinal()];
        if (i == 1) {
            reverseByteArray(bArr, 0, 4);
            reverseByteArray(bArr, 4, 2);
            reverseByteArray(bArr, 6, 2);
            return bArr;
        }
        if (i == 2) {
            reverseByteArray(bArr, 0, 8);
            reverseByteArray(bArr, 8, 8);
            return bArr;
        }
        if (i == 3 || i == 4) {
            return bArr;
        }
        throw new BSONException("Unexpected UUID representation: " + uuidRepresentation);
    }

    /* JADX INFO: renamed from: org.bson.internal.UuidHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$UuidRepresentation;

        static {
            int[] iArr = new int[UuidRepresentation.values().length];
            $SwitchMap$org$bson$UuidRepresentation = iArr;
            try {
                iArr[UuidRepresentation.C_SHARP_LEGACY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$UuidRepresentation[UuidRepresentation.JAVA_LEGACY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$UuidRepresentation[UuidRepresentation.PYTHON_LEGACY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$UuidRepresentation[UuidRepresentation.STANDARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static UUID decodeBinaryToUuid(byte[] bArr, byte b, UuidRepresentation uuidRepresentation) {
        if (bArr.length != 16) {
            throw new BsonSerializationException(String.format("Expected length to be 16, not %d.", Integer.valueOf(bArr.length)));
        }
        if (b == BsonBinarySubType.UUID_LEGACY.getValue()) {
            int i = AnonymousClass1.$SwitchMap$org$bson$UuidRepresentation[uuidRepresentation.ordinal()];
            if (i == 1) {
                reverseByteArray(bArr, 0, 4);
                reverseByteArray(bArr, 4, 2);
                reverseByteArray(bArr, 6, 2);
            } else if (i == 2) {
                reverseByteArray(bArr, 0, 8);
                reverseByteArray(bArr, 8, 8);
            } else if (i != 3) {
                if (i == 4) {
                    throw new BSONException("Can not decode a subtype 3 (UUID legacy) BSON binary when the decoder is configured to use the standard UUID representation");
                }
                throw new BSONException("Unexpected UUID representation: " + uuidRepresentation);
            }
        }
        return new UUID(readLongFromArrayBigEndian(bArr, 0), readLongFromArrayBigEndian(bArr, 8));
    }

    private UuidHelper() {
    }
}

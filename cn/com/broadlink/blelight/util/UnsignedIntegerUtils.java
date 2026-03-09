package cn.com.broadlink.blelight.util;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;

/* JADX INFO: loaded from: classes.dex */
public class UnsignedIntegerUtils {
    public static int parseUnsignedInt(String str) {
        return parseUnsignedInt(str, 10);
    }

    public static int parseUnsignedInt(String str, int i) throws NumberFormatException {
        if (str == null) {
            throw new NumberFormatException(GlobalVariable.nullColor);
        }
        int length = str.length();
        if (length > 0) {
            if (str.charAt(0) == '-') {
                throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s.", str));
            }
            if (length <= 5 || (i == 10 && length <= 9)) {
                return Integer.parseInt(str, i);
            }
            long j = Long.parseLong(str, i);
            if (((-4294967296L) & j) == 0) {
                return (int) j;
            }
            throw new NumberFormatException(String.format("String value %s exceeds range of unsigned int.", str));
        }
        throw new NumberFormatException(str);
    }
}

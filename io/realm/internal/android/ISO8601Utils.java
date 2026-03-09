package io.realm.internal.android;

import j$.util.DesugarTimeZone;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes4.dex */
public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC;
    private static final TimeZone TIMEZONE_Z;
    private static final String UTC_ID = "UTC";

    static {
        TimeZone timeZone = DesugarTimeZone.getTimeZone(UTC_ID);
        TIMEZONE_UTC = timeZone;
        TIMEZONE_Z = timeZone;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00e1 A[Catch: IllegalArgumentException -> 0x01ca, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01cc, IndexOutOfBoundsException -> 0x01ce, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01cc, blocks: (B:3:0x000c, B:5:0x001f, B:6:0x0021, B:8:0x002e, B:9:0x0030, B:11:0x003f, B:13:0x0045, B:17:0x005b, B:19:0x006b, B:20:0x006d, B:22:0x0079, B:24:0x007d, B:26:0x0083, B:30:0x008d, B:35:0x009d, B:37:0x00a5, B:49:0x00db, B:51:0x00e1, B:53:0x00e7, B:79:0x0190, B:59:0x00f8, B:60:0x010c, B:61:0x010d, B:63:0x011e, B:64:0x012f, B:66:0x0137, B:69:0x0140, B:71:0x015a, B:74:0x016b, B:75:0x0188, B:78:0x018e, B:81:0x01c2, B:82:0x01c9, B:42:0x00bf, B:43:0x00c2), top: B:98:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c2 A[Catch: IllegalArgumentException -> 0x01ca, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01cc, IndexOutOfBoundsException -> 0x01ce, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01cc, blocks: (B:3:0x000c, B:5:0x001f, B:6:0x0021, B:8:0x002e, B:9:0x0030, B:11:0x003f, B:13:0x0045, B:17:0x005b, B:19:0x006b, B:20:0x006d, B:22:0x0079, B:24:0x007d, B:26:0x0083, B:30:0x008d, B:35:0x009d, B:37:0x00a5, B:49:0x00db, B:51:0x00e1, B:53:0x00e7, B:79:0x0190, B:59:0x00f8, B:60:0x010c, B:61:0x010d, B:63:0x011e, B:64:0x012f, B:66:0x0137, B:69:0x0140, B:71:0x015a, B:74:0x016b, B:75:0x0188, B:78:0x018e, B:81:0x01c2, B:82:0x01c9, B:42:0x00bf, B:43:0x00c2), top: B:98:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Date parse(java.lang.String r20, java.text.ParsePosition r21) throws java.text.ParseException {
        /*
            Method dump skipped, instruction units count: 558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.android.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int iDigit = Character.digit(str.charAt(i), 10);
            if (iDigit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -iDigit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int iDigit2 = Character.digit(str.charAt(i4), 10);
            if (iDigit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - iDigit2;
            i4 = i5;
        }
        return -i3;
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}

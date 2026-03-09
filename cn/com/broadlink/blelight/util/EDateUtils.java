package cn.com.broadlink.blelight.util;

import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class EDateUtils {
    private EDateUtils() {
    }

    public static String formatTime(long j) {
        long j2 = j / 1000;
        return String.format("%02d:%02d", Long.valueOf(j / 60), Long.valueOf(j % 60));
    }

    public static String formatDate(int i, int i2, int i3) {
        return String.format("%02d%02d%02d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static String formatDate(long j) {
        Date date = new Date(j);
        return String.format("%04d%02d%02d-%02d%02d%02d", Integer.valueOf(date.getYear() + 1900), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(date.getDate()), Integer.valueOf(date.getHours()), Integer.valueOf(date.getMinutes()), Integer.valueOf(date.getSeconds()));
    }

    public static String formatDate(int i, int i2, int i3, int i4, int i5, int i6) {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6));
    }

    public static Date strToDate(String str, String str2) {
        try {
            return new SimpleDateFormat(str2).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date strToYearDate(String str) {
        return strToDate(str, "yyyy-MM-dd HH:mm:sss");
    }

    public static String getStringByFormat(Date date, String str) {
        try {
            return new SimpleDateFormat(str).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringByFormat(long j, String str) {
        try {
            return new SimpleDateFormat(str).format(Long.valueOf(j));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd");
    }

    public static String getCurrentDateFull() {
        return getCurrentDate(null);
    }

    public static String getCurrentDate(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            return new SimpleDateFormat(str).format(new GregorianCalendar().getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLeapYear(int i) {
        return (i % 4 == 0 && i % 400 != 0) || i % 400 == 0;
    }

    public static String toTime(int i, int i2) {
        return String.format("%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static String toTime(int i, int i2, int i3) {
        return String.format("%02d:%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static final int getCurrrentYear() {
        return Calendar.getInstance().get(1);
    }

    public static final int getCurrrentMonth() {
        return Calendar.getInstance().get(2);
    }

    public static final int getCurrrentDay() {
        return Calendar.getInstance().get(5);
    }

    public static final int getCurrrentHour() {
        return Calendar.getInstance().get(11);
    }

    public static final int getCurrrentMin() {
        return Calendar.getInstance().get(12);
    }

    public static final int getCurrrentSeconds() {
        return Calendar.getInstance().get(13);
    }

    public static final long dateToMillis(int i, int i2, int i3, int i4, int i5, int i6) {
        return dateToMillis(String.format("%d-%d-%d %d:%d:%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)));
    }

    public static int getWeekByDate() {
        int[] iArr = {0, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int i = calendar.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        return iArr[i];
    }

    public static final long dateToMillisyyyyMMdd_HHmmss(String str) {
        try {
            return new SimpleDateFormat("yyyyMMdd-HHmmss").parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public static final long dateToMillis(String str) {
        return dateToMillis("yyyy-MM-dd HH:mm:ss", str);
    }

    public static final long dateToMillis(String str, String str2) {
        try {
            return new SimpleDateFormat(str).parse(str2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public static final long dateToMill(int i, int i2, int i3, int i4, int i5, int i6) {
        return dateToMillis(i, i2, i3, i4, i5, i6);
    }

    public static final long dateToMillis(int i, int i2, int i3) {
        return dateToMillis(Calendar.getInstance().get(1), Calendar.getInstance().get(2) + 1, Calendar.getInstance().get(5), i, i2, i3);
    }

    public static final int[] millsToDateArray(long j) {
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new int[]{calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13)};
    }

    public static int getMonthByDate() {
        int[] iArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int i = calendar.get(2);
        if (i < 0) {
            i = 0;
        }
        return iArr[i];
    }

    public static int getWeek(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(3);
    }

    public static int getWeek() {
        int[] iArr = {0, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int i = calendar.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        return iArr[i];
    }

    public static String changeDateToPhoneDate(int i, int i2) {
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = (timeZone.getRawOffset() / 1000) / 3600;
        int rawOffset2 = ((timeZone.getRawOffset() / 1000) / 60) % 60;
        int i3 = i - (8 - rawOffset);
        if (i3 < 0) {
            if (rawOffset2 == 0) {
                i3 += 24;
            } else if (i2 >= 30) {
                i3 += 24;
                i2 -= 30;
            } else {
                i3 += 23;
                i2 += 30;
            }
        } else if (i3 < 24) {
            if (rawOffset2 != 0) {
                if (rawOffset2 == 45) {
                    if (i2 >= 15) {
                        i3++;
                        i2 -= 15;
                    }
                    i2 += 45;
                } else {
                    if (i2 >= 30) {
                        i3++;
                        i2 -= 30;
                    }
                    i2 += 30;
                }
            }
        } else if (rawOffset2 == 0) {
            i3 -= 24;
        } else if (rawOffset2 == 45) {
            if (i2 >= 15) {
                i3 -= 23;
                i2 -= 15;
            } else {
                i3 -= 24;
                i2 += 45;
            }
        } else if (i2 >= 30) {
            i3 -= 23;
            i2 -= 30;
        } else {
            i3 -= 24;
            i2 += 30;
        }
        return toTime(i3, i2);
    }

    public static int[] getNewWeeksFromPhoneToDevice(int[] iArr, int i) {
        int[] iArr2 = new int[7];
        if (i == 0) {
            return iArr;
        }
        if (i == 1) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (i2 == 0) {
                    iArr2[i2] = iArr[6];
                } else {
                    iArr2[i2] = iArr[i2 - 1];
                }
            }
        } else if (i == -1) {
            for (int i3 = 0; i3 < iArr.length; i3++) {
                if (i3 == 6) {
                    iArr2[i3] = iArr[0];
                } else {
                    iArr2[i3] = iArr[i3 + 1];
                }
            }
        }
        return iArr2;
    }

    public static int[] getNewWeeksFromDeviceToPhone(int[] iArr, int i) {
        int[] iArr2 = new int[7];
        if (i == 0) {
            return iArr;
        }
        if (i == 1) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (i2 == 6) {
                    iArr2[i2] = iArr[0];
                } else {
                    iArr2[i2] = iArr[i2 + 1];
                }
            }
        } else if (i == -1) {
            for (int i3 = 0; i3 < iArr.length; i3++) {
                if (i3 == 0) {
                    iArr2[i3] = iArr[6];
                } else {
                    iArr2[i3] = iArr[i3 - 1];
                }
            }
        }
        return iArr2;
    }

    public static ArrayList<Integer> weeksDiffDaySwitch(ArrayList<Integer> arrayList, int i) {
        if (arrayList == null || arrayList.isEmpty() || i == 0) {
            return arrayList;
        }
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            int iIntValue = arrayList.get(i2).intValue() + i;
            if (iIntValue > 7) {
                iIntValue -= 7;
            } else if (iIntValue < 1) {
                iIntValue += 7;
            }
            arrayList2.add(Integer.valueOf(iIntValue));
        }
        Collections.sort(arrayList2);
        return arrayList2;
    }

    public static int getDiffDay(long j, long j2) {
        return getWeekDayByMill(j) - getWeekDayByMill(j2);
    }

    public static int getWeekDayByMill(long j) {
        Calendar.getInstance().setTimeInMillis(j);
        return r0.get(7) - 1;
    }

    public static String getCurrentTimeZone() {
        return createGmtOffsetString(true, true, TimeZone.getDefault().getRawOffset());
    }

    private static String createGmtOffsetString(boolean z, boolean z2, int i) {
        char c;
        int i2 = i / 60000;
        if (i2 < 0) {
            i2 = -i2;
            c = '-';
        } else {
            c = '+';
        }
        StringBuilder sb = new StringBuilder(9);
        if (z) {
            sb.append("GMT");
        }
        sb.append(c);
        appendNumber(sb, 2, i2 / 60);
        if (z2) {
            sb.append(':');
        }
        appendNumber(sb, 2, i2 % 60);
        return sb.toString();
    }

    private static void appendNumber(StringBuilder sb, int i, int i2) {
        String string = Integer.toString(i2);
        for (int i3 = 0; i3 < i - string.length(); i3++) {
            sb.append('0');
        }
        sb.append(string);
    }

    public static Date changeTimeZone(Date date, TimeZone timeZone, TimeZone timeZone2) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime() - ((long) (timeZone.getRawOffset() - timeZone2.getRawOffset())));
    }

    public static Date getFirstDayOfWeek(int i, int i2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(1, i);
        gregorianCalendar.set(2, 0);
        gregorianCalendar.set(5, 1);
        GregorianCalendar gregorianCalendar2 = (GregorianCalendar) gregorianCalendar.clone();
        gregorianCalendar2.add(5, i2 * 7);
        return getFirstDayOfWeek(gregorianCalendar2.getTime());
    }

    public static Date getLastDayOfWeek(int i, int i2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(1, i);
        gregorianCalendar.set(2, 0);
        gregorianCalendar.set(5, 1);
        GregorianCalendar gregorianCalendar2 = (GregorianCalendar) gregorianCalendar.clone();
        gregorianCalendar2.add(5, i2 * 7);
        return getLastDayOfWeek(gregorianCalendar2.getTime());
    }

    public static Date getLastDayOfWeek(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(1);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(7, gregorianCalendar.getFirstDayOfWeek() + 6);
        return gregorianCalendar.getTime();
    }

    public static Date getFirstDayOfWeek(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(1);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(7, gregorianCalendar.getFirstDayOfWeek());
        return gregorianCalendar.getTime();
    }

    public static final long changeDataToMill(int i, int i2) {
        try {
            Date date = new Date(System.currentTimeMillis());
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.format("%d-%d-%d %d:%d:%d", Integer.valueOf(date.getYear() + 1900), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(date.getDate()), Integer.valueOf(i), Integer.valueOf(i2), 30)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public static final int getYearByMill(long j) {
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    public static final int getMonthByMill(long j) {
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static final int getDayByMill(long j) {
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static final int getHourByMill(long j) {
        return new Date(j).getHours();
    }

    public static final int getMinByMill(long j) {
        return new Date(j).getMinutes();
    }
}

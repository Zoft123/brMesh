package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import cn.com.broadlink.blelight.bean.BLEDeviceInfo;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.R;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public enum Tool {
    INSTANCE;

    private static final long TAI_OFFSET_SECOND = 946684800;

    public static boolean isToChoiceType() {
        return false;
    }

    public static boolean isToRadar() {
        return false;
    }

    public boolean isIP(String str) {
        if (str.length() < 7 || str.length() > 15) {
            return false;
        }
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (!isNUM(strArrSplit[i]) || strArrSplit[i].length() == 0 || Integer.parseInt(strArrSplit[i]) > 255 || Integer.parseInt(strArrSplit[i]) < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isNUM(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static int dpToPx(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static List<Integer> getScreenInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        ArrayList arrayList = new ArrayList();
        if (windowManager == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        arrayList.add(Integer.valueOf(i));
        arrayList.add(Integer.valueOf(i2));
        return arrayList;
    }

    public static List<Integer> getRealScreenInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        ArrayList arrayList = new ArrayList();
        if (windowManager == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        arrayList.add(Integer.valueOf(i));
        arrayList.add(Integer.valueOf(i2));
        return arrayList;
    }

    public static int getRealScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getRealScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static void setBottomNavigationItem(Context context, BottomNavigationBar bottomNavigationBar, int i, int i2, int i3) {
        for (Field field : bottomNavigationBar.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                try {
                    LinearLayout linearLayout = (LinearLayout) field.get(bottomNavigationBar);
                    if (linearLayout == null) {
                        return;
                    }
                    for (int i4 = 0; i4 < linearLayout.getChildCount(); i4++) {
                        View childAt = linearLayout.getChildAt(i4);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, dpToPx(context, 56.0f));
                        FrameLayout frameLayout = (FrameLayout) childAt.findViewById(R.id.fixed_bottom_navigation_container);
                        frameLayout.setLayoutParams(layoutParams);
                        frameLayout.setPadding(dpToPx(context, 10.0f), dpToPx(context, 0.0f), dpToPx(context, 10.0f), dpToPx(context, 0.0f));
                        TextView textView = (TextView) childAt.findViewById(R.id.fixed_bottom_navigation_title);
                        textView.setTextSize(1, i3);
                        textView.setIncludeFontPadding(false);
                        textView.setPadding(0, 0, 0, dpToPx(context, 18 - i3));
                        ImageView imageView = (ImageView) childAt.findViewById(R.id.fixed_bottom_navigation_icon);
                        float f = i2;
                        try {
                            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(dpToPx(context, f), dpToPx(context, f));
                            try {
                                layoutParams2.setMargins(0, 15, 0, i);
                                layoutParams2.gravity = 81;
                                imageView.setLayoutParams(layoutParams2);
                            } catch (IllegalAccessException e) {
                                e = e;
                                Log.d("printStackTrace", "printStackTrace" + e);
                            }
                        } catch (IllegalAccessException e2) {
                            e = e2;
                        }
                    }
                } catch (IllegalAccessException e3) {
                    e = e3;
                }
            }
        }
    }

    public static String stringToHexString(String str) {
        char[] charArray = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (byte b : str.getBytes()) {
            sb.append(charArray[(b & 240) >> 4]);
            sb.append(charArray[b & BSON.CODE_W_SCOPE]);
        }
        return sb.toString().trim();
    }

    public static String bytes2HexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = GlobalVariable.ILLUMINATION + hexString;
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] hexString2Bytes(String str) {
        if (str == null || str.equals("") || str.length() % 2 != 0) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = upperCase.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String byteToString(byte b) {
        String hexString = Integer.toHexString(b & 255);
        if (hexString.length() != 1) {
            return hexString;
        }
        return GlobalVariable.ILLUMINATION + hexString;
    }

    public static String intToHEXString(int i, int i2) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 == 1) {
            hexString = GlobalVariable.ILLUMINATION + hexString;
        }
        return patchHexString(hexString.toUpperCase(), i2);
    }

    public static String patchHexString(String str, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i - str.length(); i2++) {
            sb.insert(0, GlobalVariable.ILLUMINATION);
        }
        return (((Object) sb) + str).substring(0, i);
    }

    public static int bytesToInt2(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    public static int intToBitInt(int i) {
        return (int) Math.pow(2.0d, i - 1);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int count = adapter.getCount();
        int measuredHeight = 0;
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, null, listView);
            view.measure(0, 0);
            measuredHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = measuredHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
    }

    public static boolean isKeyboardShow(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return ((float) (view.getBottom() - rect.bottom)) > view.getResources().getDisplayMetrics().density * 100.0f;
    }

    public static String getCurrentHour() {
        return new SimpleDateFormat("HH", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentMinute() {
        return new SimpleDateFormat("mm", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static long getTaiTime() {
        return (Calendar.getInstance().getTimeInMillis() / 1000) - TAI_OFFSET_SECOND;
    }

    public static int getZoneOffset() {
        return ((((Calendar.getInstance().get(15) + Calendar.getInstance().get(16)) / 60) / 1000) / 15) + 64;
    }

    public static boolean checkStringIsSpaces(String str) {
        if (str.charAt(0) == ' ' || str.charAt(str.length() - 1) == ' ') {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ' && str.charAt(i + 1) == ' ') {
                return true;
            }
        }
        return false;
    }

    public static File getAppFile(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return context.getExternalFilesDir("");
        }
        return context.getFilesDir();
    }

    public static void deleteFile(Context context, String str) {
        File file = new File(getAppFile(context), str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String getFileName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
    }

    public static boolean checkBleCASPermission() {
        if (Build.VERSION.SDK_INT < 31) {
            return true;
        }
        String[] strArr = {"android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.BLUETOOTH_SCAN"};
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            String str = strArr[i];
            if (ContextCompat.checkSelfPermission(GlobalApplication.getMyApplication(), str) != 0) {
                arrayList.add(str);
            }
        }
        return arrayList.size() == 0;
    }

    public static boolean checkLocationPermission() {
        String[] strArr = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 2; i++) {
            String str = strArr[i];
            if (ContextCompat.checkSelfPermission(GlobalApplication.getMyApplication(), str) != 0) {
                arrayList.add(str);
            }
        }
        return arrayList.size() == 0;
    }

    public static boolean checkReadStoragePermission() {
        String[] strArr;
        if (Build.VERSION.SDK_INT >= 34) {
            strArr = new String[]{"android.permission.READ_MEDIA_VISUAL_USER_SELECTED"};
        } else if (Build.VERSION.SDK_INT == 33) {
            strArr = new String[]{"android.permission.READ_MEDIA_IMAGES"};
        } else {
            strArr = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(GlobalApplication.getMyApplication(), str) != 0) {
                arrayList.add(str);
            }
        }
        return arrayList.size() == 0;
    }

    public static boolean checkCameraPermission() {
        ArrayList arrayList = new ArrayList();
        String str = new String[]{"android.permission.CAMERA"}[0];
        if (ContextCompat.checkSelfPermission(GlobalApplication.getMyApplication(), str) != 0) {
            arrayList.add(str);
        }
        return arrayList.size() == 0;
    }

    public static boolean checkMusicPermission() {
        String[] strArr;
        if (Build.VERSION.SDK_INT >= 33) {
            strArr = new String[]{"android.permission.READ_MEDIA_AUDIO", "android.permission.RECORD_AUDIO"};
        } else {
            strArr = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"};
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(GlobalApplication.getMyApplication(), str) != 0) {
                arrayList.add(str);
            }
        }
        return arrayList.size() == 0;
    }

    public static boolean checkMicroPhonePermission() {
        ArrayList arrayList = new ArrayList();
        String str = new String[]{"android.permission.RECORD_AUDIO"}[0];
        if (ContextCompat.checkSelfPermission(GlobalApplication.getMyApplication(), str) != 0) {
            arrayList.add(str);
        }
        return arrayList.size() == 0;
    }

    public static boolean checkLocationServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        return locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }

    public static String formatTime(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        if (i3 < 10) {
            return (i2 / 60) + ":0" + i3;
        }
        return (i2 / 60) + ":" + i3;
    }

    public static String getRandomString(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("0123456789".charAt(random.nextInt(10)));
        }
        return sb.toString();
    }

    public static int[] getListToArray(List<Integer> list) {
        Collections.sort(list, new Comparator<Integer>() { // from class: com.brgd.brblmesh.GeneralClass.Tool.1
            @Override // java.util.Comparator
            public int compare(Integer num, Integer num2) {
                return num.compareTo(num2);
            }
        });
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public static int getRandomColor() {
        return new int[]{-65536, CustomColor.GREEN, CustomColor.BLUE, -256, CustomColor.CYAN, CustomColor.MAGENTA}[new Random().nextInt(6)];
    }

    public static int getRandomColor1(List<Integer> list) {
        if (list.size() == 0) {
            return -1;
        }
        return list.get(new Random().nextInt(list.size())).intValue();
    }

    public static boolean isAPI31() {
        return Build.VERSION.SDK_INT > 30;
    }

    public static boolean isZh() {
        return GlobalApplication.getMyApplication().getApplicationContext().getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }

    public static boolean isRGBW(int i) {
        int iRed = Color.red(i);
        int iGreen = Color.green(i);
        int iBlue = Color.blue(i);
        int iAbs = Math.abs(iRed - iGreen);
        int iAbs2 = Math.abs(iRed - iBlue);
        int iAbs3 = Math.abs(iGreen - iBlue);
        return iRed != 0 && iGreen != 0 && iBlue != 0 && iAbs >= 0 && iAbs <= 40 && iAbs2 >= 0 && iAbs2 <= 40 && iAbs3 >= 0 && iAbs3 <= 40;
    }

    public static String getTimeStr(int i) {
        if (i < 10) {
            return GlobalVariable.ILLUMINATION + i;
        }
        return String.valueOf(i);
    }

    public static boolean isNot65V(String str) {
        return str != null && getVersionType(str, 0) == 6 && getVersionType(str, 4) < 65;
    }

    public static boolean bleNotOnToast(Context context) {
        if (GlobalBluetooth.getInstance().isBleEnable()) {
            return false;
        }
        GlobalToast.showText(context, com.brgd.brblmesh.R.string.enableBle, 1);
        return true;
    }

    public static String colorToHexStr(int i) {
        return String.format("%06X", Integer.valueOf(i & ViewCompat.MEASURED_SIZE_MASK));
    }

    public static int hexStrToColor(String str) {
        return Color.parseColor("#" + str);
    }

    public static int getVersionType(String str, int i) {
        if (str == null) {
            return 0;
        }
        return Integer.parseInt(str.split("\\.")[i]);
    }

    public static String formatMacAddress(String str) {
        if (str == null || str.length() != 12) {
            return null;
        }
        String str2 = str + "!";
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < str2.length() - 1) {
            int i2 = i + 2;
            arrayList.add(str2.substring(i, i2));
            i = i2;
        }
        StringBuilder sb = new StringBuilder();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            sb.append((String) arrayList.get(size));
            if (size > 0) {
                sb.append(":");
            }
        }
        return sb.toString().toUpperCase();
    }

    public static String incrementLastOctet(String str) {
        String[] strArrSplit = str.split(":");
        strArrSplit[strArrSplit.length - 1] = String.format("%02X", Integer.valueOf((Integer.parseInt(strArrSplit[strArrSplit.length - 1], 16) + 1) % 256));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArrSplit.length; i++) {
            sb.append(strArrSplit[i]);
            if (i < strArrSplit.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

    public static File findFile(Context context, String str) {
        File[] fileArrListFiles = getAppFile(context).listFiles();
        if (fileArrListFiles == null) {
            return null;
        }
        for (File file : fileArrListFiles) {
            if (file.isFile() && file.getName().contains(str)) {
                return file;
            }
        }
        return null;
    }

    public static String updateNewVersion(String str) {
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length <= 0) {
            return null;
        }
        strArrSplit[strArrSplit.length - 1] = String.valueOf(Integer.parseInt(strArrSplit[strArrSplit.length - 1]) + 1);
        return Tool$$ExternalSyntheticBackport0.m(".", strArrSplit);
    }

    public static String getAreaHost() {
        if ("US".equals(Locale.getDefault().getCountry())) {
            return GlobalVariable.US_HOST;
        }
        return GlobalVariable.EUROPE_HOST;
    }

    public static boolean isSupportTimerStatus(BLEDeviceInfo bLEDeviceInfo) {
        int versionType = getVersionType(bLEDeviceInfo.version, 0);
        if (versionType == 4) {
            return getVersionType(bLEDeviceInfo.version, 4) > 53;
        }
        if (versionType == 6) {
            return bLEDeviceInfo.supportColorTimer();
        }
        return versionType == 8;
    }

    public static boolean isSupportModeLightness(BLEDeviceInfo bLEDeviceInfo) {
        int versionType = getVersionType(bLEDeviceInfo.version, 0);
        return versionType == 4 ? getVersionType(bLEDeviceInfo.version, 4) > 53 : versionType == 6 ? getVersionType(bLEDeviceInfo.version, 4) > 68 : versionType == 8;
    }

    public static boolean isChip0912_20(String str) {
        int versionType = getVersionType(str, 0);
        return versionType == 4 || versionType == 8;
    }
}

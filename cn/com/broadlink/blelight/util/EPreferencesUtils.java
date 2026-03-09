package cn.com.broadlink.blelight.util;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class EPreferencesUtils {
    public static String PREFERENCE_NAME = "common_pref";

    private EPreferencesUtils() {
        throw new AssertionError();
    }

    public static boolean putString(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editorEdit.putString(str, str2);
        return editorEdit.commit();
    }

    public static String getString(Context context, String str) {
        return getString(context, str, null);
    }

    public static String getString(Context context, String str, String str2) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(str, str2);
    }

    public static boolean putInt(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editorEdit.putInt(str, i);
        return editorEdit.commit();
    }

    public static int getInt(Context context, String str) {
        return getInt(context, str, 0);
    }

    public static int getInt(Context context, String str, int i) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(str, i);
    }

    public static boolean putLong(Context context, String str, long j) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editorEdit.putLong(str, j);
        return editorEdit.commit();
    }

    public static long getLong(Context context, String str) {
        return getLong(context, str, -1L);
    }

    public static long getLong(Context context, String str, long j) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getLong(str, j);
    }

    public static boolean putFloat(Context context, String str, float f) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editorEdit.putFloat(str, f);
        return editorEdit.commit();
    }

    public static float getFloat(Context context, String str) {
        return getFloat(context, str, -1.0f);
    }

    public static float getFloat(Context context, String str, float f) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getFloat(str, f);
    }

    public static boolean putBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editorEdit.putBoolean(str, z);
        return editorEdit.commit();
    }

    public static boolean getBoolean(Context context, String str) {
        return getBoolean(context, str, false);
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(str, z);
    }

    public static boolean clearAll(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREFERENCE_NAME, 0).edit();
        editorEdit.clear();
        return editorEdit.commit();
    }
}

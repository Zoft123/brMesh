package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class SharePreferenceUtils {
    private static final String FILE_NAME = "setting";

    public static void put(Context context, String str, Object obj) {
        SharedPreferences.Editor editor = getEditor(context);
        if (obj instanceof String) {
            editor.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Boolean) {
            editor.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Float) {
            editor.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Long) {
            editor.putLong(str, ((Long) obj).longValue());
        } else {
            editor.putString(str, obj.toString());
        }
        editor.commit();
    }

    public static Object get(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        if (obj instanceof String) {
            return sharedPreferences.getString(str, (String) obj);
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue()));
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if (obj instanceof Float) {
            return Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue()));
        }
        if (obj instanceof Long) {
            return Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue()));
        }
        return null;
    }

    public static void remove(Context context, String str) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(str);
        editor.commit();
    }

    public static boolean contains(Context context, String str) {
        return context.getSharedPreferences(FILE_NAME, 0).contains(str);
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.commit();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(FILE_NAME, 0).edit();
    }
}

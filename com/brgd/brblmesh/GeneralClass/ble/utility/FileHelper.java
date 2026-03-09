package com.brgd.brblmesh.GeneralClass.ble.utility;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class FileHelper {
    public static String getFilePathByUri(Context context, Uri uri) {
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
                }
                if (isMediaDocument(uri)) {
                    String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = strArrSplit2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
                }
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) throws Throwable {
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                        throw th;
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getFileNameNoEx(String str) {
        int iLastIndexOf;
        return (str == null || str.length() <= 0 || (iLastIndexOf = str.lastIndexOf(46)) <= -1 || iLastIndexOf >= str.length()) ? str : str.substring(0, iLastIndexOf);
    }

    public static void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                deleteRecursive(file2);
            }
        }
        file.delete();
    }

    public static String readFile(String str) throws Throwable {
        int i;
        FileInputStream fileInputStream = null;
        FileInputStream fileInputStream2 = null;
        fileInputStream = null;
        if (!new File(str).exists()) {
            return null;
        }
        String str2 = "";
        try {
            try {
                try {
                    FileInputStream fileInputStream3 = new FileInputStream(str);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            i = fileInputStream3.read(bArr);
                            if (i == -1) {
                                break;
                            }
                            str2 = str2 + new String(bArr, 0, i);
                        }
                        fileInputStream3.close();
                        fileInputStream = i;
                    } catch (Exception e) {
                        e = e;
                        fileInputStream2 = fileInputStream3;
                        e.printStackTrace();
                        fileInputStream2.close();
                        fileInputStream = fileInputStream2;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream3;
                        try {
                            fileInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            return str2;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static byte[] readFile(String str, int i, int i2) {
        byte[] bArr;
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        if (i2 != 0) {
            bArr = new byte[i2];
        } else {
            bArr = new byte[(int) file.length()];
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            dataInputStream.skip(i);
            dataInputStream.read(bArr, 0, bArr.length);
            dataInputStream.close();
            fileInputStream.close();
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isCheckSDCardWarning() {
        return !isSDCardAvailable();
    }

    public static boolean createDir(String str) {
        if (isCheckSDCardWarning() || TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file.exists()) {
            return true;
        }
        file.mkdirs();
        return true;
    }

    public static File createFile(String str, String str2) {
        if (!createDir(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        File file = new File(str, str2);
        if (file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException unused) {
            return null;
        }
    }

    public static File createFile(String str) {
        if (TextUtils.isEmpty(str) || isCheckSDCardWarning()) {
            return null;
        }
        File file = new File(str);
        if (file.exists()) {
            return file;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException unused) {
            return null;
        }
    }

    public static boolean isFileExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.isFile();
    }

    public static void deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static File createNewFile(String str, String str2) {
        if (isCheckSDCardWarning()) {
            return null;
        }
        File file = new File(str, str2);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException unused) {
        }
        return file;
    }

    public static String getSDCardAppCachePath(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            return null;
        }
        return externalCacheDir.getAbsolutePath();
    }

    public static String getExternalStorageDirectory(Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return null;
        }
        return externalStorageDirectory.getAbsolutePath();
    }

    public static void writeFile(String str, String str2) {
        try {
            FileWriter fileWriter = new FileWriter(str, true);
            fileWriter.write(str2);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

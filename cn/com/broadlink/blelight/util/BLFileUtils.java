package cn.com.broadlink.blelight.util;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public final class BLFileUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public interface OnReplaceListener {
        boolean onReplace();
    }

    private BLFileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    public static boolean isFileExists(String str) {
        return isFileExists(getFileByPath(str));
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    public static boolean rename(String str, String str2) {
        return rename(getFileByPath(str), str2);
    }

    public static boolean rename(File file, String str) {
        if (file == null || !file.exists() || isSpace(str)) {
            return false;
        }
        if (str.equals(file.getName())) {
            return true;
        }
        File file2 = new File(file.getParent() + File.separator + str);
        return !file2.exists() && file.renameTo(file2);
    }

    public static boolean isDir(String str) {
        return isDir(getFileByPath(str));
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(String str) {
        return isFile(getFileByPath(str));
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsDir(File file) {
        if (file != null) {
            return file.exists() ? file.isDirectory() : file.mkdirs();
        }
        return false;
    }

    public static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createFileByDeleteOldFile(String str) {
        return createFileByDeleteOldFile(getFileByPath(str));
    }

    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0064 A[EXC_TOP_SPLITTER, PHI: r4
  0x0064: PHI (r4v7 ??) = (r4v6 ??), (r4v9 ??) binds: [B:44:0x0062, B:57:0x007c] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean copyAssertFile(android.content.Context r4, java.lang.String r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            r2.<init>(r6)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            boolean r3 = r2.exists()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            if (r3 != 0) goto L7f
            r2.createNewFile()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            android.content.res.Resources r4 = r4.getResources()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            java.io.InputStream r4 = r4.open(r5)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53 java.io.FileNotFoundException -> L6d
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.lang.Throwable -> L80
            r5.<init>(r6)     // Catch: java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.lang.Throwable -> L80
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L46 java.io.FileNotFoundException -> L49
        L25:
            int r1 = r4.read(r6)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L46 java.io.FileNotFoundException -> L49
            if (r1 <= 0) goto L2f
            r5.write(r6, r0, r1)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L46 java.io.FileNotFoundException -> L49
            goto L25
        L2f:
            r5.close()     // Catch: java.io.IOException -> L33
            goto L37
        L33:
            r5 = move-exception
            r5.printStackTrace()
        L37:
            if (r4 == 0) goto L41
            r4.close()     // Catch: java.io.IOException -> L3d
            goto L41
        L3d:
            r4 = move-exception
            r4.printStackTrace()
        L41:
            r4 = 1
            return r4
        L43:
            r6 = move-exception
            r1 = r5
            goto L81
        L46:
            r6 = move-exception
            r1 = r5
            goto L55
        L49:
            r6 = move-exception
            r1 = r5
            goto L6f
        L4c:
            r6 = move-exception
            goto L55
        L4e:
            r6 = move-exception
            goto L6f
        L50:
            r6 = move-exception
            r4 = r1
            goto L81
        L53:
            r6 = move-exception
            r4 = r1
        L55:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L80
            if (r1 == 0) goto L62
            r1.close()     // Catch: java.io.IOException -> L5e
            goto L62
        L5e:
            r5 = move-exception
            r5.printStackTrace()
        L62:
            if (r4 == 0) goto L7f
        L64:
            r4.close()     // Catch: java.io.IOException -> L68
            goto L7f
        L68:
            r4 = move-exception
            r4.printStackTrace()
            goto L7f
        L6d:
            r6 = move-exception
            r4 = r1
        L6f:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L80
            if (r1 == 0) goto L7c
            r1.close()     // Catch: java.io.IOException -> L78
            goto L7c
        L78:
            r5 = move-exception
            r5.printStackTrace()
        L7c:
            if (r4 == 0) goto L7f
            goto L64
        L7f:
            return r0
        L80:
            r6 = move-exception
        L81:
            if (r1 == 0) goto L8b
            r1.close()     // Catch: java.io.IOException -> L87
            goto L8b
        L87:
            r5 = move-exception
            r5.printStackTrace()
        L8b:
            if (r4 == 0) goto L95
            r4.close()     // Catch: java.io.IOException -> L91
            goto L95
        L91:
            r4 = move-exception
            r4.printStackTrace()
        L95:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.broadlink.blelight.util.BLFileUtils.copyAssertFile(android.content.Context, java.lang.String, java.lang.String):boolean");
    }

    public static String readAssetsFile(Context context, String str) {
        return readAssetsFile(context, str, "utf-8");
    }

    public static String readAssetsFile(Context context, String str, String str2) {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            int iAvailable = inputStreamOpen.available();
            byte[] bArr = new byte[iAvailable];
            inputStreamOpen.read(bArr, 0, iAvailable);
            return new String(bArr, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] readAssetsFileByte(Context context, String str) {
        byte[] bArr = null;
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            int iAvailable = inputStreamOpen.available();
            bArr = new byte[iAvailable];
            inputStreamOpen.read(bArr, 0, iAvailable);
            return bArr;
        } catch (Exception e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public static boolean copyDir(String str, String str2) {
        return copyDir(getFileByPath(str), getFileByPath(str2));
    }

    public static boolean copyDir(String str, String str2, OnReplaceListener onReplaceListener) {
        return copyDir(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean copyDir(File file, File file2) {
        return copyOrMoveDir(file, file2, false);
    }

    public static boolean copyDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, false);
    }

    public static boolean copyFile(String str, String str2) {
        return copyFile(getFileByPath(str), getFileByPath(str2));
    }

    public static boolean copyFile(String str, String str2, OnReplaceListener onReplaceListener) {
        return copyFile(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean copyFile(File file, File file2) {
        return copyOrMoveFile(file, file2, false);
    }

    public static boolean copyFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, false);
    }

    public static boolean moveDir(String str, String str2) {
        return moveDir(getFileByPath(str), getFileByPath(str2));
    }

    public static boolean moveDir(String str, String str2, OnReplaceListener onReplaceListener) {
        return moveDir(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean moveDir(File file, File file2) {
        return copyOrMoveDir(file, file2, true);
    }

    public static boolean moveDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, true);
    }

    public static boolean moveFile(String str, String str2) {
        return moveFile(getFileByPath(str), getFileByPath(str2));
    }

    public static boolean moveFile(String str, String str2, OnReplaceListener onReplaceListener) {
        return moveFile(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean moveFile(File file, File file2) {
        return copyOrMoveFile(file, file2, true);
    }

    public static boolean moveFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, true);
    }

    private static boolean copyOrMoveDir(File file, File file2, boolean z) {
        return copyOrMoveDir(file, file2, new OnReplaceListener() { // from class: cn.com.broadlink.blelight.util.BLFileUtils.1
            @Override // cn.com.broadlink.blelight.util.BLFileUtils.OnReplaceListener
            public boolean onReplace() {
                return true;
            }
        }, z);
    }

    private static boolean copyOrMoveDir(File file, File file2, OnReplaceListener onReplaceListener, boolean z) {
        if (file != null && file2 != null) {
            String str = file.getPath() + File.separator;
            String str2 = file2.getPath() + File.separator;
            if (!str2.contains(str) && file.exists() && file.isDirectory()) {
                if (file2.exists()) {
                    if (onReplaceListener != null && !onReplaceListener.onReplace()) {
                        return true;
                    }
                    if (!deleteAllInDir(file2)) {
                        return false;
                    }
                }
                if (!createOrExistsDir(file2)) {
                    return false;
                }
                for (File file3 : file.listFiles()) {
                    File file4 = new File(str2 + file3.getName());
                    if (file3.isFile()) {
                        if (!copyOrMoveFile(file3, file4, onReplaceListener, z)) {
                            return false;
                        }
                    } else if (file3.isDirectory() && !copyOrMoveDir(file3, file4, onReplaceListener, z)) {
                        return false;
                    }
                }
                return !z || deleteDir(file);
            }
        }
        return false;
    }

    private static boolean copyOrMoveFile(File file, File file2, boolean z) {
        return copyOrMoveFile(file, file2, new OnReplaceListener() { // from class: cn.com.broadlink.blelight.util.BLFileUtils.2
            @Override // cn.com.broadlink.blelight.util.BLFileUtils.OnReplaceListener
            public boolean onReplace() {
                return true;
            }
        }, z);
    }

    private static boolean copyOrMoveFile(File file, File file2, OnReplaceListener onReplaceListener, boolean z) {
        if (file != null && file2 != null && !file.equals(file2) && file.exists() && file.isFile()) {
            if (file2.exists()) {
                if (onReplaceListener != null && !onReplaceListener.onReplace()) {
                    return true;
                }
                if (!file2.delete()) {
                    return false;
                }
            }
            if (!createOrExistsDir(file2.getParentFile())) {
                return false;
            }
            try {
                if (BLFileIOUtils.writeFileFromIS(file2, (InputStream) new FileInputStream(file), false)) {
                    if (z) {
                        if (deleteFile(file)) {
                        }
                    }
                    return true;
                }
                return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteDir(String str) {
        return deleteDir(getFileByPath(str));
    }

    public static boolean deleteDir(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    if (!file2.delete()) {
                        return false;
                    }
                } else if (file2.isDirectory() && !deleteDir(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(String str) {
        return deleteFile(getFileByPath(str));
    }

    public static boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile() && file.delete();
        }
        return true;
    }

    public static boolean deleteAllInDir(String str) {
        return deleteAllInDir(getFileByPath(str));
    }

    public static boolean deleteAllInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: cn.com.broadlink.blelight.util.BLFileUtils.3
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(String str) {
        return deleteFilesInDir(getFileByPath(str));
    }

    public static boolean deleteFilesInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: cn.com.broadlink.blelight.util.BLFileUtils.4
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return file2.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return deleteFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    public static boolean deleteFilesInDirWithFilter(File file, FileFilter fileFilter) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    if (file2.isFile()) {
                        if (!file2.delete()) {
                            return false;
                        }
                    } else if (file2.isDirectory() && !deleteDir(file2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<File> listFilesInDir(String str) {
        return listFilesInDir(str, false);
    }

    public static List<File> listFilesInDir(File file) {
        return listFilesInDir(file, false);
    }

    public static List<File> listFilesInDir(String str, boolean z) {
        return listFilesInDir(getFileByPath(str), z);
    }

    public static List<File> listFilesInDir(File file, boolean z) {
        return listFilesInDirWithFilter(file, new FileFilter() { // from class: cn.com.broadlink.blelight.util.BLFileUtils.5
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        }, z);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, false);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter) {
        return listFilesInDirWithFilter(file, fileFilter, false);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z) {
        if (!isDir(file)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    arrayList.add(file2);
                }
                if (z && file2.isDirectory()) {
                    arrayList.addAll(listFilesInDirWithFilter(file2, fileFilter, true));
                }
            }
        }
        return arrayList;
    }

    public static long getFileLastModified(String str) {
        return getFileLastModified(getFileByPath(str));
    }

    public static long getFileLastModified(File file) {
        if (file == null) {
            return -1L;
        }
        return file.lastModified();
    }

    public static String getFileCharsetSimple(String str) {
        return getFileCharsetSimple(getFileByPath(str));
    }

    public static String getFileCharsetSimple(File file) throws Throwable {
        int i;
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    i = (bufferedInputStream2.read() << 8) + bufferedInputStream2.read();
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    e.printStackTrace();
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    i = 0;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
        if (i == 61371) {
            return "UTF-8";
        }
        if (i == 65279) {
            return "UTF-16BE";
        }
        if (i == 65534) {
            return "Unicode";
        }
        return "GBK";
    }

    public static int getFileLines(String str) {
        return getFileLines(getFileByPath(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0049 -> B:45:0x005e). Please report as a decompilation issue!!! */
    public static int getFileLines(File file) throws Throwable {
        int i = 1;
        ?? r1 = 0;
        r1 = 0;
        r1 = 0;
        try {
            try {
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    try {
                        byte[] bArr = new byte[1024];
                        if (!LINE_SEP.endsWith("\n")) {
                            while (true) {
                                int i2 = bufferedInputStream.read(bArr, 0, 1024);
                                if (i2 == -1) {
                                    break;
                                }
                                for (int i3 = 0; i3 < i2; i3++) {
                                    if (bArr[i3] == 13) {
                                        i++;
                                    }
                                }
                            }
                        } else {
                            while (true) {
                                int i4 = bufferedInputStream.read(bArr, 0, 1024);
                                if (i4 == -1) {
                                    break;
                                }
                                for (int i5 = 0; i5 < i4; i5++) {
                                    if (bArr[i5] == 10) {
                                        i++;
                                    }
                                }
                            }
                        }
                        bufferedInputStream.close();
                        r1 = bArr;
                    } catch (IOException e) {
                        e = e;
                        r1 = bufferedInputStream;
                        e.printStackTrace();
                        if (r1 != 0) {
                            r1.close();
                            r1 = r1;
                        }
                        return i;
                    } catch (Throwable th) {
                        th = th;
                        r1 = bufferedInputStream;
                        if (r1 != 0) {
                            try {
                                r1.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                r1 = r1;
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getDirSize(String str) {
        return getDirSize(getFileByPath(str));
    }

    public static String getDirSize(File file) {
        long dirLength = getDirLength(file);
        return dirLength == -1 ? "" : byte2FitMemorySize(dirLength);
    }

    public static String getFileSize(String str) {
        long fileLength = getFileLength(str);
        return fileLength == -1 ? "" : byte2FitMemorySize(fileLength);
    }

    public static String getFileSize(File file) {
        long fileLength = getFileLength(file);
        return fileLength == -1 ? "" : byte2FitMemorySize(fileLength);
    }

    public static long getDirLength(String str) {
        return getDirLength(getFileByPath(str));
    }

    public static long getDirLength(File file) {
        long length;
        if (!isDir(file)) {
            return -1L;
        }
        File[] fileArrListFiles = file.listFiles();
        long j = 0;
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    length = getDirLength(file2);
                } else {
                    length = file2.length();
                }
                j += length;
            }
        }
        return j;
    }

    public static long getFileLength(String str) {
        if (str.matches("[a-zA-z]+://[^\\s]*")) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    return httpURLConnection.getContentLength();
                }
                return -1L;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(str));
    }

    public static long getFileLength(File file) {
        if (isFile(file)) {
            return file.length();
        }
        return -1L;
    }

    public static String getFileMD5ToString(String str) {
        return getFileMD5ToString(isSpace(str) ? null : new File(str));
    }

    public static String getFileMD5ToString(File file) {
        return bytes2HexString(getFileMD5(file));
    }

    public static byte[] getFileMD5(String str) {
        return getFileMD5(getFileByPath(str));
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0048: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:34:0x0048 */
    /* JADX WARN: Removed duplicated region for block: B:44:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getFileMD5(java.io.File r3) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r3 != 0) goto L4
            return r0
        L4:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.lang.String r3 = "MD5"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.security.DigestInputStream r2 = new java.security.DigestInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r3 = 262144(0x40000, float:3.67342E-40)
            byte[] r3 = new byte[r3]     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
        L18:
            int r1 = r2.read(r3)     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            if (r1 > 0) goto L18
            java.security.MessageDigest r3 = r2.getMessageDigest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            byte[] r3 = r3.digest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            r2.close()     // Catch: java.io.IOException -> L2a
            return r3
        L2a:
            r0 = move-exception
            r0.printStackTrace()
            return r3
        L2f:
            r3 = move-exception
            goto L39
        L31:
            r3 = move-exception
            goto L39
        L33:
            r3 = move-exception
            goto L49
        L35:
            r3 = move-exception
            goto L38
        L37:
            r3 = move-exception
        L38:
            r2 = r0
        L39:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L47
            if (r2 == 0) goto L46
            r2.close()     // Catch: java.io.IOException -> L42
            goto L46
        L42:
            r3 = move-exception
            r3.printStackTrace()
        L46:
            return r0
        L47:
            r3 = move-exception
            r0 = r2
        L49:
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r0 = move-exception
            r0.printStackTrace()
        L53:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.broadlink.blelight.util.BLFileUtils.getFileMD5(java.io.File):byte[]");
    }

    public static String getDirName(File file) {
        if (file == null) {
            return "";
        }
        return getDirName(file.getAbsolutePath());
    }

    public static String getDirName(String str) {
        int iLastIndexOf;
        return (isSpace(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? "" : str.substring(0, iLastIndexOf + 1);
    }

    public static String getFileName(File file) {
        if (file == null) {
            return "";
        }
        return getFileName(file.getAbsolutePath());
    }

    public static String getFileName(String str) {
        if (isSpace(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf == -1 ? str : str.substring(iLastIndexOf + 1);
    }

    public static String getFileNameNoExtension(File file) {
        if (file == null) {
            return "";
        }
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileNameNoExtension(String str) {
        if (isSpace(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        if (iLastIndexOf2 == -1) {
            return iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf);
        }
        if (iLastIndexOf == -1 || iLastIndexOf2 > iLastIndexOf) {
            return str.substring(iLastIndexOf2 + 1);
        }
        return str.substring(iLastIndexOf2 + 1, iLastIndexOf);
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            return "";
        }
        return getFileExtension(file.getPath());
    }

    public static String getFileExtension(String str) {
        if (isSpace(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return (iLastIndexOf == -1 || str.lastIndexOf(File.separator) >= iLastIndexOf) ? "" : str.substring(iLastIndexOf + 1);
    }

    private static String bytes2HexString(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return "";
        }
        char[] cArr = new char[length << 1];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = HEX_DIGITS;
            cArr[i] = cArr2[(b >>> 4) & 15];
            i += 2;
            cArr[i2] = cArr2[b & BSON.CODE_W_SCOPE];
        }
        return new String(cArr);
    }

    private static String byte2FitMemorySize(long j) {
        if (j < 0) {
            return "shouldn't be less than zero!";
        }
        return j < 1024 ? String.format("%.3fB", Double.valueOf(j)) : j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED ? String.format("%.3fKB", Double.valueOf(j / 1024.0d)) : j < 1073741824 ? String.format("%.3fMB", Double.valueOf(j / 1048576.0d)) : String.format("%.3fGB", Double.valueOf(j / 1.073741824E9d));
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void upZipFile(File file, String str) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry != null) {
                String name = nextEntry.getName();
                if (!TextUtils.isEmpty(name) && !name.contains("../")) {
                    if (nextEntry.isDirectory()) {
                        new File(str + File.separator + name.substring(0, name.length() - 1)).mkdirs();
                    } else {
                        File file2 = new File(str + File.separator + name);
                        if (!file2.getParentFile().exists()) {
                            file2.getParentFile().mkdirs();
                        }
                        file2.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = zipInputStream.read(bArr);
                            if (i == -1) {
                                break;
                            } else {
                                fileOutputStream.write(bArr, 0, i);
                            }
                        }
                        zipInputStream.closeEntry();
                        fileOutputStream.close();
                    }
                }
            } else {
                zipInputStream.close();
                return;
            }
        }
    }

    public static byte[] zipData(byte[] bArr) throws Exception {
        ZipEntry zipEntry = new ZipEntry("zipFile");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1048576);
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        zipOutputStream.putNextEntry(zipEntry);
        byte[] bArr2 = new byte[4096];
        while (true) {
            int i = byteArrayInputStream.read(bArr2);
            if (i != -1) {
                zipOutputStream.write(bArr2, 0, i);
            } else {
                zipOutputStream.closeEntry();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] unZipData(byte[] bArr) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1048576);
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(bArr));
        while (zipInputStream.getNextEntry() != null) {
            byte[] bArr2 = new byte[4096];
            while (true) {
                int i = zipInputStream.read(bArr2);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr2, 0, i);
                }
            }
            zipInputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }
}

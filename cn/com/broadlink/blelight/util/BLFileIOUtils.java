package cn.com.broadlink.blelight.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLFileIOUtils {
    private static int sBufferSize = 8192;

    private BLFileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream) {
        return writeFileFromIS(getFileByPath(str), inputStream, false);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, boolean z) {
        return writeFileFromIS(getFileByPath(str), inputStream, z);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream) {
        return writeFileFromIS(file, inputStream, false);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, boolean z) throws Throwable {
        if (!createOrExistsFile(file) || inputStream == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z));
                try {
                    byte[] bArr = new byte[sBufferSize];
                    while (true) {
                        int i = inputStream.read(bArr, 0, sBufferSize);
                        if (i != -1) {
                            bufferedOutputStream2.write(bArr, 0, i);
                        } else {
                            try {
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    inputStream.close();
                    try {
                        bufferedOutputStream2.close();
                        return true;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return true;
                    }
                } catch (IOException e3) {
                    e = e3;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    try {
                        inputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                            throw th;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e8) {
            e = e8;
        }
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr) {
        return writeFileFromBytesByStream(getFileByPath(str), bArr, false);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, boolean z) {
        return writeFileFromBytesByStream(getFileByPath(str), bArr, z);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr) {
        return writeFileFromBytesByStream(file, bArr, false);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, boolean z) throws Throwable {
        if (bArr == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z));
                try {
                    bufferedOutputStream2.write(bArr);
                    try {
                        bufferedOutputStream2.close();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return true;
                    }
                } catch (IOException e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
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
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z) {
        return writeFileFromBytesByChannel(getFileByPath(str), bArr, false, z);
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z, boolean z2) {
        return writeFileFromBytesByChannel(getFileByPath(str), bArr, z, z2);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z) {
        return writeFileFromBytesByChannel(file, bArr, false, z);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            return false;
        }
        FileChannel channel = null;
        try {
            try {
                channel = new FileOutputStream(file, z).getChannel();
                channel.position(channel.size());
                channel.write(ByteBuffer.wrap(bArr));
                if (z2) {
                    channel.force(true);
                }
                if (channel != null) {
                    try {
                        channel.close();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z) {
        return writeFileFromBytesByMap(str, bArr, false, z);
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z, boolean z2) {
        return writeFileFromBytesByMap(getFileByPath(str), bArr, z, z2);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z) {
        return writeFileFromBytesByMap(file, bArr, false, z);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z, boolean z2) throws Throwable {
        Throwable th;
        IOException iOException;
        if (bArr == null || !createOrExistsFile(file)) {
            return false;
        }
        FileChannel fileChannel = null;
        try {
            try {
                FileChannel channel = new FileOutputStream(file, z).getChannel();
                try {
                    MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, channel.size(), bArr.length);
                    map.put(bArr);
                    if (z2) {
                        map.force();
                    }
                    if (channel != null) {
                        try {
                            channel.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                } catch (IOException e2) {
                    iOException = e2;
                    fileChannel = channel;
                    iOException.printStackTrace();
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    fileChannel = channel;
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                            throw th;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException e5) {
            iOException = e5;
        }
    }

    public static boolean writeFileFromString(String str, String str2) {
        return writeFileFromString(getFileByPath(str), str2, false);
    }

    public static boolean writeFileFromString(String str, String str2, boolean z) {
        return writeFileFromString(getFileByPath(str), str2, z);
    }

    public static boolean writeFileFromString(File file, String str) {
        return writeFileFromString(file, str, false);
    }

    public static boolean writeFileFromString(File file, String str, boolean z) throws Throwable {
        if (file == null || str == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file, z));
                try {
                    bufferedWriter2.write(str);
                    try {
                        bufferedWriter2.close();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return true;
                    }
                } catch (IOException e2) {
                    e = e2;
                    bufferedWriter = bufferedWriter2;
                    e.printStackTrace();
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static List<String> readFile2List(String str) {
        return readFile2List(getFileByPath(str), (String) null);
    }

    public static List<String> readFile2List(String str, String str2) {
        return readFile2List(getFileByPath(str), str2);
    }

    public static List<String> readFile2List(File file) {
        return readFile2List(file, 0, Integer.MAX_VALUE, (String) null);
    }

    public static List<String> readFile2List(File file, String str) {
        return readFile2List(file, 0, Integer.MAX_VALUE, str);
    }

    public static List<String> readFile2List(String str, int i, int i2) {
        return readFile2List(getFileByPath(str), i, i2, (String) null);
    }

    public static List<String> readFile2List(String str, int i, int i2, String str2) {
        return readFile2List(getFileByPath(str), i, i2, str2);
    }

    public static List<String> readFile2List(File file, int i, int i2) {
        return readFile2List(file, i, i2, (String) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> readFile2List(java.io.File r6, int r7, int r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            boolean r0 = isFileExists(r6)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            if (r7 <= r8) goto Lb
            return r1
        Lb:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r0.<init>()     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            boolean r2 = isSpace(r9)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r3 = 1
            if (r2 == 0) goto L27
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r9.<init>(r2)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            goto L37
        L27:
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r4.<init>(r5, r9)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L59
            r9 = r2
        L37:
            java.lang.String r6 = r9.readLine()     // Catch: java.io.IOException -> L55 java.lang.Throwable -> L69
            if (r6 == 0) goto L4a
            if (r3 <= r8) goto L40
            goto L4a
        L40:
            if (r7 > r3) goto L47
            if (r3 > r8) goto L47
            r0.add(r6)     // Catch: java.io.IOException -> L55 java.lang.Throwable -> L69
        L47:
            int r3 = r3 + 1
            goto L37
        L4a:
            if (r9 == 0) goto L54
            r9.close()     // Catch: java.io.IOException -> L50
            return r0
        L50:
            r6 = move-exception
            r6.printStackTrace()
        L54:
            return r0
        L55:
            r6 = move-exception
            goto L5b
        L57:
            r6 = move-exception
            goto L6b
        L59:
            r6 = move-exception
            r9 = r1
        L5b:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L69
            if (r9 == 0) goto L68
            r9.close()     // Catch: java.io.IOException -> L64
            goto L68
        L64:
            r6 = move-exception
            r6.printStackTrace()
        L68:
            return r1
        L69:
            r6 = move-exception
            r1 = r9
        L6b:
            if (r1 == 0) goto L75
            r1.close()     // Catch: java.io.IOException -> L71
            goto L75
        L71:
            r7 = move-exception
            r7.printStackTrace()
        L75:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.broadlink.blelight.util.BLFileIOUtils.readFile2List(java.io.File, int, int, java.lang.String):java.util.List");
    }

    public static String readFile2String(String str) {
        return readFile2String(getFileByPath(str), (String) null);
    }

    public static String readFile2String(String str, String str2) {
        return readFile2String(getFileByPath(str), str2);
    }

    public static String readFile2String(File file) {
        return readFile2String(file, (String) null);
    }

    public static String readFile2String(File file, String str) {
        byte[] file2BytesByStream = readFile2BytesByStream(file);
        if (file2BytesByStream == null) {
            return null;
        }
        if (isSpace(str)) {
            return new String(file2BytesByStream);
        }
        try {
            return new String(file2BytesByStream, str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] readFile2BytesByStream(String str) {
        return readFile2BytesByStream(getFileByPath(str));
    }

    public static byte[] readFile2BytesByStream(File file) {
        if (!isFileExists(file)) {
            return null;
        }
        try {
            return is2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFile2BytesByChannel(String str) {
        return readFile2BytesByChannel(getFileByPath(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static byte[] readFile2BytesByChannel(File file) throws Throwable {
        FileChannel channel;
        FileChannel fileChannel = 0;
        try {
            if (!isFileExists(file)) {
                return null;
            }
            try {
                channel = new RandomAccessFile(file, "r").getChannel();
            } catch (IOException e) {
                e = e;
                channel = null;
            } catch (Throwable th) {
                th = th;
                if (fileChannel != 0) {
                    try {
                        fileChannel.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
                while (channel.read(byteBufferAllocate) > 0) {
                }
                byte[] bArrArray = byteBufferAllocate.array();
                if (channel != null) {
                    try {
                        channel.close();
                        return bArrArray;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return bArrArray;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileChannel = file;
        }
    }

    public static byte[] readFile2BytesByMap(String str) {
        return readFile2BytesByMap(getFileByPath(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0054 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] readFile2BytesByMap(java.io.File r9) throws java.lang.Throwable {
        /*
            boolean r0 = isFileExists(r9)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L3a java.io.IOException -> L3d
            java.lang.String r2 = "r"
            r0.<init>(r9, r2)     // Catch: java.lang.Throwable -> L3a java.io.IOException -> L3d
            java.nio.channels.FileChannel r3 = r0.getChannel()     // Catch: java.lang.Throwable -> L3a java.io.IOException -> L3d
            long r4 = r3.size()     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            int r9 = (int) r4     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            java.nio.channels.FileChannel$MapMode r4 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            r5 = 0
            long r7 = (long) r9     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            java.nio.MappedByteBuffer r0 = r3.map(r4, r5, r7)     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            java.nio.MappedByteBuffer r0 = r0.load()     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            byte[] r2 = new byte[r9]     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            r4 = 0
            r0.get(r2, r4, r9)     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L4f
            if (r3 == 0) goto L36
            r3.close()     // Catch: java.io.IOException -> L31
            return r2
        L31:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
        L36:
            return r2
        L37:
            r0 = move-exception
            r9 = r0
            goto L40
        L3a:
            r0 = move-exception
            r9 = r0
            goto L52
        L3d:
            r0 = move-exception
            r9 = r0
            r3 = r1
        L40:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L4f
            if (r3 == 0) goto L4e
            r3.close()     // Catch: java.io.IOException -> L49
            goto L4e
        L49:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
        L4e:
            return r1
        L4f:
            r0 = move-exception
            r9 = r0
            r1 = r3
        L52:
            if (r1 == 0) goto L5c
            r1.close()     // Catch: java.io.IOException -> L58
            goto L5c
        L58:
            r0 = move-exception
            r0.printStackTrace()
        L5c:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.broadlink.blelight.util.BLFileIOUtils.readFile2BytesByMap(java.io.File):byte[]");
    }

    public static void setBufferSize(int i) {
        sBufferSize = i;
    }

    private static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    private static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    private static boolean createOrExistsFile(File file) {
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

    private static boolean createOrExistsDir(File file) {
        if (file != null) {
            return file.exists() ? file.isDirectory() : file.mkdirs();
        }
        return false;
    }

    private static boolean isFileExists(File file) {
        return file != null && file.exists();
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

    private static byte[] is2Bytes(InputStream inputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        byte[] bArr;
        if (inputStream == null) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (IOException e) {
            e = e;
            byteArrayOutputStream = null;
        } catch (Throwable th2) {
            byteArrayOutputStream = null;
            th = th2;
        }
        try {
            try {
                bArr = new byte[sBufferSize];
            } catch (Throwable th3) {
                th = th3;
            }
            while (true) {
                int i = inputStream.read(bArr, 0, sBufferSize);
                if (i == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
                th = th3;
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                        throw th;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        throw th;
                    }
                }
                throw th;
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                inputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            return byteArray;
        } catch (IOException e6) {
            e = e6;
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e7) {
                e7.printStackTrace();
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            return null;
        }
    }
}

package com.getkeepsafe.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.getkeepsafe.relinker.ReLinker;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    private String[] sourceDirectories(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo.splitSourceDirs != null && applicationInfo.splitSourceDirs.length != 0) {
            String[] strArr = new String[applicationInfo.splitSourceDirs.length + 1];
            strArr[0] = applicationInfo.sourceDir;
            System.arraycopy(applicationInfo.splitSourceDirs, 0, strArr, 1, applicationInfo.splitSourceDirs.length);
            return strArr;
        }
        return new String[]{applicationInfo.sourceDir};
    }

    private static class ZipFileInZipEntry {
        public ZipEntry zipEntry;
        public ZipFile zipFile;

        public ZipFileInZipEntry(ZipFile zipFile, ZipEntry zipEntry) {
            this.zipFile = zipFile;
            this.zipEntry = zipEntry;
        }
    }

    private ZipFileInZipEntry findAPKWithLibrary(Context context, String[] strArr, String str, ReLinkerInstance reLinkerInstance) {
        String[] strArrSourceDirectories = sourceDirectories(context);
        int length = strArrSourceDirectories.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            ZipFile zipFile = null;
            if (i2 >= length) {
                return null;
            }
            String str2 = strArrSourceDirectories[i2];
            int i3 = i;
            while (true) {
                int i4 = i3 + 1;
                if (i3 >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(str2), 1);
                    break;
                } catch (IOException unused) {
                    i3 = i4;
                }
            }
            if (zipFile != null) {
                int i5 = i;
                while (true) {
                    int i6 = i5 + 1;
                    if (i5 < 5) {
                        int length2 = strArr.length;
                        int i7 = i;
                        while (i7 < length2) {
                            String str3 = "lib" + File.separatorChar + strArr[i7] + File.separatorChar + str;
                            Object[] objArr = new Object[2];
                            objArr[i] = str3;
                            objArr[1] = str2;
                            reLinkerInstance.log("Looking for %s in APK %s...", objArr);
                            ZipEntry entry = zipFile.getEntry(str3);
                            if (entry != null) {
                                return new ZipFileInZipEntry(zipFile, entry);
                            }
                            i7++;
                            i = 0;
                        }
                        i5 = i6;
                        i = 0;
                    } else {
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException unused2) {
                        }
                    }
                }
            }
            i2++;
            i = 0;
        }
    }

    private String[] getSupportedABIs(Context context, String str) {
        Pattern patternCompile = Pattern.compile("lib" + File.separatorChar + "([^\\" + File.separatorChar + "]*)" + File.separatorChar + str);
        HashSet hashSet = new HashSet();
        for (String str2 : sourceDirectories(context)) {
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(new File(str2), 1).entries();
                while (enumerationEntries.hasMoreElements()) {
                    Matcher matcher = patternCompile.matcher(enumerationEntries.nextElement().getName());
                    if (matcher.matches()) {
                        hashSet.add(matcher.group(1));
                    }
                }
            } catch (IOException unused) {
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0062, code lost:
    
        r1.zipFile.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:?, code lost:
    
        return;
     */
    @Override // com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void installLibrary(android.content.Context r10, java.lang.String[] r11, java.lang.String r12, java.io.File r13, com.getkeepsafe.relinker.ReLinkerInstance r14) throws java.lang.Throwable {
        /*
            r9 = this;
            r0 = 0
            com.getkeepsafe.relinker.ApkLibraryInstaller$ZipFileInZipEntry r1 = r9.findAPKWithLibrary(r10, r11, r12, r14)     // Catch: java.lang.Throwable -> Lad
            if (r1 == 0) goto L96
            r10 = 0
            r11 = r10
        L9:
            int r2 = r11 + 1
            r3 = 5
            if (r11 >= r3) goto L89
            java.lang.String r11 = "Found %s! Extracting..."
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L9b
            r4[r10] = r12     // Catch: java.lang.Throwable -> L9b
            r14.log(r11, r4)     // Catch: java.lang.Throwable -> L9b
            boolean r11 = r13.exists()     // Catch: java.io.IOException -> L87 java.lang.Throwable -> L9b
            if (r11 != 0) goto L26
            boolean r11 = r13.createNewFile()     // Catch: java.io.IOException -> L87 java.lang.Throwable -> L9b
            if (r11 != 0) goto L26
            goto L87
        L26:
            java.util.zip.ZipFile r11 = r1.zipFile     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L7b java.io.FileNotFoundException -> L81
            java.util.zip.ZipEntry r4 = r1.zipEntry     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L7b java.io.FileNotFoundException -> L81
            java.io.InputStream r11 = r11.getInputStream(r4)     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L7b java.io.FileNotFoundException -> L81
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L6a java.io.IOException -> L6e java.io.FileNotFoundException -> L70
            r4.<init>(r13)     // Catch: java.lang.Throwable -> L6a java.io.IOException -> L6e java.io.FileNotFoundException -> L70
            long r5 = r9.copy(r11, r4)     // Catch: java.lang.Throwable -> L68 java.io.IOException -> L7d java.io.FileNotFoundException -> L83
            java.io.FileDescriptor r7 = r4.getFD()     // Catch: java.lang.Throwable -> L68 java.io.IOException -> L7d java.io.FileNotFoundException -> L83
            r7.sync()     // Catch: java.lang.Throwable -> L68 java.io.IOException -> L7d java.io.FileNotFoundException -> L83
            long r7 = r13.length()     // Catch: java.lang.Throwable -> L68 java.io.IOException -> L7d java.io.FileNotFoundException -> L83
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L4d
            r9.closeSilently(r11)     // Catch: java.lang.Throwable -> L9b
        L49:
            r9.closeSilently(r4)     // Catch: java.lang.Throwable -> L9b
            goto L87
        L4d:
            r9.closeSilently(r11)     // Catch: java.lang.Throwable -> L9b
            r9.closeSilently(r4)     // Catch: java.lang.Throwable -> L9b
            r13.setReadable(r3, r10)     // Catch: java.lang.Throwable -> L9b
            r13.setExecutable(r3, r10)     // Catch: java.lang.Throwable -> L9b
            r13.setWritable(r3)     // Catch: java.lang.Throwable -> L9b
            if (r1 == 0) goto L95
            java.util.zip.ZipFile r10 = r1.zipFile     // Catch: java.io.IOException -> L95
            if (r10 == 0) goto L95
        L62:
            java.util.zip.ZipFile r10 = r1.zipFile     // Catch: java.io.IOException -> L95
            r10.close()     // Catch: java.io.IOException -> L95
            goto L95
        L68:
            r10 = move-exception
            goto L6c
        L6a:
            r10 = move-exception
            r4 = r0
        L6c:
            r0 = r11
            goto L74
        L6e:
            r4 = r0
            goto L7d
        L70:
            r4 = r0
            goto L83
        L72:
            r10 = move-exception
            r4 = r0
        L74:
            r9.closeSilently(r0)     // Catch: java.lang.Throwable -> L9b
            r9.closeSilently(r4)     // Catch: java.lang.Throwable -> L9b
            throw r10     // Catch: java.lang.Throwable -> L9b
        L7b:
            r11 = r0
            r4 = r11
        L7d:
            r9.closeSilently(r11)     // Catch: java.lang.Throwable -> L9b
            goto L49
        L81:
            r11 = r0
            r4 = r11
        L83:
            r9.closeSilently(r11)     // Catch: java.lang.Throwable -> L9b
            goto L49
        L87:
            r11 = r2
            goto L9
        L89:
            java.lang.String r10 = "FATAL! Couldn't extract the library from the APK!"
            r14.log(r10)     // Catch: java.lang.Throwable -> L9b
            if (r1 == 0) goto L95
            java.util.zip.ZipFile r10 = r1.zipFile     // Catch: java.io.IOException -> L95
            if (r10 == 0) goto L95
            goto L62
        L95:
            return
        L96:
            java.lang.String[] r10 = r9.getSupportedABIs(r10, r12)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9e
            goto La7
        L9b:
            r10 = move-exception
            r0 = r1
            goto Lae
        L9e:
            r10 = move-exception
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L9b
            java.lang.String[] r10 = new java.lang.String[]{r10}     // Catch: java.lang.Throwable -> L9b
        La7:
            com.getkeepsafe.relinker.MissingLibraryException r13 = new com.getkeepsafe.relinker.MissingLibraryException     // Catch: java.lang.Throwable -> L9b
            r13.<init>(r12, r11, r10)     // Catch: java.lang.Throwable -> L9b
            throw r13     // Catch: java.lang.Throwable -> L9b
        Lad:
            r10 = move-exception
        Lae:
            if (r0 == 0) goto Lb9
            java.util.zip.ZipFile r11 = r0.zipFile     // Catch: java.io.IOException -> Lb9
            if (r11 == 0) goto Lb9
            java.util.zip.ZipFile r11 = r0.zipFile     // Catch: java.io.IOException -> Lb9
            r11.close()     // Catch: java.io.IOException -> Lb9
        Lb9:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getkeepsafe.relinker.ApkLibraryInstaller.installLibrary(android.content.Context, java.lang.String[], java.lang.String, java.io.File, com.getkeepsafe.relinker.ReLinkerInstance):void");
    }

    private long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                outputStream.write(bArr, 0, i);
                j += (long) i;
            } else {
                outputStream.flush();
                return j;
            }
        }
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}

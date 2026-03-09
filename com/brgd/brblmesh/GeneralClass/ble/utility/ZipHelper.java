package com.brgd.brblmesh.GeneralClass.ble.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes.dex */
public class ZipHelper {
    public static void UnZipFolder(String str, String str2) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry != null) {
                String name = nextEntry.getName();
                if (nextEntry.isDirectory()) {
                    new File(str2 + File.separator + name.substring(0, name.length() - 1)).mkdirs();
                } else {
                    File file = new File(str2 + File.separator + name);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = zipInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, i);
                        fileOutputStream.flush();
                    }
                    fileOutputStream.close();
                }
            } else {
                zipInputStream.close();
                return;
            }
        }
    }

    public static void UnZipFolder(String str, String str2, String str3) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry != null) {
                if (nextEntry.isDirectory()) {
                    str3 = str3.substring(0, str3.length() - 1);
                    new File(str2 + File.separator + str3).mkdirs();
                } else {
                    File file = new File(str2 + File.separator + str3);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = zipInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, i);
                        fileOutputStream.flush();
                    }
                    fileOutputStream.close();
                }
            } else {
                zipInputStream.close();
                return;
            }
        }
    }

    public static void ZipFolder(String str, String str2) throws Exception {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(str2));
        File file = new File(str);
        ZipFiles(file.getParent() + File.separator, file.getName(), zipOutputStream);
        zipOutputStream.finish();
        zipOutputStream.close();
    }

    private static void ZipFiles(String str, String str2, ZipOutputStream zipOutputStream) throws Exception {
        if (zipOutputStream == null) {
            return;
        }
        File file = new File(str + str2);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(str2);
            FileInputStream fileInputStream = new FileInputStream(file);
            zipOutputStream.putNextEntry(zipEntry);
            byte[] bArr = new byte[4096];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    zipOutputStream.write(bArr, 0, i);
                } else {
                    zipOutputStream.closeEntry();
                    return;
                }
            }
        } else {
            String[] list = file.list();
            if (list.length <= 0) {
                zipOutputStream.putNextEntry(new ZipEntry(str2 + File.separator));
                zipOutputStream.closeEntry();
            }
            for (String str3 : list) {
                ZipFiles(str, str2 + File.separator + str3, zipOutputStream);
            }
        }
    }

    public static InputStream UpZip(String str, String str2) throws Exception {
        ZipFile zipFile = new ZipFile(str);
        return zipFile.getInputStream(zipFile.getEntry(str2));
    }

    public static List<File> GetFileList(String str, boolean z, boolean z2) throws Exception {
        ArrayList arrayList = new ArrayList();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry != null) {
                String name = nextEntry.getName();
                if (nextEntry.isDirectory()) {
                    File file = new File(name.substring(0, name.length() - 1));
                    if (z) {
                        arrayList.add(file);
                    }
                } else {
                    File file2 = new File(name);
                    if (z2) {
                        arrayList.add(file2);
                    }
                }
            } else {
                zipInputStream.close();
                return arrayList;
            }
        }
    }
}

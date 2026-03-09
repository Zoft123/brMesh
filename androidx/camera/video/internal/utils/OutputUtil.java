package androidx.camera.video.internal.utils;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class OutputUtil {
    private static final String TAG = "OutputUtil";

    private OutputUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getAbsolutePathFromUri(android.content.ContentResolver r8, android.net.Uri r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            r1 = 0
            java.lang.String[] r4 = new java.lang.String[]{r10}     // Catch: java.lang.Throwable -> L2c java.lang.RuntimeException -> L2f
            r6 = 0
            r7 = 0
            r5 = 0
            r2 = r8
            r3 = r9
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.RuntimeException -> L2a java.lang.Throwable -> L2c
            if (r8 != 0) goto L16
            if (r8 == 0) goto L15
            r8.close()
        L15:
            return r1
        L16:
            int r9 = r8.getColumnIndexOrThrow(r10)     // Catch: java.lang.RuntimeException -> L27 java.lang.Throwable -> L55
            r8.moveToFirst()     // Catch: java.lang.RuntimeException -> L27 java.lang.Throwable -> L55
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.RuntimeException -> L27 java.lang.Throwable -> L55
            if (r8 == 0) goto L26
            r8.close()
        L26:
            return r9
        L27:
            r0 = move-exception
            r9 = r0
            goto L33
        L2a:
            r0 = move-exception
            goto L31
        L2c:
            r0 = move-exception
            r9 = r0
            goto L58
        L2f:
            r0 = move-exception
            r3 = r9
        L31:
            r9 = r0
            r8 = r1
        L33:
            java.lang.String r10 = "OutputUtil"
            java.lang.String r0 = "Failed in getting absolute path for Uri %s with Exception %s"
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L55
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L55
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L55
            r4 = 0
            r3[r4] = r2     // Catch: java.lang.Throwable -> L55
            r2 = 1
            r3[r2] = r9     // Catch: java.lang.Throwable -> L55
            java.lang.String r9 = java.lang.String.format(r0, r3)     // Catch: java.lang.Throwable -> L55
            androidx.camera.core.Logger.e(r10, r9)     // Catch: java.lang.Throwable -> L55
            if (r8 == 0) goto L54
            r8.close()
        L54:
            return r1
        L55:
            r0 = move-exception
            r9 = r0
            r1 = r8
        L58:
            if (r1 == 0) goto L5d
            r1.close()
        L5d:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.internal.utils.OutputUtil.getAbsolutePathFromUri(android.content.ContentResolver, android.net.Uri, java.lang.String):java.lang.String");
    }

    public static boolean createParentFolder(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return false;
        }
        return parentFile.exists() ? parentFile.isDirectory() : parentFile.mkdirs();
    }
}

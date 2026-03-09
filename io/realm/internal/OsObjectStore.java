package io.realm.internal;

import io.realm.RealmConfiguration;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class OsObjectStore {
    public static final long SCHEMA_NOT_VERSIONED = -1;

    private static native boolean nativeCallWithLock(String str, Runnable runnable);

    private static native boolean nativeDeleteTableForObject(long j, String str);

    @Nullable
    private static native String nativeGetPrimaryKeyForObject(long j, String str);

    private static native long nativeGetSchemaVersion(long j);

    private static native void nativeSetPrimaryKeyForObject(long j, String str, @Nullable String str2);

    private static native void nativeSetSchemaVersion(long j, long j2);

    public static void setPrimaryKeyForObject(OsSharedRealm osSharedRealm, String str, @Nullable String str2) {
        try {
            nativeSetPrimaryKeyForObject(osSharedRealm.getNativePtr(), Util.getTablePrefix() + str, str2);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Nullable
    public static String getPrimaryKeyForObject(OsSharedRealm osSharedRealm, String str) {
        return nativeGetPrimaryKeyForObject(osSharedRealm.getNativePtr(), Util.getTablePrefix() + str);
    }

    public static void setSchemaVersion(OsSharedRealm osSharedRealm, long j) {
        nativeSetSchemaVersion(osSharedRealm.getNativePtr(), j);
    }

    public static long getSchemaVersion(OsSharedRealm osSharedRealm) {
        return nativeGetSchemaVersion(osSharedRealm.getNativePtr());
    }

    public static boolean deleteTableForObject(OsSharedRealm osSharedRealm, String str) {
        return nativeDeleteTableForObject(osSharedRealm.getNativePtr(), str);
    }

    public static boolean callWithLock(RealmConfiguration realmConfiguration, Runnable runnable) {
        return nativeCallWithLock(realmConfiguration.getPath(), runnable);
    }
}

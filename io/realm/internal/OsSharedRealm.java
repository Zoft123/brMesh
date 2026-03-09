package io.realm.internal;

import io.realm.RealmConfiguration;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmError;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsResults;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import java.io.Closeable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public final class OsSharedRealm implements Closeable, NativeObject {
    public static final byte FILE_EXCEPTION_INCOMPATIBLE_SYNC_FILE = 7;
    public static final byte FILE_EXCEPTION_KIND_ACCESS_ERROR = 0;
    public static final byte FILE_EXCEPTION_KIND_BAD_HISTORY = 1;
    public static final byte FILE_EXCEPTION_KIND_EXISTS = 3;
    public static final byte FILE_EXCEPTION_KIND_FORMAT_UPGRADE_REQUIRED = 6;
    public static final byte FILE_EXCEPTION_KIND_INCOMPATIBLE_LOCK_FILE = 5;
    public static final byte FILE_EXCEPTION_KIND_NOT_FOUND = 4;
    public static final byte FILE_EXCEPTION_KIND_PERMISSION_DENIED = 2;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private static final List<OsSharedRealm> sharedRealmsUnderConstruction = new CopyOnWriteArrayList();
    private static volatile File temporaryDirectory;
    public final Capabilities capabilities;
    public final NativeContext context;
    final List<WeakReference<OsResults.Iterator>> iterators;
    private final long nativePtr;
    private final OsRealmConfig osRealmConfig;
    private final List<WeakReference<PendingRow>> pendingRows;
    public final RealmNotifier realmNotifier;
    private final OsSchemaInfo schemaInfo;
    private final List<OsSharedRealm> tempSharedRealmsForCallback;

    public interface InitializationCallback {
        void onInit(OsSharedRealm osSharedRealm);
    }

    public interface MigrationCallback {
        void onMigrationNeeded(OsSharedRealm osSharedRealm, long j, long j2);
    }

    public interface SchemaChangedCallback {
        void onSchemaChanged();
    }

    private static native void nativeBeginTransaction(long j);

    private static native void nativeCancelTransaction(long j);

    private static native void nativeCloseSharedRealm(long j);

    private static native void nativeCommitTransaction(long j);

    private static native boolean nativeCompact(long j);

    private static native long nativeCreateTable(long j, String str);

    private static native long nativeCreateTableWithPrimaryKeyField(long j, String str, String str2, int i, boolean z);

    private static native long nativeFreeze(long j);

    private static native long nativeGetActiveSubscriptionSet(long j);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetLatestSubscriptionSet(long j);

    private static native long nativeGetSchemaInfo(long j);

    private static native long nativeGetSharedRealm(long j, long j2, long j3, RealmNotifier realmNotifier);

    private static native long nativeGetTableRef(long j, String str);

    private static native String[] nativeGetTablesName(long j);

    private static native long[] nativeGetVersionID(long j);

    private static native boolean nativeHasTable(long j, String str);

    private static native void nativeInit(String str);

    private static native boolean nativeIsAutoRefresh(long j);

    private static native boolean nativeIsClosed(long j);

    private static native boolean nativeIsEmpty(long j);

    private static native boolean nativeIsFrozen(long j);

    private static native boolean nativeIsInTransaction(long j);

    private static native long nativeNumberOfVersions(long j);

    private static native void nativeRefresh(long j);

    private static native void nativeRegisterSchemaChangedCallback(long j, SchemaChangedCallback schemaChangedCallback);

    private static native void nativeRenameTable(long j, String str, String str2);

    private static native void nativeSetAutoRefresh(long j, boolean z);

    private static native long nativeSize(long j);

    private static native void nativeStopWaitForChange(long j);

    private static native boolean nativeWaitForChange(long j);

    private static native void nativeWriteCopy(long j, String str, @Nullable byte[] bArr);

    public static class VersionID implements Comparable<VersionID> {
        public static final VersionID LIVE = new VersionID(-1, -1);
        public final long index;
        public final long version;

        VersionID(long j, long j2) {
            this.version = j;
            this.index = j2;
        }

        @Override // java.lang.Comparable
        public int compareTo(VersionID versionID) {
            if (versionID == null) {
                throw new IllegalArgumentException("Version cannot be compared to a null value.");
            }
            long j = this.version;
            long j2 = versionID.version;
            if (j > j2) {
                return 1;
            }
            return j < j2 ? -1 : 0;
        }

        public String toString() {
            return "VersionID{version=" + this.version + ", index=" + this.index + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                VersionID versionID = (VersionID) obj;
                if (this.version == versionID.version && this.index == versionID.index) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            long j = this.version;
            int i = ((int) (j ^ (j >>> 32))) * 31;
            long j2 = this.index;
            return i + ((int) ((j2 >>> 32) ^ j2));
        }
    }

    private OsSharedRealm(OsRealmConfig osRealmConfig, VersionID versionID) {
        ArrayList arrayList = new ArrayList();
        this.tempSharedRealmsForCallback = arrayList;
        this.pendingRows = new CopyOnWriteArrayList();
        this.iterators = new ArrayList();
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        AndroidRealmNotifier androidRealmNotifier = new AndroidRealmNotifier(this, androidCapabilities);
        NativeContext context = osRealmConfig.getContext();
        this.context = context;
        List<OsSharedRealm> list = sharedRealmsUnderConstruction;
        list.add(this);
        try {
            long jNativeGetSharedRealm = nativeGetSharedRealm(osRealmConfig.getNativePtr(), versionID.version, versionID.index, androidRealmNotifier);
            this.nativePtr = jNativeGetSharedRealm;
            arrayList.clear();
            list.remove(this);
            this.osRealmConfig = osRealmConfig;
            this.schemaInfo = new OsSchemaInfo(nativeGetSchemaInfo(jNativeGetSharedRealm), this);
            context.addReference(this);
            this.capabilities = androidCapabilities;
            this.realmNotifier = androidRealmNotifier;
            if (versionID.equals(VersionID.LIVE)) {
                nativeSetAutoRefresh(jNativeGetSharedRealm, androidCapabilities.canDeliverNotification());
            }
        } catch (Throwable th) {
            try {
                for (OsSharedRealm osSharedRealm : this.tempSharedRealmsForCallback) {
                    if (!osSharedRealm.isClosed()) {
                        osSharedRealm.close();
                    }
                }
                throw th;
            } catch (Throwable th2) {
                this.tempSharedRealmsForCallback.clear();
                sharedRealmsUnderConstruction.remove(this);
                throw th2;
            }
        }
    }

    OsSharedRealm(long j, OsRealmConfig osRealmConfig) {
        this(j, osRealmConfig, osRealmConfig.getContext());
        for (OsSharedRealm osSharedRealm : sharedRealmsUnderConstruction) {
            if (osSharedRealm.context == osRealmConfig.getContext()) {
                osSharedRealm.tempSharedRealmsForCallback.add(this);
                return;
            }
        }
        throw new IllegalStateException("Cannot find the parent 'OsSharedRealm' which is under construction.");
    }

    OsSharedRealm(long j, OsRealmConfig osRealmConfig, NativeContext nativeContext) {
        this.tempSharedRealmsForCallback = new ArrayList();
        this.pendingRows = new CopyOnWriteArrayList();
        this.iterators = new ArrayList();
        this.nativePtr = j;
        this.osRealmConfig = osRealmConfig;
        this.schemaInfo = new OsSchemaInfo(nativeGetSchemaInfo(j), this);
        this.context = nativeContext;
        nativeContext.addReference(this);
        this.capabilities = new AndroidCapabilities();
        this.realmNotifier = null;
        nativeSetAutoRefresh(j, false);
    }

    public static OsSharedRealm getInstance(RealmConfiguration realmConfiguration, VersionID versionID) {
        return getInstance(new OsRealmConfig.Builder(realmConfiguration), versionID);
    }

    public static OsSharedRealm getInstance(OsRealmConfig.Builder builder, VersionID versionID) {
        OsRealmConfig osRealmConfigBuild = builder.build();
        ObjectServerFacade.getSyncFacadeIfPossible().wrapObjectStoreSessionIfRequired(osRealmConfigBuild);
        return new OsSharedRealm(osRealmConfigBuild, versionID);
    }

    public static void initialize(File file) {
        if (temporaryDirectory != null) {
            return;
        }
        String absolutePath = file.getAbsolutePath();
        if (!file.isDirectory() && !file.mkdirs() && !file.isDirectory()) {
            throw new IOException("failed to create temporary directory: " + absolutePath);
        }
        if (!absolutePath.endsWith("/")) {
            absolutePath = absolutePath + "/";
        }
        nativeInit(absolutePath);
        temporaryDirectory = file;
    }

    public static File getTemporaryDirectory() {
        return temporaryDirectory;
    }

    public void beginTransaction() {
        detachIterators();
        executePendingRowQueries();
        nativeBeginTransaction(this.nativePtr);
    }

    public void commitTransaction() {
        nativeCommitTransaction(this.nativePtr);
    }

    public void cancelTransaction() {
        nativeCancelTransaction(this.nativePtr);
    }

    public boolean isInTransaction() {
        return nativeIsInTransaction(this.nativePtr);
    }

    public boolean hasTable(String str) {
        return nativeHasTable(this.nativePtr, str);
    }

    public Table getTable(String str) {
        return new Table(this, nativeGetTableRef(this.nativePtr, str));
    }

    public Table createTable(String str) {
        return new Table(this, nativeCreateTable(this.nativePtr, str));
    }

    public Table createTableWithPrimaryKey(String str, String str2, RealmFieldType realmFieldType, boolean z) {
        return new Table(this, nativeCreateTableWithPrimaryKeyField(this.nativePtr, str, str2, realmFieldType.getNativeValue(), z));
    }

    public void renameTable(String str, String str2) {
        try {
            nativeRenameTable(this.nativePtr, str, str2);
        } catch (IllegalArgumentException e) {
            throw new RealmError(e.getMessage());
        }
    }

    public String[] getTablesNames() {
        String[] strArrNativeGetTablesName = nativeGetTablesName(this.nativePtr);
        return strArrNativeGetTablesName != null ? strArrNativeGetTablesName : new String[0];
    }

    public long size() {
        return nativeSize(this.nativePtr);
    }

    public String getPath() {
        return this.osRealmConfig.getRealmConfiguration().getPath();
    }

    public boolean isEmpty() {
        return nativeIsEmpty(this.nativePtr);
    }

    public void refresh() {
        if (isFrozen()) {
            throw new IllegalStateException("It is not possible to refresh frozen Realms.");
        }
        nativeRefresh(this.nativePtr);
    }

    public VersionID getVersionID() {
        long[] jArrNativeGetVersionID = nativeGetVersionID(this.nativePtr);
        if (jArrNativeGetVersionID == null) {
            throw new IllegalStateException("Cannot get versionId, this could be related to a non existing read/write transaction");
        }
        return new VersionID(jArrNativeGetVersionID[0], jArrNativeGetVersionID[1]);
    }

    public boolean isClosed() {
        return nativeIsClosed(this.nativePtr);
    }

    public void writeCopy(File file, @Nullable byte[] bArr) {
        if (file.isFile() && file.exists()) {
            throw new IllegalArgumentException("The destination file must not exist");
        }
        if (isSyncRealm()) {
            Util.checkNotOnMainThread("writeCopyTo() cannot be called from the main thread when using synchronized Realms.");
        }
        try {
            nativeWriteCopy(this.nativePtr, file.getAbsolutePath(), bArr);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message.contains("Could not write file as not all client changes are integrated in server")) {
                throw new IllegalStateException(message);
            }
            throw e;
        }
    }

    public boolean compact() {
        return nativeCompact(this.nativePtr);
    }

    public void setAutoRefresh(boolean z) {
        this.capabilities.checkCanDeliverNotification(null);
        nativeSetAutoRefresh(this.nativePtr, z);
    }

    public boolean waitForChange() {
        return nativeWaitForChange(this.nativePtr);
    }

    public void stopWaitForChange() {
        nativeStopWaitForChange(this.nativePtr);
    }

    public boolean isAutoRefresh() {
        return nativeIsAutoRefresh(this.nativePtr);
    }

    public RealmConfiguration getConfiguration() {
        return this.osRealmConfig.getRealmConfiguration();
    }

    public long getNumberOfVersions() {
        return nativeNumberOfVersions(this.nativePtr);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        RealmNotifier realmNotifier = this.realmNotifier;
        if (realmNotifier != null) {
            realmNotifier.close();
        }
        synchronized (this.context) {
            nativeCloseSharedRealm(this.nativePtr);
        }
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public OsSchemaInfo getSchemaInfo() {
        return this.schemaInfo;
    }

    public void registerSchemaChangedCallback(SchemaChangedCallback schemaChangedCallback) {
        nativeRegisterSchemaChangedCallback(this.nativePtr, schemaChangedCallback);
    }

    public boolean isSyncRealm() {
        return this.osRealmConfig.getResolvedRealmURI() != null;
    }

    public boolean isFrozen() {
        return nativeIsFrozen(this.nativePtr);
    }

    public OsSharedRealm freeze() {
        return new OsSharedRealm(this.osRealmConfig, getVersionID());
    }

    void addIterator(OsResults.Iterator iterator) {
        this.iterators.add(new WeakReference<>(iterator));
    }

    private void detachIterators() {
        Iterator<WeakReference<OsResults.Iterator>> it = this.iterators.iterator();
        while (it.hasNext()) {
            OsResults.Iterator iterator = it.next().get();
            if (iterator != null) {
                iterator.detach();
            }
        }
        this.iterators.clear();
    }

    void invalidateIterators() {
        Iterator<WeakReference<OsResults.Iterator>> it = this.iterators.iterator();
        while (it.hasNext()) {
            OsResults.Iterator iterator = it.next().get();
            if (iterator != null) {
                iterator.invalidate();
            }
        }
        this.iterators.clear();
    }

    void addPendingRow(PendingRow pendingRow) {
        this.pendingRows.add(new WeakReference<>(pendingRow));
    }

    void removePendingRow(PendingRow pendingRow) {
        for (WeakReference<PendingRow> weakReference : this.pendingRows) {
            PendingRow pendingRow2 = weakReference.get();
            if (pendingRow2 == null || pendingRow2 == pendingRow) {
                this.pendingRows.remove(weakReference);
            }
        }
    }

    private void executePendingRowQueries() {
        Iterator<WeakReference<PendingRow>> it = this.pendingRows.iterator();
        while (it.hasNext()) {
            PendingRow pendingRow = it.next().get();
            if (pendingRow != null) {
                pendingRow.executeQuery();
            }
        }
        this.pendingRows.clear();
    }

    private static void runMigrationCallback(long j, OsRealmConfig osRealmConfig, MigrationCallback migrationCallback, long j2) {
        migrationCallback.onMigrationNeeded(new OsSharedRealm(j, osRealmConfig), j2, osRealmConfig.getRealmConfiguration().getSchemaVersion());
    }

    private static void runInitializationCallback(long j, OsRealmConfig osRealmConfig, InitializationCallback initializationCallback) {
        initializationCallback.onInit(new OsSharedRealm(j, osRealmConfig));
    }
}

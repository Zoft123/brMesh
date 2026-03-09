package io.realm;

import android.content.Context;
import android.os.Looper;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmCache;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.CheckedRow;
import io.realm.internal.ColumnInfo;
import io.realm.internal.InvalidRow;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.Util;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
abstract class BaseRealm implements Closeable {
    static final String CLOSED_REALM_MESSAGE = "This Realm instance has already been closed, making it unusable.";
    static final String DELETE_NOT_SUPPORTED_UNDER_PARTIAL_SYNC = "This API is not supported by partially synchronized Realms. Either unsubscribe using 'Realm.unsubscribeAsync()' or delete the objects using a query and 'RealmResults.deleteAllFromRealm()'";
    private static final String INCORRECT_THREAD_CLOSE_MESSAGE = "Realm access from incorrect thread. Realm instance can only be closed on the thread it was created.";
    static final String INCORRECT_THREAD_MESSAGE = "Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.";
    static final String LISTENER_NOT_ALLOWED_MESSAGE = "Listeners cannot be used on current thread.";
    private static final String NOT_IN_TRANSACTION_MESSAGE = "Changing Realm data can only be done from inside a transaction.";
    static volatile Context applicationContext;
    protected final RealmConfiguration configuration;
    final boolean frozen;
    private RealmCache realmCache;
    private OsSharedRealm.SchemaChangedCallback schemaChangedCallback;
    public OsSharedRealm sharedRealm;
    private boolean shouldCloseSharedRealm;
    final long threadId;
    static final RealmThreadPoolExecutor asyncTaskExecutor = RealmThreadPoolExecutor.newDefaultExecutor();
    public static final RealmThreadPoolExecutor WRITE_EXECUTOR = RealmThreadPoolExecutor.newSingleThreadExecutor();
    public static final ThreadLocalRealmObjectContext objectContext = new ThreadLocalRealmObjectContext();

    public abstract Flowable asFlowable();

    public abstract BaseRealm freeze();

    public abstract RealmSchema getSchema();

    public abstract boolean isEmpty();

    BaseRealm(RealmCache realmCache, @Nullable OsSchemaInfo osSchemaInfo, OsSharedRealm.VersionID versionID) {
        this(realmCache.getConfiguration(), osSchemaInfo, versionID);
        this.realmCache = realmCache;
    }

    BaseRealm(RealmConfiguration realmConfiguration, @Nullable OsSchemaInfo osSchemaInfo, OsSharedRealm.VersionID versionID) {
        this.schemaChangedCallback = new OsSharedRealm.SchemaChangedCallback() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.OsSharedRealm.SchemaChangedCallback
            public void onSchemaChanged() {
                RealmSchema schema = BaseRealm.this.getSchema();
                if (schema != null) {
                    schema.refresh();
                }
                if (BaseRealm.this instanceof Realm) {
                    schema.createKeyPathMapping();
                }
            }
        };
        this.threadId = Thread.currentThread().getId();
        this.configuration = realmConfiguration;
        this.realmCache = null;
        OsSharedRealm.MigrationCallback migrationCallbackCreateMigrationCallback = (osSchemaInfo == null || realmConfiguration.getMigration() == null) ? null : createMigrationCallback(realmConfiguration.getMigration());
        final Realm.Transaction initialDataTransaction = realmConfiguration.getInitialDataTransaction();
        OsSharedRealm osSharedRealm = OsSharedRealm.getInstance(new OsRealmConfig.Builder(realmConfiguration).fifoFallbackDir(new File(applicationContext.getFilesDir(), ".realm.temp")).autoUpdateNotification(true).migrationCallback(migrationCallbackCreateMigrationCallback).schemaInfo(osSchemaInfo).initializationCallback(initialDataTransaction != null ? new OsSharedRealm.InitializationCallback() { // from class: io.realm.BaseRealm.2
            @Override // io.realm.internal.OsSharedRealm.InitializationCallback
            public void onInit(OsSharedRealm osSharedRealm2) {
                initialDataTransaction.execute(Realm.createInstance(osSharedRealm2));
            }
        } : null), versionID);
        this.sharedRealm = osSharedRealm;
        this.frozen = osSharedRealm.isFrozen();
        this.shouldCloseSharedRealm = true;
        this.sharedRealm.registerSchemaChangedCallback(this.schemaChangedCallback);
    }

    BaseRealm(OsSharedRealm osSharedRealm) {
        this.schemaChangedCallback = new OsSharedRealm.SchemaChangedCallback() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.OsSharedRealm.SchemaChangedCallback
            public void onSchemaChanged() {
                RealmSchema schema = BaseRealm.this.getSchema();
                if (schema != null) {
                    schema.refresh();
                }
                if (BaseRealm.this instanceof Realm) {
                    schema.createKeyPathMapping();
                }
            }
        };
        this.threadId = Thread.currentThread().getId();
        this.configuration = osSharedRealm.getConfiguration();
        this.realmCache = null;
        this.sharedRealm = osSharedRealm;
        this.frozen = osSharedRealm.isFrozen();
        this.shouldCloseSharedRealm = false;
    }

    public void setAutoRefresh(boolean z) {
        checkIfValid();
        this.sharedRealm.setAutoRefresh(z);
    }

    public boolean isAutoRefresh() {
        return this.sharedRealm.isAutoRefresh();
    }

    public void refresh() {
        checkIfValid();
        checkAllowQueriesOnUiThread();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot refresh a Realm instance inside a transaction.");
        }
        this.sharedRealm.refresh();
    }

    public boolean isInTransaction() {
        checkIfValid();
        return this.sharedRealm.isInTransaction();
    }

    protected <T extends BaseRealm> void addListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        checkIfValid();
        this.sharedRealm.capabilities.checkCanDeliverNotification(LISTENER_NOT_ALLOWED_MESSAGE);
        if (this.frozen) {
            throw new IllegalStateException("It is not possible to add a change listener to a frozen Realm since it never changes.");
        }
        this.sharedRealm.realmNotifier.addChangeListener(this, realmChangeListener);
    }

    protected <T extends BaseRealm> void removeListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        if (isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.configuration.getPath());
        }
        this.sharedRealm.realmNotifier.removeChangeListener(this, realmChangeListener);
    }

    protected void removeAllListeners() {
        if (isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.configuration.getPath());
        }
        this.sharedRealm.realmNotifier.removeChangeListeners(this);
    }

    public void writeCopyTo(File file) {
        if (file == null) {
            throw new IllegalArgumentException("The destination argument cannot be null");
        }
        checkIfValid();
        this.sharedRealm.writeCopy(file, null);
    }

    public void writeEncryptedCopyTo(File file, byte[] bArr) {
        if (file == null) {
            throw new IllegalArgumentException("The destination argument cannot be null");
        }
        checkIfValid();
        this.sharedRealm.writeCopy(file, bArr);
    }

    @Deprecated
    public boolean waitForChange() {
        checkIfValid();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot wait for changes inside of a transaction.");
        }
        if (Looper.myLooper() != null) {
            throw new IllegalStateException("Cannot wait for changes inside a Looper thread. Use RealmChangeListeners instead.");
        }
        boolean zWaitForChange = this.sharedRealm.waitForChange();
        if (zWaitForChange) {
            this.sharedRealm.refresh();
        }
        return zWaitForChange;
    }

    @Deprecated
    public void stopWaitForChange() {
        RealmCache realmCache = this.realmCache;
        if (realmCache != null) {
            realmCache.invokeWithLock(new RealmCache.Callback0() { // from class: io.realm.BaseRealm.3
                @Override // io.realm.RealmCache.Callback0
                public void onCall() {
                    if (BaseRealm.this.sharedRealm == null || BaseRealm.this.sharedRealm.isClosed()) {
                        throw new IllegalStateException(BaseRealm.CLOSED_REALM_MESSAGE);
                    }
                    BaseRealm.this.sharedRealm.stopWaitForChange();
                }
            });
            return;
        }
        throw new IllegalStateException(CLOSED_REALM_MESSAGE);
    }

    public void beginTransaction() {
        checkIfValid();
        this.sharedRealm.beginTransaction();
    }

    public void commitTransaction() {
        checkIfValid();
        this.sharedRealm.commitTransaction();
    }

    public void cancelTransaction() {
        checkIfValid();
        this.sharedRealm.cancelTransaction();
    }

    public boolean isFrozen() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || osSharedRealm.isClosed()) {
            throw new IllegalStateException(CLOSED_REALM_MESSAGE);
        }
        return this.frozen;
    }

    public long getNumberOfActiveVersions() {
        checkIfValid();
        return getSharedRealm().getNumberOfVersions();
    }

    protected void checkIfValid() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || osSharedRealm.isClosed()) {
            throw new IllegalStateException(CLOSED_REALM_MESSAGE);
        }
        if (!this.frozen && this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException(INCORRECT_THREAD_MESSAGE);
        }
    }

    protected void checkAllowQueriesOnUiThread() {
        if (getSharedRealm().capabilities.isMainThread() && !getConfiguration().isAllowQueriesOnUiThread()) {
            throw new RealmException("Queries on the UI thread have been disabled. They can be enabled by setting 'RealmConfiguration.Builder.allowQueriesOnUiThread(true)'.");
        }
    }

    protected void checkAllowWritesOnUiThread() {
        if (getSharedRealm().capabilities.isMainThread() && !getConfiguration().isAllowWritesOnUiThread()) {
            throw new RealmException("Running transactions on the UI thread has been disabled. It can be enabled by setting 'RealmConfiguration.Builder.allowWritesOnUiThread(true)'.");
        }
    }

    protected void checkIfInTransaction() {
        if (!this.sharedRealm.isInTransaction()) {
            throw new IllegalStateException(NOT_IN_TRANSACTION_MESSAGE);
        }
    }

    protected void checkIfValidAndInTransaction() {
        if (!isInTransaction()) {
            throw new IllegalStateException(NOT_IN_TRANSACTION_MESSAGE);
        }
    }

    Row getEmbeddedObjectRow(String str, RealmObjectProxy realmObjectProxy, String str2, RealmSchema realmSchema, RealmObjectSchema realmObjectSchema) {
        long columnKey = realmObjectSchema.getColumnKey(str2);
        RealmFieldType fieldType = realmObjectSchema.getFieldType(str2);
        Row row$realm = realmObjectProxy.realmGet$proxyState().getRow$realm();
        if (!realmObjectSchema.isPropertyAcceptableForEmbeddedObject(realmObjectSchema.getFieldType(str2))) {
            throw new IllegalArgumentException(String.format("Field '%s' does not contain a valid link", str2));
        }
        String propertyClassName = realmObjectSchema.getPropertyClassName(str2);
        if (propertyClassName.equals(str)) {
            return realmSchema.getTable(str).getCheckedRow(row$realm.createEmbeddedObject(columnKey, fieldType));
        }
        throw new IllegalArgumentException(String.format("Parent type %s expects that property '%s' be of type %s but was %s.", realmObjectSchema.getClassName(), str2, propertyClassName, str));
    }

    void checkNotInSync() {
        if (this.configuration.isSyncConfiguration()) {
            throw new UnsupportedOperationException("You cannot perform destructive changes to a schema of a synced Realm");
        }
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public long getVersion() {
        return OsObjectStore.getSchemaVersion(this.sharedRealm);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.frozen && this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException(INCORRECT_THREAD_CLOSE_MESSAGE);
        }
        RealmCache realmCache = this.realmCache;
        if (realmCache != null) {
            realmCache.release(this);
        } else {
            doClose();
        }
    }

    void doClose() {
        this.realmCache = null;
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || !this.shouldCloseSharedRealm) {
            return;
        }
        osSharedRealm.close();
        this.sharedRealm = null;
    }

    public boolean isClosed() {
        if (!this.frozen && this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException(INCORRECT_THREAD_MESSAGE);
        }
        OsSharedRealm osSharedRealm = this.sharedRealm;
        return osSharedRealm == null || osSharedRealm.isClosed();
    }

    <E extends RealmModel> E get(@Nullable Class<E> cls, @Nullable String str, UncheckedRow uncheckedRow) {
        if (str != null) {
            return new DynamicRealmObject(this, CheckedRow.getFromRow(uncheckedRow));
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, uncheckedRow, getSchema().getColumnInfo((Class<? extends RealmModel>) cls), false, Collections.EMPTY_LIST);
    }

    <E extends RealmModel> E get(Class<E> cls, long j, boolean z, List<String> list) {
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, getSchema().getTable((Class<? extends RealmModel>) cls).getUncheckedRow(j), getSchema().getColumnInfo((Class<? extends RealmModel>) cls), z, list);
    }

    <E extends RealmModel> E get(@Nullable Class<E> cls, @Nullable String str, long j) {
        boolean z = str != null;
        Table table = z ? getSchema().getTable(str) : getSchema().getTable((Class<? extends RealmModel>) cls);
        if (z) {
            return new DynamicRealmObject(this, j != -1 ? table.getCheckedRow(j) : InvalidRow.INSTANCE);
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, j != -1 ? table.getUncheckedRow(j) : InvalidRow.INSTANCE, getSchema().getColumnInfo((Class<? extends RealmModel>) cls), false, Collections.EMPTY_LIST);
    }

    public void deleteAll() {
        checkIfValid();
        Iterator<RealmObjectSchema> it = getSchema().getAll().iterator();
        while (it.hasNext()) {
            getSchema().getTable(it.next().getClassName()).clear();
        }
    }

    static boolean deleteRealm(final RealmConfiguration realmConfiguration) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        if (!OsObjectStore.callWithLock(realmConfiguration, new Runnable() { // from class: io.realm.BaseRealm.4
            @Override // java.lang.Runnable
            public void run() {
                atomicBoolean.set(Util.deleteRealm(realmConfiguration.getPath(), realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()));
            }
        })) {
            throw new IllegalStateException("It's not allowed to delete the file associated with an open Realm. Remember to close() all the instances of the Realm before deleting its file: " + realmConfiguration.getPath());
        }
        return atomicBoolean.get();
    }

    static boolean compactRealm(RealmConfiguration realmConfiguration) {
        OsSharedRealm osSharedRealm = OsSharedRealm.getInstance(realmConfiguration, OsSharedRealm.VersionID.LIVE);
        boolean zCompact = osSharedRealm.compact();
        Boolean boolValueOf = Boolean.valueOf(zCompact);
        osSharedRealm.close();
        boolValueOf.getClass();
        return zCompact;
    }

    protected static void migrateRealm(final RealmConfiguration realmConfiguration, @Nullable final RealmMigration realmMigration) throws FileNotFoundException {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException("RealmConfiguration must be provided");
        }
        if (realmConfiguration.isSyncConfiguration()) {
            throw new IllegalArgumentException("Manual migrations are not supported for synced Realms");
        }
        if (realmMigration == null && realmConfiguration.getMigration() == null) {
            throw new RealmMigrationNeededException(realmConfiguration.getPath(), "RealmMigration must be provided.");
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback() { // from class: io.realm.BaseRealm.5
            @Override // io.realm.RealmCache.Callback
            public void onResult(int i) {
                if (i != 0) {
                    throw new IllegalStateException("Cannot migrate a Realm file that is already open: " + realmConfiguration.getPath());
                }
                if (!new File(realmConfiguration.getPath()).exists()) {
                    atomicBoolean.set(true);
                    return;
                }
                OsSchemaInfo osSchemaInfo = new OsSchemaInfo(realmConfiguration.getSchemaMediator().getExpectedObjectSchemaInfoMap().values());
                RealmMigration migration = realmMigration;
                if (migration == null) {
                    migration = realmConfiguration.getMigration();
                }
                OsSharedRealm osSharedRealm = OsSharedRealm.getInstance(new OsRealmConfig.Builder(realmConfiguration).autoUpdateNotification(false).schemaInfo(osSchemaInfo).migrationCallback(migration != null ? BaseRealm.createMigrationCallback(migration) : null), OsSharedRealm.VersionID.LIVE);
                if (osSharedRealm != null) {
                    osSharedRealm.close();
                }
            }
        });
        if (atomicBoolean.get()) {
            throw new FileNotFoundException("Cannot migrate a Realm file which doesn't exist: " + realmConfiguration.getPath());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static OsSharedRealm.MigrationCallback createMigrationCallback(final RealmMigration realmMigration) {
        return new OsSharedRealm.MigrationCallback() { // from class: io.realm.BaseRealm.6
            @Override // io.realm.internal.OsSharedRealm.MigrationCallback
            public void onMigrationNeeded(OsSharedRealm osSharedRealm, long j, long j2) {
                realmMigration.migrate(DynamicRealm.createInstance(osSharedRealm), j, j2);
            }
        };
    }

    protected void finalize() throws Throwable {
        OsSharedRealm osSharedRealm;
        if (this.shouldCloseSharedRealm && (osSharedRealm = this.sharedRealm) != null && !osSharedRealm.isClosed()) {
            RealmLog.warn("Remember to call close() on all Realm instances. Realm %s is being finalized without being closed, this can lead to running out of native memory.", this.configuration.getPath());
            RealmCache realmCache = this.realmCache;
            if (realmCache != null) {
                realmCache.leak();
            }
        }
        super.finalize();
    }

    OsSharedRealm getSharedRealm() {
        return this.sharedRealm;
    }

    public static final class RealmObjectContext {
        private boolean acceptDefaultValue;
        private ColumnInfo columnInfo;
        private List<String> excludeFields;
        private BaseRealm realm;
        private Row row;

        public void set(BaseRealm baseRealm, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
            this.realm = baseRealm;
            this.row = row;
            this.columnInfo = columnInfo;
            this.acceptDefaultValue = z;
            this.excludeFields = list;
        }

        BaseRealm getRealm() {
            return this.realm;
        }

        public Row getRow() {
            return this.row;
        }

        public ColumnInfo getColumnInfo() {
            return this.columnInfo;
        }

        public boolean getAcceptDefaultValue() {
            return this.acceptDefaultValue;
        }

        public List<String> getExcludeFields() {
            return this.excludeFields;
        }

        public void clear() {
            this.realm = null;
            this.row = null;
            this.columnInfo = null;
            this.acceptDefaultValue = false;
            this.excludeFields = null;
        }
    }

    static final class ThreadLocalRealmObjectContext extends ThreadLocal<RealmObjectContext> {
        ThreadLocalRealmObjectContext() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public RealmObjectContext initialValue() {
            return new RealmObjectContext();
        }
    }

    public static abstract class InstanceCallback<T extends BaseRealm> {
        public abstract void onSuccess(T t);

        public void onError(Throwable th) {
            throw new RealmException("Exception happens when initializing Realm in the background thread.", th);
        }
    }
}

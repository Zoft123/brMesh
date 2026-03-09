package io.realm;

import io.realm.BaseRealm;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmNotifier;
import io.realm.internal.Util;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.util.Pair;
import io.realm.log.RealmLog;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
final class RealmCache {
    private static final String ASYNC_CALLBACK_NULL_MSG = "The callback cannot be null.";
    private static final String ASYNC_NOT_ALLOWED_MSG = "Realm instances cannot be loaded asynchronously on a non-looper thread.";
    private static final String DIFFERENT_KEY_MESSAGE = "Wrong key used to decrypt Realm.";
    private static final String WRONG_REALM_CLASS_MESSAGE = "The type of Realm class must be Realm or DynamicRealm.";
    private static final List<WeakReference<RealmCache>> cachesList = new ArrayList();
    private static final Collection<RealmCache> leakedCaches = new ConcurrentLinkedQueue();
    private RealmConfiguration configuration;
    private final String realmPath;
    private final Map<Pair<RealmCacheType, OsSharedRealm.VersionID>, ReferenceCounter> refAndCountMap = new HashMap();
    private final AtomicBoolean isLeaked = new AtomicBoolean(false);
    private final Set<String> pendingRealmFileCreation = new HashSet();

    interface Callback {
        void onResult(int i);
    }

    interface Callback0 {
        void onCall();
    }

    private static abstract class ReferenceCounter {
        protected AtomicInteger globalCount;
        protected final ThreadLocal<Integer> localCount;

        abstract void clearThreadLocalCache();

        abstract BaseRealm getRealmInstance();

        abstract int getThreadLocalCount();

        abstract boolean hasInstanceAvailableForThread();

        abstract void onRealmCreated(BaseRealm baseRealm);

        private ReferenceCounter() {
            this.localCount = new ThreadLocal<>();
            this.globalCount = new AtomicInteger(0);
        }

        public void incrementThreadCount(int i) {
            Integer num = this.localCount.get();
            ThreadLocal<Integer> threadLocal = this.localCount;
            if (num != null) {
                i += num.intValue();
            }
            threadLocal.set(Integer.valueOf(i));
        }

        public void setThreadCount(int i) {
            this.localCount.set(Integer.valueOf(i));
        }

        public int getGlobalCount() {
            return this.globalCount.get();
        }
    }

    private static class GlobalReferenceCounter extends ReferenceCounter {
        private BaseRealm cachedRealm;

        private GlobalReferenceCounter() {
            super();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        boolean hasInstanceAvailableForThread() {
            return this.cachedRealm != null;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        BaseRealm getRealmInstance() {
            return this.cachedRealm;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        void onRealmCreated(BaseRealm baseRealm) {
            this.cachedRealm = baseRealm;
            this.localCount.set(0);
            this.globalCount.incrementAndGet();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public void clearThreadLocalCache() {
            String path = this.cachedRealm.getPath();
            this.localCount.set(null);
            this.cachedRealm = null;
            if (this.globalCount.decrementAndGet() >= 0) {
                return;
            }
            throw new IllegalStateException("Global reference counter of Realm" + path + " not be negative.");
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        int getThreadLocalCount() {
            return this.globalCount.get();
        }
    }

    private static class ThreadConfinedReferenceCounter extends ReferenceCounter {
        private final ThreadLocal<BaseRealm> localRealm;

        private ThreadConfinedReferenceCounter() {
            super();
            this.localRealm = new ThreadLocal<>();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public boolean hasInstanceAvailableForThread() {
            return this.localRealm.get() != null;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public BaseRealm getRealmInstance() {
            return this.localRealm.get();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public void onRealmCreated(BaseRealm baseRealm) {
            this.localRealm.set(baseRealm);
            this.localCount.set(0);
            this.globalCount.incrementAndGet();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public void clearThreadLocalCache() {
            String path = this.localRealm.get().getPath();
            this.localCount.set(null);
            this.localRealm.set(null);
            if (this.globalCount.decrementAndGet() >= 0) {
                return;
            }
            throw new IllegalStateException("Global reference counter of Realm" + path + " can not be negative.");
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public int getThreadLocalCount() {
            Integer num = this.localCount.get();
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }
    }

    private enum RealmCacheType {
        TYPED_REALM,
        DYNAMIC_REALM;

        static RealmCacheType valueOf(Class<? extends BaseRealm> cls) {
            if (cls == Realm.class) {
                return TYPED_REALM;
            }
            if (cls == DynamicRealm.class) {
                return DYNAMIC_REALM;
            }
            throw new IllegalArgumentException(RealmCache.WRONG_REALM_CLASS_MESSAGE);
        }
    }

    private static class CreateRealmRunnable<T extends BaseRealm> implements Runnable {
        private final BaseRealm.InstanceCallback<T> callback;
        private final CountDownLatch canReleaseBackgroundInstanceLatch = new CountDownLatch(1);
        private final RealmConfiguration configuration;
        private Future future;
        private final RealmNotifier notifier;
        private final Class<T> realmClass;

        CreateRealmRunnable(RealmNotifier realmNotifier, RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
            this.configuration = realmConfiguration;
            this.realmClass = cls;
            this.callback = instanceCallback;
            this.notifier = realmNotifier;
        }

        public void setFuture(Future future) {
            this.future = future;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseRealm baseRealmCreateRealmOrGetFromCache = null;
            try {
                try {
                    try {
                        baseRealmCreateRealmOrGetFromCache = RealmCache.createRealmOrGetFromCache(this.configuration, this.realmClass);
                        if (!this.notifier.post(new Runnable() { // from class: io.realm.RealmCache.CreateRealmRunnable.1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public void run() {
                                if (CreateRealmRunnable.this.future == null || CreateRealmRunnable.this.future.isCancelled()) {
                                    CreateRealmRunnable.this.canReleaseBackgroundInstanceLatch.countDown();
                                    return;
                                }
                                BaseRealm baseRealm = null;
                                try {
                                    BaseRealm baseRealmCreateRealmOrGetFromCache2 = RealmCache.createRealmOrGetFromCache(CreateRealmRunnable.this.configuration, CreateRealmRunnable.this.realmClass);
                                    CreateRealmRunnable.this.canReleaseBackgroundInstanceLatch.countDown();
                                    th = null;
                                    baseRealm = baseRealmCreateRealmOrGetFromCache2;
                                } catch (Throwable th) {
                                    th = th;
                                    CreateRealmRunnable.this.canReleaseBackgroundInstanceLatch.countDown();
                                }
                                if (baseRealm != null) {
                                    CreateRealmRunnable.this.callback.onSuccess(baseRealm);
                                } else {
                                    CreateRealmRunnable.this.callback.onError(th);
                                }
                            }
                        })) {
                            this.canReleaseBackgroundInstanceLatch.countDown();
                        }
                        if (!this.canReleaseBackgroundInstanceLatch.await(2L, TimeUnit.SECONDS)) {
                            RealmLog.warn("Timeout for creating Realm instance in foreground thread in `CreateRealmRunnable` ", new Object[0]);
                        }
                        if (baseRealmCreateRealmOrGetFromCache != null) {
                            baseRealmCreateRealmOrGetFromCache.close();
                        }
                    } catch (Throwable th) {
                        if (!ObjectServerFacade.getSyncFacadeIfPossible().wasDownloadInterrupted(th)) {
                            RealmLog.error(th, "`CreateRealmRunnable` failed.", new Object[0]);
                            this.notifier.post(new Runnable() { // from class: io.realm.RealmCache.CreateRealmRunnable.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    CreateRealmRunnable.this.callback.onError(th);
                                }
                            });
                        }
                        if (baseRealmCreateRealmOrGetFromCache != null) {
                            baseRealmCreateRealmOrGetFromCache.close();
                        }
                    }
                } catch (InterruptedException e) {
                    RealmLog.warn(e, "`CreateRealmRunnable` has been interrupted.", new Object[0]);
                    if (baseRealmCreateRealmOrGetFromCache == null) {
                        return;
                    }
                    baseRealmCreateRealmOrGetFromCache.close();
                }
            } catch (Throwable th2) {
                if (baseRealmCreateRealmOrGetFromCache != null) {
                    baseRealmCreateRealmOrGetFromCache.close();
                }
                throw th2;
            }
        }
    }

    private RealmCache(String str) {
        this.realmPath = str;
    }

    private static RealmCache getCache(String str, boolean z) {
        RealmCache realmCache;
        List<WeakReference<RealmCache>> list = cachesList;
        synchronized (list) {
            Iterator<WeakReference<RealmCache>> it = list.iterator();
            realmCache = null;
            while (it.hasNext()) {
                RealmCache realmCache2 = it.next().get();
                if (realmCache2 == null) {
                    it.remove();
                } else if (realmCache2.realmPath.equals(str)) {
                    realmCache = realmCache2;
                }
            }
            if (realmCache == null && z) {
                realmCache = new RealmCache(str);
                cachesList.add(new WeakReference<>(realmCache));
            }
        }
        return realmCache;
    }

    static <T extends BaseRealm> RealmAsyncTask createRealmOrGetFromCacheAsync(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        return getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCacheAsync(realmConfiguration, instanceCallback, cls);
    }

    private synchronized <T extends BaseRealm> RealmAsyncTask doCreateRealmOrGetFromCacheAsync(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        Future<?> futureSubmitTransaction;
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        androidCapabilities.checkCanDeliverNotification(ASYNC_NOT_ALLOWED_MSG);
        if (instanceCallback == null) {
            throw new IllegalArgumentException(ASYNC_CALLBACK_NULL_MSG);
        }
        if (realmConfiguration.isSyncConfiguration() && !realmConfiguration.realmExists()) {
            this.pendingRealmFileCreation.add(realmConfiguration.getPath());
        }
        CreateRealmRunnable createRealmRunnable = new CreateRealmRunnable(new AndroidRealmNotifier(null, androidCapabilities), realmConfiguration, instanceCallback, cls);
        futureSubmitTransaction = BaseRealm.asyncTaskExecutor.submitTransaction(createRealmRunnable);
        createRealmRunnable.setFuture(futureSubmitTransaction);
        ObjectServerFacade.getSyncFacadeIfPossible().createNativeSyncSession(realmConfiguration);
        return new RealmAsyncTaskImpl(futureSubmitTransaction, BaseRealm.asyncTaskExecutor);
    }

    static <E extends BaseRealm> E createRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls) {
        return (E) getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCache(realmConfiguration, cls, OsSharedRealm.VersionID.LIVE);
    }

    static <E extends BaseRealm> E createRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls, OsSharedRealm.VersionID versionID) {
        return (E) getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCache(realmConfiguration, cls, versionID);
    }

    private synchronized <E extends BaseRealm> E doCreateRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls, OsSharedRealm.VersionID versionID) {
        E e;
        ReferenceCounter refCounter = getRefCounter(cls, versionID);
        boolean z = getTotalGlobalRefCount() == 0;
        if (z) {
            copyAssetFileIfNeeded(realmConfiguration);
            boolean zRealmExists = realmConfiguration.realmExists();
            if (realmConfiguration.isSyncConfiguration() && (!zRealmExists || this.pendingRealmFileCreation.contains(realmConfiguration.getPath()))) {
                ObjectServerFacade.getSyncFacadeIfPossible().wrapObjectStoreSessionIfRequired(new OsRealmConfig.Builder(realmConfiguration).build());
                ObjectServerFacade.getSyncFacadeIfPossible().downloadInitialRemoteChanges(realmConfiguration);
                this.pendingRealmFileCreation.remove(realmConfiguration.getPath());
            }
            this.configuration = realmConfiguration;
        } else {
            validateConfiguration(realmConfiguration);
        }
        if (!refCounter.hasInstanceAvailableForThread()) {
            createInstance(cls, refCounter, versionID);
        }
        refCounter.incrementThreadCount(1);
        e = (E) refCounter.getRealmInstance();
        if (z) {
            ObjectServerFacade.getSyncFacadeIfPossible().downloadInitialFlexibleSyncData(Realm.createInstance(e.sharedRealm), realmConfiguration);
            if (!realmConfiguration.isReadOnly()) {
                e.refresh();
            }
        }
        return e;
    }

    private <E extends BaseRealm> ReferenceCounter getRefCounter(Class<E> cls, OsSharedRealm.VersionID versionID) {
        Pair<RealmCacheType, OsSharedRealm.VersionID> pair = new Pair<>(RealmCacheType.valueOf((Class<? extends BaseRealm>) cls), versionID);
        ReferenceCounter globalReferenceCounter = this.refAndCountMap.get(pair);
        if (globalReferenceCounter == null) {
            boolean zEquals = versionID.equals(OsSharedRealm.VersionID.LIVE);
            if (zEquals) {
                globalReferenceCounter = new ThreadConfinedReferenceCounter();
            } else {
                globalReferenceCounter = new GlobalReferenceCounter();
            }
            this.refAndCountMap.put(pair, globalReferenceCounter);
        }
        return globalReferenceCounter;
    }

    private <E extends BaseRealm> void createInstance(Class<E> cls, ReferenceCounter referenceCounter, OsSharedRealm.VersionID versionID) {
        BaseRealm baseRealmCreateInstance;
        if (cls == Realm.class) {
            baseRealmCreateInstance = Realm.createInstance(this, versionID);
            baseRealmCreateInstance.getSchema().createKeyPathMapping();
        } else if (cls == DynamicRealm.class) {
            baseRealmCreateInstance = DynamicRealm.createInstance(this, versionID);
        } else {
            throw new IllegalArgumentException(WRONG_REALM_CLASS_MESSAGE);
        }
        referenceCounter.onRealmCreated(baseRealmCreateInstance);
    }

    synchronized void release(BaseRealm baseRealm) {
        BaseRealm realmInstance;
        String path = baseRealm.getPath();
        ReferenceCounter refCounter = getRefCounter(baseRealm.getClass(), baseRealm.isFrozen() ? baseRealm.sharedRealm.getVersionID() : OsSharedRealm.VersionID.LIVE);
        int threadLocalCount = refCounter.getThreadLocalCount();
        if (threadLocalCount <= 0) {
            RealmLog.warn("%s has been closed already. refCount is %s", path, Integer.valueOf(threadLocalCount));
            return;
        }
        int i = threadLocalCount - 1;
        if (i == 0) {
            refCounter.clearThreadLocalCache();
            baseRealm.doClose();
            if (getTotalLiveRealmGlobalRefCount() == 0) {
                this.configuration = null;
                for (ReferenceCounter referenceCounter : this.refAndCountMap.values()) {
                    if ((referenceCounter instanceof GlobalReferenceCounter) && (realmInstance = referenceCounter.getRealmInstance()) != null) {
                        while (!realmInstance.isClosed()) {
                            realmInstance.close();
                        }
                    }
                }
                ObjectServerFacade.getFacade(baseRealm.getConfiguration().isSyncConfiguration()).realmClosed(baseRealm.getConfiguration());
            }
        } else {
            refCounter.setThreadCount(i);
        }
    }

    private void validateConfiguration(RealmConfiguration realmConfiguration) {
        if (this.configuration.equals(realmConfiguration)) {
            return;
        }
        if (!Arrays.equals(this.configuration.getEncryptionKey(), realmConfiguration.getEncryptionKey())) {
            throw new IllegalArgumentException(DIFFERENT_KEY_MESSAGE);
        }
        RealmMigration migration = realmConfiguration.getMigration();
        RealmMigration migration2 = this.configuration.getMigration();
        if (migration2 != null && migration != null && migration2.getClass().equals(migration.getClass()) && !migration.equals(migration2)) {
            throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. The most likely cause is that equals() and hashCode() are not overridden in the migration class: " + realmConfiguration.getMigration().getClass().getCanonicalName());
        }
        throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. \nCached configuration: \n" + this.configuration + "\n\nNew configuration: \n" + realmConfiguration);
    }

    static void invokeWithGlobalRefCount(RealmConfiguration realmConfiguration, Callback callback) {
        synchronized (cachesList) {
            RealmCache cache = getCache(realmConfiguration.getPath(), false);
            if (cache == null) {
                callback.onResult(0);
            } else {
                cache.doInvokeWithGlobalRefCount(callback);
            }
        }
    }

    private synchronized void doInvokeWithGlobalRefCount(Callback callback) {
        callback.onResult(getTotalGlobalRefCount());
    }

    synchronized void invokeWithLock(Callback0 callback0) {
        callback0.onCall();
    }

    private static void copyAssetFileIfNeeded(final RealmConfiguration realmConfiguration) {
        final File file = realmConfiguration.hasAssetFile() ? new File(realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()) : null;
        final String syncServerCertificateFilePath = ObjectServerFacade.getFacade(realmConfiguration.isSyncConfiguration()).getSyncServerCertificateFilePath(realmConfiguration);
        boolean zIsEmptyString = Util.isEmptyString(syncServerCertificateFilePath);
        final boolean z = !zIsEmptyString;
        if (file == null && zIsEmptyString) {
            return;
        }
        OsObjectStore.callWithLock(realmConfiguration, new Runnable() { // from class: io.realm.RealmCache.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                if (file != null) {
                    RealmCache.copyFileIfNeeded(realmConfiguration.getAssetFilePath(), file);
                }
                if (z) {
                    RealmCache.copyFileIfNeeded(ObjectServerFacade.getFacade(realmConfiguration.isSyncConfiguration()).getSyncServerCertificateAssetName(realmConfiguration), new File(syncServerCertificateFilePath));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void copyFileIfNeeded(java.lang.String r6, java.io.File r7) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Invalid input stream to the asset file: "
            boolean r1 = r7.exists()
            if (r1 == 0) goto L9
            goto L3c
        L9:
            r1 = 0
            android.content.Context r2 = io.realm.BaseRealm.applicationContext     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            java.io.InputStream r2 = r2.open(r6)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            if (r2 == 0) goto L51
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r0.<init>(r7)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r7 = 4096(0x1000, float:5.74E-42)
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L47
        L1f:
            int r3 = r2.read(r7)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L47
            r4 = -1
            if (r3 <= r4) goto L2b
            r4 = 0
            r0.write(r7, r4, r3)     // Catch: java.lang.Throwable -> L45 java.io.IOException -> L47
            goto L1f
        L2b:
            if (r2 == 0) goto L32
            r2.close()     // Catch: java.io.IOException -> L31
            goto L32
        L31:
            r1 = move-exception
        L32:
            r0.close()     // Catch: java.io.IOException -> L36
            goto L3a
        L36:
            r6 = move-exception
            if (r1 != 0) goto L3a
            r1 = r6
        L3a:
            if (r1 != 0) goto L3d
        L3c:
            return
        L3d:
            io.realm.exceptions.RealmFileException r6 = new io.realm.exceptions.RealmFileException
            io.realm.exceptions.RealmFileException$Kind r7 = io.realm.exceptions.RealmFileException.Kind.ACCESS_ERROR
            r6.<init>(r7, r1)
            throw r6
        L45:
            r6 = move-exception
            goto L4b
        L47:
            r7 = move-exception
            goto L4f
        L49:
            r6 = move-exception
            r0 = r1
        L4b:
            r1 = r2
            goto L84
        L4d:
            r7 = move-exception
            r0 = r1
        L4f:
            r1 = r2
            goto L6a
        L51:
            io.realm.exceptions.RealmFileException r7 = new io.realm.exceptions.RealmFileException     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            io.realm.exceptions.RealmFileException$Kind r3 = io.realm.exceptions.RealmFileException.Kind.ACCESS_ERROR     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r4.append(r6)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r7.<init>(r3, r0)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            throw r7     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
        L65:
            r6 = move-exception
            r0 = r1
            goto L84
        L68:
            r7 = move-exception
            r0 = r1
        L6a:
            io.realm.exceptions.RealmFileException r2 = new io.realm.exceptions.RealmFileException     // Catch: java.lang.Throwable -> L83
            io.realm.exceptions.RealmFileException$Kind r3 = io.realm.exceptions.RealmFileException.Kind.ACCESS_ERROR     // Catch: java.lang.Throwable -> L83
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L83
            r4.<init>()     // Catch: java.lang.Throwable -> L83
            java.lang.String r5 = "Could not resolve the path to the asset file: "
            r4.append(r5)     // Catch: java.lang.Throwable -> L83
            r4.append(r6)     // Catch: java.lang.Throwable -> L83
            java.lang.String r6 = r4.toString()     // Catch: java.lang.Throwable -> L83
            r2.<init>(r3, r6, r7)     // Catch: java.lang.Throwable -> L83
            throw r2     // Catch: java.lang.Throwable -> L83
        L83:
            r6 = move-exception
        L84:
            if (r1 == 0) goto L89
            r1.close()     // Catch: java.io.IOException -> L89
        L89:
            if (r0 == 0) goto L8e
            r0.close()     // Catch: java.io.IOException -> L8e
        L8e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.RealmCache.copyFileIfNeeded(java.lang.String, java.io.File):void");
    }

    static int getLocalThreadCount(RealmConfiguration realmConfiguration) {
        int threadLocalCount = 0;
        RealmCache cache = getCache(realmConfiguration.getPath(), false);
        if (cache == null) {
            return 0;
        }
        Iterator<ReferenceCounter> it = cache.refAndCountMap.values().iterator();
        while (it.hasNext()) {
            threadLocalCount += it.next().getThreadLocalCount();
        }
        return threadLocalCount;
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    private int getTotalGlobalRefCount() {
        Iterator<ReferenceCounter> it = this.refAndCountMap.values().iterator();
        int globalCount = 0;
        while (it.hasNext()) {
            globalCount += it.next().getGlobalCount();
        }
        return globalCount;
    }

    private int getTotalLiveRealmGlobalRefCount() {
        int globalCount = 0;
        for (ReferenceCounter referenceCounter : this.refAndCountMap.values()) {
            if (referenceCounter instanceof ThreadConfinedReferenceCounter) {
                globalCount += referenceCounter.getGlobalCount();
            }
        }
        return globalCount;
    }

    void leak() {
        if (this.isLeaked.getAndSet(true)) {
            return;
        }
        leakedCaches.add(this);
    }
}

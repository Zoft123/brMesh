package io.realm;

import android.content.Context;
import io.realm.Realm;
import io.realm.annotations.RealmModule;
import io.realm.coroutines.FlowFactory;
import io.realm.coroutines.RealmFlowFactory;
import io.realm.exceptions.RealmException;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Util;
import io.realm.internal.modules.CompositeMediator;
import io.realm.internal.modules.FilterableMediator;
import io.realm.rx.RealmObservableFactory;
import io.realm.rx.RxObservableFactory;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class RealmConfiguration {
    private static final Object DEFAULT_MODULE;
    protected static final RealmProxyMediator DEFAULT_MODULE_MEDIATOR;
    public static final String DEFAULT_REALM_NAME = "default.realm";
    private final boolean allowQueriesOnUiThread;
    private final boolean allowWritesOnUiThread;
    private final String assetFilePath;
    private final String canonicalPath;
    private final CompactOnLaunchCallback compactOnLaunch;
    private final boolean deleteRealmIfMigrationNeeded;
    private final OsRealmConfig.Durability durability;
    private final FlowFactory flowFactory;
    private final Realm.Transaction initialDataTransaction;
    private final boolean isRecoveryConfiguration;
    private final byte[] key;
    private final long maxNumberOfActiveVersions;
    private final RealmMigration migration;
    private final boolean readOnly;
    private final File realmDirectory;
    private final String realmFileName;
    private final RxObservableFactory rxObservableFactory;
    private final RealmProxyMediator schemaMediator;
    private final long schemaVersion;

    protected boolean isSyncConfiguration() {
        return false;
    }

    static {
        Object defaultModule = Realm.getDefaultModule();
        DEFAULT_MODULE = defaultModule;
        if (defaultModule != null) {
            RealmProxyMediator moduleMediator = getModuleMediator(defaultModule.getClass().getCanonicalName());
            if (!moduleMediator.transformerApplied()) {
                throw new ExceptionInInitializerError("RealmTransformer doesn't seem to be applied. Please update the project configuration to use the Realm Gradle plugin. See https://docs.mongodb.com/realm/sdk/android/install/#customize-dependecies-defined-by-the-realm-gradle-plugin");
            }
            DEFAULT_MODULE_MEDIATOR = moduleMediator;
            return;
        }
        DEFAULT_MODULE_MEDIATOR = null;
    }

    protected RealmConfiguration(File file, @Nullable String str, @Nullable byte[] bArr, long j, @Nullable RealmMigration realmMigration, boolean z, OsRealmConfig.Durability durability, RealmProxyMediator realmProxyMediator, @Nullable RxObservableFactory rxObservableFactory, @Nullable FlowFactory flowFactory, @Nullable Realm.Transaction transaction, boolean z2, @Nullable CompactOnLaunchCallback compactOnLaunchCallback, boolean z3, long j2, boolean z4, boolean z5) {
        this.realmDirectory = file.getParentFile();
        this.realmFileName = file.getName();
        this.canonicalPath = file.getAbsolutePath();
        this.assetFilePath = str;
        this.key = bArr;
        this.schemaVersion = j;
        this.migration = realmMigration;
        this.deleteRealmIfMigrationNeeded = z;
        this.durability = durability;
        this.schemaMediator = realmProxyMediator;
        this.rxObservableFactory = rxObservableFactory;
        this.flowFactory = flowFactory;
        this.initialDataTransaction = transaction;
        this.readOnly = z2;
        this.compactOnLaunch = compactOnLaunchCallback;
        this.isRecoveryConfiguration = z3;
        this.maxNumberOfActiveVersions = j2;
        this.allowWritesOnUiThread = z4;
        this.allowQueriesOnUiThread = z5;
    }

    public File getRealmDirectory() {
        return this.realmDirectory;
    }

    public String getRealmFileName() {
        return this.realmFileName;
    }

    public byte[] getEncryptionKey() {
        byte[] bArr = this.key;
        if (bArr == null) {
            return null;
        }
        return Arrays.copyOf(bArr, bArr.length);
    }

    public long getSchemaVersion() {
        return this.schemaVersion;
    }

    public RealmMigration getMigration() {
        return this.migration;
    }

    public boolean shouldDeleteRealmIfMigrationNeeded() {
        return this.deleteRealmIfMigrationNeeded;
    }

    public OsRealmConfig.Durability getDurability() {
        return this.durability;
    }

    protected RealmProxyMediator getSchemaMediator() {
        return this.schemaMediator;
    }

    protected Realm.Transaction getInitialDataTransaction() {
        return this.initialDataTransaction;
    }

    public boolean hasAssetFile() {
        return !Util.isEmptyString(this.assetFilePath);
    }

    @Nullable
    public String getAssetFilePath() {
        return this.assetFilePath;
    }

    public CompactOnLaunchCallback getCompactOnLaunchCallback() {
        return this.compactOnLaunch;
    }

    public Set<Class<? extends RealmModel>> getRealmObjectClasses() {
        return this.schemaMediator.getModelClasses();
    }

    public String getPath() {
        return this.canonicalPath;
    }

    protected boolean realmExists() {
        return new File(this.canonicalPath).exists();
    }

    public RxObservableFactory getRxFactory() {
        RxObservableFactory rxObservableFactory = this.rxObservableFactory;
        if (rxObservableFactory != null) {
            return rxObservableFactory;
        }
        throw new UnsupportedOperationException("RxJava seems to be missing from the classpath. Remember to add it as an implementation dependency. See https://github.com/realm/realm-java/tree/master/examples/rxJavaExample for more details.");
    }

    public FlowFactory getFlowFactory() {
        FlowFactory flowFactory = this.flowFactory;
        if (flowFactory != null) {
            return flowFactory;
        }
        throw new UnsupportedOperationException("The coroutines framework is missing from the classpath. Remember to add it as an implementation dependency. See https://github.com/Kotlin/kotlinx.coroutines#android for more details");
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isRecoveryConfiguration() {
        return this.isRecoveryConfiguration;
    }

    public long getMaxNumberOfActiveVersions() {
        return this.maxNumberOfActiveVersions;
    }

    public boolean isAllowWritesOnUiThread() {
        return this.allowWritesOnUiThread;
    }

    public boolean isAllowQueriesOnUiThread() {
        return this.allowQueriesOnUiThread;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RealmConfiguration realmConfiguration = (RealmConfiguration) obj;
            if (this.schemaVersion != realmConfiguration.schemaVersion || this.deleteRealmIfMigrationNeeded != realmConfiguration.deleteRealmIfMigrationNeeded || this.readOnly != realmConfiguration.readOnly || this.isRecoveryConfiguration != realmConfiguration.isRecoveryConfiguration) {
                return false;
            }
            File file = this.realmDirectory;
            if (file == null ? realmConfiguration.realmDirectory != null : !file.equals(realmConfiguration.realmDirectory)) {
                return false;
            }
            String str = this.realmFileName;
            if (str == null ? realmConfiguration.realmFileName != null : !str.equals(realmConfiguration.realmFileName)) {
                return false;
            }
            if (!this.canonicalPath.equals(realmConfiguration.canonicalPath)) {
                return false;
            }
            String str2 = this.assetFilePath;
            if (str2 == null ? realmConfiguration.assetFilePath != null : !str2.equals(realmConfiguration.assetFilePath)) {
                return false;
            }
            if (!Arrays.equals(this.key, realmConfiguration.key)) {
                return false;
            }
            RealmMigration realmMigration = this.migration;
            if (realmMigration == null ? realmConfiguration.migration != null : !realmMigration.equals(realmConfiguration.migration)) {
                return false;
            }
            if (this.durability != realmConfiguration.durability || !this.schemaMediator.equals(realmConfiguration.schemaMediator)) {
                return false;
            }
            RxObservableFactory rxObservableFactory = this.rxObservableFactory;
            if (rxObservableFactory == null ? realmConfiguration.rxObservableFactory != null : !rxObservableFactory.equals(realmConfiguration.rxObservableFactory)) {
                return false;
            }
            Realm.Transaction transaction = this.initialDataTransaction;
            if (transaction == null ? realmConfiguration.initialDataTransaction != null : !transaction.equals(realmConfiguration.initialDataTransaction)) {
                return false;
            }
            CompactOnLaunchCallback compactOnLaunchCallback = this.compactOnLaunch;
            if (compactOnLaunchCallback == null ? realmConfiguration.compactOnLaunch != null : !compactOnLaunchCallback.equals(realmConfiguration.compactOnLaunch)) {
                return false;
            }
            if (this.maxNumberOfActiveVersions == realmConfiguration.maxNumberOfActiveVersions) {
                return true;
            }
        }
        return false;
    }

    protected Realm getInstance(OsSharedRealm.VersionID versionID) {
        return (Realm) RealmCache.createRealmOrGetFromCache(this, Realm.class, versionID);
    }

    public int hashCode() {
        File file = this.realmDirectory;
        int iHashCode = (file != null ? file.hashCode() : 0) * 31;
        String str = this.realmFileName;
        int iHashCode2 = (((iHashCode + (str != null ? str.hashCode() : 0)) * 31) + this.canonicalPath.hashCode()) * 31;
        String str2 = this.assetFilePath;
        int iHashCode3 = (((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + Arrays.hashCode(this.key)) * 31;
        long j = this.schemaVersion;
        int i = (iHashCode3 + ((int) (j ^ (j >>> 32)))) * 31;
        RealmMigration realmMigration = this.migration;
        int iHashCode4 = (((((((i + (realmMigration != null ? realmMigration.hashCode() : 0)) * 31) + (this.deleteRealmIfMigrationNeeded ? 1 : 0)) * 31) + this.durability.hashCode()) * 31) + this.schemaMediator.hashCode()) * 31;
        RxObservableFactory rxObservableFactory = this.rxObservableFactory;
        int iHashCode5 = (iHashCode4 + (rxObservableFactory != null ? rxObservableFactory.hashCode() : 0)) * 31;
        Realm.Transaction transaction = this.initialDataTransaction;
        int iHashCode6 = (((iHashCode5 + (transaction != null ? transaction.hashCode() : 0)) * 31) + (this.readOnly ? 1 : 0)) * 31;
        CompactOnLaunchCallback compactOnLaunchCallback = this.compactOnLaunch;
        int iHashCode7 = (((iHashCode6 + (compactOnLaunchCallback != null ? compactOnLaunchCallback.hashCode() : 0)) * 31) + (this.isRecoveryConfiguration ? 1 : 0)) * 31;
        long j2 = this.maxNumberOfActiveVersions;
        return iHashCode7 + ((int) (j2 ^ (j2 >>> 32)));
    }

    protected static RealmProxyMediator createSchemaMediator(Set<Object> set, Set<Class<? extends RealmModel>> set2, boolean z) {
        if (set2.size() > 0) {
            return new FilterableMediator(DEFAULT_MODULE_MEDIATOR, set2, z);
        }
        if (set.size() == 1) {
            return getModuleMediator(set.iterator().next().getClass().getCanonicalName());
        }
        RealmProxyMediator[] realmProxyMediatorArr = new RealmProxyMediator[set.size()];
        Iterator<Object> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            realmProxyMediatorArr[i] = getModuleMediator(it.next().getClass().getCanonicalName());
            i++;
        }
        return new CompositeMediator(realmProxyMediatorArr);
    }

    private static RealmProxyMediator getModuleMediator(String str) {
        String[] strArrSplit = str.split("\\.");
        String str2 = String.format(Locale.US, "io.realm.%s%s", strArrSplit[strArrSplit.length - 1], "Mediator");
        try {
            Constructor<?> constructor = Class.forName(str2).getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return (RealmProxyMediator) constructor.newInstance(null);
        } catch (ClassNotFoundException e) {
            throw new RealmException("Could not find " + str2, e);
        } catch (IllegalAccessException e2) {
            throw new RealmException("Could not create an instance of " + str2, e2);
        } catch (InstantiationException e3) {
            throw new RealmException("Could not create an instance of " + str2, e3);
        } catch (InvocationTargetException e4) {
            throw new RealmException("Could not create an instance of " + str2, e4);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("realmDirectory: ");
        File file = this.realmDirectory;
        sb.append(file != null ? file.toString() : "");
        sb.append("\nrealmFileName : ");
        sb.append(this.realmFileName);
        sb.append("\ncanonicalPath: ");
        sb.append(this.canonicalPath);
        sb.append("\nkey: [length: ");
        sb.append(this.key == null ? 0 : 64);
        sb.append("]\nschemaVersion: ");
        sb.append(Long.toString(this.schemaVersion));
        sb.append("\nmigration: ");
        sb.append(this.migration);
        sb.append("\ndeleteRealmIfMigrationNeeded: ");
        sb.append(this.deleteRealmIfMigrationNeeded);
        sb.append("\ndurability: ");
        sb.append(this.durability);
        sb.append("\nschemaMediator: ");
        sb.append(this.schemaMediator);
        sb.append("\nreadOnly: ");
        sb.append(this.readOnly);
        sb.append("\ncompactOnLaunch: ");
        sb.append(this.compactOnLaunch);
        sb.append("\nmaxNumberOfActiveVersions: ");
        sb.append(this.maxNumberOfActiveVersions);
        return sb.toString();
    }

    protected static RealmConfiguration forRecovery(String str, @Nullable byte[] bArr, RealmProxyMediator realmProxyMediator) {
        return new RealmConfiguration(new File(str), null, bArr, 0L, null, false, OsRealmConfig.Durability.FULL, realmProxyMediator, null, null, null, true, null, true, Long.MAX_VALUE, false, true);
    }

    public static class Builder {
        private boolean allowQueriesOnUiThread;
        private boolean allowWritesOnUiThread;
        private String assetFilePath;
        private CompactOnLaunchCallback compactOnLaunch;
        private HashSet<Class<? extends RealmModel>> debugSchema;
        private boolean deleteRealmIfMigrationNeeded;
        private File directory;
        private OsRealmConfig.Durability durability;
        private boolean excludeDebugSchema;
        private String fileName;

        @Nullable
        private FlowFactory flowFactory;
        private Realm.Transaction initialDataTransaction;
        private byte[] key;
        private long maxNumberOfActiveVersions;
        private RealmMigration migration;
        private HashSet<Object> modules;
        private boolean readOnly;

        @Nullable
        private RxObservableFactory rxFactory;
        private long schemaVersion;

        public Builder() {
            this(BaseRealm.applicationContext);
        }

        Builder(Context context) {
            this.modules = new HashSet<>();
            this.debugSchema = new HashSet<>();
            this.excludeDebugSchema = false;
            this.maxNumberOfActiveVersions = Long.MAX_VALUE;
            if (context == null) {
                throw new IllegalStateException("Call `Realm.init(Context)` before creating a RealmConfiguration");
            }
            RealmCore.loadLibrary(context);
            initializeBuilder(context);
        }

        private void initializeBuilder(Context context) {
            this.directory = context.getFilesDir();
            this.fileName = "default.realm";
            this.key = null;
            this.schemaVersion = 0L;
            this.migration = null;
            this.deleteRealmIfMigrationNeeded = false;
            this.durability = OsRealmConfig.Durability.FULL;
            this.readOnly = false;
            this.compactOnLaunch = null;
            if (RealmConfiguration.DEFAULT_MODULE != null) {
                this.modules.add(RealmConfiguration.DEFAULT_MODULE);
            }
            this.allowWritesOnUiThread = false;
            this.allowQueriesOnUiThread = true;
        }

        public Builder name(String str) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("A non-empty filename must be provided");
            }
            this.fileName = str;
            return this;
        }

        public Builder directory(File file) {
            if (file == null) {
                throw new IllegalArgumentException("Non-null 'dir' required.");
            }
            if (file.isFile()) {
                throw new IllegalArgumentException("'dir' is a file, not a directory: " + file.getAbsolutePath() + ".");
            }
            if (!file.exists() && !file.mkdirs()) {
                throw new IllegalArgumentException("Could not create the specified directory: " + file.getAbsolutePath() + ".");
            }
            if (!file.canWrite()) {
                throw new IllegalArgumentException("Realm directory is not writable: " + file.getAbsolutePath() + ".");
            }
            this.directory = file;
            return this;
        }

        public Builder encryptionKey(byte[] bArr) {
            if (bArr == null) {
                throw new IllegalArgumentException("A non-null key must be provided");
            }
            if (bArr.length != 64) {
                throw new IllegalArgumentException(String.format(Locale.US, "The provided key must be %s bytes. Yours was: %s", 64, Integer.valueOf(bArr.length)));
            }
            this.key = Arrays.copyOf(bArr, bArr.length);
            return this;
        }

        public Builder schemaVersion(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("Realm schema version numbers must be 0 (zero) or higher. Yours was: " + j);
            }
            this.schemaVersion = j;
            return this;
        }

        public Builder migration(RealmMigration realmMigration) {
            if (realmMigration == null) {
                throw new IllegalArgumentException("A non-null migration must be provided");
            }
            this.migration = realmMigration;
            return this;
        }

        public Builder deleteRealmIfMigrationNeeded() {
            String str = this.assetFilePath;
            if (str != null && str.length() != 0) {
                throw new IllegalStateException("Realm cannot clear its schema when previously configured to use an asset file by calling assetFile().");
            }
            this.deleteRealmIfMigrationNeeded = true;
            return this;
        }

        public Builder inMemory() {
            if (!Util.isEmptyString(this.assetFilePath)) {
                throw new RealmException("Realm can not use in-memory configuration if asset file is present.");
            }
            this.durability = OsRealmConfig.Durability.MEM_ONLY;
            return this;
        }

        public Builder modules(Object obj, Object... objArr) {
            this.modules.clear();
            addModule(obj);
            if (objArr != null) {
                for (Object obj2 : objArr) {
                    addModule(obj2);
                }
            }
            return this;
        }

        public final Builder addModule(Object obj) {
            if (obj != null) {
                checkModule(obj);
                this.modules.add(obj);
            }
            return this;
        }

        public Builder rxFactory(@Nonnull RxObservableFactory rxObservableFactory) {
            if (rxObservableFactory == null) {
                throw new IllegalArgumentException("The provided Rx Observable factory must not be null.");
            }
            this.rxFactory = rxObservableFactory;
            return this;
        }

        public Builder flowFactory(@Nonnull FlowFactory flowFactory) {
            if (flowFactory == null) {
                throw new IllegalArgumentException("The provided Flow factory must not be null.");
            }
            this.flowFactory = flowFactory;
            return this;
        }

        public Builder initialData(Realm.Transaction transaction) {
            this.initialDataTransaction = transaction;
            return this;
        }

        public Builder assetFile(String str) {
            if (Util.isEmptyString(str)) {
                throw new IllegalArgumentException("A non-empty asset file path must be provided");
            }
            if (this.durability == OsRealmConfig.Durability.MEM_ONLY) {
                throw new RealmException("Realm can not use in-memory configuration if asset file is present.");
            }
            if (this.deleteRealmIfMigrationNeeded) {
                throw new IllegalStateException("Realm cannot use an asset file when previously configured to clear its schema in migration by calling deleteRealmIfMigrationNeeded().");
            }
            this.assetFilePath = str;
            return this;
        }

        public Builder readOnly() {
            this.readOnly = true;
            return this;
        }

        public Builder compactOnLaunch() {
            return compactOnLaunch(new DefaultCompactOnLaunchCallback());
        }

        public Builder compactOnLaunch(CompactOnLaunchCallback compactOnLaunchCallback) {
            if (compactOnLaunchCallback == null) {
                throw new IllegalArgumentException("A non-null compactOnLaunch must be provided");
            }
            this.compactOnLaunch = compactOnLaunchCallback;
            return this;
        }

        public Builder maxNumberOfActiveVersions(long j) {
            if (j < 1) {
                throw new IllegalArgumentException("Only positive numbers above 0 are allowed. Yours was: " + j);
            }
            this.maxNumberOfActiveVersions = j;
            return this;
        }

        final Builder schema(Class<? extends RealmModel> cls, Class<? extends RealmModel>... clsArr) {
            if (cls == null) {
                throw new IllegalArgumentException("A non-null class must be provided");
            }
            this.modules.clear();
            this.modules.add(RealmConfiguration.DEFAULT_MODULE_MEDIATOR);
            this.debugSchema.add(cls);
            if (clsArr != null) {
                Collections.addAll(this.debugSchema, clsArr);
            }
            return this;
        }

        final Builder excludeSchema(Class<? extends RealmModel> cls, Class<? extends RealmModel>... clsArr) {
            this.excludeDebugSchema = true;
            return schema(cls, clsArr);
        }

        public Builder allowWritesOnUiThread(boolean z) {
            this.allowWritesOnUiThread = z;
            return this;
        }

        public Builder allowQueriesOnUiThread(boolean z) {
            this.allowQueriesOnUiThread = z;
            return this;
        }

        public RealmConfiguration build() {
            if (this.readOnly) {
                if (this.initialDataTransaction != null) {
                    throw new IllegalStateException("This Realm is marked as read-only. Read-only Realms cannot use initialData(Realm.Transaction).");
                }
                if (this.assetFilePath == null) {
                    throw new IllegalStateException("Only Realms provided using 'assetFile(path)' can be marked read-only. No such Realm was provided.");
                }
                if (this.deleteRealmIfMigrationNeeded) {
                    throw new IllegalStateException("'deleteRealmIfMigrationNeeded()' and read-only Realms cannot be combined");
                }
                if (this.compactOnLaunch != null) {
                    throw new IllegalStateException("'compactOnLaunch()' and read-only Realms cannot be combined");
                }
            }
            if (this.rxFactory == null && Util.isRxJavaAvailable()) {
                this.rxFactory = new RealmObservableFactory(true);
            }
            if (this.flowFactory == null && Util.isCoroutinesAvailable()) {
                this.flowFactory = new RealmFlowFactory(true);
            }
            return new RealmConfiguration(new File(this.directory, this.fileName), this.assetFilePath, this.key, this.schemaVersion, this.migration, this.deleteRealmIfMigrationNeeded, this.durability, RealmConfiguration.createSchemaMediator(this.modules, this.debugSchema, this.excludeDebugSchema), this.rxFactory, this.flowFactory, this.initialDataTransaction, this.readOnly, this.compactOnLaunch, false, this.maxNumberOfActiveVersions, this.allowWritesOnUiThread, this.allowQueriesOnUiThread);
        }

        private void checkModule(Object obj) {
            if (obj.getClass().isAnnotationPresent(RealmModule.class)) {
                return;
            }
            throw new IllegalArgumentException(obj.getClass().getCanonicalName() + " is not a RealmModule. Add @RealmModule to the class definition.");
        }
    }
}

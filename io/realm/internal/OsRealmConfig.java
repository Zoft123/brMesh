package io.realm.internal;

import io.realm.CompactOnLaunchCallback;
import io.realm.RealmConfiguration;
import io.realm.internal.OsSharedRealm;
import io.realm.log.RealmLog;
import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class OsRealmConfig implements NativeObject {
    public static final byte CLIENT_RESYNC_MODE_DISCARD_LOCAL = 1;
    public static final byte CLIENT_RESYNC_MODE_MANUAL = 0;
    public static final byte CLIENT_RESYNC_MODE_RECOVER = 2;
    public static final byte CLIENT_RESYNC_MODE_RECOVER_OR_DISCARD = 3;
    private static final byte PROXYCONFIG_TYPE_VALUE_HTTP = 0;
    private static final byte SCHEMA_MODE_VALUE_ADDITIVE_DISCOVERED = 5;
    private static final byte SCHEMA_MODE_VALUE_ADDITIVE_EXPLICIT = 6;
    private static final byte SCHEMA_MODE_VALUE_AUTOMATIC = 0;
    private static final byte SCHEMA_MODE_VALUE_IMMUTABLE = 1;
    private static final byte SCHEMA_MODE_VALUE_MANUAL = 7;
    private static final byte SCHEMA_MODE_VALUE_READONLY = 2;
    private static final byte SCHEMA_MODE_VALUE_SOFT_RESET_FILE = 3;
    private static final byte SYNCSESSION_STOP_POLICY_VALUE_AFTER_CHANGES_UPLOADED = 2;
    private static final byte SYNCSESSION_STOP_POLICY_VALUE_IMMEDIATELY = 0;
    private static final byte SYNCSESSION_STOP_POLICY_VALUE_LIVE_INDEFINETELY = 1;
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final Object afterClientResetHandler;
    private final Object beforeClientResetHandler;
    private final CompactOnLaunchCallback compactOnLaunchCallback;
    private final NativeContext context;
    private final OsSharedRealm.InitializationCallback initializationCallback;
    private final OsSharedRealm.MigrationCallback migrationCallback;
    private final long nativePtr;
    private final RealmConfiguration realmConfiguration;
    private final URI resolvedRealmURI;

    private static native long nativeCreate(String str, String str2, boolean z, long j);

    private native String nativeCreateAndSetSyncConfig(long j, long j2, String str, String str2, String str3, String str4, String str5, String str6, byte b, String str7, String str8, String[] strArr, byte b2, Object obj, Object obj2, String str9, Object obj3);

    private static native void nativeEnableChangeNotification(long j, boolean z);

    private static native long nativeGetFinalizerPtr();

    private static native void nativeSetCompactOnLaunchCallback(long j, CompactOnLaunchCallback compactOnLaunchCallback);

    private static native void nativeSetEncryptionKey(long j, byte[] bArr);

    private static native void nativeSetInMemory(long j, boolean z);

    private native void nativeSetInitializationCallback(long j, OsSharedRealm.InitializationCallback initializationCallback);

    private native void nativeSetSchemaConfig(long j, byte b, long j2, long j3, @Nullable OsSharedRealm.MigrationCallback migrationCallback);

    private static native void nativeSetSyncConfigProxySettings(long j, byte b, String str, int i);

    private static native void nativeSetSyncConfigSslSettings(long j, boolean z, String str);

    /* synthetic */ OsRealmConfig(RealmConfiguration realmConfiguration, String str, boolean z, OsSchemaInfo osSchemaInfo, OsSharedRealm.MigrationCallback migrationCallback, OsSharedRealm.InitializationCallback initializationCallback, AnonymousClass1 anonymousClass1) {
        this(realmConfiguration, str, z, osSchemaInfo, migrationCallback, initializationCallback);
    }

    public enum Durability {
        FULL(0),
        MEM_ONLY(1);

        final int value;

        Durability(int i) {
            this.value = i;
        }
    }

    public enum SchemaMode {
        SCHEMA_MODE_AUTOMATIC((byte) 0),
        SCHEMA_MODE_IMMUTABLE((byte) 1),
        SCHEMA_MODE_READONLY((byte) 2),
        SCHEMA_MODE_SOFT_RESET_FILE((byte) 3),
        SCHEMA_MODE_ADDITIVE_DISCOVERED((byte) 5),
        SCHEMA_MODE_MANUAL((byte) 7);

        final byte value;

        SchemaMode(byte b) {
            this.value = b;
        }

        public byte getNativeValue() {
            return this.value;
        }
    }

    public enum SyncSessionStopPolicy {
        IMMEDIATELY((byte) 0),
        LIVE_INDEFINITELY((byte) 1),
        AFTER_CHANGES_UPLOADED((byte) 2);

        final byte value;

        SyncSessionStopPolicy(byte b) {
            this.value = b;
        }

        public byte getNativeValue() {
            return this.value;
        }
    }

    public static class Builder {
        private RealmConfiguration configuration;
        private OsSchemaInfo schemaInfo = null;
        private OsSharedRealm.MigrationCallback migrationCallback = null;
        private OsSharedRealm.InitializationCallback initializationCallback = null;
        private boolean autoUpdateNotification = false;
        private String fifoFallbackDir = "";

        public Builder(RealmConfiguration realmConfiguration) {
            this.configuration = realmConfiguration;
        }

        public Builder schemaInfo(@Nullable OsSchemaInfo osSchemaInfo) {
            this.schemaInfo = osSchemaInfo;
            return this;
        }

        public Builder migrationCallback(@Nullable OsSharedRealm.MigrationCallback migrationCallback) {
            this.migrationCallback = migrationCallback;
            return this;
        }

        public Builder initializationCallback(@Nullable OsSharedRealm.InitializationCallback initializationCallback) {
            this.initializationCallback = initializationCallback;
            return this;
        }

        public Builder autoUpdateNotification(boolean z) {
            this.autoUpdateNotification = z;
            return this;
        }

        public OsRealmConfig build() {
            return new OsRealmConfig(this.configuration, this.fifoFallbackDir, this.autoUpdateNotification, this.schemaInfo, this.migrationCallback, this.initializationCallback, null);
        }

        public Builder fifoFallbackDir(File file) {
            this.fifoFallbackDir = file.getAbsolutePath();
            return this;
        }
    }

    private OsRealmConfig(RealmConfiguration realmConfiguration, String str, boolean z, @Nullable OsSchemaInfo osSchemaInfo, @Nullable OsSharedRealm.MigrationCallback migrationCallback, @Nullable OsSharedRealm.InitializationCallback initializationCallback) {
        OsRealmConfig osRealmConfig;
        URI uri;
        String str2;
        String str3;
        int i;
        URI uri2;
        this.context = new NativeContext();
        this.realmConfiguration = realmConfiguration;
        this.nativePtr = nativeCreate(realmConfiguration.getPath(), str, true, realmConfiguration.getMaxNumberOfActiveVersions());
        NativeContext.dummyContext.addReference(this);
        Object[] syncConfigurationOptions = ObjectServerFacade.getSyncFacadeIfPossible().getSyncConfigurationOptions(realmConfiguration);
        String str4 = (String) syncConfigurationOptions[0];
        String str5 = (String) syncConfigurationOptions[1];
        String str6 = (String) syncConfigurationOptions[2];
        String str7 = (String) syncConfigurationOptions[3];
        String str8 = (String) syncConfigurationOptions[4];
        String str9 = (String) syncConfigurationOptions[5];
        String str10 = (String) syncConfigurationOptions[6];
        Byte b = (Byte) syncConfigurationOptions[7];
        String str11 = (String) syncConfigurationOptions[8];
        String str12 = (String) syncConfigurationOptions[9];
        Map map = (Map) syncConfigurationOptions[10];
        Byte b2 = (Byte) syncConfigurationOptions[11];
        this.beforeClientResetHandler = syncConfigurationOptions[12];
        this.afterClientResetHandler = syncConfigurationOptions[13];
        String str13 = (String) syncConfigurationOptions[14];
        Object obj = syncConfigurationOptions[15];
        Long l = (Long) syncConfigurationOptions[16];
        boolean zEquals = Boolean.TRUE.equals(syncConfigurationOptions[17]);
        String str14 = (String) syncConfigurationOptions[18];
        String[] strArr = new String[map != null ? map.size() * 2 : 0];
        if (map != null) {
            int i2 = 0;
            for (Map.Entry entry : map.entrySet()) {
                strArr[i2] = (String) entry.getKey();
                strArr[i2 + 1] = (String) entry.getValue();
                i2 += 2;
            }
        }
        byte[] encryptionKey = realmConfiguration.getEncryptionKey();
        if (encryptionKey != null) {
            nativeSetEncryptionKey(this.nativePtr, encryptionKey);
        }
        nativeSetInMemory(this.nativePtr, realmConfiguration.getDurability() == Durability.MEM_ONLY);
        nativeEnableChangeNotification(this.nativePtr, z);
        SchemaMode schemaMode = SchemaMode.SCHEMA_MODE_MANUAL;
        if (realmConfiguration.isRecoveryConfiguration()) {
            schemaMode = SchemaMode.SCHEMA_MODE_IMMUTABLE;
        } else if (realmConfiguration.isReadOnly()) {
            schemaMode = SchemaMode.SCHEMA_MODE_READONLY;
        } else if (str6 != null) {
            schemaMode = SchemaMode.SCHEMA_MODE_ADDITIVE_DISCOVERED;
        } else if (realmConfiguration.shouldDeleteRealmIfMigrationNeeded()) {
            schemaMode = SchemaMode.SCHEMA_MODE_SOFT_RESET_FILE;
        }
        long schemaVersion = realmConfiguration.getSchemaVersion();
        long nativePtr = osSchemaInfo == null ? 0L : osSchemaInfo.getNativePtr();
        this.migrationCallback = migrationCallback;
        nativeSetSchemaConfig(this.nativePtr, schemaMode.getNativeValue(), schemaVersion, nativePtr, migrationCallback);
        CompactOnLaunchCallback compactOnLaunchCallback = realmConfiguration.getCompactOnLaunchCallback();
        this.compactOnLaunchCallback = compactOnLaunchCallback;
        if (compactOnLaunchCallback != null) {
            nativeSetCompactOnLaunchCallback(this.nativePtr, compactOnLaunchCallback);
        }
        this.initializationCallback = initializationCallback;
        if (initializationCallback != null) {
            nativeSetInitializationCallback(this.nativePtr, initializationCallback);
        }
        if (str6 != null) {
            osRealmConfig = this;
            String strNativeCreateAndSetSyncConfig = osRealmConfig.nativeCreateAndSetSyncConfig(l.longValue(), this.nativePtr, str6, str4, str5, str8, str9, str10, b.byteValue(), str11, str12, strArr, b2.byteValue(), this.beforeClientResetHandler, this.afterClientResetHandler, str13, obj);
            try {
                strNativeCreateAndSetSyncConfig = str7 + str11.substring(1);
                uri = new URI(strNativeCreateAndSetSyncConfig);
                str3 = strNativeCreateAndSetSyncConfig;
                str2 = "Cannot create a URI from the Realm URL address";
            } catch (URISyntaxException e) {
                str2 = "Cannot create a URI from the Realm URL address";
                RealmLog.error(e, str2, new Object[0]);
                str3 = strNativeCreateAndSetSyncConfig;
                uri = null;
            }
            nativeSetSyncConfigSslSettings(osRealmConfig.nativePtr, zEquals, str14);
            ProxySelector proxySelector = ProxySelector.getDefault();
            if (uri != null && proxySelector != null) {
                try {
                    uri2 = new URI(str3.replaceFirst("ws", "http"));
                    i = 0;
                } catch (URISyntaxException e2) {
                    i = 0;
                    RealmLog.error(e2, str2, new Object[0]);
                    uri2 = null;
                }
                List<Proxy> listSelect = proxySelector.select(uri2);
                if (listSelect != null && !listSelect.isEmpty()) {
                    Proxy proxy = listSelect.get(i);
                    if (proxy.type() != Proxy.Type.DIRECT) {
                        byte b3 = AnonymousClass1.$SwitchMap$java$net$Proxy$Type[proxy.type().ordinal()] != 1 ? (byte) -1 : (byte) 0;
                        if (proxy.type() == Proxy.Type.HTTP) {
                            SocketAddress socketAddressAddress = proxy.address();
                            if (socketAddressAddress instanceof InetSocketAddress) {
                                InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddressAddress;
                                String hostString = osRealmConfig.getHostString(inetSocketAddress);
                                if (hostString != null) {
                                    nativeSetSyncConfigProxySettings(osRealmConfig.nativePtr, b3, hostString, inetSocketAddress.getPort());
                                } else {
                                    RealmLog.error("Could not retrieve proxy's hostname.", new Object[0]);
                                }
                            } else {
                                RealmLog.error("Unsupported proxy socket address type: " + socketAddressAddress.getClass().getName(), new Object[0]);
                            }
                        } else {
                            RealmLog.error("SOCKS proxies are not supported.", new Object[0]);
                        }
                    }
                }
            }
        } else {
            osRealmConfig = this;
            uri = null;
        }
        osRealmConfig.resolvedRealmURI = uri;
    }

    /* JADX INFO: renamed from: io.realm.internal.OsRealmConfig$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$net$Proxy$Type;

        static {
            int[] iArr = new int[Proxy.Type.values().length];
            $SwitchMap$java$net$Proxy$Type = iArr;
            try {
                iArr[Proxy.Type.HTTP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private String getHostString(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress.getHostName() != null) {
            return inetSocketAddress.getHostName();
        }
        if (inetSocketAddress.getAddress() == null) {
            return null;
        }
        InetAddress address = inetSocketAddress.getAddress();
        if (address.getHostName() != null) {
            return address.getHostName();
        }
        return address.getHostAddress();
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public RealmConfiguration getRealmConfiguration() {
        return this.realmConfiguration;
    }

    public URI getResolvedRealmURI() {
        return this.resolvedRealmURI;
    }

    NativeContext getContext() {
        return this.context;
    }
}

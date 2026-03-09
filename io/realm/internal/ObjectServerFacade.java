package io.realm.internal;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import io.realm.internal.OsSharedRealm;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class ObjectServerFacade {
    public static final int SYNC_CONFIG_OPTIONS = 19;
    private static final ObjectServerFacade nonSyncFacade = new ObjectServerFacade();
    private static ObjectServerFacade syncFacade;

    public interface RealmCacheAccessor {
        Realm createRealmOrGetFromCache(RealmConfiguration realmConfiguration, OsSharedRealm.VersionID versionID);
    }

    public interface RealmInstanceFactory {
        Realm createInstance(OsSharedRealm osSharedRealm);
    }

    public void checkFlexibleSyncEnabled(RealmConfiguration realmConfiguration) {
    }

    public void createNativeSyncSession(RealmConfiguration realmConfiguration) {
    }

    public void downloadInitialFlexibleSyncData(Realm realm, RealmConfiguration realmConfiguration) {
    }

    public void downloadInitialRemoteChanges(RealmConfiguration realmConfiguration) {
    }

    public String getSyncServerCertificateAssetName(RealmConfiguration realmConfiguration) {
        return null;
    }

    public String getSyncServerCertificateFilePath(RealmConfiguration realmConfiguration) {
        return null;
    }

    public void initialize(Context context, String str, RealmCacheAccessor realmCacheAccessor, RealmInstanceFactory realmInstanceFactory) {
    }

    public void realmClosed(RealmConfiguration realmConfiguration) {
    }

    public boolean wasDownloadInterrupted(Throwable th) {
        return false;
    }

    public void wrapObjectStoreSessionIfRequired(OsRealmConfig osRealmConfig) {
    }

    static {
        syncFacade = null;
        try {
            syncFacade = (ObjectServerFacade) Class.forName("io.realm.internal.SyncObjectServerFacade").getDeclaredConstructor(null).newInstance(null);
        } catch (ClassNotFoundException unused) {
        } catch (IllegalAccessException e) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e);
        } catch (InstantiationException e2) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e2);
        } catch (NoSuchMethodException e3) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e3);
        } catch (InvocationTargetException e4) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e4.getTargetException());
        }
    }

    public Object[] getSyncConfigurationOptions(RealmConfiguration realmConfiguration) {
        return new Object[19];
    }

    public static ObjectServerFacade getFacade(boolean z) {
        if (z) {
            return syncFacade;
        }
        return nonSyncFacade;
    }

    public static ObjectServerFacade getSyncFacadeIfPossible() {
        ObjectServerFacade objectServerFacade = syncFacade;
        return objectServerFacade != null ? objectServerFacade : nonSyncFacade;
    }
}

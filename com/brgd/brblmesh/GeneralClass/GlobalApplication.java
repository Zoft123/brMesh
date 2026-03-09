package com.brgd.brblmesh.GeneralClass;

import android.app.Application;
import android.content.Context;
import cn.com.broadlink.blelight.BLSBleLight;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/* JADX INFO: loaded from: classes.dex */
public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().schemaVersion(17L).migration(new CustomMigration()).build());
        BLSBleLight.init(this, false);
        BLSBleLight.setNeedRemoteCtrl(false);
        BLSBleLight.setShowDialogWhenPermInvalid(false);
        BLSBleLight.setShowDialogWhenBlueToothNeedRestart(false);
        String str = (String) SharePreferenceUtils.get(this, GlobalVariable.EXPORT_DIRECTORY, "");
        if (str != null) {
            Tool.deleteFile(this, str);
        }
    }

    public static Context getMyApplication() {
        return instance;
    }
}

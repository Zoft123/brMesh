package io.realm;

import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;

/* JADX INFO: loaded from: classes4.dex */
public interface com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface {
    RealmList<BleDevice> realmGet$bleDeviceRealmList();

    String realmGet$fileName();

    int realmGet$fixedId();

    String realmGet$fixedName();

    int realmGet$iconIndex();

    int realmGet$index();

    void realmSet$bleDeviceRealmList(RealmList<BleDevice> realmList);

    void realmSet$fileName(String str);

    void realmSet$fixedId(int i);

    void realmSet$fixedName(String str);

    void realmSet$iconIndex(int i);

    void realmSet$index(int i);
}

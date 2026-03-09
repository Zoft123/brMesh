package io.realm;

import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;

/* JADX INFO: loaded from: classes4.dex */
public interface com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface {
    RealmList<RadarDevice> realmGet$bleDeviceRealmList();

    int realmGet$fixedId();

    String realmGet$fixedName();

    int realmGet$index();

    void realmSet$bleDeviceRealmList(RealmList<RadarDevice> realmList);

    void realmSet$fixedId(int i);

    void realmSet$fixedName(String str);

    void realmSet$index(int i);
}

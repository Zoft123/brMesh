package io.realm;

import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;

/* JADX INFO: loaded from: classes4.dex */
public interface com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface {
    int realmGet$iconIndex();

    int realmGet$index();

    RealmList<SceneDevice> realmGet$sceneDeviceRealmList();

    String realmGet$sceneFileName();

    String realmGet$sceneName();

    int realmGet$sceneNumber();

    void realmSet$iconIndex(int i);

    void realmSet$index(int i);

    void realmSet$sceneDeviceRealmList(RealmList<SceneDevice> realmList);

    void realmSet$sceneFileName(String str);

    void realmSet$sceneName(String str);

    void realmSet$sceneNumber(int i);
}

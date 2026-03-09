package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class Scene extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface {
    private int iconIndex;
    private int index;

    @Ignore
    public boolean isEdit;

    @Ignore
    public boolean isSelect;
    private RealmList<SceneDevice> sceneDeviceRealmList;
    private String sceneFileName;
    private String sceneName;
    private int sceneNumber;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public int realmGet$iconIndex() {
        return this.iconIndex;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public int realmGet$index() {
        return this.index;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public RealmList realmGet$sceneDeviceRealmList() {
        return this.sceneDeviceRealmList;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public String realmGet$sceneFileName() {
        return this.sceneFileName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public String realmGet$sceneName() {
        return this.sceneName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public int realmGet$sceneNumber() {
        return this.sceneNumber;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$iconIndex(int i) {
        this.iconIndex = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$index(int i) {
        this.index = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneDeviceRealmList(RealmList realmList) {
        this.sceneDeviceRealmList = realmList;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneFileName(String str) {
        this.sceneFileName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneName(String str) {
        this.sceneName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxyInterface
    public void realmSet$sceneNumber(int i) {
        this.sceneNumber = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Scene() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setIndex(int i) {
        realmSet$index(i);
    }

    public int getIndex() {
        return realmGet$index();
    }

    public void setSceneNumber(int i) {
        realmSet$sceneNumber(i);
    }

    public int getSceneNumber() {
        return realmGet$sceneNumber();
    }

    public void setSceneName(String str) {
        realmSet$sceneName(str);
    }

    public String getSceneName() {
        return realmGet$sceneName();
    }

    public void setSceneFileName(String str) {
        realmSet$sceneFileName(str);
    }

    public String getSceneFileName() {
        return realmGet$sceneFileName();
    }

    public void setIconIndex(int i) {
        realmSet$iconIndex(i);
    }

    public int getIconIndex() {
        return realmGet$iconIndex();
    }

    public void setSceneDeviceRealmList(RealmList<SceneDevice> realmList) {
        realmSet$sceneDeviceRealmList(realmList);
    }

    public RealmList<SceneDevice> getSceneDeviceRealmList() {
        return realmGet$sceneDeviceRealmList();
    }
}
